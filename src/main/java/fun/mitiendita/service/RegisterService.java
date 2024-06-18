package fun.mitiendita.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RegisterService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url}")
    private String apiUrl;

    public void register(String username, String password) {
        // Construir el JSON para enviar al servidor
        String requestBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        // Configurar las cabeceras para la solicitud HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        // Hacer la solicitud POST para registrar al usuario
        restTemplate.postForEntity(apiUrl + "clientes/register/", requestEntity, String.class);
    }
}
