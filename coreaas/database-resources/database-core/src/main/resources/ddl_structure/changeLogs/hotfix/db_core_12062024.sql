IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'AVI_INC_PER' AND v.vcoClave = '${tipoIdentificacion}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${tipoIdentificacion}', 
        'Tipo de identificación del empleador que realizó la solicitud', 
        'Tipo identificación', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'AVI_INC_PER'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'AVI_INC_PER' AND v.vcoClave = '${numeroIdentificacion}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${numeroIdentificacion}', 
        'Número de identificación del empleador que realizó la solicitud', 
        'Número identificación', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'AVI_INC_PER'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'AVI_INC_PER' AND v.vcoClave = '${tabla}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${tabla}', 
        'Tabla que contiene los periodos adeudados', 
        'Tabla', 
        pcoId, 
        '', 
        'REPORTE_VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'AVI_INC_PER'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'AVI_INC_PER' AND v.vcoClave = '${tabla}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${tabla}', 
        'Tabla que contiene los periodos adeudados', 
        'Tabla', 
        pcoId, 
        '', 
        'REPORTE_VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'AVI_INC_PER'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'AVI_INC' AND v.vcoClave = '${tabla}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${tabla}', 
        'Tabla que contiene los periodos adeudados', 
        'Tabla', 
        pcoId, 
        '', 
        'REPORTE_VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'AVI_INC'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'LIQ_APO_MOR' AND v.vcoClave = '${tabla}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${tabla}', 
        'Tabla que contiene los periodos adeudados', 
        'Tabla', 
        pcoId, 
        '', 
        'REPORTE_VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'LIQ_APO_MOR'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'NTF_NO_REC_APO' AND v.vcoClave = '${tabla}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${tabla}', 
        'Tabla que contiene los periodos adeudados', 
        'Tabla', 
        pcoId, 
        '', 
        'REPORTE_VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'NTF_NO_REC_APO'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'NTF_NO_REC_APO_PER' AND v.vcoClave = '${tabla}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${tabla}', 
        'Tabla que contiene los periodos adeudados', 
        'Tabla', 
        pcoId, 
        '', 
        'REPORTE_VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'CAR_PER_EXP' AND v.vcoClave = '${tipoIdentificacion}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${tipoIdentificacion}', 
        'Tipo de identificación del empleador que realizó la solicitud', 
        'Tipo identificación', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'CAR_PER_EXP'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'CAR_PER_EXP' AND v.vcoClave = '${numeroIdentificacion}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${numeroIdentificacion}', 
        'Número de identificación del empleador que realizó la solicitud', 
        'Número identificación', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'CAR_PER_EXP'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'NTF_NO_REC_APO_PER' AND v.vcoClave = '${tipoIdentificacion}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${tipoIdentificacion}', 
        'Tipo de identificación del empleador que realizó la solicitud', 
        'Tipo identificación', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'NTF_NO_REC_APO_PER' AND v.vcoClave = '${numeroIdentificacion}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${numeroIdentificacion}', 
        'Número de identificación del empleador que realizó la solicitud', 
        'Número identificación', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI' AND v.vcoClave = '${FechaInicioLabores}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${FechaInicioLabores}', 
        'Fecha de inicio de labores del trabajador por la empresa por la cual se está solicitando el certificado', 
        'Fecha inicio labores', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI' AND v.vcoClave = '${FechaRecepcionDocumentos}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${FechaRecepcionDocumentos}', 
        'Fecha en la que se reciben los documentos en la CCF para el trámite de afiliación respectivo', 
        'Fecha de recepción de documentos', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI' AND v.vcoClave = '${CategoriaAfiliado}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${CategoriaAfiliado}', 
        'Categoría que presenta el trabajador al momento de solicitar el documento', 
        'Categoría afiliado principal', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI' AND v.vcoClave = '${GrupoFamiliar}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${GrupoFamiliar}', 
        'Se debe presentar el detalle de los grupos familiares que presenta el trabajador con sus respectivos nombres, tipo y número de identificación, fechas de ingreso, fecha de retiro', 
        'Grupo familiar', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT' AND v.vcoClave = '${FechaInicioLabores}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${FechaInicioLabores}', 
        'Fecha de inicio de labores del trabajador por la empresa por la cual se está solicitando el certificado', 
        'Fecha inicio labores', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT' AND v.vcoClave = '${FechaRecepcionDocumentos}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${FechaRecepcionDocumentos}', 
        'Fecha en la que se reciben los documentos en la CCF para el trámite de afiliación respectivo', 
        'Fecha de recepción de documentos', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT' AND v.vcoClave = '${CategoriaAfiliado}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${CategoriaAfiliado}', 
        'Categoría que presenta el trabajador al momento de solicitar el documento', 
        'Categoría afiliado principal', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT' AND v.vcoClave = '${GrupoFamiliar}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${GrupoFamiliar}', 
        'Se debe presentar el detalle de los grupos familiares que presenta el trabajador con sus respectivos nombres, tipo y número de identificación, fechas de ingreso, fecha de retiro', 
        'Grupo familiar', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI_PNS' AND v.vcoClave = '${FechaInicioLabores}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${FechaInicioLabores}', 
        'Fecha de inicio de labores del trabajador por la empresa por la cual se está solicitando el certificado', 
        'Fecha inicio labores', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI_PNS' AND v.vcoClave = '${FechaRecepcionDocumentos}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${FechaRecepcionDocumentos}', 
        'Fecha en la que se reciben los documentos en la CCF para el trámite de afiliación respectivo', 
        'Fecha de recepción de documentos', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI_PNS' AND v.vcoClave = '${CategoriaAfiliado}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${CategoriaAfiliado}', 
        'Categoría que presenta el trabajador al momento de solicitar el documento', 
        'Categoría afiliado principal', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI_PNS' AND v.vcoClave = '${GrupoFamiliar}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${GrupoFamiliar}', 
        'Se debe presentar el detalle de los grupos familiares que presenta el trabajador con sus respectivos nombres, tipo y número de identificación, fechas de ingreso, fecha de retiro', 
        'Grupo familiar', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_PNS'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI_DEP' AND v.vcoClave = '${FechaInicioLabores}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${FechaInicioLabores}', 
        'Fecha de inicio de labores del trabajador por la empresa por la cual se está solicitando el certificado', 
        'Fecha inicio labores', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI_DEP' AND v.vcoClave = '${FechaRecepcionDocumentos}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${FechaRecepcionDocumentos}', 
        'Fecha en la que se reciben los documentos en la CCF para el trámite de afiliación respectivo', 
        'Fecha de recepción de documentos', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI_DEP' AND v.vcoClave = '${CategoriaAfiliado}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${CategoriaAfiliado}', 
        'Categoría que presenta el trabajador al momento de solicitar el documento', 
        'Categoría afiliado principal', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI_DEP' AND v.vcoClave = '${GrupoFamiliar}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${GrupoFamiliar}', 
        'Se debe presentar el detalle de los grupos familiares que presenta el trabajador con sus respectivos nombres, tipo y número de identificación, fechas de ingreso, fecha de retiro', 
        'Grupo familiar', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_DEP'
END;

IF EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI_EMP' AND v.vcoClave in ('${FechaInicioLabores}' ,'${CategoriaAfiliado}','${GrupoFamiliar}')
)
BEGIN
    Delete from VariableComunicado
	where vcoClave in( '${FechaInicioLabores}' ,'${CategoriaAfiliado}','${GrupoFamiliar}')and (select distinct pcoId from PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI_EMP') = vcoPlantillaComunicado
END;

IF NOT EXISTS (
    SELECT * FROM PlantillaComunicado p 
    INNER JOIN VariableComunicado v ON p.pcoId = v.vcoPlantillaComunicado
    WHERE p.pcoEtiqueta = 'COM_GEN_CER_AFI_EMP' AND v.vcoClave = '${FechaRecepcionDocumentos}'
)
BEGIN
    INSERT INTO VariableComunicado(vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
    SELECT DISTINCT 
        '${FechaRecepcionDocumentos}', 
        'Fecha en la que se reciben los documentos en la CCF para el trámite de afiliación respectivo', 
        'Fecha de recepción de documentos', 
        pcoId, 
        '', 
        'VARIABLE', 
        0 
    FROM PlantillaComunicado 
    WHERE pcoEtiqueta = 'COM_GEN_CER_AFI_EMP'
END;

