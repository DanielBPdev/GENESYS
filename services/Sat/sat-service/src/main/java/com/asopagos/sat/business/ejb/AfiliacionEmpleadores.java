/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.sat.business.ejb;


import co.gov.sed.sace.util.responseUtil;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.sat.business.interfaces.IAfiliacionEmpleadores;
import com.asopagos.sat.dto.AfiliacionEmpleadoresDTO;
import com.asopagos.sat.dto.BusquedaSAT;
import com.asopagos.sat.dto.RespuestaEstandar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.asopagos.afiliaciones.empleadores.clients.ConsultarSolicitudAfiliacionEmpleador;
import com.asopagos.afiliaciones.empleadores.composite.clients.IniciarProcesoAfliliacionEmpleadoresPresencial;
import com.asopagos.afiliaciones.empleadores.composite.clients.RadicarSolicitudAfiliacionComunicado;
import com.asopagos.afiliaciones.empleadores.composite.clients.AsignarSolicitudAfiliacionEmpleador;
import com.asopagos.afiliaciones.empleadores.composite.dto.AsignarSolicitudAfiliacionDTO;
import com.asopagos.tareashumanas.clients.ObtenerTareaActiva;
import com.asopagos.afiliaciones.empleadores.composite.dto.ProcesoAfiliacionEmpleadoresPresencialDTO;
import com.asopagos.afiliaciones.empleadores.composite.dto.RadicarSolicitudAfiliacionDTO;
import com.asopagos.dto.ElementoListaDTO;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.dto.afiliaciones.EmpleadorIntentoAfiliacionDTO;
import com.asopagos.empleadores.clients.GuardarDatosTemporalesEmpleador;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.afiliaciones.EstadoDocumentacionEnum;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionEmpleadorEnum;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.afiliaciones.ResultadoRadicacionSolicitudEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.EstadoRequisitoTipoSolicitanteEnum;
import com.asopagos.enumeraciones.core.TipoRequisitoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.sat.dto.AfiliacionEmpleadoresprimeraVezSatDTO;
import java.util.Map;
import com.asopagos.sat.dto.EnvioSatDTO;
import com.asopagos.sat.dto.NotificacionSatDTO;
import com.asopagos.sat.dto.RespuestaNotificacionSatDTO;
import com.asopagos.sat.util.HttpUtil;
import org.jose4j.json.JsonUtil;
import java.util.HashMap;
import com.asopagos.usuarios.clients.GenerarTokenAccesoCore;
import com.asopagos.usuarios.dto.TokenDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Calendar;
import java.util.Date;
import javax.transaction.Transactional;
import com.asopagos.rest.security.dto.UserDTO;
import javax.ws.rs.core.HttpHeaders;
import com.asopagos.listas.clients.ConsultarListasValores;
import java.util.Arrays;
import org.apache.commons.collections.CollectionUtils;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;
import com.asopagos.sat.constantes.NamedQueriesConstants;
import javax.persistence.NoResultException;
import com.asopagos.tareashumanas.clients.ReasignarTarea;

/**
 *
 * @author Maria Cuellar
 */
@Stateless
public class AfiliacionEmpleadores implements IAfiliacionEmpleadores,Serializable{

    private static final ILogger logger = LogManager.getLogger(AfiliacionEmpleadores.class);
    
