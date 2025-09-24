package com.asopagos.comunicados.ejb;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.archivos.clients.ObtenerArchivo;
import com.asopagos.comunicados.constants.NamedQueriesConstants;
import com.asopagos.comunicados.service.PlantillasService;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.entidades.ccf.comunicados.PlantillaComunicado;
import com.asopagos.entidades.ccf.comunicados.VariableComunicado;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

/**
 * Clase singleton que permite mejorar el uso de objetos comunes
 * entre procesamientos de plantillas de Reporte Consolidado de Cartera
 * con el proósito de evitar que cada procesamiento haga consultas 
 * a base de datos para obtener la plantilla, las variables de 
 * plantilla y los valores de Imágenes. De esta manera se mejora
 * el rendimiento.  
 * @author jrico
 *
 */
public class AuxiliarReporteConsolidadoCarteraSingleton {

    private static List<String> CLAVES_IMAGENES_COMUNICADOS = new ArrayList<>();
    
    private Map<String, String> valoresImagenesMap;
    private Map<Long, List<VariableComunicado>> variablesComunicadoMap;
    private Map<Long, List<Object[]>> variablesComunicadoResueltasMap;
    private Map<EtiquetaPlantillaComunicadoEnum, PlantillaComunicado> platillaComunicadoMap;
    
    private static AuxiliarReporteConsolidadoCarteraSingleton auxiliarReporteCosolidadoSingleton;
    
    /*
     * Variable que controla la cantidad de usos activos de la clase singleton
     */
    private static long cantidadSolicitudesActivas = 0;
    
    /**
     * Instancia del gestor de registro de eventos.
     */
    private static final ILogger logger = LogManager.getLogger(AuxiliarReporteConsolidadoCarteraSingleton.class);
    
    /**
     * Constructor privado de la clase.
     */
    private AuxiliarReporteConsolidadoCarteraSingleton() {
        this.valoresImagenesMap     = new HashMap<String, String>();
        this.variablesComunicadoMap = new HashMap<Long, List<VariableComunicado>>(); 
        this.variablesComunicadoResueltasMap = new HashMap<Long, List<Object[]>>(); 
        this.platillaComunicadoMap  = new HashMap<EtiquetaPlantillaComunicadoEnum, PlantillaComunicado>();
        this.inicializarClavesImagenes();
    }
    
    /**
     * Obtiene una única instancia de la clase
     * @return
     */
    public synchronized static AuxiliarReporteConsolidadoCarteraSingleton getSingletonInstance() {
        if (auxiliarReporteCosolidadoSingleton == null){
            auxiliarReporteCosolidadoSingleton = new AuxiliarReporteConsolidadoCarteraSingleton();
        } 
        cantidadSolicitudesActivas++;
        return auxiliarReporteCosolidadoSingleton;
    }

    /**
     * Permite obtener la planilla comunicado
     * @param etiquetaPlantillaComunicadoEnum
     * @param entityManager
     * @return
     */
    public synchronized PlantillaComunicado obtenerPlanillaComunicado(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicadoEnum, PlantillasService plantillaService) {
    
        PlantillaComunicado plantillaComunicado = null;
        
        if(this.platillaComunicadoMap.containsKey(etiquetaPlantillaComunicadoEnum)) {
            plantillaComunicado = this.platillaComunicadoMap.get(etiquetaPlantillaComunicadoEnum);
        } else {
            plantillaComunicado = plantillaService.consultarPlantillaComunicado(etiquetaPlantillaComunicadoEnum); 
            this.platillaComunicadoMap.put(etiquetaPlantillaComunicadoEnum, plantillaComunicado);
        }
        
        PlantillaComunicado clone = new PlantillaComunicado();
        clone.setAsunto(plantillaComunicado.getAsunto());
        clone.setCuerpo(plantillaComunicado.getCuerpo());
        clone.setEncabezado(plantillaComunicado.getEncabezado());
        clone.setEtiqueta(plantillaComunicado.getEtiqueta());
        clone.setIdentificadorImagenPie(plantillaComunicado.getIdentificadorImagenPie());
        clone.setIdPlantillaComunicado(plantillaComunicado.getIdPlantillaComunicado());
        clone.setMensaje(plantillaComunicado.getMensaje());
        clone.setNombre(plantillaComunicado.getNombre());
        clone.setPie(plantillaComunicado.getPie());
        
        return clone;
    }
    
    
    /**
     * Obtiene las variables del Comunicado consultado por su idPlantilla
     * @param idPlantillaComunicado
     * @param entityManager
     * @return
     */
    public synchronized List<VariableComunicado> obtenerVariablesComunicado(Long idPlantillaComunicado, EntityManager entityManager) {
        
        List<VariableComunicado> variablesComunicado = null;
        
        if(this.variablesComunicadoMap.containsKey(idPlantillaComunicado)) {
            variablesComunicado = this.variablesComunicadoMap.get(idPlantillaComunicado);
        } else {
            // Se consulta las variables sobre la tabla VariableComunicado
            variablesComunicado = entityManager
                .createNamedQuery(NamedQueriesConstants.CONSULTAR_VARIABLES_COMUNICADO, VariableComunicado.class)
                .setParameter("idPlantillaComunicado", idPlantillaComunicado).getResultList();
            this.variablesComunicadoMap.put(idPlantillaComunicado, variablesComunicado);
        }
        
        return variablesComunicado;
    }
    
