package fun.mitiendita.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fun.mitiendita.model.Marca;

@Service
public class MarcaService {

    @Value("${api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public MarcaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Marca[] getMarcas() {
        return restTemplate.getForObject(apiUrl + "marcas/", Marca[].class);
    }
    
    public Marca getMarcaById(Long id) {
        return restTemplate.getForObject(apiUrl + "marcas/" + id + "/", Marca.class);
    }
}
