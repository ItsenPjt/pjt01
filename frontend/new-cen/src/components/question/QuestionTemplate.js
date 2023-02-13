import React from 'react';
import PaginationComponent from '../common/PaginationComponent';
import QuestionMain from './QuestionMain';

const QuestionTemplate = () => {
    return (
        <div>
            <QuestionMain />

            {/* 검색 컴포넌트 필요 */}
            
            <PaginationComponent />
        </div>
    )
}

export default QuestionTemplate;
