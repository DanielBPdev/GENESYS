-- =============================================
-- Author:		Oscar Gabriel Ocampo
-- Create date: 2020/10/02
-- Description:	SP para consultar las Cuentas de Subsidios
-- =============================================


CREATE PROCEDURE [dbo].[USP_PG_GET_ConsultaTransDetallesSubsidio]
(
	@medioDePago varchar(100),
	@tipoTransaccion varchar(40),
	@estadoTransaccion varchar(25),
	@fechaInicio date,
	@fechaFin date,
	@idsAminSubsidio varchar(MAX),
	@idsEmpleadores varchar(MAX),
	@idsAfiliados varchar(MAX),
	@idsBeneficiarios varchar(MAX),
	@idsGrupoFamiliar varchar(MAX),
	@offsetParametro int,
	@filtroFechas varchar(MAX)
)
AS



SET NOCOUNT ON;

	BEGIN 

	DECLARE @filtrarAdmin INT, @filtrarEmpleador INT, @filtrarAfiliado INT,@filtrarBeneficiario INT, @filtrarGrupo INT
	
	DECLARE @cantidadRegistros INT;
	SET @cantidadRegistros= 700000;
	
	DROP TABLE IF EXISTS #administradoresSubsidios;
	CREATE TABLE #administradoresSubsidios(idAdminSubsidio BIGINT);	
	CREATE CLUSTERED INDEX idxAdmin ON #administradoresSubsidios (idAdminSubsidio);

	DROP TABLE IF EXISTS #empleadores;
	CREATE TABLE #empleadores(idEmpleador BIGINT);	
	CREATE CLUSTERED INDEX idxEmp ON #empleadores (idEmpleador);

	DROP TABLE IF EXISTS #afiliados;
	CREATE TABLE #afiliados(idAfiliado BIGINT);	
	CREATE CLUSTERED INDEX idxAfi ON #afiliados (idAfiliado);

	DROP TABLE IF EXISTS #beneficiarios;
	CREATE TABLE #beneficiarios(idBeneficiario BIGINT);	
	CREATE CLUSTERED INDEX idxben ON #beneficiarios (idBeneficiario);

	DROP TABLE IF EXISTS #gruposFamiliares;
	CREATE TABLE #gruposFamiliares(idGrupoFamiliar BIGINT);	
	CREATE CLUSTERED INDEX idxgrf ON #gruposFamiliares (idGrupoFamiliar);

	DROP TABLE IF EXISTS #cuentas;
	CREATE TABLE #cuentas (dsaId BIGINT, casId BIGINT,casFechaHoraCreacionRegistro DATETIME,casUsuarioCreacionRegistro varchar(50),casNumeroTarjetaAdmonSubsidio VARCHAR(50),casCodigoBanco VARCHAR(8),casNombreBanco VARCHAR(255),
	casTipoCuentaAdmonSubsidio VARCHAR(30),casNumeroCuentaAdmonSubsidio VARCHAR(30),casTipoIdentificacionTitularCuentaAdmonSubsidio VARCHAR(20),
	casNumeroIdentificacionTitularCuentaAdmonSubsidio VARCHAR(20),casNombreTitularCuentaAdmonSubsidio VARCHAR(200), mdpTipo varchar(30), slsId BIGINT, fechaLiquidacionDetalle DATETIME,
	dsaCuentaAdministradorSubsidio BIGINT, dsaEmpleador BIGINT, dsaAfiliadoPrincipal BIGINT, dsaBeneficiarioDetalle BIGINT, benTipoId VARCHAR(50),benNumeroId VARCHAR(16), benRazonSocial VARCHAR(250), dsaAdministradorSubsidio BIGINT
	, dsaGrupoFamiliar BIGINT,
	casTipoTransaccionSubsidio VARCHAR(40),casEstadoTransaccionSubsidio VARCHAR(25),nombreSitioCobro VARCHAR(200),
	nombreSitioPago VARCHAR(255),
	nombreTerceroPagado VARCHAR(200),
	casIdRemisionDatosTerceroPagador VARCHAR(200),
	casIdTransaccionTerceroPagador VARCHAR(200),
	casValorOriginalTransaccion numeric,
	casValorRealTransaccion numeric,tipoIdAdminSubsidio VARCHAR(100),numIdAdminSubsidio  VARCHAR(100),adminSubsidio  VARCHAR(100), casFechaHoraUltimaModificacion DATETIME ,
            casUsuarioUltimaModificacion VARCHAR(100),casIdTransaccionOriginal BIGINT,casOrigenTransaccion VARCHAR(100) );
	

	DROP TABLE IF EXISTS #detalles
	CREATE TABLE #detalles (dsaId BIGINT, casFechaHoraCreacionRegistro DATETIME,casUsuarioCreacionRegistro varchar(50),perEmp BIGINT, perAfi BIGINT, perBen BIGINT, perAdmin BIGINT, grfNumero SMALLINT, casNumeroTarjetaAdmonSubsidio VARCHAR(50),casCodigoBanco VARCHAR(8),casNombreBanco VARCHAR(255),
	casTipoCuentaAdmonSubsidio VARCHAR(30),casNumeroCuentaAdmonSubsidio VARCHAR(30),casTipoIdentificacionTitularCuentaAdmonSubsidio VARCHAR(20),
	casNumeroIdentificacionTitularCuentaAdmonSubsidio VARCHAR(20),casNombreTitularCuentaAdmonSubsidio VARCHAR(200), mdpTipo varchar(30), slsId BIGINT, fechaLiquidacionDetalle DATETIME,perTipoIdentificacion VARCHAR(50),perNumeroIdentificacion VARCHAR(16),perRazonSocial VARCHAR(250),
	benTipoId VARCHAR(50),benNumeroId VARCHAR(16), benRazonSocial VARCHAR(250),
	casId BIGINT,casTipoTransaccionSubsidio VARCHAR(40),casEstadoTransaccionSubsidio VARCHAR(25),
	nombreSitioCobro VARCHAR(200),nombreSitioPago VARCHAR(255),
	nombreTerceroPagado VARCHAR(200),
	casIdRemisionDatosTerceroPagador VARCHAR(200),
	casIdTransaccionTerceroPagador VARCHAR(200),
	casValorOriginalTransaccion BIGINT,
	casValorRealTransaccion BIGINT,tipoIdAdminSubsidio VARCHAR(100),numIdAdminSubsidio  VARCHAR(100),adminSubsidio  VARCHAR(100),casFechaHoraUltimaModificacion DATETIME ,
            casUsuarioUltimaModificacion VARCHAR(100),casIdTransaccionOriginal BIGINT,casOrigenTransaccion VARCHAR(100));

	CREATE CLUSTERED INDEX idxdet ON #detalles (dsaId); 
	SET @filtrarAdmin = 0
	IF (LEN(@idsAminSubsidio) > 0)
	BEGIN
		SET @filtrarAdmin = 1
	 	WHILE LEN(@idsAminSubsidio) > 0
	    BEGIN
	        INSERT INTO #administradoresSubsidios(idAdminSubsidio)
	        SELECT left(@idsAminSubsidio, charindex(',', @idsAminSubsidio+',') -1) AS id;
	        SET @idsAminSubsidio = stuff(@idsAminSubsidio, 1, charindex(',', @idsAminSubsidio + ','), '');
	    END
	END 
	
	SET @filtrarEmpleador = 0
	IF (LEN(@idsEmpleadores) > 0)
	BEGIN
		SET @filtrarEmpleador = 1
	 	WHILE LEN(@idsEmpleadores) > 0
	    BEGIN
	        INSERT INTO #empleadores(idEmpleador)
	        SELECT left(@idsEmpleadores, charindex(',', @idsEmpleadores+',') -1) AS id;
	        SET @idsEmpleadores = stuff(@idsEmpleadores, 1, charindex(',', @idsEmpleadores + ','), '');
	    END
	END 
	
	SET @filtrarAfiliado = 0
	IF (LEN(@idsAfiliados) > 0)
	BEGIN
		SET @filtrarAfiliado = 1
	 	WHILE LEN(@idsAfiliados) > 0
	    BEGIN
	        INSERT INTO #afiliados(idAfiliado)
	        SELECT left(@idsAfiliados, charindex(',', @idsAfiliados+',') -1) AS id;
	        SET @idsAfiliados = stuff(@idsAfiliados, 1, charindex(',', @idsAfiliados + ','), '');
	    END
	END 
	
	SET @filtrarBeneficiario = 0
	IF (LEN(@idsBeneficiarios) > 0)
	BEGIN
		SET @filtrarBeneficiario = 1
	 	WHILE LEN(@idsBeneficiarios) > 0
	    BEGIN
	        INSERT INTO #beneficiarios(idBeneficiario)
	        SELECT left(@idsBeneficiarios, charindex(',', @idsBeneficiarios+',') -1) AS id;
	        SET @idsBeneficiarios = stuff(@idsBeneficiarios, 1, charindex(',', @idsBeneficiarios + ','), '');
	    END
	END 
	
	SET @filtrarGrupo = 0
	IF (LEN(@idsGrupoFamiliar) > 0)
	BEGIN
		SET @filtrarGrupo = 1
	 	WHILE LEN(@idsGrupoFamiliar) > 0
	    BEGIN
	        INSERT INTO #gruposFamiliares(idGrupoFamiliar)
	        SELECT left(@idsGrupoFamiliar, charindex(',', @idsGrupoFamiliar+',') -1) AS id;
	        SET @idsGrupoFamiliar = stuff(@idsGrupoFamiliar, 1, charindex(',', @idsGrupoFamiliar + ','), '');
	    END
	END 
	
	INSERT INTO #cuentas (dsaId,casId,casFechaHoraCreacionRegistro,casUsuarioCreacionRegistro,casNumeroTarjetaAdmonSubsidio,casCodigoBanco,casNombreBanco,
	casTipoCuentaAdmonSubsidio,casNumeroCuentaAdmonSubsidio,casTipoIdentificacionTitularCuentaAdmonSubsidio,
	casNumeroIdentificacionTitularCuentaAdmonSubsidio,casNombreTitularCuentaAdmonSubsidio, mdpTipo, slsId, fechaLiquidacionDetalle,
	dsaCuentaAdministradorSubsidio, dsaEmpleador, dsaAfiliadoPrincipal, dsaBeneficiarioDetalle, benTipoId, benNumeroId, benRazonSocial, dsaAdministradorSubsidio
	, dsaGrupoFamiliar,casTipoTransaccionSubsidio,casEstadoTransaccionSubsidio,nombreSitioCobro,nombreSitioPago,
	nombreTerceroPagado,casIdRemisionDatosTerceroPagador,casIdTransaccionTerceroPagador,casValorOriginalTransaccion,
	casValorRealTransaccion,tipoIdAdminSubsidio,numIdAdminSubsidio,adminSubsidio, casFechaHoraUltimaModificacion , casUsuarioUltimaModificacion,casIdTransaccionOriginal,casOrigenTransaccion)
	SELECT DISTINCT(dsa.dsaId), 
		cas.casId,
		 cas.casFechaHoraCreacionRegistro,
		 cas.casUsuarioCreacionRegistro,
		cas.casNumeroTarjetaAdmonSubsidio,
		cas.casCodigoBanco,
		cas.casNombreBanco,
		cas.casTipoCuentaAdmonSubsidio,
		cas.casNumeroCuentaAdmonSubsidio,
		cas.casTipoIdentificacionTitularCuentaAdmonSubsidio,
		cas.casNumeroIdentificacionTitularCuentaAdmonSubsidio,
		cas.casNombreTitularCuentaAdmonSubsidio,
		mdp.mdpTipo,
		sls.slsId,
		CASE
			WHEN sls.slsFechaEjecucionProgramada IS NOT NULL THEN sls.slsFechaEjecucionProgramada
			ELSE sls.slsFechaInicio
		END AS fechaLiquidacionDetalle,
		dsa.dsaCuentaAdministradorSubsidio, 
		dsa.dsaEmpleador, 
		dsa.dsaAfiliadoPrincipal, 
		dsa.dsaBeneficiarioDetalle,
		perBen.perTipoIdentificacion,
		perBen.perNumeroIdentificacion,
		perBen.perRazonSocial,
		COALESCE(dsa.dsaAdministradorSubsidio, cas.casAdministradorSubsidio),
		dsa.dsaGrupoFamiliar,
		cas.casTipoTransaccionSubsidio,
		cas.casEstadoTransaccionSubsidio,
		CASE
				WHEN cas.casSitioDeCobro IS NOT NULL
				THEN (
						SELECT CONCAT(Departamento.depNombre,'-',Municipio.munNombre)
						FROM SitioPago
						INNER JOIN Infraestructura ON SitioPago.sipInfraestructura = Infraestructura.infId
						INNER JOIN Municipio ON Municipio.munId = Infraestructura.infMunicipio
						INNER JOIN Departamento ON Departamento.depId = Municipio.munDepartamento
						WHERE SitioPago.sipId = cas.casSitioDeCobro
					 )
				ELSE NULL
			END AS nombreSitioCobro,
		CASE
				WHEN cas.casSitioDePago IS NOT NULL
				THEN (
						SELECT SitioPago.sipNombre
						FROM SitioPago
						WHERE SitioPago.sipId = cas.casSitioDePago
					 )
				ELSE NULL
		END AS nombreSitioPago,

		cas.casNombreTerceroPagado,
		cas.casIdRemisionDatosTerceroPagador,
		cas.casIdTransaccionTerceroPagador,
		cas.casValorOriginalTransaccion,
		cas.casValorRealTransaccion,
		   adminPer.perTipoIdentificacion AS tipoIdAdminSubsidio,
            adminPer.perNumeroIdentificacion AS numIdAdminSubsidio,
            adminPer.perRazonSocial AS adminSubsidio,
			 casFechaHoraUltimaModificacion ,
            casUsuarioUltimaModificacion,
			casIdTransaccionOriginal,
			cas.casOrigenTransaccion

	FROM CuentaAdministradorSubsidio cas
	LEFT JOIN DetalleSubsidioAsignado dsa  on cas.casid= dsa.dsaCuentaAdministradorSubsidio
	INNER JOIN MedioDePago mdp on mdp.mdpId = cas.casMedioDePago
	LEFT JOIN dbo.SolicitudLiquidacionSubsidio sls ON cas.casSolicitudLiquidacionSubsidio = sls.slsId
	LEFT JOIN dbo.Beneficiario ben ON dsa.dsaBeneficiarioDetalle = ben.benBeneficiarioDetalle
	LEFT JOIN dbo.Persona AS perBen ON perBen.perId = ben.benPersona

	 LEFT JOIN AdministradorSubsidio AS admin ON admin.asuId = cas.casAdministradorSubsidio
     LEFT JOIN Persona AS adminPer ON adminPer.perId = admin.asuPersona
	WHERE (@medioDePago = ''  OR mdp.mdpTipo = @medioDePago) AND
	(@tipoTransaccion = ''  OR cas.casTipoTransaccionSubsidio = @tipoTransaccion) AND
	(@estadoTransaccion = ''  OR cas.casEstadoTransaccionSubsidio = @estadoTransaccion)
	AND ( @filtroFechas = '' or (cast(cas.casFechaHoraTransaccion As Date) BETWEEN @fechaInicio AND @fechaFin))

	DECLARE @COUNTCTA BIGINT;
	SET @COUNTCTA = (SELECT COUNT(*) FROM #cuentas)
	PRINT @COUNTCTA; 



	INSERT INTO #detalles (dsaId,casFechaHoraCreacionRegistro,casUsuarioCreacionRegistro, perEmp, perAfi, perAdmin, grfNumero, casNumeroTarjetaAdmonSubsidio,casCodigoBanco,casNombreBanco,
	casTipoCuentaAdmonSubsidio,casNumeroCuentaAdmonSubsidio,casTipoIdentificacionTitularCuentaAdmonSubsidio,
	casNumeroIdentificacionTitularCuentaAdmonSubsidio,casNombreTitularCuentaAdmonSubsidio, mdpTipo, slsId, fechaLiquidacionDetalle,perTipoIdentificacion,perNumeroIdentificacion,perRazonSocial, benTipoId, benNumeroId
	, benRazonSocial,casId,casTipoTransaccionSubsidio,casEstadoTransaccionSubsidio,nombreSitioCobro,nombreSitioPago,
	nombreTerceroPagado,
	casIdRemisionDatosTerceroPagador,casIdTransaccionTerceroPagador,
	casValorOriginalTransaccion,
	casValorRealTransaccion,tipoIdAdminSubsidio,numIdAdminSubsidio,adminSubsidio, casFechaHoraUltimaModificacion , casUsuarioUltimaModificacion,casIdTransaccionOriginal,casOrigenTransaccion)
	SELECT cta.dsaId,
		 cta.casFechaHoraCreacionRegistro,
		 casUsuarioCreacionRegistro ,
		empresa.empPersona,
		afi.afiPersona,
		asu.asuPersona,
		grf.grfNumero,
 		cta.casNumeroTarjetaAdmonSubsidio,
		cta.casCodigoBanco,
		cta.casNombreBanco,
		cta.casTipoCuentaAdmonSubsidio,
		cta.casNumeroCuentaAdmonSubsidio,
		cta.casTipoIdentificacionTitularCuentaAdmonSubsidio,
		cta.casNumeroIdentificacionTitularCuentaAdmonSubsidio,
		cta.casNombreTitularCuentaAdmonSubsidio,
		cta.mdpTipo,
		cta.slsId,
		cta.fechaLiquidacionDetalle,
		perEmp.perTipoIdentificacion AS perEmpTipoId,
		perEmp.perNumeroIdentificacion AS perEmpNumeroId,
		perEmp.perRazonSocial AS empleador,
		cta.benTipoId,
		cta.benNumeroId,
		cta.benRazonSocial,
		cta.casId,
		cta.casTipoTransaccionSubsidio,
		cta.casEstadoTransaccionSubsidio,
		cta.nombreSitioCobro,
		cta.nombreSitioPago,
		cta.nombreTerceroPagado,
		cta.casIdRemisionDatosTerceroPagador,
		cta.casIdTransaccionTerceroPagador,
		cta.casValorOriginalTransaccion,
		cta.casValorRealTransaccion,
	    cta.tipoIdAdminSubsidio,
         cta.numIdAdminSubsidio,
         cta.adminSubsidio,
		  cta.casFechaHoraUltimaModificacion ,
          cta.casUsuarioUltimaModificacion,
		 cta.casIdTransaccionOriginal,
		 cta.casOrigenTransaccion

		 	FROM #cuentas cta
		 	left JOIN dbo.Empleador AS empleador ON empleador.empId = cta.dsaEmpleador
		 	left JOIN dbo.Empresa AS empresa ON empleador.empEmpresa = empresa.empId
			left JOIN dbo.Persona AS perEmp ON perEmp.perId = empresa.empPersona
		 	left JOIN dbo.Afiliado afi ON cta.dsaAfiliadoPrincipal = afi.afiId
		 	left JOIN dbo.AdministradorSubsidio asu ON cta.dsaAdministradorSubsidio = asu.asuId
		 	left JOIN dbo.GrupoFamiliar grf ON cta.dsaGrupoFamiliar = grf.grfId
			WHERE (@filtrarEmpleador = 0 OR cta.dsaEmpleador IN (SELECT idEmpleador FROM #empleadores))
		 	  AND (@filtrarAfiliado = 0 OR cta.dsaAfiliadoPrincipal IN (SELECT idAfiliado FROM #afiliados))
		 	  AND (@filtrarBeneficiario = 0 OR cta.dsaBeneficiarioDetalle IN (SELECT idBeneficiario FROM #beneficiarios))
		 	  AND (@filtrarAdmin = 0 OR cta.dsaAdministradorSubsidio IN (SELECT idAdminSubsidio FROM #administradoresSubsidios))
		 	  AND (@filtrarGrupo = 0 OR cta.dsaGrupoFamiliar IN (SELECT idGrupoFamiliar FROM #gruposFamiliares))
		 	  order by cta.dsaId
			  offset @offsetParametro rows fetch next @cantidadRegistros rows only;
	
	SET @COUNTCTA = (SELECT COUNT(*) FROM #detalles)
	PRINT @COUNTCTA; 	  


			SELECT det.dsaId,
		--dsa.dsaFechaHoraCreacion,
		det.casFechaHoraCreacionRegistro,
		casUsuarioCreacionRegistro,
		--dsa.dsaUsuarioCreador,
		dsa.dsaPeriodoLiquidado,
		dsa.dsaEstado,
		dsa.dsaMotivoAnulacion,
		det.perTipoIdentificacion AS perEmpTipoId,
		det.perNumeroIdentificacion AS perEmpNumeroId,
		det.perRazonSocial AS empleador,
		perAfi.perTipoIdentificacion AS perAfiTipoId,
		perAfi.perNumeroIdentificacion AS perAfiNumeroId,
		perAfi.perRazonSocial AS afiliado,
		det.grfNumero,
		det.benTipoId AS perBenTipoId,
		det.benNumeroId AS perBenNumeroId,
		det.benRazonSocial AS beneficiario, 
		--perAdmin.perTipoIdentificacion AS perAdminTipoId,  COMENTADO  Y REMPLAZADO POR GLPI 68248 PUNTO 2 VERSION ANTES 1.3.18
		--perAdmin.perNumeroIdentificacion AS perAdminNumeroId,  COMENTADO  Y REMPLAZADO POR GLPI 68248 PUNTO 2 VERSION ANTES 1.3.18
		--perAdmin.perRazonSocial AS adminSubsidio,   COMENTADO  Y REMPLAZADO POR GLPI 68248 PUNTO 2 VERSION ANTES 1.3.18
		 det.tipoIdAdminSubsidio AS perAdminTipoId,
         det.numIdAdminSubsidio AS perAdminNumeroId,
        det.adminSubsidio AS adminSubsidio, 
		det.mdpTipo,
		det.casNumeroTarjetaAdmonSubsidio,
		det.casCodigoBanco,
		det.casNombreBanco,
		det.casTipoCuentaAdmonSubsidio,
		det.casNumeroCuentaAdmonSubsidio,
		det.casTipoIdentificacionTitularCuentaAdmonSubsidio,
		det.casNumeroIdentificacionTitularCuentaAdmonSubsidio,
		det.casNombreTitularCuentaAdmonSubsidio,
		--dsa.dsaOrigenRegistroSubsidio,  COMENTADO  Y REMPLAZADO POR GLPI 68248 PUNTO 2 VERSION ANTES 1.3.18
		casOrigenTransaccion as dsaOrigenRegistroSubsidio,
 		det.slsId,
 		det.fechaLiquidacionDetalle,
		dsa.dsaTipoliquidacionSubsidio,
		dsa.dsaTipoCuotaSubsidio,
		dsa.dsaValorSubsidioMonetario,
		dsa.dsaValorDescuento,
		dsa.dsaValorOriginalAbonado,
		dsa.dsaValorTotal,   
		dsa.dsaFechaTransaccionRetiro,	
		dsa.dsaUsuarioTransaccionRetiro,
		dsa.dsaFechaTransaccionAnulacion,
		dsa.dsaUsuarioTransaccionAnulacion,
		--dsa.dsaFechaHoraUltimaModificacion, COMENTADO  Y REMPLAZADO POR GLPI 68248 PUNTO 2 VERSION ANTES 1.3.18
		--dsa.dsaUsuarioUltimaModificacion,  COMENTADO  Y REMPLAZADO POR GLPI 68248 PUNTO 2 VERSION ANTES 1.3.18
		 casFechaHoraUltimaModificacion ,
            casUsuarioUltimaModificacion,
		--dsa.dsaIdRegistroOriginalRelacionado, COMENTADO  Y REMPLAZADO POR GLPI 68248 PUNTO 2 VERSION ANTES 1.3.18
		 det.casIdTransaccionOriginal as dsaIdRegistroOriginalRelacionado,
		dsa.dsaCuentaAdministradorSubsidio,
		det.casId,
		det.casTipoTransaccionSubsidio,
		det.casEstadoTransaccionSubsidio,
		det.nombreSitioCobro,
		det.nombreSitioPago,
		det.nombreTerceroPagado,
		det.casIdRemisionDatosTerceroPagador,
		det.casIdTransaccionTerceroPagador,
		det.casValorOriginalTransaccion,
		det.casValorRealTransaccion
		  
		FROM #detalles det
		 	left JOIN DetalleSubsidioAsignado dsa ON  dsa.dsaId = det.dsaId
			left JOIN dbo.Persona AS perAfi ON perAfi.perId = det.perAfi
			left JOIN dbo.persona AS perAdmin ON perAdmin.perId = det.perAdmin
		
		
	DROP TABLE IF EXISTS #administradoresSubsidios
	DROP TABLE IF EXISTS #empleadores
	DROP TABLE IF EXISTS #afiliados
	DROP TABLE IF EXISTS #beneficiarios
	DROP TABLE IF EXISTS #gruposFamiliares
	DROP TABLE IF EXISTS #cuentas
	DROP TABLE IF EXISTS #detalles

	END
	;