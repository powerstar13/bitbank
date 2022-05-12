import React, { useState, useEffect, useRef } from 'react';
import { makeStyles } from '@mui/styles';
import clsx from 'clsx';
import { Link, useHistory } from 'react-router-dom';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import OutputIcon from '@mui/icons-material/Output';
import SearchIcon from '@mui/icons-material/Search';
import AddIcon from '@mui/icons-material/Add';

const useStyles = makeStyles((theme) => ({
    root: {
        fontFamily: "IBM Plex Sans KR",
        display: "flex",
    },
}));

const style = {
    width: '100%',
    maxWidth: 320,
    bgcolor: 'rgba(242, 242, 242, 0.7)',
};

const BookInquiry = () => {
    const classes = useStyles();
    let [loading, setLoading] = useState(false);   
    let now = new Date();                           //현재 날짜 및 시간
    let todayMonth = now.getMonth() + 1;            //월 구하기

    return (
        <div>
            <div className={clsx('between','margin_10')}>
                <div className='subtitle_6'>권설아님 가계부</div>
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
                        <div className='subtitle_7'>300.000원</div>
                    </div>
                    <div className='flex'>
                        <div className='info3'>수입</div>
                        <div className='subtitle_6'>450.000원</div>
                    </div>
                </div>
                <div>
                    <button className="categoryBtn">분석</button>
                </div>
            </div>
            <Grid container>
                <Grid item xs={12} style={{ justifyContent: 'center', marginTop: '20px' }}>
                    <Link to='/books/addbook'>
                        <Box className={clsx('pointer','item_right')}>
                            <AddIcon style={{ color:"#676767", fontSize: "25px" }}/>
                            <div className='info4'>내역 추가</div>
                        </Box>
                    </Link>    
                    <div className='books_paper'>
                        <div className={clsx('books_data', 'between')}>  
                            <div className='info4'>10일 화요일</div>
                            <div className='info4'>-85.000원</div>
                        </div>
                        <hr/>
                        <div className={clsx('books_data', 'between')}>  
                            <div className='info5'>김밥나라</div>
                            <div className='books_price'>-5.000원</div>
                        </div>
                        <div className={clsx('books_data', 'between')}>  
                            <div className='info5'>LGUPLUS 통신 요금 자동 청구 5월</div>
                            <div className='books_price'>-60.000원</div>
                        </div>
                        <div className={clsx('books_data', 'between')}>  
                            <div className='info5'>홈플러스</div>
                            <div className='books_price'>-5.000원</div>
                        </div>
                        <div className={clsx('books_data', 'between')}>  
                            <div className='info5'>이마트</div>
                            <div className='books_price'>-5.000원</div>
                        </div>
                        <div className={clsx('books_data', 'between')}>  
                            <div className='info5'>신세계 백화점</div>
                            <div className='books_price'>-5.000원</div>
                        </div>
                        <div className={clsx('books_data', 'between')}>  
                            <div className='info5'>롯데마트</div>
                            <div className='books_price'>-5.000원</div>
                        </div>
                    </div>    

                    <div className='books_paper'>
                        <div className={clsx('books_data', 'between')}>  
                            <div className='info4'>9일 월요일</div>
                            <div className='info4'>-85.000원</div>
                        </div>
                        <hr/>
                        <div className={clsx('books_data', 'between')}>  
                            <div className='info5'>김밥나라</div>
                            <div className='books_price'>-5.000원</div>
                        </div>
                        <div className={clsx('books_data', 'between')}>  
                            <div className='info5'>LGUPLUS 통신 요금 자동 청구 5월</div>
                            <div className='books_price'>-60.000원</div>
                        </div>
                        <div className={clsx('books_data', 'between')}>  
                            <div className='info5'>홈플러스</div>
                            <div className='books_price'>-5.000원</div>
                        </div>
                        <div className={clsx('books_data', 'between')}>  
                            <div className='info5'>이마트</div>
                            <div className='books_price'>-5.000원</div>
                        </div>
                        <div className={clsx('books_data', 'between')}>  
                            <div className='info5'>신세계 백화점</div>
                            <div className='books_price'>-5.000원</div>
                        </div>
                        <div className={clsx('books_data', 'between')}>  
                            <div className='info5'>롯데마트</div>
                            <div className='books_price'>-5.000원</div>
                        </div>
                    </div>            
                </Grid>
            </Grid>
        </div>
    );
}

export default BookInquiry;
