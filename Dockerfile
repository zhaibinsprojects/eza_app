FROM tomcat:7-alpine

COPY ./sanbang-front/target/front.war /usr/local/tomcat/webapps/front.war
