# Employment management #

## Description ##
This project represents a solution for managing current employers and jobs applications.  
The GUI (implemented with Java awt) provides different screens, according to the type
of user that has logged in:
- Manager: can see and approve / reject application requests from his company
- Employee: can view his CV and search other users
- Active user: can view his CV, search other users and manage his job applications
- Administrator: can view details from all companies (applications, available positions, departments
employees)

The app has been created using various design patterns, so that the implementation
is as robust and flexible as possible.

## Testing ##
To see how it works, you open the project in your IDE and run the main from
`Test_GUI.java`.  
In order to log in, you can take an email from `resources/consumers.json` (pay attention
to the role you've selected). The password is currently `POO` for every user.

## Future development ##
In a future stage, the application will use a database for storing users information,
removing `resources/consumers.json`. 
Also, `sign up` functionality will be implemented and a secure log in system will
be provided.