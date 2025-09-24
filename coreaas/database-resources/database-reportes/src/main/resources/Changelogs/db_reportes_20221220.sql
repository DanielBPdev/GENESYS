
--liquibase formatted sql
--changeset ovega:02
/****** Object:  Table [rno].HistoricoAfiliadoss]    Script Date: 20/12/2022 15:50 p. m. ******/

alter table  rno.HistoricoAfiliados alter column hraTipoIdentificacionEmpresa varchar(20)
alter table  rno.HistoricoAfiliados alter column hraTipoIdentificacionAfiliado varchar(20)