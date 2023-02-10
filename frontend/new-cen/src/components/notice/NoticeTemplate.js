import React from 'react';
import NoticeMain from './NoticeMain';

import Button from 'react-bootstrap/Button';

import '../css/notice/NoticeTemplate.css';

const NoticeTemplate = () => {

    const onInsertPage = () => {
        window.location.href = "/notice/insert";
    }

    return (
        <div>
            <Button className='notice_btn_orange' onClick={onInsertPage}>추가</Button>
            <NoticeMain />
        </div>
    )
}

export default NoticeTemplate;
