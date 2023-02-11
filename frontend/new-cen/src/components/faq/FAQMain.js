import React from 'react';
import Table from 'react-bootstrap/Table';
import FAQButton from './FAQButton';

import './css/FAQMain.css';

// 자주 묻는 질문 메인
const FAQMain = () => {

    return (
        <>
            <div id='faq_btn_main'>
                <FAQButton />
                <div id='faq_table_main'>
                    <Table responsive id='faq_table'>
                        <thead>
                            <tr id='faq_main_thead'>
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

export default FAQMain;