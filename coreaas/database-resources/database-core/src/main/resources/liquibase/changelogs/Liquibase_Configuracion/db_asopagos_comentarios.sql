--liquibase formatted sql

--changeset mgiraldo:01 

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa una solicitud global que puede ser
 usada en varios procesos de los definidos en el core
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', Solicitud;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa una solicitud global que puede ser
 usada en varios procesos de los definidos en el core
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', Solicitud;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la solicitud',
'User', dbo, 'table', Solicitud, 'column', solId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la solicitud',
'User', dbo, 'table', Solicitud, 'column', solId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de la instancia del proceso',
'User', dbo, 'table', Solicitud, 'column', solInstanciaProceso;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de la instancia del proceso',
'User', dbo, 'table', Solicitud, 'column', solInstanciaProceso;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del canal de recepción de las solicitudes',
'User', dbo, 'table', Solicitud, 'column', solCanalRecepcion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del canal de recepción de las solicitudes',
'User', dbo, 'table', Solicitud, 'column', solCanalRecepcion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado de documentación de la solicitud de afiliación',
'User', dbo, 'table', Solicitud, 'column', solEstadoDocumentacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado de documentación de la solicitud de afiliación',
'User', dbo, 'table', Solicitud, 'column', solEstadoDocumentacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de los formatos de entrega de documentos al back',
'User', dbo, 'table', Solicitud, 'column', solMetodoEnvio;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de los formatos de entrega de documentos al back',
'User', dbo, 'table', Solicitud, 'column', solMetodoEnvio;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la caja de correspondencia asociada a la solicitud',
'User', dbo, 'table', Solicitud, 'column', solCajaCorrespondencia;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la caja de correspondencia asociada a la solicitud',
'User', dbo, 'table', Solicitud, 'column', solCajaCorrespondencia;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de transacción dentro del proceso de la solicitud',
'User', dbo, 'table', Solicitud, 'column', solTipoTransaccion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de transacción dentro del proceso de la solicitud',
'User', dbo, 'table', Solicitud, 'column', solTipoTransaccion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de la clasificación del tipo solicitante',
'User', dbo, 'table', Solicitud, 'column', solClasificacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de la clasificación del tipo solicitante',
'User', dbo, 'table', Solicitud, 'column', solClasificacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de radicación de los estados de documentación de la afiliación de un nuevo empleador',
'User', dbo, 'table', Solicitud, 'column', solTipoRadicacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de radicación de los estados de documentación de la afiliación de un nuevo empleador',
'User', dbo, 'table', Solicitud, 'column', solTipoRadicacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Número de radicación de la solicitud',
'User', dbo, 'table', Solicitud, 'column', solNumeroRadicacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Número de radicación de la solicitud',
'User', dbo, 'table', Solicitud, 'column', solNumeroRadicacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Usuario de la radicación',
'User', dbo, 'table', Solicitud, 'column', solUsuarioRadicacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Usuario de la radicación',
'User', dbo, 'table', Solicitud, 'column', solUsuarioRadicacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de radicación de la solicitud',
'User', dbo, 'table', Solicitud, 'column', solFechaRadicacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de radicación de la solicitud',
'User', dbo, 'table', Solicitud, 'column', solFechaRadicacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del nombre de la ciudad del usuario de radiación',
'User', dbo, 'table', Solicitud, 'column', solCiudadUsuarioRadicacion ;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del nombre de la ciudad del usuario de radiación',
'User', dbo, 'table', Solicitud, 'column', solCiudadUsuarioRadicacion ;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Usuario destinatario',
'User', dbo, 'table', Solicitud, 'column', solDestinatario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Usuario destinatario',
'User', dbo, 'table', Solicitud, 'column', solDestinatario;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del código de la sede del destinatario',
'User', dbo, 'table', Solicitud, 'column', solSedeDestinatario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del código de la sede del destinatario',
'User', dbo, 'table', Solicitud, 'column', solSedeDestinatario;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de creación de la solicitud',
'User', dbo, 'table', Solicitud, 'column', solFechaCreacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de creación de la solicitud',
'User', dbo, 'table', Solicitud, 'column', solFechaCreacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Observaciones de la solicitud',
'User', dbo, 'table', Solicitud, 'column', solObservacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Observaciones de la solicitud',
'User', dbo, 'table', Solicitud, 'column', solObservacion;
END CATCH;



-- TABLA Parametro
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa constantes del sistema que 
 determinan comportamientos específicos de la aplicación.
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', Parametro;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa constantes del sistema que 
 determinan comportamientos específicos de la aplicación.
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', Parametro;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del parametro',
'User', dbo, 'table', Parametro, 'column', prmId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del parametro',
'User', dbo, 'table', Parametro, 'column', prmId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Nombre del parámetro',
'User', dbo, 'table', Parametro, 'column', prmNombre;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Nombre del parámetro',
'User', dbo, 'table', Parametro, 'column', prmNombre;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del valor del parámetro',
'User', dbo, 'table', Parametro, 'column', prmValor;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del valor del parámetro',
'User', dbo, 'table', Parametro, 'column', prmValor;
END CATCH;



-- TABLA ParametrizacionMetodoAsignacion
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa la definición del método de
 asignación por sede, proceso <b>Historia de Usuario: </b>TRA-084 Administrar
 asignación de solicitudes',
'User', dbo, 'table', ParametrizacionMetodoAsignacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa la definición del método de
 asignación por sede, proceso <b>Historia de Usuario: </b>TRA-084 Administrar
 asignación de solicitudes',
'User', dbo, 'table', ParametrizacionMetodoAsignacion;
END CATCH;


BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del los métodos de asignación de la solicitud',
'User', dbo, 'table', ParametrizacionMetodoAsignacion, 'column', pmaMetodoAsignacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del los métodos de asignación de la solicitud',
'User', dbo, 'table', ParametrizacionMetodoAsignacion, 'column', pmaMetodoAsignacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Nombre de usuario',
'User', dbo, 'table', ParametrizacionMetodoAsignacion, 'column', pmaUsuario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Nombre de usuario',
'User', dbo, 'table', ParametrizacionMetodoAsignacion, 'column', pmaUsuario;
END CATCH;



-- TABLA Constante
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa constantes del sistema que 
 determinan comportamientos específicos de la aplicación.
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', Constante;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa constantes del sistema que 
 determinan comportamientos específicos de la aplicación.
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', Constante;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la constante',
'User', dbo, 'table', Constante, 'column', cnsId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la constante',
'User', dbo, 'table', Constante, 'column', cnsId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del nombre de la constante',
'User', dbo, 'table', Constante, 'column', cnsNombre;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del nombre de la constante',
'User', dbo, 'table', Constante, 'column', cnsNombre;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Valor de la constante',
'User', dbo, 'table', Constante, 'column', cnsValor;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Valor de la constante',
'User', dbo, 'table', Constante, 'column', cnsValor;
END CATCH;



-- TABLA OcupacionProfesion
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa las diferentes Aseguradoras de 
 Riesgos Laborales a las que se afilian los empleadores
 <b>Historia de Usuario: </b>121-107',
'User', dbo, 'table', OcupacionProfesion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa las diferentes Aseguradoras de 
 Riesgos Laborales a las que se afilian los empleadores
 <b>Historia de Usuario: </b>121-107',
'User', dbo, 'table', OcupacionProfesion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la ocupación o profesión',
'User', dbo, 'table', OcupacionProfesion, 'column', ocuId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la ocupación o profesión',
'User', dbo, 'table', OcupacionProfesion, 'column', ocuId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Nombre de la ocupación o profesión',
'User', dbo, 'table', OcupacionProfesion, 'column', ocuNombre;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Nombre de la ocupación o profesión',
'User', dbo, 'table', OcupacionProfesion, 'column', ocuNombre;
END CATCH;



-- TABLA ListaEspecialRevision
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa la lista especial de revisión de los 
 empleadores que han sido negados y puestos en lista 
 <b>Historia de Usuario: </b>121-107',
'User', dbo, 'table', ListaEspecialRevision;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa la lista especial de revisión de los 
 empleadores que han sido negados y puestos en lista 
 <b>Historia de Usuario: </b>121-107',
'User', dbo, 'table', ListaEspecialRevision;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la lista especial de revisión',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la lista especial de revisión',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de identificación del empleador',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerTipoIdentificacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de identificación del empleador',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerTipoIdentificacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Número de identificacion del empleador',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerNumeroIdentificacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Número de identificacion del empleador',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerNumeroIdentificacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Digito de verificación',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerDigitoVerificacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Digito de verificación',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerDigitoVerificacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de la caja de compensación que puso en lista de revisión al empleador',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerCajaCompensacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de la caja de compensación que puso en lista de revisión al empleador',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerCajaCompensacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Nombre o Razón social del empleador',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerNombreEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Nombre o Razón social del empleador',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerNombreEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de inicio de inclusión',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerFechaInicioInclusion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de inicio de inclusión',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerFechaInicioInclusion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha fin de inclusión',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerFechaFinInclusion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha fin de inclusión',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerFechaFinInclusion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de la razón de inclusión',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerRazonInclusion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de la razón de inclusión',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerRazonInclusion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado del empleador dentro de la lista de revisión',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerEstado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado del empleador dentro de la lista de revisión',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerEstado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del comentario',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerComentario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del comentario',
'User', dbo, 'table', ListaEspecialRevision, 'column', lerComentario;
END CATCH;



-- TABLA ARL
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa las diferentes Aseguradoras de
 Riesgos Laborales a las que se afilian los empleadores <b>Historia de
 Usuario: </b>Transversal',
'User', dbo, 'table', ARL;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa las diferentes Aseguradoras de
 Riesgos Laborales a las que se afilian los empleadores <b>Historia de
 Usuario: </b>Transversal',
'User', dbo, 'table', ARL;
END CATCH;


BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la administradora de riesgos laborales',
'User', dbo, 'table', ARL, 'column', arlId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la administradora de riesgos laborales',
'User', dbo, 'table', ARL, 'column', arlId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del nombre de la administradora de riesgos laborales',
'User', dbo, 'table', ARL, 'column', arlNombre;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del nombre de la administradora de riesgos laborales',
'User', dbo, 'table', ARL, 'column', arlNombre;
END CATCH;



-- TABLA AFP
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa las diferentes Aseguradoras de 
Fondos de Pensiones del país
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', AFP;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa las diferentes Aseguradoras de 
Fondos de Pensiones del país
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', AFP;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la administradora de fondo de pensiones (AFP)',
'User', dbo, 'table', AFP, 'column', afpId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la administradora de fondo de pensiones (AFP)',
'User', dbo, 'table', AFP, 'column', afpId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del nombre de la administradora de fondo de pensiones (AFP)',
'User', dbo, 'table', AFP, 'column', afpNombre;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del nombre de la administradora de fondo de pensiones (AFP)',
'User', dbo, 'table', AFP, 'column', afpNombre;
END CATCH;



-- TABLA SolicitudAsociacionPersonaEntidadPagadora
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que registra las solicitudes de asociación de
 personas a entidades pagadoras
 <b>Historia de Usuario: </b>121-109',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que registra las solicitudes de asociación de
 personas a entidades pagadoras
 <b>Historia de Usuario: </b>121-109',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la solicitud de asociación de la entidad pagadora',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la solicitud de asociación de la entidad pagadora',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la solicitud global asociada al solicitud de asociación de la entidad pagadora',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaSolicitudGlobal;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la solicitud global asociada al solicitud de asociación de la entidad pagadora',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaSolicitudGlobal;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al rol afiliado asociada al solicitud de asociación de la entidad pagadora',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaRolAfiliado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al rol afiliado asociada al solicitud de asociación de la entidad pagadora',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaRolAfiliado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado de solicitud',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaEstado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado de solicitud',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaEstado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de solicitud de la asociación',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaTipoGestion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de solicitud de la asociación',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaTipoGestion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de gestión de la solicitud de asociación de la entidad pagadora',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaFechaGestion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de gestión de la solicitud de asociación de la entidad pagadora',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaFechaGestion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de número consecutivo',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaConsecutivo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de número consecutivo',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaConsecutivo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica el archivo plano asociado a la solicitud de asociación  de la entidad pagadora',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaIdentificadorArchivoPlano;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica el archivo plano asociado a la solicitud de asociación  de la entidad pagadora',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaIdentificadorArchivoPlano;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica el archivo carta asociado a la solicitud de asociación  de la entidad pagadora',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaIdentificadorArchivoCarta;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica el archivo carta asociado a la solicitud de asociación  de la entidad pagadora',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaIdentificadorArchivoCarta;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica el archivo carta asociado a la solicitud de asociación  de la entidad pagadora',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaIdentificadorCartaResultadoGestion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica el archivo carta asociado a la solicitud de asociación  de la entidad pagadora',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaIdentificadorCartaResultadoGestion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Usuario de gestión',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaUsuarioGestion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Usuario de gestión',
'User', dbo, 'table', SolicitudAsociacionPersonaEntidadPagadora, 'column', soaUsuarioGestion;
END CATCH;



-- TABLA SolicitudAfiliacionPersona
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que registra las solicitudes de afiliación de
 personas
 <b>Historia de Usuario: </b>121-104 Digitar datos identificación persona',
'User', dbo, 'table', SolicitudAfiliacionPersona;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que registra las solicitudes de afiliación de
 personas
 <b>Historia de Usuario: </b>121-104 Digitar datos identificación persona',
'User', dbo, 'table', SolicitudAfiliacionPersona;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de la solicitud de afiliación de la persona',
'User', dbo, 'table', SolicitudAfiliacionPersona, 'column', sapId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de la solicitud de afiliación de la persona',
'User', dbo, 'table', SolicitudAfiliacionPersona, 'column', sapId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica la solicitud asociada a la afiliación de la persona',
'User', dbo, 'table', SolicitudAfiliacionPersona, 'column', sapSolicitudGlobal;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica la solicitud asociada a la afiliación de la persona',
'User', dbo, 'table', SolicitudAfiliacionPersona, 'column', sapSolicitudGlobal;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica el rol afiliado  asociada a la afiliación de la persona',
'User', dbo, 'table', SolicitudAfiliacionPersona, 'column', sapRolAfiliado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica el rol afiliado  asociada a la afiliación de la persona',
'User', dbo, 'table', SolicitudAfiliacionPersona, 'column', sapRolAfiliado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado de la solicitud',
'User', dbo, 'table', SolicitudAfiliacionPersona, 'column', sapEstadoSolicitud;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado de la solicitud',
'User', dbo, 'table', SolicitudAfiliacionPersona, 'column', sapEstadoSolicitud;
END CATCH;



