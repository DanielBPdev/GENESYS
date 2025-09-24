-- =============================================
-- Author:      <Author, , Name>
-- Create Date: <Create Date, , >
-- Description: <Description, , >
-- =============================================
CREATE OR ALTER PROCEDURE [sap].[MI_consultarDetalleIntegracionRegistrosClientes]
@direccion varchar(200), 
@integracion varchar(200), 
@identificacion varchar(200), 
@codigoSap varchar(200), 
@fechaInicial varchar(200), 
@fechaFinal varchar(200), 
@estado varchar(200), 
@operacion varchar(200)
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON
	declare @scriptSQL varchar(2000)
	declare @tabla varchar(200)
	declare @filtros varchar(2000)
	declare @nombreEstado varchar(200) = 'estado'
	declare @nombreidentificacion varchar(200) = 'numerodoc'
	declare @select varchar(max)

	IF @direccion = 'GENESYS-ERP'
	begin
		set @tabla = concat(@integracion,'_G2E');

		IF @integracion = 'Personas'
		begin
			set @select ='[fecing],[horaing],case [operacion] when ''U'' then ''Actualizado'' when ''I'' then ''Creado'' end as operacion
	  ,[codigosap],[codigoGenesys],[tipodoc],[numerodoc],[nombre1],[nombre2],[apellido1],[apellido2],[fecnac],[estcivil],[direcc],[ciudad],[region]
	  ,[telefono],[celular],[email],[tipoafil],[afiliadoa],[categoria],[estado],[sexo],[autenvio],[discapacidad],[fecafil],[fecretiro]
	  ,[fecformulario],[nucleo],[fecexped],[fecingreso],[gradoesc],[grupofam],[indsub],[niveledu],[ccostos],[nrotarjeta],[ocupacion],[porcdisca]
	  ,[profesion],[rangosalario],[sectorresidencia],[tipocontrato],[tiposalario],[tipopersona],[codpostal],[nomempresa],[nitempresa],[morosa]
	  ,[nrointentos],[fecproceso],[horaproceso],[estadoreg],[observacion],[usuario]';
			set @nombreEstado = 'estadoreg';
		end
		IF @integracion = 'Contactos'
		begin
			set @nombreEstado = 'estadoreg';
			set @nombreidentificacion = 'nit'; -- Adicionado por John Sotelo 24052022
			--set @nombreidentificacion = concat(' (nit = ','''',@identificacion,''' or ','numerodoc = ','''',@identificacion,''')'); --Adicionado por John Sotelo 26052022 VALIDAR
			set @select = '[fecing],[horaing],[codigosap],[tipodoc],[numerodoc],[nombrecompleto],[nombrepila],[TipoContacto],[genero]
      ,[fecnac],[estadocivil],[telefono],[celular],[email],[direcc],[ciudad],[dpto],[autenvio],[nit],[tipodocEmpresa],[CodigoGenesys]
	  ,case [operacion] when ''M'' then ''Modificado'' when ''I'' then ''Creado'' end as operacion,[nrointentos],[fecproceso],[horaproceso],[estadoreg]
      ,[observacion],[usuario]';
		end
		IF @integracion = 'Empresas'
		begin
			set @nombreEstado = 'estadoreg';
			set @select = '[fecIng],[horaIng],case [operacion] when ''U'' then ''Actualizado'' when ''I'' then ''Creado'' end as operacion
      ,[codigoSAP],[tipoDoc],[numeroDoc],[digVerif],[nombreEmpresa],[direcc],[ciudad],[region],[telefono],[celular],[email],[tipoAfil],[afiliadoA]
      ,[tarifaServ],[estado],[grupoCta],[celCorresp],[dptoCorresp],[dirCorresp],[munCorresp],[telCorresp],[morosa],[nroTrabInsc],[solPazySalvo]
      ,[tipoSector],[tipoCliente],[trasladoCaja],[ultimaCCFProcedencia],[vlrAporte],[autEnvio],[fecConstitucion],[fecAfil],[fecRetiro],[fecFormulario]
      ,[actEco],[nucleo],[fecIngreso],[arl],[paginaWeb],[tipoPersona],[telefono2],[autCorreo],[codPostal],[nroIntentos],[fecProceso],[horaProceso]
	  ,[estadoReg],[observacion]q,[usuario],[codigoGenesys]';
		end
		IF @integracion = 'Relaciones'
		begin
			set @nombreEstado = 'estadoreg';
			set @nombreidentificacion = concat(' identificacionCliente1 = ','''',@identificacion,''' or ','identificacionCliente2');
			set @select = '[fecing],[horaing],[tipocliente1],[cliente1],[identificacionCliente1],[codrelacion],[tipocliente2]
      ,[cliente2],[identificacionCliente2],case [operacion] when ''R'' then ''Retirado'' when ''I'' then ''Ingresado'' end as operacion,[nrointentos]
	  ,[fecproceso],[horaproceso],[estadoreg],[observacion],[usuario]';
		end
	end;

	IF @direccion = 'ERP-GENESYS'
	begin
		set @tabla = concat(@integracion,'_E2G');
		IF @integracion = 'Personas'
		begin
			set @nombreidentificacion = 'nroiden';
			set @select = '[fecing],[horaing],[direccionresidencia],[municipioresidencia],[departamentoresidencia]
      ,[telefonoresidencia],[celular],[correoelectronico],[codigosap],[nombre1],[nombre2],[apellido1],[apellido2],[tipoi],[nroiden]
      ,case [operacion] when ''U'' then ''Actualizado'' when ''I'' then ''Creado'' end as operacion,[fecproceso],[horaproceso],[estado],[observacion]';
		end
		IF @integracion = 'Empresas'
		begin
			set @nombreEstado = 'estadoreg';
			set @nombreidentificacion = 'nitempresa';
			set @select = '[fecing] ,[horaing],[direccion],[municipio],[departamento],[telefono],[celular],[direccioncorrespondencia]
      ,[municipiocorrespondencia],[departamentocorrespondencia],[telefonocorrespondencia],[celularcorrespondencia],[email],[codigosap],[nitempresa]
      ,[digverif],[nombreempresa],[tipoid],[pagweb],[telefono2],[fecproceso],[horaproceso],[estadoreg],[observacion]
      ,case [operacion] when ''U'' then ''Actualizado'' when ''I'' then ''Creado'' end as operacion ';
		end
		IF @integracion = 'Contactos'
		begin
			set @nombreEstado = 'estadoreg';
			set @nombreidentificacion = 'nit';
			set @select = '[fecing],[horaing],case [operacion] when ''U'' then ''Actualizado'' when ''I'' then ''Creado''  when ''D'' then ''Borrado'' end as operacion
      ,[nit],[codigosap],[tipoidcontacto],[cedulacontacto],[nombres],[apellidos],[estcivil],[departamento],[municipio],[direccion],[telefono]
      ,[telefonomovil],[mail],[TipoContacto],[fecproceso],[horaproceso],[estadoreg],[observacion],[usuario]';
		end
	end;

	set @scriptSQL = CONCAT('select top 2750 consecutivo as consecutivoInicial, CASE ',@nombreEstado,' 
			WHEN ''E'' THEN ''Error''
		WHEN ''V'' THEN ''Enviado''
		WHEN ''S'' THEN ''Satisfactorio''
		WHEN ''P'' THEN ''Pendiente''
		END
	as estadoInicial,',@select ,'from sap.',@tabla)

	if @identificacion != ''
	begin
		set @filtros = CONCAT (@filtros,' and ( ',  @nombreidentificacion,' =''',@identificacion,''')') 
	end

	if @codigoSap != ''
	begin		
		set @filtros = CONCAT (@filtros, ' and  codigosap =''',@codigoSap,'''') 
	end;
	
	if @fechaInicial != '' and @fechaFinal != ''
	begin
		set @filtros = CONCAT (@filtros, ' and fecing between ''',@fechaInicial,''' and ''', @fechaFinal ,'''') 
	end;

	if @estado != ''
	begin
		set @filtros = CONCAT (@filtros,' and ', @nombreEstado, ' =''',@estado,'''') 
	end;

	if @operacion != ''
	begin
		set @filtros = CONCAT (@filtros, ' and operacion =''',@operacion,'''') 
	end;
	
	--if @filtros != ''
	--begin
		set @scriptSQL = CONCAT(@scriptSQL,' where ', @nombreEstado, ' in (''S'',''P'',''V'',''E'') ' ,@filtros,' ORDER BY 1 desc')
	--End

	print @scriptSQL 

	EXEC (@scriptSQL)

END