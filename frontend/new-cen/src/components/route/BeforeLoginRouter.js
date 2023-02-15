import React from 'react'
import { Route, Routes } from 'react-router-dom'

import Header from '../common/Header'
import FAQContent from '../faq/FAQContent'
import FAQTemplate from '../faq/FAQTemplate'
import MainTemplate from '../main/MainTemplate'
import MessageTemplate from '../message/MessageTemplate'
import NoticeContent from '../notice/NoticeContent'
import NoticeTemplate from '../notice/NoticeTemplate'
import QuestionContent from '../question/QuestionContent'
import QuestionTemplate from '../question/QuestionTemplate'
import UserFindInfo from '../user/UserFindInfo'
import UserJoin from '../user/UserJoin'
import UserSignUp from '../user/UserSignUp'



const BeforeLoginRouter = () => {
  return (

    // 로그인 전 - insert, update 불가

    <>
        <Header />
        <Routes>  
            <Route path="/" element={<MainTemplate />} />

            <Route path="/join" element={<UserJoin />} />
            <Route path="/signup" element={<UserSignUp />} />
            <Route path="/findinfo" element={<UserFindInfo />} />

            <Route path="/notice" element={<NoticeTemplate />} />
            <Route path="/notice/:noticeId" element={<NoticeContent />}/>
    
            <Route path="/question" element={<QuestionTemplate />} />
            <Route path="/question/:questionId" element={<QuestionContent />}/>

            <Route path="/faq" element={<FAQTemplate />} />
            <Route path="/faq/:faqId" element={<FAQContent />}/>

            {/* <Route path="/admin" element={<AdminTemplate />} /> */}

            <Route path="/message" element={<MessageTemplate />} />
        </Routes>
    </>
  )
}

export default BeforeLoginRouter