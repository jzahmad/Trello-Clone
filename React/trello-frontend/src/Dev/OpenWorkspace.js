import React, { useState, useEffect } from 'react';
import { useNavigate, Link, useParams } from 'react-router-dom';
import Cookies from 'js-cookie';
import axios from 'axios';

import Board from './Board';
import LogoutButton from './LogoutButton';

// it is front end that you will see when you open a workspace

function OpenWorkspace({token}) {
    const [boards, setBoards] = useState([]);
    const [newBoardTitle, setNewBoardTitle] = useState('');
    const [newMemberName, setNewMemberName] = useState('');
    const [selectedBoard, setSelectedBoard] = useState(null);
    const [workspaceName, setWorkspaceName] = useState('');
    const [addMemberError, setAddMemberError] = useState('');

    // Should match backend ID for the workspace being viewed
    const {workspaceID} = useParams();

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

    const getBoardsFromAPI = async () => {
        const url = "http://localhost:8080/workspaces/" + workspaceID + "?token=" + Cookies.get('token');
        let responseMessage;

        try {
            responseMessage = await axios.get(url);
            responseMessage = responseMessage.data;
        } catch (error) {
            // User is no longer logged in
            navigate('/login');
            return;
        }

        setWorkspaceName(responseMessage.name);
        setBoards(responseMessage.boards);
    }

    // Loads existing boards onto the page on startup
    useEffect(() => {
        getBoardsFromAPI();
    }, []);


    const handleAddBoard = async () => {
        if (newBoardTitle.trim() === '') {
            return;
        }

        const url = "http://localhost:8080/boards/workspace/" + workspaceID + "/add-board?token=" 
            + Cookies.get('token') + "&name=" + newBoardTitle.trim();

        let newBoard;
        try {
            const responseMessage = await axios.post(url);
            newBoard = responseMessage.data;
        } catch (error) {
            // User is no longer logged in
            navigate('/login');
            return;
        }

        setBoards((prevBoards) => [...prevBoards, newBoard]);
        setNewBoardTitle('');
    };

    const handleOpenBoard = (boardID) => {
        // Will need to use database IDs when integrating with the backend
        navigate('/workspace/' + workspaceID + '/board/' + boardID);
    };

    const handleGoBackToWorkspaces = () => {
        navigate('/workspaces');
    };

    const handleDeleteBoard = (board) => {
        const url = "http://localhost:8080/boards/" + board.id + "/delete?token=" + Cookies.get('token');

        try {
            axios.delete(url);
        } catch (error) {
            console.log(error);
            return;
        }

        setBoards((prevBoards) => prevBoards.filter((b) => b.id !== board.id));
    };

    const addMember = async () => {
        if (newMemberName.trim() === '') {
            return;
        }

        const url = "http://localhost:8080/workspaces/" + workspaceID + "/add-member?token=" 
            + Cookies.get('token') + "&username=" + newMemberName;

        try {
            await axios.post(url);
        } catch (error) {
            console.log(error);
            setAddMemberError(error.response.data);
            return;
        }

        setNewMemberName('');
        setAddMemberError('');
    }

    return (
        <>
            {/*Only load page once login status has been checked*/}
            {!authorizationChecked && <p className="loading">Loading...</p>}
            {authorizationChecked &&
                // the option to add a board for space
                <div className="App">
                    <LogoutButton />
                    <div className="workspace-container">
                        <div>
                            <Link to='/workspaces'>
                                <button>Go Back</button>
                            </Link>
                            <h2>Welcome to {workspaceName}</h2>
                            <div className="add-list-container">
                                <input
                                    type="text"
                                    placeholder="Enter board title"
                                    value={newBoardTitle}
                                    onChange={(e) => setNewBoardTitle(e.target.value)}
                                />
                                <button id="add-member-button"onClick={handleAddBoard}>Add Board</button>
                                <div>
                                    <div>
                                        <input
                                            type="text"
                                            placeholder="Enter username"
                                            value={newMemberName}
                                            onChange={(e) => setNewMemberName(e.target.value)}
                                        />
                                    </div>
                                    <span id="add-member-message">{addMemberError}</span>
                                </div>
                                <button onClick={addMember}>Add Member</button>
                            </div>

                            {/* these are boards that will showup */}

                            <div className="board-container">
                                {boards.map((board) => (
                                    <div key={board.id} className="board">
                                        <h3>{board.name}</h3>
                                        <button onClick={() => handleOpenBoard(board.id)}>Open</button>
                                        <button onClick={() => handleDeleteBoard(board)}>Delete</button>
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>
                </div>
            }
        </>
    );
};

export default OpenWorkspace;