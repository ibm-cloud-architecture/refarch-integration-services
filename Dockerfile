FROM websphere-liberty:webProfile7
MAINTAINER https://github.com/ibm-cloud-architecture - IBM - Jerome Boyer
RUN installUtility install --acceptLicense jpa-2.0 jaxws-2.2 adminCenter-1.0 jaxrs-2.0 ejbLite-3.2 localConnector-1.0
RUN apt-get update -y && apt-get install -y openssh-server

RUN mkdir /servapp
WORKDIR /servapp
COPY . /servapp
RUN cd /servapp
ADD src/main/liberty/config/server.xml /opt/ibm/wlp/usr/servers/defaultServer
RUN mkdir /opt/ibm/wlp/usr/shared/config/lib && mkdir /opt/ibm/wlp/usr/servers/defaultServer/logs
ADD ./lib/db2jcc4.jar /opt/ibm/wlp/usr/shared/config/lib
ADD ./build/libs/refarch-integration-services.war /opt/ibm/wlp/usr/servers/defaultServer/apps
EXPOSE 9080 9443
#ENTRYPOINT ["/opt/ibm/wlp/bin/server", "start", "defaultServer"]
