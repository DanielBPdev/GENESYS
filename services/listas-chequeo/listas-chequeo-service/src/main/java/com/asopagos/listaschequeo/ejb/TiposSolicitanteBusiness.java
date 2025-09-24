package com.asopagos.listaschequeo.ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.asopagos.cache.CacheManager;
import com.asopagos.constants.ConstantesSistemaConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.converter.SujetoTramiteUtils;
import com.asopagos.dto.ElementoListaDTO;
import com.asopagos.entidades.transversal.personas.ISujetoTramite;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoRequisitoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoHogarEnum;
import com.asopagos.enumeraciones.personas.TipoIntegranteHogarEnum;
import com.asopagos.enumeraciones.personas.TipoJefeHogarEnum;
import com.asopagos.listaschequeo.constants.NamedQueriesConstants;
import com.asopagos.listaschequeo.dto.TransaccionRequisitoDTO;
import com.asopagos.listaschequeo.service.TiposSolicitanteService;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados
 * con la gestión de listas de chequeo para la afiliación de empleadores
 * <b>Historia de Usuario:</b> HU-TRA-061 Administración general de listas de
 * chequeo
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Stateless
public class TiposSolicitanteBusiness implements TiposSolicitanteService {
    /**
     * Representa los sujetos trámite que son susceptibles de ser configurados para una transacción específica.
     */
    private final static Map<TipoTransaccionEnum, List<ISujetoTramite>> txSujetoTramite = new HashMap<>();
    /**
     * Representa la asociación entre transacciones y clasificaciones cuando la transacción aplica a una única clasificación
     */
    private final static Map<TipoTransaccionEnum, ClasificacionEnum> txClasificacion = new HashMap<>();

