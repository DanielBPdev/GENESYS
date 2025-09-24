package com.asopagos.archivos.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.InformacionHojaCruceFovisDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.fovis.CampoHojaArchivoCruceEnum;
import com.asopagos.enumeraciones.fovis.HojaArchivoCruceEnum;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.Interpolator;

/**
 * Class to read the excel file cruce FOVIS
 * @author Jose Arley Correa Salamanca <jocorrea@heinsohn.com.co>
 *
 */
public class ExcelReaderUtil {

    /**
     * Default message indicating the sheet is empty
     */
    private static final String VALUE_DEFAULT = "!!!NINGUNA DE LAS CEDULAS RELACIONADAS TIENE CRUCE CON ESTA ENTIDAD!!!";
    private static final String VALUE_DEFAULT_NUEVO_HOGAR = "!!!NINGUNA DE LAS CEDULAS RELACIONADAS HA REPORTADO CONFORMACION DE NUEVO HOGAR!!!";

    /**
     * Default prefix to obtain field method
     */
    private static final String PREFIX_DEFAULT = "set";

    /**
     * Service to read the contents of the file sheet sent by parameter
     * @param workbook
     *        Reference excel file
     * @param sheet
     *        Sheet info
     * @return Map with the sheet content
     * @throws Exception
     */
    public static Map<String, Object> readSheet(Workbook workbook, HojaArchivoCruceEnum sheet) throws Exception {
        Map<String, Object> result = new HashMap<>();
        result.put(ArchivoMultipleCampoConstants.NOMBRE_HOJA, sheet);
        List<ResultadoHallazgosValidacionArchivoDTO> listErrores = new ArrayList<>();
        result.put(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS, listErrores);
        List<InformacionHojaCruceFovisDTO> listResult = new ArrayList<>();

        // Getting the sheet to read
        Sheet currentSheet = workbook.getSheet(sheet.getDescripcion());
        // Getting the number of empty rows in the sheet start
        Integer firstRow = sheet.getLineaInicial();
        // Iterating the rows of the current sheet
        for (Row row : currentSheet) {
            if (row == null) {
                continue;
            }
            Integer rowNum = row.getRowNum();
            if (rowNum >= firstRow) {
                // Reading row info
                InformacionHojaCruceFovisDTO infoHojaCruceFovisDTO = new InformacionHojaCruceFovisDTO();
                if (readRowCell(result, row, sheet, infoHojaCruceFovisDTO) && rowNum > firstRow) {
                    listResult.add(isNotNullDTO(infoHojaCruceFovisDTO) ? infoHojaCruceFovisDTO : null);
                }
            }
        }
        result.put(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS, listResult);
        return result;
    }

    /**
     * Verify the DTO has at least one main identifier
     * @param infoHojaCruceFovisDTO
     *        DTO with sheet info
     * @return True if is valid falso in otherwise
     */
    private static Boolean isNotNullDTO(InformacionHojaCruceFovisDTO infoHojaCruceFovisDTO) {
        return infoHojaCruceFovisDTO.getNitEntidad() != null || infoHojaCruceFovisDTO.getIdentificacion() != null
                || infoHojaCruceFovisDTO.getDocumento() != null || infoHojaCruceFovisDTO.getNombreEntidad() != null
                || infoHojaCruceFovisDTO.getNroCedula() != null;
    }

