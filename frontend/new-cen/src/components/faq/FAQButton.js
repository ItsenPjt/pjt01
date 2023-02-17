import React from 'react';
import { useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';

import { getUserRole } from '../common/util/login-util';

import './css/FAQButton.css';

// 자주 묻는 질문 버튼들
const FAQButton = () => {
    const USER_ROLE = getUserRole();

    const navigate = useNavigate();
    const onInsertPage = () => {
        const path = `/faq/insert`;
        navigate(path);
    };

    return (
        <div className='justify'>
            <div id='faq_button_txt'>자주 묻는 질문</div>
            {USER_ROLE === 'ADMIN' &&
                <div id='faq_button_group'>
                    <Button onClick={onInsertPage} className='btn_orange btn_size_100' id='faq_button_insert'>글쓰기</Button>
                </div>
            }
            
        </div>      
    )
}

export default FAQButton;
