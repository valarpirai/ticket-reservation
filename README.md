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
