package com.asopagos.jpa;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import org.apache.commons.beanutils.PropertyUtils;
import com.asopagos.constants.ConsultasDinamicasConstants;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.CalendarUtils;

/**
 * Clase que contiene métodos utilitarios para operaciones de JPA
 * 
 * @author sbrinez
 */
public class JPAUtils {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(JPAUtils.class);

    /**
     * Método que ejecuta actualizaciones de los campos complejos de una entidad
     * 
     * @param <T>
     * @param em
     * @param claseEntidad
     * @param entidad
     * @param ignoreFields
     *        Lista de campos de la entidad a ser ignarados
     */
    public static <T extends Serializable> void actualizarEntidadCamposComplejos(EntityManager em, Class<T> claseEntidad, T entidad,
            List<String> ignoreFields) {

        try {
            Metamodel meta = em.getMetamodel();
            EntityType<T> entityModel = meta.entity(claseEntidad);
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaUpdate<T> update = cb.createCriteriaUpdate(claseEntidad);
            Root entityRoot = update.from(claseEntidad);
            String nombreAtributo;
            String nombreCampoId = entityModel.getId(Long.class).getName();
            boolean actualizar = false;
            for (Attribute<? super T, ?> atributo : entityModel.getAttributes()) {
                nombreAtributo = atributo.getName();
                if ((ignoreFields == null || !ignoreFields.contains(nombreAtributo))
                        && atributo.getPersistentAttributeType() != Attribute.PersistentAttributeType.BASIC
                        && !nombreAtributo.equals(nombreCampoId)) {
                    update.set(nombreAtributo, PropertyUtils.getProperty(entidad, nombreAtributo));
                    actualizar = true;
                }
            }
            if (actualizar) {
                update.where(cb.equal(entityRoot.get(nombreCampoId), PropertyUtils.getProperty(entidad, nombreCampoId)));
                em.createQuery(update).executeUpdate();
                em.flush();
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            logger.error(ex.getMessage(), ex);
            throw new TechnicalException(ex.getMessage(), ex);
        }
    }

    public static <T extends Serializable> List<T> consultaEntidad(EntityManager entityManager, Class<T> claseEntidad,
            Map<String, String> fieldOperator, Map<String, Object> fieldValue) {
        return consultaEntidad(entityManager, claseEntidad, fieldOperator, fieldValue, Long.class);
    }

    public static <T extends Serializable> List<T> consultaEntidad(EntityManager entityManager, Class<T> claseEntidad,
            Map<String, String> fieldOperator, Map<String, Object> fieldValue, Class idClass) {
        Metamodel meta = entityManager.getMetamodel();
        EntityType<T> entityModel = meta.entity(claseEntidad);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(claseEntidad);
        Root<T> per = cq.from(claseEntidad);
        String nombreCampoId = entityModel.getId(idClass).getName();
        List<Predicate> predicates = new ArrayList<>();
        if (fieldOperator != null && !fieldOperator.isEmpty() && fieldValue != null && !fieldValue.isEmpty()) {
            Set<String> campos = fieldOperator.keySet();
            for (Attribute<? super T, ?> atributo : entityModel.getAttributes()) {
                String nombreAtributo = atributo.getName();
                if (fieldOperator != null && campos.contains(nombreAtributo)) {
                    Predicate p = null;
                    switch (fieldOperator.get(nombreAtributo)) {
                        case ConsultasDinamicasConstants.IGUAL:
                            p = cb.equal(per.get(entityModel.getSingularAttribute(nombreAtributo, atributo.getJavaType())),
                                    fieldValue.get(nombreAtributo));

                            //p= cb.equal(per.get(nombreAtributo), fieldValue.get(nombreAtributo));
                            predicates.add(p);
                            break;

                        case ConsultasDinamicasConstants.LIKE:
                            p = cb.like(per.get(nombreAtributo), "%" + fieldValue.get(nombreAtributo) + "%");
                            predicates.add(p);
                            break;

                        case ConsultasDinamicasConstants.MAYOR:
                            p = cb.greaterThan(per.get(nombreAtributo), (Comparable) fieldValue.get(nombreAtributo));
                            predicates.add(p);
                            break;

                        case ConsultasDinamicasConstants.MENOR:
                            p = cb.lessThan(per.get(nombreAtributo), (Comparable) fieldValue.get(nombreAtributo));
                            predicates.add(p);
                            break;

                        case ConsultasDinamicasConstants.MAYOR_IGUAL:
                            p = cb.greaterThanOrEqualTo(per.get(nombreAtributo), (Comparable) fieldValue.get(nombreAtributo));
                            predicates.add(p);
                            break;

                        case ConsultasDinamicasConstants.MENOR_IGUAL:
                            p = cb.lessThanOrEqualTo(per.get(nombreAtributo), (Comparable) fieldValue.get(nombreAtributo));
                            predicates.add(p);
                            break;
                            
                        case ConsultasDinamicasConstants.DIFERENTE:
                            p = cb.notEqual(per.get(nombreAtributo), (Comparable) fieldValue.get(nombreAtributo));
                            predicates.add(p);
                            break;
                        
                        case ConsultasDinamicasConstants.IN:
                            In<Object> in = cb.in(per.get(nombreAtributo));
                            List<Object> values = (List<Object>) fieldValue.get(nombreAtributo);
                            for (Object value : values) {
                                in = in.value(value);
                            }
                            p = in;
                            predicates.add(p);
                            break;
                            
                        case ConsultasDinamicasConstants.BETWEEN:
                            p = cb.between(per.get(nombreAtributo),CalendarUtils.truncarHora(new Date((Long)fieldValue.get("fechaHoraInicio"))),CalendarUtils.truncarHoraMaxima(new Date((Long) fieldValue.get("fechaHoraFin"))));
                            predicates.add(p);
                            break;
                        default:
                            if (p != null) {
                                predicates.add(p);
                            }
                            break;
                    }
                }
            }
            cq.select(per).where(predicates.toArray(new Predicate[] {}));
            List<T> resultado = entityManager.createQuery(cq).getResultList();
            return resultado;
        }
        else {
            cq.select(per);
            List<T> resultado = entityManager.createQuery(cq).getResultList();
            return resultado;
        }
    }

    /**
     * consulta de datos con una proyeccion definida
     * @param entityManager
     * @param nombreClase
     * @param atributosConsulta
     * @return
     */
    public static List<Object[]> consultaEntidad(EntityManager entityManager, String nombreClase, List<String> atributosConsulta) {

        if (atributosConsulta != null && !atributosConsulta.isEmpty()) {

            StringBuilder consulta = new StringBuilder();

            consulta.append(ConsultasDinamicasConstants.SELECT);

            for (String atributoConsulta : atributosConsulta) {

                consulta.append(atributoConsulta);
                consulta.append(",");
            }

            consulta.setLength(consulta.length() - 1);

            consulta.append(ConsultasDinamicasConstants.FROM);
            consulta.append(nombreClase);

            List<Object[]> data = entityManager.createQuery(consulta.toString()).getResultList();

            return data;

        }
        return null;
    }

    /**
     * Consulta de datos con una proyeccion definida y paginado
     * @param entityManager
     * @param claseEntidad
     * @param atributosConsulta
     * @param limit
     * @param offset
     * @return
     */
    public static <T> List<Object[]> consultaEntidad(EntityManager entityManager, Class claseEntidad, List<String> atributosConsulta,
            int limit, int offset) {

        if (atributosConsulta != null && !atributosConsulta.isEmpty()) {

            StringBuilder consulta = new StringBuilder();

            consulta.append(ConsultasDinamicasConstants.SELECT);

            for (String atributoConsulta : atributosConsulta) {

                consulta.append(atributoConsulta);
                consulta.append(",");
            }

            consulta.setLength(consulta.length() - 1);
            consulta.append(ConsultasDinamicasConstants.FROM);

            String nombreClase = claseEntidad.getName();
            consulta.append(nombreClase);

            List<Object[]> resutados = entityManager.createQuery(consulta.toString()).setFirstResult(offset).setMaxResults(limit)
                    .getResultList();

            return resutados;

        }

        return null;
    }

    /**
     * Conteo de registros
     * @param entityManager
     * @param claseEntidad
     * @return
     */
    public static <T> Long numeroRegistrosEntidad(EntityManager entityManager, Class claseEntidad) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(claseEntidad)));

        Long numeroRegistros = entityManager.createQuery(cq).getSingleResult();

        return numeroRegistros;
    }

}
