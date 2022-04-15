# Very simple online shop

An application (client-server) that is used to make some simple orders over the internet. The REST is used for communication between client side and server side.

Common (for each module) tools used
- Java 11
- Lombok
- Gradle

Frontend (desktop app) tools used
- JavaFX
- JavaFX Weaver
- Spring Boot Web

Backend tools used
- Spring Boot Data Jpa
- Spring Boot Web
- Spring Boot Validation
- PostgreSQL

## Key Features 

- Sign up (sign in) over the desktop application
- Administator's ability to block (unblock) a specific user
- Sending requests to the server to manipulate (receive, create, delete, update) data (products, orders, users) according to the REST

## Sign in / Sign up

When a user sends data during the sign in process the server receives the data and tries to find the user in the database. If the user's data is actually in the database the user successfully completes the sign in proccess otherwise an error alert occurs.

![Alt Text](https://github.com/Soqva/online-shop/blob/master/github/gifs/1.gif)

Similar actions is being happened during the registation (sing up) process.

![Alt Text](https://github.com/Soqva/online-shop/blob/master/github/gifs/2.gif)

## Making an order

A user has to add all needed products to the cart, which is used to keep user's products for purhcase, then next action is click the "Create order" button. The user 
goes to the order page. Here is the orders that the user did for all time. Right there the user submits their order by clicking the "Create order" button.

![Alt Text](https://github.com/Soqva/online-shop/blob/master/github/gifs/3.gif)

A user with the ADMIN role creates their orders in the same way as a regular user, but there is an individual order page for shop administrators.

![Alt Text](https://github.com/Soqva/online-shop/blob/master/github/gifs/4.gif)

## Blocking a specific user

An administator has the ability to block users in the shop. He just needs to go to the user page where he can click the "Receive all users" button and then choose one of the received users to ban them. A blocked user can no longer log into their account while they are blocked.

![Alt Text](https://github.com/Soqva/online-shop/blob/master/github/gifs/5.1.gif)

Of course a blocked user can be unblocked by an shop administrator.

![Alt Text](https://github.com/Soqva/online-shop/blob/master/github/gifs/5.2.gif)
