import { Button } from '@material-ui/core';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import React from 'react';
import { WrapperContainer, ContainerItem } from '../../global/components/ContainerComponents';
import appBarStyles from '../css/AppBarStyle';
import MenuIcon from '@material-ui/icons/Menu'
import UserIcon from '@material-ui/icons/PersonRounded';
import ArrowIcon from '@material-ui/icons/ArrowDropDownRounded';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import LogoutIcon from '@material-ui/icons/ExitToApp';
import { getAuthenticateUserName, isActive, isUserAdmin, isUserPlacementCoordinator, isUserPLacementPreperationFaculty, isUserStudent } from '../../../module/auth/AuthService';
import { colors } from '../../global/css/theme-constants/ThemeConstants';
import {redirectToLoginPage,getAuthenticateUserId} from '../../../module/auth/AuthService';
import {logout} from '../../../module/auth/AuthService';
import { BASE_URL } from '../../../app-constants/AppConstants';
import {Redirect} from  'react-router-dom';

export const AppBarComponent = (props) => {

  const cssStyle = {
    appBarStyles: appBarStyles()
  };

  const [anchorForMenu, setAnchorForMenu] = React.useState(null);

  const appBarMenuIconClickHandler = (ev) => {
    setAnchorForMenu(ev.currentTarget)
  }

  const appBarMenuCloseHandler = () => {
    setAnchorForMenu(null)
  }

  return (
    <WrapperContainer>
      <ContainerItem>
        <AppBar elevation={0} className={cssStyle.appBarStyles.appBar}>
          <Toolbar>
            <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
              {props.showmenuicon && <MenuIcon className={cssStyle.appBarStyles.icon} onClick={props.drawerHandler} />}
              {props.showmenuicon == false && <Typography>RecruitMe</Typography>}
              <ArrowIcon style={{ float: "right" }} className={cssStyle.appBarStyles.icon} onClick={appBarMenuIconClickHandler} />
              <UserIcon style={{ float: "right" }} className={cssStyle.appBarStyles.icon} onClick={appBarMenuIconClickHandler} />

              <Typography style={{ float: "right",paddingRight: "20px", paddingTop: "5px", cursor: "pointer" }}
                onClick={()=>{
                  window.location.href=BASE_URL+"/aboutus";
                }}
              >About Us</Typography>
              <Typography style={{ float: "right",paddingRight: "20px", paddingTop: "5px", cursor: "pointer" }}
                onClick={()=>{
                  window.location.href=BASE_URL;
                }}
                >Home</Typography>
              <Menu style={{ marginTop: "40px", zIndex: 10000, cursor: "pointer" }} anchorEl={anchorForMenu} open={Boolean(anchorForMenu)} onClose={appBarMenuCloseHandler}>
              { isActive() && isUserAdmin() &&  <MenuItem style={{ color: "black", fontWeight: "bold", fontSize: "15px",justifyContent: "center" }}
                  onClick={() => {
                    window.location.href=BASE_URL+"/admin-panel";
                  }}
                >
                  Admin
                </MenuItem>}
                { isUserStudent() &&  <MenuItem style={{ color: "black", fontWeight: "bold", fontSize: "15px",justifyContent: "center", background: colors.white }}
                  onClick={() => {
                    window.location.href=BASE_URL+"/student-panel";
                  }}
                >
                  {getAuthenticateUserName()}
                </MenuItem>}
                {isActive() && isUserPlacementCoordinator() &&  <MenuItem style={{ color: "black", fontWeight: "bold", fontSize: "15px",justifyContent: "center" }}
                  onClick={() => {
                    window.location.href=BASE_URL+"/placement-coordinator-panel";
                  }}
                >
                  {getAuthenticateUserName()}
                </MenuItem>}
                { isUserPLacementPreperationFaculty() &&  <MenuItem style={{ color: "black", fontWeight: "bold", fontSize: "15px",justifyContent: "center" }}
                  onClick={() => {
                    window.location.href=BASE_URL+"/placement-faculty-panel";
                  }}
                >
                  {getAuthenticateUserName()}
                </MenuItem>}
              { isActive()==true &&  <MenuItem style={{ color: "red", fontWeight: "bold", fontSize: "15px",justifyContent: "center" }}
                  onClick={() => {
                    logout();
                  }}
                >
                  <LogoutIcon /> Logout
                </MenuItem>}
                {isActive()==false &&  <MenuItem style={{ color: colors.dark , fontWeight: "bold", fontSize: "15px",justifyContent: "center" }}
                  onClick={() => {
                    redirectToLoginPage();
                  }}
                >
                  Login
                </MenuItem>}
              </Menu>
            </ContainerItem>
          </Toolbar>
        </AppBar>
      </ContainerItem>
    </WrapperContainer>
  )
}