if not exists (select * from ParametrizacionFOVIS where pafNombre = 'SMMLV_2024')
insert into ParametrizacionFOVIS (pafNombre, pafValorNumerico, pafValorString)
    values ('SMMLV_2024', 2024, '1300000.00000');