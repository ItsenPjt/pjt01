import React from 'react';
import { useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';

import { getToken } from '../common/util/login-util';

import './css/QuestionButton.css';

// 문의사항 버튼들
const QuestionButton = () => {
    const ACCESS_TOKEN = getToken();        // 토큰값

    const navigate = useNavigate();
    const onInsertPage = () => {
        const path = `/question/insert`;
        navigate(path);
    };

    return (
        <div className='justify'>
            <div id='question_button_txt'>문의사항</div>
            <div id='question_button_group'>
                {/* <Button className='btn_indigo' id='question_button_date_asc'>날짜 순</Button>
                <Button className='btn_indigo' id='question_button_like_asc'>좋아요 순</Button>
                <Button className='btn_indigo' id='question_button_commente_asc'>댓글 순</Button> */}
                
                
                { ACCESS_TOKEN !== null &&                 
                    <Button onClick={onInsertPage} className='btn_orange btn_size_100' id='question_button_insert'>글쓰기</Button>
                }
            </div>
        </div>      
    )
}

export default QuestionButton;
