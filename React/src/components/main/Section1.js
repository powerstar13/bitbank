import React from 'react'
import styled, { keyframes } from "styled-components";
import {Link, useHistory } from "react-router-dom";
import { Zoom } from "react-awesome-reveal";
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import img1 from './../img/img1.jpg'; 
import Grid from '@mui/material/Grid';

const Section1 = () => {

    return (
        <>
            <Container maxWidth="sm">
                <Box display="flex" justifyContent="center" alignItems="top">
                    <div>
                        <Grid container spacing={2}>
                            <Grid item xs={12} >
                                <Div>
                                    <Wrapper>
                                        <TextDiv>
                                            <Zoom triggerOnce={false} duration={1500}>
                                                <Text data-text="금융을 내편으로">
                                                    금융을 내편으로
                                                </Text>
                                                <Text>나만의 금융 도우미</Text>
                                                <Text>BITBANK</Text>
                                            </Zoom>
                                            <Link to='/cards'>
                                              <Button>BITBANK 사용해보기</Button>
                                            </Link>
                                        </TextDiv>
                                    </Wrapper>
                                </Div>
                            </Grid>
                        </Grid>
                    </div>
                </Box>
            </Container>
        </>
    );

}

export default Section1;


export const Div = styled.div`
  width: 100vw;
  height: 100%;
  background-image: url(${img1});
  background-size: cover;
  background-repeat: no-repeat;
  background-position: 50% 50%;
  margin: -40px 0 0;
  overflow: hidden;
  @media (max-width: 710px) {
    background-size: cover;
  }
  @media (max-width: 500px) {
    background-size: cover;
    margin: -25px -5px 0 5px;
  }
  @media (max-width: 400px) {
    background-size: cover;
    margin: -55px -10px 0 -10px;
  }
`;

export const Wrapper = styled.div`
  background-color: rgba(0, 0, 0, 0.21);
  width: 100vw;
  height: 100vh;
  align-items: center;
  display: flex;
  overflow: hidden;
`;

export const TextDiv = styled.div`
  margin-left: 20%;
  @media (max-width: 710px) {
    margin-left: 13%;
  }
  @media (max-width: 576px) {
    margin-left: 13%;
  }
  @media (max-width: 400px) {
    margin-left: 13%;
  }
`;

export const Text = styled.div`
  color: #ffffff;
  font-size: 56px;
  font-weight: 700;
  margin-bottom: 5px;
  position: relative;
  @media (max-width: 710px) {
    font-size: 3rem;
  }
  @media (max-width: 576px) {
    font-size: 1.7rem;
  }
  @media (max-width: 400px) {
    font-size: 1.7rem;
  }
`;

export const Button = styled.button`
  margin-top: 20px;
  width: 200px;
  height: 60px;
  font-size: 18px;
  font-weight: 600;
  border-radius: 50px;
  background-color: #F79F81;
  border: 4px solid #FFFFFF;
  color: #FFFFFF;
  @media (max-width: 710px) {
    margin-top: 15%;
    margin-left: 37px;
  }
  @media (max-width: 576px) {
    margin-left: 37px;
    margin-top: 15%;
  }
  @media (max-width: 400px) {
    margin-left: 37px;
    margin-top: 15%;
  }
`;