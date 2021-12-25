import React from 'react';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import globalStyles from '../css/AppStyles';
import MenuItem from '@material-ui/core/MenuItem';
import Box from '@material-ui/core/Box';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormLabel from '@material-ui/core/FormLabel';
import { FormControl } from '@material-ui/core';
import { FormGroup } from '@mui/material';
import Checkbox from "@material-ui/core/Checkbox";
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Dialog from '@mui/material/Dialog';
import Button from '@material-ui/core/Button';
import { ListItemText } from '@material-ui/core';
import ChipInput from 'material-ui-chip-input';
import SuccessIcon from '@material-ui/icons/VerifiedUserRounded';
import { colors } from '../css/theme-constants/ThemeConstants';

export const ContainerItem = (props) => {
  return (
    <Grid item shadows {...props} />
  )
}

export const WrapperContainer = (props) => {
  return (
    <Box component={Grid} boxShadow={3} container {...props} />
  )
}

export const OutlinedTextFieldWrapper = (props) => {
  const cssStyles = {
    globalStyles: globalStyles()
  };

  return (

    <TextField
      {...props}

      className={cssStyles.globalStyles.cssOutlinedInput}
      variant="outlined"
      InputLabelProps={{
        classes: {
          root: cssStyles.globalStyles.cssLabel,
          focused: cssStyles.globalStyles.cssFocused,
        },
      }}
      InputProps={{
        classes: {
          root: cssStyles.globalStyles.cssOutlinedInput,
          focused: cssStyles.globalStyles.cssFocused,
          notchedOutline: cssStyles.globalStyles.notchedOutline,
        },
      }}
    />
  )
}

export const FormErrorLabelWrapper = ({ errorMessage }) => {

  const cssStyles = {
    globalStyles: globalStyles()
  };

  return (
    <FormLabel className={cssStyles.globalStyles.formLabelErr} >{errorMessage}</FormLabel>
  )
}

export const OutlinedSelectWrapper = (props) => {

  const cssStyles = {
    globalStyles: globalStyles()
  };
  return (
    <TextField
      {...props}
      className={cssStyles.globalStyles.selectMenu}
      value={props.defaultSelect}
      variant="outlined"
      select
    >
      {
        props.menulist.map((item) => {
          return (
            <MenuItem key={item.id} value={item.id}>{item.name || (item.driveName + " (" + item.company + ")")}</MenuItem>
          );
        })
      }
    </TextField>
  )
}

/**
 * Wrapper for Outlined Multi Select
 * @param {*} props 
 * @returns 
 */
export function OutlinedMutliSelectWrapper(props) {

  const cssStyles = {
    globalStyles: globalStyles()
  };
  return (    <TextField      {...props}
      className={cssStyles.globalStyles.selectMenu}
      variant="outlined"
      select
      SelectProps={{
        multiple: true,
        value: props.selectedValue,
        onChange: props.onChange,
        margin: "none",
      }}
    >
      {
        props.menulist.map((item) => {
          return (
            <MenuItem key={item.id} value={item.id}>{item.name}</MenuItem>
          );
        })
      }
    </TextField>
  )
}


/**
 * Wrapper for Radio Group
 * @param {*} props  
 */
export const RadioGroupWrapper = (props) => {
  const cssStyles = {
    globalStyles: globalStyles()
  };
  return (
    <RadioGroup row={props.rowmode}>
      {
        props.options.map((item) => {
          return (
            <FormControlLabel
              {...props}
              label={item.name}
              labelPlacement='end'
              value={item.id}
              key={item.id}
              control={
                <Radio
                  classes={{
                    root: cssStyles.globalStyles.radio,
                    checked: cssStyles.globalStyles.checked
                  }}
                  key={item.id}
                  checked={item.id === props.checkvalue}
                />
              }
            />
          )
        })
      }
    </RadioGroup>
  )
}

export const CheckboxWrapper = (props) => {
  const cssStyles = {
    globalStyles: globalStyles()
  };
  return (
    <Checkbox
      {...props}
      classes={{
        root: cssStyles.globalStyles.checkbox,
        checked: cssStyles.globalStyles.checked
      }}
    />
  )
}

export const DialogWrapper = (props) => {
  const cssStyles = {
    globalStyles: globalStyles()
  };
  return (
    <Dialog
      {...props}
    >
      <DialogTitle className={cssStyles.globalStyles.dialogTitle}>
        {props.verifiedLogo &&
          <SuccessIcon style={{ color: colors.success, marginBottom: "-5px", border: "1px solid " + colors.success, borderRadius: "20px", marginRight: "5px" }} />
        }
        {props.title != null && props.title}
      </DialogTitle>
      <DialogContent style={{ fontSize: "20px" }}>
        <NewlineText text={props.message} />
      </DialogContent>
      <DialogActions>
        <Button
          onClick={() => {
            props.closeAction();
          }}
          className={cssStyles.globalStyles.dialogButton}>
          {props.buttonText}
        </Button>
      </DialogActions>

    </Dialog>
  )
}


export const ChipInputWrapper = (props) => {
  const cssStyles = {
    globalStyles: globalStyles()
  };
  return (
    <ChipInput
      {...props}
      variant="outlined"
      className={cssStyles.globalStyles.chipInput}
    />
  )
}

export const NewlineText = (props) => {
  const text = props.text;
  const newText = text.split('\n').map(str => <p>{str}</p>);
  return newText;
}