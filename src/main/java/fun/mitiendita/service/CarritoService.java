package fun.mitiendita.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fun.mitiendita.model.Carrito;
import fun.mitiendita.model.DetalleCarrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class CarritoService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url}")
    private String apiUrl;

    public Long obtenerCarritoExistente(HttpServletRequest request) {
        String carritoIdStr = getCookieValue(request, "carritoId");
        if (carritoIdStr != null) {
            try {
                return Long.parseLong(carritoIdStr);
            } catch (NumberFormatException e) {
                System.err.println("Invalid carritoId in cookie: " + carritoIdStr);
            }
        }
        return null;
    }

    public Long eliminarCarritosYCrearNuevo(HttpServletRequest request, HttpServletResponse response) {
        Long carritoId = obtenerCarritoExistente(request);
        if (carritoId != null) {
            return carritoId;
        }

        String accessToken = getCookieValue(request, "accessToken");
        int clienteId = Integer.parseInt(getCookieValue(request, "id"));

        // Primero intentamos eliminar cualquier carrito existente del cliente
        String eliminarCarritosUrl = apiUrl + "carritos/eliminar-carritos/";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accessToken", accessToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>("{\"cliente\": " + clienteId + "}", headers);

        try {
            restTemplate.exchange(
                    eliminarCarritosUrl,
                    HttpMethod.DELETE,
                    requestEntity,
                    Void.class);

            System.out.println("Carritos eliminados exitosamente para el cliente con ID: " + clienteId);
        } catch (HttpClientErrorException.NotFound ex) {
            // Si no se encontraron carritos para eliminar, manejar la excepción y crear uno nuevo
            System.err.println("No se encontraron carritos para eliminar. Creando uno nuevo para el cliente con ID: " + clienteId);
        } catch (Exception e) {
            System.err.println("Error al eliminar carritos. Código de respuesta: " + e.getMessage());
            return null;
        }

        // Crear un nuevo carrito para el cliente
        String crearCarritoUrl = apiUrl + "carritos/";

        headers = new HttpHeaders();
        headers.set("accessToken", accessToken);
        headers.set("Content-Type", "application/json");

        String requestBody = "{\"cliente\": " + clienteId + "}";

        HttpEntity<String> crearCarritoEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> apiResponse = restTemplate.exchange(
                crearCarritoUrl,
                HttpMethod.POST,
                crearCarritoEntity,
                String.class);

        if (apiResponse.getStatusCode().is2xxSuccessful()) {
            try {
                JsonNode responseBody = new ObjectMapper().readTree(apiResponse.getBody());
                carritoId = responseBody.get("id").asLong();

                // Guardar carritoId en las cookies
                Cookie cookie = new Cookie("carritoId", carritoId.toString());
                cookie.setPath("/");
                response.addCookie(cookie);

                System.out.println("Nuevo carrito creado exitosamente para el cliente con ID: " + clienteId);
                return carritoId;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Error al crear el carrito. Código de respuesta: " + apiResponse.getStatusCodeValue());
        }

        return null;
    }

    public void agregarDetalleCarrito(HttpServletRequest request, Long idProducto, int cantidad, String precioUnitario, Long carritoId) {
        String accessToken = getCookieValue(request, "accessToken");

        String agregarDetalleCarritoUrl = apiUrl + "detalles-carrito/";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accessToken", accessToken);
        headers.set("Content-Type", "application/json");

        String requestBody = "{\"cantidad\": " + cantidad + ", \"precio_unitario\": \"" + precioUnitario + "\", \"carrito\": " + carritoId + ", \"producto\": " + idProducto + "}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                agregarDetalleCarritoUrl,
                HttpMethod.POST,
                requestEntity,
                Void.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Detalle agregado exitosamente al carrito con ID: " + carritoId);
        } else {
            System.err.println("Error al agregar detalle al carrito. Código de respuesta: " + response.getStatusCodeValue());
        }
    }

    public String getCookieValue(HttpServletRequest request, String name) {
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

    public List<Carrito> obtenerCarritos(HttpServletRequest request) {
        String carritoIdStr = getCookieValue(request, "carritoId");
        if (carritoIdStr == null) {
            System.err.println("No se encontró el ID del carrito en las cookies.");
            return null;
        }

        Long carritoId = Long.parseLong(carritoIdStr);
        String obtenerCarritoUrl = apiUrl + "carritos/" + carritoId + "/";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                obtenerCarritoUrl,
                HttpMethod.GET,
                requestEntity,
                String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Carrito carrito = objectMapper.readValue(response.getBody(), Carrito.class);
                if (carrito != null) {
                    System.out.println("Detalles del carrito:");
                    System.out.println("ID: " + carrito.getId());
                    System.out.println("Cliente: " + carrito.getCliente());

                    List<DetalleCarrito> detalles = carrito.getDetalles();
                    if (detalles != null && !detalles.isEmpty()) {
                        for (DetalleCarrito detalle : detalles) {
                            System.out.println("ID Detalle: " + detalle.getId());
                            System.out.println("Cantidad: " + detalle.getCantidad());
                            System.out.println("Precio Unitario: " + detalle.getPrecioUnitario());
                            System.out.println("Producto: " + detalle.getProducto());
                            System.out.println("--------------------------------------");
                        }
                    }
                    return Collections.singletonList(carrito);
                } else {
                    System.err.println("No se encontró el carrito con ID: " + carritoId);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Error al obtener el carrito. Código de respuesta: " + response.getStatusCodeValue());
        }

        return null;
    }

    public void eliminarDetalleCarrito(HttpServletRequest request, Long detalleId) {
        String accessToken = getCookieValue(request, "accessToken");

        String eliminarDetalleCarritoUrl = apiUrl + "detalles-carrito/" + detalleId + "/";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accessToken", accessToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                eliminarDetalleCarritoUrl,
                HttpMethod.DELETE,
                requestEntity,
                Void.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Detalle eliminado exitosamente con ID: " + detalleId);
        } else {
            System.err.println("Error al eliminar el detalle. Código de respuesta: " + response.getStatusCodeValue());
        }
    }

}
