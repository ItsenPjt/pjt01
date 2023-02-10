import React, { useState } from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import './css/MyPage.css';

const MyPage = () => {

    const [modal, setModal] = useState(false); 

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };


    // 회원 탈퇴 버튼 클릭 시 경고 모달
    const handleShowModal = () => {
        setModal(true);     // 모달 열기
    }

    return (
        <>
            <div id='mypage_main'>
                <div id='mypage_logo_position'>
                    <img id='mypage_logo_img' alt='logo' src="/img/logo_title.png"/>
                </div>

                <div id='mypage_main_content'>
                    <Form>
                        <Form.Group className='mb-3'>
                            <Form.Label id='mypage_form_label'>아이디</Form.Label>
                            <Form.Control type='text' className='mypage_form_control' />
                        </Form.Group>

                        <Form.Group className='mb-3'>
                            <Form.Label id='mypage_form_label'>비밀번호</Form.Label>
                            <Form.Control type='password' className='mypage_form_control' placeholder='비밀번호'/>
                        </Form.Group>

                        <Form.Group className='mb-3'>
                            <Form.Label id='mypage_form_label'>비밀번호 재확인</Form.Label>
                            <Form.Control type='password' className='mypage_form_control' placeholder='비밀번호 확인'/>
                        </Form.Group>

                        <Form.Group className='mb-3'>
                            <Form.Label id='mypage_form_label'>이름</Form.Label>
                            <Form.Control type='text' className='mypage_form_control' />
                        </Form.Group>
                    </Form>

                    <div id = 'mypage_div'>
                        <Button className="btn_gray mypage_btn btn_size_100">
                            취소
                        </Button> 
                        <Button className="btn_orange mypage_btn btn_size_100" id="mypage_btn">
                            완료
                        </Button> 
                    </div>
                </div>
                <div onClick={handleShowModal} id = 'mypage_withdrawal'>
                    회원 탈퇴
                </div>
            </div>

            <Modal show={modal} onHide={handleClose} id="mypage_modal">
                <Modal.Body id='mypage_modal_body'>
                    <div id='mypage_modal_content'>
                        회원탈퇴를 진행하시겠습니까? <br />
                        탈퇴를 원하신다면 아래 글을 작성해주세요.
                    </div>

                    <div id='mypage_modal_message'>
                        지금<br />
                        당신이 주연인<br />
                        당신의 영화도<br />
                        멋진 <input type='text' autoFocus id='mypage_modal_message_write' placeholder='해피엔딩' /> 으로<br/>
                        끝나기를
                    </div>
                    <div id="notice_modal_content">
                        <Button className='btn_gray notice_btn btn_size_100' onClick={handleClose}>
                            취소
                        </Button>
                        <Button className='btn_orange notice_btn btn_size_100' id="mypage_withdrawal_btn">
                            탈퇴
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default MyPage;