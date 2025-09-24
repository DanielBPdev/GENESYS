--liquibase formatted sql

--changeset clmarin:01
--comment: Actualizacion de campo vcoTipoVariableComunicado
UPDATE VariableComunicado SET vcoTipoVariableComunicado = NULL where vcoTipoVariableComunicado = 'USUARIO' 

--changeset jzambrano:02
--comment: Se ajusta el tamaño del campo cecTipoProcesoMasivo
ALTER TABLE ConsolaEstadoCargueMasivo ALTER COLUMN cecTipoProcesoMasivo varchar(40);

--changeset clmarin:03
--comment: Se ajusta el tamaño del campo vcoTipoVariableComunicado 
ALTER TABLE VariableComunicado ALTER COLUMN vcoTipoVariableComunicado varchar(20) NULL;

--changeset clmarin:04
--comment: Se actualizan el campo vcoTipoVariableComunicado de acuerdo al campo vcoClave
UPDATE VariableComunicado SET vcoTipoVariableComunicado = 'USUARIO_VARIABLE' where vcoClave = '${responsableAfiliacionCcf}';
UPDATE VariableComunicado SET vcoTipoVariableComunicado = 'USUARIO_CONSTANTE' where vcoClave in ('${responsableCcf}','${cargoResponsableCcf}');

--changeset jzambrano:05
--comment: Cambio del tipo de dato del campo cecFechaInicio y cecFechaFin
ALTER TABLE ConsolaEstadoCargueMasivo ALTER COLUMN cecFechaInicio datetime2;
ALTER TABLE ConsolaEstadoCargueMasivo ALTER COLUMN cecFechaFin datetime2;

--changeset ogiral:06
--comment: Se actualizan el campo cnsValor
UPDATE Constante set cnsValor = 'libardo_ramirez' where cnsNombre = 'RESPONSABLE_CCF' 