-- TABLA SolicitudAfiliaciEmpleador
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que registra las solicitudes de afiliación de
 empleadores
 <b>Historia de Usuario: </b>111-066 Radicar solicitudes de afiliación',
'User', dbo, 'table', SolicitudAfiliaciEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que registra las solicitudes de afiliación de
 empleadores
 <b>Historia de Usuario: </b>111-066 Radicar solicitudes de afiliación',
'User', dbo, 'table', SolicitudAfiliaciEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del beneficiario',
'User', dbo, 'table', SolicitudAfiliaciEmpleador, 'column', saeId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del beneficiario',
'User', dbo, 'table', SolicitudAfiliaciEmpleador, 'column', saeId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la solicitud asociada a la solicitud de la afiliación del empleador',
'User', dbo, 'table', SolicitudAfiliaciEmpleador, 'column', saeSolicitudGlobal;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la solicitud asociada a la solicitud de la afiliación del empleador',
'User', dbo, 'table', SolicitudAfiliaciEmpleador, 'column', saeSolicitudGlobal;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código de la etiqueta preimpresa',
'User', dbo, 'table', SolicitudAfiliaciEmpleador, 'column', saeCodigoEtiquetaPreimpresa;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código de la etiqueta preimpresa',
'User', dbo, 'table', SolicitudAfiliaciEmpleador, 'column', saeCodigoEtiquetaPreimpresa;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al empleador asociada a la solicitud de la afiliación del empleador',
'User', dbo, 'table', SolicitudAfiliaciEmpleador, 'column', saeEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al empleador asociada a la solicitud de la afiliación del empleador',
'User', dbo, 'table', SolicitudAfiliaciEmpleador, 'column', saeEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de los estados de la solicitud de afiliación del empleador',
'User', dbo, 'table', SolicitudAfiliaciEmpleador, 'column', saeEstadoSolicitud;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de los estados de la solicitud de afiliación del empleador',
'User', dbo, 'table', SolicitudAfiliaciEmpleador, 'column', saeEstadoSolicitud;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Número de custodía física',
'User', dbo, 'table', SolicitudAfiliaciEmpleador, 'column', saeNumeroCustodiaFisica;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Número de custodía física',
'User', dbo, 'table', SolicitudAfiliaciEmpleador, 'column', saeNumeroCustodiaFisica;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Número de acto administrativo',
'User', dbo, 'table', SolicitudAfiliaciEmpleador, 'column', saeNumeroActoAdministrativo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Número de acto administrativo',
'User', dbo, 'table', SolicitudAfiliaciEmpleador, 'column', saeNumeroActoAdministrativo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de aprobación del consejo',
'User', dbo, 'table', SolicitudAfiliaciEmpleador, 'column', saeFechaAprobacionConsejo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de aprobación del consejo',
'User', dbo, 'table', SolicitudAfiliaciEmpleador, 'column', saeFechaAprobacionConsejo;
END CATCH;



-- TABLA RequisitoAfiliaciEmpleador
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa un requisito que debe ser 
 satisfecho en el proceso de afiliación de empleadores
 <b>Historia de Usuario: </b>111-059 Digitar datos y verficar requisitos',
'User', dbo, 'table', RequisitoAfiliaciEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa un requisito que debe ser 
 satisfecho en el proceso de afiliación de empleadores
 <b>Historia de Usuario: </b>111-059 Digitar datos y verficar requisitos',
'User', dbo, 'table', RequisitoAfiliaciEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del requisito de afiliación del empleador',
'User', dbo, 'table', RequisitoAfiliaciEmpleador, 'column', raeId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del requisito de afiliación del empleador',
'User', dbo, 'table', RequisitoAfiliaciEmpleador, 'column', raeId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica el requisito asociada al requisito de afiliación del empleador',
'User', dbo, 'table', RequisitoAfiliaciEmpleador, 'column', raeRequisito;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica el requisito asociada al requisito de afiliación del empleador',
'User', dbo, 'table', RequisitoAfiliaciEmpleador, 'column', raeRequisito;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al empleador asociada al requisito de afiliación del empleador',
'User', dbo, 'table', RequisitoAfiliaciEmpleador, 'column', raeEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al empleador asociada al requisito de afiliación del empleador',
'User', dbo, 'table', RequisitoAfiliaciEmpleador, 'column', raeEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica el documento adjunto',
'User', dbo, 'table', RequisitoAfiliaciEmpleador, 'column', raeIdentificaDocumentoAdjunto;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica el documento adjunto',
'User', dbo, 'table', RequisitoAfiliaciEmpleador, 'column', raeIdentificaDocumentoAdjunto;
END CATCH;



-- TABLA ProductoNoConforme
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa un producto no conforme no
 resuelto en la gestión documental del proceso de afiliación de empleadores.
 <b>Historia de Usuario: </b>',
'User', dbo, 'table', ProductoNoConforme;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa un producto no conforme no
 resuelto en la gestión documental del proceso de afiliación de empleadores.
 <b>Historia de Usuario: </b>',
'User', dbo, 'table', ProductoNoConforme;
END CATCH;


BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Número consecutivo del producto no conforme consolidado',
'User', dbo, 'table', ProductoNoConforme, 'column', pncNumeroConsecutivo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Número consecutivo del producto no conforme consolidado',
'User', dbo, 'table', ProductoNoConforme, 'column', pncNumeroConsecutivo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica la solicitud asociada al producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncSolicitud;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica la solicitud asociada al producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncSolicitud;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al beneficiario asociado al producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncBeneficiario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al beneficiario asociado al producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncBeneficiario;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado del producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncTipoProductoNoConforme;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado del producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncTipoProductoNoConforme;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de error no resuelto en la gestión de un producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncTipoError;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de error no resuelto en la gestión de un producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncTipoError;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del grupo de datos de la solicitud del producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncSeccion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del grupo de datos de la solicitud del producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncSeccion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de la agrupación de datos de la solicitud del producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncGrupoCampos;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de la agrupación de datos de la solicitud del producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncGrupoCampos;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del nombre del campo donde se encontró el producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncCampo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del nombre del campo donde se encontró el producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncCampo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si campo es oblitario',
'User', dbo, 'table', ProductoNoConforme, 'column', pncCampoObligatorio;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si campo es oblitario',
'User', dbo, 'table', ProductoNoConforme, 'column', pncCampoObligatorio;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si el producto no conforme es subsanable',
'User', dbo, 'table', ProductoNoConforme, 'column', pncSubsanable;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si el producto no conforme es subsanable',
'User', dbo, 'table', ProductoNoConforme, 'column', pncSubsanable;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncDescripcion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncDescripcion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de los posibles resultados de la gestión de productos no conforme.',
'User', dbo, 'table', ProductoNoConforme, 'column', pncResultadoGestion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de los posibles resultados de la gestión de productos no conforme.',
'User', dbo, 'table', ProductoNoConforme, 'column', pncResultadoGestion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Usuario front',
'User', dbo, 'table', ProductoNoConforme, 'column', pncUsuarioFront;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Usuario front',
'User', dbo, 'table', ProductoNoConforme, 'column', pncUsuarioFront;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del valor del front sobre producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncValorCampoFront;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del valor del front sobre producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncValorCampoFront;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Comentario del front',
'User', dbo, 'table', ProductoNoConforme, 'column', pncComentariosFront;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Comentario del front',
'User', dbo, 'table', ProductoNoConforme, 'column', pncComentariosFront;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Comentarios del back',
'User', dbo, 'table', ProductoNoConforme, 'column', pncComentariosBack;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Comentarios del back',
'User', dbo, 'table', ProductoNoConforme, 'column', pncComentariosBack;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Usuario back',
'User', dbo, 'table', ProductoNoConforme, 'column', pncUsuarioBack;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Usuario back',
'User', dbo, 'table', ProductoNoConforme, 'column', pncUsuarioBack;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del valor del back sobre el producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncValorCampoBack;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del valor del back sobre el producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncValorCampoBack;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de creación del producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncFechaCreacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de creación del producto no conforme',
'User', dbo, 'table', ProductoNoConforme, 'column', pncFechaCreacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica el documento de soporte de gestión',
'User', dbo, 'table', ProductoNoConforme, 'column', pncDocumentoSoporteGestion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica el documento de soporte de gestión',
'User', dbo, 'table', ProductoNoConforme, 'column', pncDocumentoSoporteGestion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del resultados de la gestión de productos no conforme, de la revisión del back',
'User', dbo, 'table', ProductoNoConforme, 'column', pncResultadoRevisionBack;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del resultados de la gestión de productos no conforme, de la revisión del back',
'User', dbo, 'table', ProductoNoConforme, 'column', pncResultadoRevisionBack;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del segundo comentario del back',
'User', dbo, 'table', ProductoNoConforme, 'column', pncComentariosBack2;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del segundo comentario del back',
'User', dbo, 'table', ProductoNoConforme, 'column', pncComentariosBack2;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del segundo resultado de la gestión de productos no conforme, de la revisión del back',
'User', dbo, 'table', ProductoNoConforme, 'column', pncResultadoRevisionBack2;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del segundo resultado de la gestión de productos no conforme, de la revisión del back',
'User', dbo, 'table', ProductoNoConforme, 'column', pncResultadoRevisionBack2;
END CATCH;



-- TABLA ItemChequeoAfiliEmpleador
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa un requisito que debe ser 
 satisfecho en el proceso de afiliación de empleadores para cumplir con la 
 lista de chequeo
 <b>Historia de Usuario: </b>111-059 Digitar datos y verficar requisitos',
'User', dbo, 'table', ItemChequeoAfiliEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa un requisito que debe ser 
 satisfecho en el proceso de afiliación de empleadores para cumplir con la 
 lista de chequeo
 <b>Historia de Usuario: </b>111-059 Digitar datos y verficar requisitos',
'User', dbo, 'table', ItemChequeoAfiliEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del Item chequeo de la afiliación del empleador',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del Item chequeo de la afiliación del empleador',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de la solicitud de afiliación del empleador',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichSolicitudAfiliaciEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de la solicitud de afiliación del empleador',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichSolicitudAfiliaciEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al afiliado asociada al beneficiario',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichRequisitoAfiliaciEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al afiliado asociada al beneficiario',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichRequisitoAfiliaciEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado de obligatoriedad de los requisitos de documentación',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichEstadoRequisito;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado de obligatoriedad de los requisitos de documentación',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichEstadoRequisito;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si el item chequeo de afiliación de empleador fue precargado[S=Si N=No]',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichPrecargado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si el item chequeo de afiliación de empleador fue precargado[S=Si N=No]',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichPrecargado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si el item chequeo de afiliación de empleador cumple con los requisitos[S=Si N=No]',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichCumpleRequisito;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si el item chequeo de afiliación de empleador cumple con los requisitos[S=Si N=No]',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichCumpleRequisito;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de formato de entregado de documentos',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichFormatoEntregaDocumento;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de formato de entregado de documentos',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichFormatoEntregaDocumento;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de los comentarios',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichComentarios;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de los comentarios',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichComentarios;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si el ítem chequeo de afiliación de empleador cumple con los requisitos del back [S=Si N=No]',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichCumpleRequisitoBack;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si el ítem chequeo de afiliación de empleador cumple con los requisitos del back [S=Si N=No]',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichCumpleRequisitoBack;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de los comentarios del back',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichComentariosBack;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de los comentarios del back',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichComentariosBack;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de nombres de los requisitos',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichNombreRequisito;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de nombres de los requisitos',
'User', dbo, 'table', ItemChequeoAfiliEmpleador, 'column', ichNombreRequisito;
END CATCH;



-- TABLA ItemChequeo
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa un requisito que debe ser 
 satisfecho en los procesos de afiliación para cumplir con la lista de chequeo
 <b>Historia de Usuario: </b>111-059, 121-139',
'User', dbo, 'table', ItemChequeo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa un requisito que debe ser 
 satisfecho en los procesos de afiliación para cumplir con la lista de chequeo
 <b>Historia de Usuario: </b>111-059, 121-139',
'User', dbo, 'table', ItemChequeo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del Item chequeo',
'User', dbo, 'table', ItemChequeo, 'column', ichId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del Item chequeo',
'User', dbo, 'table', ItemChequeo, 'column', ichId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la solicitud global asociada al item chequeo',
'User', dbo, 'table', ItemChequeo, 'column', ichSolicitud;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la solicitud global asociada al item chequeo',
'User', dbo, 'table', ItemChequeo, 'column', ichSolicitud;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al requisito asociado al item chequeo',
'User', dbo, 'table', ItemChequeo, 'column', ichRequisito;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al requisito asociado al item chequeo',
'User', dbo, 'table', ItemChequeo, 'column', ichRequisito;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la persona asociado al item chequeo',
'User', dbo, 'table', ItemChequeo, 'column', ichPersona;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la persona asociado al item chequeo',
'User', dbo, 'table', ItemChequeo, 'column', ichPersona;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al documento asoaciado al item chequeo',
'User', dbo, 'table', ItemChequeo, 'column', ichIdentificadorDocumento;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al documento asoaciado al item chequeo',
'User', dbo, 'table', ItemChequeo, 'column', ichIdentificadorDocumento;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Versión del documento',
'User', dbo, 'table', ItemChequeo, 'column', ichVersionDocumento;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Versión del documento',
'User', dbo, 'table', ItemChequeo, 'column', ichVersionDocumento;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción los estados la obligatoriedad de los requisitos',
'User', dbo, 'table', ItemChequeo, 'column', ichEstadoRequisito;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción los estados la obligatoriedad de los requisitos',
'User', dbo, 'table', ItemChequeo, 'column', ichEstadoRequisito;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si el item de chequeo fue precargado[S=Si N=No]',
'User', dbo, 'table', ItemChequeo, 'column', ichPrecargado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si el item de chequeo fue precargado[S=Si N=No]',
'User', dbo, 'table', ItemChequeo, 'column', ichPrecargado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si el item chequeo cumple con el requisito[S=Si N=No]',
'User', dbo, 'table', ItemChequeo, 'column', ichCumpleRequisito;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si el item chequeo cumple con el requisito[S=Si N=No]',
'User', dbo, 'table', ItemChequeo, 'column', ichCumpleRequisito;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de formatos de entrega de documentos',
'User', dbo, 'table', ItemChequeo, 'column', ichFormatoEntregaDocumento;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de formatos de entrega de documentos',
'User', dbo, 'table', ItemChequeo, 'column', ichFormatoEntregaDocumento;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de los comentarios',
'User', dbo, 'table', ItemChequeo, 'column', ichComentarios;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de los comentarios',
'User', dbo, 'table', ItemChequeo, 'column', ichComentarios;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si el item chequeo cumple con los requisitos del back [S=Si N=No]',
'User', dbo, 'table', ItemChequeo, 'column', ichCumpleRequisitoBack;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si el item chequeo cumple con los requisitos del back [S=Si N=No]',
'User', dbo, 'table', ItemChequeo, 'column', ichCumpleRequisitoBack;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de los comentarios del back',
'User', dbo, 'table', ItemChequeo, 'column', ichComentariosBack;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de los comentarios del back',
'User', dbo, 'table', ItemChequeo, 'column', ichComentariosBack;
END CATCH;



