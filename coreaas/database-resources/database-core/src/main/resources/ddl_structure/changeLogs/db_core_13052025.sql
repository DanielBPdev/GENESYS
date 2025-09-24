--Parametro para almacenar la clave segura 
IF NOT EXISTS (SELECT 1 FROM Parametro WHERE prmNombre = 'JWT_SECRET')
BEGIN
    INSERT INTO Parametro (prmNombre, prmValor, prmCargaInicio, prmSubcategoriaParametro, prmDescripcion, prmTipoDato, prmVisualizarPantalla)
    VALUES ('JWT_SECRET', 'XT7FM816+P+kQNzqyiTQGXir8XvcZuMhe9rQoaZZDDA=', 0, 'VALOR_GLOBAL_NEGOCIO', 'Parametro correspondiente al token del URL para cambio de contrasenia', 'TEXT', 1);
END

--Parametro para almacenar el tiempo de expiracion de la url para el cambio de contraseña
IF NOT EXISTS (SELECT 1 FROM Parametro WHERE prmNombre = 'JWT_EXPIRATION_MS')
BEGIN
    INSERT INTO Parametro (prmNombre, prmValor, prmCargaInicio, prmSubcategoriaParametro, prmDescripcion, prmTipoDato, prmVisualizarPantalla)
    VALUES ('JWT_EXPIRATION_MS', '15m', 0, 'VALOR_GLOBAL_NEGOCIO', 'Parametro correspondiente al tiempo de expiracion del token de la url de cambio de contrasenia', 'TIME', 1);
END

--Parametro para almacenar la url de la pantalla a la que se redigira desde el link enviado por correo (este se debe parametrizar en el 
-- apartado de Parametrización y gestión-> Gestionar parámetros generales por ambiente ya que este debe cambiar para los ambientes productivos)
IF NOT EXISTS (SELECT 1 FROM Parametro WHERE prmNombre = 'FRONTEND_RESET_PASSWORD_URL')
BEGIN
    INSERT INTO Parametro (prmNombre, prmValor, prmCargaInicio, prmSubcategoriaParametro, prmDescripcion, prmTipoDato, prmVisualizarPantalla)
    VALUES ('FRONTEND_RESET_PASSWORD_URL', 'https://genesyspantallastest.asopagos.com/seguridad/cambio-contrasenia', 0, 'VALOR_GLOBAL_NEGOCIO', 'Parametro correspondiente a la pantalla donde redirigira el url del correo electronico de cambio de contrasenia', 'URL', 1);
END