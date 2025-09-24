//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.03.03 a las 03:34:47 PM COT 
//


package com.asopagos.reportes.xsd;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="T_CONSOLIDADO_HISTORICO_ASIGNACIONES_PAGOS_REINTEGROS_ANUAL_2017C01">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ANIO_VIGENCIA_ASIGNACION_SUBSIDIO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="4"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="COD_TIPO_PLAN_VIVIENDA">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="EST_SUBSIDIO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="CAN_SUBSIDIOS">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="10"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="VAL_TOTAL_SUBSIDIOS">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="18"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tconsolidadohistoricoasignacionespagosreintegrosanual2017C01"
})
@XmlRootElement(name = "CONSOLIDADO_HISTORICO_ASIGNACIONES_PAGOS_REINTEGROS_ANUAL_2017C01")
public class CONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01 {

    @XmlElement(name = "T_CONSOLIDADO_HISTORICO_ASIGNACIONES_PAGOS_REINTEGROS_ANUAL_2017C01")
    protected List<CONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01 .TCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01> tconsolidadohistoricoasignacionespagosreintegrosanual2017C01;

    /**
     * Gets the value of the tconsolidadohistoricoasignacionespagosreintegrosanual2017C01 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tconsolidadohistoricoasignacionespagosreintegrosanual2017C01 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01 .TCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01 }
     * 
     * 
     */
    public List<CONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01 .TCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01> getTCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01() {
        if (tconsolidadohistoricoasignacionespagosreintegrosanual2017C01 == null) {
            tconsolidadohistoricoasignacionespagosreintegrosanual2017C01 = new ArrayList<CONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01 .TCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01>();
        }
        return this.tconsolidadohistoricoasignacionespagosreintegrosanual2017C01;
    }
    
    public CONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01 withTCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01(CONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01. TCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01 ... values) {
        if (values!= null) {
            for (CONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01 .TCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01 value: values) {
                getTCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01().add(value);
            }
        }
        return this;
    }

    public CONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01 withTCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01(Collection<CONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01 .TCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01> values) {
        if (values!= null) {
            getTCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01().addAll(values);
        }
        return this;
    }

    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ANIO_VIGENCIA_ASIGNACION_SUBSIDIO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="4"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="COD_TIPO_PLAN_VIVIENDA">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="EST_SUBSIDIO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="CAN_SUBSIDIOS">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="10"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="VAL_TOTAL_SUBSIDIOS">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="18"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "aniovigenciaasignacionsubsidio",
        "codtipoplanvivienda",
        "estsubsidio",
        "cansubsidios",
        "valtotalsubsidios"
    })
    public static class TCONSOLIDADOHISTORICOASIGNACIONESPAGOSREINTEGROSANUAL2017C01 {

        @XmlElement(name = "ANIO_VIGENCIA_ASIGNACION_SUBSIDIO", required = true)
        protected String aniovigenciaasignacionsubsidio;
        @XmlElement(name = "COD_TIPO_PLAN_VIVIENDA", required = true)
        protected String codtipoplanvivienda;
        @XmlElement(name = "EST_SUBSIDIO", required = true)
        protected String estsubsidio;
        @XmlElement(name = "CAN_SUBSIDIOS", required = true)
        protected String cansubsidios;
        @XmlElement(name = "VAL_TOTAL_SUBSIDIOS", required = true)
        protected String valtotalsubsidios;

        /**
         * Obtiene el valor de la propiedad aniovigenciaasignacionsubsidio.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getANIOVIGENCIAASIGNACIONSUBSIDIO() {
            return aniovigenciaasignacionsubsidio;
        }

        /**
         * Define el valor de la propiedad aniovigenciaasignacionsubsidio.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setANIOVIGENCIAASIGNACIONSUBSIDIO(String value) {
            this.aniovigenciaasignacionsubsidio = value;
        }

        /**
         * Obtiene el valor de la propiedad codtipoplanvivienda.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCODTIPOPLANVIVIENDA() {
            return codtipoplanvivienda;
        }

        /**
         * Define el valor de la propiedad codtipoplanvivienda.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCODTIPOPLANVIVIENDA(String value) {
            this.codtipoplanvivienda = value;
        }

        /**
         * Obtiene el valor de la propiedad estsubsidio.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getESTSUBSIDIO() {
            return estsubsidio;
        }

        /**
         * Define el valor de la propiedad estsubsidio.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setESTSUBSIDIO(String value) {
            this.estsubsidio = value;
        }

        /**
         * Obtiene el valor de la propiedad cansubsidios.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCANSUBSIDIOS() {
            return cansubsidios;
        }

        /**
         * Define el valor de la propiedad cansubsidios.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCANSUBSIDIOS(String value) {
            this.cansubsidios = value;
        }

        /**
         * Obtiene el valor de la propiedad valtotalsubsidios.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVALTOTALSUBSIDIOS() {
            return valtotalsubsidios;
        }

        /**
         * Define el valor de la propiedad valtotalsubsidios.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVALTOTALSUBSIDIOS(String value) {
            this.valtotalsubsidios = value;
        }

    }

}
