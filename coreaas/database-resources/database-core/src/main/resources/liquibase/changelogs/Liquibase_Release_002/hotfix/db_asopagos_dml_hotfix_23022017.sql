--changeset  jzambrano:01
--comment: Insercion de OcupacionProfesion
INSERT OcupacionProfesion (ocuNombre) VALUES ('Fuerzas Militares');
INSERT OcupacionProfesion (ocuNombre) VALUES ('Directores y Gerentes');
INSERT OcupacionProfesion (ocuNombre) VALUES ('Profesionales, Científicos e Intelectuales');
INSERT OcupacionProfesion (ocuNombre) VALUES ('Técnicos y Profesionales del Nivel Medio');
INSERT OcupacionProfesion (ocuNombre) VALUES ('Personal de Apoyo Administrativo');
INSERT OcupacionProfesion (ocuNombre) VALUES ('Trabajadores de los Servicios y Vendedores De Comercios Mercados');
INSERT OcupacionProfesion (ocuNombre) VALUES ('Agricultores y Trabajadores Calificados Agropecuarios, Forestales y Pesqueros');
INSERT OcupacionProfesion (ocuNombre) VALUES ('Oficiales, Operarios, Artesanos Y Oficios Relacionados');
INSERT OcupacionProfesion (ocuNombre) VALUES ('Operadores De Instalaciones Y Máquinas Y Ensambladores');
INSERT OcupacionProfesion (ocuNombre) VALUES ('Ocupaciones Elementales');

--changeset  abaquero:02
--comment: Actualizacion de fieldloadcatalog y validatorparamvalue
update validatorparamvalue set value = '0.00000,0.00600,0.01000,0.02000,0.03000,0.04000' where id = 264
update validatorparamvalue set value = '0.00000,0.00600,0.02000' where id = 674
update fieldloadcatalog set datatype = 'BIGDECIMAL', maxdecimalsize = 5, mindecimalsize = 5 where id = 125
update fieldloadcatalog set datatype = 'BIGDECIMAL', maxdecimalsize = 5, mindecimalsize = 5 where id = 186