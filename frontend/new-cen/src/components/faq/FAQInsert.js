import React, { useState }  from 'react';
import { useParams, useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import Editor from '../common/EditorComponent';

import './css/FAQInsert.css';

// 자주 묻는 질문 추가
const FAQInsert = () => {

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

    // 자주 묻는 질문 목록 페이지로
    const navigate = useNavigate();
    const onFaqPage = () => {
        const path = `/faq`;
        navigate(path);
    };

    return (
        <>
            <div id='faq_insert_div'>
                <div className='justify'>
                    <Form>
                        <Form.Group className='mb-3'>
                            <Form.Control id='faq_insert_title' autoFocus type='text' placeholder='제목 입력' />
                        </Form.Group>
                    </Form>
                </div>

                <div>
                    <Editor value={desc} onChange={onEditorChange} />
                </div>

                <div id='faq_insert_footer_div'>
                    <Button className='btn_gray btn_size_100' onClick={handleShowCancelModal}>취소</Button>
                    <Button className='btn_orange btn_size_100' id='faq_insert_btn'>등록</Button>
                </div>
            </div>

{/* Modal */}
            <Modal show={modal} onHide={handleClose} id="faq_insert_modal">
                <Modal.Body id='faq_insert_modal_body'>
                    <div id='faq_insert_modal_content'>
                        작성하신 글을 취소하시면 저장되지 않습니다. <br />
                        그래도 취소하시겠습니까?
                    </div>

                    <div id="faq_insert_modal_content">
                        <Button className='btn_gray faq_btn btn_size_100' onClick={handleClose}>
                            아니오
                        </Button>
                        <Button className='btn_orange faq_btn btn_size_100' id="faq_insert_btn" onClick={onFaqPage}>
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default FAQInsert;