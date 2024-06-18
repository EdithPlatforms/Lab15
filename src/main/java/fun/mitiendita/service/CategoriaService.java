package fun.mitiendita.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fun.mitiendita.model.Categoria;

@Service
public class CategoriaService {

    @Value("${api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public CategoriaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Categoria[] getCategorias() {
        return restTemplate.getForObject(apiUrl + "categorias/", Categoria[].class);
    }
    
    public Categoria getCategoriaById(Long id) {
        return restTemplate.getForObject(apiUrl + "categorias/" + id +"/", Categoria.class);
    }
}
