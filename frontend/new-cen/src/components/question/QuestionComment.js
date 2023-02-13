import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import { BASE_URL, QUESTION } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

import './css/QuestionContent.css'

// 댓글
const QuestionComment = ( { questionId } ) => { // QuestionContent.js 에서 받아온 questionId
    
    const API_BASE_URL = BASE_URL + QUESTION;
    const ACCESS_TOKEN = getToken();

    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    // 문의사항 api 데이터 
    const [questionComments, setQuestionComments] = useState([]);
    
    // 입력할 댓글
    const [questionInsertComment, setQuestionInsertComment] = useState({
        commentContent: ''
    });

    const commentChangeHandler = e => {
        setQuestionInsertComment({
            ...questionInsertComment,        // 기존 questionInsertComment 복사 후 commentContent 추가
            commentContent: e.target.value,
        });
    };

    // useEffect(() => {
    //     fetch(`${API_BASE_URL}/${questionId}/comments`, {
    //         method: 'GET',
    //         headers: headerInfo
    //     })
    //     .then(res => {
    //         if (res.status === 406) {
    //             if (ACCESS_TOKEN === '') {
    //                 alert('로그인이 필요한 서비스입니다');
    //                 window.location.href = '/join';
    //             } else {
    //                 alert('오류가 발생했습니다. 잠시 후 다시 이용해주세요');
    //                 return;
    //             }
    //             return;
    //         } 
    //         else if (res.status === 500) {
    //             alert('서버가 불안정합니다');
    //             return;
    //         }
    //         return res.json();
    //     })
    //     .then(result => {
    //         console.log(result);
    //     });
    // }, [API_BASE_URL]);

    // 댓글 등록 서버 요청 (POST에 대한 응답처리)
    const handleInsertNoticeComment = () => {
        if (ACCESS_TOKEN === '' || ACCESS_TOKEN === null) {
            alert('로그인이 필요한 서비스입니다.');
            window.location.href = '/join';
        }
        else {
            if (questionInsertComment.commentContent === '') {
                alert('댓글을 입력해주세요.');
            } else {
                fetch(`${API_BASE_URL}/${questionId}/comments`, {
                    method: 'POST',
                    headers: headerInfo,
                    body: JSON.stringify(questionInsertComment)
                })
                .then(res => {
                    if (res.status === 406) {
                        if (ACCESS_TOKEN === '') {
                            alert('로그인이 필요한 서비스입니다');
                            window.location.href = '/join';
                        } else {
                            alert('오류가 발생했습니다. 잠시 후 다시 이용해주세요');
                            return;
                        }
                        return;
                    } 
                    else if (res.status === 500) {
                        alert('서버가 불안정합니다');
                        return;
                    }
                    return res.json();
                })
                .then(() => {
                    window.location.href = `/question/${questionId}`;       // 해당 문의사항 페이지 새로고침
                });
            }
        }
    }

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
                            <Form.Control onChange={commentChangeHandler} value={questionInsertComment.commentContent} id='question_content_comment_insert' type='text' placeholder='댓글 입력' />
                            <input type="file" name="question_content_comment_file" id="question_content_comment_file" onChange={(e) => console.log(e.target.files[0])} />
                        </div>
                        
                    </Form.Group>
                </Form>
            </div>

            <div id='question_content_footer_div'>
                <Button onClick={onNoticePage} className='btn_gray btn_size_100'>목록</Button>
                <Button onClick={handleInsertNoticeComment} className='btn_orange btn_size_100' id='question_content_comment_insert_btn'>등록</Button>
            </div>
        </>
    )
}

export default QuestionComment;