    @PersistenceContext(unitName = "core_PU")
    private EntityManager entityManager;
    
    
    @Override    
    public List<AfiliacionEmpleadoresDTO> consultarAfiliacionEmpleadores(BusquedaSAT busquedaSAT){
        logger.info("Comienza llamado servicios consultarAfiliacionEmpleadores");
        List<AfiliacionEmpleadoresDTO> respuesta = new ArrayList<AfiliacionEmpleadoresDTO>();
        try{
            String sq = "select distinct b.*, aud.RESPONSE mensaje from (\n" +
            "	select id\n" +
            "		  ,radicado\n" +
            "		  ,numeroTransaccion\n" +
            "		  ,tipoPersona\n" +
            "		  ,naturalezaJuridica\n" +
            "		  ,tipoDocumentoEmpleador\n" +
            "		  ,numeroDocumentoEmpleador\n" +
            "		  ,serialSAT\n" +
            "		  ,primerNombreEmpleador\n" +
            "		  ,segundoNombreEmpleador\n" +
            "		  ,primerApellidoEmpleador\n" +
            "		  ,segundoApellidoEmpleador\n" +
            "		  ,fechaSolicitud\n" +
            "		  ,'' perdidaAfiliacionCausaGrave\n" +
            "		  ,fechaEfectividad\n" +
            "		  ,razonSocialEmpleador\n" +
            "		  ,numeroMatriculaMercantil\n" +
            "		  ,departamentoCausaSalarios\n" +
            "		  ,municipioContacto\n" +
            "		  ,direccionContacto\n" +
            "		  ,telefonoMunicipioCausaSalarios\n" +
            "		  ,correoElectronicoContacto\n" +
            "		  ,tipoDocumentoRepresentanteLegal\n" +
            "		  ,numeroDocumentoRepresentanteLegal\n" +
            "		  ,primerNombreRepresentanteLegal\n" +
            "		  ,segundoNombreRepresentanteLegal\n" +
            "		  ,primerApellidoRepresentanteLegal\n" +
            "		  ,segundoApellidoRepresentanteLegal\n" +
            "		  ,autorizacionDatosPersonales\n" +
            "		  ,autorizacionEnvioNotificaciones\n" +
            "		  ,manifestacionNoAfiliadoCaja, \n" +
            "	case when (estado is null and audi > 0) then 'Error'\n" +
            "	when (estado is null and audi = 0) then 'Pendiente Enviar'\n" +
            "	else estado\n" +
            "	end estado\n" +
            "	from(\n" +
            "		SELECT afil.*\n" +
            "		  ,(select count(1) from sat.aud_AFILIACION_EMPLEADORES aud where aud.id_afiliacion_empleadores = afil.id) audi\n" +
            "		  ,case \n" +
            "			when (proceso_enviado_sat = -1) then 'Retirado' \n" +
            "			when (proceso_enviado_sat > 0) then 'Satisfactorio' \n" +
            "		  END estado\n" +
            "		FROM SAT.AFILIACION_EMPLEADORES afil WITH(NOLOCK)\n" +
            "	  )a\n" +
            "  )b\n" +
            "  left join sat.aud_AFILIACION_EMPLEADORES aud WITH(NOLOCK) on aud.id_afiliacion_empleadores = b.id";
            String where = " WHERE 1=1";
            if(busquedaSAT.getEstado() != null){
                where = where + "and estado = '"+busquedaSAT.getEstado()+"'";
            }
            if(busquedaSAT.getNumeroDocumentoEmpleador() != null){
                where = where + " and numeroDocumentoEmpleador = '"+busquedaSAT.getNumeroDocumentoEmpleador()+"'";
            }
            if(busquedaSAT.getFechaInicio() != null && busquedaSAT.getFechaFin() != null){
                where = where + " and aud.fecha between '"+busquedaSAT.getFechaInicio()+"' and '"+busquedaSAT.getFechaFin()+"'";
            }
            Query query = entityManager.createNativeQuery(sq + where);
            List<Object[]> listado = query.getResultList();            
            for(Object[] obj:listado){
                try{
                    AfiliacionEmpleadoresDTO afiliacionEmpleadores = new AfiliacionEmpleadoresDTO();
                    afiliacionEmpleadores.setId(obj[0] != null ? new Long(obj[0].toString()) : 0L);
                    afiliacionEmpleadores.setRadicado(obj[1] != null ? obj[1].toString() : "");
                    afiliacionEmpleadores.setNumeroTransaccion(obj[2] != null ? obj[2].toString() : "");
                    afiliacionEmpleadores.setTipoPersona(obj[3] != null ? obj[3].toString() : "");
                    afiliacionEmpleadores.setNaturalezaJuridica(obj[4] != null ? new Long(obj[4].toString()) : null);
                    afiliacionEmpleadores.setTipoDocumentoEmpleador(obj[5] != null ? obj[5].toString() : "");
                    afiliacionEmpleadores.setNumeroDocumentoEmpleador(obj[6] != null ? obj[6].toString() : "");
                    afiliacionEmpleadores.setSerialSAT(obj[7] != null ? obj[7].toString() : "");
                    afiliacionEmpleadores.setPrimerNombreEmpleador(obj[8] != null ? obj[8].toString() : "");
                    afiliacionEmpleadores.setSegundoNombreEmpleador(obj[9] != null ? obj[9].toString() : "");
                    afiliacionEmpleadores.setPrimerApellidoEmpleador(obj[10] != null ? obj[10].toString() : "");
                    afiliacionEmpleadores.setSegundoApellidoEmpleador(obj[11] != null ? obj[11].toString() : "");
//                    afiliacionEmpleadores.setFechaSolicitud(obj[12] != null ? new Date(obj[12].toString()) : null);
                    afiliacionEmpleadores.setFechaSolicitud(obj[12] != null ? obj[12].toString() : null);
                    afiliacionEmpleadores.setPerdidaAfiliacionCausaGrave(obj[13] != null ? obj[13].toString() : "");
//                    afiliacionEmpleadores.setFechaEfectividad(obj[14] != null ? new Date(obj[14].toString()) : null);
                    afiliacionEmpleadores.setFechaEfectividad(obj[14] != null ? obj[14].toString() : null);
                    afiliacionEmpleadores.setRazonSocialEmpleador(obj[15] != null ? obj[15].toString() : "");
                    afiliacionEmpleadores.setNumeroMatriculaMercantil(obj[16] != null ? obj[16].toString() : "");
                    afiliacionEmpleadores.setDepartamentoCausaSalarios(obj[17] != null ? obj[17].toString() : "");
                    afiliacionEmpleadores.setMunicipioContacto(obj[18] != null ? obj[18].toString() : "");
                    afiliacionEmpleadores.setDireccionContacto(obj[19] != null ? obj[19].toString() : "");
                    afiliacionEmpleadores.setTelefonoMunicipioCausaSalarios(obj[20] != null ? new Long(obj[20].toString()) : null);
                    afiliacionEmpleadores.setCorreoElectronicoContacto(obj[21] != null ? obj[21].toString() : "");
                    afiliacionEmpleadores.setTipoDocumentoRepresentanteLegal(obj[22] != null ? obj[22].toString() : "");
                    afiliacionEmpleadores.setNumeroDocumentoRepresentanteLegal(obj[23] != null ? obj[23].toString() : "");
                    afiliacionEmpleadores.setPrimerNombreRepresentanteLegal(obj[24] != null ? obj[24].toString() : "");
                    afiliacionEmpleadores.setSegundoNombreRepresentanteLegal(obj[25] != null ? obj[25].toString() : "");
                    afiliacionEmpleadores.setPrimerApellidoRepresentanteLegal(obj[26] != null ? obj[26].toString() : "");
                    afiliacionEmpleadores.setSegundoApellidoRepresentanteLegal(obj[27] != null ? obj[27].toString() : "");
                    afiliacionEmpleadores.setAutorizacionDatosPersonales(obj[28] != null ? obj[28].toString() : "");
                    afiliacionEmpleadores.setAutorizacionEnvioNotificaciones(obj[29] != null ? obj[29].toString() : "");
                    afiliacionEmpleadores.setManifestacionNoAfiliadoCaja(obj[30] != null ? obj[30].toString() : "");
                    afiliacionEmpleadores.setEstado(obj[31] != null ? obj[31].toString() : "");
                    afiliacionEmpleadores.setMensajeAuditoria(obj[32] != null ? responseUtil.obtenerMensaje(obj[32].toString()) : "");
                    afiliacionEmpleadores.setGlosa(obj[32] != null ? responseUtil.obtenerCodigoGlosa(obj[32].toString()) : "");

                    respuesta.add(afiliacionEmpleadores);
                }catch(Exception e){
                    e.printStackTrace();
                    System.out.println("Ocurrio un error por ++++++++++++++++++++++++++++++++++++++++++++++++++++");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e);
        }
        logger.info("Termina llamado servicios reportarAfiliacionPrimeraVez");
        return respuesta;
    }
    
    @Override    
    public List<AfiliacionEmpleadoresDTO> consultarAfiliacionEmpleadoresAuditoria(Long id){
        logger.info("Comienza llamado servicios consultarAfiliacionEmpleadoresAuditoria");
        List<AfiliacionEmpleadoresDTO> respuesta = new ArrayList<AfiliacionEmpleadoresDTO>();
        try{
            String sq = "select \n" +
                "afil.id,\n" +
                "radicado\n" +
                ",numeroTransaccion\n" +
                ",tipoPersona\n" +
                ",naturalezaJuridica\n" +
                ",tipoDocumentoEmpleador\n" +
                ",numeroDocumentoEmpleador\n" +
                ",serialSAT\n" +
                ",primerNombreEmpleador\n" +
                ",segundoNombreEmpleador\n" +
                ",primerApellidoEmpleador\n" +
                ",segundoApellidoEmpleador\n" +
                ",fechaSolicitud\n" +
                ",'' perdidaAfiliacionCausaGrave\n" +
                ",fechaEfectividad\n" +
                ",razonSocialEmpleador\n" +
                ",numeroMatriculaMercantil\n" +
                ",departamentoCausaSalarios\n" +
                ",municipioContacto\n" +
                ",direccionContacto\n" +
                ",telefonoMunicipioCausaSalarios\n" +
                ",correoElectronicoContacto\n" +
                ",tipoDocumentoRepresentanteLegal\n" +
                ",numeroDocumentoRepresentanteLegal\n" +
                ",primerNombreRepresentanteLegal\n" +
                ",segundoNombreRepresentanteLegal\n" +
                ",primerApellidoRepresentanteLegal\n" +
                ",segundoApellidoRepresentanteLegal\n" +
                ",autorizacionDatosPersonales\n" +
                ",autorizacionEnvioNotificaciones\n" +
                ",manifestacionNoAfiliadoCaja,\n" +
                "case when aud_afil.error='SI' then 'Error' when aud_afil.error='NO' then 'Sin error' end estadoAuditoria,\n" +
                "RESPONSE mensaje,\n" +
                "aud_afil.fecha,\n" +
                "aud_afil.id id_auditoria, afil.observaciones\n"+
                "from sat.aud_AFILIACION_EMPLEADORES aud_afil\n" +
                "join sat.AFILIACION_EMPLEADORES afil on aud_afil.id_afiliacion_empleadores = afil.id";
            
            String where = " WHERE afil.id="+id;
            
            Query query = entityManager.createNativeQuery(sq + where);
            List<Object[]> listado = query.getResultList();            
            for(Object[] obj:listado){
                try{
                    AfiliacionEmpleadoresDTO afiliacionEmpleadores = new AfiliacionEmpleadoresDTO();
                    afiliacionEmpleadores.setId(obj[0] != null ? new Long(obj[0].toString()) : 0L);
                    afiliacionEmpleadores.setRadicado(obj[1] != null ? obj[1].toString() : "");
                    afiliacionEmpleadores.setNumeroTransaccion(obj[2] != null ? obj[2].toString() : "");
                    afiliacionEmpleadores.setTipoPersona(obj[3] != null ? obj[3].toString() : "");
                    afiliacionEmpleadores.setNaturalezaJuridica(obj[4] != null ? new Long(obj[4].toString()) : null);
                    afiliacionEmpleadores.setTipoDocumentoEmpleador(obj[5] != null ? obj[5].toString() : "");
                    afiliacionEmpleadores.setNumeroDocumentoEmpleador(obj[6] != null ? obj[6].toString() : "");
                    afiliacionEmpleadores.setSerialSAT(obj[7] != null ? obj[7].toString() : "");
                    afiliacionEmpleadores.setPrimerNombreEmpleador(obj[8] != null ? obj[8].toString() : "");
                    afiliacionEmpleadores.setSegundoNombreEmpleador(obj[9] != null ? obj[9].toString() : "");
                    afiliacionEmpleadores.setPrimerApellidoEmpleador(obj[10] != null ? obj[10].toString() : "");
                    afiliacionEmpleadores.setSegundoApellidoEmpleador(obj[11] != null ? obj[11].toString() : "");
//                    afiliacionEmpleadores.setFechaSolicitud(obj[12] != null ? new Date(obj[12].toString()) : null);
                    afiliacionEmpleadores.setFechaSolicitud(obj[12] != null ? obj[12].toString() : null);
                    afiliacionEmpleadores.setPerdidaAfiliacionCausaGrave(obj[13] != null ? obj[13].toString() : "");
//                    afiliacionEmpleadores.setFechaEfectividad(obj[14] != null ? new Date(obj[14].toString()) : null);
                    afiliacionEmpleadores.setFechaEfectividad(obj[14] != null ? obj[14].toString() : null);
                    afiliacionEmpleadores.setRazonSocialEmpleador(obj[15] != null ? obj[15].toString() : "");
                    afiliacionEmpleadores.setNumeroMatriculaMercantil(obj[16] != null ? obj[16].toString() : "");
                    afiliacionEmpleadores.setDepartamentoCausaSalarios(obj[17] != null ? obj[17].toString() : "");
                    afiliacionEmpleadores.setMunicipioContacto(obj[18] != null ? obj[18].toString() : "");
                    afiliacionEmpleadores.setDireccionContacto(obj[19] != null ? obj[19].toString() : "");
                    afiliacionEmpleadores.setTelefonoMunicipioCausaSalarios(obj[20] != null ? new Long(obj[20].toString()) : null);
                    afiliacionEmpleadores.setCorreoElectronicoContacto(obj[21] != null ? obj[21].toString() : "");
                    afiliacionEmpleadores.setTipoDocumentoRepresentanteLegal(obj[22] != null ? obj[22].toString() : "");
                    afiliacionEmpleadores.setNumeroDocumentoRepresentanteLegal(obj[23] != null ? obj[23].toString() : "");
                    afiliacionEmpleadores.setPrimerNombreRepresentanteLegal(obj[24] != null ? obj[24].toString() : "");
                    afiliacionEmpleadores.setSegundoNombreRepresentanteLegal(obj[25] != null ? obj[25].toString() : "");
                    afiliacionEmpleadores.setPrimerApellidoRepresentanteLegal(obj[26] != null ? obj[26].toString() : "");
                    afiliacionEmpleadores.setSegundoApellidoRepresentanteLegal(obj[27] != null ? obj[27].toString() : "");
                    afiliacionEmpleadores.setAutorizacionDatosPersonales(obj[28] != null ? obj[28].toString() : "");
                    afiliacionEmpleadores.setAutorizacionEnvioNotificaciones(obj[29] != null ? obj[29].toString() : "");
                    afiliacionEmpleadores.setManifestacionNoAfiliadoCaja(obj[30] != null ? obj[30].toString() : "");
                    afiliacionEmpleadores.setEstadoAuditoria(obj[31] != null ? obj[31].toString() : "");                    
                    afiliacionEmpleadores.setMensajeAuditoria(obj[32] != null ? responseUtil.obtenerMensaje(obj[32].toString()) : "");
                    afiliacionEmpleadores.setFechaAuditoria(obj[33] != null ? obj[33].toString() : "");     
                    afiliacionEmpleadores.setIdAuditoria(obj[34] != null ? new Long(obj[34].toString()) : null);  
                    afiliacionEmpleadores.setObservacionesAuditoria(obj[35] != null ? obj[35].toString() : "");
                    afiliacionEmpleadores.setGlosa(obj[32] != null ? responseUtil.obtenerCodigoGlosa(obj[32].toString()) : "");
                    respuesta.add(afiliacionEmpleadores);
                }catch(Exception e){
                    e.printStackTrace();
                    System.out.println("Ocurrio un error por ++++++++++++++++++++++++++++++++++++++++++++++++++++");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e);
        }
        logger.info("Termina llamado servicios reportarAfiliacionPrimeraVez");
        return respuesta;
    }
    
    @Override
    public RespuestaEstandar cambiarEstadoAuditoriaAfiliacionEmpleadores(
            Long id, Long idAuditoria, String estado, String observaciones){
        
        RespuestaEstandar respuesta = new RespuestaEstandar();
        
        String update = "UPDATE sat.AFILIACION_EMPLEADORES SET "
                + "proceso_enviado_sat=-1, observaciones='"+observaciones+"' WHERE ID= " + id;
                
        Query query = entityManager.createNativeQuery(update);
        int resul = query.executeUpdate();
        
        if(resul > 0){
            respuesta.setEstado("OK");
            respuesta.adicionarMensaje("Información actualizada correctamente");
        }else{
            respuesta.setEstado("KO");
            respuesta.adicionarMensaje("Se presento un error actualizando la "
                    + "información, por favor vuelva intentar.");
        }
        
        return respuesta;
    }



    public List<NotificacionSatDTO> consultarSolicitudAfiliacionaSATPrimeravez() {
        String sql = "SELECT TRAN_NUME_TRAN,TRAN_CODI_NOVE,TRAN_FECH_CREA, TRAN_FECH_FIN_VIGE,TRAN_ESTA_FLUJ,TRAN_URL  FROM SAT.WS_TRANSACCION WHERE ESTADO_AFILIACION = 'SIN_AFILIAR' ";
             List<NotificacionSatDTO> notificaciones = new ArrayList<>();
        try{
            List<Object[]> resultados = entityManager
            .createNativeQuery(sql)
            .getResultList();
            for (Object[] fila : resultados) {
                NotificacionSatDTO dto = new NotificacionSatDTO(
                    (String) fila[0],  // TRAN_NUME_TRAN
                    (String) fila[1],  // TRAN_CODI_NOVE
                    fila[2].toString(),// TRAN_FECH_CREA (puede ser Date → toString)
                    fila[3].toString(),// TRAN_FECH_VIGE
                    (String) fila[4],  // TRAN_ESTA_FLUJ
                    (String) fila[5]   // TRAN_URL
                );

            notificaciones.add(dto);
            }
        
        }catch(Exception e){
            e.printStackTrace();
        }
        return notificaciones;
    }
    public void actualizarEstadoAfiliacion(NotificacionSatDTO notificacion) {
        try {
            String updateString = "UPDATE SAT.WS_TRANSACCION SET ESTADO_AFILIACION = 'AFILIADO' WHERE TRAN_NUME_TRAN = ?";
            Query query = entityManager.createNativeQuery(updateString);
            query.setParameter(1, notificacion.getNumero_transaccion());

            int result = query.executeUpdate();
            if (result == 0) {
                System.out.println("No se encontró ninguna transacción con TRAN_NUME_TRAN = " + notificacion.getNumero_transaccion());
            } else {
                System.out.println("ESTADO_AFILIACION actualizado correctamente para TRAN_NUME_TRAN = " + notificacion.getNumero_transaccion());
            }

        } catch (Exception ex) {
            System.out.println("Error al actualizar ESTADO_AFILIACION: " + ex.getMessage());
        }
    }
    
    @Override
    public RespuestaNotificacionSatDTO recibirNotificacionSat(NotificacionSatDTO notificacion){        
    boolean exitoso = true;
    RespuestaNotificacionSatDTO respuesta = new RespuestaNotificacionSatDTO();
        
        try {
            String insertString = "INSERT INTO SAT.WS_TRANSACCION (TRAN_NUME_TRAN, TRAN_CODI_NOVE, TRAN_FECH_CREA, TRAN_FECH_FIN_VIGE, TRAN_ESTA_FLUJ, TRAN_URL, TRAN_USUA,ESTADO_AFILIACION ) VALUES (?, ?, ?, ?, ?, ?, 'WebServiceSAT','SIN_AFILIAR')";
            Query query = entityManager.createNativeQuery(insertString);
            query.setParameter(1, notificacion.getNumero_transaccion());
            query.setParameter(2, notificacion.getCodigo_novedad());
            query.setParameter(3, notificacion.getFecha_creacion());
            query.setParameter(4, notificacion.getFecha_vigencia());
            query.setParameter(5, notificacion.getEstado_Fujo());
            query.setParameter(6, notificacion.getUrl());
            int result = query.executeUpdate();
            if (result != 1) {
                exitoso = false;
            }
        } catch (Exception ex) {
            exitoso = false;
            //Imprimir error
            System.out.println("Error al ejecutar INSERT en la tabla WS_TRANSACCION: " + ex.getMessage());
        }
    if (exitoso) {
         respuesta.setCodigoNotificacion(notificacion.getNumero_transaccion());
    } else {
        respuesta.setCodigoNotificacion("Error!");
    }
          
    return respuesta;
}

@Override
public AfiliacionEmpleadoresprimeraVezSatDTO consultarAfiliacionesSat(String numeroTransaccion, String tokenGenerado) {
    AfiliacionEmpleadoresprimeraVezSatDTO objetoAfiliacion = new AfiliacionEmpleadoresprimeraVezSatDTO();

    if (numeroTransaccion == null || numeroTransaccion.trim().isEmpty()) {
        logger.warn("El número de transacción es nulo o vacío.");
        return objetoAfiliacion;
    }

    if (tokenGenerado == null || tokenGenerado.trim().isEmpty()) {
        logger.warn("El token generado es nulo o vacío.");
    }

    String url = "";
    String status = "ERROR"; // Asumimos error por defecto
    String requestJson = "";
    String wsResponse = "";

    // Consulta de URL SAT
    try {
        Object urlSAT = entityManager
            .createNativeQuery("SELECT TRAN_URL FROM SAT.WS_TRANSACCION WHERE TRAN_NUME_TRAN = :numeroTransaccion")
            .setParameter("numeroTransaccion", numeroTransaccion)
            .getSingleResult();

        if (urlSAT != null) {
            url = urlSAT.toString().trim();
            if (url.endsWith("/")) {
                url = url.substring(0, url.length() - 1);
            }
        } else {
            logger.warn("No se encontró URL para la transacción: {}"+ numeroTransaccion);
        }
    } catch (NoResultException nre) {
        logger.error("No se encontró resultado para la transacción: {}"+ numeroTransaccion, nre);
    } catch (Exception e) {
        logger.error("Error al consultar la URL del SAT: ", e);
    }

    // Preparar JSON y enviar petición HTTP
    try {
        Map<String, String> objeto = new HashMap<>();
        objeto.put("numerotransaccion", numeroTransaccion);
        requestJson = JsonUtil.toJson(objeto);

        logger.info("Enviando petición HTTP a URL: {}"+ url);
        wsResponse = HttpUtil.sendHttpPostJSON(url, requestJson, tokenGenerado);
    } catch (Exception e) {
        logger.error("Error al realizar la petición HTTP: ", e);
    }

    // Procesar respuesta
    if (wsResponse != null && !wsResponse.trim().isEmpty()) {
        try {
            Map<String, Object> jsonObject = JsonUtil.parseJson(wsResponse);
            logger.info("Respuesta JSON recibida: {}"+ jsonObject);

            objetoAfiliacion.setId(0L); // No viene en el JSON, se asigna 0
            objetoAfiliacion.setRadicado(jsonObject.get("RadicadoSolicitud") != null ? jsonObject.get("RadicadoSolicitud").toString() : "");
            objetoAfiliacion.setNumeroTransaccion(jsonObject.get("NumeroTransaccion") != null ? jsonObject.get("NumeroTransaccion").toString() : "");
            objetoAfiliacion.setTipoPersona(jsonObject.get("TipoPersona") != null ? jsonObject.get("TipoPersona").toString() : "");
            objetoAfiliacion.setNaturalezaJuridica(
                jsonObject.get("NaturalezaJuridicaEmpleador") != null && !jsonObject.get("NaturalezaJuridicaEmpleador").toString().trim().isEmpty()
                    ? Long.parseLong(jsonObject.get("NaturalezaJuridicaEmpleador").toString())
                    : 0L
            );
            objetoAfiliacion.setTipoDocumentoEmpleador(jsonObject.get("TipoDocumentoEmpleador") != null ? jsonObject.get("TipoDocumentoEmpleador").toString() : "");
            objetoAfiliacion.setNumeroDocumentoEmpleador(jsonObject.get("NumeroDocumentoEmpleador") != null ? jsonObject.get("NumeroDocumentoEmpleador").toString() : "");
            objetoAfiliacion.setSerialSAT(jsonObject.get("serialSat") != null ? jsonObject.get("serialSat").toString() : "");
            objetoAfiliacion.setPrimerNombreEmpleador(jsonObject.get("PrimerNombreEmpleador") != null ? jsonObject.get("PrimerNombreEmpleador").toString() : "");
            objetoAfiliacion.setSegundoNombreEmpleador(jsonObject.get("SegundoNombreEmpleador") != null ? jsonObject.get("SegundoNombreEmpleador").toString() : "");
            objetoAfiliacion.setPrimerApellidoEmpleador(jsonObject.get("PrimerApellidoEmpleador") != null ? jsonObject.get("PrimerApellidoEmpleador").toString() : "");
            objetoAfiliacion.setSegundoApellidoEmpleador(jsonObject.get("SegundoApellidoEmpleador") != null ? jsonObject.get("SegundoApellidoEmpleador").toString() : "");
            objetoAfiliacion.setFechaSolicitud(jsonObject.get("FechaSolicitud") != null ? jsonObject.get("FechaSolicitud").toString() : "");
            objetoAfiliacion.setPerdidaAfiliacionCausaGrave(jsonObject.get("PerdidaAfiliacionCausaGrave") != null ? jsonObject.get("PerdidaAfiliacionCausaGrave").toString() : "");
            objetoAfiliacion.setFechaEfectividad(jsonObject.get("FechaEfectivaAfiliacion") != null ? jsonObject.get("FechaEfectivaAfiliacion").toString() : "");
            objetoAfiliacion.setRazonSocialEmpleador(jsonObject.get("NombreRazonSocial") != null ? jsonObject.get("NombreRazonSocial").toString() : "");
            objetoAfiliacion.setNumeroMatriculaMercantil(jsonObject.get("NumeroMatriculaMercantil") != null ? jsonObject.get("NumeroMatriculaMercantil").toString() : "");
            objetoAfiliacion.setDepartamentoCausaSalarios(jsonObject.get("Departamento") != null ? jsonObject.get("Departamento").toString() : "");
            objetoAfiliacion.setMunicipioContacto(jsonObject.get("Municipio") != null ? jsonObject.get("Municipio").toString() : "");
            objetoAfiliacion.setDireccionContacto(jsonObject.get("DireccionContacto") != null ? jsonObject.get("DireccionContacto").toString() : "");
            objetoAfiliacion.setTelefonoMunicipioCausaSalarios(
                jsonObject.get("TelefonoContacto") != null && !jsonObject.get("TelefonoContacto").toString().trim().isEmpty()
                    ? Long.parseLong(jsonObject.get("TelefonoContacto").toString())
                    : 0L
            );
            objetoAfiliacion.setCorreoElectronicoContacto(jsonObject.get("CorreoElectronicoContacto") != null ? jsonObject.get("CorreoElectronicoContacto").toString() : "");
            objetoAfiliacion.setTipoDocumentoRepresentanteLegal(jsonObject.get("TipoDocumentoRepresentanteLegal") != null ? jsonObject.get("TipoDocumentoRepresentanteLegal").toString() : "");
            objetoAfiliacion.setNumeroDocumentoRepresentanteLegal(jsonObject.get("NumeroDocumentoRepresentanteLegal") != null ? jsonObject.get("NumeroDocumentoRepresentanteLegal").toString() : "");
            objetoAfiliacion.setPrimerNombreRepresentanteLegal(jsonObject.get("PrimerNombreRepresentanteLegal") != null ? jsonObject.get("PrimerNombreRepresentanteLegal").toString() : "");
            objetoAfiliacion.setSegundoNombreRepresentanteLegal(jsonObject.get("SegundoNombreRepresentanteLegal") != null ? jsonObject.get("SegundoNombreRepresentanteLegal").toString() : "");
            objetoAfiliacion.setPrimerApellidoRepresentanteLegal(jsonObject.get("PrimerApellidoRepresentanteLegal") != null ? jsonObject.get("PrimerApellidoRepresentanteLegal").toString() : "");
            objetoAfiliacion.setSegundoApellidoRepresentanteLegal(jsonObject.get("SegundoApellidoRepresentanteLegal") != null ? jsonObject.get("SegundoApellidoRepresentanteLegal").toString() : "");
            objetoAfiliacion.setAutorizacionDatosPersonales(jsonObject.get("AutorizacionManejoDatosPersonales") != null ? jsonObject.get("AutorizacionManejoDatosPersonales").toString() : "");
            objetoAfiliacion.setAutorizacionEnvioNotificaciones(jsonObject.get("AutorizacionEnvioNotificaciones") != null ? jsonObject.get("AutorizacionEnvioNotificaciones").toString() : "");
            objetoAfiliacion.setManifestacionNoAfiliadoCaja(jsonObject.get("ManifestacionNoAfiliacionPrevia") != null ? jsonObject.get("ManifestacionNoAfiliacionPrevia").toString() : "");


            status = "EXITO";

        } catch (Exception e) {
            logger.error("Error al procesar la respuesta JSON: ", e);
        }
    } else {
        logger.warn("No se recibió respuesta del servicio SAT.");
    }

    // Guardar auditoría
    guardaraAuditoriaAfiliacionEmpledoresPrimeraVez(numeroTransaccion, status, url, requestJson, wsResponse);

    return objetoAfiliacion;
}



private void guardaraAuditoriaAfiliacionEmpledoresPrimeraVez(String numeroTransaccion, String status, String url, String request, String response) {
    try {
        String sql = "INSERT INTO SAT.AUD_AFILIACION_EMPLEADORES_PRIMER_VEZ(fecha, numero_transaccion, status, URL, REQUEST, RESPONSE) " +
                     "VALUES (GETDATE(), :numeroTransaccion, :status, :url, :request, :response)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("numeroTransaccion", numeroTransaccion);
        query.setParameter("status", status);
        query.setParameter("url", url);
        query.setParameter("request", request);
        query.setParameter("response", response);

        query.executeUpdate();
        logger.info("Auditoría registrada exitosamente para transacción: {}"+ numeroTransaccion);
    } catch (Exception e) {
        logger.error("Error al registrar la auditoría: ", e);
    }
}


public String iniciarAfiliacionGenesys(ProcesoAfiliacionEmpleadoresPresencialDTO afiliacionLocal,UserDTO userDTO)
{     
    IniciarProcesoAfliliacionEmpleadoresPresencial inicial = new IniciarProcesoAfliliacionEmpleadoresPresencial(afiliacionLocal);
    inicial.execute();
    
    Long idInstanciaProceso = inicial.getResult();   
    
    return idInstanciaProceso.toString();
  
}

@Override
public ProcesoAfiliacionEmpleadoresPresencialDTO completarObjetoAfiliacion(ProcesoAfiliacionEmpleadoresPresencialDTO afiliacion)
{  
    
    String idPersona = "";
    TipoIdentificacionEnum tipoIdentificacion = afiliacion.getEmpleador().getEmpresa().getPersona().getTipoIdentificacion();
    String numeroIdentificacion = afiliacion.getEmpleador().getEmpresa().getPersona().getNumeroIdentificacion();
    
    idPersona = consultarPersonaPorTipoNumeroID(tipoIdentificacion,numeroIdentificacion);
  
    afiliacion.getEmpleador().getEmpresa().getPersona().setIdPersona(Long.parseLong(idPersona));        
    
    return afiliacion;
}


@Override
public Map<String, Object> consultarTareaActivaGenesys(String idInstanciaProceso)
{    
    Map<String, Object> tareaActivaResultado = new HashMap<>(); 
    ObtenerTareaActiva tareaActiva = new ObtenerTareaActiva(Long.parseLong(idInstanciaProceso));
    tareaActiva.execute();    
    tareaActivaResultado = tareaActiva.getResult(); 
    
    
    return tareaActivaResultado;            
}


@Override
public SolicitudAfiliacionEmpleador consultarSolicitudAfiliacionEmpleadorGenesys(String idSolicitud)
{
      
      
    ConsultarSolicitudAfiliacionEmpleador consultarSolicitudAfiliacionEmpleador =  new ConsultarSolicitudAfiliacionEmpleador(Long.parseLong(idSolicitud));    
    consultarSolicitudAfiliacionEmpleador.execute();    
    SolicitudAfiliacionEmpleador solicitudAfiliacionEmpleador= consultarSolicitudAfiliacionEmpleador.getResult();     
    
    return solicitudAfiliacionEmpleador;
          
}

    
@Override
public String radicarSolicitudEmpleadoresGenesys (Map<String, Object> informacionGeneral)
{
    RadicarSolicitudAfiliacionDTO radicacionObjJson = new RadicarSolicitudAfiliacionDTO();
    
    List<ItemChequeoDTO> listaChequeo = new ArrayList<>();
    ItemChequeoDTO itemChequeo = new ItemChequeoDTO();
    EmpleadorIntentoAfiliacionDTO empleador = new EmpleadorIntentoAfiliacionDTO();
    SolicitudAfiliacionEmpleador solicitudAfiliacionEmpleador = new SolicitudAfiliacionEmpleador();
    Solicitud solicitud = new Solicitud();
                
    //empleador.setRazonSocialEmp(informacionGeneral.get("primerNombreEmpleador") + " " + informacionGeneral.get("segundoNombreEmpleador") + " " + informacionGeneral.get("primerApellidoEmpleador") + " " + informacionGeneral.get("segundoApellidoEmpleador"));
     empleador.setRazonSocialEmp(informacionGeneral.get("NombreRazonSocial").toString());
    // Validacion de tipo de identificación
    
    switch (String.valueOf(informacionGeneral.get("tipoDocumentoEmpleador"))) {
    case "NI":
        empleador.setTipoIdentificacionEmp(TipoIdentificacionEnum.NIT);
        break;
    case "CC":
        empleador.setTipoIdentificacionEmp(TipoIdentificacionEnum.CEDULA_CIUDADANIA);
        break;
    case "CE":
        empleador.setTipoIdentificacionEmp(TipoIdentificacionEnum.CEDULA_EXTRANJERIA);
        break;
    case "CD":
        empleador.setTipoIdentificacionEmp(TipoIdentificacionEnum.CARNE_DIPLOMATICO);
        break;
    case "PE":
        empleador.setTipoIdentificacionEmp(TipoIdentificacionEnum.PERM_ESP_PERMANENCIA);
        break;
    case "PT":
        empleador.setTipoIdentificacionEmp(TipoIdentificacionEnum.PERM_PROT_TEMPORAL);
        break;        
    }
    
    empleador.setNumeroIdentificacionEmp(String.valueOf(informacionGeneral.get("numeroDocumentoEmpleador")));
    empleador.setDireccionEmp(String.valueOf(informacionGeneral.get("direccionContacto")));
    empleador.setTelefonoFijoEmp("0");
    empleador.setRepresentanteLegalEmp((informacionGeneral.get("primerNombreEmpleador") + " " + informacionGeneral.get("segundoNombreEmpleador") + " " + informacionGeneral.get("primerApellidoEmpleador") + " " + informacionGeneral.get("segundoApellidoEmpleador")));
    //solicitud.setIdSolicitud(Long.MIN_VALUE);
    solicitud.setIdSolicitud(Long.parseLong(String.valueOf(informacionGeneral.get("idSolicitud"))));
    solicitud.setIdInstanciaProceso(String.valueOf(informacionGeneral.get("idInstanciaProceso")));
    solicitud.setCanalRecepcion(CanalRecepcionEnum.WEB_SAT);
    solicitud.setMetodoEnvio(FormatoEntregaDocumentoEnum.ELECTRONICO);
    solicitud.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION);
    solicitud.setClasificacion(ClasificacionEnum.EMPLEADOR_DE_SERVICIO_DOMESTICO);
    solicitud.setUsuarioRadicacion("desarrollos@eprocess.com.co");
    solicitud.setFechaCreacion(new Date());
    solicitudAfiliacionEmpleador.setIdSolicitudAfiliacionEmpleador(Long.parseLong(String.valueOf(informacionGeneral.get("idSolicitudAfiliacionEmpleador"))));
    solicitudAfiliacionEmpleador.setIdEmpleador(Long.parseLong(String.valueOf(informacionGeneral.get("idEmpleador"))));
    solicitudAfiliacionEmpleador.setEstadoSolicitud(EstadoSolicitudAfiliacionEmpleadorEnum.PRE_RADICADA);
            
    solicitudAfiliacionEmpleador.setSolicitudGlobal(solicitud);            
    radicacionObjJson.setSolicitudAfiliacion(solicitudAfiliacionEmpleador);
    radicacionObjJson.setEmpleador(empleador);
    
    radicacionObjJson.setResultadoRadicacion(ResultadoRadicacionSolicitudEnum.EXITOSA);
    radicacionObjJson.setSolicitudAfiliacion(solicitudAfiliacionEmpleador);
    radicacionObjJson.setIdTarea(Long.parseLong(String.valueOf(informacionGeneral.get("idTarea"))));
    radicacionObjJson.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION);
    radicacionObjJson.setIdInstanciaProceso(Long.parseLong(String.valueOf(informacionGeneral.get("idInstanciaProceso"))));
        
    /**
     * 
     GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
     accesoCore.execute();
     TokenDTO token = accesoCore.getResult();    
     */
    RadicarSolicitudAfiliacionComunicado radicar = new RadicarSolicitudAfiliacionComunicado(radicacionObjJson);
    //radicar.setToken(token.getToken());    
    radicar.execute();    
    Map<String,Object> resultado = radicar.getResult();   
    
    return String.valueOf(resultado);
}


@Override
public String asignarSolicitudAfiliacion (Map<String, Object> informacionGeneral, String idTarea, String numeroRadicado)
{
    
    String resultado = "";
    Calendar calendario = Calendar.getInstance();
    Date fechaActual = calendario.getTime();
    
    AsignarSolicitudAfiliacionDTO asignarSolicitudObjeto = new AsignarSolicitudAfiliacionDTO();
    SolicitudAfiliacionEmpleador solicitudAfiliacionEmpleador = new SolicitudAfiliacionEmpleador();
    Solicitud solicitudObjeto = new Solicitud();
    
    solicitudObjeto.setIdSolicitud(Long.parseLong(String.valueOf(informacionGeneral.get("idSolicitud"))));
    solicitudObjeto.setIdInstanciaProceso(String.valueOf(informacionGeneral.get("idInstanciaProceso")));
    solicitudObjeto.setCanalRecepcion(CanalRecepcionEnum.WEB_SAT);
    solicitudObjeto.setEstadoDocumentacion(EstadoDocumentacionEnum.PENDIENTE_POR_ENVIAR);
    solicitudObjeto.setMetodoEnvio(FormatoEntregaDocumentoEnum.ELECTRONICO);
    solicitudObjeto.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION);
    solicitudObjeto.setClasificacion(ClasificacionEnum.EMPLEADOR_DE_SERVICIO_DOMESTICO);
    solicitudObjeto.setNumeroRadicacion(numeroRadicado);
    solicitudObjeto.setUsuarioRadicacion("desarrollos@eprocess.com.co");
    solicitudObjeto.setFechaRadicacion(fechaActual);
    solicitudObjeto.setFechaCreacion(fechaActual);
    solicitudObjeto.setObservacion("");
    solicitudObjeto.setSedeDestinatario("1");
    
