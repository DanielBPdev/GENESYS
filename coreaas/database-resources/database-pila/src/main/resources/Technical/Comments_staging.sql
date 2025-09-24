--liquibase formatted sql

--changeset Heinsohn:Transaccion runOnChange:true failOnError:false
-- TABLA Transaccion
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa la información general de un aporte. Aarea de staging
 de la transaccional de PILA <br/>
 <b>Historia de Usuario: </b>211-395, 211-395, 211-396, 211-397, 211-398, 211-480 y proceso de aportes' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'Transaccion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa la información general de un aporte. Aarea de staging
 de la transaccional de PILA <br/>
 <b>Historia de Usuario: </b>211-395, 211-395, 211-396, 211-397, 211-398, 211-480 y proceso de aportes' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'Transaccion'
END CATCH;

--changeset Heinsohn:Transaccion.traId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Identificador de llave unica de una transacción en el staging de Pila.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'Transaccion'
,@level2type=N'COLUMN', @level2name=N'traId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Identificador de llave unica de una transacción en el staging de Pila.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'Transaccion'
,@level2type=N'COLUMN',@level2name=N'traId'
END CATCH;

--changeset Heinsohn:Transaccion.traFechaTransaccion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Fecha en la que se inicia la transacción.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'Transaccion'
,@level2type=N'COLUMN', @level2name=N'traFechaTransaccion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Fecha en la que se inicia la transacción.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'Transaccion'
,@level2type=N'COLUMN',@level2name=N'traFechaTransaccion'
END CATCH;



