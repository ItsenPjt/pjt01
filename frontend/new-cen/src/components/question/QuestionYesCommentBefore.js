import React from 'react';
import { useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import './css/QuestionContent.css'

// 댓글 허용 - 댓글 작성 전
const QuestionYesCommentBefore = () => {
    
    // 문의사항 목록 페이지로
    const navigate = useNavigate();
    const onNoticePage = () => {
        const path = `/question`;
        navigate(path);
    };
    
    return (
        <>
            <div>
                <Form>
                    <Form.Group className='mb-3'>
                        <div className='justify' id='question_content_comment_insert_div'>
                            <Form.Control id='question_content_comment_insert' type='text' placeholder='댓글 입력' />
                            <input type="file" name="question_content_comment_file" id="question_content_comment_file" onChange={(e) => console.log(e.target.files[0])} />
                        </div>
                        
                    </Form.Group>
                </Form>
            </div>

            <div id='question_content_footer_div'>
                <Button className='btn_gray btn_size_100' onClick={onNoticePage}>목록</Button>
                <Button className='btn_orange btn_size_100' id='question_content_comment_insert_btn'>등록</Button>
            </div>
        </>
    )
}

export default QuestionYesCommentBefore;
