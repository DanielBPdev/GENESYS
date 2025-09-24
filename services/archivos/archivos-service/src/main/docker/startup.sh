#!/bin/sh
echo 'Iniciando Wildfly Swarm Servicio de Archivos'

java \
  -Xms256m -Xmx3500m \
  -XX:+UseG1GC -XX:MaxGCPauseMillis=200 \
  -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap \
  -jar /opt/wildfly-swarm-messaging-0.0.2-SNAPSHOT.jar \
  -s /opt/conf/wildfly-swarm-messaging.yaml /opt/ArchivosService.war \
  -Dswarm.context.path=ArchivosService \
  -Dinfinispan.service.host=$INFINISPAN_SERVICE_HOST \
  -DLionConfigurationImpl=com.asopagos.lion.config.LionConfigurationAsopagosImpl