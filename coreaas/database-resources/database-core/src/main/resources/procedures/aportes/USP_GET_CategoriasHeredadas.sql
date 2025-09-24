
CREATE PROCEDURE USP_GET_CategoriasHeredadas
@idsBenDetalle BIGINT,
@isAfiliadoPrincipal BIT
AS
BEGIN
		declare	@idafiliadoprincipal int;
		declare	@idafiliadosecudario int;
		declare @idbeneficiarioPersona int ;
set @idbeneficiarioPersona = (select top 1 b.benPersona from Beneficiario b where benBeneficiarioDetalle=@idsBenDetalle order by b.benId desc)
IF @isAfiliadoPrincipal=0
BEGIN
		
				if(	select COUNT(*) from Afiliado a
				--inner join RolAfiliado r on r.roaAfiliado=a.afiId
				inner join Persona p on p.perId=a.afiPersona
				inner join Beneficiario b on b.benAfiliado=a.afiId
				where b.benPersona= @idbeneficiarioPersona )>=1
				begin
				
						if(select COUNT(*) from Afiliado a
						--inner join RolAfiliado r on r.roaAfiliado=a.afiId
						inner join Persona p on p.perId=a.afiPersona
						inner join Beneficiario b on b.benAfiliado=a.afiId
						where b.benPersona=@idbeneficiarioPersona and b.benEstadoBeneficiarioAfiliado='ACTIVO')>0
						begin
								set @idafiliadoprincipal=(select top 1 a.afiId  from Afiliado a
								--inner join RolAfiliado r on r.roaAfiliado=a.afiId
								inner join Persona p on p.perId=a.afiPersona
								inner join Beneficiario b on b.benAfiliado=a.afiId
								left join PersonaDetalle d on d.pedPersona=p.perId
								where b.benPersona=@idbeneficiarioPersona order by d.pedGenero,a.afiId  desc) --b.benEstadoBeneficiarioAfiliado='ACTIVO' order by a.afiId  desc)
								set @idafiliadosecudario=(select top 1 a.afiId  from Afiliado a
								--inner join RolAfiliado r on r.roaAfiliado=a.afiId
								inner join Persona p on p.perId=a.afiPersona
								inner join Beneficiario b on b.benAfiliado=a.afiId
								where b.benPersona=@idbeneficiarioPersona  and a.afiId<>@idafiliadoprincipal order by a.afiId  asc)
							end
					else

						begin
								set @idafiliadoprincipal=(select top 1 a.afiId  from Afiliado a
								--inner join RolAfiliado r on r.roaAfiliado=a.afiId
								inner join Persona p on p.perId=a.afiPersona
								inner join Beneficiario b on b.benAfiliado=a.afiId
								left join PersonaDetalle d on d.pedPersona=p.perId
								where b.benPersona=@idbeneficiarioPersona  order by d.pedGenero,a.afiId  desc)
							set @idafiliadosecudario=(select top 1 a.afiId  from Afiliado a
								--inner join RolAfiliado r on r.roaAfiliado=a.afiId
								inner join Persona p on p.perId=a.afiPersona
								inner join Beneficiario b on b.benAfiliado=a.afiId
								where b.benPersona=@idbeneficiarioPersona  and a.afiId<>@idafiliadoprincipal order by a.afiId  asc)
						end

					select 	
						ca.ctaAfiliado as idAfiliado,
						--	ca.ctaEstadoAfiliacion as estadoAfiliado,
						b.benEstadoBeneficiarioAfiliado as estadoAfiliado,
						b.benTipoBeneficiario as clasificacion,
						ca.ctaTipoAfiliado as tipoAfiliado,
						ca.ctaCategoria as categoria
						from CategoriaAfiliado ca 
						inner join Afiliado a on a.afiId=ca.ctaAfiliado
						--inner join RolAfiliado r on r.roaAfiliado=a.afiId
					inner join Beneficiario b on b.benAfiliado=a.afiId
					--inner join Persona p on p.perId=a.afiPersona
					--inner join BeneficiarioDetalle bd on bd.bedId=b.benBeneficiarioDetalle
			
					where ca.ctaAfiliado=@idafiliadosecudario
					AND b.benPersona=@idbeneficiarioPersona
					order by ca.ctaFechaCambioCategoria desc
			end
	END
	ELSE
	BEGIN  
			if(	select COUNT(*) from Afiliado a
				--inner join RolAfiliado r on r.roaAfiliado=a.afiId
				inner join Persona p on p.perId=a.afiPersona
				inner join Beneficiario b on b.benAfiliado=a.afiId
				where b.benPersona= @idbeneficiarioPersona )>=1
				begin
				
						if(select COUNT(*) from Afiliado a
						--inner join RolAfiliado r on r.roaAfiliado=a.afiId
						inner join Persona p on p.perId=a.afiPersona
						inner join Beneficiario b on b.benAfiliado=a.afiId
						where b.benPersona=@idbeneficiarioPersona and b.benEstadoBeneficiarioAfiliado='ACTIVO')>0
						begin
								set @idafiliadoprincipal=(select top 1 a.afiId  from Afiliado a
								--inner join RolAfiliado r on r.roaAfiliado=a.afiId
								inner join Persona p on p.perId=a.afiPersona
								inner join Beneficiario b on b.benAfiliado=a.afiId
								left join PersonaDetalle d on d.pedPersona=p.perId
								where b.benPersona=@idbeneficiarioPersona  order by d.pedGenero,a.afiId  desc) --b.benEstadoBeneficiarioAfiliado='ACTIVO' order by a.afiId  desc)
								set @idafiliadosecudario=(select top 1 a.afiId  from Afiliado a
								--inner join RolAfiliado r on r.roaAfiliado=a.afiId
								inner join Persona p on p.perId=a.afiPersona
								inner join Beneficiario b on b.benAfiliado=a.afiId
								where b.benPersona=@idbeneficiarioPersona  and a.afiId<>@idafiliadoprincipal order by a.afiId  asc)
							end
					else

						begin
								set @idafiliadoprincipal=(select top 1 a.afiId  from Afiliado a
								--inner join RolAfiliado r on r.roaAfiliado=a.afiId
								inner join Persona p on p.perId=a.afiPersona
								inner join Beneficiario b on b.benAfiliado=a.afiId
								left join PersonaDetalle d on d.pedPersona=p.perId
								where b.benPersona=@idbeneficiarioPersona  order by d.pedGenero,a.afiId  desc)
							set @idafiliadosecudario=(select top 1 a.afiId  from Afiliado a
								--inner join RolAfiliado r on r.roaAfiliado=a.afiId
								inner join Persona p on p.perId=a.afiPersona
								inner join Beneficiario b on b.benAfiliado=a.afiId
								where b.benPersona=@idbeneficiarioPersona  and a.afiId<>@idafiliadoprincipal order by a.afiId  asc)
						end

					select 	
						ca.ctaAfiliado as idAfiliado,
						b.benEstadoBeneficiarioAfiliado as estadoAfiliado,
						b.benTipoBeneficiario as clasificacion,
						ca.ctaTipoAfiliado as tipoAfiliado,
						ca.ctaCategoria as categoria
						from CategoriaAfiliado ca 
						inner join Afiliado a on a.afiId=ca.ctaAfiliado
						--inner join RolAfiliado r on r.roaAfiliado=a.afiId
					inner join Beneficiario b on b.benAfiliado=a.afiId
					--inner join Persona p on p.perId=a.afiPersona
					--inner join BeneficiarioDetalle bd on bd.bedId=b.benBeneficiarioDetalle
			
					where ca.ctaAfiliado=@idafiliadoprincipal
					AND b.benPersona=@idbeneficiarioPersona
					order by ca.ctaFechaCambioCategoria desc
			end
			ELSE
			BEGIN
				set @idafiliadoprincipal=(select top 1 a.afiId  from Afiliado a
								--inner join RolAfiliado r on r.roaAfiliado=a.afiId
								inner join Persona p on p.perId=a.afiPersona
								inner join Beneficiario b on b.benAfiliado=a.afiId
								where b.benPersona=@idbeneficiarioPersona  order by a.afiId  desc)
						select 	
						ca.ctaAfiliado as idAfiliado,
						b.benEstadoBeneficiarioAfiliado as estadoAfiliado,
						b.benTipoBeneficiario as clasificacion,
						ca.ctaTipoAfiliado as tipoAfiliado,
						ca.ctaCategoria as categoria
						from CategoriaAfiliado ca 
						inner join Afiliado a on a.afiId=ca.ctaAfiliado
						--inner join RolAfiliado r on r.roaAfiliado=a.afiId
					inner join Beneficiario b on b.benAfiliado=a.afiId
					--inner join Persona p on p.perId=a.afiPersona
					--inner join BeneficiarioDetalle bd on bd.bedId=b.benBeneficiarioDetalle
			
					where ca.ctaAfiliado=@idafiliadoprincipal
					AND b.benPersona=@idbeneficiarioPersona
					order by ca.ctaFechaCambioCategoria desc

			END
	END
END


