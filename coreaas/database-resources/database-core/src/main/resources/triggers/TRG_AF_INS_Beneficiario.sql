-- =============================================
-- Author:    Diego Suesca
-- Create date: 2019/01/18 
-- Author:    Francisco Alejandro Hoyos Rojas
-- Modified date: 2020/02/24 
-- Description:
-- =============================================
CREATE TRIGGER TRG_AF_INS_Beneficiario
ON Beneficiario
AFTER INSERT AS
BEGIN
  SET NOCOUNT ON;

    -- ************** INICIO CALCULO DE CATEGORIAS BENEFICIARIO ****************************************************************************
    -- se crea tabla temporal para calcular categorías con sp USP_GET_CategoriaAfiliado    
   -- CREATE TABLE #CategoriaBeneficiarioCalculada
   --(beneficiarioDetalle bigint,persona bigint,tipoIdentificacion varchar(20),numeroIdentificacion varchar(16),tipoBeneficiario varchar(30),estadoBeneficiarioAfiliado varchar(8),afiliado bigint,tipoIdentificacionAfiliado varchar(20),numeroIdentificacionAfiliado varchar(16),tipoCotizante varchar(100),clasificacion varchar(48),salario numeric (19,2),estadoAfiliacionAfiliado varchar(8),fechaFinServicioSinAfiliacion date,categoria varchar(50))

   -- -- personas a calcular categoria
   -- INSERT #CategoriaBeneficiarioCalculada (persona)
   -- SELECT benPersona FROM INSERTED

   -- -- ejecucion sp calculo de categorias, quedaran en la tabla #CategoriaAfiliadoCalculada
   -- EXEC USP_GET_CategoriaBeneficiario

   -- -- **************** FIN CALCULO DE CATEGORIAS BENEFICIARIO ***********************************************************************

   -- -- CATEGORIA BENEFICIARIO **********************************************************************************

   -- INSERT CategoriaBeneficiario (ctbBeneficiarioDetalle,ctbTipoBeneficiario,ctbCategoriaAfiliado)
   -- SELECT vw.beneficiarioDetalle, vw.tipoBeneficiario, cta.ctaId
   -- FROM #CategoriaBeneficiarioCalculada vw
   -- INNER JOIN CategoriaAfiliado cta ON vw.afiliado = cta.ctaAfiliado AND vw.tipoCotizante = cta.ctaTipoAfiliado      
   -- INNER JOIN INSERTED ben ON ben.benAfiliado = vw.afiliado AND ben.benBeneficiarioDetalle = vw.beneficiarioDetalle
   -- LEFT OUTER JOIN CategoriaBeneficiario ctb ON ctb.ctbBeneficiarioDetalle = vw.beneficiarioDetalle 
   --                                         AND ctb.ctbTipoBeneficiario = vw.tipoBeneficiario 
   --                                         AND ctb.ctbCategoriaAfiliado = cta.ctaId
   -- WHERE ctb.ctbBeneficiarioDetalle IS NULL 
   --   AND ctb.ctbTipoBeneficiario IS NULL 
   --   AND ctb.ctbCategoriaAfiliado IS NULL

   declare @benid bigint
   declare @benAfiliado bigint

   select @benid = ben.benId, @benAfiliado = ben.benAfiliado from inserted ben

    EXEC USP_REP_CalcularCategoriaBeneficiarioInsert @benid, @benAfiliado
    INSERT HistoricoBeneficiario(hbeParentesco,
            hbeTipoBeneficiario,
            hbePrimerNombreAfiliado,
            hbeSegundoNombreAfiliado,
            hbePrimerApellidoAfiliado,
            hbeSegundoApellidoAfiliado,
            hbeRazonSocialAfiliado,
            hbeTipoIdentificacionAfiliado,
            hbeNumeroIdentificacionAfiliado,
            hbeFechaAfiliacion,
            hbeEstadoBeneficiarioAfiliado,
            hbeTipoIdentificacionBeneficiario,
            hbeNumeroIdentificacionBeneficiario,
			      hbeBeneficiario)
    SELECT CASE
              WHEN ben.benTipoBeneficiario IN ('HIJO_BIOLOGICO', 'HIJO_ADOPTIVO','HIJASTRO','HERMANO_HUERFANO_DE_PADRES','BENEFICIARIO_EN_CUSTODIA')
                THEN 'HIJO'
              WHEN ben.benTipoBeneficiario IN ('PADRE','MADRE')
                THEN 'PADRE'
              ELSE '' END,
      ben.benTipoBeneficiario,
      perAfi.perprimerNombre,
      perAfi.persegundoNombre,
      perAfi.perprimerApellido,
      perAfi.persegundoApellido,
      perAfi.perRazonSocial,
      perAfi.pertipoIdentificacion,
      perAfi.pernumeroIdentificacion,
      ben.benFechaAfiliacion,
      ben.benEstadoBeneficiarioAfiliado,
      perBen.pertipoIdentificacion,
      perBen.pernumeroIdentificacion,
      ben.benId
    FROM INSERTED ben, Persona perBen, Afiliado afi, Persona perAfi
     WHERE ben.benpersona = perBen.perid
     AND ben.benafiliado = afi.afiId
     AND afi.afiPersona = perAfi.perid
END
