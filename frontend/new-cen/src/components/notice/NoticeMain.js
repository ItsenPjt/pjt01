import React from 'react';
import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';

import './css//NoticeMain.css';

const NoticeMain = () => {

    const onInsertPage = () => {
        window.location.href = "/notice/insert";
    }

    return (
        <>
            <Button onClick={onInsertPage} className='btn_orange' id='notice_insert_btn'>추가</Button>

            <div id='notice_main'>
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
                    
                    </tbody>
                </Table >   
            </div>
        </>
    )
}

export default NoticeMain;