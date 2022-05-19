import React, { useState, useEffect, useRef } from 'react';
import { makeStyles } from '@mui/styles';
import clsx from 'clsx';
import axios from 'axios';
import Grid from '@mui/material/Grid';
import Loader from "./../common/Loader"


const CardRanking = () => {
    let [loading, setLoading] = useState(false);   
    const [page, setPage] = useState(1);
    const [cardList, setCardList] = useState([]);
    const API_SERVER = "https://gateway.bitbank.click";
    

    useEffect(() => {
        getCardRanking();
    },[]);

    // 인기 카드 조회
    const getCardRanking = async() => {  
        setLoading(true);
        try {
                const response = await axios.get( API_SERVER +'/card/popular-list');
                if( response.status === 200 && response.data.rt === 200 ){   
                    setCardList(response.data.cardDTOList)
                }    
        } catch (e) {
            console.log( 'e', e.response );
        }
        setLoading(false);
    }

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
            <div className={clsx('item_center','subtitle_2')}>인기카드 Top 10</div>
            <div className={clsx('item_center','subtitle_3')}>카드사의 대표적인<br/>1위부터 10위 카드를 소개합니다!</div>
            <Grid container>
                <Grid item xs={12} style={{ justifyContent: 'center', marginTop: '30px' }}>
                    <Loader loading={loading} />
                    {cardList && cardList.map((data, i) => (
                        <div className='paper' key={data.cardId}>
                            <div className='card_company'>{data.cardCompany}</div>
                            <div className='card_name'>{data.cardRanking}&nbsp; {data.cardName}</div>
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

export default CardRanking;