-- TABLA IntentoAfiliRequisito
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que registra los intentos de afiliación no 
 exitosos
 <b>Historia de Usuario: </b>111-066 Radicar solicitudes afiliación y activar 
 empleador',
'User', dbo, 'table', IntentoAfiliRequisito;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que registra los intentos de afiliación no 
 exitosos
 <b>Historia de Usuario: </b>111-066 Radicar solicitudes afiliación y activar 
 empleador',
'User', dbo, 'table', IntentoAfiliRequisito;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del intento de afiliación requisito',
'User', dbo, 'table', IntentoAfiliRequisito, 'column', iarId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del intento de afiliación requisito',
'User', dbo, 'table', IntentoAfiliRequisito, 'column', iarId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica el intento de afiliación asociada al requisito',
'User', dbo, 'table', IntentoAfiliRequisito, 'column', iarIntentoAfiliacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica el intento de afiliación asociada al requisito',
'User', dbo, 'table', IntentoAfiliRequisito, 'column', iarIntentoAfiliacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'',
'User', dbo, 'table', IntentoAfiliRequisito, 'column', iarRequisito;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '',
'User', dbo, 'table', IntentoAfiliRequisito, 'column', iarRequisito;
END CATCH;



-- TABLA IntentoAfiliacion
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que registra los intentos de afiliación no 
 exitosos
 <b>Historia de Usuario: </b>111-066 Radicar solicitudes afiliación y activar 
 empleador',
'User', dbo, 'table', IntentoAfiliacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que registra los intentos de afiliación no 
 exitosos
 <b>Historia de Usuario: </b>111-066 Radicar solicitudes afiliación y activar 
 empleador',
'User', dbo, 'table', IntentoAfiliacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del intento de afiliación',
'User', dbo, 'table', IntentoAfiliacion, 'column', iafId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del intento de afiliación',
'User', dbo, 'table', IntentoAfiliacion, 'column', iafId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la solicitud asociada al intento de afiliación',
'User', dbo, 'table', IntentoAfiliacion, 'column', iafSolicitud;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la solicitud asociada al intento de afiliación',
'User', dbo, 'table', IntentoAfiliacion, 'column', iafSolicitud;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de la causa por la que un intento de afiliación no resulta exitoso',
'User', dbo, 'table', IntentoAfiliacion, 'column', iafCausaIntentoFalido;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de la causa por la que un intento de afiliación no resulta exitoso',
'User', dbo, 'table', IntentoAfiliacion, 'column', iafCausaIntentoFalido;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de transacción del proceso',
'User', dbo, 'table', IntentoAfiliacion, 'column', iafTipoTransaccion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de transacción del proceso',
'User', dbo, 'table', IntentoAfiliacion, 'column', iafTipoTransaccion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de la sede de caja de compensación',
'User', dbo, 'table', IntentoAfiliacion, 'column', iafSedeCajaCompensacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de la sede de caja de compensación',
'User', dbo, 'table', IntentoAfiliacion, 'column', iafSedeCajaCompensacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de incio del proceso',
'User', dbo, 'table', IntentoAfiliacion, 'column', iafFechaInicioProceso;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de incio del proceso',
'User', dbo, 'table', IntentoAfiliacion, 'column', iafFechaInicioProceso;
END CATCH;


BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha creación',
'User', dbo, 'table', IntentoAfiliacion, 'column', iafFechaCreacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha creación',
'User', dbo, 'table', IntentoAfiliacion, 'column', iafFechaCreacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Usuario de creación',
'User', dbo, 'table', IntentoAfiliacion, 'column', iafUsuarioCreacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Usuario de creación',
'User', dbo, 'table', IntentoAfiliacion, 'column', iafUsuarioCreacion;
END CATCH;



-- TABLA EscalaSoliciAfiliEmpleador
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que registra los escalamientos realizados en
 solicitudes de afiliación de empleador
 <b>Historia de Usuario: </b>TRA-061 Administración general de listas de
 chequeo.',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que registra los escalamientos realizados en
 solicitudes de afiliación de empleador
 <b>Historia de Usuario: </b>TRA-061 Administración general de listas de
 chequeo.',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador;
END CATCH;



BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del escalamiento de las solicitudes de afiliación empleador',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del escalamiento de las solicitudes de afiliación empleador',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la solicitud de afiliación asociada al escalamiento de la solicitud de afiliación del empleador',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeSolicitudAfiliacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la solicitud de afiliación asociada al escalamiento de la solicitud de afiliación del empleador',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeSolicitudAfiliacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del asunto',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeAsunto;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del asunto',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeAsunto;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del escalamiento de la solicitud de afiliación del empleador',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeDescripcion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del escalamiento de la solicitud de afiliación del empleador',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeDescripcion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Usuario destinatario',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeDestinatario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Usuario destinatario',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeDestinatario;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del resultado analista',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeResultadoAnalista;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del resultado analista',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeResultadoAnalista;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de los comentarios del analista',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeComentarioAnalista;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de los comentarios del analista',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeComentarioAnalista;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Identificador del documento del soporte analista',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeIdentifDocumSoporteAnalista;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Identificador del documento del soporte analista',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeIdentifDocumSoporteAnalista;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Usuario de creación',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeUsuarioCreacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Usuario de creación',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeUsuarioCreacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de creación',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeFechaCreacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de creación',
'User', dbo, 'table', EscalaSoliciAfiliEmpleador, 'column', eaeFechaCreacion;
END CATCH;



-- TABLA DocumentoEntidadPagadora
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa los documentos adjuntos de una
 entidad pagadora
 <b>Historia de Usuario: </b>121-133',
'User', dbo, 'table', DocumentoEntidadPagadora;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa los documentos adjuntos de una
 entidad pagadora
 <b>Historia de Usuario: </b>121-133',
'User', dbo, 'table', DocumentoEntidadPagadora;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del documento de la entidad pagadora',
'User', dbo, 'table', DocumentoEntidadPagadora, 'column', dpgId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del documento de la entidad pagadora',
'User', dbo, 'table', DocumentoEntidadPagadora, 'column', dpgId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Identificador del lugar de almacenamiento del documento',
'User', dbo, 'table', DocumentoEntidadPagadora, 'column', dpgIdentificadorDocumento;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Identificador del lugar de almacenamiento del documento',
'User', dbo, 'table', DocumentoEntidadPagadora, 'column', dpgIdentificadorDocumento;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la entidad pagadora asociada al documento',
'User', dbo, 'table', DocumentoEntidadPagadora, 'column', dpgEntidadPagadora;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la entidad pagadora asociada al documento',
'User', dbo, 'table', DocumentoEntidadPagadora, 'column', dpgEntidadPagadora;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de documento',
'User', dbo, 'table', DocumentoEntidadPagadora, 'column', dpgTipoDocumento;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de documento',
'User', dbo, 'table', DocumentoEntidadPagadora, 'column', dpgTipoDocumento;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Nombre del documento',
'User', dbo, 'table', DocumentoEntidadPagadora, 'column', dpgNombreDocumento;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Nombre del documento',
'User', dbo, 'table', DocumentoEntidadPagadora, 'column', dpgNombreDocumento;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Versión del documento',
'User', dbo, 'table', DocumentoEntidadPagadora, 'column', dpgVersionDocumento;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Versión del documento',
'User', dbo, 'table', DocumentoEntidadPagadora, 'column', dpgVersionDocumento;
END CATCH;



-- TABLA DatoTemporalSolicitud
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa los datos temporales de la solicitud
 <b>Historia de Usuario: </b> HU110',
'User', dbo, 'table', DatoTemporalSolicitud;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa los datos temporales de la solicitud
 <b>Historia de Usuario: </b> HU110',
'User', dbo, 'table', DatoTemporalSolicitud;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de los datos temporales de la solicitud',
'User', dbo, 'table', DatoTemporalSolicitud, 'column', dtsId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de los datos temporales de la solicitud',
'User', dbo, 'table', DatoTemporalSolicitud, 'column', dtsId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la solicitud asociada a sus datos temporales',
'User', dbo, 'table', DatoTemporalSolicitud, 'column', dtsSolicitud;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la solicitud asociada a sus datos temporales',
'User', dbo, 'table', DatoTemporalSolicitud, 'column', dtsSolicitud;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Temporal de la solicitud',
'User', dbo, 'table', DatoTemporalSolicitud, 'column', dtsJsonPayload;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Temporal de la solicitud',
'User', dbo, 'table', DatoTemporalSolicitud, 'column', dtsJsonPayload;
END CATCH;



-- TABLA AsesorResponsableEmpleador
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa el asesor responsable empleador',
'User', dbo, 'table', AsesorResponsableEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa el asesor responsable empleador',
'User', dbo, 'table', AsesorResponsableEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del asesor responsable del empleador',
'User', dbo, 'table', AsesorResponsableEmpleador, 'column', areId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del asesor responsable del empleador',
'User', dbo, 'table', AsesorResponsableEmpleador, 'column', areId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del nombre de usuario del asesor responsable',
'User', dbo, 'table', AsesorResponsableEmpleador, 'column', areNombreUsuario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del nombre de usuario del asesor responsable',
'User', dbo, 'table', AsesorResponsableEmpleador, 'column', areNombreUsuario;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si el asesor responsable del empleador es el titular responsable de la afiliación [S=Si N=No]',
'User', dbo, 'table', AsesorResponsableEmpleador, 'column', arePrimario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si el asesor responsable del empleador es el titular responsable de la afiliación [S=Si N=No]',
'User', dbo, 'table', AsesorResponsableEmpleador, 'column', arePrimario;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Identificador del empleador',
'User', dbo, 'table', AsesorResponsableEmpleador, 'column', areEmpleador ;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Identificador del empleador',
'User', dbo, 'table', AsesorResponsableEmpleador, 'column', areEmpleador ;
END CATCH;



-- TABLA ValidacionProceso
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa las diferentes parametrizaciones
 para validaciones de negocio <b>Historia de Usuario: </b>TRA',
'User', dbo, 'table', ValidacionProceso;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa las diferentes parametrizaciones
 para validaciones de negocio <b>Historia de Usuario: </b>TRA',
'User', dbo, 'table', ValidacionProceso;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la validación del proceso',
'User', dbo, 'table', ValidacionProceso, 'column', vapId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la validación del proceso',
'User', dbo, 'table', ValidacionProceso, 'column', vapId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del bloque de validación a ejecutar',
'User', dbo, 'table', ValidacionProceso, 'column', vapBloque;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del bloque de validación a ejecutar',
'User', dbo, 'table', ValidacionProceso, 'column', vapBloque;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de las clasificaciones para las listas de chequeo.',
'User', dbo, 'table', ValidacionProceso, 'column', vapValidacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de las clasificaciones para las listas de chequeo.',
'User', dbo, 'table', ValidacionProceso, 'column', vapValidacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del objeto parametrizado sobre el cuál se realiza la validación',
'User', dbo, 'table', ValidacionProceso, 'column', vapObjetoValidacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del objeto parametrizado sobre el cuál se realiza la validación',
'User', dbo, 'table', ValidacionProceso, 'column', vapObjetoValidacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del proceso al cual pertene el proceso de validación',
'User', dbo, 'table', ValidacionProceso, 'column', vapProceso;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del proceso al cual pertene el proceso de validación',
'User', dbo, 'table', ValidacionProceso, 'column', vapProceso;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado del proceso de validación',
'User', dbo, 'table', ValidacionProceso, 'column', vapEstadoProceso;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado del proceso de validación',
'User', dbo, 'table', ValidacionProceso, 'column', vapEstadoProceso;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'',
'User', dbo, 'table', ValidacionProceso, 'column', vapOrden;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '',
'User', dbo, 'table', ValidacionProceso, 'column', vapOrden;
END CATCH;

-- TABLA RequisitoTipoSolicitante
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que relaciona los requisitos documentales con los
 Tipos de solicitante.
 <b>Historia de Usuario: </b>TRA-061 Administración general de listas de
 chequeo.',
'User', dbo, 'table', RequisitoTipoSolicitante;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que relaciona los requisitos documentales con los
 Tipos de solicitante.
 <b>Historia de Usuario: </b>TRA-061 Administración general de listas de
 chequeo.',
