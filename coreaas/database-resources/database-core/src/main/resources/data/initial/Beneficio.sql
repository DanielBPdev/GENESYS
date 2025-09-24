SET IDENTITY_INSERT [Beneficio] ON 

INSERT [Beneficio] ([befId], [befTipoBeneficio], [befVigenciaFiscal], [befFechaVigenciaInicio], [befFechaVigenciaFin]) VALUES (1, N'LEY_1429', 1, CAST(N'2010-12-29' AS Date), CAST(N'2014-12-31' AS Date))
INSERT [Beneficio] ([befId], [befTipoBeneficio], [befVigenciaFiscal], [befFechaVigenciaInicio], [befFechaVigenciaFin]) VALUES (2, N'LEY_590', 0, CAST(N'2000-07-12' AS Date), NULL)
SET IDENTITY_INSERT [Beneficio] OFF
