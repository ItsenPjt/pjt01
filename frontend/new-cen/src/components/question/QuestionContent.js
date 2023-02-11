import React, { useState } from 'react';
import { useParams } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import './css/QuestionContent.css';

// 문의사항 내용
const QuestionContent = () => {
    var questionId = useParams().questionId;
    
    const [modal, setModal] = useState(false); 

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 삭제 클릭 시 경고 모달
    const handleShowDeleteModal = () => {
        setModal(true);     // 모달 열기
    }

    // 문의사항 목록 페이지로
    const onQuestionPage = () => {
        window.location.href = "/question";
    }

    // 문의사항 수정 페이지로
    const onUpdatePage = () => {
        window.location.href = `/question/update/${questionId}`;
    }

    return (
        <>
            <div id='question_content_main'>
                <div className='justify'>
                    <div>
                        <Form id='question_content_title'>
                            (제목)
                        </Form>

                        <div id='question_content_write'>
                            작성자 : | 작성일 :
                        </div>
                    </div>

                    <div id='question_content_body_div'>
                        <Button className='btn_gray btn_size_100' onClick={onUpdatePage}>수정</Button>
                        <Button className='btn_orange btn_size_100' id='question_content_delete_btn' onClick={handleShowDeleteModal}>삭제</Button>
                    </div>
                </div>

                <div>
                    <Form id='question_contents'>
                        (내용)
                    </Form>
                </div>

            </div>

            {/* Modal */}
            <Modal show={modal} onHide={handleClose} id="question_delete_modal">
                <Modal.Body id='question_delete_modal_body'>
                    <div id='question_delete_modal_content'>
                        공지사항을 삭제하시겠습니까?
                    </div>

                    <div id="question_delete_modal_content">
                        <Button className='btn_gray question_btn btn_size_100' onClick={handleClose}>
                            아니오
                        </Button>
                        <Button className='btn_orange question_btn btn_size_100' id="question_content_delete_btn" onClick={onQuestionPage}>
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default QuestionContent;
