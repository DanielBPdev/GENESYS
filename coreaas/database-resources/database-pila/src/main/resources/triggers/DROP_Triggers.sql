--Drop Triggers
IF OBJECT_ID('staging.TRG_AF_INS_RegistroGeneral') IS NOT NULL
DROP TRIGGER staging.TRG_AF_INS_RegistroGeneral
IF OBJECT_ID('staging.TRG_AF_UPD_RegistroGeneral') IS NOT NULL
DROP TRIGGER staging.TRG_AF_UPD_RegistroGeneral

IF OBJECT_ID('staging.TRG_AF_INS_RegistroDetalladoNovedad') IS NOT NULL
DROP TRIGGER staging.TRG_AF_INS_RegistroDetalladoNovedad
IF OBJECT_ID('staging.TRG_AF_UPD_RegistroDetalladoNovedad') IS NOT NULL
DROP TRIGGER staging.TRG_AF_UPD_RegistroDetalladoNovedad

IF OBJECT_ID('staging.TRG_AF_INS_RegistroDetallado') IS NOT NULL
DROP TRIGGER staging.TRG_AF_INS_RegistroDetallado
IF OBJECT_ID('staging.TRG_AF_UPD_RegistroDetallado') IS NOT NULL
DROP TRIGGER staging.TRG_AF_UPD_RegistroDetallado

IF OBJECT_ID('TRG_AF_INS_PilaIndicePlanilla') IS NOT NULL
DROP TRIGGER TRG_AF_INS_PilaIndicePlanilla
IF OBJECT_ID('TRG_AF_UPD_PilaIndicePlanilla') IS NOT NULL
DROP TRIGGER TRG_AF_UPD_PilaIndicePlanilla

IF OBJECT_ID('TRG_AF_INS_PilaEstadoBloque') IS NOT NULL
DROP TRIGGER TRG_AF_INS_PilaEstadoBloque
IF OBJECT_ID('TRG_AF_UPD_PilaEstadoBloque') IS NOT NULL
DROP TRIGGER TRG_AF_UPD_PilaEstadoBloque

IF OBJECT_ID('TRG_AF_INS_PilaErrorValidacionLog') IS NOT NULL
DROP TRIGGER TRG_AF_INS_PilaErrorValidacionLog
IF OBJECT_ID('TRG_AF_UPD_PilaErrorValidacionLog') IS NOT NULL
DROP TRIGGER TRG_AF_UPD_PilaErrorValidacionLog











