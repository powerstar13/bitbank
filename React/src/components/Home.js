import React, { useState, useEffect, useRef } from 'react'
import { makeStyles } from '@mui/styles';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';


const useStyles = makeStyles((theme) => ({
    root:{
    
    }
}));

const Home = () => {
    const classes = useStyles();

    return (
        <>
            <Container maxWidth="sm">
                <Box className={classes.section_intro2} display="flex" justifyContent="center" alignItems="top">
                    <Box>
                        <Box className={classes.title}>
                            내 손안의 목돈마련 BITBANK
                        </Box>
                        <Box className={classes.title2}>
                            체계적인 예산, 통게 기능으로 쉽고 정확하게 분석합니다.
                        </Box>
                        {/* <Box display="flex" justifyContent="center">
                            <Box>
                                <Box style={{backgroundColor: "#fff",border: 'solid 5px #466495', borderRadius: "50%", cursor: "pointer", width: "144px", height: "144px", margin: "100px auto 0", textAlign: "center", paddingTop: "38px"}}>
                                    <div className={classes.tech_icon}>
                                        <div><img src='/img/home/Icon/tech_icon/learn.png'/></div>
                                    </div>
                                    <h2>학습</h2>
                                </Box>
                                <Box>
                                    <div className={classes.title3}>AI 사운드 엔지니어는, 매일<br/>수많은 데이터를 학습하여<br/>방대한 규모의 사운드 라이브러리를<br/>구축하고 있습니다</div>
                                </Box>
                            </Box>
                        <Box>
                            <Box style={{backgroundColor: "#fff", border: 'solid 5px #466495', borderRadius: "50%", cursor: "pointer", width: "144px", height: "144px", margin: "100px auto 0", textAlign: "center", paddingTop: "26px"}}>
                                <div className={classes.tech_icon}>
                                    <div><img src='/img/home/Icon/tech_icon/chart.png' style={{width: "84px", height: "82px"}}/></div>
                                </div>
                                <h2 style={{marginTop:"28px"}}>분석</h2>
                            </Box>
                            <Box >
                                <div className={classes.title3}>AI 사운드 엔지니어는, 수많은 데이터를<br/>분석하여 최고의 사운드가 무엇인지<br/>스스로 끊임없이 연구합니다</div>
                            </Box>
                        </Box>

                        <Box>
                            <Box style={{backgroundColor: "#fff", border: 'solid 5px #466495', borderRadius: "50%", cursor: "pointer", width: "144px", height: "144px", margin: "100px auto 0", textAlign: "center", paddingTop: "35px"}}>
                                <div className={classes.tech_icon}>
                                    <div><img src='/img/home/Icon/tech_icon/tune.png' style={{width: "64px", height: "64px"}}/></div>
                                </div>
                                <h2>적용</h2>
                            </Box>
                            <Box>
                                <div className={classes.title3}>AI 사운드 엔지니어는<br/>여러분들이 원하는 작업을<br/>최적의 프로세스를 통해<br/>빠르게 만들어냅니다</div>
                            </Box>
                        </Box> */}
                    {/* </Box> */}
                </Box>
                </Box>
            </Container>
        </>
    );

}

export default Home;