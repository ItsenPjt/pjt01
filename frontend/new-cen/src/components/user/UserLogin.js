import React from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import './css/UserLogin.css';

const UserLogin = () => {

    // 비밀번호 찾기 버튼 클릭 시
    const onFindPwPage = () => {
        window.location.href = "/findpw";
    }

    // 회원가입 버튼 클릭 시
    const onJoinPage = () => {
        window.location.href = "/join";
    }

    return (
        <div id='login_main'>
            <div id='login_logo_position'>
                <img id='login_logo_img' alt='logo' src="/img/logo_title.png"/>
            </div>

            <div id='login_main_content'>
                <Form>
                    <Form.Group className='mb-3'>
                        <Form.Label id='login_form_label'>아이디</Form.Label>
                        <Form.Control autoFocus type='text' className='login_form_control' placeholder='이메일' />
                    </Form.Group>

                    <Form.Group className='mb-3'>
                        <Form.Label id='login_form_label'>비밀번호</Form.Label>
                        <Form.Control type='password' className='login_form_control' placeholder='비밀번호' />
                    </Form.Group>
                </Form>

                <div id = 'login_div'>
                    <Button className='btn_orange' id='login_btn'>
                        로그인
                    </Button> 
                </div>
                
                <div className='justify' id='login_justify'>
                    <div onClick={onFindPwPage} className='on_pointer'>비밀번호 찾기</div>
                    <div onClick={onJoinPage} className='on_pointer'>회원가입</div>
                </div>
            </div>
        </div>
    )
}

export default UserLogin;