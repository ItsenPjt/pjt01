import React, { useState, useEffect } from 'react';

import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

import { BASE_URL, NOTICE, AWS } from '../common/config/host-config';
import { getToken, getUsername, getUserEmail, getUserRole } from '../common/util/login-util';

import './css/NoticeContent.css'

// 공지사항 댓글
const NoticeComment = ( { noticeId }) => {      // NoticeContent.js 에서 받아온 noticeId
    
    const API_BASE_URL = BASE_URL + NOTICE;
    const API_AWS_URL = BASE_URL + AWS;

    const ACCESS_TOKEN = getToken();
    const USER_NAME = getUsername();
    const USER_EMAIL = getUserEmail();
    const USER_ROLE = getUserRole();

    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    // 공지사항 댓글 
    const [noticeComments, setNoticeComments] = useState([]);
    const [modal, setModal] = useState(false); 
    const [deleteNoticeCommentId, setDeleteNoticeCommentId] = useState('');     // 삭제 할 공지사항 댓글 id 

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
            if (!!result) {
                setNoticeComments(result.data);
            }
        });
    }, [API_BASE_URL]);

    // 파일
    var files = [];
    const FileChangeHandler = e => {
        //files = [...e.target.files]; 
        
        e.preventDefault();
        files = e.target.files[0];    
    }

    // 댓글 등록
    const handleInsertNoticeComment = () => {

        // 댓글과 댓글 파일 모두 입력하지 않았을 때
        if (noticeInsertComment.commentContent === '' && files.length == 0) {
            alert('댓글 입력 혹은 파일을 선택해주세요');
        }

        // 댓글만 입력되어있을 때 -> 댓글 등록 서버 요청 (POST에 대한 응답처리)
        else if (noticeInsertComment.commentContent !== '' && files.length === 0) {// 댓글만 입력되어있을 때 -> 댓글 등록 서버 요청 (POST에 대한 응답처리)
            if (ACCESS_TOKEN === '' || ACCESS_TOKEN === null) {
                alert('로그인이 필요한 서비스입니다.');
                window.location.href = '/join';
            }
            else {
                fetch(`${API_BASE_URL}/${noticeId}/comments`, {
                    method: 'POST',
                    headers: headerInfo,
                    body: JSON.stringify(noticeInsertComment)
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
                    window.location.href = `/notice/${noticeId}`;       // 해당 공지사항 페이지 새로고침
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
                .then((res) => {
                    
                    // foemData
                    var formData = new FormData(); 
                    formData.append("file", files);

                    // 파일 등록
                    const newCommentId = res.data[0]["commentId"];
                    if (USER_EMAIL === res.data[0]["userEmail"]) {
                        fetch(`${API_BASE_URL}/${noticeId}/comments/${newCommentId}/files`, {
                            method: 'POST',
                            headers: {
                                'Authorization': 'Bearer ' + ACCESS_TOKEN,
                            },
                            body: formData                
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
                            window.location.href = `/notice/${noticeId}`;       // 해당 공지사항 페이지 새로고침
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
        setDeleteNoticeCommentId(commentId);
        setModal(true);     // 모달 열기
    }

    // 댓글 삭제 서버 요청 (DELETE에 대한 응답처리)
    const handleDeleteNoticeComment = () => {
        fetch(`${API_BASE_URL}/${noticeId}/comments/${deleteNoticeCommentId}`, {
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
                window.location.href = `/notice/${noticeId}`;       // 해당 공지사항 페이지 새로고침
            }
        })
    }

    // 파일 클릭 시 다운로드
    const commentFileDown = (filePath, userEmail) => {

        // 관리자이거나, 댓글 등록한 사람만 다운로드 가능
        if (USER_ROLE === 'ADMIN' || USER_EMAIL === userEmail) {
            fetch(`${API_AWS_URL}/files/${filePath}`, {
                method: 'GET',
                headers: headerInfo,
            })
            .then(res => {
                if (res.status === 404) {
                    alert('다시 시도해주세요');
                    return;
                }
                else if (res.status === 406) {
                    alert('오류가 발생했습니다. 잠시 후 다시 이용해주세요');
                    return;
                } 
                else if (res.status === 500) {
                    alert('서버가 불안정합니다');
                    return;
                }
                else {
                    window.location.href = res.url;
                }
            })
        }  else {
            alert('관리자이거나, 등록한 사용자만 다운로드가 가능합니다');
        }
    }
    
    return (
        <>
            <div>
                <div id='notice_content_comment_footer'>
                    <div id='notice_content_comment_txt'>댓글 - {USER_NAME}</div>
                    <textarea onChange={commentChangeHandler} value={noticeInsertComment.commentContent} rows="3" id='notice_content_comment_insert' placeholder='댓글 입력'/>
                    <div className='justify'>
                        <input onChange={FileChangeHandler} type="file" name="notice_content_comment_file" id="notice_content_comment_file"/>
                        <Button onClick={handleInsertNoticeComment} className='btn_orange'>등록</Button>
                    </div>
                </div>

                <div id='notice_content_comment_size'>
                      {
                        noticeComments.map((item) => {
                            return (
                                <div key={item.commentId} style={{ height: '40px'}}>
                                    <div>
                                        <span id='notice_content_comment_writer'>{item.commentWriter}</span> 
                                        <span id='notice_content_comment_detail'>| {item.commentContent}</span>
                                        {item.commentFileList.length !== 0  
                                            && <span onClick={() => commentFileDown(item.commentFileList[0].commentFilePath, item.userEmail)} id='notice_content_comment_detail_file'> 
                                                    {item.commentFileList[0].commentFileName}
                                                </span>
                                        }
                                        <span id='notice_content_comment_detail_date'>- {item.commentCreateDate}</span>
                                    </div>

                                    {/* 내가 등록한 댓글인지 아닌지 판단 필요 */}
                                    { USER_EMAIL === item.userEmail && <Button onClick={() => handleShowDeleteModal(item.commentId)} className='btn_gray' id='notice_content_comment_delete'>X</Button>}
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
                        <Button onClick={handleDeleteNoticeComment} className='btn_orange notice_btn btn_size_100' id="notice_insert_btn">
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default NoticeComment;
