import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';


import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import { BASE_URL, NOTICE } from '../common/config/host-config';
import { getToken, getUserRole } from '../common/util/login-util';

import NoticeNoComment from './NoticeNoComment';
import NoticeYesCommentBefore from './NoticeYesCommentBefore';
import NoticeYseCommentAfter from './NoticeYseCommentAfter';

import './css/NoticeContent.css';

// 공지사항 내용
const NoticeContent = () => {
    var noticeId = useParams().noticeId;
    
    const API_BASE_URL = BASE_URL + NOTICE;
    const ACCESS_TOKEN = getToken();        // 토큰값
    const USER_ROLE = getUserRole();        // 권한

    // 공지사항 api 데이터 
    const [noticeContents, setNoticeContents] = useState([]);

    const [modal, setModal] = useState(false); 

    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    // 렌더링 되자마자 할 일 => 공지사항 api GET 목록 호출
    useEffect(() => {
        fetch(`${API_BASE_URL}/${noticeId}`, {
            method: 'GET',
            headers: headerInfo
        })
            .then(res => {
                if (res.status === 406) {
                    if (ACCESS_TOKEN === '') {
                        alert('로그인이 필요한 서비스입니다');
                        window.location.href = '/join';
                    } else {
                        alert('오류가 발생했습니다. 잠시 후 다시 이용해주세요');
                        return;
                    }
                    return;
                } 
                else if (res.status === 500) {
                    alert('서버가 불안정합니다');
                    return;
                }
                return res.json();
            })
            .then(result => {
                console.log(result.noticeDetails[0]);
                setNoticeContents(result.noticeDetails[0]);
            });
    }, [API_BASE_URL]);

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 삭제 클릭 시 경고 모달
    const handleShowDeleteModal = () => {
        setModal(true);     // 모달 열기
    }

    // 공지사항 삭제 서버 요청 (DELETE)
    const handleDeleteNotice = () => {
        fetch(`${API_BASE_URL}/${noticeId}`, {
            method: 'DELETE',
            headers: headerInfo,
        })
        // .then(res => res.json())
        .then(() => {
            window.location.href = "/notice";       // 공지사항 목록 페이지로 이동
        });
    }

    // 공지사항 수정 페이지로
    const navigate = useNavigate();
    const onUpdatePage = () => {
        const path = `/notice/update/${noticeId}`;
        navigate(path);
    };

    return (
        <>
            <div id='notice_content_main'>
                <div className='justify'>
                    <div>
                        <Form id='notice_content_title'>
                            {noticeContents.boardTitle}
                        </Form>

                        <div id='notice_content_write'>
                            작성자 : {noticeContents.boardWriter} | 작성일 : {noticeContents.createDate}
                        </div>
                    </div>

                    {/* 권한이 ADMIN 인 경우에만 '수정','삭제' 버튼 보이도록 */}
                    {USER_ROLE === 'ADMIN' && 
                        <div id='notice_content_body_div'>
                            <Button className='btn_gray btn_size_100' onClick={onUpdatePage}>수정</Button>
                            <Button className='btn_orange btn_size_100' id='notice_content_delete_btn' onClick={handleShowDeleteModal}>삭제</Button>
                        </div>
                    }
                </div>

                {/* dangerouslySetInnerHTML : String형태를 html로 */}
                <div>
                    <Form id='notice_contents'
                        dangerouslySetInnerHTML={{
                            __html: noticeContents.boardContent
                        }} 
                    />
                </div>

                {/* 댓글 */}
                {noticeContents.boardCommentIs === 'ON'
                    ? 
                    (
                        <>
                            <div id='notice_content_comment_txt'>댓글</div>
                            {/* !!! 전/후 나누지 말고, 한번에 표현하기 (댓글 작성 칸 아래에 댓글들 보이게끔) */}
                            <NoticeYesCommentBefore />      {/* 댓글 작성 전 */}
                            {/* <NoticeYseCommentAfter />       댓글 작성 후 */}
                        </>
                    )
                    : <NoticeNoComment />       // 댓글 X
                }
            </div>

            {/* Modal */}
            <Modal show={modal} onHide={handleClose} id="notice_delete_modal">
                <Modal.Body id='notice_delete_modal_body'>
                    <div id='notice_delete_modal_content'>
                        공지사항을 삭제하시겠습니까?
                    </div>

                    <div id="notice_delete_modal_content">
                        <Button onClick={handleClose} className='btn_gray notice_btn btn_size_100'>
                            아니오
                        </Button>
                        <Button onClick={handleDeleteNotice} className='btn_orange notice_btn btn_size_100' id="notice_content_delete_btn">
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default NoticeContent;
