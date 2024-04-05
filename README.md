# ticket-reservation

## App to be coded
I want to board a train from London to France. The train ticket will cost $5.

- Create an API where you can submit a purchase for a ticket. Details included in the receipt are:
    - a) From, To, User , price paid.
    - (i) User should include first and last name, email address
The user is allocated a seat in the train. Assume the train has only 2 sections, section A and section B.
- An API that shows the details of the receipt for the user
- An API that lets you view the users and seat they are allocated by the requested section
- An API to remove a user from the train
- An API to modify a user's seat

## Assessment Requirements:
- Code must compile with some effort on unit tests, doesn't have to be 100%, but it shouldn't be 0%.
- Please code this using Java
- Adding a persistence layer will be cumbersome, so just store the data in your current session/in memory.
- If a requirement is ambiguous or unclear, use your best judgment to interpret it and make what you believe are reasonable assumptions
- When the assignment is ready, send the console output from your app-server and app-client. You can also include your reasonable assumptions and known limitations.

## Assumptions & Limitations
- H2 In-memory DB is used (No data persistent). Schema will be created on app startup
- Default Train, Schedule and User are created during application startup
- Trains have only start and end stations. Intermediate train stations are not supported
- Each train runs once per day
- Train will have only 2 sections
- Each section has fixed max Seats
- A Single User can book N number of Tickets (Virtually no limit)
- Request Payloads are valid (Spring boot default payload validation available)
- Supported Date format 2024-04-01 (yyyy-MM-dd)
- Authentication & Authorization not enabled (APIs are publicly accessible)
- 

## Dev Environment
- Java - openjdk version "17.0.8" 2023-07-18 LTS
- To start app => `mvn spring-boot:run` -> App listens on 8080 port
- To run tests => `mvn test`

## APIs

### Reserve seat in the Train
`POST /api/v1/reservation`

`curl --location --request POST 'http://localhost:8080/api/v1/reservation' \
--header 'Content-Type: application/json' \
--data-raw '{
"from" : "London",
"to" : "France",
"paidAmount": "$5",
"date" : "2024-04-05",
"user" : {
"firstName": "valar",
"lastName": "pirai",
"email": "valar@gmail.com"
}
}'`

sample response

`{
"reservationId": 2,
"from": "London",
"to": "France",
"paidAmount": "$5",
"date": "2024-04-05",
"user": {
"id": 4,
"firstName": "Valar",
"lastName": "User",
"email": "valar@gmail.com"
},
"train": {
"trainId": 1,
"name": "Eurostar #1"
},
"section": "SECTION_A",
"seatNumber": 2,
"departureTime": "10:00:00",
"arrivalTime": "12:40:00",
"bookedAt": "2024-04-05T18:17:02.998084"
}`

### View the users and seat they are allocated by the requested section Train, Section & Date
`GET /api/v1/reservations?trainId=<trainId>&sectionName=<Section Name>&date=<Date>'`

`curl --location --request GET 'http://localhost:8080/api/v1/reservations?trainId=1&sectionName=SECTION_A&date=2024-04-05'`

### Shows the details of the receipt for the user
`GET /api/v1/reservation/<reservationId>`

`curl --location --request GET 'http://localhost:8080/api/v1/reservation/1'`

### Remove a user from the train
`DELETE /api/v1/reservation/<reservationId>`

`curl --location --request DELETE 'http://localhost:8080/api/v1/reservation/1'`

### Modify a user's seat
`PATCH /api/v1/reservation/<reservationId>/changeSeat`

`curl --location --request PATCH 'http://localhost:8080/api/v1/reservation/1/changeSeat' \
--header 'Content-Type: application/json' \
--data-raw '{
"seatNumber": 10
}'`

### List available Trains
`curl http://localhost:8080/api/v1/trains`

### List users
`curl http://localhost:8080/api/v1/users`
