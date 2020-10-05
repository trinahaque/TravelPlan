import React from 'react';  //step 1

const Cities = ({cities}) => { // step 2 declare component
    //Notes specific logic in here
    return(
        <div className="city-list">
            {cities.map((item) => {
              // same as java
              // convert each array item to an element
              return (
                <div className="cities-item">
                  {item}
                </div>
              );
            })}
        </div>
    );
};

//step 3
export default Cities;