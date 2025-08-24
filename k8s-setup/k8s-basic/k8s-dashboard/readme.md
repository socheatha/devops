kubectl get all -n kube-system


# make sure to change from NodePort to ClusterIP 
kubectl get svc -n kube-system 
kubectl edit svc kubernetes-dashboard -n kube-system
# \type

k8s-dashboard.msswebcenter.com 
# test DNS -> IP 
nslookup k8s-dashboard.msswebcenter.com



kubectl get ingress -n kube-system
kubectl get cert -n kube-system # look for Ready State 