--liquibase formatted sql

--changeset jamartinez:01
--comment: GLPI 43470 creación y alimentación tabla Dec1467_2019

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Dec1467_2019]') AND type in (N'U'))
DROP TABLE [dbo].[Dec1467_2019]

CREATE TABLE [dbo].[Dec1467_2019](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [munId] [int] NOT NULL,
    [valorTope] [int] NOT NULL
    ) ON [PRIMARY]

    INSERT INTO Dec1467_2019
(munId, valorTope)
SELECT        munId, 150 AS Expr1
FROM            municipio
WHERE        (muncodigo IN (11001, 25126, 25175, 25214, 25269, 25286, 25377, 25430, 25473, 25740, 25754, 25785, 25817, 25899, 05088, 05129, 05212, 05266, 05308, 05360, 05380, 05001, 05631, 76130, 76364, 76892,
                            19573, 76001, 08001, 08296, 08433, 08520, 08560, 08634, 08638, 08685, 47745, 08758, 13001, 13222, 13836, 68001, 68276, 68307, 68547))