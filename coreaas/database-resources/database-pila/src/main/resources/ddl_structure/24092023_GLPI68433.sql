insert dbo.PilaPersistenciaParametrizacion (pppTabla,pppTipoDato,pppOrden,pppPosicionInicial,pppPosicionFinal,pppDescripcion,pppEsRequerido,pppSubId,pppCampo,pppSubStr)
select 'PilaArchivoIPRegistro2', 'STRING',24, 213, 214,'Letra: indicativo de correccion planilla pensionados A:C',1, 9, 'ip2Correcion', 'RTRIM(LTRIM(SUBSTRING(papTextoRegistro,214, 1)))'

if  EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME = 'ip2Correciones' AND TABLE_NAME = 'PilaArchivoIPRegistro2')
 BEGIN 
 ALTER TABLE PilaArchivoIPRegistro2 DROP COLUMN ip2Correciones;
 END
ALTER TABLE PilaArchivoIPRegistro2 ADD ip2Correcion varchar(1)