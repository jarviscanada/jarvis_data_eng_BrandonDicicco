# Introduction
This application utilizes the JDBC API to develop an application that connects to a PostgreSQL database 
through a Java program. It allows the user to perform CRUD operations on the Customer and Order data. Data
access was incorporated by utilizing the Data Access Object pattern.
The PSQL database instanced was deployed using Docker. The application was built and managed by Maven, and 
was developed using IntelliJ and the database connectivity tool.

# Implementation
## ER Diagram
![Image of ER Diagram](./assets/jdbc_er.png)

## Design Patterns
There are two commonly used design patterns when it comes to dealing with databases.

### Data Access Object Pattern
The Data Access Object (DAO) is a class that implements CRUD operations on an object. It interacts with
a Data Transfer Object (DTO), which is a model of the data. The DAO uses abstraction to separate business layer 
logic from the low level data layer. The benefits of this pattern comes through abstraction. Changing business
logic can easily be handled through the reuse of interfaces. The DAO pattern also allows access to multiple
tables, which also has both advantages and disadvantages.

### Repository Pattern
The Repository pattern only has single-table access per class, instead of access to the whole database. It
performs joins in data through the code, rather than in the database itself. Through the use of sharding, 
the Repository pattern is better for distributed databases. However, the Repository pattern is worse
for normalized data compared to the DAO due to the DAO having simpler joins.

# Testing
Testing was done by first creating a new PSQL database called hplussport. It was accessed 
through IntelliJ's database tool, but could also be accessed in a terminal using the command
`psql -h localhost -U postgres -d hplussport`. The given SQL scripts were executed to create the
database and the sample data that was inside. The `JDBCExecutor` was used to retrieve the query results
from the DAO by executing a connection and returning the DTO. The returned results were confirmed 
correct by comparing them using the PSQL client.