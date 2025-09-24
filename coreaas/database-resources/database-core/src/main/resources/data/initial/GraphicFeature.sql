SET IDENTITY_INSERT [GraphicFeature] ON 

INSERT [GraphicFeature] ([id], [dataType], [defaultValue], [description], [fileFormat], [name], [restrictions]) VALUES (3, N'BOOLEAN', N'true', N'define si las celdas llevan borde', N'EXCEL_XLSX', N'border', NULL)
INSERT [GraphicFeature] ([id], [dataType], [defaultValue], [description], [fileFormat], [name], [restrictions]) VALUES (4, N'STRING', N'Hoja', N'nombre de la hoja', N'EXCEL_XLSX', N'sheetName', NULL)
INSERT [GraphicFeature] ([id], [dataType], [defaultValue], [description], [fileFormat], [name], [restrictions]) VALUES (5, N'INT', N'20', N'indica el número de caracteres que se podran visualizar al momento de establecer el ancho de una columna', N'EXCEL_XLSX', N'cellMaxSize', NULL)
INSERT [GraphicFeature] ([id], [dataType], [defaultValue], [description], [fileFormat], [name], [restrictions]) VALUES (6, N'INT', N'9', N'color de fondo de una celda en un archivo XLS', N'EXCEL_XLSX', N'cellBackground', NULL)
INSERT [GraphicFeature] ([id], [dataType], [defaultValue], [description], [fileFormat], [name], [restrictions]) VALUES (7, N'STRING', N'Arial', N'tipo de fuente de la letra de una celda de un archivo de tipo XLS', N'EXCEL_XLSX', N'cellTypeFont', NULL)
INSERT [GraphicFeature] ([id], [dataType], [defaultValue], [description], [fileFormat], [name], [restrictions]) VALUES (8, N'INT', N'10', N'tamaño de la letra de una celda de un archivo de tipo XLS', N'EXCEL_XLSX', N'cellFontSize', NULL)
INSERT [GraphicFeature] ([id], [dataType], [defaultValue], [description], [fileFormat], [name], [restrictions]) VALUES (9, N'BOOLEAN', N'false', N'indica si la letra de una celda de un archivo de tipo XLS debe tener negrilla', N'EXCEL_XLSX', N'cellFontBold', NULL)
INSERT [GraphicFeature] ([id], [dataType], [defaultValue], [description], [fileFormat], [name], [restrictions]) VALUES (10, N'BOOLEAN', N'false', N'indica si la letra de una celda de un archivo de tipo XLS debe estar en cursiva', N'EXCEL_XLSX', N'cellFontItalic', NULL)
INSERT [GraphicFeature] ([id], [dataType], [defaultValue], [description], [fileFormat], [name], [restrictions]) VALUES (11, N'INT', N'8', N'color de la letra de una celda de un archivo de tipo XLS', N'EXCEL_XLSX', N'cellFontColor', NULL)
INSERT [GraphicFeature] ([id], [dataType], [defaultValue], [description], [fileFormat], [name], [restrictions]) VALUES (13, N'CHAR', N',', N'delimitador de campo', N'DELIMITED_TEXT_PLAIN', N'delimitador', NULL)
SET IDENTITY_INSERT [GraphicFeature] OFF
