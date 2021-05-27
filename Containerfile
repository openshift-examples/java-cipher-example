FROM registry.access.redhat.com/ubi8/openjdk-11:latest

ADD main.java /tmp/main.java

#ADD jce_policy-8.zip /tmp/jce_policy-8.zip
#USER root
#RUN unzip -o -j -q /tmp/jce_policy-8.zip -d ${JAVA_HOME}/lib/security/
#USER 18cd 5

CMD ["java","/tmp/main.java"]