--changeset Heinsohn:RegistroGeneral runOnChange:true failOnError:false
-- TABLA RegistroGeneral
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa la información general de un aporte. Aarea de staging
 de la transaccional de PILA <br/>
 <b>Historia de Usuario: </b>211-395, 211-395, 211-396, 211-397, 211-398, 211-480 y proceso de aportes' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa la información general de un aporte. Aarea de staging
 de la transaccional de PILA <br/>
 <b>Historia de Usuario: </b>211-395, 211-395, 211-396, 211-397, 211-398, 211-480 y proceso de aportes' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria llamada No. de operación de recaudo general' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria llamada No. de operación de recaudo general' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regId'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regTransaccion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia al registro de transaccion que agrupa registros relacionados en staging' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regTransaccion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia al registro de transaccion que agrupa registros relacionados en staging' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regTransaccion'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regEsAportePensionados runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Marca de Referencia que indica si el registro de aporte es de un pensionado' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regEsAportePensionados'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Marca de Referencia que indica si el registro de aporte es de un pensionado' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regEsAportePensionados'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regNombreAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre o razon social del aportante' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regNombreAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Nombre o razon social del aportante' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regNombreAportante'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regTipoIdentificacionAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de identificacion del aportante' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regTipoIdentificacionAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de identificacion del aportante' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regTipoIdentificacionAportante'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regNumeroIdentificacionAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Número de identificación del aportante' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regNumeroIdentificacionAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Número de identificación del aportante' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regNumeroIdentificacionAportante'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regDigVerAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Digito de verificación del aportante' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regDigVerAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Digito de verificación del aportante' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regDigVerAportante'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regPeriodoAporte runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Período de pago del aporte' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regPeriodoAporte'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Período de pago del aporte' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regPeriodoAporte'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regTipoPlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el tipo de planilla indicada en <code>TipoPlanillaEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regTipoPlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el tipo de planilla indicada en <code>TipoPlanillaEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regTipoPlanilla'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regClaseAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la clase de aportante indicada en <code>ClaseAportanteEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regClaseAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la clase de aportante indicada en <code>ClaseAportanteEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regClaseAportante'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regCodSucursal runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el código de la sucursal o de la dependencia' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regCodSucursal'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el código de la sucursal o de la dependencia' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regCodSucursal'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regNomSucursal runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el nombre de la sucursal o de la dependencia' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regNomSucursal'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el nombre de la sucursal o de la dependencia' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regNomSucursal'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regCantPensionados runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la cantidad de pensionados reportados en planilla' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regCantPensionados'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la cantidad de pensionados reportados en planilla' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regCantPensionados'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regModalidadPlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la Modalidad de la Planilla indicada en <code>ModalidadPlanillaEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regModalidadPlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la Modalidad de la Planilla indicada en <code>ModalidadPlanillaEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regModalidadPlanilla'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regValTotalApoObligatorio runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el aporte obligatorio del aporte' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regValTotalApoObligatorio'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el aporte obligatorio del aporte' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regValTotalApoObligatorio'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regValorIntMora runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el valor interés de mora para un pensionado' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regValorIntMora'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el valor interés de mora para un pensionado' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regValorIntMora'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regFechaRecaudo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha de reacudo del aporte' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regFechaRecaudo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha de reacudo del aporte' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regFechaRecaudo'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regCodigoEntidadFinanciera runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Codigo de la entidad financiera recaudadora o receptora' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regCodigoEntidadFinanciera'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Codigo de la entidad financiera recaudadora o receptora' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regCodigoEntidadFinanciera'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regOperadorInformacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia al operador de informacion que se relaciona en el registro
 de la planilla integrada de liquidación de aportes' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regOperadorInformacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia al operador de informacion que se relaciona en el registro
 de la planilla integrada de liquidación de aportes' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regOperadorInformacion'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regNumeroCuenta runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el número de cuenta por la cual se hace recaudo del aporte' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regNumeroCuenta'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el número de cuenta por la cual se hace recaudo del aporte' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regNumeroCuenta'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regFechaActualizacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha de actualización del registro.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regFechaActualizacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha de actualización del registro.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regFechaActualizacion'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regRegistroControl runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia al registro de control asociado al registro general del aporte cargado
 por operador de informacion mediante proceso automatico en staging' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regRegistroControl'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia al registro de control asociado al registro general del aporte cargado
 por operador de informacion mediante proceso automatico en staging' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regRegistroControl'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regRegistroControlManual runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia al registro de control asociado al registro general del aporte cargado
 mediante proceso manual en staging' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regRegistroControlManual'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia al registro de control asociado al registro general del aporte cargado
 mediante proceso manual en staging' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regRegistroControlManual'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regRegistroFControl runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia al registro de control asociado al registro general del aporte
 del operador financiero cargado mediante proceso automatico en staging' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regRegistroFControl'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia al registro de control asociado al registro general del aporte
 del operador financiero cargado mediante proceso automatico en staging' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regRegistroFControl'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regOUTTarifaEmpleador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: tarifa del empleador' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regOUTTarifaEmpleador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: tarifa del empleador' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regOUTTarifaEmpleador'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regOUTFinalizadoProcesoManual runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: finalizo proceso manual? 1=[si] y 0=[no]' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regOUTFinalizadoProcesoManual'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: finalizo proceso manual? 1=[si] y 0=[no]' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regOUTFinalizadoProcesoManual'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regOUTEsEmpleador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: es Empleador? 1=[si] y 0=[no]' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regOUTEsEmpleador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: es Empleador? 1=[si] y 0=[no]' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regOUTEsEmpleador'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regOUTEstadoEmpleador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: estado del empleador/aportante' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regOUTEstadoEmpleador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: estado del empleador/aportante' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regOUTEstadoEmpleador'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regOUTTipoBeneficio runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: típo de beneficio' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regOUTTipoBeneficio'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: típo de beneficio' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regOUTTipoBeneficio'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regOUTBeneficioActivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: tiene beneficio activo? 1=[si] y 0=[no]' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regOUTBeneficioActivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: tiene beneficio activo? 1=[si] y 0=[no]' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regOUTBeneficioActivo'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regOUTEsEmpleadorReintegrable runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Empleador reintegrable? 1=[si] y 0=[no]' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regOUTEsEmpleadorReintegrable'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Empleador reintegrable? 1=[si] y 0=[no]' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regOUTEsEmpleadorReintegrable'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regOUTEstadoArchivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: estado de archivo de planilla' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regOUTEstadoArchivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: estado de archivo de planilla' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regOUTEstadoArchivo'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regNumPlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el número de planilla ingresada por PILA' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regNumPlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el número de planilla ingresada por PILA' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regNumPlanilla'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regEsSimulado runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro es una simulación de ejecución asistida' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regEsSimulado'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro es una simulación de ejecución asistida' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regEsSimulado'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regEstadoEvaluacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado del registro respecto a situación de corrección o ajuste' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regEstadoEvaluacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado del registro respecto a situación de corrección o ajuste' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regEstadoEvaluacion'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regOUTMarcaSucursalPILA runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica que el aportante requiere que las sucursales de PILA tengan prioridad' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regOUTMarcaSucursalPILA'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica que el aportante requiere que las sucursales de PILA tengan prioridad' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regOUTMarcaSucursalPILA'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regOUTCodSucursalPrincipal runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el código de la sucursal principal del aportante en BD transaccional' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regOUTCodSucursalPrincipal'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el código de la sucursal principal del aportante en BD transaccional' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regOUTCodSucursalPrincipal'
END CATCH;

