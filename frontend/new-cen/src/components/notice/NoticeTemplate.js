import React from 'react';
import NoticeMain from './NoticeMain';

import Button from 'react-bootstrap/Button';

import '../css/NoticeTemplate.css';

const NoticeTemplate = () => {
    return (
        <div>
            <Button className='notice_btn_orange'>추가</Button>
            <NoticeMain />
        </div>
    )
}

export default NoticeTemplate;
