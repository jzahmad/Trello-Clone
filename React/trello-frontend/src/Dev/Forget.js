import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Cookies from 'js-cookie';

//this is incase you forget to your password
// it incomplete and it should send some code to useremail and if he put back he can change password ............
// (From requirement) the user should directly change the password instead of receive a mail
function Forget() {

    const [questionRetrieved, setQuestionRetrieved] = useState(false);
    const [securityQuestion, setSecurityQuestion] = useState('');
    const [usernameError, setUsernameError] = useState('');

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

    const lowercaseRegex = /[a-z]/;
    const uppercaseRegex = /[A-Z]/;
    const digitRegex = /[0-9]/;
    const specialCharRegex = /[!@#$%^&*()_+\-=[\]{}|;':",.<>/?`~\\]/;
    const minLengthRegex = /^.{8,}$/;
    const validEmailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    const validatePassword = (password) => {
        const isLowercase = lowercaseRegex.test(password);
        const isUppercase = uppercaseRegex.test(password);
        const isSpecialChar = specialCharRegex.test(password);
        const isMinLength = minLengthRegex.test(password);
        const isDigit = digitRegex.test(password);

        let errors = "";

        if (!isLowercase) {
            errors = errors + 'Password must contain at least 1 lowercase character. ';
        }

        if (!isUppercase) {
            errors = errors + 'Password must contain at least 1 uppercase character. ';
        }

        if (!isSpecialChar) {
            errors = errors + 'Password must contain at least 1 special character. ';
        }

        if (!isMinLength) {
            errors = errors + 'Password must be at least 8 characters length long. ';
        }
        if (!isDigit) {
            errors = errors + 'Password must contain at least 1 digit. ';
        }
        return errors;
    };

    // Retrives users security question from the backend
    const getSecurityQuestion = async (email) => {
        const url = "http://localhost:8080/users/security-question?email=" + email;
        let returnMessage;

        try {
            returnMessage = await axios.get(url);
            returnMessage = returnMessage.data;
        } catch (error) {
            setUsernameError(error.response.data);
            return;
        }

        setSecurityQuestion(returnMessage);
        setQuestionRetrieved(true);
        setUsernameError('');
    }

    // Resets users password on the backend and redirects to the login page
    const resetPassword = async (userData) => {
        const url = "http://localhost:8080/users/reset-password";
        let returnMessage;

        try {
            returnMessage = await axios.post(url, userData);
            returnMessage = returnMessage.data;
        } catch (error) {
            setUsernameError(error.response.data);
            return;
        }

        navigate('/login');
    }

    const handleSubmission = (event) => {
        event.preventDefault();

        if (!questionRetrieved) { // When username is being entered
            const email = event.target.email.value;

            getSecurityQuestion(email);
        } else { // When answer and password are being entered

            const passError = validatePassword(event.target.newPassword.value);
            if (passError !== "") {
                setUsernameError(passError);
                return;
            }

            const userData = {
                email: event.target.email.value,
                securityQuestionAnswer: event.target.answer.value,
                password: event.target.newPassword.value
            };

            resetPassword(userData);
        }
    }

    // Once the user successfully input their email address, they will need to answer their security question, if match
    // their answer in database, they can change their password directly
    return (
        <>
            {/*Only load page once login status has been checked*/}
            {!authorizationChecked && <p className="loading">Loading...</p>}
            {authorizationChecked &&
                <div className="container">
                    <h2>Account Recovery</h2>
                    <form onSubmit={handleSubmission}>
                        <div className="form-group">
                            <label htmlFor="email">Email:</label>
                            <input type="text" className="uid" id="email" name="email" disabled={questionRetrieved} />
                        </div>
                        {questionRetrieved && 
                            <>
                                <div className="form-group">
                                    <label htmlFor="answer">{securityQuestion}</label>
                                    <input type="text" className="uid" id="answer" name="answer" />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="newPassword">New Password:</label>
                                    <input type="password" className="uid" id="newPassword" name="newPassword" />
                                </div>
                            </>
                        }
                        <p>{usernameError}</p>
                        <div className="form-submit">
                            <input type="submit" value="Submit"/>
                        </div>
                    </form>
                    <div className="forgot-password">
                        <Link to='/login'>Go back to login</Link>
                    </div>
                </div>
            }
        </>
    );
};

export default Forget;