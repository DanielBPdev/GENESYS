if not exists (select * from sys.objects as o where o.name ='ControladorCarteraPlanilla')
begin
    create table ControladorCarteraPlanilla(
    ccpId BIGINT NOT NULL IDENTITY(1,1),
    ccpPasoCartera BIT NULL,
    ccpFechaCreacionRegistro DATETIME2 NULL,
    ccpRegistroGeneral BIGINT NULL,
    ccpCantidadAportes BIGINT NULL,
    ccpIndicePlanilla BIGINT NULL
    CONSTRAINT PK_ControladorCarteraPlanilla_ccpId PRIMARY KEY (ccpId)
    );
end