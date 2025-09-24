-- =============================================
-- Author:		Francisco Alejandro Hoyos Rojas
-- Create date: 2021/02/26
-- Description:	Procedimiento almacenado encargado de 
-- obtener la lista de subsidos a anular
-- por prescripción o por venimiento HU-223 y HU-224
-- =============================================
CREATE PROCEDURE [dbo].[USP_SM_GET_ListadoAbonosPorVencimientoYPrescripcion]
	@fechaActual DATE,            	--- Desde el servicio que se invoca el SP se envia la fecha actual del sistema
	@dias INT,						--- Dias parametrizados por la CCF para anular un subsidio monetario
	@listaMediosDePago VARCHAR(54), --- Medios de pagos por los cuales se realizara la anulación
	@offset  INT,					--- Indica la posición desde donde se empezarán a retornar registros
	@orderBy VARCHAR(100),          --- Indica el ordenamiento de la consulta base
	@limit INT,                     --- Indica cuantos registros se deben traer
	@primeraPeticion BIT,           --- Indica si es la primera vez que se genera el listado
	@consultaTotal BIT,				--- Indica si se va a calcular el valor total del listado de subsidios a anular
	@numeroIdentificacionAdminSub  VARCHAR(20),				--- Indica si se va a calcular el valor total del listado de subsidios a anular
	@totalRegistros BIGINT OUT      --- Total de registros 
AS
 	SET NOCOUNT ON;
 
