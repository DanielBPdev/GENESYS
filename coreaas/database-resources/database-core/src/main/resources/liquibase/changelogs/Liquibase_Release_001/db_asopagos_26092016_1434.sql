--liquibase formatted sql

--changeset alopez:1 stripComments:true /*26/09/2016-alopez-HU-TRA-76-161080,0161562*/


alter table NotDestinatario add nodTipoDestintatario varchar(3);

--changeset alopez:2 stripComments:true /*26/09/2016-alopez-HU-TRA-76-161080,0161562*/
alter table Comunicado add comFechaComunicado datetime2;
alter table Comunicado add comRemitente varchar(255);
alter table Comunicado add comSedeCajaCompensacion varchar(255);
alter table Comunicado add comNumeroCorreoMasivo bigint;
alter table Comunicado add comDestinatario varchar(255);
alter table Comunicado add comEstadoEnvio varchar(255);
alter table Comunicado add comMensajeEnvio varchar(255);
alter table Comunicado add comMedioComunicado varchar(255);

--changeset alopez:3 stripComments:true /*26/09/2016-alopez-HU-TRA-76-161080,0161562*/
exec sp_rename NotDestinatario, NotificacionDestinatario;
exec sp_rename NotEnviada, NotificacionEnviada;