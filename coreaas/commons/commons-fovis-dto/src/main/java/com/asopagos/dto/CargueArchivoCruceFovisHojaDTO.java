package com.asopagos.dto;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import com.asopagos.constants.QueriesConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Super clase para los DTO que componen el archivo de cruce externo
 * @author jocorrea
 *
 */
public abstract class CargueArchivoCruceFovisHojaDTO {

    /**
     * Identificador
     */
    protected Long id;

    /**
     * Nit entidad
     */
    protected String nitEntidad;

    /**
     * Nombre entidad
     */
    protected String nombreEntidad;

    /**
     * Identificacion
     */
    protected String identificacion;

    /**
     * Apellidos
     */
    protected String apellidos;

    /**
     * Nombres
     */
    protected String nombres;

    /**
     * Identificador Cargue Archivo Cruce Fovis
     */
    protected Long idCargueArchivoCruceFovis;

    /**
     * Cedula catastral
     */
    protected String cedulaCatastral;

    /**
     * Direccion inmueble
     */
    protected String direccionInmueble;

    /**
     * Matricula inmobiliaria
     */
    protected String matriculaInmobiliaria;

    /**
     * Departamento
     */
    protected String departamento;

    /**
     * Municipio
     */
    protected String municipio;

    /**
     * Tipo informacion
     */
    protected String tipoInformacion;

    /**
     * Fecha corte
     */
    protected Date fechaCorte;

    /**
     * Fecha actualizacion
     */
    protected Date fechaActualizacion;

    /**
     * Apellidos nombres
     */
    protected String apellidosNombres;

    /**
     * Fecha solicitud
     */
    protected Date fechaSolicitud;

    /**
     * Entidad otorgante
     */
    protected String entidadOtorgante;

    /**
     * Caja compensacion
     */
    protected String cajaCompensacion;

    /**
     * Asignado posterior reporte
     */
    protected String asignadoPosteriorReporte;

    /**
     * Documento
     */
    protected String documento;

    /**
     * Tipo documento
     */
    protected String tipoDocumento;

    /**
     * Puntaje
     */
    protected String puntaje;

    /**
     * Sexo
     */
    protected String sexo;

    /**
     * Zona
     */
    protected String zona;

    /**
     * Parentesco
     */
    protected String parentesco;

    /**
     * Folio
     */
    protected String folio;

    /**
     * Direccion
     */
    protected String direccion;

    /**
     * Numero cedula
     */
    protected String nroCedula;

    /**
     * Fecha asignacion
     */
    protected Date fechaAsignacion;

    /**
     * Valor asignado
     */
    protected String valorAsignado;

    /**
     * Codigo tipo de documento
     */
    protected String codTipoDocumento;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *        the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the nitEntidad
     */
    public String getNitEntidad() {
        return nitEntidad;
    }

    /**
     * @param nitEntidad
     *        the nitEntidad to set
     */
    public void setNitEntidad(String nitEntidad) {
        this.nitEntidad = nitEntidad;
    }

    /**
     * @return the nombreEntidad
     */
    public String getNombreEntidad() {
        return nombreEntidad;
    }

    /**
     * @param nombreEntidad
     *        the nombreEntidad to set
     */
    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    /**
     * @return the identificacion
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion
     *        the identificacion to set
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos
     *        the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres
     *        the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the idCargueArchivoCruceFovis
     */
    public Long getIdCargueArchivoCruceFovis() {
        return idCargueArchivoCruceFovis;
    }

    /**
     * @param idCargueArchivoCruceFovis
     *        the idCargueArchivoCruceFovis to set
     */
    public void setIdCargueArchivoCruceFovis(Long idCargueArchivoCruceFovis) {
        this.idCargueArchivoCruceFovis = idCargueArchivoCruceFovis;
    }

    /**
     * @return the cedulaCatastral
     */
    public String getCedulaCatastral() {
        return cedulaCatastral;
    }

