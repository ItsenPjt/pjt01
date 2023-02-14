import React, { useState, useEffect } from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import { BASE_URL, NOTICE } from '../common/config/host-config';
import { getToken, getUsername } from '../common/util/login-util';

import './css/NoticeContent.css'

// 댓글
const NoticeComment = ( { noticeId }) => {      // NoticeContent.js 에서 받아온 noticeId
    
    const API_BASE_URL = BASE_URL + NOTICE;
    const ACCESS_TOKEN = getToken();
    const USER_NAME = getUsername();

    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    // 공지사항 댓글 
    const [noticeComments, setNoticeComments] = useState([]);

    // 입력할 댓글
    const [noticeInsertComment, setNoticeInsertComment] = useState({
        commentContent: ''
    });

    const commentChangeHandler = e => {
        setNoticeInsertComment({
            ...noticeInsertComment,        // 기존 noticeComment 복사 후 commentContent 추가
            commentContent: e.target.value,
        });
    };

    // 댓글 조회 서버 요청 (GET에 대한 응답처리)
    useEffect(() => {
        fetch(`${API_BASE_URL}/${noticeId}/comments`, {
            method: 'GET',
            headers: headerInfo
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
        .then(result => {
            console.log(result.data);
            setNoticeComments(result.data);
        });
    }, [API_BASE_URL]);


    // 댓글 등록 서버 요청 (POST에 대한 응답처리)
    const handleInsertNoticeComment = () => {
        if (ACCESS_TOKEN === '' || ACCESS_TOKEN === null) {
            alert('로그인이 필요한 서비스입니다.');
            window.location.href = '/join';
        }
        else {
            // 댓글이 null 이고, 파일 명이 null 일때 alert창 뜨도록
            if (noticeInsertComment.commentContent === '') {
                alert('댓글을 입력해주세요.');
            } else {
                fetch(`${API_BASE_URL}/${noticeId}/comments`, {
                    method: 'POST',
                    headers: headerInfo,
                    body: JSON.stringify(noticeInsertComment)
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
                    window.location.href = `/notice/${noticeId}`;       // 해당 공지사항 페이지 새로고침
                });
            }
        }
    }
    
    return (
        <>
            <div>
                <div id='notice_content_comment_footer'>
                    <div id='notice_content_comment_txt'>댓글 - {USER_NAME}</div> {/*className="form-control"*/}
                    <textarea onChange={commentChangeHandler} value={noticeInsertComment.commentContent} rows="3"  id='notice_content_comment_insert' placeholder='댓글 입력'/>
                    <div className='justify'>
                        <input type="file" name="notice_content_comment_file" id="notice_content_comment_file" onChange={(e) => console.log(e.target.files[0])} />
                        <Button onClick={handleInsertNoticeComment}  className='btn_orange'>등록</Button>
                    </div>
                </div>
                <div  id='notice_content_comment_size'>
                    {/* 댓글 리스트 존재 여부에 따라 아래 데이터 숨김 --> map함수 처리 예정 */}
                    {
                        noticeComments.map((item) => {
                            return (
                                <div key={item.commentId}>
                                    <div>
                                        <span id='notice_content_comment_writer'>{item.commentWriter}</span> 
                                        <span id='notice_content_comment_detail'>{item.commentContent}</span>
                                    </div>

                                    {}
                                    <Button className='btn_gray' id='notice_content_comment_update'>수정하기</Button>
                                    <Button className='btn_orange' id='notice_content_comment_delete'>삭제하기</Button>
                                </div>   
                            )
                        })
                    }     
                </div>
            </div>
        </>
    )
}

export default NoticeComment;
