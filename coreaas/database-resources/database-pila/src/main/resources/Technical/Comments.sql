--liquibase formatted sql

--changeset Heinsohn:PilaSolicitudCambioNumIdentAportante runOnChange:true failOnError:false
-- TABLA PilaSolicitudCambioNumIdentAportante
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa al índice de seguimiento de planillas PILA <br/>
 <b>Historia de Usuario: </b>211-410' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa al índice de seguimiento de planillas PILA <br/>
 <b>Historia de Usuario: </b>211-410' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
END CATCH;

--changeset Heinsohn:PilaSolicitudCambioNumIdentAportante.pscId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria de la entrada de una referencia de
 corrección de planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN', @level2name=N'pscId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria de la entrada de una referencia de
 corrección de planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN',@level2name=N'pscId'
END CATCH;

--changeset Heinsohn:PilaSolicitudCambioNumIdentAportante.pscAccion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción de la acción realizada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN', @level2name=N'pscAccion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción de la acción realizada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN',@level2name=N'pscAccion'
END CATCH;

--changeset Heinsohn:PilaSolicitudCambioNumIdentAportante.pscEstadoArchivoAfectado runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del estado de procesamiento de la corrección' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN', @level2name=N'pscEstadoArchivoAfectado'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del estado de procesamiento de la corrección' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN',@level2name=N'pscEstadoArchivoAfectado'
END CATCH;

--changeset Heinsohn:PilaSolicitudCambioNumIdentAportante.pscPilaIndicePlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código de la entrada de índice de planilla OI al cual se relaciona la
 inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN', @level2name=N'pscPilaIndicePlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código de la entrada de índice de planilla OI al cual se relaciona la
 inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN',@level2name=N'pscPilaIndicePlanilla'
END CATCH;

--changeset Heinsohn:PilaSolicitudCambioNumIdentAportante.pscFechaSolicitud runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Fecha en la cual se envia a la bandeja de solicitud de cambios' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN', @level2name=N'pscFechaSolicitud'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Fecha en la cual se envia a la bandeja de solicitud de cambios' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN',@level2name=N'pscFechaSolicitud'
END CATCH;

--changeset Heinsohn:PilaSolicitudCambioNumIdentAportante.pscFechaRespuesta runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Fecha en la cual se envia a la bandeja de solicitud de cambios' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN', @level2name=N'pscFechaRespuesta'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Fecha en la cual se envia a la bandeja de solicitud de cambios' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN',@level2name=N'pscFechaRespuesta'
END CATCH;

--changeset Heinsohn:PilaSolicitudCambioNumIdentAportante.pscNumeroIdentificacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'numero de identificacion al que se desea actualizar las planillas' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN', @level2name=N'pscNumeroIdentificacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'numero de identificacion al que se desea actualizar las planillas' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN',@level2name=N'pscNumeroIdentificacion'
END CATCH;

--changeset Heinsohn:PilaSolicitudCambioNumIdentAportante.pscArchivosCorreccion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Cadena con los nombres de los archivos asociados a el archivo que envio
 la solicitud de cambio de identificacion' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN', @level2name=N'pscArchivosCorreccion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Cadena con los nombres de los archivos asociados a el archivo que envio
 la solicitud de cambio de identificacion' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN',@level2name=N'pscArchivosCorreccion'
END CATCH;

--changeset Heinsohn:PilaSolicitudCambioNumIdentAportante.pscUsuarioAprobador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Cadena con los nombres de los archivos asociados a el archivo que envio
 la solicitud de cambio de identificacion' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN', @level2name=N'pscUsuarioAprobador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Cadena con los nombres de los archivos asociados a el archivo que envio
 la solicitud de cambio de identificacion' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN',@level2name=N'pscUsuarioAprobador'
END CATCH;

--changeset Heinsohn:PilaSolicitudCambioNumIdentAportante.pscUsuario runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Cadena con los nombres de los archivos asociados a el archivo que envio
 la solicitud de cambio de identificacion' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN', @level2name=N'pscUsuario'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Cadena con los nombres de los archivos asociados a el archivo que envio
 la solicitud de cambio de identificacion' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN',@level2name=N'pscUsuario'
END CATCH;

--changeset Heinsohn:PilaSolicitudCambioNumIdentAportante.pscIdPlanillaInformacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'identificador del registro que se esta tratando en la solicitud de cambio
 de identificacion' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN', @level2name=N'pscIdPlanillaInformacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'identificador del registro que se esta tratando en la solicitud de cambio
 de identificacion' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN',@level2name=N'pscIdPlanillaInformacion'
END CATCH;

--changeset Heinsohn:PilaSolicitudCambioNumIdentAportante.pscIdPlanillaFinanciera runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'identificador del registro financiero que se esta tratando en la
 solicitud de cambio de identificacion' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN', @level2name=N'pscIdPlanillaFinanciera'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'identificador del registro financiero que se esta tratando en la
 solicitud de cambio de identificacion' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN',@level2name=N'pscIdPlanillaFinanciera'
END CATCH;

--changeset Heinsohn:PilaSolicitudCambioNumIdentAportante.pscErrorValidacionLog  runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN', @level2name=N'pscErrorValidacionLog '
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN',@level2name=N'pscErrorValidacionLog '
END CATCH;

--changeset Heinsohn:PilaSolicitudCambioNumIdentAportante.pscRazonRechazo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN', @level2name=N'pscRazonRechazo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN',@level2name=N'pscRazonRechazo'
END CATCH;

--changeset Heinsohn:PilaSolicitudCambioNumIdentAportante.pscComentarios runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN', @level2name=N'pscComentarios'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaSolicitudCambioNumIdentAportante'
,@level2type=N'COLUMN',@level2name=N'pscComentarios'
END CATCH;



--changeset Heinsohn:PilaIndicePlanillaOF runOnChange:true failOnError:false
-- TABLA PilaIndicePlanillaOF
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa al índice de archivos de operador financiero <br/>
 <b>Historia de Usuario: </b> 211-407' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa al índice de archivos de operador financiero <br/>
 <b>Historia de Usuario: </b> 211-407' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria de la entrada de índice de planilla de Operador Financiero' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria de la entrada de índice de planilla de Operador Financiero' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioId'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioFechaPago runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Fecha de pago de los registros incluidos en la planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioFechaPago'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Fecha de pago de los registros incluidos en la planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioFechaPago'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioNombreArchivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre del archivo cargado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioNombreArchivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Nombre del archivo cargado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioNombreArchivo'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioFechaRecibo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora de carga del archivo al índice de planillas de Operador Financiero' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioFechaRecibo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora de carga del archivo al índice de planillas de Operador Financiero' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioFechaRecibo'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioFechaFtp runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora de ultima modificación del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioFechaFtp'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora de ultima modificación del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioFechaFtp'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioCodigoAdministradora runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código de la adminidtradora (Caja de Compensación) para la cual se hace el recaudo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioCodigoAdministradora'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código de la adminidtradora (Caja de Compensación) para la cual se hace el recaudo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioCodigoAdministradora'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioCodigoBancoRecaudador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código del banco por el cual se hace el recaudo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioCodigoBancoRecaudador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código del banco por el cual se hace el recaudo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioCodigoBancoRecaudador'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioTipoArchivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioTipoArchivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioTipoArchivo'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioTipoCargaArchivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Decripción del tipo de carga del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioTipoCargaArchivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Decripción del tipo de carga del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioTipoCargaArchivo'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioUsuario runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del usuario que realiza la carga' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioUsuario'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del usuario que realiza la carga' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioUsuario'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioEstadoArchivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del estado actual del proceso del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioEstadoArchivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del estado actual del proceso del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioEstadoArchivo'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioIdentificadorDocumento runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Identificador del archivo almacenado en el repositorio ECM' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioIdentificadorDocumento'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Identificador del archivo almacenado en el repositorio ECM' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioIdentificadorDocumento'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioVersionDocumento runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Versión del documento en el repositorio ECM' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioVersionDocumento'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Versión del documento en el repositorio ECM' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioVersionDocumento'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioFechaProceso runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora en la que inicia el procesamiento del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioFechaProceso'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora en la que inicia el procesamiento del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioFechaProceso'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioUsuarioProceso runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del usuario que inicia el procesamiento del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioUsuarioProceso'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del usuario que inicia el procesamiento del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioUsuarioProceso'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioFechaEliminacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora en la que se solicita la eliminación del archivo en el caso de ser 
 válido para su procesamiento' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioFechaEliminacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora en la que se solicita la eliminación del archivo en el caso de ser 
 válido para su procesamiento' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioFechaEliminacion'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioUsuarioEliminacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del usuario que solicita la eliminación del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioUsuarioEliminacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del usuario que solicita la eliminación del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioUsuarioEliminacion'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioRegistroActivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indicador del estado activo del registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioRegistroActivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indicador del estado activo del registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioRegistroActivo'
END CATCH;

--changeset Heinsohn:PilaIndicePlanillaOF.pioTamanoArchivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Tamaño del archivo en Bytes' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN', @level2name=N'pioTamanoArchivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Tamaño del archivo en Bytes' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanillaOF'
,@level2type=N'COLUMN',@level2name=N'pioTamanoArchivo'
END CATCH;



--changeset Heinsohn:PilaIndicePlanilla runOnChange:true failOnError:false
-- TABLA PilaIndicePlanilla
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa al índice de seguimiento de planillas PILA <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa al índice de seguimiento de planillas PILA <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria de la entrada de índice de planilla de Operador de Información' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria de la entrada de índice de planilla de Operador de Información' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipId'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipIdPlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Número de la planilla cargada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipIdPlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Número de la planilla cargada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipIdPlanilla'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipTipoArchivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipTipoArchivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipTipoArchivo'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipNombreArchivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre del archivo cargado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipNombreArchivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Nombre del archivo cargado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipNombreArchivo'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipFechaRecibo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora de carga del archivo al índice de planillas' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipFechaRecibo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora de carga del archivo al índice de planillas' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipFechaRecibo'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipFechaFtp runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora de ultima modificación del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipFechaFtp'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora de ultima modificación del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipFechaFtp'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipCodigoOperadorInformacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del código del Operador de Información de quien se recibe el archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipCodigoOperadorInformacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del código del Operador de Información de quien se recibe el archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipCodigoOperadorInformacion'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipEstadoArchivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del estado actual del proceso del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipEstadoArchivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del estado actual del proceso del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipEstadoArchivo'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipTipoCargaArchivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Decripción del tipo de carga del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipTipoCargaArchivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Decripción del tipo de carga del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipTipoCargaArchivo'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipUsuario runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del usuario que realiza la carga' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipUsuario'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del usuario que realiza la carga' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipUsuario'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipIdentificadorDocumento runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Identificador del archivo almacenado en el repositorio ECM' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipIdentificadorDocumento'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Identificador del archivo almacenado en el repositorio ECM' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipIdentificadorDocumento'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipVersionDocumento runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Versión del documento en el repositorio ECM' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipVersionDocumento'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Versión del documento en el repositorio ECM' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipVersionDocumento'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipFechaProceso runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora en la que inicia el procesamiento del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipFechaProceso'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora en la que inicia el procesamiento del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipFechaProceso'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipUsuarioProceso runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del usuario que inicia el procesamiento del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipUsuarioProceso'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del usuario que inicia el procesamiento del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipUsuarioProceso'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipFechaEliminacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora en la que se solicita la eliminación o anulación del archivo en el caso de no ser válido para su procesamiento' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipFechaEliminacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora en la que se solicita la eliminación o anulación del archivo en el caso de no ser válido para su procesamiento' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipFechaEliminacion'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipUsuarioEliminacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del usuario que solicita la eliminación o anulación del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipUsuarioEliminacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del usuario que solicita la eliminación o anulación del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipUsuarioEliminacion'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipProcesar runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indicador que determina sí el archivo se puede procesar' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipProcesar'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indicador que determina sí el archivo se puede procesar' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipProcesar'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipRegistroActivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Indicador del estado activo del registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipRegistroActivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Indicador del estado activo del registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipRegistroActivo'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipEnLista runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Identificador que determina que el registro se encuentra incluido en una lista de procesamiento' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipEnLista'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Identificador que determina que el registro se encuentra incluido en una lista de procesamiento' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipEnLista'
END CATCH;

