import React, { useEffect, useState } from 'react';

import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';

import MessageButton from './MessageButton';
import Pagination from './Pagination';


import { BASE_URL, MESSAGE } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

import './css/MessageMain.css';

// 메세지 메인
const MessageMain = () => {

    const API_BASE_URL = BASE_URL + MESSAGE;
    const ACCESS_TOKEN = getToken();

    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }


    // Pagination
    const [currentPage, setCurrentPage] = useState(0);

    const [totalPage, setTotalPage] = useState(0);

    const [isFirst, setIsFirst] = useState(true);

    const [isLast, setIsLast] = useState(false);

    const handleChangePage = (page) => {
        
        setCurrentPage(page);

        fetch(`${API_BASE_URL}?mode=received&page=${page}&sort=`, {
            method: 'GET',
            headers: headerInfo
        })
        .then(res => {

            if(res.status === 500) {
                alert("서버가 불안정합니다");
                return;
            }else if(res.status === 400) {
                alert("잘못된 요청 값 입니다");
                return;
            }

            return res.json();
        })
        .then(res => {
            setMessages(res.content);
            setTotalPage(res.totalPages);
            if(page===0) {
                setIsFirst(true);
            }else if(page>0) {
                setIsFirst(false);
            }

            if(page===totalPage) {
                setIsLast(true);
            }else {
                setIsLast(false);
            }

        });

    }




    // 메세지 api 데이터 
    const [messages, setMessages] = useState([]);

    // 전체 선택 여부
    const [selectAll, setSelectAll] = useState(true);

    // mode값 
    const [mode, setMode] = useState('received'); // 초기값?

    const changeMode = (value) => {

        setMode(value);
        setCurrentPage(0);
        fetch(`${API_BASE_URL}?mode=${value}&page=0`, {
            method: 'GET',
            headers: headerInfo
        })
        .then(res => {

            if(res.status === 500) {
                alert("서버가 불안정합니다");
                return;
            }else if(res.status === 400) {
                alert("잘못된 요청 값 입니다");
                return;
            }

            return res.json();
        })
        .then(res => {
            setMessages(res.content);
            setCurrentPage(0);
            setIsFirst(true);
            setIsLast(false);
            setTotalPage(res.totalPages);
        })
        .then(() => {
            setSelectAll(true);
            let i = 0;
            const check_boxes = document.querySelectorAll(".message_select_checkbox");
            while(i < check_boxes.length) {
                check_boxes[i].checked = false;
                i++;
            }
        })
    }
 

    // 전체 선택 / 해제
    const handleSelectAll = () => {
        let i = 0;
        const check_boxes = document.querySelectorAll(".message_select_checkbox");
        if(selectAll) {
            while(i < check_boxes.length) {
                check_boxes[i].checked = true;
                i++;
            }
            setSelectAll(false);
        }else {
            while(i < check_boxes.length) {
                check_boxes[i].checked = false;
                i++;
            }
            setSelectAll(true);
        }
    }


    // 받은 메세지 모달
    const [receiveModal, setReceiveModal] = useState(false); 

    // 답장하기 모달
    const [replyModal, setReplyModal] = useState(false); 


    // 모달 닫기
    const handleClose = () => {
        setReceiveModal(false);
        setReplyModal(false);
        setMessageDetail({
            messageTitle: '',
            messageContent: '',
            username: ''
        });
        setReplyInfo({
            userId: '',
            username: ''
        })
    };

    // 제목 클릭 시 받은 메세지 상세 모달
    const handleShowReceiveModal = () => {
        setReceiveModal(true);     // 받은 메세지 모달 열기
    }

    // 메세지 상세
    const [messageDetail, setMessageDetail] = useState({
        messageTitle: '',
        messageContent: '',
        username: '',
        userId: ''
    });

    const handleMessageDetail = (messageId) => {

        fetch(`${API_BASE_URL}/${messageId}?mode=${mode}`, {
            headers: headerInfo
        })
        .then(res => {
            if(res.status === 400) {
                alert("잘못된 요청 값 입니다")
                return; 
            }else if(res.status === 401) {
                alert("세션이 만료되었습니다")
                window.location.href = "/";
            }else if(res.status === 404) {
                alert("해당 메세지를 찾을 수 없습니다");
                return;
            }
            return res.json();
        })
        .then(res => {
            if(mode === 'received') {
                setMessageDetail({
                    messageTitle: res.messageTitle,
                    messageContent: res.messageContent,
                    username: res.messageSender,
                    userId: res.senderId
                })
            }else if(mode === 'sent') {
                setMessageDetail({
                    messageTitle: res.messageTitle,
                    messageContent: res.messageContent,
                    username: res.messageReceiver
                })
            }

        })


    }

    // 답장하기 수신인 정보
    const [replyInfo, setReplyInfo] = useState({
        userId: '',
        username: ''
    });


    // 답장하기 상세 모달
    const handleShowReplyModal = () => {
        setReplyInfo({
            userId: messageDetail.userId,
            username: messageDetail.username
        })
        setReceiveModal(false);     // 받은 메세지 모달은 닫기
        setReplyModal(true);     // 답장하기 모달 열기
    }

    // 답장하기 메세지
    const [replyMessage, setReplyMessage] = useState({
        messageTitle: '',
        messageContent: ''
    })

    // 답장하기 제목 저장
    const saveReplyTitle = (e) => {
        setReplyMessage({
            ...replyMessage,
            messageTitle: e.target.value
        });
    }

    // 답장하기 내용 저장 
    const saveReplyContent = (e) => {
        setReplyMessage({
            ...replyMessage,
            messageContent: e.target.value
        });
    }

    // 답장 보내기 
    const sendReplyMessage = () => {
        if(replyMessage.messageTitle.trim() === '') {
            alert('제목을 입력하세요');
            return;
        }else if(replyMessage.messageContent.trim() === '') {
            alert('내용을 입력하세요');
            return;
        }else {
            fetch(`${API_BASE_URL}?receiverList=${replyInfo.userId}`, {
                method: 'POST',
                headers: headerInfo,
                body: JSON.stringify(replyMessage)
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

    // 선택 메세지 삭제
    const handleDeleteMessage = () => {

        const deleteMessageList = [];

        const messageSelectCheckBox = document.querySelectorAll(".message_select_checkbox");
        
        messageSelectCheckBox.forEach((checkBox) => {
            if(checkBox.checked) {
                deleteMessageList.push(checkBox.value);
            }
        })

        if(deleteMessageList.length === 0) {
            alert("삭제 할 메세지를 선택해주세요");
            return;
        }else {
            fetch(`${API_BASE_URL}?messageId=${deleteMessageList}`, {
                method: 'DELETE',
                headers: headerInfo,
            })
            .then(res => {
                if(res.status === 401) {
                    alert('세션이 만료되었습니다');
                    window.location.href = "/";
                }else if(res.status === 400) {
                    alert('존재하지 않는 메세지 입니다');
                    return;
                }
                return res.json();
            })
            .then(res => {
                if(res) {
                    alert(`${deleteMessageList.length}개의 메세지를 삭제했습니다😊`);
                    changeMode(mode);
                }
            })
        }

    }
    

    // 렌더링 되자마자 할 일 => 메세지 api GET 목록 호출
    useEffect(() => {
        fetch(`${API_BASE_URL}?mode=received&page=${currentPage}&sort=`, {
            method: 'GET',
            headers: headerInfo
        })
        .then(res => {

            if(res.status === 500) {
                alert("서버가 불안정합니다");
                return;
            }else if(res.status === 400) {
                alert("잘못된 요청 값 입니다");
                return;
            }

            return res.json();
        })
        .then(res => {
            setMessages(res.content);
            setTotalPage(res.totalPages);
        });
    }, [API_BASE_URL]);

    return (
        <>
            <div id='message_btn_main'>
                <MessageButton changeMode={changeMode} handleDeleteMessage={handleDeleteMessage}/>
                <div id='message_table_main'>
                    <Table responsive id='message_table'>
                        <thead>
                            <tr id='message_main_thead'>
                                {(mode==='received'&& <th width="10%">보낸 사람</th>) || (mode==='sent'&& <th width="10%">받는 사람</th>)} 
                                <th width="20%">제목</th>
                                <th width="15%">날짜</th>
                                <th width="15%" id='message_main_all_select' onClick={handleSelectAll}>전체 선택</th>
                            </tr>
                        </thead>
                        <tbody>

                            {   
                                messages.map((item) => {
                                    return (
                                        <tr key={item.messageId} id='message_main_tbody' onClick={(messageId) => handleMessageDetail(item.messageId)}>
                                            {(mode==='received' && <td>{item.messageSender}</td>) || (mode==='sent' && <td>{item.messageReceiver}</td>) }
                                            <th id='message_main_tbody_th' onClick={handleShowReceiveModal}>{item.messageTitle}</th>
                                            <td>{item.messageSenddate}</td>
                                            <td><input type='checkbox' defaultValue={item.messageId} className='message_select_checkbox' /></td>
                                        </tr>
                                    )
                                })     
                            } 
                        </tbody>
                    </Table >   
                </div>
                <Pagination currentPage={currentPage} handleChangePage={handleChangePage} isFirst={isFirst} isLast={isLast} totalPage={totalPage}/>
            </div>

            {/* Modal */}
            <Modal show={receiveModal} onHide={handleClose} id="message_send_modal">
                <Modal.Body>
                    <div id='message_send_modal_body'>
                        <Form.Group className='mb-3'>
                            
                            {(mode === 'received' && <Form.Label id='message_form_label'>보낸 사람</Form.Label>)} 
                            {(mode === 'sent' && <Form.Label id='message_form_label'>받는 사람</Form.Label>)}
                            <Form.Control type='text' className='message_form_control' defaultValue={messageDetail.username} disabled/>
                        </Form.Group>
                        <Form.Group className='mb-3'>
                            <Form.Label id='message_form_label'>제목</Form.Label>
                            <Form.Control type='text' className='message_form_control' defaultValue={messageDetail.messageTitle} disabled/>
                        </Form.Group>
                        <Form.Group className='mb-3'>
                            <Form.Label id='message_form_label'>내용</Form.Label>
                            <textarea rows="5" className="form-control" id='message_send_content' defaultValue={messageDetail.messageContent} disabled/>
                        </Form.Group>
                    </div>

                    <div id='message_send_modal_footer'>
                        <Button className='btn_gray notice_btn btn_size_100' onClick={handleClose}>
                            닫기
                        </Button>
                        {
                            (   mode === 'received' && 
                                <Button className='btn_orange notice_btn btn_size_100' id="notice_content_delete_btn" onClick={handleShowReplyModal}>
                                    답장하기
                                </Button> 
                            )
                        }
                  
                    </div>
                </Modal.Body>
            </Modal>

            <Modal show={replyModal} onHide={handleClose} id="message_send_modal">
                <Modal.Body>
                    <div id='message_send_modal_body'>
                        <Form.Group className='mb-3'>
                            <Form.Label id='message_form_label'>받는 사람</Form.Label>
                            <Form.Control autoFocus type='text' className='message_form_control' placeholder='받는 사람' id="message_receiver" defaultValue={replyInfo.username} disabled/>
                        </Form.Group>
                        <Form.Group className='mb-3'>
                            <Form.Label id='message_form_label'>제목</Form.Label>
                            <Form.Control type='text' className='message_form_control' placeholder='제목' onChange={(e) => saveReplyTitle(e)}/>
                        </Form.Group>
                        <Form.Group className='mb-3'>
                            <Form.Label id='message_form_label'>내용</Form.Label >
                            <textarea rows="5" className="form-control" onChange={(e) => saveReplyContent(e)}/>
                        </Form.Group>
                    </div>

                    <div id='message_send_modal_footer'>
                        <Button className='btn_gray notice_btn btn_size_100' onClick={handleClose}>
                            취소
                        </Button>
                        <Button className='btn_orange notice_btn btn_size_100' id="notice_content_delete_btn" onClick={sendReplyMessage}>
                            보내기
                        </Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default MessageMain