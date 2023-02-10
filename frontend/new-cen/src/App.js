import { Route, Routes } from 'react-router-dom';

import Header from './components/common/Header';

import MainTemplate from './components/main/MainTemplate';
import UserJoin from './components/user/UserJoin';
import UserLogin from './components/user/UserLogin';
import UserFindPw from './components/user/UserFindPw';
import MyPage from './components/user/MyPage';

import NoticeInsert from './components/notice/NoticeInsert';
import NoticeTemplate from './components/notice/NoticeTemplate';
import QuestionTemplate from './components/question/QuestionTemplate';
import MessageTemplate from './components/message/MessageTemplate';

function App() {
  return (
    <>
      <Header />

      <Routes>
        <Route path="/" element={<MainTemplate />} />
        <Route path="/login" element={<UserLogin />} />
        <Route path="/join" element={<UserJoin />} />
        <Route path="/findpw" element={<UserFindPw />} />
        <Route path="/mypage" element={<MyPage />} />

        <Route path="/notice" element={<NoticeTemplate />} />
        <Route path="/notice/insert" element={<NoticeInsert />} />

        <Route path="/question" element={<QuestionTemplate />} />

        <Route path="/message" element={<MessageTemplate />} />
      </Routes>
    </>
  );
}

export default App;
