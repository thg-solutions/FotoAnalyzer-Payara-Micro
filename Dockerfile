FROM payara/micro:6.2024.5-jdk21
COPY build/libs/hello-world-0.1-SNAPSHOT.war $DEPLOY_DIR
