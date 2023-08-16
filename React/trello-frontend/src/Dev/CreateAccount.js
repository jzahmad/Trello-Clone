import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Cookies from 'js-cookie';

// this is for user to create account for user
function CreateAccount() {

    const lowercaseRegex = /[a-z]/;
    const uppercaseRegex = /[A-Z]/;
    const digitRegex = /[0-9]/;
    const specialCharRegex = /[!@#$%^&*()_+\-=[\]{}|;':",.<>/?`~\\]/;
    const minLengthRegex = /^.{8,}$/;
    const validEmailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const [errorMessage, setErrorMessage] = useState('');
    const validSecurityAnswerRegex = /./;

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

    // start the confirmation of user data

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

    const validateemail = (email) => {
        if (!validEmailRegex.test(email)) {
            return ("Please enter a valid email");
        }
        return ("");
    };
    // end

    // check if the user input the answer for their security question
    const validateSecurity = (security_answer) => {
        if (!validSecurityAnswerRegex.test(security_answer)) {
            return ("Please enter security answer");
        }
        return ("");
    }

    const createUser = async (user) => {
        try {
            await axios.post('http://localhost:8080/users/register', user);
        } catch (error) {
            setErrorMessage(error.response.data);
            return;
        }

        setErrorMessage('');
        navigate('/login');
    }

    // if we put submit button
    const handleSubmit = (event) => {
        event.preventDefault();
        const name = event.target.name.value;
        const password = event.target.new_password.value;
        const confirmpassword = event.target.confirm_password.value;
        const email = event.target.new_email.value;
        const security_question = event.target.security_question.value;
        const security_answer = event.target.security_answer.value;

        const passwordErrors = validatePassword(password);
        const emailError = validateemail(email);
        const secQueAnswerErrors = validateSecurity(security_answer);

        let errorMessage = "";

        if (name.length < 3) {
            errorMessage = "Please enter a valid name";
        } else if (emailError != "") {
            errorMessage = emailError;
        } else if (passwordErrors != "") {
            errorMessage = passwordErrors;
        } else if (confirmpassword != password) {
            errorMessage = "Your passwords don't match";
        } else if (secQueAnswerErrors != "") {
            errorMessage = secQueAnswerErrors;
        }
        setErrorMessage(errorMessage);

        // Call API to create user
        if (errorMessage.length == 0) {
            const user = {
                username: name,
                password: password,
                email: email,
                securityQuestion: security_question,
                securityQuestionAnswer: security_answer
            };
            createUser(user);
        }
    };
    // front end
    // According to requirement, there should be more security question, and the user must fill one of his security
    // question.
    return (
        <>
            {/*Only load page once login status has been checked*/}
            {!authorizationChecked && <p className="loading">Loading...</p>}
            {authorizationChecked &&
                <div className="container">
                    <form onSubmit={handleSubmit}>
                        <div className="form-group">
                            <label htmlFor="name">Username:</label>
                            <input type="text" className="uid" id="name" name="name" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="email">Email:</label>
                            <input type="text" className="uid" id="new_email" name="new_email" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="new_password">Enter Password:</label>
                            <input type="password" id="new_password" name="new_password" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="new_password">Confirm Password:</label>
                            <input type="password" id="confirm_password" name="confirm_password" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="security_question">Security Question:</label>
                            <select name="security_question">
                                <option >What's your mother's name?</option>
                                <option >Where were you born?</option>
                                <option >What's your hobby?</option>
                            </select>
                        </div>
                        <div className="form-group">
                            <label htmlFor="security_question_answer">Security Question Answer:</label>
                            <input type="text" name="security_answer" />
                        </div>
                        <div className="form-submit">
                            <input type="submit" value="Sign Up" />
                        </div>
                        {errorMessage}
                        <div className="forgot-password">
                            <Link to='/login'>Go back to login</Link>
                        </div>
                    </form>
                </div>
            }
        </>
    );
};

export default CreateAccount;