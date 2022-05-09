import React, { useState } from 'react';
import { makeStyles } from '@mui/styles';
import { Link, useHistory  } from 'react-router-dom';
import clsx from 'clsx';
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';


const useStyles = makeStyles((theme) => ({
    appBar: {
        "&.MuiAppBar-root":{
            backgroundColor: '#F2F2F2',
            borderBottom:"1px solid #F2F2F2",
            padding: "20px 5px 10px"
            // boxShadow: "none"
        },
    },
}));

const MenuHeader = () => {
    const cls = useStyles();
    let history = useHistory();
    const [menu, setMenu] = useState(4); 

    return (
        <>
            <AppBar className={cls.appBar}>
                <Container maxWidth="sm" style={{padding:"0px"}}>
                   <Box display="flex" justifyContent="space-between">
                        <Box display="flex">
                            <img src="./img/logo.png" width="15px" height="20px" style={{margin:"10px 0"}}/>
                            <div className='logo' onClick={() => window.location.replace("/")}>ITBANK</div>
                        </Box>
                        <Box className={clsx("header_menu", menu===0 && "clickedMenu")} onClick={()=>setMenu(0)}>가계부</Box>
                        <Link to='/cards'>
                            <Box className={clsx("header_menu", menu===1 && "clickedMenu")} onClick={(e)=>setMenu(1)}>금융매칭</Box>
                        </Link>
                        <Link to='/setting'>
                            <Box className={clsx("header_menu", menu===2 && "clickedMenu")} onClick={()=>setMenu(2)}>설정</Box>
                        </Link>
                        <Link to='/login'>
                            <Box alignItems="center" onClick={()=>setMenu(4)}>
                                <button className="loginBtn">로그인</button>
                            </Box>
                        </Link>
                    </Box>
                </Container>
            </AppBar>

        </>
    );
}

export default MenuHeader;
