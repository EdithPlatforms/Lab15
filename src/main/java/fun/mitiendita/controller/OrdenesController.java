package fun.mitiendita.controller;

import fun.mitiendita.model.Orden;
import fun.mitiendita.service.OrdenesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OrdenesController {

    @Autowired
    private OrdenesService ordenesService;

    @GetMapping("/ordenes")
    public String getOrdenes(HttpServletRequest request, Model model) {
        List<Orden> ordenes = ordenesService.obtenerOrdenesPorCliente(request);
        model.addAttribute("ordenes", ordenes);
        
        // Obtener el clienteId del request
        String clienteId = request.getParameter("cliente");
        model.addAttribute("clienteId", clienteId);
        
        return "ordenes";
    }
}
