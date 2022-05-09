import React, { useState, useEffect, useRef } from 'react';
import clsx from 'clsx'
import { makeStyles } from '@mui/styles';
import { Link } from 'react-router-dom';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';

const useStyles = makeStyles((theme) => ({
    root: {
        display: "flex",
    },
}));


const SignUp = () => {
    const classes = useStyles();
    let [loading, setLoading] = useState(false);   
    const [userInfo, setUserInfo] = useState({
        memberLoginid: '',
        memberPassword: '',
        memberName: '',
    });
    const [validCheck, setValidCheck] = useState(false);  

    const { memberLoginid, memberPassword, memberName } = userInfo;

    const isValidInput = memberName.length >= 1 && memberPassword.length >= 1 && memberLoginid.length >= 1;
    const isValidLoginid = memberLoginid.length >= 8;
    const specialCharacter = memberPassword.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
    const isValidPassword = memberPassword.length >= 6 && specialCharacter >= 1;

    const handleChange = (e) => {
        const { value, name } = e.target;
        setUserInfo({
            ...userInfo,
            [name]: value
        });
    };

    const handleValidButton = () => {
        if ( !isValidInput ) {
            alert('회원정보를 전부 입력해주세요');
        } else if ( !isValidPassword ) {
          alert('비밀번호 형식을 맞춰주세요.');
        } else if( !isValidLoginid ){
            alert('아이디 길이를 맞춰주세요.');
        } 
    };

    return (
        <div>
            {/* <Loader loading={loading} /> */}
            <Grid item xs={12} style={{ margin: '30px 0' }}>
                <div className="subtitle_5">환영합니다!</div>
                <div className="info2">회원가입을 위해 해당 정보를 기입해주세요.</div>
            </Grid>
            <form className={classes.root} noValidate autoComplete="off">
                <Grid container>
                    <Grid item xs={12} style={{ justifyContent: 'center' }}>
                        <div style={{display:"flex", justifyContent:"space-between", margin:"20px 0"}}>
                            <div className='form_name'>이름</div>
                            <div>
                                <input type="text" placeholder="이름을 입력하세요" className='form_txt_1' value={memberName} name="memberName" onChange={handleChange} />
                            </div>
                        </div>
                        <div style={{display:"flex", justifyContent:"space-between", margin:"20px 0"}}>
                            <div className='form_name'>아이디</div>
                            <div>
                                <input type="text" placeholder="8자 이상의 아이디를 입력하세요" className='form_txt_1' value={memberLoginid} name="memberLoginid"  onChange={handleChange}/>
                            </div>
                        </div>
                        <div>
                            {(validCheck && !isValidLoginid) && <p>아이디 길이가 형식에 맞지 않습니다.</p>}
                        </div>
                        <div style={{display:"flex", justifyContent:"space-between", margin:"20px 0"}}>
                            <div className='form_name'>비밀번호</div>
                            <div>
                                <input type="password" placeholder="영문/숫자/특수문자 포함 6자 이상 비밀번호를 입력하세요" className='form_txt_1' value={memberPassword} name="memberPassword" onChange={handleChange}/>
                            </div>
                        </div>
                    </Grid>

                    <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                        <button className={clsx('btn_1', 'margin_30')} onClick={() => {setValidCheck(true);handleValidButton();}}>
                            완료
                        </button>
                    </Grid>
                </Grid>
            </form>
        </div>
    );
}

export default SignUp;
