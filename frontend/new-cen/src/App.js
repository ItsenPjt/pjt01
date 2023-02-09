import { Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import NoticeInsert from './components/notice/NoticeInsert';
import NoticeTemplate from './components/notice/NoticeTemplate';

function App() {
  return (
    <>
      <Header />

      <Routes>
        <Route path="/notice" element={<NoticeTemplate />} />
        <Route path="/notice/insert" element={<NoticeInsert />} />
      </Routes>
    </>
  );
}

export default App;
