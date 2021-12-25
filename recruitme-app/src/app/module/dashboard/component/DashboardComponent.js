import {
    Chart,
    PieSeries,
    Legend,
    ArgumentAxis,
  ValueAxis,
  BarSeries,
} from '@devexpress/dx-react-chart-material-ui';
import globalStyles from '../../../theme/global/css/AppStyles';
import { WrapperContainer, ContainerItem } from '../../../theme/global/components/ContainerComponents';
import { FormControl, FormLabel, FormGroup, } from '@material-ui/core';
import React from 'react';

import { getDashboardData } from '../api/DashboardApiCaller';

export const DashboardComponent = () => {
    const cssStyles = {
        globalStyles: globalStyles(),
    };
    const [gdata, setGdata] = React.useState([{}]);

    React.useEffect(() => {
        getDashboardData().then((data) => {
            let ts = { argument: 'Total Student', value: data[0]};
            let ps = { argument: 'Placed Student', value: data[1] };
            let nps = { argument: 'Non Placed Student', value: data[0] - data[1] };
            let gd = [];
            gd.push(ts);
            gd.push(nps);
            gd.push(ps);
            setGdata(gd);

        })
    })

    return (
        <div className={cssStyles.globalStyles.container}>
            <WrapperContainer boxShadow={false} alignItems="center" justifyContent="center" className={cssStyles.globalStyles.formMainContainer}>
                <ContainerItem boxShadow={false} lg={10}>
                    <WrapperContainer id="card-0" spacing={5} className={cssStyles.globalStyles.formContainer}>
                        <ContainerItem xs={12} sm={12} md={12} lg={4} xl={12}>
                            <h1>Placement Data</h1>
                        </ContainerItem>
                        <ContainerItem style={{ visibility: "hidden" }} xs={12} sm={12} md={12} lg={4} xl={12}>
                            <h1>Add Placement Coordinator</h1>
                        </ContainerItem>
                        <ContainerItem style={{ visibility: "hidden" }} xs={12} sm={12} md={12} lg={4} xl={12}>
                            <h1>Add Placement Coordinator</h1>
                        </ContainerItem>

                        <ContainerItem boxShadow={false} xs={12} sm={12} md={12} lg={6} xl={3}>

                            <Chart
                                data={gdata}
                                argument={true}
                            >
                                <Legend />
                                <PieSeries valueField="value" argumentField="argument" />
                            </Chart>
                        </ContainerItem>
                        <ContainerItem boxShadow={false} xs={12} sm={12} md={12} lg={6} xl={3}>

                            <Chart
                                data={gdata}
                            >
                                <ArgumentAxis />
                                <ValueAxis />

                                <BarSeries valueField="value" argumentField="argument" />
                            </Chart>
                        </ContainerItem>

                    </WrapperContainer>
                </ContainerItem>
            </WrapperContainer>
        </div>
    )
}