    solicitudAfiliacionEmpleador.setIdSolicitudAfiliacionEmpleador(Long.parseLong(String.valueOf(informacionGeneral.get("idSolicitudAfiliacionEmpleador"))));
    solicitudAfiliacionEmpleador.setIdEmpleador(Long.parseLong(String.valueOf(informacionGeneral.get("idEmpleador"))));
    solicitudAfiliacionEmpleador.setEstadoSolicitud(EstadoSolicitudAfiliacionEmpleadorEnum.ASIGNADA_AL_BACK);
    solicitudAfiliacionEmpleador.setSolicitudGlobal(solicitudObjeto);
    
    
    asignarSolicitudObjeto.setIdTarea(Long.parseLong(idTarea));
    asignarSolicitudObjeto.setIdSedeCajaCompensacion(1L);
    asignarSolicitudObjeto.setSolicitudAfiliacionEmpleador(solicitudAfiliacionEmpleador);
    
    /**
     * 
     GenerarTokenAccesoCore accesoCore = new GenerarTokenAccesoCore();
     accesoCore.execute();
     TokenDTO token = accesoCore.getResult();    
     */
    AsignarSolicitudAfiliacionEmpleador asignar = new AsignarSolicitudAfiliacionEmpleador(asignarSolicitudObjeto);
    //asignar.setToken(token.getToken());    
    asignar.execute();    

