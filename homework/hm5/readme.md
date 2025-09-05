# Build and use local docker image instead of pull from cloud:
#Clone repo then rename `dev.Dockerfile` to default filename `Dockerfile`
#Build docker image `docker build -t my-spring-app:latest .`
#Docker images


# Push to DH
#docker tag my-spring-app:latest socheatha/my-spring-app:v0.02
#docker push socheatha/my-spring-app:v0.02

## Create K8S secret to store password of postgres database
#workspace/postgres-secret.yaml
#kubectl apply -f postgres-secret.yaml
#kubectl get secret -n homework

## Create statefulset & service
#workspace/postgres-statefulset.yaml
#kubectl apply -f postgres-service.yaml
#kubectl get service -n homework
#kubectl get pod -n homework
#kubectl get pvc -n homework
#kubectl get pv -n homework

#workspace/postgres-service.yaml
#kubectl apply -f postgres-statefulset.yaml
#kubectl get statefulset -n homework

## See the result of database
#kubectl -n homework get pods
#kubectl -n homework logs statefulset/postgres-stateful-app


## Sprint project deployment
#workspace/spring-deployment.yaml
#kubectl apply -f spring-deployment.yaml
#kubectl get service -n homework
#kubectl get deployment -n homework
#kubectl get pod -n homework

kubectl -n homework logs -f deploy/spring-app
kubectl -n homework get pods -l app=postgres

# Apply Ingress



```
kubectl run pg-client --rm -it \
  --image=postgres:15 \
  --namespace homework \
  --env="PGPASSWORD=password123" \
  -- psql -h postgres-stateful-svc -U demouser -d demodb
```