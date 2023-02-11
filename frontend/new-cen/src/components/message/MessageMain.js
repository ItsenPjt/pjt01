import React, { useState } from 'react';

import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import MessageButton from './MessageButton';

import './css/MessageMain.css';

// 메세지 메인
const MessageMain = () => {

    const [modal, setModal] = useState(false); 

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 제목 클릭 시 상세 모달
    const handleShowModal = () => {
        setModal(true);     // 모달 열기
    }

    return (
        <>
            <div id='message_btn_main'>
                <MessageButton />
                <div id='message_table_main'>
                    <Table responsive id='message_table'>
                        <thead>
                            <tr id='message_main_thead'>
                                <th width="10%">보낸 사람</th>
                                <th width="20%">제목</th>
                                <th width="15%">날짜</th>
                                <th width="15%" id='message_main_all_select'>전체 선택</th>
                            </tr>
                        </thead>
                        <tbody>

                            {/* db 연결하여 map 함수 이용 */}

                                {/* 아래는 임시 데이터 -> noticeId = 1이라 가정 */}
                                <tr id='message_main_tbody'>
                                    <td>허진영</td>
                                    <th onClick={handleShowModal} id='message_main_tbody_th'>연봉은 대체 얼마인가요?</th>
                                    <td>2023-01-18</td>
                                    <td><input type='checkbox' id='message_select_checkbox' /></td>
                                </tr>
                        </tbody>
                    </Table >   
                </div>
            </div>

            {/* Modal */}
            <Modal show={modal} onHide={handleClose} id="message_send_modal">
                <Modal.Body>
                    <div id='message_send_modal_body'>
                        <Form.Group className='mb-3'>
                            <Form.Label id='message_form_label'>보낸 사람</Form.Label>
                            <Form.Control type='text' className='message_form_control'/>
                        </Form.Group>
                        <Form.Group className='mb-3'>
                            <Form.Label id='message_form_label'>제목</Form.Label>
                            <Form.Control type='text' className='message_form_control'/>
                        </Form.Group>
                        <Form.Group className='mb-3'>
                            <Form.Label id='message_form_label'>내용</Form.Label>
                            <textarea rows="5" className="form-control" id='message_send_content'/>
                        </Form.Group>
                    </div>

                    <div id='message_send_modal_footer'>
                        <Button className='btn_gray notice_btn btn_size_100' onClick={handleClose}>
                            닫기
                        </Button>
                        <Button className='btn_orange notice_btn btn_size_100' id="notice_content_delete_btn">
                            답장하기
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default MessageMain