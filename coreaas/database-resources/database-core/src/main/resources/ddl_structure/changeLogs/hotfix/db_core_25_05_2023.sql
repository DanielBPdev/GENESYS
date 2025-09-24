if not exists (select * from sysobjects where name='OficinasJuzgados' and xtype='U')
	CREATE TABLE [dbo].[OficinasJuzgados](
    ctaId bigint PRIMARY KEY IDENTITY not null,
    ofjNroCtaJudicial bigint not null,
    ofjCodJuzgado bigint not null,
    ofjNombreJuzgado varchar(118) not null
);

if not exists (select * from sysobjects where name='OficinasJuzgados_aud' and xtype='U')
	CREATE TABLE [aud].[OficinasJuzgados_aud](
    ctaId bigint PRIMARY KEY IDENTITY not null,
	REV bigint NOT NULL,
	REVTYPE smallint,
    ofjNroCtaJudicial bigint not null,
    ofjCodJuzgado bigint not null,
    ofjNombreJuzgado varchar(118) not null
);