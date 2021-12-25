import React from 'react';
import { action } from '../../../app-constants/Action';
import { DrawerComponent } from '../../../theme/global/components/DrawerComponent';
import { PlacementCoordinatorAddForm } from '../../placement/coordinator/components/PlacementCoordinationAddForm';
import globalStyles from '../../../theme/global/css/AppStyles';
import { PlacementPreparationFacultyAddForm } from '../../placement/faculty/components/PlacementPreparationFacultyAddForm';
import { isUserAdmin, redirectToLoginPage } from '../../auth/AuthService';
import { DriveList } from '../../drive/component/DriveList';
import { StudentList } from '../../student/components/StudentList';
import { StudentRegisteredInDriveList } from '../../student/registered-in-drive/component/StudentRegisteredInDriveList';
import { PlacementCoordinatorList } from '../../placement/coordinator/components/PlacementCoordinatorList';
import { PlacementPreperationFacultyList } from '../../placement/faculty/components/PlacementPreperationFacultyList';
import { DriveAddForm } from '../../drive/component/DriveAddForm';
import { DashboardComponent } from '../../dashboard/component/DashboardComponent';

export const AdminPanel = () => {

    const listitems = [
        {
            option: "Dashboard",
            action: action.DASHBOARD,
            index: 1,
            component: DashboardComponent,
            path: '/admin-panel/dashboard'
        },
        {
            option: "Placement Coordinator",
            action: action.ADD,
            index: 2,
            component: PlacementCoordinatorAddForm,
            path: '/admin-panel/add-placement-coordinator'
        },
        {
            option: "Placement Coordinators",
            action: action.LIST,
            index: 3,
            component: PlacementCoordinatorList,
            path: '/admin-panel/placement-coordinators'
        },
        {
            option: "Pereparation Faculty",
            action: action.ADD,
            index: 4,
            component: PlacementPreparationFacultyAddForm,
            path: '/admin-panel/add-preparation-faculty'
        },
        {
            option: "Pereparation Faculties",
            action: action.LIST,
            index: 5,
            component: PlacementPreperationFacultyList,
            path: '/admin-panel/preparation-faculties'

        },
        {
            option: "Students",
            action: action.LIST,
            index: 6,
            component: StudentList,
            path: '/admin-panel/students'
        },
        {
            option: "Student Registered In Drive",
            action: action.LIST,
            index: 7,
            component: StudentRegisteredInDriveList,
            path: '/admin-panel/student-registered-in-drives'
        },
        {
            option: "Drive",
            action: action.ADD,
            index: 8,
            component: DriveAddForm,
            path: '/admin-panel/add-drive'
        },
        {
            option: "Drives",
            action: action.LIST,
            index: 9,
            component: DriveList,
            path: '/admin-panel/drives'
        },
  
       
    ];

    const divider = {
        1:true,
        2: true,
        4: true,
        6: true
    }
    const cssStyles = {
        globalStyles: globalStyles(),
    };

    const [showPage, setShowPage] = React.useState(false);

    React.useEffect(() => {
        if (isUserAdmin() == false) redirectToLoginPage();
        else setShowPage(true);
    });

    return (
        <div className={cssStyles.globalStyles.mainContainer}>
            {showPage &&
                <div className={cssStyles.globalStyles.mainContainer}>
                    <DrawerComponent user={"Administrator"} listitems={listitems} divider={divider} defaultactivate={"/"} />
                </div>

            }
        </div>
    )
}
