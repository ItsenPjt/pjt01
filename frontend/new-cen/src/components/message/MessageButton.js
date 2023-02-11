import React, { useState } from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import './css/MessageButton.css';

// 메세지 버튼
const MessageButton = () => {

    const [modal, setModal] = useState(false); 

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 삭제 클릭 시 경고 모달
    const handleSendModal = () => {
        setModal(true);     // 모달 열기
    }

    return (
        <>
            <div className='justify'>
                <div id='message_button_txt'>메세지</div>
                <div id='message_button_group'>
                    <Button className='btn_indigo' id='message_button_sent'>보낸 쪽지</Button>
                    <Button className='btn_indigo' id='message_button_reception'>받은 쪽지</Button>
                    <Button className='btn_indigo' id='message_button_select_delete'>선택 삭제</Button>
                    <Button onClick={handleSendModal} className='btn_orange' id='message_button_send'>메세지 보내기</Button>
                </div>
            </div> 

            {/* Modal */}
            <Modal show={modal} onHide={handleClose} id="message_send_modal">
                <Modal.Body>
                    <div id='message_send_modal_body'>
                        <Form.Group className='mb-3'>
                            <Form.Label id='message_form_label'>받는 사람</Form.Label>
                            <Form.Control autoFocus type='text' className='message_form_control' placeholder='받는 사람'/>
                        </Form.Group>
                        <Form.Group className='mb-3'>
                            <Form.Label id='message_form_label'>제목</Form.Label>
                            <Form.Control type='text' className='message_form_control' placeholder='제목'/>
                        </Form.Group>
                        <Form.Group className='mb-3'>
                            <Form.Label id='message_form_label'>내용</Form.Label>
                            <textarea rows="5" className="form-control" id='message_send_content'/>
                        </Form.Group>
                    </div>

                    <div id='message_send_modal_footer'>
                        <Button className='btn_gray notice_btn btn_size_100' onClick={handleClose}>
                            취소
                        </Button>
                        <Button className='btn_orange notice_btn btn_size_100' id="notice_content_delete_btn">
                            보내기
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>    
    )
}

export default MessageButton