package fun.mitiendita.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class Cupon {
	@Id
    @JsonProperty("id")
    private Long id;

    @JsonProperty("codigo")
    private String codigo;

    @JsonProperty("descuento")
    private Double descuento;

    @JsonProperty("fecha_expiracion")
    private String fechaExpiracion; // Puedes cambiar el tipo según tu necesidad

    @ManyToOne
    @JsonProperty("cliente_que_lo_uso")
    private Cliente clienteQueLoUso;

	public Cupon(Long id, String codigo, Double descuento, String fechaExpiracion, Cliente clienteQueLoUso) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.descuento = descuento;
		this.fechaExpiracion = fechaExpiracion;
		this.clienteQueLoUso = clienteQueLoUso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public String getFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(String fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}

	public Cliente getClienteQueLoUso() {
		return clienteQueLoUso;
	}

	public void setClienteQueLoUso(Cliente clienteQueLoUso) {
		this.clienteQueLoUso = clienteQueLoUso;
	}
    
    
}
