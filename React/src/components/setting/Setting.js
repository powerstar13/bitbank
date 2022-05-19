import React, { useState, useEffect, useRef } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { observer, useObserver } from 'mobx-react';
import clsx from 'clsx'
import axios from 'axios';
import Grid from '@mui/material/Grid';
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';
import { store } from './../stores/Store';


const Setting = () => {
    let [loading, setLoading] = useState(false);  
    const [open, setOpen] = useState(false);  
    const API_SERVER = "https://gateway.bitbank.click" ;

    const handleOpenModal = () =>{
        setOpen(!open);
    }

    const getLogOut = async(e) => {
        e.preventDefault();
        try {
            const response = await axios.post(API_SERVER + '/member/logout', {
                memberId : store.memberId
            },
            {
                headers: { 
                    Authorization : store.accessToken
                },
            })
            if (response.status === 200 && response.data.rt === 200) {
                sessionStorage.clear();
                store.logOut();
                document.location.href = '/'
            }
        } catch (error) {
            console.log('error', error)
        } 
        setLoading(false);
    }

    //회원 탈퇴 진행
    const getResigned = async(e) => {
        e.preventDefault();
        try {
            const headers = {
                'Authorization': `${store.accessToken}`,
            };
            const response = await axios.delete(API_SERVER + '/member/delete', { headers ,
                data:{
                    memberId : store.memberId
                },
            },
            )
            if (response.status === 200 && response.data.rt === 200) {
                sessionStorage.clear();
                store.logOut();
                document.location.href = '/'
            }
        }catch (error) {
            console.log('error',error.response)
        } 
    }

    return useObserver(() =>(
        <div>
            <Grid container style={{marginTop:"70px"}}>
                <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                    <Link to='/policy'>
                        <button className={clsx('btn_2')}>
                            약관 및 정책
                        </button>
                    </Link>
                </Grid>
                { store.memberId &&(
                    <>
                        <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                            <Link to='mypage'>
                                <button className={clsx('btn_1', 'margin_40')}>
                                마이페이지
                                </button>
                            </Link>
                        </Grid>
                        <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                            <button className={clsx('btn_1')} onClick={getLogOut}>
                                로그아웃
                            </button>
                        </Grid>
                        <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                            <button className={clsx('btn_3', 'margin_40')} onClick={handleOpenModal}>
                                회원 탈퇴
                            </button>
                        </Grid>
                    </>
                )}
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
                        <div className="popup-footer">
                            <div className="pop-btn" onClick={getResigned}>탈퇴하기</div>
                            <div className="line"></div>
                            <div className="pop-btn" onClick={handleOpenModal}>취소</div>
                        </div>
                    </div>
                </div>
            </div>
            )}
        </div>
    ));
}

export default Setting;
