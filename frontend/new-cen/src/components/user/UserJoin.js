import React from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import './css/UserJoin.css';

import { BASE_URL, USER } from "../common/config/host-config";

// 로그인
const UserJoin = () => {

    const API_BASE_URL = BASE_URL + USER;

    // 엔터 키 눌렀을 때 동작하는 핸들러
    const onKeyPress = (e) => {
        // console.log(e.key);
        if (e.key === 'Enter') {
            loginHandler();
        }
    };

    const loginHandler = e => {

        // e.preventDefault();     // 기본 동작 중지

        // 이메일 입력태그, 비번 입력태그 가져오기
        const $email = document.getElementById('loginId');
        const $password = document.getElementById('loginPw');

        // console.log($email.value);
        // console.log($password.value);

        // 서버에 로그인 요청
        fetch(`${API_BASE_URL}/join`, {
            method: 'POST',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify({
                userEmail: $email.value,
                userPassword: $password.value
            })
        })
        .then(res => res.json())
        .then(result => {
            console.log(result);
            
            if (result.message) {
                // 로그인 실패
                alert(result.message);
            }
            else {
                //alert('로그인 성공..!!');

                // 발급받은 토큰을 저장, 회원정보 저장
                // 브라우저가 제공하는 로컬스토리지에 저장(브라우저가 종료되어도 남아있음) - 서버X 로컬O
                // 세션스토리지(브라우저가 종료되면 사라짐) - 서버X 로컬O
                localStorage.setItem('ACCESS_TOKEN', result.token);
                localStorage.setItem('LOGIN_USERNAME', result.userName);
                localStorage.setItem('LOGIN_USEREMAIL', result.userEmail);
                localStorage.setItem('LOGIN_USERROLE', result.userRole);

                window.location.href='/';
            }

        });

    }; // loginHandler()


    // 로고 클릭 시
    const onLogo = () => {
        window.location.href = "/";
    };

    // 비밀번호 찾기 버튼 클릭 시
    const onFindPwPage = () => {
        window.location.href = "/findpw";
    };

    // 회원가입 버튼 클릭 시
    const onJoinPage = () => {
        window.location.href = "/signup";
    };

    return (
        <div id='login_main'>
            <div id='login_logo_position'>
                <img id='login_logo_img' alt='logo' src="/img/logo_title.png" onClick={onLogo}/>
            </div>

            <div id='login_main_content'>
                <Form noValidate >
                    <Form.Group className='mb-3'>
                        <Form.Label id='login_form_label'>아이디</Form.Label>
                        <Form.Control autoFocus type='text' id='loginId' className='login_form_control' placeholder='이메일' onKeyDown={onKeyPress}/>
                    </Form.Group>

                    <Form.Group className='mb-3'>
                        <Form.Label id='login_form_label'>비밀번호</Form.Label>
                        <Form.Control type='password' id='loginPw' className='login_form_control' placeholder='비밀번호' onKeyDown={onKeyPress}/>
                    </Form.Group>
                </Form>

                <div id = 'login_footer_div'>
                    <Button className='btn_orange' id='login_btn' onClick={loginHandler}>
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
};

export default UserJoin;