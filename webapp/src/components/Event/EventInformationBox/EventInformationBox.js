import React, { Component } from 'react'
import classes from './EventInformationBox.css';

export default class EventInformationBox extends Component {

    constructor(props) {
        super(props);
    
        this.setWrapperRef = this.setWrapperRef.bind(this);
        this.handleClickOutside = this.handleClickOutside.bind(this);
      }
    
      componentDidMount() {
        document.addEventListener('mousedown', this.handleClickOutside);
      }
    
      componentWillUnmount() {
        document.removeEventListener('mousedown', this.handleClickOutside);
      }
    
      /**
       * Set the wrapper ref
       */
      setWrapperRef(node) {
        this.wrapperRef = node;
      }
    
      /**
       * Alert if clicked on outside of element
       */
      handleClickOutside(event) {
        if (this.wrapperRef && !this.wrapperRef.contains(event.target)) {
          console.log('You clicked outside of me!');
          this.props.close();
        }
      }

    render() {
        return (
            <div className={classes.Details} ref={this.setWrapperRef} style={this.props.style}>{this.props.children}</div>
        )
    }
}