    /**
     * Reading the row cell by cell
     * @param result
     *        Map with the sheet content
     * @param row
     *        Row instance
     * @param sheet
     *        Sheet info
     * @param infoHojaCruceFovisDTO
     *        DTO with sheet info
     * @return TRUE if info can added False in otherwise
     * @throws Exception
     */
    private static Boolean readRowCell(Map<String, Object> result, Row row, HojaArchivoCruceEnum sheet,
            InformacionHojaCruceFovisDTO infoHojaCruceFovisDTO) throws Exception {
        Boolean addInfo = Boolean.TRUE;
        int size = sheet.getCampos().size();
        for (int i = 0; i < size; i++) {
            Cell cell = row.getCell(i);
            // Getting cell value
            String cellValue = getCellValue(cell);
            if (sheet == HojaArchivoCruceEnum.BENEFICIARIOS) {
            }
            if (cellValue == null) {
                addInfo = Boolean.FALSE;
                break;
            }
            Long rowNumber = new Long(row.getRowNum());
            CampoHojaArchivoCruceEnum field = sheet.getCampos().get(i);
            if (sheet.getLineaInicial().equals(rowNumber.intValue())) {
                if (!validateHeaderSheet(rowNumber, result, cellValue.trim(), field)) {
                    addInfo = Boolean.FALSE;
                    break;
                }
            }
            else {
                if (!validateValueCell(rowNumber, result, cellValue.trim(), field, infoHojaCruceFovisDTO, sheet)) {
                    addInfo = Boolean.FALSE;
                    break;
                }
            }
        }
        return addInfo;
    }