--changeset Heinsohn:RegistroGeneral.regOUTNomSucursalPrincipal runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el nombre de la sucursal principal del aportante en BD transaccional' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN', @level2name=N'regOUTNomSucursalPrincipal'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el nombre de la sucursal principal del aportante en BD transaccional' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroGeneral'
,@level2type=N'COLUMN',@level2name=N'regOUTNomSucursalPrincipal'
END CATCH;



--changeset Heinsohn:RegistroDetallado runOnChange:true failOnError:false
-- TABLA RegistroDetallado
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa la información detallada de un aporte Aarea de staging
 de la transaccional de PILA <br/>
 <b>Historia de Usuario: </b>211-395, 211-395, 211-396, 211-397, 211-398, 211-480 y proceso de aportes' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa la información detallada de un aporte Aarea de staging
 de la transaccional de PILA <br/>
 <b>Historia de Usuario: </b>211-395, 211-395, 211-396, 211-397, 211-398, 211-480 y proceso de aportes' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria llamada No. de operación de recaudo' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria llamada No. de operación de recaudo' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redId'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redRegistroGeneral runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia al registro general del aporte (staging)' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redRegistroGeneral'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia al registro general del aporte (staging)' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redRegistroGeneral'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redTipoIdentificacionCotizante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el tipo identificacio?n del cotizante. indicado en <code>TipoIdentificacionEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redTipoIdentificacionCotizante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el tipo identificacio?n del cotizante. indicado en <code>TipoIdentificacionEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redTipoIdentificacionCotizante'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redNumeroIdentificacionCotizante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el Número de identificacio?n del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redNumeroIdentificacionCotizante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el Número de identificacio?n del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redNumeroIdentificacionCotizante'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redTipoCotizante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el tipo de cotizante indicado en <code>TipoAfiliadoEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redTipoCotizante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el tipo de cotizante indicado en <code>TipoAfiliadoEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redTipoCotizante'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redCodDepartamento runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el co?digo del Departamento de la ubicacio?n laboral.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redCodDepartamento'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el co?digo del Departamento de la ubicacio?n laboral.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redCodDepartamento'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redCodMunicipio runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el co?digo del Municipio de la ubicacio?n laboral.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redCodMunicipio'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el co?digo del Municipio de la ubicacio?n laboral.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redCodMunicipio'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redPrimerApellido runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el primer apellido del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redPrimerApellido'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el primer apellido del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redPrimerApellido'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redSegundoApellido runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el segundo apellido del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redSegundoApellido'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el segundo apellido del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redSegundoApellido'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redPrimerNombre runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el primer nombre del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redPrimerNombre'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el primer nombre del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redPrimerNombre'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redSegundoNombre runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el segundo nombre del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redSegundoNombre'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el segundo nombre del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redSegundoNombre'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redNovIngreso runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad ING: Ingreso.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redNovIngreso'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad ING: Ingreso.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redNovIngreso'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redNovRetiro runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad RET: Retiro.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redNovRetiro'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad RET: Retiro.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redNovRetiro'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redNovVSP runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad VSP: Variacio?n permanente de salario.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redNovVSP'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad VSP: Variacio?n permanente de salario.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redNovVSP'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redNovVST runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad VST: Variacio?n transitoria del salario.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redNovVST'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad VST: Variacio?n transitoria del salario.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redNovVST'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redNovSLN runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad SLN: Suspensio?n temporal
 del contrato de trabajo, licencia no remunerada o comisio?n de servicios.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redNovSLN'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad SLN: Suspensio?n temporal
 del contrato de trabajo, licencia no remunerada o comisio?n de servicios.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redNovSLN'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redNovIGE runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad IGE: Incapacidad Temporal
 por Enfermedad General.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redNovIGE'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad IGE: Incapacidad Temporal
 por Enfermedad General.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redNovIGE'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redNovLMA runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad LMA: Licencia de Maternidad o paternidad.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redNovLMA'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad LMA: Licencia de Maternidad o paternidad.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redNovLMA'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redNovVACLR runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad VAC - LR: Vacaciones, Licencia remunerada' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redNovVACLR'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad VAC - LR: Vacaciones, Licencia remunerada' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redNovVACLR'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redNovSUS runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad SUS: Suspensión (pensionados)' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redNovSUS'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica que el registro de aporte relaciona novedad SUS: Suspensión (pensionados)' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redNovSUS'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redDiasIRL runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 23: IRL: días de incapacidad por accidente de
 trabajo o enfermedad laboral' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redDiasIRL'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 23: IRL: días de incapacidad por accidente de
 trabajo o enfermedad laboral' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redDiasIRL'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redDiasCotizados runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica los di?as cotizados.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redDiasCotizados'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica los di?as cotizados.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redDiasCotizados'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redSalarioBasico runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el valor del Salario ba?sico.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redSalarioBasico'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el valor del Salario ba?sico.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redSalarioBasico'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redValorIBC runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el valor de Ingreso Base Cotizacio?n (IBC)' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redValorIBC'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el valor de Ingreso Base Cotizacio?n (IBC)' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redValorIBC'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redTarifa runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la Tarifa.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redTarifa'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la Tarifa.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redTarifa'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redAporteObligatorio runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el valor de Aporte obligatorio.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redAporteObligatorio'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el valor de Aporte obligatorio.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redAporteObligatorio'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redCorrecciones runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica que tipo de Correccion es el registro de aporte' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redCorrecciones'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica que tipo de Correccion es el registro de aporte' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redCorrecciones'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redSalarioIntegral runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el valor del Salario Integral' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redSalarioIntegral'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el valor del Salario Integral' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redSalarioIntegral'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaIngreso runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha de novedad ingreso' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaIngreso'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha de novedad ingreso' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaIngreso'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaRetiro runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha de novedad retiro' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaRetiro'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha de novedad retiro' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaRetiro'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaInicioVSP runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha inicio novedad VSP' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaInicioVSP'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha inicio novedad VSP' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaInicioVSP'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaInicioSLN runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha inicio novedad SLN' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaInicioSLN'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha inicio novedad SLN' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaInicioSLN'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaFinSLN runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha fin novedad SLN' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaFinSLN'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha fin novedad SLN' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaFinSLN'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaInicioIGE runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha inicio novedad IGE' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaInicioIGE'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha inicio novedad IGE' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaInicioIGE'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaFinIGE runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha fin novedad IGE' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaFinIGE'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha fin novedad IGE' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaFinIGE'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaInicioLMA runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha inicio novedad LMA' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaInicioLMA'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha inicio novedad LMA' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaInicioLMA'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaFinLMA runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha fin novedad LMA' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaFinLMA'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha fin novedad LMA' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaFinLMA'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaInicioVACLR runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha inicio novedad VAC - LR' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaInicioVACLR'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha inicio novedad VAC - LR' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaInicioVACLR'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaFinVACLR runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha fin novedad VAC - LR' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaFinVACLR'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha fin novedad VAC - LR' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaFinVACLR'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaInicioVCT runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha inicio novedad VCT' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaInicioVCT'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha inicio novedad VCT' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaInicioVCT'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaFinVCT runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha fin novedad VCT' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaFinVCT'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha fin novedad VCT' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaFinVCT'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaInicioIRL runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha inicio novedad IRL' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaInicioIRL'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha inicio novedad IRL' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaInicioIRL'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaFinIRL runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha fin novedad IRL' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaFinIRL'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha fin novedad IRL' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaFinIRL'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaInicioSuspension runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha inicio novedad SLN' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaInicioSuspension'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha inicio novedad SLN' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaInicioSuspension'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redFechaFinSuspension runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha fin novedad SLN' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redFechaFinSuspension'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha fin novedad SLN' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redFechaFinSuspension'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redHorasLaboradas runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el número de horas laboradas' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redHorasLaboradas'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el número de horas laboradas' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redHorasLaboradas'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redRegistroControl runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia al registro de control asociado al registro general del aporte cargado
 por operador de informacion mediante proceso automatico en staging' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redRegistroControl'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia al registro de control asociado al registro general del aporte cargado
 por operador de informacion mediante proceso automatico en staging' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redRegistroControl'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redUsuarioAprobadorAporte runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el usuario que aprueba el registro del aporte.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redUsuarioAprobadorAporte'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el usuario que aprueba el registro del aporte.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redUsuarioAprobadorAporte'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redNumeroOperacionAprobacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el número de operación de aprobacion' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redNumeroOperacionAprobacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el número de operación de aprobacion' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redNumeroOperacionAprobacion'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redEstadoEvaluacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado del registro respecto a situación de corrección o ajuste' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redEstadoEvaluacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado del registro respecto a situación de corrección o ajuste' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redEstadoEvaluacion'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redEstadoRegistroCorreccion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado de evaluación de registro de planilla de corrección' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redEstadoRegistroCorreccion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado de evaluación de registro de planilla de corrección' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redEstadoRegistroCorreccion'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redOUTMarcaValRegistroAporte runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: marca de validacion del
 registro del aporte en el procesamiento del archivo' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redOUTMarcaValRegistroAporte'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: marca de validacion del
 registro del aporte en el procesamiento del archivo' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redOUTMarcaValRegistroAporte'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redOUTEstadoRegistroAporte runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Estado del registro
 del aporte en el procesamiento del archivo' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redOUTEstadoRegistroAporte'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Estado del registro
 del aporte en el procesamiento del archivo' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redOUTEstadoRegistroAporte'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redOUTAnalisisIntegral runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Identifica si el
 registro de aporte es marcado por analisis integral' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redOUTAnalisisIntegral'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Identifica si el
 registro de aporte es marcado por analisis integral' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redOUTAnalisisIntegral'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redOUTFechaProcesamientoValidRegAporte runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Fecha de procesamiento
 del registro del aporte (al momento de aplicar las validaciones
 en el proceso)' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redOUTFechaProcesamientoValidRegAporte'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Fecha de procesamiento
 del registro del aporte (al momento de aplicar las validaciones
 en el proceso)' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redOUTFechaProcesamientoValidRegAporte'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redOUTEstadoValidacionV0 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Estado en el
 procesamiento de validacion de Clase de Aportante (V0),
 para el registro del archivo PILA indicado en <code>EstadoValidacionRegistroAporteEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redOUTEstadoValidacionV0'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Estado en el
 procesamiento de validacion de Clase de Aportante (V0),
 para el registro del archivo PILA indicado en <code>EstadoValidacionRegistroAporteEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redOUTEstadoValidacionV0'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redOUTEstadoValidacionV1 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Estado en el procesamiento
 de validacion de la tarifa (V1), para el registro del aporte de
 archivo PILA indicado en <code>EstadoValidacionRegistroAporteEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redOUTEstadoValidacionV1'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Estado en el procesamiento
 de validacion de la tarifa (V1), para el registro del aporte de
 archivo PILA indicado en <code>EstadoValidacionRegistroAporteEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redOUTEstadoValidacionV1'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redOUTEstadoValidacionV2 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Estado en el procesamiento
 de validacion del tipo de cotizante (V2), para el registro del
 aporte de archivo PILA indicado en <code>EstadoValidacionRegistroAporteEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redOUTEstadoValidacionV2'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Estado en el procesamiento
 de validacion del tipo de cotizante (V2), para el registro del
 aporte de archivo PILA indicado en <code>EstadoValidacionRegistroAporteEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redOUTEstadoValidacionV2'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redOUTEstadoValidacionV3 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Estado en el procesamiento
 de validacion de los dias cotizados (V3), para el registro del
 aporte de archivo PILA indicado en <code>EstadoValidacionRegistroAporteEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redOUTEstadoValidacionV3'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Estado en el procesamiento
 de validacion de los dias cotizados (V3), para el registro del
 aporte de archivo PILA indicado en <code>EstadoValidacionRegistroAporteEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redOUTEstadoValidacionV3'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redOUTClaseTrabajador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: clase del trabajador indicada en <code>ClaseTrabajadorEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redOUTClaseTrabajador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: clase del trabajador indicada en <code>ClaseTrabajadorEnum</code>' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redOUTClaseTrabajador'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redOUTPorcentajePagoAportes runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Porcentaje de Pago de Aportes' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redOUTPorcentajePagoAportes'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: Porcentaje de Pago de Aportes' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redOUTPorcentajePagoAportes'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redOUTEstadoSolicitante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: el estado del solicitante' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redOUTEstadoSolicitante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: el estado del solicitante' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redOUTEstadoSolicitante'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redOUTEsTrabajadorReintegrable runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: es trabajador reintegrable Si=[1], No=[0]' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redOUTEsTrabajadorReintegrable'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el campo de salida del proceso: es trabajador reintegrable Si=[1], No=[0]' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redOUTEsTrabajadorReintegrable'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redOUTFechaIngresoCotizante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha de ingreso del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redOUTFechaIngresoCotizante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha de ingreso del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redOUTFechaIngresoCotizante'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redOUTFechaUltimaNovedad runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha de la ultima novedad del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redOUTFechaUltimaNovedad'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica la fecha de la ultima novedad del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redOUTFechaUltimaNovedad'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redOUTCodSucursal runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el código de la sucursal del cotizante en BD transaccional' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redOUTCodSucursal'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el código de la sucursal del cotizante en BD transaccional' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redOUTCodSucursal'
END CATCH;

--changeset Heinsohn:RegistroDetallado.redOUTNomSucursal runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indica el nombre de la sucursal del cotizante en BD transaccional' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN', @level2name=N'redOUTNomSucursal'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indica el nombre de la sucursal del cotizante en BD transaccional' ,
@level0type=N'SCHEMA', @level0name=N'staging', @level1type=N'TABLE', @level1name=N'RegistroDetallado'
,@level2type=N'COLUMN',@level2name=N'redOUTNomSucursal'
END CATCH;

