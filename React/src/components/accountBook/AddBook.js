import React, { useState, useEffect, useRef } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { store } from '../stores/Store';
import clsx from 'clsx';
import axios from 'axios';
import moment from 'moment';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import MenuItem from '@mui/material/MenuItem';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import DateTimePicker from '@mui/lab/DateTimePicker';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import { expenditureOptions, incomeOptions, transferOptions } from './categoryData';
import Loader from "./../common/Loader"
import Modal from "./../common/Modal"

const defaultMenuProps = {
    PaperProps: {
      style: {
        height: 200,
        overflow: "auto",
        boxShadow: "none",
        border: "1px solid #D8D8D8",
      },
    },
};

const AddBook = () => {
    let history = useHistory();
    const [loading, setLoading] = useState(false);
    const [open, setOpen] = useState(false);   
    const [notice, setNotice] = useState();                                                                         //모달 멘트 설정
    const [price, setPrice] = useState(0);                                                                          // 가계부 금액
    const [accountBookType, setAccountBookType] = useState("");                                                     // 가계부 내역 유형
    const [expenditureType, setExpenditureType] = useState(null);                                                   // 지출 유형
    const [incomeType, setIncomeType] = useState(null);                                                             // 수입 유형
    const [transferType, setTransferType] = useState(null);                                                         // 이체 유형
    const [searchDateType, setSearchDateType] = useState(moment(new Date()).format('YYYY-MM-DD HH:mm:ss'));         // 날짜
    const [account, setAccount] = useState('');                                                                     // 거래처명
    const API_SERVER = "https://gateway.bitbank.click" ;

    // 가계부 내역 유형
    const selectType = (e, value) => {
        e.preventDefault();
        setAccountBookType(value);
    }

    // 날짜 선택
    const handleSelectEndDate = (newValue) => {
        const date = moment(newValue).format('YYYY-MM-DD HH:mm:ss');    // 날짜 포맷
        setSearchDateType(date);
    };

    //가계부 세부 내역 유형
    const selectCategory = (e) => {
        if(accountBookType === "P"){
            setExpenditureType(e.target.value);
        } else if(accountBookType === "I"){
            setIncomeType(e.target.value);
        } else if(accountBookType === "T"){
            setTransferType(e.target.value);
        } 
    }

     // 가계부 목록 추가
     const addAccountBook = async() => {
        try {
                const response = await axios.post( API_SERVER +'/account-book/write', {
                    memberId : store.memberId,
                    accountBookType : accountBookType,
                    expenditureType : expenditureType,
                    incomeType : incomeType,
                    transferType : transferType,
                    accountName  : account,
                    price : price, 
                    createdDate : searchDateType,
                },
                {
                    headers: { 
                        Authorization : store.accessToken
                    },
                });
                console.log( '가계부 목록 추가', response.data )
                if( response.status === 200 && response.data.rt === 201 ){    
                    history.push('/books')
                }    
        } catch (e) {
            console.log( 'e', e.response );
        }
    }

    // 유효성 검사
    const handleValid = (e) => {
        e.preventDefault();
        if (!account) {
            setNotice("거래처명을 입력하세요")
            setOpen(true);
        } else if (price == 0) {
            setNotice("금액을 입력하세요.")
            setOpen(true);
        } else if (!accountBookType) {
            setNotice("분류를 선택하세요.")
            setOpen(true);
        } else if (!expenditureType && !incomeType && !transferType ) {
            setNotice("카테고리를 선택하세요.")
            setOpen(true);
        } else {
            addAccountBook();
        }
    }

    const handleClose = (value) => {
        setOpen(value);
    }
    
    return (
        <div>
            <div className={clsx('item_center','subtitle_2')}>가계부를 추가해보세요.</div>
            <div className="info">(※금액 입력 시 숫자만 입력하세요.※)</div>
            <form className={clsx('item_center')} noValidate autoComplete="off">
                <Grid container>
                    <Grid item xs={12} style={{ justifyContent: 'center' }}>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>거래처명</div>
                            <div>
                                <input type="text" placeholder="거래처명을 입력하세요" className={clsx('form_txt_account','margin_5')} name="account" value={account} onChange={(e)=>setAccount(e.target.value)}/>
                            </div>
                        </div>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>금액</div>
                            <div>
                                <input type="number" id="number" placeholder="금액을 입력하세요" className={clsx('form_txt_account','margin_5')} name="price" value={price} onChange={(e) => setPrice(e.target.value)}/>
                            </div>
                        </div>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>분류</div>
                            <div className='center'>
                                <button className={clsx("categoryBtn", accountBookType==="P" && "clickCategoryBtn")}  onClick={(e) => selectType(e, "P")}>지출</button>
                                <button className={clsx("categoryBtn", accountBookType==="I" && "clickCategoryBtn")}  onClick={(e) => selectType(e, "I")}>수입</button>
                                <button className={clsx("categoryBtn", accountBookType==="T" && "clickCategoryBtn")}  onClick={(e) => selectType(e, "T")}>이체</button>
                            </div>
                        </div>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>카테고리</div>
                            <div>
                                {accountBookType ? (
                                // 분류 선택 시에 카테고리 활성화
                                <FormControl fullWidth style={{border: "1px solid #FFFFFF", borderRadius:"10px", margin: "0 5px 0 -10px"}}>
                                    <Select
                                        value={accountBookType === "P" ? expenditureType : accountBookType === "I" ? incomeType : transferType}
                                        onChange={(e)=>selectCategory(e)}
                                        style={{width: "210px", borderRadius:"10px"}}
                                        MenuProps={defaultMenuProps}
                                    >
                                    { accountBookType === "P" ? (
                                        expenditureOptions.map((option, index) => (
                                        <MenuItem
                                            value={option.id}
                                            key={"option"+index+1}
                                            selected={index === expenditureType}
                                        >
                                            {option.data}
                                        </MenuItem>
                                    ))
                                    ) : ( accountBookType === "I" ? (
                                        incomeOptions.map((option, index) => (
                                            <MenuItem
                                                value={option.id}
                                                key={"option"+index+1}
                                                selected={index === incomeType}
                                            >
                                                {option.data}
                                            </MenuItem>
                                        ))
                                    ):(
                                        transferOptions.map((option, index) => (
                                            <MenuItem
                                                value={option.id}
                                                key={"option"+index+1}
                                                selected={index === transferType}
                                            >
                                                {option.data}
                                            </MenuItem>
                                        ))
                                    ))}
                                    </Select>
                                </FormControl>
                                ):(
                                    // 분류 선택안할 시에 카테고리 비활성화
                                    <FormControl fullWidth disabled style={{border: "1px solid #FFFFFF", borderRadius:"10px", margin: "0 5px 0 -10px"}}>
                                        <InputLabel style={{zIndex: 0}}>카테고리를 선택하세요.</InputLabel>
                                        <Select
                                            style={{width: "210px", borderRadius:"10px"}}
                                        >
                                        </Select>
                                    </FormControl>
                                )}
                            </div>
                        </div>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>날짜</div>
                            <div className='center' style={{margin: "0 5px 0 -10px"}}>
                                <LocalizationProvider dateAdapter={AdapterDateFns}>
                                    <DateTimePicker
                                        value={searchDateType}
                                        onChange={handleSelectEndDate}
                                        renderInput={(params) => <TextField {...params} />}
                                    />
                                </LocalizationProvider>
                            </div>
                        </div>
                    </Grid>

                    <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                        <button className={clsx('btn_1', 'margin_40')} onClick={handleValid}>
                            저장
                        </button>
                    </Grid>
                    <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                          <Loader loading={loading} />
                    </Grid>  
                </Grid>
            </form>
            {open && (
                <Modal notice={notice} onClose={handleClose}/>
            )}
        </div>
    );
}

export default AddBook;
