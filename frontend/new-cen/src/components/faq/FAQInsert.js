import React, { useState }  from 'react';
import { useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import Editor from '../common/EditorComponent';

import './css/FAQInsert.css';

import { BASE_URL, FAQ } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

// 자주 묻는 질문 추가
const FAQInsert = () => {

    const API_BASE_URL = BASE_URL + FAQ;
    const ACCESS_TOKEN = getToken();
    const headerInfo = {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    const navigate = useNavigate();

    // FAQ 등록 값
    const [faqContent, setFaqContent] = useState({
        boardTitle: '',
        boardContent: ''
    });

    const insertFaq = () => {

        if(faqContent.boardTitle === '') {
            alert("제목은 필수 입력 값 입니다");
            return;
        }
        if(faqContent.boardContent === '') {
            alert("내용은 필수 입력 값 입니다");
            return;
        }

        fetch(API_BASE_URL, {
            method: "POST",
            headers: headerInfo,
            body: JSON.stringify(faqContent)
        })
        .then(res => {
            
            if(res.status === 403) {
                alert("권한이 없습니다");
                return;
            }else if(res.status === 404) {
                alert("세션이 만료되었습니다")
                 return;
            }else {
                window.location.href = "/faq";
            }
        })
    }

    const handleEditTitle = (e) => {
        setFaqContent({
            ...faqContent,
            boardTitle: e.target.value
        })
    }

    const handleEditContent = (value) => {
        setFaqContent({
            ...faqContent,
            boardContent: value
        })
    }

    const [modal, setModal] = useState(false); 

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 취소 클릭 시 경고 모달
    const handleShowCancelModal = () => {
        setModal(true);     // 모달 열기
    }
    
    // 자주 묻는 질문 목록 페이지로
    const onFaqPage = () => {
        const path = `/faq`;
        navigate(path);
    };

    return (
        <>
            <div id='faq_insert_div'>
                <div className='justify'>
                    <Form>
                        <Form.Group className='mb-3'>
                            <Form.Control id='faq_insert_title' autoFocus type='text' placeholder='제목 입력' onChange={handleEditTitle} />
                        </Form.Group>
                    </Form>
                </div>

                <div>
                    <Editor onChange={handleEditContent} value={faqContent.boardContent} id='faq_insert_content' />
                </div>

                <div id='faq_insert_footer_div'>
                    <Button className='btn_gray btn_size_100' onClick={handleShowCancelModal}>취소</Button>
                    <Button className='btn_orange btn_size_100' id='faq_insert_btn' onClick={insertFaq}>등록</Button>
                </div>
            </div>

{/* Modal */}
            <Modal show={modal} onHide={handleClose} id="faq_insert_modal">
                <Modal.Body id='faq_insert_modal_body'>
                    <div id='faq_insert_modal_content'>
                        작성하신 글을 취소하시면 저장되지 않습니다. <br />
                        그래도 취소하시겠습니까?
                    </div>

                    <div id="faq_insert_modal_content">
                        <Button className='btn_gray faq_btn btn_size_100' onClick={handleClose}>
                            아니오
                        </Button>
                        <Button className='btn_orange faq_btn btn_size_100' id="faq_insert_btn" onClick={onFaqPage}>
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default FAQInsert;