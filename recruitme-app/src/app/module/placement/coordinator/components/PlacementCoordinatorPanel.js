import React from 'react';
import { action } from '../../../../app-constants/Action';
import { DrawerComponent } from '../../../../theme/global/components/DrawerComponent';
import globalStyles from '../../../../theme/global/css/AppStyles';
import { DriveAddForm } from '../../../drive/component/DriveAddForm';
import {Drive} from '../../../../domain/Drive';
import { StudentList } from '../../../student/components/StudentList';
import { StudentRegisteredInDriveList } from '../../../student/registered-in-drive/component/StudentRegisteredInDriveList';
import { DriveList } from '../../../drive/component/DriveList';
import { isUserPlacementCoordinator,redirectToLoginPage } from '../../../auth/AuthService';
import { DashboardComponent } from '../../../dashboard/component/DashboardComponent';

export const PlacementCoordinatorPanel = () => {

    const listitems = [
        {
            option: "Dashboard",
            action: action.DASHBOARD,
            index: 1,
            component: DashboardComponent,
            path: '/placement-coordinator-panel/dashboard'
        },
        {
            option: "Drive",
            action: action.ADD,
            index: 2,
            component: DriveAddForm,
            path: '/placement-coordinator-panel/add-drive'
        },
        {
            option: "Drives",
            action: action.LIST,
            index: 3,
            component: DriveList,
            path: '/placement-coordinator-panel/drives'
        },
        {
            option: "Students",
            action: action.LIST,
            index: 4,
            component: StudentList,
            path: '/placement-coordinator-panel/students'
        },
        {
            option: "Student Registered In Drive",
            action: action.LIST,
            index: 5,
            component: StudentRegisteredInDriveList,
            path: '/placement-coordinator-panel/student-registered-in-drive'
        }
        
    ];

    const divider = {
        1:true,
        2: true
    }
    const cssStyles = {
        globalStyles: globalStyles(),
    };
    
    const [showPage, setShowPage] = React.useState(false);
    React.useEffect(()=>{
    if (isUserPlacementCoordinator() == false) redirectToLoginPage();
    else setShowPage(true);
    });

    return (

        <div  className={cssStyles.globalStyles.mainContainer}>
           {showPage && <DrawerComponent user={"Placement Coordinator"} listitems={listitems} divider={divider} defaultactivate={"/"} /> 
            
           }
        </div>
    )
}
