--liquibase formatted sql

--changeset abaquero:01
--comment: se eliminan los constraint de PK para las tablas de registro 2 de I-IP y registro 6 de F
alter table PilaArchivoIRegistro2 drop constraint PK_PilaArchivoIRegistro2_pi2Id
alter table PilaArchivoIPRegistro2 drop constraint PK_PilaArchivoIPRegistro2_ip2Id
alter table PilaArchivoFRegistro6 drop constraint PK_PilaArchivoFRegistro6_pf6Id

--changeset abaquero:02
--comment: Se agregan columnas temporales para la copia de los valores actuales de las llaves a las que se les quietará el IDENTITY
alter table PilaArchivoIRegistro2 add pi2Id_new bigint
alter table PilaArchivoIPRegistro2 add ip2Id_new bigint
alter table PilaArchivoFRegistro6 add pf6Id_new bigint

--changeset abaquero:03
--comment: Se copian los valores de las llaves a la columna temporal
update PilaArchivoIRegistro2 set pi2Id_new = pi2Id
update PilaArchivoIPRegistro2 set ip2Id_new = ip2Id
update PilaArchivoFRegistro6 set pf6Id_new = pf6Id

--changeset abaquero:04
--comment: Se eliman las columnas de llave con IDENTITY
alter table PilaArchivoIRegistro2 drop column pi2Id
alter table PilaArchivoIPRegistro2 drop column ip2Id
alter table PilaArchivoFRegistro6 drop column pf6Id

--changeset abaquero:05
--comment: Se crean nuevamente las columnas de llave sin IDENTITY ni restricción de NOT NULL
alter table PilaArchivoIRegistro2 add pi2Id bigint
alter table PilaArchivoIPRegistro2 add ip2Id bigint
alter table PilaArchivoFRegistro6 add pf6Id bigint

--changeset abaquero:06
--comment: Se copian de nuevo los valores de las llaves al campo respectivo
update PilaArchivoIRegistro2 set pi2Id = pi2Id_new
update PilaArchivoIPRegistro2 set ip2Id = ip2Id_new
update PilaArchivoFRegistro6 set pf6Id = pf6Id_new

--changeset abaquero:07
--comment: Se agrega la restricción de NOT NULL a las campos de llaves
alter table PilaArchivoIRegistro2 alter column pi2Id bigint not null
alter table PilaArchivoIPRegistro2 alter column ip2Id bigint not null
alter table PilaArchivoFRegistro6 alter column pf6Id bigint not null

--changeset abaquero:08
--comment: Se crean de nuevo los constraint como PK
alter table PilaArchivoIRegistro2 add constraint PK_PilaArchivoIRegistro2_pi2Id primary key (pi2Id)
alter table PilaArchivoIPRegistro2 add constraint PK_PilaArchivoIPRegistro2_ip2Id primary key (ip2Id)
alter table PilaArchivoFRegistro6 add constraint PK_PilaArchivoFRegistro6_pf6Id primary key (pf6Id)

--changeset abaquero:09
--comment: Se eliminan las columnas temporales
alter table PilaArchivoIRegistro2 drop column pi2Id_new
alter table PilaArchivoIPRegistro2 drop column ip2Id_new
alter table PilaArchivoFRegistro6 drop column pf6Id_new

--changeset abaquero:10
--comment: Se crean las secuencias para el manejo de los valores de PK
declare @inicioSeq bigint
select @inicioSeq = max(pi2Id) from PilaArchivoIRegistro2
select @inicioSeq = ISNULL(@inicioSeq,1)
exec('create sequence Sec_PilaArchivoIRegistro2 as bigint start with ' + @inicioSeq + ' increment by 1;')
select @inicioSeq = max(ip2Id) from PilaArchivoIPRegistro2
select @inicioSeq = ISNULL(@inicioSeq,1)
exec('create sequence Sec_PilaArchivoIPRegistro2 as bigint start with ' + @inicioSeq + ' increment by 1;')
select @inicioSeq = max(pf6Id) from PilaArchivoFRegistro6
select @inicioSeq = ISNULL(@inicioSeq,1)
exec('create sequence Sec_PilaArchivoFRegistro6 as bigint start with ' + @inicioSeq + ' increment by 1;')
