import React, { useEffect, useState } from 'react';

import { BASE_URL, ADMIN } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

import Table from "react-bootstrap/Table";
import Button from "react-bootstrap/Button";
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import './css/AdminList.css';

// 인증코드 관리
const AdminValidList = () => {
    const API_BASE_URL = BASE_URL + ADMIN;
    const ACCESS_TOKEN = getToken();

    // 직원 목록 api 데이터 
    const [users, setUsers] = useState([]);
    const [insertModal, setInsertModal] = useState(false); 
    const [insertUserEmail, setInsertUserEmail] = useState({
        validUserEmail: ''
    });
    const [deleteModal, setDeleteModal] = useState(false); 
    const [deleteUserId, setDeleteUserId] = useState('');       // 퇴사 대상 직원 id

    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    // 렌더링 되자마자 할 일 => 공지사항 api GET 목록 호출
    useEffect(() => {
        fetch(`${API_BASE_URL}/validuser`, {
            method: 'GET',
            headers: headerInfo
        })
            .then(res => {
                if (res.status === 403) {
                    alert('로그인이 필요한 서비스입니다');

                    window.location.href = '/join';
                    return;
                }
                else if (res.status === 500) {
                    alert('서버가 불안정합니다');
                    return;
                }
                return res.json();
            })
            .then(result => {
                if(!!result) {
                    setUsers(result);
                }
            });
    }, [API_BASE_URL]);

    // 모달 닫기
    const handleClose = () => {
        setDeleteModal(false);
        setInsertModal(false);
    };

    // 직원 추가 모달
    const handleInsertModal = () => {
        setInsertModal(true);       // 모달 열기
    }

    // 직원 퇴사 모달
    const handleDeleteModal = (userId) => {
        setDeleteUserId(userId);
        setDeleteModal(true);     // 모달 열기
    }

    const insertChangeHandler = e => {
        setInsertUserEmail({
            ...insertUserEmail,
            validUserEmail: e.target.value
        });
    };

    // 직원 추가 서버 요청 (POST)
    const handleInsertUser = () => {
        if (insertUserEmail.validUserEmail === '') {
            alert('이메일을 입력해주세요');
        } else {
            fetch(`${API_BASE_URL}/validuser`, {
                method: 'POST',
                headers: headerInfo,
                body: JSON.stringify(insertUserEmail)
            })
            .then(res => {
                if (res.status === 400) {
                    alert('잘못된 이메일 형식이거나 기존에 존재하는 이메일입니다.');
                    window.location.href = '/validlist';

                    return;
                } else if (res.status === 406) {
                    alert('로그인이 필요한 서비스입니다');
                    window.location.href = '/join';
    
                    return;
                } else if (res.status === 500) {
                    alert('서버가 불안정합니다');
                    return;
                }
                return res.json();
            })
            .then((res) => {
                window.location.href = `/validlist`;       // 인증코드 관리 페이지 새로고침
            });
        }
    }

    // 직원 퇴사 서버 요청 (DELETE)
    const handleDeleteUser = () => {
        fetch(`${API_BASE_URL}/validuser/${deleteUserId}`, {
            method: 'DELETE',
            headers: headerInfo,
        })
        .then(res => {
            if(res.status === 500) {
                alert("서버가 불안정합니다");
                return;
            }else if(res.status === 400) {
                alert("유효하지 않은 파라미터 값 입니다");
                return;
            }
            else if(res.status === 403) {
                alert("토큰 혹은 권한이 없습니다");
                return;
            }else if(res.status === 404) {
                alert("존재하지 않는 회원입니다");
                return;
            }
            return res.json();
        })
        .then(res => {
            if(!!res) {
                setUsers(res);
            }
            setDeleteModal(false);
        });
    }

    var i = 0;
    return (
        <>
            <div id='admin_main'>
                <div id='admin_txt'>인증코드 관리</div>
                <Table responsive>
                    <thead>
                        <tr>
                            <th width="2%"><Button onClick={handleInsertModal} className="btn_indigo">직원 추가</Button></th>
                            <th width="6%">아이디</th>
                            <th width="8%">가입여부</th>
                            <th width="8%">삭제</th>
                        </tr>
                    </thead>
                    <tbody>
                        {   users&&
                            users.map((item) => {
                                i++;
                                return (
                                    <tr key={item.validUserEmail}>
                                        <td>{i}</td>
                                        <td>{item.validUserEmail}</td>
                                        {
                                            item.validActive==1 ?
                                            <><td>&nbsp;미가입</td><td><Button onClick={() => handleDeleteModal(item.validUserId)} className="btn_orange">삭제</Button></td></>
                                            :
                                            <><td>가입완료</td><td></td></>
                                        }
                                    </tr>
                                )
                            })     
                        }
                    </tbody>
                </Table>
            </div>

            {/* Modal */}
            <Modal show={insertModal} onHide={handleClose} id="admin_insert_modal">
                <Modal.Body id='admin_modal_body'>
                    <div id='admin_modal_content'>
                        추가하실 직원의 이메일을 입력해주세요.
                    </div>
                    <div>
                        <Form.Group className='mb-3'>
                            <Form.Control onChange={insertChangeHandler} value={insertUserEmail.validUserEmail}  autoFocus type='text' className='admin_form_control' placeholder='이메일'/>
                        </Form.Group>
                    </div>
                    <div id="admin_modal_content">
                        <Button onClick={handleClose} className='btn_gray admin_btn btn_size_100'>
                            취소
                        </Button>
                        <Button onClick={handleInsertUser} className='btn_orange admin_btn btn_size_100' id="admin_content_delete_btn">
                            등록
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>

            <Modal show={deleteModal} onHide={handleClose} id="admin_delete_modal">
                <Modal.Body id='admin_modal_body'>
                    <div id='admin_modal_content'>
                        해당 직원을 삭제처리 하시겠습니까?
                    </div>

                    <div id="admin_modal_content">
                        <Button onClick={handleClose} className='btn_gray admin_btn btn_size_100'>
                            아니오
                        </Button>
                        <Button onClick={handleDeleteUser} className='btn_orange admin_btn btn_size_100' id="admin_content_delete_btn">
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default AdminValidList