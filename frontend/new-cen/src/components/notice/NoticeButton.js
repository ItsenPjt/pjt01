import React from 'react'
import { useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';

import './css/NoticeButton.css';
const NoticeButton = () => {

    const navigate = useNavigate();
    const onInsertPage = () => {
        const path = `/notice/insert`;
        navigate(path);
    };

    return (
        <div className='justify'>
            <div id='notice_button_txt'>공지사항</div>
            <div id='notice_button_group'>
                <Button onClick={onInsertPage} className='btn_orange btn_size_100' id='notice_button_insert'>글쓰기</Button>
            </div>
        </div>      
    )
}

export default NoticeButton