--changeset Heinsohn:PilaIndicePlanilla.pipTamanoArchivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Tamaño del archivo en Bytes' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN', @level2name=N'pipTamanoArchivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Tamaño del archivo en Bytes' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaIndicePlanilla'
,@level2type=N'COLUMN',@level2name=N'pipTamanoArchivo'
END CATCH;



--changeset Heinsohn:PilaEstadoBloqueOF runOnChange:true failOnError:false
-- TABLA PilaEstadoBloqueOF
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa la tabla de estados por bloque de los archivos de operador
 financiero <br/>
 <b>Historia de Usuario: </b>211-407' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa la tabla de estados por bloque de los archivos de operador
 financiero <br/>
 <b>Historia de Usuario: </b>211-407' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
END CATCH;

--changeset Heinsohn:PilaEstadoBloqueOF.peoId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria de la entrada de estados por bloque para
 archivo de Operador Financiero' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
,@level2type=N'COLUMN', @level2name=N'peoId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria de la entrada de estados por bloque para
 archivo de Operador Financiero' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
,@level2type=N'COLUMN',@level2name=N'peoId'
END CATCH;

--changeset Heinsohn:PilaEstadoBloqueOF.peoIndicePlanillaOF runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada de índice de planillas de Operador Financiero' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
,@level2type=N'COLUMN', @level2name=N'peoIndicePlanillaOF'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada de índice de planillas de Operador Financiero' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
,@level2type=N'COLUMN',@level2name=N'peoIndicePlanillaOF'
END CATCH;

--changeset Heinsohn:PilaEstadoBloqueOF.peoEstadoBloque0 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 0 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
,@level2type=N'COLUMN', @level2name=N'peoEstadoBloque0'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 0 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
,@level2type=N'COLUMN',@level2name=N'peoEstadoBloque0'
END CATCH;

--changeset Heinsohn:PilaEstadoBloqueOF.peoAccionBloque0 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 0 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
,@level2type=N'COLUMN', @level2name=N'peoAccionBloque0'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 0 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
,@level2type=N'COLUMN',@level2name=N'peoAccionBloque0'
END CATCH;

--changeset Heinsohn:PilaEstadoBloqueOF.peoEstadoBloque1 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 1 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
,@level2type=N'COLUMN', @level2name=N'peoEstadoBloque1'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 1 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
,@level2type=N'COLUMN',@level2name=N'peoEstadoBloque1'
END CATCH;

--changeset Heinsohn:PilaEstadoBloqueOF.peoAccionBloque1 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 1 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
,@level2type=N'COLUMN', @level2name=N'peoAccionBloque1'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 1 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
,@level2type=N'COLUMN',@level2name=N'peoAccionBloque1'
END CATCH;

--changeset Heinsohn:PilaEstadoBloqueOF.peoEstadoBloque6 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 6 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
,@level2type=N'COLUMN', @level2name=N'peoEstadoBloque6'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 6 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
,@level2type=N'COLUMN',@level2name=N'peoEstadoBloque6'
END CATCH;

--changeset Heinsohn:PilaEstadoBloqueOF.peoAccionBloque6 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 6 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
,@level2type=N'COLUMN', @level2name=N'peoAccionBloque6'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 6 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloqueOF'
,@level2type=N'COLUMN',@level2name=N'peoAccionBloque6'
END CATCH;



--changeset Heinsohn:PilaEstadoBloque runOnChange:true failOnError:false
-- TABLA PilaEstadoBloque
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa al seguimiento del estado de validación por bloque de 
 un archivo <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa al seguimiento del estado de validación por bloque de 
 un archivo <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria de la entrada de estados por bloque para 
 archivo de Operador de Información' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria de la entrada de estados por bloque para 
 archivo de Operador de Información' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebId'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebIndicePlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada de índice de planillas de Operador de Información' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebIndicePlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada de índice de planillas de Operador de Información' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebIndicePlanilla'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebTipoArchivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de archivo referenciado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebTipoArchivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de archivo referenciado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebTipoArchivo'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebEstadoBloque0 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 0 de validación - 
 Validación de Carpeta, tamaño y combinatoria de archivos' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebEstadoBloque0'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 0 de validación - 
 Validación de Carpeta, tamaño y combinatoria de archivos' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebEstadoBloque0'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebAccionBloque0 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 0 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebAccionBloque0'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 0 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebAccionBloque0'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebEstadoBloque1 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 1 de validación - 
 Validación de estructura del nombre del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebEstadoBloque1'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 1 de validación - 
 Validación de estructura del nombre del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebEstadoBloque1'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebAccionBloque1 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 1 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebAccionBloque1'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 1 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebAccionBloque1'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebEstadoBloque2 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 2 de validación - 
 Validación de estructura del contenido del archivo y reglas de normatividad' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebEstadoBloque2'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 2 de validación - 
 Validación de estructura del contenido del archivo y reglas de normatividad' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebEstadoBloque2'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebAccionBloque2 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 2 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebAccionBloque2'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 2 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebAccionBloque2'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebEstadoBloque3 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 3 de validación - 
 Validación combinada del contenido de pareja de archivos' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebEstadoBloque3'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 3 de validación - 
 Validación combinada del contenido de pareja de archivos' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebEstadoBloque3'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebAccionBloque3 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 3 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebAccionBloque3'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 3 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebAccionBloque3'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebEstadoBloque4 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 4 de validación - 
 Validación individual de consistencia de archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebEstadoBloque4'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 4 de validación - 
 Validación individual de consistencia de archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebEstadoBloque4'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebAccionBloque4 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 4 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebAccionBloque4'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 4 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebAccionBloque4'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebEstadoBloque5 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 5 de validación - 
 Validación de existencia del aportante en BD' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebEstadoBloque5'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 5 de validación - 
 Validación de existencia del aportante en BD' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebEstadoBloque5'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebAccionBloque5 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 5 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebAccionBloque5'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 5 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebAccionBloque5'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebEstadoBloque6 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 6 de validación - 
 Validación de conciliación de archivo de OI con archivo de OF' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebEstadoBloque6'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 6 de validación - 
 Validación de conciliación de archivo de OI con archivo de OF' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebEstadoBloque6'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebAccionBloque6 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 6 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebAccionBloque6'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 6 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebAccionBloque6'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebEstadoBloque7 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 7 de validación - 
 Fase 1 de PILA 2: Validación de información vs BD' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebEstadoBloque7'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 7 de validación - 
 Fase 1 de PILA 2: Validación de información vs BD' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebEstadoBloque7'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebAccionBloque7 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 7 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebAccionBloque7'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 7 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebAccionBloque7'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebEstadoBloque8 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 8 de validación - 
 Fase 2 de PILA 2: Registro o Relación del aporte' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebEstadoBloque8'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 8 de validación - 
 Fase 2 de PILA 2: Registro o Relación del aporte' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebEstadoBloque8'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebAccionBloque8 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 8 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebAccionBloque8'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 8 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebAccionBloque8'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebEstadoBloque9 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 9 de validación - 
 Fase 3 de PILA 2: Registro o Relación de las novedades del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebEstadoBloque9'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 9 de validación - 
 Fase 3 de PILA 2: Registro o Relación de las novedades del archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebEstadoBloque9'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebAccionBloque9 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 9 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebAccionBloque9'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 9 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebAccionBloque9'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebEstadoBloque10 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 10 de validación - 
 Notificación de los resultados del proceso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebEstadoBloque10'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado del proceso del archivo para el bloque 10 de validación - 
 Notificación de los resultados del proceso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebEstadoBloque10'
END CATCH;

--changeset Heinsohn:PilaEstadoBloque.pebAccionBloque10 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 10 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN', @level2name=N'pebAccionBloque10'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Acción a ejecutar al terminar el bloque 10 de validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaEstadoBloque'
,@level2type=N'COLUMN',@level2name=N'pebAccionBloque10'
END CATCH;



--changeset Heinsohn:PilaErrorValidacionLog runOnChange:true failOnError:false
-- TABLA PilaErrorValidacionLog
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'<b>Descripción:</b> Entidad que representa al log de errores e inconsistencias en validación
 de acuerdo a resolución
 <b>Historia de Usuario: </b> HU-211-391, HU-211-407, HU-211-393' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'<b>Descripción:</b> Entidad que representa al log de errores e inconsistencias en validación
 de acuerdo a resolución
 <b>Historia de Usuario: </b> HU-211-391, HU-211-407, HU-211-393' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
END CATCH;

--changeset Heinsohn:PilaErrorValidacionLog.pevId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria para la entrada de inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN', @level2name=N'pevId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria para la entrada de inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN',@level2name=N'pevId'
END CATCH;

--changeset Heinsohn:PilaErrorValidacionLog.pevIndicePlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código de la entrada de índice de planilla OI al cual se relaciona la inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN', @level2name=N'pevIndicePlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código de la entrada de índice de planilla OI al cual se relaciona la inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN',@level2name=N'pevIndicePlanilla'
END CATCH;

--changeset Heinsohn:PilaErrorValidacionLog.pevIndicePlanillaOF runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código de la entrada de índice de planilla OF al cual se relaciona la inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN', @level2name=N'pevIndicePlanillaOF'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código de la entrada de índice de planilla OF al cual se relaciona la inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN',@level2name=N'pevIndicePlanillaOF'
END CATCH;

--changeset Heinsohn:PilaErrorValidacionLog.pevTipoArchivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de archivo al cual hace referencia la inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN', @level2name=N'pevTipoArchivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de archivo al cual hace referencia la inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN',@level2name=N'pevTipoArchivo'
END CATCH;

--changeset Heinsohn:PilaErrorValidacionLog.pevTipoError runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de error presentado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN', @level2name=N'pevTipoError'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de error presentado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN',@level2name=N'pevTipoError'
END CATCH;