    /**
     * Verify header row
     * @param rowNumber
     *        Row number
     * @param result
     *        Map with the sheet content
     * @param cellValue
     *        cell value to valid
     * @param field
     *        Field info
     * @return True if is valid falso in otherwise
     */
    @SuppressWarnings("unchecked")
    private static Boolean validateHeaderSheet(Long rowNumber, Map<String, Object> result, String cellValue,
            CampoHojaArchivoCruceEnum field) {
        boolean validate = true;
        if (!validateNotNull(cellValue) || !field.getDescripcion().equals(cellValue)) {
            // Error encabezado
            ((List<ResultadoHallazgosValidacionArchivoDTO>) result.get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS))
                    .add(createHallazgo(rowNumber, field.getDescripcion(), ArchivoMultipleCampoConstants.MENSAJE_ERROR_ENCABEZADO));
            validate = false;
        }
        return validate;
    }

    /**
     * Verify format cell value
     * @param rowNumber
     *        Row number
     * @param result
     *        Map with the sheet content
     * @param cellValue
     *        cell value to valid
     * @param field
     *        Field info
     * @param infoHojaCruceFovisDTO
     *        DTO with sheet info
     * @return True if is valid falso in otherwise
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private static Boolean validateValueCell(Long rowNumber, Map<String, Object> result, String cellValue, CampoHojaArchivoCruceEnum field,
            InformacionHojaCruceFovisDTO infoHojaCruceFovisDTO, HojaArchivoCruceEnum sheet) throws Exception {
        boolean validate = true;
        boolean isDate = false;
        boolean isMessage = false;
        if (validateNotNull(cellValue)) {
            switch (field.getTipoValidacion()) {
                case NUMERO:
                    validate = validateRegex(cellValue, ExpresionesRegularesConstants.UNO_O_MAS_DIGITOS);
                    break;
                case FECHA:
                    isDate = true;
                    break;
                case MENSAJE_NUMERO:
                    if (!validateRegex(cellValue, ExpresionesRegularesConstants.UNO_O_MAS_DIGITOS)) {
                        if (!validateMessage(cellValue)) {
                            validate = false;
                        }
                        else if (validateMessage(cellValue)) {
                            isMessage = true;
                        }
                    }
                    break;
                default:
                    break;
            }
            // If cell value is allow, proceeding to write into DTO
            if (validate) {
                if (!isMessage) {
                    String methodName = getMethodName(field);
                    Method method = null;
                    if (isDate) {
                        method = infoHojaCruceFovisDTO.getClass().getMethod(methodName, Date.class);
                        if (sheet == HojaArchivoCruceEnum.BENEFICIARIO_AR) {
                            method.invoke(infoHojaCruceFovisDTO, CalendarUtils.darFormatoDDMMYYYYSlashDateBySheet(cellValue));
                        } else if(sheet == HojaArchivoCruceEnum.BENEFICIARIOS) {
                            method.invoke(infoHojaCruceFovisDTO, CalendarUtils.darFormatoDDMMYYYYSlashDateBySheet(cellValue));
                        } else {
                            method.invoke(infoHojaCruceFovisDTO, CalendarUtils.darFormatoDDMMYYYYSlashDate(cellValue));
                        }
                    }
                    else {
                        method = infoHojaCruceFovisDTO.getClass().getMethod(methodName, String.class);
                        method.invoke(infoHojaCruceFovisDTO, cellValue);
                    }
                }
            }
            else {
                ResultadoHallazgosValidacionArchivoDTO hallazgo = createHallazgo(rowNumber, field.getDescripcion(),
                        ArchivoMultipleCampoConstants.MENSAJE_ERROR_VALOR_PERMITIDO);
                if (isDate) {
                    hallazgo = createHallazgo(rowNumber, field.getDescripcion(), ArchivoMultipleCampoConstants.MENSAJE_ERROR_FORMATO_FECHA);
                }
                ((List<ResultadoHallazgosValidacionArchivoDTO>) result.get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS)).add(hallazgo);
            }
        }
        return validate;
    }

    /**
     * Obtain method name starting with the field name
     * @param field
     *        Info field sheet
     * @return Method name
     */
    private static String getMethodName(CampoHojaArchivoCruceEnum field) {
        String fieldName = field.getFieldName();
        StringBuilder methodName = new StringBuilder();
        methodName.append(PREFIX_DEFAULT);
        methodName.append(fieldName.substring(0, 1).toUpperCase());
        methodName.append(fieldName.substring(1));
        return methodName.toString();
    }

    /**
     * Validating that the cell value is not null
     * @param cellValue
     *        Info cell
     * @return True if the value is not null False in otherwise
     */
    private static Boolean validateNotNull(String cellValue) {
        Boolean result = Boolean.FALSE;
        if (cellValue != null && !cellValue.isEmpty()) {
            result = Boolean.TRUE;
        }
        return result;
    }

    /**
     * Validating that the cell value is into the regular expression
     * @param cellValue
     *        Info cell
     * @param expresionRegular
     *        Regular expression to validate
     * @return True if the value is into false in otherwise
     */
    private static Boolean validateRegex(String cellValue, String expresionRegular) {
        Boolean result = Boolean.FALSE;
        if (cellValue.matches(expresionRegular)) {
            result = Boolean.TRUE;
        }
        return result;
    }

    /**
     * Validating that the cell value is equals to the default value
     * @param cellValue
     *        Info cell
     * @return True if the value is equal false in otherwise
     */
    private static Boolean validateMessage(String cellValue) {
        Boolean result = Boolean.FALSE;
        if (cellValue.equals(VALUE_DEFAULT) || cellValue.equals(VALUE_DEFAULT_NUEVO_HOGAR)) {
            result = Boolean.TRUE;
        }
        return result;
    }

    /**
     * Setting the cell value depending cell value format
     * @param cell
     *        cell to read
     */
    private static String getCellValue(Cell cell) {
        String obj = null;
        if (cell == null) {
            return obj;
        }
        switch (cell.getCellTypeEnum()) {
            case STRING:
                obj = cell.getStringCellValue();
                break;
            case NUMERIC:
                Double nro = cell.getNumericCellValue();
                Long nrol = nro.longValue();
                obj = nrol.toString();
                break;
            case BOOLEAN:
                Boolean b = cell.getBooleanCellValue();
                obj = b.toString();
                break;
            case FORMULA:
                obj = cell.getCellFormula();
                break;
            default:
                break;
        }
        return obj;
    }

    /**
     * Método que crea un hallazgo según la información ingresada
     * 
     * @param lineNumber,
     *        numero de linea
     * @param campo,
     *        campo al que pertenece el hallazgo
     * @param errorMessage,
     *        mensaje de error
     * @return retorna el resultado de hallazgo DTO
     */
    private static ResultadoHallazgosValidacionArchivoDTO createHallazgo(Long lineNumber, String fieldName, String errorMessage) {
        String error = Interpolator.interpolate(ArchivoMultipleCampoConstants.MENSAJE_ERROR_CAMPO, fieldName, errorMessage);
        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
        hallazgo.setNumeroLinea(lineNumber);
        hallazgo.setNombreCampo(fieldName);
        hallazgo.setError(error);
        return hallazgo;
    }
}
