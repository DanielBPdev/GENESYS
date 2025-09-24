-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/01/18
-- Author:    Francisco Alejandro Hoyos Rojas
-- Modified date: 2020/02/24  
-- Description:	
-- =============================================
CREATE TRIGGER [dbo].[TRG_AF_UPD_Beneficiario]
ON [dbo].[Beneficiario]
AFTER UPDATE AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @benId BIGINT  


  --  -- CATEGORIA BENEFICIARIO **********  
  --  IF UPDATE (benAfiliado) OR UPDATE(benBeneficiarioDetalle)  
  --  BEGIN
  --  	  -- ** INICIO CALCULO DE CATEGORIAS BENEFICIARIO **********
  --  	-- se crea tabla temporal para calcular categorías con sp USP_GET_CategoriaAfiliado    
  --  	CREATE TABLE #CategoriaBeneficiarioCalculada
  -- 		(beneficiarioDetalle bigint,persona bigint,tipoIdentificacion varchar(20),numeroIdentificacion varchar(16),tipoBeneficiario varchar(30),estadoBeneficiarioAfiliado varchar(8),afiliado bigint,tipoIdentificacionAfiliado varchar(20)
		--,numeroIdentificacionAfiliado varchar(16),tipoCotizante varchar(100),clasificacion varchar(48),salario numeric (19,2),estadoAfiliacionAfiliado varchar(8),fechaFinServicioSinAfiliacion date,categoria varchar(50))

  --  	-- personas a calcular categoria
  --  	INSERT #CategoriaBeneficiarioCalculada (persona)
  --  	SELECT benPersona FROM INSERTED

  --  	-- ejecucion sp calculo de categorias, quedaran en la tabla #CategoriaAfiliadoCalculada
  --  	EXEC USP_GET_CategoriaBeneficiario

  --  	-- ** FIN CALCULO DE CATEGORIAS BENEFICIARIO *********

  --  	-- CATEGORIA BENEFICIARIO **********

  --      INSERT INTO CategoriaBeneficiario (ctbBeneficiarioDetalle,ctbTipoBeneficiario,ctbCategoriaAfiliado)
  --      SELECT vw.beneficiarioDetalle, vw.tipoBeneficiario, cta.ctaId
  --      FROM #CategoriaBeneficiarioCalculada vw
  --      INNER JOIN CategoriaAfiliado cta ON vw.afiliado = cta.ctaAfiliado AND vw.tipoCotizante = cta.ctaTipoAfiliado      
  --      INNER JOIN INSERTED ben ON ben.benAfiliado = vw.afiliado AND ben.benBeneficiarioDetalle = vw.beneficiarioDetalle
  -- 		LEFT OUTER JOIN CategoriaBeneficiario ctb ON ctb.ctbBeneficiarioDetalle = vw.beneficiarioDetalle AND ctb.ctbTipoBeneficiario = vw.tipoBeneficiario AND ctb.ctbCategoriaAfiliado = cta.ctaId
  --  	WHERE ctb.ctbBeneficiarioDetalle IS NULL AND ctb.ctbTipoBeneficiario IS NULL AND ctb.ctbCategoriaAfiliado IS NULL
      
  --  END 

	if update (benFechaRetiro)
	begin

		update b set b.benFechaRetiro = desp.benFechaRetiro
		from deleted as ant
		inner join inserted as desp on ant.benId = desp.benId
		inner join beneficiario as b on desp.benId = b.benId
		where desp.benEstadoBeneficiarioAfiliado = 'INACTIVO' and ant.benEstadoBeneficiarioAfiliado = 'INACTIVO'

	end
	if update (benEstadoBeneficiarioAfiliado)
	begin

		declare @benAfiliado bigint
		select @benId = ben.benId, @benAfiliado = ben.benAfiliado from inserted ben
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
	            hbeMotivoDesafiliacion,
	            hbeFechaRetiro)
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
	        ben.benMotivoDesafiliacion,
	        ben.benFechaRetiro
			FROM Persona as perAfi with(nolock)
			inner join afiliado as a  with(nolock) ON a.afipersona = perAfi.perid  
			inner join inserted as ben with(nolock)ON ben.benafiliado = a.afiid
			inner join Persona as perBen with(nolock)ON perBen.perid = ben.benPersona
	end
END