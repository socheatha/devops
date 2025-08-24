# get list
helm list
helm list
helm repo list

# first chart with helm 

helm create nginx-chart 
# to test your chart syntax (validation)
helm lint nginx-chart 

# to render your chart ( see the configuration after inject the value file )
helm template nginx-chart 
helm template nginx-chart --values prod-value.yaml

# to release your chart (deploy your app )
helm install/uninstall nginx-release nginx-chart 
# upgrade the old release 
helm upgrade nginx-release nginx-chart 

# if old release exist, we upgrade 
# if not exist, we will create a new one 
helm upgrade nginx-release nginx-chart --install 

# test
helm test nginx-release

kubectl get app -A 