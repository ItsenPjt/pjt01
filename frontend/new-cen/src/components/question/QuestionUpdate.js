import React, { useState }  from 'react';
import { useParams } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import Editor from '../common/EditorComponent';

import './css/QuestionUpdate.css';

// 문의사항 수정
const QuestionUpdate = () => {
    var questionId = useParams().questionId;

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

    // 공지사항 내용 페이지로
    const onQuestionContentPage = () => {
        window.location.href = `/question/${questionId}`;
    }

    return (
        <>
            <div id='question_update_div'>
                <div className='justify'>
                    <Form>
                        <Form.Group className='mb-3'>
                            <Form.Control id='question_update_title' autoFocus type='text'/>
                        </Form.Group>
                    </Form>
                </div>

                <div>
                    <Editor value={desc} onChange={onEditorChange} />
                </div>

                <div id='question_update_footer_div'>
                    <Button className='btn_gray btn_size_100' onClick={handleShowCancelModal}>취소</Button>
                    <Button className='btn_orange btn_size_100' id='question_update_btn'>수정</Button>
                </div>
            </div>

{/* Modal */}
            <Modal show={modal} onHide={handleClose} id="questione_update_modal">
                <Modal.Body id='question_update_modal_body'>
                    <div id='question_update_modal_content'>
                        작성하신 글을 취소하시면 저장되지 않습니다. <br />
                        그래도 취소하시겠습니까?
                    </div>

                    <div id="question_update_modal_content">
                        <Button className='btn_gray question_btn btn_size_100' onClick={handleClose}>
                            아니오
                        </Button>
                        <Button className='btn_orange question_btn btn_size_100' id="question_update_btn" onClick={onQuestionContentPage}>
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default QuestionUpdate;
