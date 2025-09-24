if not EXISTS(select * from ParametrizacionGaps where prgNombre = 'APORTES_FUTUROS')
    begin
        insert into ParametrizacionGaps ( prgProceso,prgNombre,prgDescripcion,prgUsuarui,prgFechaModificacion,prgVersionLiberacion,prgTipoDatos,prgEstado,GLPI)values
        ('APORTES','APORTES_FUTUROS','Permitira dejar los aportes futuros como registrados si se encuentra activo y relacionados si esta inactivo dependiendo de las condiciones del aportante','Andres Rey',null, '1.3.19','Booleano','INACTIVO','63385')
        end