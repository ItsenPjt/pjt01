// 로그인 유저의 토큰을 반환하는 함수
export const getToken = () => {
    return localStorage.getItem('ACCESS_TOKEN');
};

// 로그인 유저의 이름을 반환하는 함수
export const getUsername = () => {
    return localStorage.getItem('LOGIN_USERNAME');
};

// 로그인 상태인지 검증해주는 함수
export const isLogin = () => {
    return !!getUsername();      // getUsername() !== null
                                 // null이면 login 안한것, null이 아니면 login 한것
};