'User', dbo, 'table', RequisitoTipoSolicitante;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del requisito tipo solicitante',
'User', dbo, 'table', RequisitoTipoSolicitante, 'column', rtsId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del requisito tipo solicitante',
'User', dbo, 'table', RequisitoTipoSolicitante, 'column', rtsId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al requisito asociada al requisito tipo solicitante',
'User', dbo, 'table', RequisitoTipoSolicitante, 'column', rtsRequisito;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al requisito asociada al requisito tipo solicitante',
'User', dbo, 'table', RequisitoTipoSolicitante, 'column', rtsRequisito;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del sujeto trámite o tipo solicitante',
'User', dbo, 'table', RequisitoTipoSolicitante, 'column', rtsClasificacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del sujeto trámite o tipo solicitante',
'User', dbo, 'table', RequisitoTipoSolicitante, 'column', rtsClasificacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción tipo de transacción asociado al requisito de tipo solicitante',
'User', dbo, 'table', RequisitoTipoSolicitante, 'column', rtsTipoTransaccion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción tipo de transacción asociado al requisito de tipo solicitante',
'User', dbo, 'table', RequisitoTipoSolicitante, 'column', rtsTipoTransaccion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado de obligatoriedad del requisito a partir del proceso y del tipo solicitante',
'User', dbo, 'table', RequisitoTipoSolicitante, 'column', rtsEstado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado de obligatoriedad del requisito a partir del proceso y del tipo solicitante',
'User', dbo, 'table', RequisitoTipoSolicitante, 'column', rtsEstado;
END CATCH;



-- TABLA RequisitoCajaCompensacion
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que relaciona los Requisito documentales con las 
 cajas de compensación.
 <b>Historia de Usuario: </b>TRA-061 Administración general de listas de 
 chequeo.',
'User', dbo, 'table', RequisitoCajaCompensacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que relaciona los Requisito documentales con las 
 cajas de compensación.
 <b>Historia de Usuario: </b>TRA-061 Administración general de listas de 
 chequeo.',
'User', dbo, 'table', RequisitoCajaCompensacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del requisito de la caja de compensanción',
'User', dbo, 'table', RequisitoCajaCompensacion, 'column', rccId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del requisito de la caja de compensanción',
'User', dbo, 'table', RequisitoCajaCompensacion, 'column', rccId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al requistio asociada al requisito de la caja de compensanción',
'User', dbo, 'table', RequisitoCajaCompensacion, 'column', rccRequisito;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al requistio asociada al requisito de la caja de compensanción',
'User', dbo, 'table', RequisitoCajaCompensacion, 'column', rccRequisito;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica la caja de compensación asociada al requisito de la caja de compensanción',
'User', dbo, 'table', RequisitoCajaCompensacion, 'column', rccCajaCompensacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica la caja de compensación asociada al requisito de la caja de compensanción',
'User', dbo, 'table', RequisitoCajaCompensacion, 'column', rccCajaCompensacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de los estados de los requisitos de la caja de compensación',
'User', dbo, 'table', RequisitoCajaCompensacion, 'column', rccEstado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de los estados de los requisitos de la caja de compensación',
'User', dbo, 'table', RequisitoCajaCompensacion, 'column', rccEstado;
END CATCH;



-- TABLA Requisito
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa un Requisito documental para los
 proceso de la cajas de compensación.
 <b>Historia de Usuario: </b>TRA-061 Administración general de listas de 
 chequeo.',
'User', dbo, 'table', Requisito;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa un Requisito documental para los
 proceso de la cajas de compensación.
 <b>Historia de Usuario: </b>TRA-061 Administración general de listas de 
 chequeo.',
'User', dbo, 'table', Requisito;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del requisito',
'User', dbo, 'table', Requisito, 'column', reqId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del requisito',
'User', dbo, 'table', Requisito, 'column', reqId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del requisito',
'User', dbo, 'table', Requisito, 'column', reqDescripcion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del requisito',
'User', dbo, 'table', Requisito, 'column', reqDescripcion;
END CATCH;



-- TABLA ParametrizacionMedioDePago
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa las diferentes configuraciones 
 posbiles para la definición de medios de pago
 <b>Historia de Usuario: </b>111-066 Radicar solicitudes afiliación y activar 
 empleador',
'User', dbo, 'table', ParametrizacionMedioDePago;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa las diferentes configuraciones 
 posbiles para la definición de medios de pago
 <b>Historia de Usuario: </b>111-066 Radicar solicitudes afiliación y activar 
 empleador',
'User', dbo, 'table', ParametrizacionMedioDePago;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la parametrización del medio de pago',
'User', dbo, 'table', ParametrizacionMedioDePago, 'column', pmpId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la parametrización del medio de pago',
'User', dbo, 'table', ParametrizacionMedioDePago, 'column', pmpId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del medio de pago',
'User', dbo, 'table', ParametrizacionMedioDePago, 'column', pmpMedioPago;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del medio de pago',
'User', dbo, 'table', ParametrizacionMedioDePago, 'column', pmpMedioPago;
END CATCH;



-- TABLA Municipio
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa los municipios de los diferentes
 departamentos <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', Municipio;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa los municipios de los diferentes
 departamentos <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', Municipio;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del municipio',
'User', dbo, 'table', Municipio, 'column', munId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del municipio',
'User', dbo, 'table', Municipio, 'column', munId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador del municipio',
'User', dbo, 'table', Municipio, 'column', munCodigo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador del municipio',
'User', dbo, 'table', Municipio, 'column', munCodigo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del nombre del municipio',
'User', dbo, 'table', Municipio, 'column', munNombre;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del nombre del municipio',
'User', dbo, 'table', Municipio, 'column', munNombre;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al departamento asociada al municipio',
'User', dbo, 'table', Municipio, 'column', munDepartamento;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al departamento asociada al municipio',
'User', dbo, 'table', Municipio, 'column', munDepartamento;
END CATCH;



-- TABLA HistoriaResultadoValidacion
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa las diferentes parametrizaciones
 para validaciones de negocio <b>Historia de Usuario: </b>TRA',
'User', dbo, 'table', HistoriaResultadoValidacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa las diferentes parametrizaciones
 para validaciones de negocio <b>Historia de Usuario: </b>TRA',
'User', dbo, 'table', HistoriaResultadoValidacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la historia resuelta de validación',
'User', dbo, 'table', HistoriaResultadoValidacion, 'column', hrvId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la historia resuelta de validación',
'User', dbo, 'table', HistoriaResultadoValidacion, 'column', hrvId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'',
'User', dbo, 'table', HistoriaResultadoValidacion, 'column', hrvDetalle;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '',
'User', dbo, 'table', HistoriaResultadoValidacion, 'column', hrvDetalle;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción que representa las clasificaciones para las listas de chequeo.',
'User', dbo, 'table', HistoriaResultadoValidacion, 'column', hrvValidacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción que representa las clasificaciones para las listas de chequeo.',
'User', dbo, 'table', HistoriaResultadoValidacion, 'column', hrvValidacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del resultado de la validación',
'User', dbo, 'table', HistoriaResultadoValidacion, 'column', hrvResultado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del resultado de la validación',
'User', dbo, 'table', HistoriaResultadoValidacion, 'column', hrvResultado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica la referencia a los datos con los que se realizó la validación',
'User', dbo, 'table', HistoriaResultadoValidacion, 'column', hrvIdDatosRegistro;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica la referencia a los datos con los que se realizó la validación',
'User', dbo, 'table', HistoriaResultadoValidacion, 'column', hrvIdDatosRegistro;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del los tipos de excepciones funcionales que puede retornar una validación del Framework de validaciones',
'User', dbo, 'table', HistoriaResultadoValidacion, 'column', hrvTipoExepcion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del los tipos de excepciones funcionales que puede retornar una validación del Framework de validaciones',
'User', dbo, 'table', HistoriaResultadoValidacion, 'column', hrvTipoExepcion;
END CATCH;



-- TABLA Departamento
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa los departamentos del país
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', Departamento;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa los departamentos del país
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', Departamento;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del departamento',
'User', dbo, 'table', Departamento, 'column', depId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del departamento',
'User', dbo, 'table', Departamento, 'column', depId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador del departamento',
'User', dbo, 'table', Departamento, 'column', depCodigo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador del departamento',
'User', dbo, 'table', Departamento, 'column', depCodigo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Nombre del departamento',
'User', dbo, 'table', Departamento, 'column', depNombre;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Nombre del departamento',
'User', dbo, 'table', Departamento, 'column', depNombre;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código indicativo del telefono fijo asoaciado al departamento',
'User', dbo, 'table', Departamento, 'column', depIndicativoTelefoniaFija;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código indicativo del telefono fijo asoaciado al departamento',
'User', dbo, 'table', Departamento, 'column', depIndicativoTelefoniaFija;
END CATCH;



-- TABLA DatosRegistroValidacion
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa las diferentes parametrizaciones
 para validaciones de negocio <b>Historia de Usuario: </b>TRA',
'User', dbo, 'table', DatosRegistroValidacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa las diferentes parametrizaciones
 para validaciones de negocio <b>Historia de Usuario: </b>TRA',
'User', dbo, 'table', DatosRegistroValidacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de datos registro validación',
'User', dbo, 'table', DatosRegistroValidacion, 'column', drvId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de datos registro validación',
'User', dbo, 'table', DatosRegistroValidacion, 'column', drvId;
END CATCH;





-- TABLA CodigoCIIU
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Tabla que registra los diferentes códigos CIIU según el 
 DANE
 <b>Historia de Usuario: </b>TRA-329 Consultar empleador',
'User', dbo, 'table', CodigoCIIU;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Tabla que registra los diferentes códigos CIIU según el 
 DANE
 <b>Historia de Usuario: </b>TRA-329 Consultar empleador',
'User', dbo, 'table', CodigoCIIU;
END CATCH;


BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del Codigo CIIU',
'User', dbo, 'table', CodigoCIIU, 'column', ciiId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del Codigo CIIU',
'User', dbo, 'table', CodigoCIIU, 'column', ciiId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código único de CIIU',
'User', dbo, 'table', CodigoCIIU, 'column', ciiCodigo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código único de CIIU',
'User', dbo, 'table', CodigoCIIU, 'column', ciiCodigo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del código CIUU',
'User', dbo, 'table', CodigoCIIU, 'column', ciiDescripcion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del código CIUU',
'User', dbo, 'table', CodigoCIIU, 'column', ciiDescripcion;
END CATCH;



-- TABLA CajaCompensacion
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa la información de las cajas de compensación
 
 <b>Historia de Usuario: </b>TRA',
'User', dbo, 'table', CajaCompensacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa la información de las cajas de compensación
 
 <b>Historia de Usuario: </b>TRA',
'User', dbo, 'table', CajaCompensacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la caja de compensación',
'User', dbo, 'table', CajaCompensacion, 'column', ccfId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la caja de compensación',
'User', dbo, 'table', CajaCompensacion, 'column', ccfId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Nombre de la Caja de compensación',
'User', dbo, 'table', CajaCompensacion, 'column', ccfNombre;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Nombre de la Caja de compensación',
'User', dbo, 'table', CajaCompensacion, 'column', ccfNombre;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al departamento asociado a la caja de compensación',
'User', dbo, 'table', CajaCompensacion, 'column', ccfDepartamento;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al departamento asociado a la caja de compensación',
'User', dbo, 'table', CajaCompensacion, 'column', ccfDepartamento;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si la caja de compensación se encuentra habilitado',
'User', dbo, 'table', CajaCompensacion, 'column', ccfHabilitado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si la caja de compensación se encuentra habilitado',
'User', dbo, 'table', CajaCompensacion, 'column', ccfHabilitado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si la caja de compensación es socio de Asopagos',
'User', dbo, 'table', CajaCompensacion, 'column', ccfSocioAsopagos;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si la caja de compensación es socio de Asopagos',
'User', dbo, 'table', CajaCompensacion, 'column', ccfSocioAsopagos;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del método [GENERADA,PREIMPRESA]de generación de etiquetas',
'User', dbo, 'table', CajaCompensacion, 'column', ccfMetodoGeneracionEtiquetas;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del método [GENERADA,PREIMPRESA]de generación de etiquetas',
'User', dbo, 'table', CajaCompensacion, 'column', ccfMetodoGeneracionEtiquetas;
END CATCH;



-- TABLA VariableComunicado
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa las plantillas posibles para la 
 edición y envío de comunicados
 <b>Historia de Usuario: </b>TRA-331 Editar comunicado',
'User', dbo, 'table', VariableComunicado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa las plantillas posibles para la 
 edición y envío de comunicados
 <b>Historia de Usuario: </b>TRA-331 Editar comunicado',
'User', dbo, 'table', VariableComunicado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la variable asociado a las plantilla',
'User', dbo, 'table', VariableComunicado, 'column', vcoId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la variable asociado a las plantilla',
'User', dbo, 'table', VariableComunicado, 'column', vcoId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la plantilla asociada al comunicado',
'User', dbo, 'table', VariableComunicado, 'column', vcoPlantillaComunicado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la plantilla asociada al comunicado',
'User', dbo, 'table', VariableComunicado, 'column', vcoPlantillaComunicado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del nombre de la variable asociado a la plantilla',
'User', dbo, 'table', VariableComunicado, 'column', vcoNombre;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del nombre de la variable asociado a la plantilla',
'User', dbo, 'table', VariableComunicado, 'column', vcoNombre;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de la variable comunicado',
'User', dbo, 'table', VariableComunicado, 'column', vcoDescripcion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de la variable comunicado',
'User', dbo, 'table', VariableComunicado, 'column', vcoDescripcion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del nombre que se usa en la plantilla para el uso de la variable',
'User', dbo, 'table', VariableComunicado, 'column', vcoClave;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del nombre que se usa en la plantilla para el uso de la variable',
'User', dbo, 'table', VariableComunicado, 'column', vcoClave;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Nombre de la constante',
'User', dbo, 'table', VariableComunicado, 'column', vcoNombreConstante;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Nombre de la constante',
'User', dbo, 'table', VariableComunicado, 'column', vcoNombreConstante;
END CATCH;



-- TABLA PlantillaComunicado
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa las plantillas posibles para la 
 edición y envío de comunicados
 <b>Historia de Usuario: </b>TRA-331 Editar comunicado',
'User', dbo, 'table', PlantillaComunicado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa las plantillas posibles para la 
 edición y envío de comunicados
 <b>Historia de Usuario: </b>TRA-331 Editar comunicado',
'User', dbo, 'table', PlantillaComunicado;
END CATCH;


BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la plantilla comunicado',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la plantilla comunicado',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Nombre de la plantilla del comunicado',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoNombre;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Nombre de la plantilla del comunicado',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoNombre;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de etiqueta de las plantillas de comunicado personalizadas por la caja de compensación',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoEtiqueta;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de etiqueta de las plantillas de comunicado personalizadas por la caja de compensación',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoEtiqueta;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del asunto de la plantilla comunicado',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoAsunto;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del asunto de la plantilla comunicado',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoAsunto;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del mensaje de la plantilla comunicado',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoMensaje;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del mensaje de la plantilla comunicado',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoMensaje;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del encabezado de la plantilla del comunicado',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoEncabezado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del encabezado de la plantilla del comunicado',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoEncabezado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del cuerpo de la plantilla del comunicado',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoCuerpo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del cuerpo de la plantilla del comunicado',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoCuerpo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del pie de página de la plantilla comunicado',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoPie;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del pie de página de la plantilla comunicado',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoPie;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador del pie de imagen de la plantilla comunicado',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoIdentificadorImagenPie;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador del pie de imagen de la plantilla comunicado',
'User', dbo, 'table', PlantillaComunicado, 'column', pcoIdentificadorImagenPie;
END CATCH;



-- TABLA ParametrizaEnvioComunicado
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que reprsenta las parametrizaciones almacenadas
 para el envío de comunicados.
 <b>Historia de Usuario: </b>TRA-073 Administrar plantillas de comunicado',
'User', dbo, 'table', ParametrizaEnvioComunicado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que reprsenta las parametrizaciones almacenadas
 para el envío de comunicados.
 <b>Historia de Usuario: </b>TRA-073 Administrar plantillas de comunicado',
'User', dbo, 'table', ParametrizaEnvioComunicado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la parametrizaciones almacenadas para envío de comunicados',
'User', dbo, 'table', ParametrizaEnvioComunicado, 'column', pecId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la parametrizaciones almacenadas para envío de comunicados',
'User', dbo, 'table', ParametrizaEnvioComunicado, 'column', pecId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del proceso del negocio',
'User', dbo, 'table', ParametrizaEnvioComunicado, 'column', pecProceso;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del proceso del negocio',
'User', dbo, 'table', ParametrizaEnvioComunicado, 'column', pecProceso;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del de los tipos de correo de acuerdo a la parametrización',
'User', dbo, 'table', ParametrizaEnvioComunicado, 'column', pecTipoCorreo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del de los tipos de correo de acuerdo a la parametrización',
'User', dbo, 'table', ParametrizaEnvioComunicado, 'column', pecTipoCorreo;
END CATCH;



-- TABLA ComunicadoImpreso
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa la traza que se almacena cuando se
 realiza la impresión de un comunicado.
 <b>Historia de Usuario: </b>TRA-331 Editar comunicado',
'User', dbo, 'table', ComunicadoImpreso;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa la traza que se almacena cuando se
 realiza la impresión de un comunicado.
 <b>Historia de Usuario: </b>TRA-331 Editar comunicado',
'User', dbo, 'table', ComunicadoImpreso;
END CATCH;


BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del comunicado enviado',
'User', dbo, 'table', ComunicadoImpreso, 'column', coiId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del comunicado enviado',
'User', dbo, 'table', ComunicadoImpreso, 'column', coiId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al comunicado asociada al comunicado impreso',
'User', dbo, 'table', ComunicadoImpreso, 'column', coiComunicado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al comunicado asociada al comunicado impreso',
'User', dbo, 'table', ComunicadoImpreso, 'column', coiComunicado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de impresión del comunicado',
'User', dbo, 'table', ComunicadoImpreso, 'column', coiFechaImpresion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de impresión del comunicado',
'User', dbo, 'table', ComunicadoImpreso, 'column', coiFechaImpresion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Usuario remitente del comunicado',
'User', dbo, 'table', ComunicadoImpreso, 'column', coiRemitente;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Usuario remitente del comunicado',
'User', dbo, 'table', ComunicadoImpreso, 'column', coiRemitente;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código de la sede de la caja de compensación',
'User', dbo, 'table', ComunicadoImpreso, 'column', coiSedeCajaCompensacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código de la sede de la caja de compensación',
'User', dbo, 'table', ComunicadoImpreso, 'column', coiSedeCajaCompensacion;
END CATCH;



-- TABLA ComunicadoEnviado
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa la traza que se almacena cuando se
 realiza el envío de un comunicado.
 <b>Historia de Usuario: </b>TRA-331 Editar comunicado',
'User', dbo, 'table', ComunicadoEnviado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa la traza que se almacena cuando se
 realiza el envío de un comunicado.
 <b>Historia de Usuario: </b>TRA-331 Editar comunicado',
'User', dbo, 'table', ComunicadoEnviado;
END CATCH;


BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del comunicado enviado',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del comunicado enviado',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al comunicado asociada al comunicado enviado',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeComunicado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al comunicado asociada al comunicado enviado',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeComunicado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica el número de la notificación enviada asoaciado al comunicado',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeNumeroCorreoMasivo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica el número de la notificación enviada asoaciado al comunicado',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeNumeroCorreoMasivo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de envío del comunicado',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeFechaEnvio;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de envío del comunicado',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeFechaEnvio;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del mensaje enviado',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeMensajeEnvio;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del mensaje enviado',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeMensajeEnvio;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Estado del comunicado enviado',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeEstadoEnvio;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Estado del comunicado enviado',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeEstadoEnvio;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Usuario destinatario',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeDestinatario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Usuario destinatario',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeDestinatario;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Usuario remitente',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeRemitente;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Usuario remitente',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeRemitente;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código de la sede de la caja de compensación',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeSedeCajaCompensacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código de la sede de la caja de compensación',
'User', dbo, 'table', ComunicadoEnviado, 'column', coeSedeCajaCompensacion;
END CATCH;



-- TABLA Comunicado
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa un comunicado
 <b>Historia de Usuario: </b>TRA-331 Editar comunicado',
'User', dbo, 'table', Comunicado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa un comunicado
 <b>Historia de Usuario: </b>TRA-331 Editar comunicado',
'User', dbo, 'table', Comunicado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del comunicado',
'User', dbo, 'table', Comunicado, 'column', comId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del comunicado',
'User', dbo, 'table', Comunicado, 'column', comId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la solicitud asoaciado al comunicado',
'User', dbo, 'table', Comunicado, 'column', comSolicitud;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la solicitud asoaciado al comunicado',
'User', dbo, 'table', Comunicado, 'column', comSolicitud;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del texto a adicionar al comunicado',
'User', dbo, 'table', Comunicado, 'column', comTextoAdicionar;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del texto a adicionar al comunicado',
'User', dbo, 'table', Comunicado, 'column', comTextoAdicionar;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Dirección de correo electrónico al que se envía el comunicado',
'User', dbo, 'table', Comunicado, 'column', comEmail;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Dirección de correo electrónico al que se envía el comunicado',
'User', dbo, 'table', Comunicado, 'column', comEmail;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la plantilla asoaciado al comunicado',
'User', dbo, 'table', Comunicado, 'column', comPlantillaComunicado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la plantilla asoaciado al comunicado',
'User', dbo, 'table', Comunicado, 'column', comPlantillaComunicado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Identificador del archivo asociado al comunicado',
'User', dbo, 'table', Comunicado, 'column', comIdentificaArchivoComunicado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Identificador del archivo asociado al comunicado',
'User', dbo, 'table', Comunicado, 'column', comIdentificaArchivoComunicado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha del comunicado',
'User', dbo, 'table', Comunicado, 'column', comFechaComunicado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha del comunicado',
'User', dbo, 'table', Comunicado, 'column', comFechaComunicado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Usarios remitente del comunicado',
'User', dbo, 'table', Comunicado, 'column', comRemitente;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Usarios remitente del comunicado',
'User', dbo, 'table', Comunicado, 'column', comRemitente;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código de la sede de la caja de compensación',
'User', dbo, 'table', Comunicado, 'column', comSedeCajaCompensacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código de la sede de la caja de compensación',
'User', dbo, 'table', Comunicado, 'column', comSedeCajaCompensacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica el número de la notificación enviada asoaciado al comunicado',
'User', dbo, 'table', Comunicado, 'column', comNumeroCorreoMasivo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica el número de la notificación enviada asoaciado al comunicado',
'User', dbo, 'table', Comunicado, 'column', comNumeroCorreoMasivo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Nombre del destinatario del comunicado',
'User', dbo, 'table', Comunicado, 'column', comDestinatario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Nombre del destinatario del comunicado',
'User', dbo, 'table', Comunicado, 'column', comDestinatario;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado del envío',
'User', dbo, 'table', Comunicado, 'column', comEstadoEnvio;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado del envío',
'User', dbo, 'table', Comunicado, 'column', comEstadoEnvio;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Texto del mensaje del envío',
'User', dbo, 'table', Comunicado, 'column', comMensajeEnvio;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Texto del mensaje del envío',
'User', dbo, 'table', Comunicado, 'column', comMensajeEnvio;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del medio de comunicación',
'User', dbo, 'table', Comunicado, 'column', comMedioComunicado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del medio de comunicación',
'User', dbo, 'table', Comunicado, 'column', comMedioComunicado;
END CATCH;



-- TABLA SocioEmpleador
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa un socio de un empleador
 <b>Historia de Usuario: </b>111-066 Radicar solicitudes afiliación y activar 
 empleador',
'User', dbo, 'table', SocioEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa un socio de un empleador
 <b>Historia de Usuario: </b>111-066 Radicar solicitudes afiliación y activar 
 empleador',
'User', dbo, 'table', SocioEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del Socio empleador',
'User', dbo, 'table', SocioEmpleador, 'column', semId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del Socio empleador',
'User', dbo, 'table', SocioEmpleador, 'column', semId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la persona asociada al socio empleador',
'User', dbo, 'table', SocioEmpleador, 'column', semPersona;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la persona asociada al socio empleador',
'User', dbo, 'table', SocioEmpleador, 'column', semPersona;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al empleador asociada al socio empleador',
'User', dbo, 'table', SocioEmpleador, 'column', semEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al empleador asociada al socio empleador',
'User', dbo, 'table', SocioEmpleador, 'column', semEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al conyuge asociada al socio empleador',
'User', dbo, 'table', SocioEmpleador, 'column', semConyugue;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al conyuge asociada al socio empleador',
'User', dbo, 'table', SocioEmpleador, 'column', semConyugue;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si el socio empleador tiene capitulaciones[S=Si N=No]',
'User', dbo, 'table', SocioEmpleador, 'column', semExistenCapitulaciones;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si el socio empleador tiene capitulaciones[S=Si N=No]',
'User', dbo, 'table', SocioEmpleador, 'column', semExistenCapitulaciones;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador del documento de capitulaciones',
'User', dbo, 'table', SocioEmpleador, 'column', semIdentifiDocumCapitulaciones;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador del documento de capitulaciones',
'User', dbo, 'table', SocioEmpleador, 'column', semIdentifiDocumCapitulaciones;
END CATCH;



-- TABLA RolContactoEmpleador
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa un funcionario de un empleador que
 actúa como persona de contacto ante la caja de compensación
 <b>Historia de Usuario: </b>111-066 Radicar solicitudes afiliación y activar 
 empleador',
'User', dbo, 'table', RolContactoEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa un funcionario de un empleador que
 actúa como persona de contacto ante la caja de compensación
 <b>Historia de Usuario: </b>111-066 Radicar solicitudes afiliación y activar 
 empleador',
'User', dbo, 'table', RolContactoEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del rol contacto empleador',
'User', dbo, 'table', RolContactoEmpleador, 'column', rceId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del rol contacto empleador',
'User', dbo, 'table', RolContactoEmpleador, 'column', rceId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la persona asociada al rol contacto empleador',
'User', dbo, 'table', RolContactoEmpleador, 'column', rcePersona;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la persona asociada al rol contacto empleador',
'User', dbo, 'table', RolContactoEmpleador, 'column', rcePersona;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al empleador asociado al rol contacto empleador',
'User', dbo, 'table', RolContactoEmpleador, 'column', rceEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al empleador asociado al rol contacto empleador',
'User', dbo, 'table', RolContactoEmpleador, 'column', rceEmpleador;
END CATCH;


BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de rol del contacto empleador',
'User', dbo, 'table', RolContactoEmpleador, 'column', rceTipoRolContactoEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de rol del contacto empleador',
'User', dbo, 'table', RolContactoEmpleador, 'column', rceTipoRolContactoEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del cargo de las personas que solicitan Token',
'User', dbo, 'table', RolContactoEmpleador, 'column', rceCargo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del cargo de las personas que solicitan Token',
'User', dbo, 'table', RolContactoEmpleador, 'column', rceCargo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código del token',
'User', dbo, 'table', RolContactoEmpleador, 'column', rceToken;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código del token',
'User', dbo, 'table', RolContactoEmpleador, 'column', rceToken;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indica  si el correo fue enviado',
'User', dbo, 'table', RolContactoEmpleador, 'column', rceCorreoEnviado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indica  si el correo fue enviado',
'User', dbo, 'table', RolContactoEmpleador, 'column', rceCorreoEnviado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la ubicación asociada al contacto empleador',
'User', dbo, 'table', RolContactoEmpleador, 'column', rceUbicacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la ubicación asociada al contacto empleador',
'User', dbo, 'table', RolContactoEmpleador, 'column', rceUbicacion;
END CATCH;



-- TABLA RolAfiliado
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa los posibles roles de afiliación
 que puede tener un afiliado
 <b>Historia de Usuario: </b>121-104',
'User', dbo, 'table', RolAfiliado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa los posibles roles de afiliación
 que puede tener un afiliado
 <b>Historia de Usuario: </b>121-104',
