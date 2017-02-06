# karate-admin-service

###Building, Deploying and Testing
Running the maven goals clean and install will build, deploy and test the project. The database is set to `jdbc:h2:~/hello`. The port used for HTTP requests/the AJAX client is `localhost:8000`. All services are accessed with the URL prefix `localhost:8000/service`.


###The Domain
This web service is intended to be used by the UoA Karate Club in order to keep track of members, attendance at trainings (sessions) and fees for each member. Some members are students. Some sessions are gradings. The domain class diagrams are roughly shown in UML.png.


###AJAX Client
To access the AJAX client, run the maven clean install goals and then run the jetty server. Navigate to a browser and then enter localhost:8000 as the URL. This will load a web page that displays a list of all the members in the MEMBERS database table, in blocks of 10 and with links to the next and previous block. The "Add Member" button will bring up a modal where the user can input the details of a new member to be added to the database. After confirming, the new member will be visible in the members table (may be on another page). The other options on the navbar don't lead anywhere as of yet.


###Asynchronous Use Case
Invoices are generated on the fly and not stored/persisted in an Asynchronous request, as per the priority scheduling use case. Generating invoices may be seen as a "computationally expensive" operation. This is shown in asyncTest(), which can be found in MemberWebServiceTest.java. 


###Leveraging the HTTP Protocol
- Cookies and @CookieParams are used when getting certain objects in order to tell the server to ignore its cache and query the database for a fresh object.
- @QueryParams are used in the requests from the AJAX client for creating a member and specifying the size of the list of all members to get. 
- HATEOAS is used in the @GET method getAllMembers(int start, int size). urlNext and urlPrev are stored in the MemberListWrapper object and sent back to the AJAX client. The urls specify links for the next 10 entries in the table, or the previous 10. To see this in action, make sure persistence.xml schema-generation is set to "update" not "drop-and-create" and maven install a few times to get the necessary rows in the Member table.

