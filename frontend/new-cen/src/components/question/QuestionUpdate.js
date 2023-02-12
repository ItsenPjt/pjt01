import React, { useState, useEffect }  from 'react';
import { useParams, useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import { BASE_URL, QUESTION } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

import Editor from '../common/EditorComponent';

import './css/QuestionUpdate.css';

// 문의사항 수정
const QuestionUpdate = () => {
    var questionId = useParams().questionId;

    const API_BASE_URL = BASE_URL + QUESTION;
    const ACCESS_TOKEN = getToken();

    // 공지사항 api 데이터 
    const [ questionContents, setQuestionContents] = useState([]);

    const [modal, setModal] = useState(false); 
    const [desc, setDesc] = useState('');

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
                setQuestionContents(result);
            });
    }, [API_BASE_URL]);


    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 취소 클릭 시 경고 모달
    const handleShowCancelModal = () => {
        setModal(true);     // 모달 열기
    }
    
    function onEditorChange(value) {
        setDesc(value);
    };

    // 문의사항 내용 페이지로
    const navigate = useNavigate();
    const onQuestionContentPage = () => {
        const path = `/question/${questionId}`;
        navigate(path);
    };

    return (
        <>
            <div id='question_update_div'>
                <div className='justify'>
                    <Form>
                        <Form.Group className='mb-3'>
                            <Form.Control id='question_update_title' autoFocus type='text' defaultValue={questionContents.boardTitle}/>
                        </Form.Group>
                    </Form>
                </div>

                <div>
                    <Editor value={desc} onChange={onEditorChange} defaultValue={questionContents.boardContent}/>
                </div>

                <div id='question_update_footer_div'>
                    <Button className='btn_gray btn_size_100' onClick={handleShowCancelModal}>취소</Button>
                    <Button className='btn_orange btn_size_100' id='question_update_btn'>수정</Button>
                </div>
            </div>

{/* Modal */}
            <Modal show={modal} onHide={handleClose} id="questione_update_modal">
                <Modal.Body id='question_update_modal_body'>
                    <div id='question_update_modal_content'>
                        작성하신 글을 취소하시면 저장되지 않습니다. <br />
                        그래도 취소하시겠습니까?
                    </div>

                    <div id="question_update_modal_content">
                        <Button className='btn_gray question_btn btn_size_100' onClick={handleClose}>
                            아니오
                        </Button>
                        <Button className='btn_orange question_btn btn_size_100' id="question_update_btn" onClick={onQuestionContentPage}>
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default QuestionUpdate;
