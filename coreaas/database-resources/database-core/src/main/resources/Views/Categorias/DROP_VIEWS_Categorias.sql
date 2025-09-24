--Drop Views of Categorias
IF ( Object_id( 'VW_CategoriaBeneficiario' ) IS NOT NULL )
  DROP VIEW [dbo].[VW_CategoriaBeneficiario];
IF ( Object_id( 'VW_CategoriaAfiliado' ) IS NOT NULL )
  DROP VIEW [dbo].[VW_CategoriaAfiliado];
  IF ( Object_id( 'VW_EstadoAfiliacionPersonaIndependiente' ) IS NOT NULL )
  DROP VIEW [dbo].[VW_EstadoAfiliacionPersonaIndependiente];
