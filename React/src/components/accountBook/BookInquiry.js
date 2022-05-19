import React, { useState, useEffect, useRef } from 'react';
import { Link, Redirect, useLocation } from 'react-router-dom';
import clsx from 'clsx';
import axios from 'axios';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import OutputIcon from '@mui/icons-material/Output';
import SearchIcon from '@mui/icons-material/Search';
import AddIcon from '@mui/icons-material/Add';
import Loader from "./../common/Loader"
import { store } from '../stores/Store';


const BookInquiry = () => {
    const { pathname } = useLocation();
    let [loading, setLoading] = useState(false);   
    const [accountbookList, setAccountBookList] = useState([]);  
    const [expenditureTotal, setExpenditureTotal] = useState();
    const [incomeTotal, setIncomeTotal] = useState();
    let now = new Date();                           //현재 날짜 및 시간
    let todayMonth = now.getMonth() + 1;            //월 구하기

    const API_SERVER = "https://gateway.bitbank.click" ;

    useEffect(() => {
        getAccountBook();
    }, [])

    // 가계부 목록 조회
    const getAccountBook = async() => {
        try {
                const response = await axios.post( API_SERVER +'/account-book/search', {
                    memberId : store.memberId,
                    searchKeyword : null,
                    searchDateType : "M",
                    searhStartDate : null,
                    searchEndDate : null,
                    expenditureType : null,
                    incomeType : null,
                    transferType : null,
                },
                {
                    headers: { 
                        Authorization : store.accessToken
                    },
                });
                console.log( '가계부 목록 조회', response.data.accountBookSearchByDailyDTOList )
                if( response.status === 200 && response.data.rt === 200 ){    
                    setAccountBookList(response.data.accountBookSearchByDailyDTOList);
                    setIncomeTotal(comma(response.data.incomeTotal))
                    setExpenditureTotal(comma(response.data.expenditureTotal))
                }    
        } catch (e) {
            console.log( 'e', e.response );
        }
    }

    function comma(str) {
        str = String(str);
        var minus = str.substring(0, 1);
     
        str = str.replace(/[^\d]+/g, '');
        str = str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
    
        if(minus == "-") str = "-"+str;
     
        return str;
    }
    

    if (!store.memberId) {
        return <Redirect to={{ pathname: "/login", state: { from: pathname } }} />;
    } else {
    return (
        <div>
            <div className={clsx('between','margin_10')}>
                <div className='subtitle_6'>{store.memberName}님 가계부</div>
                <div>
                    <Link to='/books/search'>
                        <SearchIcon style={{ margin:"0 15px", color:"#676767", fontSize: "30px" }}/>
                    </Link>
                    <Link to='/books/excel'>
                        <OutputIcon style={{ color:"#676767", fontSize: "30px" }}/>
                    </Link>
                </div>
            </div>
            <div className='month'>{todayMonth}월</div>
            <div className={clsx('between','margin_20')}>
                <div>
                    <div className='flex'>
                        <div className='info3'>지출</div>
                        <div className='subtitle_7'>{expenditureTotal}원</div>
                    </div>
                    <div className='flex'>
                        <div className='info3'>수입</div>
                        <div className='subtitle_6'>{incomeTotal}원</div>
                    </div>
                </div>
                <div>
                    <Link to='/books/ExpenditureStatistics'>
                        <button className="categoryBtn">분석</button>
                    </Link>
                </div>
            </div>
            <Grid container>
                <Grid item xs={12} style={{ justifyContent: 'center', marginTop: '20px' }}>
                    <Loader loading={loading} />
                    <Link to='/books/addbook'>
                        <Box className={clsx('pointer','item_right')}>
                            <AddIcon style={{ color:"#676767", fontSize: "25px" }}/>
                            <div className='info4'>내역 추가</div>
                        </Box>
                    </Link> 
                    {accountbookList && accountbookList.map((data, i) => (
                        <div className='books_paper'>
                            <div className={clsx('books_data', 'between')}>  
                                <div className='info4'>{data.date} {data.day}</div>
                                <div className='info4'><b>{comma(data.accountBookTotalByDaily)}원</b></div>
                            </div>
                            <hr/>
                            {data.accountBookInfoDTOList && data.accountBookInfoDTOList.map((o, index) =>(
                                <div className={clsx('books_data', 'between')}>  
                                    <div className='info5'>{o.accountBookInfo}</div>
                                    <div className='books_price'>{comma(o.accountMoney)}원</div>
                                </div>
                            ))}
                        </div>    
                    ))}       
                </Grid>
            </Grid>
        </div>
    );
}}

export default BookInquiry;
