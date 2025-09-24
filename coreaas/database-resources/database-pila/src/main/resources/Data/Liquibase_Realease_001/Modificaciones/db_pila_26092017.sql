--liquibase formatted sql

--changeset arocha:01
--comment: Se actualiza registros en la tabla TemAporte y se adiciona campo en la tabla TemNovedad
UPDATE TemAporte SET temModalidadRecaudoAporte = CASE WHEN temMarcaAporteSimulado = 1 THEN 'PILA_MANUAL' ELSE (CASE WHEN temMarcaAporteManual = 1 THEN 'MANUAL' ELSE 'PILA' END) END;
ALTER TABLE TemNovedad ADD tenModalidadRecaudoAporte VARCHAR(40);
UPDATE TemNovedad SET tenModalidadRecaudoAporte = CASE WHEN tenMarcaNovedadSimulado = 1 THEN 'PILA_MANUAL' ELSE (CASE WHEN tenMarcaNovedadManual = 1 THEN 'MANUAL' ELSE 'PILA' END) END;
ALTER TABLE TemNovedad ALTER COLUMN tenModalidadRecaudoAporte VARCHAR(40) NOT NULL;
