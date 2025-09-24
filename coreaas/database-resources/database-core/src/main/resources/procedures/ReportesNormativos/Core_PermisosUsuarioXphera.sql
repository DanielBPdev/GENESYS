--- Core ----

IF USER_ID('xphera') IS NULL
    CREATE USER [xphera] FOR LOGIN [xphera];

EXEC sp_addrolemember 'db_datareader', [xphera];
grant ALTER ANY EXTERNAL DATA SOURCE to [xphera]
GRANT EXECUTE, alter ON OBJECT::reporteEmpresasAportantes to [xphera]
GRANT EXECUTE, alter ON OBJECT::reporteAfiliadosACargo to [xphera]
GRANT EXECUTE, alter ON OBJECT::reporteCuotaMonetariaNumeroPersonas to [xphera]
GRANT EXECUTE, alter ON OBJECT::reporteCuotaMonetariaNumeroTotalCuotas to [xphera]
GRANT EXECUTE, alter ON OBJECT::reporteInactivosAfiliados to [xphera]
GRANT EXECUTE, alter ON OBJECT::reporteMaestroAfiliados to [xphera]
GRANT EXECUTE, alter ON OBJECT::reporteNovedadesAfiliacionAportante to [xphera]
GRANT EXECUTE, alter ON OBJECT::reporteNovedadesAfiliadosSubsidios to [xphera]
GRANT EXECUTE, alter ON OBJECT::reportePastulacionesyAsignacionesFOVIS to [xphera]
