import React from 'react'
import Container from '@mui/material/Container';
import Section1 from './main/Section1';
import Section2 from './main/Section2';
import Section3 from './main/Section3';
import Section4 from './main/Section4';

const Home = () => {

    return (
        <>
            <Container maxWidth="sm">
                <Section1 />
                <Section2 />
                <Section3 />
                <Section4 />
            </Container>
        </>
    );

}

export default Home;
