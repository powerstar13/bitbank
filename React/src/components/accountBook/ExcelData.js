import React, { useState, useEffect, useRef } from 'react';
import { makeStyles } from '@mui/styles';
import clsx from 'clsx';
import { Link, useHistory } from 'react-router-dom';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';

const useStyles = makeStyles((theme) => ({
    root: {
        display: "flex",
    },
}));


const ExcelData = () => {
    const classes = useStyles();
    let history = useHistory();
    let [loading, setLoading] = useState(false);   


    return (
        <div>
            {/* <Loader loading={loading} /> */}
            <div className={clsx('block','subtitle_2')}> 나의 내역과 자산 현황을<br/>엑셀로 공유 받으세요</div>
            <div className="subtitle_3">연결한 금융사 정보의 <br/>업데이트 시점을 기준으로 데이터가 전송됩니다</div>
            <form className={classes.root} noValidate autoComplete="off">
                <Grid container>
                    <Grid item xs={12} style={{ justifyContent: 'center' }}>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>월 평균 사용금액</div>
                            <div>
                                <input type="text" placeholder="월 평균 사용금액을 입력하세요" className={clsx('form_txt','margin_5')} />
                            </div>
                        </div>
                    </Grid>

                    <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                        <Link to='/cards/profits'>
                            <button className={clsx('btn_1', 'margin_30')}>
                                완료
                            </button>
                        </Link>
                    </Grid>
                </Grid>
            </form>
        </div>
    );
}

export default ExcelData;
