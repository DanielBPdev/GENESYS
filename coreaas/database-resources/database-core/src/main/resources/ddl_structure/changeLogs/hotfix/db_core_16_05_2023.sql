--liquibase formatted sql

if not exists (select * from sysobjects where name='legalizacionDesembolosoProveedor' and xtype='U')
    create table legalizacionDesembolosoProveedor(
    id bigint identity(1,1) not null,
    idProveedor bigint,
    sldId bigint,
    idPersona bigint,
    numeroRadicacion varchar(100),
    valorADesembolsar NUMERIC,
    porcentajeASembolsar numeric,
    fecha date
    );

if not exists (select * from sysobjects where name='legalizacionDesembolosoProveedor_aud' and xtype='U')
    create table aud.legalizacionDesembolosoProveedor_aud (
    id bigint identity(1,1) not null,
    REV bigint,
    REVTYPE smallint,
    idProveedor bigint,
    sldId bigint,
    idPersona bigint,
    numeroRadicacion varchar(100),
    valorADesembolsar NUMERIC,
    porcentajeASembolsar numeric,
    fecha date,
    constraint FK_legalizacionDesembolosoProveedor_aud_REV FOREIGN KEY(REV) REFERENCES core.aud.Revision(revId)
    );
