all:
	mvn clean compile -U;

test:
	mvn clean test

h2_setup:
	cd ~/.m2/repository/com/h2database/h2/2.2.224/
	java -cp h2-2.2.224.jar org.h2.tools.Shell

h2_html_interface:
	java -cp ~/.m2/repository/com/h2database/h2/2.2.224/h2-2.2.224.jar org.h2.tools.Server

server_run:
	mvn exec:java -Dexec.mainClass="Server.Server"

client_run:
	mvn exec:java -Dexec.mainClass="User.UserLogin"