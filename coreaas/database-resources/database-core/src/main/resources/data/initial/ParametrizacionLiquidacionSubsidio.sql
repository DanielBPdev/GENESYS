SET IDENTITY_INSERT [ParametrizacionLiquidacionSubsidio] ON 

INSERT [ParametrizacionLiquidacionSubsidio] ([plsId], [plsAnioVigenciaParametrizacion], [plsPeriodoInicio], [plsPeriodoFin], [plsFactorCuotaInvalidez], [plsFactorPorDefuncion], [plsHorasTrabajadas], [plsSMLMV]) VALUES (1, 2016, CAST(N'2016-01-02' AS Date), CAST(N'2016-12-28' AS Date), CAST(2.00000 AS Numeric(19, 5)), CAST(3.00000 AS Numeric(19, 5)), 96, CAST(780000.00000 AS Numeric(19, 5)))
SET IDENTITY_INSERT [ParametrizacionLiquidacionSubsidio] OFF