    static {
        // El proceso 1.1.1 para sus transacciones puede ser configurado sólo para empleador
        txSujetoTramite.put(TipoTransaccionEnum.AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION,
                Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        //Proceso 1.1.2 en sus dos transacciones, se puede configurar sólo para empleador
        txSujetoTramite.put(TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.AFILIACION_EMPLEADORES_WEB_REINTEGRO, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        //Proceso 1.2.1 se puede configurar para todos los tipos de afiliados principales, y además para los distintos tipos de Beneficiario. 
        txSujetoTramite.put(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION,
                Arrays.asList(TipoAfiliadoEnum.PENSIONADO, TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE,
                        TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO,
                        TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO,
                Arrays.asList(TipoAfiliadoEnum.PENSIONADO, TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE,
                        TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO,
                        TipoBeneficiarioEnum.PADRES));
        //Proceso 1.2.2. Afiliación de trabajadores dependientes web - acceso empleador, solo se puede configurar para trabajadores dependientes y para sus beneficiarios 
        txSujetoTramite.put(TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION,
                Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO,
                        TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO,
                Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO,
                        TipoBeneficiarioEnum.PADRES));
        //Proceso 1.2.3. Afiliación de independientes / pensionados web, se configura para todos los tipos de personas y sus beneficiarios a excepción de los trabajadores dependientes
        txSujetoTramite.put(TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_NUEVA_AFILIACION,
                Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE,
                        TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.AFILIACION_PERSONAS_WEB_INDEPENDIENTE_PENSIONADO_REINTEGRO,
                Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE,
                        TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));

        //=======================Transacciones novedades Empleador===============================
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_TIPO_NUMERO_DOCUMENTO, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_RAZON_SOCIAL_NOMBRE, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_PRESENCIAL,
                Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_WEB, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIOS_OTROS_DATOS_IDENTIFICACION_EMPLEADOR, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL,
                Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_WEB, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_PRESENCIAL,
                Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_WEB, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_PRESENCIAL,
                Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_WEB,
                Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_PRESENCIAL,
                Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_WEB,
                Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_MEDIO_PAGO_EMPLEADOR_PRESENCIAL, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_MEDIO_PAGO_EMPLEADOR_WEB, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_CODIGO_NOMBRE_SUCURSAL, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_CODIGO_NOMBRE_SUCURSAL_WEB, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_DATOS_SUCURSAL_PRESENCIAL, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_DATOS_SUCURSAL_WEB, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_ACTIVIDAD_ECONOMICA_SUCURSAL_PRESENCIAL, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_ACTIVIDAD_ECONOMICA_SUCURSAL_WEB, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_MEDIO_PAGO_SUCURSAL_PRESENCIAL, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_MEDIO_PAGO_SUCURSAL_WEB, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.ACTIVAR_INACTIVAR_CODIGO_SUCURSAL_DEBE_COINCIDIR_CON_PILA,
                Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.AGREGAR_SUCURSAL, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.INACTIVAR_SUCURSAL, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIOS_ROLES_CONTACTO_PRESENCIAL, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIOS_ROLES_CONTACTO_WEB, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_RESPONSABLE_CONTACTOS_CFF, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.TRASLADO_TRABAJADORES_ENTRE_SUCURSALES, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.SUSTITUCION_PATRONAL, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.DESAFILIACION, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZAR_DOCUMENTACION_AFILIADO_PRESENCIAL, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZAR_DOCUMENTACION_AFILIADO_WEB, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.REGISTRAR_SUBSANACION_EXPULSION_PRESENCIAL, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        txSujetoTramite.put(TipoTransaccionEnum.REGISTRAR_SUBSANACION_EXPULSION_WEB, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        //Las novedades automáticas ejecutadas por procesos periódicos programados no pueden tener requisitos documentales. REVISAR
        //txSujetoTramite.put(TipoTransaccionEnum.INACTIVAR_AUTOMATICAMENTE_BENEFICIO_LEY_1429_2010, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        //txSujetoTramite.put(TipoTransaccionEnum.INACTIVAR_AUTOMATICAMENTE_BENEFICIOS_LEY_590_2000, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));
        //txSujetoTramite.put(TipoTransaccionEnum.INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_EMPLEADOR, Arrays.asList(TipoEmpleadorEnum.EMPLEADOR));

        //============ Transacciones con clasficaciones específicas EMPLEADOR ==========================//
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_NATURALEZA_JURIDICA, ClasificacionEnum.PERSONA_JURIDICA);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL, ClasificacionEnum.PERSONA_JURIDICA);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIOS_LEY_1429_2010_WEB, ClasificacionEnum.PERSONA_JURIDICA);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL, ClasificacionEnum.PERSONA_JURIDICA);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_LEY_1429_2010_WEB, ClasificacionEnum.PERSONA_JURIDICA);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL, ClasificacionEnum.PERSONA_JURIDICA);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIOS_LEY_590_2000_WEB, ClasificacionEnum.PERSONA_JURIDICA);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL, ClasificacionEnum.PERSONA_JURIDICA);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_LEY_590_2000_WEB, ClasificacionEnum.PERSONA_JURIDICA);

        //=======================Transacciones novedades Personas===============================
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB, ClasificacionEnum.CONYUGE);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL, ClasificacionEnum.CONYUGE);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_CONYUGE_WEB, ClasificacionEnum.CONYUGE);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB, ClasificacionEnum.HIJO_BIOLOGICO);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL, ClasificacionEnum.HIJO_BIOLOGICO);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_WEB, ClasificacionEnum.HIJO_BIOLOGICO);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB, ClasificacionEnum.HIJASTRO);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL, ClasificacionEnum.HIJASTRO);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HIJASTRO_WEB, ClasificacionEnum.HIJASTRO);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_DEPWEB, ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_PRESENCIAL, ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_HERMANO_HUERFANO_WEB, ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_DEPWEB, ClasificacionEnum.HIJO_ADOPTIVO);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_PRESENCIAL, ClasificacionEnum.HIJO_ADOPTIVO);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIO_HIJO_ADOPTIVO_WEB, ClasificacionEnum.HIJO_ADOPTIVO);
        txClasificacion.put(TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_DEPWEB, ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
        txClasificacion.put(TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL, ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
        txClasificacion.put(TipoTransaccionEnum.ACTIVA_BENEFICIARIO_EN_CUSTODIA_WEB, ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_DEPWEB, ClasificacionEnum.PADRE);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_PRESENCIAL, ClasificacionEnum.PADRE);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_PADRE_WEB, ClasificacionEnum.PADRE);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_DEPWEB, ClasificacionEnum.MADRE);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_WEB, ClasificacionEnum.MADRE);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIO_MADRE_PRESENCIAL, ClasificacionEnum.MADRE);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_CONYUGE_DEPWEB, ClasificacionEnum.CONYUGE);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_CONYUGE_PRESENCIAL, ClasificacionEnum.CONYUGE);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_CONYUGE_WEB, ClasificacionEnum.CONYUGE);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_DEPWEB, ClasificacionEnum.HIJO_BIOLOGICO);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL, ClasificacionEnum.HIJO_BIOLOGICO);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_WEB, ClasificacionEnum.HIJO_BIOLOGICO);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJASTRO_DEPWEB, ClasificacionEnum.HIJASTRO);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJASTRO_PRESENCIAL, ClasificacionEnum.HIJASTRO);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJASTRO_WEB, ClasificacionEnum.HIJASTRO);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HUERFANO_DEPWEB, ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HUERFANO_PRESENCIAL, ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HUERFANO_WEB, ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_DEPWEB, ClasificacionEnum.HIJO_ADOPTIVO);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_PRESENCIAL, ClasificacionEnum.HIJO_ADOPTIVO);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIARIO_HIJO_ADOPTIVO_WEB, ClasificacionEnum.HIJO_ADOPTIVO);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIO_EN_CUSTODIA_DEPWEB, ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIO_EN_CUSTODIA_PRESENCIAL, ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIO_EN_CUSTODIA_WEB, ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_PADRE_DEPWEB, ClasificacionEnum.PADRE);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_PADRE_PRESENCIAL, ClasificacionEnum.PADRE);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_PADRE_WEB, ClasificacionEnum.PADRE);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_MADRE_DEPWEB, ClasificacionEnum.MADRE);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_MADRE_PRESENCIAL, ClasificacionEnum.MADRE);
        txClasificacion.put(TipoTransaccionEnum.INACTIVAR_BENEFICIOS_MADRE_WEB, ClasificacionEnum.MADRE);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_BIOLOGICO_DEPWEB , ClasificacionEnum.HIJO_BIOLOGICO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_BIOLOGICO_PRESENCIAL , ClasificacionEnum.HIJO_BIOLOGICO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_BIOLOGICO_WEB , ClasificacionEnum.HIJO_BIOLOGICO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJASTRO_DEPWEB , ClasificacionEnum.HIJASTRO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJASTRO_PRESENCIAL , ClasificacionEnum.HIJASTRO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJASTRO_WEB , ClasificacionEnum.HIJASTRO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HERMANO_HUERFANO_DEPWEB , ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HERMANO_HUERFANO_PRESENCIAL , ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HERMANO_HUERFANO_WEB , ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_ADOTIVO_DEPWEB , ClasificacionEnum.HIJO_ADOPTIVO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_ADOTIVO_PRESENCIAL , ClasificacionEnum.HIJO_ADOPTIVO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_HIJO_ADOTIVO_WEB , ClasificacionEnum.HIJO_ADOPTIVO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_BENEFICIARIO_EN_CUSTODIA_DEPWEB, ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL , ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_CERTIFICADO_ESTUDIOS_BENEFICIARIO_EN_CUSTODIA_WEB , ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_DEPWEB, ClasificacionEnum.HIJO_BIOLOGICO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_PRESENCIAL , ClasificacionEnum.HIJO_BIOLOGICO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_BIOLOGICO_WEB , ClasificacionEnum.HIJO_BIOLOGICO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJASTRO_DEPWEB, ClasificacionEnum.HIJASTRO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJASTRO_PRESENCIAL , ClasificacionEnum.HIJASTRO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJASTRO_WEB , ClasificacionEnum.HIJASTRO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HERMANO_HUERFANO_DEPWEB , ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HERMANO_HUERFANO_PRESENCIAL , ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HERMANO_HUERFANO_WEB , ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_ADOPTIVO_DEPWEB , ClasificacionEnum.HIJO_ADOPTIVO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_ADOPTIVO_PRESENCIAL , ClasificacionEnum.HIJO_ADOPTIVO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_HIJO_ADOPTIVO_WEB , ClasificacionEnum.HIJO_ADOPTIVO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_BENEFICIARIO_EN_CUSTODIA_DEPWEB , ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_BENEFICIARIO_EN_CUSTODIA_PRESENCIAL , ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_ESCOLARIDAD_ESTUDIANTE_EDUCACION_BASICA_MEDIA_BENEFICIARIO_EN_CUSTODIA_WEB , ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_TIPO_UNION_CONYUGE_PRESENCIAL, ClasificacionEnum.CONYUGE);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_TIPO_UNION_CONYUGE_WEB, ClasificacionEnum.CONYUGE);
        //Novedad Activacion Multiple GLPI 89526
        txSujetoTramite.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIOS_MULTIPLES_PRESENCIAL, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIOS_MULTIPLES_WEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.ACTIVAR_BENEFICIARIOS_MULTIPLES_DEPWEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));

        // NOVEDAD Cambio de tipo y número de identificación - PersonaS
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS,
                Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE,
                        TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));

        // GLPI 96686 NOVEDAD Cambio de tipo y número de identificación - Personas Masivo
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_TIPO_NUMERO_DOCUMENTO_PERSONAS_MASIVO,
                Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE,
                        TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));

        // NOVEDAD Cambio de nombres y/o apellidos - Personas
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_NOMBRE_APELLIDOS_PERSONAS_DEPWEB,
                Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO,
                        TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_NOMBRE_APELLIDOS_PERSONAS,
                Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE,
                        TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_NOMBRE_APELLIDOS_PERSONAS_WEB,
                Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE,
                        TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));

        // NOVEDAD Cambio de género - Personas
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_GENERO_PERSONAS, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE,
                TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));

        // NOVEDAD Cambio de nivel educativo y/o ocupación/profesión - Personas
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS_DEPWEB,
                Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO,
                        TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS,
                Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE,
                        TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_NIVEL_EDUCATIVO_OCUPACION_PROFESION_PERSONAS_WEB,
                Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE,
                        TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));

        // NOVEDAD Activación/Desactivación de condición cabeza de hogar - Personas
        txSujetoTramite.put(TipoTransaccionEnum.ACTIVAR_DESACTIVAR_CONDICION_CABEZA_HOGAR_PERSONAS_DEPWEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE));
        txSujetoTramite.put(TipoTransaccionEnum.ACTIVAR_DESACTIVAR_CONDICION_CABEZA_HOGAR_PERSONAS, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO));
        txSujetoTramite.put(TipoTransaccionEnum.ACTIVAR_DESACTIVAR_CONDICION_CABEZA_HOGAR_PERSONAS_WEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO));

        // NOVEDAD Cambio de estado civil - Personas
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_ESTADO_CIVIL_PERSONAS, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_ESTADO_CIVIL_PERSONAS_WEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));

        // NOVEDAD Cambio de municipio, dirección de residencia, condición de casa propia, código postal, teléfono fijo, teléfono celular, correo electrónico, residencia en sector rural - Personas
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS_DEPWEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_PERSONAS_WEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO));

        // NOVEDAD Actualización de grado cursado - Personas
        txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZACION_GRADO_CURSADO_PERSONAS_DEPWEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZACION_GRADO_CURSADO_PERSONAS, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZACION_GRADO_CURSADO_PERSONAS_WEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));

        txClasificacion.put(TipoTransaccionEnum.CAMBIO_CLASE_TRABAJADOR_TRABAJADOR_DEPENDIENTE_DEPWEB,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_CLASE_TRABAJADOR_TRABAJADOR_DEPENDIENTE_PRESENCIAL,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_PRESENCIAL,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_TIPO_SALARIO_CONTRATO_TRABAJADOR_DEPENDIENTE_DEPWEB,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        //TODO NOVEDAD 126 txClasificacion.put(TipoTransaccionEnum.representante
        //TODO novedad 127
        //TODO novedad 128
        //TODO novedad 129
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_SUCURSA_TRABAJADOR_DEPENDIENTE_DEPWEB,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.CONYUGE_LABORA_DEPWEB,ClasificacionEnum.CONYUGE);
        txClasificacion.put(TipoTransaccionEnum.CONYUGE_LABORA_PRESENCIAL,ClasificacionEnum.CONYUGE);
        txClasificacion.put(TipoTransaccionEnum.CONYUGE_LABORA_WEB,ClasificacionEnum.CONYUGE);
        txClasificacion.put(TipoTransaccionEnum.VALOR_INGRESO_MENSUAL_CONYUGE_DEPWEB,ClasificacionEnum.CONYUGE);
        txClasificacion.put(TipoTransaccionEnum.VALOR_INGRESO_MENSUAL_CONYUGE_PRESENCIAL,ClasificacionEnum.CONYUGE);
        txClasificacion.put(TipoTransaccionEnum.VALOR_INGRESO_MENSUAL_CONYUGE_WEB,ClasificacionEnum.CONYUGE);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZACION_VACACIONES_TRABAJADOR_DEPENDIENTE,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        //TODO NOVEDAD 139 txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_25ANIOS,
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MAYOR_1_5SM_0_6_WEB,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MAYOR_1_5SM_2_WEB,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        //TODO NOVEDAD 142
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL,ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MENOR_1_5SM_0_6_WEB,ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_MENOR_1_5SM_2_WEB,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        //txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_PENSION_FAMILIAR_PRESENCIAL,ClasificacionEnum.PENSION_FAMILIAR);
        //txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_ENTIDAD_PAGADORA_APORTES_PENSIONADO_PENSION_FAMILIAR_WEB,ClasificacionEnum.PENSION_FAMILIAR);
        //TODO NOVEDAD 146 txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_GRUPO_FAMILIAR_PRESENCIAL_25ANIOS
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_0_6_WEB,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_2_WEB,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        //TODO NOVEDAD 149 txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MEN
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL,ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MENOR_1_5SM_0_6_WEB,ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MENOR_1_5SM_2_WEB,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        //txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_GRUPO_FAMILIAR_PRESENCIAL,ClasificacionEnum.PENSION_FAMILIAR);
        //txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_GRUPO_FAMILIAR_WEB,ClasificacionEnum.PENSION_FAMILIAR);
        //TODO NOVEDAD 153 txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_25
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_0_6_WEB,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MAYOR_1_5SM_2_WEB,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        //TODO NOVEDAD 156 txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONALMEN
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL,ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MENOR_1_5SM_0_6_WEB,ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_MENOR_1_5SM_2_WEB,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        //txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_GRUPO_FAMILIAR_PRESENCIAL,ClasificacionEnum.PENSION_FAMILIAR);
        //txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_VALOR_MESADA_PENSIONAL_PENSIONADO_GRUPO_FAMILIAR_WEB,ClasificacionEnum.PENSION_FAMILIAR);
        //TODO NOVEDAD 160 txClasificacion.put(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_25ANIOS}
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_0_6_WEB,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_2_WEB,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_0_PRESENCIAL,ClasificacionEnum.MENOS_1_5_SM_0_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_0_WEB,ClasificacionEnum.MENOS_1_5_SM_0_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL,ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_0_6_WEB,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSIONADO_MENOR_1_5SM_2_WEB,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        //txClasificacion.put(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSION_FAMILIAR_PRESENCIAL,ClasificacionEnum.PENSION_FAMILIAR);
        //txClasificacion.put(TipoTransaccionEnum.CAMBIO_TIPO_PENSIONADO_PENSION_FAMILIAR_WEB,ClasificacionEnum.PENSION_FAMILIAR);
        //TODO NOVEDAD 167 txClasificacion.put(TipoTransaccionEnum.ACTIVAR_PAGADOR_DE_PENSION_PENSIONADO_25anios
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_PAGADOR_DE_PENSION_PENSIONADO_MAYOR_1_5SM_0_6,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_PAGADOR_DE_PENSION_PENSIONADO_MAYOR_1_5SM_2,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_PAGADOR_DE_PENSION_PENSIONADO_MENOR_1_5SM_0_6,ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ACTIVAR_PAGADOR_DE_PENSION_PENSIONADO_MENOR_1_5SM_2,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        //txClasificacion.put(TipoTransaccionEnum.ACTIVAR_PAGADOR_DE_PENSION_PENSIONADO_PENSION_FAMILIAR,ClasificacionEnum.PENSION_FAMILIAR);
        //TODO NOVEDAD 175
        txClasificacion.put(TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_DEPWEB,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_PRESENCIAL,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_DEPENDIENTE_WEB,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_DEPWEB,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_PRESENCIAL,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.CAMBIAR_MEDIO_DE_PAGO_ADMINISTRADOR_DE_SUBSIDIO_WEB,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        //
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_TRABAJADOR_DEPENDIENTE_WEB,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_ADMINISTRADOR_SUBSIDIO,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_ADMINISTRADOR_DEL_SUBSIDIO_MONETARIO_ADMINISTRADOR_SUBSIDIO_WEB,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.BLOQUEO_TRAJETA_TRABAJADOR_DEPENDIENTE,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        //TODO NOVEDAD 188 txClasificacion.put(TipoTransaccionEnum.BLOQUE_TARJETA_ADMINISTRADOR_SUBSIDIO,ClasificacionEnum.GRUPO_FAMILIAR); 
        txClasificacion.put(TipoTransaccionEnum.RE_EXPEDICION_TARJETA_TRABAJADOR_DEPENDIENTE,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        //TODO NOVEDAD 190 txClasificacion.put(TipoTransaccionEnum.RE_EXPEDICION_TARJETA_ADMINISTRADOR_SUBSIDIO,ClasificacionEnum.GRUPO_FAMILIAR);
        txClasificacion.put(TipoTransaccionEnum.EXPEDICION_PRIMERA_VEZ_TARJETA_TRABAJADOR_DEPENDIENTE,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        //TODO NOVEDAD 192 txClasificacion.put(TipoTransaccionEnum.EXPEDICION_PRIMERA_VEZ_TARJETA_ADMINISTRADOR_SUBSIDIO,ClasificacionEnum.GRUPO_FAMILIAR);
        txClasificacion.put(TipoTransaccionEnum.TRASLADO_DE_SALDOS_TRABAJADOR_DEPENDIENTE,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        //TODO NOVEDAD 194 txClasificacion.put(TipoTransaccionEnum.TRASLADO_SALDOS_ADMINISTRADOR_SUBSIDIO,ClasificacionEnum.GRUPO_FAMILIAR);
        txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_DATOS_TARJETA_TRABAJADOR_DEPENDIENTE,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        //TODO NOVEDAD 196 txClasificacion.put(TipoTransaccionEnum.ACTUALIZAR_DATOS_TARJETA_ADMINISTRADOR_SUBSIDIO,ClasificacionEnum.GRUPO_FAMILIAR);
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_DEPWEB,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_PRESENCIAL,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.CAMBIO_DATOS_DE_CUENTA_TRABAJADOR_DEPENDIENTE_WEB,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        //TODO NOVEDAD 199 txClasificacion.put(TipoTransaccionEnum.CAMBIO_DATOS_DE_CUENTA_ADMINISTRADOR_SUBSIDIO,ClasificacionEnum.GRUPO_FAMILIAR);

        // NOVEDAD Reporte de invalidez - Personas
        txSujetoTramite.put(TipoTransaccionEnum.REPORTE_INVALIDEZ_PERSONAS_DEPWEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.REPORTE_INVALIDEZ_PERSONAS, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.REPORTE_INVALIDEZ_PERSONAS_WEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));

        // NOVEDAD Reporte de fallecimiento - Personas
        txSujetoTramite.put(TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS_DEPWEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.REPORTE_FALLECIMIENTO_PERSONAS_WEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));

        /*TODO NOVEDADES DESDE LA 237 A LA 245*/
        txClasificacion.put(TipoTransaccionEnum.RETIRO_TRABAJADOR_DEPENDIENTE,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.RETIRO_PENSIONADO_25ANIOS,ClasificacionEnum.FIDELIDAD_25_ANIOS);
        txClasificacion.put(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_0_6,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.RETIRO_PENSIONADO_MAYOR_1_5SM_2,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0,ClasificacionEnum.MENOS_1_5_SM_0_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_0_6,ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.RETIRO_PENSIONADO_MENOR_1_5SM_2,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        //txClasificacion.put(TipoTransaccionEnum.RETIRO_PENSIONADO_PENSION_FAMILIAR,ClasificacionEnum.PENSION_FAMILIAR);
        txClasificacion.put(TipoTransaccionEnum.RETIRO_CONYUGE,ClasificacionEnum.CONYUGE);
        txClasificacion.put(TipoTransaccionEnum.RETIRO_HJO_BIOLOGICO,ClasificacionEnum.HIJO_BIOLOGICO);
        txClasificacion.put(TipoTransaccionEnum.RETIRO_HIJASTRO,ClasificacionEnum.HIJASTRO);
        txClasificacion.put(TipoTransaccionEnum.RETIRO_HERMANO_HUERFANO,ClasificacionEnum.HERMANO_HUERFANO_DE_PADRES);
        txClasificacion.put(TipoTransaccionEnum.RETIRO_HIJO_ADOPTIVO,ClasificacionEnum.HIJO_ADOPTIVO);
        txClasificacion.put(TipoTransaccionEnum.RETIRO_BENEFICIARIO_CUSTODIA,ClasificacionEnum.BENEFICIARIO_EN_CUSTODIA);
        txClasificacion.put(TipoTransaccionEnum.RETIRO_PADRE,ClasificacionEnum.PADRE);
        txClasificacion.put(TipoTransaccionEnum.RETIRO_MADRE,ClasificacionEnum.MADRE);
        txClasificacion.put(TipoTransaccionEnum.SOLICITUD_RETENCION_SUBSIDIO_TRABAJADOR_DEPENDIENTE,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.DESACTIVACION_RETENCION_SUBSIDIO_TRABAJADOR_DEPENDIENTE,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.ACTIVACION_CESION_SUBSIDIO_TRABAJADOR_DEPENDIENTE,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.DESACTIVACION_CESION_SUBSIDIO_TRABAJADOR_DEPENDIENTE,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.PIGNORACION_DEL_SUBSIDIO_TRABAJADOR_DEPENDIENTE,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        //TODO NOVEDAD 272 txClasificacion.put(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_GRUPO_FAMILIAR_PRESENCIAL,ClasificacionEnum.GRUPO_FAMILIAR);
        //TODO NOVEDAD 272 txClasificacion.put(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_GRUPO_FAMILIAR_PRESENCIAL,CL
        //TODO NOVEDAD 272 txClasificacion.put(TipoTransaccionEnum.CAMBIO_DATOS_CORRESPONDENCIA_GRUPO_FAMILIAR_WEB,CLA
        //TODO NOVEDADES 275 A LA 284 txClasificacion.put(TipoTransaccionEnum.VENCIMIENTO_AUTOMATICO_CERTIFICADOS
        txClasificacion.put(TipoTransaccionEnum.VENCIMIENTO_AUTOMATICO_INCAPACIDADES,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        //TODO NOVEDAD 287
        txClasificacion.put(TipoTransaccionEnum.SUSTITUCION_PATRONAL,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.SOLICITUD_SERVICIOS_SIN_AFILIACION_TRABAJADOR_DEPENDIENTE,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.DESACTIVAR_ACTIVAR_GRUPO_FAMILIAR_INEMBARGABLE_GRUPO_FAMILIAR,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        //TODO NOVEDAD 291 txClasificacion.put(TipoTransaccionEnum.INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_EMPLEADOR
        //TODO NOVEDAD 292 txClasificacion.put(TipoTransaccionEnum.INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_TRABAJADOR
        //TODO NOVEDAD 293 A LA 299

        // NOVEDAD Activación / inactivación de autorización de envío de correo electrónico y Autorización utilización de datos personales - Personas
        txSujetoTramite.put(TipoTransaccionEnum.ACTIVAR_INACTIVAR_AUTORIZACION_ENVIO_CORREO_DATOS_PERSONALES_PERSONAS_DEPWEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.ACTIVAR_INACTIVAR_AUTORIZACION_ENVIO_CORREO_DATOS_PERSONALES_PERSONAS, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));

        // NOVEDAD: Traslado de beneficiarios entre grupos familiares del mismo afiliado principal
        txSujetoTramite.put(TipoTransaccionEnum.TRASLADO_BENEFICIARIO_GRUPO_FAMILIAR_AFILIADO_PERSONAS, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO));

        //============ Transacciones con clasficaciones específicas Personas ==========================//
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_TIPO_INDEPENDIENTE_SEGUN_VALOR_APORTES_PRESENCIAL,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_TIPO_INDEPENDIENTE_SEGUN_VALOR_APORTES_WEB,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_CLASE_DE_INDEPENDIENTE_PRESENCIAL,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_CLASE_DE_INDEPENDIENTE_WEB,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.ACTIVACION_ENTIDAD_PAGADORA_APORTES_INDEPENDIENTES_PRESENCIAL,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.ACTIVACION_ENTIDAD_PAGADORA_APORTES_INDEPENDIENTES_WEB,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.DESACTIVAR_ENTIDA_PAGADORA_APORTES_INDEPENDIENTES_PRESENCIAL,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.DESACTIVAR_ENTIDA_PAGADORA_APORTES_INDEPENDIENTES_WEB,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIAR_VALOR_DE_INGRESOS_MENSUALES_INDEPENDIENTE_PRESENCIAL, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIAR_VALOR_DE_INGRESOS_MENSUALES_INDEPENDIENTE_WEB,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.RETIRO_TRABAJADOR_INDEPENDIENTE,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        // LMA
        txSujetoTramite.put(TipoTransaccionEnum.LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_DEPWEB,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_WEB,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        // IGE IRL
        txSujetoTramite.put(TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_DEPWEB,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_WEB,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_DEPWEB,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_WEB,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        // SLN
        txClasificacion.put(TipoTransaccionEnum.SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_DEPWEB,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txSujetoTramite.put(TipoTransaccionEnum.SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_PRESENCIAL,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_WEB,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        //VAC
        txClasificacion.put(TipoTransaccionEnum.VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_DEPWEB,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_PRESENCIAL,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txSujetoTramite.put(TipoTransaccionEnum.VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_PRESENCIAL,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_PRESENCIAL,Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        // VST
        txClasificacion.put(TipoTransaccionEnum.VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_DEPWEB, ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_PRESENCIAL, ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txSujetoTramite.put(TipoTransaccionEnum.VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_PRESENCIAL, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_WEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
		// VSP
        txClasificacion.put(TipoTransaccionEnum.VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_DEPWEB, ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txClasificacion.put(TipoTransaccionEnum.VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PRESENCIAL, ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
        txSujetoTramite.put(TipoTransaccionEnum.VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_PRESENCIAL, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
        txSujetoTramite.put(TipoTransaccionEnum.VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_WEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE ));
		// VSP Pensionado
        txClasificacion.put(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_25ANIOS_PRESENCIAL,ClasificacionEnum.FIDELIDAD_25_ANIOS);
        txClasificacion.put(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_25ANIOS_WEB,ClasificacionEnum.FIDELIDAD_25_ANIOS);
        txClasificacion.put(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_0_6_WEB,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_2_WEB,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL,ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_6_WEB,ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_PRESENCIAL,ClasificacionEnum.MENOS_1_5_SM_0_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_WEB,ClasificacionEnum.MENOS_1_5_SM_0_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_2_WEB,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        //txClasificacion.put(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_PENSION_FAMILIAR_PRESENCIAL,ClasificacionEnum.PENSION_FAMILIAR);
        //txClasificacion.put(TipoTransaccionEnum.VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_PENSION_FAMILIAR_WEB,ClasificacionEnum.PENSION_FAMILIAR);
        // Asociar Entidad Pagadora
        txClasificacion.put(TipoTransaccionEnum.ASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_MAYOR_1_5SM_0_6_WEB,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_MAYOR_1_5SM_2_WEB,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL,ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_MENOR_1_5SM_0_6_WEB,ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.ASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_MENOR_1_5SM_2_WEB,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        //txClasificacion.put(TipoTransaccionEnum.ASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_PENSION_FAMILIAR_PRESENCIAL,ClasificacionEnum.PENSION_FAMILIAR);
        //txClasificacion.put(TipoTransaccionEnum.ASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_PENSION_FAMILIAR_WEB,ClasificacionEnum.PENSION_FAMILIAR);
        // Desasociar Entidad Pagadora
        txClasificacion.put(TipoTransaccionEnum.DESASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.DESASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_MAYOR_1_5SM_0_6_WEB,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.DESASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.DESASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_MAYOR_1_5SM_2_WEB,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.DESASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL,ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.DESASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_MENOR_1_5SM_0_6_WEB,ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.DESASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.DESASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_MENOR_1_5SM_2_WEB,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        //txClasificacion.put(TipoTransaccionEnum.DESASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_PENSION_FAMILIAR_PRESENCIAL,ClasificacionEnum.PENSION_FAMILIAR);
        //txClasificacion.put(TipoTransaccionEnum.DESASOCIAR_PENSIONADO_ENTIDAD_PAGADORA_PENSIONADO_PENSION_FAMILIAR_WEB,ClasificacionEnum.PENSION_FAMILIAR);
        // Cambiar Fecha Expedicion documento
		txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_PRESENCIAL,
				Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE,
						TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO,
						TipoBeneficiarioEnum.PADRES));
		txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_DEPWEB,
				Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE,
						TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO,
						TipoBeneficiarioEnum.PADRES));
		txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_WEB,
				Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE,
						TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO,
						TipoBeneficiarioEnum.PADRES));
		// Cambiar fecha nacimiento	
		txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_FECHA_NACIMIENTO_PERSONA_DEPWEB,
				Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE,
						TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO,
						TipoBeneficiarioEnum.PADRES));
		txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_FECHA_NACIMIENTO_PERSONA_PRESENCIAL,
				Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE,
						TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO,
						TipoBeneficiarioEnum.PADRES));
		txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_FECHA_NACIMIENTO_PERSONA_WEB,
				Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE,
						TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO,
						TipoBeneficiarioEnum.PADRES));
		// Actualizacion documento identidad
		txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_DEPWEB,
				Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE,
						TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO,
						TipoBeneficiarioEnum.PADRES));
		txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_PRESENCIAL,
				Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE,
						TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO,
						TipoBeneficiarioEnum.PADRES));
		txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZAR_DOCUMENTO_IDENTIDAD_PERSONA_WEB,
				Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE,
						TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO,
						TipoBeneficiarioEnum.PADRES));
		// Subsanacion Expulsion
		txSujetoTramite.put(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_INDEPENDIENTE,
				Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE));
		txClasificacion.put(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_DEPENDIENTE,ClasificacionEnum.TRABAJADOR_DEPENDIENTE);
		txClasificacion.put(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_25ANIOS,ClasificacionEnum.FIDELIDAD_25_ANIOS);
        txClasificacion.put(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MAYOR_1_5SM_0_6,ClasificacionEnum.MAS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MAYOR_1_5SM_2,ClasificacionEnum.MAS_1_5_SM_2_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MENOR_1_5SM_0,ClasificacionEnum.MENOS_1_5_SM_0_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MENOR_1_5SM_0_6,ClasificacionEnum.MENOS_1_5_SM_0_6_POR_CIENTO);
        txClasificacion.put(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_MENOR_1_5SM_2,ClasificacionEnum.MENOS_1_5_SM_2_POR_CIENTO);
        //txClasificacion.put(TipoTransaccionEnum.REGISTRO_SUBSANACION_EXPULSION_PENSIONADO_PENSION_FAMILIAR,ClasificacionEnum.PENSION_FAMILIAR);
        
        // Novedad Actualizar información de padre/madre biológico - Personas
        txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZAR_INFORMACION_PADRE_MADRE_BIOLOGICO_PERSONA_PRESENCIAL, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZAR_INFORMACION_PADRE_MADRE_BIOLOGICO_PERSONA_WEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZAR_INFORMACION_PADRE_MADRE_BIOLOGICO_PERSONA_DEPWEB, Arrays.asList(TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE, TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE, TipoAfiliadoEnum.PENSIONADO, TipoBeneficiarioEnum.CONYUGE, TipoBeneficiarioEnum.HIJO, TipoBeneficiarioEnum.PADRES));
        
        /*Postulacion FOVIS PRESENCIAL*/
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_NUEVA_RURAL_PRESENCIAL, Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_NUEVA_URBANA_PRESENCIAL, Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_USADA_RURAL_PRESENCIAL, Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_USADA_URBANA_PRESENCIAL,  Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CONSTRUCCION_SITIO_PROPIO_RURAL_PRESENCIAL,  Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CONSTRUCCION_SITIO_PROPIO_URBANO_PRESENCIAL,  Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_URBANA_PRESENCIAL,  Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_RURAL_PRESENCIAL,  Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_SALUDABLE_PRESENCIAL,  Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        
        /*Postulacion FOVIS WEB*/
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_NUEVA_RURAL_WEB,  Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_NUEVA_URBANA_WEB,  Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_USADA_RURAL_WEB,  Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_USADA_URBANA_WEB,  Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CONSTRUCCION_SITIO_PROPIO_RURAL_WEB,  Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CONSTRUCCION_SITIO_PROPIO_URBANO_WEB,  Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_URBANA_WEB,  Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_RURAL_WEB,  Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_SALUDABLE_WEB,  Arrays.asList(TipoHogarEnum.HOGAR, TipoJefeHogarEnum.JEFE_HOGAR, TipoIntegranteHogarEnum.ABUELO_HOGAR, 
        		TipoIntegranteHogarEnum.BISNIETO_HOGAR, TipoIntegranteHogarEnum.CONYUGE_HOGAR, TipoIntegranteHogarEnum.CUNIADO_HOGAR, TipoIntegranteHogarEnum.HERMANO_HOGAR, TipoIntegranteHogarEnum.HIJO_HOGAR,
        		TipoIntegranteHogarEnum.NIETO_HOGAR, TipoIntegranteHogarEnum.PADRES_HOGAR, TipoIntegranteHogarEnum.SOBRINO_HOGAR, TipoIntegranteHogarEnum.SUEGRO_HOGAR, TipoIntegranteHogarEnum.TIO_HOGAR, 
        		TipoIntegranteHogarEnum.YERNO_HOGAR));
        
        // Novedades FOVIS
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIOS_CONDICION_ESPECIAL, Arrays.asList(TipoJefeHogarEnum.JEFE_HOGAR,TipoIntegranteHogarEnum.CONYUGE_HOGAR,TipoIntegranteHogarEnum.ABUELO_HOGAR,TipoIntegranteHogarEnum.CUNIADO_HOGAR,TipoIntegranteHogarEnum.BISNIETO_HOGAR,TipoIntegranteHogarEnum.HIJO_HOGAR,TipoIntegranteHogarEnum.HERMANO_HOGAR,TipoIntegranteHogarEnum.PADRES_HOGAR,TipoIntegranteHogarEnum.NIETO_HOGAR,TipoIntegranteHogarEnum.SOBRINO_HOGAR,TipoIntegranteHogarEnum.SUEGRO_HOGAR,TipoIntegranteHogarEnum.TIO_HOGAR,TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ACTUALIZACION_DATOS_CONTACTO_JEFE_HOGAR, Arrays.asList(TipoJefeHogarEnum.JEFE_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIOS_INGRESOS, Arrays.asList(TipoIntegranteHogarEnum.CONYUGE_HOGAR,TipoIntegranteHogarEnum.ABUELO_HOGAR,TipoIntegranteHogarEnum.CUNIADO_HOGAR,TipoIntegranteHogarEnum.BISNIETO_HOGAR,TipoIntegranteHogarEnum.HIJO_HOGAR,TipoIntegranteHogarEnum.HERMANO_HOGAR,TipoIntegranteHogarEnum.PADRES_HOGAR,TipoIntegranteHogarEnum.NIETO_HOGAR,TipoIntegranteHogarEnum.SOBRINO_HOGAR,TipoIntegranteHogarEnum.SUEGRO_HOGAR,TipoIntegranteHogarEnum.TIO_HOGAR,TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_TIPO_NUMERO_DOCUMENTOS_IDENTIDAD, Arrays.asList(TipoIntegranteHogarEnum.CONYUGE_HOGAR,TipoIntegranteHogarEnum.ABUELO_HOGAR,TipoIntegranteHogarEnum.CUNIADO_HOGAR,TipoIntegranteHogarEnum.BISNIETO_HOGAR,TipoIntegranteHogarEnum.HIJO_HOGAR,TipoIntegranteHogarEnum.HERMANO_HOGAR,TipoIntegranteHogarEnum.PADRES_HOGAR,TipoIntegranteHogarEnum.NIETO_HOGAR,TipoIntegranteHogarEnum.SOBRINO_HOGAR,TipoIntegranteHogarEnum.SUEGRO_HOGAR,TipoIntegranteHogarEnum.TIO_HOGAR,TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_REEMPLAZANTE_JEFE, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_OTROS_DATOS, Arrays.asList(TipoJefeHogarEnum.JEFE_HOGAR,TipoIntegranteHogarEnum.CONYUGE_HOGAR,TipoIntegranteHogarEnum.ABUELO_HOGAR,TipoIntegranteHogarEnum.CUNIADO_HOGAR,TipoIntegranteHogarEnum.BISNIETO_HOGAR,TipoIntegranteHogarEnum.HIJO_HOGAR,TipoIntegranteHogarEnum.HERMANO_HOGAR,TipoIntegranteHogarEnum.PADRES_HOGAR,TipoIntegranteHogarEnum.NIETO_HOGAR,TipoIntegranteHogarEnum.SOBRINO_HOGAR,TipoIntegranteHogarEnum.SUEGRO_HOGAR,TipoIntegranteHogarEnum.TIO_HOGAR,TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.AGREGAR_MIEMBRO_HOGAR, Arrays.asList(TipoIntegranteHogarEnum.CONYUGE_HOGAR,TipoIntegranteHogarEnum.ABUELO_HOGAR,TipoIntegranteHogarEnum.CUNIADO_HOGAR,TipoIntegranteHogarEnum.BISNIETO_HOGAR,TipoIntegranteHogarEnum.HIJO_HOGAR,TipoIntegranteHogarEnum.HERMANO_HOGAR,TipoIntegranteHogarEnum.PADRES_HOGAR,TipoIntegranteHogarEnum.NIETO_HOGAR,TipoIntegranteHogarEnum.SOBRINO_HOGAR,TipoIntegranteHogarEnum.SUEGRO_HOGAR,TipoIntegranteHogarEnum.TIO_HOGAR,TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.RETIRAR_MIEMBRO_HOGAR, Arrays.asList(TipoIntegranteHogarEnum.CONYUGE_HOGAR,TipoIntegranteHogarEnum.ABUELO_HOGAR,TipoIntegranteHogarEnum.CUNIADO_HOGAR,TipoIntegranteHogarEnum.BISNIETO_HOGAR,TipoIntegranteHogarEnum.HIJO_HOGAR,TipoIntegranteHogarEnum.HERMANO_HOGAR,TipoIntegranteHogarEnum.PADRES_HOGAR,TipoIntegranteHogarEnum.NIETO_HOGAR,TipoIntegranteHogarEnum.SOBRINO_HOGAR,TipoIntegranteHogarEnum.SUEGRO_HOGAR,TipoIntegranteHogarEnum.TIO_HOGAR,TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_NOMBRES_APELLIDOS, Arrays.asList(TipoIntegranteHogarEnum.CONYUGE_HOGAR,TipoIntegranteHogarEnum.ABUELO_HOGAR,TipoIntegranteHogarEnum.CUNIADO_HOGAR,TipoIntegranteHogarEnum.BISNIETO_HOGAR,TipoIntegranteHogarEnum.HIJO_HOGAR,TipoIntegranteHogarEnum.HERMANO_HOGAR,TipoIntegranteHogarEnum.PADRES_HOGAR,TipoIntegranteHogarEnum.NIETO_HOGAR,TipoIntegranteHogarEnum.SOBRINO_HOGAR,TipoIntegranteHogarEnum.SUEGRO_HOGAR,TipoIntegranteHogarEnum.TIO_HOGAR,TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.DESISTIMIENTO_POSTULACION, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.HABILITACION_POSTULACION_SUSPENDIDA_CAMBIO_ANIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MOVILIZACION_AHORRO_PREVIO_HOGAR_NO_ASIGNADO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.RECHAZO_DE_POSTULACION, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.HABILITACION_POSTULACION_RECHAZADA, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIO_DATOS_BASICOS_SOLUCION_VIVIENDA, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CAMBIOS_CIERRE_FINANCIERO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.RENUNCIO_SUBISIDIO_ASIGNADO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.PRORROGA_FOVIS, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.RESTITUCION_SUBSIDIO_INCUMPLIMIENTO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.REEMBOLSO_VOLUNTARIO_SUBSIDIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.AUTORIZACION_ENAJENACION_VIVIENDA_SUBSIDIADA, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CONFORMACION_NUEVO_HOGAR, Arrays.asList(TipoJefeHogarEnum.JEFE_HOGAR,TipoIntegranteHogarEnum.CONYUGE_HOGAR,TipoIntegranteHogarEnum.ABUELO_HOGAR,TipoIntegranteHogarEnum.CUNIADO_HOGAR,TipoIntegranteHogarEnum.BISNIETO_HOGAR,TipoIntegranteHogarEnum.HIJO_HOGAR,TipoIntegranteHogarEnum.HERMANO_HOGAR,TipoIntegranteHogarEnum.PADRES_HOGAR,TipoIntegranteHogarEnum.NIETO_HOGAR,TipoIntegranteHogarEnum.SOBRINO_HOGAR,TipoIntegranteHogarEnum.SUEGRO_HOGAR,TipoIntegranteHogarEnum.TIO_HOGAR,TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MOVILIZACION_AHORRO_PREVIO_PAGO_OFERENTE, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.AJUSTE_ACTUALIZACION_VALOR_SFV, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.LEVANTAR_INHABILIDAD_SANCION, Arrays.asList(TipoJefeHogarEnum.JEFE_HOGAR,TipoIntegranteHogarEnum.CONYUGE_HOGAR,TipoIntegranteHogarEnum.ABUELO_HOGAR,TipoIntegranteHogarEnum.CUNIADO_HOGAR,TipoIntegranteHogarEnum.BISNIETO_HOGAR,TipoIntegranteHogarEnum.HIJO_HOGAR,TipoIntegranteHogarEnum.HERMANO_HOGAR,TipoIntegranteHogarEnum.PADRES_HOGAR,TipoIntegranteHogarEnum.NIETO_HOGAR,TipoIntegranteHogarEnum.SOBRINO_HOGAR,TipoIntegranteHogarEnum.SUEGRO_HOGAR,TipoIntegranteHogarEnum.TIO_HOGAR,TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.LEVANTAR_INHABILIDAD_SANCION_HOGAR, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.REGISTRAR_INHABILIDAD_SUBSIDIO, Arrays.asList(TipoJefeHogarEnum.JEFE_HOGAR,TipoIntegranteHogarEnum.CONYUGE_HOGAR,TipoIntegranteHogarEnum.ABUELO_HOGAR,TipoIntegranteHogarEnum.CUNIADO_HOGAR,TipoIntegranteHogarEnum.BISNIETO_HOGAR,TipoIntegranteHogarEnum.HIJO_HOGAR,TipoIntegranteHogarEnum.HERMANO_HOGAR,TipoIntegranteHogarEnum.PADRES_HOGAR,TipoIntegranteHogarEnum.NIETO_HOGAR,TipoIntegranteHogarEnum.SOBRINO_HOGAR,TipoIntegranteHogarEnum.SUEGRO_HOGAR,TipoIntegranteHogarEnum.TIO_HOGAR,TipoIntegranteHogarEnum.YERNO_HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.REGISTRAR_INHABILIDAD_SUBSIDIO_HOGAR, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.AJUSTE_ACTUALIZACION_VALOR_SFV_133_2018, Arrays.asList(TipoHogarEnum.HOGAR));

        // Legalizacion FOVIS
        //Modalidad 1
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_NUEVA_RURAL_PAGO_CONTRA_ESCRITURA, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_NUEVA_RURAL_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_NUEVA_RURAL_GIRO_ANTICIPADO_AVAL_BANCARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_NUEVA_RURAL_GIRO_ANTICIPADO_CONVENIO_CCF, Arrays.asList(TipoHogarEnum.HOGAR));
        // Modalidad 2
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_NUEVA_URBANA_PAGO_CONTRA_ESCRITURA, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_NUEVA_URBANA_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_NUEVA_URBANA_GIRO_ANTICIPADO_AVAL_BANCARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_NUEVA_URBANA_GIRO_ANTICIPADO_CONVENIO_CCF, Arrays.asList(TipoHogarEnum.HOGAR));
        // Modalidad 3
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_USADA_RURAL_PAGO_CONTRA_ESCRITURA, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_USADA_RURAL_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_USADA_RURAL_GIRO_ANTICIPADO_AVAL_BANCARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_USADA_RURAL_GIRO_ANTICIPADO_CONVENIO_CCF, Arrays.asList(TipoHogarEnum.HOGAR));
        // Modalidad 4
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_USADA_URBANA_PAGO_CONTRA_ESCRITURA, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_USADA_URBANA_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_USADA_URBANA_GIRO_ANTICIPADO_AVAL_BANCARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.ADQUISICION_VIVIENDA_USADA_URBANA_GIRO_ANTICIPADO_CONVENIO_CCF, Arrays.asList(TipoHogarEnum.HOGAR));
        // Modalidad 5
        txSujetoTramite.put(TipoTransaccionEnum.CONSTRUCCION_SITIO_PROPIO_RURAL_PAGO_CONTRA_ESCRITURA, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CONSTRUCCION_SITIO_PROPIO_RURAL_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CONSTRUCCION_SITIO_PROPIO_RURAL_GIRO_ANTICIPADO_AVAL_BANCARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CONSTRUCCION_SITIO_PROPIO_RURAL_GIRO_ANTICIPADO_CONVENIO_CCF, Arrays.asList(TipoHogarEnum.HOGAR));
        // Modalidad 6
        txSujetoTramite.put(TipoTransaccionEnum.CONSTRUCCION_SITIO_PROPIO_URBANO_PAGO_CONTRA_ESCRITURA, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CONSTRUCCION_SITIO_PROPIO_URBANO_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CONSTRUCCION_SITIO_PROPIO_URBANO_GIRO_ANTICIPADO_AVAL_BANCARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.CONSTRUCCION_SITIO_PROPIO_URBANO_GIRO_ANTICIPADO_CONVENIO_CCF, Arrays.asList(TipoHogarEnum.HOGAR));
        // Modalidad 7
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_URBANA_PAGO_CONTRA_ESCRITURA, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_URBANA_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_URBANA_GIRO_ANTICIPADO_AVAL_BANCARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_URBANA_GIRO_ANTICIPADO_CONVENIO_CCF, Arrays.asList(TipoHogarEnum.HOGAR));
        // Modalidad 8
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_RURAL_PAGO_CONTRA_ESCRITURA, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_RURAL_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_RURAL_GIRO_ANTICIPADO_AVAL_BANCARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_RURAL_GIRO_ANTICIPADO_CONVENIO_CCF, Arrays.asList(TipoHogarEnum.HOGAR));
        // Modalidad 9
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_SALUDABLE_PAGO_CONTRA_ESCRITURA, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_SALUDABLE_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_SALUDABLE_GIRO_ANTICIPADO_AVAL_BANCARIO, Arrays.asList(TipoHogarEnum.HOGAR));
        txSujetoTramite.put(TipoTransaccionEnum.MEJORAMIENTO_VIVIENDA_SALUDABLE_GIRO_ANTICIPADO_CONVENIO_CCF, Arrays.asList(TipoHogarEnum.HOGAR));

        txSujetoTramite.put(TipoTransaccionEnum.EXCLUIR_CONYUGE_INACTIVO_SUMATORIA_INGRESOS_PRESENCIAL, Arrays.asList(TipoBeneficiarioEnum.CONYUGE));
        txSujetoTramite.put(TipoTransaccionEnum.INCLUIR_CONYUGE_SUMATORIA_INGRESOS_PRESENCIAL, Arrays.asList(TipoBeneficiarioEnum.CONYUGE));
        txSujetoTramite.put(TipoTransaccionEnum.EXCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL, Arrays.asList(TipoBeneficiarioEnum.PADRES));
        txSujetoTramite.put(TipoTransaccionEnum.INCLUIR_PADRE_BIOLOGICO_SUMATORIA_INGRESOS_PRESENCIAL, Arrays.asList(TipoBeneficiarioEnum.PADRES));
    }

    /**
     * Referencia a la unidad de persistencia
     */
    @PersistenceContext(unitName = "listaschequeo_PU")
    private EntityManager entityManager;

    /**
     * Referencia al logger
     */
    private ILogger logger = LogManager.getLogger(TiposSolicitanteBusiness.class);

    /**
     * Método que permite consultar los tipos de solicitante parametrizados por
     * caja de compensación
     * 
     * @return Retorna la lista de tipos de solicirtante para la caja de
     *         compensación
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ElementoListaDTO> consultarClasificacionesHabilitadas(String tipoSolicitante) {

        logger.debug("Inicio servicio consultarClasificacionesHabilitadas");
        ISujetoTramite sujetoTramite = SujetoTramiteUtils.toSujetoTramite(tipoSolicitante);
        if (sujetoTramite == null) {
            logger.error("Error de procesamiento solicitante");
            throw new ParametroInvalidoExcepcion(MensajesGeneralConstants.ERROR_RECURSO_INCORRECTO);
        }
        String cajaCompensacion = (String) CacheManager.getConstante(ConstantesSistemaConstants.CAJA_COMPENSACION_ID);
        Integer idCajaCompensacion = Integer.valueOf(cajaCompensacion);

        List<ClasificacionEnum> tiposSolicitanteRaw = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_TIPO_SOLICITANTE_POR_CAJA)
                .setParameter("idCajaCompensacion", idCajaCompensacion)
                .setParameter("estadoInhabilitado", EstadoRequisitoTipoSolicitanteEnum.INHABILITADO).getResultList();



        ElementoListaDTO elementoListaDTO;
        List<ElementoListaDTO> tiposSolicitante = new ArrayList<>();
        for (ClasificacionEnum clasificacion : tiposSolicitanteRaw) {
            if (clasificacion != null && clasificacion.getSujetoTramite() == sujetoTramite) {
                if(clasificacion.equals("PENSION_FAMILIAR")||"PENSION_FAMILIAR".equals(clasificacion.name())) continue;
                elementoListaDTO = new ElementoListaDTO();
                elementoListaDTO.setIdentificador(clasificacion.name());
                elementoListaDTO.setValor(clasificacion.getDescripcion());
                tiposSolicitante.add(elementoListaDTO);
            }
            logger.info("Clasificacion: " + clasificacion);
        }
        if (tiposSolicitante.isEmpty()) {
            return null;
        }        
        logger.debug("Fin servicio consultarClasificacionesHabilitadas");
        return tiposSolicitante;
    }

    /**
     * @see com.asopagos.listaschequeo.service.TiposSolicitanteService#consultarTiposSolicitante(com.asopagos.enumeraciones.core.TipoTransaccionEnum)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ElementoListaDTO> consultarTiposSolicitante(TipoTransaccionEnum tipoTx) {
        List<ElementoListaDTO> tiposSolicitante = new ArrayList<>();
        ElementoListaDTO elemento = null;
        List<ISujetoTramite> configurables = null;

        // Si la transacción aplica para un beneficiario específico se retorna dicho beneficiario
        if (tipoTx.isAplicaClasificacion()) {
            ClasificacionEnum clasif = txClasificacion.get(tipoTx);
            elemento = new ElementoListaDTO();
            elemento.setIdentificador(clasif.name());
            elemento.setValor(clasif.getDescripcion());
            tiposSolicitante.add(elemento);
            return tiposSolicitante;
        }
        //Se obtienen los sujetos de trámite que se pueden configurar para la transacción dada
        configurables = txSujetoTramite.get(tipoTx);

        for (TipoAfiliadoEnum tipoAfiliado : TipoAfiliadoEnum.values()) {
            if (configurables.contains(tipoAfiliado)) {
                elemento = new ElementoListaDTO();
                elemento.setIdentificador(tipoAfiliado.name());
                elemento.setValor(tipoAfiliado.getDescripcion());
                tiposSolicitante.add(elemento);
            }
        }
        for (TipoBeneficiarioEnum tipoBeneficiario : TipoBeneficiarioEnum.values()) {
            if (configurables.contains(tipoBeneficiario)) {
                elemento = new ElementoListaDTO();
                elemento.setIdentificador(tipoBeneficiario.name());
                elemento.setValor(tipoBeneficiario.getDescripcion());
                tiposSolicitante.add(elemento);
            }
        }
        for (TipoEmpleadorEnum tipoEmpleador : TipoEmpleadorEnum.values()) {
            if (configurables.contains(tipoEmpleador)) {
                elemento = new ElementoListaDTO();
                elemento.setIdentificador(tipoEmpleador.name());
                elemento.setValor(tipoEmpleador.getDescripcion());
                tiposSolicitante.add(elemento);
            }
        }
        for (TipoHogarEnum tipoHogar : TipoHogarEnum.values()) {
            if (configurables.contains(tipoHogar)) {
                elemento = new ElementoListaDTO();
                elemento.setIdentificador(tipoHogar.name());
                elemento.setValor(tipoHogar.getDescripcion());
                tiposSolicitante.add(elemento);
            }
        }
        for (TipoIntegranteHogarEnum tipoIntegranteHogar : TipoIntegranteHogarEnum.values()) {
            if (configurables.contains(tipoIntegranteHogar)) {
                elemento = new ElementoListaDTO();
                elemento.setIdentificador(tipoIntegranteHogar.name());
                elemento.setValor(tipoIntegranteHogar.getDescripcion());
                tiposSolicitante.add(elemento);
            }
        }
        for (TipoJefeHogarEnum tipoJefeHogar : TipoJefeHogarEnum.values()) {
            if (configurables.contains(tipoJefeHogar)) {
                elemento = new ElementoListaDTO();
                elemento.setIdentificador(tipoJefeHogar.name());
                elemento.setValor(tipoJefeHogar.getDescripcion());
                tiposSolicitante.add(elemento);
            }
        }
        return tiposSolicitante;
    }

    /**
     * @see com.asopagos.listaschequeo.service.TiposSolicitanteService#consultarTransaccionesProceso(com.asopagos.enumeraciones.core.ProcesoEnum)
     */
    @Override
    public List<TransaccionRequisitoDTO> consultarTransaccionesProceso(ProcesoEnum proceso) {
        List<TransaccionRequisitoDTO> transaccionesProceso = new ArrayList<>(TipoTransaccionEnum.values().length);
        TransaccionRequisitoDTO elementoLista = null;
        for (TipoTransaccionEnum eachTx : TipoTransaccionEnum.values()) {
        	if(eachTx.getProceso() != null){
        		if (eachTx.getProceso().equals(proceso)) {
        			elementoLista = new TransaccionRequisitoDTO();
        			elementoLista.setIdentificador(eachTx.name());
        			elementoLista.setValor(eachTx.getDescripcion());
        			elementoLista.setAplicableClasificacion(eachTx.isAplicaClasificacion());
        			if (eachTx.isAplicaClasificacion()) {
        				elementoLista.setClasificacionObjetivo(txClasificacion.get(eachTx));
        			}
        			transaccionesProceso.add(elementoLista);
        		}        		
        	}
        }
        return transaccionesProceso;
    }
    
    public static List<ISujetoTramite> getSujetoTramite(TipoTransaccionEnum tipoTransaccionEnum){
        return txSujetoTramite.get(tipoTransaccionEnum);
    }
    
    public static ClasificacionEnum getTxClasificacion(TipoTransaccionEnum tipoTransaccionEnum){
        return txClasificacion.get(tipoTransaccionEnum);
    }
}
