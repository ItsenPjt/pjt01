import React, {useState} from 'react';

import Pagination from "react-js-pagination";

import './css/PaginationComponent.css';

const PaginationComponent = () => {
    
    const [page, setPage] = useState(1);

    const handlePageChange = (page) => {
        setPage(page);
    };

    return (
        <div id='pagination_css'>
            <Pagination
                activePage={page}       // 현재 페이지
                itemsCountPerPage={10}  // 한 페이지랑 보여줄 아이템 갯수
                totalItemsCount={450}   // 총 아이템 갯수
                pageRangeDisplayed={5}  // paginator의 페이지 범위
                prevPageText={"이전"}   // 이전 페이지를 나타낼 텍스트
                nextPageText={"다음"}   // 다음 페이지를 나타낼 텍스트
                firstPageText={"처음"}  // 처음 페이지를 나타낼 텍스트
                lastPageText={"마지막"} // 마지막 페이지를 나타낼 텍스트
                itemClass={"pagination_li"}     // <li> 태그 클래스
                linkClass={"pagination_a"}       // <a> 태그 클래스
                onChange={handlePageChange} // 페이지 변경을 핸들링하는 함수
            />
        </div>
        
    )
}

export default PaginationComponent;
