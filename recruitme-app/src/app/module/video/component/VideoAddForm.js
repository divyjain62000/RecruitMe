import React from 'react';
import FormControl from '@material-ui/core/FormControl';
import FormGroup from '@mui/material/FormGroup';
import FormLabel from '@material-ui/core/FormLabel';
import { WrapperContainer, ContainerItem, OutlinedTextFieldWrapper, OutlinedSelectWrapper, RadioGroupWrapper, CheckboxWrapper, DialogWrapper } from '../../../theme/global/components/ContainerComponents';
import globalStyles from '../../../theme/global/css/AppStyles';
import { FormErrorLabelWrapper } from '../../../theme/global/components/ContainerComponents';
import Button from '@material-ui/core/Button';
import VideoMetaData from '../../../domain/VideoMetaData';
import { Fab } from '@material-ui/core';
import AddIcon from '@material-ui/icons/Add';
import { colors } from '../../../theme/global/css/theme-constants/ThemeConstants';
import { Typography } from '@material-ui/core';
import CircularProgress from '@material-ui/core/CircularProgress';
import progressImg from '../../../assets/home/carousel-img1.png';

const getTopics = () => {
    var promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' },
        };
        fetch("/api/topics", requestOption).then((response) => {
            if (response.ok) return response.json();
        })
            .then((response) => {
                //alert(JSON.stringify(response));
                resolve(response.content);
            })
            .catch((error) => {
                console.log("Error while fetching topics" + error);
            })
    });
    return promise;
}


/**
 * To add student
 * @param {*} videoMetaDataObj 
 * @returns promise
 */
const addVideoMetaData = (formData) => {
    var promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: 'POST',
            "Content-Type": "multipart/form-data",
            body: formData
        };
        fetch("/api/video/upload", requestOption).then((response) => {
            if (response.ok) return response.json();
        })
            .then((response) => {
              //  alert(JSON.stringify(response));
                if (response.successful) {
                    resolve(JSON.stringify(response.result));
                }
                else {
                    reject(response.result);
                }
            })
            .catch((error) => {
                console.log("Error while adding video" + error);
            })
    });
    return promise;
}



/**
 * Placement Coordinator add form component
 */
export const VideoAddForm = () => {
    const cssStyles = {
        globalStyles: globalStyles(),
    };


    const [topics, setTopics] = React.useState([]);

    const [videoFile, setVideoFile] = React.useState(null);
    const [videoFileName,setVideoFileName] = React.useState("");

    const [videoMetaDataObj, setVideoMetaDataObj] = React.useState(new VideoMetaData());

    const [videoMetaDataErr, setVideoMetaDataErr] = React.useState({});

    const [dialogOpen, setDialogOpen] = React.useState(false);

    const [tmp, setTmp] = React.useState("");

    const [showProgress,setShowProgress] = React.useState(true);


    const submitVideoMetaData = async() => {
        //code to add placement coordinator method call
        const formData = new FormData();
		formData.append('videoFile', videoFile);
        formData.append('topicId',videoMetaDataObj.getTopic());
        formData.append('title',videoMetaDataObj.getTitle());
        setShowProgress(true);
        await addVideoMetaData(formData).then((id) => {
            setShowProgress(false);
            setDialogOpen(true);
            setVideoMetaDataObj(new VideoMetaData());
            setVideoMetaDataErr({});
        }, (exception) => {
            console.log("VideoMetaDataErr: " + JSON.stringify(exception));
            setVideoMetaDataErr(exception);
            setDialogOpen(false);
        }).catch((error) => {
            setDialogOpen(false);
        });
    }



    const handleTabClosing = () => {
      //  alert("handle");
    }


    const alertUser = (event) => {
        event.preventDefault();
        event.returnValue = 'Ares you sure';
    }

    React.useEffect(() => {
        getTopics().then((topicList) => {
            setTopics(topicList);
        });
    }, []);
    return (
        <div className={cssStyles.globalStyles.mainContainer}>
            <WrapperContainer boxShadow={0} alignItems="center" justifyContent="center" className={cssStyles.globalStyles.formMainContainer}>
                <ContainerItem lg={10}>
                    <WrapperContainer spacing={5} className={cssStyles.globalStyles.formContainer}>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <h1>Add Video For Preparation</h1>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={videoMetaDataErr.NAME_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Video Title"
                                        onChange={(ev)=>{
                                            videoMetaDataObj.setTitle(ev.target.value);
                                        }}

                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={videoMetaDataErr.PROGRAM_ERR} />
                                    <OutlinedSelectWrapper
                                        label="Topic"
                                        menulist={topics}
                                        onChange={(ev)=>{
                                            videoMetaDataObj.setTopic(ev.target.value);
                                        }}

                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>

                        <ContainerItem xs={12} sm={6} md={6} lg={5} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={videoMetaDataErr.PRIMARY_EMAIL_ERR} />
                                        <Typography style={{color: colors.dark}}>File: {videoFileName}</Typography>
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={1} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={videoMetaDataErr.PRIMARY_EMAIL_ERR} />
                                    <OutlinedTextFieldWrapper

                                        style={{ visibility: 'hidden' }}
                                    />
                                </FormControl>
                            </FormGroup>
                            
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={6} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <label htmlFor="upload-photo">
                                        <input
                                            style={{ display: 'none' }}
                                            id="upload-photo"
                                            name="upload-photo"
                                            type="file"
                                            onChange={(ev)=>{
                                              //  alert(JSON.stringify(ev.target.files[0]));
                                                setVideoFile(ev.target.files[0]);
                                                setVideoFileName(ev.target.value);
                                            }}
                                        />
                                        <Fab
                                            style={{background: colors.lightDark,padding: "15px"}}
                                            size="small"
                                            component="span"
                                            aria-label="add"
                                            variant="extended"
                                        >
                                            <AddIcon /> Upload Video
                                        </Fab>
                                    </label>
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>



                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <Button
                                className={cssStyles.globalStyles.formButtonRight}
                                variant="contained"
                                onClick={submitVideoMetaData}
                            >
                                Add
                            </Button>
                        </ContainerItem>
                    </WrapperContainer>
                </ContainerItem>
            </WrapperContainer>
            
            <DialogWrapper open={dialogOpen} onClick={(ev) => {
                if (dialogOpen == true) setDialogOpen(false);
                else setDialogOpen(true);
                ev.preventDefault();
                window.location.reload(false);
            }} />
        </div>

    )
}