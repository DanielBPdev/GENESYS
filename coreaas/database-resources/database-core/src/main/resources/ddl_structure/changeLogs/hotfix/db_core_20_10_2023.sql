-------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'NTF_INVL_AFL_TRBW'
	and v.vcoClave='${departamentoAfiliado}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${departamentoAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'NTF_INVL_AFL_TRBW'
end
-------------------

-------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'NTF_INVL_AFL_TRBW'
	and v.vcoClave='${municipioAfiliado}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${municipioAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'NTF_INVL_AFL_TRBW'
end
-------------------

-----------------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'RCHZ_AFL_DPT_INC_VAL_TRAB'
	and v.vcoClave='${departamentoAfiliado}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${departamentoAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'RCHZ_AFL_DPT_INC_VAL_TRAB'
end
-------------------
-----------------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'RCHZ_AFL_DPT_INC_VAL_TRAB'
	and v.vcoClave='${municipioAfiliado}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${municipioAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'RCHZ_AFL_DPT_INC_VAL_TRAB'
end
-------------------

-----------------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'RCHZ_AFL_DPT_PROD_NSUBLE_TRB'
	and v.vcoClave='${municipioAfiliado}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${municipioAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'RCHZ_AFL_DPT_PROD_NSUBLE_TRB'
end
-------------------

-----------------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'RCHZ_AFL_DPT_PROD_NSUBLE_TRB'
	and v.vcoClave='${numeroDeRadicacion}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${numeroDeRadicacion}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'RCHZ_AFL_DPT_PROD_NSUBLE_TRB'
end
-------------------

-----------------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'RCHZ_AFL_DPT_PROD_NSUB_TRB'
	and v.vcoClave='${departamentoAfiliado}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${departamentoAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'RCHZ_AFL_DPT_PROD_NSUBLE_TRB'
end
-------------------
-----------------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'RCHZ_AFL_DPT_PROD_NSUB_TRB'
	and v.vcoClave='${departamentoAfiliado}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${departamentoAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'RCHZ_AFL_DPT_PROD_NSUBLE_TRB'
end
-------------------
-----------------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'RCHZ_AFL_DPT_PROD_NSUB_TRB'
	and v.vcoClave='${municipioAfiliado}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${municipioAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'RCHZ_AFL_DPT_PROD_NSUB_TRB'
end
-------------------

--------------------------------------------------------------------------------------------------------------------------------------
-----------------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'NTF_APR_DVL_APT'
	and v.vcoClave='${municipioRepesentanteLegal}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${municipioRepesentanteLegal}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'NTF_APR_DVL_APT'
end
-------------------

-----------------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'NTF_APR_DVL_APT'
	and v.vcoClave='${departamentoRepesentanteLegal}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${departamentoRepesentanteLegal}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'NTF_APR_DVL_APT'
end
-------------------
--------------------------------------------------------------------------

-----------------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'NTF_GST_INF_FLT_APT'
	and v.vcoClave='${municipioRepesentanteLegal}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${municipioRepesentanteLegal}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'NTF_GST_INF_FLT_APT'
end
-------------------

-----------------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'NTF_GST_INF_FLT_APT'
	and v.vcoClave='${departamentoRepesentanteLegal}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${departamentoRepesentanteLegal}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'NTF_GST_INF_FLT_APT'
end
-------------------
--------------------------------------------------------------------------
-----------------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'NTF_PAG_DVL_APT'
	and v.vcoClave='${municipioRepesentanteLegal}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${municipioRepesentanteLegal}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'NTF_PAG_DVL_APT'
end
-------------------

-----------------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'NTF_PAG_DVL_APT'
	and v.vcoClave='${departamentoRepesentanteLegal}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${departamentoRepesentanteLegal}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'NTF_PAG_DVL_APT'
end
-------------------
---------------------------------------------------------------------------
-----------------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'NTF_RCHZ_DVL_APT'
	and v.vcoClave='${municipioRepesentanteLegal}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${municipioRepesentanteLegal}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'NTF_RCHZ_DVL_APT'