    // ReasignarTarea reasignarTarea = new ReasignarTarea(Long.parseLong(idTarea), "gabriel.ortega@asopagos.com");
    // reasignarTarea.execute(); 
    
    return "Finalizado";
    
}


@Override
public ProcesoAfiliacionEmpleadoresPresencialDTO mappingAfiliacionGenesys(AfiliacionEmpleadoresprimeraVezSatDTO afiliacionLocal)
{
                
    ProcesoAfiliacionEmpleadoresPresencialDTO afiliacion = new ProcesoAfiliacionEmpleadoresPresencialDTO();
    
    List<ItemChequeoDTO> listaChequeo = new ArrayList<>();
    ItemChequeoDTO itemChequeo = new ItemChequeoDTO();
    Empleador empleador = new Empleador();
    Empresa empresa = new Empresa();
    Persona persona = new Persona();
    
    // Validacion de tipo de identificación
    
    switch (afiliacionLocal.getTipoDocumentoEmpleador()) {
    case "NI":
        persona.setTipoIdentificacion(TipoIdentificacionEnum.NIT);
        break;
    case "CC":
        persona.setTipoIdentificacion(TipoIdentificacionEnum.CEDULA_CIUDADANIA);
        break;
    case "CE":
        persona.setTipoIdentificacion(TipoIdentificacionEnum.CEDULA_EXTRANJERIA);
        break;
    case "CD":
        persona.setTipoIdentificacion(TipoIdentificacionEnum.CARNE_DIPLOMATICO);
        break;
    case "PE":
        persona.setTipoIdentificacion(TipoIdentificacionEnum.PERM_ESP_PERMANENCIA);
        break;
    case "PT":
        persona.setTipoIdentificacion(TipoIdentificacionEnum.PERM_PROT_TEMPORAL);
        break;        
    }
    
    persona.setDigitoVerificacion((short)3);
        
    persona.setNumeroIdentificacion(afiliacionLocal.getNumeroDocumentoEmpleador());
    persona.setRazonSocial(afiliacionLocal.getRazonSocialEmpleador());
    persona.setDigitoVerificacion(null);
    persona.setPrimerNombre(afiliacionLocal.getPrimerNombreEmpleador());
    persona.setSegundoNombre(afiliacionLocal.getSegundoNombreEmpleador());
    persona.setPrimerApellido(afiliacionLocal.getPrimerApellidoEmpleador());
    persona.setSegundoApellido(afiliacionLocal.getSegundoApellidoEmpleador());
    
    
//    persona.setIdPersona(Long.parseLong(consultarPersonaPorTipoNumeroID(persona.getTipoIdentificacion(),afiliacionLocal.getNumeroDocumentoEmpleador())));
        
    empresa.setPersona(persona);
   
    empleador.setEmpresa(empresa);
    
    // Se deja el requisito por defecto 72 con la fecha actual
    itemChequeo.setIdRequisito(72L);
    itemChequeo.setIdentificadorDocumento(null);
    itemChequeo.setEstadoRequisito(EstadoRequisitoTipoSolicitanteEnum.OPCIONAL);
    itemChequeo.setCumpleRequisito(true);
    itemChequeo.setFormatoEntregaDocumento(null);
    itemChequeo.setTipoRequisito(TipoRequisitoEnum.ESTANDAR);
    itemChequeo.setFechaRecepcionDocumentos(new Date().getTime());
    listaChequeo.add(itemChequeo);
    afiliacion.setEstado(true);
    afiliacion.setEmpleador(empleador);
    afiliacion.setUsuarioRadicador("gabriel.ortega@asopagos.com");
    afiliacion.setMetodoEnvio(FormatoEntregaDocumentoEnum.ELECTRONICO);
	
	if(afiliacionLocal.getTipoPersona() != null && afiliacionLocal.getTipoPersona().equals("J")){
        afiliacion.setClasificacion(ClasificacionEnum.PERSONA_JURIDICA);
        empresa.setNaturalezaJuridica(NaturalezaJuridicaEnum.obtenerNaturalezaJuridica(afiliacionLocal.getNaturalezaJuridica().intValue()));
    }else{
        afiliacion.setClasificacion(ClasificacionEnum.EMPLEADOR_DE_SERVICIO_DOMESTICO);
    }
	
    afiliacion.setTipoTransaccion(TipoTransaccionEnum.AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION);
    afiliacion.setCausaIntentoFallido(null);
    afiliacion.setListaChequeo(listaChequeo);

    return afiliacion;
  
}

@Override
public String guardarDatosTemporalesGenesys (Map<String, Object> informacionGeneral)
{
    Map<String, Object> jsonBody = new HashMap<>();
    String nombreRepresentanteLegal = "";
    
    nombreRepresentanteLegal = informacionGeneral.get("primerNombreRepresentanteLegal") + " " + informacionGeneral.get("segundoNombreRepresentanteLegal") + " " + informacionGeneral.get("primerApellidoRepresentanteLegal") + " " + informacionGeneral.get("segundoApellidoRepresentanteLegal");
    
    //Empleador
    Map<String, Object> empleador = new HashMap<>();
    Map<String, Object> empresa = new HashMap<>();
    Map<String, Object> persona = new HashMap<>();
    
    // Validacion de tipo de identificación
    
    switch (String.valueOf(informacionGeneral.get("tipoDocumentoEmpleador"))) {
    case "NI":        
        persona.put("tipoIdentificacion", TipoIdentificacionEnum.NIT);
        break;
    case "CC":
        persona.put("tipoIdentificacion",TipoIdentificacionEnum.CEDULA_CIUDADANIA);
        break;
    case "CE":
        persona.put("tipoIdentificacion",TipoIdentificacionEnum.CEDULA_EXTRANJERIA);
        break;
    case "CD":
        persona.put("tipoIdentificacion",TipoIdentificacionEnum.CARNE_DIPLOMATICO);
        break;
    case "PE":
        persona.put("tipoIdentificacion",TipoIdentificacionEnum.PERM_ESP_PERMANENCIA);
        break;
    case "PT":
        persona.put("tipoIdentificacion",TipoIdentificacionEnum.PERM_PROT_TEMPORAL);
        break;        
    }    
    switch (String.valueOf(informacionGeneral.get("tipoDocumentoRepresentanteLegal"))) {
    case "NI":        
        informacionGeneral.put("tipoDocumentoRepresentanteLegalTexto", TipoIdentificacionEnum.NIT);
        break;
    case "CC":
        informacionGeneral.put("tipoDocumentoRepresentanteLegalTexto",TipoIdentificacionEnum.CEDULA_CIUDADANIA);
        break;
    case "CE":
        informacionGeneral.put("tipoDocumentoRepresentanteLegalTexto",TipoIdentificacionEnum.CEDULA_EXTRANJERIA);
        break;
    case "CD":
        informacionGeneral.put("tipoDocumentoRepresentanteLegalTexto",TipoIdentificacionEnum.CARNE_DIPLOMATICO);
        break;
    case "PE":
        informacionGeneral.put("tipoDocumentoRepresentanteLegalTexto",TipoIdentificacionEnum.PERM_ESP_PERMANENCIA);
        break;
    case "PT":
        informacionGeneral.put("tipoDocumentoRepresentanteLegalTexto",TipoIdentificacionEnum.PERM_PROT_TEMPORAL);
        break;        
    }

    persona.put("numeroIdentificacion", informacionGeneral.get("numeroDocumentoEmpleador"));
    persona.put("primerNombre", informacionGeneral.get("primerNombreEmpleador"));
    persona.put("segundoNombre", informacionGeneral.get("segundoNombreEmpleador"));
    persona.put("primerApellido", informacionGeneral.get("primerApellidoEmpleador"));
    persona.put("segundoApellido", informacionGeneral.get("segundoApellidoEmpleador"));
   // persona.put("razonSocial", (informacionGeneral.get("primerNombreEmpleador") + " " + informacionGeneral.get("segundoNombreEmpleador") + " " + informacionGeneral.get("primerApellidoEmpleador") + " " + informacionGeneral.get("segundoApellidoEmpleador")));
   persona.put("razonSocial", informacionGeneral.get("NombreRazonSocial"));
    empresa.put("persona", persona);
    //empresa.put("fechaConstitucion", 1683262800000L);
    empleador.put("idEmpleador", informacionGeneral.get("idEmpleador"));
    empleador.put("estadoEmpleador", "NO_FORMALIZADO_CON_INFORMACION");
    empleador.put("medioDePagoSubsidioMonetario", "EFECTIVO");
    empleador.put("empresa", empresa);
    jsonBody.put("empleador",empleador);
    //Sucursales
    
    Map<String, Object> municipio = new HashMap<>();
    List<ElementoListaDTO> listElementsDpto= consultarListasValores(Arrays.asList(315));
    if(CollectionUtils.isNotEmpty(listElementsDpto)){
        
        String codigo = (String)informacionGeneral.get("departamentoCausaSalarios")+(String)informacionGeneral.get("municipioContacto");
        ElementoListaDTO elementCodigo = listElementsDpto.stream()
                .filter(ite -> ite.getAtributos().get("codigo").equals(codigo))
                .findFirst().orElse(null);
        
        if(elementCodigo != null){
            municipio.put("idDepartamento", elementCodigo.getAtributos().get("idDepartamento"));
            municipio.put("idMunicipio", elementCodigo.getAtributos().get("idMunicipio"));
        }
    }    
    
    Map<String, Object> ubicacion = new HashMap<>();
    ubicacion.put("municipio", municipio);
    ubicacion.put("indicativoTelFijo", "");
    ubicacion.put("direccionFisica", informacionGeneral.get("direccionContacto"));
    ubicacion.put("telefonoFijo", "");
    ubicacion.put("telefonoCelular", informacionGeneral.get("telefonoContacto"));
    
    //En el proceso masivo no lleva notificaciones
    ubicacion.put("autorizacionEnvioEmail", false);
    /*
    String notificacion = (String)informacionGeneral.get("telefonoContacto");
    if(notificacion != null && notificacion.equals("1")){
        ubicacion.put("autorizacionEnvioEmail", true);
    }else{
        ubicacion.put("autorizacionEnvioEmail", false);
    }*/
    
    ubicacion.put("email", informacionGeneral.get("correoElectronicoContacto"));
    Map<String, Object> sucursal = new HashMap<>();
    sucursal.put("codigo", 1);
    sucursal.put("ubicacion", ubicacion);
    sucursal.put("nombre", "Oficina Principal");
    sucursal.put("estadoSucursal", "ACTIVO");
    sucursal.put("medioDePagoSubsidioMonetario", "CONSIGNACION");//Preguntar Esto !!!
    sucursal.put("idEmpresa", informacionGeneral.get("idEmpresa"));
    sucursal.put("sucursalPrincipal", true);
    Map<String, Object> sucursales = new HashMap<>();
    jsonBody.put("sucursales", new Object[] {sucursal});

    // Representante 1
    Map<String, Object> representante1 = new HashMap<>();
    representante1.put("tipoIdentificacion", informacionGeneral.get("tipoDocumentoRepresentanteLegalTexto"));
    representante1.put("numeroIdentificacion", informacionGeneral.get("numeroDocumentoRepresentanteLegal"));
    representante1.put("primerNombre", informacionGeneral.get("primerNombreRepresentanteLegal"));
    representante1.put("segundoNombre", informacionGeneral.get("segundoNombreRepresentanteLegal"));
    representante1.put("primerApellido", informacionGeneral.get("primerApellidoRepresentanteLegal"));
    representante1.put("segundoApellido", informacionGeneral.get("segundoApellidoRepresentanteLegal"));
    representante1.put("razonSocial", nombreRepresentanteLegal);
    representante1.put("ubicacionPrincipal", ubicacion);
    jsonBody.put("representante1",representante1);
    //Ubicaciones
    Map<String, Object> ubicaciones = new HashMap<>();
    ubicaciones.put("ubicacion", ubicacion);
    ubicaciones.put("tipoUbicacion", "UBICACION_PRINCIPAL");
    jsonBody.put("ubicaciones", new Object[] {ubicaciones});
    // Convertir jsonBody a String
    String json = JsonUtil.toJson(jsonBody);

    GuardarDatosTemporalesEmpleador guardarDatosTemporalesEmpleador = new GuardarDatosTemporalesEmpleador(Long.parseLong(String.valueOf(informacionGeneral.get("idSolicitud"))), String.valueOf(informacionGeneral.get("numeroDocumentoEmpleador")), TipoIdentificacionEnum.CEDULA_CIUDADANIA, json);
    guardarDatosTemporalesEmpleador.execute();    
    Long resultado = guardarDatosTemporalesEmpleador.getResult();              
    
    return String.valueOf(resultado);
}


