import React from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import './css/UserJoin.css';

const UserJoin = () => {

    // 취소 버튼 클릭 시
    const onLoginPage = () => {
        window.location.href = "/";
    }
    
    return (
        <div id='join_main'>
            <div id='join_logo_position'>
                <img id='join_logo_img' alt='logo' src="/img/logo_title.png"/>
            </div>

            <div id='join_main_content'>
                <Form>
                    <Form.Group className='mb-3'>
                        <Form.Label id='join_form_label'>아이디</Form.Label>
                        <Form.Control autoFocus type='text' className='join_form_control' placeholder='이메일' />
                    </Form.Group>

                    <Form.Group className='mb-3'>
                        <Form.Label id='join_form_label'>비밀번호</Form.Label>
                        <Form.Control type='text' className='join_form_control' placeholder='비밀번호' />
                    </Form.Group>

                    <Form.Group className='mb-3'>
                        <Form.Label id='join_form_label'>비밀번호 재확인</Form.Label>
                        <Form.Control type='text' className='join_form_control' placeholder='비밀번호 확인' />
                    </Form.Group>

                    <Form.Group className='mb-3'>
                        <Form.Label id='join_form_label'>이름</Form.Label>
                        <Form.Control type='text' className='join_form_control' placeholder='이름' />
                    </Form.Group>

                    <Form.Group className='mb-3'>
                        <Form.Label id='join_form_label'>사내 인증코드</Form.Label>
                        <Form.Control type='text' className='join_form_control' placeholder='인증코드' />
                    </Form.Group>
                </Form>

                <div id = 'join_div'>
                    <Button onClick={onLoginPage} className="btn_gray join_btn btn_size_100">
                        취소
                    </Button> 
                    <Button className="btn_orange join_btn btn_size_100" id="user_jon_btn">
                        가입
                    </Button> 
                </div>
                
            </div>
        </div>
    )
}

export default UserJoin;