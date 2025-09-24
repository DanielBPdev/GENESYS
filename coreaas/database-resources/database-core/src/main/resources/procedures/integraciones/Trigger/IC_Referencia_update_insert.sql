CREATE OR ALTER TRIGGER [sap].[IC_Referencia_update_insert]
ON [sap].[IC_Referencia]  
for update,insert
  as

if( update(VALORACTUAL))
begin


---se activa la referencia-----------------
update r set estado = 'A' from SAP.IC_Referencia R inner join 
(SELECT COUNT(id) cantidad,comentario,min(id) id 
FROM SAP.IC_Referencia
where estado <> 'A'
GROUP BY COMENTARIO) s on r.comentario = s.comentario
LEFT JOIN 
(SELECT COUNT(id) cantidad,comentario,min(id) id 
FROM SAP.IC_Referencia
where estado = 'A'
GROUP BY COMENTARIO) p on r.comentario = p.comentario
inner join inserted i on i.id = i.id and s.comentario = i.comentario
where s.cantidad > 1 AND p.cantidad IS  NULL
and r.VALORACTUAL = r.rangoinicial

--- se expira la referencia-------------
update ir set estado = 'E' from sap.IC_Referencia ir inner join inserted i on ir.id = i.id  where ir.valoractual > ir.rangofinal

end

else
begin

---se inactiva la referencia------------
update ir set estado = 'I'  from SAP.IC_Referencia ir inner join 
(SELECT COUNT(id) cantidad,comentario,min(id) id 
FROM SAP.IC_Referencia
where estado <> 'E'
GROUP BY COMENTARIO) s on ir.comentario = s.comentario
inner join inserted i on ir.id = i.id and s.comentario = i.comentario
where s.cantidad > 1
and ir.VALORACTUAL = ir.rangoinicial
and ir.id > s.id

update r set estado = 'A' from SAP.IC_Referencia R inner join 
(SELECT COUNT(id) cantidad,comentario,min(id) id 
FROM SAP.IC_Referencia
where estado <> 'A'
GROUP BY COMENTARIO) s on r.comentario = s.comentario
LEFT JOIN 
(SELECT COUNT(id) cantidad,comentario,min(id) id 
FROM SAP.IC_Referencia
where estado = 'A'
GROUP BY COMENTARIO) p on r.comentario = p.comentario
inner join inserted i on r.id = i.id and s.comentario = i.comentario
where s.cantidad > 1 AND p.cantidad IS  NULL
and r.VALORACTUAL = r.rangoinicial

end