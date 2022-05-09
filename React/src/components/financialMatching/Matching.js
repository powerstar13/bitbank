import React, { useState, useEffect, useRef } from 'react';
import clsx from 'clsx'
import { makeStyles } from '@mui/styles';
import { Link, useHistory } from 'react-router-dom';
import Grid from '@mui/material/Grid';


const useStyles = makeStyles((theme) => ({
}));


const Matching = () => {
    const classes = useStyles();
    let [loading, setLoading] = useState(false);   

    return (
        <div>
            {/* <Loader loading={loading} /> */}
  
            <Grid container style={{marginTop:"80px"}}>
                <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                    <Link to='/cards/questions'>
                        <button className={clsx('btn_1', 'margin_40')}>
                            나의 BEST 카드 찾기
                        </button>
                    </Link>
                </Grid>
                <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                    <Link to='/cards/ranking'>
                        <button className={clsx('btn_2', 'margin_40')}>
                            인기카드 Top 10
                        </button>
                    </Link>
                </Grid>
            </Grid>
        </div>
    );
}

export default Matching;
