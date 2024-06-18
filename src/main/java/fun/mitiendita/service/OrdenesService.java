package fun.mitiendita.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fun.mitiendita.model.Orden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class OrdenesService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url}")
    private String apiUrl;

    public List<Orden> obtenerOrdenesPorCliente(HttpServletRequest request) {
        String accessToken = getCookieValue(request, "accessToken");
        int clienteId = Integer.parseInt(getCookieValue(request, "id"));

        String obtenerOrdenesUrl = apiUrl + "ordenes/";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accessToken", accessToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                obtenerOrdenesUrl,
                HttpMethod.GET,
                requestEntity,
                String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            System.out.println("Datos de órdenes obtenidos de la API:");
            System.out.println(responseBody); // Imprimir el cuerpo de la respuesta

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                List<Orden> ordenes = Arrays.asList(objectMapper.readValue(responseBody, Orden[].class));
                // Filtrar las órdenes por clienteId
                ordenes = ordenes.stream().filter(orden -> orden.getCliente().equals((long) clienteId)).toList();

                // Imprimir las órdenes filtradas
                System.out.println("Órdenes filtradas por clienteId " + clienteId + ":");
                for (Orden orden : ordenes) {
                    System.out.println(orden); // Puedes personalizar la impresión según tus necesidades
                }

                return ordenes;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Error al obtener las órdenes. Código de respuesta: " + response.getStatusCodeValue());
        }

        return null;
    }

    private String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
