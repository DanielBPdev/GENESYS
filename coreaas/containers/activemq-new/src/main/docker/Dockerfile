# Dockerfile para ActiveMQ Classic
FROM eclipse-temurin:17-jre-alpine

# Variables de entorno
ENV ACTIVEMQ_VERSION=5.18.7
ENV ACTIVEMQ_HOME=/opt/activemq
ENV ACTIVEMQ_USER=activemq
ENV ACTIVEMQ_GROUP=activemq
ENV PATH="${ACTIVEMQ_HOME}/bin:${PATH}"

# Crear usuario y grupo para ActiveMQ (comandos específicos de Alpine)
RUN addgroup -S ${ACTIVEMQ_GROUP} && \
    adduser -S -G ${ACTIVEMQ_GROUP} -h ${ACTIVEMQ_HOME} -s /sbin/nologin ${ACTIVEMQ_USER}

# Instalar dependencias necesarias
RUN apk add --no-cache \
        curl \
        wget \
        bash \
        procps

# Descargar e instalar ActiveMQ
RUN cd /tmp && \
    wget "https://archive.apache.org/dist/activemq/${ACTIVEMQ_VERSION}/apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz" && \
    tar -xzf apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz && \
    mv apache-activemq-${ACTIVEMQ_VERSION} ${ACTIVEMQ_HOME} && \
    rm apache-activemq-${ACTIVEMQ_VERSION}-bin.tar.gz && \
    mkdir -p ${ACTIVEMQ_HOME}/data ${ACTIVEMQ_HOME}/log && \
    chown -R ${ACTIVEMQ_USER}:${ACTIVEMQ_GROUP} ${ACTIVEMQ_HOME} && \
    chmod +x ${ACTIVEMQ_HOME}/bin/activemq

# Exponer puertos necesarios
# 61616: OpenWire/TCP
# 8161: Web Console
# 5672: AMQP
# 61613: STOMP
# 1883: MQTT
EXPOSE 61616 8161

# Cambiar al usuario no privilegiado
USER ${ACTIVEMQ_USER}

# Directorio de trabajo
WORKDIR ${ACTIVEMQ_HOME}

# Comando por defecto
CMD ["activemq", "console"]