BEGIN
	--- Se retorna el valor total de
	IF @consultaTotal = 0
	BEGIN
		IF @primeraPeticion = 1
		BEGIN
			--- Se asigna el valor a totalRegistros. Esta consulta solo se realiza la primera vez que se llama el SP para retornar el número de registros ya que es paginada.
			SELECT
				@totalRegistros = COUNT(*)
			FROM CuentaAdministradorSubsidio as cas
			INNER JOIN DetalleSubsidioAsignado as dsa on cas.casId = dsa.dsaCuentaAdministradorSubsidio  
			INNER JOIN AdministradorSubsidio on casAdministradorSubsidio = asuId
			INNER JOIN PERSONA ON perid = asuPersona
			WHERE (SELECT DATEDIFF(DAY, cas.casFechaHoraTransaccion, @fechaActual)) > @dias
				AND cas.casTipoTransaccionSubsidio = 'ABONO'
				AND cas.casEstadoTransaccionSubsidio = 'APLICADO'
				AND  cas.casMedioDePagoTransaccion IN (SELECT value FROM STRING_SPLIT(@listaMediosDePago,','))
				AND (perNumeroIdentificacion = @numeroIdentificacionAdminSub or @numeroIdentificacionAdminSub = 'null');
		END
		IF  @orderBy = ''
		BEGIN
			SET @orderBy = '1';
		END

		SELECT * FROM (

						SELECT 
						dsa.dsaPeriodoLiquidado as periodoLiquidado,dsa.dsaSolicitudLiquidacionSubsidio as idLiquidacionAsociada, sls.slsFechaInicio as fechaLiquidacionAsociada, 
						dsa.dsaTipoliquidacionSubsidio as tipoLiquidacion, emplPer.perTipoIdentificacion as tipoIdEmpl, emplPer.perNumeroIdentificacion as numIdEmpl, 
						emplPer.perRazonSocial, emplPer.perPrimerNombre as priNombreEmpl, emplPer.perSegundoNombre as segNombreEmpl, emplPer.perPrimerApellido as priApellidoEmpl, 
						emplPer.perSegundoApellido as segApellidoEmpl,perAfi.perTipoIdentificacion as tipoIdAfi, perAfi.perNumeroIdentificacion as numIdAfi,
						perAfi.perPrimerNombre as priNombreAfi, perAfi.perSegundoNombre as segNombreAfi,perAfi.perPrimerApellido as priApellidoAfi, perAfi.perSegundoApellido  as segApellidoAfi,
						grf.grfNumero as codigoGrupoFamiliar, ben.benTipoBeneficiario as parentescoBeneficiario,perBen.perTipoIdentificacion as tipoIdBen, perBen.perNumeroIdentificacion as numIdBen,
						perBen.perPrimerNombre as priNombreBen, perBen.perSegundoNombre as segNombreBen, perBen.perPrimerApellido as priApellidoBen, perBen.perSegundoApellido as segApellidoBen,
						dsa.dsaTipoCuotaSubsidio as tipoCuota,perAdm.perTipoIdentificacion as tipoIdAdm, perAdm.perNumeroIdentificacion as numIdAdm,perAdm.perPrimerNombre as priNombreAdm, 
						perAdm.perSegundoNombre as segNombreAdm, perAdm.perPrimerApellido as priApellidoAdm, perAdm.perSegundoApellido as segApellidoAdm, sop.sipCodigo,
						sop.sipNombre as nombreSitioDePago,dsa.dsaValorTotal as valorTotal,mun.munCodigo,mun.munNombre,dep.depCodigo,dep.depNombre, dsa.dsaCuentaAdministradorSubsidio, dsa.dsaId,
						cas.casMedioDePagoTransaccion as medioDePago
					FROM CuentaAdministradorSubsidio as cas
					INNER JOIN DetalleSubsidioAsignado as dsa on cas.casId = dsa.dsaCuentaAdministradorSubsidio 
					INNER JOIN SolicitudLiquidacionSubsidio as sls on sls.slsId = dsa.dsaSolicitudLiquidacionSubsidio
					INNER JOIN Empleador as empl on empl.empId = dsa.dsaEmpleador
					INNER JOIN Empresa as emp on emp.empId = empl.empEmpresa
					INNER JOIN Persona as emplPer on emplper.perId = emp.empPersona
					INNER JOIN Afiliado as afi on afi.afiId = dsa.dsaAfiliadoPrincipal
					INNER JOIN Persona as perAfi on perAfi.perId = afi.afiPersona
					INNER JOIN Beneficiario ben ON ben.benBeneficiarioDetalle = dsa.dsaBeneficiarioDetalle	
					INNER JOIN Persona perBen ON perBen.perId = ben.benPersona		
					INNER JOIN AdministradorSubsidio as adm on adm.asuId = cas.casAdministradorSubsidio
					INNER JOIN Persona as perAdm on perAdm.perId = adm.asuPersona 
					LEFT JOIN SitioPago as sop on sop.sipId = cas.casSitioDePago
					LEFT JOIN Infraestructura as inf on inf.infId = sop.sipInfraestructura
					LEFT JOIN Municipio as mun on mun.munId = inf.infMunicipio
					LEFT JOIN Departamento as dep on dep.depId = mun.munDepartamento
					INNER JOIN GrupoFamiliar as grf on grf.grfAfiliado = dsa.dsaAfiliadoPrincipal  
					WHERE (SELECT DATEDIFF(DAY, cas.casFechaHoraTransaccion, @fechaActual)) > @dias
					AND cas.casTipoTransaccionSubsidio = 'ABONO'
					AND cas.casEstadoTransaccionSubsidio = 'APLICADO'
					AND  cas.casMedioDePagoTransaccion IN (SELECT value FROM STRING_SPLIT(@listaMediosDePago,','))
					AND ben.benGrupoFamiliar = grf.grfId
					AND (perAdm.perNumeroIdentificacion = @numeroIdentificacionAdminSub or @numeroIdentificacionAdminSub = 'null')
					) AS registros 
					ORDER BY convert(smallint,@orderBy) OFFSET @offset ROWS FETCH NEXT @limit ROWS ONLY

	END
	ELSE
	BEGIN
		SELECT 
			SUM(dsa.dsaValorTotal) AS valorTotal
		FROM CuentaAdministradorSubsidio as cas 
		INNER JOIN DetalleSubsidioAsignado as dsa on cas.casId = dsa.dsaCuentaAdministradorSubsidio  
		WHERE  (SELECT DATEDIFF(DAY, cas.casFechaHoraTransaccion, @fechaActual)) > @dias
			AND  cas.casTipoTransaccionSubsidio = 'ABONO'
			AND  cas.casEstadoTransaccionSubsidio = 'APLICADO' 
			AND  cas.casMedioDePagoTransaccion IN (SELECT value FROM STRING_SPLIT(@listaMediosDePago,','));
	END

	IF isnull(@totalRegistros,0) < 1
		BEGIN
			set @totalRegistros = 0
		END

END;