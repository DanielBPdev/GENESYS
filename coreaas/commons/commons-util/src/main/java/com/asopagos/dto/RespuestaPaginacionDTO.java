package com.asopagos.dto;

import com.asopagos.dto.TrabajadorEmpleadorDTO;
import java.util.List;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
public class RespuestaPaginacionDTO implements Serializable {
    private List<TrabajadorEmpleadorDTO> data;
    private int recordsTotal;
    private int recordsFiltered;

    public RespuestaPaginacionDTO(List<TrabajadorEmpleadorDTO> data, int recordsTotal, int recordsFiltered) {
        this.data = data;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
    }

    public List<TrabajadorEmpleadorDTO> getData() {
        return data;
    }

    public void setData(List<TrabajadorEmpleadorDTO> data) {
        this.data = data;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return this.recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }
    
}
