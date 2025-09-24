if not exists (select * from sysobjects where name='RegistroDespliegueAmbiente' and xtype='U')
    create table dbo.RegistroDespliegueAmbiente (
        rdaUltimaFechaDespliegue DATETIME,
        rdaRamaDespliegue VARCHAR(50)
    )