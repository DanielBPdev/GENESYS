
	declare @table varchar(100)
	declare @sql table(s varchar(1000), id int identity)

	DECLARE @tablas AS TABLE (tabla VARCHAR(100))
	DECLARE @cant BIGINT

	INSERT INTO @Tablas (tabla) VALUES
	('MedioDePago'),
	('MedioEfectivo'),
	('MedioTarjeta'),
	('MedioTransferencia'),
	('Persona'),
	('Afiliado'),
	('GrupoFamiliar'),
	('CodigoCIIU'),
	('Empresa'),
	('SucursalEmpresa'),
	('Empleador'),
	('RolAfiliado'),
	('AdministradorSubsidio'),
	('AdminSubsidioGrupo'),
	('DatoTemporalSolicitud'), --No va en aud
	('IntentoNovedad'),
	('IntentoAfiliacion'),
	('SedeCajaCompensacion'),
	('Solicitud'),
	('SolicitudAfiliaciEmpleador'),
	('SolicitudAfiliacionPersona'),
	('Comunicado'),
	('ParametrizacionNovedad'),
	('SolicitudNovedad'),
	('SolicitudNovedadEmpleador'),
	('SolicitudNovedadPersona'),
	('NovedadDetalle'),
	('AporteGeneral'),
	('AporteDetallado'),
	('SocioEmpleador'),
	('PersonaDetalle'),
	('BeneficiarioDetalle'),
	('Beneficiario'),
	('BeneficioEmpleador'),
	('CondicionInvalidez'),
	('RolContactoEmpleador')

	
	insert into  @sql(s) values ('--liquibase formatted sql')
	insert into  @sql(s) values ('')

	insert into  @sql(s) values ('--changeset arocha:01 runOnChange:true')
	insert into  @sql(s) values ('')
	DECLARE @generatorCursor AS CURSOR

	SET @generatorCursor = CURSOR FAST_FORWARD FOR
	
	SELECT tabla FROM @Tablas
	
	OPEN @generatorCursor
	FETCH NEXT FROM @generatorCursor INTO
	@table
	
	WHILE @@FETCH_STATUS = 0
	BEGIN
	
		-- create statement
		insert into  @sql(s) values ('IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE=''BASE TABLE'' AND TABLE_NAME=''' + @table + ''' AND TABLE_SCHEMA = ''dbo'')')
		insert into  @sql(s) values ('DROP TABLE dbo.' + @table + ';')
		insert into  @sql(s) values ('CREATE TABLE dbo.' + @table + ' (')

		-- column list
		insert into @sql(s)
		select 
			'  '+column_name+' ' + 
			data_type + coalesce(CASE WHEN data_type NOT IN ('text') THEN '('+ CASE WHEN character_maximum_length = -1 THEN 'max' ELSE cast(character_maximum_length as varchar) END +')' ELSE '' END,'') + ' ' + +
			( case when IS_NULLABLE = 'No' then 'NOT ' else '' end ) + 'NULL ' + 
			coalesce('DEFAULT '+COLUMN_DEFAULT,'') + ','

		 from INFORMATION_SCHEMA.COLUMNS where table_name = @table
		 order by ordinal_position

		-- primary key
		declare @pkname varchar(100)
		select @pkname = constraint_name from INFORMATION_SCHEMA.TABLE_CONSTRAINTS
		where table_name = @table and constraint_type='PRIMARY KEY'

		if ( @pkname is not null ) begin
			insert into @sql(s) values('CONSTRAINT ' + @pkname + ' PRIMARY KEY  (')
			insert into @sql(s)
				select '   '+COLUMN_NAME+',' from INFORMATION_SCHEMA.KEY_COLUMN_USAGE
				where constraint_name = @pkname
				order by ordinal_position
			-- remove trailing comma
			update @sql set s=left(s,len(s)-1) where id=@@identity
			insert into @sql(s) values ('  )')
		end
		else begin
			-- remove trailing comma
			update @sql set s=left(s,len(s)-1) where id=@@identity
		end

		-- closing bracket
		insert into @sql(s) values( ');' )

	
		FETCH NEXT FROM @generatorCursor INTO
		@table
	END
CLOSE @generatorCursor;
DEALLOCATE @generatorCursor;

-- result!
select s from @sql order by id
