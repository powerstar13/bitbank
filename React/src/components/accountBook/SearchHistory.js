import React from 'react'
import clsx from 'clsx';
import Box from '@mui/material/Box';
import CloseIcon from '@mui/icons-material/Close';


function History({ keywords, onRemoveKeyword, onClearKeywords }) {

    if (keywords.length === 0) {
        return <div className={clsx('center', 'info4')}>최근 검색된 기록이 없습니다.</div>
    }


    return (
      <div>
        {/* <div className='paper'> */}
          <div className={clsx('info4','between', 'margin_10')}>
              <div>최근 검색어</div>
              <div className='reset_btn' onClick={onClearKeywords}>전체삭제</div>
          </div>
          <div className={clsx('flex', 'overflow_x')}>
              {keywords.map(({ id, keyword }) => {
                  return (
                      <div key={id} className={clsx('info3', 'between', 'search_btn')}>
                          <Box>{keyword}</Box>
                          <CloseIcon style={{ color:"#FFFFFF", fontSize:"18px", cursor:"pointer" }}  onClick={() => onRemoveKeyword(id)}>삭제</CloseIcon>
                      </div>
                  )
              })}
          </div>
        {/* </div> */}
      </div>
    )
}

export default History