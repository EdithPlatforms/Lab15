package fun.mitiendita.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class Review {
	@Id
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("comentario")
    private String comentario;

    @JsonProperty("estrellas")
    private Integer estrellas;
    
    @ManyToOne
    @JsonProperty("cliente")
    private Cliente cliente;

    @ManyToOne
    @JsonProperty("producto")
    private Producto producto;
    
    public Review() {
    	
    }

	public Review(Integer id, String comentario, Integer estrellas, Cliente cliente, Producto producto) {
		super();
		this.id = id;
		this.comentario = comentario;
		this.estrellas = estrellas;
		this.cliente = cliente;
		this.producto = producto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Integer getEstrellas() {
		return estrellas;
	}

	public void setEstrellas(Integer estrellas) {
		this.estrellas = estrellas;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}
