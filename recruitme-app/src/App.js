import React from 'react';
import globalStyles from './app/theme/global/css/AppStyles';
import { AppBarComponent } from './app/theme/home/components/AppBarComponent';
import { CarouselComponent } from './app/theme/home/components/CarouselComponent';



const App = () => {
  const cssStyle = {
    globalStyles: globalStyles(),
  };

  return (
    <div className={cssStyle.globalStyles.mainContainer}>
      <AppBarComponent />
      <br /><br /><br /><br /><br />
      <CarouselComponent />
     </div>
  );
}


export default App;
