import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import { BASE_URL, NOTICE } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

import Editor from '../common/EditorComponent';
import CommentRadioBtn from '../common/CommentRadioBtn';

import './css/NoticeUpdate.css';

// 공지사항 수정
const NoticeUpdate = () => {
    var noticeId = useParams().noticeId;

    const API_BASE_URL = BASE_URL + NOTICE;
    const ACCESS_TOKEN = getToken();

    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    // 공지사항 api 데이터 
    const [noticeData, setNoticeData] = useState({
        boardTitle: '',          // 공지사항 제목
        boardCommentIs: 'ON',      // 공지사항 댓글 여부 (default: ON)
        boardContent: '',        // 공지사항 내용
    });
    const [modal, setModal] = useState(false); 

    // title
    const titleChangeHandler = e => {
        setNoticeData({
            ...noticeData,        // 기존 noticeData 복사 후 boardTitle 추가
            boardTitle: e.target.value
        });
    };

    // 댓글 허용 여부 (radio)
    const commentChangeHandler = commentStatus => {
        setNoticeData({
            ...noticeData,        // 기존 noticeData 복사 후 boardCommentIs 추가
            boardCommentIs: commentStatus
        });
    }

    // 내용
    const contentChangeHandler = value => {
        setNoticeData({
            ...noticeData,        // 기존 noticeData 복사 후 boardContent 추가
            boardContent: value
        });
    };

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
                setNoticeData({
                    boardTitle: result.noticeDetails[0].boardTitle,
                    boardCommentIs: result.noticeDetails[0].boardCommentIs,
                    boardContent: result.noticeDetails[0].boardContent
                })
            });
    }, [API_BASE_URL]);

    // 공지사항 api PATCH 수정 호출
    const handleUpdateNotice = () => {
        fetch(`${API_BASE_URL}/${noticeId}`, {
            method: 'PATCH',
            headers: headerInfo,
            body: JSON.stringify(noticeData)
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
        .then(() => {
            window.location.href = `/notice/${noticeId}`;       // 해당 공지사항 페이지로 이동
        });
    }

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 취소 클릭 시 경고 모달
    const handleShowCancelModal = () => {
        setModal(true);     // 모달 열기
    }

    // 공지사항 내용 페이지로
    const navigate = useNavigate();
    const onNoticeContentPage = () => {
        const path = `/notice/${noticeId}`;
        navigate(path);
    };

    return (
        <>
            <div id='notice_update_div'>
                <div className='justify'>
                    <Form>
                        <Form.Group className='mb-3'>
                            <Form.Control onChange={titleChangeHandler} defaultValue={noticeData.boardTitle} id='notice_update_title' autoFocus type='text'/>
                        </Form.Group>
                    </Form>

                     {/* 라디오 버튼 */} 
                    <CommentRadioBtn updateBeforeComment={noticeData.boardCommentIs} />     {/* commentStatus={commentChangeHandler} */}
                </div>

                <div>
                    <Editor onChange={contentChangeHandler} value={noticeData.boardContent}/>
                </div>

                <div id='notice_update_footer_div'>
                    <Button onClick={handleShowCancelModal} className='btn_gray btn_size_100'>취소</Button>
                    <Button onClick={handleUpdateNotice} className='btn_orange btn_size_100' id='notice_update_btn'>수정</Button>
                </div>
            </div>

{/* Modal */}
            <Modal show={modal} onHide={handleClose} id="noticee_update_modal">
                <Modal.Body id='notice_update_modal_body'>
                    <div id='notice_update_modal_content'>
                        작성하신 글을 취소하시면 저장되지 않습니다. <br />
                        그래도 취소하시겠습니까?
                    </div>

                    <div id="notice_update_modal_content">
                        <Button onClick={handleClose} className='btn_gray notice_btn btn_size_100'>
                            아니오
                        </Button>
                        <Button onClick={onNoticeContentPage} className='btn_orange notice_btn btn_size_100' id="notice_update_btn">
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default NoticeUpdate;
