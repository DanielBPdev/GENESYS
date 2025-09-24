package com.asopagos.util;

import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;

/**
 * <b>Descripción:</b> Clase que contiene métodos utilitarios para realizar la desafiliacion de beneficiarios
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Julian Andres Sanchez Bedoya <jusanchez@heinsohn.com.co>
 */
public class ValidacionDesafiliacionUtils {

    /**
     * Método encargado de verificiar el tipo de desafiliacion de un beneficiario respecto a su afiliado principal
     * @param motivoDesafiliacionAfiliado,
     *        motivo de desafiliacion del afiliado principal
     * @return retorna el motivo de desafiliacion del beneficiario
     */
    public static MotivoDesafiliacionBeneficiarioEnum validarMotivoDesafiliacionBeneficiario(
            MotivoDesafiliacionAfiliadoEnum motivoDesafiliacionAfiliado) {
        MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacionBeneficiario = null;
        if (motivoDesafiliacionAfiliado == null) {
            motivoDesafiliacionBeneficiario = MotivoDesafiliacionBeneficiarioEnum.OTROS;
            return motivoDesafiliacionBeneficiario;
        }
        switch (motivoDesafiliacionAfiliado) {
            case RETIRO_VOLUNTARIO:
                motivoDesafiliacionBeneficiario = MotivoDesafiliacionBeneficiarioEnum.RETIRO_VOLUNTARIO;
                break;
            case RETIRO_POR_MORA_APORTES:
                motivoDesafiliacionBeneficiario = MotivoDesafiliacionBeneficiarioEnum.RETIRO_POR_MORA_APORTES;
                break;
            case MAL_USO_DE_SERVICIOS_CCF:
                motivoDesafiliacionBeneficiario = MotivoDesafiliacionBeneficiarioEnum.MAL_USO_DE_SERVICIOS_CCF;
                break;
            case ENTREGA_DE_INFORMACION_FRAUDULENTA_CCF:
                motivoDesafiliacionBeneficiario = MotivoDesafiliacionBeneficiarioEnum.ENTREGA_DE_INFORMACION_FRAUDULENTA_CCF;
                break;
            case RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION:
                motivoDesafiliacionBeneficiario = MotivoDesafiliacionBeneficiarioEnum.RETIRO_POR_CAMBIO_DE_CAJA_COMPENSACION;
                break;
            case DESAFILIACION_EMPLEADOR:
                motivoDesafiliacionBeneficiario = MotivoDesafiliacionBeneficiarioEnum.DESAFILIACION_EMPLEADOR;
                break;
            case AFILIACION_ANULADA:
                motivoDesafiliacionBeneficiario = MotivoDesafiliacionBeneficiarioEnum.AFILIACION_ANULADA;
                break;
            default:
                motivoDesafiliacionBeneficiario = MotivoDesafiliacionBeneficiarioEnum.OTROS;
                break;
        }
        return motivoDesafiliacionBeneficiario;
    }

}
