
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Functions\table-valued\Transversal\Split.sql

sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU395\USP_ValidateHU395CondicionesAportesDependiente.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU395\USP_ValidateHU395CondicionesAportesIndependiente.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU395\USP_ValidateHU395CondicionesAportesPensionados.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU395\USP_ExecutePILA2Fase1VerificarCondicionesValidarAportes.sql

sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU396\USP_ValidateEstadoArchivoDependientes.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU396\USP_ValidateEstadoArchivoInDependiente.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU396\USP_ValidateEstadoArchivoPensionado.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU396\USP_ValidateHU396BD590BD1429_T2T3.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU396\USP_ValidateHU396V0_T1T2T3.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU396\USP_ValidateNovedadesPILA.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU396\USP_ValidateHU396V1BD1429BD590_T2T3.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU396\USP_ValidateHU396V1BDNormal_T1T2T3.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU396\USP_ValidateHU396V1Independientes.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU396\USP_ValidateHU396V1Pensionados.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU396\USP_ValidateHU396V2BDNormalBD1429BD590_T1T2T3.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU396\USP_ValidateHU396V3BDNormalBD1429BD590_T1T2T3.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU396\USP_ValidateHU396V1Dependientes.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU396\USP_ExecutePILA2Fase1ValidarRegistrosPILAvsBD.sql


sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU480\USP_ValidateHU480V3_T1T2T3.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU480\USP_ExecutePILA2Fase1EjecutarPILAIntegralmente.sql

sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU397\USP_ExecuteRegistrarRelacionarAportesRegistro.sql

sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU398\USP_ExecuteRegistrarNovedadesNoAplicadas.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU398\USP_ValidarNovedadesRetroactivasEmpleador.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU398\USP_ValidarNovedadesRetiros.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU398\USP_RegistrarRelacionarNovedadesReferenciales.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU398\USP_ValidarNovedadesEmpleadorActivo.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU398\USP_ExecuteRegistrarRelacionarNovedadesRegistro.sql

sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU400\USP_GetNotificacionesRegistro.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\HU400\USP_GetNotificacionesRegistroDetallado.sql

sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\Transversal\USP_GetCotizantesPlanillaSSIS.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\Transversal\USP_GetAportantesPlanillaSSIS.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\Transversal\USP_DeleteBloqueStaging.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\Transversal\USP_ExecuteBloqueStaging.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\Transversal\USP_ExecutePILA2CopiarPlanilla.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\Transversal\USP_ExecutePILA2Fase1Validacion.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\Transversal\USP_ExecutePILA2Fase2RegistrarRelacionarAportes.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\Transversal\USP_ExecutePILA2Fase3RegistrarRelacionarNovedades.sql
sqlcmd -S 10.77.187.7 -U pila_%USERNAME% -P %USERNAME% -d pila_%USERNAME% -i Procedures\Transversal\USP_ExecutePILA2.sql