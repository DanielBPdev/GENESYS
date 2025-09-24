--- Update para tipo de documento PT
UPDATE ValidatorParamValue
SET value = 'CC,CE,TI,RC,PA,CD,SC,PE,PT'
WHERE id in (2110268,2110202);
update ValidatorParamValue
set value = 'NI,CC,CE,TI,RC,PA,CD,SC,PE,PT'
where id in (2110022,2110671);