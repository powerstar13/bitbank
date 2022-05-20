import React, { useState, useEffect, useRef } from 'react';
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
import History from './SearchHistory.js'
import SearchBar from './SearchBar.js'
import { expenditureOptions, incomeOptions, transferOptions } from './categoryData';


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

const BookSearch = () => {
    let [loading, setLoading] = useState(false);     
    const [searchList, setSearchList] = useState(false);
    const [accountBookType, setAccountBookType] = useState("");                 // 가계부 내역 유형
    const [expenditureType, setExpenditureType] = useState([]);                 // 지출 유형
    const [incomeType, setIncomeType] = useState([]);                           // 수입 유형
    const [transferType, setTransferType] = useState([]);                       // 이체 유형
    const [searchDateType, setSearchDateType] = useState("M");                  // 기간  
    const [searchStartDate, setSearchStartDate] = useState();                     // 시작 날짜
    const [searchEndDate , setSearchEndDate] = useState();                      // 종료 날짜
    const [nowKeyword, setNowKeyword] = useState("");                             // 현재 검색어
    const [accountbookList, setAccountBookList] = useState([]); 
    const [keywords, setKeywords] = useState(                                   // 검색어
        JSON.parse(localStorage.getItem('keywords') || '[]'),
    )

    const API_SERVER = "https://gateway.bitbank.click" ;

    useEffect(() => {
        localStorage.setItem('keywords', JSON.stringify(keywords))
    }, [keywords])


    //검색어 추가
    const handleAddKeyword = (keyword) => {
        setNowKeyword(keyword);
        if(keyword !== ''){
            const newKeyword = {
                id: Date.now(),
                keyword: keyword,                 //현재 입력한 검색어
            }
            setKeywords([newKeyword, ...keywords])
        }
    }

    //검색어 삭제
    const handleRemoveKeyword = (id) => {
        const nextKeyword = keywords.filter((thisKeyword) => {
            return thisKeyword.id != id
        })
        setKeywords(nextKeyword)
    }
    
    //검색어 전체 삭제
    const handleClearKeywords = () => {
        setKeywords([])
    }

    // 가계부 내역 유형
    const selectType = (e, value) => {
        e.preventDefault();
        setAccountBookType(value);
    }

    // 시작 날짜 선택
    const handleSelectStartDate = (newValue) => {
        const date = moment(newValue).format('YYYY-MM-DD HH:mm:ss');    // 날짜 포맷
        setSearchStartDate(date);
    };

    // 종료 날짜 선택
    const handleSelectEndDate = (newValue) => {
        const date = moment(newValue).format('YYYY-MM-DD HH:mm:ss');    // 날짜 포맷
        setSearchEndDate(date);
    };

    //가계부 세부 내역 유형
    const selectCategory = (e) => {
        if(accountBookType === "P"){
            setExpenditureType([e.target.value]);
            setIncomeType([]);
            setTransferType([]);
        } else if(accountBookType === "I"){
            setIncomeType([e.target.value]);
            setExpenditureType([]);
            setTransferType([]);
        } else if(accountBookType === "T"){
            setTransferType([e.target.value]);
            setIncomeType([]);
            setExpenditureType([]);
        } 
    }

    // 가계부 목록 조회
    const getAccountBook = async(e) => {
        e.preventDefault();
        try {
                const response = await axios.post( API_SERVER +'/account-book/search', {
                    memberId : store.memberId,
                    searchKeyword : nowKeyword,
                    searchDateType : searchDateType,
                    accountBookType : accountBookType,
                    searchStartDate : searchStartDate,
                    searchEndDate : searchEndDate,
                    expenditureType : expenditureType,
                    incomeType : incomeType,
                    transferType : transferType,
                },
                {
                    headers: { 
                        Authorization : store.accessToken
                    },
                });
                if( response.status === 200 && response.data.rt === 200 ){    
                    if( response.data.accountBookSearchByDailyDTOList.length === 0 ){
                        setSearchList(false);
                    } else setSearchList(true);
                    setAccountBookList(response.data.accountBookSearchByDailyDTOList)
                }    
        } catch (e) {
            console.log( 'e', e.response );
        }
    }

    function comma(str) {
        str = String(str);
        var minus = str.substring(0, 1);
        
        str = str.replace(/[^\d]+/g, '');
        str = str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
    
        if(minus == "-") str = "-"+str;
        
        return str;
    }
    

    // 금액 포맷
    const getValue = (value) => {
        return comma(value);
    }
    
    
    return (
        <div className='filter'>
            <div className={clsx('item_center')}> 
                <SearchBar onAddKeyword={handleAddKeyword}></SearchBar>
            </div>
            <Grid item xs={12} style={{ justifyContent: 'center'}}> 
                <div>
                    <History
                        keywords={keywords}
                        onClearKeywords={handleClearKeywords}
                        onRemoveKeyword={handleRemoveKeyword}
                    />
                </div>    
            </Grid>
            
            <form className={clsx('item_center')} noValidate autoComplete="off">
                <Grid container>
                    <Grid item xs={12} style={{ justifyContent: 'center' }}>
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
                            <div className='form_name'>기간</div>
                            <div className='center'>
                                <FormControl fullWidth style={{border: "1px solid #FFFFFF", borderRadius:"10px", margin: "0 5px 0 -10px"}}>
                                    <Select
                                            value={searchDateType}
                                            onChange={(e)=> setSearchDateType(e.target.value)}
                                            style={{width: "230px", borderRadius:"10px"}}
                                            MenuProps={defaultMenuProps}
                                    >
                                        <MenuItem value={"A"}>전체</MenuItem>
                                        <MenuItem value={"W"}>이번 주</MenuItem>
                                        <MenuItem value={"M"}>이번 달</MenuItem>
                                        <MenuItem value={"Y"}>이번 년도</MenuItem>
                                        <MenuItem value={"M3"}>최근 3개월</MenuItem>
                                        <MenuItem value={"M6"}>최근 6개월</MenuItem>
                                        <MenuItem value={"S"}>기간 설정</MenuItem>
                                    </Select>
                                </FormControl>
                            </div>
                        </div>
                        {searchDateType === "S" && (
                            <>
                                <div className={clsx('between', 'margin_20')}>
                                    <div className='form_name'>시작 날짜</div>
                                    <div className='center' style={{margin: "0 5px 0 -10px"}}>
                                        <LocalizationProvider dateAdapter={AdapterDateFns}>
                                            <DateTimePicker
                                                value={searchStartDate}
                                                onChange={handleSelectStartDate}
                                                renderInput={(params) => <TextField {...params} />}
                                                format="YYYY-MM-DD HH:mm:ss"
                                            />
                                        </LocalizationProvider>
                                    </div>
                                </div>
                                <div className={clsx('between', 'margin_20')}>
                                    <div className='form_name'>종료 날짜</div>
                                    <div className='center' style={{margin: "0 5px 0 -10px"}}>
                                        <LocalizationProvider dateAdapter={AdapterDateFns}>
                                            <DateTimePicker
                                                value={searchEndDate}
                                                onChange={handleSelectEndDate}
                                                renderInput={(params) => <TextField {...params} />}
                                                format="YYYY-MM-DD HH:mm:ss"
                                            />
                                        </LocalizationProvider>
                                    </div>
                                </div>
                            </>
                        )}
                    </Grid>
                    <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                        <button className={clsx('btn_1', 'margin_40')} onClick={getAccountBook}>
                            조회
                        </button>
                    </Grid>
                </Grid>
            </form>
                {!searchList ? (
                    <div className='center'>  
                        <div className='info4' style={{marginBottom:"20px"}}>검색된 내용이 없습니다.</div>
                    </div>
                ):( 
                    accountbookList.map((data, i) => (
                        <div className='books_paper'>
                            <div className={clsx('books_data', 'between')}>  
                                <div className='info4'>{data.date} {data.day}</div>
                                <div className='info4'>{getValue(data.accountBookTotalByDaily)}</div>
                            </div>
                            <hr/>
                            {data.accountBookInfoDTOList && data.accountBookInfoDTOList.map((o, index) =>(
                                <div className={clsx('books_data', 'between')}>  
                                    <div className='info5'>{o.accountBookInfo}</div>
                                    <div className='books_price'>{getValue(o.accountMoney)}원</div>
                                </div>
                            ))}
                        </div>    
                    ))
                )}   
        </div>
    );
}

export default BookSearch;
