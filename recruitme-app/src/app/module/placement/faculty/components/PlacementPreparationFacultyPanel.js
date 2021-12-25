import React from 'react';
import { action } from '../../../../app-constants/Action';
import { DrawerComponent } from '../../../../theme/global/components/DrawerComponent';
import globalStyles from '../../../../theme/global/css/AppStyles';
import { isUserPLacementPreperationFaculty, redirectToLoginPage } from '../../../auth/AuthService';
import { VideoAddForm } from '../../../video/component/VideoAddForm';
import { VideoList } from '../../../video/component/VideoList';


export const PlacementPreperationFacultyPanel = () => {

    const listitems = [
        {
            option: "Video",
            action: action.ADD,
            index: 1,
            component: VideoAddForm,
            path: '/placement-faculty-panel/add-video'
        },
        {
            option: "Videos",
            action: action.LIST,
            index: 2,
            component: VideoList,
            path: '/placement-faculty-panel/videos'
        },

    ];

    const divider = {
        2: true
    }
    const cssStyles = {
        globalStyles: globalStyles(),
    };
    const [showPage, setShowPage] = React.useState(false);

    React.useEffect(()=>{
        if (isUserPLacementPreperationFaculty() == false) redirectToLoginPage();
        else setShowPage(true);
        });

    return (

        <div className={cssStyles.globalStyles.mainContainer}>
            {showPage && <DrawerComponent user={"Preparation Faculty"} listitems={listitems} divider={divider} defaultactivate={"/"} />}
        </div>
    )
}
