import { Route, Routes } from 'react-router-dom';
import Header from './components/Header';

import MainTemplate from './components/main/MainTemplate';
import NoticeInsert from './components/notice/NoticeInsert';
import NoticeTemplate from './components/notice/NoticeTemplate';
import QuestionTemplate from './components/question/QuestionTemplate';

function App() {
  return (
    <>
      <Header />

      <Routes>
        <Route path="/" element={<MainTemplate />} />

        <Route path="/notice" element={<NoticeTemplate />} />
        <Route path="/notice/insert" element={<NoticeInsert />} />

        <Route path="/question" element={<QuestionTemplate />} />
      </Routes>
    </>
  );
}

export default App;