'User', dbo, 'table', RolAfiliado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al afiliado asociada al rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaAfiliado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al afiliado asociada al rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaAfiliado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del sujeto trámite de tipo afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaTipoAfiliado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del sujeto trámite de tipo afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaTipoAfiliado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado del rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaEstadoAfiliado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado del rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaEstadoAfiliado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado activo/inactivo de la entidad pagadora',
'User', dbo, 'table', RolAfiliado, 'column', roaEstadoEnEntidadPagadora;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado activo/inactivo de la entidad pagadora',
'User', dbo, 'table', RolAfiliado, 'column', roaEstadoEnEntidadPagadora;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de las posibles clases de personas independientes',
'User', dbo, 'table', RolAfiliado, 'column', roaClaseIndependiente;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de las posibles clases de personas independientes',
'User', dbo, 'table', RolAfiliado, 'column', roaClaseIndependiente;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al empleador asociada al rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al empleador asociada al rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la sucursal empleador asociada al rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaSucursalEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la sucursal empleador asociada al rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaSucursalEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la administradora de fondo de pensiones asociada al rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaPagadorPension;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la administradora de fondo de pensiones asociada al rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaPagadorPension;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al entidad pagadora asociada al rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaPagadorAportes;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al entidad pagadora asociada al rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaPagadorAportes;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador ante entidad pagadora',
'User', dbo, 'table', RolAfiliado, 'column', roaIdentificadorAnteEntidadPagadora;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador ante entidad pagadora',
'User', dbo, 'table', RolAfiliado, 'column', roaIdentificadorAnteEntidadPagadora;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de ingreso',
'User', dbo, 'table', RolAfiliado, 'column', roaFechaIngreso;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de ingreso',
'User', dbo, 'table', RolAfiliado, 'column', roaFechaIngreso;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de retiro',
'User', dbo, 'table', RolAfiliado, 'column', roaFechaRetiro;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de retiro',
'User', dbo, 'table', RolAfiliado, 'column', roaFechaRetiro;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Valor del salario de la mesada de ingresos',
'User', dbo, 'table', RolAfiliado, 'column', roaValorSalarioMesadaIngresos;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Valor del salario de la mesada de ingresos',
'User', dbo, 'table', RolAfiliado, 'column', roaValorSalarioMesadaIngresos;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Porcentaje del pago de los aportes',
'User', dbo, 'table', RolAfiliado, 'column', roaPorcentajePagoAportes;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Porcentaje del pago de los aportes',
'User', dbo, 'table', RolAfiliado, 'column', roaPorcentajePagoAportes;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de las posibles clases de personas independientes',
'User', dbo, 'table', RolAfiliado, 'column', roaClaseTrabajador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de las posibles clases de personas independientes',
'User', dbo, 'table', RolAfiliado, 'column', roaClaseTrabajador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de salario del rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaTipoSalario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de salario del rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaTipoSalario;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de contrato del rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaTipoContrato;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de contrato del rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaTipoContrato;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del cargo del afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaCargo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del cargo del afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaCargo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Número de horas laboradas por mes del rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaHorasLaboradasMes;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Número de horas laboradas por mes del rol afiliado',
'User', dbo, 'table', RolAfiliado, 'column', roaHorasLaboradasMes;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de la afiliación',
'User', dbo, 'table', RolAfiliado, 'column', roaFechaAfiliacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de la afiliación',
'User', dbo, 'table', RolAfiliado, 'column', roaFechaAfiliacion;
END CATCH;



-- TABLA RelacionGrupoFamiliar 
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa la relación del Grupo Familiar
 <b>Historia de Usuario: </b>-121-108',
'User', dbo, 'table', RelacionGrupoFamiliar ;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa la relación del Grupo Familiar
 <b>Historia de Usuario: </b>-121-108',
'User', dbo, 'table', RelacionGrupoFamiliar ;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la relación grupo familiar',
'User', dbo, 'table', RelacionGrupoFamiliar , 'column', rgfId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la relación grupo familiar',
'User', dbo, 'table', RelacionGrupoFamiliar , 'column', rgfId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del nombre de la relación del grupo familiar',
'User', dbo, 'table', RelacionGrupoFamiliar , 'column', rgfNombre;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del nombre de la relación del grupo familiar',
'User', dbo, 'table', RelacionGrupoFamiliar , 'column', rgfNombre;
END CATCH;



-- TABLA Persona
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa una persona y contiene sus datos
 básicos
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', Persona;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa una persona y contiene sus datos
 básicos
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', Persona;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la persona',
'User', dbo, 'table', Persona, 'column', perId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la persona',
'User', dbo, 'table', Persona, 'column', perId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de identificación de la persona',
'User', dbo, 'table', Persona, 'column', perTipoIdentificacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de identificación de la persona',
'User', dbo, 'table', Persona, 'column', perTipoIdentificacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Número de identificación de la persona',
'User', dbo, 'table', Persona, 'column', perNumeroIdentificacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Número de identificación de la persona',
'User', dbo, 'table', Persona, 'column', perNumeroIdentificacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Digito de verificación de la persona',
'User', dbo, 'table', Persona, 'column', perDigitoVerificacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Digito de verificación de la persona',
'User', dbo, 'table', Persona, 'column', perDigitoVerificacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del primer nombre',
'User', dbo, 'table', Persona, 'column', perPrimerNombre;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del primer nombre',
'User', dbo, 'table', Persona, 'column', perPrimerNombre;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del segundo nombre',
'User', dbo, 'table', Persona, 'column', perSegundoNombre;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del segundo nombre',
'User', dbo, 'table', Persona, 'column', perSegundoNombre;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del primer apellido',
'User', dbo, 'table', Persona, 'column', perPrimerApellido;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del primer apellido',
'User', dbo, 'table', Persona, 'column', perPrimerApellido;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del segundo apellido',
'User', dbo, 'table', Persona, 'column', perSegundoApellido;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del segundo apellido',
'User', dbo, 'table', Persona, 'column', perSegundoApellido;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de la razón social',
'User', dbo, 'table', Persona, 'column', perRazonSocial;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de la razón social',
'User', dbo, 'table', Persona, 'column', perRazonSocial;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la ubicación asociada a la persona',
'User', dbo, 'table', Persona, 'column', perUbicacionPrincipal;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la ubicación asociada a la persona',
'User', dbo, 'table', Persona, 'column', perUbicacionPrincipal;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de nacimiento de la persona',
'User', dbo, 'table', Persona, 'column', perFechaNacimiento;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de nacimiento de la persona',
'User', dbo, 'table', Persona, 'column', perFechaNacimiento;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de expedición del documento de la persona',
'User', dbo, 'table', Persona, 'column', perFechaExpedicionDocumento;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de expedición del documento de la persona',
'User', dbo, 'table', Persona, 'column', perFechaExpedicionDocumento;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del género',
'User', dbo, 'table', Persona, 'column', perGenero;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del género',
'User', dbo, 'table', Persona, 'column', perGenero;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica la ocupación o profesión asociada a la persona',
'User', dbo, 'table', Persona, 'column', perOcupacionProfesion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica la ocupación o profesión asociada a la persona',
'User', dbo, 'table', Persona, 'column', perOcupacionProfesion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del nivel de educación',
'User', dbo, 'table', Persona, 'column', perNivelEducativo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del nivel de educación',
'User', dbo, 'table', Persona, 'column', perNivelEducativo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si la persona es cabeza de hogar',
'User', dbo, 'table', Persona, 'column', perCabezaHogar;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si la persona es cabeza de hogar',
'User', dbo, 'table', Persona, 'column', perCabezaHogar;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si la persona posee casa propia',
'User', dbo, 'table', Persona, 'column', perHabitaCasaPropia;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si la persona posee casa propia',
'User', dbo, 'table', Persona, 'column', perHabitaCasaPropia;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si la persona autoriza el envío de email',
'User', dbo, 'table', Persona, 'column', perAutorizaEnvioEmail;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si la persona autoriza el envío de email',
'User', dbo, 'table', Persona, 'column', perAutorizaEnvioEmail;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si la persona autoriza el uso de los datos personales',
'User', dbo, 'table', Persona, 'column', perAutorizaUsoDatosPersonales;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si la persona autoriza el uso de los datos personales',
'User', dbo, 'table', Persona, 'column', perAutorizaUsoDatosPersonales;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si la persona reside en un sector rural',
'User', dbo, 'table', Persona, 'column', perResideSectorRural;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si la persona reside en un sector rural',
'User', dbo, 'table', Persona, 'column', perResideSectorRural;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica el medio de pago asoaciado a la persona',
'User', dbo, 'table', Persona, 'column', perMedioPago;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica el medio de pago asoaciado a la persona',
'User', dbo, 'table', Persona, 'column', perMedioPago;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado civil de la persona',
'User', dbo, 'table', Persona, 'column', perEstadoCivil;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado civil de la persona',
'User', dbo, 'table', Persona, 'column', perEstadoCivil;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si la persona ha fallecido',
'User', dbo, 'table', Persona, 'column', perfallecido;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si la persona ha fallecido',
'User', dbo, 'table', Persona, 'column', perfallecido;
END CATCH;



-- TABLA GrupoFamiliar
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa los grupos familiares de un 
 afiliado
 <b>Historia de Usuario: </b>121-107',
'User', dbo, 'table', GrupoFamiliar;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa los grupos familiares de un 
 afiliado
 <b>Historia de Usuario: </b>121-107',
'User', dbo, 'table', GrupoFamiliar;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del grupo familiar',
'User', dbo, 'table', GrupoFamiliar, 'column', grfId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del grupo familiar',
'User', dbo, 'table', GrupoFamiliar, 'column', grfId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'',
'User', dbo, 'table', GrupoFamiliar, 'column', grfNumero;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '',
'User', dbo, 'table', GrupoFamiliar, 'column', grfNumero;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al afiliado asociada al grupo familiar',
'User', dbo, 'table', GrupoFamiliar, 'column', grfAfiliado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al afiliado asociada al grupo familiar',
'User', dbo, 'table', GrupoFamiliar, 'column', grfAfiliado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica la ubicación asociada al grupo familiar',
'User', dbo, 'table', GrupoFamiliar, 'column', grfUbicacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica la ubicación asociada al grupo familiar',
'User', dbo, 'table', GrupoFamiliar, 'column', grfUbicacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica el administrador subsidio asociado al grupo familiar',
'User', dbo, 'table', GrupoFamiliar, 'column', grfAdministradorSubsidio;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica el administrador subsidio asociado al grupo familiar',
'User', dbo, 'table', GrupoFamiliar, 'column', grfAdministradorSubsidio;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de las observaciones',
'User', dbo, 'table', GrupoFamiliar, 'column', grfObservaciones;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de las observaciones',
'User', dbo, 'table', GrupoFamiliar, 'column', grfObservaciones;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al medio de pago parametrizado para el grupo familiar',
'User', dbo, 'table', GrupoFamiliar, 'column', grfParametrizacionMedioPago;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al medio de pago parametrizado para el grupo familiar',
'User', dbo, 'table', GrupoFamiliar, 'column', grfParametrizacionMedioPago;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica la relación del grupo familiar',
'User', dbo, 'table', GrupoFamiliar, 'column', grfRelacionGrupoFamiliar;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica la relación del grupo familiar',
'User', dbo, 'table', GrupoFamiliar, 'column', grfRelacionGrupoFamiliar;
END CATCH;



-- TABLA EntidadPagadora
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa una entidad pagadora
 <b>Historia de Usuario: </b>121-104',
'User', dbo, 'table', EntidadPagadora;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa una entidad pagadora
 <b>Historia de Usuario: </b>121-104',
'User', dbo, 'table', EntidadPagadora;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la entidad pagadora',
'User', dbo, 'table', EntidadPagadora, 'column', epaId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la entidad pagadora',
'User', dbo, 'table', EntidadPagadora, 'column', epaId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la empresa asociada a la entidad pagadora',
'User', dbo, 'table', EntidadPagadora, 'column', epaEmpresa;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la empresa asociada a la entidad pagadora',
'User', dbo, 'table', EntidadPagadora, 'column', epaEmpresa;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado de la entidad pagadora',
'User', dbo, 'table', EntidadPagadora, 'column', epaEstadoEntidadPagadora;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado de la entidad pagadora',
'User', dbo, 'table', EntidadPagadora, 'column', epaEstadoEntidadPagadora;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si la entidad pagadora es aportante[S=Si N=No]',
'User', dbo, 'table', EntidadPagadora, 'column', epaAportante;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si la entidad pagadora es aportante[S=Si N=No]',
'User', dbo, 'table', EntidadPagadora, 'column', epaAportante;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del canal de comunicación de la entidad pagadora',
'User', dbo, 'table', EntidadPagadora, 'column', epaCanalComunicacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del canal de comunicación de la entidad pagadora',
'User', dbo, 'table', EntidadPagadora, 'column', epaCanalComunicacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del medio de comunicación de la entidad pagadora',
'User', dbo, 'table', EntidadPagadora, 'column', epaMedioComunicacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del medio de comunicación de la entidad pagadora',
'User', dbo, 'table', EntidadPagadora, 'column', epaMedioComunicacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Correo electrónico  de la entidad pagadora',
'User', dbo, 'table', EntidadPagadora, 'column', epaEmailComunicacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Correo electrónico  de la entidad pagadora',
'User', dbo, 'table', EntidadPagadora, 'column', epaEmailComunicacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Nombre del contacto',
'User', dbo, 'table', EntidadPagadora, 'column', epaNombreContacto;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Nombre del contacto',
'User', dbo, 'table', EntidadPagadora, 'column', epaNombreContacto;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la sucursal pagadora asoaciado a la entidad pagadora',
'User', dbo, 'table', EntidadPagadora, 'column', epaSucursalPagadora;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la sucursal pagadora asoaciado a la entidad pagadora',
'User', dbo, 'table', EntidadPagadora, 'column', epaSucursalPagadora;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de afiliación',
'User', dbo, 'table', EntidadPagadora, 'column', epaTipoAfiliacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de afiliación',
'User', dbo, 'table', EntidadPagadora, 'column', epaTipoAfiliacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Nombre del cargo del contacto',
'User', dbo, 'table', EntidadPagadora, 'column', epaCargoContacto;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Nombre del cargo del contacto',
'User', dbo, 'table', EntidadPagadora, 'column', epaCargoContacto;
END CATCH;



-- TABLA Empresa
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa un empleador que se afilia a la
 caja de compensación. <b>Historia de Usuario: </b>TRA-329 Consultar empleador',
'User', dbo, 'table', Empresa;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa un empleador que se afilia a la
 caja de compensación. <b>Historia de Usuario: </b>TRA-329 Consultar empleador',
