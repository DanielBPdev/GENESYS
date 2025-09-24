IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'CRT_ASIG_FOVIS'
	and v.vcoClave='${nombresYApellidosDelJefeDelHogar}')
BEGIN
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  VALUES 
   (N'${nombresYApellidosDelJefeDelHogar}',
    N'Nombre completo Jefe de hogar (Concatenado)', 
   'Nombres y Apellidos del jefe del hogar', 
   (select pcoId from PlantillaComunicado where pcoEtiqueta = 'CRT_ASIG_FOVIS'), NULL, N'VARIABLE', NULL)
END
