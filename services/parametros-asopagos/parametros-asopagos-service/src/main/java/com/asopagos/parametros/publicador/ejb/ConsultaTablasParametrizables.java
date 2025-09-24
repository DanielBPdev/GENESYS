package com.asopagos.parametros.publicador.ejb;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import com.asopagos.dto.ElementoListaDTO;
import com.asopagos.entidades.annotations.AtributoParametro;
import com.asopagos.entidades.annotations.Parametro;
import com.asopagos.enumeraciones.core.DecisionSiNoEnum;
import com.asopagos.enumeraciones.core.HabilitadoInhabilitadoEnum;
import com.asopagos.enumeraciones.core.TipoListaParametroEnum;
import com.asopagos.enumeraciones.core.TipoParametroEnum;
import com.asopagos.jpa.JPAUtils;
import com.asopagos.parametros.AtributoTablaParametro;
import com.asopagos.parametros.GrupoValidacionEnum;
import com.asopagos.parametros.RelatedTable;
import com.asopagos.parametros.TablaParametro;
import com.asopagos.parametros.TipoValidacionEnum;
import com.asopagos.parametros.ValidacionParametro;
import com.asopagos.parametros.dto.RelatedTableDTO;
import com.asopagos.parametros.dto.TablaParametroDTO;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.validacion.GrupoActualizacion;
import com.asopagos.validacion.GrupoCreacion;

/**
 * <b>Descripcion:</b> Clase que se encarga del manejo de informacion de tablas
 * parametrizables<br/>
 * <b>Módulo:</b> Asopagos - HU Transversal<br/>
 *
 * @author <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */
@Startup
@Singleton
public class ConsultaTablasParametrizables {

    /**
     * Referencia a la unidad de persistencia del servicio
     */
    @PersistenceContext(unitName = "parametros_PU")
    private EntityManager entityManager;

    private Map<String, TablaParametro> tablasParametros = new TreeMap<>();

    /**
     * @return the tablasParametros
     */
    public Map<String, TablaParametro> getTablasParametros() {
        return tablasParametros;
    }

