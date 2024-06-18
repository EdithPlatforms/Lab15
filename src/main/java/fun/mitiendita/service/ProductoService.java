package fun.mitiendita.service;

import fun.mitiendita.model.Producto;
import fun.mitiendita.model.ProductoImagen; // Importar la clase ProductoImagen
import fun.mitiendita.model.Review;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductoService {

    @Value("${api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public ProductoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Producto[] getProductos() {
        Producto[] productos = restTemplate.getForObject(apiUrl + "productos/", Producto[].class);

        for (Producto producto : productos) {
            // Manejar imágenes
            if (producto.getImagenes() != null && !producto.getImagenes().isEmpty()) {
                ProductoImagen primeraImagen = new ProductoImagen();
                primeraImagen.setImagen(producto.getImagenes().get(0).getImagen());
                producto.setImagenes(Collections.singletonList(primeraImagen));
            } else {
                producto.setImagenes(Collections.emptyList());
            }

            if (producto.getReviews() != null && !producto.getReviews().isEmpty()) {
                double promedioEstrellas = calcularPromedioEstrellas(producto.getReviews());
                producto.setPromedioEstrellas(promedioEstrellas);
            } else {
                producto.setPromedioEstrellas(0.0);
            }
        }

        return productos;
    }
    
    public Producto getProductoById(Long id) {
        return restTemplate.getForObject(apiUrl + "productos/" + id, Producto.class);
    }
    
    private double calcularPromedioEstrellas(List<Review> reviews) {
        int totalEstrellas = 0;
        for (Review review : reviews) {
            totalEstrellas += review.getEstrellas();
        }
        return (double) totalEstrellas / reviews.size();
    }
    
    public Producto[] searchProductos(String query) {
        return restTemplate.getForObject(apiUrl + "productos/search/?format=json&query=" + query, Producto[].class);
    }
}
