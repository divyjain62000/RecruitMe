import globalStyles from '../../global/css/AppStyles';
import Paper from '@material-ui/core/Paper';
import Hidden from '@material-ui/core/Hidden';
import carouselStyles from '../css/CarouselStyles';
import Link from '@material-ui/core/Link';
import { WrapperContainer, ContainerItem } from '../../global/components/ContainerComponents';
import CarouselImg from '../../../assets/home/carousel-img1.png'
import Typography from '@material-ui/core/Typography';
import divyImg from '../../../assets/aboutus/divy.jfif';
import jayImg from '../../../assets/aboutus/jay.jfif';
import mardavImg from '../../../assets/aboutus/mardav.jfif';
import { AppBarComponent } from './AppBarComponent';
import { colors } from '../../global/css/theme-constants/ThemeConstants';


var cssStyle = {};

export const AboutUsMainComponent = () => {
    const allStyle = {
        globalStyles: globalStyles(),
        carouselStyles: carouselStyles()
    };
    cssStyle = allStyle;

    return (
        <div>
        <AppBarComponent />
        <WrapperContainer className={cssStyle.carouselStyles.carouselContainer} spacing={2}>
            <ContainerItem lg={12}>
                <WrapperContainer boxShadow={true} style={{ padding: "20px",marginTop: "40px" }} spacing={5} alignItems="center">
                    <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12} align="center">
                        <Typography className={cssStyle.globalStyles.heading}><h1>RecruitMe</h1></Typography>
                    </ContainerItem>
                    <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12} align="center">
                        <Typography >As a student even we felt the need of a platform for recruitment activities. This was because we saw students(including us) getting impatient of filling the same details in different forms.<br />
                            This platform aims to store the details of students in a database permanently. This details can be used by the institute and recruiters for sorting the candidates for the next round .<br />
                            Basically, all the recruitment activites can be performed here and students can update their information on their respective account. </Typography>
                    </ContainerItem>
                </WrapperContainer>
                <WrapperContainer boxShadow={true} style={{marginTop: "40px" }} spacing={2} align="center">
                    <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                        <Typography className={cssStyle.globalStyles.heading}><h1>About our Team Members</h1></Typography>
                    </ContainerItem>
                </WrapperContainer>
                <WrapperContainer boxShadow={true} style={{ padding: "20px" }} spacing={2}>
                    <ContainerItem xs={12} sm={12} md={12} lg={3} xl={12} style={{ paddingRight: "-50px", border: "3px solid "+colors.dark }} align="center" backgroundColor="blue">
                        <img src={divyImg} style={{ width: "250px", height: "300px" }} />
                    </ContainerItem>
                    <ContainerItem xs={12} sm={12} md={12} lg={9} xl={12} style={{ border: "3px solid "+colors.dark }} align="center">
                        <Typography className={cssStyle.globalStyles.heading}><h2>Divy Jain [Team Lead]</h2></Typography>
                        <Typography ><h3>Field of work</h3></Typography>
                        <Typography >Designed the whole system <br />Worked on and Integrated the frontend and backend of Recruitme</Typography>
                    </ContainerItem>
                </WrapperContainer>
                <WrapperContainer boxShadow={true} style={{ padding: "20px" }} spacing={2}>
                    <Hidden only={['xs', 'sm']}>
                        <ContainerItem xs={12} sm={12} md={12} lg={9} xl={12} style={{ paddingRight: "-50px", border: "3px solid "+colors.dark }} align="center">
                            <Typography className={cssStyle.globalStyles.heading}><h2>Jayesh Bhardwaj</h2></Typography>
                            <Typography ><h3>Field of work</h3></Typography>
                            <Typography >Handled the frontend part of the project<br />Handled the testing part</Typography>
                        </ContainerItem>

                        <ContainerItem xs={12} sm={12} md={12} lg={3} xl={12} style={{ paddingRight: "-50px", border: "3px solid "+colors.dark }} align="center">
                            <img src={jayImg} style={{ width: "250px", height: "300px" }} />
                        </ContainerItem>
                    </Hidden>
                </WrapperContainer>
                <WrapperContainer boxShadow={true} style={{ padding: "20px", marginTop: "-40px" }} spacing={2}>
                    <Hidden only={['lg', 'md']}>
                        <ContainerItem xs={12} sm={12} md={12} lg={3} xl={12} style={{ paddingRight: "-50px", border: "3px solid "+colors.dark }} align="center">
                            <img src={jayImg} style={{ width: "250px", height: "300px" }} />
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={9} xl={12} style={{ paddingRight: "-50px", border: "3px solid "+colors.dark }} align="center">
                            <Typography className={cssStyle.globalStyles.heading}><h2>Jayesh Bhardwaj</h2></Typography>
                            <Typography ><h3>Field of work</h3></Typography>
                            <Typography >Handled the frontend part of the project<br />Handled the testing part</Typography>
                        </ContainerItem>
                    </Hidden>
                </WrapperContainer>
                <WrapperContainer boxShadow={0} style={{ padding: "20px" }} spacing={2}>
                    <ContainerItem xs={12} sm={12} md={12} lg={3} xl={12} style={{ paddingRight: "-50px", border: "3px solid "+colors.dark }} align="center">
                        <img src={mardavImg} style={{ width: "250px", height: "300px" }} />
                    </ContainerItem>
                    <ContainerItem xs={12} sm={12} md={12} lg={9} xl={12} style={{ paddingRight: "-50px", border: "3px solid "+colors.dark }} align="center">
                        <Typography className={cssStyle.globalStyles.heading}><h2>Mardav Bakliwal</h2></Typography>
                        <Typography ><h3>Field of work</h3></Typography>
                        <Typography >Handled the backend part of the project<br />Handled the testing part</Typography>
                    </ContainerItem>
                </WrapperContainer>
            </ContainerItem>
        </WrapperContainer>
        </div>
    )
}