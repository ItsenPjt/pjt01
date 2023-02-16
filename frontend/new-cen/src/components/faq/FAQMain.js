import React, {useEffect, useState} from 'react';

import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';

import Pagination from '../common/Pagination';

import FAQButton from './FAQButton';

import { BASE_URL, FAQ } from '../common/config/host-config';
import { useNavigate } from "react-router-dom";

import './css/FAQMain.css';


// 자주 묻는 질문 메인
const FAQMain = () => {

    // 자주 묻는 질문 번호
    let i = 0;

    const API_BASE_URL = BASE_URL + FAQ;

    // 자주 묻는 질문 리스트
    const [faqList, setFaqList] = useState([]);

    // 자주 묻는 질문 상세
    const navigate = useNavigate();
    const handleFaqContent = (boardId) => {
        const path = `/faq/${boardId}`;
        navigate(path);
    }


     // Pagination 
     const [currentPage, setCurrentPage] = useState(0);

     const [totalPage, setTotalPage] = useState(0);
 
     const [isFirst, setIsFirst] = useState(true);
 
     const [isLast, setIsLast] = useState(false);
 
     const handleChangePage = (page) => {
 
        setCurrentPage(page);
 
        fetch(`${API_BASE_URL}?page=${page}`)
        .then(res => {
            if (res.status === 500) {
                alert('서버가 불안정합니다');
                return;
            }
            return res.json();
        })
        .then(result => {
            if (!!result) {
                setFaqList(result.content);
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
  



    // 렌더링 되자마자 할 일 => FAQ api GET 목록 호출
    useEffect(() => {
        fetch(API_BASE_URL)
        .then(res => {
            if (res.status === 500) {
                alert('서버가 불안정합니다');
                return;
            }

            return res.json();
        })
        .then(res => {
            setFaqList(res.content);
            setTotalPage(res.totalPages);
        });
    }, [API_BASE_URL]);


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
            fetch(`${API_BASE_URL}/search`, {
                method: 'POST',
                headers: {
                    'content-type': 'application/json', 
                },
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
                    setFaqList(result.content);
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
            <div id='faq_btn_main'>
                <FAQButton />
                <div id='faq_table_main'>
                    <Table responsive id='faq_table'>
                        <thead>
                            <tr id='faq_main_thead'>
                                <th width="10%">번호</th>
                                <th width="20%">제목</th>
                                <th width="15%">날짜</th>
                                <th width="15%">작성자</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                            !faqList.empty &&  
                                faqList.map((item) => {
                                    i++;
                                    return (
                                        <tr key={item.boardId} id='faq_main_rows'>
                                            <td width="10%">{item.boardId}</td>
                                            <th id='question_main_tbody_th' onClick={() => handleFaqContent(item.boardId)}>{item.boardTitle}</th>
                                            <td width="15%">{item.boardCreatedate.substring(0, 10)}</td>
                                            <td width="15%">{item.boardWriter}</td>
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
                </DropdownButton>
                <Form.Control onChange={searchChangeHandler} type='text' id='notice_select_dropdown_form' placeholder='검색' onKeyDown={onKeyPress}/>
                <Button onClick={handleSearch} id='notice_select_dropdown_search_button' className='btn_gray'>검색</Button>
            </div>
            <Pagination currentPage={currentPage} handleChangePage={handleChangePage} isFirst={isFirst} isLast={isLast} totalPage={totalPage} />
        </>
    )
}

export default FAQMain;