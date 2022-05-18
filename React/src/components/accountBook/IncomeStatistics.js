import React, { useState, useEffect, useRef } from 'react';
import clsx from 'clsx';
import axios from 'axios';
import { Link, useHistory } from 'react-router-dom';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import { Donut } from "britecharts-react";
import { store } from '../stores/Store';
import Loader from "./../common/Loader"

const IncomeStatistics = () => {
    const API_SERVER = "https://gateway.bitbank.click";
    let [loading, setLoading] = useState(false);   
    let now = new Date();                           //현재 날짜 및 시간
    let todayMonth = now.getMonth() + 1;            //월 구하기
    const [monthlyTotal, setMonthlyTotal] = useState();
    const [weeklyTotalList, setWeeklyTotalList] = useState([]);
    const [donutGraphList, setDonutGraphList] = useState([]);
  
    useEffect(()=> {
        getIncome();
    }, [])


     // 알림 목록 조회
     const getIncome = async() => {
        setLoading(true);
        try {
                const headers = {
                    'Authorization': `${store.accessToken}`,
                };
                const response = await axios.get( API_SERVER +'/account-book/statistic/income',{ headers ,
                    params: {
                        memberId : store.memberId,
                        month : todayMonth,
                    }
                });
                console.log( '월 별 수입 통계 조회', response.data )
                if( response.status === 200 && response.data.rt === 200 ){   
                    setMonthlyTotal(comma(response.data.monthlyTotal));
                    setWeeklyTotalList(response.data.weeklyTotalDTOList);
                    setDonutGraphList(response.data.donutGraphDTOList);
                }    
        } catch (e) {
            console.log( 'e', e.response );
        }
        setLoading(false);
    }

    function comma(str) {
        str = String(str);
        var minus = str.substring(0, 1);
     
        str = str.replace(/[^\d]+/g, '');
        str = str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
    
        if(minus == "-") str = "-"+str;
     
        return str;
    }

    
    return (
        <div>
            <Loader loading={loading}/>
            <div>
                <div className={clsx('padding_10', 'margin_10', 'info7')}>{todayMonth}월 수입</div>
                <div className={clsx('padding_10', 'subtitle_7')}>{monthlyTotal}원</div>
                <Link to='/books/expenditurestatistics'>
                    <Box className={clsx('statistic_btn')}>
                        <div>지출 통계</div>
                    </Box>
                </Link>
            </div>
            <Grid container>
                <Grid item xs={12} style={{ justifyContent: 'center', marginTop: '20px' }}> 
                    <div className='books_paper'>
                        <div className={clsx('subtitle_7')}>요약</div>
                        {weeklyTotalList && weeklyTotalList.map((data, i) => (
                            <div className={clsx('books_data', 'between')} key={data.week+i}>  
                                <div className='info5'>{data.week}</div>
                                <div className='books_price'>{comma(data.total)}원</div>
                            </div>
                        ))}
                        <hr/>
                        <div className={clsx('books_data', 'between')}>  
                            <div className='info7'>총 수입</div>
                            <div className='books_price'>{monthlyTotal}원</div>
                        </div>
                    </div>    

                    <div className='statistic_paper'>
                        <div className={clsx('subtitle_7', 'margin_30')}>통계</div>
                        <div className={clsx('item_center')}>
                            <Donut
                                loadingState = {true}
                                data={donutGraphList}
                                externalRadius={150}
                                internalRadius={70}
                                highlightSliceById={1}   //해당 슬라이드 id 강조
                                hasHoverAnimation={true}
                                isAnimated={true}
                            />
                        </div>
                    </div>               
                </Grid>
            </Grid>
        </div>
    );
}

export default IncomeStatistics;
