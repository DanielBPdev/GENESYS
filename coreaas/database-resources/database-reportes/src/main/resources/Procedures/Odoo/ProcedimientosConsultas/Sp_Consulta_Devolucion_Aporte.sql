/****** Object:  StoredProcedure [odoo].[ConsultaDevolucionAporte]    Script Date: 26/06/2021 11:06:38 a. m. ******/

---7. Consulta Devolución de Aportes

--
--[Número de Solicitud de Devolución]
--[id del aporte]
--[tipo de aporte]
--[No. de identificación del aportante]
--[Estado de afiliación del aportante respecto a la CCF]
--[No. de identificación a quien se devuelve el aporte.]
--[Valor total del aporte]
--[Valor de aporte sin intereses]
--[Valor de interés del aporte]
--[Valor de devolución]
--[Saldo de aporte disponible]
--[Estado de aporte], 
--[Fecha de Recaudo PILA],
--[Fecha de Devolución],
--[Fecha contable],
--[Campo tipo plantilla]

CREATE PROCEDURE [odoo].[ConsultaDevolucionAporte](
	@fecha_procesamiento DATE = NULL
)

AS
BEGIN
	IF @fecha_procesamiento IS NULL
	BEGIN
		SET @fecha_procesamiento = CONVERT(DATE, DATEADD(DAY, -1, DATEADD(HH, -5, GETDATE())))
	END
	
	INSERT INTO [odoo].[devolucion_aporte] (numero_solicitud_devolucion, id_aporte, tipo_aporte, numero_identificacion_aportante, estado_afiliacion_aportante_respecto_CCF, num_identificacion_quien_devuelve_aporte,
		valor_total_aporte, valor_aporte_sin_intereses, valor_interes_aporte, valor_devolucion, saldo_aporte_disponible, estado_aporte, fecha_recaudo_PILA, fecha_devolucion, fecha_contable, campo_tipo_plantilla)
	
	SELECT  
		(select top 1 s.solNumeroRadicacion from Solicitud as s inner join SolicitudDevolucionAporte as sdax on s.solId = sdax.sdaSolicitudGlobal where sdax.sdaId = sda.sdaId) as [Número de Solicitud de Devolución],
		cast(apg.apgId as varchar (max)) as [id del aporte], --[No. Operación recaudo]
		apg.apgModalidadRecaudoAporte [tipo de aporte], 
		case 
			when apg.apgTipoSolicitante <> 'EMPLEADOR' 
				then (select p.perNumeroIdentificacion from AporteGeneral as apgx inner join Persona as p on apgx.apgPersona = p.perId where apgx.apgId = apg.apgId) 
			else (select P.perNumeroIdentificacion from Empresa as emp inner join Persona as p on emp.empPersona = p.perId 
				where emp.empId = apg.apgEmpresa) 
		end  [No. de identificación del Aportante],
		isnull(replace(apg.apgEstadoAportante,'_',' '),'')   [Estado de afiliación del aportante respecto a la CCF],
		null as [No. de identificación a quien se devuelve el aporte.],--No se encontró en movimientoaporte, aportedetallado y aportegeneral, devolucionaporte y devolucionaportedetalle
		apg.apgValTotalApoObligatorio+apg.apgValorIntMora as [Valor total del aporte],
		apg.apgValTotalApoObligatorio as [Valor de aporte sin intereses],
		apg.apgValorIntMora as [Valor de interés del aporte],
		dadDevolucionAporte [Valor de devolución],
		apdValorSaldoAporte[Saldo de aporte disponible],
		apg.apgestadoregistroaporteaportante as [Estado de aporte],
		apg.apgFechaRecaudo [Fecha de Recaudo PILA],
		ma.moaFechaCreacion [Fecha de Devolución],
		ma.moaFechaCreacion [Fecha contable],-- por validar
		case 
			when apg.apgestadoregistroaporteaportante='REGISTRADO' 
				THEN 'ACT_AP_DEV_REG'
			ELSE 'ACT_AP_DEV_REL'
		END [Campo tipo plantilla]

		FROM MovimientoAporte as ma
		inner join AporteGeneral as apg on ma.moaAporteGeneral = apg.apgId
		inner join AporteDetallado as apd on ma.moaAporteDetallado = apd.apdId
		left join DevolucionAporteDetalle as dad on dad.dadMovimientoAporte = ma.moaId
		left join DevolucionAporte as da on da.dapId = dad.dadDevolucionAporte
		left join SolicitudDevolucionAporte as sda on sda.sdaDevolucionAporte = da.dapId
		--inner join persona p on p.perid=sda.sdapersona
		left join Solicitud as s on s.solId = sda.sdaSolicitudGlobal
		where moaTipoAjuste = 'DEVOLUCION'
		and cast(apg.apgfechaprocesamiento as date)=@fecha_procesamiento--'2021-02-10'
END
