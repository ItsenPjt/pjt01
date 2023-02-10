import React from 'react';
import Table from 'react-bootstrap/Table';

import MessageButton from './MessageButton';

import './css/MessageMain.css';

// 메세지 메인
const MessageMain = () => {
    return (
        <div id='message_main'>
            <MessageButton />
            <Table responsive id='notice_table'>
                <thead>
                    <tr id='notice_main_thead'>
                        <th width="15%">보낸 사람</th>
                        <th width="20%">제목</th>
                        <th width="15%">날짜</th>
                        <th width="10%">전체 선택</th>
                    </tr>
                </thead>
                <tbody>
                   
                </tbody>
            </Table > 
        </div>
    )
}

export default MessageMain