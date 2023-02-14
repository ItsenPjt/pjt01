import React, { useState } from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import './css/UserSignSet.css';

import { BASE_URL, USER } from "../common/config/host-config";

import { getToken } from '../common/util/login-util';
import { getUserEmail } from '../common/util/login-util';
import { getUsername } from '../common/util/login-util';
import { getUserId } from '../common/util/login-util';

// 내 정보 수정
const UserSignSet = () => {

    const [modal, setModal] = useState(false); 


    const API_BASE_URL = BASE_URL + USER;

    const ACCESS_TOKEN = getToken();

    const ACCESS_EMAIL = getUserEmail();

    const ACCESS_NAME = getUsername();

    const ACCESS_UUID = getUserId();

    const headerInfo = {
        'content-type': 'application/json'
        , 'Authorization': 'Bearer ' + ACCESS_TOKEN
    };

    
    // 비번 검증 메세지 저장
    const [message, setMessage] = useState({
        userPassword: '',
        userPasswordCheck: ''
    });

    // 비번 검증 완료 여부
    const [validate, setValiDate] = useState({
        userPassword: false,
        userPasswordCheck: false
    });

    // 비번 입력값 저장
    const [userValue, setUserValue] = useState({
        userPassword: ''
    });


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


    // 내 정보 수정 요청 처리
    const updateUserPw = e => {
        //console.log(`${ACCESS_UUID}`);

        if (isValid()) {
            fetch(`${API_BASE_URL}/signset/${ACCESS_UUID}`, {
                method: 'PATCH',
                headers: headerInfo,
                body: JSON.stringify(userValue)
            })
            .then(res => {
                //console.log(res.status);
                if (res.status === 200) {
                    alert(`회원정보가 정상적으로 변경되었습니다.`);
                    // 메인 페이지로 리다이렉트
                    window.location.href = '/';                 
                } else {
                    alert(`회원정보 수정에 실패했습니다.\n잠시 후 다시 시도해 주세요.`);
                    window.location.href = '/signset';
                }
                return res.json();
            });
        } else {
            alert(`알 수 없는 오류입니다.\n관리자에게 문의하세요`);
        }
       
    };


    // 취소 버튼 클릭 시(헤더없는 경우)
    const onLoginPage = () => {
        window.location.href = "/";
    };


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
                            <Form.Control type='text' className='mypage_form_control' value={ACCESS_EMAIL} disabled />
                        </Form.Group>

                        <Form.Group className='mb-3'>
                            <Form.Label id='mypage_form_label'>비밀번호</Form.Label>
                            <Form.Control type='password' className='mypage_form_control' placeholder='비밀번호'  onChange={userPasswordHandler}/>
                            <span style={
                            validate.userPassword
                            ? {color: 'green'}
                            : {color: 'red'}
                            }>{message.userPassword}</span>
                        </Form.Group>

                        <Form.Group className='mb-3'>
                            <Form.Label id='mypage_form_label'>비밀번호 재확인</Form.Label>
                            <Form.Control type='password' id='userPasswordRecheck' className='mypage_form_control' placeholder='비밀번호 확인' onChange={userPasswordCheckHandler}/>
                            <span id='check-pass' style={
                            validate.userPasswordCheck
                            ? {color: 'green'}
                            : {color: 'red'}
                            }>{message.userPasswordCheck}</span>
                        </Form.Group>

                        <Form.Group className='mb-3'>
                            <Form.Label id='mypage_form_label'>이름</Form.Label>
                            <Form.Control type='text' className='mypage_form_control' value={ACCESS_NAME} disabled />
                        </Form.Group>
                    </Form>

                    <div id = 'mypage_footer_div'>
                        <Button onClick={onLoginPage} className="btn_gray mypage_btn btn_size_100">
                            취소
                        </Button> 
                        <Button className="btn_orange mypage_btn btn_size_100" id="mypage_btn"  onClick={updateUserPw}>
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

export default UserSignSet;