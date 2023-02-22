import React from 'react'

import './css/Footer.css';
const Footer = () => {
    return (
        <div id='footer_div'>
            <img className='cen_logo' id='footer_img' alt='footer_logo' src="/img/Black_logo_title.png"/>
            <div id='footer_txt'>
                서울특별시 서초구 반포대로 13 아이티센빌딩 &nbsp;&nbsp;/&nbsp;&nbsp; TEL : 02-580-6571 &nbsp;&nbsp;/&nbsp;&nbsp; Help Desk : 02-580-6539/6540 &nbsp;&nbsp;/&nbsp;&nbsp; E-mail : icm@goodcen.com<br />
                Copyright 2023 NEWCEN Company. &nbsp;&nbsp;All Rights Reserved.
            </div>
        </div>
    )
}

export default Footer