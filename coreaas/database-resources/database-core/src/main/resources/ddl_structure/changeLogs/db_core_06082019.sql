--liquibase formatted sql

--changeset mamonroy:01
--comment:
UPDATE RolAfiliado
SET roaEmpleador = 4155
WHERE roaId = 10039
AND roaEmpleador IS NULL
AND roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
AND EXISTS (SELECT 1 FROM Constante WHERE cnsNombre = 'CAJA_COMPENSACION_ID' AND cnsValor = '14')
AND EXISTS (SELECT 1 FROM Empleador WHERE empId = 4155)