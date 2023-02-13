import React from 'react';
import { useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';

import './css/NoticeContent.css'

// 댓글 비허용
const NoticeNoComment = () => {

    // 공지사항 목록 페이지로
    const navigate = useNavigate();
    const onNoticePage = () => {
        const path = `/notice`;
        navigate(path);
    };

    return (
        <div id='notice_content_footer_div'>
            <Button className='btn_gray btn_size_100' onClick={onNoticePage}>목록</Button>
        </div>
    )
}

export default NoticeNoComment;
