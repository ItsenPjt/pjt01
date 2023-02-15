import React, { useState } from 'react';

import './css/CommentRadioBtn.css';

const CommentRadioBtn = ( { commentStatus, updateBeforeComment } ) => {      // commentStatus : 부모(NoticeInsert.js)가 보낸 commentChangeHandler 함수
        
    var defaultComment = '';

    // NoticeUpdate.js 에서 넘어온 updateBeforeComment 값이 ON이나 OFF이면 (수정하기) 상태
    if (updateBeforeComment === 'ON' || updateBeforeComment === 'OFF') {
        defaultComment = updateBeforeComment;       // defaultComment 값을 updateBeforeComment으로
    } else {
        defaultComment = 'ON'   // (등록하기) 상태라면 댓글 상태의 default 값은 ON
    }

    const [ radioStatus, setRadioStatus ] = useState(defaultComment);     // default : ON

    const handleClickRadioButton = (radioBtnName) => {
        commentStatus(radioBtnName);        // 부모에게 radio 값 넘김
        setRadioStatus(radioBtnName);
    };

    return (
        <div id='comment_radio_main'>
            <label id='comment_radio_label'>
                <span id='comment_radio_span' htmlFor='ON'>댓글 허용</span>
                <input 
                    className='comment_radio'
                    type='radio' 
                    id='ON' 
                    checked={radioStatus === 'ON'} 
                    onChange={() => handleClickRadioButton('ON')} 
                />
            </label>
            <label id='comment_radio_label'>
                <span id='comment_radio_span' htmlFor='OFF'>댓글 비허용</span>
                <input 
                    className='comment_radio'
                    type='radio' 
                    id='OFF' 
                    checked={radioStatus === 'OFF'} 
                    onChange={() => handleClickRadioButton('OFF')} 
                />
            </label>
        </div>
    )
}

export default CommentRadioBtn;
