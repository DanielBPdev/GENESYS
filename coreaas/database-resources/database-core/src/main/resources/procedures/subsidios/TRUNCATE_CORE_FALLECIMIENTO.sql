CREATE PROCEDURE TRUNCATE_CORE_FALLECIMIENTO
AS
BEGIN

		TRUNCATE TABLE fall.MedioDePago;
		TRUNCATE TABLE fall.GrupoFamiliar;
		TRUNCATE TABLE fall.Empresa;
		TRUNCATE TABLE fall.SucursalEmpresa;
		TRUNCATE TABLE fall.Empleador;
		TRUNCATE TABLE fall.RolAfiliado;
		TRUNCATE TABLE fall.AdministradorSubsidio;
		TRUNCATE TABLE fall.AdminSubsidioGrupo;
		TRUNCATE TABLE fall.Solicitud;
		TRUNCATE TABLE fall.ParametrizacionNovedad;
		TRUNCATE TABLE fall.SolicitudNovedad;
		TRUNCATE TABLE fall.SolicitudNovedadPersona;
		TRUNCATE TABLE fall.NovedadDetalle;
		TRUNCATE TABLE fall.AporteGeneral;
		TRUNCATE TABLE fall.AporteDetallado;
		TRUNCATE TABLE fall.SocioEmpleador;
		TRUNCATE TABLE fall.PersonaDetalle;
		TRUNCATE TABLE fall.BeneficiarioDetalle;
		TRUNCATE TABLE fall.Beneficiario;
		TRUNCATE TABLE fall.BeneficioEmpleador;
		TRUNCATE TABLE fall.CondicionInvalidez;
		TRUNCATE TABLE fall.SolicitudNovedadEmpleador;
		TRUNCATE TABLE fall.CertificadoEscolarBeneficiario;
		TRUNCATE TABLE fall.CargueBloqueoCuotaMonetaria;
		TRUNCATE TABLE fall.BloqueoBeneficiarioCuotaMonetaria;
		TRUNCATE TABLE fall.ItemChequeo;
		TRUNCATE TABLE fall.SolicitudAfiliacionPersona;
		TRUNCATE TABLE fall.Persona;
		TRUNCATE TABLE fall.Afiliado;

END