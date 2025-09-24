IF NOT EXISTS ( SELECT id FROM conceptoProv WHERE id in (1,2,3))
BEGIN
    INSERT INTO conceptoProv (id, nombre, estado)
    VALUES
    (1,'Suministro de materiales','ACTIVO'),
    (2,'Mano de obra','ACTIVO'),
    (3,'Saldo disponible','ACTIVO');
END