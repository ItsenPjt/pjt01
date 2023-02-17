import React from 'react';

import './css/Pagination.css';

const Pagination = ({currentPage, totalPage, handleChangePage, isFirst, isLast}) => {

    const pageNumbers = [];

    let startPage = 0;
    let endPage = 0;

    startPage = parseInt(currentPage/5)*5;

    if(totalPage < 5) {
        startPage = 0;
        endPage = totalPage-1;
    }else if(currentPage < 5) {
        startPage = 0;
        endPage = 4;
    }else if(startPage+4<totalPage) {
        endPage = startPage+4;
    }else if(startPage+4>=totalPage) {
        endPage = totalPage-1;
    }

    for(let i=startPage; i<=endPage; i++) {
        pageNumbers.push(i);    
    }

    return (
        <div className='message_pagination'>
            {
                isFirst
                ?
                <>
                    <span id='first-page-disabled'>처음</span>
                    <span id='prev-page-disabled'>이전</span>
                </>
                :
                <>
                    <span id='first-page' onClick={() => handleChangePage(0)}>처음</span>
                    {
                        currentPage<5
                        ?
                        <span id='prev-page-disabled'>이전</span>
                        :
                        <span id='prev-page' onClick={() => handleChangePage(startPage-5)}>이전</span>
                    }
                </>
            }

            {
                pageNumbers.map((page) => {
                    return  (
                        (
                            page===currentPage
                            ?
                            <span key={page} id='selected-page' onClick={() => handleChangePage(page)}>{page+1}</span>
                            :
                            <span key={page} id='unselected-page' onClick={() => handleChangePage(page)}>{page+1}</span>
                        )
                    )         
                })
            }
            
            {
                isLast
                ?
                <>
                    <span id='next-page-disabled'>다음</span>
                    <span id='last-page-disabled'>마지막</span>
                </>
                :
                <>
                    {
                        startPage+5<totalPage
                        ?
                        <>
                            <span id='next-page' onClick={() => handleChangePage(startPage+5)}>다음</span>
                        </>
                        :
                        <>
                            <span id='next-page-disabled'>다음</span>
                        </>
                    } 
                    <span id='last-page' onClick={() => handleChangePage(totalPage-1)}>마지막</span>
                </>
            }
            
        </div>
    )
}

export default Pagination