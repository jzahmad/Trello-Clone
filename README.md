## Trello Application

This project implements a web-based Trello clone, consisting of a backend built with Spring Boot and a frontend built
with ReactJS. This application allows users to manage their tasks, create workspaces, and collaborate with others.

### Summary

#### Backend Structure:

* **Users:** Users are the core entities, each having a unique username, email, password, and security question/answer
for account recovery.
* **Workspaces:** Workspaces are created by users and can contain multiple boards. Users can be added as members to
workspaces, allowing collaboration.
* **Boards:** Boards are within workspaces and contain tasks organized into three categories: "To Do," "Doing," and
"Done."
* **Tasks:** Tasks are individual items with a name, status, optional due date, and optional assigned member.
* **Tokens:** A token-based authentication system is implemented for user login, with tokens associated with users and a
set expiry time. The token expiry is extended with each valid API call.

#### Features:

* **User registration and login:** Users can create new accounts with secure passwords and set security questions for
account recovery. They can then login using their credentials.
* **Workspace management:** Users can create new workspaces, add members, and delete workspaces. Workspace details (name
and description) can be updated.
* **Board management:** Users can add new boards to workspaces, open existing boards, and delete boards.
* **Task management:** Users can add new tasks to boards, change the task status (TODO, DOING, DONE), set due dates, and
assign tasks to other members of the workspace.

### Tech Stack

* **Languages:** Java, Javascript
* **Backend Framework:** Spring Boot
* **Frontend Framework:** ReactJS
* **Database:** MySQL
* **Authentication:** JWT (JSON Web Token)

### Installation and Setup

#### Prerequisites

* Java JDK 17
* Maven 3.8.5
* NodeJS 8.11.0
* npm 18.3.0

#### Installation

1. **Backend:**
- Clone the repository.
- Navigate to `Spring-Boot-Project/1717` directory.
- Run `mvn clean install` to download dependencies.
2. **Frontend:**
- Navigate to `React/trello-frontend` directory.
- Run `npm install` to download dependencies.

#### Configuration

1. **Database Setup:**
- Create a MySQL database with the name specified in `application.properties` (default: `trello`).
- Adjust the database connection details in `application.properties` if necessary.
2. **Frontend Configuration:**
- **Backend API URL:** Make sure the `REACT_APP_BACKEND_URL` environment variable in `.env` file is set to the correct
URL of your running backend application (default: `http://localhost:8080`).

### Running the Project Locally

1. **Backend:**
- In the `Spring-Boot-Project/1717` directory, run `mvn spring-boot:run` to start the backend server.
2. **Frontend:**
- In the `React/trello-frontend` directory, run `npm start` to start the frontend development server.

### Testing

1. **Backend:**
- Run `mvn test` in the `Spring-Boot-Project/1717` directory to execute the backend unit tests.
2. **Frontend:**
- Run `npm test` in the `React/trello-frontend` directory to execute the frontend unit tests.

### Contributing

We welcome contributions!

1. Fork the repository.
2. Create a new branch for your changes.
3. Make your changes and ensure all tests pass.
4. Commit your changes and push them to your fork.
5. Create a pull request to the main repository.

### License

This project is licensed under the MIT License - see the LICENSE file for details.
