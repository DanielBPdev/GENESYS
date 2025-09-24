begin
	alter table ConsolaEstadoCargueMasivo
		alter column cecTipoProcesoMasivo varchar(47)
end

begin
	alter table aud.ConsolaEstadoCargueMasivo_aud
		alter column cecTipoProcesoMasivo varchar(47)
end