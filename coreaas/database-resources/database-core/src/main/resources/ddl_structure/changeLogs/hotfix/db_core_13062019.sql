--liquibase formatted sql

--changeset jocorrea:01
--comment:
DELETE FROM ValidacionProceso
WHERE vapBloque IN ('CAMBIO_DATOS_CORRESPONDENCIA_GRUPO_FAMILIAR_DEPWEB', 'CAMBIO_DATOS_CORRESPONDENCIA_GRUPO_FAMILIAR_PRESENCIAL', 'CAMBIO_DATOS_CORRESPONDENCIA_GRUPO_FAMILIAR_WEB')
AND vapValidacion = 'VALIDACION_DEPENDIENTE_NO_ADMIN_SUBSIDIO_GRUPO';

--changeset mamonroy:01
--comment:
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion,prmTipodato) VALUES ('CORREO_ADMINISTRADOR_ANIBOL','JARROYAVE@HEINSOHN.COM.CO',0,'VALOR_GLOBAL_TECNICO','Correo electronico del administrador ANIBOL','EMAIL');

--changeset mamonroy:02
--comment:
UPDATE PlantillaComunicado
SET pcoEncabezado = '<table style="width: 680px; height: 100px;">
<tbody>
<tr style="width: 100%;">
<th style="width: 25%; font-weight: normal;">${logoDeLaCcf}</th>
<th style="width: 50%; text-align: center;">SOLICITUD DE AFILIACI&Oacute;N DE ${tipoAfiliado}</th>
<th style="width: 25%; font-weight: normal;">
<p><strong>&nbsp;N&uacute;mero de solicitud</strong></p>
<p><strong>${numeroRadicacion}&nbsp;&nbsp;</strong></p>
<p><strong>&nbsp;</strong></p>
</th>
</tr>
</tbody>
</table>'
where pcoEtiqueta = 'ACRDS_TRMS_CDNES_PERS';