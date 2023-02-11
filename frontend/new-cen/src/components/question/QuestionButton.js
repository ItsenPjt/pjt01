import React from 'react';
import Button from 'react-bootstrap/Button';

import './css/QuestionButton.css';

// 문의사항 버튼들
const QuestionButton = () => {

    const onInsertPage = () => {
        window.location.href = "/question/insert";
    }

    return (
        <div className='justify'>
            <div id='question_button_txt'>문의사항</div>
            <div id='question_button_group'>
                <Button className='btn_indigo' id='question_button_date_asc'>날짜 순</Button>
                <Button className='btn_indigo' id='question_button_like_asc'>좋아요 순</Button>
                <Button className='btn_indigo' id='question_button_commente_asc'>댓글 순</Button>
                <Button onClick={onInsertPage} className='btn_orange btn_size_100' id='question_button_insert'>글쓰기</Button>
            </div>
        </div>      
    )
}

export default QuestionButton;