--changeset Heinsohn:PilaErrorValidacionLog.pevNumeroLinea runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Número de línea en el que se encuentra la inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN', @level2name=N'pevNumeroLinea'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Número de línea en el que se encuentra la inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN',@level2name=N'pevNumeroLinea'
END CATCH;

--changeset Heinsohn:PilaErrorValidacionLog.pevBloqueValidacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del bloque de validación en el cual se presenta la inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN', @level2name=N'pevBloqueValidacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del bloque de validación en el cual se presenta la inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN',@level2name=N'pevBloqueValidacion'
END CATCH;

--changeset Heinsohn:PilaErrorValidacionLog.pevNombreCampo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Nombre del campo con inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN', @level2name=N'pevNombreCampo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Nombre del campo con inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN',@level2name=N'pevNombreCampo'
END CATCH;

--changeset Heinsohn:PilaErrorValidacionLog.pevPosicionInicial runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Posición inicial del campo con inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN', @level2name=N'pevPosicionInicial'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Posición inicial del campo con inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN',@level2name=N'pevPosicionInicial'
END CATCH;

--changeset Heinsohn:PilaErrorValidacionLog.pevPosicionFinal runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Posición final del campo con inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN', @level2name=N'pevPosicionFinal'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Posición final del campo con inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN',@level2name=N'pevPosicionFinal'
END CATCH;

--changeset Heinsohn:PilaErrorValidacionLog.pevValorCampo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Valor del campo con inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN', @level2name=N'pevValorCampo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Valor del campo con inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN',@level2name=N'pevValorCampo'
END CATCH;

--changeset Heinsohn:PilaErrorValidacionLog.pevCodigoError runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código del mensaje de error' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN', @level2name=N'pevCodigoError'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código del mensaje de error' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN',@level2name=N'pevCodigoError'
END CATCH;

--changeset Heinsohn:PilaErrorValidacionLog.pevMensajeError runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción de la inconsistencia presentada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN', @level2name=N'pevMensajeError'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción de la inconsistencia presentada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN',@level2name=N'pevMensajeError'
END CATCH;

--changeset Heinsohn:PilaErrorValidacionLog.pevEstadoInconsistencia runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Estado de gestión de la inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN', @level2name=N'pevEstadoInconsistencia'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Estado de gestión de la inconsistencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaErrorValidacionLog'
,@level2type=N'COLUMN',@level2name=N'pevEstadoInconsistencia'
END CATCH;



--changeset Heinsohn:PilaOportunidadPresentacionPlanilla runOnChange:true failOnError:false
-- TABLA PilaOportunidadPresentacionPlanilla
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa la especificación de la oportunidad en la presentación 
 de planillas PILA con base en el tipo de planilla que se presenta <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaOportunidadPresentacionPlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa la especificación de la oportunidad en la presentación 
 de planillas PILA con base en el tipo de planilla que se presenta <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaOportunidadPresentacionPlanilla'
END CATCH;

--changeset Heinsohn:PilaOportunidadPresentacionPlanilla.popId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro de oportunidad en la presentación de planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaOportunidadPresentacionPlanilla'
,@level2type=N'COLUMN', @level2name=N'popId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro de oportunidad en la presentación de planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaOportunidadPresentacionPlanilla'
,@level2type=N'COLUMN',@level2name=N'popId'
END CATCH;

--changeset Heinsohn:PilaOportunidadPresentacionPlanilla.popTipoPlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del código de tipo de planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaOportunidadPresentacionPlanilla'
,@level2type=N'COLUMN', @level2name=N'popTipoPlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del código de tipo de planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaOportunidadPresentacionPlanilla'
,@level2type=N'COLUMN',@level2name=N'popTipoPlanilla'
END CATCH;

--changeset Heinsohn:PilaOportunidadPresentacionPlanilla.popOportunidad runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción de la periodicidad de la presentación de planilla para el caso específico' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaOportunidadPresentacionPlanilla'
,@level2type=N'COLUMN', @level2name=N'popOportunidad'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción de la periodicidad de la presentación de planilla para el caso específico' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaOportunidadPresentacionPlanilla'
,@level2type=N'COLUMN',@level2name=N'popOportunidad'
END CATCH;

--changeset Heinsohn:PilaOportunidadPresentacionPlanilla.popTipoCotizanteEspecifico runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de cotizante para el caso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaOportunidadPresentacionPlanilla'
,@level2type=N'COLUMN', @level2name=N'popTipoCotizanteEspecifico'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de cotizante para el caso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaOportunidadPresentacionPlanilla'
,@level2type=N'COLUMN',@level2name=N'popTipoCotizanteEspecifico'
END CATCH;



--changeset Heinsohn:PilaNormatividadFechaVencimiento runOnChange:true failOnError:false
-- TABLA PilaNormatividadFechaVencimiento
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa la tabla de la normatividad aplicable al momento de 
 identificar la fecha de vencimiento de una planilla PILA <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaNormatividadFechaVencimiento'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa la tabla de la normatividad aplicable al momento de 
 identificar la fecha de vencimiento de una planilla PILA <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaNormatividadFechaVencimiento'
END CATCH;

--changeset Heinsohn:PilaNormatividadFechaVencimiento.pfvId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria de la entrada de normatividad' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaNormatividadFechaVencimiento'
,@level2type=N'COLUMN', @level2name=N'pfvId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria de la entrada de normatividad' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaNormatividadFechaVencimiento'
,@level2type=N'COLUMN',@level2name=N'pfvId'
END CATCH;

--changeset Heinsohn:PilaNormatividadFechaVencimiento.pfvPeriodoInicial runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del período inicial de referencia válido para el caso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaNormatividadFechaVencimiento'
,@level2type=N'COLUMN', @level2name=N'pfvPeriodoInicial'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del período inicial de referencia válido para el caso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaNormatividadFechaVencimiento'
,@level2type=N'COLUMN',@level2name=N'pfvPeriodoInicial'
END CATCH;

--changeset Heinsohn:PilaNormatividadFechaVencimiento.pfvPeriodoFinal runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del período final de referencia válido para el caso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaNormatividadFechaVencimiento'
,@level2type=N'COLUMN', @level2name=N'pfvPeriodoFinal'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del período final de referencia válido para el caso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaNormatividadFechaVencimiento'
,@level2type=N'COLUMN',@level2name=N'pfvPeriodoFinal'
END CATCH;

--changeset Heinsohn:PilaNormatividadFechaVencimiento.pfvUltimoDigitoId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del último o últimos dígitos del número de identificación del aportante válidos para el caso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaNormatividadFechaVencimiento'
,@level2type=N'COLUMN', @level2name=N'pfvUltimoDigitoId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del último o últimos dígitos del número de identificación del aportante válidos para el caso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaNormatividadFechaVencimiento'
,@level2type=N'COLUMN',@level2name=N'pfvUltimoDigitoId'
END CATCH;

--changeset Heinsohn:PilaNormatividadFechaVencimiento.pfvClasificacionAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la condición del aportante de acuerdo a su clase o a su cantidad de trabajadores' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaNormatividadFechaVencimiento'
,@level2type=N'COLUMN', @level2name=N'pfvClasificacionAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la condición del aportante de acuerdo a su clase o a su cantidad de trabajadores' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaNormatividadFechaVencimiento'
,@level2type=N'COLUMN',@level2name=N'pfvClasificacionAportante'
END CATCH;

--changeset Heinsohn:PilaNormatividadFechaVencimiento.pfvDiaVencimiento runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Día del mes para el vencimiento de la planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaNormatividadFechaVencimiento'
,@level2type=N'COLUMN', @level2name=N'pfvDiaVencimiento'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Día del mes para el vencimiento de la planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaNormatividadFechaVencimiento'
,@level2type=N'COLUMN',@level2name=N'pfvDiaVencimiento'
END CATCH;

--changeset Heinsohn:PilaNormatividadFechaVencimiento.pfvTipoFecha runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de día de vencimiento de la planilla (Hábil, calendario, etc)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaNormatividadFechaVencimiento'
,@level2type=N'COLUMN', @level2name=N'pfvTipoFecha'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de día de vencimiento de la planilla (Hábil, calendario, etc)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaNormatividadFechaVencimiento'
,@level2type=N'COLUMN',@level2name=N'pfvTipoFecha'
END CATCH;



--changeset Heinsohn:PilaCondicionAportanteVencimiento runOnChange:true failOnError:false
-- TABLA PilaCondicionAportanteVencimiento
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa la clasificación por casos de un aportante con base en su
 clase o su cantidad de trabajadores, como parte del procedimiento para establecer
 la fecha de vencimiento de una planilla PILA <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaCondicionAportanteVencimiento'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa la clasificación por casos de un aportante con base en su
 clase o su cantidad de trabajadores, como parte del procedimiento para establecer
 la fecha de vencimiento de una planilla PILA <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaCondicionAportanteVencimiento'
END CATCH;

--changeset Heinsohn:PilaCondicionAportanteVencimiento.pcaId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria de la entrada de caso de clasificación de aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaCondicionAportanteVencimiento'
,@level2type=N'COLUMN', @level2name=N'pcaId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria de la entrada de caso de clasificación de aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaCondicionAportanteVencimiento'
,@level2type=N'COLUMN',@level2name=N'pcaId'
END CATCH;

--changeset Heinsohn:PilaCondicionAportanteVencimiento.pcaTipoArchivo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de archivo para el cual se establece el caso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaCondicionAportanteVencimiento'
,@level2type=N'COLUMN', @level2name=N'pcaTipoArchivo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de archivo para el cual se establece el caso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaCondicionAportanteVencimiento'
,@level2type=N'COLUMN',@level2name=N'pcaTipoArchivo'
END CATCH;

--changeset Heinsohn:PilaCondicionAportanteVencimiento.pcaCampo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del campo a emplear para la validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaCondicionAportanteVencimiento'
,@level2type=N'COLUMN', @level2name=N'pcaCampo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del campo a emplear para la validación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaCondicionAportanteVencimiento'
,@level2type=N'COLUMN',@level2name=N'pcaCampo'
END CATCH;

--changeset Heinsohn:PilaCondicionAportanteVencimiento.pcaValor runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Valor contra el cual se realiza la comparación del campo a emplear' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaCondicionAportanteVencimiento'
,@level2type=N'COLUMN', @level2name=N'pcaValor'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Valor contra el cual se realiza la comparación del campo a emplear' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaCondicionAportanteVencimiento'
,@level2type=N'COLUMN',@level2name=N'pcaValor'
END CATCH;

--changeset Heinsohn:PilaCondicionAportanteVencimiento.pcaComparacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de comparación a efectuar entre el campo a emplear y el valor definido' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaCondicionAportanteVencimiento'
,@level2type=N'COLUMN', @level2name=N'pcaComparacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de comparación a efectuar entre el campo a emplear y el valor definido' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaCondicionAportanteVencimiento'
,@level2type=N'COLUMN',@level2name=N'pcaComparacion'
END CATCH;



--changeset Heinsohn:PilaArchivoIRegistro3 runOnChange:true failOnError:false
-- TABLA PilaArchivoIRegistro3
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos en combinación de los renglones 31, 36 y 39 del 
 registro tipo 3 de los archivos PILA tipo I-IR (Dependientes - Independientes) <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro3'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos en combinación de los renglones 31, 36 y 39 del 
 registro tipo 3 de los archivos PILA tipo I-IR (Dependientes - Independientes) <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro3'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro3.pi3Id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 3' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro3'
