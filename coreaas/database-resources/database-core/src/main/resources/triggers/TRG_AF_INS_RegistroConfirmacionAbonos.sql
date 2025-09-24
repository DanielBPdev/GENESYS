CREATE OR ALTER TRIGGER [dbo].[TRG_AF_INS_RegistroConfirmacionAbonos]
ON [dbo].[RegistroConfirmacionAbonos]
AFTER INSERT AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @rcaId BIGINT  
	declare @tipoIdenAdminSubsidio varchar(50);
	declare  @numeroIdenAdminSubsidio varchar(50);
	declare @casId bigint;
	declare @tipoCuenta varchar(50);
	declare @valorTransferencia varchar(50);
	declare @numeroCuentaAdmon varchar(50);

	declare @val3 bit;
	declare @val4 bit;
	declare @val5 bit;
	declare @val6 bit;
	declare @val7 bit;
	declare @val8 bit;
	declare @val9 bit;
	declare @val10 bit = 0;

   	SELECT
		@rcaId = rcaId,
		@tipoIdenAdminSubsidio = rcaTipoIdentificacion,
		@numeroIdenAdminSubsidio = rcaNumeroIdentificacion,
		@casId = rcaCuentaAdministradorSubsidio,
		@tipoCuenta = rcaTipoCuentaAdminSubsidio,
		@valorTransferencia = rcaValorTransferencia,
		@numeroCuentaAdmon = rcaNumeroCuentaAdmon
	FROM INSERTED

	-- Administrador de subsidio existe
	select
		@val3 = case when count(per.perId) > 0 then 1 else 0 end
    from Persona per, AdministradorSubsidio adm
    where adm.asuPersona = per.perId
    and per.perTipoIdentificacion = @tipoIdenAdminSubsidio
    and per.perNumeroIdentificacion = @numeroIdenAdminSubsidio

	-- Identificador transacción en cuenta de admin subsidio monetario existe en genesys y/o está relacionado al administrador de subsidio
	select @val4 = case when count(per.perId) > 0 then 1 else 0 end
	FROM CuentaAdministradorSubsidio cas, AdministradorSubsidio adm, Persona per
	where cas.casAdministradorSubsidio = adm.asuId
	AND cas.casAdministradorSubsidio = adm.asuId
	AND adm.asuPersona = per.perId
	and per.perTipoIdentificacion = @tipoIdenAdminSubsidio
	and per.perNumeroIdentificacion = @numeroIdenAdminSubsidio
	and cas.casId = @casId

	-- El administrador de subsidio tiene relacionado un medio de pago igual a transferencia y en estado ENVIADO (para pasar a COBRADO)
	select
		@val5 = case when count(per.perId) > 0 then 1 else 0 end
	FROM CuentaAdministradorSubsidio cas, AdministradorSubsidio adm, Persona per
	where cas.casAdministradorSubsidio = adm.asuId
	AND cas.casAdministradorSubsidio = adm.asuId
	AND adm.asuPersona = per.perId
	and per.perTipoIdentificacion = @tipoIdenAdminSubsidio
	and per.perNumeroIdentificacion = @numeroIdenAdminSubsidio
	and cas.casId = @casId
	and cas.casMedioDePagoTransaccion = 'TRANSFERENCIA'
	and cas.casEstadoTransaccionSubsidio = 'ENVIADO'

	-- -- Titular de la cuenta está relacionado con el administrador de subsidio
	-- select
	-- 	@val6 = case when count(per.perId) > 0 then 1 else 0 end
	-- FROM CuentaAdministradorSubsidio cas, AdministradorSubsidio adm, Persona per
	-- where cas.casAdministradorSubsidio = adm.asuId
	-- AND cas.casAdministradorSubsidio = adm.asuId
	-- AND adm.asuPersona = per.perId
	-- and cas.casTipoIdentificacionTitularCuentaAdmonSubsidio = @tipoIdenAdminSubsidio
	-- and cas.casNumeroIdentificacionTitularCuentaAdmonSubsidio = @numeroIdenAdminSubsidio
	-- and cas.casId = @casId

	-- -- Titular de la cuenta es el mismo administrador de subsidio
	-- select
	-- 	@val7 = case when count(per.perId) > 0 then 1 else 0 end
	-- FROM CuentaAdministradorSubsidio cas, AdministradorSubsidio adm, Persona per
	-- where cas.casAdministradorSubsidio = adm.asuId
	-- AND cas.casAdministradorSubsidio = adm.asuId
	-- AND adm.asuPersona = per.perId
	-- and cas.casTipoIdentificacionTitularCuentaAdmonSubsidio = @tipoIdenAdminSubsidio
	-- and cas.casNumeroIdentificacionTitularCuentaAdmonSubsidio = @numeroIdenAdminSubsidio
	-- and cas.casId = @casId

	
	set @val6 = 1
	set @val7 = 1

	-- El valor en el campo tipo de cuenta es CORRECTO y/o está asociado al titular de la cuenta
	SELECT @val8 = CASE 
						WHEN COUNT(per.perId) > 0 AND (ISNULL(@tipoCuenta, '') = '' OR @tipoCuenta IN ('1', '2', '3')) THEN 1 
						ELSE 0 
					END
		FROM CuentaAdministradorSubsidio cas
		JOIN AdministradorSubsidio adm ON cas.casAdministradorSubsidio = adm.asuId
		JOIN Persona per ON adm.asuPersona = per.perId
		WHERE per.perTipoIdentificacion = @tipoIdenAdminSubsidio
		AND per.perNumeroIdentificacion = @numeroIdenAdminSubsidio
		AND cas.casId = @casId
		AND (cas.casTipoCuentaAdmonSubsidio = CASE 
						WHEN @tipoCuenta = '1' THEN 'AHORROS'
						WHEN @tipoCuenta = '2' THEN 'CORRIENTE'
						WHEN @tipoCuenta = '3' THEN 'DAVIPLATA'
						WHEN @tipoCuenta = '' THEN 'VALIDO'
						WHEN @tipoCuenta IS NULL THEN 'VALIDO'
						END OR @tipoCuenta IS NULL OR @tipoCuenta = '')

	-- Número de cuenta existe y/o está asociado al titular de la cuenta
	select @val9 = case when count(per.perId) > 0 then 1 else 0 end
	FROM CuentaAdministradorSubsidio cas, AdministradorSubsidio adm, Persona per
	where cas.casAdministradorSubsidio = adm.asuId
	AND cas.casAdministradorSubsidio = adm.asuId
	AND adm.asuPersona =	per.perId
	and per.perTipoIdentificacion = @tipoIdenAdminSubsidio
	and per.perNumeroIdentificacion = @numeroIdenAdminSubsidio
	and cas.casId = @casId
	and cas.casNumeroCuentaAdmonSubsidio = @numeroCuentaAdmon
	
	-- Se valida que el campo No Valor transferencia sea igual al “valor real de la transacción” del abono que existe en Genesys
	select @val10 = case when count(per.perId) > 0 then 1 else 0 end
    FROM CuentaAdministradorSubsidio cas, AdministradorSubsidio adm, Persona per
    where cas.casAdministradorSubsidio = adm.asuId
    AND cas.casAdministradorSubsidio = adm.asuId
    AND adm.asuPersona = per.perId
    and per.perTipoIdentificacion = @tipoIdenAdminSubsidio
    and per.perNumeroIdentificacion = @numeroIdenAdminSubsidio
    and cas.casId = @casId
    and cas.casValorRealTransaccion = @valorTransferencia

	update RegistroConfirmacionAbonos
	set rcaErrorValidacion3 = @val3,
		rcaErrorValidacion4 = @val4,
		rcaErrorValidacion5 = @val5,
		rcaErrorValidacion6 = @val6,
		rcaErrorValidacion7 = @val7,
		rcaErrorValidacion8 = @val8,
		rcaErrorValidacion9 = @val9,
		rcaErrorValidacion10 = @val10
	where rcaId = @rcaId
    
END
