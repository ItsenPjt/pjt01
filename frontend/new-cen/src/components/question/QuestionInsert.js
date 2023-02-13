import React, { useState }  from 'react';
import { useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import { BASE_URL, QUESTION } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

import Editor from '../common/EditorComponent';

import './css/QuestionInsert.css';

// 문의사항 추가
const QuestionInsert = () => {
    
    const API_BASE_URL = BASE_URL + QUESTION;
    const ACCESS_TOKEN = getToken();

    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    const [modal, setModal] = useState(false); 
    const [questionData, setQuestionData] = useState({          // 문의사항 입력 데이터
        boardTitle: '',          // 문의사항 제목
        boardCommentIs: 'ON',      // 문의사항 댓글 여부 - 문의사항 댓글 항상 ON
        boardContent: '',        // 문의사항 내용
    });

    // title
    const titleChangeHandler = e => {
        setQuestionData({
            ...questionData,        // 기존 questionData 복사 후 boardTitle 추가
            boardTitle: e.target.value,
        });
    };

    // 내용
    const contentChangeHandler = value => {
        setQuestionData({
            ...questionData,        // 기존 questionData 복사 후 boardContent 추가
            boardContent: value,
        });
    };

    // 문의사항 등록 서버 요청  (POST에 대한 응답처리)
    const handleInsertNotice = () => {
        if (questionData.boardTitle === '') {
            alert('제목을 입력해주세요.');
        } else if (questionData.boardContent === '') {
            alert('내용을 입력해주세요.');
        } else {
            console.log(questionData);
            console.log(ACCESS_TOKEN);
            
            fetch(API_BASE_URL, {
                method: 'POST',
                headers: headerInfo,
                body: JSON.stringify(questionData)
            })
            .then(res => {
                if (res.status === 406) {
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
            .then(() => {
                window.location.href = "/question";       // 문의사항 목록 페이지로 이동
            });
        }
    };

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 취소 클릭 시 경고 모달
    const handleShowCancelModal = () => {
        setModal(true);     // 모달 열기
    }

    // 문의사항 목록 페이지로
    const navigate = useNavigate();
    const onQuestionPage = () => {
        const path = `/question`;
        navigate(path);
    };

    return (
        <>
            <div id='question_insert_div'>
                <div className='justify'>
                    <Form>
                        <Form.Group className='mb-3'>
                            <Form.Control onChange={titleChangeHandler} value={questionData.boardTitle} id='question_insert_title' autoFocus type='text' placeholder='문의사항 제목 입력' />
                        </Form.Group>
                    </Form>
                </div>

                <div>
                    <Editor onChange={contentChangeHandler} value={questionData.boardContent} />
                </div>

                <div id='question_insert_footer_div'>
                    <Button onClick={handleShowCancelModal} className='btn_gray btn_size_100'>취소</Button>
                    <Button onClick={handleInsertNotice} className='btn_orange btn_size_100' id='question_insert_btn'>등록</Button>
                </div>
            </div>

{/* Modal */}
            <Modal show={modal} onHide={handleClose} id="question_insert_modal">
                <Modal.Body id='question_insert_modal_body'>
                    <div id='question_insert_modal_content'>
                        작성하신 글을 취소하시면 저장되지 않습니다. <br />
                        그래도 취소하시겠습니까?
                    </div>

                    <div id="question_insert_modal_content">
                        <Button className='btn_gray question_btn btn_size_100' onClick={handleClose}>
                            아니오
                        </Button>
                        <Button className='btn_orange question_btn btn_size_100' id="question_insert_btn" onClick={onQuestionPage}>
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default QuestionInsert;