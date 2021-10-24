kubectl apply -f ./k8s/service-accounts.yml
kubectl apply -f ./k8s/role.yml
kubectl apply -f ./k8s/roleBinding.yml

kubectl apply -f ./k8s/deployment-config-server.yml
kubectl apply -f ./k8s/deployment-eureka-server.yml
kubectl apply -f ./k8s/deployment-admin-server.yml
kubectl apply -f ./k8s/deployment-gateway-server.yml
kubectl apply -f ./k8s/deployment-user-service.yml
kubectl apply -f ./k8s/deployment-film-service.yml
kubectl apply -f ./k8s/deployment-serie-service.yml
kubectl apply -f ./k8s/service-config-server.yml
kubectl apply -f ./k8s/service-eureka-server.yml
kubectl apply -f ./k8s/service-admin-server.yml
kubectl apply -f ./k8s/service-gateway-server.yml
kubectl apply -f ./k8s/service-user-service.yml
kubectl apply -f ./k8s/service-film-service.yml
kubectl apply -f ./k8s/service-serie-service.yml
#kubectl delete -n default pod pod-config-server