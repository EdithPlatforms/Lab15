package fun.mitiendita.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Carrito {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("cliente")
    private Integer cliente;

    @JsonProperty("detalles")
    private List<DetalleCarrito> detalles;

    @JsonProperty("pagado")
    private boolean pagado;

    public Carrito() {
        // Initializes the creation date automatically
    }

	public Carrito(Integer id, Integer cliente, List<DetalleCarrito> detalles, boolean pagado) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.detalles = detalles;
		this.pagado = pagado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCliente() {
		return cliente;
	}

	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}

	public List<DetalleCarrito> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleCarrito> detalles) {
		this.detalles = detalles;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}




}
