import React from 'react';
import Table from 'react-bootstrap/Table';

import MessageButton from './MessageButton';

import './css/MessageMain.css';

// 메세지 메인
const MessageMain = () => {
    return (
        <div id='message_btn_main'>
            <MessageButton />
            <div id='message_table_main'>
                <Table responsive id='message_table'>
                    <thead>
                        <tr id='message_main_thead'>
                            <th width="10%">보낸 사람</th>
                            <th width="20%">제목</th>
                            <th width="15%">날짜</th>
                            <th width="15%">전체 선택</th>
                        </tr>
                    </thead>
                    <tbody>
                        {/* map 함수 이용 */}
                    </tbody>
                </Table >   
            </div>
        </div>
    )
}

export default MessageMain