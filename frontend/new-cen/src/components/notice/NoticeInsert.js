import React, { useState }  from 'react';
import { useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import { BASE_URL, NOTICE } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

import Editor from '../common/EditorComponent';
import CommentRadioBtn from '../common/CommentRadioBtn';

import './css/NoticeInsert.css';
import NoticeMain from './NoticeMain';

// 공지사항 추가
const NoticeInsert = () => {

    const API_BASE_URL = BASE_URL + NOTICE;
    const ACCESS_TOKEN = getToken();
    
    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    const [modal, setModal] = useState(false); 
    const [noticeData, setNoticeData] = useState({          // 공지사항 입력 데이터
        boardTitle: '',          // 공지사항 제목
        boardCommentIs: 'ON',      // 공지사항 댓글 여부 (default: ON)
        boardContent: '',        // 공지사항 내용
    });

    // title
    const titleChangeHandler = e => {
        setNoticeData({
            ...noticeData,        // 기존 todo데이터 복사 후 boardTitle 추가
            boardTitle: e.target.value,
        });
    };

    // 댓글 허용 여부 (radio)
    const commentChangeHandler = commentStatus => {
        setNoticeData({
            ...noticeData,        // 기존 todo데이터 복사 후 boardCommentIs 추가
            boardCommentIs: commentStatus,
        });
    }

    // 내용
    const contentChangeHandler = value => {
        setNoticeData({
            ...noticeData,        // 기존 todo데이터 복사 후 boardContent 추가
            boardContent: value,
        });
    };

    // 공지사항 등록 서버 요청  (POST에 대한 응답처리)
    const handleInsertNotice = () => {
        console.log(noticeData);
        console.log(ACCESS_TOKEN);

        fetch(API_BASE_URL, {
            method: 'POST',
            headers: headerInfo,
            body: JSON.stringify(noticeData)
        })
        .then(res => {
            if (res.status === 406) {
                alert('로그인이 필요한 서비스입니다');

                window.location.href = '/join';
                return;
            } 
            else if (res.status === 500) {
                alert('서버가 불안정합니다');
                return;
            }
            return res.json();
        })
        .then(() => {
            window.location.href = "/notice";       // 공지사항 메인 페이지로 이동
        });
    };

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 취소 클릭 시 경고 모달
    const handleShowCancelModal = () => {
        setModal(true);     // 모달 열기
    }

    // 공지사항 목록 페이지로
    const navigate = useNavigate();
    const onNoticePage = () => {
        const path = `/notice`;
        navigate(path);
    };

    return (
        <>
            <div id='notice_insert_div'>
                <div className='justify'>
                    <Form>
                        <Form.Group className='mb-3'>
                            <Form.Control onChange={titleChangeHandler} value={noticeData.boardTitle} id='notice_insert_title' autoFocus type='text' placeholder='공지사항 제목 입력' />
                        </Form.Group>
                    </Form>

                     {/* 라디오 버튼 */} 
                    <CommentRadioBtn commentStatus={commentChangeHandler}/>
                </div>

                <div>
                    <Editor onChange={contentChangeHandler} value={noticeData.boardContent} />
                </div>

                <div id='notice_insert_footer_div'>
                    <Button onClick={handleShowCancelModal} className='btn_gray btn_size_100'>취소</Button>
                    <Button onClick={handleInsertNotice} className='btn_orange btn_size_100' id='notice_insert_btn'>등록</Button>
                </div>
            </div>

{/* Modal */}
            <Modal show={modal} onHide={handleClose} id="notice_insert_modal">
                <Modal.Body id='notice_insert_modal_body'>
                    <div id='notice_insert_modal_content'>
                        작성하신 글을 취소하시면 저장되지 않습니다. <br />
                        그래도 취소하시겠습니까?
                    </div>

                    <div id="notice_insert_modal_content">
                        <Button className='btn_gray notice_btn btn_size_100' onClick={handleClose}>
                            아니오
                        </Button>
                        <Button className='btn_orange notice_btn btn_size_100' id="notice_insert_btn" onClick={onNoticePage}>
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default NoticeInsert;