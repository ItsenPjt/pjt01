import React from 'react';

import Button from 'react-bootstrap/esm/Button';

import './css/MessageButton.css';

// 메세지 버튼
const MessageButton = () => {
    return (
        <div className='message_btn_group'>
            <Button className='btn_etc'>보낸 메세지</Button>
            <Button className='btn_etc'>받은 메세지</Button>
            <Button className='btn_etc'>메세지 보내기</Button>
            <Button className='btn_orange'>선택 삭제</Button>
        </div>
    )
}

export default MessageButton