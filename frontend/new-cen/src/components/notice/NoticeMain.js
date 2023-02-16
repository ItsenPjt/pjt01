import React, { useEffect, useState } from 'react';

import { useNavigate } from "react-router-dom";

import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';

import NoticeButton from './NoticeButton';

import { BASE_URL, NOTICE } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

import './css//NoticeMain.css';

// 공지사항 메인
const NoticeMain = () => {

    const API_BASE_URL = BASE_URL + NOTICE;
    const ACCESS_TOKEN = getToken();        // 토큰값

    // 공지사항 api 데이터 
    const [notices, setNotices] = useState([]);

    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    // 렌더링 되자마자 할 일 => 공지사항 api GET 목록 호출
    useEffect(() => {
        fetch(API_BASE_URL)
        .then(res => {
            if (res.status === 406) {
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
            return res.json();
        })
        .then(result => {
            if (!!result) {
                setNotices(result.content);
            }
        });
    }, [API_BASE_URL]);

    // 제목 클릭 시 url 변경
    const navigate = useNavigate();
    const onTitleClick = (id) => {
        const path = `/notice/${id}`;
        navigate(path);
    };

    // 검색 입력 데이터
    const [searchData, setSearchData] = useState({         
        boardTitle: '',          // 검색 제목
        boardContent: '',      // 검색 내용
        boardWriter: '',        // 검색 작성자
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
        if (eventKey === '작성자') { 
            setSearchData({
                ...searchData,
                boardTitle: '',
                boardContent: '',
                boardWriter: e.target.value
            })
        } else if (eventKey === '제목') {
            setSearchData({
                ...searchData,
                boardTitle: e.target.value,
                boardContent: '',
                boardWriter: ''
            })
        } else if (eventKey === '내용') {
            setSearchData({
                ...searchData,
                boardTitle: '',
                boardContent: e.target.value,
                boardWriter: ''
            })
        } else if (eventKey === '제목+내용') {
            setSearchData({
                ...searchData,
                boardTitle: e.target.value,
                boardContent: e.target.value,
                boardWriter: ''
            })
        }  
    }

    // 검색 버튼 클릭 시 
    const handleSearch = () => {
        console.log(searchData);

        fetch(`${API_BASE_URL}/search`, {
            method: 'POST',
            headers: headerInfo,
            body: JSON.stringify(searchData)
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
                setNotices(result.content);
            }
        });
    }

    // 엔터 키 눌렀을 때 동작하는 핸들러
    const onKeyPress = (e) => {
        if (e.key === 'Enter') {
            handleSearch();
        }
    };

    var i = 0;
    return (
        <>
            <div id='notice_btn_main'>
                <NoticeButton />
                <div id='notice_table_main'>
                    <Table responsive id='notice_table'>
                        <thead>
                            <tr id='notice_main_thead'>
                                <th width="10%">번호</th>
                                <th width="20%">제목</th>
                                <th width="15%">날짜</th>
                                <th width="15%">작성자</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                notices.map((item) => {
                                    i++;
                                    return (
                                        <tr key={item.boardId} id='notice_main_tbody'>
                                            <td>{i}</td>
                                            <th id='notice_main_tbody_th' onClick={() => onTitleClick(item.boardId)}>{item.boardTitle}</th>
                                            <td>{item.createDate.substring(0, 10)}</td>
                                            <td>{item.boardWriter}</td>
                                        </tr>
                                    )
                                })
                            }
                        </tbody>
                    </Table >   
                </div>
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
        </>
    )
}

export default NoticeMain;