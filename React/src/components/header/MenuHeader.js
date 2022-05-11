import React, { useState } from 'react';
import { makeStyles } from '@mui/styles';
import { Link, useHistory  } from 'react-router-dom';
import clsx from 'clsx';
import AppBar from "@mui/material/AppBar";
import Badge from '@mui/material/Badge';
import MailIcon from '@mui/icons-material/Mail'
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import Menu from '@mui/material/Menu';
import TableContainer from '@mui/material/TableContainer';
import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';
import TableBody from '@mui/material/TableBody';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import NotificationsIcon from '@mui/icons-material/Notifications';
import CloseIcon from '@mui/icons-material/Close';

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


    const goBack = (e) => {
        history.goBack();
    };

    return (
        <>
            <AppBar className={cls.appBar}>
                <Container maxWidth="sm" style={{padding:"0px"}}>
                   <div className={clsx('between', 'padding_10')}>
                        <div>
                            <ArrowBackIosIcon style={{margin: '10px 0', color: '#6E6E6E'}} onClick={goBack}/>
                        </div>
                        <Box display="flex">
                            <img src="./img/logo.png" width="17px" height="25px" style={{margin:"10px 20px"}}/>
                            <div className='logo' onClick={() => window.location.replace("/")}>ITBANK</div>
                        </Box>
                        {/* <Link to='/login'>
                            <Box onClick={()=>setMenu(4)}>
                                <button className="loginBtn">로그인</button>
                            </Box>
                        </Link> */}

                        <div style={{width:"35px", marginTop:"-10px"}}>
                            <Badge badgeContent={99} color="primary">
                                <NotificationsIcon style={{color:"#848484", fontSize:"30px"}} onClick={(e) => setShowNoti(true)}/>
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
                                <div style={{ width: '475px', height: '416px' }}></div>
                                <Container
                                    style={{ position: 'absolute', top: '32px', left: '0', height: '400px' }}
                                >
                                    <Box display="flex" justifyContent="space-between">
                                        <Box color="primary" style={{ fontWeight: "bold", fontSize: "1rem" }}>
                                            알림을 확인하세요.
                                        </Box>
                                        <Box>
                                            <CloseIcon onClick={(e) => { setShowNoti(false); e.stopPropagation(); }} className="pointer"></CloseIcon>
                                        </Box>
                                    </Box>
                                    <TableContainer>
                                        <Table className={cls.table} aria-label="simple table">
                                            <TableHead className={cls.MuiTableHead}>
                                                <TableRow>
                                                    <TableCell>ooo</TableCell>
                                                    <TableCell>ooo</TableCell>
                                                    <TableCell>ooo</TableCell>
                                                    <TableCell>ooo</TableCell>
                                                </TableRow>
                                            </TableHead>
                                            <TableBody className={cls.MuiTableBody}>
                                                {/* <Box textAlign="center" mt={5}>
                                                    No data
                                                </Box> */}
                                            </TableBody>
                                        </Table>
                                    </TableContainer>
                                </Container>
                            </Menu>
                        </div>

                    </div>
                </Container>
            </AppBar>
            <AppBar className={cls.appBar1}>
                <Container maxWidth="sm" style={{padding:"0px"}}>
                    <div className={clsx('around', 'menu_appbar')}>
                        <Box className={clsx("header_menu", "txt_left", menu===0 && "clickedMenu")} onClick={()=>setMenu(0)}>가계부</Box>
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
    );
}

export default MenuHeader;