,@level2type=N'COLUMN', @level2name=N'pi3Id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 3' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro3'
,@level2type=N'COLUMN',@level2name=N'pi3Id'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro3.pi3IndicePlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador de Información para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro3'
,@level2type=N'COLUMN', @level2name=N'pi3IndicePlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador de Información para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro3'
,@level2type=N'COLUMN',@level2name=N'pi3IndicePlanilla'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro3.pi3ValorTotalIBC runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 Renglón 31 campo 3: Ingreso Base de Cotización' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro3'
,@level2type=N'COLUMN', @level2name=N'pi3ValorTotalIBC'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 Renglón 31 campo 3: Ingreso Base de Cotización' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro3'
,@level2type=N'COLUMN',@level2name=N'pi3ValorTotalIBC'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro3.pi3ValorTotalAporteObligatorio runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 Renglón 31 campo 4: Aporte obligatorio.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro3'
,@level2type=N'COLUMN', @level2name=N'pi3ValorTotalAporteObligatorio'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 Renglón 31 campo 4: Aporte obligatorio.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro3'
,@level2type=N'COLUMN',@level2name=N'pi3ValorTotalAporteObligatorio'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro3.pi3DiasMora runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 Renglón 36 campo 3: Nu?mero de di?as de mora liquidado.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro3'
,@level2type=N'COLUMN', @level2name=N'pi3DiasMora'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 Renglón 36 campo 3: Nu?mero de di?as de mora liquidado.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro3'
,@level2type=N'COLUMN',@level2name=N'pi3DiasMora'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro3.pi3ValorMora runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 Renglón 36 campo 4: Mora en los Aportes.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro3'
,@level2type=N'COLUMN', @level2name=N'pi3ValorMora'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 Renglón 36 campo 4: Mora en los Aportes.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro3'
,@level2type=N'COLUMN',@level2name=N'pi3ValorMora'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro3.pi3ValorTotalAportes runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 Renglón 39 campo 3: Total Aportes.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro3'
,@level2type=N'COLUMN', @level2name=N'pi3ValorTotalAportes'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 Renglón 39 campo 3: Total Aportes.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro3'
,@level2type=N'COLUMN',@level2name=N'pi3ValorTotalAportes'
END CATCH;



--changeset Heinsohn:PilaArchivoIRegistro2 runOnChange:true failOnError:false
-- TABLA PilaArchivoIRegistro2
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 2 de los archivos
 PILA tipo I-IR (Dependientes - Independientes) <br/>
 <b>Historia de Usuario: </b> 211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 2 de los archivos
 PILA tipo I-IR (Dependientes - Independientes) <br/>
 <b>Historia de Usuario: </b> 211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2Id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 2' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2Id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 2' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2Id'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2IndicePlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador de Información para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2IndicePlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador de Información para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2IndicePlanilla'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2Secuencia runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 1: Secuencia.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2Secuencia'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 1: Secuencia.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2Secuencia'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2TipoIdCotizante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 3: Tipo identificacio?n del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2TipoIdCotizante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 3: Tipo identificacio?n del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2TipoIdCotizante'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2IdCotizante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 4: No. de identificacio?n del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2IdCotizante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 4: No. de identificacio?n del cotizante.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2IdCotizante'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2TipoCotizante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 5: Tipo de cotizante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2TipoCotizante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 5: Tipo de cotizante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2TipoCotizante'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2SubTipoCotizante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 6: Subtipo de cotizante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2SubTipoCotizante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 6: Subtipo de cotizante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2SubTipoCotizante'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2ExtrangeroNoObligado runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 7: Extranjero no obligado a cotizar a pensiones.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2ExtrangeroNoObligado'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 7: Extranjero no obligado a cotizar a pensiones.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2ExtrangeroNoObligado'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2ColombianoExterior runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 8: Colombiano en el exterior.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2ColombianoExterior'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 8: Colombiano en el exterior.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2ColombianoExterior'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2CodDepartamento runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 9: Co?digo del Departamento de la ubicacio?n laboral.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2CodDepartamento'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 9: Co?digo del Departamento de la ubicacio?n laboral.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2CodDepartamento'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2CodMunicipio runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 10: Co?digo del Municipio de la ubicacio?n laboral.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2CodMunicipio'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 10: Co?digo del Municipio de la ubicacio?n laboral.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2CodMunicipio'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2PrimerApellido runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 11: Primer apellido.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2PrimerApellido'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 11: Primer apellido.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2PrimerApellido'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2SegundoApellido runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 12: Segundo apellido.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2SegundoApellido'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 12: Segundo apellido.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2SegundoApellido'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2PrimerNombre runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 13: Primer nombre.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2PrimerNombre'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 13: Primer nombre.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2PrimerNombre'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2SegundoNombre runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 14: Segundo nombre.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2SegundoNombre'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 14: Segundo nombre.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2SegundoNombre'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2NovIngreso runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 15: Marcación de novedad ING: Ingreso.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2NovIngreso'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 15: Marcación de novedad ING: Ingreso.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2NovIngreso'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2NovRetiro runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 16: Marcación de novedad RET: Retiro.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2NovRetiro'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 16: Marcación de novedad RET: Retiro.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2NovRetiro'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2NovVSP runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 17: Marcación de novedad VSP: Variacio?n permanente de salario.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2NovVSP'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 17: Marcación de novedad VSP: Variacio?n permanente de salario.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2NovVSP'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2NovVST runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 18: Marcación de novedad VST: Variacio?n transitoria del salario.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2NovVST'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 18: Marcación de novedad VST: Variacio?n transitoria del salario.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2NovVST'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2NovSLN runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 19: Marcación de novedad SLN: Suspensio?n temporal
 del contrato de trabajo, licencia no remunerada o comisio?n de servicios.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2NovSLN'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 19: Marcación de novedad SLN: Suspensio?n temporal
 del contrato de trabajo, licencia no remunerada o comisio?n de servicios.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2NovSLN'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2NovIGE runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 20: Marcación de novedad IGE: Incapacidad Temporal
 por Enfermedad General.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2NovIGE'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 20: Marcación de novedad IGE: Incapacidad Temporal
 por Enfermedad General.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2NovIGE'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2NovLMA runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 21: Marcación de novedad LMA: Licencia de Maternidad o paternidad.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2NovLMA'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 21: Marcación de novedad LMA: Licencia de Maternidad o paternidad.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2NovLMA'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2NovVACLR runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 22: Marcación de novedad VAC - LR: Vacaciones, Licencia remunerada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2NovVACLR'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 22: Marcación de novedad VAC - LR: Vacaciones, Licencia remunerada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2NovVACLR'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2DiasIRL runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 23: IRL: días de incapacidad por accidente de
 trabajo o enfermedad laboral' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2DiasIRL'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 23: IRL: días de incapacidad por accidente de
 trabajo o enfermedad laboral' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2DiasIRL'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2DiasCotizados runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 24: Di?as cotizados.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2DiasCotizados'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 24: Di?as cotizados.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2DiasCotizados'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2SalarioBasico runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 25: Salario ba?sico.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2SalarioBasico'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 25: Salario ba?sico.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2SalarioBasico'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2ValorIBC runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 26: Ingreso Base Cotizacio?n (IBC)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2ValorIBC'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 26: Ingreso Base Cotizacio?n (IBC)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2ValorIBC'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2Tarifa runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 27: Tarifa.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2Tarifa'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 27: Tarifa.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2Tarifa'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2AporteObligatorio runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 28: Aporte obligatorio.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2AporteObligatorio'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 28: Aporte obligatorio.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2AporteObligatorio'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2Correcciones runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 29: Correcciones' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2Correcciones'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 29: Correcciones' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2Correcciones'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2SalarioIntegral runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 30: Salario Integral' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2SalarioIntegral'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 30: Salario Integral' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2SalarioIntegral'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2FechaIngreso runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 31: Fecha de novedad ingreso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2FechaIngreso'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 31: Fecha de novedad ingreso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2FechaIngreso'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2FechaRetiro runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 32: Fecha de novedad retiro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2FechaRetiro'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 32: Fecha de novedad retiro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2FechaRetiro'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2FechaInicioVSP runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 33: Fecha inicio novedad VSP' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2FechaInicioVSP'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 33: Fecha inicio novedad VSP' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2FechaInicioVSP'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2FechaInicioSLN runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 34: Fecha inicio novedad SLN' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2FechaInicioSLN'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 34: Fecha inicio novedad SLN' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2FechaInicioSLN'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2FechaFinSLN runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 35: Fecha fin novedad SLN' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2FechaFinSLN'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 35: Fecha fin novedad SLN' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2FechaFinSLN'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2FechaInicioIGE runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 36: Fecha inicio novedad IGE' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2FechaInicioIGE'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 36: Fecha inicio novedad IGE' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2FechaInicioIGE'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2FechaFinIGE runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 37: Fecha fin novedad IGE' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2FechaFinIGE'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 37: Fecha fin novedad IGE' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2FechaFinIGE'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2FechaInicioLMA runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 38: Fecha inicio novedad LMA' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2FechaInicioLMA'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 38: Fecha inicio novedad LMA' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2FechaInicioLMA'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2FechaFinLMA runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 39: Fecha fin novedad LMA' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2FechaFinLMA'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 39: Fecha fin novedad LMA' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2FechaFinLMA'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2FechaInicioVACLR runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 40: Fecha inicio novedad VAC - LR' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2FechaInicioVACLR'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 40: Fecha inicio novedad VAC - LR' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2FechaInicioVACLR'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2FechaFinVACLR runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 41: Fecha fin novedad VAC - LR' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2FechaFinVACLR'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 41: Fecha fin novedad VAC - LR' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2FechaFinVACLR'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2FechaInicioVCT runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 42: Fecha inicio novedad VCT' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2FechaInicioVCT'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 42: Fecha inicio novedad VCT' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2FechaInicioVCT'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2FechaFinVCT runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 43: Fecha fin novedad VCT' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2FechaFinVCT'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 43: Fecha fin novedad VCT' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2FechaFinVCT'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2FechaInicioIRL runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 44: Fecha inicio novedad IRL' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2FechaInicioIRL'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 44: Fecha inicio novedad IRL' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2FechaInicioIRL'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2FechaFinIRL runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 45: Fecha fin novedad IRL' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2FechaFinIRL'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 45: Fecha fin novedad IRL' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2FechaFinIRL'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro2.pi2HorasLaboradas runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 46: Número de horas laboradas' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN', @level2name=N'pi2HorasLaboradas'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 46: Número de horas laboradas' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro2'
,@level2type=N'COLUMN',@level2name=N'pi2HorasLaboradas'
END CATCH;



