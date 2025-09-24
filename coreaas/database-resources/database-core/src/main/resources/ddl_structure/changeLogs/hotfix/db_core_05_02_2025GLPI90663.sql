	IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI_PNS') and vcoClave='${fechaRadicacionSolicitud}')
    BEGIN 
		insert into VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden) values
		('${fechaRadicacionSolicitud}','Fecha en que se radica la solicitud',	'Fecha de Radicacion',	(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI_PNS' ),NULL,'FECHA_LARGA',0)
	 END

	IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI_DEP') and vcoClave='${fechaRadicacionSolicitud}')
    BEGIN 
		insert into VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden) values
		('${fechaRadicacionSolicitud}','Fecha en que se radica la solicitud',	'Fecha de Radicacion',	(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI_DEP' ),NULL,'FECHA_LARGA',0)
	 END

	 IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT') and vcoClave='${fechaRadicacionSolicitud}')
    BEGIN 
		insert into VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden) values
		('${fechaRadicacionSolicitud}','Fecha en que se radica la solicitud',	'Fecha de Radicacion',	(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI_IDPT' ),NULL,'FECHA_LARGA',0)
	 END

	  IF NOT EXISTS (SELECT 1 FROM VariableComunicado param WHERE  vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI') and vcoClave='${fechaRadicacionSolicitud}')
    BEGIN 
		insert into VariableComunicado (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden) values
		('${fechaRadicacionSolicitud}','Fecha en que se radica la solicitud',	'Fecha de Radicacion',	(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_AFI' ),NULL,'FECHA_LARGA',0)
	 END
