import React, { useState, useEffect } from "react"; 
import { Route, Routes } from 'react-router-dom';

import Header from './components/common/Header';

import MainTemplate from './components/main/MainTemplate';
import UserJoin from './components/user/UserJoin';
import UserSignUp from './components/user/UserSignUp';
import UserSignSet from './components/user/UserSignSet';
import UserFindInfo from './components/user/UserFindInfo';

import NoticeTemplate from './components/notice/NoticeTemplate';
import NoticeInsert from './components/notice/NoticeInsert';
import NoticeContent from "./components/notice/NoticeContent";
import NoticeUpdate from "./components/notice/NoticeUpdate";

import QuestionTemplate from './components/question/QuestionTemplate';
import QuestionInsert from "./components/question/QuestionInsert";
import QuestionContent from "./components/question/QuestionContent";
import QuestionUpdate from "./components/question/QuestionUpdate";

import FAQTemplate from './components/faq/FAQTemplate';
import FAQInsert from "./components/faq/FAQInsert";
import FAQContent from "./components/faq/FAQContent";
import FAQUpdate from "./components/faq/FAQUpdate";

import MessageTemplate from './components/message/MessageTemplate';

import AdminUserList from "./components/admin/AdminUserList";
import AdminValidList from "./components/admin/AdminValidList";

import NotFound from "./components/common/NotFound";

function App() {

    // 로그인 상태 관리 
    const [isLogin, setIsLogin] = useState(false);
    
    useEffect(() => {

        if (localStorage.getItem("ACCESS_TOKEN") === null || localStorage.getItem("LOGIN_USERNAME") === null) {
            // localStorage 에 ACCESS_TOKEN이나 LOGIN_USERNAME의 key 값으로 저장된 값이 없다면
            setIsLogin(false);
        } else {
            // localStorage 에 ACCESS_TOKEN이나 LOGIN_USERNAME의 key 값으로 저장된 값이 있다면
            setIsLogin(true);   // 로그인 상태 변경
        }

    }, []);

    var myPath = '';
    if (isLogin) {      // 로그인 후
        if (myPath === '/signset') {  // 주소가 signset 이면 --> <Header /> 없어야 함
            return (
                <>
                    <Routes>
                        <Route path={myPath} element={<UserSignSet />} />
                    </Routes>
                </>
            )
        } 
        else {
            return (
                <>
                    <Header />
                
                    <Routes>
                        <Route path="/" element={<MainTemplate />} />
                        
                        <Route path="/join" element={<UserJoin />} />
                        <Route path="/signup" element={<UserSignUp />} />
                        <Route path="/findinfo" element={<UserFindInfo />} />

                        <Route path="/notice" element={<NoticeTemplate />} />
                        <Route path="/notice/insert" element={<NoticeInsert />} />
                        <Route path="/notice/:noticeId" element={<NoticeContent />}/>
                        <Route path="/notice/update/:noticeId" element={<NoticeUpdate />}/>
                
                        <Route path="/question" element={<QuestionTemplate />} />
                        <Route path="/question/insert" element={<QuestionInsert />} />
                        <Route path="/question/:questionId" element={<QuestionContent />}/>
                        <Route path="/question/update/:questionId" element={<QuestionUpdate />}/>

                        <Route path="/faq" element={<FAQTemplate />} />
                        <Route path="/faq/insert" element={<FAQInsert />} />
                        <Route path="/faq/:faqId" element={<FAQContent />}/>
                        <Route path="/faq/update/:faqId" element={<FAQUpdate />}/>

                        <Route path="/userlist" element={<AdminUserList />} />
                        <Route path="/validlist" element={<AdminValidList />} />

                        <Route path="/message" element={<MessageTemplate />} />
                        <Route path={"*"} element={<NotFound />}/>
                    </Routes>
                </>
            )
        }

    }



    // return (
    //     <>
    //         {
    //             isLogin ?
    //             (   // 로그인 후
    //                 <>
    //                     <Header />
                
    //                     <Routes>
    //                         <Route path="/" element={<MainTemplate />} />
                            
    //                         <Route path="/join" element={<UserJoin />} />
    //                         <Route path="/signup" element={<UserSignUp />} />
    //                         <Route path="/findinfo" element={<UserFindInfo />} />

    //                         <Route path="/notice" element={<NoticeTemplate />} />
    //                         <Route path="/notice/insert" element={<NoticeInsert />} />
    //                         <Route path="/notice/:noticeId" element={<NoticeContent />}/>
    //                         <Route path="/notice/update/:noticeId" element={<NoticeUpdate />}/>
                    
    //                         <Route path="/question" element={<QuestionTemplate />} />
    //                         <Route path="/question/insert" element={<QuestionInsert />} />
    //                         <Route path="/question/:questionId" element={<QuestionContent />}/>
    //                         <Route path="/question/update/:questionId" element={<QuestionUpdate />}/>

    //                         <Route path="/faq" element={<FAQTemplate />} />
    //                         <Route path="/faq/insert" element={<FAQInsert />} />
    //                         <Route path="/faq/:faqId" element={<FAQContent />}/>
    //                         <Route path="/faq/update/:faqId" element={<FAQUpdate />}/>

    //                         <Route path="/admin" element={<AdminTemplate />} />
                                // <Route path="/userlist" element={<AdminUserList />} />
                                // <Route path="/validlist" element={<AdminValidList />} />
    //                         <Route path="/signset" element={<UserSignSet />} />

    //                         <Route path="/message" element={<MessageTemplate />} />
    //                         <Route path={"*"} element={<NotFound />}/>
    //                     </Routes>
    //                 </>
    //             )
    //             :
    //             (   // 로그인 전 - insert, update 불가
    //                 <>
    //                 <Header />

    //                 <Routes>
    //                     <Route path="/" element={<MainTemplate />} />

    //                     <Route path="/join" element={<UserJoin />} />
    //                     <Route path="/signup" element={<UserSignUp />} />
    //                     <Route path="/findinfo" element={<UserFindInfo />} />

    //                     <Route path="/notice" element={<NoticeTemplate />} />
    //                     <Route path="/notice/:noticeId" element={<NoticeContent />}/>
                
    //                     <Route path="/question" element={<QuestionTemplate />} />
    //                     <Route path="/question/:questionId" element={<QuestionContent />}/>

    //                     <Route path="/faq" element={<FAQTemplate />} />
    //                     <Route path="/faq/:faqId" element={<FAQContent />}/>

    //                     <Route path="/admin" element={<AdminTemplate />} />

    //                     <Route path="/message" element={<MessageTemplate />} />
    //                     <Route path={"*"} element={<NotFound />}/>
    //                 </Routes>
    //                 </>
    //             )  
    //         }
    //     </>
    // );
}

export default App;