    @PostConstruct
    public void inicializarTablasParametros() {

        Metamodel metaModel = entityManager.getMetamodel();
        Set<EntityType<?>> entidades = metaModel.getEntities();

        if (entidades == null || entidades.isEmpty()) {
            return;
        }

        for (EntityType<?> entityType : entidades) {
            Class<?> clase = entityType.getJavaType();
            if (clase != null && clase.isAnnotationPresent(Table.class)) {
                Table tabla = clase.getAnnotation(Table.class);
                // si tiene la anotacion parametro
                if (clase.isAnnotationPresent(Parametro.class)) {
                    List<AtributoTablaParametro> atributos = new ArrayList<>();
                    AtributoTablaParametro atributoParametro;
                    List<RelatedTable> relatedTables = new ArrayList<>();
                    List<RelatedTable> relatedLists = new ArrayList<>();

                    atributos.add(obtenerAtributoId(clase));

                    // obtener los atributos de la clase
                    for (Field field : clase.getDeclaredFields()) {
                        // obtener las anotaciones

                        // preguntar al campo por la anotacion de atributo
                        // parametro
                        Annotation atributoParametroAnnotation = field.getAnnotation(AtributoParametro.class);
                        // si tiene la anotacion
                        if (atributoParametroAnnotation != null) {
                            AtributoParametro atributo = (AtributoParametro) atributoParametroAnnotation;
                            atributoParametro = new AtributoTablaParametro();
                            atributoParametro.setNombreAtributo(field.getName());
                            // si es una enumeracion
                            if (field.getType().isEnum()) {
                                atributoParametro.setTipoDato("Enum");
                                atributoParametro.setTipoParametro(TipoParametroEnum.ENUM);
                                relatedLists.add(crearTablaRelacionadaEnum(field));
                            }
                            else {
                                atributoParametro.setTipoDato(field.getType().getSimpleName());
                            }

                            List<ValidacionParametro> validations = new ArrayList<>();
                            ValidacionParametro vp;
                            // si es vacio no hay una entidad relacionada
                            if (atributo.entity().isAssignableFrom(Class.class)) {
                                atributoParametro.setNombre(field.getName());
                                if (atributo.tipo().name().equals(TipoParametroEnum.FECHA.name())) {
                                    atributoParametro.setTipoParametro(TipoParametroEnum.FECHA);
                                }
                                // si hay una lista relacionada a una
                                // enumeracion
                                if (atributo.tipo().name().equals(TipoParametroEnum.LISTA.name())) {
                                    atributoParametro.setTipoParametro(TipoParametroEnum.LISTA);
                                    RelatedTable relatedList = new RelatedTable();
                                    relatedList.setNombre(field.getName());
                                    relatedList.setNombreClase(atributo.claseEnumeracion().getName());
                                    // Seteamos el tipo de lista
                                    if (atributo.claseEnumeracion().equals((HabilitadoInhabilitadoEnum.class))) {
                                        // habilitado deshabilitado
                                        atributoParametro.setTipoListaParametro(TipoListaParametroEnum.HABILITADO_DESHABILITADO);
                                    }
                                    if (atributo.claseEnumeracion().equals(DecisionSiNoEnum.class)) {
                                        // si no
                                        atributoParametro.setTipoListaParametro(TipoListaParametroEnum.SI_NO);
                                    }
                                    relatedList.setAtributeId(field.getName());
                                    relatedList.setDisplayAtributeName("descripcion");
                                    relatedLists.add(relatedList);

                                }
                                // preguntar al campo por la anotacion
                                // NotNull
                                Annotation notNullAnnotation = field.getAnnotation(NotNull.class);
                                // si tiene la anotacion
                                if (notNullAnnotation != null) {
                                    NotNull notNullAnn = (NotNull) notNullAnnotation;
                                    Class<?>[] grupos = notNullAnn.groups();
                                    List<GrupoValidacionEnum> gruposValidacionAttr = new ArrayList<>();
                                    // Agregado de grupos de validacion
                                    for (Class<?> claseGrupo : grupos) {
                                        if (claseGrupo.getName().equals(GrupoActualizacion.class.getName())) {
                                            gruposValidacionAttr.add(GrupoValidacionEnum.GRUPO_ACTUALIZACION);
                                        }
                                        if (claseGrupo.getName().equals(GrupoCreacion.class.getName())) {
                                            gruposValidacionAttr.add(GrupoValidacionEnum.GRUPO_CREACION);
                                        }
                                    }
                                    vp = new ValidacionParametro();
                                    vp.setTipoValidacion(TipoValidacionEnum.NOT_NULL);
                                    vp.setGrupoValidacion(gruposValidacionAttr);
                                    validations.add(vp);
                                }
                                // preguntar al campo por la anotacion Size
                                Annotation sizeAnnotation = field.getAnnotation(Size.class);
                                // si tiene la anotacion
                                if (sizeAnnotation != null) {
                                    Size sizeAnn = (Size) sizeAnnotation;
                                    vp = new ValidacionParametro();
                                    vp.setTipoValidacion(TipoValidacionEnum.SIZE);
                                    if (sizeAnn.max() != Integer.MAX_VALUE) {
                                        vp.setMax(sizeAnn.max());
                                    }
                                    if (sizeAnn.min() != 0) {
                                        vp.setMin(sizeAnn.min());
                                    }
                                    validations.add(vp);
                                }
                            }
                            else {
                                // obtenemos la clase relacionada
                                Class relatedClass = atributo.entity();
                                if (relatedClass != null && relatedClass.isAnnotationPresent(Table.class)) {
                                    Table relatedTable = (Table) relatedClass.getAnnotation(Table.class);
                                    // nombre de columna a mostrar
                                    // relacionada
                                    atributoParametro.setNombre(relatedTable.name());
                                    vp = new ValidacionParametro();
                                    vp.setTipoValidacion(TipoValidacionEnum.NOT_NULL);
                                    validations.add(vp);
                                    RelatedTable relatedTableObj = new RelatedTable();
                                    relatedTableObj.setNombre(relatedTable.name());
                                    relatedTableObj.setNombreClase(relatedClass.getName());
                                    // recorrido de los atributos
                                    for (Field fieldRelated : relatedClass.getDeclaredFields()) {
                                        Annotation entityId = fieldRelated.getAnnotation(Id.class);
                                        if (entityId != null) {
                                            // identificador entidad
                                            // relacionada
                                            relatedTableObj.setAtributeId(fieldRelated.getName());
                                        }
                                    }
                                    // recorrido de los atributos
                                    for (Field fieldRelated : relatedClass.getDeclaredFields()) {
                                        // si es de tipo AtributoParametro
                                        Annotation[] annotationsRelated = fieldRelated.getAnnotationsByType(AtributoParametro.class);
                                        // recorrido de atributos
                                        for (Annotation annotationRelated : annotationsRelated) {
                                            AtributoParametro atributeRelated = (AtributoParametro) annotationRelated;
                                            // si este es el atributo
                                            // relacionado para mostrar
                                            if (atributeRelated.display()) {
                                                // TODO identificador de la
                                                // clase
                                                relatedTableObj.setDisplayAtributeName(fieldRelated.getName());
                                            }
                                        }
                                    }
                                    relatedTables.add(relatedTableObj);
                                }
                            }
                            atributoParametro.setValidaciones(validations);
                            atributos.add(atributoParametro);
                        }
                    }
                    TablaParametro tablaParametro = new TablaParametro();
                    tablaParametro.setNombreTabla(tabla.name());
                    tablaParametro.setNombreClase(clase.getName());
                    tablaParametro.setAtributos(atributos);
                    tablaParametro.setRelatedTables(relatedTables);
                    tablaParametro.setRelatedLists(relatedLists);
                    tablasParametros.put(tablaParametro.getNombreTabla(), tablaParametro);
                }
            }
        }
    }

