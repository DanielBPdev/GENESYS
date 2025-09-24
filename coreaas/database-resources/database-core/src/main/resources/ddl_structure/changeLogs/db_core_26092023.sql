---GLPI 67357 - ACTUALIZA INDICATIVOS A NORMATIVA 2021
ALTER TABLE Departamento ALTER COLUMN depIndicativoTelefoniaFija VARCHAR(3);
ALTER TABLE aud.Departamento_aud ALTER COLUMN depIndicativoTelefoniaFija VARCHAR(3);
ALTER TABLE UBICACION ALTER COLUMN ubiIndicativoTelFijo VARCHAR(3);
ALTER TABLE aud.Ubicacion_aud ALTER COLUMN ubiIndicativoTelFijo VARCHAR(3);
ALTER TABLE ConvenioTerceroPagador ALTER COLUMN conIndicativoTelFijoContacto VARCHAR(3);

-- base de datos audioria
ALTER TABLE aud.Departamento_aud ALTER COLUMN depIndicativoTelefoniaFija VARCHAR(3);
ALTER TABLE aud.Ubicacion_aud ALTER COLUMN ubiIndicativoTelFijo VARCHAR(3);

--actualizacion DEPARTAMENTO
update Departamento set depIndicativoTelefoniaFija='608' where depNombre='AMAZONAS'
update Departamento set depIndicativoTelefoniaFija='604' where depNombre='ANTIOQUIA'
update Departamento set depIndicativoTelefoniaFija='607' where depNombre='ARAUCA'
update Departamento set depIndicativoTelefoniaFija='608' where depNombre='ARCHIPIÉLAGO DE SAN ANDRÉS, PROVIDENCIA Y SANTA CATALINA'
update Departamento set depIndicativoTelefoniaFija='605' where depNombre='ATLÁNTICO'
update Departamento set depIndicativoTelefoniaFija='601' where depNombre='BOGOTÁ, D.C.'
update Departamento set depIndicativoTelefoniaFija='605' where depNombre='BOLÍVAR'
update Departamento set depIndicativoTelefoniaFija='608' where depNombre='BOYACÁ'
update Departamento set depIndicativoTelefoniaFija='606' where depNombre='CALDAS'
update Departamento set depIndicativoTelefoniaFija='608' where depNombre='CAQUETÁ'
update Departamento set depIndicativoTelefoniaFija='608' where depNombre='CASANARE'
update Departamento set depIndicativoTelefoniaFija='602' where depNombre='CAUCA'
update Departamento set depIndicativoTelefoniaFija='605' where depNombre='CESAR'
update Departamento set depIndicativoTelefoniaFija='604' where depNombre='CHOCÓ'
update Departamento set depIndicativoTelefoniaFija='604' where depNombre='CÓRDOBA'
update Departamento set depIndicativoTelefoniaFija='601' where depNombre='CUNDINAMARCA'
update Departamento set depIndicativoTelefoniaFija='608' where depNombre='GUAINÍA'
update Departamento set depIndicativoTelefoniaFija='608' where depNombre='GUAVIARE'
update Departamento set depIndicativoTelefoniaFija='608' where depNombre='HUILA'
update Departamento set depIndicativoTelefoniaFija='605' where depNombre='LA GUAJIRA'
update Departamento set depIndicativoTelefoniaFija='605' where depNombre='MAGDALENA'
update Departamento set depIndicativoTelefoniaFija='608' where depNombre='META'
update Departamento set depIndicativoTelefoniaFija='602' where depNombre='NARIÑO'
update Departamento set depIndicativoTelefoniaFija='607' where depNombre='NORTE DE SANTANDER'
update Departamento set depIndicativoTelefoniaFija='608' where depNombre='PUTUMAYO'
update Departamento set depIndicativoTelefoniaFija='606' where depNombre='QUINDIO'
update Departamento set depIndicativoTelefoniaFija='606' where depNombre='RISARALDA'
update Departamento set depIndicativoTelefoniaFija='607' where depNombre='SANTANDER'
update Departamento set depIndicativoTelefoniaFija='605' where depNombre='SUCRE'
update Departamento set depIndicativoTelefoniaFija='608' where depNombre='TOLIMA'
update Departamento set depIndicativoTelefoniaFija='602' where depNombre='VALLE DEL CAUCA'
update Departamento set depIndicativoTelefoniaFija='608' where depNombre='VAUPÉS'
update Departamento set depIndicativoTelefoniaFija='608' where depNombre='VICHADA'

update Ubicacion set ubiIndicativoTelFijo='601' where ubiIndicativoTelFijo='1'
update Ubicacion set ubiIndicativoTelFijo='602' where ubiIndicativoTelFijo='2'
update Ubicacion set ubiIndicativoTelFijo='603' where ubiIndicativoTelFijo='3'
update Ubicacion set ubiIndicativoTelFijo='604' where ubiIndicativoTelFijo='4'
update Ubicacion set ubiIndicativoTelFijo='605' where ubiIndicativoTelFijo='5'
update Ubicacion set ubiIndicativoTelFijo='606' where ubiIndicativoTelFijo='6'
update Ubicacion set ubiIndicativoTelFijo='607' where ubiIndicativoTelFijo='7'
update Ubicacion set ubiIndicativoTelFijo='608' where ubiIndicativoTelFijo='8'


