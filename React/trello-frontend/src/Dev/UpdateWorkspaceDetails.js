import React, { useState, useEffect } from 'react';
import Cookies from 'js-cookie';
import axios from 'axios';

function UpdateWorkspaceDetails(props) {

	const handleX = () => {
		props.close();
	}

	const handleSubmit = async (e) => {
		e.preventDefault();
		const workspaceDetails = {
			name: e.target.name.value,
			description: e.target.description.value
		}

		console.log(workspaceDetails)
		console.log(props.workspaceID);

		const url = "http://localhost:8080/workspaces/" + props.workspaceID 
			+ "/update-details?token=" + Cookies.get('token');
		try {
			await axios.post(url, workspaceDetails);
		} catch (error) {
			console.log(error);
			return;
		}

		props.close();
	}

	return (
		<div className="overlay">
			<div className="update-details-container">
				<div id="x-button-container">
					<button id="x-button" onClick={handleX}>X</button>
				</div>
				<h3>Update Workspace Details</h3>
				<form onSubmit={handleSubmit}>
					<div className="form-group">
                        <label htmlFor="name">Name:</label>
                        <input type="text" className="uid" id="name" name="name" />
                    </div>
                    <div className="form-group">
                        <label htmlFor="description">Description:</label>
                        <input type="text" id="description" name="description" />
                    </div>
                    <button type="submit">Update</button>
				</form>
			</div>
		</div>
	);
}

export default UpdateWorkspaceDetails;