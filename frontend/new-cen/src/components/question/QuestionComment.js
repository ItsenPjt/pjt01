import React, { useState, useEffect } from 'react';

import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

import { BASE_URL, QUESTION } from '../common/config/host-config';
import { getToken, getUsername, getUserEmail } from '../common/util/login-util';

import './css/QuestionContent.css'

// 문의사항 댓글
const QuestionComment = ( { questionId } ) => { // QuestionContent.js 에서 받아온 questionId
    
    const API_BASE_URL = BASE_URL + QUESTION;
    const ACCESS_TOKEN = getToken();
    const USER_NAME = getUsername();
    const USER_EMAIL = getUserEmail();

    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    // 문의사항 댓글
    const [questionComments, setQuestionComments] = useState([]);
    const [modal, setModal] = useState(false); 
    const [deleteQuestionCommentId, setDeleteQuestionCommentId] = useState('');     // 삭제 할 공지사항 댓글 id 
    
    // 입력할 댓글
    const [questionInsertComment, setQuestionInsertComment] = useState({
        commentContent: ''
    });

    // 입력할 파일
    const [questionInsertCommentFile, setQuestionInsertCommentFile] = useState({
        commentFilePath: ''     
    })

    const commentChangeHandler = e => {
        setQuestionInsertComment({
            ...questionInsertComment,        // 기존 questionInsertComment 복사 후 commentContent 추가
            commentContent: e.target.value,
        });
    };

    const commentFileChangeHandler = e => {
        setQuestionInsertCommentFile({
            ...questionInsertCommentFile,
            commentFilePath: e.target.files[0].name
        })
    }

    // 댓글 조회 서버 요청 (GET에 대한 응답처리)
    useEffect(() => {
        fetch(`${API_BASE_URL}/${questionId}/comments`, {
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
            if (!!result) {
                console.log(result.content);
                setQuestionComments(result.content);
            }
        });
    }, [API_BASE_URL]);

    // 댓글 등록
    const handleInserQuestionComment = () => {
        // 댓글과 댓글 파일 모두 입력하지 않았을 때
        if (questionInsertComment.commentContent === '' && questionInsertCommentFile.commentFilePath === '') {
            alert('댓글 입력 혹은 파일을 선택해주세요');
        }
        // 댓글만 입력되어있을 때 -> 댓글 등록 서버 요청 (POST에 대한 응답처리)
        else if (questionInsertComment.commentContent !== '' && questionInsertCommentFile.commentFilePath === '') {
            if (ACCESS_TOKEN === '' || ACCESS_TOKEN === null) {
                alert('로그인이 필요한 서비스입니다.');
                window.location.href = '/join';
            }
            else {
                fetch(`${API_BASE_URL}/${questionId}/comments`, {
                    method: 'POST',
                    headers: headerInfo,
                    body: JSON.stringify(questionInsertComment)
                })
                .then(res => {
                    if (res.status === 406) {
                        alert('오류가 발생했습니다. 잠시 후 다시 이용해주세요');
                        return;
                    } 
                    else if (res.status === 500) {
                        alert('서버가 불안정합니다');
                        return;
                    }
                    return res.json();
                })
                .then(() => {
                    window.location.href = `/question/${questionId}`;       // 해당 공지사항 페이지 새로고침
                });
            }
        } 
        // 파일이 존재할 때 -> 댓글 등록 서버 요청 후, 댓글 파일 등록 서버 요청 (POST에 대한 응답처리)
        else {
            if (ACCESS_TOKEN === '' || ACCESS_TOKEN === null) {
                alert('로그인이 필요한 서비스입니다.');
                window.location.href = '/join';
            }
            else {
                // 댓글 등록
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
                .then((res) => {
                    
                    // 파일 등록
                    const newCommentId = res.data[0]["commentId"];

                    if (USER_EMAIL === res.data[0]["userEmail"]) {
                        fetch(`${API_BASE_URL}/${questionId}/comments/${newCommentId}/files`, {
                            method: 'POST',
                            headers: headerInfo,
                            body: JSON.stringify(questionInsertCommentFile)
                        })
                        .then(res => {
                            if (res.status === 406) {
                                alert('오류가 발생했습니다. 잠시 후 다시 이용해주세요');
                                return;
                            } 
                            else if (res.status === 500) {
                                alert('서버가 불안정합니다');
                                return;
                            }
                            return res.json();
                        })
                        .then(() => {
                            window.location.href = `/question/${questionId}`;       // 해당 공지사항 페이지 새로고침
                        });
                    }
                });
            }
        }
    }
 
    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 댓글 삭제 클릭 시 경고 모달
    const handleShowDeleteModal = (commentId) => {
        setDeleteQuestionCommentId(commentId);
        setModal(true);     // 모달 열기
    }

    // 댓글 삭제 서버 요청 (DELETE에 대한 응답처리)
    const handleDeleteQuestionComment = () => {
        fetch(`${API_BASE_URL}/${questionId}/comments/${deleteQuestionCommentId}`, {
            method: 'DELETE',
            headers: headerInfo,
        })
        .then(res => {
            if (res.status === 404) {
                alert('다시 시도해주세요');
                return;
            }
            else if (res.status === 406) {
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
            else {
                window.location.href = `/question/${questionId}`;       // 해당 문의사항 페이지 새로고침
            }
        })
    }

    return (
        <>
            <div>
                <div id='question_content_comment_footer'>
                    <div id='question_content_comment_txt'>댓글 - {USER_NAME}</div>
                    <textarea onChange={commentChangeHandler} value={questionInsertComment.commentContent} rows="3" id='question_content_comment_insert' placeholder='댓글 입력'/>
                    <div className='justify'>
                        <input onChange={commentFileChangeHandler} type="file" name="question_content_comment_file" id="question_content_comment_file" />
                        <Button onClick={handleInserQuestionComment}  className='btn_orange'>등록</Button>
                    </div>
                </div>
                
                 <div id='question_content_comment_size'>
                    {
                        questionComments.map((item) => {
                            return (
                                <div key={item.commentId} style={{ height: '40px'}}>
                                    <div>
                                        <span id='question_content_comment_writer'>{item.commentWriter}</span> 
                                        <span id='question_content_comment_detail'>| {item.commentContent}</span>
                                        {item.commentFileList.length !== 0  && <span id='question_content_comment_detail_file'> {item.commentFileList[0].commentFilePath}</span>}
                                        <span id='question_content_comment_detail_date'>- {item.commentCreateDate}</span>
                                    </div>

                                    {/* 내가 등록한 댓글인지 아닌지 판단 필요 */}
                                    { USER_EMAIL === item.userEmail && <Button onClick={() => handleShowDeleteModal(item.commentId)} className='btn_gray' id='question_content_comment_delete'>X</Button> }
                                </div>   
                            )
                        })
                    }     
                </div>
            </div>

            {/* Modal */}
            <Modal show={modal} onHide={handleClose} id="notice_insert_modal">
                <Modal.Body id='notice_insert_modal_body'>
                    <div id='notice_insert_modal_content'>
                        댓글을 삭제하시겠습니까?
                    </div>

                    <div id="notice_insert_modal_content">
                        <Button onClick={handleClose} className='btn_gray notice_btn btn_size_100'>
                            아니오
                        </Button>
                        <Button onClick={handleDeleteQuestionComment} className='btn_orange notice_btn btn_size_100' id="notice_insert_btn">
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default QuestionComment;
