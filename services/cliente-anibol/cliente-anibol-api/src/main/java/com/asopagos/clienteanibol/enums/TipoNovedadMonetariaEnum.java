package com.asopagos.clienteanibol.enums;

public enum TipoNovedadMonetariaEnum {

    ABONO("AB"),
    DESCUENTO("DS");
    
    private final String valor;
    
    private TipoNovedadMonetariaEnum(String valor) {
      this.valor = valor;
    }
     
    public String getValor() { 
      return valor; 
    }
}
