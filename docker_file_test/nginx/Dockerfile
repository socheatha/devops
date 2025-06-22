# base image 
FROM nginx:latest

COPY index.html /usr/share/nginx/html/index.html
EXPOSE 80

# run nginx in foregroup mode vs background mode (Keep running)
# ENTRYPOINT ["nginx", "-g", "daemon off;"]
