import { makeStyles } from '@material-ui/core';
import { colors } from '../../../../theme/global/css/theme-constants/ThemeConstants';
import { fonts } from '../../../../theme/global/css/theme-constants/ThemeConstants';

const placementCoordinatorStyles = makeStyles(theme => ({
  formContainer: {
    paddingRight: "30px",
    paddingLeft: "30px",
    paddingTop: "20px",
    marginTop: "50px",
    background: colors.white
  },
  placementCoordinatorDetailTitle: {
    fontWeight: "bold",
    color: colors.dark,
    border: "none",
    fontSize: "1.5rem"
  },
  placementCoordinatorDetailOption: {
    fontWeight: "bold",
    color: colors.text2,
    border: "none",

  },
  placementCoordinatorDetail: {
    color: colors.text2,
    border: "none",
  },
  placementCoordinatorDetailTitleRow: {
    border: "none",
    background: colors.dark,
    color: colors.text1,
    fontWeight: "bold"
  },
  placementCoordinatorDetailCloseIcon: {
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

export default placementCoordinatorStyles;
