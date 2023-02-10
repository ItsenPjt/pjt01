import React from 'react';
import Table from 'react-bootstrap/Table';

import './css//NoticeMain.css';

const NoticeMain = () => {

    return (
        <div id='notice_main'>
            <Table responsive id='notice_table'>
                <thead>

                </thead>
                <thead>
                    <tr id='notice_main_thead'>
                        <th width="10%">번호</th>
                        <th width="20%">제목</th>
                        <th width="15%">날짜</th>
                        <th width="15%">작성자</th>
                    </tr>
                </thead>
                <tbody>
                   
                </tbody>
            </Table >   
        </div>
    )
}

export default NoticeMain;