--changeset Heinsohn:PilaArchivoIRegistro1 runOnChange:true failOnError:false
-- TABLA PilaArchivoIRegistro1
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 1 de los archivos 
 PILA tipo I-IR (Dependientes - Independientes) <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 1 de los archivos 
 PILA tipo I-IR (Dependientes - Independientes) <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1Id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 1' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1Id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 1' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1Id'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1IndicePlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador de Información para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1IndicePlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador de Información para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1IndicePlanilla'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1IdCCF runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 4: Nu?mero de Identificacio?n CCF, ICBF o SENA' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1IdCCF'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 4: Nu?mero de Identificacio?n CCF, ICBF o SENA' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1IdCCF'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1DigVerCCF runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 5: Di?gito de Verificacio?n del NIT.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1DigVerCCF'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 5: Di?gito de Verificacio?n del NIT.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1DigVerCCF'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1RazonSocial runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 6: Nombre o razo?n social del aportante.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1RazonSocial'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 6: Nombre o razo?n social del aportante.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1RazonSocial'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1TipoDocAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 7: Tipo de documento de identidad del aportante.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1TipoDocAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 7: Tipo de documento de identidad del aportante.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1TipoDocAportante'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1IdAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 8: Número de documento de identidad del aportante.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1IdAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 8: Número de documento de identidad del aportante.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1IdAportante'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1DigVerAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 9: Di?gito de Verificacio?n Aportante.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1DigVerAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 9: Di?gito de Verificacio?n Aportante.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1DigVerAportante'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1TipoAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 10: Tipo de aportante.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1TipoAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 10: Tipo de aportante.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1TipoAportante'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1Direccion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 11: Direccio?n Correspondencia.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1Direccion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 11: Direccio?n Correspondencia.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1Direccion'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1CodCiudad runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 12: Co?digo ciudad o municipio.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1CodCiudad'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 12: Co?digo ciudad o municipio.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1CodCiudad'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1CodDepartamento runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 13: Co?digo departamento.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1CodDepartamento'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 13: Co?digo departamento.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1CodDepartamento'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1Telefono runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 14: Tele?fono.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1Telefono'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 14: Tele?fono.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1Telefono'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1Fax runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 15: Fax.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1Fax'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 15: Fax.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1Fax'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1Email runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 16: Correo electro?nico.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1Email'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 16: Correo electro?nico.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1Email'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1PeriodoAporte runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 17: Periodo de pago: (aaaa-mm)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1PeriodoAporte'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 17: Periodo de pago: (aaaa-mm)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1PeriodoAporte'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1TipoPlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 18: Tipo de Planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1TipoPlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 18: Tipo de Planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1TipoPlanilla'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1FechaPagoAsociado runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 19: Fecha de pago de la planilla asociada a esta planilla (aaaa-mm-dd).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1FechaPagoAsociado'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 19: Fecha de pago de la planilla asociada a esta planilla (aaaa-mm-dd).' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1FechaPagoAsociado'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1FechaPago runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 20: Fecha de pago (aaaa-mm-dd)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1FechaPago'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 20: Fecha de pago (aaaa-mm-dd)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1FechaPago'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1NumPlanillaAsociada runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 21: Nº de la planilla asociada a esta planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1NumPlanillaAsociada'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 21: Nº de la planilla asociada a esta planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1NumPlanillaAsociada'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1NumPlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 22: Nu?mero de radicacio?n o de la Planilla Integrada de Liquidacio?n de Aportes.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1NumPlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 22: Nu?mero de radicacio?n o de la Planilla Integrada de Liquidacio?n de Aportes.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1NumPlanilla'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1Presentacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 23: Forma de presentacio?n' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1Presentacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 23: Forma de presentacio?n' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1Presentacion'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1CodSucursal runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 24: Co?digo de la Sucursal o de la Dependencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1CodSucursal'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 24: Co?digo de la Sucursal o de la Dependencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1CodSucursal'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1NomSucursal runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 25: Nombre de la Sucursal o de la Dependencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1NomSucursal'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 25: Nombre de la Sucursal o de la Dependencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1NomSucursal'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1CantidadEmpleados runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 26: Numero total de empleados' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1CantidadEmpleados'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 26: Numero total de empleados' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1CantidadEmpleados'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1CantidadAfiliados runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 27: Numero total de afiliados a la administradora' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1CantidadAfiliados'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 27: Numero total de afiliados a la administradora' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1CantidadAfiliados'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1CodOperador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 28: Co?digo del Operador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1CodOperador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 28: Co?digo del Operador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1CodOperador'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1ModalidadPlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 29: Modalidad de la Planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1ModalidadPlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 29: Modalidad de la Planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1ModalidadPlanilla'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1DiasMora runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 30: Di?as de mora' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1DiasMora'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 30: Di?as de mora' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1DiasMora'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1CantidadReg2 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 31: Número de registros de salida tipo 2' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1CantidadReg2'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 31: Número de registros de salida tipo 2' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1CantidadReg2'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1FechaMatricula runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 32: Fecha de Matrícula Mercantil (aaaa-mm-dd)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1FechaMatricula'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 32: Fecha de Matrícula Mercantil (aaaa-mm-dd)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1FechaMatricula'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1CodDepartamentoBeneficio runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 33: Código del departamento' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1CodDepartamentoBeneficio'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 33: Código del departamento' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1CodDepartamentoBeneficio'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1AcogeBeneficio runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 34: Aportante que se acoge a los beneficios del 
 art. 5 de la ley 1429 de 2010 con respecto a aporte para las cajas de compensación familiar' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1AcogeBeneficio'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 34: Aportante que se acoge a los beneficios del 
 art. 5 de la ley 1429 de 2010 con respecto a aporte para las cajas de compensación familiar' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1AcogeBeneficio'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1ClaseAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 35: Clase de aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1ClaseAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 35: Clase de aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1ClaseAportante'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1NaturalezaJuridica runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 36: Naturaleza jurídica' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1NaturalezaJuridica'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 36: Naturaleza jurídica' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1NaturalezaJuridica'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1TipoPersona runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 37: Tipo de persona' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1TipoPersona'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 37: Tipo de persona' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1TipoPersona'
END CATCH;

--changeset Heinsohn:PilaArchivoIRegistro1.pi1FechaActualizacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 38: Fecha Actualización archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN', @level2name=N'pi1FechaActualizacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 38: Fecha Actualización archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIRegistro1'
,@level2type=N'COLUMN',@level2name=N'pi1FechaActualizacion'
END CATCH;



--changeset Heinsohn:PilaArchivoIPRegistro3 runOnChange:true failOnError:false
-- TABLA PilaArchivoIPRegistro3
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 3 de los archivos 
 PILA tipo IP-IPR (Pensionados) <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro3'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 3 de los archivos 
 PILA tipo IP-IPR (Pensionados) <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro3'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro3.ip3Id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 3' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro3'
,@level2type=N'COLUMN', @level2name=N'ip3Id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 3' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro3'
,@level2type=N'COLUMN',@level2name=N'ip3Id'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro3.ip3IndicePlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador de Información para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro3'
,@level2type=N'COLUMN', @level2name=N'ip3IndicePlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador de Información para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro3'
,@level2type=N'COLUMN',@level2name=N'ip3IndicePlanilla'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro3.ip3ValorTotalAporte runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 campo 2: Valor aporte' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro3'
,@level2type=N'COLUMN', @level2name=N'ip3ValorTotalAporte'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 campo 2: Valor aporte' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro3'
,@level2type=N'COLUMN',@level2name=N'ip3ValorTotalAporte'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro3.ip3DiasMora runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 campo 3: Días de mora' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro3'
,@level2type=N'COLUMN', @level2name=N'ip3DiasMora'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 campo 3: Días de mora' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro3'
,@level2type=N'COLUMN',@level2name=N'ip3DiasMora'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro3.ip3ValorMora runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 campo 4: Valor interés de mora' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro3'
,@level2type=N'COLUMN', @level2name=N'ip3ValorMora'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 campo 4: Valor interés de mora' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro3'
,@level2type=N'COLUMN',@level2name=N'ip3ValorMora'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro3.ip3ValorTotalPagar runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 campo 5: Valor total a pagar' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro3'
,@level2type=N'COLUMN', @level2name=N'ip3ValorTotalPagar'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 3 campo 5: Valor total a pagar' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro3'
,@level2type=N'COLUMN',@level2name=N'ip3ValorTotalPagar'
END CATCH;



--changeset Heinsohn:PilaArchivoIPRegistro2 runOnChange:true failOnError:false
-- TABLA PilaArchivoIPRegistro2
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 2 de los archivos
 PILA tipo IP-IPR (Pensionados) <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 2 de los archivos
 PILA tipo IP-IPR (Pensionados) <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2Id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 2' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2Id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 2' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2Id'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2IndicePlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador de Información para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2IndicePlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador de Información para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2IndicePlanilla'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2Secuencia runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 2: Secuencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2Secuencia'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 2: Secuencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2Secuencia'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2TipoIdPensionado runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 3: Tipo identificación del pensionado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2TipoIdPensionado'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 3: Tipo identificación del pensionado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2TipoIdPensionado'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2IdPensionado runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 4: No. de identificación del pensionado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2IdPensionado'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 4: No. de identificación del pensionado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2IdPensionado'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2PrimerApellido runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 5: Primer apellido del pensionado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2PrimerApellido'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 5: Primer apellido del pensionado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2PrimerApellido'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2SegundoApellido runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 6: Segundo apellido del pensionado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2SegundoApellido'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 6: Segundo apellido del pensionado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2SegundoApellido'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2PrimerNombre runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 7: Primer nombre del pensionado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2PrimerNombre'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 7: Primer nombre del pensionado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2PrimerNombre'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2SegundoNombre runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 8: Segundo nombre del pensionado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2SegundoNombre'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 8: Segundo nombre del pensionado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2SegundoNombre'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2CodDepartamento runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 9: Código del departamento de la ubicación de residencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2CodDepartamento'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 9: Código del departamento de la ubicación de residencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2CodDepartamento'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2CodMunicipio runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 10: Código del municipio de la ubicación de residencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2CodMunicipio'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 10: Código del municipio de la ubicación de residencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2CodMunicipio'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2Tarifa runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 11: Tarifa.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2Tarifa'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 11: Tarifa.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2Tarifa'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2ValorAporte runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 12: Valor aporte' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2ValorAporte'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 12: Valor aporte' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2ValorAporte'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2ValorMesada runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 13: Valor de la mesada pensional' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2ValorMesada'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 13: Valor de la mesada pensional' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2ValorMesada'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2DiasCotizados runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 14: Número de días cotizados' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2DiasCotizados'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 14: Número de días cotizados' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2DiasCotizados'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2NovING runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 15: marcación de novedad ING: Ingreso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2NovING'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 15: marcación de novedad ING: Ingreso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2NovING'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2NovRET runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 16: marcación de novedad RET: Retiro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2NovRET'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 16: marcación de novedad RET: Retiro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2NovRET'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2NovVSP runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 17: marcación de novedad VSP: Variación
 permanente de la mesada pensional' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2NovVSP'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 17: marcación de novedad VSP: Variación
 permanente de la mesada pensional' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2NovVSP'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2NovSUS runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 18: marcación de novedad SUS: Suspensión' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2NovSUS'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 18: marcación de novedad SUS: Suspensión' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2NovSUS'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2FechaIngreso runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 19: Fecha de novedad ingreso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2FechaIngreso'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 19: Fecha de novedad ingreso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2FechaIngreso'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2FechaRetiro runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 20: Fecha de novedad retiro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2FechaRetiro'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 20: Fecha de novedad retiro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2FechaRetiro'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2FechaInicioVSP runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 21: Fecha inicio novedad VSP' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2FechaInicioVSP'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 21: Fecha inicio novedad VSP' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2FechaInicioVSP'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2FechaInicioSuspension runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 22: Fecha inicio novedad suspensión' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2FechaInicioSuspension'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 22: Fecha inicio novedad suspensión' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2FechaInicioSuspension'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro2.ip2FechaFinSuspension runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 23: Fecha fin novedad suspensión' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN', @level2name=N'ip2FechaFinSuspension'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 2 campo 23: Fecha fin novedad suspensión' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro2'
