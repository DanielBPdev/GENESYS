package com.asopagos.validaciones.fovis.dto;

import java.io.Serializable;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.util.Map;
import com.asopagos.entidades.ccf.afiliaciones.DatoRegistraduriaNacional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class ConsultaRegistroCivilDTO implements Serializable {

    private static final ILogger logger = LogManager.getLogger(ConsultaRegistroCivilDTO.class);

    private String nuipnip;

    private TipoIdentificacionEnum tipoIdentificacion;

    private String numeroIdentificacion;

    private String numeroIdentificacionANI;

    private String apellido1Inscrito;

    private String apellido2Inscrito;

    private String nombre1Inscrito;

    private String nombre2Inscrito;

    private String fechaNacInscrito;

    private GeneroEnum sexoInscrito;

    private String paisNacDescInscrito;

    private String dptoNacDescInscrito;

    private String mpioNacDescInscrito;

    private String corregNacDescInscrito;

    private String oficinaRegistroDesc;

    private String paisInscripcionDesc;

    private String dptoInscripcionDesc;

    private String fechaInscripcion;

    private String serial;

    private String apellido1Madre;

    private String apellido2Madre;

    private String nombre1Madre;

    private String nombre2Madre;

    private TipoIdentificacionEnum tipoDocMadre;

    private String numDocMadre;

    private String nacion1DescMadre;

    private String apellido1Padre;

    private String apellido2Padre;

    private String nombre1Padre;

    private String nombre2Padre;

    private TipoIdentificacionEnum tipoDocPadre;

    private String numDocPadre;

    private String numeroControl;

    private String codError;

    private String primerApellido;

    private String segundoApellido;

    private String descripcionError;

    private String fechaHoraConsulta;

    private String estadoDesc;

    private String particula;

    private String estadoDescCedula;

    private String numResolucion;

    private String anoResolucion;

    private String lugarNacimiento;

    private String informante;

    private String fechaExpedicionCedula;

    private String fechaDefuncion;

    private String lugarNovedad;

    private String lugarPreparacion;

    private String grupoSanguineo;

    private String estatura;

    private String mpioNacDescInscritoExpCedula;

    private String dptoNacDescInscritoExpCedula;

    private GeneroEnum genero;

    private String serialDefuncion;

    public ConsultaRegistroCivilDTO(){
    }

    public void convertToDTO(Map<String,Object> retorno, String busqueda) throws ParseException{
        if(busqueda.equals("REGN")|| busqueda.equals("REGS")){
            logger.info("entra if converToDTO");
            logger.info(retorno.get("apellido1Inscrito"));
        this.apellido1Inscrito = retorno.get("apellido1Inscrito")!=null?retorno.get("apellido1Inscrito").toString():"";
        this.apellido2Inscrito = retorno.get("apellido2Inscrito")!=null?retorno.get("apellido2Inscrito").toString():"";
        this.nombre1Inscrito = retorno.get("nombre1Inscrito")!=null?retorno.get("nombre1Inscrito").toString():"";
        this.nombre2Inscrito = retorno.get("nombre2Inscrito")!=null?retorno.get("nombre2Inscrito").toString():"";
        this.nuipnip = retorno.get("nuipnip")!=null?retorno.get("nuipnip").toString():"";

        this.fechaNacInscrito = retorno.get("fechaNacInscrito")!=null?retorno.get("fechaNacInscrito").toString():"";
        if(retorno.get("sexoInscrito")!=null){
            if(retorno.get("sexoInscrito").toString().equals("M")){
                this.sexoInscrito = GeneroEnum.MASCULINO;
            }else{
                this.sexoInscrito = GeneroEnum.FEMENINO;
            }
        }
        this.paisNacDescInscrito = retorno.get("paisNacDescInscrito")!=null?retorno.get("paisNacDescInscrito").toString():"";
        this.dptoNacDescInscrito = retorno.get("dptoNacDescInscrito")!=null?retorno.get("dptoNacDescInscrito").toString():"";
        this.mpioNacDescInscrito = retorno.get("mpioNacDescInscrito")!=null?retorno.get("mpioNacDescInscrito").toString():"";
        this.corregNacDescInscrito = retorno.get("corregNacDescInscrito")!=null?retorno.get("corregNacDescInscrito").toString():"";
        this.oficinaRegistroDesc = retorno.get("oficinaRegistroDesc")!=null?retorno.get("oficinaRegistroDesc").toString():"";
        this.dptoInscripcionDesc = retorno.get("dptoInscripcionDesc")!=null?retorno.get("dptoInscripcionDesc").toString():"";
        this.fechaInscripcion = retorno.get("fechaInscripcion")!=null?retorno.get("fechaInscripcion").toString():"";
        this.paisInscripcionDesc = retorno.get("paisInscripcionDesc")!=null?retorno.get("paisInscripcionDesc").toString():"";
        this.estadoDesc = retorno.get("estadoDesc")!=null?retorno.get("estadoDesc").toString():"";
        this.serial = retorno.get("serial")!=null?retorno.get("serial").toString():"";

        if(retorno.get("tipoDocMadre")!= null){
            switch (retorno.get("tipoDocMadre").toString()) {
                case "CC":
                    this.tipoDocMadre = TipoIdentificacionEnum.CEDULA_CIUDADANIA;
                    break;
                case "CE":
                    this.tipoDocMadre = TipoIdentificacionEnum.CEDULA_EXTRANJERIA;
                    break;
                case "TI":
                    this.tipoDocMadre = TipoIdentificacionEnum.TARJETA_IDENTIDAD;
                    break;
                case "CD":
                    this.tipoDocMadre = TipoIdentificacionEnum.CARNE_DIPLOMATICO;
                    break;
            
                default:
                    break;
            }
    }        
        this.numDocMadre = retorno.get("numDocMadre")!=null?retorno.get("numDocMadre").toString():"";
        this.apellido1Madre = retorno.get("apellido1Madre")!=null?retorno.get("apellido1Madre").toString():"";
        this.apellido2Madre = retorno.get("apellido2Madre")!=null?retorno.get("apellido2Madre").toString():"";
        this.nombre1Madre = retorno.get("nombre1Madre")!=null?retorno.get("nombre1Madre").toString():"";
        this.nombre2Madre = retorno.get("nombre2Madre")!=null?retorno.get("nombre2Madre").toString():"";
        this.nacion1DescMadre = retorno.get("nacion1DescMadre")!=null?retorno.get("nacion1DescMadre").toString():"";

        if(retorno.get("tipoDocPadre")!= null){
            switch (retorno.get("tipoDocPadre").toString()) {
                case "CC":
                    this.tipoDocPadre = TipoIdentificacionEnum.CEDULA_CIUDADANIA;
                    break;
                case "CE":
                    this.tipoDocPadre = TipoIdentificacionEnum.CEDULA_EXTRANJERIA;
                    break;
                case "TI":
                    this.tipoDocPadre = TipoIdentificacionEnum.TARJETA_IDENTIDAD;
                    break;
                case "CD":
                    this.tipoDocPadre = TipoIdentificacionEnum.CARNE_DIPLOMATICO;
                    break;
            
                default:
                    break;
            }
    }
        
        this.numDocPadre = retorno.get("numDocPadre")!=null?retorno.get("numDocPadre").toString():"";
        this.apellido1Padre = retorno.get("apellido1Padre")!=null?retorno.get("apellido1Padre").toString():"";
        this.apellido2Padre = retorno.get("apellido2Padre")!=null?retorno.get("apellido2Padre").toString():"";
        this.nombre1Padre = retorno.get("nombre1Padre")!=null?retorno.get("nombre1Padre").toString():"";
        this.nombre2Padre = retorno.get("nombre2Padre")!=null?retorno.get("nombre2Padre").toString():"";
        }
        else{

            this.numeroIdentificacionANI = retorno.get("nuip")!=null?retorno.get("nuip").toString():"";
            this.codError = retorno.get("codError")!=null?retorno.get("codError").toString():"";
            this.fechaInscripcion = retorno.get("fechaInscripcion")!=null?retorno.get("fechaInscripcion").toString():"";
            this.apellido1Inscrito = retorno.get("primerApellido")!=null?retorno.get("primerApellido").toString():"";
            this.particula = retorno.get("particula")!=null?retorno.get("particula").toString():"";
            this.apellido2Inscrito = retorno.get("segundoApellido")!=null?retorno.get("segundoApellido").toString():"";
            this.nombre1Inscrito = retorno.get("primerNombre")!=null?retorno.get("primerNombre").toString():"";
            this.nombre2Inscrito = retorno.get("segundoNombre")!=null?retorno.get("segundoNombre").toString():"";
            this.mpioNacDescInscritoExpCedula = retorno.get("municipioExpedicion")!=null?retorno.get("municipioExpedicion").toString():"";
            this.dptoNacDescInscritoExpCedula = retorno.get("departamentoExpedicion")!=null?retorno.get("departamentoExpedicion").toString():"";
            this.fechaExpedicionCedula = retorno.get("fechaExpedicion")!=null?retorno.get("fechaExpedicion").toString():"";
            this.estadoDescCedula = retorno.get("estadoCedula")!=null?retorno.get("estadoCedula").toString():"";
            this.numResolucion = retorno.get("numResolucion")!=null?retorno.get("numResolucion").toString():"";
            this.anoResolucion = retorno.get("anoResolucion")!=null?retorno.get("anoResolucion").toString():"";

            if(retorno.get("genero")!=null){
                if(retorno.get("genero").toString().equals("M")){
                    this.genero = GeneroEnum.MASCULINO;
                }else{
                    this.genero = GeneroEnum.FEMENINO;
                }
            }
            this.fechaNacInscrito = retorno.get("fechaNacimiento")!=null?retorno.get("fechaNacimiento").toString():"";
            this.lugarNacimiento = retorno.get("lugarNacimiento")!=null?retorno.get("lugarNacimiento").toString():"";
            this.informante = retorno.get("informante")!=null?retorno.get("informante").toString():"";
            this.serialDefuncion = retorno.get("serial")!=null?retorno.get("serial").toString():"";
            this.fechaDefuncion = retorno.get("fechaDefuncion")!=null?retorno.get("fechaDefuncion").toString():"";
            this.lugarNovedad = retorno.get("lugarNovedad")!=null?retorno.get("lugarNovedad").toString():"";
            this.lugarPreparacion = retorno.get("lugarPreparacion")!=null?retorno.get("lugarPreparacion").toString():"";
            this.grupoSanguineo = retorno.get("grupoSanguineo")!=null?retorno.get("grupoSanguineo").toString():"";
            this.estatura = retorno.get("estatura")!=null?retorno.get("estatura").toString():"";

        }

    }

        /**
     * Asocia los datos del DTO a la Entidad
     * 
     * @return CalificacionPostulacion
     */
    public DatoRegistraduriaNacional convertToEntity() throws ParseException {

        DatoRegistraduriaNacional datoRegistraduriaNacional = new DatoRegistraduriaNacional();
        datoRegistraduriaNacional.setTipoIdentificacion(this.getTipoIdentificacion());
        datoRegistraduriaNacional.setNumeroIdentificacion(this.getNumeroIdentificacion());
        datoRegistraduriaNacional.setParticula(this.getParticula());
        datoRegistraduriaNacional.setMunicipioExpedicion(this.getMpioNacDescInscritoExpCedula());
        datoRegistraduriaNacional.setDepartamentoExpedicion(this.getDptoNacDescInscritoExpCedula());
        datoRegistraduriaNacional.setPaisNacimiento(this.getPaisNacDescInscrito());
        try {
            Date fechaExPedicionCED = new SimpleDateFormat("dd/MM/yyyy").parse(this.getFechaExpedicionCedula()); 
        datoRegistraduriaNacional.setFechaExpedicion(fechaExPedicionCED);
        } catch (Exception e) {
          

        }
        datoRegistraduriaNacional.setEstadoCED(this.getEstadoDescCedula());
        datoRegistraduriaNacional.setNumeroResolucion(this.getNumResolucion());
        datoRegistraduriaNacional.setAnoResolucion(this.getAnoResolucion());
        datoRegistraduriaNacional.setNacionalidadMadre(this.getNacion1DescMadre());
        datoRegistraduriaNacional.setInformante(this.getInformante());
        datoRegistraduriaNacional.setSerialDefuncion(this.getSerialDefuncion());
        datoRegistraduriaNacional.setLugarNovedad(this.getLugarNovedad());
        datoRegistraduriaNacional.setLugarPreparacion(this.getLugarPreparacion());
        datoRegistraduriaNacional.setGrupoSanguineo(this.getGrupoSanguineo());
        datoRegistraduriaNacional.setEstatura(this.getEstatura());
        datoRegistraduriaNacional.setNuipANI(this.getNumeroIdentificacionANI());
        datoRegistraduriaNacional.setPrimerApellidoCED(this.getApellido1Inscrito() != null?this.getApellido1Inscrito():null);
        datoRegistraduriaNacional.setSegundoApellidoCED(this.getApellido2Inscrito() != null?this.getApellido2Inscrito():null);
        datoRegistraduriaNacional.setPrimerNombreCED(this.getNombre1Inscrito() != null?this.getNombre1Inscrito():null);
        datoRegistraduriaNacional.setSegundoNombreCED(this.getNombre2Inscrito() != null?this.getNombre2Inscrito():null);
        if(this.getFechaNacInscrito() != null && !this.getFechaNacInscrito().equals("")){
            try {
                Date fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(this.getFechaNacInscrito()); 
                datoRegistraduriaNacional.setFechaNacimientoCED(fechaNacimiento);
            } catch (Exception e) {
                Date fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy").parse(this.getFechaNacInscrito()); 
                datoRegistraduriaNacional.setFechaNacimientoCED(fechaNacimiento);      
            }
        }
        datoRegistraduriaNacional.setGeneroCED(this.getGenero());
        if(this.getFechaDefuncion() != null && !this.getFechaDefuncion().equals("")){
            try {
                Date fechaFallecimiento = new SimpleDateFormat("yyyy/MM/dd").parse(this.getFechaDefuncion()); 
                datoRegistraduriaNacional.setFechaFallecimientoCED(fechaFallecimiento);
            } catch (Exception e) {
                Date fechaFallecimiento = new SimpleDateFormat("yyyy-MM-dd").parse(this.getFechaDefuncion()); 
                datoRegistraduriaNacional.setFechaFallecimientoCED(fechaFallecimiento);
                // TODO: handle exception
            }
        }

        datoRegistraduriaNacional.setNuipRNCE(this.getNuipnip());
        datoRegistraduriaNacional.setPrimerApellidoREG(this.getApellido1Inscrito() != null?this.getApellido1Inscrito():null);
        datoRegistraduriaNacional.setSegundoApellidoREG(this.getApellido2Inscrito() != null?this.getApellido2Inscrito():null);
        datoRegistraduriaNacional.setPrimerNombreREG(this.getNombre1Inscrito() != null?this.getNombre1Inscrito():null);
        datoRegistraduriaNacional.setSegundoNombreREG(this.getNombre2Inscrito() != null?this.getNombre2Inscrito():null);
        if(this.getFechaNacInscrito() != null&& !this.getFechaNacInscrito().equals("")){
            try {
                Date fechaNacimientoREG = new SimpleDateFormat("yyyy-MM-dd").parse(this.getFechaNacInscrito()); 
                datoRegistraduriaNacional.setFechaNacimientoREG(fechaNacimientoREG);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        datoRegistraduriaNacional.setGeneroREG(this.getSexoInscrito());
        datoRegistraduriaNacional.setDepartamentoInscripcion(this.getDptoInscripcionDesc());
        datoRegistraduriaNacional.setMunicipioNacimiento(this.getMpioNacDescInscrito());
        datoRegistraduriaNacional.setDepartamentoNacimiento(this.getDptoNacDescInscrito());
        datoRegistraduriaNacional.setTipoIdentificacionMadre(this.getTipoDocMadre());
        datoRegistraduriaNacional.setTipoIdentificacionPadre(this.getTipoDocPadre());
        datoRegistraduriaNacional.setNumeroIdentificacionMadre(this.getNumDocMadre());
        datoRegistraduriaNacional.setNumeroIdentificacionPadre(this.getNumDocPadre());
        datoRegistraduriaNacional.setPrimerApellidoMadre(this.getApellido1Madre());
        datoRegistraduriaNacional.setSegundoApellidoMadre(this.getApellido2Madre());
        datoRegistraduriaNacional.setPrimerNombreMadre(this.getNombre1Madre());
        datoRegistraduriaNacional.setSegundoNombreMadre(this.getNombre2Madre());
        datoRegistraduriaNacional.setPrimerApellidoPadre(this.getApellido1Padre());
        datoRegistraduriaNacional.setSegundoApellidoPadre(this.getApellido2Padre());
        datoRegistraduriaNacional.setPrimerNombrePadre(this.getNombre1Padre());
        datoRegistraduriaNacional.setSegundoNombrePadre(this.getNombre2Padre());
        if(this.getCorregNacDescInscrito() != null && this.getCorregNacDescInscrito().equals("")){
            datoRegistraduriaNacional.setCorrecionNacimiento(!this.getCorregNacDescInscrito().equals("")?this.getCorregNacDescInscrito():null);
        }
        datoRegistraduriaNacional.setOficinaInscripcion(this.getOficinaRegistroDesc());

        
        datoRegistraduriaNacional.setFechaInscripcion(this.getFechaInscripcion());
        datoRegistraduriaNacional.setEstadoREG(this.getEstadoDesc());
        datoRegistraduriaNacional.setSerial(this.getSerial());
        if(this.getFechaHoraConsulta() != null && !this.getFechaHoraConsulta().equals("")){
            try {
                Date fechaConsulta = new SimpleDateFormat("yyyy-MM-dd").parse(this.getFechaHoraConsulta()); 
                datoRegistraduriaNacional.setFechaConsulta(fechaConsulta);
                // TODO: handle exception
            }catch (Exception e) {
                // TODO: handle exception
            }
        }



        return datoRegistraduriaNacional;
    }

    public TipoIdentificacionEnum getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return this.numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getSerialDefuncion() {
        return this.serialDefuncion;
    }

    public void setSerialDefuncion(String serialDefuncion) {
        this.serialDefuncion = serialDefuncion;
    }
    

    public String getNuipnip() {
        return this.nuipnip;
    }

    public void setNuipnip(String nuipnip) {
        this.nuipnip = nuipnip;
    }

    public String getNumeroIdentificacionANI() {
        return this.numeroIdentificacionANI;
    }

    public void setNumeroIdentificacionANI(String numeroIdentificacionANI) {
        this.numeroIdentificacionANI = numeroIdentificacionANI;
    }

    public String getApellido1Inscrito() {
        return this.apellido1Inscrito;
    }

    public void setApellido1Inscrito(String apellido1Inscrito) {
        this.apellido1Inscrito = apellido1Inscrito;
    }

    public String getApellido2Inscrito() {
        return this.apellido2Inscrito;
    }

    public void setApellido2Inscrito(String apellido2Inscrito) {
        this.apellido2Inscrito = apellido2Inscrito;
    }

    public String getNombre1Inscrito() {
        return this.nombre1Inscrito;
    }

    public void setNombre1Inscrito(String nombre1Inscrito) {
        this.nombre1Inscrito = nombre1Inscrito;
    }

    public String getNombre2Inscrito() {
        return this.nombre2Inscrito;
    }

    public void setNombre2Inscrito(String nombre2Inscrito) {
        this.nombre2Inscrito = nombre2Inscrito;
    }

    public String getFechaNacInscrito() {
        return this.fechaNacInscrito;
    }

    public void setFechaNacInscrito(String fechaNacInscrito) {
        this.fechaNacInscrito = fechaNacInscrito;
    }

    public GeneroEnum getSexoInscrito() {
        return this.sexoInscrito;
    }

    public void setSexoInscrito(GeneroEnum sexoInscrito) {
        this.sexoInscrito = sexoInscrito;
    }

    public String getPaisNacDescInscrito() {
        return this.paisNacDescInscrito;
    }

    public void setPaisNacDescInscrito(String paisNacDescInscrito) {
        this.paisNacDescInscrito = paisNacDescInscrito;
    }

    public String getDptoNacDescInscrito() {
        return this.dptoNacDescInscrito;
    }

    public void setDptoNacDescInscrito(String dptoNacDescInscrito) {
        this.dptoNacDescInscrito = dptoNacDescInscrito;
    }

    public String getMpioNacDescInscrito() {
        return this.mpioNacDescInscrito;
    }

    public void setMpioNacDescInscrito(String mpioNacDescInscrito) {
        this.mpioNacDescInscrito = mpioNacDescInscrito;
    }

    public String getCorregNacDescInscrito() {
        return this.corregNacDescInscrito;
    }

    public void setCorregNacDescInscrito(String corregNacDescInscrito) {
        this.corregNacDescInscrito = corregNacDescInscrito;
    }

    public String getOficinaRegistroDesc() {
        return this.oficinaRegistroDesc;
    }

    public void setOficinaRegistroDesc(String oficinaRegistroDesc) {
        this.oficinaRegistroDesc = oficinaRegistroDesc;
    }

    public String getPaisInscripcionDesc() {
        return this.paisInscripcionDesc;
    }

    public void setPaisInscripcionDesc(String paisInscripcionDesc) {
        this.paisInscripcionDesc = paisInscripcionDesc;
    }

    public String getDptoInscripcionDesc() {
        return this.dptoInscripcionDesc;
    }

    public void setDptoInscripcionDesc(String dptoInscripcionDesc) {
        this.dptoInscripcionDesc = dptoInscripcionDesc;
    }

    public String getFechaInscripcion() {
        return this.fechaInscripcion;
    }

    public void setFechaInscripcion(String fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getApellido1Madre() {
        return this.apellido1Madre;
    }

    public void setApellido1Madre(String apellido1Madre) {
        this.apellido1Madre = apellido1Madre;
    }

    public String getApellido2Madre() {
        return this.apellido2Madre;
    }

    public void setApellido2Madre(String apellido2Madre) {
        this.apellido2Madre = apellido2Madre;
    }

    public String getNombre1Madre() {
        return this.nombre1Madre;
    }

    public void setNombre1Madre(String nombre1Madre) {
        this.nombre1Madre = nombre1Madre;
    }

    public String getNombre2Madre() {
        return this.nombre2Madre;
    }

    public void setNombre2Madre(String nombre2Madre) {
        this.nombre2Madre = nombre2Madre;
    }

    public TipoIdentificacionEnum getTipoDocMadre() {
        return this.tipoDocMadre;
    }

    public void setTipoDocMadre(TipoIdentificacionEnum tipoDocMadre) {
        this.tipoDocMadre = tipoDocMadre;
    }

    public String getNumDocMadre() {
        return this.numDocMadre;
    }

    public void setNumDocMadre(String numDocMadre) {
        this.numDocMadre = numDocMadre;
    }

    public String getNacion1DescMadre() {
        return this.nacion1DescMadre;
    }

    public void setNacion1DescMadre(String nacion1DescMadre) {
        this.nacion1DescMadre = nacion1DescMadre;
    }

    public String getApellido1Padre() {
        return this.apellido1Padre;
    }

    public void setApellido1Padre(String apellido1Padre) {
        this.apellido1Padre = apellido1Padre;
    }

    public String getApellido2Padre() {
        return this.apellido2Padre;
    }

    public void setApellido2Padre(String apellido2Padre) {
        this.apellido2Padre = apellido2Padre;
    }

    public String getNombre1Padre() {
        return this.nombre1Padre;
    }

    public void setNombre1Padre(String nombre1Padre) {
        this.nombre1Padre = nombre1Padre;
    }

    public String getNombre2Padre() {
        return this.nombre2Padre;
    }

    public void setNombre2Padre(String nombre2Padre) {
        this.nombre2Padre = nombre2Padre;
    }

    public TipoIdentificacionEnum getTipoDocPadre() {
        return this.tipoDocPadre;
    }

    public void setTipoDocPadre(TipoIdentificacionEnum tipoDocPadre) {
        this.tipoDocPadre = tipoDocPadre;
    }

    public String getNumDocPadre() {
        return this.numDocPadre;
    }

    public void setNumDocPadre(String numDocPadre) {
        this.numDocPadre = numDocPadre;
    }

    public String getNumeroControl() {
        return this.numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getCodError() {
        return this.codError;
    }

    public void setCodError(String codError) {
        this.codError = codError;
    }

    public String getPrimerApellido() {
        return this.primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return this.segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getDescripcionError() {
        return this.descripcionError;
    }

    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }

    public String getFechaHoraConsulta() {
        return this.fechaHoraConsulta;
    }

    public void setFechaHoraConsulta(String fechaHoraConsulta) {
        this.fechaHoraConsulta = fechaHoraConsulta;
    }

    public String getEstadoDesc() {
        return this.estadoDesc;
    }

    public void setEstadoDesc(String estadoDesc) {
        this.estadoDesc = estadoDesc;
    }

    public String getParticula() {
        return this.particula;
    }

    public void setParticula(String particula) {
        this.particula = particula;
    }

    public String getEstadoDescCedula() {
        return this.estadoDescCedula;
    }

    public void setEstadoDescCedula(String estadoDescCedula) {
        this.estadoDescCedula = estadoDescCedula;
    }

    public String getNumResolucion() {
        return this.numResolucion;
    }

    public void setNumResolucion(String numResolucion) {
        this.numResolucion = numResolucion;
    }

    public String getAnoResolucion() {
        return this.anoResolucion;
    }

    public void setAnoResolucion(String anoResolucion) {
        this.anoResolucion = anoResolucion;
    }

    public String getLugarNacimiento() {
        return this.lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public String getInformante() {
        return this.informante;
    }

    public void setInformante(String informante) {
        this.informante = informante;
    }

    public String getFechaExpedicionCedula() {
        return this.fechaExpedicionCedula;
    }

    public void setFechaExpedicionCedula(String fechaExpedicionCedula) {
        this.fechaExpedicionCedula = fechaExpedicionCedula;
    }

    public String getFechaDefuncion() {
        return this.fechaDefuncion;
    }

    public void setFechaDefuncion(String fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    public String getLugarNovedad() {
        return this.lugarNovedad;
    }

    public void setLugarNovedad(String lugarNovedad) {
        this.lugarNovedad = lugarNovedad;
    }

    public String getLugarPreparacion() {
        return this.lugarPreparacion;
    }

    public void setLugarPreparacion(String lugarPreparacion) {
        this.lugarPreparacion = lugarPreparacion;
    }

    public String getGrupoSanguineo() {
        return this.grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getEstatura() {
        return this.estatura;
    }

    public void setEstatura(String estatura) {
        this.estatura = estatura;
    }

    public String getMpioNacDescInscritoExpCedula() {
        return this.mpioNacDescInscritoExpCedula;
    }

    public void setMpioNacDescInscritoExpCedula(String mpioNacDescInscritoExpCedula) {
        this.mpioNacDescInscritoExpCedula = mpioNacDescInscritoExpCedula;
    }

    public String getDptoNacDescInscritoExpCedula() {
        return this.dptoNacDescInscritoExpCedula;
    }

    public void setDptoNacDescInscritoExpCedula(String dptoNacDescInscritoExpCedula) {
        this.dptoNacDescInscritoExpCedula = dptoNacDescInscritoExpCedula;
    }

    public GeneroEnum getGenero() {
        return this.genero;
    }

    public void setGenero(GeneroEnum genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "{" +
            " nuipnip='" + getNuipnip() + "'" +
            ", numeroIdentificacionANI='" + getNumeroIdentificacionANI() + "'" +
            ", apellido1Inscrito='" + getApellido1Inscrito() + "'" +
            ", apellido2Inscrito='" + getApellido2Inscrito() + "'" +
            ", nombre1Inscrito='" + getNombre1Inscrito() + "'" +
            ", nombre2Inscrito='" + getNombre2Inscrito() + "'" +
            ", fechaNacInscrito='" + getFechaNacInscrito() + "'" +
            ", sexoInscrito='" + getSexoInscrito() + "'" +
            ", paisNacDescInscrito='" + getPaisNacDescInscrito() + "'" +
            ", dptoNacDescInscrito='" + getDptoNacDescInscrito() + "'" +
            ", mpioNacDescInscrito='" + getMpioNacDescInscrito() + "'" +
            ", corregNacDescInscrito='" + getCorregNacDescInscrito() + "'" +
            ", oficinaRegistroDesc='" + getOficinaRegistroDesc() + "'" +
            ", paisInscripcionDesc='" + getPaisInscripcionDesc() + "'" +
            ", dptoInscripcionDesc='" + getDptoInscripcionDesc() + "'" +
            ", fechaInscripcion='" + getFechaInscripcion() + "'" +
            ", serial='" + getSerial() + "'" +
            ", apellido1Madre='" + getApellido1Madre() + "'" +
            ", apellido2Madre='" + getApellido2Madre() + "'" +
            ", nombre1Madre='" + getNombre1Madre() + "'" +
            ", nombre2Madre='" + getNombre2Madre() + "'" +
            ", tipoDocMadre='" + getTipoDocMadre() + "'" +
            ", numDocMadre='" + getNumDocMadre() + "'" +
            ", nacion1DescMadre='" + getNacion1DescMadre() + "'" +
            ", apellido1Padre='" + getApellido1Padre() + "'" +
            ", apellido2Padre='" + getApellido2Padre() + "'" +
            ", nombre1Padre='" + getNombre1Padre() + "'" +
            ", nombre2Padre='" + getNombre2Padre() + "'" +
            ", tipoDocPadre='" + getTipoDocPadre() + "'" +
            ", numDocPadre='" + getNumDocPadre() + "'" +
            ", numeroControl='" + getNumeroControl() + "'" +
            ", codError='" + getCodError() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", descripcionError='" + getDescripcionError() + "'" +
            ", fechaHoraConsulta='" + getFechaHoraConsulta() + "'" +
            ", estadoDesc='" + getEstadoDesc() + "'" +
            ", particula='" + getParticula() + "'" +
            ", estadoDescCedula='" + getEstadoDescCedula() + "'" +
            ", numResolucion='" + getNumResolucion() + "'" +
            ", anoResolucion='" + getAnoResolucion() + "'" +
            ", lugarNacimiento='" + getLugarNacimiento() + "'" +
            ", informante='" + getInformante() + "'" +
            ", fechaExpedicionCedula='" + getFechaExpedicionCedula() + "'" +
            ", fechaDefuncion='" + getFechaDefuncion() + "'" +
            ", lugarNovedad='" + getLugarNovedad() + "'" +
            ", lugarPreparacion='" + getLugarPreparacion() + "'" +
            ", grupoSanguineo='" + getGrupoSanguineo() + "'" +
            ", estatura='" + getEstatura() + "'" +
            ", mpioNacDescInscritoExpCedula='" + getMpioNacDescInscritoExpCedula() + "'" +
            ", dptoNacDescInscritoExpCedula='" + getDptoNacDescInscritoExpCedula() + "'" +
            ", genero='" + getGenero() + "'" + 
            "}";
    }
  

}