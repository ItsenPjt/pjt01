// // 로그인 유저의 토큰을 반환하는 함수
// export const getToken = () => {
//     return localStorage.getItem('ACCESS_TOKEN');
// };

// // 로그인 유저의 아이디를 반환하는 함수
// export const getUserId = () => {
//     return localStorage.getItem('LOGIN_USERID');
// };

// // 로그인 유저의 이름을 반환하는 함수
// export const getUsername = () => {
//     return localStorage.getItem('LOGIN_USERNAME');
// };

// // 로그인 유저의 이메일을 반환하는 함수
// export const getUserEmail = () => {
//     return localStorage.getItem('LOGIN_USEREMAIL');
// };

// // 로그인 유저의 권한을 반환하는 함수
// export const getUserRole = () => {
//     return localStorage.getItem('LOGIN_USERROLE');
// };

// // 로그인 상태인지 검증해주는 함수
// export const isLogin = () => {
//     return !!getUsername();      // getUsername() !== null
//                                  // null이면 login 안한것, null이 아니면 login 한것
// };


// ---------------------------- //

// 웹 브라우저 종료 시 토큰을 삭제하기 위해 sessionStorage 사용 //

// 로그인 유저의 토큰을 반환하는 함수
export const getToken = () => {
    return sessionStorage.getItem('ACCESS_TOKEN');
};

// 로그인 유저의 아이디를 반환하는 함수
export const getUserId = () => {
    return sessionStorage.getItem('LOGIN_USERID');
};

// 로그인 유저의 이름을 반환하는 함수
export const getUsername = () => {
    return sessionStorage.getItem('LOGIN_USERNAME');
};

// 로그인 유저의 이메일을 반환하는 함수
export const getUserEmail = () => {
    return sessionStorage.getItem('LOGIN_USEREMAIL');
};

// 로그인 유저의 권한을 반환하는 함수
export const getUserRole = () => {
    return sessionStorage.getItem('LOGIN_USERROLE');
};

// 로그인 상태인지 검증해주는 함수
export const isLogin = () => {
    return !!getUsername();      // getUsername() !== null
                                 // null이면 login 안한것, null이 아니면 login 한것
};
