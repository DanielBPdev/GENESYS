alter table ParametrizacionFOVIS
ALTER COLUMN pafValorNumerico numeric(5,1)

---------------------------

if not exists (select * from ParametrizacionFOVIS where pafNombre = 'SMMLV_2010') insert into ParametrizacionFOVIS (pafNombre, pafValorNumerico, pafValorString) values ('SMMLV_2010', 2010,'515000.00000');
if not exists (select * from ParametrizacionFOVIS where pafNombre = 'SMMLV_2011') insert into ParametrizacionFOVIS (pafNombre, pafValorNumerico, pafValorString) values ('SMMLV_2011', 2011, '535600.00000');
if not exists (select * from ParametrizacionFOVIS where pafNombre = 'SMMLV_2012') insert into ParametrizacionFOVIS (pafNombre, pafValorNumerico, pafValorString) values ('SMMLV_2012', 2012, '566700.00000');
if not exists (select * from ParametrizacionFOVIS where pafNombre = 'SMMLV_2013') insert into ParametrizacionFOVIS (pafNombre, pafValorNumerico, pafValorString) values ('SMMLV_2013', 2013, '589500.00000');
if not exists (select * from ParametrizacionFOVIS where pafNombre = 'SMMLV_2014') insert into ParametrizacionFOVIS (pafNombre, pafValorNumerico, pafValorString) values ('SMMLV_2014', 2014, '616000.00000');
if not exists (select * from ParametrizacionFOVIS where pafNombre = 'SMMLV_2015') insert into ParametrizacionFOVIS (pafNombre, pafValorNumerico, pafValorString) values ('SMMLV_2015', 2015, '644350.00000');
if not exists (select * from ParametrizacionFOVIS where pafNombre = 'SMMLV_2016') insert into ParametrizacionFOVIS (pafNombre, pafValorNumerico, pafValorString) values ('SMMLV_2016', 2016, '689455.00000');
if not exists (select * from ParametrizacionFOVIS where pafNombre = 'SMMLV_2017') insert into ParametrizacionFOVIS (pafNombre, pafValorNumerico, pafValorString) values ('SMMLV_2017', 2017, '737717.00000');
if not exists (select * from ParametrizacionFOVIS where pafNombre = 'SMMLV_2018') insert into ParametrizacionFOVIS (pafNombre, pafValorNumerico, pafValorString) values ('SMMLV_2018', 2018, '781242.00000');
if not exists (select * from ParametrizacionFOVIS where pafNombre = 'SMMLV_2019') insert into ParametrizacionFOVIS (pafNombre, pafValorNumerico, pafValorString) values ('SMMLV_2019', 2019, '828116.00000');
if not exists (select * from ParametrizacionFOVIS where pafNombre = 'SMMLV_2020') insert into ParametrizacionFOVIS (pafNombre, pafValorNumerico, pafValorString) values ('SMMLV_2020', 2020, '877803.00000');
if not exists (select * from ParametrizacionFOVIS where pafNombre = 'SMMLV_2021') insert into ParametrizacionFOVIS (pafNombre, pafValorNumerico, pafValorString) values ('SMMLV_2021', 2021, '908526.00000');
if not exists (select * from ParametrizacionFOVIS where pafNombre = 'SMMLV_2022') insert into ParametrizacionFOVIS (pafNombre, pafValorNumerico, pafValorString) values ('SMMLV_2022', 2022, '1000000.00000');
if not exists (select * from ParametrizacionFOVIS where pafNombre = 'SMMLV_2023') insert into ParametrizacionFOVIS (pafNombre, pafValorNumerico, pafValorString) values ('SMMLV_2023', 2023, '1160000.00000');

----------------------------

update ParametrizacionFOVIS
			set pafValorString = 566700.00000 ,pafValorNumerico = 2012
			where pafNombre = 'SMMLV_2012'
update ParametrizacionFOVIS
			set pafValorString = 589500.00000 ,pafValorNumerico = 2013
			where pafNombre = 'SMMLV_2013'
update ParametrizacionFOVIS
			set pafValorString = 616000.00000 ,pafValorNumerico = 2014
			where pafNombre = 'SMMLV_2014'
update ParametrizacionFOVIS
			set pafValorString = 644350.00000 ,pafValorNumerico = 2015
			where pafNombre = 'SMMLV_2015'
update ParametrizacionFOVIS
			set pafValorString = 689455.00000 ,pafValorNumerico = 2016
			where pafNombre = 'SMMLV_2016'
update ParametrizacionFOVIS
			set pafValorString = 737717.00000 ,pafValorNumerico = 2017
			where pafNombre = 'SMMLV_2017'
update ParametrizacionFOVIS
			set pafValorString = 781242.00000 ,pafValorNumerico = 2018
			where pafNombre = 'SMMLV_2018'
update ParametrizacionFOVIS
			set pafValorString = 828116.00000 ,pafValorNumerico = 2019
			where pafNombre = 'SMMLV_2019'
update ParametrizacionFOVIS
			set pafValorString = 877803.00000 ,pafValorNumerico = 2020
			where pafNombre = 'SMMLV_2020'
update ParametrizacionFOVIS
			set pafValorString = 908526.00000 ,pafValorNumerico = 2021
			where pafNombre = 'SMMLV_2021'
update ParametrizacionFOVIS
			set pafValorString = 1000000.00000 ,pafValorNumerico = 2022
			where pafNombre = 'SMMLV_2022'
update ParametrizacionFOVIS
			set pafValorString = 1160000.00000 ,pafValorNumerico = 2023
			where pafNombre = 'SMMLV_2023'
