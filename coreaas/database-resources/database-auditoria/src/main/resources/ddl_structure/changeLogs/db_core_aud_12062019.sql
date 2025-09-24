--liquibase formatted sql

--changeset jocorrea:01
--comment:
ALTER TABLE DetalleNovedadFovis_aud ADD dnfTotalIngresosHogarAsignacion numeric(19,5);
ALTER TABLE DetalleNovedadFovis_aud ADD dnfTotalIngresosHogar numeric(19,5);
ALTER TABLE DetalleNovedadFovis_aud ADD dnfRangoTopeSMLMVAsignacion numeric(4,1)
ALTER TABLE DetalleNovedadFovis_aud ADD dnfRangoTopeSMLMV numeric(4,1)