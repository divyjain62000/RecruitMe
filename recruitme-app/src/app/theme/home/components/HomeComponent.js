import React from 'react';
import { AppBarComponent } from '../components/AppBarComponent';
import { CarouselComponent } from '../components/CarouselComponent';


export const HomeComponent = () => {

    return (
        <div style={{marginTop: "40px"}}>
            <AppBarComponent usermenu={false} />
            <CarouselComponent />
        </div>
    )
}


