--liquibase formatted sql

--changeset jocorrea:01
--comment:
ALTER TABLE CalificacionPostulacion ADD cafValorParte7  NUMERIC(12,6) NULL;
ALTER TABLE CalificacionPostulacion ADD cafValorParte8  NUMERIC(12,6) NULL;

ALTER TABLE aud.CalificacionPostulacion_aud ADD cafValorParte7  NUMERIC(12,6) NULL;
ALTER TABLE aud.CalificacionPostulacion_aud ADD cafValorParte8  NUMERIC(12,6) NULL;

--changeset jocorrea:02
--comment:
ALTER TABLE IntegranteHogar ADD inhPostulacionFovis BIGINT 
ALTER TABLE aud.IntegranteHogar_aud ADD inhPostulacionFovis BIGINT

--changeset jocorrea:03
--comment:
ALTER TABLE IntegranteHogar ADD CONSTRAINT FK_IntegranteHogar_inhPostulacionFovis FOREIGN KEY(inhPostulacionFovis) REFERENCES PostulacionFOVIS (pofId);

--changeset jocorrea:04
--comment:
UPDATE inh
SET inh.inhPostulacionFovis = pof.pofId
FROM IntegranteHogar inh
JOIN (SELECT MAX(pof.pofId) pofId, pof.pofJefeHogar FROM PostulacionFOVIS pof GROUP BY pof.pofJefeHogar ) pof ON (pof.pofJefeHogar = inh.inhJefeHogar)
