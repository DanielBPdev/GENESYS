--liquibase formatted sql

--changeset anbuitrago:01 
--comment:Se elimina las columnas picIndicePlanillaAfectada, picIndicePlanillaCargado y picFechaAccion de la tabla PilaIndiceCorreccionPlanilla_aud
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla_aud DROP COLUMN picIndicePlanillaAfectada;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla_aud DROP COLUMN picIndicePlanillaCargado;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla_aud DROP COLUMN picFechaAccion;

--Cambios para HU-392 y HU-411 gestion de cambio de numero de identificacion del Aportante_aud
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla_aud ADD picFechaSolicitud date NOT NULL;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla_aud ADD picUsuario varchar(25) NOT NULL;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla_aud ADD picNumeroIdentificacion bigint NOT NULL;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla_aud ADD picArchivosCorreccion varchar(225);
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla_aud ADD picUsuarioAprobador varchar(25);
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla_aud ADD picFechaRespuesta date;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla_aud ADD picRazonRechazo varchar(25);
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla_aud ADD picIdPlanillaInformacion bigint NOT NULL;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla_aud ADD picIdPlanillaFinanciera bigint NOT NULL;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla_aud ADD picPilaIndicePlanilla bigint NOT NULL;

--changeset abaquero:02 
--comment: Se agrega campo pipTamanoArchivo para la tabla PilaIndicePlanilla_aud y se agrega el campo pioTamanoArchivo para el camp PilaIndicePlanillaOF_aud
ALTER TABLE dbo.PilaIndicePlanilla_aud ADD pipTamanoArchivo bigint;
ALTER TABLE dbo.PilaIndicePlanillaOF_aud ADD pioTamanoArchivo bigint;

--Se elimina el campo pevIdIndicePlanilla de la tabla PilaErrorValidacionLog_aud
ALTER TABLE dbo.PilaErrorValidacionLog_aud DROP COLUMN pevIdIndicePlanilla;

--Se agrega el campo pevEstadoInconsistencia, pevIndicePlanilla y pevIndicePlanillaOF de la tabla PilaErrorValidacionLog_aud
ALTER TABLE dbo.PilaErrorValidacionLog_aud ADD pevEstadoInconsistencia varchar(30);
ALTER TABLE dbo.PilaErrorValidacionLog_aud ADD pevIndicePlanilla bigint;
ALTER TABLE dbo.PilaErrorValidacionLog_aud ADD pevIndicePlanillaOF bigint;

--Se agrega marca que identifica que el estado de validación PILA vs BD corresponde a un análisis integral (HU-211-480)
ALTER TABLE PilaArchivoIRegistro2_aud ADD pi2AnalisisIntegral bit;

--Se eliminan los campos pebIdLogLecturaBloque2 y pebIdLogLecturaBloque4 de la tabla PilaEstadoBloque_aud
ALTER TABLE dbo.PilaEstadoBloque_aud DROP COLUMN pebIdLogLecturaBloque2;
ALTER TABLE dbo.PilaEstadoBloque_aud DROP COLUMN pebIdLogLecturaBloque4;

--Se agregan campos de estadoBloque y accionBloque para las 3 fases de PILA
ALTER TABLE dbo.PilaEstadoBloque_aud ADD pebEstadoBloque7 varchar(75);
ALTER TABLE dbo.PilaEstadoBloque_aud ADD pebAccionBloque7 varchar(75);
ALTER TABLE dbo.PilaEstadoBloque_aud ADD pebEstadoBloque8 varchar(75);
ALTER TABLE dbo.PilaEstadoBloque_aud ADD pebAccionBloque8 varchar(75);
ALTER TABLE dbo.PilaEstadoBloque_aud ADD pebEstadoBloque9 varchar(75);
ALTER TABLE dbo.PilaEstadoBloque_aud ADD pebAccionBloque9 varchar(75);

--changeset hhernandez:03
--comment: Se adicionan los campos apgApoConDetalle, apgFormaReconocimientoAporte y apgNumeroCuenta de la tabla AporteGeneral
ALTER TABLE dbo.AporteGeneral_aud ADD apgApoConDetalle bit;
ALTER TABLE dbo.AporteGeneral_aud ADD apgFormaReconocimientoAporte varchar(75);
ALTER TABLE dbo.AporteGeneral_aud ADD apgNumeroCuenta varchar(17);

