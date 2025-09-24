--liquibase formatted sql
--changeset rcastillo:01
--comment: Inserta registro para GLPI 55870
if not exists (select * from parametro where prmNombre = 'APLICAR_NOVEDADES_PILA_SUBSIDIO')
	begin
		insert parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion,prmTipoDato)
		select 'APLICAR_NOVEDADES_PILA_SUBSIDIO','FALSE',NULL,'VALOR_GLOBAL_NEGOCIO','INDICA SI LAS NOVEDADES IRL, IGE, LMA QUE SE REPORTAN DESDE LOS CANALES PILA O APORTES SERÁN APLIADAS','BOOLEAN'
	end