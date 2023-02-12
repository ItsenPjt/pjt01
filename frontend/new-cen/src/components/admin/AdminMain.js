import React, { useEffect, useState } from 'react';

import { BASE_URL, ADMIN } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

import Table from "react-bootstrap/Table";
import Button from "react-bootstrap/Button";

import './css/AdminMain.css';

const AdminMain = () => {

    const API_BASE_URL = BASE_URL + ADMIN;
    const ACCESS_TOKEN = getToken();

    // 직원 목록 api 데이터 
    const [users, setUsers] = useState([]);

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
                // if (res.status === 403) {
                //     alert('로그인이 필요한 서비스입니다');

                //     window.location.href = '/';
                //     return;
                // } 
                // else if (res.status === 500) {
                //     alert('서버가 불안정합니다');
                //     return;
                // }
                return res.json();
            })
            .then(result => {
                console.log(result);
                setUsers(result.notices);
            });
    }, [API_BASE_URL]);


    // 직원 정보 변경 모달
    const handleChangeModal = (userId) => {

    }

    // 직원 퇴사 모달
    const handleDeleteModal = (userId) => {

    }

    return (
        <div id='admin_main'>
            <Table responsive>
                <thead>
                    <tr>
                        <th width="2%"></th>
                        <th width="10%">권한</th>       {/* 해당 직원이 관리자(O)인지 사용자(X)인지 */}
                        <th width="6%">이름</th>
                        <th width="6%">아이디</th>
                        <th width="6%">인증코드</th>
                        <th width="10%">전화번호</th>
                        <th width="8%">정보 변경</th>
                        <th width="8%">퇴사 처리</th>
                    </tr>
                </thead>
                <tbody>
                    {/* map 함수 이용 */}


                    {/* 아래는 임시 데이터 */}
                    <tr>
                        <td>1</td>
                        <td>X</td>
                        <td>암호맨</td>
                        <td>postman@naver.com</td>
                        <td>XY2baJQ</td>
                        <td>010-0000-0000</td>
                        <td><Button className="btn_gray" onClick = {() => handleChangeModal('암호맨userId')}>변경</Button></td>
                        <td><Button className="btn_orange" onClick = {() => handleDeleteModal('암호맨userId')}>퇴사</Button></td>
                    </tr>
                </tbody>
            </Table>
        </div>
    )
}

export default AdminMain
