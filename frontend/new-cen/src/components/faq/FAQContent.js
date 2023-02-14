import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import './css/FAQContent.css';
import { BASE_URL, FAQ } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

// 자주 묻는 질문 내용
const FAQContent = () => {

    const API_BASE_URL = BASE_URL + FAQ;

    const ACCESS_TOKEN = getToken();

    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    // 네비게이터
    const navigate = useNavigate();
    
    // FAQ 번호
    const faqId = useParams().faqId;

    // 자주 묻는 질문 상세 
    const [faqContent, setFaqContent] = useState({});


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

    // 삭제 클릭 시 경고 모달
    const handleShowDeleteModal = () => {
        setModal(true);     // 모달 열기
    }

    // FAQ 삭제 후 자주 묻는 질문 목록 페이지로
    const onFaqPage = () => {

        fetch(`${API_BASE_URL}/${faqId}`, {
            headers: headerInfo,
            method: "DELETE"
        })
        .then(res => {
            if(res.status === 500) {
                alert("서버가 불안정합니다")
                window.location.href = "/";
                return;
            }else if(res.status === 403) {
                alert("권한이 없습니다");
                window.location.href = "/";
                return;
            }else if(res.status === 404) {
                alert("존재하지 않는 회원 혹은 FAQ입니다")
                window.location.href = "/";
                return;
            }
        })

        alert("해당 글이 삭제되었습니다");
        navigate("/faq");
    }

    // 자주 묻는 질문 수정 페이지로
    const onUpdatePage = () => {
        const path = `/faq/update/${faqId}`;
        navigate(path);
    };

    return (
        <>
            <div id='faq_content_main'>
                <div className='justify'>
                    <div>
                        <Form id='faq_content_title'>
                            {faqContent.boardTitle}
                        </Form>

                        <div id='faq_content_write'>
                            작성자 : {faqContent.boardWriter} | 작성일 : {faqContent.boardCreatedate}
                        </div>
                    </div>

                    <div id='faq_content_body_div'>
                        <Button className='btn_gray btn_size_100' onClick={onUpdatePage}>수정</Button>
                        <Button className='btn_orange btn_size_100' id='faq_content_delete_btn' onClick={handleShowDeleteModal}>삭제</Button>
                    </div>
                </div>
                <div>
                    <Form id='faq_contents' 
                        dangerouslySetInnerHTML={{
                            __html: faqContent.boardContent
                        }}
                    >
                    </Form>
                </div>
            </div>

            {/* Modal */}
            <Modal show={modal} onHide={handleClose} id="faq_delete_modal">
                <Modal.Body id='faq_delete_modal_body'>
                    <div id='faq_delete_modal_content'>
                        게시물을 삭제하시겠습니까?
                    </div>

                    <div id="faq_delete_modal_content">
                        <Button className='btn_gray faq_btn btn_size_100' onClick={handleClose}>
                            아니오
                        </Button>
                        <Button className='btn_orange faq_btn btn_size_100' id="faq_content_delete_btn" onClick={onFaqPage}>
                            네
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default FAQContent;
