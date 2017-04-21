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


