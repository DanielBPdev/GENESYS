--liquibase formatted sql

--changeset clmarin:01
--comment: Actualizacion de registros en la tabla VariableComunicado con respecto a las Variables
--NTF_PAG_DVL_APT
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoSolicitante}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoSolicitante}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccion}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRegistroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRegistroSolicitud}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${resultadoFinal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${resultadoFinal}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${reporte}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${reporte}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${totalDescuentos}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${totalDescuentos}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${descuentosOperadorInformacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${descuentosOperadorInformacion}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${descuentosFinanciera}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${descuentosFinanciera}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${otros}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${otros}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${totalDevolucion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${totalDevolucion}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${datosPago}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${datosPago}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${titularCuenta}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${titularCuenta}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroCuentaBancaria}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroCuentaBancaria}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoCuentaBancaria}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoCuentaBancaria}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreBanco}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreBanco}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';

--changeset clmarin:02
--comment: Actualizacion de registros en la tabla VariableComunicado con respecto a las Constantes
--NTF_PAG_DVL_APT
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';

--changeset jocorrea:03
--comment: Se modifica el tamaño del campo hrvValidacion para la tabla HistoriaResultadoValidacion
ALTER TABLE HistoriaResultadoValidacion ALTER COLUMN hrvValidacion varchar(100);

--changeset fvasquez:04
--comment: Se modifica campos de la tabla MovimientoAporte
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'FK_MovimientoAporte_moaAporteDetallado')) ALTER TABLE MovimientoAporte DROP CONSTRAINT FK_MovimientoAporte_moaAporteDetallado;
ALTER TABLE MovimientoAporte ALTER COLUMN moaAporteDetallado bigint NULL;
ALTER TABLE MovimientoAporte WITH CHECK ADD CONSTRAINT FK_MovimientoAporte_moaAporteDetallado FOREIGN KEY (moaAporteDetallado) REFERENCES AporteDetallado (apdId);
ALTER TABLE MovimientoAporte ADD moaAporteGeneral bigint NULL;
UPDATE MovimientoAporte SET MovimientoAporte.moaAporteGeneral = AporteDetallado.apdAporteGeneral FROM MovimientoAporte INNER JOIN AporteDetallado ON AporteDetallado.apdId=MovimientoAporte.moaAporteDetallado;
ALTER TABLE MovimientoAporte ALTER COLUMN moaAporteGeneral bigint NOT NULL;
ALTER TABLE MovimientoAporte WITH CHECK ADD CONSTRAINT FK_MovimientoAporte_moaAporteGeneral FOREIGN KEY (moaAporteGeneral) REFERENCES AporteGeneral (apgId);