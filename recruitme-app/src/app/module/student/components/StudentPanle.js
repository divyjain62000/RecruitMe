import React from 'react';
import { action } from '../../../app-constants/Action';
import { DrawerComponent } from '../../../theme/global/components/DrawerComponent';
import globalStyles from '../../../theme/global/css/AppStyles';
import { DriveList } from '../../drive/component/DriveList';
import { VideoList } from '../../video/component/VideoList';
import { InterviewExperienceAddForm } from '../../interview-experience/components/InterviewExperienceAddForm';
import { InterviewExperienceList} from '../../interview-experience/components/InterviewExperienceList';
import { StudentEditFormComponent } from './StudentEditForm';
import { isUserStudent, redirectToLoginPage } from '../../auth/AuthService';
import { StudentHoldingOfferComponentAddForm } from '../holding-offer/StudentHoldingOfferAddForm';

export const StudentPanel = () => {

    const listitems = [
        
        {
            option: "Edit Profie",
            action: action.EDIT,
            index: 1,
            component: StudentEditFormComponent,
            path: '/student-panel/edit-student'
        },
        {
            option: "Drive List",
            action: action.LIST,
            index: 2,
            component: DriveList,
            path: '/student-panel/drives'
        },
        {
            option: "Videos",
            action: action.LIST,
            index: 3,
            component: VideoList,
            path: '/student-panel/videos'
        },
        {
            option: "Add Interview Experience",
            action: action.ADD,
            index: 4,
            component: InterviewExperienceAddForm,
            path: '/student-panel/add-interview-experience'
        },
        {
            option: "Interview Experiences",
            action: action.LIST,
            index: 5,
            component: InterviewExperienceList,
            path: '/student-panel/interview-experiences'
        },
        {
            option: "Selected In Companies",
            action: action.ADD,
            index: 6,
            component: StudentHoldingOfferComponentAddForm,
            path: '/student-panel/add-student-holding-offer'
        }
        
    ];

    const divider = {
        1: true,
        2:true,
        3: true,
        5:true
    }
    const cssStyles = {
        globalStyles: globalStyles(),
    };

    const [showPage, setShowPage] = React.useState(false);
    React.useEffect(()=>{
        if(isUserStudent()==false) redirectToLoginPage();
        else setShowPage(true);
    });
    

    return (

        <div  className={cssStyles.globalStyles.mainContainer}>
            {showPage && <DrawerComponent user={"Student"} listitems={listitems} divider={divider} defaultactivate={"/"} />}
        </div>
    )
}
