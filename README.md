# skarlat
Advances Service Engineering course contributions

----------------------------------------------
Main Application

-------sensorApplication----------------------

To build an app and docker image and store it in dockerhub:

1. build anapplication
	$ mvn clean package
2. build docker image
	$ ./docker-build.sh
3. login to docker
	$ docker login
4. push latest image to dockerhub:
	$ docker push lenaskarlat/sensor-app
To deploy an app at the remote host:
1. pull docker from dockerhub
	$ docker pull lenaskarlat/sensor-app
2. run docker container
	$ docker run -dit -p 34006:8080 --name=lenaskarlat_sensorapp lenaskarlat/sensor-app

To run on local host:
	$ ./docker-run.sh


Amazon cloud:
1. created tiny amazon linux machine
2. connected to it using ssh
3. installed docker according to http://docs.aws.amazon.com/AmazonECS/latest/developerguide/docker-basics.html
4. docker pull lenaskarlat/sensor-app
5. docker run -dit -p 34006:8080 --name=lenaskarlat_sensorapp lenaskarlat/sensor-app (TODO: need to add -v to mount a folder because of properties file and keys)
6. amazon console -> Security Groups added Custom tcp post 34006
7. app is running here:
will be ec2-54-149-45-132.us-west-2.compute.amazonaws.com:34006

---------------------------------------------------------
Sensor Simulator

-----------rangeSensor Apllication-----------------------

This software is built upon the package provided by the AWS IoT Connect Wizard.

The main class is PublishController. The main method takes the arguments from the command line.
To run the sensor application 
	$mvn exec:java -Dexec.mainClass="PublishController" -Dexec.args="-clientEndpoint <here the enpoint must be specified for the AWS IoT, this endpoint is taken from AWS IoT, go to main dashboard and then settings> -clientId <this client id has to conform to the client id indicated in the security policy of a sensor thing created in aws iot dashboard, currently this policy allows any clientId> -certificateFile <full path to the certificate for ssh connection to the thing (seedetails for AWS IoT) The extension of file has to be .pem.crt> -privateKeyFile <this is the full path to the privvate key file provided along with certificate, the extension should be .pem.key> -topic <here a topic has to be indicated where our sensor has to publish messages, has to be sensors/sensor_A1 without quotes> -thingName <thing name has to be indicated here, thing name is the one which was used to create a sensor thing in the aws iot dashbord, please note, this is not 'arn' provided in the thing details>"

Sensor pass message in format {"dataFrameMessage":<content>}, where content is a big ;-separated string consisting of sensor name, timestamp, and array of numbers as string.
For example: {"dataFrameMessage":"sensor_A1;1352866076810000000;[4798, ....<here the array consists of 19200 values that represent distance from sensor to objects in a certain resolution, the resolution is 120x160> "}
As was said before, the message goes to the topic sensors/sensorName and then according to this topic, sensor data is received by application, and also processed by lambda functions (the details will be stated in the AWS IoT section)

---------------------------------------------------------
Database Service

InfluxDB v1.2
The influxdb database is running in t2.medium. 
Installation on AWS ( https://medium.com/brightergy-engineering/install-and-configure-influxdb-on-amazon-linux-b0c82b38ba2c ):

	$wget https://dl.influxdata.com/influxdb/releases/influxdb-1.2.4.x86_64.rpm
	$sudo yum localinstall influxdb-1.2.4.x86_64.rpm
	$sudo service influxdb start

	Open internal influx console:
	$influx

Create a database by:
> create database DatabaseName

Influx is a time series database (https://docs.influxdata.com/influxdb/v1.2/introduction/getting_started/ )

All sensor data is imported once to the database PositioningData. To import all sensor data from prepared file: go to folder where the file for_influx_sensors.txt is located, run the next command. Command loads sensor data in measurement “sensor” (according to what is inside the file). Permissions and ports have to be open.
	$curl -i -XPOST 'http://<host_ip>:8086/write?db=PositioningData' --data-binary @for_influx_sensors.txt

In the configuration file enable admin interface in the [admin] section enabled=true
and in [http] section enable authorization by setting auth-enabled=true in /etc/influxdb/influxdb.conf  by using editor, e.g., vi. 
	$echo $INFLUXDB_CONFIG_PATH /etc/influxdb/influxdb.conf

Create user and password for influx instance  https://docs.influxdata.com/influxdb/v1.2/query_language/authentication_and_authorization/ 
Admin interface runs on port 8083 (full link is not provided for public).

To establish connection in the main application:
 <dependency>
   <groupId>org.influxdb</groupId>
   <artifactId>influxdb-java</artifactId>
   <version>2.6</version>
</dependency>
or just use simple http post requests to write data. In the main application both approaches are implemented.

To get data from influx (java):
	Query query = new Query("SELECT * FROM sensor", databaseName);
	QueryResult queryResult = influxDB.query(query);

and then QueryResult has to be parsed.
Details: https://github.com/influxdata/influxdb-java 

To write data to Influx, it is necessary to provide a string of measurement, where the first word in that string is like a table name, then go tags and values. Tags are indexed, values-not. 
Example insert data with curl with credentials:
	$curl -i -XPOST 'http://ip.of.influx.db:8086/write?db=DataBaseName' -u login:password --data-binary 'seriesName,tag1=value01,tag2=value02 value=someValue time'

Inserting data to Influx is performed in from the main application and from lambda functions triggered by receiving a message to AWS IoT MQTT from AWS IoT.




