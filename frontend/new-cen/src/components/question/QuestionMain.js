import React from 'react';

import { useNavigate } from "react-router-dom";

import Table from 'react-bootstrap/Table';
import QuestionButton from './QuestionButton';

import './css/QuestionMain.css';

// 문의사항 메인
const QuestionMain = () => {

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

                            {/* 아래는 임시 데이터 -> questionId = 1이라 가정 */}
                            <tr id='question_main_tbody'>
                                <td>김진행</td>
                                <th id='question_main_tbody_th' onClick={() => onTitleClick(1)}>회사 재직 시 복장관련 질문</th>
                                <td>2023-01-20</td>
                                <td>132</td>
                            </tr>
                        </tbody>
                    </Table >   
                </div>
            </div>
        </>
    )
}

export default QuestionMain;