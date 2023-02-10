import React, { useState }  from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import Editor from '../common/EditorComponent';
import CommentRadioBtn from '../common/CommentRadioBtn';

import './css/NoticeInsert.css';

const NoticeInsert = () => {

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

    const onNoticePage = () => {
        window.location.href = "/notice";
    }

    return (
        <>
            <div id='insert_div'>
                <div className='justify'>
                    <Form>
                        <Form.Group className='mb-3'>
                            <Form.Control id='insert_title' autoFocus type='text' placeholder='공지사항 제목 입력' />
                        </Form.Group>
                    </Form>

                     {/* 라디오 버튼 */} 
                    <CommentRadioBtn />
                </div>

                <div>
                    <Editor value={desc} onChange={onEditorChange} />
                </div>

                <div id='notice_insert_div'>
                    <Button className='btn_gray btn_size_100' onClick={handleShowCancelModal}>취소</Button>
                    <Button className='btn_orange btn_size_100' id='notice_insert_btn'>등록</Button>
                </div>
            </div>

            <Modal show={modal} onHide={handleClose} id="notice_modal">
                <Modal.Body id='notice_modal_body'>
                    <div id='notice_modal_content'>
                        작성하신 글을 취소하시면 저장되지 않습니다. <br />
                        그래도 취소하시겠습니까?
                    </div>

                    <div id="notice_modal_content">
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