@Override
public String guardarInformacionSat(AfiliacionEmpleadoresprimeraVezSatDTO afiliacion)
{
    
    boolean exitoso = true;
    RespuestaNotificacionSatDTO respuesta = new RespuestaNotificacionSatDTO();
    String idCreado = "";
        
        try {
            String insertString = "INSERT INTO SAT.WS_AFILIACION_EMPLEADORES_PRIMERA_VEZ (radicado,numeroTransaccion,tipoPersona,naturalezaJuridica,tipoDocumentoEmpleador,numeroDocumentoEmpleador,serialSAT,primerNombreEmpleador,segundoNombreEmpleador,primerApellidoEmpleador,segundoApellidoEmpleador,fechaSolicitud,perdidaAfiliacionCausaGrave,fechaEfectividad,razonSocialEmpleador,numeroMatriculaMercantil,departamentoCausaSalarios,municipioContacto,direccionContacto,telefonoContacto,correoElectronicoContacto,tipoDocumentoRepresentanteLegal,numeroDocumentoRepresentanteLegal,primerNombreRepresentanteLegal,segundoNombreRepresentanteLegal,primerApellidoRepresentanteLegal,segundoApellidoRepresentanteLegal,autorizacionDatosPersonales,autorizacionEnvioNotificaciones,manifestacionNoAfiliadoCaja,resultado,estadoSolicitud,fechaCreacion) VALUES (" +
            "'" + afiliacion.getRadicado() + "'," +
            "'" + afiliacion.getNumeroTransaccion() + "'," +
            "'" + afiliacion.getTipoPersona() + "', " +
            "'" + afiliacion.getNaturalezaJuridica() + "', " +
            "'" + afiliacion.getTipoDocumentoEmpleador() + "', " +
            "'" + afiliacion.getNumeroDocumentoEmpleador() + "', " +
            "'" + afiliacion.getSerialSAT() + "', " +
            "'" + afiliacion.getPrimerNombreEmpleador() + "', " +
            "'" + afiliacion.getSegundoNombreEmpleador() + "', " +
            "'" + afiliacion.getPrimerApellidoEmpleador() + "', " +
            "'" + afiliacion.getSegundoApellidoEmpleador() + "', " +
            "'" + afiliacion.getFechaSolicitud() + "', " +
            "'" + afiliacion.getPerdidaAfiliacionCausaGrave() + "', " +
            "'" + afiliacion.getFechaEfectividad() + "', " +
            "'" + afiliacion.getRazonSocialEmpleador() + "', " +
            "'" + afiliacion.getNumeroMatriculaMercantil() + "', " +
            "'" + afiliacion.getDepartamentoCausaSalarios() + "', " +
            "'" + afiliacion.getMunicipioContacto() + "', " +
            "'" + afiliacion.getDireccionContacto() + "', " +
            "'" + afiliacion.getTelefonoMunicipioCausaSalarios() + "', " +
            "'" + afiliacion.getCorreoElectronicoContacto() + "', " +
            "'" + afiliacion.getTipoDocumentoRepresentanteLegal() + "', " +
            "'" + afiliacion.getNumeroDocumentoRepresentanteLegal() + "', " +
            "'" + afiliacion.getPrimerNombreRepresentanteLegal() + "', " +
            "'" + afiliacion.getSegundoNombreRepresentanteLegal() + "', " +
            "'" + afiliacion.getPrimerApellidoRepresentanteLegal() + "', " +
            "'" + afiliacion.getSegundoApellidoRepresentanteLegal() + "', " +
            "'" + afiliacion.getAutorizacionDatosPersonales() + "', " +
            "'" + afiliacion.getAutorizacionEnvioNotificaciones() + "', " +
            "'" + afiliacion.getManifestacionNoAfiliadoCaja() + "', " +
            "'En espera...', " +
            "'En espera...', " +
            "getdate())";  
   
            Query query = entityManager.createNativeQuery(insertString);
          
            int result = query.executeUpdate();
            
            if (result != 1) {
                exitoso = false;
            }else{
                // Obtener el ID insertado
                Object insertedId = entityManager.createNativeQuery("SELECT MAX(ID) FROM SAT.WS_AFILIACION_EMPLEADORES_PRIMERA_VEZ").getSingleResult();
                if (insertedId != null) {
                    idCreado = String.valueOf(insertedId);
                    // Hacer algo con el ID insertado
                    System.out.println("ID insertado: " + idCreado);
                }
            }
        } catch (Exception ex) {
            exitoso = false;
            //Imprimir error
            System.out.println("Error al ejecutar INSERT en la tabla AFILIACION_EMPLEADORES_MISMO_DEPARTAMENTO: " + ex.getMessage());
        }
        
        return idCreado;
}

