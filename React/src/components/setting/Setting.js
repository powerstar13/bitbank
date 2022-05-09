import React, { useState, useEffect, useRef } from 'react';
import clsx from 'clsx'
import { makeStyles } from '@mui/styles';
import { Link, useHistory } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';

const useStyles = makeStyles((theme) => ({
}));


const Setting = () => {
    const classes = useStyles();
    let history = useHistory();
    let [loading, setLoading] = useState(false);  
    const [open, setOpen] = useState(false);  

    const handleOpenModal = () =>{
        setOpen(!open);
    }

    return (
        <div>
            <Grid container style={{marginTop:"70px"}}>
                <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                    <Link to='mypage'>
                        <button className={clsx('btn_1')}>
                           마이페이지
                        </button>
                    </Link>
                </Grid>
                <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                    <button className={clsx('btn_1', 'margin_40')}>
                        로그아웃
                    </button>
                </Grid>
                <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                    <Link to='/policy'>
                        <button className={clsx('btn_2')}>
                            약관 및 정책
                        </button>
                    </Link>
                </Grid>
                <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                    <button className={clsx('btn_3', 'margin_40')} onClick={handleOpenModal}>
                        회원 탈퇴
                    </button>
                </Grid>
            </Grid>
            {open && (
            <div className="container">
                <div className="popup-wrap" > 
                    <div className="popup">	
                        <div className="popup-body">
                            <div className="body-content">
                                <div className="body-titlebox">
                                    <ErrorOutlineIcon style={{fontSize: '47px'}}/>
                                </div>
                                <div className="body-contentbox">
                                    탈퇴 후에는 회원정보 데이터가 삭제되므로<br/>한번 삭제된 정보는 복구할 수 없습니다.<br/>정말 탈퇴하시겠습니까?
                                </div>
                            </div>
                        </div>
                        <div class="popup-footer">
                            <div className="pop-btn">탈퇴하기</div>
                            <div className="line"></div>
                            <div className="pop-btn" onClick={handleOpenModal}>취소</div>
                        </div>
                    </div>
                </div>
            </div>
            )}
        </div>
    );
}

export default Setting;
