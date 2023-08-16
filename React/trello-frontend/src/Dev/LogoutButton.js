import React from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';

function LogoutButton() {

	// Funcion used to change pages
    const navigate = useNavigate();

	const logout = () => {
		Cookies.remove("token");
		navigate('/login');
	}

	return(
		<button id="logout-button" onClick={logout}>Logout</button>
	);
}

export default LogoutButton;