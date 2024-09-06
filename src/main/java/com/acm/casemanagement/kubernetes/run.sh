kubectl apply -f mysql-pvc.yaml
kubectl apply -f mysql-deployment.yaml
kubectl apply -f mysql-service.yaml
kubectl apply -f springdeployement.yaml
kubectl apply -f springboot-service.yaml

kubectl delete -f mysql-pvc.yaml
kubectl delete -f mysql-deployment.yaml
kubectl delete -f mysql-service.yaml
kubectl delete -f springdeployement.yaml
kubectl delete -f springboot-service.yaml


