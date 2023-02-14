import React, { useEffect, useState } from 'react';

import { BASE_URL, ADMIN } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

import Table from "react-bootstrap/Table";
import Button from "react-bootstrap/Button";
import Modal from 'react-bootstrap/Modal';

import './css/AdminMain.css';

const AdminMain = () => {

    const API_BASE_URL = BASE_URL + ADMIN;
    const ACCESS_TOKEN = getToken();

    // 직원 목록 api 데이터 
    const [users, setUsers] = useState([]);
    const [modal, setModal] = useState(false); 
    const [deleteUser, setDeleteUser] = useState('');       // 퇴사 대상 직원 id

    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    // 렌더링 되자마자 할 일 => 공지사항 api GET 목록 호출
    useEffect(() => {
        fetch(API_BASE_URL, {
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
                setUsers(result);
            });
    }, [API_BASE_URL]);

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 직원 퇴사 모달
    const handleDeleteModal = (userId) => {
        setDeleteUser(userId);
        setModal(true);     // 모달 열기
    }

    // 직원 퇴사 서버 요청 (DELETE)
    const handleDeleteUser = () => {
        fetch(`${API_BASE_URL}/${deleteUser}`, {
            method: 'DELETE',
            headers: headerInfo,
        })
        .then(res => res.json())
        .then(() => {
            window.location.href = "/admin";
        });
    }

    var i = 0;
    return (
        <>
            <div id='admin_main'>
                <Table responsive>
                    <thead>
                        <tr>
                            <th width="2%"></th>
                            <th width="5%">권한</th>
                            <th width="6%">이름</th>
                            <th width="6%">아이디</th>
                            <th width="6%">가입일</th>
                            <th width="8%">퇴사 처리</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            users.map((item) => {
                                i++;
                                return (
                                    <tr key={item.userId}>
                                        <td>{i}</td>
                                        <td>{item.userRole === 'ADMIN' ? 'O' : 'X'}</td>
                                        <td>{item.userName}</td>
                                        <td>{item.userEmail}</td>
                                        <td>{item.userRegdate}</td>
                                        <td><Button className="btn_orange" onClick = {() => handleDeleteModal(item.userId)}>퇴사</Button></td>
                                    </tr>
                                )
                            })     
                        }
                    </tbody>
                </Table>
            </div>

            {/* Modal */}
            <Modal show={modal} onHide={handleClose} id="admin_delete_modal">
                <Modal.Body id='admin_delete_modal_body'>
                    <div id='admin_delete_modal_content'>
                        해당 직원을 퇴사처리 하시겠습니까?
                    </div>

                    <div id="admin_delete_modal_content">
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

export default AdminMain
