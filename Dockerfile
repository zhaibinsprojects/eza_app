FROM tomcat:7-alpine

COPY ./sanbang-front/target/front /usr/local/tomcat/webapps/front
