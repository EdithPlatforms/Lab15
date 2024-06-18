package fun.mitiendita.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Producto {
	@Id
	@JsonProperty("id")
    private Integer id;
	
	@ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
	
	@ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;
	
	@OneToMany(mappedBy = "producto")
    @JsonProperty("imagenes")
    private List<ProductoImagen> imagenes;
	
	@OneToMany(mappedBy = "producto")
    @JsonProperty("reviews")
    private List<Review> reviews;
	
    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("precio")
    private String precio;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("stock")
    private Integer stock;

    @JsonProperty("lote")
    private String lote;

	//Constructor
    public Producto(Integer id) {
        this.id = id;
    }
    
    public Producto() {
        
    }

	public Producto(Integer id, Categoria categoria, Marca marca, List<ProductoImagen> imagenes, List<Review> reviews,
			String nombre, String precio, String descripcion, Integer stock, String lote) {
		super();
		this.id = id;
		this.categoria = categoria;
		this.marca = marca;
		this.imagenes = imagenes;
		this.reviews = reviews;
		this.nombre = nombre;
		this.precio = precio;
		this.descripcion = descripcion;
		this.stock = stock;
		this.lote = lote;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public List<ProductoImagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<ProductoImagen> imagenes) {
		this.imagenes = imagenes;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	
	private double promedioEstrellas;

    // Constructor, getters y setters

    public double getPromedioEstrellas() {
        return promedioEstrellas;
    }

    public void setPromedioEstrellas(double promedioEstrellas) {
        this.promedioEstrellas = promedioEstrellas;
    }
}