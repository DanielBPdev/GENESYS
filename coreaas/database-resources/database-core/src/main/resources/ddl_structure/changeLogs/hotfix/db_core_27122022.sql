 update FieldDefinitionLoad
 set finalposition = 60,
  initialposition = 47
 where label = 'archivoIregistro4campo6'

 update FieldDefinitionLoad
 set finalposition = 47,
  initialposition = 27
 where label = 'archivoIregistro4campo5'

  update FieldDefinitionLoad
 set finalposition = 27,
  initialposition = 17
 where label = 'archivoIregistro4campo4'


update FieldDefinitionLoad
 set finalposition = 17,
  initialposition = 3
 where label = 'archivoIregistro4campo3'

 update FieldDefinitionLoad
 set finalposition = 3,
  initialposition = 1
 where label = 'archivoIregistro4campo2'

  update validatorparamvalue
 set value = '01,02,03,04,05,06,07,08,09,10,11,12'
 where id = 2111662