import React from 'react'

import Table from "react-bootstrap/Table";
import Button from "react-bootstrap/Button";

import './css/ManagementMain.css';

const ManagementMain = () => {

    // 직원 정보 변경 모달
    const handleChangeModal = (userId) => {

    }

    // 직원 퇴사 모달
    const handleDeleteModal = (userId) => {

    }

    return (
        <div id='management_main'>
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

export default ManagementMain
