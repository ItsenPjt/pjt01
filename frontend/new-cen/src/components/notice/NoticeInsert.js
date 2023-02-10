import React, { useState}  from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import Editor from '../common/EditorComponent';
import CommentRadioBtn from '../common/CommentRadioBtn';

import '../css/notice/NoticeInsert.css';

const NoticeInsert = () => {

    const [modal, setModal] = useState(false); 
    const [desc, setDesc] = useState('');
    function onEditorChange(value) {
        setDesc(value);
    };

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 취소 클릭 시 경고 모달
    const handleShowCalcelModal = () => {
        setModal(true);     // 모달 열기
    }

    const onNoticePage = () => {
        window.location.href = "/notice";
    }

    return (
        <>
            <div className='insert_div'>

                <div className='insert_justify'>
                    <Form>
                        <Form.Group className='mb-3'>
                            <Form.Control className='insert_title' type='text' placeholder='공지사항 제목 입력' />
                        </Form.Group>
                    </Form>

                     {/* 라디오 버튼 */} 
                    <CommentRadioBtn />
                </div>

                <div>
                    <Editor value={desc} onChange={onEditorChange} />
                </div>

                <div className='insert_btn'>
                    <Button className='notice_btn_gray btn_size' onClick={handleShowCalcelModal}>취소</Button>
                    <Button className='notice_btn_orange btn_size'>등록</Button>
                </div>
            </div>

            <Modal show={modal} onHide={handleClose} id="header-modal">
                <Modal.Body className='notice_modal_body'>
                    <div className='notice_modal_content'>
                        작성하신 글을 취소하시면 저장되지 않습니다. <br />
                        그래도 취소하시겠습니까?
                    </div>

                    <div className="notice_modal_content">
                        <Button className='notice_btn_gray notice_btn btn_size' id="userInformation_modalSearch" onClick={handleClose}>
                            아니오
                        </Button>
                        <Button className='notice_btn_orange notice_btn btn_size' id="userInformation_modalClose" onClick={onNoticePage}>
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default NoticeInsert;