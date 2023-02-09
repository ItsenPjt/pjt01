import React from 'react'

import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

import 'bootstrap/dist/css/bootstrap.min.css';

import './css/Header.css';

const Header = () => {
    return (
        <Navbar className="header_nav_bar">
            <Container>
                <Navbar.Brand href="/">
                    <img className='cen_logo' alt='logo' src="img/logo.png"/>
                    <span className='header_title'>아이티센그룹 </span>
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />

                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="/notice" className='header_nav_link'>공지사항</Nav.Link>
                        <Nav.Link href="/introduction" className='header_nav_link'>회사소개</Nav.Link>
                        <Nav.Link href="/question" className='header_nav_link'>문의사항</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}

export default Header