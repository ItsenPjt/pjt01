import React, { useState } from 'react';

import './css/CommentRadioBtn.css';

const CommentRadioBtn = (comment) => {
    
    // console.log(comment);

    const [ radioStatus, setRadioStatus ] = useState(comment);     // default : ON

    // console.log(radioStatus);

    const handleClickRadioButton = (radioBtnName) => {
        setRadioStatus(radioBtnName)
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
