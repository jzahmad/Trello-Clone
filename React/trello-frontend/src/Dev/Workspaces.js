import React, { useState, useEffect } from 'react';
import {Link, useNavigate} from 'react-router-dom';
import Cookies from 'js-cookie';
import axios from 'axios';

import OpenWorkspace from './OpenWorkspace';
import UpdateWorkspaceDetails from './UpdateWorkspaceDetails';
import LogoutButton from './LogoutButton';

// this is the page where you will see your list of workspaces

function Workspaces({token}) {
    const [Workspaces, setWorkspaces] = useState([]);
    const [newWorkspaceTitle, setNewWorkspaceTitle] = useState('');
    const [newWorkspaceCategory, setNewWorkspaceCategory] = useState('');
    const [newWorkspaceDesc, setNewWorkspaceDesc] = useState('');

    const [updateDetailsIsOpen, setUpdateDetailsIsOpen] = useState(false);
    const [workspaceToUpdate, setWorkspaceToUpdate] = useState(0);

    // Funcion used to change pages
    const navigate = useNavigate();

    // Take user to login page if they're not logged in
    const [authorizationChecked, setAuthorizationChecked] = useState(false);
    useEffect(() => {
        async function validate() {
            const url = 'http://localhost:8080/tokens/validate?token=' + Cookies.get('token');
            let responseMessage;

            try {
                responseMessage = await axios.get(url);
            } catch (error) {
                navigate('/login');
                return;
            }

            setAuthorizationChecked(true);
        }
        validate();
    }, []);

    const getWorkspacesFromAPI = async () => {
        const url = "http://localhost:8080/users/user-info?token=" + Cookies.get('token');
        let responseMessage;

        try {
            responseMessage = await axios.get(url);
            responseMessage = responseMessage.data.workspaces;
        } catch (error) {
            // User is no longer logged in
            navigate('/login');
            return;
        }

        setWorkspaces(responseMessage);
    }

    // Loads existing workspace onto the page on startup
    useEffect(() => {
        getWorkspacesFromAPI();
    }, []);

    const handleAddWorkspace = async () => {
        if (newWorkspaceTitle.trim() === '' || newWorkspaceDesc.trim() === '') {
            return;
        }

        const newWorkspace = {
            name: newWorkspaceTitle.trim(),
            description: newWorkspaceDesc.trim()
        };

        const url = "http://localhost:8080/workspaces/create?token=" + Cookies.get('token');
        let responseMessage;

        try {
            responseMessage = await axios.post(url, newWorkspace);
            responseMessage = responseMessage.data;
        } catch (error) {
            return;
        }

        setWorkspaces(prevWorkspaces => [...prevWorkspaces, responseMessage]);
        setNewWorkspaceTitle('');
        setNewWorkspaceCategory('');
        setNewWorkspaceDesc('');
    };

    const handleDeleteWorkspace = async (id) => {
        const url = "http://localhost:8080/workspaces/" + id + "/delete?token=" + Cookies.get('token');

        try {
            await axios.delete(url);
        } catch (error) {
            console.log(error);
            return;
        }

        setWorkspaces(prevWorkspaces => prevWorkspaces.filter(Workspace => Workspace.id !== id));
    };

    // it is when you press button to open a workspace
    const handleOpenWorkspace = (WorkspaceID) => {
        // Will need to use database IDs when integrating with the backend
        navigate('/workspace/' + WorkspaceID);
    };

    const closeUpdateWorkspaceDetails = () => {
        setUpdateDetailsIsOpen(false);
        getWorkspacesFromAPI();
    }

    const openUpdateDetails = (WorkspaceID) => {
        setUpdateDetailsIsOpen(true);
        setWorkspaceToUpdate(WorkspaceID);
    }

    return (
        <>
            {/*Only load page once login status has been checked*/}
            {!authorizationChecked && <p className="loading">Loading...</p>}
            {authorizationChecked &&
                <div className="App">
                    {updateDetailsIsOpen && <UpdateWorkspaceDetails workspaceID={workspaceToUpdate} close={closeUpdateWorkspaceDetails} />}
                    <LogoutButton />
                    <div>
                        {/* this is the option to add a workspace */}
                        <h1>Workspace</h1>
                        <div className="add-Workspace">
                            <input
                                type="text"
                                placeholder="Enter workspace name"
                                value={newWorkspaceTitle}
                                onChange={(e) => setNewWorkspaceTitle(e.target.value)}
                            />
                            <input
                                type="text"
                                placeholder="Enter description"
                                value={newWorkspaceDesc}
                                onChange={(e) => setNewWorkspaceDesc(e.target.value)}
                            />
                            <button onClick={handleAddWorkspace}>Add Workspace</button>
                        </div>

                        {/* till here */}

                        {/* this is your workspace */}
                        {Workspaces.map((Workspace) => (
                            <div key={Workspace.id} className="Workspace">
                                <h2>{Workspace.name}</h2>
                                <p>Description: {Workspace.description}</p>
                                <div className="button-container">
                                    <button onClick={() => handleOpenWorkspace(Workspace.id)}>Open</button>
                                    <button onClick={() => openUpdateDetails(Workspace.id)}>Update</button>
                                    <button onClick={() => handleDeleteWorkspace(Workspace.id)}>Delete</button>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            }
        </>
    );
};

export default Workspaces;