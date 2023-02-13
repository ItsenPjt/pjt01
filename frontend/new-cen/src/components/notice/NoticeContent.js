import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';


import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import { BASE_URL, NOTICE } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

import './css/NoticeContent.css';
import NoticeNoComment from './NoticeNoComment';
import NoticeYesCommentBefore from './NoticeYesCommentBefore';
import NoticeYseCommentAfter from './NoticeYseCommentAfter';

// 공지사항 내용
const NoticeContent = () => {
    var noticeId = useParams().noticeId;
    
    const API_BASE_URL = BASE_URL + NOTICE;
    const ACCESS_TOKEN = getToken();

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
                // if (res.status === 403) {
                //     alert('로그인이 필요한 서비스입니다');

                //     window.location.href = '/';
                //     return;
                // } 
                // else if (res.status === 500) {
                //     alert('서버가 불안정합니다');
                //     return;
                // }
                return res.json();
            })
            .then(result => {
                console.log(result.noticeDetails[0]);
                setNoticeContents(result.noticeDetails[0]);
            });
    }, [API_BASE_URL, noticeId]);

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 삭제 클릭 시 경고 모달
    const handleShowDeleteModal = () => {
        setModal(true);     // 모달 열기
    }

    // 공지사항 목록 페이지로
    const onNoticePage = () => {
        window.location.href = "/notice";
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

                    <div id='notice_content_body_div'>
                        <Button className='btn_gray btn_size_100' onClick={onUpdatePage}>수정</Button>
                        <Button className='btn_orange btn_size_100' id='notice_content_delete_btn' onClick={handleShowDeleteModal}>삭제</Button>
                    </div>
                </div>

                <div>
                    <Form id='notice_contents'>
                        {noticeContents.boardContent}
                    </Form>
                </div>

{/* 아래 3개 컴포넌트 DB 에서 데이터에 따라 이용 */}
                {/* 댓글 X */}
                <NoticeNoComment />
<br /><br /><br /><br /><hr />
                {/* 댓글 O - 댓글 작성 전 */}
                <NoticeYesCommentBefore />
<br /><br /><br /><br /><hr />
                {/* 댓글 O - 댓글 작성 후 */}
                <NoticeYseCommentAfter />
            </div>

            {/* Modal */}
            <Modal show={modal} onHide={handleClose} id="notice_delete_modal">
                <Modal.Body id='notice_delete_modal_body'>
                    <div id='notice_delete_modal_content'>
                        공지사항을 삭제하시겠습니까?
                    </div>

                    <div id="notice_delete_modal_content">
                        <Button className='btn_gray notice_btn btn_size_100' onClick={handleClose}>
                            아니오
                        </Button>
                        <Button className='btn_orange notice_btn btn_size_100' id="notice_content_delete_btn" onClick={onNoticePage}>
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default NoticeContent;
