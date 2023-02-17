
// 브라우저가 현재 클라이언트 호스트 이름 얻어오기 
const hostname = window.location.hostname;

let backendHost; // 백엔드 호스트 이름

if(hostname === 'localhost') {
    backendHost = 'http://localhost:8080';  // 로컬 테스트용
}else if(hostname === 'http://newcen.co.kr.s3-website.ap-northeast-2.amazonaws.com') {
    backendHost = 'http://54.180.7.97';     // 배포 테스트용 
}


export const BASE_URL = backendHost;
export const NOTICE = '/api/notices';
export const QUESTION = '/api/questions';
export const USER = '/api/user';
export const MESSAGE = '/api/messages';
export const ADMIN = '/api/admins';
export const FAQ = '/api/faqs';
export const AWS = '/api/aws';