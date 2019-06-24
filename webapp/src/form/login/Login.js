import React from 'react';

import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import FormControl from '@material-ui/core/FormControl';
import FormHelperText from '@material-ui/core/FormHelperText';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import withStyles from '@material-ui/core/styles/withStyles';

import './Login.css'
import { CircularProgress, Collapse, Link } from '@material-ui/core';

const styles = theme => ({
  main: {
    width: 'auto',
    display: 'block', // Fix IE 11 issue.
    marginLeft: theme.spacing.unit * 3,
    marginRight: theme.spacing.unit * 3,
    [theme.breakpoints.up(400 + theme.spacing.unit * 3 * 2)]: {
      width: 400,
      marginLeft: 'auto',
      marginRight: 'auto',
    },
  },
  paper: {
    marginTop: theme.spacing.unit * 8,
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: `${theme.spacing.unit * 2}px ${theme.spacing.unit * 3}px ${theme.spacing.unit * 3}px`,
  },
  avatar: {
    margin: theme.spacing.unit,
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing.unit,
  },
  submit: {
    marginTop: theme.spacing.unit * 3,
  },
  linkButton: {
    marginTop: theme.spacing.unit * 3,
    alignText: 'center',
  }
});

class Login extends React.Component {
  
  constructor(props) {
    super(props);
    this.state = {
      isRegistering: false,
      username: this.props.username, 
      password: this.props.password,
      retypedPassword: "",
      email: this.props.email,
    }

    this.handleUsernameChange = this.handleUsernameChange.bind(this);
    this.handlePasswordChange = this.handlePasswordChange.bind(this);
    this.handleRetypedPasswordChange = this.handleRetypedPasswordChange.bind(this);
    this.handleEmailChange = this.handleEmailChange.bind(this);
    this.onLogin = this.onLogin.bind(this);
    this.onRegister = this.onRegister.bind(this);
  }

  handleUsernameChange(event) {
    this.setState({username: event.target.value});
  }

  handlePasswordChange(event) {
    this.setState({password: event.target.value});
  }

  handleEmailChange(event) {
    this.setState({email: event.target.value});
  }

  handleRetypedPasswordChange(event) {
    this.setState({retypedPassword: event.target.value});
  }

  onLogin(event) {
    this.props.onLogin(event, this.state);
  }

  onRegister(event) {
    this.props.onRegister(event, this.state);
  }

  render() {
    const { classes } = this.props;

    return (
      <main className={classes.main}>
        <CssBaseline />
        <Paper className={classes.paper}>
          <Avatar className={classes.avatar}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            {this.state.isRegistering ? "Register" : "Sign in"}
          </Typography>
          <form className={classes.form} onSubmit={this.state.isRegistering ? this.onRegister : this.onLogin}>
            <FormControl margin="normal" required fullWidth error={this.props.isUserError}>
              <InputLabel htmlFor="username">Username</InputLabel>
              <Input id="username" name="username" autoComplete="username" defaultValue={this.props.username} onChange={this.handleUsernameChange} autoFocus />
              {this.props.isUserError ? <FormHelperText id="component-error-text">{this.props.logError}</FormHelperText> : <div/>}
            </FormControl>
            <FormControl margin="normal" required fullWidth error={this.props.isError}>
              <InputLabel htmlFor="password">Password</InputLabel>
              <Input name="password" type="password" id="password" autoComplete={this.state.isRegistering ? "none" : "current-password"} defaultValue={this.props.password} onChange={this.handlePasswordChange} />
              {this.props.isError ? <FormHelperText id="component-error-text">{this.props.logError}</FormHelperText> : <div/>}
            </FormControl>
            <Collapse in={this.state.isRegistering} timeout="auto" unmountOnExit>
              <FormControl margin="normal" required fullWidth>
                <InputLabel htmlFor="password">Re-type password</InputLabel>
                <Input name="retypedPassword" type="password" id="retypedPassword" autoComplete="none" defaultValue={this.props.retypedPassword} onChange={this.handleRetypedPasswordChange}/>
              </FormControl>
              <FormControl margin="normal" required fullWidth>
                <InputLabel htmlFor="email">Email</InputLabel>
                <Input name="email" type="email" id="email" autoComplete="email" defaultValue={this.props.email} onChange={this.handleEmailChange}/>
              </FormControl>
            </Collapse>
            {this.props.isLoading ? <CircularProgress/> :
              <Button
                type="submit"
                fullWidth
                variant="contained"
                color="primary"
                className={classes.submit}>
                  {this.state.isRegistering ? "Register" : "Sign in"}
              </Button>
            }
            <Link
              className={classes.linkButton}
              component="button"
              variant="body2"
              onClick={() => {
                this.setState({isRegistering: !this.state.isRegistering});
              }}>
              {this.state.isRegistering ? "Already have an account?" : "Don't have an account?"}
            </Link>
          </form>
        </Paper>
      </main>
    );
  }
}

export default withStyles(styles)(Login);