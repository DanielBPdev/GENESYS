
IF NOT EXISTS (SELECT 1 FROM sys.columns  WHERE Name = N'grfTarjetaMultiservicio' AND Object_ID = Object_ID(N'dbo.GrupoFamiliar')) ALTER TABLE GrupoFamiliar ADD  grfTarjetaMultiservicio bit;
IF NOT EXISTS (SELECT 1 FROM sys.columns  WHERE Name = N'grfTarjetaMultiservicio' AND Object_ID = Object_ID(N'aud.GrupoFamiliar_aud')) ALTER TABLE aud.GrupoFamiliar_aud ADD  grfTarjetaMultiservicio bit;
IF NOT EXISTS (SELECT 1 FROM sys.columns  WHERE Name = N'mppTarjetaMultiservicio' AND Object_ID = Object_ID(N'dbo.MedioPagoPersona')) ALTER TABLE MedioPagoPersona ADD  mppTarjetaMultiservicio bit;
IF NOT EXISTS (SELECT 1 FROM sys.columns  WHERE Name = N'mppTarjetaMultiservicio' AND Object_ID = Object_ID(N'aud.MedioPagoPersona_aud')) ALTER TABLE aud.MedioPagoPersona_aud ADD  mppTarjetaMultiservicio bit;