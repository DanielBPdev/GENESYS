package com.asopagos.aportes.masivos.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import com.asopagos.enumeraciones.FormaPresentacionEnum;
import com.asopagos.enumeraciones.TipoPersonaEnum;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.enumeraciones.aportes.ModalidadPlanillaEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.NaturalezaJuridicaEnum;


@XmlRootElement
public class InformacionPagoMasivoDTO implements Serializable{
    /**
     * Atribuo que indica el id de la solicitud correspondiente a la informacion del pago.
     */
    private Long idSolicitud;
    /**
     * Atributo que indica si el aportante se acoge a la ley 1429.
     */
    private Boolean aportanteAcogeLey1429;
    /**
     * Atributo que incida la clase de aportante.
     */
    private ClaseAportanteEnum claseAportante;
    /**
     * Atributo que contiene el codigo del departamento.
     */
    private String codigoDepartamento;
    /**
     * Atributo que contiene el código del municipio.
     */
    private String codigoMunicipio;
    /**
     * Atributo que contiene el código del operador.
     */
    private Long codigoOperador;
    /**
     * Atributo que contiene el código de la sucursal.
     */
    private String codigoSucursal;
    /**
     * Atributo que contiene el correo electrónico.
     */
    private String correoElectronico;
    /**
     * Atributo que contiene los días de mora
     */
    private Long diasMora;
    /**
     * Atributo que contiene la dirección del aportante.
     */
    private String direccion;
    /**
     * Atributo que contiene la descripción de la ubicación del aportante.
     */
    private String descripcionIndicacion;
    /**
     * Atributo que contiene el estado del aportante.
     */
    private EstadoEmpleadorEnum estadoAportante;
    /**
     * Atributo que contiene el fax.
     */
    private String fax;
    /**
     * Atributo que continee el indicativo del fax.
     */
    private String faxInd;
    /**
     * Atributo que contiene la fecha de la matricula mercantil.
     */
    private Long fechaMatriculaMercantil;
    /**
     * Atributo que contiene la fecha del pago.
     */
    private Long fechaPagoPlantilla;
    /**
     * Atributo que contiene la forma de presentación.
     */
    private FormaPresentacionEnum formaPresentacion;
    /**
     * Atributo que contiene la modalidad de la plantilla.
     */
    private ModalidadPlanillaEnum modalidadPlantilla;
    /**
     * Atributo que contiene la naturaleza jurídica.
     */
    private NaturalezaJuridicaEnum naturalezaJuridica;
    /**
     * Atributo que contiene el nombre de la sucursal.
     */
    private String nombreSucursal;
    /**
     * Atributo que contiene el número de afiliados.
     */
    private Long numeroAfiliadosAdministradora;
    /**
     * Atributo que contiene el número de empleados.
     */
    private Long numeroEmpleados;
    /**
     * Atributo que contiene el número de la planilla.
     */
    private Long numeroPlanilla;
    /**
     * Atributo que contiene el número de la radicación.
     */
    private String numeroRadicacion;
    /**
     * Atributo que contiene el número de registros de salida tipo 2.
     */
    private Long numeroRegistroSalida2;
    /**
     * Atributo que contiene el teléfono.
     */
    private String telefono;
    /**
     * Atributo que contiene el indicativo del teléfono.
     */
    private String telefonoInd;
    /**
     * Atributo que contiene el tipo de persona.
     */
    private TipoPersonaEnum tipoPersona;
    /**
     * Atributo que contiene el tipo de planilla.
     */
    private TipoPlanillaEnum tipoPlanilla;
    /**
     * Documentos asociados a la informacion de pago.
     */
    private List<DocumentoAdministracionEstadoSolicitudDTO> documentos;
}
