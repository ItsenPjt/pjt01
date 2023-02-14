import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import { BASE_URL, QUESTION } from '../common/config/host-config';
import { getToken, getUserRole } from '../common/util/login-util';

import QuestionComment from './QuestionComment';

import './css/QuestionContent.css';

// 문의사항 내용
const QuestionContent = () => {
    var questionId = useParams().questionId;
    
    const API_BASE_URL = BASE_URL + QUESTION;
    const ACCESS_TOKEN = getToken();
    const USER_ROLE = getUserRole();        // 권한

    // 문의사항 api 데이터 
    const [questionContents, setQuestionContents] = useState([]);

    const [modal, setModal] = useState(false); 

    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    // 렌더링 되자마자 할 일 => 공지사항 api GET 목록 호출
    useEffect(() => {
        fetch(`${API_BASE_URL}/${questionId}`, {
            method: 'GET',
            headers: headerInfo
        })
        .then(res => {
            if (res.status === 403) {
                alert('로그인이 필요한 서비스입니다');

                window.location.href = '/join';
                return;
            } 
            else if (res.status === 500) {
                alert('서버가 불안정합니다');
                return;
            }
            return res.json();
        })
        .then(result => {
            setQuestionContents(result);
        });
    }, [API_BASE_URL]);

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 삭제 클릭 시 경고 모달
    const handleShowDeleteModal = () => {
        setModal(true);     // 모달 열기
    }

    // 문의사항 삭제 서버 요청 (DELETE)
    const handleDeleteQuestion = () => {
        fetch(`${API_BASE_URL}/${questionId}`, {
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
                window.location.href = "/question";       // 공지사항 목록 페이지로 이동
            }
        })
    }

    // 문의사항 수정 페이지로
    const navigate = useNavigate();
    const onUpdatePage = () => {
        const path = `/question/update/${questionId}`;
        navigate(path);
    };

    // 문의사항 목록 페이지로
    const onQuestionPage = () => {
        const path = `/question`;
        navigate(path);
    };

    return (
        <>
            <div id='question_content_main'>
                <div className='justify'>
                    <div>
                        <Form id='question_content_title'>
                            {questionContents.boardTitle}
                        </Form>

                        <div id='question_content_write'>
                            작성자 : {questionContents.boardWriter} | 작성일 : {questionContents.createDate}
                        </div>
                    </div>
                    
                    <>
                        {/* 권한이 ADMIN 인 경우에만 '수정','삭제' 버튼 보이도록 */}
                        {USER_ROLE === 'ADMIN' 
                        ? 
                            <div id='question_content_body_div'>
                                <Button onClick={onUpdatePage} className='btn_gray btn_size_100'>수정</Button>
                                <Button onClick={handleShowDeleteModal} className='btn_orange btn_size_100' id='question_content_delete_btn'>삭제</Button>
                                <Button onClick={onQuestionPage} className='btn_indigo btn_size_100' id='question_content_list'>목록</Button>
                            </div>
                            :
                            <div id='question_content_body_div'>
                                <Button onClick={onQuestionPage} className='btn_indigo btn_size_100'>목록</Button>
                            </div>
                        }
                    </>
                </div>

                {/* dangerouslySetInnerHTML : String형태를 html로 */}
                <div>
                    <Form id='question_contents'
                        dangerouslySetInnerHTML={{
                            __html: questionContents.boardContent
                        }} 
                    />
                </div>

                {/* 댓글 */}
                <QuestionComment questionId = {questionId}/>
            </div>

            {/* Modal */}
            <Modal show={modal} onHide={handleClose} id="question_delete_modal">
                <Modal.Body id='question_delete_modal_body'>
                    <div id='question_delete_modal_content'>
                        문의사항을 삭제하시겠습니까?
                    </div>

                    <div id="question_delete_modal_content">
                        <Button onClick={handleClose} className='btn_gray question_btn btn_size_100'>
                            아니오
                        </Button>
                        <Button onClick={handleDeleteQuestion} className='btn_orange question_btn btn_size_100' id="question_content_delete_btn">
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default QuestionContent;
