# base image 
FROM ubuntu:latest 
# steps or configuration instruction 
RUN apt update && apt upgrade -y
RUN echo 'echo "hello world" ' > helloworld.sh
RUN echo 'echo "hello  facebook! " ' > facebook.sh

# execution instruction 
# cmd , entrypiont 
ENTRYPOINT [ "sh", "facebook.sh" ]
