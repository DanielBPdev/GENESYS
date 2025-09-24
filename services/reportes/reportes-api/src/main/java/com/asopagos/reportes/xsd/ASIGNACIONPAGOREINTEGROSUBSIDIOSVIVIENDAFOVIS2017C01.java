//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.08.15 a las 09:45:24 AM COT 
//


package com.asopagos.reportes.xsd;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="T_ASIGNACION_PAGO_REINTEGRO_SUBSIDIOS_VIVIENDA_FOVIS_2017C01" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ANIO_VIGENCIA"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="4"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="FUE_FINANCIAMIENTO"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="1"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="COD_TIPO_VIVIENDA"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="1"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="COD_MUNICIPIO_DANE"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;maxLength value="5"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="GENERO_CCF"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="1"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="RAN_EDAD"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="2"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="NIV_INGRESO"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="2"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="COM_VIVIENDA"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="1"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="EST_SUBSIDIO_VIVIENDA"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="1"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="CAN_SUBSIDIOS_ASIGNADOS"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="10"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="VALSUBSIDIOS_ASIGNADOS"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *                         &lt;totalDigits value="18"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tasignacionpagoreintegrosubsidiosviviendafovis2017C01"
})
@XmlRootElement(name = "ASIGNACION_PAGO_REINTEGRO_SUBSIDIOS_VIVIENDA_FOVIS_2017C01")
public class ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 {

    @XmlElement(name = "T_ASIGNACION_PAGO_REINTEGRO_SUBSIDIOS_VIVIENDA_FOVIS_2017C01", required = true)
    protected List<ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01> tasignacionpagoreintegrosubsidiosviviendafovis2017C01;

    /**
     * Gets the value of the tasignacionpagoreintegrosubsidiosviviendafovis2017C01 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tasignacionpagoreintegrosubsidiosviviendafovis2017C01 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 }
     * 
     * 
     */
    public List<ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01> getTASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01() {
        if (tasignacionpagoreintegrosubsidiosviviendafovis2017C01 == null) {
            tasignacionpagoreintegrosubsidiosviviendafovis2017C01 = new ArrayList<ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01>();
        }
        return this.tasignacionpagoreintegrosubsidiosviviendafovis2017C01;
    }

    public ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 withTASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01(ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 ... values) {
        if (values!= null) {
            for (ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 value: values) {
                getTASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01().add(value);
            }
        }
        return this;
    }

