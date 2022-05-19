import React from 'react';
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';
import Box from '@mui/material/Box';


const Modal = ({notice, onClose}) => {

    const handleClose = () => {
        onClose(false)
    }

    return (
        <div>
            <div className="container">
                <div className="popup-wrap" > 
                    <div className="popup">	
                        <div className="popup-body">
                            <div className="body-content">
                                <div className="body-titlebox">
                                    <ErrorOutlineIcon style={{fontSize: '47px'}}/>
                                </div>
                                <div className="body-contentbox">
                                    {notice}
                                </div>
                            </div>
                        </div>
                        <Box className="popup-footer" onClick={handleClose}>
                            <Box className="pop-btn">확인</Box>
                        </Box>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Modal;

