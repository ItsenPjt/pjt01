import { Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import NoticeTemplate from './components/notice/NoticeTemplate';

function App() {
  return (
    <>
      <Header />

      <Routes>
        <Route path="/notice" element={<NoticeTemplate />} />
      </Routes>
    </>
  );
}

export default App;