'User', dbo, 'table', Empresa;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la empresa',
'User', dbo, 'table', Empresa, 'column', empId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la empresa',
'User', dbo, 'table', Empresa, 'column', empId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la persona asociada a la empresa',
'User', dbo, 'table', Empresa, 'column', empPersona;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la persona asociada a la empresa',
'User', dbo, 'table', Empresa, 'column', empPersona;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Nombre comercial de la empresa',
'User', dbo, 'table', Empresa, 'column', empNombreComercial;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Nombre comercial de la empresa',
'User', dbo, 'table', Empresa, 'column', empNombreComercial;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de constitución de la empresa',
'User', dbo, 'table', Empresa, 'column', empFechaConstitucion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de constitución de la empresa',
'User', dbo, 'table', Empresa, 'column', empFechaConstitucion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de la naturalez jurídica',
'User', dbo, 'table', Empresa, 'column', empNaturalezaJuridica;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de la naturalez jurídica',
'User', dbo, 'table', Empresa, 'column', empNaturalezaJuridica;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código CIIU (Clasificación Industrial Internacional Uniforme de todas las actividades económicas)',
'User', dbo, 'table', Empresa, 'column', empCodigoCIIU;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código CIIU (Clasificación Industrial Internacional Uniforme de todas las actividades económicas)',
'User', dbo, 'table', Empresa, 'column', empCodigoCIIU;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la ARL asociada a la empresa',
'User', dbo, 'table', Empresa, 'column', empArl;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la ARL asociada a la empresa',
'User', dbo, 'table', Empresa, 'column', empArl;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código de la última caja de compensación de la empresa',
'User', dbo, 'table', Empresa, 'column', empUltimaCajaCompensacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código de la última caja de compensación de la empresa',
'User', dbo, 'table', Empresa, 'column', empUltimaCajaCompensacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Página web',
'User', dbo, 'table', Empresa, 'column', empPaginaWeb;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Página web',
'User', dbo, 'table', Empresa, 'column', empPaginaWeb;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al representante legal asociado a la empresa',
'User', dbo, 'table', Empresa, 'column', empRepresentanteLegal;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al representante legal asociado a la empresa',
'User', dbo, 'table', Empresa, 'column', empRepresentanteLegal;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al representante legal suplente asociado a la empresa',
'User', dbo, 'table', Empresa, 'column', empRepresentanteLegalSuplente;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al representante legal suplente asociado a la empresa',
'User', dbo, 'table', Empresa, 'column', empRepresentanteLegalSuplente;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si la empresa cuenta con revisión especial [S=Si N=No]',
'User', dbo, 'table', Empresa, 'column', empEspecialRevision;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si la empresa cuenta con revisión especial [S=Si N=No]',
'User', dbo, 'table', Empresa, 'column', empEspecialRevision;
END CATCH;



-- TABLA Empleador
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa un empleador que se afilia a la
 caja de compensación. <b>Historia de Usuario: </b>TRA-329 Consultar empleador',
'User', dbo, 'table', Empleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa un empleador que se afilia a la
 caja de compensación. <b>Historia de Usuario: </b>TRA-329 Consultar empleador',
'User', dbo, 'table', Empleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del empleador',
'User', dbo, 'table', Empleador, 'column', empId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del empleador',
'User', dbo, 'table', Empleador, 'column', empId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la empresa asociada al empleador',
'User', dbo, 'table', Empleador, 'column', empEmpresa;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la empresa asociada al empleador',
'User', dbo, 'table', Empleador, 'column', empEmpresa;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado de los aportes del empleador',
'User', dbo, 'table', Empleador, 'column', empEstadoAportesEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado de los aportes del empleador',
'User', dbo, 'table', Empleador, 'column', empEstadoAportesEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del motivo de desafiliación',
'User', dbo, 'table', Empleador, 'column', empMotivoDesafiliacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del motivo de desafiliación',
'User', dbo, 'table', Empleador, 'column', empMotivoDesafiliacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si el empleador cuenta con alguna expulsión subsanada[S=Si N=No]',
'User', dbo, 'table', Empleador, 'column', empExpulsionSubsanada;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si el empleador cuenta con alguna expulsión subsanada[S=Si N=No]',
'User', dbo, 'table', Empleador, 'column', empExpulsionSubsanada;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha del cambio de estado de afiliación del empleador',
'User', dbo, 'table', Empleador, 'column', empFechaCambioEstadoAfiliacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha del cambio de estado de afiliación del empleador',
'User', dbo, 'table', Empleador, 'column', empFechaCambioEstadoAfiliacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Número total de trabajadores asociados al empleador',
'User', dbo, 'table', Empleador, 'column', empNumeroTotalTrabajadores;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Número total de trabajadores asociados al empleador',
'User', dbo, 'table', Empleador, 'column', empNumeroTotalTrabajadores;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Valor total de la última nómina',
'User', dbo, 'table', Empleador, 'column', empValorTotalUltimaNomina;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Valor total de la última nómina',
'User', dbo, 'table', Empleador, 'column', empValorTotalUltimaNomina;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Periodo de la última nómina',
'User', dbo, 'table', Empleador, 'column', empPeriodoUltimaNomina;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Periodo de la última nómina',
'User', dbo, 'table', Empleador, 'column', empPeriodoUltimaNomina;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al medio de pago asociada al empleador',
'User', dbo, 'table', Empleador, 'column', empMedioPagoSubsidioMonetario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al medio de pago asociada al empleador',
'User', dbo, 'table', Empleador, 'column', empMedioPagoSubsidioMonetario;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado del empleador',
'User', dbo, 'table', Empleador, 'column', empEstadoEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado del empleador',
'User', dbo, 'table', Empleador, 'column', empEstadoEmpleador;
END CATCH;



-- TABLA Beneficiario
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad representa la información de los beneficiarios
 <b>Historia de Usuario: </b>121-104',
'User', dbo, 'table', Beneficiario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad representa la información de los beneficiarios
 <b>Historia de Usuario: </b>121-104',
'User', dbo, 'table', Beneficiario;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del beneficiario',
'User', dbo, 'table', Beneficiario, 'column', benId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del beneficiario',
'User', dbo, 'table', Beneficiario, 'column', benId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la persona asociada al beneficiario',
'User', dbo, 'table', Beneficiario, 'column', benPersona;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la persona asociada al beneficiario',
'User', dbo, 'table', Beneficiario, 'column', benPersona;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al afiliado asociada al beneficiario',
'User', dbo, 'table', Beneficiario, 'column', benAfiliado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al afiliado asociada al beneficiario',
'User', dbo, 'table', Beneficiario, 'column', benAfiliado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al grupo familiar asoaciado beneficiario',
'User', dbo, 'table', Beneficiario, 'column', benGrupoFamiliar;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al grupo familiar asoaciado beneficiario',
'User', dbo, 'table', Beneficiario, 'column', benGrupoFamiliar;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Sujeto Tramite tipo solicitante de tipo beneficiario',
'User', dbo, 'table', Beneficiario, 'column', benTipoBeneficiario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Sujeto Tramite tipo solicitante de tipo beneficiario',
'User', dbo, 'table', Beneficiario, 'column', benTipoBeneficiario;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado de afiliación del beneficiario',
'User', dbo, 'table', Beneficiario, 'column', benEstadoBeneficiarioCaja;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado de afiliación del beneficiario',
'User', dbo, 'table', Beneficiario, 'column', benEstadoBeneficiarioCaja;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado de afiliación del beneficiario',
'User', dbo, 'table', Beneficiario, 'column', benEstadoBeneficiarioAfiliado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado de afiliación del beneficiario',
'User', dbo, 'table', Beneficiario, 'column', benEstadoBeneficiarioAfiliado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si el beneficiario labora[S=Si N=No]',
'User', dbo, 'table', Beneficiario, 'column', benLabora;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si el beneficiario labora[S=Si N=No]',
'User', dbo, 'table', Beneficiario, 'column', benLabora;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si el beneficiario cuenta con certificado de escolaridad [S=Si N=No]',
'User', dbo, 'table', Beneficiario, 'column', benCertificadoEscolaridad;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si el beneficiario cuenta con certificado de escolaridad [S=Si N=No]',
'User', dbo, 'table', Beneficiario, 'column', benCertificadoEscolaridad;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de vencimiento del certificado de escolaridad     *',
'User', dbo, 'table', Beneficiario, 'column', benFechaVencimientoCertificadoEscolar;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de vencimiento del certificado de escolaridad     *',
'User', dbo, 'table', Beneficiario, 'column', benFechaVencimientoCertificadoEscolar;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha recepción del certificado de escolaridad',
'User', dbo, 'table', Beneficiario, 'column', benFechaRecepcionCertificadoEscolar;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha recepción del certificado de escolaridad',
'User', dbo, 'table', Beneficiario, 'column', benFechaRecepcionCertificadoEscolar;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'',
'User', dbo, 'table', Beneficiario, 'column', benEstudianteTrabajoDesarrolloHumano;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '',
'User', dbo, 'table', Beneficiario, 'column', benEstudianteTrabajoDesarrolloHumano;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' de si el beneficiario posee alguna invalidez',
'User', dbo, 'table', Beneficiario, 'column', benInvalidez;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' de si el beneficiario posee alguna invalidez',
'User', dbo, 'table', Beneficiario, 'column', benInvalidez;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha del reporte de invalidez',
'User', dbo, 'table', Beneficiario, 'column', benFechaReporteInvalidez;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha del reporte de invalidez',
'User', dbo, 'table', Beneficiario, 'column', benFechaReporteInvalidez;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Comentarios sobre la invalidez del benificiario',
'User', dbo, 'table', Beneficiario, 'column', benComentariosInvalidez;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Comentarios sobre la invalidez del benificiario',
'User', dbo, 'table', Beneficiario, 'column', benComentariosInvalidez;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de afiliación del beneficiario',
'User', dbo, 'table', Beneficiario, 'column', benFechaAfiliacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de afiliación del beneficiario',
'User', dbo, 'table', Beneficiario, 'column', benFechaAfiliacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Salario mensual del beneficiaro',
'User', dbo, 'table', Beneficiario, 'column', benSalarioMensualBeneficiario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Salario mensual del beneficiaro',
'User', dbo, 'table', Beneficiario, 'column', benSalarioMensualBeneficiario;
END CATCH;



-- TABLA Afiliado
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa una persona afiliada a la caja de 
 compensación
 <b>Historia de Usuario: </b>121-104',
'User', dbo, 'table', Afiliado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa una persona afiliada a la caja de 
 compensación
 <b>Historia de Usuario: </b>121-104',
'User', dbo, 'table', Afiliado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del afiliado',
'User', dbo, 'table', Afiliado, 'column', afiId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del afiliado',
'User', dbo, 'table', Afiliado, 'column', afiId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la persona asociada al beneficiario',
'User', dbo, 'table', Afiliado, 'column', afiPersona;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la persona asociada al beneficiario',
'User', dbo, 'table', Afiliado, 'column', afiPersona;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado de la afiliación del empleador',
'User', dbo, 'table', Afiliado, 'column', afiEstadoAfiliadoCaja;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado de la afiliación del empleador',
'User', dbo, 'table', Afiliado, 'column', afiEstadoAfiliadoCaja;
END CATCH;



-- TABLA NotificacionEnviada
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa las notificaciones enviadas
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', NotificacionEnviada;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa las notificaciones enviadas
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', NotificacionEnviada;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la notificación enviada',
'User', dbo, 'table', NotificacionEnviada, 'column', noeId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la notificación enviada',
'User', dbo, 'table', NotificacionEnviada, 'column', noeId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de envío',
'User', dbo, 'table', NotificacionEnviada, 'column', noeFechaEnvio;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de envío',
'User', dbo, 'table', NotificacionEnviada, 'column', noeFechaEnvio;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Usuario remitente',
'User', dbo, 'table', NotificacionEnviada, 'column', noeRemitente;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Usuario remitente',
'User', dbo, 'table', NotificacionEnviada, 'column', noeRemitente;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código de la sede de caja de compensación',
'User', dbo, 'table', NotificacionEnviada, 'column', noeSccfId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código de la sede de caja de compensación',
'User', dbo, 'table', NotificacionEnviada, 'column', noeSccfId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del proceso evento',
'User', dbo, 'table', NotificacionEnviada, 'column', noeProcesoEvento;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del proceso evento',
'User', dbo, 'table', NotificacionEnviada, 'column', noeProcesoEvento;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado de envío de la notificación',
'User', dbo, 'table', NotificacionEnviada, 'column', noeEstadoEnvioNot;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado de envío de la notificación',
'User', dbo, 'table', NotificacionEnviada, 'column', noeEstadoEnvioNot;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del error de la notificación enviada',
'User', dbo, 'table', NotificacionEnviada, 'column', noeError;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del error de la notificación enviada',
'User', dbo, 'table', NotificacionEnviada, 'column', noeError;
END CATCH;



-- TABLA NotificacionDestinatario
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que los destinatarios a los que se les envió una
 notificación dada <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', NotificacionDestinatario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que los destinatarios a los que se les envió una
 notificación dada <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', NotificacionDestinatario;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la notificación del destinatario',
'User', dbo, 'table', NotificacionDestinatario, 'column', nodId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la notificación del destinatario',
'User', dbo, 'table', NotificacionDestinatario, 'column', nodId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica la notificación enviada asociado a la notificación destinatario',
'User', dbo, 'table', NotificacionDestinatario, 'column', nodNotEnviada;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica la notificación enviada asociado a la notificación destinatario',
'User', dbo, 'table', NotificacionDestinatario, 'column', nodNotEnviada;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Usuario destinatario',
'User', dbo, 'table', NotificacionDestinatario, 'column', nodDestintatario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Usuario destinatario',
'User', dbo, 'table', NotificacionDestinatario, 'column', nodDestintatario;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de destinatario',
'User', dbo, 'table', NotificacionDestinatario, 'column', nodTipoDestintatario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de destinatario',
'User', dbo, 'table', NotificacionDestinatario, 'column', nodTipoDestintatario;
END CATCH;



