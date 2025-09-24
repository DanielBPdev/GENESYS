------- create table 

if not exists (select 1
from INFORMATION_SCHEMA.TABLES
where TABLE_NAME = 'AuditoriaIntegracionServicios'
and TABLE_SCHEMA = 'dbo')
begin
-- creacion de la tabla AuditoriaIntegracionServicios
create table AuditoriaIntegracionServicios(
aisId int IDENTITY(1,1) PRIMARY KEY NOT NULL,
aisFechaHoraOperacionTransaccion DATETIME NOT NULL,
aisServicio VARCHAR(225) NOT NULL,
aisParametrosIn TEXT NOT NULL,
aisCodigo_Estado INT NOT NULL,
aisParametrosOut TEXT NOT NULL,
aisDireccion_IP VARCHAR(45) NOT NULL,
aisDuraccion INT NOT NULL,
aisResultado BIT NOT NULL,
aisDetalles_error TEXT);
end