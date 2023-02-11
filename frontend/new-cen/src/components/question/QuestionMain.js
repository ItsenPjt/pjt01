import React from 'react';
import Table from 'react-bootstrap/Table';
import QuestionButton from './QuestionButton';

import './css/QuestionMain.css';

// 문의사항 메인
const QuestionMain = () => {


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
                            {/* map 함수 이용 */}
                        </tbody>
                    </Table >   
                </div>
            </div>
        </>
    )
}

export default QuestionMain;