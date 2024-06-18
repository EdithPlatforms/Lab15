package fun.mitiendita.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url}")
    private String apiUrl;

    public Map<String, Object> login(String username, String password) throws Exception {
        String loginUrl = apiUrl + "clientes/login/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new LinkedHashMap<>();
        requestBody.put("username", username);
        requestBody.put("password", password);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonNodes = mapper.createObjectNode();
        jsonNodes.put("username", username);
        jsonNodes.put("password", password);
        String jsonToSend = mapper.writeValueAsString(jsonNodes);

        System.out.println("JSON a enviar: " + jsonToSend);

        HttpEntity<String> request = new HttpEntity<>(jsonToSend, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, request, String.class);

            JsonNode root = mapper.readTree(response.getBody());
            // Obtener el nodo 'user' del JSON
            JsonNode userNode = root.path("user");
            
            Map<String, Object> result = new HashMap<>();
            result.put("message", root.path("message").asText());
            result.put("refresh", root.path("refresh").asText());
            result.put("access", root.path("access").asText());
            result.put("id", userNode.path("id").asInt()); 
            result.put("nombre", root.path("nombre").asText());
            result.put("apellido", root.path("apellido").asText());
            result.put("email", root.path("email").asText());
            result.put("imagen", root.path("imagen").asText());
            result.put("telefono", root.path("telefono").asText());
            result.put("direccion", root.path("direccion").asText());
            
            return result;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new Exception("El nombre de usuario y la contraseña son obligatorios.");
            }
            throw e;
        }
    }
}
