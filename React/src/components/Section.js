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

const useStyles = makeStyles((theme) => ({
    root:{
        marginTop:"110px", 
        padding: "0 100px", 
        minHeight: "calc( 100vh - 594px )",
        height:"auto",
        [theme.breakpoints.up('lg')]: {
            padding: "0 300px", 
        },
        // backgroundColor:"#F2F2F2"
    }
}));

const Section = () => {
    const cls = useStyles();
    // const location = useLocation();

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
        </Box>
    );

}

export default Section;