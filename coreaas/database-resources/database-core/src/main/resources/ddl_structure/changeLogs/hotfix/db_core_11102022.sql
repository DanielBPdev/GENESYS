--correccion fallo e-process
ALTER TABLE aud.solicitudaporte_aud ADD soaCuentaBancariaRecaudo int null;

ALTER TABLE aud.aporteGeneral_aud ADD apgCuentaBancariaRecaudo int null;