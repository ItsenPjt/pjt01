import { Route, Routes } from 'react-router-dom';
import Header from './components/Header';

import MainTemplate from './components/main/MainTemplate';
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

        <Route path="/notice" element={<NoticeTemplate />} />
        <Route path="/notice/insert" element={<NoticeInsert />} />

        <Route path="/question" element={<QuestionTemplate />} />

        <Route path="/message" element={<MessageTemplate />} />
      </Routes>
    </>
  );
}

export default App;
