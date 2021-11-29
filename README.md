# Zendesk Coding Challenge

This is a CLI ticket viewer that connects to the Zendesk API and gets all the tickets for the user's account and can also get individual tickets.

## Dependencies

• JDK 16.0.2 or later

• All other dependencies are included in the lib folder

## Instructions to Run the Program (Windows)

1. Clone the repository with the following command:

`$ git clone https://github.com/keshavba/zendesk-coding-challenge.git`

2. Navigate to the directory with the Main.java file in command prompt or other command line application
3. To compile the program, use the following command (if not using Windows, replace all instances of ";" with ":") -

`$ javac -cp ".;./lib/dotenv.jar;./lib/json.jar;./lib/junit.jar" Main.java`

4. To run the program, use the following commmand (if not using Windows, replace all instances of ";" with ":") -

`$ java -cp ".;./lib/dotenv.jar;./lib/json.jar;./lib/junit.jar" Main`

## Instructions to Run Tests

1. To run the tests, open the project in Visual Studio Code and installing the following extensions: "Extension Pack for Java", "Language Support for Java", "Project Manager for Java"
2. Make sure the JAR files are added to the class path
3. After opening TicketTest.java and TicketRequesterTest.java in VS Code, you should be able to see a green play button to the left side of the class and method signatures. Clicking on the play buttons runs the tests

## Design Choices

#### Connect to Zendesk API and request tickets

I decided to use OAuth 2.0 for authentication as it has many benefits over using basic authentication or API tokens. OAuth 2.0 prevents the need to use the user's email address and/or password to make API calls and hence reduces the possibility of the credentials being leaked. Also, the scope of an OAuth 2.0 token can be limited, and this allows the user to revoke the token if they suspect that it has been misused.

#### Paging through tickets

I decided to make API calls to fetch 25 tickets for each page instead of manually paginating after getting all the tickets. I believe this is a better approach as only the necessary number of pages of tickets will be requested if the user does not decide to page through all the tickets while all the tickets will be requested regardless of how many pages the user decides to view in the other approach. Also, by requesting tickets only when the user decides to view another page, the code became simplified and is a lot cleaner and easier to read.
