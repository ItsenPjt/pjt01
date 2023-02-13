import React from 'react';

import NoticeMain from './NoticeMain';
import PaginationComponent from '../common/PaginationComponent';

import './css//NoticeTemplate.css';

const NoticeTemplate = () => {

    return (
        <div>
            <NoticeMain />

            {/* 검색 컴포넌트 필요 */}
            
            <PaginationComponent />
        </div>
    )
}

export default NoticeTemplate;
