import React, { useState } from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import { BASE_URL, MESSAGE } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

import './css/MessageButton.css';

// 메세지 버튼
const MessageButton = ({changeMode, handleDeleteMessage}) => {

    const API_BASE_URL = BASE_URL + MESSAGE;
    const ACCESS_TOKEN = getToken();

    const headerInfo = {
        'content-type' : 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    const [modal, setModal] = useState(false); 

    // 모달 닫기
    const handleClose = () => {
        setModal(false);
        setSearchReceiverList([]);
        setReceiverList([]);
    };

    // 삭제 클릭 시 경고 모달
    const handleSendModal = () => {
        setModal(true);     // 모달 열기
    }

    // 실시간 회원 검색 목록
    const [searchReceiverList, setSearchReceiverList] = useState([]);

    // 메세지 수신인 리스트
    const [receiverList, setReceiverList] = useState([]);

    // 실시간 검색
    const handleSearchReceiver = (e) => {

        fetch(API_BASE_URL+"/receiver?username="+e.target.value, {
            headers: headerInfo,
        })
        .then(res => res.json())
        .then(res => {
            if(!!res) {
                setSearchReceiverList(res);
            }
        })
    }

    // 선택된 수신인 목록
    const receiverListContainer = document.querySelector('#message_receiver_list');

    // 수신인 추가
    const handleAddReceiver = (userId, userName, e) => {

        let addable = true;

        receiverList.forEach((receiver) => {
            if(receiver === userId) {
                addable = false;
                return;
            }
        })

        if(addable) {
            receiverListContainer.innerHTML += 
            `<button type='button' class='receiver_button' id=${userId}>${userName}</button>`
            setReceiverList([
                ...receiverList, 
                userId
            ]);
        }
    }


    const deleteReceiver = (e) => {

            setReceiverList(receiverList.filter(
                (userId) => userId !== e.target.id
            ));
            
            e.target.remove();
    }

    // 메세지 보내기 유효성 검사

    const [messageValid, setMessageValid] = useState({
        title: false,
        content: false
    });

    const [message, setMessage] = useState({
        messageTitle: '',
        messageContent: ''
    });
    
    const saveTitle = (e) => {

        if(!e.target.value) {
            setMessageValid({
                ...messageValid,
                title: false
            })
        }else if(e.target.value.trim() === '') {
            setMessageValid({
                ...messageValid,
                title: false
            })
        }else {
            setMessageValid({
                ...messageValid,
                title: true
            })
        }

        setMessage({
            ...message,
            messageTitle: e.target.value
        })
    }

    const saveContent = (e) => {

        if(!e.target.value) {
            setMessageValid({
                ...messageValid,
                content: false
            })
        }else if(e.target.value.trim() === '') {
            setMessageValid({
                ...messageValid,
                content: false
            })
        }else {
            setMessageValid({
                ...messageValid,
                content: true
            })
        }

        setMessage({
            ...message,
            messageContent: e.target.value
        })
    }
   
    // 메세지 보내기 버튼
    const handleSendMessage = () => {
  
        if(receiverList.length===0) {
            alert("최소 한명의 수신인을 선택해주세요");
            return;
        }else if(!messageValid.title) {
            alert("제목을 입력해주세요");
            return;
        }else if(!messageValid.content) {
            alert('내용을 입력해주세요');
            return;
        }else {
            fetch(`${API_BASE_URL}?receiverList=${receiverList}`, {
                method: 'POST',
                headers: headerInfo,
                body: JSON.stringify(message)
            })
            .then(res => {
                if(res.status === 400) {
                    alert("잘못된 요청 값 입니다")
                    return; 
                }else if(res.status === 401) {
                    alert("세션이 만료되었습니다")
                    window.location.href = "/";
                }else if(res.status === 404) {
                    alert("수신인 목록을 다시 확인해주세요");
                    return;
                }
                
                return res.json();
            })
            .then(res => {
                if(res) {
                    alert("메세지를 전송했습니다😊");
                    handleClose();
                    changeMode('sent');
                }
            })
        }
    }

    return (
        <>
            <div className='justify'>
                <div id='message_button_txt'>메세지</div>
                <div id='message_button_group'>
                    <Button className='btn_indigo' id='message_button_sent' onClick={() => changeMode('sent')}>보낸 메세지</Button>
                    <Button className='btn_indigo' id='message_button_reception' onClick={() => changeMode('received')}>받은 메세지</Button>
                    <Button className='btn_indigo' id='message_button_select_delete' onClick={handleDeleteMessage}>선택 삭제</Button>
                    <Button onClick={handleSendModal} className='btn_orange' id='message_button_send'>메세지 보내기</Button>
                </div>
            </div> 

            {/* Modal */}
            <Modal show={modal} onHide={handleClose} id="message_send_modal">
                <Modal.Body>
                    <div id='message_send_modal_body'>
                        <Form.Group className='mb-3'>
                            <Form.Label id='message_form_label'>받는 사람</Form.Label>
                            <Form.Control autoFocus type='text' className='message_form_control' placeholder='받는 사람' id='message_receiver' onChange={handleSearchReceiver}/>
                            <div className='message_form_control' id='message_receiver_list' disabled onClick={deleteReceiver}>
                            </div>

                            <div id="receiver_list_container">
                                { searchReceiverList && searchReceiverList.map((item) => {
                                    return (
                                        <div key={item.userId} className="receiver_list_option" onClick={(e) => handleAddReceiver(item.userId, item.userName, e)}>
                                            <span>{item.userName}({item.userEmail})</span>
                                        </div>
                                    )
                                })}
                            </div> 
                        </Form.Group>
                        <Form.Group className='mb-3'>
                            <Form.Label id='message_form_label'>제목</Form.Label>
                            <Form.Control type='text' className='message_form_control' placeholder='제목' id='message_title' onChange={saveTitle}/>
                        </Form.Group>
                        <Form.Group className='mb-3'>
                            <Form.Label id='message_form_label'>내용</Form.Label>
                            <textarea rows="5" className="form-control" id='message_content' onChange={saveContent}/>
                        </Form.Group>
                    </div>

                    <div id='message_send_modal_footer'>
                        <Button className='btn_gray notice_btn btn_size_100' onClick={handleClose}>
                            취소
                        </Button>
                        <Button className='btn_orange notice_btn btn_size_100' id="notice_content_delete_btn" onClick={handleSendMessage}>
                            보내기
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>    
    )
}

export default MessageButton