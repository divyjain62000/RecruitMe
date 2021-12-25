import React from 'react';
import globalStyles from '../css/AppStyles';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Dialog from '@mui/material/Dialog';
import LoaderGIF from '../../../assets/loader/loaderGIF.gif'



export const LoaderComponent = (props) => {
    const cssStyles = {
        globalStyles: globalStyles(),
    };

    return (

        <Dialog
            {...props}
            PaperProps={{
                style: {
                  backgroundColor: 'transparent',
                  boxShadow: 'none',
                },
              }}
        >

            <DialogTitle >
                <img src={LoaderGIF} width="250px" />
            </DialogTitle>

        </Dialog>
    )
}