import React, { Component } from 'react';
import NavigationItem from './NavigationItem/NavigationItem';
import List from '@material-ui/core/List';

import Icon from '@material-ui/icons/Person';
import RemoveIcon from '@material-ui/icons/Remove';
import axios from 'axios';

class NavigationItems extends Component {

    constructor(props) {
        super(props);
        this.state = {
            user: props.user,
            friends: [],
        }
    }

    componentDidMount() {
        this.loadFriends(this.state.user.userId, this.state.user.token);
    }

    clicked = () => {
        console.log("clicked");
    }

    loadFriends = (userID, token) => {
        axios.get('https://spicymemes.app/eventmap/public/getFollowers/' + userID, { headers: { Authorization: 'Bearer ' + token }})
        .then(res => {
            if(res.data.Code === 200) {
                if(res.data.result) {
                    this.setState({friends: res.data.result});
                }
                console.log("Friends: " + res.data.result);
            }
            else {
                console.log(res);
            }
        })
        .catch(
        err => {
            console.log(err);
            console.log(err.response);
            this.setState({isLoading: false, isError:true, isUserError:true, logError:"Error " + err.response.status + " " + err.response.data.message});
        }
        );
    }

    render() {
        const friends = this.state.friends.map(
            friends => {
                return (
                    <NavigationItem 
                    key={friends.user_id} 
                    text={friends.username} 
                    icon={<Icon/>}
                    sideIcon={<RemoveIcon color="secondary"/>}
                    click={() => this.props.clicked(friends.user_id)}/>
                );
            }
        )
        return (
            <div>
            <List>
                {friends}
            </List>
            </div>
        )
    }
}

export default NavigationItems