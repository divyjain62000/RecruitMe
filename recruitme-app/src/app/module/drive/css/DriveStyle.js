import { makeStyles } from '@material-ui/core';
import { colors } from '../../../theme/global/css/theme-constants/ThemeConstants';
import { fonts } from '../../../theme/global/css/theme-constants/ThemeConstants';

const driveStyles = makeStyles(theme => ({
  formContainer: {
    paddingRight: "30px",
    paddingLeft: "30px",
    paddingTop: "20px",
    marginTop: "50px",
    background: colors.white
  },
  driveDetailTitle: {
    fontWeight: "bold",
    color: colors.dark,
    border: "none",
    fontSize: "1.5rem"
  },
  driveDetailOption: {
    fontWeight: "bold",
    color: colors.text2,
    border: "none",

  },
  driveDetail: {
    color: colors.text2,
    border: "none",
  },
  driveDetailTitleRow: {
    border: "none",
    background: colors.dark,
    color: colors.text1,
    fontWeight: "bold"
  },
  driveDetailCloseIcon: {
    width: "30px",
    height: "30px",
    color: colors.black,
    cursor: "pointer"
  },
  applyButton: {
    background: colors.success,
    color: colors.white,
    "&:hover": {
      background: colors.success,
      color: colors.black,
    }
  }
}));

export default driveStyles;
