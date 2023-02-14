import React from 'react'
import { useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import './css/UserFindInfo.css';

import { BASE_URL, USER } from "../common/config/host-config";

import { getToken } from '../common/util/login-util';
import { getUserEmail } from '../common/util/login-util';
import { getUsername } from '../common/util/login-util';

// 익명 사용자 비밀번호 변경
const UserFindInfo = () => {

    // const API_BASE_URL = BASE_URL + USER;
    // const ACCESS_TOKEN = getToken();

    // const headerInfo = {
    //     'content-type': 'application/json'
    //     , 'Authorization': 'Bearer ' + ACCESS_TOKEN
    // };


    // // 내 정보 수정 요청 처리
    // const updateUser = e => {

    //     fetch(`${API_BASE_URL}/signset/getUserEmail`)

    // };




    // 로고 클릭 시
    const onLogo = () => {
        window.location.href = "/";
    };

    // 취소 버튼 클릭 시(헤더없는 경우)
    const onLoginPage = () => {
        window.location.href = "/";
    };

    // // 취소 버튼 클릭 시(헤더있는 경우)
    // const navigate = useNavigate();
    // const onLoginPage = () => {
    //     const path = `/`;
    //     navigate(path);
    // };

    return (
        <div id='find_pw_main'>
            <div id='find_pw_logo_position'>
                <img id='find_pw_logo_img' alt='logo' src="/img/logo_title.png"  onClick={onLogo}/>
            </div>

            <div id='find_pw_main_content'>
                <div id='user_find_title'>
                    비밀번호를 잊어버리셨나요? <br/>
                    아래 정보를 입력해주세요.
                </div>
            
                <Form>
                    <Form.Group className='mb-3'>
                        <Form.Label id='find_pw_form_label'>아이디</Form.Label>
                        <Form.Control autoFocus type='text' className='find_pw_form_control' placeholder='itcen02@sicc.co.kr' />
                    </Form.Group>

                    <Form.Group className='mb-3'>
                        <Form.Label id='find_pw_form_label'>이름</Form.Label>
                        <Form.Control type='text' className='find_pw_form_control' placeholder='이름' />
                    </Form.Group>

                    <Form.Group className='mb-3'>
                        <Form.Label id='find_pw_form_label'>사내 인증코드</Form.Label>
                        <Form.Control type='text' className='find_pw_form_control' placeholder='인증코드' />
                    </Form.Group>
                    
                    <Form.Group className='mb-3'>
                        <Form.Label id='find_pw_form_label'>새 비밀번호</Form.Label>
                        <Form.Control type='password' className='find_pw_form_control' placeholder='비밀번호' />
                    </Form.Group>

                    <Form.Group className='mb-3'>
                        <Form.Label id='find_pw_form_label'>새 비밀번호 재확인</Form.Label>
                        <Form.Control type='password' className='find_pw_form_control' placeholder='비밀번호 확인' />
                    </Form.Group>
                </Form>

                <div id = 'find_pw_footer_div'>
                    <Button onClick={onLoginPage} className="btn_gray find_pw_btn btn_size_100">
                        취소
                    </Button> 
                    <Button className="btn_orange find_pw_btn btn_size_100" id="user_find_change_btn">
                        변경
                    </Button> 
                </div>
                
            </div>
        </div>
    )
}

export default UserFindInfo