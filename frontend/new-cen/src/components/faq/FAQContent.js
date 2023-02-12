import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import './css/FAQContent.css';

// 자주 묻는 질문 내용
const FAQContent = () => {
    var faqId = useParams().faqId;
    
    const [modal, setModal] = useState(false); 

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 삭제 클릭 시 경고 모달
    const handleShowDeleteModal = () => {
        setModal(true);     // 모달 열기
    }

    // 자주 묻는 질문 목록 페이지로
    const onFaqPage = () => {
        window.location.href = "/faq";
    }

    // 자주 묻는 질문 수정 페이지로
    const navigate = useNavigate();
    const onUpdatePage = () => {
        const path = `/faq/update/${faqId}`;
        navigate(path);
    };

    return (
        <>
            <div id='faq_content_main'>
                <div className='justify'>
                    <div>
                        <Form id='faq_content_title'>
                            (제목)
                        </Form>

                        <div id='faq_content_write'>
                            작성자 : | 작성일 :
                        </div>
                    </div>

                    <div id='faq_content_body_div'>
                        <Button className='btn_gray btn_size_100' onClick={onUpdatePage}>수정</Button>
                        <Button className='btn_orange btn_size_100' id='faq_content_delete_btn' onClick={handleShowDeleteModal}>삭제</Button>
                    </div>
                </div>

                <div>
                    <Form id='faq_contents'>
                        (내용)
                    </Form>
                </div>

            </div>

            {/* Modal */}
            <Modal show={modal} onHide={handleClose} id="faq_delete_modal">
                <Modal.Body id='faq_delete_modal_body'>
                    <div id='faq_delete_modal_content'>
                        게시물을 삭제하시겠습니까?
                    </div>

                    <div id="faq_delete_modal_content">
                        <Button className='btn_gray faq_btn btn_size_100' onClick={handleClose}>
                            아니오
                        </Button>
                        <Button className='btn_orange faq_btn btn_size_100' id="faq_content_delete_btn" onClick={onFaqPage}>
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default FAQContent;