/*
@Override
public String generarTokenGenesys()
{        
         try{
            // Genesys DESARROLLO
            String url = "https://securitygenesyspruebas.asopagos.com/auth/realms/Integracion/protocol/openid-connect/token";
            String username = "DESARROLLOS@EPROCESS.COM.CO";
            String password = "Master02*123";
            String client_id = "admin-cli";
            String grant_type = "password";
            Map<String,Object> urlParams = new HashMap<String,Object>();
            urlParams.put("username", username);
            urlParams.put("password", password);
            urlParams.put("client_id", client_id);
            urlParams.put("grant_type", grant_type);
            Map<String,Object> respuesta = HttpUtil.sendHttpPost(url, urlParams);
            String token = "";
            if(respuesta != null && respuesta.get("status") != null && respuesta.get("status").toString().equals("OK")){
                String json = respuesta.get("mensaje").toString();
                Map<String,Object> retorno = JsonUtil.parseJson(json);
                token = retorno.get("access_token").toString();
                //System.out.println(retorno);
            }else{
                String json = respuesta.get("mensaje").toString();
                Map<String,Object> retorno = JsonUtil.parseJson(json);
                //System.out.println(retorno);
                token = null;
            }
            logger.info("Mauricio - Token:" + token);
            return token;
        }catch(Exception e){
            logger.error(e);
            return null;
        }
        
}
*/

 @Override    
    public List<AfiliacionEmpleadoresprimeraVezSatDTO> consultarAfiliacionEmpleadoresPrimeraVez(BusquedaSAT busquedaSAT){
        logger.info("Comienza llamado servicios consultarAfiliacionEmpleadores");
        List<AfiliacionEmpleadoresprimeraVezSatDTO> respuesta = new ArrayList<AfiliacionEmpleadoresprimeraVezSatDTO>();
        try{
            String sq = "select id\n" +
            "                  ,radicado\n" +
            "                  ,numeroTransaccion\n" +
            "                  ,tipoPersona\n" +
            "                  ,naturalezaJuridica\n" +
            "                  ,tipoDocumentoEmpleador\n" +
            "                  ,numeroDocumentoEmpleador\n" +
            "                  ,serialSAT\n" +
            "                  ,primerNombreEmpleador\n" +
            "                  ,segundoNombreEmpleador\n" +
            "                  ,primerApellidoEmpleador\n" +
            "                  ,segundoApellidoEmpleador\n" +
            "                  ,fechaSolicitud\n" +
            "                  ,'' perdidaAfiliacionCausaGrave\n" +
            "                  ,fechaEfectividad\n" +
            "                  ,razonSocialEmpleador\n" +
            "                  ,numeroMatriculaMercantil\n" +
            "                  ,departamentoCausaSalarios\n" +
            "                  ,municipioContacto\n" +
            "                  ,direccionContacto\n" +
            "                  ,correoElectronicoContacto\n" +
            "                  ,tipoDocumentoRepresentanteLegal\n" +
            "                  ,numeroDocumentoRepresentanteLegal\n" +
            "                  ,primerNombreRepresentanteLegal\n" +
            "                  ,segundoNombreRepresentanteLegal\n" +
            "                  ,primerApellidoRepresentanteLegal\n" +
            "                  ,segundoApellidoRepresentanteLegal\n" +
            "                  ,autorizacionDatosPersonales\n" +
            "                  ,autorizacionEnvioNotificaciones\n" +
            "                  ,manifestacionNoAfiliadoCaja \n" +            
            "                  ,sola.saeEstadoSolicitud \n" +
            "                  ,(\n" +
"                 SELECT CASE sola.saeEstadoSolicitud \n" +
"                   When 'INSTANCIADA' Then 'ASIGNADO AL BACK'\n" +
"                   When 'RADICADA' Then 'ASIGNADO AL BACK'\n" +
"                   When 'ASIGNADA_AL_BACK' Then 'ASIGNADO AL BACK'\n" +
"                   When 'PENDIENTE_ENVIO_AL_BACK' Then 'ASIGNADO AL BACK'\n" +
"                   When 'PRE_RADICADA' Then 'ASIGNADO AL BACK'\n" +
"                   When 'EN_ANALISIS_ESPECIALIZADO' Then 'ASIGNADO AL BACK'\n" +
"                   When 'APROBADA' Then 'APROBADO/CERRADO'\n" +
"                   When 'CERRADA' Then 'APROBADO/CERRADO'\n" +
"                   When 'NO_CONFORME_SUBSANABLE' Then 'RECHAZADO/CERRADO'\n" +
"                   When 'NO_CONFORME_EN_GESTION' Then 'RECHAZADO/CERRADO'\n" +
"                   When 'NO_CONFORME_GESTIONADA' Then 'RECHAZADO/CERRADO'\n" +
"                   When 'RECHAZADA' Then 'RECHAZADO/CERRADO'\n" +
"                   When 'DESISTIDA' Then 'RECHAZADO/CERRADO'\n" +
"                   When 'CANCELADA' Then 'RECHAZADO/CERRADO'\n" +
"                   ELSE 'NO APLICA.'\n" +
"                   END\n" +
"                 ) AS resultado"+
            "                  ,motivoRechazoSolicitud \n" +
            "                  ,fechaCreacion \n" +        
            "       from SAT.WS_AFILIACION_EMPLEADORES_PRIMERA_VEZ" +
            "       left join solicitud sol on sol.solNumeroRadicacion = radicado\n" +
            "       left join SolicitudAfiliaciEmpleador sola on sola.saeSolicitudGlobal = sol.solid" +
            "";

            String where = " WHERE 1=1 ";
            if(!busquedaSAT.getEstado().equals("")){
                where = where + " and (\n" +
"                 SELECT CASE sola.saeEstadoSolicitud \n" +
"                   When 'INSTANCIADA' Then 'Asignado al back'\n" +
"                   When 'RADICADA' Then 'Asignado al back'\n" +
"                   When 'ASIGNADA_AL_BACK' Then 'Asignado al back'\n" +
"                   When 'PENDIENTE_ENVIO_AL_BACK' Then 'Asignado al back'\n" +
"                   When 'PRE_RADICADA' Then 'Asignado al back'\n" +
"                   When 'EN_ANALISIS_ESPECIALIZADO' Then 'Asignado al back'\n" +
"                   When 'APROBADA' Then 'Aprobado/cerrado'\n" +
"                   When 'CERRADA' Then 'Aprobado/cerrado'\n" +
"                   When 'NO_CONFORME_SUBSANABLE' Then 'Rechazado/cerrado'\n" +
"                   When 'NO_CONFORME_EN_GESTION' Then 'Rechazado/cerrado'\n" +
"                   When 'NO_CONFORME_GESTIONADA' Then 'Rechazado/cerrado'\n" +
"                   When 'RECHAZADA' Then 'Rechazado/cerrado'\n" +
"                   When 'DESISTIDA' Then 'Rechazado/cerrado'\n" +
"                   When 'CANCELADA' Then 'Rechazado/cerrado'\n" +
"                   ELSE 'NO APLICA.'\n" +
"                   END\n" +
"                 ) = '"+busquedaSAT.getEstado()+"'";
            }
            
            where = where + " and resultado not like '%ENVIADO A SAT%' ";
            
                    if(!busquedaSAT.getNumeroDocumentoEmpleador().equals("")){
                where = where + " and numeroDocumentoEmpleador = '"+busquedaSAT.getNumeroDocumentoEmpleador()+"'";
            }
            if(!(busquedaSAT.getFechaInicio().equals("") && busquedaSAT.getFechaFin().equals(""))){

                //where = where + " and fechaCreacion between '"+busquedaSAT.getFechaInicio()+"' and '"+busquedaSAT.getFechaFin()+"'";
                where = where + "AND fechaCreacion >= '" + busquedaSAT.getFechaInicio() + " 00:00:00.000' AND fechaCreacion <= '" + busquedaSAT.getFechaFin() + " 23:59:59.999';";
            }
            
            Query query = entityManager.createNativeQuery(sq + where);
            Integer numero = 0;
            List<Object[]> listado = query.getResultList();            
            for(Object[] obj:listado){
                try{
                    numero = numero + 1;
                    AfiliacionEmpleadoresprimeraVezSatDTO afiliacionEmpleadores = new AfiliacionEmpleadoresprimeraVezSatDTO();
                    afiliacionEmpleadores.setId(obj[0] != null ? new Long(obj[0].toString()) : 0L);
                    afiliacionEmpleadores.setRadicado(obj[1] != null ? obj[1].toString() : "");
                    afiliacionEmpleadores.setNumeroTransaccion(obj[2] != null ? obj[2].toString() : "");
                    afiliacionEmpleadores.setTipoPersona(obj[3] != null ? obj[3].toString() : "");
                    afiliacionEmpleadores.setNaturalezaJuridica(obj[4] != null ? new Long(obj[4].toString()) : null);
                    afiliacionEmpleadores.setTipoDocumentoEmpleador(obj[5] != null ? obj[5].toString() : "");
                    afiliacionEmpleadores.setNumeroDocumentoEmpleador(obj[6] != null ? obj[6].toString() : "");
                    afiliacionEmpleadores.setSerialSAT(obj[7] != null ? obj[7].toString() : "");
                    afiliacionEmpleadores.setPrimerNombreEmpleador(obj[8] != null ? obj[8].toString() : "");
                    afiliacionEmpleadores.setSegundoNombreEmpleador(obj[9] != null ? obj[9].toString() : "");
                    afiliacionEmpleadores.setPrimerApellidoEmpleador(obj[10] != null ? obj[10].toString() : "");
                    afiliacionEmpleadores.setSegundoApellidoEmpleador(obj[11] != null ? obj[11].toString() : "");
                    afiliacionEmpleadores.setFechaSolicitud(obj[12] != null ? obj[12].toString() : null);
                    afiliacionEmpleadores.setPerdidaAfiliacionCausaGrave(obj[13] != null ? obj[13].toString() : "");
                    afiliacionEmpleadores.setFechaEfectividad(obj[14] != null ? obj[14].toString() : null);
                    afiliacionEmpleadores.setRazonSocialEmpleador(obj[15] != null ? obj[15].toString() : "");
                    afiliacionEmpleadores.setNumeroMatriculaMercantil(obj[16] != null ? obj[16].toString() : "");
                    afiliacionEmpleadores.setDepartamentoCausaSalarios(obj[17] != null ? obj[17].toString() : "");
                    afiliacionEmpleadores.setMunicipioContacto(obj[18] != null ? obj[18].toString() : "");
                    afiliacionEmpleadores.setDireccionContacto(obj[19] != null ? obj[19].toString() : "");                    
                    afiliacionEmpleadores.setCorreoElectronicoContacto(obj[20] != null ? obj[20].toString() : "");
                    afiliacionEmpleadores.setTipoDocumentoRepresentanteLegal(obj[21] != null ? obj[21].toString() : "");
                    afiliacionEmpleadores.setNumeroDocumentoRepresentanteLegal(obj[22] != null ? obj[22].toString() : "");
                    afiliacionEmpleadores.setPrimerNombreRepresentanteLegal(obj[23] != null ? obj[23].toString() : "");
                    afiliacionEmpleadores.setSegundoNombreRepresentanteLegal(obj[24] != null ? obj[24].toString() : "");
                    afiliacionEmpleadores.setPrimerApellidoRepresentanteLegal(obj[25] != null ? obj[25].toString() : "");
                    afiliacionEmpleadores.setSegundoApellidoRepresentanteLegal(obj[26] != null ? obj[26].toString() : "");
                    afiliacionEmpleadores.setAutorizacionDatosPersonales(obj[27] != null ? obj[27].toString() : "");
                    afiliacionEmpleadores.setAutorizacionEnvioNotificaciones(obj[28] != null ? obj[28].toString() : "");
                    afiliacionEmpleadores.setManifestacionNoAfiliadoCaja(obj[29] != null ? obj[29].toString() : "");
                    afiliacionEmpleadores.setEstadoSolicitud(obj[30] != null ? obj[30].toString() : "");
                    afiliacionEmpleadores.setResultado(obj[31] != null ? obj[31].toString() : "");
                    afiliacionEmpleadores.setMotivoRechazo(obj[32] != null ? obj[32].toString() : "");
                    //afiliacionEmpleadores.setFechaEfectividad(obj[14] != null ? new Date(obj[14].toString()) : null);                    
                    //afiliacionEmpleadores.setTelefonoMunicipioCausaSalarios(obj[20] != null ? new Long(obj[20].toString()) : null);
                    //afiliacionEmpleadores.setFechaSolicitud(obj[12] != null ? new Date(obj[12].toString()) : null);                        
                    //afiliacionEmpleadores.setEstado(obj[31] != null ? obj[31].toString() : "");                    
                    respuesta.add(afiliacionEmpleadores);
                }catch(Exception e){
                    e.printStackTrace();
                    System.out.println("Ocurrio un error por ++++++++++++++++++++++++++++++++++++++++++++++++++++");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e);
        }
        logger.info("Termina llamado servicios reportarAfiliacionPrimeraVez");
        return respuesta;
    }

    
    @Override    
    public List<AfiliacionEmpleadoresprimeraVezSatDTO> consultarAfiliacionesEnvioMasivo(BusquedaSAT busquedaSAT){
        logger.info("Comienza llamado servicios consultarAfiliacionEmpleadores");
        List<AfiliacionEmpleadoresprimeraVezSatDTO> respuesta = new ArrayList<AfiliacionEmpleadoresprimeraVezSatDTO>();
        try{
            String sq = "select distinct b.* from (\n" +
            "        select id\n" +
            "                  ,radicado\n" +
            "                  ,numeroTransaccion\n" +
            "                  ,tipoPersona\n" +
            "                  ,naturalezaJuridica\n" +
            "                  ,tipoDocumentoEmpleador\n" +
            "                  ,numeroDocumentoEmpleador\n" +
            "                  ,serialSAT\n" +
            "                  ,primerNombreEmpleador\n" +
            "                  ,segundoNombreEmpleador\n" +
            "                  ,primerApellidoEmpleador\n" +
            "                  ,segundoApellidoEmpleador\n" +
            "                  ,fechaSolicitud\n" +
            "                  ,'' perdidaAfiliacionCausaGrave\n" +
            "                  ,fechaEfectividad\n" +
            "                  ,razonSocialEmpleador\n" +
            "                  ,numeroMatriculaMercantil\n" +
            "                  ,departamentoCausaSalarios\n" +
            "                  ,municipioContacto\n" +
            "                  ,direccionContacto\n" +
            "                  ,correoElectronicoContacto\n" +
            "                  ,tipoDocumentoRepresentanteLegal\n" +
            "                  ,numeroDocumentoRepresentanteLegal\n" +
            "                  ,primerNombreRepresentanteLegal\n" +
            "                  ,segundoNombreRepresentanteLegal\n" +
            "                  ,primerApellidoRepresentanteLegal\n" +
            "                  ,segundoApellidoRepresentanteLegal\n" +
            "                  ,autorizacionDatosPersonales\n" +
            "                  ,autorizacionEnvioNotificaciones\n" +
            "                  ,manifestacionNoAfiliadoCaja \n" +            
            "                  ,estadoSolicitud \n" +
            "                  ,resultado \n" +
            "                  ,motivoRechazoSolicitud \n" +
            "                  ,fechaCreacion \n" +        
            "       from SAT.WS_AFILIACION_EMPLEADORES_PRIMERA_VEZ ) b\n" +
            "       inner join solicitud sol on sol.solNumeroRadicacion = b.radicado\n" +
            "       inner join SolicitudAfiliaciEmpleador sola on sola.saeSolicitudGlobal = sol.solId";
            
            String where = " WHERE " +                    
                    "sola.saeEstadoSolicitud in( \n" +
                    "'NO_CONFORME_SUBSANABLE',\n" +
                    "'NO_CONFORME_EN_GESTION',\n" +
                    "'NO_CONFORME_GESTIONADA',\n" +
                    "'RECHAZADA',\n" +
                    "'APROBADA',\n" +
                    "'CERRADA',\n" +
                    "'DESISTIDA',\n" +
                    "'CANCELADA'\n" +
                    ") ";

            if(!busquedaSAT.getEstado().equals("")){
                where = where + " and (\n" +
"                 SELECT CASE sola.saeEstadoSolicitud \n" +
"                   When 'INSTANCIADA' Then 'ASIGNADO AL BACK'\n" +
"                   When 'RADICADA' Then 'ASIGNADO AL BACK'\n" +
"                   When 'ASIGNADA_AL_BACK' Then 'ASIGNADO AL BACK'\n" +
"                   When 'PENDIENTE_ENVIO_AL_BACK' Then 'ASIGNADO AL BACK'\n" +
"                   When 'PRE_RADICADA' Then 'ASIGNADO AL BACK'\n" +
"                   When 'EN_ANALISIS_ESPECIALIZADO' Then 'ASIGNADO AL BACK'\n" +
"                   When 'APROBADA' Then 'APROBADO/CERRADO'\n" +
"                   When 'CERRADA' Then 'APROBADO/CERRADO'\n" +
"                   When 'NO_CONFORME_SUBSANABLE' Then 'RECHAZADO/CERRADO'\n" +
"                   When 'NO_CONFORME_EN_GESTION' Then 'RECHAZADO/CERRADO'\n" +
"                   When 'NO_CONFORME_GESTIONADA' Then 'RECHAZADO/CERRADO'\n" +
"                   When 'RECHAZADA' Then 'RECHAZADO/CERRADO'\n" +
"                   When 'DESISTIDA' Then 'RECHAZADO/CERRADO'\n" +
"                   When 'CANCELADA' Then 'RECHAZADO/CERRADO'\n" +
"                   ELSE 'NO APLICA.'\n" +
"                   END\n" +
"                 ) = '"+busquedaSAT.getEstado()+"'";
            }
            
            
            where = where + " and resultado not like '%ENVIADO A SAT%'";
            if(!busquedaSAT.getNumeroDocumentoEmpleador().equals("")){
                where = where + " and numeroDocumentoEmpleador = '"+busquedaSAT.getNumeroDocumentoEmpleador()+"'";
            }
            if(!(busquedaSAT.getFechaInicio().equals("") && busquedaSAT.getFechaFin().equals(""))){
                where = where + " and fechaCreacion between '"+busquedaSAT.getFechaInicio()+"' and '"+busquedaSAT.getFechaFin()+"'";
            }
            
            
            Query query = entityManager.createNativeQuery(sq + where);
            Integer numero = 0;
            List<Object[]> listado = query.getResultList();            
            for(Object[] obj:listado){
                try{
                    numero = numero + 1;
                    AfiliacionEmpleadoresprimeraVezSatDTO afiliacionEmpleadores = new AfiliacionEmpleadoresprimeraVezSatDTO();
                    afiliacionEmpleadores.setId(obj[0] != null ? new Long(obj[0].toString()) : 0L);
                    afiliacionEmpleadores.setRadicado(obj[1] != null ? obj[1].toString() : "");
                    afiliacionEmpleadores.setNumeroTransaccion(obj[2] != null ? obj[2].toString() : "");
                    afiliacionEmpleadores.setTipoPersona(obj[3] != null ? obj[3].toString() : "");
                    afiliacionEmpleadores.setNaturalezaJuridica(obj[4] != null ? new Long(obj[4].toString()) : null);
                    afiliacionEmpleadores.setTipoDocumentoEmpleador(obj[5] != null ? obj[5].toString() : "");
                    afiliacionEmpleadores.setNumeroDocumentoEmpleador(obj[6] != null ? obj[6].toString() : "");
                    afiliacionEmpleadores.setSerialSAT(obj[7] != null ? obj[7].toString() : "");
                    afiliacionEmpleadores.setPrimerNombreEmpleador(obj[8] != null ? obj[8].toString() : "");
                    afiliacionEmpleadores.setSegundoNombreEmpleador(obj[9] != null ? obj[9].toString() : "");
                    afiliacionEmpleadores.setPrimerApellidoEmpleador(obj[10] != null ? obj[10].toString() : "");
                    afiliacionEmpleadores.setSegundoApellidoEmpleador(obj[11] != null ? obj[11].toString() : "");
                    afiliacionEmpleadores.setFechaSolicitud(obj[12] != null ? obj[12].toString() : null);
                    afiliacionEmpleadores.setPerdidaAfiliacionCausaGrave(obj[13] != null ? obj[13].toString() : "");
                    afiliacionEmpleadores.setFechaEfectividad(obj[14] != null ? obj[14].toString() : null);
                    afiliacionEmpleadores.setRazonSocialEmpleador(obj[15] != null ? obj[15].toString() : "");
                    afiliacionEmpleadores.setNumeroMatriculaMercantil(obj[16] != null ? obj[16].toString() : "");
                    afiliacionEmpleadores.setDepartamentoCausaSalarios(obj[17] != null ? obj[17].toString() : "");
                    afiliacionEmpleadores.setMunicipioContacto(obj[18] != null ? obj[18].toString() : "");
                    afiliacionEmpleadores.setDireccionContacto(obj[19] != null ? obj[19].toString() : "");
                    afiliacionEmpleadores.setCorreoElectronicoContacto(obj[20] != null ? obj[20].toString() : "");
                    afiliacionEmpleadores.setTipoDocumentoRepresentanteLegal(obj[21] != null ? obj[21].toString() : "");
                    afiliacionEmpleadores.setNumeroDocumentoRepresentanteLegal(obj[22] != null ? obj[22].toString() : "");
                    afiliacionEmpleadores.setPrimerNombreRepresentanteLegal(obj[23] != null ? obj[23].toString() : "");
                    afiliacionEmpleadores.setSegundoNombreRepresentanteLegal(obj[24] != null ? obj[24].toString() : "");
                    afiliacionEmpleadores.setPrimerApellidoRepresentanteLegal(obj[25] != null ? obj[25].toString() : "");
                    afiliacionEmpleadores.setSegundoApellidoRepresentanteLegal(obj[26] != null ? obj[26].toString() : "");
                    afiliacionEmpleadores.setAutorizacionDatosPersonales(obj[27] != null ? obj[27].toString() : "");
                    afiliacionEmpleadores.setAutorizacionEnvioNotificaciones(obj[28] != null ? obj[28].toString() : "");
                    afiliacionEmpleadores.setManifestacionNoAfiliadoCaja(obj[29] != null ? obj[29].toString() : "");
                    afiliacionEmpleadores.setEstadoSolicitud(obj[30] != null ? obj[30].toString() : "");
                    afiliacionEmpleadores.setResultado(obj[31] != null ? obj[31].toString() : "");
                    afiliacionEmpleadores.setMotivoRechazo(obj[32] != null ? obj[32].toString() : "");
                    //afiliacionEmpleadores.setFechaEfectividad(obj[14] != null ? new Date(obj[14].toString()) : null);
                    //afiliacionEmpleadores.setTelefonoMunicipioCausaSalarios(obj[20] != null ? new Long(obj[20].toString()) : null);
                    //afiliacionEmpleadores.setFechaSolicitud(obj[12] != null ? new Date(obj[12].toString()) : null);                        
                    //afiliacionEmpleadores.setEstado(obj[31] != null ? obj[31].toString() : "");                    
                    respuesta.add(afiliacionEmpleadores);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e);
        }
        logger.info("Termina llamado servicios reportarAfiliacionPrimeraVez");
        return respuesta;
    }

    
@Override
public RespuestaEstandar registrarMotivoRechazo(String id, String motivoRechazo){
    RespuestaEstandar respuesta = new RespuestaEstandar();
    List<String> mensajes = new ArrayList<>();
    boolean exitoso = true;
    try {
            String insertString = "UPDATE SAT.WS_AFILIACION_EMPLEADORES_PRIMERA_VEZ SET RESULTADO = 'RECHAZADO/CERRADO', motivoRechazoSolicitud = '" + motivoRechazo + "' WHERE ID = " + id;
            Query query = entityManager.createNativeQuery(insertString);
            int result = query.executeUpdate();
            logger.info(insertString);
            if (result != 1) {
                exitoso = false;
            }
        } catch (Exception ex) {
            exitoso = false;
            //Imprimir error
            logger.info("Ocurrió un error durante el proceso: " + ex.getMessage(), ex);
            System.out.println("Error al ejecutar ACTUALIZACIÓN en la tabla WS_AFILIACION_EMPLEADORES_PRIMERA_VEZ: " + ex.getMessage());
        }
    if (exitoso) {
        respuesta.setEstado("OK");
        mensajes.add("Motivo de rechazo registrado correctamente!");
    } else {
        respuesta.setEstado("ERROR");
        mensajes.add("Ocurrió un error al registrar el motivo de rechazo, por favor revise la información!");
    }
    respuesta.setMensajes(mensajes);
    return respuesta;
}

    
@Override
public RespuestaEstandar enviarASatIndividual(String id,String tokenGeneradoMinisterioSat){
    
    EnvioSatDTO datos = consultarInformacionAfiliacion(id);

    RespuestaEstandar respuesta = enviarInformacionASat(datos,tokenGeneradoMinisterioSat);
    
    Boolean cambiarEstadoEnvio = cambiarEstadoEnvio(id);
    
    return respuesta;
    
}


    @Override
    public String cambiarEstado(Map<String, Object>  informacionGeneralAfiliacion, String resultado){
        
        boolean exitoso = true;
        String respuesta = "";
        String estadoSolicitud = "";
        
        try {
                        
            Object estadoSolicitudObjeto = entityManager.createNativeQuery("select SOLA.saeEstadoSolicitud from solicitud inner join SolicitudAfiliaciEmpleador SOLA on saeSolicitudGlobal = solid where solnumeroradicacion = '" + informacionGeneralAfiliacion.get("numeroRadicado")  + "'").getSingleResult();
            if (estadoSolicitudObjeto != null) {
                    estadoSolicitud = String.valueOf(estadoSolicitudObjeto);
                }
            
            
            String updateString = "UPDATE SAT.WS_AFILIACION_EMPLEADORES_PRIMERA_VEZ SET "
                    + "RADICADO = '" + informacionGeneralAfiliacion.get("numeroRadicado") 
                    + "' ," + "RESULTADO = '" + resultado
                    + "'," + "estadoSolicitud = '" + estadoSolicitud                    
                    + "' WHERE ID = '" + informacionGeneralAfiliacion.get("idWSAfiliacion") + "'";
           
            Query query = entityManager.createNativeQuery(updateString);
          
            int result = query.executeUpdate();
            if (result != 1) {
                exitoso = false;
            }
            
            updateString = "UPDATE SAT.WS_AUDITORIA SET "
                    + "idWSAfiliacion = '" + informacionGeneralAfiliacion.get("idWSAfiliacion") 
                    + "' WHERE numeroRadicado = '" + informacionGeneralAfiliacion.get("numeroRadicado") + "'";
           
            query = entityManager.createNativeQuery(updateString);
          
            result = query.executeUpdate();
            if (result != 1) {
                exitoso = false;
            }                 
      
            
        } catch (Exception ex) {
            exitoso = false;
        }
        
        return respuesta;
        
    }

    
    @Override
    public String insertarAuditoria(Map<String, Object>  informacionGeneralAfiliacion){
        
        boolean exitoso = true;
        String respuesta = "";
        
        try {
             
            String insertString = "INSERT INTO SAT.WS_AUDITORIA (estado, serialSAT, idSolicitudAfiliacionEmpleador, correoElectronicoContacto, tipoDocumentoRepresentanteLegal, idSolicitud, primerNombreRepresentanteLegal, datosRepresentanteLegalSuplente, URLPaginaWeb, responsableAportes, totalUltimaNominaDepto, autorizacionDatosPersonales, fechaConstitucion, tipoDocumentoEmpleador, id, departamentoCausaSalarios, segundoNombreRepresentanteLegal, totalTrabajadores, observacionesAuditoria, segundoNombreEmpleador, codigoPostal, numeroTransaccion, fechaEfectividad, idInstanciaProceso, responsableAfiliacion, periodoUltimaNominaDepto, naturalezaJuridica, primerApellidoEmpleador, mensajeAuditoria, ARL, medioPagoSubsidioMonetario, nombreCajaCompensacion, idEmpleador, radicado, primerApellidoRepresentanteLegal, datosEnvioCorrespondencia, primerNombreEmpleador, idAuditoria, tipoPersona, numeroDocumentoEmpleador, numeroDocumentoRepresentanteLegal, idTarea, direccionContacto, ultimaTareaActiva, numeroMatriculaMercantil, segundoApellidoRepresentanteLegal, autorizacionEnvioCorreoElectronico, perdidaAfiliacionCausaGrave, autorizacionEnvioNotificaciones, estadoAuditoria, segundoApellidoEmpleador, codigoCIIU, datosSucursal, fechaAuditoria, responsableCaja2, fechaSolicitud, responsableCaja1, fechaRecepcionDocumentos, manifestacionNoAfiliadoCaja, direccionNotificacionJudicial, razonSocialEmpleador, descripcionCIIU, municipioContacto, numeroRadicado, idWSAfiliacion, fechaCreacion) VALUES ("
            + "'"  + informacionGeneralAfiliacion.get("estado") + "'"
            + ",'" + informacionGeneralAfiliacion.get("serialSAT") + "'"
            + ",'" + informacionGeneralAfiliacion.get("idSolicitudAfiliacionEmpleador") + "'"
            + ",'" + informacionGeneralAfiliacion.get("correoElectronicoContacto") + "'"
            + ",'" + informacionGeneralAfiliacion.get("tipoDocumentoRepresentanteLegal") + "'"
            + ",'" + informacionGeneralAfiliacion.get("idSolicitud") + "'"
            + ",'" + informacionGeneralAfiliacion.get("primerNombreRepresentanteLegal") + "'"
            + ",'" + informacionGeneralAfiliacion.get("datosRepresentanteLegalSuplente") + "'"
            + ",'" + informacionGeneralAfiliacion.get("URLPaginaWeb") + "'"
            + ",'" + informacionGeneralAfiliacion.get("responsableAportes") + "'"
            + ",'" + informacionGeneralAfiliacion.get("totalUltimaNominaDepto") + "'"
            + ",'" + informacionGeneralAfiliacion.get("autorizacionDatosPersonales") + "'"
            + ",'" + informacionGeneralAfiliacion.get("fechaConstitucion") + "'"
            + ",'" + informacionGeneralAfiliacion.get("tipoDocumentoEmpleador") + "'"
            + ",'" + informacionGeneralAfiliacion.get("id") + "'"
            + ",'" + informacionGeneralAfiliacion.get("departamentoCausaSalarios") + "'"
            + ",'" + informacionGeneralAfiliacion.get("segundoNombreRepresentanteLegal") + "'"
            + ",'" + informacionGeneralAfiliacion.get("totalTrabajadores") + "'"
            + ",'" + informacionGeneralAfiliacion.get("observacionesAuditoria") + "'"
            + ",'" + informacionGeneralAfiliacion.get("segundoNombreEmpleador") + "'"
            + ",'" + informacionGeneralAfiliacion.get("codigoPostal") + "'"
            + ",'" + informacionGeneralAfiliacion.get("numeroTransaccion") + "'"
            + ",'" + informacionGeneralAfiliacion.get("fechaEfectividad") + "'"
            + ",'" + informacionGeneralAfiliacion.get("idInstanciaProceso") + "'"
            + ",'" + informacionGeneralAfiliacion.get("responsableAfiliacion") + "'"
            + ",'" + informacionGeneralAfiliacion.get("periodoUltimaNominaDepto") + "'"
            + ",'" + informacionGeneralAfiliacion.get("naturalezaJuridica") + "'"
            + ",'" + informacionGeneralAfiliacion.get("primerApellidoEmpleador") + "'"
            + ",'" + informacionGeneralAfiliacion.get("mensajeAuditoria") + "'"
            + ",'" + informacionGeneralAfiliacion.get("ARL") + "'"
            + ",'" + informacionGeneralAfiliacion.get("medioPagoSubsidioMonetario") + "'"
            + ",'" + informacionGeneralAfiliacion.get("nombreCajaCompensacion") + "'"
            + ",'" + informacionGeneralAfiliacion.get("idEmpleador") + "'"
            + ",'" + informacionGeneralAfiliacion.get("radicado") + "'"
            + ",'" + informacionGeneralAfiliacion.get("primerApellidoRepresentanteLegal") + "'"
            + ",'" + informacionGeneralAfiliacion.get("datosEnvioCorrespondencia") + "'"
            + ",'" + informacionGeneralAfiliacion.get("primerNombreEmpleador") + "'"
            + ",'" + informacionGeneralAfiliacion.get("idAuditoria") + "'"
            + ",'" + informacionGeneralAfiliacion.get("tipoPersona") + "'"
            + ",'" + informacionGeneralAfiliacion.get("numeroDocumentoEmpleador") + "'"
            + ",'" + informacionGeneralAfiliacion.get("numeroDocumentoRepresentanteLegal") + "'"
            + ",'" + informacionGeneralAfiliacion.get("idTarea") + "'"
            + ",'" + informacionGeneralAfiliacion.get("direccionContacto") + "'"
            + ",'" + informacionGeneralAfiliacion.get("ultimaTareaActiva") + "'"
            + ",'" + informacionGeneralAfiliacion.get("numeroMatriculaMercantil") + "'"
            + ",'" + informacionGeneralAfiliacion.get("segundoApellidoRepresentanteLegal") + "'"
            + ",'" + informacionGeneralAfiliacion.get("autorizacionEnvioCorreoElectronico") + "'"
            + ",'" + informacionGeneralAfiliacion.get("perdidaAfiliacionCausaGrave") + "'"
            + ",'" + informacionGeneralAfiliacion.get("autorizacionEnvioNotificaciones") + "'"
            + ",'" + informacionGeneralAfiliacion.get("estadoAuditoria") + "'"
            + ",'" + informacionGeneralAfiliacion.get("segundoApellidoEmpleador") + "'"
            + ",'" + informacionGeneralAfiliacion.get("codigoCIIU") + "'"
            + ",'" + informacionGeneralAfiliacion.get("datosSucursal") + "'"
            + ",'" + informacionGeneralAfiliacion.get("fechaAuditoria") + "'"
            + ",'" + informacionGeneralAfiliacion.get("responsableCaja2") + "'"
            + ",'" + informacionGeneralAfiliacion.get("fechaSolicitud") + "'"
            + ",'" + informacionGeneralAfiliacion.get("responsableCaja1") + "'"
            + ",'" + informacionGeneralAfiliacion.get("fechaRecepcionDocumentos") + "'"
            + ",'" + informacionGeneralAfiliacion.get("manifestacionNoAfiliadoCaja") + "'"
            + ",'" + informacionGeneralAfiliacion.get("direccionNotificacionJudicial") + "'"
            + ",'" + informacionGeneralAfiliacion.get("razonSocialEmpleador") + "'"
            + ",'" + informacionGeneralAfiliacion.get("descripcionCIIU") + "'"
            + ",'" + informacionGeneralAfiliacion.get("municipioContacto") + "'"
            + ",'" + informacionGeneralAfiliacion.get("numeroRadicado") + "'"
            + ",'" + informacionGeneralAfiliacion.get("idWSAfiliacion") + "'"                    
            + ",GETDATE()"
            + ")";
           
            Query query = entityManager.createNativeQuery(insertString);
          
            int result = query.executeUpdate();
            if (result != 1) {
                exitoso = false;
            }
        } catch (Exception ex) {
            exitoso = false;
        }
        
        return respuesta;
        
    }

     /**
     * Metodo encargado de consulta una persona por tipo y numero de ID
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     */
    private String consultarPersonaPorTipoNumeroID(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion) {
        List<Persona> personas = (List<Persona>) entityManager
                .createNativeQuery("select per.perId from Persona per " +
"           where per.perNumeroIdentificacion = :cedula " +
"           and  per.perTipoIdentificacion = :tipoIdentificacion ")
                .setParameter("tipoIdentificacion", tipoIdentificacion.name())
                .setParameter("cedula", numeroIdentificacion)
                .getResultList();
        if (!personas.isEmpty()) {
            return String.valueOf(personas.get(0));
        } else {
            return null;
        }
    }
     public  String obtenerDominio(String url) {
        String dominioHasta = ".gov.co";
        
        // Verificamos si contiene .gov.co
        int indice = url.indexOf(dominioHasta);
        if (indice != -1) {
            // Tomamos hasta el final de .gov.co
            int finalDominio = indice + dominioHasta.length();
            return url.substring(0, finalDominio + 1); // +1 para incluir el "/"
        } else {
            return "DOMINIO_NO_ENCONTRADO";
        }
    }
    
public RespuestaEstandar enviarInformacionASat(EnvioSatDTO datos,String tokenGenerado){
        RespuestaEstandar respuesta = new RespuestaEstandar();
        String respuestaString = "";

    if (tokenGenerado == null || tokenGenerado.trim().isEmpty()) {
        logger.warn("El token generado es nulo o vacío.");
    }

    String url = "";
    String status = "ERROR"; // Asumimos error por defecto
    String requestJson = "";
    String jsonResult = "";

    // Consulta de URL SAT
    try {
        Object urlSAT = entityManager
            .createNativeQuery("SELECT TRAN_URL FROM SAT.WS_TRANSACCION WHERE TRAN_NUME_TRAN = :numeroTransaccion")
            .setParameter("numeroTransaccion", datos.getNumTransaccion())
            .getSingleResult();

        if (urlSAT != null) {
            url = obtenerDominio(urlSAT.toString());
            if (url.equals("DOMINIO_NO_ENCONTRADO")) {
                return respuesta;
            }
        } else {
            logger.warn("No se encontró URL para la transacción: {}"+ datos.getNumTransaccion());
        }
    } catch (NoResultException nre) {
        logger.error("No se encontró resultado para la transacción: {}"+ datos.getNumTransaccion(), nre);
    } catch (Exception e) {
        logger.error("Error al consultar la URL del SAT: ", e);
    }

        Map<String, Object> datosParaJson = new HashMap<>();
        List<String> mensajes = new ArrayList<>();
        datosParaJson.put("NumeroTransaccion", datos.getNumTransaccion());
        datosParaJson.put("TipoDocumentoEmpleador", datos.getTipoDocIdEmpleador());
        datosParaJson.put("NumeroDocumentoEmpleador", datos.getNumDocIdEmpleador() != null ? datos.getNumDocIdEmpleador().replaceAll("\\s+", "") : "");
        datosParaJson.put("SerialSat", datos.getSerialSatOrgTerritoriales());
        datosParaJson.put("ResultadoTramite", datos.getResultadoTramite());
        datosParaJson.put("FechaEfectivaAfiliacion", datos.getFechaEfectivaAfiliacion());
        datosParaJson.put("MotivoRechazo", datos.getMotivoRechazo());
        datosParaJson.put("NumeroRadicadoSolicitud", datos.getNumRadicado());
      //  String json = JsonUtil.toJson(datosParaJson);
        

        if(url.equals("")){
           return respuesta; 
        }else{
            url = url + "ResponderSolicitudAfiliacionCcfPrimeravez";
        }
            // Preparar JSON y enviar petición HTTP
        try {
            requestJson = JsonUtil.toJson(datosParaJson);
            logger.info("JSON a enviar: {}"+ requestJson);
            logger.info("Enviando petición HTTP a URL: {}"+ url);
            jsonResult = HttpUtil.sendHttpPostJSON(url, requestJson, tokenGenerado);
        } catch (Exception e) {
            logger.error("Error al realizar la petición HTTP: ", e);
        }

       // String url = "https://sat-pruebas.onrender.com/enviarSATindividual";

            if (jsonResult != null) {
                logger.info("Respuesta del SAT: {}"+ jsonResult);   
                try {
                    Map<String, Object> jsonObject = JsonUtil.parseJson(jsonResult);
                    respuestaString = jsonObject.get("mensaje").toString();               
                } catch (Exception e) {
                    logger.error("Error al procesar el JSON: " + e.getMessage());    
                    e.printStackTrace();
                }
            } else {
                logger.error("No se pudo obtener el JSON.");
            }
            mensajes.add(respuestaString);
            respuesta.setMensajes(mensajes);
            return respuesta;
}
public EnvioSatDTO consultarInformacionAfiliacion(String id){
    EnvioSatDTO datos = new EnvioSatDTO();
            try{
            String sq = "SELECT  numerotransaccion, tipoDocumentoEmpleador, numeroDocumentoEmpleador, serialSAT, " +
                "(SELECT CASE sol.solResultadoProceso " +
                "  WHEN 'APROBADA' THEN '1' " +
                "  WHEN 'NO_CONFORME_SUBSANABLE' THEN '2' " +
                "  WHEN 'NO_CONFORME_EN_GESTION' THEN '2' " +
                "  WHEN 'NO_CONFORME_GESTIONADA' THEN '2' " +
                "  WHEN 'RECHAZADA' THEN '2' " +
                "  WHEN 'DESISTIDA' THEN '2' " +
                "  WHEN 'CANCELADA' THEN '2' " +
                "  ELSE 'NO APLICA' END) AS resultado, " +
                "CASE WHEN sol.solResultadoProceso = 'APROBADA' THEN CONVERT(varchar(40), empFechaCambioEstadoAfiliacion, 23) ELSE NULL END AS FechaAfiliacion, " +
                "CASE WHEN motivoRechazoSolicitud IS NULL THEN 'ANULADO' ELSE motivoRechazoSolicitud END AS motivoRechazoSolicitud, " +
                "solnumeroradicacion " +
                "FROM SAT.WS_AFILIACION_EMPLEADORES_PRIMERA_VEZ " +
                "LEFT JOIN solicitud sol ON sol.solNumeroRadicacion = radicado " +
                "LEFT JOIN SolicitudAfiliaciEmpleador sola ON sola.saeSolicitudGlobal = sol.solid " +
                "LEFT JOIN empleador ON empid = saeEmpleador " +
                "WHERE id = " + id;

                Query query = entityManager.createNativeQuery(sq);
                Integer numero = 0;
                List<Object[]> listado = query.getResultList();            
                for(Object[] obj:listado){
                    try{
                        datos.setNumTransaccion(obj[0] != null ? obj[0].toString() : "");
                        datos.setTipoDocIdEmpleador(obj[1] != null ? obj[1].toString() : "");
                        datos.setNumDocIdEmpleador(obj[2] != null ? obj[2].toString() : "");
                        datos.setSerialSatOrgTerritoriales(obj[3] != null ? obj[3].toString() : "");
                        datos.setResultadoTramite(obj[4] != null ? obj[4].toString() : "");
                        datos.setFechaEfectivaAfiliacion(obj[5] != null ? obj[5].toString() : "");
                        datos.setMotivoRechazo(obj[6] != null ? obj[6].toString() : "");
                        datos.setNumRadicado(obj[7] != null ? obj[7].toString() : "");
                    }catch(Exception e){
                        e.printStackTrace();
                        logger.error(e);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                logger.error(e);
            }
            return datos;
    }

    
 public Boolean cambiarEstadoEnvio(String id)
 {
     
    String updateString = "update SAT.WS_AFILIACION_EMPLEADORES_PRIMERA_VEZ set resultado = (\n" +
        "\n" +
        "                 SELECT CASE sola.saeEstadoSolicitud \n" +
        "                   When 'INSTANCIADA' Then 'ASIGNADO AL BACK'\n" +
        "                   When 'RADICADA' Then 'ASIGNADO AL BACK'\n" +
        "                   When 'ASIGNADA_AL_BACK' Then 'ASIGNADO AL BACK'\n" +
        "                   When 'PENDIENTE_ENVIO_AL_BACK' Then 'ASIGNADO AL BACK'\n" +
        "                   When 'PRE_RADICADA' Then 'ASIGNADO AL BACK'\n" +
        "                   When 'EN_ANALISIS_ESPECIALIZADO' Then 'ASIGNADO AL BACK'\n" +
        "                   When 'APROBADA' Then 'APROBADO/CERRADO'\n" +
        "                   When 'CERRADA' Then 'APROBADO/CERRADO'\n" +
        "                   When 'NO_CONFORME_SUBSANABLE' Then 'RECHAZADO/CERRADO'\n" +
        "                   When 'NO_CONFORME_EN_GESTION' Then 'RECHAZADO/CERRADO'\n" +
        "                   When 'NO_CONFORME_GESTIONADA' Then 'RECHAZADO/CERRADO'\n" +
        "                   When 'RECHAZADA' Then 'RECHAZADO/CERRADO'\n" +
        "                   When 'DESISTIDA' Then 'RECHAZADO/CERRADO'\n" +
        "                   When 'CANCELADA' Then 'RECHAZADO/CERRADO'\n" +
        "                   ELSE 'NO APLICA.'\n" +
        "                   END             \n" +
        "FROM SAT.WS_AFILIACION_EMPLEADORES_PRIMERA_VEZ \n" +
        "left join solicitud sol on sol.solNumeroRadicacion = radicado\n" +
        "left join SolicitudAfiliaciEmpleador sola on sola.saeSolicitudGlobal = sol.solid \n" +
        "where id = '" + id + "'"+
        ")\n" +
        "where id = '" + id + "'";
           
    Query query = entityManager.createNativeQuery(updateString);
          
    int result = query.executeUpdate();
     
     
    updateString = "UPDATE SAT.WS_AFILIACION_EMPLEADORES_PRIMERA_VEZ SET "
                    + "RESULTADO = CONCAT('ENVIADO A SAT - ',RESULTADO)"
                    + " WHERE ID = '" + id + "'";
           
    query = entityManager.createNativeQuery(updateString);
          
    result = query.executeUpdate();
    
    return result == 1;
    
 }
 
	/**
     * Metodo que invoca el clinte ConsultarListasValores de la clase ListasBusiness
     *
     * @param idsListaValores el id de la lista de valores por el que se va a buscar
     * @return una lista de ElementoListaDTO
     */
    private List<ElementoListaDTO> consultarListasValores(List<Integer> idsListaValores) {
        logger.debug("Inicia consultarListasValores (List<Integer> idsListaValores)");
        /* Se instancia el cliente */
        ConsultarListasValores listasValores = new ConsultarListasValores(idsListaValores);
        listasValores.execute();
        /* Se almacena la informacion de consultarListasValores */
        logger.debug("Finaliza consultarListasValores (List<Integer> idsListaValores)");
        return listasValores.getResult();
    }

}

