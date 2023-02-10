import React, { useState}  from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import Editor from '../common/EditorComponent';


import '../css/NoticeInsert.css';
import NoticeCommentRadioBtn from './NoticeCommentRadioBtn';

const NoticeInsert = () => {

    const [desc, setDesc] = useState('');
    function onEditorChange(value) {
        setDesc(value);
    };

    return (
        <div className="insert_div">

            <div className="insert_justify">
                <Form>
                    <Form.Group className="mb-3">
                        <Form.Control className="insert_title" type="text" placeholder="공지사항 제목 입력" />
                    </Form.Group>
                </Form>
                <NoticeCommentRadioBtn />    
            </div>

            <div>
                <Editor value={desc} onChange={onEditorChange} />
            </div>

            <div className="insert_btn">
                <Button className="notice_btn_gray" id="btn_size">취소</Button>
                <Button className="notice_btn_orange" id="btn_size">등록</Button>
            </div>
        </div>
    )
}

export default NoticeInsert;