package com.asopagos.listaschequeo.composite.ejb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.ejb.Stateless;
import com.asopagos.dto.ItemChequeoDTO;
import com.asopagos.dto.ListaChequeoDTO;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.listaschequeo.clients.ActualizarRequisitosCajaClasificacion;
import com.asopagos.listaschequeo.clients.ConsultarListaChequeo;
import com.asopagos.listaschequeo.clients.ConsultarRequisitosListaChequeo;
import com.asopagos.listaschequeo.clients.CrearRequisitosCajaClasificacion;
import com.asopagos.listaschequeo.composite.service.RequisitosCompositeService;
import com.asopagos.listaschequeo.dto.ConsultarRequisitosListaChequeoOutDTO;
import com.asopagos.listaschequeo.dto.RequisitoCajaClasificacionDTO;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.security.dto.UserDTO;
import com.asopagos.usuarios.clients.ConsultarIdsPorNombresGrupos;

/**
 * <b>Descripción:</b> EJB que implementa los métodos de negocio relacionados 
 * con la gestión de requisitos para la afiliación de empleadores
 * <b>Historia de Usuario:</b> HU-TRA-061 Administración general de listas de
 * chequeo
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@Stateless
public class RequisitosCompositeBusiness implements RequisitosCompositeService {
    
    /**
     * Referencia al logger
     */
    private final ILogger logger = LogManager.getLogger(RequisitosCompositeBusiness.class);

	/**
     * Método encargado de registar los datos en la
     * tabla RequisitoCajaClasificacion
     * 
     * @param requisitosCajaClasificacion
     * 				objeto con los datos a registrar
     */
    @Override
    public void guardarCrearRequisitosCajaClasificacion(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion) {
        logger.debug("Inicia  guardarCrearRequisitosCajaClasificacion(List<RequisitoCajaClasificacionDTO>)");
        
        obtenerListaGrupos(requisitosCajaClasificacion);
        
        //Se ejecuta el servicio CrearRequisitosCajaClasificacion para crear el RequisitoCajaClasificacionDTO 
        //con sus grupos
        CrearRequisitosCajaClasificacion crearRequisitosCajaClasificacion = new CrearRequisitosCajaClasificacion(
                requisitosCajaClasificacion);
        crearRequisitosCajaClasificacion.execute();
        
        logger.debug("Finaliza guardarCrearRequisitosCajaClasificacion(List<RequisitoCajaClasificacionDTO>)");
    }

    /**
     * Método encargado de actualizar los datos en la
     * tabla RequisitoCajaClasificacion
     * 
     * @param requisitosCajaClasificacion
     *        objeto con los datos a registrar
     */
    @Override
    public void guardarActualizarRequisitosCajaClasificacion(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion) {
        logger.debug("Inicia guardarActualizarRequisitosCajaClasificacion(List<RequisitoCajaClasificacionDTO>)");
        
        obtenerListaGrupos(requisitosCajaClasificacion);

        ActualizarRequisitosCajaClasificacion actualizarRequisitosCajaClasificacion = new ActualizarRequisitosCajaClasificacion(
                requisitosCajaClasificacion);
        actualizarRequisitosCajaClasificacion.execute();
        
        logger.debug("Finaliza guardarActualizarRequisitosCajaClasificacion(List<RequisitoCajaClasificacionDTO>)");
    }

    private List<RequisitoCajaClasificacionDTO> obtenerListaGrupos(List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion) {
        logger.debug("Inicia obtenerListaGrupos(List<RequisitoCajaClasificacionDTO>)");
        List<String> nombresGruposUsuarios = new ArrayList<>();
        List<String> gruposUsuarios;

        // Se recorre la lista de RequisitoCajaClasificacionDTO para obtener 
        // los nombres de la lista grupoUsuarios que tiene cada RequisitoCajaClasificacionDTO
        for (RequisitoCajaClasificacionDTO requisitoCajaClasificacionDTO : requisitosCajaClasificacion) {
            gruposUsuarios = requisitoCajaClasificacionDTO.getGrupoUsuario();
            for (String grupoUsuario : gruposUsuarios) {
                nombresGruposUsuarios.add(grupoUsuario);
            }
        }

        // Se ejecuta el servicio ConsultarIdsPorNombresGrupos mandando los nombres de los grupos usuarios
        // y retorna los ids de los mismos
        ConsultarIdsPorNombresGrupos obtenerIdsGruposUsuarios = new ConsultarIdsPorNombresGrupos(nombresGruposUsuarios);
        obtenerIdsGruposUsuarios.execute();
        Map<String, String> idsGruposUsuarios = obtenerIdsGruposUsuarios.getResult();
        List<String> nuevosGruposUsuarios;

        // Se recorre nuevamente la lista de RequisitoCajaClasificacionDTO para settear los ids obtenidos
        // con el servicio ConsultarIdsPorNombresGrupos a la lista de gruposUsuarios de RequisitoCajaClasificacionDTO
        for (RequisitoCajaClasificacionDTO requisitoCajaClasificacionDTO : requisitosCajaClasificacion) {
            nuevosGruposUsuarios = new ArrayList<>();

            for (String grupoUsuario : requisitoCajaClasificacionDTO.getGrupoUsuario()) {
                if (idsGruposUsuarios.containsKey(grupoUsuario)) {
                    nuevosGruposUsuarios.add(idsGruposUsuarios.get(grupoUsuario));
                }
            }
            requisitoCajaClasificacionDTO.setGrupoUsuario(nuevosGruposUsuarios);
        }
        logger.debug("Finaliza obtenerListaGrupos(List<RequisitoCajaClasificacionDTO>)");
        return requisitosCajaClasificacion;
    }
    
    public List<ListaChequeoDTO> consultarListaChequeo(Long idSolicitud, TipoTransaccionEnum tipoTransaccion, ClasificacionEnum clasificacion, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, UserDTO userDTO){
        logger.debug("Inicia consultarListaChequeo(Long, TipoIdentificacionEnum, String, UserDTO)");
        List<String> grupos = new ArrayList<>();

        if (userDTO.getGrupos() != null && !userDTO.getGrupos().isEmpty()) {
            grupos.addAll(userDTO.getGrupos());
        } 
        
        ConsultarListaChequeo consultarListaChequeo = new ConsultarListaChequeo(clasificacion, idSolicitud, numeroIdentificacion, tipoIdentificacion, grupos);
        consultarListaChequeo.execute();
        
        ConsultarRequisitosListaChequeo consultarRequisitosListaChequeo = new ConsultarRequisitosListaChequeo(clasificacion, numeroIdentificacion, tipoIdentificacion, tipoTransaccion, grupos);
        consultarRequisitosListaChequeo.execute();
        
        if(consultarListaChequeo.getResult()!=null && !consultarListaChequeo.getResult().isEmpty()&&
                consultarRequisitosListaChequeo.getResult()!=null && !consultarRequisitosListaChequeo.getResult().isEmpty()){
            for (ListaChequeoDTO listaChequeoDTO : consultarListaChequeo.getResult()) {
                for (ItemChequeoDTO itemChequeoDTO : listaChequeoDTO.getListaChequeo()) {
                    for (ConsultarRequisitosListaChequeoOutDTO requisitosListaChequeo : consultarRequisitosListaChequeo.getResult()) {
                        if (itemChequeoDTO.getIdRequisito() == requisitosListaChequeo.getIdRequisito()) {
                            itemChequeoDTO.setIdentificadorDocumentoPrevio(requisitosListaChequeo.getIdentificadorDocumentoPrevio());
                        }
                    }
                }
            }
        }
        logger.debug("Finaliza consultarListaChequeo(Long, TipoIdentificacionEnum, String, UserDTO)");
        return consultarListaChequeo.getResult();
    }
    
    public List<ConsultarRequisitosListaChequeoOutDTO> consultarRequisitosListaChequeo(TipoTransaccionEnum tipoTransaccion, ClasificacionEnum clasificacion,
             TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, UserDTO userDTO){
        logger.debug("Inicia consultarListaChequeo(Long, TipoIdentificacionEnum, String, UserDTO)");
        List<String> grupos = new ArrayList<>();

        if (userDTO.getGrupos() != null && !userDTO.getGrupos().isEmpty()) {
            grupos.addAll(userDTO.getGrupos());
        } 
        
        ConsultarRequisitosListaChequeo consultarRequisitosListaChequeo = new ConsultarRequisitosListaChequeo(clasificacion, numeroIdentificacion, tipoIdentificacion, tipoTransaccion, grupos);
        consultarRequisitosListaChequeo.execute();
        
        logger.debug("Finaliza consultarListaChequeo(Long, TipoIdentificacionEnum, String, UserDTO)");
        return consultarRequisitosListaChequeo.getResult();
    }
}
