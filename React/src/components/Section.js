import React, { useState, useEffect, useRef } from 'react'
import { Route, useLocation, BrowserRouter as Router } from 'react-router-dom';
import { makeStyles } from '@mui/styles';
import Box from '@mui/material/Box';
import Home from './Home'
import Login from './profile/Login'
import SignUp from './profile/SignUp'
import Matching from './financialMatching/Matching'
import Poll from './financialMatching/Poll'
import CardRecommendation from './financialMatching/CardRecommendation'
import CardRanking from './financialMatching/CardRanking'
import Setting from './setting/Setting'
import Policy from './setting/Policy'
import MyPage from './setting/MyPage'
import AddBook from './accountBook/AddBook'
import ExcelData from './accountBook/ExcelData'
import PDFdata from './accountBook/PDFdata'
import BookInquiry from './accountBook/BookInquiry'
import BookSearch from './accountBook/BookSearch'

const useStyles = makeStyles((theme) => ({
    root:{
        margin:"130px auto 100PX", 
        padding: "0 100px", 
        minHeight: "calc( 100vh - 594px )",
        height:"auto",
        fontSize: "16px",
    }
}));

const Section = () => {
    const cls = useStyles();

    return (
        <Box className={cls.root} display="flex" justifyContent="center">
            <Route path="/" component={Home} exact/>
            <Route path="/login" component={Login} exact/>
            <Route path="/signup" component={SignUp} exact/>
            <Route path="/cards" component={Matching} exact/>
            <Route path="/cards/questions" component={Poll} exact/>
            <Route path="/cards/profits" component={CardRecommendation} exact/>
            <Route path="/cards/ranking" component={CardRanking} exact/>
            <Route path="/setting" component={Setting} exact/>
            <Route path="/policy" component={Policy} exact/>
            <Route path="/mypage" component={MyPage} exact/>
            <Route path="/books" component={BookInquiry} exact/>
            <Route path="/books/addbook" component={AddBook} exact/>
            <Route path="/books/excel" component={ExcelData} exact/>
            <Route path="/books/pdf" component={PDFdata} exact/>
            <Route path="/books/search" component={BookSearch} exact/>
            {/*
            <Route path="/books/expenditurestatistics" component={ExpenditureStatistics} exact/> 
            <Route path="/books/incomestatistics" component={IncomeStatistics} exact/>*/}
        </Box>
    );

}

export default Section;