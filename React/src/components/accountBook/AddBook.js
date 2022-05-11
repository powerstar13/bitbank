import React, { useState, useEffect, useRef } from 'react';
import { makeStyles } from '@mui/styles';
import clsx from 'clsx';
import { Link, useHistory } from 'react-router-dom';
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


const useStyles = makeStyles((theme) => ({
    root: {
        display: "flex",
    },
}));


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
    const cls = useStyles();
    const [price, setPrice] = useState(0);                                  // 가계부 금액
    const [accountBookType, setAccountBookType] = useState(0);              // 가계부 내역 유형
    const [expenditureType, setExpenditureType] = useState(["0"]);          // 지출 유형
    const [incomeType, setIncomeType] = useState(["0"]);                    // 수입 유형
    const [transferType, setTransferType] = useState(["0"]);                // 이체 유형
    const [searchDateType, setSearchDateType] = useState(new Date());       // 날짜
    const [account, setAccount] = useState('');                             // 거래처명


    //가계부 금액 입력시 천 단위마다 콤마
    const inputPriceFormat = (str) => {
        const comma = (str) => {
          str = String(str);
          return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, "$1,");
        };
        const uncomma = (str) => {
          str = String(str);
          return str.replace(/[^\d]+/g, "");
        };
        return comma(uncomma(str));
    };

    // 가계부 내역 유형
    const selectType = (e, value) => {
        e.preventDefault();
        setAccountBookType(value);
    }

    // 날짜 선택
    const selectDate= (newValue) => {
        setSearchDateType(newValue);
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

    return (
        <div>
            {/* <Loader loading={loading} /> */}
            <div className={clsx('item_center','subtitle_2')}>가계부를 추가해보세요.</div>
            <div className="info">(※금액 입력 시 숫자만 입력하세요.※)</div>
            <form className={cls.root} noValidate autoComplete="off">
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
                                <input type="text" id="number" placeholder="금액을 입력하세요" className={clsx('form_txt_account','margin_5')} name="price" value={price} onChange={(e) => setPrice(inputPriceFormat(e.target.value))}/>
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
                                            disabled={index === 0}
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
                                                disabled={index === 0}
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
                                                disabled={index === 0}
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
                                        <InputLabel>카테고리를 선택하세요.</InputLabel>
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
                                        onChange={selectDate}
                                        renderInput={(params) => <TextField {...params} />}
                                    />
                                </LocalizationProvider>
                            </div>
                        </div>
                    </Grid>

                    <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                        <Link to='/cards/profits'>
                            <button className={clsx('btn_1', 'margin_40')}>
                                저장
                            </button>
                        </Link>
                    </Grid>
                </Grid>
            </form>
        </div>
    );
}

export default AddBook;
