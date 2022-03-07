# hiring-test-jesus-osuna 
ORIGINAL
This standalone app receives two input from the user, a URL and an optional message.
The normal behavior can be tested against any “echo” endpoint that accept POST like “https://postman-echo.com/post”.

The main task is to “fix” the user detected:
Whenever I misspell the URL or point to some endpoint that is down, I have no idea what's going on or why it failed.

The user also add:
It would be nice to being able to test non POST endpoints, at least GET endpoints or being clear that it only accept POST URLs.

FIXED
This standalone app receives three inputs from the user, a URL, an optional message and the HTTP method to be tested.
The normal behavior can be tested against any “echo” endpoint that accept POST,GET,PUT,DELETE like 
“https://postman-echo.com/post”,“https://postman-echo.com/get”,“https://postman-echo.com/put”
,“https://postman-echo.com/delete”.

If something go wrong, the app give you enough information to see the problem, you can see the status code returned 
by the HTTP petition and an informative message with a brief description of the problem. 

If the url given doesn't match with de pattern "http" or "https" the app return the following error message 
"The url must be valid example **https://postman-echo.com/METHOD** or **http://postman-echo.com/METHOD**".

If the method inputted do not match with the method valid to test the app return the following error message
"You can only test, POST, GET, PUT, DELETE methods".