--Se adicionan los campos agsApoConDetalle, agsFormaReconocimientoAporte y agsNumeroCuenta de la tabla AporteGeneralSimulado
ALTER TABLE dbo.AporteGeneralSimulado_aud ADD agsApoConDetalle bit;
ALTER TABLE dbo.AporteGeneralSimulado_aud ADD agsFormaReconocimientoAporte varchar(75);
ALTER TABLE dbo.AporteGeneralSimulado_aud ADD agsNumeroCuenta varchar(17);

--Se crea nuevamente el campo apdValorSalarioIntegral para las tablas AporteDetallado y AporteDetalladoSimulado_aud para que tenga coherencia con la HU-211-397 y PILA
ALTER TABLE dbo.AporteDetallado_aud DROP COLUMN apdValorSalarioIntegral;
ALTER TABLE dbo.AporteDetallado_aud ADD apdSalarioIntegral bit;
ALTER TABLE dbo.AporteDetalladoSimulado_aud DROP COLUMN adsValorSalarioIntegral;
ALTER TABLE dbo.AporteDetalladoSimulado_aud ADD adsSalarioIntegral bit;

-- se elimina la referencia en forma de ubicación para el departamento y municipio del aporte detallado
-- la información incluida en PILA es muy limitada para generar una entrada de Ubicación

ALTER TABLE dbo.AporteDetallado_aud DROP COLUMN apdUbicacionLaboral;
ALTER TABLE dbo.AporteDetallado_aud ADD apdMunicipioLaboral smallint;
ALTER TABLE dbo.AporteDetallado_aud ADD apdDepartamentoLaboral smallint;

ALTER TABLE dbo.AporteDetalladoSimulado_aud DROP COLUMN adsUbicacionLaboral;
ALTER TABLE dbo.AporteDetalladoSimulado_aud ADD adsMunicipioLaboral smallint;
ALTER TABLE dbo.AporteDetalladoSimulado_aud ADD adsDepartamentoLaboral smallint;

--Se agrega el campo de referencia con una sucursal de Empresa_aud para los casos en los que aplica según PILA
ALTER TABLE dbo.Aportante_aud ADD apoSucursalEmpresa bigint;

--Se adiciona los campos apoEmpresa_aud y apoPagadorPorTerceros de la tabla Aportante_aud.
ALTER TABLE dbo.Aportante_aud ADD apoEmpresa bigint;
ALTER TABLE dbo.Aportante_aud ADD apoPagadorPorTerceros bit;

--Cambio de nombre de la columna apoEstadoAportante_audInicial a apoEstadoAportante_aud de la tabla Aportante_aud
EXEC sp_RENAME 'dbo.Aportante_aud.apoEstadoAportanteInicial' , 'apoEstadoAportante';

--Eliminación de la columnna apoEstadoAportante_audFinal, apoEmpleador y apoEntidadPagadora de la tabla Aportante_aud
ALTER TABLE dbo.Aportante_aud DROP COLUMN apoEstadoAportanteFinal;
ALTER TABLE dbo.Aportante_aud DROP COLUMN apoEmpleador;
ALTER TABLE dbo.Aportante_aud DROP COLUMN apoEntidadPagadora;

--Cambio de nombre de la columna cotEstadoCotizante_audInicial a cotEstadoCotizante_aud de la tabla Cotizante_aud
EXEC sp_RENAME 'dbo.Cotizante_aud.cotEstadoCotizanteInicial' , 'cotEstadoCotizante';

--Eliminación de la columnna cotEstadoCotizante_audFinal de la tabla Cotizante_aud
ALTER TABLE dbo.Cotizante_aud DROP COLUMN cotEstadoCotizanteFinal;
ALTER TABLE dbo.Cotizante_aud DROP COLUMN cotAfiliadoCotizante;

--Se agrega el campo de referencia con una sucursal de Empresa_aud para los casos en los que aplica según PILA
ALTER TABLE dbo.Cotizante_aud ADD cotPersona bigint;

--Se agrega campo perCreadoPorPila para la tabla Persona_aud
ALTER TABLE dbo.Persona_aud ADD perCreadoPorPila bit NULL;

--Se agrega campo perCreadoPorPila para la tabla Empresa_aud
ALTER TABLE dbo.Empresa_aud ADD empCreadoPorPila bit NULL;





