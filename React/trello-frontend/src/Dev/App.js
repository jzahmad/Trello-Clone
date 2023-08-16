import React, {useEffect, useState} from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Cookies from 'js-cookie';

import Login from "./Login";
import Forget from "./Forget";
import CreateAccount from "./CreateAccount";
import Trello from './Trello';
import OpenWorkspace from './OpenWorkspace';
import Board from './Board';
import Workspaces from './Workspaces';

function App() {
    // provide cookies for user
    const [token, setToken] = useState();

    useEffect(() => {
        setToken(Cookies.get('token'))
    }, [])

    const saveToken = (token) => {
        Cookies.set('token', token, { expires: 7, sameSite: 'strict' })
        setToken(token)
    }


    return (
        <Router>
            <Trello />
            <Routes>
                <Route path='/' element={<Login saveToken={ saveToken }/>} />
                <Route path='/login' element={<Login saveToken={ saveToken }/>} />
                <Route path='/register' element={<CreateAccount />} />
                <Route path='/password-reset' element={<Forget />} />
                <Route path='/workspaces' element={<Workspaces token={ token }/>} />
                <Route path='/workspace/:workspaceID' element={<OpenWorkspace token={ token }/>} />
                <Route path='/workspace/:workspaceID/board/:boardID' element={<Board token={ token }/>} />
            </Routes>
        </Router>
    );
}

export default App;
