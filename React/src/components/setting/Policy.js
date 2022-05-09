import React, { useState, useEffect, useRef } from 'react';
import clsx from 'clsx'
import { makeStyles } from '@mui/styles';
import { Link, useHistory } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import { policyData, termsData } from './PolicyData';

const useStyles = makeStyles((theme) => ({
    txtfieldBox: {                    
        whiteSpace: "pre-wrap",
        marginBottom: "15px",
        '& .MuiInputBase-root': {
          borderRadius: "20px",
        },
        "& .txtField": {
          width: "300px",
          fontSize: "11px",
          fontFamily: "Spoqa Han Sans Neo",
          color: "#333333",
          lineHeight: "14px",
        },
        '& .MuiOutlinedInput-root':{
            padding: '16.5px 10px'
        }
    },
}));


const Policy = () => {
    const cls = useStyles();
    let history = useHistory();
    let [loading, setLoading] = useState(false);   
    const [terms, setTerms] = useState(false);   
    const [policy, setPolicy] = useState(false);
    
    const goBack = (e) => {
        history.goBack();
    };

    return (
        <div>
            {/* <Loader loading={loading} /> */}
            <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                <ArrowBackIosIcon style={{margin: '10px 20px 0 -30px', color: '#6E6E6E'}} onClick={goBack}/>
                <div className='subtitle_1'>약관 및 정책</div>
            </Grid>
            <Grid container style={{marginTop:"50px"}}>
                <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                    <button className={clsx('btn_1', 'margin_30')} onClick={() => setTerms(!terms)}>
                        이용약관
                    </button>
                </Grid>
                <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                    { terms && (
                        <Box className={cls.txtfieldBox}>
                            <TextField
                                multiline
                                rows={5}
                                defaultValue={termsData}
                                variant="outlined"
                                InputProps={{
                                    readOnly: true,
                                }}
                                id="txtField"
                            />
                        </Box>
                    )}
                </Grid>
                <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                    <button className={clsx('btn_2', 'margin_30')} onClick={() => setPolicy(!policy)}>
                        개인정보처리방침
                    </button>
                </Grid>
                <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                { policy && (
                    <Box className={cls.txtfieldBox}>
                        <TextField
                            multiline
                            rows={5}
                            defaultValue={policyData}
                            variant="outlined"
                            InputProps={{
                                readOnly: true,
                            }}
                            id="txtField"
                        />
                    </Box>
                )}
                </Grid>
            </Grid>
        </div>
    );
}

export default Policy;