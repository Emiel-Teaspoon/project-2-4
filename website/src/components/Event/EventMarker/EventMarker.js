import React from 'react';

import icon from '../../../assets/icon.svg'
import classes from './EventMarker.css';

const eventMarker = (props) => 
{
    let style = classes.Small;

    if(props.size >= 30) {
        style = classes.Big;
    }
    else if(props.size >= 15) {
        style = classes.Medium;
    }

    return(<div className={classes.Icon}><img src={icon} alt="icon" className={style} onClick={() => props.clickHandler(props.id)}/></div>);
}

export default eventMarker;