Generic Client-Side Sequence Diagram
```mermaid
sequenceDiagram
User ->> ClientGUI: "Information"
activate ClientGUI
ClientGUI ->> Client: "Information"
deactivate ClientGUI
activate Client
Client ->> NetworkAccess: "Information"
deactivate Client
activate NetworkAccess
NetworkAccess ->> TO SERVER SIDE: "Information"
note over TO SERVER SIDE: Message travels over network
TO SERVER SIDE -->> NetworkAccess: "reply"
NetworkAccess -->> Client: "reply"
activate Client
deactivate NetworkAccess
Client -->> ClientGUI: "reply"
deactivate Client
activate ClientGUI
deactivate ClientGUI
```
Login Diagram - Server Side
```mermaid
sequenceDiagram
activate ClientHandler
CLIENT SIDE ->> NetworkAccess: "u/L/username/password"
activate NetworkAccess
ClientHandler ->> NetworkAccess: GetMessage()
NetworkAccess -->> ClientHandler: "u/L/username/password"
deactivate NetworkAccess
ClientHandler ->> UserHandler: Process("u/L/username/password")
activate UserHandler
UserHandler ->> UserDataBase: login(username, password)
activate UserDataBase
alt LockCount == 3
UserDataBase -->> UserHandler: Account Locked
else LoggedIn == true
UserDataBase -->> UserHandler: Duplicate Login
else SysPassword == Userpassword
UserDataBase -->> UserHandler: Success
else SysPassword == null
UserDataBase -->> UserHandler: Invalid UserName
else SysPassword != UserPassword
UserDataBase -->> UserHandler: Invalid Password
end
deactivate UserDataBase
UserHandler -->> ClientHandler: "reply"
deactivate UserHandler
ClientHandler -->> NetworkAccess: "reply"
activate NetworkAccess
NetworkAccess -->> CLIENT SIDE: "reply"
deactivate NetworkAccess
deactivate ClientHandler
```
User Registration - Server Side
```mermaid
sequenceDiagram
activate ClientHandler
CLIENT SIDE ->> NetworkAccess: "u/R/username/password/email"
activate NetworkAccess
ClientHandler ->> NetworkAccess: GetMessage()
NetworkAccess -->> ClientHandler: "u/R/username/password/email"
deactivate NetworkAccess
ClientHandler ->> UserHandler: CheckValidity("email", "username", "password")
activate UserHandler
opt !valid
UserHandler -->> ClientHandler: Send("RegistrationError")
end
UserHandler ->> UserDataBase: register(username,password,email)
activate UserDataBase
opt UsernameExists == true
UserDataBase -->> UserHandler: send("RegistrationError")
end
UserDataBase -->> UserHandler: "RegistrationSuccess"
deactivate UserDataBase
UserHandler -->> ClientHandler: "reply"
deactivate UserHandler
ClientHandler ->> NetworkAccess: send(response)
activate NetworkAccess
NetworkAccess ->> CLIENT SIDE: send(response)
deactivate NetworkAccess
deactivate ClientHandler
```
Query Server - Server Side
```mermaid
sequenceDiagram
activate ClientHandler
activate CommandProtocol
CLIENT SIDE ->> NetworkAccess: datatofind
activate NetworkAccess
ClientHandler ->> NetworkAccess: GetMessage()
NetworkAccess -->> ClientHandler: datatofind
deactivate NetworkAccess
ClientHandler -> CommandProtocol: process(datatofind)
CommandProtocol -> ServerDataBase: search(datatofind)
activate ServerDataBase
ServerDataBase -->> CommandProtocol: "reply"
deactivate ServerDataBase
CommandProtocol -->> ClientHandler: "reply"
ClientHandler -->> NetworkAccess: "reply"
activate NetworkAccess
NetworkAccess -->> CLIENT SIDE: "reply"
deactivate NetworkAccess
deactivate ClientHandler
deactivate CommandProtocol
```
Logout - Server Side
```mermaid
sequenceDiagram
activate ClientHandler
CLIENT SIDE ->> NetworkAccess: "Logout/userName"
activate NetworkAccess
ClientHandler ->> NetworkAccess: GetMessage()
NetworkAccess -->> ClientHandler: "Logout/userName"
deactivate NetworkAccess
ClientHandler ->> UserHandler: process("Logout/username")
activate UserHandler
UserHandler ->> UserDataBase: logout(username)
activate UserDataBase
UserDataBase -->> UserHandler: "logout successful"
deactivate UserDataBase
UserHandler -->> ClientHandler: "logout successful"
deactivate UserHandler
ClientHandler ->> NetworkAccess: send(response)
activate NetworkAccess
NetworkAccess ->> CLIENT SIDE: send(response)
deactivate NetworkAccess
deactivate ClientHandler
```
Password Change - Server Side
```mermaid
sequenceDiagram
activate ClientHandler
CLIENT SIDE ->> NetworkAccess: "changePassword/username/oldPassword/newPassword"
activate NetworkAccess
ClientHandler ->> NetworkAccess: GetMessage()
NetworkAccess -->> ClientHandler: "changePassword/username/oldPassword/newPassword"
deactivate NetworkAccess
ClientHandler ->> UserHandler: "changePassword/username/oldPassword/newPassword"
activate UserHandler
alt newPassword.length < 8
UserHandler -->> ClientHandler: Password Length Invalid
end
UserHandler ->> UserDataBase: passwordChange(username, oldPassword, newPassword)
activate UserDataBase
alt oldPassword != sysPassword || oldPassword == newPassword
UserDataBase -->> UserHandler: Wrong Password Entered
end
UserDataBase -->> UserHandler: Password Successfully Changed
deactivate UserDataBase
UserHandler -->> ClientHandler: response
ClientHandler ->> NetworkAccess: send(response)
activate NetworkAccess
NetworkAccess ->> CLIENT SIDE: send(response)
deactivate NetworkAccess
deactivate ClientHandler
```
Forgotten Password - Server Side
```mermaid
sequenceDiagram
activate ClientHandler
CLIENT SIDE ->> NetworkAccess: "u/f/email"
activate NetworkAccess
ClientHandler ->> NetworkAccess: GetMessage()
NetworkAccess -->> ClientHandler: "u/f/email"
deactivate NetworkAccess
ClientHandler ->> UserHandler: process("u/f/email")
activate UserHandler
UserHandler ->> UserDataBase: forgotPassword(email)
activate UserDataBase
UserDataBase -->> UserHandler: reply
deactivate UserDataBase
UserHandler -->> ClientHandler: reply
deactivate UserHandler
ClientHandler -->> NetworkAccess: reply
activate NetworkAccess
NetworkAccess ->> CLIENT SIDE: send(reply)
deactivate NetworkAccess
deactivate ClientHandler
```
Query System Admin Data
```mermaid
sequenceDiagram
activate Server
USER ->> AdminGUI: query
activate AdminGUI
AdminGUI ->> UserDataBase: query
activate UserDataBase
alt query == NumofUsersRegistered
AdminGUI ->> UserDataBase: query
UserDataBase -->> AdminGUI: reply
else query == NumofUsersLoggedIn
AdminGUI ->> UserDataBase: query
UserDataBase -->> AdminGUI: reply
else query == WhichUsersLockedOut
AdminGUI ->> UserDataBase: query
UserDataBase -->> AdminGUI: reply
else query == WhichUsersLoggedIn
AdminGUI ->> UserDataBase: query
UserDataBase -->> AdminGUI: reply
else query == NumofUsersConnected
AdminGUI ->> Server: query
Server -->> AdminGUI: reply
end
deactivate UserDataBase
AdminGUI ->> USER: reply
deactivate AdminGUI
```
Create connection
```mermaid
sequenceDiagram
participant Client
participant NetworkAccess
participant ClientHandler
participant Server
activate Server
activate Client
Server ->> ClientHandler: connect(ID, socket, server)
activate ClientHandler
ClientHandler ->> NetworkAccess: NewNetworkAccess(socket)
activate NetworkAccess
Client ->> NetworkAccess: NetworkAccess("ip", int port)
opt clientSocket(ip,port).equals(serverSocket(ip,port))
NetworkAccess ->> ClientHandler: UpdateServerConnections
end
ClientHandler ->> Server: UpdateServerConnections
```
Disconnect Client
```mermaid
sequenceDiagram
participant USER
participant ClientGUI
participant Client
participant NetworkAccess
participant ClientHandler
participant CommandProtocol
USER ->> ClientGUI: disconnect
activate ClientGUI
ClientGUI ->> Client: disconnect
deactivate ClientGUI
activate Client
Client ->> NetworkAccess: send(disconnect)
deactivate Client
activate NetworkAccess
ClientHandler ->> NetworkAccess: GetMessage()
activate ClientHandler
NetworkAccess -->> ClientHandler: disconnect
deactivate NetworkAccess
activate CommandProtocol
opt cmd.equals("disconnect")
ClientHandler ->> CommandProtocol: disconnect
end
deactivate CommandProtocol
deactivate ClientHandler
```
