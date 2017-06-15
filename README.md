# skarlat
Advances Service Engineering course contributions

-------sensorApplication---------

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
5. docker run -dit -p 34006:8080 --name=lenaskarlat_sensorapp lenaskarlat/sensor-app
6. amazon console -> Security Groups added Custom tcp post 34006
7. app is running here:
ec2-54-149-45-132.us-west-2.compute.amazonaws.com:34006

---------------------------------------------------------
InfluxDB v1.2
The influxdb database is running in t2.medium.
Installation on AWS ( https://medium.com/brightergy-engineering/install-and-configure-influxdb-on-amazon-linux-b0c82b38ba2c )

$wget https://dl.influxdata.com/influxdb/releases/influxdb-1.2.4.x86_64.rpm
$sudo yum localinstall influxdb-1.2.4.x86_64.rpm
$sudo service influxdb start

Open internal influx console:
$influx

Create a database by:
> create database PositioningData

Influx is a time series database (https://docs.influxdata.com/influxdb/v1.2/introduction/getting_started/ )

All sensor data is imported once. To import all sensor data from prepared file: go to folder where the file for_influx_sensors.txt is located, run command. Command loads sensor data in measurement “sensor” (according to what is inside the file). Permissions and ports have to be open.
$curl -i -XPOST 'http://<host_ip>:8086/write?db=PositioningData' --data-binary @for_influx_sensors.txt

In the configuration file enable admin interface in the [admin] section enabled=true
and in [http] section enable authorization by setting auth-enabled=true in /etc/influxdb/influxdb.conf  by using editor, e.g., vi. 
$echo $INFLUXDB_CONFIG_PATH /etc/influxdb/influxdb.conf

Create user and passwordfor PositioningData database user https://docs.influxdata.com/influxdb/v1.2/query_language/authentication_and_authorization/ 
Admin interface runs on port 8083 (full link is currently not provided).

To establish connection in the main application:
 <dependency>
   <groupId>org.influxdb</groupId>
   <artifactId>influxdb-java</artifactId>
   <version>2.6</version>
</dependency>

To get data from influx:
  Query query = new Query("SELECT * FROM sensor", databaseName);
  QueryResult queryResult = influxDB.query(query);

and then QueryResult has to be parsed.
Details: https://github.com/influxdata/influxdb-java 




