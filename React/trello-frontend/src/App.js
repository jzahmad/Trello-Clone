import logo from './logo.svg';
import './App.css';
import { useState } from 'react';

function App({ showCreateAccount, showForget }) {
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [loginStatus, setLoginStatus] = useState("");

  const database = [
    {
      username: "user1",
      password: "pass1"
    },
    {
      username: "user2",
      password: "pass2"
    }
  ];

  const handleSubmit = (event) => {
    event.preventDefault();
    const username = event.target.name2.value;
    const password = event.target.password.value;

    const userData = database.find((user) => user.username === username);

    if (userData && userData.password === password) {
      setLoginStatus("Correct password");
    } else {
      setLoginStatus("Invalid Username or password");
    }
    setIsSubmitted(true);
  };

  if (isSubmitted && loginStatus === "Correct password") {
    return <App />;
  }
  return (
    <div className="container">
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="name">UserID:</label>
          <input type="text" className="uid" id="name1" name="name2" />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password:</label>
          <input type="password" id="password" name="password" />
        </div>
        {isSubmitted && <p>{loginStatus}</p>}
        <div className="form-group">
          <input type="submit" value="Sign In" />
        </div>
      </form>
      <div className="forgot-password">
        <a onClick={() => showForget(true)} href="#">
          Forgot Password?
        </a>
      </div>
      <div className="create-account">
        <a onClick={() => showCreateAccount(true)} href="#">
          Create Account
        </a>
      </div>
    </div>
  );
};

export default App;
