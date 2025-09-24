CREATE OR ALTER PROCEDURE USP_UTIL_PersonaExclusionSumatoriaSalario_CoreToSubsidio
AS
BEGIN
SET NOCOUNT ON
	waitfor delay '00:00:10'
	print('Termina espera')
EXEC sp_execute_remote SubsidioReferenceData,N'
	declare @tableCore table (pessId bigint,pessPersona bigint,pessEstadoExclusion bit,pessFechaInicioExclusion date,pessFechaFinExclusion date,pessShardName varchar(500))
	insert @tableCore
	exec sp_execute_remote CoreReferenceData,N''
	select pessId,pessPersona,pessEstadoExclusion,pessFechaInicioExclusion,pessFechaFinExclusion
	from PersonaExclusionSumatoriaSalario''

	merge PersonaExclusionSumatoriaSalario as d
	using @tableCore o on o.pessId = d.pessId
	when matched then update set d.pessPersona = o.pessPersona,d.pessEstadoExclusion = o.pessEstadoExclusion,d.pessFechaInicioExclusion = o.pessFechaInicioExclusion,d.pessFechaFinExclusion = o.pessFechaFinExclusion
	when not matched then insert (pessId,pessPersona,pessEstadoExclusion,pessFechaInicioExclusion,pessFechaFinExclusion)
	values(o.pessId,o.pessPersona,o.pessEstadoExclusion,o.pessFechaInicioExclusion,o.pessFechaFinExclusion)
	when not matched by source then delete;'
END
