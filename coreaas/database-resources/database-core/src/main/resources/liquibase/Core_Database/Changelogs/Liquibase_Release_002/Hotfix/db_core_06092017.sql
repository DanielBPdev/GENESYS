--liquibase formatted sql

--changeset arocha:01
--comment: Se eliminan las tablas pila.Aportante, pila.Cotizante, pila.AporteEmpresa y se crea la tabla pila.Aporte
DROP TABLE pila.Aportante;
DROP TABLE pila.Cotizante;
DROP TABLE pila.AporteEmpresa;

CREATE TABLE pila.Aporte(
       appPeriodoAporte DATE NOT NULL,
       appPeriodoAporteYYYYMM Varchar(7) NOT NULL,
       appTipoIdentificacionAportante varchar(20) NOT NULL,
       appNumeroIdentificacionAportante varchar(16) NOT NULL,
       appTipoIdentificacionCotizante varchar(20) NOT NULL,
       appNumeroIdentificacionCotizante varchar(16) NOT NULL,
       appTipoPlanilla varchar(15) NOT NULL,
       appTipoCotizante varchar(30) NOT NULL,
       appTransaccion bigint NOT NULL
);