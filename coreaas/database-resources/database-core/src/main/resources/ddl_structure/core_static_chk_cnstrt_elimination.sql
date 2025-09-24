--liquibase formatted sql
--changeset Heinsohn:01 runAlways:true runOnChange:true
--comment: Borrado de constraints estáticos
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ValidacionProceso_vapObjetoValidacion')) ALTER TABLE ValidacionProceso DROP CONSTRAINT CK_ValidacionProceso_vapObjetoValidacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Beneficiario_benTipoBeneficiario')) ALTER TABLE Beneficiario DROP CONSTRAINT CK_Beneficiario_benTipoBeneficiario;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Beneficiario_benEstadoBeneficiarioAfiliado')) ALTER TABLE Beneficiario DROP CONSTRAINT CK_Beneficiario_benEstadoBeneficiarioAfiliado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RolAfiliado_roaEstadoAfiliado')) ALTER TABLE RolAfiliado DROP CONSTRAINT CK_RolAfiliado_roaEstadoAfiliado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_Empleador_empEstadoEmpleador')) ALTER TABLE Empleador DROP CONSTRAINT CK_Empleador_empEstadoEmpleador;