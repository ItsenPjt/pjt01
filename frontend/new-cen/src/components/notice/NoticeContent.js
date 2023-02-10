import React, { useState } from 'react';
import { useParams } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import './css/NoticeContent.css';

// 공지사항 내용
const NoticeContent = () => {
    var noticeId = useParams().noticeId;
    
    const [modal, setModal] = useState(false); 

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
    const onUpdatePage = () => {
        window.location.href = `/notice/update/${noticeId}`;
    }

    return (
        <>
            <div id='notice_content_main'>
                <div className='justify'>
                    <div>
                        <Form id='notice_content_title'>
                            (제목)
                        </Form>

                        <div id='notice_content_write'>
                            작성자 : | 작성일 :
                        </div>
                    </div>

                    <div id='notice_content_body_div'>
                        <Button className='btn_gray btn_size_100' onClick={onUpdatePage}>수정</Button>
                        <Button className='btn_orange btn_size_100' id='notice_content_delete_btn' onClick={handleShowDeleteModal}>삭제</Button>
                    </div>
                </div>

                <div>
                    <Form id='notice_contents'>
                        (내용)
                    </Form>
                </div>

                <div id='notice_content_footer_div'>
                    <Button className='btn_gray btn_size_100' onClick={onNoticePage}>목록</Button>
                </div>
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
