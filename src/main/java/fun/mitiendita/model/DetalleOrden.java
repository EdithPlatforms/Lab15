package fun.mitiendita.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.ManyToOne;

public class DetalleOrden {
	@JsonProperty("id")
    private Long id;

    @ManyToOne
    @JsonProperty("producto")
    private Producto producto;

    @JsonProperty("cantidad")
    private int cantidad;

    @JsonProperty("precio_unitario")
    private String precioUnitario;

    @JsonProperty("orden")
    private Long orden;

	public DetalleOrden() {
		
	}

	public DetalleOrden(Long id, Producto producto, int cantidad, String precioUnitario, Long orden) {
		super();
		this.id = id;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.orden = orden;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(String precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}
    
    
}
