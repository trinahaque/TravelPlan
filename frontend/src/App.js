import React from 'react';
import './App.css';
import { Switch, Route, Link } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Signup from './pages/Signup';
import UserPage from './pages/UserPage';

const App = () => {
  return (
    <div>
      <nav>
        <Link to="/"> Home </Link>
        <Link to="/logout">Log Out</Link>
        <Link to="/login"> Login</Link>
        <Link to="/signup"> Sign Up</Link>
      </nav>
      <Switch>
        <Route path="/login">
          <Login />
        </Route>
        <Route path="/signup">
          <Signup />
        </Route>
        <Route path="/">
          <UserPage />
        </Route>
        <Route path="/">
          <Home />
        </Route>
      </Switch>
    </div>
  );
};

export default App;