    /**
     * @param cedulaCatastral
     *        the cedulaCatastral to set
     */
    public void setCedulaCatastral(String cedulaCatastral) {
        this.cedulaCatastral = cedulaCatastral;
    }

    /**
     * @return the direccionInmueble
     */
    public String getDireccionInmueble() {
        return direccionInmueble;
    }

    /**
     * @param direccionInmueble
     *        the direccionInmueble to set
     */
    public void setDireccionInmueble(String direccionInmueble) {
        this.direccionInmueble = direccionInmueble;
    }

    /**
     * @return the matriculaInmobiliaria
     */
    public String getMatriculaInmobiliaria() {
        return matriculaInmobiliaria;
    }

    /**
     * @param matriculaInmobiliaria
     *        the matriculaInmobiliaria to set
     */
    public void setMatriculaInmobiliaria(String matriculaInmobiliaria) {
        this.matriculaInmobiliaria = matriculaInmobiliaria;
    }

    /**
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento
     *        the departamento to set
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio
     *        the municipio to set
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the tipoInformacion
     */
    public String getTipoInformacion() {
        return tipoInformacion;
    }

    /**
     * @param tipoInformacion
     *        the tipoInformacion to set
     */
    public void setTipoInformacion(String tipoInformacion) {
        this.tipoInformacion = tipoInformacion;
    }

    /**
     * @return the fechaCorte
     */
    public Date getFechaCorte() {
        return fechaCorte;
    }

    /**
     * @param fechaCorte
     *        the fechaCorte to set
     */
    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    /**
     * @return the fechaActualizacion
     */
    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * @param fechaActualizacion
     *        the fechaActualizacion to set
     */
    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * @return the apellidosNombres
     */
    public String getApellidosNombres() {
        return apellidosNombres;
    }

    /**
     * @param apellidosNombres
     *        the apellidosNombres to set
     */
    public void setApellidosNombres(String apellidosNombres) {
        this.apellidosNombres = apellidosNombres;
    }

    /**
     * @return the fechaSolicitud
     */
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * @param fechaSolicitud
     *        the fechaSolicitud to set
     */
    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * @return the entidadOtorgante
     */
    public String getEntidadOtorgante() {
        return entidadOtorgante;
    }

    /**
     * @param entidadOtorgante
     *        the entidadOtorgante to set
     */
    public void setEntidadOtorgante(String entidadOtorgante) {
        this.entidadOtorgante = entidadOtorgante;
    }

    /**
     * @return the cajaCompensacion
     */
    public String getCajaCompensacion() {
        return cajaCompensacion;
    }

    /**
     * @param cajaCompensacion
     *        the cajaCompensacion to set
     */
    public void setCajaCompensacion(String cajaCompensacion) {
        this.cajaCompensacion = cajaCompensacion;
    }

    /**
     * @return the asignadoPosteriorReporte
     */
    public String getAsignadoPosteriorReporte() {
        return asignadoPosteriorReporte;
    }

    /**
     * @param asignadoPosteriorReporte
     *        the asignadoPosteriorReporte to set
     */
    public void setAsignadoPosteriorReporte(String asignadoPosteriorReporte) {
        this.asignadoPosteriorReporte = asignadoPosteriorReporte;
    }

    /**
     * @return the documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * @param documento
     *        the documento to set
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * @return the tipoDocumento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento
     *        the tipoDocumento to set
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @return the puntaje
     */
    public String getPuntaje() {
        return puntaje;
    }

    /**
     * @param puntaje
     *        the puntaje to set
     */
    public void setPuntaje(String puntaje) {
        this.puntaje = puntaje;
    }

    /**
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo
     *        the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the zona
     */
    public String getZona() {
        return zona;
    }

    /**
     * @param zona
     *        the zona to set
     */
    public void setZona(String zona) {
        this.zona = zona;
    }

    /**
     * @return the parentesco
     */
    public String getParentesco() {
        return parentesco;
    }

    /**
     * @param parentesco
     *        the parentesco to set
     */
    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    /**
     * @return the folio
     */
    public String getFolio() {
        return folio;
    }

