import React, { useState, useEffect, useRef } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios'
import clsx from 'clsx'
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Loader from "./../common/Loader"
import Modal from "./../common/Modal"

const SignUp = () => {
    let [loading, setLoading] = useState(false);   
    const [userInfo, setUserInfo] = useState({
        memberLoginId: '',
        memberPassword: '',
        memberName: '',
    });

    const [accessCheck, setAccessCheck] = useState(false);
    const [open, setOpen] = useState(false);   
    const [notice, setNotice] = useState();           //모달 멘트 설정

    const { memberLoginId, memberPassword, memberName } = userInfo;

    // 유효성 체크
    const isValidName = memberName.length >= 1;
    const isValidLoginid = memberLoginId.length >= 8;
    const specialCharacter = memberPassword.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
    const isValidPassword = memberPassword.length >= 6 && specialCharacter >= 1;

    const API_SERVER = "https://gateway.bitbank.click";

    const handleChange = (e) => {
        const { value, name } = e.target;
        setUserInfo({
            ...userInfo,
            [name]: value
        });
    };

    // 유효성 체크
    const handleValid = (e) => {
        e.preventDefault();
        if ( !isValidName ) {
            setNotice("이름을 입력하세요.")
            setOpen(true);
        } else if( !isValidLoginid ){
            setNotice(`입력하신 아이디가 형식에 맞지 않습니다.\n8자 이상의 아이디를 입력하세요.`)
            setOpen(true);
        } else if ( !isValidPassword ) {
            setNotice(`입력하신 비밀번호가 형식에 맞지 않습니다.\n6자 이상의 영문/숫자/특수문자를 사용하세요.`)
            setOpen(true);
        } else {
            registerMember(userInfo)
        }
    };


    // 회원가입
    const registerMember = async(userInfo) => {  
        setLoading(true);
        try {
            const response = await axios.post( API_SERVER +'/auth/signup', {
                memberName: userInfo.memberName,
                memberLoginId: userInfo.memberLoginId,
                memberPassword: userInfo.memberPassword,
            })
            if( response.status === 200 && response.data.rt === 201 ){   
                setAccessCheck(true);
            } else {   
                setNotice(response.data.rtMsg)
                setOpen(true);
            }
        } catch (e) {
            console.log( 'e', e.response );
        }
        setLoading(false)
    }

    const handleClose = (value) => {
        setOpen(value);
    }

    return (
        <div>
            <Grid item xs={12} style={{ margin: '30px 0' }}>
                <div className="subtitle_5">환영합니다!</div>
                <div className="info2">회원가입을 위해 해당 정보를 기입해주세요.</div>
            </Grid>
            <form className="flex" noValidate autoComplete="off">
                <Grid container>
                    <Grid item xs={12} style={{ justifyContent: 'center' }}>
                        <div className={clsx('between', 'margin_30')}>
                            <div className='form_name'>이름</div>
                            <div>
                                <input type="text" placeholder="이름을 입력하세요" className='form_txt_1' value={memberName} name="memberName" onChange={handleChange} />
                            </div>
                        </div>
                        <div className={clsx('between', 'margin_30')}>
                            <div className='form_name'>아이디</div>
                            <div>
                                <input type="text" placeholder="8자 이상의 아이디를 입력하세요" className='form_txt_1' value={memberLoginId} name="memberLoginId"  onChange={handleChange}/>
                            </div>
                        </div>
                        <div className={clsx('between', 'margin_30')}>
                            <div className='form_name'>비밀번호</div>
                            <div>
                                <input type="password" placeholder="영문/숫자/특수문자 포함 6자 이상 비밀번호를 입력하세요" className='form_txt_1' value={memberPassword} name="memberPassword" onChange={handleChange}/>
                            </div>
                        </div>
                    </Grid>

                    <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                        <button className={clsx('btn_1', 'margin_30')} onClick={handleValid}>
                            완료
                        </button>
                    </Grid>          
                    <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                          <Loader loading={loading} />
                    </Grid>  
                </Grid>
            </form>
            {accessCheck && (
                <div className="container">
                <div className="popup-wrap" > 
                    <div className="popup">	
                        <div className="popup-body">
                            <div className="body-content">
                                <div className="body-titlebox">
                                    <CheckCircleOutlineIcon style={{fontSize: '47px'}}/>
                                </div>
                                <div className="body-contentbox">
                                    회원가입이 완료되었습니다
                                </div>
                            </div>
                        </div>
                        <div className="popup-footer">
                            <Link to='/login'>
                                <Box className="pop-btn">확인</Box>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
            )}
            {open && (
                <Modal notice={notice} onClose={handleClose}/>
            )}
        </div>
    );
}

export default SignUp;
