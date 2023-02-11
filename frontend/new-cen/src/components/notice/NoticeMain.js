import React from 'react';

import { useNavigate } from "react-router-dom";

import Table from 'react-bootstrap/Table';

import NoticeButton from './NoticeButton';

import './css//NoticeMain.css';

// 공지사항 메인
const NoticeMain = () => {

    // 제목 클릭 시 url 변경
    const navigate = useNavigate();
    const onTitleClick = (id) => {
        const path = `/notice/${id}`;
        navigate(path);
    };

    return (
        <>
            <div id='notice_btn_main'>
                <NoticeButton />
                <div id='notice_table_main'>
                    <Table responsive id='notice_table'>
                        <thead>
                            <tr id='notice_main_thead'>
                                <th width="10%">번호</th>
                                <th width="20%">제목</th>
                                <th width="15%">날짜</th>
                                <th width="15%">작성자</th>
                            </tr>
                        </thead>
                        <tbody>
                            {/* db 연결하여 map 함수 이용 */}

                            {/* 아래는 임시 데이터 -> noticeId = 1이라 가정 */}
                            <tr id='notice_main_tbody'>
                                <td>1</td>
                                <th id='notice_main_tbody_th' onClick={() => onTitleClick(1)}>조직문화팀 입니다.</th>
                                <td>2023-01-20</td>
                                <td>관리자</td>
                            </tr>
                        </tbody>
                    </Table >   
                </div>
            </div>
        </>
    )
}

export default NoticeMain;