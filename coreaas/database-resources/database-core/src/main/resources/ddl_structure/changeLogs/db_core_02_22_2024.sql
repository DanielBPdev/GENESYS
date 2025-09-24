declare @ccfId int;
set @ccfId = (select ccfId from CajaCompensacion where ccfID in (select cnsValor from COnstante where cnsNombre = 'CAJA_COMPENSACION_ID'));
if not exists (select 1 from RequisitoCajaClasificacion rts
join CajaCompensacion cc on cc.ccfId = rts.rtsCajaCompensacion
where rts.rtsRequisito = 91
and rts.rtsClasificacion = 'TRABAJADOR_DEPENDIENTE'
and rts.rtsTipoTransaccion = 'AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION'
and cc.ccfId = @ccfId
) insert into RequisitoCajaClasificacion(rtsEstado, rtsRequisito, rtsClasificacion, rtsTipoTransaccion, rtsCajaCompensacion, rtsTextoAyuda,rtsTipoRequisito)
values ('OPCIONAL', 91, 'TRABAJADOR_DEPENDIENTE', 'AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION', @ccfId,
'Se revisa:<br />-Formulario completamente diligenciado sin enmendaduras ni tachones<br />-Firma de representante legal o funcionario designado (del empleador) y sello.<br />-Firma del trabajador',
'COMPORTAMIENTO_TIPO_A');