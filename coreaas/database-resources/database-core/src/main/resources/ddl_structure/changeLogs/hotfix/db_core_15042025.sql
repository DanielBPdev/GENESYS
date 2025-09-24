if not exists (select * from ParametrizacionFOVIS where pafNombre = 'SMMLV_2025')
insert into ParametrizacionFOVIS (pafNombre, pafValorNumerico, pafValorString)
    values ('SMMLV_2025', 2025, '1423500.00000');