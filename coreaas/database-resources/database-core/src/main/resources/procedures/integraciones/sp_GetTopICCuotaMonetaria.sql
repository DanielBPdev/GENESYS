CREATE OR ALTER PROCEDURE [sap].[sp_GetTopICCuotaMonetaria]
AS
BEGIN

--Se actualiza codigoSap de acreedor en la tabla de integración contable si codigoGenesys existe en la tabla de homologación de acreedor	
-- ---------------------
DECLARE @consecutivoD BIGINT, @codigoGenesysH BIGINT, @codigoSapH VARCHAR(10), @tipoH VARCHAR(1)
  SET NOCOUNT ON
  DECLARE CUR_SEL_CODIGSAP CURSOR
  FOR
   SELECT DISTINCT a.consecutivo, c.codigoGenesys, c.codigoSap, c.tipo
    FROM [sap].[IC_CM_Det] a , [sap].[IC_CM_Enc] b, [sap].[CodSAPGenesysAcreedor] c 
   WHERE a.consecutivo = b.consecutivo
    AND b.estadoReg = 'P'
    AND a.codigoGenesys = c.codigoGenesys 
	AND a.tipo = c.tipo 
	AND ( a.codigoSap is null or a.codigoSap = '' )
	AND a.claseCuenta = 'K' 
  OPEN CUR_SEL_CODIGSAP
  FETCH NEXT FROM CUR_SEL_CODIGSAP INTO @consecutivoD,@codigoGenesysH, @codigoSapH, @tipoH
  WHILE @@fetch_status = 0
  BEGIN
   UPDATE [sap].[IC_CM_Det]
	SET codigoSap = @codigoSapH								
   WHERE consecutivo = @consecutivoD
    AND ( codigoSap is null or codigoSap = '' )
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
    FROM [sap].[IC_CM_Det] a , [sap].[IC_CM_Enc] b, [sap].[CodSAPGenesysDeudor] c 
   WHERE a.consecutivo = b.consecutivo
    AND b.estadoReg = 'P'
    AND a.codigoGenesys = c.codigoGenesys 
	AND a.tipo = c.tipo 
	AND ( a.codigoSap is null or a.codigoSap = '' )
	AND a.claseCuenta = 'D' 
  OPEN CUR_SEL_CODIGSAP
  FETCH NEXT FROM CUR_SEL_CODIGSAP INTO @consecutivoD,@codigoGenesysH, @codigoSapH, @tipoH
  WHILE @@fetch_status = 0
  BEGIN
   UPDATE [sap].[IC_CM_Det]
	SET codigoSap = @codigoSapH								
   WHERE consecutivo = @consecutivoD
    AND ( codigoSap is null or codigoSap = '' )
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
WHERE  a.codigo = 'IC_CM_G2E') c.consecutivo
INTO   #TempConsecutivosIC_CM_G2E
FROM   SAP.IC_CM_Enc c
WHERE  c.estadoReg = 'P'
AND ( c.referencia is not null OR len(c.referencia) > 0 )
AND c.consecutivo NOT IN (SELECT DISTINCT d.consecutivo
                           FROM [sap].[IC_CM_Det] d
                          WHERE ( d.codigoSap is null or d.codigoSap = '' )
						  AND d.claseCuenta in ( 'K','D') )
AND 2 <= ( SELECT COUNT(*)
            FROM [sap].[IC_CM_Det] d
          WHERE d.consecutivo = c.consecutivo )	
ORDER BY c.consecutivo;

-- Seleccionar la informacion a enviar
SELECT a.consecutivo,FORMAT(a.fechaDocumento,'yyyyMMdd') AS fechaDocumento, FORMAT(a.fechaContabilizacion,'yyyyMMdd') AS fechaContabilizacion, a.periodo,FORMAT(a.periodoAnioLiquidacion,'yyyyMMdd') AS periodoAnioLiquidacion, a.referencia,a.tipoMovimiento,
  a.moneda,a.sociedad,a.radicadoLiquidacion,b.codigoSap,b.importeDocumento,b.claveCont,
           b.asignacion,b.ref1,b.ref2,b.tipoDocumento,b.ref3,b.tipoCuotaMonetaria,b.idTipoCm,b.formaPago,b.codigoEntidadDescuento,
           b.bancoDispersionDevolucion,b.cajaPago,FORMAT(b.periodoLiquidado,'yyyyMMdd') AS periodoLiquidado    
    FROM   SAP.IC_CM_Enc a, SAP.IC_CM_Det b
WHERE  a.consecutivo = b.consecutivo
AND    a.consecutivo IN (SELECT c.consecutivo
                        FROM   #TempConsecutivosIC_CM_G2E c);
-- Actualizar a Enviado (V) de los registros tomados
UPDATE SAP.IC_CM_Enc SET estadoReg = 'V'
WHERE  consecutivo IN (SELECT c.consecutivo
                      FROM   #TempConsecutivosIC_CM_G2E c);
END;