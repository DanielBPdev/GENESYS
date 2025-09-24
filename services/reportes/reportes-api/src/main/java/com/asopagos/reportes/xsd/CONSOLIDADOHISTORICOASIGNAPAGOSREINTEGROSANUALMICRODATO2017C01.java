//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.03.04 a las 05:02:44 PM COT 
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
 *         &lt;element name="T_CONSOLIDADO_HISTORICO_ASIGNA_PAGOS_REINTEGROS_ANUAL_MICRODATO_2017C01">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TIP_IDENTIFICACION_AFILIADO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;totalDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="NUM_IDENTIFICA_AFILIADO">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="16"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
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
 *                   &lt;element name="VAL_SUBSIDIO">
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
    "tconsolidadohistoricoasignapagosreintegrosanualmicrodato2017C01"
})
@XmlRootElement(name = "CONSOLIDADO_HISTORICO_ASIGNA_PAGOS_REINTEGROS_ANUAL_MICRODATO_2017C01")
public class CONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01 {

    @XmlElement(name = "T_CONSOLIDADO_HISTORICO_ASIGNA_PAGOS_REINTEGROS_ANUAL_MICRODATO_2017C01")
    protected List<CONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01 .TCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01> tconsolidadohistoricoasignapagosreintegrosanualmicrodato2017C01;

    /**
     * Gets the value of the tconsolidadohistoricoasignapagosreintegrosanualmicrodato2017C01 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tconsolidadohistoricoasignapagosreintegrosanualmicrodato2017C01 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01 .TCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01 }
     * 
     * 
     */
    public List<CONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01 .TCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01> getTCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01() {
        if (tconsolidadohistoricoasignapagosreintegrosanualmicrodato2017C01 == null) {
            tconsolidadohistoricoasignapagosreintegrosanualmicrodato2017C01 = new ArrayList<CONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01 .TCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01>();
        }
        return this.tconsolidadohistoricoasignapagosreintegrosanualmicrodato2017C01;
    }

    public CONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01 withTCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01(CONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01. TCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01 ... values) {
        if (values!= null) {
            for (CONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01 .TCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01 value: values) {
                getTCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01().add(value);
            }
        }
        return this;
    }

    public CONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01 withTCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01(Collection<CONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01 .TCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01> values) {
        if (values!= null) {
            getTCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01().addAll(values);
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
     *         &lt;element name="TIP_IDENTIFICACION_AFILIADO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;totalDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="NUM_IDENTIFICA_AFILIADO">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="16"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
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
     *         &lt;element name="VAL_SUBSIDIO">
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
        "tipidentificacionafiliado",
        "numidentificaafiliado",
        "aniovigenciaasignacionsubsidio",
        "codtipoplanvivienda",
        "estsubsidio",
        "valsubsidio"
    })
    public static class TCONSOLIDADOHISTORICOASIGNAPAGOSREINTEGROSANUALMICRODATO2017C01 {

        @XmlElement(name = "TIP_IDENTIFICACION_AFILIADO", required = true)
        protected String tipidentificacionafiliado;
        @XmlElement(name = "NUM_IDENTIFICA_AFILIADO", required = true)
        protected String numidentificaafiliado;
        @XmlElement(name = "ANIO_VIGENCIA_ASIGNACION_SUBSIDIO", required = true)
        protected String aniovigenciaasignacionsubsidio;
        @XmlElement(name = "COD_TIPO_PLAN_VIVIENDA", required = true)
        protected String codtipoplanvivienda;
        @XmlElement(name = "EST_SUBSIDIO", required = true)
        protected String estsubsidio;
        @XmlElement(name = "VAL_SUBSIDIO", required = true)
        protected String valsubsidio;

        /**
         * Obtiene el valor de la propiedad tipidentificacionafiliado.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTIPIDENTIFICACIONAFILIADO() {
            return tipidentificacionafiliado;
        }

        /**
         * Define el valor de la propiedad tipidentificacionafiliado.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTIPIDENTIFICACIONAFILIADO(String value) {
            this.tipidentificacionafiliado = value;
        }

        /**
         * Obtiene el valor de la propiedad numidentificaafiliado.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNUMIDENTIFICAAFILIADO() {
            return numidentificaafiliado;
        }

        /**
         * Define el valor de la propiedad numidentificaafiliado.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNUMIDENTIFICAAFILIADO(String value) {
            this.numidentificaafiliado = value;
        }

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
         * Obtiene el valor de la propiedad valsubsidio.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVALSUBSIDIO() {
            return valsubsidio;
        }

        /**
         * Define el valor de la propiedad valsubsidio.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVALSUBSIDIO(String value) {
            this.valsubsidio = value;
        }

    }

}