    private AtributoTablaParametro obtenerAtributoId(Class<?> clase) {
        AtributoTablaParametro atributoParametro = new AtributoTablaParametro();
        for (Field field : clase.getDeclaredFields()) {
            Annotation entityId = field.getAnnotation(Id.class);
            if (entityId != null) {
                atributoParametro = new AtributoTablaParametro();
                atributoParametro.setNombre(field.getName());
                atributoParametro.setNombreAtributo(field.getName());
                atributoParametro.setTipoDato(field.getType().getSimpleName());
                atributoParametro.setTipoParametro(TipoParametroEnum.DEFECTO);
                return atributoParametro;
            }
        }
        return atributoParametro;
    }

    private RelatedTable crearTablaRelacionadaEnum(Field field) {
        RelatedTable relatedEnum = new RelatedTable();
        relatedEnum.setNombre(field.getName());
        relatedEnum.setNombreClase(field.getType().getName());
        relatedEnum.setAtributeId(field.getName());
        relatedEnum.setDisplayAtributeName("descripcion");
        return relatedEnum;
    }

    /**
     * Nombres de las tablas parametricas
     * 
     * @see com.asopagos.parametros.service.ParametroService#listaTablaParametros()
     */
    public List<ElementoListaDTO> listaTablaParametros() {
        List<ElementoListaDTO> tablas = new ArrayList<>();
        for (String tablaParametro : tablasParametros.keySet()) {
            ElementoListaDTO elemento = new ElementoListaDTO();
            elemento.setIdentificador(tablaParametro);
            elemento.setValor(modificarNombreParametro(tablaParametro));
            tablas.add(elemento);
        }
        return tablas;
    }

