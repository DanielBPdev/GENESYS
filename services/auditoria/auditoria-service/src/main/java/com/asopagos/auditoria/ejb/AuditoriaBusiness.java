package com.asopagos.auditoria.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.hibernate.envers.RevisionType;
import com.asopagos.auditoria.constants.NamedQueriesConstants;
import com.asopagos.auditoria.dto.ParametrizacionTablaAuditableDTO;
import com.asopagos.auditoria.dto.RevisionDTO;
import com.asopagos.auditoria.service.AuditoriaService;
import com.asopagos.entidades.auditoria.ParametrizacionTablaAuditable;
import com.asopagos.entidades.auditoria.RevisionEntidad;
import com.asopagos.entidades.enumeraciones.auditoria.TipoOperacionEnum;
import com.asopagos.pagination.QueryBuilder;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */
@Stateless
public class AuditoriaBusiness implements AuditoriaService {

    @PersistenceContext(unitName = "Aud_PU")
    private EntityManager entityManager;

    @EJB
    TablasAuditablesManager tablasAuditablesManager;

    /**
     * Funcion que retorna la enumeracion de RevisionType
     * asociada a la enumeracion dada por parametro
     * @param TipoOperacionEnum
     *        tipoOperacion
     * @return RevisionType
     */
    protected RevisionType getRevisionType(TipoOperacionEnum tipoOperacion) {

        RevisionType revision = null;

        switch (tipoOperacion.toString()) {

            case "CREACION":
                revision = RevisionType.ADD;
                break;

            case "ACTUALIZACION":
                revision = RevisionType.MOD;
                break;

            case "ELIMINACION":
                revision = RevisionType.DEL;
                break;

            case "CONSULTA":
                revision = null;
                break;
        }
        return revision;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.asopagos.auditoria.service.AuditoriaService#consultarLog(java.lang.String, java.lang.Long, java.lang.Long,
     *      org.hibernate.envers.RevisionType)
     */
    @Override
    public List<RevisionDTO> consultarLog(String usuario, Long fechaInicio, Long fechaFin, TipoOperacionEnum tipoOperacion,
            List<String> tablas, @Context UriInfo uri, @Context HttpServletResponse response) {
        Query query = null;
        QueryBuilder queryBuilder = new QueryBuilder(entityManager, uri, response);
        List<RevisionDTO> logs = new ArrayList<>();
        RevisionType revision = this.getRevisionType(tipoOperacion);
        List<String> rutaQualificadaTablas = new ArrayList<>();
        //Tablas auditables
        Map<String, String> tablasAuditables = tablasAuditablesManager.getListaTablasAuditables();
        //Se obtienen las rutas cualificadas para consultar
        for (String nombreTabla : tablas) {
            if (tablasAuditables.get(nombreTabla) != null) {
                rutaQualificadaTablas.add(tablasAuditables.get(nombreTabla));
            }
        }
        queryBuilder.addParam(ConstantesAuditoria.NOMBRE_USUARIO, usuario);
        queryBuilder.addParam(ConstantesAuditoria.FECHA_INICIO, fechaInicio);
        queryBuilder.addParam(ConstantesAuditoria.FECHA_FIN, fechaFin);
        queryBuilder.addParam(ConstantesAuditoria.TABLAS, rutaQualificadaTablas);
        
        //Si la revision es nula quiere decir que se consultara solo por revision de consulta
        queryBuilder.addParam(ConstantesAuditoria.TIPO_OPERACION, revision);
        
        //si el tipo de revision es de agregado
        if(revision.equals(RevisionType.ADD)){
        
            query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_LOG_CREAR, NamedQueriesConstants.CONSULTAR_LOG_AGREGAR_TOTAL);
        
        //si el tipo de revision es de modificacion
        }else if(revision.equals(RevisionType.MOD)){
            
            query = queryBuilder.createQuery(NamedQueriesConstants.CONSULTAR_LOG_ACTUALIZAR, NamedQueriesConstants.CONSULTAR_LOG_ACTUALIZAR_TOTAL);
        }
        
        List<RevisionEntidad> logsAuditoriaEntidad = (List<RevisionEntidad>) query.getResultList();
        if (logsAuditoriaEntidad != null && !logsAuditoriaEntidad.isEmpty()) {
            for (RevisionEntidad logAuditoriaEntidad : logsAuditoriaEntidad) {
                RevisionDTO dto = RevisionDTO.convertToDTO(logAuditoriaEntidad);
                logs.add(dto);
            }
        }
        return logs;
    }

    /**
     * Consultar en tablas auditables
     * @see com.asopagos.auditoria.service.AuditoriaService#actualizarTablasAuditables(java.util.List)
     */
    @Override
    public void actualizarTablasAuditables(List<ParametrizacionTablaAuditableDTO> tablasAuditablesIn) {
        if (tablasAuditablesIn != null && !tablasAuditablesIn.isEmpty()) {
            Map<String, String> tablasAuditables = tablasAuditablesManager.getListaTablasAuditables();
            for (ParametrizacionTablaAuditableDTO tablaAuditableDTO : tablasAuditablesIn) {
                if (tablasAuditables.get(tablaAuditableDTO.getNombreTabla()) != null) {
                    entityManager.createNamedQuery(NamedQueriesConstants.ACTUALIZAR_TABLA_AUDITABLE)
                            .setParameter(ConstantesAuditoria.NOMBRE_TABLA, tablaAuditableDTO.getNombreTabla())
                            .setParameter(ConstantesAuditoria.CONSULTAR, tablaAuditableDTO.getConsultar())
                            .setParameter(ConstantesAuditoria.CREAR, tablaAuditableDTO.getCrear())
                            .setParameter(ConstantesAuditoria.ACTUALIZAR, tablaAuditableDTO.getActualizar()).executeUpdate();

                }
            }
        }
    }

    /**
     * Consulta de la informacion de eventos de auditoria
     * @see com.asopagos.auditoria.service.AuditoriaService#consultarTablasAuditables(java.lang.String, java.lang.String)
     */
    @Override
    public List<ParametrizacionTablaAuditableDTO> consultarOperacionesAuditoria(List<String> tablasConsultar) {
        List<ParametrizacionTablaAuditable> eventosAuditoria = null;
        List<ParametrizacionTablaAuditableDTO> eventosAuditoriaDTO = new ArrayList<>();
        if (tablasConsultar != null && !tablasConsultar.isEmpty()) {
            eventosAuditoria = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_EVENTOS_AUDITORIA, ParametrizacionTablaAuditable.class)
                    .setParameter(ConstantesAuditoria.LISTA_TABLAS, tablasConsultar).getResultList();
        }
        else {
            eventosAuditoria = entityManager.createNamedQuery(NamedQueriesConstants.CONSULTAR_TODOS_EVENTOS_AUDITORIA, ParametrizacionTablaAuditable.class)
                    .getResultList();
        }
        if (eventosAuditoria != null && !eventosAuditoria.isEmpty()) {
            for (ParametrizacionTablaAuditable eventoAuditoria : eventosAuditoria) {
                eventosAuditoriaDTO.add(ParametrizacionTablaAuditableDTO.convertToDTO(eventoAuditoria));
            }
        }
        return eventosAuditoriaDTO;
    }

    /**
     * Listado de las tablas auditables
     * @see com.asopagos.auditoria.service.AuditoriaService#listarTablasAuditables()
     */
    @Override
    public Set<String> listarTablasAuditables() {

        return tablasAuditablesManager.getListaTablasAuditables().keySet();
    }

}
