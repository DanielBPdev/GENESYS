

--liquibase formatted sql

--changeset kvides:1 comment:add coaddlumn estPersona

if not exists (select  * from information_schema.columns where table_schema = 'dbo' and table_name = 'EstablecimientosMediosPago' and column_name = 'estPersona')
begin
alter table dbo.EstablecimientosMediosPago add estPersona bigint

-- Agregar la llave foránea a la columna IdPadre en la tabla TablaHija
alter table dbo.EstablecimientosMediosPago
add constraint FK_EstablecimientosMediosPago_perid
foreign key (estPersona) references dbo.persona(perid);

end
--el objeto no cuenta con auditoria