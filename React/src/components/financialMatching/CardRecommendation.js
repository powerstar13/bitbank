import React, { useState, useEffect, useRef } from 'react';
import { makeStyles } from '@mui/styles';
import { Link, useHistory, useLocation } from 'react-router-dom';
import clsx from 'clsx';
import Grid from '@mui/material/Grid';
import Loader from "./../common/Loader"


const CardRecommendation = () => {
    const location = useLocation();
    let [loading, setLoading] = useState(false);   
    const [cardList, setCardList] = useState(location.state.cardList);

    // 카드 혜택 유형
    const getValue = (type) => {
        try {
            if( type === 'T') {
                return "교통비 혜택"
            } else if (type === 'P') {
                return "통신비 혜택"
            } else if (type === 'E') {
                return "식사비 혜택"
            } else if (type === 'D') {
                return "카페비 혜택"
            } else if (type === 'S') {
                return "쇼핑비 혜택"
            } else if (type === 'O') {
                return "주유비 혜택"
            } else if (type === 'B') {
                return "도서비 혜택"
            } else if (type === 'I') {
                return "보험비 혜택"
            } else if (type === 'M') {
                return "대형마트비 혜택"
            } else if (type === 'C') {
                return "편의점 혜택"
            } 
        } catch (e) {
            return '';
        }
    }

    return (
        <div>
            <div className={clsx('item_center','subtitle_2')}>나에게 가장 좋은 카드는?</div>
            <div className="subtitle_3">입력하신 소비 패턴에 맞게<br/>많은 혜택을 받을 수 있는 카드를 찾았어요!</div>
            <Grid container>
                <Grid item xs={12} style={{ justifyContent: 'center', marginTop: '30px' }}>
                    <Loader loading={loading} />
                    {cardList && cardList.map((data, i) => (
                        <div className='paper' key={data.cardRanking}>
                            <div className='card_company'>{data.cardCompany}</div>
                            <div className='card_name'>{i+1}&nbsp; {data.cardName}</div>
                            <img src={data.cardImagePath} height="80" width="150"/>
                            <div className='tag_btn'>{getValue(data.cardBenefitType)}</div>
                            <div className='card_benefit'>{data.cardBenefitContent}</div>
                        </div>
                    ))}
                </Grid>
            </Grid>
        </div>
    );
}

export default CardRecommendation;
