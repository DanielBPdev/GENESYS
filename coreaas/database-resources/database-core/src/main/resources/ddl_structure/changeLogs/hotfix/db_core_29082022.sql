declare @pcoId int;
select  @pcoId = pcoId from PlantillaComunicado where pcoEtiqueta = 'CARTA_APOR_EXPUL'

    INSERT
INTO VariableComunicado
( vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
VALUES
    ('${cargoResponsableCarteraCcf}', 'Cargo responsable del proceso de cartera', 'Cargo responsable del proceso de cartera', @pcoId, 'CARGO_RESPONSABLE_CARTERA_CCF', 'CONSTANTE', ''),
    ('${cargoResponsableCarteraCcf}', 'Cargo responsable del proceso de cartera', 'Cargo responsable del proceso de cartera', @pcoId, 'CARGO_RESPONSABLE_CARTERA_CCF','CONSTANTE', ''),
    ('${fechaDelSistema}', 'dd/mm/aaaa proporcionado por el sistema al generar el comunicado', 'Fecha del sistema', @pcoId, '', 'VARIABLE', 0),
    ('${nombreYApellidosRepresentanteLegal}', 'Nombres y apellidos del representante legal asociado a la solicitud', 'Nombre y Apellidos Representante Legal', @pcoId, '', 'VARIABLE', 0),
    ('${razonSocial/Nombre}', 'Razón social de la empresa a la cual se encuentra asociado el representante legal', 'Razón social / Nombre', @pcoId, '', 'VARIABLE', 0),
    ('${direccion}', 'Dirección de la empresa relacionada al representante legal', 'Dirección', @pcoId, '', 'VARIABLE', 0),
    ('${telefono}', 'Teléfono fijo o Celular capturado en Información de representante legal', 'Teléfono', @pcoId, '', 'VARIABLE', 0),
    ('${ciudad}', 'Ciudad donde se encuentra el representante legal', 'Ciudad', @pcoId, '', 'VARIABLE', 0),
    ('${responsableCcf}', 'responsable caja de compensación', 'Responsable CCF', @pcoId, 'RESPONSABLE_CCF', 'CONSTANTE', ''),
    ('${cargoResponsableCcf}', 'Cargo del responsable del envío del comunicado en la caja', 'Cargo responsable CCF', @pcoId, 'CARGO_RESPONSABLE_CCF', 'CONSTANTE', ''),
    ('${firmaResponsableCcf}', 'Imagen de la firma del responsable del envío del comunicado en la caja', 'Firma Responsable CCF', @pcoId, 'FIRMA_RESPONSABLE_CCF', 'CONSTANTE', ''),
    ('${firmaResponsableProcesoCartera}', 'Firma del responsable de proceso de cartera', 'Firma del responsable de proceso de cartera', @pcoId, 'FIRMA_RESPONSABLE_CARTERA_CCF', 'CONSTANTE', ''),
    ('${numeroIdentificacionEmpleador}', 'Número de identificación del empleador', 'Número de identificación del empleador', @pcoId, '', 'VARIABLE', ''),
    ('${numeroIdentificacionRepresentanteLegal}', 'Número de identificación del representante legal', 'Número de identificación del representante legal', @pcoId, '', 'VARIABLE', ''),
    ('${periodos}', 'Periodo-año / Monto adeudado (en miles sin decimales)', 'Periodos', @pcoId, '', 'VARIABLE', ''),
    ('${tipoIdentificacionEmpleador}', 'Tipo de identificación del empleador', 'Tipo de identificación del empleador', @pcoId, '', 'VARIABLE', ''),
    ('${tipoIdentificacionRepresentanteLegal}', 'Tipo de identificación del representante legal', 'Tipo de identificación del representante legal', @pcoId, '', 'VARIABLE', ''),
    ('${cargoResponsableCcf}', 'Cargo del responsable del envío del comunicado en la caja', 'Cargo responsable CCF', @pcoId, 'CARGO_RESPONSABLE_CCF', 'CONSTANTE', ''),
    ('${consecutivoLiquidacion}', 'Número consecutivo de la liquidación', 'Consecutivo Liquidación', @pcoId, '', 'VARIABLE', 0),
    ('${totalLiquidacion}', 'El valor total de la liquidación para el aportante', 'Total Liquidación', @pcoId, '', 'VARIABLE', 0),
    ('${responsableCarteraCcf}', 'Responsable del proceso de cartera', 'Responsable del proceso de cartera', @pcoId, 'RESPONSABLE_CARTERA_CCF', 'CONSTANTE', ''),
    ('${numeroIdentificacionAportante}', 'Número de identificación', 'Número de identificación', @pcoId, '', 'VARIABLE', ''),
    ('${logoDeLaCcf}', 'Logotipo de la Caja de Compensación', 'Logo de la CCF', @pcoId, 'LOGO_DE_LA_CCF', 'CONSTANTE', '')