,@level2type=N'COLUMN',@level2name=N'ip2FechaFinSuspension'
END CATCH;



--changeset Heinsohn:PilaArchivoIPRegistro1 runOnChange:true failOnError:false
-- TABLA PilaArchivoIPRegistro1
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 1 de los archivos PILA 
 tipo IP-IPR(Pensionados) <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 1 de los archivos PILA 
 tipo IP-IPR(Pensionados) <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1Id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 1' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1Id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 1' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1Id'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1IndicePlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador de Información para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1IndicePlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador de Información para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1IndicePlanilla'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1Secuencia runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 2: Secuencia.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1Secuencia'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 2: Secuencia.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1Secuencia'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1IdAdministradora runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 3: Número de identificación (NIT) de la Administradora.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1IdAdministradora'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 3: Número de identificación (NIT) de la Administradora.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1IdAdministradora'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1DigVerAdministradora runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 4: Dígito de verificación del NIT.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1DigVerAdministradora'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 4: Dígito de verificación del NIT.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1DigVerAdministradora'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1CodAdministradora runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 5: Código administradora – Caja de Compensación Familiar ' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1CodAdministradora'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 5: Código administradora – Caja de Compensación Familiar ' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1CodAdministradora'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1NombrePagador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 6: Nombre o razón social del pagador de pensiones.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1NombrePagador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 6: Nombre o razón social del pagador de pensiones.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1NombrePagador'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1TipoIdPagador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 7: Tipo documento del pagador de pensiones.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1TipoIdPagador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 7: Tipo documento del pagador de pensiones.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1TipoIdPagador'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1IdPagador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 8: Número de identificación del pagador de pensiones.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1IdPagador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 8: Número de identificación del pagador de pensiones.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1IdPagador'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1DigVerPagador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 9: Dígito de verificación del pagador de pensiones.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1DigVerPagador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 9: Dígito de verificación del pagador de pensiones.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1DigVerPagador'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1PeriodoAporte runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 10: Período de pago: (aaaa-mm). ' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1PeriodoAporte'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 10: Período de pago: (aaaa-mm). ' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1PeriodoAporte'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1FechaPago runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 11: Fecha de pago (aaaa-mm-dd)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1FechaPago'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 11: Fecha de pago (aaaa-mm-dd)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1FechaPago'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1NumPlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 12: Número de radicación o de la planilla integrada de liquidación de aportes.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1NumPlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 12: Número de radicación o de la planilla integrada de liquidación de aportes.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1NumPlanilla'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1FormaPresentacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 13: Forma de presentación.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1FormaPresentacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 13: Forma de presentación.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1FormaPresentacion'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1CodSucursal runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 14: Código de la sucursal o de la dependencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1CodSucursal'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 14: Código de la sucursal o de la dependencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1CodSucursal'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1NomSucursal runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 15: Nombre de la sucursal o de la dependencia.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1NomSucursal'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 15: Nombre de la sucursal o de la dependencia.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1NomSucursal'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1ValorTotalMesadas runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 16: Valor total de las mesadas' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1ValorTotalMesadas'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 16: Valor total de las mesadas' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1ValorTotalMesadas'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1CantPensionados runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 17: Número total de pensionados.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1CantPensionados'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 17: Número total de pensionados.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1CantPensionados'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1DiasMora runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 18: Días de mora.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1DiasMora'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 18: Días de mora.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1DiasMora'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1CodOperador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 19: Código del operador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1CodOperador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 19: Código del operador.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1CodOperador'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1CantidadReg2 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 20: Número de registros de salida tipo 2.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1CantidadReg2'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 20: Número de registros de salida tipo 2.' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1CantidadReg2'
END CATCH;

--changeset Heinsohn:PilaArchivoIPRegistro1.ip1FechaActualizacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 21: Fecha actualización archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ip1FechaActualizacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 21: Fecha actualización archivo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoIPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ip1FechaActualizacion'
END CATCH;



--changeset Heinsohn:PilaArchivoFRegistro9 runOnChange:true failOnError:false
-- TABLA PilaArchivoFRegistro9
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 9 de los archivos PILA tipo F 
 (Operador Financiero) <br/>
 <b>Historia de Usuario: </b>211-407' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro9'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 9 de los archivos PILA tipo F 
 (Operador Financiero) <br/>
 <b>Historia de Usuario: </b>211-407' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro9'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro9.pf9Id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 9' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro9'
,@level2type=N'COLUMN', @level2name=N'pf9Id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 9' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro9'
,@level2type=N'COLUMN',@level2name=N'pf9Id'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro9.pf9IndicePlanillaOF runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador Financiero para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro9'
,@level2type=N'COLUMN', @level2name=N'pf9IndicePlanillaOF'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador Financiero para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro9'
,@level2type=N'COLUMN',@level2name=N'pf9IndicePlanillaOF'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro9.pf9CantidadTotalPlanillas runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 9 campo 2: Número total de planillas' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro9'
,@level2type=N'COLUMN', @level2name=N'pf9CantidadTotalPlanillas'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 9 campo 2: Número total de planillas' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro9'
,@level2type=N'COLUMN',@level2name=N'pf9CantidadTotalPlanillas'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro9.pf9CantidadTotalRegistros runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 9 campo 3: Número total de registros de empleados' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro9'
,@level2type=N'COLUMN', @level2name=N'pf9CantidadTotalRegistros'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 9 campo 3: Número total de registros de empleados' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro9'
,@level2type=N'COLUMN',@level2name=N'pf9CantidadTotalRegistros'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro9.pf9ValorTotalRecaudo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 9 campo 4: Valor total recaudado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro9'
,@level2type=N'COLUMN', @level2name=N'pf9ValorTotalRecaudo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 9 campo 4: Valor total recaudado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro9'
,@level2type=N'COLUMN',@level2name=N'pf9ValorTotalRecaudo'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro9.pf9CantidadTotalLotes runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 9 campo 5: Número total de lotes' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro9'
,@level2type=N'COLUMN', @level2name=N'pf9CantidadTotalLotes'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 9 campo 5: Número total de lotes' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro9'
,@level2type=N'COLUMN',@level2name=N'pf9CantidadTotalLotes'
END CATCH;



--changeset Heinsohn:PilaArchivoFRegistro8 runOnChange:true failOnError:false
-- TABLA PilaArchivoFRegistro8
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 8 de los archivos PILA tipo F
 (Operador Financiero) <br/>
 <b>Historia de Usuario: </b>211-407' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro8'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 8 de los archivos PILA tipo F
 (Operador Financiero) <br/>
 <b>Historia de Usuario: </b>211-407' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro8'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro8.pf8Id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 8' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro8'
,@level2type=N'COLUMN', @level2name=N'pf8Id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 8' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro8'
,@level2type=N'COLUMN',@level2name=N'pf8Id'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro8.pf8IndicePlanillaOF runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador Financiero para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro8'
,@level2type=N'COLUMN', @level2name=N'pf8IndicePlanillaOF'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador Financiero para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro8'
,@level2type=N'COLUMN',@level2name=N'pf8IndicePlanillaOF'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro8.pf8CantidadPlanillas runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 8 campo 2: Número de planillas' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro8'
,@level2type=N'COLUMN', @level2name=N'pf8CantidadPlanillas'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 8 campo 2: Número de planillas' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro8'
,@level2type=N'COLUMN',@level2name=N'pf8CantidadPlanillas'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro8.pf8CantidadRegistros runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 8 campo 3: Número de registros' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro8'
,@level2type=N'COLUMN', @level2name=N'pf8CantidadRegistros'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 8 campo 3: Número de registros' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro8'
,@level2type=N'COLUMN',@level2name=N'pf8CantidadRegistros'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro8.pf8ValorRecaudado runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 8 campo 4: Valor recaudado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro8'
,@level2type=N'COLUMN', @level2name=N'pf8ValorRecaudado'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 8 campo 4: Valor recaudado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro8'
,@level2type=N'COLUMN',@level2name=N'pf8ValorRecaudado'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro8.pf8PilaArchivoFRegistro5 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a el identificador de la tabla PilaArchivoFRegistro5
 Datos de los registros tipo 5 Operador Financiero' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro8'
,@level2type=N'COLUMN', @level2name=N'pf8PilaArchivoFRegistro5'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a el identificador de la tabla PilaArchivoFRegistro5
 Datos de los registros tipo 5 Operador Financiero' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro8'
,@level2type=N'COLUMN',@level2name=N'pf8PilaArchivoFRegistro5'
END CATCH;



--changeset Heinsohn:PilaArchivoFRegistro6 runOnChange:true failOnError:false
-- TABLA PilaArchivoFRegistro6
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 6 de los archivos PILA tipo F
 (Operador Financiero) <br/>
 <b>Historia de Usuario: </b>211-407' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 6 de los archivos PILA tipo F
 (Operador Financiero) <br/>
 <b>Historia de Usuario: </b>211-407' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro6.pf6Id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 6' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN', @level2name=N'pf6Id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 6' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN',@level2name=N'pf6Id'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro6.pf6IndicePlanillaOF runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador Financiero para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN', @level2name=N'pf6IndicePlanillaOF'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador Financiero para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN',@level2name=N'pf6IndicePlanillaOF'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro6.pf6IdAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 2: Identificación del aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN', @level2name=N'pf6IdAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 2: Identificación del aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN',@level2name=N'pf6IdAportante'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro6.pf6NombreAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 3: Nombre del aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN', @level2name=N'pf6NombreAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 3: Nombre del aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN',@level2name=N'pf6NombreAportante'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro6.pf6CodBanco runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 4: Código del banco autorizador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN', @level2name=N'pf6CodBanco'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 4: Código del banco autorizador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN',@level2name=N'pf6CodBanco'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro6.pf6NumeroPlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 5: Número de planilla de liquidación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN', @level2name=N'pf6NumeroPlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 5: Número de planilla de liquidación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN',@level2name=N'pf6NumeroPlanilla'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro6.pf6PeriodoPago runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 6: Período de pago' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN', @level2name=N'pf6PeriodoPago'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 6: Período de pago' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN',@level2name=N'pf6PeriodoPago'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro6.pf6CanalPago runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 7: Canal de pago' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN', @level2name=N'pf6CanalPago'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 7: Canal de pago' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN',@level2name=N'pf6CanalPago'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro6.pf6CantidadRegistros runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 8: Número de registros' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN', @level2name=N'pf6CantidadRegistros'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 8: Número de registros' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN',@level2name=N'pf6CantidadRegistros'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro6.pf6CodOperadorInformacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 9: Código del Operador de Información' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN', @level2name=N'pf6CodOperadorInformacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 9: Código del Operador de Información' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN',@level2name=N'pf6CodOperadorInformacion'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro6.pf6ValorPlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 10: Valor de la planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN', @level2name=N'pf6ValorPlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 10: Valor de la planilla' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN',@level2name=N'pf6ValorPlanilla'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro6.pf6HoraMinuto runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 11: Hora-Minuto' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN', @level2name=N'pf6HoraMinuto'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 11: Hora-Minuto' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN',@level2name=N'pf6HoraMinuto'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro6.pf6Secuencia runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 12: Número de secuencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN', @level2name=N'pf6Secuencia'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 6 campo 12: Número de secuencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN',@level2name=N'pf6Secuencia'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro6.pf6EstadoConciliacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Campo especial: Descripción del estado de conciliación del registro tipo 6
 respecto a archivo de Operador de Información' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN', @level2name=N'pf6EstadoConciliacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Campo especial: Descripción del estado de conciliación del registro tipo 6
 respecto a archivo de Operador de Información' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN',@level2name=N'pf6EstadoConciliacion'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro6.pf6PilaArchivoFRegistro5 runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a el identificador de la tabla PilaArchivoFRegistro5
 Datos de los registros tipo 5 Operador Financiero' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN', @level2name=N'pf6PilaArchivoFRegistro5'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a el identificador de la tabla PilaArchivoFRegistro5
 Datos de los registros tipo 5 Operador Financiero' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro6'
