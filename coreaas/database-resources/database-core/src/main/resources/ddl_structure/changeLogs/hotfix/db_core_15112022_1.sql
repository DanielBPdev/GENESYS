
if not exists (select * from Parametro where prmNombre = 'CANT_SMLMV_MEJORAMIENTO_SFV')
	begin
	INSERT [Parametro] ( [prmNombre], [prmValor], [prmCargaInicio], [prmSubCategoriaParametro], [prmDescripcion], [prmTipoDato]) 
VALUES ( N'CANT_SMLMV_MEJORAMIENTO_SFV', N'11.5', 0, N'VALOR_GLOBAL_NEGOCIO',
 N'Cantidad de SMLMV del SFV cuando la modalidad sea mejoramiento de vivienda y su condicion especial sea HOGAR_OBJETO_REUBICACION_ZONA_ALTO_RIESGO y DAMNIFICADO_DESASTRE_NATURAL', 'NUMBER')
	end
else
	begin
		print 'el parametro CANT_SMLMV_MEJORAMIENTO_SFV ya existe'
	end

