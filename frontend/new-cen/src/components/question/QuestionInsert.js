import React, { useState }  from 'react';
import { useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import Editor from '../common/EditorComponent';

import './css/QuestionInsert.css';

// 문의사항 추가
const QuestionInsert = () => {

    const [modal, setModal] = useState(false); 
    const [desc, setDesc] = useState('');

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 취소 클릭 시 경고 모달
    const handleShowCancelModal = () => {
        setModal(true);     // 모달 열기
    }
    
    function onEditorChange(value) {
        setDesc(value);
    };

    // 문의사항 목록 페이지로
    const navigate = useNavigate();
    const onQuestionPage = () => {
        const path = `/question`;
        navigate(path);
    };

    return (
        <>
            <div id='question_insert_div'>
                <div className='justify'>
                    <Form>
                        <Form.Group className='mb-3'>
                            <Form.Control id='question_insert_title' autoFocus type='text' placeholder='문의사항 제목 입력' />
                        </Form.Group>
                    </Form>
                </div>

                <div>
                    <Editor value={desc} onChange={onEditorChange} />
                </div>

                <div id='question_insert_footer_div'>
                    <Button className='btn_gray btn_size_100' onClick={handleShowCancelModal}>취소</Button>
                    <Button className='btn_orange btn_size_100' id='question_insert_btn'>등록</Button>
                </div>
            </div>

{/* Modal */}
            <Modal show={modal} onHide={handleClose} id="question_insert_modal">
                <Modal.Body id='question_insert_modal_body'>
                    <div id='question_insert_modal_content'>
                        작성하신 글을 취소하시면 저장되지 않습니다. <br />
                        그래도 취소하시겠습니까?
                    </div>

                    <div id="question_insert_modal_content">
                        <Button className='btn_gray question_btn btn_size_100' onClick={handleClose}>
                            아니오
                        </Button>
                        <Button className='btn_orange question_btn btn_size_100' id="question_insert_btn" onClick={onQuestionPage}>
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default QuestionInsert;