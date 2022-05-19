import React, { useEffect, useState } from 'react';
import { makeStyles } from '@mui/styles';
import { Link, useHistory, Redirect } from 'react-router-dom';
import { observer, useObserver } from 'mobx-react';
import axios from 'axios';
import clsx from 'clsx';
import AppBar from "@mui/material/AppBar";
import Badge from '@mui/material/Badge';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import Menu from '@mui/material/Menu';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import NotificationsIcon from '@mui/icons-material/Notifications';
import CloseIcon from '@mui/icons-material/Close';
import PaidIcon from '@mui/icons-material/Paid';
import logo from './../img/logo.png'
import {store} from './../stores/Store';


const useStyles = makeStyles((theme) => ({
    appBar: {
        "&.MuiAppBar-root":{
            padding: "17px 5px 5px",
        },
    },
    appBar1: {
        "&.MuiAppBar-root":{
            padding: "0px 0 10px",
            marginTop: "65px",
        },
        "& .menu_appbar":{
            [theme.breakpoints.down('mobile')]: {
                position: "fixed",
                bottom: 0,
                width: "100%",
                height: "60px",
                backgroundColor: '#F2F2F2',
            }
        }
    },
    popup: {
        '& .MuiPaper-rounded': {
            border: '3px solid #9cb1b0',
            // backgroundColor: '#f0fcfc',
            borderRadius: '20px',
        }
    },
}));

const MenuHeader = () => {
    const cls = useStyles();
    let history = useHistory();
    const [menu, setMenu] = useState(4); 
    const [showNoti, setShowNoti] = useState(false)
    const [alarmCount, setAlarmCount] = useState(0);
    const [alarmList, setAlarmList] = useState([]);
    const API_SERVER = "https://gateway.bitbank.click" ;

    useEffect(() => {
        if( store.memberId != null ) {
            getAlarmCount(store.accessToken, store.memberId);
        }
    },[store.memberId])

    useEffect(()=> {
        if( menu===0 && !store.memberId ){
            history.push('/login')
        }
    }, [menu])

    // 알림 갯수 조회
    const getAlarmCount = async(token, id) => {
        try {
                const headers = {
                    'Authorization': `${token}`,
                };
                const response = await axios.get( API_SERVER +'/member/alarm-count',{ headers ,
                    params: {
                        memberId : id,
                    }
                });
                if( response.status === 200 && response.data.rt === 200 ){   
                    setAlarmCount(response.data.alarmCount)
                }    
        } catch (e) {
            console.log( 'e', e.response );
        }
    }

    // 알림 목록 조회
    const getAlarmList = async(token, id) => {
        try {
                const headers = {
                    'Authorization': `${token}`,
                };
                const response = await axios.get( API_SERVER +'/member/alarm-list',{ headers ,
                    params: {
                        memberId : id,
                    }
                });
                if( response.status === 200 && response.data.rt === 200 ){   
                    setShowNoti(true)
                    setAlarmList(response.data.alarmDTOList)
                    setAlarmCount(0)
                }    
        } catch (e) {
            console.log( 'e', e.response );
        }
    }

    const goBack = (e) => {
        history.goBack();
    };

    return useObserver(() => (
        <>
            <AppBar className={cls.appBar}>
                <Container maxWidth="sm" style={{padding:"0px"}}>
                   <div className={clsx('between', 'padding_10')}>
                        <div>
                            {/* {menu === 4 ? (
                                <ArrowBackIosIcon style={{margin: '10px 0', color: '#F2F2F2'}}/>
                            ):(
                                <ArrowBackIosIcon style={{margin: '10px 0', color: '#6E6E6E'}} onClick={goBack}/>
                            )} */}
                            <ArrowBackIosIcon style={{margin: '10px 0', color: '#6E6E6E'}} onClick={goBack}/>
                        </div>
                        <Box display="flex">
                            <img src={logo} alt="B" width="17px" height="25px" style={{margin:"10px 20px"}}/>
                            <div className='logo' onClick={() => window.location.replace("/")}>ITBANK</div>
                        </Box>

                        { !store.memberId ? ( 
                            <Link to='/login'>
                                <Box onClick={()=>setMenu(4)}>
                                    <button className="loginBtn">로그인</button>
                                </Box>
                            </Link>
                         ):(
                            <div style={{width:"38px", marginTop:"-10px"}}>
                                <Badge badgeContent={alarmCount} color="primary">
                                    <NotificationsIcon style={{color:"#848484", fontSize:"25px"}} onClick={(e) => getAlarmList(store.accessToken, store.memberId)}/>
                                </Badge>
                                <Menu
                                    id="simple-menu1"
                                    className={cls.popup}
                                    anchorEl={showNoti}
                                    open={showNoti}
                                    anchorOrigin={{
                                        vertical: 'top',
                                        horizontal: 'center',
                                    }}
                                    keepMounted
                                    style={{ top: '-70px' }}
                                    onClick={() => setShowNoti(false)}
                                >
                                    <div style={{ width: '475px', height: '350px' }}></div>
                                    <Container
                                        style={{ position: 'absolute', top: '32px', left: '0', height: '330px', overflowY:"auto" }}
                                    >
                                        <Box display="flex" justifyContent="space-between">
                                            <Box color="primary" style={{ fontWeight: "bold", fontSize: "1rem" }}>
                                                알림을 확인하세요.
                                            </Box>
                                            <Box>
                                                <CloseIcon onClick={(e) => { setShowNoti(false); e.stopPropagation(); }} className="pointer"></CloseIcon>
                                            </Box>
                                        </Box>
                                        {alarmList && alarmList.map((data, i) => (
                                            <div className={clsx('margin_20','flex')} key={data.id}>
                                                <div>
                                                    <PaidIcon style={{ color:'#2167C2', fontSize: '35px'}}/>
                                                </div>
                                                <div className='padding_10'>
                                                    <div className='info4'>소비 리포트</div>
                                                    <div className={clsx('padding_5','subtitle_8')}>{data.alarmMessage}</div>
                                                    <div className={clsx('info4')}>{data.regDate}</div>
                                                </div>
                                            </div>
                                        ))}
                                    </Container>
                                </Menu>
                            </div> 
                        )} 
                    </div>
                </Container>
            </AppBar>
            <AppBar className={cls.appBar1}>
                <Container maxWidth="sm" style={{padding:"0px"}}>
                    <div className={clsx('around', 'menu_appbar')}>
                        <Link to='/books'>
                            <Box className={clsx("header_menu", "txt_left", menu===0 && "clickedMenu")} onClick={()=>setMenu(0)}>가계부</Box>
                        </Link>
                        <Link to='/cards'>
                            <Box className={clsx("header_menu", "txt_center", menu===1 && "clickedMenu")} onClick={(e)=>setMenu(1)}>금융매칭</Box>
                        </Link>
                        <Link to='/setting'>
                            <Box className={clsx("header_menu", "txt_right", menu===2 && "clickedMenu")} onClick={()=>setMenu(2)}>설정</Box>
                        </Link>
                    </div>
                </Container>
            </AppBar>
        </>
    ));
}

export default MenuHeader;
