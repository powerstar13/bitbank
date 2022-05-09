import React, { useState, useEffect, useRef } from 'react';
import { makeStyles } from '@mui/styles';
import clsx from 'clsx';
import { Link, useHistory } from 'react-router-dom';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';

const useStyles = makeStyles((theme) => ({
    root: {
        fontFamily: "IBM Plex Sans KR",
        display: "flex",
    },
}));


const CardRecommendation = ({ history }) => {
    const classes = useStyles();
    let [loading, setLoading] = useState(false);   
 
    const goBack = (e) => {
        history.goBack();
    };

    return (
        <div>
            {/* <Loader loading={loading} /> */}
            <div className='center'>
                <ArrowBackIosIcon style={{margin: '8px 0 0 -5px', color: '#6E6E6E'}} onClick={goBack}/>
                <div className={clsx('item_center','subtitle_2')}>나에게 가장 좋은 카드는?</div>
            </div>
            <div className="subtitle_3">입력하신 소비 패턴에 맞게<br/>많은 혜택을 받을 수 있는 카드를 찾았어요!</div>
            <Grid container>
                <Grid item xs={12} style={{ justifyContent: 'center', marginTop: '30px' }}>
                    <div className='paper'>
                        <div className='card_company'>삼성카드</div>
                        <div className='card_name'>1. 삼성카드 taptap O</div>
                        <img src="http://db.kookje.co.kr/news2000/photo/2017/0915/L20170915.99099006474i1.jpg?37" height="80" width="150"/>
                        <div className='tag_btn'>대형마트 할인</div>
                        <div className='card_benefit'>삼성이 혜택을 책임지고 다 드립니다. 통신비에서 10프로 할인까지!</div>
                    </div>
                    <div className='paper'>
                        <div className='card_company'>KB국민카드</div>
                        <div className='card_name'>2. KB국민 청춘대로 매니아i카드</div>
                        <img src="https://blog.kakaocdn.net/dn/bbr8ku/btrfG14HdFy/ZOuwNuAMcTmG6KBYUOIvJ0/img.jpg" height="80" width="150"/>
                        <div className='tag_btn'>통신비 할인</div>
                        <div className='card_benefit'>KB국민이 혜택을 책임지고 다 드립니다. 통신비에서 10프로 할인까지!</div>
                    </div>
                    
                </Grid>
            </Grid>
        </div>
    );
}

export default CardRecommendation;