-- TABLA UbicacionEmpresa
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa una ubicación de la empresa
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', UbicacionEmpresa;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa una ubicación de la empresa
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', UbicacionEmpresa;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la ubicación de la empresa',
'User', dbo, 'table', UbicacionEmpresa, 'column', ubeId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la ubicación de la empresa',
'User', dbo, 'table', UbicacionEmpresa, 'column', ubeId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la empresa asociada a la ubicación de la empresa',
'User', dbo, 'table', UbicacionEmpresa, 'column', ubeEmpresa;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la empresa asociada a la ubicación de la empresa',
'User', dbo, 'table', UbicacionEmpresa, 'column', ubeEmpresa;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica la ubicación asociada a la ubicación de la empresa',
'User', dbo, 'table', UbicacionEmpresa, 'column', ubeUbicacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica la ubicación asociada a la ubicación de la empresa',
'User', dbo, 'table', UbicacionEmpresa, 'column', ubeUbicacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de ubicación asociado a la ubicación de la empresa',
'User', dbo, 'table', UbicacionEmpresa, 'column', ubeTipoUbicacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de ubicación asociado a la ubicación de la empresa',
'User', dbo, 'table', UbicacionEmpresa, 'column', ubeTipoUbicacion;
END CATCH;



-- TABLA Ubicacion
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa una ubicación fisica que contiene
 datos de contacto
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', Ubicacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa una ubicación fisica que contiene
 datos de contacto
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', Ubicacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la ubicación',
'User', dbo, 'table', Ubicacion, 'column', ubiId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la ubicación',
'User', dbo, 'table', Ubicacion, 'column', ubiId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de la dirección física',
'User', dbo, 'table', Ubicacion, 'column', ubiDireccionFisica;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de la dirección física',
'User', dbo, 'table', Ubicacion, 'column', ubiDireccionFisica;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Códgigo postal',
'User', dbo, 'table', Ubicacion, 'column', ubiCodigoPostal;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Códgigo postal',
'User', dbo, 'table', Ubicacion, 'column', ubiCodigoPostal;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del teléfono fijo',
'User', dbo, 'table', Ubicacion, 'column', ubiTelefonoFijo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del teléfono fijo',
'User', dbo, 'table', Ubicacion, 'column', ubiTelefonoFijo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicativo del teléfono fijo asociado a la ubicación',
'User', dbo, 'table', Ubicacion, 'column', ubiIndicativoTelFijo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicativo del teléfono fijo asociado a la ubicación',
'User', dbo, 'table', Ubicacion, 'column', ubiIndicativoTelFijo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Número telefónico del celular asociado a la ubicación',
'User', dbo, 'table', Ubicacion, 'column', ubiTelefonoCelular;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Número telefónico del celular asociado a la ubicación',
'User', dbo, 'table', Ubicacion, 'column', ubiTelefonoCelular;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del correo electrónico asociado a la ubicación',
'User', dbo, 'table', Ubicacion, 'column', ubiEmail;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del correo electrónico asociado a la ubicación',
'User', dbo, 'table', Ubicacion, 'column', ubiEmail;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' autoriza envío de email [S=Si N=No]
 Default false',
'User', dbo, 'table', Ubicacion, 'column', ubiAutorizacionEnvioEmail;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' autoriza envío de email [S=Si N=No]
 Default false',
'User', dbo, 'table', Ubicacion, 'column', ubiAutorizacionEnvioEmail;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica al municipio asoaciado a la ubicación',
'User', dbo, 'table', Ubicacion, 'column', ubiMunicipio;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica al municipio asoaciado a la ubicación',
'User', dbo, 'table', Ubicacion, 'column', ubiMunicipio;
END CATCH;



-- TABLA Tarjeta
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa una ubicación fisica que contiene
 datos de contacto
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', Tarjeta;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa una ubicación fisica que contiene
 datos de contacto
 <b>Historia de Usuario: </b>Transversal',
'User', dbo, 'table', Tarjeta;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la ubicación física que contiene datos del contacto',
'User', dbo, 'table', Tarjeta, 'column', tarId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la ubicación física que contiene datos del contacto',
'User', dbo, 'table', Tarjeta, 'column', tarId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Número de la tarjeta',
'User', dbo, 'table', Tarjeta, 'column', tarNumeroTarjeta;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Número de la tarjeta',
'User', dbo, 'table', Tarjeta, 'column', tarNumeroTarjeta;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado de la tarjeta',
'User', dbo, 'table', Tarjeta, 'column', tarEstadoTarjeta;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado de la tarjeta',
'User', dbo, 'table', Tarjeta, 'column', tarEstadoTarjeta;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la persona asociada a la tarjeta',
'User', dbo, 'table', Tarjeta, 'column', afiPersona;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la persona asociada a la tarjeta',
'User', dbo, 'table', Tarjeta, 'column', afiPersona;
END CATCH;



-- TABLA SucursaRolContactEmpleador
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa las sucursales de un empleador
 <b>Historia de Usuario: </b>111-066 Radicar solicitudes afiliación y activar 
 empleador',
'User', dbo, 'table', SucursaRolContactEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa las sucursales de un empleador
 <b>Historia de Usuario: </b>111-066 Radicar solicitudes afiliación y activar 
 empleador',
'User', dbo, 'table', SucursaRolContactEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la sucursal del empleador',
'User', dbo, 'table', SucursaRolContactEmpleador, 'column', srcId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la sucursal del empleador',
'User', dbo, 'table', SucursaRolContactEmpleador, 'column', srcId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la sucursal asociada al empleador',
'User', dbo, 'table', SucursaRolContactEmpleador, 'column', srcSucursalEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la sucursal asociada al empleador',
'User', dbo, 'table', SucursaRolContactEmpleador, 'column', srcSucursalEmpleador;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica el rol del contacto empleador asociada al empleador',
'User', dbo, 'table', SucursaRolContactEmpleador, 'column', srcRolContactoEmpleador;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica el rol del contacto empleador asociada al empleador',
'User', dbo, 'table', SucursaRolContactEmpleador, 'column', srcRolContactoEmpleador;
END CATCH;



-- TABLA SucursalEmpresa
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa las sucursales de un empleador
 <b>Historia de Usuario: </b>111-066 Radicar solicitudes afiliación y activar 
 empleador',
'User', dbo, 'table', SucursalEmpresa;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa las sucursales de un empleador
 <b>Historia de Usuario: </b>111-066 Radicar solicitudes afiliación y activar 
 empleador',
'User', dbo, 'table', SucursalEmpresa;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la sucursal de la empresa',
'User', dbo, 'table', SucursalEmpresa, 'column', sueId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la sucursal de la empresa',
'User', dbo, 'table', SucursalEmpresa, 'column', sueId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica a la empresa asociada a la sucursal de la empresa',
'User', dbo, 'table', SucursalEmpresa, 'column', sueEmpresa;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica a la empresa asociada a la sucursal de la empresa',
'User', dbo, 'table', SucursalEmpresa, 'column', sueEmpresa;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del código asociado a la empresa',
'User', dbo, 'table', SucursalEmpresa, 'column', sueCodigo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del código asociado a la empresa',
'User', dbo, 'table', SucursalEmpresa, 'column', sueCodigo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del nombre de la empresa',
'User', dbo, 'table', SucursalEmpresa, 'column', sueNombre;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del nombre de la empresa',
'User', dbo, 'table', SucursalEmpresa, 'column', sueNombre;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica la ubicación asociada a la sucursal de la empresa',
'User', dbo, 'table', SucursalEmpresa, 'column', sueUbicacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica la ubicación asociada a la sucursal de la empresa',
'User', dbo, 'table', SucursalEmpresa, 'column', sueUbicacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica el código CIIU asociada a la sucursal de la empresa',
'User', dbo, 'table', SucursalEmpresa, 'column', sueCodigoCIIU;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica el código CIIU asociada a la sucursal de la empresa',
'User', dbo, 'table', SucursalEmpresa, 'column', sueCodigoCIIU;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Id que identifica el la parametrización del pago asociada a la sucursal de la empresa',
'User', dbo, 'table', SucursalEmpresa, 'column', sueMedioPagoSubsidioMonetario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Id que identifica el la parametrización del pago asociada a la sucursal de la empresa',
'User', dbo, 'table', SucursalEmpresa, 'column', sueMedioPagoSubsidioMonetario;
END CATCH;



-- TABLA SedeCajaCompensacion
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa una sede de la caja de compensación
 <b>Historia de Usuario: </b>TRA-084 Administrar asignación de solicitudes',
'User', dbo, 'table', SedeCajaCompensacion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa una sede de la caja de compensación
 <b>Historia de Usuario: </b>TRA-084 Administrar asignación de solicitudes',
'User', dbo, 'table', SedeCajaCompensacion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria del beneficiario',
'User', dbo, 'table', SedeCajaCompensacion, 'column', sccfId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria del beneficiario',
'User', dbo, 'table', SedeCajaCompensacion, 'column', sccfId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del nombre de la sede de la caja de compensación',
'User', dbo, 'table', SedeCajaCompensacion, 'column', sccfNombre;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del nombre de la sede de la caja de compensación',
'User', dbo, 'table', SedeCajaCompensacion, 'column', sccfNombre;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si la sucursal es virtual [S=Si N=No]',
'User', dbo, 'table', SedeCajaCompensacion, 'column', sccfVirtual;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si la sucursal es virtual [S=Si N=No]',
'User', dbo, 'table', SedeCajaCompensacion, 'column', sccfVirtual;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de la sede de la caja de compensación',
'User', dbo, 'table', SedeCajaCompensacion, 'column', sccCodigo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de la sede de la caja de compensación',
'User', dbo, 'table', SedeCajaCompensacion, 'column', sccCodigo;
END CATCH;



-- TABLA Novedad
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa las novedades
 <b>Historia de Usuario: </b>121-122',
'User', dbo, 'table', Novedad;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa las novedades
 <b>Historia de Usuario: </b>121-122',
'User', dbo, 'table', Novedad;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la novedad',
'User', dbo, 'table', Novedad, 'column', novId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la novedad',
'User', dbo, 'table', Novedad, 'column', novId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de la causal de novedad',
'User', dbo, 'table', Novedad, 'column', novCausal;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de la causal de novedad',
'User', dbo, 'table', Novedad, 'column', novCausal;
END CATCH;



-- TABLA EtiquetaCorrespondenciaRadicado
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa las etiquetas que son usadas para
 el envío de las cajas de correspondencia
 <b>Historia de Usuario: </b>111-067 Generar etiquetas para radicación',
'User', dbo, 'table', EtiquetaCorrespondenciaRadicado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa las etiquetas que son usadas para
 el envío de las cajas de correspondencia
 <b>Historia de Usuario: </b>111-067 Generar etiquetas para radicación',
'User', dbo, 'table', EtiquetaCorrespondenciaRadicado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la etiqueta',
'User', dbo, 'table', EtiquetaCorrespondenciaRadicado, 'column', eprId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la etiqueta',
'User', dbo, 'table', EtiquetaCorrespondenciaRadicado, 'column', eprId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código de la etiqueta de correspondencia',
'User', dbo, 'table', EtiquetaCorrespondenciaRadicado, 'column', eprCodigo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código de la etiqueta de correspondencia',
'User', dbo, 'table', EtiquetaCorrespondenciaRadicado, 'column', eprCodigo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Indicador ''S/N'' si la etiqueta ya esta asignada',
'User', dbo, 'table', EtiquetaCorrespondenciaRadicado, 'column', eprAsignada;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Indicador ''S/N'' si la etiqueta ya esta asignada',
'User', dbo, 'table', EtiquetaCorrespondenciaRadicado, 'column', eprAsignada;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del tipo de etiqueta',
'User', dbo, 'table', EtiquetaCorrespondenciaRadicado, 'column', eprTipoEtiqueta;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del tipo de etiqueta',
'User', dbo, 'table', EtiquetaCorrespondenciaRadicado, 'column', eprTipoEtiqueta;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción de la procedencia de la etiqueta',
'User', dbo, 'table', EtiquetaCorrespondenciaRadicado, 'column', eprProcedenciaEtiqueta;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción de la procedencia de la etiqueta',
'User', dbo, 'table', EtiquetaCorrespondenciaRadicado, 'column', eprProcedenciaEtiqueta;
END CATCH;



-- TABLA CajaCorrespondencia
BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'<b>Descripción:</b> Entidad que representa las cajas físicas que se envían
 entre las disitinas sedes de una caja de compensación con documentos físicos
 <b>Historia de Usuario: </b>111-086 Generar listado solicitudes y registro 
 remisión back',
'User', dbo, 'table', CajaCorrespondencia;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', '<b>Descripción:</b> Entidad que representa las cajas físicas que se envían
 entre las disitinas sedes de una caja de compensación con documentos físicos
 <b>Historia de Usuario: </b>111-086 Generar listado solicitudes y registro 
 remisión back',
'User', dbo, 'table', CajaCorrespondencia;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de llave primaria de la caja de correspondencia',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoId;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de llave primaria de la caja de correspondencia',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoId;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Descripción del estado de la correspondencia',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoEstado;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Descripción del estado de la correspondencia',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoEstado;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código identificador de la etiqueta',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoCodigoEtiqueta;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código identificador de la etiqueta',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoCodigoEtiqueta;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Nombre del usuario remitente',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoRemitente;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Nombre del usuario remitente',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoRemitente;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Nombre del usuario destinatario',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoDestinatario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Nombre del usuario destinatario',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoDestinatario;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Nombre del usuario receptor de la correspondencia',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoUsuarioRecepcion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Nombre del usuario receptor de la correspondencia',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoUsuarioRecepcion;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Códgo de la sede del usuario remitente',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoSedeRemitente;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Códgo de la sede del usuario remitente',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoSedeRemitente;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código de la sede del usuario destinatario',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoSedeDestinatario;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código de la sede del usuario destinatario',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoSedeDestinatario;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Código único consecutivo de la correspondencia',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoConsecutivo;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Código único consecutivo de la correspondencia',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoConsecutivo;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de apertura',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoFechaInicio;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de apertura',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoFechaInicio;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de cierre',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoFechaFin;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de cierre',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoFechaFin;
END CATCH;

BEGIN TRY
EXEC sp_updateextendedproperty 'MS_Description',
'Fecha de recepción de la correspondencia',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoFechaRecepcion;
END TRY
BEGIN CATCH
EXEC sp_addextendedproperty 'MS_Description', 'Fecha de recepción de la correspondencia',
'User', dbo, 'table', CajaCorrespondencia, 'column', ccoFechaRecepcion;
END CATCH;