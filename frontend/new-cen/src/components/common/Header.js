import React, { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom';

import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';

import { getToken, getUsername, getUserRole } from '../common/util/login-util';

import 'bootstrap/dist/css/bootstrap.min.css';

import './css/Header.css';

const Header = () => {

    const ACCESS_TOKEN = getToken();
    const USER_NAME = getUsername();
    const USER_ROLE = getUserRole();

    const [userRole, setUserRole] = useState('MEMBER');     // default : MEMBER

    // userRole 가 ADMIN 인 경우에만 관리자 버튼 확인 가능
    useEffect(() => {
        // DB에서 읽어온 후 set 해주기
        if (USER_ROLE === 'ADMIN') {
            setUserRole('ADMIN');
        }
    })

    // 로그인 페이지로
    const navigate = useNavigate();
    const onLoginPage = () => {
        const path = `/join`;
        navigate(path);
    };
    const signup = () => {
        const path = `/signup`;
        navigate(path);
    }

    // 로그아웃
    const logoutHandler = () => {
        localStorage.removeItem('ACCESS_TOKEN');
        localStorage.removeItem('LOGIN_USERNAME');
        localStorage.removeItem('LOGIN_USEREMAIL')
        localStorage.removeItem('LOGIN_USERROLE')

        window.location.href='/';
    }

    var textSize1, fontWeight1, textSize2, fontWeight2,  textSize3, fontWeight3, textSize4, fontWeight4, textSize5, fontWeight5;
    if (window.location.pathname === '/notice') {
        textSize1 = '18px';
        fontWeight1 = 700;
    } else if (window.location.pathname === '/question') {
        textSize2 = '18px';
        fontWeight2 = 700;
    } else if (window.location.pathname === '/faq') {
        textSize3 = '18px';
        fontWeight3 = 700;
    } else if (window.location.pathname === '/admin') {
        textSize4 = '18px';
        fontWeight4 = 700;
    } else if (window.location.pathname === '/signset' || window.location.pathname === '/message') {
        textSize5 = '18px';
        fontWeight5 = 700;
    }

    return (
        <Navbar className="header_nav_bar">
            <Container>
                <Navbar.Brand href="/">
                    <img className='cen_logo' alt='logo' src="/img/logo.png"/>
                    <span className='header_title'>
                        아이티센그룹
                        <br/>
                        {/* <span className='header_title_eng'>
                            ITCENGROUP
                        </span> */}
                    </span>
                </Navbar.Brand>

                <Navbar.Toggle aria-controls="basic-navbar-nav" />

                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link style={{fontSize: textSize1, fontWeight: fontWeight1}} onClick={() => {document.location.href = '/notice'}} className='header_nav_link'>공지사항</Nav.Link>
                        <Nav.Link style={{fontSize: textSize2, fontWeight: fontWeight2}} onClick={() => {document.location.href = '/question'}} className='header_nav_link'>문의사항</Nav.Link>
                        <Nav.Link style={{fontSize: textSize3, fontWeight: fontWeight3}} onClick={() => {document.location.href = '/faq'}} className='header_nav_link'>자주 묻는 질문</Nav.Link>
                        
                        {/* // userRole 가 ADMIN 인 경우에만 관리자 버튼 확인 가능 */}
                        {(userRole === 'ADMIN') &&
                            <Nav.Link style={{fontSize: textSize4, fontWeight: fontWeight4}} onClick={() => {document.location.href = '/admin'}} className='header_nav_link'>관리자</Nav.Link>
                        }

                        {ACCESS_TOKEN === null || ACCESS_TOKEN === '' 
                            ? (
                                <>
                                <span onClick={onLoginPage} id='header_login'>
                                    로그인
                                </span>
                                <span id='header_dot'>　·　</span>
                                <span onClick={signup} id='header_signup'>
                                회원가입
                                </span>
                                </>
                              )
                            : (
                                <>
                                    <div id='header_user'>{USER_NAME} 님 환영합니다.</div>
                                    <NavDropdown style={{fontSize: textSize5, fontWeight: fontWeight5}} title="내정보" id="basic-nav-dropdown">
                                        <NavDropdown.Item onClick={() => {document.location.href = '/signset'}} className='header_nav_dropdown_link'>마이페이지</NavDropdown.Item>
                                        <NavDropdown.Item onClick={() => {document.location.href = '/message'}} className='header_nav_dropdown_link'>메세지함</NavDropdown.Item>
                                    </NavDropdown>
                                    <div onClick={logoutHandler} id='header_logout'>
                                        로그아웃
                                    </div>
                                </>
                              )  
                        }
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}

export default Header