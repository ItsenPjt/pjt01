import React, { useState } from 'react'
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

    const API_BASE_URL = BASE_URL + USER;

    // 검증 메세지 저장
    const [message, setMessage] = useState({
        userEmail: '',
        userPassword: '',
        userPasswordCheck: '',
        userName: '',
        validCode: ''
    });

    // 검증 완료 여부
    const [validate, setValiDate] = useState({
        userEmail: false,
        userPassword: false,
        userPasswordCheck: false,
        userName: false,
        validCode: false
    });

    // 서버에서 내려온 인증코드
    const [responseValidCode , setResponseValidCode] = useState('');

    // 입력값 저장
    const [userValue, setUserValue] = useState({
        userEmail: '',
        userPassword: '',
        userName: '',
        validCode: ''
    });


    // 이메일 존재 여부 확인 요청(해당 이메일 인증코드 같이 받아서 저장)
    const checkEmail = userEmail => {

        fetch(`${API_BASE_URL}/valid/${userEmail}`)
        .then(res => {
            if (res.status === 200) {
                return res.json();
            }
        })
        .then(flag => {
            let msg;
            if (flag) {
                msg = '등록된 이메일 입니다.';
                setValiDate({
                    ...validate,
                    userEmail: true
                });
                setResponseValidCode(flag.validCode);   // 인증코드 상태변수에 저장
            } else {
                let msg = '등록되지 않은 이메일입니다.';
                setValiDate({
                    ...validate,
                    userEmail: false
                });
                setMessage({
                    ...message,
                    userEmail: msg
                });
                return;
            }
            setValiDate({
                ...validate,
                userEmail: true
            });
            setMessage({
                ...message,
                userEmail: msg
            });
        });

    };


    // 유저 이메일 입력란 검증 체인지 이벤트 핸들러
    const userEmailHandler = e => {

        //console.log(e.target.value);

        // eslint-disable-next-line
        const emailRegex = /^[a-z0-9\.\-_]+@([a-z0-9\-]+\.)+[a-z]{2,6}$/;

        // 이메일 검증 시작
        let msg;
        if (!e.target.value) {
            msg = '이메일은 필수 입력값입니다!';
            setValiDate({...validate, userEmail: false});
        } else if (!emailRegex.test(e.target.value)) {
            msg = '이메일 형식이 아닙니다!';
            setValiDate({...validate, userEmail: false});
        } else {
            checkEmail(e.target.value);
        }
        setMessage({
            ...message,
             userEmail: msg
        });
        setUserValue({
            ...userValue,
            userEmail: e.target.value
        });

    };


    // 사내 인증코드 입력란 검증 체인지 이벤트 핸들러
    const validCodeHandler = e => {
        // console.log(e.target.value);
        
        // 사내코드 검증 시작
        let msg;
        if (!e.target.value) {
            msg = '인증코드는 필수 입력값입니다!';
            setValiDate({...validate, validCode: false});
        } else if (responseValidCode !== e.target.value) {
            msg = '정확한 인증코드를 입력하세요!';
            setValiDate({...validate, validCode: false});
        } else if (responseValidCode === e.target.value) {
            msg = '인증완료';
            setValiDate({
                ...validate,
                validCode: e.target.value
            });
        }
        setMessage({
            ...message,
             validCode: msg
        });
        setUserValue({
            ...userValue,
            validCode: e.target.value
        });

    };
    

    // 유저 이름 입력란 검증 체인지 이벤트 핸들러
    const userNameHandler = e => {

        //console.log(e.target.value);

        const nameRegex = /^[가-힣]{2,10}$/;

        // 이름 검증 시작
        let msg;
        if (!e.target.value) {  // 유저 이름 안 적을 시
            msg = '이름은 필수 입력값입니다!';
            setValiDate({
                ...validate,
                userName: false
            });
        } else if (!nameRegex.test(e.target.value)) {
            msg = '2~10글자 사이의 한글로만 작성해주세요!';
            setValiDate({
                ...validate,
                userName: false
            });
        } else {
            msg = '사용 가능한 이름입니다.';
            setValiDate({
                ...validate,
                userName: true
            });
        }
        setMessage({
            ...message,
            userName: msg
        });

        setUserValue({
            ...userValue,
            userName: e.target.value
        });
    };


    // 비밀번호 입력란 검증 체인지 이벤트 핸들러
    const userPasswordHandler = e => {

        // 패스워드 입력값 변경 시 패스워드 확인란을 비워버리기
        document.getElementById('userPasswordRecheck').value = '';
        document.getElementById('check-pass').textContent = '';
        setValiDate({
            ...validate,
            userPasswordCheck: false
        });

        const pwRegex =  /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$/;

        // 비번 검증 시작
        let msg;
        if (!e.target.value) {  // 비밀번호 입력 안 했을 시
            msg = '비밀번호는 필수 입력값입니다.';
            setValiDate({
                ...validate,
                userPassword: false
            });
        } else if (!pwRegex.test(e.target.value)) {
            msg = '8글자 이상의 영문, 숫자, 특수문자를 포함해 주세요!';
            setValiDate({
                ...validate,
                userPassword: false
            });
        } else {
            msg = '사용 가능한 비밀번호입니다.';
            setValiDate({
                ...validate,
                userPassword: true
            });
        }
        setMessage({
            ...message,
            userPassword: msg
        });
        setUserValue({
            ...userValue,
            userPassword: e.target.value
        });
    };


    // 비밀번호 재확인 입력란 검증 체인지 이벤트 핸들러
    const userPasswordCheckHandler = e => {

        // 비밀번호 재확인 검증 시작
        let msg;
        if (!e.target.value) {  // 재확인 비밀번호 미 입력 시
            msg = '비밀번호 확인란은 필수 입력값입니다.';
            setValiDate({
                ...validate,
                userPasswordCheck: false
            });
        } else if (userValue.userPassword !== e.target.value) { // 비밀번호와 재입력란 값이 다른 경우
            msg = '비밀번호가 일치하지 않습니다.';
            setValiDate({
                ...validate,
                userPasswordCheck: false
            });
        } else {
            msg = '비밀번호가 일치합니다.';
            setValiDate({
                ...validate,
                userPasswordCheck: true
            });
        }
        setMessage({
            ...message,
            userPasswordCheck: msg
        });
    };


    // validate 객체 안의 모든 논리값이 true인지 검사하는 함수
    const isValid = () => {

        // of : 배열 반복, in : 객체 반복
        // 객체에서 key값만 뽑아줌 'userName'
        for (let key in validate) {
            let value = validate[key];
            if (!value) return false;
        }
        return true;
    };


    // 익명 사용자 정보 수정 서버 요청
    const anonymousUserUpdate = e => {

        if (isValid()) {
            fetch(`${API_BASE_URL}/findset`, {
                method: 'PATCH',
                headers: {
                    'content-type': 'application/json'
                },
                body: JSON.stringify(userValue)
            })
            .then(res => {
                // console.log(res.status);
                if (res.status === 200) {
                    alert(`회원정보가 정상적으로 변경되었습니다.`);
                    // 메인 페이지로 리다이렉트
                    window.location.href = '/';
                } else {
                    alert(`회원정보 수정에 실패했습니다.\n잠시 후 다시 시도해 주세요.`);
                    // window.location.href = '/findinfo';
                }
                return res.json();
            });
        } else {
            alert(`입력창을 확인하세요.`);
        }
    };



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
                <img id='find_pw_logo_img' alt='logo' src="/img/logo_title.png" onClick={onLogo} />
            </div>

            <div id='find_pw_main_content'>
                <div id='user_find_title'>
                    비밀번호를 잊어버리셨나요? <br/>
                    아래 정보를 입력해주세요.
                </div>
            
                <Form>
                    <Form.Group className='mb-3'>
                        <Form.Label id='find_pw_form_label'>아이디</Form.Label>
                        <Form.Control autoFocus type='text' className='find_pw_form_control' placeholder='itcen@sicc.co.kr' onChange={userEmailHandler} />
                        <span style={
                            validate.userEmail
                            ? {color: 'green'}
                            : {color: 'red'}
                        }>{message.userEmail}</span>
                    </Form.Group>

                    <Form.Group className='mb-3'>
                        <Form.Label id='find_pw_form_label'>이름</Form.Label>
                        <Form.Control type='text' className='find_pw_form_control' placeholder='이름' onChange={userNameHandler} />
                        <span style={
                            validate.userName
                            ? {color: 'green'}
                            : {color: 'red'}
                        }>{message.userName}</span>
                    </Form.Group>

                    <Form.Group className='mb-3'>
                        <Form.Label id='find_pw_form_label'>사내 인증코드</Form.Label>
                        <Form.Control type='text' className='find_pw_form_control' placeholder='인증코드' onChange={validCodeHandler} />
                        <span style={
                            validate.validCode
                            ? {color: 'green'}
                            : {color: 'red'}
                        }>{message.validCode}</span>
                    </Form.Group>
                    
                    <Form.Group className='mb-3'>
                        <Form.Label id='find_pw_form_label'>새 비밀번호</Form.Label>
                        <Form.Control type='password' className='find_pw_form_control' placeholder='비밀번호'  onChange={userPasswordHandler} />
                        <span style={
                            validate.userPassword
                            ? {color: 'green'}
                            : {color: 'red'}
                        }>{message.userPassword}</span>
                    </Form.Group>

                    <Form.Group className='mb-3'>
                        <Form.Label id='find_pw_form_label'>새 비밀번호 재확인</Form.Label>
                        <Form.Control type='password' id='userPasswordRecheck' className='find_pw_form_control' placeholder='비밀번호 확인' onChange={userPasswordCheckHandler} />
                        <span id='check-pass' style={
                            validate.userPasswordCheck
                            ? {color: 'green'}
                            : {color: 'red'}
                        }>{message.userPasswordCheck}</span>
                    </Form.Group>
                </Form>

                <div id = 'find_pw_footer_div'>
                    <Button onClick={onLoginPage} className="btn_gray find_pw_btn btn_size_100">
                        취소
                    </Button> 
                    <Button className="btn_orange find_pw_btn btn_size_100" id="user_find_change_btn" onClick={anonymousUserUpdate}>
                        변경
                    </Button> 
                </div>
                
            </div>
        </div>
    )
}

export default UserFindInfo