import globalStyles from '../../global/css/AppStyles';
import Paper from '@material-ui/core/Paper';
import Hidden from '@material-ui/core/Hidden';
import carouselStyles from '../css/CarouselStyles';
import Link from '@material-ui/core/Link';
import { WrapperContainer, ContainerItem } from '../../global/components/ContainerComponents';
import CarouselImg from '../../../assets/home/carousel-img1.png'
import { Typography } from '@material-ui/core';
import TcsLogo from '../../../assets/home/tcs.png';
import YashLogo from '../../../assets/home/yash_tech.png';
import ImpetusLogo from '../../../assets/home/impetus.png';
import MindrubyLogo from '../../../assets/home/mindruby.png';
import TatvaSoftLogo from '../../../assets/home/tatva_soft.png';
import DiasparkLogo from '../../../assets/home/diaspark.png';
import HexawareLogo from '../../../assets/home/hexaware.png';
import WiproLogo from '../../../assets/home/wipro.png';
import BirlaSoftLogo from '../../../assets/home/birla_soft.png';
import WileyLogo from '../../../assets/home/wiley.png';
import VirstusaLogo from '../../../assets/home/virtusa.png';
import { redirectToLoginPage } from '../../../module/auth/AuthService';
import { BASE_URL } from '../../../app-constants/AppConstants';

var cssStyle = {};

export const CarouselComponent = () => {
  const allStyle = {
    globalStyles: globalStyles(),
    carouselStyles: carouselStyles()
  };
  cssStyle = allStyle;

  return (
    <div className={cssStyle.globalStyles.mainContainer}>
      <WrapperContainer boxShadow={0}>
      <ContainerItem lg={12}>
      <WrapperContainer className={cssStyle.carouselStyles.carouselContainer} spacing={2}>
        <ContainerItem xs={12} sm={12} md={12} lg={6} xs={12}>
          <Paper className={cssStyle.carouselStyles.slogan} elevation={0}>
            <p><b>RecruitMe</b></p>
            <p>Make The System Automate</p>
            <hr />
            <Link onClick={()=>{ window.location.href=BASE_URL+"/register" }} className={cssStyle.globalStyles.button1}>Register</Link>
            <Link onClick={()=>{ redirectToLoginPage(); }} className={cssStyle.globalStyles.button2}>Login</Link>
          </Paper>
        </ContainerItem>
        <ContainerItem xs={12} sm={12} md={12} lg={6} xs={12}>
          <Paper className={cssStyle.carouselStyles.carouselImg} elevation={0}>
            <WrapperContainer>
              <ContainerItem>
                <Hidden only={['xs', 'md']}> <img src={CarouselImg} width="760px" style={{ paddingRight: "0px" }} /> </Hidden>
                <Hidden only={['xs','lg', 'xl', 'sm', 'md']}> <img src={CarouselImg} width="370px" style={{ paddingRight: "0px" }} /> </Hidden>
                <Hidden only={['xs','lg', 'xl', 'sm']}> <img src={CarouselImg} width="1270px" height="530px" style={{ paddingRight: "0px" }} /> </Hidden>
                <Hidden only={['md','lg', 'xl', 'sm']}> <img src={CarouselImg} width="470px" height="330px" style={{ paddingRight: "0px" }} /> </Hidden>
              </ContainerItem>
            </WrapperContainer>
          </Paper>
        </ContainerItem>
      </WrapperContainer>
      <WrapperContainer boxShadow={0} style={{ padding: "20px" }} spacing={2}>
        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12} style={{ paddingRight: "-50px" }} align="center" backgroundColor="blue">
            <h2>Companies That Visit Our Campus</h2>
          </ContainerItem>
          <ContainerItem xs={12} sm={12} md={12} lg={2} xl={12} style={{ paddingRight: "-50px" }} align="center" backgroundColor="blue">
            <img src={TcsLogo} style={{ width: "150px", height: "150px" }} />
          </ContainerItem>
          <ContainerItem xs={12} sm={12} md={12} lg={2} xl={12} style={{ paddingRight: "-50px" }} align="center" backgroundColor="blue">
            <img src={YashLogo} style={{ width: "150px", height: "150px" }} />
          </ContainerItem>
          <ContainerItem xs={12} sm={12} md={12} lg={2} xl={12} style={{ paddingRight: "-50px" }} align="center" backgroundColor="blue">
            <img src={ImpetusLogo} style={{ width: "150px", height: "150px" }} />
          </ContainerItem>
          <ContainerItem xs={12} sm={12} md={12} lg={2} xl={12} style={{ paddingRight: "-50px" }} align="center" backgroundColor="blue">
            <img src={TatvaSoftLogo} style={{ width: "150px", height: "150px" }} />
          </ContainerItem>
          <ContainerItem xs={12} sm={12} md={12} lg={2} xl={12} style={{ paddingRight: "-50px" }} align="center" backgroundColor="blue">
            <img src={HexawareLogo} style={{ width: "150px", height: "150px" }} />
          </ContainerItem>
          <ContainerItem xs={12} sm={12} md={12} lg={2} xl={12} style={{ paddingRight: "-50px" }} align="center" backgroundColor="blue">
            <img src={MindrubyLogo} style={{ width: "250px", height: "150px" }} />
          </ContainerItem>
          <ContainerItem xs={12} sm={12} md={12} lg={2} xl={12} style={{ paddingRight: "-50px" }} align="center" backgroundColor="blue">
            <img src={DiasparkLogo} style={{ width: "150px", height: "150px" }} />
          </ContainerItem>
          <ContainerItem xs={12} sm={12} md={12} lg={2} xl={12} style={{ paddingRight: "-50px" }} align="center" backgroundColor="blue">
            <img src={WileyLogo} style={{ width: "150px", height: "150px" }} />
          </ContainerItem>
          <ContainerItem xs={12} sm={12} md={12} lg={2} xl={12} style={{ paddingRight: "-50px" }} align="center" backgroundColor="blue">
            <img src={VirstusaLogo} style={{ width: "150px", height: "150px" }} />
          </ContainerItem>
          <ContainerItem xs={12} sm={12} md={12} lg={2} xl={12} style={{ paddingRight: "-50px" }} align="center" backgroundColor="blue">
            <img src={BirlaSoftLogo} style={{ width: "150px", height: "150px" }} />
          </ContainerItem>
          <ContainerItem xs={12} sm={12} md={12} lg={2} xl={12} style={{ paddingRight: "-50px" }} align="center" backgroundColor="blue">
            <img src={WiproLogo} style={{ width: "150px", height: "150px" }} />
          </ContainerItem>
          <ContainerItem xs={12} sm={12} md={12} lg={2} xl={12} style={{ paddingRight: "-50px",marginTop: "50px" }} align="center" backgroundColor="blue">
            <h2>Many More...</h2>
          </ContainerItem>
        </WrapperContainer>
        </ContainerItem>
        </WrapperContainer>
    </div>
  )
}