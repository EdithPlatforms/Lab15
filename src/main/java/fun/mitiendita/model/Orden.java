package fun.mitiendita.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.OneToMany;

public class Orden {

    @JsonProperty("id")
    private Long id;
    
    @OneToMany(mappedBy = "orden")
    @JsonProperty("detalles")
    private List<DetalleOrden> detalles;

    @JsonProperty("creado_en")
    private String creadoEn;

    @JsonProperty("total")
    private String total;

    @JsonProperty("cliente")
    private Long cliente;

	public Orden() {
		
	}

	public Orden(Long id, List<DetalleOrden> detalles, String creadoEn, String total, Long cliente) {
		super();
		this.id = id;
		this.detalles = detalles;
		this.creadoEn = creadoEn;
		this.total = total;
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<DetalleOrden> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleOrden> detalles) {
		this.detalles = detalles;
	}

	public String getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(String creadoEn) {
		this.creadoEn = creadoEn;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

    
    
}
