package fun.mitiendita.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class DetalleCarrito {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("cantidad")
    private Integer cantidad;
    
    @JsonProperty("precio_unitario")
    private String precioUnitario;

    @JsonProperty("carrito")
    private Integer carrito;
    

    @JsonProperty("producto")
    private String producto;


    public DetalleCarrito() {
        // Constructor vacío requerido por JPA
    }


    public DetalleCarrito(Integer id, Integer cantidad, String precioUnitario, Integer carrito, String producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.carrito = carrito;
        this.producto = producto;
    }


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getCantidad() {
		return cantidad;
	}


	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}


	public String getPrecioUnitario() {
		return precioUnitario;
	}


	public void setPrecioUnitario(String precioUnitario) {
		this.precioUnitario = precioUnitario;
	}


	public Integer getCarrito() {
		return carrito;
	}


	public void setCarrito(Integer carrito) {
		this.carrito = carrito;
	}


	public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
}
