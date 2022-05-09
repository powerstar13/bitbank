import React, { useState, useEffect, useRef } from 'react';
import { makeStyles } from '@mui/styles';
import clsx from 'clsx';
import { Link, useHistory } from 'react-router-dom';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';

const useStyles = makeStyles((theme) => ({
    root: {
        display: "flex",
    },
}));


const Poll = () => {
    const classes = useStyles();
    let history = useHistory();
    let [loading, setLoading] = useState(false);   
    const [userInfo, setUserInfo] = useState({
        memberLoginid: '',
        memberPassword: '',
        memberName: '',
    });

    const { memberLoginid, memberPassword, memberName } = userInfo;

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

    const handleClick = () => {
        if (!memberLoginid) {
        }
        else if (!memberPassword) {
        }
    }

    const goBack = (e) => {
        history.goBack();
    };

    return (
        <div>
            {/* <Loader loading={loading} /> */}
            <div className='center'>
                <ArrowBackIosIcon style={{ margin:'3px 0 -3px 0', color: '#6E6E6E'}} onClick={goBack}/>
                <div className={clsx('item_center','subtitle_2')}>나에게 가장 좋은 카드는?</div>
            </div>
            <div className="subtitle_3">소비패턴을 더 정확하게 입력할수록, <br/>최고의 카드를 찾을 확률이 높아집니다!</div>
            <div className="info">(※숫자만 입력하세요.※)</div>
            <form className={classes.root} noValidate autoComplete="off">
                <Grid container>
                    <Grid item xs={12} style={{ justifyContent: 'center' }}>
                        <div style={{display:"flex", justifyContent:"space-between", margin:"20px 0"}}>
                            <div className='form_name'>월 평균 사용금액</div>
                            <div>
                                <input type="text" placeholder="월 평균 사용금액을 입력하세요" className={clsx('form_txt','margin_5')} value={memberName}/>
                            </div>
                        </div>
                        <div style={{display:"flex", justifyContent:"space-between", margin:"20px 0"}}>
                            <div className='form_name'>대중교통 금액</div>
                            <div>
                                <input type="text" placeholder="한달 대중교통 금액을 입력하세요" className={clsx('form_txt','margin_5')} value={memberLoginid}/>
                            </div>
                        </div>
                        <div style={{display:"flex", justifyContent:"space-between", margin:"20px 0"}}>
                            <div className='form_name'>휴대폰 요금</div>
                            <div>
                                <input type="text" placeholder="한달 휴대폰 요금을 입력하세요" className={clsx('form_txt','margin_5')} value={memberPassword}/>
                            </div>
                        </div>
                        <div style={{display:"flex", justifyContent:"space-between", margin:"20px 0"}}>
                            <div className='form_name'>식사</div>
                            <div>
                                <input type="text" placeholder="한달 외식비를 입력하세요" className={clsx('form_txt','margin_5')} value={memberPassword}/>
                            </div>
                        </div>
                        <div style={{display:"flex", justifyContent:"space-between", margin:"20px 0"}}>
                            <div className='form_name'>카페</div>
                            <div>
                                <input type="text" placeholder="한달 카페비용을 입력하세요" className={clsx('form_txt','margin_5')} value={memberPassword}/>
                            </div>
                        </div>
                        <div style={{display:"flex", justifyContent:"space-between", margin:"20px 0"}}>
                            <div className='form_name'>쇼핑</div>
                            <div>
                                <input type="text" placeholder="한달 쇼핑금액을 입력하세요" className={clsx('form_txt','margin_5')} value={memberPassword}/>
                            </div>
                        </div>
                        <div style={{display:"flex", justifyContent:"space-between", margin:"20px 0"}}>
                            <div className='form_name'>주유</div>
                            <div>
                                <input type="text" placeholder="한달 주유비를 입력하세요" className={clsx('form_txt','margin_5')} value={memberPassword}/>
                            </div>
                        </div>
                        <div style={{display:"flex", justifyContent:"space-between", margin:"20px 0"}}>
                            <div className='form_name'>서점</div>
                            <div>
                                <input type="text" placeholder="한달 서점비를 입력하세요" className={clsx('form_txt','margin_5')} value={memberPassword}/>
                            </div>
                        </div>
                        <div style={{display:"flex", justifyContent:"space-between", margin:"20px 0"}}>
                            <div className='form_name'>보험</div>
                            <div>
                                <input type="text" placeholder="한달 보험료를 입력하세요" className={clsx('form_txt','margin_5')} value={memberPassword}/>
                            </div>
                        </div>
                        <div style={{display:"flex", justifyContent:"space-between", margin:"20px 0"}}>
                            <div className='form_name'>대형마트</div>
                            <div>
                                <input type="text" placeholder="한달 대형마트 쇼핑금액을 입력하세요" className={clsx('form_txt','margin_5')} value={memberPassword}/>
                            </div>
                        </div>
                        <div style={{display:"flex", justifyContent:"space-between", margin:"20px 0"}}>
                            <div className='form_name'>편의점</div>
                            <div>
                                <input type="text" placeholder="한달 편의점 사용금액을 입력하세요" className={clsx('form_txt','margin_5')} value={memberPassword}/>
                            </div>
                        </div>
                    </Grid>

                    <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                        <Link to='/cards/profits'>
                            <button className={clsx('btn_1', 'margin_30')} onClick={handleClick}>
                                완료
                            </button>
                        </Link>
                    </Grid>
                </Grid>
            </form>
        </div>
    );
}

export default Poll;
