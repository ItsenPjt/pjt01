import React, { useState } from 'react';

import './css/CommentRadioBtn.css';

const CommentRadioBtn = () => {
    
    const [ radioStatus, setRadioStatus ] = useState('ON');     // default : ON
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
