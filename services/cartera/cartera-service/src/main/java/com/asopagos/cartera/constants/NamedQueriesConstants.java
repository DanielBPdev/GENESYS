package com.asopagos.cartera.constants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * Clase que contiene las constantes con los nombres de los NamedQueries del
 * servicio <b>Módulo:</b> Asopagos - 2.2 Cartera<br/>
 * Transversal
 *
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class NamedQueriesConstants {

    /**
     * Consulta encargada de traer una solicitud preventiva por número de
     * radicado
     */
    public static final String CONSULTAR_SOLICITUD_PREVENTIVA_POR_NUMERO_RADICADO = "Cartera.SolicitudPreventiva.consultar.solicitud.preventiva.por.numero.radicado";

    /**
     * Constantes Claudia
     */

    /**
     * Se consulta la parametrización de convenios de pago
     */
    public static final String CONSULTAR_PARAMETRIZACION_CONVENIO_PAGO = "Cartera.ParametrizacionConveniosPago.consultar.parametrizacion.convenios.pago";

    /**
     * Se consulta la parametrización de convenios de pago
     */
    public static final String CONSULTAR_PARAMETRIZACION_EXCLUSIONES = "Cartera.ParametrizacionConveniosPago.consultar.parametrizacion.exclusiones";

    /**
     * Se consulta la parametrización de convenios de pago
     */
    public static final String CONSULTAR_PARAMETRIZACION_DESAFILIACION = "Cartera.ParametrizacionConveniosPago.consultar.parametrizacion.desafiliacion";

    /**
     * Se consulta la parametrización de gestión de cobro
     */
    public static final String CONSULTAR_PARAMETRIZACION_GESTION_COBRO_TIPO_PARAMETRIZACION = "Cartera.ParametrizacionGestionCobro.Consultar.Parametrizacion.Gestion.Cobro.Tipo.Parametrizacion";

    /**
     * Se consulta los convenios de pago modelo dto por tipo de solicitante número y tipo de identificación
     */
    public static final String CONSULTAR_CONVENIOS_PAGO_DTO = "Cartera.ConvenioPagoModeloDTO.consultar.convenios.pago";

    /**
     * Se consulta los pagos de periodos de los convenios con los identificadores de convenios de pago
     */
    public static final String CONSULTAR_PAGO_PERIODO_CONVENIO_DTO = "Cartera.PagoPeriodoConvenioModeloDTO.consultar.pago.periodo.convenio";

    /**
     * Se consulta la acción de cobro del método 2E
     */
    public static final String CONSULTAR_ACCION_COBRO_2E = "Cartera.AccionCobro2E.Consultar.Accion.Cobro.2E";

    /**
     * Se consulta el convenio de pago con la fecha limite de pago
     */
    public static final String CONSULTAR_CONVENIO_PAGO_FECHA_LIMITE = "Cartera.ConvenioPagoModeloDTO.consultar.convenio.fecha.limite";

    /**
     * Se consulta las personas por tipo de solicitante
     */
    public static final String CONSULTAR_APORTANTE_CONVENIO = "Cartera.AportanteConvenioDTO.consultar.aportantes";

    /**
     * Se consulta cartera por tipo y número de identificación y tipo de solicitante
     */
    public static final String CONSULTAR_CARTERA = "Cartera.Cartera.tipo.numero.identificacion.tipo.solicitante";

    /**
     * Se consulta el modelo dto de cartera dependiente
     */
    public static final String CONSULTAR_CARTERA_DEPENDIENTE = "Cartera.CarteraDependienteModeloDTO.consultar.cartera.dependiente";

    /**
     * Constantes Angelica
     */
    /**
     * Constante que contiene el nombre de la consulta que busca los ciclos de unas fechas determinadas.
     */
    public static final String CONSULTAR_CICLO_MANUAL_POR_FECHA = "Cartera.CicloCartera.ConsultarCicloPorFecha";
    /**
     * Constante que contiene el nombre de la consulta que busca los aportantes de los edictos.
     */
    public static final String CONSULTAR_APORTANTES_SOLICITUD_EDICTOS = "Cartera.SolicitudGestionFisica.ConsultarAportantesEdictos";
    /**
     * Constante que contiene el nombre de la consultar que busca el método activo de la LC1.
     */
    public static final String CONSULTAR_APORTANTES_LINEA_COBRO = "Cartera.ConsultarAportantes.LineaCobro";
    /**
     * Constante que contiene el nombre de la consultar que busca el método activo de la LC1.
     */
    public static final String CONSULTAR_APORTANTES_LINEA_COBRO_ACCION_COBRO = "Cartera.ConsultarAportantes.LineaCobro.Accion";
    /**
     * Constante que contiene el nombre de la consultar que busca el método activo de la LC1.
     */
    public static final String CONSULTAR_APORTANTES_LINEA_COBRO_ACCION_COBRO_LC2 = "Cartera.ConsultarAportantes.LineaCobro.Accion.LC2";
    /**
     * Constante que contiene el total de detalles de los aportantes por linea de cobro.
     */
    public static final String CONSULTAR_TOTAL_APORTANTES_LINEA_COBRO = "Cartera.ConsultarAportantes.Total.LineaCobro";
    /**
     * Constante que contiene el total de detalles de los aportantes por linea de cobro y accion.
     */
    public static final String CONSULTAR_TOTAL_APORTANTES_LINEA_COBRO_ACCION_COBRO = "Cartera.ConsultarAportantes.Total.LineaCobro.Accion";
    /**
     * Constante que contiene el total de detalles de los aportantes por linea de cobro y accion.
     */
    public static final String CONSULTAR_TOTAL_APORTANTES_LINEA_COBRO_ACCION_COBRO_LC2 = "Cartera.ConsultarAportantes.Total.LineaCobro.Accion.LC2";
    /**
     * Constante que contiene los aportantes a trasladar la deuda antigua por lineas de cobro
     */
    public static final String CONSULTAR_APORTANTES_TRASLADO_DEUDA_ANTIGUA = "Cartera.ConsultarAportantes.Traslado.DeudaAntigua";
    /**
     * Constante que contiene los aportantes a trasladar la deuda antigua por lineas de cobro y lista de personas
     */
    public static final String CONSULTAR_APORTANTES_TRASLADO_DEUDA_ANTIGUA_PERSONAS = "Cartera.ConsultarAportantes.Traslado.DeudaAntigua.Personas";

    /**
     * Constante que contiene el nombre de la consultar que busca el método activo de la LC1.
     */
    public static final String CONSULTAR_METODO_ACTIVO_LC1 = "Cartera.ConsultarMetodoActivo";
    /**
     * Constante que contiene el nombre de la consulta que busca el id del documento de un aportante para un número de radicación.
     */
    public static final String CARTERA_IDENTIFICADOR_DOCUMENTO_CONSULTAR_POR_SOLICITUD_APORTANTE = "Cartera.IdentificadorDocumento.ConsultarPorSolicitudAportante";
    /**
     * Constante que contiene el nombre de la consulta del aportante para la segunda remisión.
     */
    public static final String CARTERA_SOLICITUD_GESTION_COBRO_APORTANTES_SEGUNDA_REMISION_ENTREGA = "Cartera.SolicitudGestionCobro.AportantesPrimeraEntregaRemision";
    /**
     * Constante que contiene el nombre de la consulta de los aportantes para la primera entrega.
     */
    public static final String CARTERA_DATOS_ACTUALIZACION = "Cartera.DatosActualizacion";
    /**
     * Constante que contiene el nombre de la consulta de los aportantes para la primera entrega.
     */
    public static final String CARTERA_SOLICITUD_GESTION_COBRO_APORTANTES_PRIMERA_ENTREGA = "Cartera.SolicitudGestionCobro.AportantesPrimeraEntrega";
    /**
     * Constante que contiene el nombre de la consulta de la solicitud de gestión de ocbro física por número de radicación.
     */
    public static final String CARTERA_SOLICITUD_GESTION_COBRO_NUMERO_RADICACION = "Cartera.Consultar.SolicitudGestionCobro.NumeroRadicacion";
    /**
     * Constante que contiene el nombre de la consulta de los aportantes para primera remisión de cobro.
     */
    public static final String CARTERA_SOLICITUD_GESTION_COBRO_APORTANTES = "SELECT * FROM(SELECT car.cartipoSolicitante,per.perTipoIdentificacion,per.perNumeroIdentificacion,per.perRazonSocial,car.carPeriodoDeuda,per.perRazonSocial as destinatario,muni.mundepartamento,ubi.ubimunicipio,ubi.ubiDireccionFisica,ubi.ubicodigopostal,case when ubi.ubitelefonofijo is null then ubi.ubitelefonocelular else ubi.ubitelefonofijo end as telefono,det.dsgenviarprimeraremision,det.dsgobservacionprimeraremision FROM DetalleSolicitudGestionCobro det JOIN SolicitudGestionCobroFisico sgc ON det.dsgsolicitudPrimeraRemision = sgc.sgfid  JOIN Solicitud sol ON sgc.sgfSolicitud = sol.solid JOIN Cartera car ON det.dsgCartera = car.carId JOIN  Persona per ON car.carPersona = per.perId JOIN Ubicacion ubi ON ubi.ubiid = per.perUbicacionPrincipal JOIN Municipio muni ON muni.munid=ubi.ubimunicipio WHERE  det.dsgsolicitudPrimeraRemision = sgc.sgfid AND sgc.sgfSolicitud = sol.solId AND det.dsgCartera = car.carId AND car.carPersona = per.perId AND per.perUbicacionPrincipal = ubi.ubiId AND car.carTipoSolicitante in ('INDEPENDIENTE','PENSIONADO') AND sol.solnumeroRadicacion = :numeroRadicacion UNION SELECT car.cartipoSolicitante,per.perTipoIdentificacion,per.perNumeroIdentificacion,per.perRazonSocial,car.carPeriodoDeuda,rep.perRazonSocial as destinatario,muni.mundepartamento,ubi.ubimunicipio,ubi.ubiDireccionFisica,ubi.ubicodigopostal,case when ubi.ubitelefonofijo is null then ubi.ubitelefonocelular else ubi.ubitelefonofijo end as telefono,det.dsgenviarprimeraremision,det.dsgobservacionprimeraremision FROM DetalleSolicitudGestionCobro det JOIN SolicitudGestionCobroFisico sgc ON det.dsgsolicitudPrimeraRemision = sgc.sgfid  JOIN Solicitud sol ON sgc.sgfSolicitud = sol.solid JOIN Cartera car ON det.dsgCartera = car.carId JOIN  Persona per ON car.carPersona = per.perId JOIN Empresa emp ON Per.perId = emp.empPersona JOIN Persona rep ON rep.perid = emp.empRepresentanteLegal JOIN UbicacionEmpresa ube  ON ube.ubeEmpresa = emp.empid JOIN Ubicacion ubi ON ube.ubeubicacion = ubi.ubiid JOIN Municipio muni ON muni.munid=ubi.ubimunicipio WHERE ube.ubetipoUbicacion = :tipoUbicacion AND car.carTipoSolicitante ='EMPLEADOR' AND sol.solnumeroRadicacion = :numeroRadicacion) x";
    /**
     * Constante que contiene el nombre de la consulta de aportantes fiscalizados por ciclo.
     */
    public static final String CARTERA_SOLICITUD_FISCALIZACION_CONSULTAR_APORTANTES_FISCALIZADOS_POR_CICLO = "Cartera.SolicitudFiscalizacion.Consultar.Aportantes.Fiscalizados.Por.Ciclo";
    /**
     * Constante que contiene el nombre de la consulta del último usuario back asignado.
     */
    public static final String CARTERA_SOLICITUD_PREVENTIVA_CONSULTAR_BACK_ACTUALIZACION_POR_TIPO_SOLICITANTE = "Cartera.SolicitudPreventiva.Consultar.BackActualizacion.Por.TipoSolicitante";
    /**
     * Constante que sirve para traer la parametrización de cartera por tipo de
     * parametrización
     */
    public static final String CONSULTAR_PARAMETRIZACION_CARTERA_TIPO_PARAMETRIZACION_FECHA = "Cartera.ParametrizacionCartera.Consultar.Parametrizacion.Cartera.Tipo.Parametrizacion.Fecha";

    /**
     * Constante que tiene la llave de la consulta de los pagos de los convenios.
     */
    public static final String CONSULTAR_PAGOS_CONVENIO = "Cartera.ConvenioPago.Consultar.Pagos";

    /**
     * Constante que tiene la llave de la consulta de los pagos de los convenios para personas independientes o pensionados.
     */
    public static final String CONSULTAR_PAGOS_CONVENIO_PERSONAS = "Cartera.ConvenioPago.Consultar.Pagos.Personas";

    /**
     * Constante que tiene la llave de la consulta los convenios que deben ser cerrados.
     */
    public static final String CONSULTAR_CONVENIOS_CIERRE = "Cartera.ConvenioPago.Consultar.Convenio.Cierre";

    /**
     * Constante que tiene la llave de la consulta los convenios que deben ser notificados.
     */
    public static final String CONSULTAR_CONVENIOS_COMUNICADO = "Cartera.ConvenioPago.Consultar.Convenio.Comunicado";

    /**
     * Constante que tiene la llave de la consulta de toda la cartera.
     */
    public static final String CARTERA_CONSULTAR_CARTERA_ALL = "Cartera.ConsultarCartera.All";

    /**
     * Constante que tiene la llave de la consulta para ver el detalle de la solicitud de gestion de cobro
     */
    public static final String CONSULTAR_DETALLE_SOLICITUD_GESTION_COBRO_POR_SOLICITUD = "Cartera.DetalleSolicitudGestionCobro.consultarSolicitud";

    /**
     * Constante que tiene la llave de la consulta para ver la solicitud de gestion de cobro electronico
     */
    public static final String CONSULTAR_SOLICITUD_GESTION_COBRO_ELECTRONICO = "Cartera.SolicitudGestionCobroElectronico.consultarSolicitud";

    /**
     * Constantes Bryan
     */

    /**
     * Constante que sirve para traer la parametrización de cartera por tipo de
     * parametrización
     */
    public static final String CONSULTAR_PARAMETRIZACION_CARTERA_TIPO_PARAMETRIZACION = "Cartera.ParametrizacionCartera.Consultar.Parametrizacion.Cartera.Tipo.Parametrizacion";
    /**
     * Constante que sirve para traer la solicitdu de fiscalización por número
     * de radicación
     */
    public static final String CONSULTAR_SOLICITUD_FISCALIZACION_POR_NUMERO_RADICACION = "Cartera.SolicitudFiscalizacion.Consultar.SolicitudFiscalizacion.Numero.Radicacion";
    /**
     * Constante que permite consultar la cantidad de entidades del ciclo de fiscalización
     * por número de ciclo
     */
    public static final String CARTERA_CICLOFISCALIZACION_CONSULTAR_ENTITADES_CICLOFISCALIZACION_POR_NUMEROCICLO = "Cartera.CicloFiscalizacion.Consultar.Entidades.CicloFiscalizacion.Por.NumeroCiclo";

    /**
     * Constante que permite consultar los ciclos de fiscalizacion actualies del aportanto con tipo de identificación y número de
     * identificación
     */
    public static final String CARTERA_CICLOFISCALIZACION_CONSULTAR_APORTANTES_CICLO_ACTUAL_CON_PERSONA = "Cartera.CicloFiscalizacion.Consultar.Aportantes.CicloActual.Con.Persona";

    /**
     * Constante que permite consultar los ciclos de fiscalizacion actualies del aportanto sin tipo de identificación y número de
     * identificación
     */
    public static final String CARTERA_CICLOFISCALIZACION_CONSULTAR_APORTANTES_CICLO_ACTUAL_SIN_PERSONA = "Cartera.CicloFiscalizacion.Consultar.Aportantes.CicloActual.Sin.Persona";

    /**
     * Constante que permite obtener el id de instancia de la solicitud y el usuario destino
     */
    public static final String CARTERA_SOLICITUD_CONSULTAR_IDISNTANCIA_USUARIODESTINO_POR_IDCICLOAPORTANTE = "Cartera.Solicitud.Consultar.idInstancia.usarioDestino.Por.idCicloAportante";

    /**
     * Constante con la clave de la consulta de un ciclo fiscalización por estado.
     */
    public static final String CONSULTAR_CICLO_FISCALIZACION = "Cartera.CicloFiscalizacion.ConsultarCiclo";

    /**
     * Consulta mock
     */
    public static final String CONSULTAR_APORTANTES_GESTION_PREVENTIVA = "Cartera.Personas.ConsultaGestionPreventiva";

    /**
     * Consulta ciclo aportante
     */
    public static final String CONSULTAR_CICLO_APORTANTES = "Cartera.Aportantes.ConsultarCiclo";

    /**
     * Constante que se encarga de consultar los aportantes de acuerdo a una parametrización.
     */
    public static final String CONSULTAR_APORTANTES_PARAMETRIZACION = "Consultar.aportantes.empleadores.trabajadores.parametrizacion";
    /**
     * Constante que se encarga de consultar los aportantes de acuerdo a una parametrización.
     */
    public static final String CONSULTAR_APORTANTES_PARAMETRIZACION_SIN_FILTRO_MORA = "Consultar.aportantes.empleadores.trabajadores.parametrizacion.sinMora";
    /**
     * Constante que se encarga de consultar los aportantes de acuerdo a una parametrización.
     */
    public static final String CONSULTAR_APORTANTES_EMPLEADORES_PARAMETRIZACION = "Consultar.aportantes.empleadores.parametrizacion";
    /**
     * Constante que se encarga de consultar los aportantes de acuerdo a una parametrización.
     */
    public static final String CONSULTAR_APORTANTES_EMPLEADORES_PARAMETRIZACION_SIN_FILTRO_MORA = "Consultar.aportantes.empleadores.parametrizacion.sinMora";
    /**
     * Constante consulta la informacion de la persona que se le va a
     * aplicar el cierre de convenio
     */
    public static final String CONSULTAR_PERSONA_CONVENIOCIERRE = "Consultar.personas.convenio.cierre";
    /**
     * Constante consulta la informacion de la empleador que se le va a
     * aplicar el cierre de convenio
     */
    public static final String CONSULTAR_EMPLEADOR_CONVENIOCIERRE = "Consultar.empleador.convenio.cierre";

    /**
     * consulta que obtiene los empleadores que se solicitan mediante los datos de consulta
     */
    public static final String CONSULTAR_EMPLEADORES_CONVENIOS = "Consultar.Empleadores.Convenios";
    /**
     * consulta que obtiene los empleadores que se solicitan mediante los datos de consulta
     */
    public static final String CONSULTAR_PERSONAS_CONVENIOS = "Consultar.Personas.Convenios";
    /**
     *
     */
    public static final String CONSULTAR_CONVENIOS_ACTIVOS_EMPLEADORES = "Consultar.Convenios.Activos.Empleadores";
    /**
     *
     */
    public static final String EJECUCION_VALIDACIONES_CREACION_CONVENIO = "Cartera.Validaciones.Creacion.Convenios";

    /**
     * Constante que consulta el ultimo destinatario de la tabla solicitud
     */
    public static final String CARTERA_GESTION_COBRO_1A_CONSULTAR_DESTINATARIO = "Cartera.GestionCobro1A.Consultar.Destinatario";

    /**
     * Constante que consulta la solicitud de gestion de cobro por id solicitud primera remision
     */
    public static final String CARTERA_GESTION_COBRO_CONSULTAR_SOLICITUD_GESTION_COBRO_POR_ID_PRIMERA_REMISION = "Cartera.GestionCobro.Consultar.Solicitud.Gestion.Cobro.Por.Id.Pprimera.Remision";

    /**
     * Constante que tiene la llave de la consulta para ver el detalle de la solicitud de gestion de cobro por id primera remision
     */
    public static final String CONSULTAR_DETALLE_SOLICITUD_GESTION_COBRO_POR_SOLICITUD_PRIMERA_REMISION = "Cartera.DetalleSolicitudGestionCobro.consultarSolicitudPrimeraRemision";

    /**
     * Constante que se encarga de consultar los aportantes de acuerdo a una parametrización de gestion cobro para pensionados e
     * independientes sin mora.
     */
    public static final String CARTERA_SOLICITUD_DESAFILIACION_CONSULTAR_SOLICITUD_DESAFILIACION_POR_NUMERO_RADICACION = "Cartera.Solicitud.Desafiliacion.Consultar.Solicitud.Desafiliacion.Por.Numero.Radicacion";

    /**
     * Constante que se encarga de consultar los aportantes empleadores para tener encuenta para la desafiliacion.
     */
    public static final String CARTERA_APORTANTE_DESAFILIACION_CONSULTAR_EMPLEADORES_DESAFILIACION = "Cartera.Aportante.Desafiliacion.Consultar.Empleadores.Desafiliacion";

    /**
     * Constante que se encarga de consultar los aportantes independientes para tener encuenta para la desafiliacion.
     */
    public static final String CARTERA_APORTANTE_DESAFILIACION_CONSULTAR_INDEPENDIENTE_DESAFILIACION = "Cartera.Aportante.Desafiliacion.Consultar.Independiente.Desafiliacion";

    /**
     * Constante que se encarga de consultar los aportantes pensionados para tener encuenta para la desafiliacion.
     */
    public static final String CARTERA_APORTANTE_DESAFILIACION_CONSULTAR_PENSIONADO_DESAFILIACION = "Cartera.Aportante.Desafiliacion.Consultar.Pensionado.Desafiliacion";
    /**
     * Constante que se encarga de consultar los aportantes realcionados con la solcitud para el proceso de desafiliacion.
     */
    public static final String CARTERA_SOLICITUD_DESAFILIACION_CONSULTAR_APORTANTES_SOLICITUD_DESAFILIACION = "Cartera.Solicitud.Desafiliacion.Consultar.Aportantes.Solicitud.Desafiliacion";

    /**
     * Constante que se encarga de consultar el promedio de los aportantes seleccionados para el proceso de desafiliacion.
     */
    public static final String CARTERA_CONSULTAR_PROMEDIO_DESAFILIACION = "Cartera.Consultar.Promedio.Desafiliacion";

    /**
     * Constante que se encarga de consultar en cartera los aportantes seleccionados para la desafiliacion.
     */
    public static final String CARTERA_CONSULTAR_CARTERA_APORTANTES_DESAFILIACION = "Cartera.Consultar.Cartera.Aportantes.Desafiliacion";

    /**
     * Constante que se encarga de consultar en cartera si la accion de cobro actual de la solicitud electronica cambio de accion en
     * cartera.
     */
    public static final String CARTERA_SOLICITUD_GESTIONCOBRO_ELECTRONICO_CONSULTAR_ACCION_COBRO_POSTERIOR = "Cartera.Solicitud.Gestion.Cobro.Electronico.Consultar.Accion.Cobro.Posterior";

    /**
     * Constante que se encarga de consultar en cartera si el empleador se le puede hacer traspaso a cartera antigua.
     */
    public static final String CARTERA_TRASPASO_CARTERA_ANTIGUA_EMPLEADOR = "Cartera.Traspaso.Cartera.Antigua.Empleador";

    /**
     * Constante que se encarga de consultar en cartera si el independiente o pensionado se le puede hacer traspaso a cartera antigua.
     */
    public static final String CARTERA_TRASPASO_CARTERA_ANTIGUA_INDEPENDIENTE_PENSIONADO = "Cartera.Traspaso.Cartera.Antigua.Independiente.Pensionado";

    /**
     * Constante que se encarga de consultar en cartera si esta vigente para la solicitud de gestion cobro fisico.
     */
    public static final String CARTERA_SOLICITUD_GESTION_COBRO_FISICO_VALIDAR_CARTERA_VIGENTE_ENVIO_FISICO = "cartera.solicitud.gestion.cobro.fisico.validar.cartera.vigente.envio.fisico";

    /**
     * Constante que se encarga de consultar en cartera si esta vigente para la solicitud de gestion cobro electronico.
     */
    public static final String CARTERA_SOLICITUD_GESTION_COBRO_ELECTRONICO_VALIDAR_CARTERA_VIGENTE_ENVIO_ELECTRONICO = "cartera.solicitud.gestion.cobro.electronico.validar.cartera.vigente.envio.electronico";

    /**
     * Constante que se encarga de consultar en cartera los ciclos proximos a vencer.
     */
    public static final String CARTERA_CICLOCARTERA_CONSULTAR_CICLOS_VENCIDOS = "cartera.ciclocartera.consultar.ciclos.vencidos";

    /**
     * Constante que permite obtener el id de instancia de la solicitud y el usuario destino
     */
    public static final String CARTERA_SOLICITUD_CONSULTAR_IDISNTANCIA_USUARIODESTINO_POR_IDCICLOAPORTANTEMANUAL = "Cartera.Solicitud.Consultar.idInstancia.usarioDestino.Por.idCicloAportanteManual";

    /**
     * Constante que se encarga de consultar los aportantes de acuerdo a una parametrización.
     */
    public static final String CONSULTAR_APORTANTES_PARAMETRIZACION_PREVENTIVA_AUTOMATICO = "Consultar.aportantes.empleadores.trabajadores.parametrizacion.preventiva.automatico";
    /**
     * Constante que se encarga de consultar los aportantes de acuerdo a una parametrización.
     */
    public static final String CONSULTAR_APORTANTES_PARAMETRIZACION_SIN_FILTRO_MORA_PREVENTIVA_AUTOMATICO = "Consultar.aportantes.empleadores.trabajadores.parametrizacion.preventiva.automatico.sinMora";
    /**
     * Constante que se encarga de consultar los aportantes de acuerdo a una parametrización.
     */
    public static final String CONSULTAR_APORTANTES_EMPLEADORES_PARAMETRIZACION_PREVENTIVA_AUTOMATICO = "Consultar.aportantes.empleadores.parametrizacion.preventiva.automatico";
    /**
     * Constante que se encarga de consultar los aportantes de acuerdo a una parametrización.
     */
    public static final String CONSULTAR_APORTANTES_EMPLEADORES_PARAMETRIZACION_SIN_FILTRO_MORA_PREVENTIVA_AUTOMATICO = "Consultar.aportantes.empleadores.parametrizacion.sinMora.preventiva.automatico";
    /**
     * Constante que se encarga de consultar si hay registro de notificacion personal con respecto al ultimo periodo de mora.
     */
    public static final String CARTERA_NOTIFICACION_PERSONAL_CONSULTAR_NOTIFICACION_PERSONAL_ULTIMO_PERIODO_MORA = "Cartera.Notificacion.Personal.Consultar.Notificacion.Personal.Ultimo.Periodo.Mora";
    /**
     * Constante que se encarga de consultar los procesos historicos de desafiliacion.
     */
    public static final String CARTERA_DESAFILIACION_CONSULTAR_HISTORICOS = "Consultar.Desafiliacion.Consultar.Historicos";
    /**
     * Constante que se encarga de consultar los documentos de desafiliacion.
     */
    public static final String CARTERA_DESAFILIACION_CONSULTAR_DOCUMENTOS_DESAFILIACION = "Consultar.Desafiliacion.Consultar.Documentos.Desafiliacion";
    /**
     * Constante con el identificador de la consulta que se encarga de mostrar las solicitudes de gestion cobro manual por estados
     */
    public static final String CARTERA_SOLICITUD_GESTION_COBRO_MANUAL_POR_ESTADOS = "Cartera.SolicitudFiscalizacion.ConsultarSolicitudGestionCobroManualPorEstados";
    /**
     * Constante con la clave de la consulta de un ciclo fiscalización por estado.
     */
    public static final String CONSULTAR_CICLO_GESTION_COBRO_MANUAL = "Cartera.CicloGestionCobroManual.ConsultarCiclo";
    /**
     * Constante que se encarga de consultar la cartera con el ultimo periodo para guardar la notificacion personal.
     */
    public static final String CARTERA_NOTIFICACION_PERSONAL_CONSULTAR_CARTERA_ULTIMO_PERIODO = "Cartera.Notificacion.Personal.Cartera.Ultimo.Periodo";
    /**
     * Constante que se encarga de consultar el idMensaje del comunicado que se envio a traves de Niyaraky.
     */
    public static final String CONSULTAR_IDMENSAJE_NIYARAKY = "Cartera.Consultar.Idmensaje.Niyaraky";
    /**
     * Constante que se encarga de consultar la Solicitud agrupadora de gestion preventiva.
     */
    public static final String CARTERA_SOLICITUD_PREVENTIVA_AGRUPADORA_CONSULTAR_SOLICITUD_PREVENTIVA_AGRUPADORA = "Cartera.Solicitud.Preventiva.Agrupadora.Consultar.Solicitud.Preventiva.Agrupadora";
    /**
     * Constante que se encarga de consultar las solicitudes agrupadores con solicitudes individuales cerradas por medio del id de la
     * solicitud agrupadora.
     */
    public static final String CARTERA_SOLICITUD_PREVENTIVA_AGRUPADORA_CONSULTAR_CIERRE_AGRUPADORA = "Cartera.Solicitud.Preventiva.Agrupadora.Consultar.Cierre.Agrupadora";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de solicitudes individuales de gestión preventiva, candidatas a cierre
     * por extemporaneidad
     */
    public static final String CARTERA_SOLICITUD_INDIVIDUAL_CIERRE_PREVENTIVA = "Cartera.Solicitud.Individual.Cierre.Preventiva";

    /**
     * Constante que se encarga de consultar el detalle de las solicitudes indivuales.
     */
    public static final String CARTERA_SOLICITUD_PREVENTIVA_CONSULTAR_DETALLE_SOLICITUDES_INDIVIDUALES = "Cartera.Solicitud.Preventiva.Consultar.Detalle.Solicitudes.Individuales";

    /**
     * Constante que se encarga de actualizar la activacion del metodo anterior para la linea de cobro 1
     */
    public static final String CARTERA_PARAMETRIZACION_GESTION_COBRO_ACTUALIZAR_ACTIVACION_METODO_ANTERIOR = "Cartera.Parametrizacion.Gestion.Cobro.Actualizar.Activacion.Metodo.Anterior";

    /**
     * Inicio: Constantes jusanchez
     */

    /**
     * Constante con el identificacador de la consulta que mostrara el detalle de un ciclo de fiscalizacion especifico
     */
    public static final String CONSULTAR_CICLO_FISCALIZACION_CON_DETALLE = "Cartera.CicloFiscalizacion.ConsultarDetalle";
    /**
     * Constante con el identificacador de la consulta que mostrara el detalle de un ciclo de fiscalizacion especifico
     */
    public static final String CONSULTAR_CICLO_GESTION_MANUAL_CON_DETALLE = "Cartera.CicloGestionManual.ConsultarDetalle";
    /**
     * Constante con el identificador de la consulta que mostrara los detalles de un ciclo de fiscaclización para un aportante
     */
    public static final String CONSULTAR_DETALLE_CICLO_FISCALIZACION_APORTANTE = "Cartera.CicloFiscalizacion.consultarDetalleCicloAportante";
    /**
     * Constante con el identificacador de la consulta que mostrara el detalle de un ciclo de fiscalizacion especifico y por usuario
     * especifico
     */
    public static final String CONSULTAR_CICLO_FISCALIZACION_CON_DETALLE_USUARIO = "Cartera.CicloFiscalizacion.ConsultarDetalle.usuario";
    /**
     * Constante con el identificador de la consulta que se encarga de mostrar las solicitudes de fiscalizacion por estados
     */
    public static final String CARTERA_SOLICITUD_FISCALIZACION_POR_ESTADOS = "Cartera.SolicitudFiscalizacion.ConsultarSolicitudFiscalizacionPorEstados";
    /**
     * Constatnte con el identificador de la consulta exclusion cartera activa
     */
    public static final String CONSULTAR_EXCLUSION_CARTERA_ACTIVA = "Cartera.exclusionCartera.consultarExclusionCatera";

    /**
     * Constante que se encarga de consultar las exclusiones de cartera por aportante
     */
    public static final String CONSULTAR_EXCLUSION_CARTERA_APORTANTE = "Cartera.exclusionCartera.consultarExclusionCartera.aportante";
    /**
     * Constante que se encarga de consultar los periodos de exclusion cartera en mora
     */
    public static final String CONSULTAR_PERIODOS_EXCLUSION_CARTERA_MORA = "Cartera.exclusionCartera.consultarPeriodosExclusionMora";
    /**
     * Constante que se encarga de consultar exclusiones cartera por id
     */
    public static final String CONSULTAR_EXCLUSION_CARTERA_POR_ID = "Cartera.exclusionCartera.consultarExclusionCarteraPorId";
    /**
     * Constante que se encarga de consultar convenios de pago por exclusion de la persona
     */
    public static final String CONSULTAR_CONVENIO_PAGO_EXCLUSION_POR_PERSONA = "Cartera.convenioPago.consultarConveniPagoExclusionPersona";
    /**
     * Constante que se encarga de consultar la trazabilidad de las exclusiones para una persona
     */
    public static final String CONSULTAR_TRAZABILIDAD_EXCLUSION_CARTERA = "Cartera.exclusionCartera.consultarTrazabilidadExclusionCartera.persona";
    /**
     * Constante que se encarga de consultar la trazabilidad de las exclusiones para un empleador
     */
    public static final String CONSULTAR_TRAZABILIDAD_EXCLUSION_CARTERA_EMPLEADOR = "Cartera.exclusionCartera.consultarTrazabilidadExclusionCartera.estado.empleador";
    /**
     * Constante que se encarga de consultar la trazabilidad de las exclusiones para un afiliado
     */
    public static final String CONSULTAR_TRAZABILIDAD_EXCLUSION_CARTERA_AFILIADO = "Cartera.exclusionCartera.consultarTrazabilidadExclusionCartera.estado.afiliado";
    /**
     * Constante que se encarga de consultar un convenio de pago por id
     */
    public static final String CONSULTAR_CONVENIO_PAGO_POR_ID = "Cartera.convenioPago.consultarConvenioPagoPorId";
    /**
     * Constante que se encarga de consultar las exclusiones de cartera para aportantes que se encuentren en estado activo como Empleador
     */
    public static final String CONSULTAR_EXCLUSION_CARTERA_APORTANTE_ACTIVA_EMPLEADOR = "Cartera.exclusionCartera.consultarExclusionesActivasAportanteEmpleador";
    /**
     * Constante que se encarga de consultar las exclusiones de cartera para aportantes que se encuentren en estado activo como
     * Independiente o pensionado
     */
    public static final String CONSULTAR_EXCLUSION_CARTERA_APORTANTE_ACTIVA_INDEPENDIENTE_PENSIONADO = "Cartera.exclusionCartera.consultarExclusionesActivasAportanteIndependientePensionado";
    /**
     * Constante que se encarga de consultar el estado de cartera por aportante
     */
    public static final String CONSULTAR_ESTADO_CARTERA_APORTANTE = "Cartera.consultarEstadoCartera";
    /**
     * Constante que se encarga de consultar la exclusion para convenio de pago
     */
    public static final String CONSULTAR_EXCLUSIONES_CONVENIO_PAGO = "Cartera.exclusionCartera.consultarExclusionConvenioPago";
    /**
     * Constante que se encarga de ejecutar la consulta de datos temporales parametrización
     */
    public static final String CONSULTAR_DATO_TEMPORAL_PARAMETRIZACION = "DatoTemporalParametrizacion.consultarDatoTemporalParametrizacion";
    /**
     * Constante que se encarga de ejecutar la consulta que trae los aportantes que se encuentran en cartera con estado de operación vigente
     * y en linea de cobro uno
     */
    public static final String CONSULTAR_APORTANTES_EN_CARTERA_LINEA_DE_COBRO_UNO_VIGENTE = "Cartera.consultarAportantesCarteraLCUnoVigente";
    /**
     * Constante que se encarga de ejecutar la consulta que trae los criterios de gestión de cobro por línea y método
     */
    public static final String CONSULTAR_CRITERIO_GESTION_DE_COBRO_POR_LINEA_Y_METODO = "Cartera.consultarCriterioGestionCobroPorLineaYMetodo";
    /**
     * Constante que se encarga de ejecutar la consulta de criterios de gestión de cobro para una línea de acción
     */
    public static final String CONSULTAR_CRITERIO_GESTION_DE_COBRO_POR_LINEA_Y_ACCION = "Cartera.consultarCriterioGestionCobroPorLineaYAccion";

    /**
     * Constante con el nombre del procedimiento almacenado que asigna las acciones de cobro a entidades en cartera
     */
    public static final String PROCEDURE_USP_EXECUTE_CARTERA_ASIGNAR_ACCION_COBRO = "Cartera.StoredProcedures.USP_ExecuteCARTERAAsignarAccionCobro";

    /**
     * Constante con el nombre del procedimiento almacenado que consulta el primer valor para un conjunto de secuencias
     */
    public static final String PROCEDURE_USP_GESTOR_VALOR_SECUENCIA = "Cartera.StoredProcedures.USP_GET_GestorValorSecuencia";

    /**
     * Constante que se encarga de ejecutar la consultar lod id de detalle de solicitud de gestión de cobro
     */
    public static final String CONSULTAR_DETALLE_SOLICITUD_ID_GESTION_COBRO = "Cartera.DetalleSolicitudGestionCobro.consultarIdDetalleSolicitudGestionCobro";

    /**
     * Constante que se encarga de ejecutar la consulta de detalle de solicitud de gestión de cobro
     */
    public static final String CONSULTAR_DETALLE_SOLICITUD_GESTION_COBRO = "Cartera.DetalleSolicitudGestionCobro.consultarDetalleSolicitudGestionCobro";

    /**
     * Constante que se encarga de ejecutar la consulta de detalle de solicitud de gestión de cobro integrada
     */
    public static final String CONSULTAR_DETALLE_SOLICITUD_GESTION_COBRO_INTEGRADO = "Cartera.DetalleSolicitudGestionCobro.consultarDetalleSolicitudGestionCobroIntegrado";

    /**
     * Constante con el nombre de la consulta que obtiene el último usuario con perfil "Back de actualización" a quien se le asignó una
     * tarea de gestión de cobro electrónico
     */
    public static final String CONSULTAR_ULTIMO_BACK_ACTUALIZACION_ELECTRONICO = "Cartera.Consultar.Ultimo.BackActualizacion.Electronico";
    public static final String CONSULTAR_USUARIO_ELECTRONICO = "Cartera.Consultar.Usuario .Electronico";

    /**
     * Constante con el nombre de la consulta que obtiene el último usuario con perfil "Analista de cartera de aportes" a quien se le asignó
     * una tarea de gestión de cobro físico
     */
    public static final String CONSULTAR_ULTIMO_ANALISTA_FISICO = "Cartera.Consultar.Ultimo.AnalistaAportes.Fisico";

    /**
     * Constante con el nombre de la consulta que obtiene una solicitud de gestión de cobro electronico por exclusiones
     */
    public static final String CONSULTAR_SOLICITUD_GESTION_COBRO_ELECTRONICO_POR_EXCLUSIONES = "Cartera.consultar.solicitud.gestion.cobro.electronico.exclusiones";
    /**
     * Constante con el nombre de la consulta que obtiene una solicitud de gestión de cobro fisico por exclusiones
     */
    public static final String CONSULTAR_SOLICITUD_GESTION_COBRO_FISICO_POR_EXCLUSIONES = "Cartera.consultar.solicitud.gestion.cobro.fisico.exclusiones";

    /**
     * Constante con el nobmre de la consulta que obtiene el listado de bitacoras
     */
    public static final String CONSULTAR_BITACORA_APORTANTE_CARTERA = "Cartera.consultar.bitacora.aportante.cartera";
    /**
     * Constante con el nobmre de la consulta que obtiene el listado de bitacoras
     */
    public static final String CONSULTAR_BITACORA_APORTANTE_CARTERA_SIN_RESULTADO = "Cartera.consultar.bitacora.aportante.cartera.sin.resultado";
    /**
     * Constante con el nombre de la consulta que obtiene el listado de deudas pertenecientes a una persona
     */
    public static final String CONSULTAR_DEUDA_APORTANTE_TIPO_PERSONA = "Cartera.consultar.deuda.aportante.tipo.persona";
    /**
     * Constante con el nombre de la consulta que obtiene el listado de deudas pertenecientes a un empleador
     */
    public static final String CONSULTAR_DEUDA_APORTANTE_TIPO_EMPLEADOR = "Cartera.consultar.deuda.aportante.tipo.empleador";

    /**
     * Constante con el nombre de la consulta del correo de la oficina principal
     */
    public static final String CORREO_OFICINA_PRINCIPAL = "correo.oficina.principal";

    /**
     * Constante con el nombre de la consulta del correo de la oficina principal
     */
    public static final String CORREO_REPRESENTANTE_LEGAL = "correo.representante.legal";

    /**
     * Constante con el nombre de la consulta del correo de la oficina principal
     */
    public static final String CORREO_RESPONSABLE_APORTES = "correo.responsable.aportes";

    /**
     * Constante con el nombre de la consulta que obtiene el correo del afiliado principal
     */
    public static final String CORREO_RESPONSABLE_AFILIADO = "correo.responsable.afiliado";

    /**
     * Constante con el nombre de la consulta que obtiene los documentos pertenecientes a la bitacora
     */
    public static final String CONSULTAR_DOCUMENTO_SOPORTE_BITACORA = "Cartera.consultar.documento.soporte.bitacora";
    /**
     * Constante con el nombre de la consulta que obtiene las solicitudes de gestión de cobro manuales
     */
    public static final String CONSULTAR_SOLICITUD_GESTION_COBRO_MANUAL = "Cartera.SolicitudGestionCobroManual.consultarSolicitud";

    /**
     * Constante con el nombre de la consulta que obtiene las solicitudes de gestión de cobro manuales
     */
    public static final String CONSULTAR_SOLICITUD_GESTION_COBRO_MANUAL_ESTADO = "Cartera.SolicitudGestionCobroManual.consultarSolicitud.estado";

    /**
     * Constante con el nombre de la consulta que obtiene las solicitudes de gestión de cobro manual, filtradas por identificador de cartera
     */
    public static final String CONSULTAR_SOLICITUD_GESTION_COBRO_MANUAL_CARTERA = "Cartera.Consultar.SolicitudManual.Cartera";

    /**
     * Constante que se encarga de ejecutar la consulta que trae los criterios de gestión de cobro por línea
     */
    public static final String CONSULTAR_CRITERIO_GESTION_DE_COBRO_POR_LINEA = "Cartera.consultarCriterioGestionCobroPorLinea";
    /**
     * Constante con el nombre de la consulta que trae los empleadores candidatos de exclusion de cartera
     */
    public static final String CONSULTAR_EMPLEADORES_CANDITADOS_EXCLUSION_CARTERA = "Cartera.consultar.empleadoresCandidatosExclusionCartera";
    /**
     * Constante con el nombre de la consulta que trae los empleadores candidatos de exclusion de cartera sin exclusiones
     */
    public static final String CONSULTAR_EMPLEADORES_CANDITADOS_EXCLUSION_CARTERA_SIN_EXCLUSION = "Cartera.consultar.empleadoresCandidatosExclusionCarteraSinExclusiones";
    /**
     * Constante con el nombre de la consulta que trae los afiliados candidatos de exclusion de cartera
     */
    public static final String CONSULTAR_AFILIADOS_CANDITADOS_EXCLUSION_CARTERA = "Cartera.consultar.afiliadosCandidatosExclusionCartera";
    /**
     * Constante con el nombre de la consulta que trae los empleadores candidatos de exclusion de cartera sin exclusiones
     */
    public static final String CONSULTAR_AFILIADOS_CANDITADOS_EXCLUSION_CARTERA_SIN_EXCLUSION = "Cartera.consultar.afiliadosCandidatosExclusionCarteraSinExclusiones";

    /**
     * Constante con el nombre de la consulta que obtiene el último consecutivo de liquidación generado para el periodo actual
     */
    public static final String CONSULTAR_ULTIMO_CONSECUTIVO_PERIODO_ACTUAL = "Cartera.consultar.UltimoConsecutivo.PeriodoActual";

    /**
     * Constante con el nombre de la consulta que obtiene el consecutivo existente de liquidación para una cartera especifica
     */
    public static final String CONSULTAR_EXISTENTE_CONSECUTIVO_LIQUIDACION = "Cartera.consultar.Existente.Consecutivo.Liquidacion";

    /**
     * Constante con el nombre de la consulta que obtiene los aportantes para la parametrización de mayor valor de aportes
     */
    public static final String CONSULTAR_APORTANTES_PARAMETRIZACION_MAYOR_VALOR_APORTES = "Consultar.aportantes.parametrizacion.mayorValorAportes";

    /**
     * Constante con el nombre de la consulta que obtiene los aportantes para la parametrización de cantidad de veces moroso
     */
    public static final String CONSULTAR_APORTANTES_PARAMETRIZACION_CANTIDAD_VECES_MOROSO = "Consultar.aportantes.parametrizacion.cantidadVecesMoroso";

    /**
     * Constante con el nombre de la consulta que obtiene una lista de identificadores de personas
     */
    public static final String CONSULTAR_APORTANTES_EXCLUSION_CONVENIO = "Consultar.aportantes.exclusion.convenio";

    /**
     * Constante que contiene el total de detalles de los aportantes por linea de cobro y ciclo y por usuario Analista.
     */
    public static final String CONSULTAR_TOTAL_APORTANTES_LINEA_COBRO_CICLO_USUARIO_ANALISTA = "Cartera.ConsultarAportantes.Total.LineaCobro.ciclo.usuario.analista";
    /**
     * Consulta una lista de aportantes de gestion manual con los estados, tipos d esolicitantes y los identificadores de las personas
     */
    public static final String CONSULTAR_APORTANTES_GESTION_MANUAL_DETALLE = "Consultar.aportantes.gestion.manual.detalle";

    /**
     * Constante que contiene el total de detalles de los aportantes por linea de cobro y por usuario Analista.
     */
    public static final String CONSULTAR_TOTAL_APORTANTES_LINEA_COBRO_CICLO = "Cartera.ConsultarAportantes.Total.LineaCobro.ciclo";
    /**
     * Consulta una lista de aportantes de gestion manual con los estados y tipos de solicitantes
     */
    public static final String CONSULTAR_APORTANTES_GESTION_MANUAL_SIN_DETALLE = "Consultar.aportantes.gestion.manual.sin.detalle";
    /**
     * Constante que contiene el nombre de la consulta para obtener los aportantes con acciones de cobros manuales ejecutadas por un usuario
     * analista especifico
     */
    public static final String CONSULTAR_APORTANTES_LINEA_COBRO_ACCION_COBRO_MANUAL_ANALISTA = "Cartera.ConsultarAportantes.LineaCobro.Accion.Manual.Analista";
    /**
     * Constante que contiene el nombre de la consulta para obtener los aportantes con acciones de cobros manuales
     */
    public static final String CONSULTAR_APORTANTES_LINEA_COBRO_ACCION_COBRO_MANUAL = "Cartera.ConsultarAportantes.LineaCobro.Accion.Manual";
    /**
     * Constante que se encarga de consultar los aportantes con mayor valor promedio con la linea de cobro.
     */
    public static final String CONSULTAR_APORTANTES_EMPLEADORES_PARAMETRIZACION_MAYOR_VALOR_APORTES_LINEA_COBRO = "Consultar.aportantes.empleadores.parametrizacion.mayorValorAportes.linea.cobro";
    /**
     * Constante que se encarga de consultar los aportantes con mayor veces morosos con linea de cobro
     */
    public static final String CONSULTAR_APORTANTES_EMPLEADORES_PARAMETRIZACION_CANTIDAD_VECES_MOROSO_LINEA_COBRO = "Consultar.aportantes.empleadores.parametrizacion.cantidadVecesMoroso.linea.cobro";
    /**
     * Constante que se encarga de consultar los aportante con mayor cantidad de trabajadores.
     */
    public static final String CONSULTAR_APORTANTES_EMPLEADORES_PARAMETRIZACION_CANTIDAD_TRABAJADORES_LINEA_COBRO = "Consultar.aportantes.empleadores.parametrizacion.cantidadTrabajadores.linea.cobro";

    /**
     * Constante con el nombre del procedimiento almacenado que registra en cartera las entidades que presentan impago
     */
    public static final String PROCEDURE_USP_EXECUTE_CARTERA_CREAR_CARTERA = "Cartera.StoredProcedures.USP_ExecuteCARTERACrearCartera";

    /**
     * Constante con el nombre del procedimiento almacenado que actualiza en cartera las entidades que presentan impago y las saca de la
     * línea, si es el caso
     */
    public static final String PROCEDURE_USP_EXECUTE_CARTERA_ACTUALIZAR_CARTERA = "Cartera.StoredProcedures.USP_ExecuteCARTERAActualizarCartera";

    /**
     * Constante con el nombre de la consulta que obtiene el listado de IDs de cartera pertenecientes a las lineas de cobro de un aportante
     */
    public static final String CONSULTAR_ID_CARTERA_POR_CADA_LINEA_DE_COBRO_POR_APORTANTE = "Cartera.consultar.idCarteraPorCadaLineaCobroAportante";

    /**
     * Constante con el nombre del procedimiento almacenado que calcula y guarda la deuda presunta para un empleador
     */
    public static final String PROCEDURE_USP_EXECUTE_CARTERA_CALCULAR_DEUDA_PRESUNTA_EMPLEADORES = "Cartera.StoredProcedures.USP_ExecuteCARTERACalcularDeudaPresuntaEmpleadores";

    /**
     * Constante con el nombre del procedimiento almacenado que calcula y guarda la deuda presunta para un independiente
     */
    public static final String PROCEDURE_USP_EXECUTE_CARTERA_CALCULAR_DEUDA_PRESUNTA_INDEPENDIENTES = "Cartera.StoredProcedures.USP_ExecuteCARTERACalcularDeudaPresuntaIndependientes";

    /**
     * Constante con el nombre del procedimiento almacenado que calcula y guarda la deuda presunta para un pensionado
     */
    public static final String PROCEDURE_USP_EXECUTE_CARTERA_CALCULAR_DEUDA_PRESUNTA_PENSIONADOS = "Cartera.StoredProcedures.USP_ExecuteCARTERACalcularDeudaPresuntaPensionados";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de aportantes con cartera vigente
     */
    public static final String CONSULTAR_APORTANTES_VIGENTES_CARTERA = "Cartera.Consultar.Aportantes.Vigentes.Cartera";

    /**
     * Constante con el nombre de la consulta que obtiene el estado de una solicitud de gestión de cobro
     */
    public static final String CONSULTAR_ESTADO_SOLICITUD_GESTION_COBRO = "Cartera.Consultar.EstadoSolicitud.GestionCobro";

    /**
     * Constante con el nombre de la consulta que obtiene el total de deuda de un aportante registrado en cartera, agrupada por línea de
     * cobro
     */
    public static final String CONSULTAR_CARTERA_APORTANTE = "Cartera.StoredProcedures.USP_ExecuteCARTERAConsultarCarteraAportante";

    /**
     * Constante con el nombre de la consulta que obtiene el detalle de cartera de un aportante, por línea de cobro
     */
    public static final String CONSULTAR_PERIODOS_APORTANTE_LINEACOBRO = "Cartera.Consultar.Periodos.Aportante.LineaCobro";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de cotizantes asociados a un aportante registrado en cartera
     */
    public static final String CONSULTAR_CARTERA_COTIZANTES_APORTANTE = "Cartera.Consultar.Cotizantes.Aportante.Cartera";

    /**
     * Constante que contiene el nombre de la consulta que obtiene la información de un documento almacenado en la tabla DocumentoSoporte
     */
    public static final String CONSULTAR_DOCUMENTO_SOPORTE = "Cartera.Consultar.DocumentoSoporte";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de detalle de solicitudes de gestión de cobro
     */
    public static final String CONSULTAR_DETALLE_GESTION = "Cartera.Consultar.Detalle.Gestion";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de detalle de solicitudes de gestión de cobro
     */
    public static final String CONSULTAR_DETALLE_GESTION_POR_CARTERA = "Cartera.DetalleSolicitudGestionCobro.consultar.porIdCartera";

    /**
     * Constante con el nombre de la consulta para obtener el o los empleadores que coincidan con el número de identificación ingresados por
     * parametro
     */
    public static final String CONSULTAR_EMPLEADOR_NUMERO_INDENTIFICACION = "Aporte.consultar.empleador.numero.identificacion";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de registros para la vista 360 Cartera -> Gestión de cartera
     */
    public static final String CONSULTAR_GESTION_CARTERA_360 = "Cartera.Consultar.Gestion.Cartera.360";

    public static final String CONSULTAR_GESTION_CARTERA_CARGUE = "Cartera.Consultar.Gestion.Cartera.Gestion";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de registros de bitácora para la vista 360 Cartera -> Gestión de cartera
     */
    public static final String CONSULTAR_BITACORA_CARTERA_360 = "Cartera.Consultar.Bitacora.Cartera.360";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de registros para la vista 360 Cartera -> Gestión de fiscalización
     */
    public static final String CONSULTAR_GESTION_FISCALIZACION_360 = "Cartera.Consultar.Gestion.Fiscalizacion.360";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de registros para la vista 360 Cartera -> Gestión preventiva
     */
    public static final String CONSULTAR_GESTION_PREVENTIVA_360 = "Cartera.Consultar.Gestion.Preventiva.360";

    /**
     * Constante con el nombre de la consulta que obtiene la información de un empleador de acuerdo a un identificador de cartera
     */
    public static final String CONSULTAR_EMPLEADOR_CARTERA = "Cartera.Consultar.Empleador.Cartera";

    /**
     * Constante con el nombre de la consulta que obtiene la información de un afiliado de acuerdo a un identificador de cartera
     */
    public static final String CONSULTAR_ROLAFILIADO_CARTERA = "Cartera.Consultar.RolAfiliado.Cartera";

    /**
     * Constante con el nombre de la consulta que obtiene un ciclo de fiscalización por identificador
     */
    public static final String CONSULTAR_CICLO_APORTANTE_ID = "Cartera.Consultar.CicloAportante.Id";

    /**
     * Constante con el nombre de la consulta que desactiva exclusiones de cartera no vigentes
     */
    public static final String ACTUALIZAR_EXCLUSION_CARTERA = "Cartera.Actualizar.Exlusion.Cartera";

    /**
     * Constante con el nombre de la consulta que determina si un empleador es candidato a expulsión
     */
    public static final String CONSULTA_VALIDACION_EMPLEADOR_EXPULSION = "Cartera.Consultar.EmpleadorCandidatoExpulsion";

    /**
     * Constante con el nombre de la consulta que determina si una persona es candidata a expulsión
     */
    public static final String CONSULTA_VALIDACION_PERSONA_EXPULSION = "Cartera.Consultar.PersonaCandidatoExpulsion";

    /**
     * Constante con el nombre de la consulta que obtiene el total de deuda real registrar por un empleador
     */
    public static final String CONSULTAR_DEUDA_REAL = "Cartera.Consultar.DeudaReal";

    /**
     * Constante con el nombre de la consulta que obtiene el total de deuda real registrar por un empleador
     */
    public static final String CONSULTAR_BITACORA_DEUDA_REAL = "Cartera.consultar.bitacora.deudaReal";

    /**
     * Constante con el nombre de actualizar deudaReal por idPersona
     */
    public static final String ACTUALIZAR_CARTERA_DEPENDIENTE_DEUDA_REAL_IDPERSONA = "CarteraPendiente.actualizar.Deuda.Real";

    /**
     * Constante con el nombre de la consulta que obtiene el estado en cartera de un aportante
     */
    public static final String CONSULTAR_ESTADO_CARTERA_INTEGRACION = "Cartera.Consultar.Estado.Cartera.Integracion";

    /**
     * Constante con el nombre de la consulta que obtiene el detalle del estado en cartera de un aportante
     */
    public static final String CONSULTAR_ESTADO_CARTERA_DETALLE_INTEGRACION = "Cartera.Consultar.Estado.Cartera.Detalle.Integracion";

    /**
     * Constante con el nombre de la consulta que obtiene la información de un convenio de pago por identificación del aportante
     */
    public static final String CONSULTAR_ESTADO_CARTERA_CONVENIO_PAGO_INTEGRACION = "Cartera.Consultar.Estado.Cartera.Convenio.Pago.Integracion";

    /**
     * Constante con el nombre de la consulta que obtiene la información de un convenio de pago por número
     */
    public static final String CONSULTAR_CONVENIO_PAGO_INTEGRACION = "Cartera.Consultar.Convenio.Pago.Integracion";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de solicitudes agrupadoras candidatas a cierre por extemporaneidad
     */
    public static final String CARTERA_SOLICITUD_AGRUPADORA_CIERRE_PREVENTIVA = "Carteta.Consulta.Solicitud.Agrupadora.Cierre.Preventiva";

    /**
     * Constante con el nombre de la consulta que obtiene el estado en cartera de un aportante o cotizante
     */
    public static final String OBTENER_ESTADO_CARTERA_INTEGRACION = "Cartera.Obtener.Estado.Cartera.Integracion";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de empleadores asociados a un cotizante en cartera
     */
    public static final String OBTENER_COTIZANTE_APORTANTE = "Cartera.Consultar.Empleador.Cotizante";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de números de convenios de pago asociados a un aportante
     */
    public static final String OBTENER_CONVENIO_APORTANTE = "Cartera.Consultar.Convenio.Aportante";

    /**
     * Constante con el nombre de la consulta que obtiene los datos temporales de cartera, por número de operación
     */
    public static final String CONSULTAR_DATO_TEMPORAL_CARTERA = "Cartera.Consultar.DatoTemporalCartera";

    /**
     * Constante con el nombre de la consulta que obtiene un registro de cartera por número de operación
     */
    public static final String CONSULTAR_CARTERA_NUMERO_OPERACION = "Cartera.Consultar.Cartera.NumeroOperacion";

    /**
     * Constantes nativas registros temporales
     */

    /**
     * Constante con el nombre de la consulta que obtiene el número de operación de cartera por identificador del registro en cartera
     */
    public static final String CONSULTAR_NUMERO_OPERACION_CARTERA = "Cartera.Consultar.Numero.Operacion.Cartera";


    /**
     * Constante con el nombre de la consulta que obtiene la vigencia de operación de cartera por identificador del registro en cartera
     */
    public static final String CONSULTAR_VIGENCIA_OPERACION_CARTERA = "Cartera.Consultar.Vigencia.Operacion.Cartera";
    /**
     * Constante con el nombre de la consulta que obtiene un registro de la tabla DocumentoCartera, donde se almacenan los comunicados de
     * las acciones de cobro automáticas
     */
    public static final String CONSULTAR_DOCUMENTO_CARTERA = "Cartera.Consultar.Documento.Cartera";

    /**
     * Constante con el nombre de la consulta que obtiene los registros de bitácora de un ciclo de fiscalización
     */
    public static final String CONSULTAR_BITACORA_FISCALIZACION_360 = "Cartera.Consultar.Bitacora.Fiscalizacion";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de documentos asociados a la bitácora de fiscalización
     */
    public static final String CONSULTAR_DOCUMENTOS_BITACORA_FISCALIZACION = "Consultar.Documentos.Bitacora.Fiscalizacion";

    /**
     * Constante con el nombre de la consulta que obtiene los aportantes que cumplen los criterios de gestión de cobro
     */
    public static final String CONSULTAR_APORTANTES_GESTION_COBRO = "Cartera.StoredProcedures.USP_ExecuteCARTERAConsultarAportantesGestionCobro";

    /**
     * Constante con el nombre de la consulta que obtiene los aportantes que cumplen los criterios de gestión de cobro
     */
    public static final String CONSULTAR_APORTANTES_GESTION_COBRO_PERSONA = "Cartera.StoredProcedures.USP_ExecuteCARTERAConsultarAportantesGestionCobroPersonas";

    /**
     * Constante con el nombre de la consulta que obtiene la parametrización de la linea de cobro de persona ya sea independiente o
     * pensionado
     */
    public static final String CONSULTAR_LINEA_COBRO = "Cartera.LineaCobroPersona.Consultar";

    /**
     * Constante con el nombre de la consulta que obtiene los aportantes que cumplen los criterios de desafiliación
     */
    public static final String CONSULTAR_APORTANTES_DESAFILIACION = "Cartera.StoredProcedures.USP_ExecuteCARTERAConsultarAportantesDesafiliacion";

    /**
     * Constante con el nombre que consulta el metodo de envio parametrizado activo en el momento
     */
    public static final String CONSULTAR_METODO_CRITERIO_GESTION_COBRO_ACTIVO = "Cartera.MetodoEnvioComunicadoEnum.consultarMetodoCriterioGestionCobro";

    /**
     * Constante con el nombre que elimina un dato temporal de cartera buscando con el número de operación
     */
    public static final String ELIMINAR_DATO_TEMPORAL_CARTERA = "Cartera.DatoTemporalCartera.eliminar";

    /**
     * Consulta que obtiene el id de la entidad asociada a un tipo y numero de identificacion asociados a un tipo de aportante
     */
    public static final String CONSULTAR_ID_ENTIDAD_COMUNICADO = "Cartera.IdEntidadComunicado.consultar";

    /**
     * Consulta que obtiene el id de la entidad asociada a un tipo y numero de identificacion asociados a un tipo de aportante
     */
    public static final String CONSULTAR_DATOS_CARTERA_NUMERO_OPERACION = "Cartera.consultar.datos.porIdCartera";

    /**
     * Constante con el nombre de la consulta de los aportantes que se encuentran en convenio de pago dados unos filtros
     */
    public static final String CONSULTAR_CONVENIOS_PAGO_FILTRO = "Cartera.consultar.convenios.pago.filtros";

    /**
     * Constante con el nombre de la consulta de las actividades de cartera
     */
    public static final String CONSULTAR_ACTIVIDADES_CARTERA = "Cartera.consultar.actividades.cartera";

    /**
     * Constante con el nombre de la consulta de las agendas de cartera
     */
    public static final String CONSULTAR_AGENDAS_CARTERA = "Cartera.consultar.agendas.cartera";

    /**
     * Constante con el nombre de la consulta de las actividades de cartera
     */
    public static final String CONSULTAR_ACTIVIDADES_CARTERA_FISCALIZACION = "Cartera.consultar.actividades.cartera.fiscalizacion";

    /**
     * Constante con el nombre de la consulta de las agendas de cartera
     */
    public static final String CONSULTAR_AGENDAS_CARTERA_FISCALIZACION = "Cartera.consultar.agendas.cartera.fiscalizacion";

    /**
     * Constante con el nombre de la actualización de actividades de cartera
     */
    public static final String ACTUALIZAR_ACTIVIDADES_CARTERA = "Cartera.actualizar.actividades.cartera";

    /**
     * Constante con el nombre de la actualización de las agendas de cartera
     */
    public static final String ACTUALIZAR_AGENDAS_CARTERA = "Cartera.actualizar.agendas.cartera";

    /**
     * Constante con el nombre de la consulta de la parametrización preventiva en cartera
     */
    public static final String CONSULTAR_PARAMETRIZACION_PREVENTIVA = "Cartera.StoredProcedures.USP_ExecuteCARTERAConsultarGestionPreventiva";

    /**
     * Constante con el nombre de la consulta de la parametrización de fiscalización en cartera
     */
    public static final String CONSULTAR_PARAMETRIZACION_FISCALIZACION = "Cartera.StoredProcedures.USP_ExecuteCARTERAConsultarGestionFiscalizacion";

    /**
     * Constante con el nombre del procedimiento almacenado que actualiza masivamente los DetallesSolicitudGestionCobro
     */
    public static final String PROCEDURE_USP_EXECUTE_CARTERA_ACTUALIZAR_DETALLE_SOLICITUD_GESTION_COBRO_OK = "Cartera.StoredProcedures.USP_ExecuteCARTERAActualizarDetalleSolicitudGestionCobroOK";

    /**
     * Constante con el nombre del procedimiento almacenado que registra en cartera las entidades que presentan impago
     */
    public static final String PROCEDURE_USP_EXECUTE_CARTERA_ACTUALIZAR_CARTERA_OK = "Cartera.StoredProcedures.USP_ExecuteCARTERAActualizarCarteraOK";

    /**
     * Constante con el nombre de la consulta que trae los documentos asociados a una actividad de cartera
     */
    public static final String CONSULTAR_DOCUMENTOS_ACTIVIDAD_CARTERA = "cartera.documentos.actividad.cartera";

    /**
     * Constante con el nombre de la función que consulta la deuda presunta
     */
    public static final String PROCEDURE_USP_OBTENER_DEUDA_PRESUNTA = "Cartera.StoredProcedures.USP_GET_ConsultarDeudaPresunta";

    /**
     * Constante con la clave de la consulta de un ciclo fiscalización del aportante.
     */
    public static final String CONSULTAR_CICLO_FISCALIZACION_APORTANTE = "Cartera.CicloFiscalizacion.ConsultarCicloAportante";

    /**
     * Constante con la clave de la consulta de cartera por tipo de solicitante y el id de persona que se encuentre con un estaod de operación vigente
     */
    public static final String CONSULTAR_CARTERA_PERSONA = "Cartera.CarteraModelo.persona.tipoSolicitante";

    /**
     * Constante con la clave de la consulta las exclusiones de cartera que se inactivaran
     */
    public static final String CONSULTAR_EXCLUSION_CARTERA_INACTIVAR = "Cartera.consultar.exclusionCartera.inactivar";

    /**
     * Constante con named query de consulta para una novedad por tipo de
     * transacción.
     */
    public static final String CONSULTAR_PERIODO_APORTE_POR_REG_DETALLADO = "Novedad.consultar.periodo.aporte.por.reg.detallado";

    /**
     * Constante con named query de consulta tipo de accion de cobro 2D
     * transacción.
     */
    public static final String CONSULTAR_PARAMETRZACION_TIPO_ACCION_COBRO_2D = "Cartera.consultar.parametrizacion.accion.2d";


    /**
     * Constante con named query de consulta tipo de accion de cobro 2E
     * transacción.
     */
    public static final String CONSULTAR_PARAMETRZACION_TIPO_ACCION_COBRO_2E = "Cartera.consultar.parametrizacion.accion.2e";

    /**
     * Constante con named query de consulta tipo de accion de cobro 2E
     * transacción.
     */
    public static final String INSERTAR_PERSONA_CARTERA = "Cartera.guardar.registro.persona";

    public static final String INSERTAR_CARTERA_APORTANTE = "Cartera.guardar.registro.aportante";

    public static final String INSERTAR_CARTERA_AGRUPADORA = "Cartera.guardar.registro.cartera.agrupadora";

    public static final String CONSULTAR_ESTADO_APORTANTE_EMPLEADOR = "Cartera.consultar.estado.aportante";

    public static final String CONSULTAR_NUMERO_OPERACION = "Cartera.consultar.numero.operacion";
    
    public static final String CONSULTAR_PARAMETRZACION_TIPO_ACCION_COBRO_2C = "Cartera.consultar.parametrizacion.accion.2c";


    /**
     * Constante para invocar NamedQuery de consulta de cartera por linea de cobro y accion de cobro
     */
    public static final String CONSULTAR_CARTERA_POR_LINEA_DE_COBRO_Y_ACCION_DE_COBRO = "Cartera.consultar.lineaDeCobro.accionDeCobro";
    /**
     * Constante para invocar NamedQuery de consulta de cartera por accion de cobro
     */
    public static final String CONSULTAR_CARTERA_POR_ACCION_DE_COBRO = "Cartera.consultar.accionDeCobro";
    /**
     * Constante para invocar NamedQuery de consulta de Persona por id
     */
    public static final String CONSULTAR_NUMERO_TIPO_ID_PERSONA = "Cartera.consultar.numero.tipo.idpersona";

    /**
     * Constante para invocar NamedQuery de consulta de firmeza de titulo por id de persona
     */
    public static final String CONSULTAR_FIRMEZA_TTTULO_ID_PERSONA = "CarteraBitacora.consultar.firmeza.titulo";

    /**
     * Constante con el nombre de la consulta cartera dependiente por idCartera
     */
    public static final String CONSULTAR_CARTERA_BITACORA_IDCARTERA = "Cartera.Consultar.CarteraBitacora.IdCartera";

    /**
     * Constante con el nombre de la consulta que desactiva por idPersona exclusiones de cartera no vigentes
     */
    public static final String ACTUALIZAR_EXCLUSION_CARTERA_INACTIVAR = "Cartera.Actualizar.Exlusion.Cartera.inactivar";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de cotizantes asociados a un aportante registrado en cartera
     */
    public static final String CONSULTAR_CARTERA_ENVIO_COMUNICADO_H2C6_F1C6 = "Cartera.consultar.envioo.comunicado.h2c6.f1c6";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de cotizantes asociados a un aportante registrado en cartera
     */
    public static final String CONSULTAR_CARTERA_ENVIO_COMUNICADO_H2C6_F1C6_EXPULSION = "Cartera.consultar.envioo.comunicado.h2c6.f1c6.explulsion";

    public static final String CONSULTAR_CARTERA_ENVIO_COMUNICADO_H2C6_F1C6_EXPULSION_POR_IDS = "Cartera.consultar.envioo.comunicado.h2c6.f1c6.explulsion.por.ids";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de cotizantes asociados a un aportante registrado en cartera
     */
    public static final String CONSULTAR_CARTERA_FUNCION_CALCULO_DIAS_ENVIO_COMUNICADO_H2C2_F1C6 = "Cartera.consultar.funcion.calculo.dias.comunicado.h2c6.f1c6";
    /**
     * Constante la actividad de una empresa en bitacora
     */
    public static final String CONSULTAR_ACTIVIDAD_PERID_CARID = "Cartera.consultar.actividad.carId.perId";

    public static final String CONSULTAR_CARTERA_ENVIO_COMUNICADO_H2C6_F1C6_EXPULSION_PRUEBA = "Cartera.consultar.envioo.comunicado.h2c6.f1c6.explulsion.prueba";

    /**
     * Constante con el nombre de la consulta que obtiene las actividades en bitacota por numero de identificacion
     */
    public static final String CONSULTAR_ACTIVIDADES_IDENTIFICACION = "Cartera.consultar.actividades.identificacion.bitacora";
    /**
     * Constante con el nombre de la consulta que obtiene las actividades en bitacota por numero de identificacion
     */
    public static final String CONSULTAR_DIAS_GENERACION_ACTA_LIQUIDACION = "Cartera.consultar.accion2C.diasActa";

    public static final String CONSULTAR_DIAS_NOTIFICACION_BITACORA = "Cartera.consultar.fecha.notificacion.bitacora";
    /**
     * contante para la consulta de novedades por id
     */
    public static final String CONSULTAR_NOVEDADES_ID = "Cartera.consultar.novedades.id";
    /**
     * Constante para consultar parametrizacion de accion de cobro 2c
     */
    public static final String CONSULTAR_ACCION_COBRO_2C = "Cartera.consultar.accion.cobro.2c";
    /**
     * constante para insertar un nuevo registro en la tabla de parametrizacion de accion de cobro 2c
     */
    public static final String INSERTAR_ACCION_COBRO_2C = "Cartera.guardar.registro.accion.cobro.2c";
    /**
     * Constante para actualizar un registro en la tabla de parametrizacion de accion de cobro 2c
     */
    public static final String ACTUALIZAR_ACCION_COBRO_2C = "Cartera.actualizar.registro.accion.cobro.2c";
    /**
     * Constante para consultar id parametrizacion de accion de cobro 1D
     */
    public static final String CONSULTAR_ID_ACCION_COBRO_1D = "Cartera.consultar.accion.cobro.1d.id";
    /**
     * constante para insertar un nuevo registro en la tabla de parametrizacion de accion de cobro 2c
     */
    public static final String INSERTAR_ACCION_COBRO_1D = "Cartera.guardar.registro.accion.cobro.1d";
    /**
     * Constante para actualizar un registro en la tabla de parametrizacion de accion de cobro 2c
     */
    public static final String ACTUALIZAR_ACCION_COBRO_1D = "Cartera.actualizar.registro.accion.cobro.1d";
    /**
     * Constante para consultar parametrizacion de accion de cobro 1D
     */
    public static final String CONSULTAR_ACCION_COBRO_1D = "Cartera.consultar.accion.cobro.1d";
    /**
     * Constante para la consulta de nuemro de operacion en bitacora
     */
    public static final String CONSULTAR_NUM_OPERACION_BITACORA = "Cartera.consultar.numero.operacion.bitacora";
    /**
     * Constante para consultar id parametrizacion de accion de cobro 2F
     */
    public static final String CONSULTAR_ID_ACCION_COBRO_2F = "Cartera.consultar.accion.cobro.2F.id";
    /**
     * constante para insertar un nuevo registro en la tabla de parametrizacion de accion de cobro 2F
     */
    public static final String INSERTAR_ACCION_COBRO_2F = "Cartera.guardar.registro.accion.cobro.2F";
    /**
     * Constante para actualizar un registro en la tabla de parametrizacion de accion de cobro 2F
     */
    public static final String ACTUALIZAR_ACCION_COBRO_2F = "Cartera.actualizar.registro.accion.cobro.2F";
    /**
     * constante para consultar las actividades en bitacra cartera
     */
    public static final String CONSULTAR_ACTIVIDAD_BITACORA_CARTERTA_NUMERO_IDENTENDIFICACION = "CarteraBitacora.consultar.firmeza.titulo.numero.identificacion";
    /**
     * constante para consultar cartera  con cartera dependiente
     */
    public static final String CONSULTAR_CARTERA_CARTERA_DEPENDIENTE_ID = "Cartera.consultar.cartera.dependiente";
    /**
     * constante para consultar numero de identificacion por id de cartera
     */
    public static final String CONSULTAR_IDENTIFICACION_CARTERA_ID = "Cartera.consultar.identificacion.cartera.id";
    /**
     * Constante con el nombre consultar cartera dependiente
     */
    public static final String CONSULTAR_CARTERA_DEPENDIENTE_ID = "Cartera.consultar.cartera.dependiente.id";
    /**
     * Constante con el nombre cartera por id
     */
    public static final String CONSULTAR_CARTERA_ID = "Cartera.consultar.cartera.id.cartera";
    /**
     * Constante con el nombre persona por numero de identificacion
     */
    public static final String CONSULTAR_PERSONA_NUMERO_IDENTIFICACION = "Cartera.persona.numero.identificacion";
    /**
     * Constante con el nombre persona por numero de identificacion y periodo
     */
    public static final String CONSULTAR_PERSONA_NUMERO_IDENTIFICACION_PERIODO = "Cartera.consultar.periodo.numero.identificacion";
    /**
     * Constante con el id maximo cartera
     */
    public static final String CONSULTAR_ID_MAX_CARTERA = "Cartera.consultar.id.max";
    /**
     * Constante con el id maximo cartera agrupadora
     */
    public static final String CONSULTAR_ID_MAX_CARTERA_AGRUPADORA = "Cartera.cartera.agrupadora.consultar.id.max";

    /**
     * Constante con el id maximo cartera dependiente
     */
    public static final String CONSULTAR_ID_MAX_CARTERA_DEPENDIENTE = "Cartera.cartera.dependiente.consultar.id.max";


    /**
     * Constante para la consulta de datos de la plantilla de documento de fiscalizacion
     */
    public static final String CONSULTAR_DATA_DOCUMENTO_FISCALIZACION = "plantilla.DocumentoFiscalizacion.Data";

    /**
     * Constante para la consulta de datos de una persona en bitacora por actividad y perId
     */
    public static final String CONSULTAR_BITACORA_CATERA = "Cartera.consultar.actividad.bitacora";

     /**
     * Constante para la consulta de datos de una persona en bitacora por actividad y perId
     */
    public static final String CONSULTAR_BITACORA_CATERA_PERSONA_RADICADO = "Cartera.consultar.bitacora.persona.radicado";

     /**
     * Constante para la consulta de datos de una persona en bitacora por actividad y perId
     */
    public static final String CONSULTAR_BITACORA_A_ACTUALIZAR_CATERA_PERSONA_RADICADO = "Cartera.consultar.bitacora.a.actualizar.persona.radicado";

    /**
     * Constante para find Documento y seguimientos gestion
     */
    public static final String FIND_DOCUMENTOS_SEGUIMIENTO_GESTION = "Cartera.find.documentos.seguimiento.gestion";
    /**
     * Constante para find Documento y seguimientos novedades
     */
    public static final String FIND_DOCUMENTOS_SEGUIMIENTO_NOVEDADES = "Cartera.find.documentos.seguimiento.novedades";
    /**
     * Constante para find Documento y seguimientos convenio pagos
     */
    public static final String FIND_DOCUMENTOS_SEGUIMIENTO_CONVENIO_PAGO = "Cartera.find.documentos.seguimiento.convenio.pago";
    /**
     * Constante para consultar firmeza por periodo
     */
    public static final String CONSULTAR_PERIODO_FIRMEZA_TITULO = "Cartera.consultar.periodo.firmeza.titulo";
    /**
     * Constante para consultar linea de cobro uno en mora parcial
     */
    public static final String CONSULTAR_LINEA_COBRO_UNO_MORA_PARCIAL = "Cartera.consultar.moral.parcial";
    /**
     * Constante para consultar linea de cobro uno exclusion en mora parcial
     */
    public static final String CONSULTAR_LINEA_COBRO_UNO_EXCLUSION_MORA_PARCIAL = "Cartera.consultar.exclusion.moral.parcial";
    /**
     * Constante para invocar NamedQuery de consulta  PERSONAS PARA HACERLE FIRMEZA
     */
    public static final String STORED_PROCEDURE_CONSULTAR_PERSONAS_FIRMEZA_TITULO = "stored.procedure.Cartera.consultar.personas.firmeza";

    /**
     * Constante para invocar NamedQuery para prescribir en cartera
     */
    public static final String STORED_PROCEDURE_CARTERA_PRESCRITA = "stored.procedure.Cartera.prescrita";

    /**
     * Constante para consultar cuenta las actividades prescrita
     */
    public static final String CONSULTAR_CARTERA_PRESCRITA = "Cartera.consultar.prescrita";
    /**
     * Constante para consultar cuenta las actividades generar liquidacion
     */
    public static final String CONSULTAR_CARTERA_TOTAL = "Cartera.consultar.total";

    /**
     * Constante para consultar actividad bitacora por numeroOperacion
     */
    public static final String CONSULTAR_ACTIVIDAD_BITACORA_NUMERO_OPERACION = "Cartera.consultar.actividad.bitacora.numero.operacion";

    public static final String STORED_PROCEDURE_CARTERA_PANEL = "stored.procedure.Cartera.panel";

    public static final String STORED_PROCEDURE_CARTERA_PANEL_CONTEO = "stored.procedure.Cartera.panel.conteo";
}

