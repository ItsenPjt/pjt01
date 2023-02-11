import React, { useState, useEffect } from "react"; 
import { Route, Routes } from 'react-router-dom';

import Header from './components/common/Header';

import MainTemplate from './components/main/MainTemplate';
import UserJoin from './components/user/UserJoin';
import UserLogin from './components/user/UserLogin';
import UserFindPw from './components/user/UserFindPw';
import MyPage from './components/user/MyPage';

import NoticeTemplate from './components/notice/NoticeTemplate';
import NoticeInsert from './components/notice/NoticeInsert';
import NoticeContent from "./components/notice/NoticeContent";
import NoticeUpdate from "./components/notice/NoticeUpdate";

import QuestionTemplate from './components/question/QuestionTemplate';
import QuestionInsert from "./components/question/QuestionInsert";
import QuestionContent from "./components/question/QuestionContent";
import QuestionUpdate from "./components/question/QuestionUpdate";

import FAQTemplate from './components/faq/FAQTemplate';

import MessageTemplate from './components/message/MessageTemplate';

function App() {

    // 로그인 상태 관리 
    const [isLogin, setIsLogin] = useState(false);
    
    useEffect(() => {

        /*
        if (localStorage.getItem("ACCESS_TOKEN") === null || localStorage.getItem("LOGIN_USERNAME") === null) {
            // localStorage 에 ACCESS_TOKEN이나 LOGIN_USERNAME의 key 값으로 저장된 값이 없다면
            setIsLogin(false);
        } else {
            // localStorage 에 ACCESS_TOKEN이나 LOGIN_USERNAME의 key 값으로 저장된 값이 있다면
            setIsLogin(true);   // 로그인 상태 변경
        }
        */

        // 로그인 기능 구현 전까지만 아래 코드로 사용
        setIsLogin(true);
    }, []);

    return (
        <>
            {
                isLogin ?
                (
                    <>
                        <Header />
                
                        <Routes>
                            <Route path="/" element={<MainTemplate />} />
                            
                            <Route path="/mypage" element={<MyPage />} />
                    
                            <Route path="/notice" element={<NoticeTemplate />} />
                            <Route path="/notice/insert" element={<NoticeInsert />} />
                            <Route path="/notice/:noticeId" element={<NoticeContent />}/>
                            <Route path="/notice/update/:noticeId" element={<NoticeUpdate />}/>
                    
                            <Route path="/question" element={<QuestionTemplate />} />
                            <Route path="/question/insert" element={<QuestionInsert />} />
                            <Route path="/question/:questionId" element={<QuestionContent />}/>
                            <Route path="/question/update/:questionId" element={<QuestionUpdate />}/>

                            <Route path="/faq" element={<FAQTemplate />} />
                            
                            <Route path="/message" element={<MessageTemplate />} />
                        </Routes>
                    </>
                )
                :
                (
                    <>
                    <Routes>
                        <Route path="/" element={<UserLogin />} />
                        <Route path="/join" element={<UserJoin />} />
                        <Route path="/findpw" element={<UserFindPw />} />
                    </Routes>
                    </>
                )  
            }
        </>
    );
}

export default App;
