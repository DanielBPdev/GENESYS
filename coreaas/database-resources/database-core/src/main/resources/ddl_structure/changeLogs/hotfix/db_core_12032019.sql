--liquibase formatted sql

--changeset dsuesca:01
--comment: 
while exists (
	select * from(
					SELECT case when lag(revTimeStamp) OVER (ORDER BY REVID)>revTimeStamp then 1 else 0 end corregir, * 
	FROM aud.REVISION ) a where corregir = 1)
begin 
	UPDATE rev 
	SET rev.revTimeStamp = revAnt.revTimeStamp + 1
	FROM aud.REVISION rev
	INNER JOIN aud.Revision revAnt on rev.revId = revAnt.revid+1
	where rev.revTimeStamp < revAnt.revTimeStamp
end