update aud.Ubicacion_aud set ubiIndicativoTelFijo='601' where ubiIndicativoTelFijo='1'
update aud.Ubicacion_aud set ubiIndicativoTelFijo='602' where ubiIndicativoTelFijo='2'
update aud.Ubicacion_aud set ubiIndicativoTelFijo='603' where ubiIndicativoTelFijo='3'
update aud.Ubicacion_aud set ubiIndicativoTelFijo='604' where ubiIndicativoTelFijo='4'
update aud.Ubicacion_aud set ubiIndicativoTelFijo='605' where ubiIndicativoTelFijo='5'
update aud.Ubicacion_aud set ubiIndicativoTelFijo='606' where ubiIndicativoTelFijo='6'
update aud.Ubicacion_aud set ubiIndicativoTelFijo='607' where ubiIndicativoTelFijo='7'
update aud.Ubicacion_aud set ubiIndicativoTelFijo='608' where ubiIndicativoTelFijo='8'


update ConvenioTerceroPagador set conIndicativoTelFijoContacto='601' where conIndicativoTelFijoContacto='1'
update ConvenioTerceroPagador set conIndicativoTelFijoContacto='602' where conIndicativoTelFijoContacto='2'
update ConvenioTerceroPagador set conIndicativoTelFijoContacto='603' where conIndicativoTelFijoContacto='3'
update ConvenioTerceroPagador set conIndicativoTelFijoContacto='604' where conIndicativoTelFijoContacto='4'
update ConvenioTerceroPagador set conIndicativoTelFijoContacto='605' where conIndicativoTelFijoContacto='5'
update ConvenioTerceroPagador set conIndicativoTelFijoContacto='606' where conIndicativoTelFijoContacto='6'
update ConvenioTerceroPagador set conIndicativoTelFijoContacto='607' where conIndicativoTelFijoContacto='7'
update ConvenioTerceroPagador set conIndicativoTelFijoContacto='608' where conIndicativoTelFijoContacto='8'


-- Actualiza JSON FOVIS Postulacion
UPDATE postulacionFovis
SET pofJsonPostulacion = JSON_MODIFY(
    CAST(pofJsonPostulacion AS NVARCHAR(MAX)),
    '$.postulacion.jefeHogar.ubicacionModeloDTO.indicativoTelFijo',
    CASE
        WHEN JSON_VALUE(CAST(pofJsonPostulacion AS NVARCHAR(MAX)), '$.postulacion.jefeHogar.ubicacionModeloDTO.indicativoTelFijo') = '9' THEN '609'
        WHEN JSON_VALUE(CAST(pofJsonPostulacion AS NVARCHAR(MAX)), '$.postulacion.jefeHogar.ubicacionModeloDTO.indicativoTelFijo') = '8' THEN '608'
        WHEN JSON_VALUE(CAST(pofJsonPostulacion AS NVARCHAR(MAX)), '$.postulacion.jefeHogar.ubicacionModeloDTO.indicativoTelFijo') = '7' THEN '607'
        WHEN JSON_VALUE(CAST(pofJsonPostulacion AS NVARCHAR(MAX)), '$.postulacion.jefeHogar.ubicacionModeloDTO.indicativoTelFijo') = '6' THEN '606'
        WHEN JSON_VALUE(CAST(pofJsonPostulacion AS NVARCHAR(MAX)), '$.postulacion.jefeHogar.ubicacionModeloDTO.indicativoTelFijo') = '5' THEN '605'
        WHEN JSON_VALUE(CAST(pofJsonPostulacion AS NVARCHAR(MAX)), '$.postulacion.jefeHogar.ubicacionModeloDTO.indicativoTelFijo') = '4' THEN '604'
        WHEN JSON_VALUE(CAST(pofJsonPostulacion AS NVARCHAR(MAX)), '$.postulacion.jefeHogar.ubicacionModeloDTO.indicativoTelFijo') = '3' THEN '603'
        WHEN JSON_VALUE(CAST(pofJsonPostulacion AS NVARCHAR(MAX)), '$.postulacion.jefeHogar.ubicacionModeloDTO.indicativoTelFijo') = '2' THEN '602' 
        WHEN JSON_VALUE(CAST(pofJsonPostulacion AS NVARCHAR(MAX)), '$.postulacion.jefeHogar.ubicacionModeloDTO.indicativoTelFijo') = '1' THEN '601'
        ELSE JSON_VALUE(CAST(pofJsonPostulacion AS NVARCHAR(MAX)), '$.postulacion.jefeHogar.ubicacionModeloDTO.indicativoTelFijo')
    END
)
--archivo de conciliacion
UPDATE FieldDefinitionLoad
SET LINEDEFINITION_ID = '12128'
where fieldLoadCatalog_id IN ('32133189',
'32133190',
'32133191',
'32133192',
'32133193',
'32133194',
'32133195',
'32133196',
'32133197')