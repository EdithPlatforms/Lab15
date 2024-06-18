package fun.mitiendita.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductoImagen {
	@Id
    @JsonProperty("id")
    private Integer id;
	
    @JsonProperty("imagen")
    private String imagen;

    @ManyToOne
    @JsonProperty("producto")
    private Producto producto;

    public ProductoImagen() {
    	
    }
    
	public ProductoImagen(Integer id, String imagen, Producto producto) {
		super();
		this.id = id;
		this.imagen = imagen;
		this.producto = producto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}
