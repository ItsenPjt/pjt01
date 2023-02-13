import React from 'react'
import { useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';

import { getUserRole } from '../common/util/login-util';

import './css/NoticeButton.css';
const NoticeButton = () => {
    const USER_ROLE = getUserRole();        // 권한

    const navigate = useNavigate();
    const onInsertPage = () => {
        const path = `/notice/insert`;
        navigate(path);
    };

    return (
        <div className='justify'>
            <div id='notice_button_txt'>공지사항</div>

            {/* 권한이 ADMIN 인 경우에만 '글쓰기' 버튼 보이도록 */}
            {USER_ROLE === 'ADMIN' && 
                <div id='notice_button_group'>
                    <Button onClick={onInsertPage} className='btn_orange btn_size_100' id='notice_button_insert'>글쓰기</Button>
                </div>
            }
        </div>      
    )
}

export default NoticeButton
