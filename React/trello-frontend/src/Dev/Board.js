import React, { useState, useEffect, useLayoutEffect } from 'react';
import { Link, useParams, useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import axios from 'axios';

import LogoutButton from './LogoutButton';

function Board({ token }) {
    const [tasks, setTasks] = useState([]);
    const [members, setMembers] = useState([]);
    const [searchTitle, setSearchTitle] = useState('')
    const [filterRange, setFilterRange] = useState('all')

    const navigate = useNavigate();

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
        setAuthorizationChecked(true);
    }, []);

    const { workspaceID, boardID } = useParams();

    const getTasksFromAPI = async () => {
        const url = "http://localhost:8080/boards/" + boardID + "?token=" + Cookies.get('token');
        let responseMessage;

        try {
            responseMessage = await axios.get(url);
            responseMessage = responseMessage.data;
        } catch (error) {
            // User is no longer logged in
            navigate('/login');
            return;
        }

        setTasks(responseMessage.tasks);
    }

    const getMembersFromAPI = async () => {
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

        setMembers(responseMessage.members);
    }

    // Loads existing tasks and member list into the page on startup
    useEffect(() => {
        const init = async () => {
            // Members must be fetched before task for 
            // the assignment dropdowns to function properly
            await getMembersFromAPI();
            getTasksFromAPI();
        };
        init();
    }, []);

    // const handleEditTask = (category, index) => {
    //     const editedTask = prompt('Enter the edited task:', tasks[category][index].task);
    //     if (editedTask !== null) {
    //         setTasks(prevState => {
    //             const updatedTasks = [...prevState[category]];
    //             updatedTasks[index].task = editedTask.trim();
    //             return {
    //                 ...prevState,
    //                 [category]: updatedTasks.filter(task => task.task.trim() !== '')
    //             };
    //         });
    //     }
    // };

    // const handleDeleteTask = (category, index) => {
    //     setTasks(prevState => {
    //         const updatedPrevCategory = [...prevState[category]];
    //         updatedPrevCategory.splice(index, 1);

    //         return {
    //             ...prevState,
    //             [category]: updatedPrevCategory
    //         };
    //     });
    // };

    const handleAddTask = async (category) => {
        const newTaskName = prompt('Enter the task:');
        if (newTaskName === null) {
            return;
        }

        if (newTaskName.trim() === '') {
            return;
        }

        const requestBody = {
            name: newTaskName.trim(),
            status: category
        }

        const url = "http://localhost:8080/tasks/board/" + boardID + "/add-task?token=" 
            + Cookies.get('token');

        let newTask;
        try {
            const responseMessage = await axios.post(url, requestBody);
            newTask = responseMessage.data;
        } catch (error) {
            // User is no longer logged in
            navigate('/login');
            return;
        }

        setTasks((prevTasks) => [...prevTasks, newTask]);
    };

    const AddDueDate = async (task) => {
        let duedate;
        if (task.dueDate === null) {
            duedate = prompt('Enter the due date (YYYY-MM-DD):', new Date().toLocaleDateString('en-CA'));
        } else {
            duedate = prompt('Enter the due date (YYYY-MM-DD):', task.dueDate);
        }

        if (duedate) {
            if (isNaN(Date.parse(duedate))) { // Checks if the input value is an invalid date
                alert('Invalid date! Please enter a valid date in YYYY-MM-DD format.');
                return;
            }

            var currentDate = new Date().toLocaleDateString('en-CA'); // Use 'en-CA' for Canadian date format
            if (duedate < currentDate) {
                alert('Due date cannot be in the past!');
                return;
            }

            const url = "http://localhost:8080/tasks/" + task.id + "/update?date=" + duedate;
            try {
                await axios.post(url);
            } catch (error) {
                console.log(error);
            }

            getTasksFromAPI();
        }
    };

    const changeStatus = async (task, newStatus) => {
        const url = "http://localhost:8080/tasks/" + task.id + "/change-status?status=" + newStatus;

        try {
            await axios.post(url);
        } catch (error) {
            console.log(error);
        }

        getTasksFromAPI();
    };

    const changeAssignment = async (task, newAssignedMember) => {
        if (!members.includes(newAssignedMember)) {
            newAssignedMember = "";
        }

        const url = "http://localhost:8080/tasks/" + task.id + "/assign-member?token=" 
            + Cookies.get('token') + "&member=" + newAssignedMember;

        try {
            await axios.post(url);
        } catch (error) {
            console.log(error);
            getTasksFromAPI();
        }
    };

    const dueDateFilters = [
        {
            value: 'all',
            label: 'All'
        },
        {
            value: 'today',
            label: 'Due today'
        },
        {
            value: 'week',
            label: 'Due in this week'
        },
        {
            value: 'overdue',
            label: 'Overdue'
        }
    ];

    const matchesSearch = (name) => {
        return name.toLowerCase().includes(searchTitle.toLowerCase());
    }

    const inFilterRange = (task) => {
        if (filterRange === "all") {
            return true;
        } else if(filterRange === 'today') {
            if(task.dueDate === new Date().toLocaleDateString('en-CA')) {
                return true;
            }
            return false;
        } else if(filterRange === 'week') {
            let datetime = new Date();
            datetime = datetime.setDate(datetime.getDate() + 7);
            datetime = new Date(datetime);

            const isDueBeforeAWeekFromNow = task.dueDate < datetime.toLocaleDateString('en-CA');
            const isOverdue = task.dueDate < new Date().toLocaleDateString('en-CA');

            if(isDueBeforeAWeekFromNow && !isOverdue) {
                return true;
            }
        } else if(filterRange === 'overdue'){
            if(task.dueDate < new Date().toLocaleDateString('en-CA')) {
                return true;
            }
            return false;
        }
        return false;
    }

    return (
        <>
            {!authorizationChecked ? (
                <p className="loading">Loading...</p>
            ) : (
                <div className="App">
                    <LogoutButton />
                    <div className="workspace-container">
                        <Link to={`/workspace/${workspaceID}`}>
                            <button>Go Back</button>
                        </Link>
                        <div id="search-and-filter-container">
                            <input value={searchTitle} onChange={(e) => setSearchTitle(e.target.value)} placeholder='Search tasks...'></input>
                            <select defaultValue='all' onChange={(e) => setFilterRange(e.target.value)}>
                            {
                                dueDateFilters.map((option, index) => (
                                    <option value={option.value}>{option.label}</option>
                                ))
                            }
                            </select>
                        </div>
                        <div className="taskContainer">
                            <div className="item">
                                <h1>To Do</h1>
                                {tasks.map(task => (
                                    <>
                                    {task.status === "TODO" && matchesSearch(task.name) && inFilterRange(task) &&
                                    <div key={task.id}>
                                        <div className="container">
                                            <div>{task.name}</div>
                                            {task.dueDate && (
                                                <div style={{ color: 'red' }}>Due Date: {task.dueDate}</div>
                                            )}
                                            <div>
                                                {/*<button onClick={() => handleEditTask('todo', index)}>Update</button>*/}
                                                {/*<button onClick={() => handleDeleteTask('todo', index)}>Delete</button>*/}
                                                <button onClick={() => AddDueDate(task)}>Add Due Date</button>
                                                <select className="card-select" defaultValue="TODO" onChange={(event) => changeStatus(task, event.target.value)}>
                                                    <option value="TODO">To Do</option>
                                                    <option value="DOING">Doing</option>
                                                    <option value="DONE">Done</option>
                                                </select>
                                                <select defaultValue={task.assignedUser} className='card-select'  onChange={(event) => changeAssignment(task, event.target.value)}>
                                                    <option value={null}>None</option>
                                                {
                                                    members.map(member => (
                                                        <option value={member}>{member}</option>
                                                    ))
                                                }
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    }
                                    </>
                                ))}
                                <button onClick={() => handleAddTask('TODO')}>Add Task</button>
                            </div>

                            <div className="item">
                                <h1>Doing</h1>
                                {tasks.map(task => (
                                    <>
                                    {task.status === "DOING" && matchesSearch(task.name) && inFilterRange(task) &&
                                    <div key={task.id}>
                                        <div className="container">
                                            <div>{task.name}</div>
                                            {task.dueDate && (
                                                <div style={{ color: 'red' }}>Due Date: {task.dueDate}</div>
                                            )}
                                            <div>
                                                {/*<button onClick={() => handleEditTask('doing', index)}>Update</button>*/}
                                                {/*<button onClick={() => handleDeleteTask('doing', index)}>Delete</button>*/}
                                                <button onClick={() => AddDueDate(task)}>Add Due Date</button>
                                                <select className="card-select" defaultValue="DOING" onChange={(event) => changeStatus(task, event.target.value)}>
                                                    <option value="TODO">To Do</option>
                                                    <option value="DOING">Doing</option>
                                                    <option value="DONE">Done</option>
                                                </select>
                                                <select defaultValue={task.assignedUser} className='card-select'  onChange={(event) => changeAssignment(task, event.target.value)}>
                                                    <option value="">None</option>
                                                {
                                                    members.map(member => (
                                                        <option value={member}>{member}</option>
                                                    ))
                                                }
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    }
                                    </>
                                ))}
                                <button onClick={() => handleAddTask('DOING')}>Add Task</button>
                            </div>

                            <div className="item">
                                <h1>Done</h1>
                                {tasks.map(task => (
                                    <>
                                    {task.status === "DONE" && matchesSearch(task.name) && inFilterRange(task) &&
                                    <div key={task.id}>
                                        <div className="container">
                                            <div>{task.name}</div>
                                            {task.dueDate && (
                                                <div style={{ color: 'red' }}>Due Date: {task.dueDate}</div>
                                            )}
                                            <div>
                                                {/*<button onClick={() => handleEditTask('done', index)}>Update</button>*/}
                                                {/*<button onClick={() => handleDeleteTask('done', index)}>Delete</button>*/}
                                                <button onClick={() => AddDueDate(task)}>Add Due Date</button>
                                                <select className="card-select" defaultValue="DONE" onChange={(event) => changeStatus(task, event.target.value)}>
                                                    <option value="TODO">To Do</option>
                                                    <option value="DOING">Doing</option>
                                                    <option value="DONE">Done</option>
                                                </select>
                                                <select defaultValue={task.assignedUser} className='card-select'  onChange={(event) => changeAssignment(task, event.target.value)}>
                                                    <option value="">None</option>
                                                {
                                                    members.map(member => (
                                                        <option value={member}>{member}</option>
                                                    ))
                                                }
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    }
                                    </>
                                ))}
                                <button onClick={() => handleAddTask('DONE')}>Add Task</button>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </>
    );
}

export default Board;
