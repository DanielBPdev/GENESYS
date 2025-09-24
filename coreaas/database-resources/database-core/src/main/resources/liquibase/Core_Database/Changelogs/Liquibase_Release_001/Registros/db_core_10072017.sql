--liquibase formatted sql

--changeset mosanchez:01
--comment: Se hace la insercion en las tablas beneficio y periodobeneficio
INSERT dbo.Beneficio (befTipoBeneficio,befVigenciaFiscal,befFechaVigenciaInicio,befFechaVigenciaFin) VALUES ('LEY_1429',1,'2010-12-29',NULL);
INSERT dbo.Beneficio (befTipoBeneficio,befVigenciaFiscal,befFechaVigenciaInicio,befFechaVigenciaFin) VALUES ('LEY_590',0,'2000-07-12',NULL);

INSERT dbo.PeriodoBeneficio (pbePeriodo,pbePorcentaje,pbeBeneficio) VALUES (1,0.00,(SELECT befId FROM Beneficio WHERE befTipoBeneficio = 'LEY_1429'));
INSERT dbo.PeriodoBeneficio (pbePeriodo,pbePorcentaje,pbeBeneficio) VALUES (2,0.00,(SELECT befId FROM Beneficio WHERE befTipoBeneficio = 'LEY_1429'));
INSERT dbo.PeriodoBeneficio (pbePeriodo,pbePorcentaje,pbeBeneficio) VALUES (3,0.25,(SELECT befId FROM Beneficio WHERE befTipoBeneficio = 'LEY_1429'));
INSERT dbo.PeriodoBeneficio (pbePeriodo,pbePorcentaje,pbeBeneficio) VALUES (4,0.50,(SELECT befId FROM Beneficio WHERE befTipoBeneficio = 'LEY_1429'));
INSERT dbo.PeriodoBeneficio (pbePeriodo,pbePorcentaje,pbeBeneficio) VALUES (5,0.75,(SELECT befId FROM Beneficio WHERE befTipoBeneficio = 'LEY_1429'));
INSERT dbo.PeriodoBeneficio (pbePeriodo,pbePorcentaje,pbeBeneficio) VALUES (1,0.25,(SELECT befId FROM Beneficio WHERE befTipoBeneficio = 'LEY_590'));
INSERT dbo.PeriodoBeneficio (pbePeriodo,pbePorcentaje,pbeBeneficio) VALUES (2,0.50,(SELECT befId FROM Beneficio WHERE befTipoBeneficio = 'LEY_590'));
INSERT dbo.PeriodoBeneficio (pbePeriodo,pbePorcentaje,pbeBeneficio) VALUES (3,0.75,(SELECT befId FROM Beneficio WHERE befTipoBeneficio = 'LEY_590'));
