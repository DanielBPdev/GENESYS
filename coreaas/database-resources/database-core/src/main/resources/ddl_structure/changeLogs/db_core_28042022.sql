ALTER TABLE MedioConsignacion DROP CONSTRAINT CK_MedioConsignacion_mcoTipoCuenta

ALTER TABLE MedioConsignacion ADD CONSTRAINT CK_MedioConsignacion_mcoTipoCuenta check (mcoTipoCuenta in ('AHORROS','CORRIENTE','DAVIPLATA'));

ALTER TABLE MedioTransferencia DROP CONSTRAINT CK_MedioTransferencia_metTipoCuenta

ALTER TABLE MedioTransferencia ADD CONSTRAINT CK_MedioTransferencia_metTipoCuenta check (metTipoCuenta in ('AHORROS','CORRIENTE','DAVIPLATA'));

ALTER TABLE CuentaAdministradorSubsidio DROP CONSTRAINT CK_CuentaAdministradorSubsidio_casTipoCuentaAdmonSubsidio

ALTER TABLE CuentaAdministradorSubsidio ADD CONSTRAINT CK_CuentaAdministradorSubsidio_casTipoCuentaAdmonSubsidio check (casTipoCuentaAdmonSubsidio in ('AHORROS','CORRIENTE','DAVIPLATA'));

ALTER TABLE Oferente DROP CONSTRAINT CK_Oferente_ofeTipoCuenta

ALTER TABLE Oferente ADD CONSTRAINT CK_Oferente_ofeTipoCuenta check (ofeTipoCuenta in ('AHORROS','CORRIENTE','DAVIPLATA'));