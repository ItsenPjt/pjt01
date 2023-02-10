import React from 'react';
import Button from 'react-bootstrap/esm/Button';
import './css/MessageButton.css';

const MessageButton = () => {
  return (
    <div className='message-btn-group'>

      <Button className='message-confirm'>보낸 메세지</Button>
      <Button className='message-confirm'>받은 메세지</Button>
      <Button className='message-confirm'>메세지 보내기</Button>
      <Button className='message-cancel'>선택 삭제</Button>


    </div>
  )
}

export default MessageButton