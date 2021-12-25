import React from 'react';
import { PlacementCoordinatorPanel } from './app/module/placement/coordinator/components/PlacementCoordinatorPanel';
import { StudentRegistrationFormComponent } from './app/module/student/components/StudentRegistrationForm';
import globalStyles from './app/theme/global/css/AppStyles';
import { PlacementPreperationFacultyPanel } from './app/module/placement/faculty/components/PlacementPreparationFacultyPanel';
import { StudentPanel } from './app/module/student/components/StudentPanle';
import { AdminPanel } from './app/module/admin/components/AdminPanel';
import { LoginComponent } from './app/theme/home/components/LoginComponent';
import { Redirect, Route } from 'react-router';
import { Switch, BrowserRouter } from 'react-router-dom';
import { StudentEditFormComponent } from './app/module/student/components/StudentEditForm';
import { HomeComponent } from './app/theme/home/components/HomeComponent';
import { DriveAddForm } from './app/module/drive/component/DriveAddForm';
import InterviewExperience from './app/domain/InterviewExperience';
import { InterviewExperienceAddForm } from './app/module/interview-experience/components/InterviewExperienceAddForm';
import { VideoList } from './app/module/video/component/VideoList';
import { DriveList } from './app/module/drive/component/DriveList';
import { isUserAdmin, isUserStudent, isUserPLacementPreperationFaculty, isUserPlacementCoordinator, redirectToLoginPage } from './app/module/auth/AuthService';
import { AboutUsMainComponent } from './app/theme/home/components/AboutUs';
import { PlacementCoordinatorList } from './app/module/placement/coordinator/components/PlacementCoordinatorList';
import { isActive } from './app/module/auth/AuthService';
import { ResetPasswordComponent } from './app/theme/home/components/ResetPassword';


const Test = () => {
    const cssStyles = {
        globalStyles: globalStyles(),
    };
    //<PlacementCoordinatorPanel />
    //<StudentPanel />

   /* React.useEffect(()=>{
        if(isActive()==false) {
            sessionStorage.setItem("userId", null);
            sessionStorage.setItem("role", null);
            sessionStorage.setItem("enrollmentNumber",null);
            sessionStorage.setItem("email", null);
            sessionStorage.setItem("name",null);
            localStorage.setItem("userId",null);
            localStorage.setItem("role",null);
            localStorage.setItem("enrollmentNumber",null);
            localStorage.setItem("email",null);
            localStorage.setItem("name",null);
        }
    });*/


    return (
        <div>
            <BrowserRouter>
                <div>
                    <Switch>
                        <Route exact path="/" component={HomeComponent} />
                        <Route exact path="/admin-panel" component={AdminPanel} />
                        <Route exact path="/admin-panel/add-placement-coordinator" component={AdminPanel} />
                        <Route exact path="/admin-panel/placement-coordinators" component={AdminPanel} />
                        <Route exact path="/admin-panel/add-preparation-faculty" component={AdminPanel} />
                        <Route exact path="/admin-panel/preparation-faculties" component={AdminPanel} />
                        <Route exact path="/admin-panel/dashboard" component={AdminPanel} />

                        <Route exact path="/admin-panel/students" component={AdminPanel} />
                        <Route exact path="/admin-panel/student-registered-in-drives" component={AdminPanel} />

                        <Route exact path="/admin-panel/add-drive" component={AdminPanel} />
                        <Route exact path="/admin-panel/drives" component={AdminPanel} />

                        <Route exact path="/student-panel" component={StudentPanel} />
                        <Route exact path="/student-panel/edit-student" component={StudentPanel} />
                        <Route exact path="/student-panel/drives" component={StudentPanel} />
                        <Route exact path="/student-panel/videos" component={StudentPanel} />
                        <Route exact path="/student-panel/add-interview-experience" component={StudentPanel} />
                        <Route exact path="/student-panel/interview-experiences" component={StudentPanel} />
                        <Route exact path="/student-panel/add-student-holding-offer" component={StudentPanel} />

                        <Route exact path="/placement-coordinator-panel" component={PlacementCoordinatorPanel} />
                        <Route exact path="/placement-coordinator-panel/dashboard" component={PlacementCoordinatorPanel} />
                        <Route exact path="/placement-coordinator-panel/add-drive" component={PlacementCoordinatorPanel} />
                        <Route exact path="/placement-coordinator-panel/drives" component={PlacementCoordinatorPanel} />
                        <Route exact path="/placement-coordinator-panel/students" component={PlacementCoordinatorPanel} />
                        <Route exact path="/placement-coordinator-panel/student-registered-in-drive" component={PlacementCoordinatorPanel} />

                        
                        <Route exact path="/placement-faculty-panel" component={PlacementPreperationFacultyPanel} />
                        <Route exact path="/placement-faculty-panel/add-video" component={PlacementPreperationFacultyPanel} />
                        <Route exact path="/placement-faculty-panel/videos" component={PlacementPreperationFacultyPanel} />

                        <Route exact path="/login" component={LoginComponent} />
                        <Route exact path="/register" component={StudentRegistrationFormComponent} />
                        <Route exact path="/edit-student" component={StudentEditFormComponent, StudentPanel} />
                        <Route exact path="/add-drive" component={DriveAddForm, PlacementCoordinatorPanel} />
                        <Route exact path="/interview-experiences" component={InterviewExperience, StudentPanel} />
                        <Route exact path="/add-interview-experience" component={InterviewExperienceAddForm, StudentPanel} />
                        <Route exact path="/videos" component={VideoList, isUserStudent() ? StudentPanel : isUserPLacementPreperationFaculty() ? PlacementPreperationFacultyPanel : null} />
                        <Route exact path="/drives" component={DriveList, isUserStudent() ? StudentPanel : isUserPlacementCoordinator() ? PlacementCoordinatorPanel : null} />
                        <Route exact path="/aboutus" component={AboutUsMainComponent} />
                        <Route exact path="/videos" component={VideoList} />
                        <Route exact path="/reset-password" component={ResetPasswordComponent} />
                    </Switch>
                </div>
            </BrowserRouter>
        </div>
    )
}



export default Test;