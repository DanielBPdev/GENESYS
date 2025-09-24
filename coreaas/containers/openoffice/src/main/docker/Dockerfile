FROM debian:9

RUN apt-get remove --purge libreoffice* && apt-get clean && apt-get autoremove && \
    apt-get update && \
    apt-get install -y procps && \
    apt-get install -y net-tools && \
    apt-get install -y telnet && \
    apt-get install -y freetype* && \
    apt-get install -y wget && \
    wget http://www-us.apache.org/dist/openoffice/4.1.5/binaries/es/Apache_OpenOffice_4.1.5_Linux_x86-64_install-deb_es.tar.gz && \
    apt-get install -y tar && \
    tar -xzf Apache_OpenOffice_4.1.5_Linux_x86-64_install-deb_es.tar.gz  && \
    apt-get install -y libxtst6 && \
    apt-get install -y libxt6 && \
    apt-get install -y libxrender1 && \
    rm -f Apache_OpenOffice_4.1.5_Linux_x86-64_install-deb_es.tar.gz && \
    dpkg -i es/DEBS/*.deb && \
    rm -fr es

EXPOSE 8100

CMD ["/opt/openoffice4/program/soffice.bin", "-headless", "-nodefault", "-nologo", "-nocrashreport", "-nofirststartwizard", "-nolockcheck", "-norestore", "-accept=socket,host=0.0.0.0,port=8100;urp;"]