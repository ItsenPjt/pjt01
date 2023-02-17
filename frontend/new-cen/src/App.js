import React, { useState, useEffect } from "react"; 

import AfterLoginRouter from "./components/route/AfterLoginRouter";
import BeforeLoginRouter from "./components/route/BeforeLoginRouter";

function App() {

    // 로그인 상태 관리 
    const [isLogin, setIsLogin] = useState(false);
    
    useEffect(() => {

        if (sessionStorage.getItem("ACCESS_TOKEN") === null || sessionStorage.getItem("LOGIN_USERNAME") === null) {
            // sessionStorage 에 ACCESS_TOKEN이나 LOGIN_USERNAME의 key 값으로 저장된 값이 없다면
            setIsLogin(false);
        } else {
            // sessionStorage 에 ACCESS_TOKEN이나 LOGIN_USERNAME의 key 값으로 저장된 값이 있다면
            setIsLogin(true);   // 로그인 상태 변경
        }
    }, []);

    return (
        <>
            {
                isLogin ?
                (   // 로그인 후
                    <>
                        <AfterLoginRouter />
                    </>
                )
                :
                (   // 로그인 전 - insert, update 불가
                    <>
                        <BeforeLoginRouter />
                    </>
                )  
            }
        </>
    );
}

export default App;