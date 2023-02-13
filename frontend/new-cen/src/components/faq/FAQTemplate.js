import React from 'react';

import FAQMain from './FAQMain';
import PaginationComponent from '../common/PaginationComponent';

const FAQTemplate = () => {
    return (
        <div>
            <FAQMain />

            {/* 검색 컴포넌트 필요 */}
            
            <PaginationComponent />
        </div>
    )
}

export default FAQTemplate;