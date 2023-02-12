import React, { useEffect, useState } from 'react';

import { useNavigate } from "react-router-dom";

import Table from 'react-bootstrap/Table';
import QuestionButton from './QuestionButton';

import { BASE_URL, QUESTION } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

import './css/QuestionMain.css';

// 문의사항 메인
const QuestionMain = () => {

    const API_BASE_URL = BASE_URL + QUESTION;
    const ACCESS_TOKEN = getToken();

    // 공지사항 api 데이터 
    const [questions, setQuestions] = useState([]);

    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    // 렌더링 되자마자 할 일 => 문의사항 api GET 목록 호출
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
                setQuestions(result.data);
            });
    }, [API_BASE_URL]);

    // 제목 클릭 시 url 변경
    const navigate = useNavigate();
    const onTitleClick = (id) => {
        const path = `/question/${id}`;
        navigate(path);
    };

    return (
        <>
            <div id='question_btn_main'>
                <QuestionButton />
                <div id='question_table_main'>
                    <Table responsive id='question_table'>
                        <thead>
                            <tr id='question_main_thead'>
                                <th width="10%">이름</th>
                                <th width="20%">제목</th>
                                <th width="15%">날짜</th>
                                <th width="15%">좋아요</th>
                            </tr>
                        </thead>
                        <tbody>
                            {/* db 연결하여 map 함수 이용 */}
                            {
                                questions.map((item) => {
                                    return (
                                        <tr key={item.boardId} id='question_main_tbody'>
                                            <td>{item.boardId}</td>
                                            <th id='question_main_tbody_th' onClick={() => onTitleClick(item.boardId)}>{item.boardTitle}</th>
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

export default QuestionMain;