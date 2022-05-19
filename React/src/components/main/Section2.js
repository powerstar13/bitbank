import React from 'react'
import styled from "styled-components";
import { Zoom } from "react-awesome-reveal";
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import card from './../img/card1.png'; 
import Grid from '@mui/material/Grid';

const Section2 = () => {

    return (
        <>
            <Container maxWidth="sm">
                <Box display="flex" justifyContent="center" alignItems="top">
                    <div>
                         <Grid container spacing={2}>
                            <Grid item xs={12} >
                                <Wrapper>
                                    <TextDiv>
                                      <Zoom triggerOnce={false} duration={1500}>
                                          <Text>나만의 맞춤 혜택 카드</Text>
                                          <Text>내게 딱 맞는 카드는?</Text>
                                      </Zoom>
                                    </TextDiv>
                                    <div>
                                      <Zoom triggerOnce={false} duration={1500}>
                                          <Image src={card} alt="card"/>
                                      </Zoom>
                                    </div>
                                </Wrapper>
                            </Grid>
                        </Grid>
                    </div>
                </Box>
            </Container>
        </>
    );

}

export default Section2;

export const Wrapper = styled.div`
  background-color: #EFF8FB;
  padding-top: 75px;
  height: 100vh;
  width: 100vw;
  padding: 0 20px;
  align-items: center;
  display: flex;
  margin-top: -50px;
  @media (max-width: 710px) {
    display: block;
  }
  @media (max-width: 576px) {
    display: block;
  }
  @media (max-width: 400px) {
    display: block;
    padding-top: 20px;
    width: 90vw;
  }
`;

export const TextDiv = styled.div`
  margin-left: 20%;
  @media (max-width: 1100px) {
    margin-left: 13%;
  }
  @media (max-width: 710px) {
    margin-left: -100px;
  }
  @media (max-width: 576px) {
    margin-left: 17%;
  }
  @media (max-width: 400px) {
    margin-left: 14%;
  }
`;

export const Text = styled.div`
  color: #2167C2;
  font-size: 45px;
  font-weight: 700;
  margin-bottom: 5px;
  @media (max-width: 770px) {
    font-size: 1.7rem;
    margin: 0 20px 0 -20px;
  }
  @media (max-width: 576px) {
    font-size: 1.7rem;
    padding-top: 10px;
  }
  @media (max-width: 400px) {
    text-align: center;
    font-size: 1.6rem;
    padding-top: 10px;
  }
`;

export const Image = styled.img`
  margin-left: 200px;
  border-radius: 45px;
  height: 100%;
  @media (max-width: 1300px) {
    margin-left: 80px;
    height: 90vh;
    width: 25vw;
  }
  @media (max-width: 770px) {
    margin: 0 20px 0 -10px;
    display: block;
    height: 85vh;
    width: 55vw;
  }
  @media (max-width: 576px) {
    margin: 20px 0 0 9%;
    width: 85vw;
    height: 82vh;
  }
  @media (max-width: 400px) {
    margin-left: 3%;
    width: 85vw;
    height: 81vh;
  }
`;