    /**
     * @param folio
     *        the folio to set
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion
     *        the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the nroCedula
     */
    public String getNroCedula() {
        return nroCedula;
    }

    /**
     * @param nroCedula
     *        the nroCedula to set
     */
    public void setNroCedula(String nroCedula) {
        this.nroCedula = nroCedula;
    }

    /**
     * @return the fechaAsignacion
     */
    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    /**
     * @param fechaAsignacion
     */
    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    /**
     * @return the valorAsignado
     */
    public String getValorAsignado() {
        return valorAsignado;
    }

    /**
     * @param valorAsignado
     */
    public void setValorAsignado(String valorAsignado) {
        this.valorAsignado = valorAsignado;
    }

    /**
     * @return the tipoIdentificacion
     */
    public String getCodTipoDocumento() {
        return codTipoDocumento;
    }

    /**
     * @param codTipoDocumento
     */
    public void setCodTipoDocumento(String codTipoDocumento) {
        this.codTipoDocumento = codTipoDocumento;
    }

    /**
     * Convierte la informacion leida del archivo en el tipo de hoja correcto
     * @param infoHoja
     *        Informacion hoja leida
     * @return DTO tipo hoja archivo
     */
    public abstract void convertHojaToDTO(InformacionHojaCruceFovisDTO infoHoja);

    /**
     * Realiza la conversion del DTO a la entidad
     * @return Entidad
     */
    public abstract Object convertToEntity();

    /**
     * Obtiene el nombre de la tabla que representa
     * @return Nombre tabla
     */
    @JsonIgnore
    public abstract String getTableName();

    /**
     * Obtiene la lista de columnas de la tabla
     * @return Lista columnas
     */
    @JsonIgnore
    public abstract List<String> getColumnsName();

    /**
     * Obtiene el nombre de la secuencia de la tabla
     * @return Nombre secuencia
     */
    @JsonIgnore
    public abstract String getSequenceName();

    /**
     * Obtiene la estructura del insert para cada tabla con el nombre de los campos que aplica
     * @return Cadena de texto con la instruccion insert para la tabla
     */
    @JsonIgnore
    public String getInsertStructure() {
        StringBuffer sqlInsert = new StringBuffer();
        sqlInsert.append(QueriesConstants.INSERT_INTO_CLAUSE);
        sqlInsert.append(this.getTableName());
        sqlInsert.append(QueriesConstants.LEFT_PARENTHESIS_SYMBOL);
        List<String> columns = this.getColumnsName();
        int size = columns.size();
        for (int i = 0; i < size; i++) {
            sqlInsert.append(columns.get(i));
            if (i < (size - 1)) {
                sqlInsert.append(QueriesConstants.COMMA_SYMBOL);
            }
        }
        sqlInsert.append(QueriesConstants.RIGHT_PARENTHESIS_SYMBOL);
        sqlInsert.append(QueriesConstants.VALUES_CLAUSE);
        sqlInsert.append(QueriesConstants.LEFT_PARENTHESIS_SYMBOL);
        sqlInsert.append(QueriesConstants.NEXT_VALUE_FOR_CLAUSE);
        sqlInsert.append(this.getSequenceName());
        sqlInsert.append(QueriesConstants.COMMA_SYMBOL);
        for (int i = 1; i < size; i++) {
            sqlInsert.append(QueriesConstants.INTERROGATION_SYMBOL);
            if (i < (size - 1)) {
                sqlInsert.append(QueriesConstants.COMMA_SYMBOL);
            }
        }
        sqlInsert.append(QueriesConstants.RIGHT_PARENTHESIS_SYMBOL);
        return sqlInsert.toString();
    }

    /**
     * Agrega el valor de los campos al insert para la ejecucion del mismo
     * @param statement
     *        Sentencia en ejecucion
     * @throws Exception
     *         Lanzada en caso que se presente un error
     */
    public abstract void processStatement(PreparedStatement statement) throws Exception;

}