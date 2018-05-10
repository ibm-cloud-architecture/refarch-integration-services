# STAGE: Build
FROM ibmcase/gradle:latest as builder

# Copy Code Over
RUN mkdir /servapp
WORKDIR /servapp
COPY . /servapp

# Build jar
RUN gradle build

# STAGE: Deploy
FROM openliberty/open-liberty
MAINTAINER https://github.com/ibm-cloud-architecture - IBM - Jerome Boyer
RUN installUtility install --acceptLicense jpa-2.0  openapi-3.0 adminCenter-1.0 jaxws-2.2 jaxrs-2.0 ejbLite-3.2 localConnector-1.0 apiDiscovery-1.0
RUN apt-get update -y && apt-get install -y openssh-server

# Create Directory
RUN mkdir /opt/ibm/wlp/usr/shared/config/lib

# Add jar and war files
COPY --from=builder /servapp/src/main/liberty/config/server.xml /opt/ibm/wlp/usr/servers/defaultServer
COPY --from=builder /servapp/lib/db2jcc4.jar /opt/ibm/wlp/usr/shared/config/lib
COPY --from=builder /servapp/build/libs/refarch-integration-services*.war /opt/ibm/wlp/usr/servers/defaultServer/apps

# Set Port
EXPOSE 9080 9443

# Start
ENTRYPOINT ["/opt/ibm/wlp/bin/server", "run", "defaultServer"]
