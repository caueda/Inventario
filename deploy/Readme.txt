This file has some tips about this little project.

What we are using:
- Glassfish 4
- JDK 1.8.0_31
- MySQL
- Eclipse Version: Neon (4.6) OEPE (Oracle Enterprise Pack for Eclipse)

In this project we are using Glassfish 4, and unfortunatelly it has a bug in administration console. This
bug does not allow us to create a connection pool by the console, so we are going to do this by hand.
Lets put the code below inside the tag <resources> of the domain.xml.

You will need to change the ../glassfish4/glassfish/modules/jboss-logging.jar by 
jboss-logging-3.3.0.Final.jar (in the WEB-INF/lib)

	
	<jdbc-resource pool-name="InventarioPool" object-type="system-all" jndi-name="jdbc/InventarioPool"></jdbc-resource>
	<jdbc-connection-pool connection-validation-method="auto-commit" datasource-classname="com.mysql.jdbc.jdbc2.optional.MysqlDataSource" 
			wrap-jdbc-objects="false" res-type="javax.sql.DataSource" name="InventarioPool">
      <property name="URL" value="jdbc:mysql://localhost:3306/inventario?relaxAutoCommit=true"></property>
      <property name="driverClass" value="com.mysql.jdbc.Driver"></property>
      <property name="Password" value="welcome1"></property>
      <property name="portNumber" value="3306"></property>
      <property name="databaseName" value="inventario"></property>
      <property name="User" value="aplicacao"></property>
      <property name="serverName" value="localhost"></property>
    </jdbc-connection-pool>
    
Don't forget to put the jdbc driver in "../glassfish/domains/<your domain folder>/lib
In this project, we are using the lib mysql-connector-java-5.1.38-bin.jar



persistence.xml (version without datasource)
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.1"
	xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
	<persistence-unit name="inventario" transaction-type="JTA">

		<!-- provedor/implementacao do JPA -->
		<provider>org.hibernate.ejb.HibernatePersistence</provider>

		<!-- entidade mapeada -->
		<class>br.com.cursojsf.entities.Usuario</class>
		<class>br.com.cursojsf.entities.Categoria</class>
		<properties>
			<!-- dados da conexao -->
			<property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver" />
			<property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/inventario" />
			<property name="javax.persistence.jdbc.user" value="aplicacao" />
			<property name="javax.persistence.jdbc.password" value="welcome1" />
			<!-- propriedades do hibernate -->
			<property name="hibernate.dialect" value="org.hibernate.dialect.MySQL5InnoDBDialect" />
			<property name="hibernate.show_sql" value="true" />
			<property name="hibernate.format_sql" value="true" />

			<!-- atualiza o banco, gera as tabelas se for preciso -->
			<!-- property name="hibernate.hbm2ddl.auto" value="update" / -->

		</properties>
	</persistence-unit>
</persistence>


Setting the database.
We're using the wampserver, so the instructions that follow will describe the steps based on 
wampserver:
Open Windows command and go to the directory:
%WAMPSERVER_HOME%\wamp\bin\mysql\mysql5.6.17\bin
#Login in MySQL
mysql -u root
#Put a password for root
SET PASSWORD FOR 'root'@'localhost' = PASSWORD('welcome1');

################# D A T A B A S E (MySQL) #################
#Create a new database:
create database inventario;
#Create the user inventario
grant all on inventario.* to 'inventario'@'localhost';
#Set the password for the user 'inventario'@'localhost'
SET PASSWORD FOR 'inventario'@'localhost' = PASSWORD('welcome1');
#Import the script inventario.sql

#You will need to set the password for myphpadmin for the root.


To config the Wildfly module for jdbc:

Create a file path structure under the EAP_HOME/modules/ directory. 
For example, for a MySQL JDBC driver, create a directory structure as follows: EAP_HOME/modules/com/mysql/main/.