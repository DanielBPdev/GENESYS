ALTER TABLE HistoricoBeneficiario  
ADD CONSTRAINT FK_HistoricoBeneficiario_Beneficiario  
FOREIGN KEY (hbeBeneficiario)  
REFERENCES Beneficiario(benId);