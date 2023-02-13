import React, { useEffect, useState } from 'react';

import { useNavigate } from "react-router-dom";

import Table from 'react-bootstrap/Table';

import NoticeButton from './NoticeButton';

import { BASE_URL, NOTICE } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

import './css//NoticeMain.css';

// 공지사항 메인
const NoticeMain = () => {

    const API_BASE_URL = BASE_URL + NOTICE;
    const ACCESS_TOKEN = getToken();

    // 공지사항 api 데이터 
    const [notices, setNotices] = useState([]);

    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    // 제목 클릭 시 url 변경
    const navigate = useNavigate();
    const onTitleClick = (id) => {
        const path = `/notice/${id}`;
        navigate(path);
    };

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
                setNotices(result.notices);
            });
    }, [API_BASE_URL]);

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
                            {
                                notices.map((item) => {
                                    return (
                                        <tr key={item.boardId} id='notice_main_tbody'>
                                            <td>{item.boardId}</td>
                                            <th id='notice_main_tbody_th' onClick={() => onTitleClick(item.boardId)}>{item.boardTitle}</th>
                                            <td>{item.createDate.substring(0, 10)}</td>
                                            <td>{item.boardWriter}</td>
                                        </tr>
                                    )
                                })     
                            }
                        </tbody>
                    </Table >   
                </div>
            </div>
        </>
    )
}

export default NoticeMain;