,@level2type=N'COLUMN',@level2name=N'pf6PilaArchivoFRegistro5'
END CATCH;



--changeset Heinsohn:PilaArchivoFRegistro5 runOnChange:true failOnError:false
-- TABLA PilaArchivoFRegistro5
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 5 de los archivos PILA tipo F 
 (Operador Financiero) <br/>
 <b>Historia de Usuario: </b>211-407' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro5'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 5 de los archivos PILA tipo F 
 (Operador Financiero) <br/>
 <b>Historia de Usuario: </b>211-407' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro5'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro5.pf5Id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 9' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro5'
,@level2type=N'COLUMN', @level2name=N'pf5Id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 9' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro5'
,@level2type=N'COLUMN',@level2name=N'pf5Id'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro5.pf5IndicePlanillaOF runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador Financiero para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro5'
,@level2type=N'COLUMN', @level2name=N'pf5IndicePlanillaOF'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador Financiero para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro5'
,@level2type=N'COLUMN',@level2name=N'pf5IndicePlanillaOF'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro5.pf5NumeroCuenta runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 5 campo 2: Número de cuenta' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro5'
,@level2type=N'COLUMN', @level2name=N'pf5NumeroCuenta'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 5 campo 2: Número de cuenta' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro5'
,@level2type=N'COLUMN',@level2name=N'pf5NumeroCuenta'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro5.pf5TipoCuenta runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 5 campo 3: Tipo de cuenta' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro5'
,@level2type=N'COLUMN', @level2name=N'pf5TipoCuenta'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 5 campo 3: Tipo de cuenta' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro5'
,@level2type=N'COLUMN',@level2name=N'pf5TipoCuenta'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro5.pf5NumeroLote runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 5 campo 4: Número de lote' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro5'
,@level2type=N'COLUMN', @level2name=N'pf5NumeroLote'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 5 campo 4: Número de lote' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro5'
,@level2type=N'COLUMN',@level2name=N'pf5NumeroLote'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro5.pf5SistemaPago runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 5 campo 5: Sistema de pago' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro5'
,@level2type=N'COLUMN', @level2name=N'pf5SistemaPago'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 5 campo 5: Sistema de pago' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro5'
,@level2type=N'COLUMN',@level2name=N'pf5SistemaPago'
END CATCH;



--changeset Heinsohn:PilaArchivoFRegistro1 runOnChange:true failOnError:false
-- TABLA PilaArchivoFRegistro1
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 1 de los archivos PILA 
 tipo F (Operador Financiero) <br/>
 <b>Historia de Usuario: </b>211-407' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro1'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 1 de los archivos PILA 
 tipo F (Operador Financiero) <br/>
 <b>Historia de Usuario: </b>211-407' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro1'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro1.pf1Id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 1' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro1'
,@level2type=N'COLUMN', @level2name=N'pf1Id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 1' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro1'
,@level2type=N'COLUMN',@level2name=N'pf1Id'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro1.pf1IndicePlanillaOF runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador Financiero para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro1'
,@level2type=N'COLUMN', @level2name=N'pf1IndicePlanillaOF'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador Financiero para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro1'
,@level2type=N'COLUMN',@level2name=N'pf1IndicePlanillaOF'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro1.pf1FechaRecaudo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 2: Fecha de recaudo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro1'
,@level2type=N'COLUMN', @level2name=N'pf1FechaRecaudo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 2: Fecha de recaudo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro1'
,@level2type=N'COLUMN',@level2name=N'pf1FechaRecaudo'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro1.pf1CodigoEntidadFinanciera runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 3: Codigo de la entidad financiera recaudadora 
 o receptora' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro1'
,@level2type=N'COLUMN', @level2name=N'pf1CodigoEntidadFinanciera'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 3: Codigo de la entidad financiera recaudadora 
 o receptora' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro1'
,@level2type=N'COLUMN',@level2name=N'pf1CodigoEntidadFinanciera'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro1.pf1IdAdministradora runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 4: NIT de la administradora' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro1'
,@level2type=N'COLUMN', @level2name=N'pf1IdAdministradora'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 4: NIT de la administradora' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro1'
,@level2type=N'COLUMN',@level2name=N'pf1IdAdministradora'
END CATCH;

--changeset Heinsohn:PilaArchivoFRegistro1.pf1NombreAdministradora runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 5: Nombre administradora' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro1'
,@level2type=N'COLUMN', @level2name=N'pf1NombreAdministradora'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 5: Nombre administradora' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoFRegistro1'
,@level2type=N'COLUMN',@level2name=N'pf1NombreAdministradora'
END CATCH;



--changeset Heinsohn:PilaArchivoARegistro1 runOnChange:true failOnError:false
-- TABLA PilaArchivoARegistro1
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 1 de los archivos 
 PILA tipo A-AP (Dependientes - Independientes) <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 1 de los archivos 
 PILA tipo A-AP (Dependientes - Independientes) <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1Id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 1' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1Id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 1' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1Id'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1IndicePlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador de Información 
 para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1IndicePlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador de Información 
 para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1IndicePlanilla'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1NombreAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 1: Nombre o razón social del aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1NombreAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 1: Nombre o razón social del aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1NombreAportante'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1TipoIdAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 2: Tipo documento del aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1TipoIdAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 2: Tipo documento del aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1TipoIdAportante'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1IdAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 3: Número de Identificación del aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1IdAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 3: Número de Identificación del aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1IdAportante'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1DigVerAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 4: Dígito de verificación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1DigVerAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 4: Dígito de verificación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1DigVerAportante'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1CodSucursal runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 5: Código de la sucursal o de la dependencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1CodSucursal'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 5: Código de la sucursal o de la dependencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1CodSucursal'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1NomSucursal runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 6: Nombre de la sucursal o de la dependencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1NomSucursal'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 6: Nombre de la sucursal o de la dependencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1NomSucursal'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1ClaseAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 7: Clase de aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1ClaseAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 7: Clase de aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1ClaseAportante'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1NaturalezaJuridica runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 8: Naturaleza jurídica' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1NaturalezaJuridica'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 8: Naturaleza jurídica' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1NaturalezaJuridica'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1TipoPersona runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 9: Tipo persona' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1TipoPersona'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 9: Tipo persona' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1TipoPersona'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1FormaPresentacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 10: Forma de presentación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1FormaPresentacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 10: Forma de presentación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1FormaPresentacion'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1Direccion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 11: Dirección de correspondencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1Direccion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 11: Dirección de correspondencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1Direccion'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1CodCiudad runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 12: Código ciudad o municipio' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1CodCiudad'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 12: Código ciudad o municipio' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1CodCiudad'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1CodDepartamento runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 13: Código departamento' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1CodDepartamento'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 13: Código departamento' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1CodDepartamento'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1CodActividadEconomica runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 14: Código DANE de la actividad económica' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1CodActividadEconomica'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 14: Código DANE de la actividad económica' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1CodActividadEconomica'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1Telefono runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 15: Teléfono' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1Telefono'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 15: Teléfono' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1Telefono'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1Fax runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 16: Fax' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1Fax'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 16: Fax' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1Fax'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1Email runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 17: Dirección de correo electrónico (E-mail)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1Email'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 17: Dirección de correo electrónico (E-mail)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1Email'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1IdRepresentante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 18: Número de identificación del 
 representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1IdRepresentante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 18: Número de identificación del 
 representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1IdRepresentante'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1DigVerRepresentante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 19: Dígito de verificación representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1DigVerRepresentante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 19: Dígito de verificación representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1DigVerRepresentante'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1TipoIdRepresentante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 20: Tipo identificación representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1TipoIdRepresentante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 20: Tipo identificación representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1TipoIdRepresentante'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1PrimerApellidoRep runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 21: Primer apellido del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1PrimerApellidoRep'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 21: Primer apellido del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1PrimerApellidoRep'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1SegundoApellidoRep runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 22: Segundo apellido del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1SegundoApellidoRep'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 22: Segundo apellido del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1SegundoApellidoRep'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1PrimerNombreRep runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 23: Primer nombre del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1PrimerNombreRep'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 23: Primer nombre del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1PrimerNombreRep'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1SegundoNombreRep runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 24: Segundo nombre del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1SegundoNombreRep'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 24: Segundo nombre del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1SegundoNombreRep'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1FechaInicioConcordato runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 25: Fecha de inicio del concordato, 
 reestructuración, liquidación o cese de actividades' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1FechaInicioConcordato'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 25: Fecha de inicio del concordato, 
 reestructuración, liquidación o cese de actividades' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1FechaInicioConcordato'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1TipoAccion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 26: Tipo de acción' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1TipoAccion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 26: Tipo de acción' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1TipoAccion'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1FechaFinActividades runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 27: Fecha en que terminó actividades comerciales' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1FechaFinActividades'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 27: Fecha en que terminó actividades comerciales' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1FechaFinActividades'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1CodOperador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 28: Código del operador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1CodOperador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 28: Código del operador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1CodOperador'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1PeriodoAporte runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 29: Período de pago' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1PeriodoAporte'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 29: Período de pago' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1PeriodoAporte'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1TipoAportante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 30: Tipo de aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1TipoAportante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 30: Tipo de aportante' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1TipoAportante'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1FechaMatricula runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 31: Fecha de matrícula mercantil (aaaa-mm-dd)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1FechaMatricula'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 31: Fecha de matrícula mercantil (aaaa-mm-dd)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1FechaMatricula'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1CodDepartamentoBene runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 32: Código del departamento' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1CodDepartamentoBene'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 32: Código del departamento' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1CodDepartamentoBene'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1AportanteExonerado runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 33: Aportante exonerado de pago de aporte a 
 salud, SENA e ICBF- Ley 1607 de 2012' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1AportanteExonerado'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 33: Aportante exonerado de pago de aporte a 
 salud, SENA e ICBF- Ley 1607 de 2012' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1AportanteExonerado'
END CATCH;

