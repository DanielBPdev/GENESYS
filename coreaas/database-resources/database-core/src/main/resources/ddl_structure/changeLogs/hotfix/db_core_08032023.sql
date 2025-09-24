if not exists (select * from ParametrizacionEjecucionProgramada where pepProceso = 'CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA_II')
	begin
INSERT [ParametrizacionEjecucionProgramada] ([pepProceso], [pepHoras], [pepMinutos], [pepSegundos], [pepDiaSemana], [pepDiaMes], [pepMes], [pepAnio], [pepFechaInicio], [pepFechaFin], [pepFrecuencia], [pepEstado]) VALUES (N'CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA_II', N'00', N'00', N'00', NULL, NULL, NULL, NULL, NULL, NULL, N'DIARIO', N'ACTIVO')	
end

if not exists (select * from parametrizaciongaps where prgNombre = 'CONSULTA_REGISTRADURIA')
	begin
INSERT [parametrizaciongaps] ([prgProceso], [prgNombre], [prgDescripcion], [prgUsuarui], [prgFechaModificacion], [prgVersionLiberacion],[prgTipoDatos], [prgEstado]) VALUES (N'Afiliación personas', N'CONSULTA_REGISTRADURIA', N'El parametro activara o no la consulta a registraduria para la afiliación de personas', N'Gabriel Ortega', NULL, NULL, N'Booleano', N'INACTIVO')	
end