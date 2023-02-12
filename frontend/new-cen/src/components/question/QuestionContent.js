import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import { BASE_URL, QUESTION } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

import './css/QuestionContent.css';

// 문의사항 내용
const QuestionContent = () => {
    var questionId = useParams().questionId;
    
    const API_BASE_URL = BASE_URL + QUESTION;
    const ACCESS_TOKEN = getToken();

    // 문의사항 api 데이터 
    const [noticeContents, setNoticeContents] = useState([]);

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
                // if (res.status === 403) {
                //     alert('로그인이 필요한 서비스입니다');

                //     window.location.href = '/';
                //     return;
                // } 
                // else if (res.status === 500) {
                //     alert('서버가 불안정합니다');
                //     return;
                // }
                return res.json();
            })
            .then(result => {
                console.log(result);
                setNoticeContents(result);
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

    // 문의사항 목록 페이지로
    const onQuestionPage = () => {
        window.location.href = "/question";
    }

    // 문의사항 수정 페이지로
    const navigate = useNavigate();
    const onUpdatePage = () => {
        const path = `/question/update/${questionId}`;
        navigate(path);
    };

    return (
        <>
            <div id='question_content_main'>
                <div className='justify'>
                    <div>
                        <Form id='question_content_title'>
                            {noticeContents.boardTitle}
                        </Form>

                        <div id='question_content_write'>
                            작성자 : {noticeContents.boardWriter} | 작성일 : {noticeContents.createDate}
                        </div>
                    </div>

                    <div id='question_content_body_div'>
                        <Button className='btn_gray btn_size_100' onClick={onUpdatePage}>수정</Button>
                        <Button className='btn_orange btn_size_100' id='question_content_delete_btn' onClick={handleShowDeleteModal}>삭제</Button>
                    </div>
                </div>

                <div>
                    <Form id='question_contents'>
                        {noticeContents.boardContent}
                    </Form>
                </div>

            </div>

            {/* Modal */}
            <Modal show={modal} onHide={handleClose} id="question_delete_modal">
                <Modal.Body id='question_delete_modal_body'>
                    <div id='question_delete_modal_content'>
                        문의사항을 삭제하시겠습니까?
                    </div>

                    <div id="question_delete_modal_content">
                        <Button className='btn_gray question_btn btn_size_100' onClick={handleClose}>
                            아니오
                        </Button>
                        <Button className='btn_orange question_btn btn_size_100' id="question_content_delete_btn" onClick={onQuestionPage}>
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default QuestionContent;
