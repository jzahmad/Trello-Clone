// this is the login page asking for username and password

import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import axios from 'axios';

import Workspaces from './Workspaces';

function Login({ saveToken }) {
    const [isSubmitted, setIsSubmitted] = useState(false);
    const [loginStatus, setLoginStatus] = useState("");

    // Funcion used to change pages
    const navigate = useNavigate();

    // Take user to default page if they're already logged in
    const [authorizationChecked, setAuthorizationChecked] = useState(false);
    useEffect(() => {
        async function validate() {
            const url = 'http://localhost:8080/tokens/validate?token=' + Cookies.get('token');
            let responseMessage;

            try {
                responseMessage = await axios.get(url);
            } catch (error) {
                setAuthorizationChecked(true);
                return;
            }

            navigate('/workspaces');
        }
        validate();
    }, []);

    // Logs the user in if a login token is returned by the login API call
    const handleSubmit = async (event) => {
        event.preventDefault();

        const email = event.target.email.value;
        const password = event.target.password.value;
        const userDetails = {
            email: email,
            password: password
        };

        let returnMessage
        try {
            returnMessage = await axios.post('http://localhost:8080/users/login', userDetails);
            returnMessage = returnMessage.data;
        } catch (error) {
            setLoginStatus("Invalid email or password");
            setIsSubmitted(true);
            return;
        }

        saveToken(returnMessage); // save token cookie
        setLoginStatus("Correct password");
        setIsSubmitted(true);
    };

    // if correct password then it will log into your account
    if (isSubmitted && loginStatus === "Correct password") {
        navigate('/workspaces');
    }

    return (
        <>
            {/*Only load page once login status has been checked*/}
            {!authorizationChecked && <p className="loading">Loading...</p>}
            {authorizationChecked &&
                <div className="container">
                    <form onSubmit={handleSubmit}>
                        <div className="form-group">
                            <label htmlFor="email">Email:</label>
                            <input type="text" className="uid" id="email" name="email" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="password">Password:</label>
                            <input type="password" id="password" name="password" />
                        </div>
                        {isSubmitted && <p>{loginStatus}</p>}
                        <div className="form-submit">
                            <input type="submit" value="Sign In" />
                        </div>
                    </form>
                    <div className="forgot-password">
                        <Link to='/password-reset'>
                            Forgot Password?
                        </Link>
                    </div>
                    <div className="create-account">
                        <Link to='/register'>
                            Create Account
                        </Link>
                    </div>
                </div>
            }
        </>
    );
};

export default Login;