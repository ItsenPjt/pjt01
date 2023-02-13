import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import './css/QuestionContent.css'

// 댓글 허용 - 댓글 작성 후
const QuestionYesCommentAfter = () => {
    var questionId = useParams().questionId;

    // 댓글 작성 전 화면으로
    const navigate = useNavigate();
    const onNoticeComment = () => {
        const path = `/question/${questionId}`;
        navigate(path);
    };

    // 문의사항 목록 페이지로
    const onNoticePage = () => {
        window.location.href = "/question";
    }

    return (
        <>
            {/* map 함수 이용하여 DB 에서 해당 공지사항에 대한 전체 댓글 가져오기 */}
            <div>
                <Form id='question_content_comment_writer'>
                    (작성자)
                </Form>
                <Form id='question_content_comment_detail'>
                    (내용)
                </Form>
                <Button className='btn_gray' id='question_content_comment_update' onClick={onNoticeComment}>수정하기</Button>
                <Button className='btn_orange' id='question_content_comment_delete'>삭제하기</Button>
            </div>
            

            <div id='question_content_footer_div'>
                <Button className='btn_gray btn_size_100' onClick={onNoticePage}>목록</Button>
            </div>
        </>
    )
}

export default QuestionYesCommentAfter;
