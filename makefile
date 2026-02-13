all:
	mvn clean compile -U;
comunication:
	mvn compile -DcompileSourceRoots=src/Comunication
comunication_type:
	mvn compile -DcompileSourceRoots=src/Comunication/ComunicationType
database_objects:
	mvn compile -DcompileSourceRoots=src/Comunication/DatabaseObjects
reply:
	mvn compile -DcompileSourceRoots=src/Comunication/Reply
request:
	mvn compile -DcompileSourceRoots=src/Comunication/Request
server:
	mvn compile -DcompileSourceRoots=src/Server
engines:
	mvn compile -DcompileSourceRoots=src/Server/Engine
