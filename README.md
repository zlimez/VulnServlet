# VulnServlet
Refer to https://www3.ntu.edu.sg/home/ehchua/programming/howto/Tomcat_HowTo.html, https://www3.ntu.edu.sg/home/ehchua/programming/howto/EclipseJava_HowTo.html
for setup of tomcat in eclipse.

Git clone the entire repository and open the directory as an eclipse project.
Add commons-collections4.jar into the WebContent/WEB-INF/lib folder where clsspath is dynamically managed in a dynamic web project.
The Servlet code and index.html page as per sepcified in the repository. The <form action="">'s reference should be modified to the IP address of your pc.
Run the project as server using tomcat

Configure firewall to accept incoming request. 
Go to another pc to connect to the server.
Using burp suite inject the base64.txt payload into the Cookie value of a request and send it to the server to achieve RCE. (google chrome should be launched)
