import React, { useState, useEffect, useRef } from 'react';
import { styled } from '@mui/material/styles';
import InputBase from '@mui/material/InputBase';
import SearchIcon from '@mui/icons-material/Search';
import CancelIcon from '@mui/icons-material/Cancel';

const Search = styled('div')(({ theme }) => ({
    position: 'relative',
    border: '1px solid #e5e5e5',
    borderRadius: '30px',
    backgroundColor: '#f9f9f9',
    width: '300px',
    height: '40px',
    padding: '0 14px',
    marginTop: '10px'
}));
  
const SearchIconWrapper = styled('div')(({ theme }) => ({
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    color: '#848484'
}));
  
const StyledInputBase = styled(InputBase)(({ theme }) => ({
    color: 'inherit',
    '& .MuiInputBase-input': {
      padding: theme.spacing(1, 0.5, 1, 0),
      paddingLeft: `calc(1em + ${theme.spacing(1)})`,
      transition: theme.transitions.create('width'),
      width: '260px',
    },
}));

function SearchBar({ onAddKeyword }) {
  const [keyword, setKeyword] = useState('')

  const handleKeyword = (e) => {
      setKeyword(e.target.value)
  }

  const handleEnter = (e) => {
    if (keyword && e.keyCode === 13) {
      //엔터일때 부모의 addkeyword에 전달
        onAddKeyword(keyword)
    }
  }

  const handleClearKeyword = () => {
      setKeyword('')
  }

  return (
    <div>
        <Search className="flex">
            <SearchIconWrapper>
                <SearchIcon />
            </SearchIconWrapper>
            <StyledInputBase
                placeholder="내역을 입력해주세요"
                inputProps={{ 'aria-label': 'search' }}
                value={keyword}
                onChange={handleKeyword}
                onKeyDown={handleEnter}
            />
            {keyword && <CancelIcon style={{ color:"#848484", fontSize:"20px", cursor:"pointer" }} onClick={handleClearKeyword} />}
        </Search> 
    </div>
  )
}

export default SearchBar