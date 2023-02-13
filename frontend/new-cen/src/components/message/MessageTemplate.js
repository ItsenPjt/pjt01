import React from 'react';

import MessageMain from './MessageMain';
import PaginationComponent from '../common/PaginationComponent';

const MessageTemplate = () => {
    return (
        <div>
            <MessageMain />

            {/* 검색 컴포넌트 필요 */}


            <PaginationComponent />
        </div>
    )
}

export default MessageTemplate;