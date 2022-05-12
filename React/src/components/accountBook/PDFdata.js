import React, { useState, useEffect, useRef } from 'react';
import { makeStyles } from '@mui/styles';
import clsx from 'clsx';
import Grid from '@mui/material/Grid';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import Box from '@mui/material/Box';
import transmit from './transmit.png'
import excelTransmit from './excelTransmit.png'

const useStyles = makeStyles((theme) => ({
    root: {
        display: "flex",
    },
}));


const PDFdata = () => {
    const classes = useStyles();
    let [loading, setLoading] = useState(false);   
    const [email, setEmail] = useState('');   
    const [open, setOpen] = useState(false);  
    const [error, setError] = useState(false);   

    const CheckEmail = (e) => {
        e.preventDefault();
        var regEmail = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
        if(regEmail.test(email) === true){
            setError(false);
            setOpen(true);
        } else {
            setError(true);
        }
    }
    return (
        <div>
            <div className={clsx('block','subtitle_2')}> 나의 지출/소비 통계 내역을<br/>PDF로 공유 받으세요</div>
            <div className="subtitle_3">나의 지출/소비 통계 내역 <br/>업데이트 시점을 기준으로 다운로드 됩니다</div>
            <div className='center'>
                <img src={transmit} width="350px" height="280px"/>
            </div>
            <form className={classes.root} noValidate autoComplete="off">
                <Grid container>
                    <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                        <button className={clsx('btn_1', 'margin_30')} onClick={CheckEmail}>
                            다운로드
                        </button>
                    </Grid>
                </Grid>
            </form>
            {open && (
                <div className="container">
                    <div className="popup-wrap" > 
                        <div className="popup">	
                            <div className="popup-body">
                                <div className="body-content">
                                    <div className="body-titlebox">
                                        <CheckCircleOutlineIcon style={{fontSize: '47px'}}/>
                                    </div>
                                    <div className="body-contentbox">
                                        공유가 완료되었습니다!<br/>
                                        이메일 수신함을 확인하세요!<br/>
                                        길게는 5분 정도 걸릴 수 있습니다.
                                    </div>
                                </div>
                            </div>
                            <div class="popup-footer">
                                <Box className="pop-btn" onClick={()=>setOpen(false)}>확인</Box>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default PDFdata;
