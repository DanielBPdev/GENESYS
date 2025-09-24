FROM fabric8/java-alpine-openjdk8-jre:1.4.0


ENV TIPO_SERVIDOR wildfly-swarm-afiliacion-personas-web-composite-0.0.2-SNAPSHOT.jar

ENV AB_OFF true

ENV BASE /opt

COPY $TIPO_SERVIDOR $BASE/
 
ENV TZ America/Bogota

RUN apk --update add tzdata && cp /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone && apk del tzdata
