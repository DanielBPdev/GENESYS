# Agregar imagen del registro interno de openshift que contiene java 8 
# y un jar de wildfly swarm en su configuración simple
FROM ${docker.registry.name.archivos}/${base.servidor}

LABEL maintainer="Heinsohn Business Technology"

ENV TZ America/Bogota
RUN apk --update add tzdata && cp /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone && apk del tzdata

#install software
RUN apk add --no-cache --purge -uU libreoffice
RUN apk add --no-cache --purge -uU libreoffice-base
RUN apk add --no-cache --purge -uU libreoffice-lang-uk
RUN apk add --no-cache --purge -uU ttf-freefont 
RUN apk add --no-cache --purge -uU ttf-opensans 
RUN set -xe
RUN echo "http://dl-cdn.alpinelinux.org/alpine/edge/community" >> /etc/apk/repositories
RUN echo "http://dl-cdn.alpinelinux.org/alpine/edge/testing" >> /etc/apk/repositories
RUN apk add --no-cache --purge -uU ttf-font-awesome 
RUN apk add --no-cache --purge -uU ttf-ubuntu-font-family 
RUN apk add --no-cache --purge -uU ttf-inconsolata 
RUN apk add --no-cache --purge -uU libstdc++ 
RUN apk add --no-cache --purge -uU dbus-x11
RUN apk add --no-cache --purge -uU curl
RUN apk add --no-cache --purge -uU icu-libs
RUN apk add --no-cache --purge -uU unzip
RUN apk add --no-cache --purge -uU zlib-dev
RUN apk add --no-cache --purge -uU musl
RUN apk add --no-cache --purge -uU mesa-gl
RUN apk add --no-cache --purge -uU mesa-dri-swrast
RUN rm -rf /var/cache/apk/* /tmp/*
# Copiar el fragmento de standalone.xml y el archivo .war
COPY startup.sh /opt/
#COPY wildfly-swarm-simple.yaml opt/
COPY maven/ArchivosService.war /opt/

RUN mkdir /opt/conf/

EXPOSE 8100
EXPOSE 8101
EXPOSE 8102
EXPOSE 8103
EXPOSE 8104

# Inicializar el servidor swarm y openoffice
CMD ["sh", "/opt/startup.sh"]
