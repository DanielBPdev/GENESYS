
if not exists (select * from sysobjects where name='legalizacionDesembolosoProveedor_aud' and xtype='U')
    create table legalizacionDesembolosoProveedor_aud (
    id bigint identity(1,1) not null,
    REV bigint,
    REVTYPE smallint,
    idProveedor bigint,
    sldId bigint,
    idPersona bigint,
    numeroRadicacion varchar(100),
    valorADesembolsar NUMERIC,
    porcentajeASembolsar numeric,
    fecha date
    );