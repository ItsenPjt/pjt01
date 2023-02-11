import React from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import './css/NoticeContent.css'

// 댓글 허용 - 댓글 작성 전
const NoticeYesCommentBefore = () => {
    
    // 공지사항 목록 페이지로
    const onNoticePage = () => {
        window.location.href = "/notice";
    }

    return (
        <>
            <div>
                <Form>
                    <Form.Group className='mb-3'>
                        <div className='justify' id='notice_content_comment_insert_div'>
                            <Form.Control id='notice_content_comment_insert' type='text' placeholder='댓글 입력' />
                            <input type="file" name="notice_content_comment_file" id="notice_content_comment_file" onChange={(e) => console.log(e.target.files[0])} />
                        </div>
                        
                    </Form.Group>
                </Form>
            </div>

            <div id='notice_content_footer_div'>
                <Button className='btn_gray btn_size_100' onClick={onNoticePage}>목록</Button>
                <Button className='btn_orange btn_size_100' id='notice_content_comment_insert_btn'>등록</Button>
            </div>
        </>
    )
}

export default NoticeYesCommentBefore;
