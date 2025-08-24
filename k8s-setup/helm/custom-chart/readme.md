
helm create custom-chart


# render the chart from value file 
helm template custom-chart  --values custom-chart/values.yaml

helm template custom-chart  --values custom-chart/stag-values.yaml