    public ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 withTASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01(Collection<ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01> values) {
        if (values!= null) {
            getTASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01().addAll(values);
        }
        return this;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="ANIO_VIGENCIA"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="4"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="FUE_FINANCIAMIENTO"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="1"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="COD_TIPO_VIVIENDA"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="1"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="COD_MUNICIPIO_DANE"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;maxLength value="5"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="GENERO_CCF"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="1"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="RAN_EDAD"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="2"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="NIV_INGRESO"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="2"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="COM_VIVIENDA"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="1"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="EST_SUBSIDIO_VIVIENDA"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="1"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="CAN_SUBSIDIOS_ASIGNADOS"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="10"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="VALSUBSIDIOS_ASIGNADOS"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
     *               &lt;totalDigits value="18"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
    	"aniovigencia",
        "fuefinanciamiento",
        "codtipovivienda",
        "codmunicipiodane",
        "generoccf",
        "ranedad",
        "nivingreso",
        "comvivienda",
        "estsubsidiovivienda",        
        "cansubsidiosasignados",
        "valsubsidiosasignados"
    })
    public static class TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 {

        @XmlElement(name = "ANIO_VIGENCIA_ASIGNACION_SUB", required = true)
        protected String aniovigencia;
        @XmlElement(name = "FUE_FINANCIAMIENTO", required = true)
        protected String fuefinanciamiento;
        @XmlElement(name = "COD_TIPO_VIVIENDA", required = true)
        protected String codtipovivienda;
        @XmlElement(name = "COD_MUNICIPIO_DANE", required = true)
        protected String codmunicipiodane;
        @XmlElement(name = "GENERO_CCF", required = true)
        protected String generoccf;
        @XmlElement(name = "RAN_EDAD", required = true)
        protected String ranedad;
        @XmlElement(name = "NIV_INGRESO", required = true)
        protected String nivingreso;
        @XmlElement(name = "COM_VIVIENDA", required = true)
        protected String comvivienda;
        @XmlElement(name = "EST_SUBSIDIO_VIVIENDA", required = true)
        protected String estsubsidiovivienda;
        @XmlElement(name = "CAN_SUBSIDIOS_ASIGNADOS", required = true)
        protected String cansubsidiosasignados;
        @XmlElement(name = "VALSUBSIDIOS_ASIGNADOS", required = true)
        protected String valsubsidiosasignados;

        /**
         * Obtiene el valor de la propiedad aniovigencia.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getANIOVIGENCIA() {
            return aniovigencia;
        }

        /**
         * Define el valor de la propiedad aniovigencia.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setANIOVIGENCIA(String value) {
            this.aniovigencia = value;
        }

        /**
         * Obtiene el valor de la propiedad fuefinanciamiento.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getFUEFINANCIAMIENTO() {
            return fuefinanciamiento;
        }

        /**
         * Define el valor de la propiedad fuefinanciamiento.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setFUEFINANCIAMIENTO(String value) {
            this.fuefinanciamiento = value;
        }

        /**
         * Obtiene el valor de la propiedad codtipovivienda.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getCODTIPOVIVIENDA() {
            return codtipovivienda;
        }

        /**
         * Define el valor de la propiedad codtipovivienda.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setCODTIPOVIVIENDA(String value) {
            this.codtipovivienda = value;
        }

        /**
         * Obtiene el valor de la propiedad codmunicipiodane.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCODMUNICIPIODANE() {
            return codmunicipiodane;
        }

        /**
         * Define el valor de la propiedad codmunicipiodane.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCODMUNICIPIODANE(String value) {
            this.codmunicipiodane = value;
        }

        /**
         * Obtiene el valor de la propiedad generoccf.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getGENEROCCF() {
            return generoccf;
        }

        /**
         * Define el valor de la propiedad generoccf.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setGENEROCCF(String value) {
            this.generoccf = value;
        }

        /**
         * Obtiene el valor de la propiedad ranedad.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getRANEDAD() {
            return ranedad;
        }

        /**
         * Define el valor de la propiedad ranedad.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setRANEDAD(String value) {
            this.ranedad = value;
        }

        /**
         * Obtiene el valor de la propiedad nivingreso.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getNIVINGRESO() {
            return nivingreso;
        }

        /**
         * Define el valor de la propiedad nivingreso.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNIVINGRESO(String value) {
            this.nivingreso = value;
        }

        /**
         * Obtiene el valor de la propiedad comvivienda.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getCOMVIVIENDA() {
            return comvivienda;
        }

        /**
         * Define el valor de la propiedad comvivienda.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setCOMVIVIENDA(String value) {
            this.comvivienda = value;
        }

        /**
         * Obtiene el valor de la propiedad estsubsidiovivienda.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getESTSUBSIDIOVIVIENDA() {
            return estsubsidiovivienda;
        }

        /**
         * Define el valor de la propiedad estsubsidiovivienda.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setESTSUBSIDIOVIVIENDA(String value) {
            this.estsubsidiovivienda = value;
        }

        /**
         * Obtiene el valor de la propiedad cansubsidiosasignados.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getCANSUBSIDIOSASIGNADOS() {
            return cansubsidiosasignados;
        }

        /**
         * Define el valor de la propiedad cansubsidiosasignados.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setCANSUBSIDIOSASIGNADOS(String value) {
            this.cansubsidiosasignados = value;
        }

        /**
         * Obtiene el valor de la propiedad valsubsidiosasignados.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public String getVALSUBSIDIOSASIGNADOS() {
            return valsubsidiosasignados;
        }

        /**
         * Define el valor de la propiedad valsubsidiosasignados.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setVALSUBSIDIOSASIGNADOS(String value) {
            this.valsubsidiosasignados = value;
        }

        public ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 withANIOVIGENCIA(String value) {
            setANIOVIGENCIA(value);
            return this;
        }

        public ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 withFUEFINANCIAMIENTO(String value) {
            setFUEFINANCIAMIENTO(value);
            return this;
        }

        public ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 withCODTIPOVIVIENDA(String value) {
            setCODTIPOVIVIENDA(value);
            return this;
        }

        public ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 withCODMUNICIPIODANE(String value) {
            setCODMUNICIPIODANE(value);
            return this;
        }

        public ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 withGENEROCCF(String value) {
            setGENEROCCF(value);
            return this;
        }

        public ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 withRANEDAD(String value) {
            setRANEDAD(value);
            return this;
        }

        public ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 withNIVINGRESO(String value) {
            setNIVINGRESO(value);
            return this;
        }

        public ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 withCOMVIVIENDA(String value) {
            setCOMVIVIENDA(value);
            return this;
        }

        public ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 withESTSUBSIDIOVIVIENDA(String value) {
            setESTSUBSIDIOVIVIENDA(value);
            return this;
        }

        public ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 withCANSUBSIDIOSASIGNADOS(String value) {
            setCANSUBSIDIOSASIGNADOS(value);
            return this;
        }

        public ASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 .TASIGNACIONPAGOREINTEGROSUBSIDIOSVIVIENDAFOVIS2017C01 withVALSUBSIDIOSASIGNADOS(String value) {
            setVALSUBSIDIOSASIGNADOS(value);
            return this;
        }

    }

}
