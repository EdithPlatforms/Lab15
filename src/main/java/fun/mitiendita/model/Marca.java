package fun.mitiendita.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Marca {
	@Id
    @JsonProperty("id")
    private Integer id;
	
    @OneToMany(mappedBy = "marca")
    private List<Producto> productos;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("imagen")
    private String imagen;

    public Marca() {
        // You can initialize fields here if needed
    }
    
	public Marca(Integer id, List<Producto> productos, String nombre, String imagen) {
		super();
		this.id = id;
		this.productos = productos;
		this.nombre = nombre;
		this.imagen = imagen;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	
}
