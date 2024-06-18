package fun.mitiendita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import fun.mitiendita.service.PagadoService;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class PagadoController {

    @Autowired
    private PagadoService pagadoService;

    @PostMapping("/marcarCarritoComoPagado")
    public String marcarCarritoComoPagado(HttpServletRequest request) {
       // Llamar al servicio para marcar como pagado
        pagadoService.marcarComoPagado(request);

        // Redirigir a alguna página de confirmación o actualizar la vista
        return "redirect:/";
    }
}
