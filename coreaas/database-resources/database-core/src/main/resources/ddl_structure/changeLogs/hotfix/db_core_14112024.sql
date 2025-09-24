
if not exists (select * from [dbo].[Parametro] where [prmNombre] = 'ID_GRUPO_CAJA_COMPENSACION')
begin
    INSERT INTO [dbo].[Parametro]
        ([prmNombre]
        ,[prmValor]
        ,[prmCargaInicio]
        ,[prmSubCategoriaParametro]
        ,[prmDescripcion]
        ,[prmTipoDato]
        ,[prmVisualizarPantalla])
    VALUES
        (
            'ID_GRUPO_CAJA_COMPENSACION'
            ,'ecfaf657-8ecd-499c-947e-87c04b7ce508'
            ,0
            ,'KEYCLOAK'
            ,'Id del groupo supervisor afiliacion personas el cual se usa para consultar los usuarios a los que se le puede reasignar la solicitud.'
            ,'TEXT'
            ,1
        );
end