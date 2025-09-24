package com.asopagos.auditoria.ejb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import org.hibernate.envers.Audited;
import com.asopagos.auditoria.constants.NamedQueriesConstants;
import com.asopagos.entidades.auditoria.ParametrizacionTablaAuditable;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

@Startup
@Singleton
public class TablasAuditablesManager {

    @PersistenceContext(unitName = "Aud_PU")
    private EntityManager entityManager;

    @PersistenceContext(unitName = "Base_PU")
    private EntityManager entityManagerBase;

    private Map<String, String> listaTablasAuditables = new TreeMap<>();

    private static final ILogger logger = LogManager.getLogger(TablasAuditablesManager.class);
    
    @PostConstruct
    public void limpiarTablasAuditables() {

        loadAuditedTables();

        Map<String, String> tablas = CacheTablasAuditables.getInstance().getListaTablasAuditables();

        if (tablas != null && !tablas.isEmpty()) {

            Set<String> tableNames = tablas.keySet();

            Set<String> tablasEventos = new HashSet<String>();

            entityManager.createNamedQuery(NamedQueriesConstants.ELIMINAR_TABLAS_INNECESARIAS)
                    .setParameter(ConstantesAuditoria.LISTA_TABLAS_AUDITABLES, tableNames).executeUpdate();

            List<ParametrizacionTablaAuditable> eventosAuditoria = entityManager
                    .createNamedQuery(NamedQueriesConstants.CONSULTAR_TODOS_EVENTOS_AUDITORIA, ParametrizacionTablaAuditable.class)
                    .getResultList();

            if (eventosAuditoria != null && !eventosAuditoria.isEmpty()) {
                for (ParametrizacionTablaAuditable eventoAuditoria : eventosAuditoria) {
                    tablasEventos.add(eventoAuditoria.getNombreTabla());
                }
            }

            tableNames.removeAll(tablasEventos);

            List<String> listaTablasNuevas = new ArrayList<String>(tableNames);

            for (String tablaNueva : listaTablasNuevas) {

                ParametrizacionTablaAuditable tablaAuditable = new ParametrizacionTablaAuditable();
                tablaAuditable.setEntityClassName(tablas.get(tablaNueva));
                tablaAuditable.setNombreTabla(tablaNueva);
                tablaAuditable.setActualizar(false);
                tablaAuditable.setConsultar(false);
                tablaAuditable.setCrear(false);
                entityManager.persist(tablaAuditable);
            }

            loadAuditedTables();
        }
    }

    private void loadAuditedTables() {
        Metamodel metaModel = entityManagerBase.getMetamodel();
        Set<EntityType<?>> entidades = metaModel.getEntities();
        Map<String, String> tablasAuditables = new TreeMap<>();
        if (entidades != null && !entidades.isEmpty()) {
            for (EntityType<?> entityType : entidades) {
                Class clase = entityType.getJavaType();
                if (clase != null && clase.isAnnotationPresent(Table.class)) {
                    Table tabla = (Table) clase.getAnnotation(Table.class);
                    if (clase != null && clase.isAnnotationPresent(Audited.class)) {
                        tablasAuditables.put(tabla.name(), clase.getName());
                    }
                }
            }
        }
        logger.debug("Tablas auditables: " + tablasAuditables);
        listaTablasAuditables = tablasAuditables;
    }

    /**
     * @return the listaTablasAuditables
     */
    public Map<String, String> getListaTablasAuditables() {
        return listaTablasAuditables;
    }

}