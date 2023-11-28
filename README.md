# task-2

Application with Maven + Java + Liquibase + JDBC + PostgresQL

## Specification

## 2.1. SQL SELECT: Getting a product card based on its group

There are the following tables:

Table with product groups:

group (
  group_id VARCHAR(200) UNIQUE NOT NULL,
  parent_id VARCHAR(200),
  name VARCHAR(200) NOT NULL
);

Groups can be nested, for example: 
Electronics -> 
    Home Appliances -> 
        Vacuum Cleaners 
        
(a group within a group - its parentId = the groupId of the parent, and for the root group, parentId = null).

Table with product cards for groups:

position_card (
  id VARCHAR(200) PRIMARY KEY,
  is_shown_in_app BOOLEAN DEFAULT FALSE,
  name VARCHAR(200),
  group_id VARCHAR(200) NOT NULL UNIQUE
);

Product cards are assigned to groups.
### ToDo:
Write an SQL query to obtain the name (position_card.name) of a product card based on its group, with groupId as the query parameter. 
If a product card is not assigned to a group, then the card of its parent group is applied to this product, and so on. If a card can be unassigned from anyone. Implement this in one SQL query.

## 2.2. Token Retrieval/Update

Implementing the retrieval/update of a token for access to a third-party system, such as for sending messages. 
The token is required in a single instance on the server and has a finite lifespan, for example, half an hour.
You are given a class for working with the token, TokenProvider, and the Token class itself:

```
class TokenProvider {
    // returns the token
    public Token getToken() throws SomeExceptions {
        // ..
    }
}

class Token {
    // returns the expiration time of the token
    public Date getExpirationTime() {
        // ..
    }

    // refreshes the token
    public void refresh() throws SomeExceptions {
        // ..
    }
}
```

### ToDo:
To implement the TokenUtil class with one method, getToken(), which returns a valid Token or null in case of unsuccessful retrieval. 
The getToken function should complete within a maximum of 15 seconds, while sending the minimum number of requests to TokenProvider.getToken() 
and Token.refresh() if possible. Note that the functions TokenProvider.getToken() and Token.refresh() send requests to a third-party system 
and may take a long time to complete or even hang. Since this is all on the server, the implemented TokenUtil.getToken() function may be called 
by multiple threads simultaneously.


## Compile app 
Before build the application jar run be sure you have appropriated database properties in the file <a href="https://github.com/sergei-gots/task-2/blob/master/src/main/resources/liquibase.properties">/resources/liquibase.properties</a>

```
mvn clean package
```

The built jar will be stored in`/target` folder as `task2-1.0-SNAPSHOT-jar-with-dependencies.jar`

## Run app 

<br>
To run the application use:

```
java -jar target/task2-1.0-SNAPSHOT-jar-with-dependencies.jar
```


