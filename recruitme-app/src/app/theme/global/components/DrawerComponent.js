import React from 'react';
import { AppBarComponent } from '../../home/components/AppBarComponent';
import Drawer from '@material-ui/core/Drawer';
import AddBox from '@material-ui/icons/AddBox';
import Button from '@material-ui/core/Button';
import Divider from '@material-ui/core/Divider';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import { WrapperContainer } from './ContainerComponents';
import { ContainerItem } from './ContainerComponents';
import Toolbar from '@material-ui/core/Toolbar';
import drawerStyles from '../css/DraweStyle';
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import ListIcon from '@material-ui/icons/List';
import EditIcon from '@material-ui/icons/Edit';
import { action } from '../../../app-constants/Action';
import UserIcon from '@material-ui/icons/Person';
import { BrowserRouter, Router, Route, Link } from 'react-router-dom';
import { createBrowserHistory } from "history";
import DashboardIcon from '@material-ui/icons/Dashboard';


const history = createBrowserHistory();

export const DrawerComponent = (props) => {
    const cssStyles = {
        drawerStyles: drawerStyles()
    };
    const [showDrawer, setShowDrawer] = React.useState(false);

    const drawerHandler = () => {
        if (showDrawer == true) closeDrawer();
        else openDrawer();
    }

    const openDrawer = () => {
        setShowDrawer(true);
    }
    const closeDrawer = () => {
        setShowDrawer(false);
    }

    const [activateOption, setActivateOption] = React.useState(window.location.pathname);

    return (
        <div className={cssStyles.drawerStyles.container}>
            <AppBarComponent showmenuicon={true} drawerHandler={drawerHandler} />
            <div>
                <WrapperContainer justify='space-between' >
                    <ContainerItem>
                        <BrowserRouter history={history}>
                            <Drawer
                                classes={{
                                    paper: cssStyles.drawerStyles.paper
                                }}
                                open={showDrawer} onClose={showDrawer} variant='persistent'>
                                <Toolbar />
                                <List>
                                    <ListItem className={cssStyles.drawerStyles.userArea} >
                                        <ListItemIcon className={cssStyles.drawerStyles.userIconac}>
                                            <UserIcon />
                                        </ListItemIcon>
                                        <ListItemText className={cssStyles.drawerStyles.userTitle}>{props.user}</ListItemText>
                                    </ListItem>
                                    {
                                        props.listitems.map((item) => {

                                            return (

                                                <div>
                                                    {activateOption == item.path && <ListItem
                                                        className={cssStyles.drawerStyles.activeListItem}
                                                    >
                                                        <ListItemIcon className={cssStyles.drawerStyles.activeListItemIcon}>
                                                            {item.action == action.ADD && <AddIcon />}
                                                            {item.action == action.EDIT && <EditIcon />}
                                                            {item.action == action.LIST && <ListIcon />}
                                                            {item.action == action.DASHBOARD && <DashboardIcon />}
                                                        </ListItemIcon>
                                                        <ListItemText className={cssStyles.drawerStyles.activeListItemText}>
                                                            {item.option}
                                                        </ListItemText>
                                                    </ListItem>
                                                    }

                                                    {activateOption != item.path && <ListItem
                                                        className={cssStyles.drawerStyles.listItem}
                                                        component={Link}
                                                        to={item.path}
                                                        onClick={() => {
                                                            setActivateOption(item.path);
                                                            drawerHandler()
                                                        }}
                                                    >
                                                        <ListItemIcon className={cssStyles.drawerStyles.listItemIcon}>
                                                            {item.action == action.ADD && <AddIcon />}
                                                            {item.action == action.EDIT && <EditIcon />}
                                                            {item.action == action.LIST && <ListIcon />}
                                                            {item.action == action.DASHBOARD && <DashboardIcon />}
                                                        </ListItemIcon>
                                                        <ListItemText className={cssStyles.drawerStyles.listItemText}>
                                                            {item.option}
                                                        </ListItemText>
                                                    </ListItem>
                                                    }
                                                    {props.divider[item.index] && <Divider className={cssStyles.drawerStyles.dividerLine} />}
                                                </div>
                                            )

                                        })
                                    }


                                </List>
                            </Drawer>
                            <main>
                                {
                                    props.listitems.map((item) => {

                                        return (
                                            <Route exact path={item.path} key={item.option} component={item.component} />
                                        )

                                    })
                                }
                            </main>
                        </BrowserRouter>

                    </ContainerItem>
                </WrapperContainer>
            </div>
        </div>
    )
}