end
-------------------
-----------------------------
IF NOT EXISTS (select * from PlantillaComunicado p inner join
	VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	where p.pcoEtiqueta = 'NTF_RCHZ_DVL_APT'
	and v.vcoClave='${departamentoRepesentanteLegal}')
begin
 Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
  select distinct '${departamentoRepesentanteLegal}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	from dbo.VariableComunicado as a
	inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	where b.pcoEtiqueta =  'NTF_RCHZ_DVL_APT'
end
-------------------

   -----------------------------
   IF NOT EXISTS (select * from PlantillaComunicado p inner join
   VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	 where p.pcoEtiqueta = 'NTF_INT_AFL'
	 and v.vcoClave='${departamentoAfiliado}')
   begin
   Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
   select distinct '${departamentoAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	 from dbo.VariableComunicado as a
	 inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	 where b.pcoEtiqueta ='NTF_INT_AFL'
   end
   -------------------

   -----------------------------
   IF NOT EXISTS (select * from PlantillaComunicado p inner join
   VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	 where p.pcoEtiqueta = 'NTF_INT_AFL'
	 and v.vcoClave='${municipioAfiliado}')
   begin
   Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
   select distinct '${municipioAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	 from dbo.VariableComunicado as a
	 inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	 where b.pcoEtiqueta ='NTF_INT_AFL'
   end
   -------------------


  
    -----------------------------
   IF NOT EXISTS (select * from PlantillaComunicado p inner join
   VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	 where p.pcoEtiqueta = 'NTF_RCHZ_AFL_IDPE_DSP_SUB'
	 and v.vcoClave='${departamentoAfiliado}')
   begin
   Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
   select distinct '${departamentoAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	 from dbo.VariableComunicado as a
	 inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	 where b.pcoEtiqueta ='NTF_RCHZ_AFL_IDPE_DSP_SUB'
   end
   -------------------

    -----------------------------
   IF NOT EXISTS (select * from PlantillaComunicado p inner join
   VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	 where p.pcoEtiqueta = 'NTF_RCHZ_AFL_IDPE_DSP_SUB'
	 and v.vcoClave='${municipioAfiliado}')
   begin
   Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
   select distinct '${municipioAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	 from dbo.VariableComunicado as a
	 inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	 where b.pcoEtiqueta ='NTF_RCHZ_AFL_IDPE_DSP_SUB'
   end
   -------------------


  -----------------------------
   IF NOT EXISTS (select * from PlantillaComunicado p inner join
   VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	 where p.pcoEtiqueta = 'NTF_RCHZ_AFL_PNS_DSP_SUB'
	 and v.vcoClave='${departamentoAfiliado}')
   begin
   Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
   select distinct '${departamentoAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	 from dbo.VariableComunicado as a
	 inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	 where b.pcoEtiqueta ='NTF_RCHZ_AFL_PNS_DSP_SUB'
   end
   -------------------
   IF NOT EXISTS (select * from PlantillaComunicado p inner join
   VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	 where p.pcoEtiqueta = 'NTF_RCHZ_AFL_PNS_DSP_SUB'
	 and v.vcoClave='${municipioAfiliado}')
   begin
   Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
   select distinct '${municipioAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	 from dbo.VariableComunicado as a
	 inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	 where b.pcoEtiqueta ='NTF_RCHZ_AFL_PNS_DSP_SUB'
   end
   -------------------

    IF NOT EXISTS (select * from PlantillaComunicado p inner join
   VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	 where p.pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB'
	 and v.vcoClave='${departamentoAfiliado}')
   begin
   Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
   select distinct '${departamentoAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	 from dbo.VariableComunicado as a
	 inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	 where b.pcoEtiqueta =  'NTF_REG_BNF_WEB_TRB'
   end
   -------------------

    -----------------------------
   IF NOT EXISTS (select * from PlantillaComunicado p inner join
   VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	 where p.pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB'
	 and v.vcoClave='${municipioAfiliado}')
   begin
   Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
   select distinct '${municipioAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	 from dbo.VariableComunicado as a
	 inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	 where b.pcoEtiqueta =  'NTF_REG_BNF_WEB_TRB'
   end
   -------------------
    IF NOT EXISTS (select * from PlantillaComunicado p inner join
   VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	 where p.pcoEtiqueta = 'NTF_INVL_AFL_TRBW'
	 and v.vcoClave='${MunicipioAfiliado}')
   begin
   Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
   select distinct '${MunicipioAfiliado}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	 from dbo.VariableComunicado as a
	 inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	 where b.pcoEtiqueta =  'NTF_INVL_AFL_TRBW'
   end
   -------------------

      -----------------------------
   IF NOT EXISTS (select * from PlantillaComunicado p inner join
   VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	 where p.pcoEtiqueta = 'CRR_STC_CNV_PAG'
	 and v.vcoClave='${tipoIdentificacionEmpleador}')
   begin
   Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
   select distinct '${tipoIdentificacionEmpleador}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	 from dbo.VariableComunicado as a
	 inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	 where b.pcoEtiqueta =  'CRR_STC_CNV_PAG'
   end
   -------------------

   -----------------------------
   IF NOT EXISTS (select * from PlantillaComunicado p inner join
   VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	 where p.pcoEtiqueta = 'CRR_STC_CNV_PAG'
	 and v.vcoClave='${numeroIdentificacionEmpleador}')
   begin
   Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
   select distinct '${numeroIdentificacionEmpleador}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	 from dbo.VariableComunicado as a
	 inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	 where b.pcoEtiqueta =  'CRR_STC_CNV_PAG'
   end
   -------------------

   -----------------------------
   IF NOT EXISTS (select * from PlantillaComunicado p inner join
   VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	 where p.pcoEtiqueta = 'CRR_STC_CNV_PAG'
	 and v.vcoClave='${anioConvenio}')
   begin
   Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
   select distinct '${anioConvenio}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	 from dbo.VariableComunicado as a
	 inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	 where b.pcoEtiqueta =  'CRR_STC_CNV_PAG'
   end
   -------------------

   --- --------------------------
   IF NOT EXISTS (select * from PlantillaComunicado p inner join
   VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	 where p.pcoEtiqueta = 'CRR_STC_CNV_PAG'
	 and v.vcoClave='${valorUltimaCuotaConvenio}')
   begin
   Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
   select distinct '${valorUltimaCuotaConvenio}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	 from dbo.VariableComunicado as a
	 inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	 where b.pcoEtiqueta =  'CRR_STC_CNV_PAG'
   end
   -------------------

  -----------------------------
   IF NOT EXISTS (select * from PlantillaComunicado p inner join
   VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	 where p.pcoEtiqueta = 'CRR_STC_CNV_PAG'
	 and v.vcoClave='${valorConvenio}')
   begin
   Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
   select distinct '${valorConvenio}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	 from dbo.VariableComunicado as a
	 inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	 where b.pcoEtiqueta =  'CRR_STC_CNV_PAG'
   end
   -------------------

    -----------------------------
   IF NOT EXISTS (select * from PlantillaComunicado p inner join
   VariableComunicado v on p.pcoId=v.vcoPlantillaComunicado
	 where p.pcoEtiqueta = 'LIQ_APO_MOR'
	 and v.vcoClave='${departamentoRepesentanteLegal}')
   begin
   Insert into VariableComunicado(vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden)
   select distinct '${departamentoRepesentanteLegal}','Numero identificacion empleador','Numero identificacion empleador',pcoId,'','VARIABLE',0
	 from dbo.VariableComunicado as a
	 inner join PlantillaComunicado as b on a.vcoPlantillaComunicado = b.pcoId
	 where b.pcoEtiqueta =  'LIQ_APO_MOR'
   end
-------------------