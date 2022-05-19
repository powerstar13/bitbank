import React, { useState, useEffect, useRef } from 'react';
import { Link, useHistory } from 'react-router-dom';
import clsx from 'clsx'
import Grid from '@mui/material/Grid';




const Matching = () => {

    return (
        <div>
            <Grid container style={{marginTop:"80px"}}>
                <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                    <div className='margin_40'>
                        <Link to='/cards/questions'>
                            <button className={clsx('btn_1')}>
                                나의 BEST 카드 찾기
                            </button>
                        </Link>
                    </div>
                </Grid>
                <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                    <div className='margin_40'>
                        <Link to='/cards/ranking'>
                            <button className={clsx('btn_2')}>
                            인기카드 Top 10
                            </button>
                        </Link>
                    </div>
                </Grid>
            </Grid>
        </div>
    );
}

export default Matching;
