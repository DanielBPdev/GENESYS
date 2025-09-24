package com.asopagos.archivos.almacenamiento;

/**
 * Enumeracion que representa las implementaciones de las operaciones para realizar
 * la gestion de archivos sobre el sistema
 * 
 * @author sbrinez
 * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
 */
public enum AlmacenamientoArchivosEnum {
    
    
	//ALFRESCO("com.asopagos.archivos.almacenamiento.alfresco.AlmacenamientoAlfresco"),
	ALFRESCO("java:global/ArchivosService/AlmacenamientoAlfresco"),
    
	//GOOGLE_STORAGE("com.asopagos.archivos.almacenamiento.google.AlmacenamientoGoogle");
	GOOGLE_STORAGE("java:global/ArchivosService/AlmacenamientoGoogle");
    
    private final String implClass;
    
    private AlmacenamientoArchivosEnum(String implClass) {
        this.implClass = implClass;
    }
    
    public String getImplClass() {
        return implClass;
    }
    
}
