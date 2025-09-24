FROM jboss/keycloak:2.1.0.Final

ADD https.zip https.zip
RUN unzip -o https.zip 

USER root
RUN mv etc/x509 /etc
RUN chown -R root:root /etc/x509 && \
chmod 755  /etc/x509/https/ && \
chmod 604  /etc/x509/https/* 

WORKDIR $JBOSS_HOME

ADD modules-keycloak.zip modules-keycloak.zip
ADD keycloak.json keycloak.json

RUN unzip -o modules-keycloak.zip && \
rm -f modules-keycloak.zip

USER root
COPY standalone.conf /opt/jboss/keycloak/bin/
RUN chown jboss:root $JBOSS_HOME/standalone/configuration/standalone.xml && \
chown -R jboss:root $JBOSS_HOME/modules/system/layers/keycloak/com/microsoft && \
chown -R jboss:jboss $JBOSS_HOME/themes/asopagos-theme && \
chown -R jboss:jboss $JBOSS_HOME/bin/standalone.conf && \
chmod 666 $JBOSS_HOME/standalone/configuration/standalone.xml && \
chmod 666 $JBOSS_HOME/modules/system/layers/keycloak/com/microsoft/main/* && \
chmod 666 $JBOSS_HOME/bin/standalone.conf && \
chmod 755 -R $JBOSS_HOME/themes/asopagos-theme





USER jboss

WORKDIR $JBOSS_HOME/..