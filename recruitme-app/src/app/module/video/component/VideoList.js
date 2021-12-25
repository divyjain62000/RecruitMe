import React from 'react';
import { WrapperContainer, ContainerItem, CheckboxWrapper, DialogWrapper } from '../../../theme/global/components/ContainerComponents';
import globalStyles from '../../../theme/global/css/AppStyles';
import Button from '@material-ui/core/Button';
import FilterIcon from '@material-ui/icons/FilterList';
import videoStyles from '../css/VideoStyle';
import FormControl from '@material-ui/core/FormControl';
import FormGroup from '@mui/material/FormGroup';
import FormLabel from '@material-ui/core/FormLabel';
import { colors } from '../../../theme/global/css/theme-constants/ThemeConstants';
import { isUserPLacementPreperationFaculty,isUserStudent,redirectToLoginPage } from '../../auth/AuthService';
import { LoaderComponent } from '../../../theme/global/components/LoaderComponent';


const getVideos = (data) => {
    var promise = new Promise((resolve) => {
        fetch("/api/video/").then((response) => {
            if (response.ok) return response.json();
            throw response;
        })
            .then((responseObj) => {
              //  alert(responseObj);
              //  alert(JSON.stringify(responseObj));
                resolve(responseObj);
            })
            .catch(error => {
                console.error("Error while fetching videos: " + error);
            })

    });
    return promise;
}


export const VideoList = () => {

    const cssStyles = {
        globalStyles: globalStyles(),
        videoStyles: videoStyles()
    };

    const [videos, setStudents] = React.useState([]);
    const [videoObj, setVideoObj] = React.useState(null);
    const [showList,setShowList] = React.useState(false);
    const [showPage, setShowPage] = React.useState(false);
    const [progressLoader,setProgressLoader] = React.useState(false);

    React.useEffect(() => {
        if(isUserStudent()==false && isUserPLacementPreperationFaculty()==false) redirectToLoginPage();
        else setShowPage(true);
        let data = {

        };
        setProgressLoader(true);
        getVideos(data).then((videoList) => {
            setStudents(videoList);
            setShowList(true);
            setProgressLoader(false);
        });
    }, []);

    return (
        <div className={cssStyles.globalStyles.mainContainer}>
               <LoaderComponent 
                open={progressLoader}
            />
            {showPage && showList && <WrapperContainer boxShadow={0} alignItems="center" justifyContent="center" className={cssStyles.globalStyles.tableMainContainer}>
                <WrapperContainer xs={12} id="card-0" spacing={5} className={cssStyles.globalStyles.formContainer}>
                    <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6}>
                        <h1>Videos</h1>
                    </ContainerItem>
                    <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6}>
                        <Button className={cssStyles.globalStyles.filterIcon}></Button>
                    </ContainerItem>
                    {
                        videos.map((video) => {
                            return (
                                <ContainerItem xs={12} sm={12} md={6} lg={3} xl={3}>
                                    <FormGroup>
                                        <FormControl>
                                        <FormLabel style={{color: colors.text2,paddingBottom: "15px",fontWeight: "bold"}}>{video.title}</FormLabel>
                                            <iframe src={"https://drive.google.com/file/d/"+video.videoExternalId+"/preview"} width="240" height="240" frameborder="0" ></iframe>
                                        </FormControl>
                                    </FormGroup>
                                    <br />
                                    <br />
                                </ContainerItem>
                            )
                        })
                    }

                </WrapperContainer>
            </WrapperContainer>}
        </div>
    )
}