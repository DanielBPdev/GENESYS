package com.asopagos.pila.validadores.bloque5.ejb;

import com.asopagos.entidades.ccf.afiliaciones.PreRegistroEmpresaDesCentralizada;
import java.io.Serializable;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.asopagos.entidades.ccf.aportes.ListasBlancasAportantes;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIPRegistro1;
import com.asopagos.entidades.pila.archivolinea.PilaArchivoIRegistro1;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.pila.business.interfaces.IConsultaModeloDatosCore;
import com.asopagos.pila.business.interfaces.IPersistenciaDatosValidadores;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.ErrorFuncionalValidacionException;
import com.asopagos.pila.validadores.bloque5.interfaces.IValidacionOIBloque5;

/**
 * <b>Descripción:</b> Clase que contiene la función para la validación ID de
 * aportante en los archivos del operador de Información en el Bloque 5 <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 */
@Stateless
public class ValidacionOIBloque5 implements IValidacionOIBloque5, Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidacionOIBloque5.class);

    /**
     * Injección del EJB para consulta al modelo de datos transaccional
     */
    @Inject
    private IConsultaModeloDatosCore consultaModeloCore;

    @Inject
    private IPersistenciaDatosValidadores persistenciaDatosValidadores;

    /*
     * (non-Javadoc)
     * 
     * @see com.asopagos.pila.validadores.bloque5.interfaces.IValidacionOIBloque5#validarBloque5(java.util.Map)
     */
    @Override
    public void validarBloque5(Map<String, Object> contexto) throws ErrorFuncionalValidacionException {

        logger.debug("Inicia validarBloque5(Map<String, Object>, ConsultaModeloDatosCoreInterface)");

        // se toma el valor del registro tipo 1 del contexto
        Object registro1 = contexto.get(ConstantesContexto.REGISTRO_1);
        TipoPlanillaEnum tipoPlanillaRegistro1 = null;

        if (registro1 != null) {
            String tipoId = null;
            String numeroId = null;
            String nomSucursal = null;
            String codSucursal = null;
            String tipoPlanilla = null;
            String tipo = " a un aportante";
            Short tipoBusqueda = 2;

            if (registro1 instanceof PilaArchivoIRegistro1) {
                tipoId = ((PilaArchivoIRegistro1) registro1).getTipoDocAportante();
                numeroId = ((PilaArchivoIRegistro1) registro1).getIdAportante();
                nomSucursal = ((PilaArchivoIRegistro1) registro1).getNomSucursal();
                codSucursal = ((PilaArchivoIRegistro1) registro1).getCodSucursal();
                tipoPlanilla = ((PilaArchivoIRegistro1) registro1).getTipoPlanilla();
                tipoPlanillaRegistro1 = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanilla);
            } else if (registro1 instanceof PilaArchivoIPRegistro1) {
                tipoId = ((PilaArchivoIPRegistro1) registro1).getTipoIdPagador();
                numeroId = ((PilaArchivoIPRegistro1) registro1).getIdPagador();
                nomSucursal = ((PilaArchivoIPRegistro1) registro1).getNomSucursal();
                codSucursal = ((PilaArchivoIPRegistro1) registro1).getCodSucursal();
            }

            PreRegistroEmpresaDesCentralizada desCentralizada = consultaModeloCore.consultarPersonaPorEmpresaDescentralizada(numeroId, nomSucursal, codSucursal);

            if (desCentralizada != null) {
                logger.info("Se encuentra Empresa Descentralizada: " + desCentralizada.getNumeroIdentificacionSerial());
                numeroId = desCentralizada.getNumeroIdentificacionSerial();
                if (registro1 instanceof PilaArchivoIRegistro1) {
                    ((PilaArchivoIRegistro1) registro1).setIdAportante(numeroId);
                } else if (registro1 instanceof PilaArchivoIPRegistro1) {
                    ((PilaArchivoIPRegistro1) registro1).setIdPagador(numeroId);
                }
                ListasBlancasAportantes listaBlanca = new ListasBlancasAportantes();
                listaBlanca.setTipoIdentificacionEmpleador(TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoId));
                listaBlanca.setNumeroIdentificacionEmpleador(numeroId);
                persistenciaDatosValidadores.actualizarRegistro1ConListasBlancasAportantes(registro1, listaBlanca);
            }

            Boolean aplicarListaBlanca = Boolean.FALSE;
            ListasBlancasAportantes listaBlanca = consultaModeloCore.consultarListasBlancasAportantes(numeroId);
            if (listaBlanca != null) {
                if (listaBlanca.getActivo()) {
                    if (consultaModeloCore.consultarEmpleadorEntidadPagadora(tipoBusqueda, listaBlanca.getTipoIdentificacionEmpleador().getValorEnPILA(), listaBlanca.getNumeroIdentificacionEmpleador()) != null) {
                        aplicarListaBlanca = Boolean.TRUE;

                        if (registro1 instanceof PilaArchivoIRegistro1) {
                            ((PilaArchivoIRegistro1) registro1).setTipoDocAportante(listaBlanca.getTipoIdentificacionEmpleador().getValorEnPILA());
                            ((PilaArchivoIRegistro1) registro1).setIdAportante(listaBlanca.getNumeroIdentificacionEmpleador());
                        } else if (registro1 instanceof PilaArchivoIPRegistro1) {
                            ((PilaArchivoIPRegistro1) registro1).setTipoIdPagador(listaBlanca.getTipoIdentificacionEmpleador().getValorEnPILA());
                            ((PilaArchivoIPRegistro1) registro1).setIdPagador(listaBlanca.getNumeroIdentificacionEmpleador());
                        }

                        persistenciaDatosValidadores.actualizarRegistro1ConListasBlancasAportantes(registro1, listaBlanca);
                    }
                }
            }

            if (Boolean.FALSE.equals(aplicarListaBlanca) ) {
                if(TipoPlanillaEnum.EMPLEADOS.equals(tipoPlanillaRegistro1)||TipoPlanillaEnum.SERVICIO_DOMESTICO.equals(tipoPlanillaRegistro1)){
                    if(tipoId != null && numeroId != null
                        && consultaModeloCore.consultarEmpleadorEntidadPagadora((short) 1, tipoId, numeroId) == null){
                             String mensaje = MensajesValidacionEnum.ERROR_TIPO_PLANILLA_PILA
                            .getReadableMessage(TipoErrorValidacionEnum.TIPO_1.toString(), tipo);

                        logger.debug("Finaliza validarBloque5(Map<String, Object>, ConsultaModeloDatosCoreInterface) - " + mensaje);
                        throw new ErrorFuncionalValidacionException(mensaje, new Throwable());

                    }

                }
                else if (tipoId != null && numeroId != null
                        && consultaModeloCore.consultarEmpleadorEntidadPagadora(tipoBusqueda, tipoId, numeroId) == null) {
                    String mensaje = MensajesValidacionEnum.ERROR_TIPO_PLANILLA_PILA
                            .getReadableMessage(TipoErrorValidacionEnum.TIPO_1.toString(), tipo);

                    logger.debug("Finaliza validarBloque5(Map<String, Object>, ConsultaModeloDatosCoreInterface) - " + mensaje);
                    throw new ErrorFuncionalValidacionException(mensaje, new Throwable());

                }
            }
        }

        logger.debug("Finaliza validarBloque5(Map<String, Object>, ConsultaModeloDatosCoreInterface)");
    }
}
