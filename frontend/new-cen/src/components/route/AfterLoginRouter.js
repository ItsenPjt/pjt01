import React from 'react'
import { Route, Routes } from 'react-router-dom'
import AdminUserList from '../admin/AdminUserList'
import AdminValidList from '../admin/AdminValidList'
import Header from '../common/Header'
import FAQContent from '../faq/FAQContent'
import FAQInsert from '../faq/FAQInsert'
import FAQTemplate from '../faq/FAQTemplate'
import FAQUpdate from '../faq/FAQUpdate'
import MainTemplate from '../main/MainTemplate'
import MessageTemplate from '../message/MessageTemplate'
import NoticeContent from '../notice/NoticeContent'
import NoticeInsert from '../notice/NoticeInsert'
import NoticeTemplate from '../notice/NoticeTemplate'
import NoticeUpdate from '../notice/NoticeUpdate'
import QuestionContent from '../question/QuestionContent'
import QuestionInsert from '../question/QuestionInsert'
import QuestionTemplate from '../question/QuestionTemplate'
import QuestionUpdate from '../question/QuestionUpdate'
import UserFindInfo from '../user/UserFindInfo'
import UserJoin from '../user/UserJoin'
import UserSignSet from '../user/UserSignSet'
import UserSignUp from '../user/UserSignUp'


const AfterLoginRouter = () => {
  return (

    // 로그인 후

    <>
        <Header />
        <Routes>
            <Route path="/" element={<MainTemplate />} />
            <Route path="/notice" element={<NoticeTemplate />} />
            <Route path="/notice/insert" element={<NoticeInsert />} />
            <Route path="/notice/:noticeId" element={<NoticeContent />}/>
            <Route path="/notice/update/:noticeId" element={<NoticeUpdate />}/>
    
            <Route path="/join" element={<UserJoin />} />
            <Route path="/signup" element={<UserSignUp />} />
            <Route path="/findinfo" element={<UserFindInfo />} />
            <Route path="/signset" element={<UserSignSet />} />

            <Route path="/question" element={<QuestionTemplate />} />
            <Route path="/question/insert" element={<QuestionInsert />} />
            <Route path="/question/:questionId" element={<QuestionContent />}/>
            <Route path="/question/update/:questionId" element={<QuestionUpdate />}/>

            <Route path="/faq" element={<FAQTemplate />} />
            <Route path="/faq/insert" element={<FAQInsert />} />
            <Route path="/faq/:faqId" element={<FAQContent />}/>
            <Route path="/faq/update/:faqId" element={<FAQUpdate />}/>

            {/* <Route path="/admin" element={<AdminTemplate />} /> */}
            <Route path="/userlist" element={<AdminUserList />} />
            <Route path="/validlist" element={<AdminValidList />} />
            

            <Route path="/message" element={<MessageTemplate />} />
        </Routes>
    </>
  )
}

export default AfterLoginRouter