    @SuppressWarnings({ "unchecked" })
    public synchronized List<Object[]> obtenerVariablesComunicado(String nombreQuery, Long idPlantillaComunicado, EntityManager entityManager) {
        
        List<Object[]> variablesComunicado = null;
        
        if(this.variablesComunicadoResueltasMap.containsKey(idPlantillaComunicado)) {
            variablesComunicado = this.variablesComunicadoResueltasMap.get(idPlantillaComunicado);
        } else {
            
            variablesComunicado = new ArrayList<Object[]>();
            
            // Se consulta las variables sobre la tabla VariableComunicado
            List<Object[]> resultados = entityManager.createNamedQuery(nombreQuery)
                .setParameter("idPlantillaComunicado", idPlantillaComunicado).getResultList();
           
            if(!resultados.isEmpty()) {
                for (Object[] fila : resultados) {
                    fila[1] = fila[1]!=null?fila[1]:"";
                    variablesComunicado.add(fila);
                }
           }
           this.variablesComunicadoResueltasMap.put(idPlantillaComunicado, variablesComunicado);
        }
        
        return variablesComunicado;
    }
    
    
    /**
     * Llena la lista CLAVES_IMAGENES_COMUNICADOS con las claves de comunicados que son imagenes.
     */
    private void inicializarClavesImagenes(){
        CLAVES_IMAGENES_COMUNICADOS.add("${logoDeLaCcf}");
        CLAVES_IMAGENES_COMUNICADOS.add("${firmaResponsableCcf}");
        CLAVES_IMAGENES_COMUNICADOS.add("${logoSupersubsidio}");
        CLAVES_IMAGENES_COMUNICADOS.add("${firmaRespAfiPersonas}");
        CLAVES_IMAGENES_COMUNICADOS.add("${firmaRespAfiEmpresas}");
        CLAVES_IMAGENES_COMUNICADOS.add("${firmaResponsableProcesoCartera}");
        CLAVES_IMAGENES_COMUNICADOS.add("${firmagerentefinanciera}");
        CLAVES_IMAGENES_COMUNICADOS.add("${firmagerentecomercial}");
        CLAVES_IMAGENES_COMUNICADOS.add("${FirmaDirectorAdminppalCCF}");
        CLAVES_IMAGENES_COMUNICADOS.add("${FirmaDirectorAdminsplCCF}");
        CLAVES_IMAGENES_COMUNICADOS.add("${firmasecretariageneral}");
        CLAVES_IMAGENES_COMUNICADOS.add("${membretePieDePaginaDeLaCcf}");
        CLAVES_IMAGENES_COMUNICADOS.add("${membreteEncabezadoDeLaCcf}");
    }
    
    /**
     * Obtiene el valor de la clave en base 64, si ocurre una excepción el valor por defecto será el identificador del archivo
     * @param Identificador del archivo
     * @return Valor de la clave en base 64
     */
    public synchronized String obtenerValorClaveImagen(String key, String valor) {
       
        String valorImgLogo = null;
        
        if(this.valoresImagenesMap.containsKey(key)) {
            return this.valoresImagenesMap.get(key);
        } else {
            if (CLAVES_IMAGENES_COMUNICADOS.contains(key)) {
            System.out.println("**--** gggobtenerValorClaveImagen -> valor:"+valor );
                try {
                    String encoded;
                   // System.out.println("**--** obtenerValorClaveImagen -> valor:"+valor );
                    ObtenerArchivo obtenerArchivoSrv = new ObtenerArchivo(valor);
                    obtenerArchivoSrv.execute();
                    if(obtenerArchivoSrv.getResult() != null){
                        InformacionArchivoDTO archivo = obtenerArchivoSrv.getResult();
                        encoded = Base64.getEncoder().encodeToString(archivo.getDataFile());
                        if(key.equals("${membretePieDePaginaDeLaCcf}") || key.equals("${membreteEncabezadoDeLaCcf}")){
                            valorImgLogo = "<img id=\"membrete\" style=\"width: 48%;\" src=\"data:image/png;base64," + encoded + "\"/>";
                        }else{
                            valorImgLogo = "<img id=\"Imagen\" style=\"width: 100px;\" src=\"data:image/png;base64," + encoded + "\"/>";                        
                        }
                        // InformacionArchivoDTO archivo = obtenerArchivoSrv.getResult();
                        //     encoded = Base64.getEncoder().encodeToString(archivo.getDataFile());
                        //    valorImgLogo = "<img id=\"logoComCCF\" style=\"width: 100px;\" src=\"data:image/png;base64," + encoded + "\"/>";
                    }
                 
                } catch (Exception e) {
                    logger.warn("Ocurrió una excepción contruyendo la imagen en base 64",e);
                    valorImgLogo = valor;
                }
                this.valoresImagenesMap.put(key, valorImgLogo);
            }
        }
        return valorImgLogo;
    }
    
    /**
     * Permite destruir la instancia únicamente cuando esta no tiene
     * usos activos.
     */
    public synchronized void destruir () {
        if(cantidadSolicitudesActivas > 0) {
            cantidadSolicitudesActivas--;
        }
        if(cantidadSolicitudesActivas == 0) {
            auxiliarReporteCosolidadoSingleton = null;
        }
    }
    
}
