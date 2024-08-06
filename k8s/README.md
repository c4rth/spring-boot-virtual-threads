# Minikube

## Switch to docker of minikube
```
eval $(minikube docker-env)
```

## Deploy

```
kubectl apply -f k8s-roles.yaml
kubectl apply -f k8s-configmap.yaml
kubectl apply -f k8s-deploy.yaml
```

## Build
```
./gradlew clean build && docker build -f Dockerfile-2 . -t test:latest
```
