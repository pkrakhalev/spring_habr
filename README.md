Spring Web server with Websoket chat and WebService API 
==========================================================

1. Web server. Used technologies: Spring Boot, Thymeleaf, WebSocket, SOAP WebServise
2. SOAP WebClient that can login, send messages and get latest messages
 between interval. It also responds with available hadr link if any user from chat 
 sends command in the chat. 
3. All chat users get unique generated avatar based on First Name and Last name 
 hashes.


REQUIREMENTS
-----------------------

1. JRE\JDK for Java 8
2. Postgresql data base. https://www.postgresql.org/download/
 My settings can be found in application.properties file.
3. Access to user.home directory to create "/images/" directory and save files
 It can be changed by IMAGE_FOLDER_PATH constant.

WebServer
----------------------
Web server has basic authentication for the user (login/pass).
If user has not been logged in before then the server also requests first name 
and last name that can be used in the chat.
When user is logged in, the user subscribes to public topic (/topic/public) and 
can receive and send messages.

User gets unique avatar based on First Name and Last name hash codes that will
be present on page and in the chat.

All users and messages saved in the database.

When user closes page, it automatically unsubscribes from the chat.

WebClient API
----------------------

WebClient uses wsdl to connect to Spring. 
WebServise address is http://localhost:8080/ws/chatapi.wsdl

- Login 
Requires login name, password and first name with last name if user is new.

- Send message
Requires login name and message body.

- Get messages
Requires timestamp which uses to get messages between timestamp and current 
time.
Requires count of messages to limit.

- Logout
Requires login name and password 

HabrBot
----------------------
HabrBot is a sample client that responds with available link to a post 
from https://habr.com/

It sends every 5 second request to the server to get new messages for 10 seconds
and if one of them equals /habr or !habr then the bot sends message with habr 
link to the chat.

Interval can be changed using CHECK_NEW_HABR_REQUEST_TIMEOUT constant to 
decrease respond timeout.