import React, { useState, useEffect, useRef } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { observer, useObserver  } from 'mobx-react';
import KakaoLogin from "react-kakao-login";
import clsx from 'clsx'
import axios from 'axios';
import Grid from '@mui/material/Grid';
import logo from './../img/logo.png'
import Loader from "./../common/Loader"
import Modal from "./../common/Modal"
import {store}  from './../stores/Store';


const { Kakao } = window;

const Login = () => {
    const history = useHistory();
    let [loading, setLoading] = useState(false);   
    const [userInfo, setUserInfo] = useState({
        memberLoginId: '',
        memberPassword: '',
    });
    const [open, setOpen] = useState(false);   
    const [notice, setNotice] = useState();           //모달 멘트 설정

    const { memberLoginId, memberPassword } = userInfo;
    const API_SERVER = "https://gateway.bitbank.click" ;

    const handleChange = (e) => {
        const { value, name } = e.target;
        setUserInfo({
            ...userInfo,
            [name]: value
        });
    };

    const onKeyPress = (e) => {
        if (e.key == 'Enter') {
            handleValid();
        }
    }

    // 일반 로그인
    const getLogin = async ( userInfo ) => {
        setLoading(true);
        try {
            const response = await axios.post(API_SERVER + '/auth/login', {
                memberLoginId: userInfo.memberLoginId,
                memberPassword: userInfo.memberPassword,
            })
            if (response.status === 200 && response.data.rt === 200) {
                console.log("유저", response.data);
                store.setUserInfo(response.data);
                sessionStorage.setItem('access_token', response.data.accessToken);
                sessionStorage.setItem('refresh_token', response.data.refreshToken);
                sessionStorage.setItem('memberName', response.data.memberName);
                sessionStorage.setItem('memberType', response.data.memberType);
                sessionStorage.setItem('memberId',  response.data.memberId);
                document.location.href = '/';
            } else if( response.status === 200 && response.data.rt > 499 ) {
                    setNotice(`로그인에 실패했습니다.\n관리자에게 문의하시길 바랍니다.`)
                    setOpen(true);
            } else { 
                setNotice(response.data.rtMsg)
                setOpen(true);
            } 
        } catch (error) {
            console.log('error', error)
        } 
        setLoading(false);
    }

    // 유효성 검사
    const handleValid = (e) => {
        e.preventDefault();
        if (!memberLoginId) {
            setNotice("아이디를 입력하세요.")
            setOpen(true);
        } else if (!memberPassword) {
            setNotice("비밀번호를 입력하세요.")
            setOpen(true);
        } else {
            getLogin(userInfo);
        }
    }

    const handleClose = (value) => {
        setOpen(value);
    }

    const kakaoLoginClickHandler = (e) => {
        e.preventDefault();
        if (!Kakao.isInitialized()) {
            Kakao.init("38b3f0aff12245b4f33fdeb8829476c6");
        }
      
        Kakao.Auth.login({
            success: function (authObj) {
                fetch(API_SERVER + '/auth/login/social', {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        socialToken : authObj.access_token,
                        memberName : null,
                    }),
                })
                .then(res => res.json())
                .then(res => {
                    console.log("소셜 로그인 성공", res)
                    store.setUserInfo(res);
                    sessionStorage.setItem('access_token', res.accessToken);
                    sessionStorage.setItem('refresh_token', res.refreshToken);
                    sessionStorage.setItem('memberName', res.memberName);
                    sessionStorage.setItem('memberType', res.memberType);
                    sessionStorage.setItem('memberId',  res.memberId);
                    history.push("/")
                })
            },
            fail: function (err) {
                alert(JSON.stringify(err))
            }
        })
    }


    return useObserver(() => (
        <div>
            <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                <img src={logo} alt="B" width="23px" height="35px" style={{margin:"28px 0", paddingLeft:"30px"}}/>
                <div className='logo_subtitle'>ITBANK</div>
            </Grid>
            <form className="flex" noValidate autoComplete="off">
                <Grid container>
                    <Grid item xs={12} style={{ justifyContent: 'center' }}>
                        <div className="item_center">
                            <input type="text" placeholder="아이디를 입력하세요" className={clsx('margin_20','form_txt_login')} value={memberLoginId} name="memberLoginId" onChange={handleChange} />
                        </div>
                        <div className="item_center">
                            <input type="password" placeholder="비밀번호를 입력하세요" className={clsx('form_txt_login')} value={memberPassword} name="memberPassword" onChange={handleChange} />
                        </div>
                    </Grid>
                    <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                        <button className={clsx('btn_1', 'margin_30')} onClick={handleValid}>
                            로그인
                        </button>
                    </Grid>
                    <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                        <Link to='/signup'>
                            <button className='btn_2'>
                                회원가입
                            </button>
                        </Link>
                    </Grid>    
                    <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                        <button className={clsx('kakao_btn', 'margin_30')} onClick={kakaoLoginClickHandler}>
                            카카오 로그인
                        </button>
                    </Grid>         
                    <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                        <div className='margin_40_10'>
                            <Loader loading={loading} />
                        </div>
                    </Grid>

                </Grid>
            </form>
            {open && (
                <Modal notice={notice} onClose={handleClose}/>
            )}
        </div>
    ));
}

export default Login;
