

if not exists (select * from Parametro where prmNombre = 'PASSWORD_ACTUALIZAR_USUARIOS_MASIVOS')
begin		
    INSERT INTO Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion,prmTipoDato,prmVisualizarPantalla)
    VALUES ('PASSWORD_ACTUALIZAR_USUARIOS_MASIVOS', 'fs4kQ3Nk+YlPKNvE4srzrA==', 0, 'VALOR_GLOBAL_TECNICO', 'Contraseña utilizada al reestablecer usuarios de forma masiva', 'TEXT', 0)
END