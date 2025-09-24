if not exists (select * from parametrizaciongaps where prgNombre = 'APORTES_MASIVOS')
	begin
INSERT [parametrizaciongaps] ([prgProceso], [prgNombre], [prgDescripcion], [prgUsuarui], [prgFechaModificacion], [prgVersionLiberacion],[prgTipoDatos], [prgEstado],[GLPI]) VALUES (N'APORTES MASIVOS', N'APORTES_MASIVOS', N'Habilita la vista de aportes masivos', N'Juan Quintero', NULL, NULL, N'Booleano', N'ACTIVO','51960')	
end