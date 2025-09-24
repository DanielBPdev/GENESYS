if not EXISTS(select * from ParametrizacionGaps where prgNombre = 'MARCA_TARJETA_MULTISERVICIO_PREFERENTE')
    begin
        insert into ParametrizacionGaps ( prgProceso,prgNombre,prgDescripcion,prgUsuarui,prgFechaModificacion,prgVersionLiberacion,prgTipoDatos,prgEstado,GLPI)values
        ('AFILIACIONES','MARCA_TARJETA_MULTISERVICIO_PREFERENTE','Habilita la marca para la asignación preferente de medio de pago tarjeta multiservicio','Luis Lopez',null, '1.3.13','TEXTO','INACTIVO','45063')
        end