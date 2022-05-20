import React, { useState, useEffect, useRef } from 'react';
import { Link, useHistory  } from 'react-router-dom';
import clsx from 'clsx';
import axios from 'axios';
import Grid from '@mui/material/Grid';
import Loader from "./../common/Loader"


const Poll = () => {
    let history = useHistory();
    let [loading, setLoading] = useState(false);   
    const [consumptionList, setConsumptionList] = useState({    // 소비패턴 리스트
        consumptionAmountCost : '',                             // 월간 사용 금액    
        transportationCost : '',                                // 교통비
        phoneCost : '',                                         // 통신비
        eatCost : '',                                           // 외식비
        drinkCost : '',                                         // 카페비
        shoppingCost : '',                                      // 쇼핑비
        oilCost : '',                                           // 주유비
        bookCost : '',                                          // 도서비
        insuranceCost : '',                                     // 보험비
        marketCost : '',                                        // 대형마트비
        convenienceStoreCost : '',                              // 편의점비
    });
    const API_SERVER = "https://gateway.bitbank.click" ;

    const { consumptionAmountCost, transportationCost, phoneCost, eatCost, drinkCost, shoppingCost, oilCost, bookCost, insuranceCost, marketCost, convenienceStoreCost } = consumptionList;

    const handleChange = (e) => {
        const { value, name } = e.target;
        setConsumptionList({
            ...consumptionList,
            [name]: inputPriceFormat(value)
        });
    };

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
    

    const getCardRecommendation = async( e, consumptionList ) => {
        e.preventDefault();
        try {
                const response = await axios.get( API_SERVER +'/card/recommendation-list', 
                {
                    params: {
                        consumptionAmountCost : consumptionList.consumptionAmountCost,
                        transportationCost : consumptionList.transportationCost,
                        phoneCost : consumptionList.phoneCost,
                        eatCost : consumptionList.eatCost,
                        drinkCost : consumptionList.drinkCost ,
                        shoppingCost : consumptionList.shoppingCost ,
                        oilCost : consumptionList.oilCost ,
                        bookCost : consumptionList.bookCost ,
                        insuranceCost : consumptionList.insuranceCost ,
                        marketCost : consumptionList.marketCost ,
                        convenienceStoreCost : consumptionList.convenienceStoreCost ,
                    }
                });
                if( response.status === 200 && response.data.rt === 200 ){   
                    history.push({
                        pathname: "/cards/profits",
                        state: { cardList: response.data.cardDTOList }
                    })
                }    
        } catch (e) {
            console.log( 'e', e.response );
        }
    }


    return (
        <div>
            <div className={clsx('item_center','subtitle_2')}>나에게 가장 좋은 카드는?</div>
            <div className="subtitle_3">소비패턴을 더 정확하게 입력할수록, <br/>최고의 카드를 찾을 확률이 높아집니다!</div>
            <div className="info">(※숫자만 입력하세요.※)</div>
            <form className='flex' noValidate autoComplete="off">
                <Grid container>
                    <Grid item xs={12} style={{ justifyContent: 'center' }}>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>월 평균 사용금액</div>
                            <div>
                                <input type="text" placeholder="월 평균 사용금액을 입력하세요" className={clsx('form_txt','margin_5')} name="consumptionAmountCost" value={consumptionAmountCost} onChange={handleChange}/>
                            </div>
                        </div>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>대중교통 금액</div>
                            <div>
                                <input type="text" placeholder="한달 대중교통 금액을 입력하세요" className={clsx('form_txt','margin_5')} name="transportationCost" value={transportationCost} onChange={handleChange}/>
                            </div>
                        </div>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>휴대폰 요금</div>
                            <div>
                                <input type="text" placeholder="한달 휴대폰 요금을 입력하세요" className={clsx('form_txt','margin_5')} name="phoneCost" value={phoneCost} onChange={handleChange}/>
                            </div>
                        </div>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>식사</div>
                            <div>
                                <input type="text" placeholder="한달 외식비를 입력하세요" className={clsx('form_txt','margin_5')} name="eatCost" value={eatCost} onChange={handleChange}/>
                            </div>
                        </div>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>카페</div>
                            <div>
                                <input type="text" placeholder="한달 카페비용을 입력하세요" className={clsx('form_txt','margin_5')} name="drinkCost" value={drinkCost} onChange={handleChange}/>
                            </div>
                        </div>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>쇼핑</div>
                            <div>
                                <input type="text" placeholder="한달 쇼핑금액을 입력하세요" className={clsx('form_txt','margin_5')} name="shoppingCost" value={shoppingCost} onChange={handleChange}/>
                            </div>
                        </div>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>주유</div>
                            <div>
                                <input type="text" placeholder="한달 주유비를 입력하세요" className={clsx('form_txt','margin_5')} name="oilCost" value={oilCost} onChange={handleChange}/>
                            </div>
                        </div>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>서점</div>
                            <div>
                                <input type="text" placeholder="한달 서점비를 입력하세요" className={clsx('form_txt','margin_5')} name="bookCost" value={bookCost} onChange={handleChange}/>
                            </div>
                        </div>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>보험</div>
                            <div>
                                <input type="text" placeholder="한달 보험료를 입력하세요" className={clsx('form_txt','margin_5')} name="insuranceCost" value={insuranceCost} onChange={handleChange}/>
                            </div>
                        </div>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>대형마트</div>
                            <div>
                                <input type="text" placeholder="한달 대형마트 쇼핑금액을 입력하세요" className={clsx('form_txt','margin_5')} name="marketCost" value={marketCost} onChange={handleChange}/>
                            </div>
                        </div>
                        <div className={clsx('between', 'margin_20')}>
                            <div className='form_name'>편의점</div>
                            <div>
                                <input type="text" placeholder="한달 편의점 사용금액을 입력하세요" className={clsx('form_txt','margin_5')} name="convenienceStoreCost" value={convenienceStoreCost} onChange={handleChange}/>
                            </div>
                        </div>
                    </Grid>

                    <Grid item xs={12} style={{ display: 'flex', justifyContent: 'center'}}>
                        {/* <Link to={{
                            pathname:'/cards/profits',
                            cardList:cardList
                        }}> */}
                            <button className={clsx('btn_1', 'margin_30')} onClick={(e) => getCardRecommendation(e, consumptionList)}>
                                완료
                            </button>
                        {/* </Link> */}
                    </Grid>
                </Grid>
            </form>
        </div>
    );
}

export default Poll;
