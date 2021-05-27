FROM registry.access.redhat.com/ubi8/openjdk-11:latest

ADD main.java /tmp/main.java

# Check the current configuration
RUN java /tmp/main.java;


# Enable TLS_ECDH_* ciphers
user root
RUN sed -i 's/ ECDH,//' /etc/crypto-policies/back-ends/java.config
user 184

# Check the current configuration
RUN java /tmp/main.java;


CMD ["java","/tmp/main.java"]
