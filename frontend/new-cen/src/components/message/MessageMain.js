import React, { useEffect, useState } from 'react';

import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';

import MessageButton from './MessageButton';
import Pagination from '../common/Pagination';

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

        fetch(`${API_BASE_URL}?mode=${mode}&page=${page}&sort=`, {
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
            if (page===0) {
                setIsFirst(true);
            } else if(page>0) {
                setIsFirst(false);
            }

            if (page===totalPage-1) {
                setIsLast(true);
            } else {
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
        let isChanged = false;

        if(mode!==value) {
            setMode(value);
            setCurrentPage(0);
            isChanged = true;
        }
        fetch(`${API_BASE_URL}?mode=${value}&page=${currentPage}`, {
            method: 'GET',
            headers: headerInfo
        })
        .then(res => {
            if(res.status === 500) {
                alert("서버가 불안정합니다");
                return;
            } else if(res.status === 400) {
                alert("잘못된 요청 값 입니다");
                return;
            }
            return res.json();
        })
        .then(res => {
            setMessages(res.content);
            setTotalPage(res.totalPages);
            if(currentPage===res.totalPages) {
                handleChangePage(res.totalPages-1);
            }
        })
        .then(() => {
            
            if(isChanged) {
                setIsFirst(true);
                setIsLast(false);
                setSelectAll(true);

                let i = 0;
                const check_boxes = document.querySelectorAll(".message_select_checkbox");

                while(i < check_boxes.length) {
                    check_boxes[i].checked = false;
                    i++;
                }
            }
        })
    }

    // 전체 선택 / 해제
    const handleSelectAll = () => {
        let i = 0;
        const check_boxes = document.querySelectorAll(".message_select_checkbox");
        if (selectAll) {
            while(i < check_boxes.length) {
                check_boxes[i].checked = true;
                i++;
            }
            setSelectAll(false);
        } else {
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
        } else if(replyMessage.messageContent.trim() === '') {
            alert('내용을 입력하세요');
            return;
        } else {
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
            if (checkBox.checked) {
                deleteMessageList.push(checkBox.value);
            }
        })

        if (deleteMessageList.length === 0) {
            alert("삭제 할 메세지를 선택해주세요");
            return;
        } else {
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
            } else if(res.status === 400) {
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
    
    // 받은 메세지 검색
    const [receiverSearchData, setReceiverSearchData] = useState({         
        messageTitle: '',
        messageContent: '',
        messageSender: ''  
        // messageReceiver: ''     
    });
    // 보낸 메세지 검색
    const [sentSearchData, setSentSearchData] = useState({         
        messageTitle: '',
        messageContent: '',
        messageReceiver: '' 
        // messageSender: ''     
    });

    const [eventKey, setEventKey] = useState('');

    // dropdown 선택
    const onSelectItem = (ek) => {
        setEventKey(ek);

        // dropdown 버튼 text 변경
        document.getElementById('notice_select_dropdown_button').innerText = ek;

        // dropdown 선택 시 input값 지우기
        document.getElementById('notice_select_dropdown_form').value = '';
    }

    const searchChangeHandler = e => {
        console.log(mode);

        if (mode ==='received') {           // 받는 메세지

            setSentSearchData({
                ...sentSearchData,
                messageTitle: '',
                messageContent: '',
                messageReceiver: ''
            })

            if (eventKey === '작성자') { 
                setReceiverSearchData({
                    ...receiverSearchData,
                    messageTitle: '',
                    messageContent: '',
                    messageSender: e.target.value
                })
            } else if (eventKey === '제목') {
                setReceiverSearchData({
                    ...receiverSearchData,
                    messageTitle: e.target.value,
                    messageContent: '',
                    messageSender: ''
                })
            } else if (eventKey === '내용') {
                setReceiverSearchData({
                    ...receiverSearchData,
                    messageTitle: '',
                    messageContent: e.target.value,
                    messageSender: ''
                })
            } else if (eventKey === '제목+내용') {
                setReceiverSearchData({
                    ...receiverSearchData,
                    messageTitle: e.target.value,
                    messageContent: e.target.value,
                    messageSender: ''
                })
            }  
        } 
        else if (mode === 'sent') {     // 보낸 메세지

            setReceiverSearchData({
                ...receiverSearchData,
                messageTitle: '',
                messageContent: '',
                messageSender: ''
            })

            if (eventKey === '작성자') { 
                setSentSearchData({
                    ...sentSearchData,
                    messageTitle: '',
                    messageContent: '',
                    messageReceiver: e.target.value
                })
            } else if (eventKey === '제목') {
                setSentSearchData({
                    ...sentSearchData,
                    messageTitle: e.target.value,
                    messageContent: '',
                    messageReceiver: ''
                })
            } else if (eventKey === '내용') {
                setSentSearchData({
                    ...sentSearchData,
                    messageTitle: '',
                    messageContent: e.target.value,
                    messageReceiver: ''
                })
            } else if (eventKey === '제목+내용') {
                setSentSearchData({
                    ...sentSearchData,
                    messageTitle: e.target.value,
                    messageContent: e.target.value,
                    messageReceiver: ''
                })
            }  
        }
    }

    // 검색 버튼 클릭 시 
    const handleSearch = () => {
        console.log(receiverSearchData);
        console.log(sentSearchData);

        if (mode ==='received') {           // 받은 메세지 검색
            fetch(`${API_BASE_URL}/search/received`, {
                method: 'POST',
                headers: headerInfo,
                body: JSON.stringify(receiverSearchData)
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
            .then((result) => {
                if (!!result) {
                    console.log(result.content);
                    setMessages(result.content);
                }
            });
        } 
        else if (mode === 'sent') {       // 보낸 메세지 검색
            fetch(`${API_BASE_URL}/search/sent`, {
                method: 'POST',
                headers: headerInfo,
                body: JSON.stringify(sentSearchData)
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
            .then((result) => {
                if (!!result) {
                    console.log(result.content);
                    setMessages(result.content);
                }
            });
        }
    }

    // 엔터 키 눌렀을 때 동작하는 핸들러
    const onKeyPress = (e) => {
        if (e.key === 'Enter') {
            handleSearch();
        }
    };

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

                 {/* 검색 */}
                <div id='notice_search_dropdown_div'>
                    <DropdownButton drop = {'up'} title={'선택'} onSelect={(eventKey) => onSelectItem(eventKey)} id='notice_select_dropdown_button' >
                        <Dropdown.Item eventKey="작성자" id='notice_selct_dropdown_item'>작성자</Dropdown.Item>
                        <Dropdown.Item eventKey="제목" id='notice_selct_dropdown_item'>제목</Dropdown.Item>
                        <Dropdown.Item eventKey="내용" id='notice_selct_dropdown_item'>내용</Dropdown.Item>
                        <Dropdown.Item eventKey="제목+내용" id='notice_selct_dropdown_item'>제목 + 내용</Dropdown.Item>
                    </DropdownButton>
                    <Form.Control onChange={searchChangeHandler} type='text' id='notice_select_dropdown_form' placeholder='검색' onKeyDown={onKeyPress}/>
                    <Button onClick={handleSearch} id='notice_select_dropdown_search_button' className='btn_gray'>검색</Button>
                </div>

                {/* 페이지 */}
                <Pagination currentPage={currentPage} handleChangePage={handleChangePage} isFirst={isFirst} isLast={isLast} totalPage={totalPage} />
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