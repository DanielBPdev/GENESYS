/*se agrega columna*/
if not exists (select 1
from INFORMATION_SCHEMA.COLUMNS
where COLUMN_NAME = 'aisUsuario' and TABLE_NAME = 'AuditoriaIntegracionServicios'
)
ALTER TABLE AuditoriaIntegracionServicios ADD aisUsuario TEXT;
--update AuditoriaIntegracionServicios set aisUsuario = '';
--ALTER TABLE AuditoriaIntegracionServicios alter column aisUsuario text not null;