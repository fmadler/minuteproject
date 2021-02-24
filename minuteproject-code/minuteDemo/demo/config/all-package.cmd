echo install mp-bsla in maven
set pwd=%cd%
cd ..\..\target\mp-bsla
call install_maven.cmd

echo bsla-jpa2
cd ..\..\demo\output
cd bsla-jpa2\petshop
call mvn clean package

echo CXF 
cd ..\..\cxf\petshop
call mvn clean package

echo JOOQ TECH
cd ..\..\JOOQ\jooq-tech
call mvn clean package

echo JOOQ petshop
cd ..\..\JOOQ\petshop\JOOQ
call mvn clean package

echo JPA2
cd ..\..\..\JPA2\petshop\JPA2
call mvn clean package

echo JSF 
cd ..\..\..\Primefaces-Spring\petshop
call mvn clean package

echo Mvn Spring Hibernate 
cd ..\..\MvnSpringHibernate
call mvn clean package

echo Mvn Spring JPA 
cd ..\MvnSpringJpa
call mvn clean package

echo REST-JEE
cd ..\REST-JEE\petshop
call mvn clean package

echo REST-SPRING MVC
cd ..\..\REST-SpringMVC\petshop
call mvn clean package

echo REST-CXF-SPRING
cd ..\..\REST-CXF-Spring\petshop
call mvn clean package

echo REST-CXF-JEE
cd ..\..\REST-CXF-JEE\petshop
call mvn clean package

echo WebService
cd ..\..\WS-JEE\petshop
call mvn clean package

echo Primefaces-Spring
cd ..\..\Primefaces-Spring\petshop
call mvn clean package

echo Primefaces-JEE
cd ..\..\Primefaces-JEE\petshop
call mvn clean package

echo Vaadin-spring
cd ..\..\Vaadin\petshop
call mvn clean package
