import React, { useState, useEffect, useRef } from 'react';
import { makeStyles } from '@mui/styles';
import clsx from 'clsx'
import { Link } from 'react-router-dom';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';


const useStyles = makeStyles((theme) => ({
    root: {
        display: "flex",
    },
}));


const Login = () => {
    const classes = useStyles();
    let [loading, setLoading] = useState(false);   
    const [userInfo, setUserInfo] = useState({
        memberLoginid: '',
        memberPassword: '',
    });

    const { memberLoginid, memberPassword } = userInfo;

    const handleChange = (e) => {
        const { value, name } = e.target;
        setUserInfo({
            ...userInfo,
            [name]: value
        });
    };

    const onKeyPress = (e) => {
        if (e.key == 'Enter') {
            handleClick();
        }
    }

    // const getLogin = async (userInfo) => {
    //     try {
    //         setLoading(true);
    //         const response = await axios.post(API_SERVER + '/auth/signup', {
    //             memberLoginid: userInfo.memberLoginid,
    //             memberPassword: userInfo.memberPassword,
    //         })
    //         console.log('response', response)
    //         if (response.status === 200 && response.data.access_token != null && response.data.user != null) {
    //             console.log("유저", JSON.stringify(response.data));
    //             // updateUserInfo(dispatch, response.data.access_token, response.data.user)

    //             Swal.fire({
    //                 text: "로그인 되었습니다.",
    //                 icon: "success",
    //                 showConfirmButton: false,
    //                 timer: 1200,
    //             }).then(() => {
    //                 if (location.state && location.state.referer) document.location.href = location.state.referer
    //                 else document.location.href = '/'
    //             });

    //         }
    //     } catch (error) {
    //         console.log('error', error)
    //             // Swal.fire({
    //             //     title: "로그인에 실패하였습니다",
    //             //     html: "아이디 또는 비밀번호가 잘못 입력 되었습니다.<br>아이디와 비밀번호를 정확히 입력해주세요.",
    //             //     icon: "error",
    //             //     confirmButtonText: "확인",
    //             //     buttons: {
    //             //         text: "확인",
    //             //     },
    //             // });
    //     } 
    //     setLoading(false);
    // }

    const handleClick = () => {
        if (!memberLoginid) {
            // Swal.fire({
            //     text: "아이디를 입력해주세요",
            //     icon: "warning",
            //     confirmButtonText: "확인",
            //     buttons: {
            //         cancel: "확인",
            //     },
            // });
        }
        else if (!memberPassword) {
            // Swal.fire({
            //     text: "비밀번호를 입력해주세요",
            //     icon: "warning",
            //     confirmButtonText: "확인",
            //     buttons: {
            //         cancel: "확인",
            //     },
            // });
        }
        // else getLogin(userInfo);
    }

    return (
        <div>
            <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                <img src="./img/logo.png" width="23px" height="35px" style={{margin:"28px 0", paddingLeft:"30px"}}/>
                <div className='logo_subtitle'>ITBANK</div>
            </Grid>
            <form className={classes.root} noValidate autoComplete="off">
                <Grid container>
                    <Grid item xs={12} style={{ justifyContent: 'center' }}>
                        <div className="item_center">
                            <input type="text" placeholder="아이디를 입력하세요" className={clsx('margin_20','form_txt_login')} value={memberLoginid} name="memberLoginid" onChange={handleChange} />
                        </div>
                        <div className="item_center">
                            <input type="password" placeholder="비밀번호를 입력하세요" className={clsx('form_txt_login')} value={memberPassword} name="memberPassword" onChange={handleChange} />
                        </div>
                    </Grid>
                    <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                        <button className={clsx('btn_1', 'margin_30')}>
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
                        {/* <button className='signup_btn' onClick={handleClick}>
                            소셜
                        </button> */}
                    </Grid>
                </Grid>
            </form>
        </div>
    );
}

export default Login;
