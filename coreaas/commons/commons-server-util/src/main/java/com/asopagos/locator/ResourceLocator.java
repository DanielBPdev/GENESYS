package com.asopagos.locator;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Clase utilitaria para localización de recursos en el servidor
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class ResourceLocator {
    
    
    /**
     * Referencia al Context
     */
    private static InitialContext context;
    
    /**
     * Mapa que contiene los nombres JNDI de todos los EJB desplegados
     */
    private static Map<String, String> nombresJNDI;
    
    private static final String SUFIJO_LOCAL = "Local";
    
    private static final String SUFIJO_REMOTE = "Remote";
    
    private static final String SUFIJO_BUSINESS = "Business";
    
    private static final String DATASOURCE_CORE = "java:/jboss/datasources/asopagosDS";
    
    
    /**
     * Contexto de inicialización estático
     */
    static {
        try {
            context = new InitialContext();
            cargarRecursosJBoss();
        } catch (NamingException ex) {
            Logger.getLogger(ResourceLocator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método de inicialización que identifica e indexa todos los recursos EJB
     * desplegados en el servidor de aplicaciones
     */
    private static void cargarRecursosJBoss() {
		try {
			MBeanServer server = java.lang.management.ManagementFactory.getPlatformMBeanServer();
			ObjectName on = new ObjectName("jboss.msc:type=container,name=jboss-as");
			String[] servicesNames = (String[]) server.invoke(on, "queryServiceNames", null, null);
            nombresJNDI = new HashMap<String, String>();
			for (String serviceName : servicesNames) {
				if (serviceName.matches("\\Qjboss.naming.context.java.global.\\E.+\\..+\\!.+(Local)?\"")) {
					String bean = "";
					Pattern p = Pattern.compile("\"(.+?)\"");
					Matcher m = p.matcher(serviceName);
					String globalName = "";
					while (m.find()) {
						String group = m.group(1);
						globalName += "/" + group;
						if (group.contains("!")) {
							bean = group.substring(0, group.indexOf("!"));
 						}
					}
                    nombresJNDI.put(bean, globalName);
                    System.out.println(bean + " " + globalName);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("No se puede inicializar el ServiceLocator", e);
		}         
    }    
    
    /**
     * Método que permite hacer lookup de un EJB desplegado en el servidor
     * @param <T>
     * @param classInterface Class de la interface del EJB
     * @return 
     */
    public static <T extends Object> T lookupEJBReference(Class<T> classInterface) {
        try {
            if (classInterface != null) {
                String nombreInterface = classInterface.getSimpleName();
                String nombreEjb = null;
                String nombreJNDI = "";
                if (nombreInterface.endsWith(SUFIJO_LOCAL)) {
                    nombreEjb = nombreInterface.replace(SUFIJO_LOCAL, SUFIJO_BUSINESS);
                    nombreJNDI = "java:module" + nombresJNDI.get(nombreEjb);
                } else if (nombreInterface.endsWith(SUFIJO_REMOTE)) {
                    nombreEjb =  nombreInterface.replace(SUFIJO_REMOTE, SUFIJO_BUSINESS);
                    nombreJNDI = "java:global" + nombresJNDI.get(nombreEjb);
                }
                
                if (nombreJNDI != null) {
                    return (T) context.lookup(nombreJNDI);
                }                
            }
        } catch (NamingException ex) {
            Logger.getLogger(ResourceLocator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Se obtiene la referencia al datasource core
     * @return Datasource
     * @throws Exception 
     */
    public static DataSource getCoreDataSource() throws Exception {
        return (DataSource) context.lookup(DATASOURCE_CORE);
    }
}
