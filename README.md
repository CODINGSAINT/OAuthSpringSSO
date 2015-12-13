# OAuthSpringSSO
OAuthSpringSSO is an attempt to perform SSO via OAuth and using incredible Spring framework. This is the server implementation on same, As a complete package there is a client called User Service (https://github.com/CODINGSAINT/us) Which can be looked as complete implementation.

It uses

  - Spring Boot
  - Maven
  - Spring OAuth 2.0
  - MySQL 

In order to use it , install sql files inside src/resources/sql:

 -  schema.sql
 -  data.sql

# Installation
```
git clone https://github.com/CODINGSAINT/OAuthSpringSSO.git
cd OAuthServer
mvn package
cd target
java -java OAuthSpringSSO-0.0.1-SNAPSHOT.jar
```


> This is just for tutorial . We don't take any responsibility for security
.

### Version
0.0.1

### How to Use
A get/post request with basic Auth

```
Method : GET/POST
URL:  http://localhost:9191/cds/auth/v1/oauth/token?username=admin&password=admin&grant_type=password
Basic Security Parameters
Username : coding_saint_client
password : coding_saint_client_secret
```
# Response 
```
{
  "access_token": "361c248d-d47c-4292-85ec-6791fcd61d27",
  "token_type": "bearer",
  "refresh_token": "5413ef08-91c0-4b9b-ab0b-bb0b2f47ef79",
  "expires_in": 43199,
  "scope": "read write"
}
```
Tt get Access Token via Refresh token
```
Method : GET
URL : http://localhost:9191/cds/auth/v1/oauth/token?grant_type=refresh_token&refresh_token=5413ef08-91c0-4b9b-ab0b-bb0b2f47ef79
```

To use this server for SSO you need a client , You can have a look at 
https://github.com/CODINGSAINT/us

