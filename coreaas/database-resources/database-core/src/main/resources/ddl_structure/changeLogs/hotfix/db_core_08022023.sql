

if not exists (select * from Parametro where prmNombre = 'EDAD_CAMBIO_CATEGORIA_BENEFICIARIO_PADRE')
	begin
insert into Parametro(prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato) values ('EDAD_CAMBIO_CATEGORIA_BENEFICIARIO_PADRE', '60', 0, 'CAJA_COMPENSACION', 'Edad de beneficiario para el cambio de categoria para padres. Debe ser menor a 60', 'NUMBER');
	end
if not exists (select * from Parametro where prmNombre = 'EDAD_CAMBIO_CATEGORIA_BENEFICIARIO_HIJOS_CERTIFICADO_ESCOLAR')
	begin
insert into Parametro(prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion, prmTipoDato) values ('EDAD_CAMBIO_CATEGORIA_BENEFICIARIO_HIJOS_CERTIFICADO_ESCOLAR', '18,23', 0, 'CAJA_COMPENSACION', 'Edad de beneficiario para el cambio de categoria para hijos con certificado escolar. Debe ser mayor a 18 y menor a 23', 'NUMBER');
	end

if not exists (select * from ParametrizacionEjecucionProgramada where pepProceso = 'CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA')
	begin
INSERT [ParametrizacionEjecucionProgramada] ([pepProceso], [pepHoras], [pepMinutos], [pepSegundos], [pepDiaSemana], [pepDiaMes], [pepMes], [pepAnio], [pepFechaInicio], [pepFechaFin], [pepFrecuencia], [pepEstado]) VALUES (N'CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA', N'00', N'00', N'00', NULL, NULL, NULL, NULL, NULL, NULL, N'DIARIO', N'ACTIVO')	
end
if not exists (select * from ParametrizacionNovedad where novTipoTransaccion = 'CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA')
	begin
INSERT [ParametrizacionNovedad] ( [novTipoTransaccion], [novPuntoResolucion], [novRutaCualificada], [novTipoNovedad], [novProceso], [novAplicaTodosRoles]) VALUES (N'CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA', N'SISTEMA_AUTOMATICO', N'com.asopagos.novedades.convertidores.persona.ActualizarCategoriaBeneficiarioCircularUnica', N'AUTOMATICA', N'NOVEDADES_PERSONAS_PRESENCIAL', NULL)
end

ALTER TABLE Categoria ALTER COLUMN catMotivoCambioCategoria VARCHAR(60)
ALTER TABLE aud.categoria_aud ALTER COLUMN catMotivoCambioCategoria VARCHAR(60)

