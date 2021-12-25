import { makeStyles } from '@material-ui/core';
import { BorderBottom } from '@material-ui/icons';
import { padding } from '@mui/system';
import { colors } from './theme-constants/ThemeConstants';
import { fonts } from './theme-constants/ThemeConstants';


const drawerStyles = makeStyles(theme => ({
  mainContainer: {
    flexGrow: 1,
  },
  paper: {  
        background: colors.white,
        border: "2px solid "+colors.white,
  },
  divider: {
    background: colors.lightDark,
    borderBottom: "1px solid "+colors.lightDark
  },
  dividerLine: {
    background: colors.lightDark,
  },
  listItem: {
    background: colors.white,
    padding: "15px",
    cursor: "pointer",
    '&:hover': {
      background: colors.grey,
    }
  },
  activeListItem: {
    background: colors.grey,
    padding: "15px",
    cursor: "cursor",
  },
  activeListItemText: {
    marginLeft: "-20px",
    color: colors.black,
    background: colors.grey,
    cursor: "default",
  },
  activeListItemIcon: {
    marginLeft: "-5px",
    color: colors.black
  },
  listItemText: {
    marginLeft: "-20px",
    color: colors.lightDark,
    fontSize: "1.5rem"
  },
  listItemIcon: {
    marginLeft: "-5px",
    color: colors.lightDark,
    
  },
  userTitle: {
    marginLeft: "-10px",
    color: colors.black,
  },
  userIcon: {
    color: colors.black,
  },
  userArea: {
    background: colors.lightDark,
    marginTop: "-12px",
    borderTop: "4px solid "+colors.white,
    borderBottom: "2px solid "+colors.black,
    marginRight: "10px",
    padding: "25px",
    color: colors.black,
  }


}));


export default drawerStyles;