--changeset Heinsohn:PilaArchivoARegistro1.pa1AcogeBeneficio runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 34: Aportante que se acoge a los beneficios 
 del artículo 5 de la Ley 1429 de 2010 con respecto al aporte para las cajas de 
 compensación familiar' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN', @level2name=N'pa1AcogeBeneficio'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 34: Aportante que se acoge a los beneficios 
 del artículo 5 de la Ley 1429 de 2010 con respecto al aporte para las cajas de 
 compensación familiar' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoARegistro1'
,@level2type=N'COLUMN',@level2name=N'pa1AcogeBeneficio'
END CATCH;



--changeset Heinsohn:PilaArchivoAPRegistro1 runOnChange:true failOnError:false
-- TABLA PilaArchivoAPRegistro1
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 1 de los archivos 
 PILA tipo AP-APR (Pensionados) <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa los datos de los registros tipo 1 de los archivos 
 PILA tipo AP-APR (Pensionados) <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1Id runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 1' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1Id'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro tipo 1' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1Id'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1IndicePlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador de 
 Información para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1IndicePlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Referencia a la entrada del índice de planilla de Operador de 
 Información para el registro' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1IndicePlanilla'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1NombrePagador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 1: Nombre o razón social 
 del pagador de pensiones' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1NombrePagador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 1: Nombre o razón social 
 del pagador de pensiones' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1NombrePagador'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1TipoIdPagador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 2: Tipo documento del pagador 
 de pensiones' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1TipoIdPagador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 2: Tipo documento del pagador 
 de pensiones' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1TipoIdPagador'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1IdPagador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 3: Número de identificación del
  pagador de pensiones' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1IdPagador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 3: Número de identificación del
  pagador de pensiones' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1IdPagador'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1DigVerPagador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 4: Dígito de verificación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1DigVerPagador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 4: Dígito de verificación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1DigVerPagador'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1CodSucursal runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 5: Código de la sucursal o dependencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1CodSucursal'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 5: Código de la sucursal o dependencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1CodSucursal'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1NomSucursal runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 6: Nombre de la sucursal o dependencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1NomSucursal'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 6: Nombre de la sucursal o dependencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1NomSucursal'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1ClasePagador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 7: Clase de pagador de pensiones' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1ClasePagador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 7: Clase de pagador de pensiones' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1ClasePagador'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1NaturalezaJuridica runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 8: Naturaleza jurídica' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1NaturalezaJuridica'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 8: Naturaleza jurídica' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1NaturalezaJuridica'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1TipoPersona runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 9: Tipo persona' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1TipoPersona'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 9: Tipo persona' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1TipoPersona'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1FormaPresentacion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 10: Forma de presentación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1FormaPresentacion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 10: Forma de presentación' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1FormaPresentacion'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1Direccion runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 11: Dirección correspondencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1Direccion'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 11: Dirección correspondencia' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1Direccion'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1CodCiudad runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 12: Código ciudad o municipio' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1CodCiudad'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 12: Código ciudad o municipio' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1CodCiudad'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1CodDepartamento runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 13: Código departamento' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1CodDepartamento'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 13: Código departamento' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1CodDepartamento'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1CodActividadEconomica runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 14: Código DANE de la actividad económica' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1CodActividadEconomica'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 14: Código DANE de la actividad económica' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1CodActividadEconomica'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1Telefono runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 15: Teléfono' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1Telefono'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 15: Teléfono' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1Telefono'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1Fax runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 16: Fax' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1Fax'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 16: Fax' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1Fax'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1Email runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 17: Dirección de correo 
 electrónico (E-mail)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1Email'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 17: Dirección de correo 
 electrónico (E-mail)' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1Email'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1IdRepresentante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 18: Número de identificación 
 del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1IdRepresentante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 18: Número de identificación 
 del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1IdRepresentante'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1DigVerRepresentante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 19: Dígito de verificación 
 representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1DigVerRepresentante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 19: Dígito de verificación 
 representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1DigVerRepresentante'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1TipoIdRepresentante runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 20: Tipo identificación representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1TipoIdRepresentante'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 20: Tipo identificación representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1TipoIdRepresentante'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1PrimerApellidoRep runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 21: Primer apellido del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1PrimerApellidoRep'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 21: Primer apellido del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1PrimerApellidoRep'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1SegundoApellidoRep runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 22: Segundo apellido del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1SegundoApellidoRep'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 22: Segundo apellido del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1SegundoApellidoRep'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1PrimerNombreRep runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 23: Primer nombre del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1PrimerNombreRep'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 23: Primer nombre del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1PrimerNombreRep'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1SegundoNombreRep runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 24: Segundo nombre del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1SegundoNombreRep'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 24: Segundo nombre del representante legal' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1SegundoNombreRep'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1CodOperador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 25: Código del operador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1CodOperador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 25: Código del operador' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1CodOperador'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1PeriodoAporte runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 26: Período de pago' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1PeriodoAporte'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 26: Período de pago' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1PeriodoAporte'
END CATCH;

--changeset Heinsohn:PilaArchivoAPRegistro1.ap1TipoPagador runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 27: Tipo de pagador de pensiones' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN', @level2name=N'ap1TipoPagador'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Contenido del Registro tipo 1 campo 27: Tipo de pagador de pensiones' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaArchivoAPRegistro1'
,@level2type=N'COLUMN',@level2name=N'ap1TipoPagador'
END CATCH;


--changeset Heinsohn:PilaProceso runOnChange:true failOnError:false
-- TABLA PilaProceso
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa a la tabla en la que se registran los procesos de 
 validación de PILA <br/>
 <b>Historia de Usuario: </b>211-386, 211-390, 211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaProceso'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa a la tabla en la que se registran los procesos de 
 validación de PILA <br/>
 <b>Historia de Usuario: </b>211-386, 211-390, 211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaProceso'
END CATCH;

--changeset Heinsohn:PilaProceso.pprId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del proceso PILA' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaProceso'
,@level2type=N'COLUMN', @level2name=N'pprId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del proceso PILA' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaProceso'
,@level2type=N'COLUMN',@level2name=N'pprId'
END CATCH;

--changeset Heinsohn:PilaProceso.pprNumeroRadicado runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Número de radicado del proceso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaProceso'
,@level2type=N'COLUMN', @level2name=N'pprNumeroRadicado'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Número de radicado del proceso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaProceso'
,@level2type=N'COLUMN',@level2name=N'pprNumeroRadicado'
END CATCH;

--changeset Heinsohn:PilaProceso.pprTipoProceso runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de proceso ejecutado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaProceso'
,@level2type=N'COLUMN', @level2name=N'pprTipoProceso'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de proceso ejecutado' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaProceso'
,@level2type=N'COLUMN',@level2name=N'pprTipoProceso'
END CATCH;

--changeset Heinsohn:PilaProceso.pprFechaInicioProceso runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora de inicio del proceso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaProceso'
,@level2type=N'COLUMN', @level2name=N'pprFechaInicioProceso'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora de inicio del proceso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaProceso'
,@level2type=N'COLUMN',@level2name=N'pprFechaInicioProceso'
END CATCH;

--changeset Heinsohn:PilaProceso.pprFechaFinProceso runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora de finalización del proceso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaProceso'
,@level2type=N'COLUMN', @level2name=N'pprFechaFinProceso'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Fecha y hora de finalización del proceso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaProceso'
,@level2type=N'COLUMN',@level2name=N'pprFechaFinProceso'
END CATCH;

--changeset Heinsohn:PilaProceso.pprUsuarioProceso runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del usuario que lanza el proceso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaProceso'
,@level2type=N'COLUMN', @level2name=N'pprUsuarioProceso'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del usuario que lanza el proceso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaProceso'
,@level2type=N'COLUMN',@level2name=N'pprUsuarioProceso'
END CATCH;

--changeset Heinsohn:PilaProceso.pprEstadoProceso runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del estado del proceso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaProceso'
,@level2type=N'COLUMN', @level2name=N'pprEstadoProceso'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del estado del proceso' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaProceso'
,@level2type=N'COLUMN',@level2name=N'pprEstadoProceso'
END CATCH;



--changeset Heinsohn:PilaPasoValores runOnChange:true failOnError:false
-- TABLA PilaPasoValores
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa la tabla empleada para la persistencia temporal de la información
 que debe ser cruzada entre los archivos A e I de acuerdo a lo establecido 
 en el bloque 3 de validación <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaPasoValores'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Entidad que representa la tabla empleada para la persistencia temporal de la información
 que debe ser cruzada entre los archivos A e I de acuerdo a lo establecido 
 en el bloque 3 de validación <br/>
 <b>Historia de Usuario: </b>211-391' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaPasoValores'
END CATCH;

--changeset Heinsohn:PilaPasoValores.ppvId runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro de valor de variable almacenada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaPasoValores'
,@level2type=N'COLUMN', @level2name=N'ppvId'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código identificador de llave primaria del registro de valor de variable almacenada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaPasoValores'
,@level2type=N'COLUMN',@level2name=N'ppvId'
END CATCH;

--changeset Heinsohn:PilaPasoValores.ppvIdPlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Número de la planilla a la que corresponde la variable almacenada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaPasoValores'
,@level2type=N'COLUMN', @level2name=N'ppvIdPlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Número de la planilla a la que corresponde la variable almacenada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaPasoValores'
,@level2type=N'COLUMN',@level2name=N'ppvIdPlanilla'
END CATCH;

--changeset Heinsohn:PilaPasoValores.ppvTipoPlanilla runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de archivo de planilla por el cual se almacena la variable' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaPasoValores'
,@level2type=N'COLUMN', @level2name=N'ppvTipoPlanilla'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del tipo de archivo de planilla por el cual se almacena la variable' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaPasoValores'
,@level2type=N'COLUMN',@level2name=N'ppvTipoPlanilla'
END CATCH;

--changeset Heinsohn:PilaPasoValores.ppvBloque runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Bloque de validación para el cual se almacena la variable' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaPasoValores'
,@level2type=N'COLUMN', @level2name=N'ppvBloque'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Bloque de validación para el cual se almacena la variable' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaPasoValores'
,@level2type=N'COLUMN',@level2name=N'ppvBloque'
END CATCH;

--changeset Heinsohn:PilaPasoValores.ppvNombreVariable runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Descripción del nombre de la variable almacenada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaPasoValores'
,@level2type=N'COLUMN', @level2name=N'ppvNombreVariable'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Descripción del nombre de la variable almacenada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaPasoValores'
,@level2type=N'COLUMN',@level2name=N'ppvNombreVariable'
END CATCH;

--changeset Heinsohn:PilaPasoValores.ppvValorVariable runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Valor de la variable almacenada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaPasoValores'
,@level2type=N'COLUMN', @level2name=N'ppvValorVariable'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Valor de la variable almacenada' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaPasoValores'
,@level2type=N'COLUMN',@level2name=N'ppvValorVariable'
END CATCH;

--changeset Heinsohn:PilaPasoValores.ppvCodigoCampo runOnChange:true failOnError:false
BEGIN TRY
EXEC sys.sp_addextendedproperty
@name=N'MS_Description',
@value=N'Código interno de identificación del campo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaPasoValores'
,@level2type=N'COLUMN', @level2name=N'ppvCodigoCampo'
END TRY
BEGIN CATCH
EXEC sys.sp_updateextendedproperty
@name=N'MS_Description',
@value=N'Código interno de identificación del campo' ,
@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'PilaPasoValores'
,@level2type=N'COLUMN',@level2name=N'ppvCodigoCampo'
END CATCH;


