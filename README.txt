# RMIStringComparison


**Student Name:** Paul Dolan    

**Student ID:** G00297086    

**Course:** Software Development    

**Current Year:** 4th Year    

**Module:** Distributed Systems    

**Lecturer:** Dr. John Healy    



## Introduction
For this project, we were required to use the Java RMI framework to develop a remote, asynchronous string
comparison service. 
This project compares two strings against each other and returns the distance between the 
strings depending on the algorithm that was chosen. 
The way the application works is there are two boxes shown 
to the user where a string can be input to compare them. 
It also offers the user a list of algorithms from a drop down menu.
When the user has input their message string, they must then click compare. 
This redirects the user to another page where 
the request is processed. 
This refreshes every 10 seconds to check if the proccess is complete. 
Once completed, the distance 
between string one and string two is output.




## Deployment

- Both the WAR and JAR files are needed to run this project which I have provided. I have added both of these file to the Deployment folder.    

- Apache Tomat needs to be installed which can be found at the following site https://tomcat.apache.org/download-90.cgi    

-  Once completed, the comparator.war must be transferred into the webapps folder of apache tomcats installation folder    

- To start the Apache Tomcat server, open up the command prompt and navigate to the bin directory and execute the command startup.bat.

- Start the RMI server by using the command line while navigating to the directory it's in **java –cp ./string_service.jar ie.gmit.sw.StringServiceServant** which will then start your server.   

- The go to **localhost:8080/comparator** in your web browser and you should be good to go


## Algorithms

**Levenshtein Distance** 

**Damerau-Levenshtein Distance**     

**Hamming Distance**    