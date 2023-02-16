import React from 'react';

import NoticeMain from './NoticeMain';
import PaginationComponent from '../common/PaginationComponent';

import './css//NoticeTemplate.css';

const NoticeTemplate = () => {

    return (
        <div>
            <NoticeMain />

            <PaginationComponent />
        </div>
    )
}

export default NoticeTemplate;
