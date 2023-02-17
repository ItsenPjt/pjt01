import React, { useEffect, useState } from 'react';

import { useNavigate } from "react-router-dom";

import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';

import Pagination from '../common/Pagination';

import QuestionButton from './QuestionButton';

import { BASE_URL, QUESTION } from '../common/config/host-config';
import { getToken } from '../common/util/login-util';

import './css/QuestionMain.css';

// 문의사항 메인
const QuestionMain = () => {

    const API_BASE_URL = BASE_URL + QUESTION;
    const ACCESS_TOKEN = getToken();

    // 문의사항 api 데이터 
    const [questions, setQuestions] = useState([]);

    // headers
    const headerInfo = {
        'content-type': 'application/json',
        'Authorization': 'Bearer ' + ACCESS_TOKEN
    }

    // 검색 여부
    const [isSearched, setIsSearched] = useState(false);

    // Pagination 
    const [currentPage, setCurrentPage] = useState(0);

    const [totalPage, setTotalPage] = useState(0);

    const [isFirst, setIsFirst] = useState(true);

    const [isLast, setIsLast] = useState(false);

    const handleChangePage = (page) => {

        setCurrentPage(page);

        if(isSearched) {

            fetch(`${API_BASE_URL}/search?page=${page}`, {
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
                    setQuestions(result.content);
                    setTotalPage(result.totalPages);
                }

                if(page===0) {
                    setIsFirst(true);
                }else if(page>0) {
                    setIsFirst(false);
                }
    
                if(page===totalPage-1) {
                    setIsLast(true);
                }else {
                    setIsLast(false);
                }
            });

        }else {
            fetch(`${API_BASE_URL}?page=${page}`)
            .then(res => {
                if (res.status === 403) {
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
            .then(result => {
                if (!!result) {
                    setQuestions(result.content);
                    setTotalPage(result.totalPages);
                }
    
                if(page===0) {
                    setIsFirst(true);
                }else if(page>0) {
                    setIsFirst(false);
                }
    
                if(page===totalPage-1) {
                    setIsLast(true);
                }else {
                    setIsLast(false);
                }
            });
        }
    }
 

    // 렌더링 되자마자 할 일 => 문의사항 api GET 목록 호출
    useEffect(() => {
        fetch(API_BASE_URL)
        .then(res => {
            if (res.status === 403) {
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
        .then(result => {
            if (!!result) {
                setQuestions(result.content);
                setTotalPage(result.totalPages);
            }
        });
    }, [API_BASE_URL]);

    // 제목 클릭 시 url 변경
    const navigate = useNavigate();
    const onTitleClick = (id) => {
        const path = `/question/${id}`;
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
        if (document.getElementById('notice_select_dropdown_button').innerText === '선택') {
            alert('검색 카테고리를 먼저 선택해주세요');
        }
        else if (searchData.boardTitle === '' && searchData.boardContent === '' && searchData.boardWriter === '') {
            alert('검색어를 입력해주세요');
        }
        else {
            setIsSearched(true);
            setIsFirst(true);
            setIsLast(false);
            setCurrentPage(0);

            fetch(`${API_BASE_URL}/search?page=0`, {
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
                    setQuestions(result.content);
                    setTotalPage(result.totalPages);
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

    var i = 0;
    return (
        <>
            <div id='question_btn_main'>
                <QuestionButton />
                <div id='question_table_main'>
                    <Table responsive id='question_table'>
                        <thead>
                            <tr id='question_main_thead'>
                                <th width="10%">번호</th>
                                <th width="20%">제목</th>
                                <th width="15%">날짜</th>
                                <th width="15%">작성자</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                questions.map((item) => {
                                    i++;
                                    return (
                                        <tr key={item.boardId} id='question_main_tbody'>
                                            <td>{item.boardId}</td>
                                            <th id='question_main_tbody_th' onClick={() => onTitleClick(item.boardId)}>{item.boardTitle}</th>
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
                    <Dropdown.Item eventKey="제목+내용" id='notice_selct_dropdown_item'>제목+내용</Dropdown.Item>
                </DropdownButton>
                <Form.Control onChange={searchChangeHandler} type='text' id='notice_select_dropdown_form' placeholder='검색' onKeyDown={onKeyPress}/>
                <Button onClick={handleSearch} id='notice_select_dropdown_search_button' className='btn_gray'>검색</Button>
            </div>
            <Pagination currentPage={currentPage} handleChangePage={handleChangePage} isFirst={isFirst} isLast={isLast} totalPage={totalPage} />
        </>
    )
}

export default QuestionMain;