    /**
     * Método encargado de modificar una cadena que cuenta con mayusculas
     * intermedias, agregandoles un espacio antes de ellass
     * 
     * @param campoValidar,
     *        campo a modificar
     * @return retorna la cadena con espacios antes de una mayuscula
     */
    public String modificarNombreParametro(String campoModificar) {
        return StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(campoModificar), " ");
    }

    /**
     * Informacion necesaria para el tratamiento dinamico de una tabla
     * parametrizable
     * 
     * @see com.asopagos.parametros.service.ParametroService#informacionTablasParametricas(java.lang.String)
     */
    public TablaParametroDTO informacionTablasParametricas(String nombreTabla) {
        TablaParametroDTO tablaParametroDTO = new TablaParametroDTO();
        TablaParametro tablaParametroCache = this.tablasParametros.get(nombreTabla);

        if (tablaParametroCache == null) {
            return tablaParametroDTO;
        }

        tablaParametroDTO = TablaParametroDTO.convertToDTO(tablaParametroCache);
        List<RelatedTable> relatedTables = tablaParametroCache.getRelatedTables();
        List<RelatedTable> relatedLists = tablaParametroCache.getRelatedLists();
        List<RelatedTableDTO> relatedTablesDTO = new ArrayList<>();
        List<RelatedTableDTO> relatedListsDTO = new ArrayList<>();

        // listas relacionadas
        for (RelatedTable relatedTable : relatedLists) {
            RelatedTableDTO relatedListDTO = new RelatedTableDTO();
            relatedListDTO.setAtributeId(relatedTable.getAtributeId());
            relatedListDTO.setDisplayAtributeName(relatedTable.getDisplayAtributeName());
            relatedListDTO.setNombre(relatedTable.getNombre());
            List<ElementoListaDTO> elementosLista = new ArrayList<>();
            try {
                Class enumClass = Class.forName(relatedTable.getNombreClase());
                Object[] constantesEnum = enumClass.getEnumConstants();
                for (Object constanteEnum : constantesEnum) {
                    ElementoListaDTO elemento = obtenerDescripcion(constanteEnum);
                    if(elemento.getValor() != null){
                        elementosLista.add(obtenerDescripcion(constanteEnum));    
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new TechnicalException("Clase no encontrada: " + relatedTable.getNombreClase(), e);
            }
            relatedListDTO.setListaDatos(elementosLista);
            relatedListsDTO.add(relatedListDTO);
        }

        tablaParametroDTO.setRelatedLists(relatedListsDTO);
        // tablas relacionadas
        for (RelatedTable relatedTable : relatedTables) {
            RelatedTableDTO relatedTableDTO = new RelatedTableDTO();
            relatedTableDTO.setAtributeId(relatedTable.getAtributeId());
            relatedTableDTO.setDisplayAtributeName(relatedTable.getDisplayAtributeName());
            relatedTableDTO.setNombre(relatedTable.getNombre());

            String nombreClase = relatedTable.getNombreClase();
            List<ElementoListaDTO> elementosLista = new ArrayList<>();
            List<String> atributosConsulta = new ArrayList<>();
            atributosConsulta.add(relatedTable.getAtributeId());
            atributosConsulta.add(relatedTable.getDisplayAtributeName());

            List<Object[]> resultados = JPAUtils.consultaEntidad(entityManager, nombreClase, atributosConsulta);
            for (Object[] row : resultados) {
                ElementoListaDTO elemento = new ElementoListaDTO();
                elemento.setIdentificador(row[0]);
                elemento.setValor((String) row[1]);
                elementosLista.add(elemento);
            }
            relatedTableDTO.setListaDatos(elementosLista);
            relatedTablesDTO.add(relatedTableDTO);
        }
        tablaParametroDTO.setRelatedTables(relatedTablesDTO);
        return tablaParametroDTO;
    }

    private ElementoListaDTO obtenerDescripcion(Object constanteEnum) {
        ElementoListaDTO elemento = new ElementoListaDTO();
        try {
            Enum enumConstant = (Enum) constanteEnum;
            elemento.setIdentificador(enumConstant.name());
            if(PropertyUtils.isReadable(constanteEnum, "descripcion")){
                elemento.setValor((String) PropertyUtils.getProperty(constanteEnum, "descripcion"));    
            }
        } catch (IllegalAccessException e) {
            throw new TechnicalException("El campo descripcion no es accesible", e);
        } catch (InvocationTargetException e) {
            throw new TechnicalException("Error al intentar obtener el valor del campo descripcion", e);
        } catch (NoSuchMethodException e) {
            throw new TechnicalException("El campo descripcion no existe", e);
        }
        return elemento;
    }
}