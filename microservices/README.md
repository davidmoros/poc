# How to deploy with Docker

## Package every project
mvn clean package -f admin-server/pom.xml  
mvn clean package -f config-server/pom.xml  
mvn clean package -f eureka-server/pom.xml  
mvn clean package -f film-service/pom.xml  
mvn clean package -f gateway-server/pom.xml  
mvn clean package -f serie-service/pom.xml  
mvn clean package -f user-service/pom.xml  

## Create the Docker images
docker build -t admin-server ./admin-server  
docker build -t config-server ./config-server  
docker build -t eureka-server ./eureka-server  
docker build -t film-service ./film-service  
docker build -t gateway-server ./gateway-server  
docker build -t serie-service ./serie-service  
docker build -t user-service ./user-service

## Create an isolated network
docker network create net-poc_microservices

## Run Zipkin container and connect to isolated network
docker pull openzipkin/zipkin  
docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin  
docker network connect net-poc_microservices zipkin

## Run the containers of POC 
docker run --rm -d -p 8081:8081 --network net-poc_microservices --name config-server config-server  
docker run --rm -d -p 8761:8761 --network net-poc_microservices --name eureka-server eureka-server  
docker run --rm -d -p 8086:8086 --network net-poc_microservices --name admin-server admin-server  
docker run --rm -d -p 8090:8090 --network net-poc_microservices --name gateway-server gateway-server  
docker run --rm -d --network net-poc_microservices --name user-service user-service  
docker run --rm -d --network net-poc_microservices --name film-service film-service  
docker run --rm -d --network net-poc_microservices --name serie-service serie-service  


# How to deploy with Docker compose

## Run Zipkin container and connect to private network
docker pull openzipkin/zipkin  
docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin  
docker network connect micro zipkin

## Build and Up Docker compose
docker-compose up -d --build


# HOw to deploy with Kubernetes

## Install minikube in local environment

https://minikube.sigs.k8s.io/docs/start/

## How to use local docker images

# Load local images to minikube 

https://minikube.sigs.k8s.io/docs/handbook/pushing/#7-loading-directly-to-in-cluster-container-runtime

# Build images into minikube 

https://stackoverflow.com/questions/42564058/how-to-use-local-docker-images-with-minikube/48999680#48999680

# HELM

https://artifacthub.io/packages/helm/prometheus-community/prometheus
https://artifacthub.io/packages/helm/grafana/grafana
https://artifacthub.io/packages/helm/grafana/loki
https://artifacthub.io/packages/helm/grafana/promtail

https://blog.marcnuri.com/instalar-prometheus-grafana-minikube

# Access services

## Eureka 
http://localhost:8761

## Spring Boot Admin
http://localhost:8086

## Zipkin
http://zipkin:9411

# Other Software

## Octant
https://octant.dev/

## Prometheus
https://prometheus.io/
https://refactorizando.com/spring-boot-actuator-prometheus-grafana/

## Graphana
https://grafana.com/
https://grafana.com/grafana/dashboards/10280
https://grafana.com/grafana/dashboards/1598
https://grafana.com/grafana/dashboards/315
https://grafana.com/grafana/dashboards/6417

