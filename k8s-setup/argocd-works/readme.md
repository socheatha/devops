 kubectl get clusterissuer 
 # letsencrypt-prod -> ns= default 

 kubectl get svc -A | grep "argocd-server"
 # get only service inside the argocd namespace 
 kubectl get svc -n argocd

# username of argo is: admin 
kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d

k get cr -n argocd
k get service -n argocd | grep 'argocd-server'