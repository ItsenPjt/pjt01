import React from 'react';
import Table from 'react-bootstrap/Table';

import '../css/notice/NoticeMain.css';

const NoticeMain = () => {

    return (
        <div className='notice_main'>
            <Table responsive className='notice_table'>
                <thead>

                </thead>
                <thead>
                    <tr className='notice_main_thead'>
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