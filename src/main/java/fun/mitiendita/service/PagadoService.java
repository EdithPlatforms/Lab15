package fun.mitiendita.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class PagadoService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url}")
    private String apiUrl;

    public void marcarComoPagado(HttpServletRequest request) {
    	int carritoId = Integer.parseInt(getCookieValue(request, "carritoId")); 
        String url = apiUrl + "carritos/" + carritoId + "/marcar-como-pagado/";
        restTemplate.put(url, null);  // Se envía una solicitud PUT vacía (sin cuerpo)
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
}
