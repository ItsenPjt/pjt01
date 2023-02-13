import React, { useState, useEffect }  from 'react';
import { useParams, useNavigate } from 'react-router-dom';


import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import Editor from '../common/EditorComponent';

import './css/FAQUpdate.css';

import { BASE_URL, FAQ } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

// 자주 묻는 질문 수정
const FAQUpdate = () => {
    
    const API_BASE_URL = BASE_URL + FAQ;
    const ACCESS_TOKEN = getToken();
    const headerInfo = {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }
    const navigate = useNavigate();


    // 자주 묻는 질문 상세 
    const [faqContent, setFaqContent] = useState({});


    // FAQ 번호
    const faqId = useParams().faqId;

    // 렌더링 되자마자 할 일 => FAQ api GET 상세 호출
    useEffect(() => {
        
        fetch(`${API_BASE_URL}/${faqId}`)
        .then(res => {
            if (res.status === 500) {
                alert('서버가 불안정합니다');
                return;
            }
            return res.json();
        })
        .then(res => {
            setFaqContent(res);
        })
    }, [API_BASE_URL]); 


    const [modal, setModal] = useState(false); 

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
    };

    // 취소 클릭 시 경고 모달
    const handleShowCancelModal = () => {
        setModal(true);     // 모달 열기
    }

    // 자주 묻는 질문 내용 페이지로
    const onFaqContentPage = () => {
        const path = `/faq/${faqId}`;
        navigate(path);
    };
    
    return (
        <>
            <div id='faq_update_div'>
                <div className='justify'>
                    <Form>
                        <Form.Group className='mb-3'>
                            <Form.Control id='faq_update_title' autoFocus type='text' defaultValue={faqContent.boardTitle}/>
                        </Form.Group>
                    </Form>
                </div>

                <div>
                    <Editor id='faq_update_content' defaultValue={faqContent.boardContent}/>
                        
                </div>

                <div id='faq_update_footer_div'>
                    <Button className='btn_gray btn_size_100' onClick={handleShowCancelModal}>취소</Button>
                    <Button className='btn_orange btn_size_100' id='faq_update_btn'>수정</Button>
                </div>
            </div>

{/* Modal */}
            <Modal show={modal} onHide={handleClose} id="faqe_update_modal">
                <Modal.Body id='faq_update_modal_body'>
                    <div id='faq_update_modal_content'>
                        작성하신 글을 취소하시면 저장되지 않습니다. <br />
                        그래도 취소하시겠습니까?
                    </div>

                    <div id="faq_update_modal_content">
                        <Button className='btn_gray faq_btn btn_size_100' onClick={handleClose}>
                            아니오
                        </Button>
                        <Button className='btn_orange faq_btn btn_size_100' id="faq_update_btn" onClick={onFaqContentPage}>
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default FAQUpdate;
