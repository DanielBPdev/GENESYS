CREATE OR ALTER PROCEDURE [sap].[sp_GetTopICFovis]
AS
BEGIN

	--Se actualiza codigoSap de acreedor en la tabla de integración contable si codigoGenesys existe en la tabla de homologación de acreedor	
-- ---------------------
DECLARE @consecutivoD BIGINT, @codigoGenesysH BIGINT, @codigoSapH VARCHAR(10), @tipoH VARCHAR(1)
  SET NOCOUNT ON
  DECLARE CUR_SEL_CODIGSAP CURSOR
  FOR
   SELECT DISTINCT a.consecutivo, c.codigoGenesys, c.codigoSap, c.tipo
    FROM [sap].[IC_FOVIS_Det] a , [sap].[IC_FOVIS_Enc] b, [sap].[CodSAPGenesysAcreedor] c 
   WHERE a.consecutivo = b.consecutivo
    AND b.estadoReg = 'P'
	AND a.codigoGenesys = c.codigoGenesys 
	AND a.tipo = c.tipo 
	AND (a.codigoSap is null or a.codigoSap = '' ) --a.codigoSap is null 
	AND a.claseCuenta = 'K' 
	
  OPEN CUR_SEL_CODIGSAP
  FETCH NEXT FROM CUR_SEL_CODIGSAP INTO @consecutivoD,@codigoGenesysH, @codigoSapH, @tipoH
  WHILE @@fetch_status = 0
  BEGIN
   UPDATE [sap].[IC_FOVIS_Det]
	SET codigoSap = @codigoSapH								
   WHERE consecutivo = @consecutivoD
    AND (codigoSap is null or codigoSap = '') --codigoSap is null 
    AND claseCuenta = 'K'
    AND codigoGenesys = @codigoGenesysH
    AND tipo = @tipoH
   FETCH NEXT FROM CUR_SEL_CODIGSAP INTO @consecutivoD,@codigoGenesysH, @codigoSapH, @tipoH
  END
  CLOSE CUR_SEL_CODIGSAP
  DEALLOCATE CUR_SEL_CODIGSAP
  SET NOCOUNT OFF
-- ---------------------
--Se actualiza codigoSap de deudor en la tabla de integración contable si codigoGenesys existe en la tabla de homologación de deudor	
-- ---------------------
  SET NOCOUNT ON
  DECLARE CUR_SEL_CODIGSAP CURSOR
  FOR
   SELECT DISTINCT a.consecutivo, c.codigoGenesys, c.codigoSap, c.tipo
    FROM [sap].[IC_FOVIS_Det] a , [sap].[IC_FOVIS_Enc] b, [sap].[CodSAPGenesysDeudor] c 
   WHERE a.consecutivo = b.consecutivo
    AND b.estadoReg = 'P'
    AND a.codigoGenesys = c.codigoGenesys 
	AND a.tipo = c.tipo 
	AND (a.codigoSap is null or a.codigoSap = '') --a.codigoSap is null 
	AND a.claseCuenta = 'D' 
	
  OPEN CUR_SEL_CODIGSAP
  FETCH NEXT FROM CUR_SEL_CODIGSAP INTO @consecutivoD,@codigoGenesysH, @codigoSapH, @tipoH
  WHILE @@fetch_status = 0
  BEGIN
   UPDATE [sap].[IC_FOVIS_Det]
	SET codigoSap = @codigoSapH								
   WHERE consecutivo = @consecutivoD
    AND (codigoSap is null or codigoSap = '' ) --codigoSap is null 
    AND claseCuenta = 'D'
    AND codigoGenesys = @codigoGenesysH
    AND tipo = @tipoH
   FETCH NEXT FROM CUR_SEL_CODIGSAP INTO @consecutivoD,@codigoGenesysH, @codigoSapH, @tipoH
  END
  CLOSE CUR_SEL_CODIGSAP
  DEALLOCATE CUR_SEL_CODIGSAP
  SET NOCOUNT OFF
-- ---------------------

-- Definir los registros a tomar
SELECT TOP (SELECT a.nroRegEnvio
           FROM   SAP.ConfigIntegraciones a
WHERE  a.codigo = 'IC_FOVIS_G2E') c.consecutivo
INTO   #TempConsecutivosIC_FOVIS_G2E
FROM   SAP.IC_FOVIS_Enc c
WHERE  c.estadoReg = 'P'
AND ( c.referencia is not null OR len(c.referencia) > 0 )
AND c.consecutivo NOT IN (SELECT DISTINCT d.consecutivo
                           FROM [sap].[IC_FOVIS_Det] d
                          WHERE (d.codigoSap is null or d.codigoSap = '') --d.codigoSap is null
						  AND d.claseCuenta in ( 'K','D') )
AND 2 <= ( SELECT COUNT(*)
            FROM [sap].[IC_FOVIS_Det] d
          WHERE d.consecutivo = c.consecutivo )	
ORDER BY c.consecutivo;

-- Seleccionar la informacion a enviar
SELECT FORMAT(a.fechaDocumento,'yyyyMMdd') AS fechaDocumento,FORMAT(a.fechaContabilizacion,'yyyyMMdd') AS fechaContabilizacion,
      a.periodo,a.referencia,a.tipoMovimiento,
      a.consecutivo,a.moneda,a.sociedad,b.codigoSap,b.claveCont,b.importeDocumento,b.ref1,
      b.tipoDocumento,b.ref3,b.tipoSector,b.anulacion,b.idProyecto,b.nombreProyecto,b.proyectoPropio,
      b.rendimientoFinanciero,b.asignacion,b.clavePaisBanco,b.claveBanco,b.nroCuentaBancaria,
      b.titularCuenta,b.claveControlBanco,b.tipoBancoInter,b.referenciaBancoCuenta,a.componente
    FROM   SAP.IC_FOVIS_Enc a, SAP.IC_FOVIS_Det b
WHERE  a.consecutivo = b.consecutivo
AND    a.consecutivo IN (SELECT c.consecutivo
                        FROM   #TempConsecutivosIC_FOVIS_G2E c);
-- Actualizar a Enviado (V) de los registros tomados
UPDATE SAP.IC_FOVIS_Enc SET estadoReg = 'V'
WHERE  consecutivo IN (SELECT c.consecutivo
                      FROM   #TempConsecutivosIC_FOVIS_G2E c);
END;



