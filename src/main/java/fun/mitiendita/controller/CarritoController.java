package fun.mitiendita.controller;

import fun.mitiendita.model.Carrito;
import fun.mitiendita.service.CarritoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @PostMapping("/agregarAlCarrito")
    public String agregarAlCarrito(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam Long idProducto, @RequestParam int cantidad,
                                   @RequestParam String precioUnitario) {
        Long carritoId = carritoService.obtenerCarritoExistente(request);

        if (carritoId == null) {
            carritoId = carritoService.eliminarCarritosYCrearNuevo(request, response);
        }

        if (carritoId != null) {
            carritoService.agregarDetalleCarrito(request, idProducto, cantidad, precioUnitario, carritoId);
            return "redirect:/";
        } else {
            return "error";
        }
    }

    @GetMapping("/carrito")
    public String verCarrito(HttpServletRequest request, Model model) {
        List<Carrito> carritos = carritoService.obtenerCarritos(request);
        if (carritos != null && !carritos.isEmpty()) {
            Carrito carrito = carritos.get(0);
            model.addAttribute("carritoId", carrito.getId());
            model.addAttribute("detalles", carrito.getDetalles());
        } else {
            return "redirect:/";
        }
        return "carrito";
    }

    @PostMapping("/eliminarDetalle")
    public String eliminarDetalle(HttpServletRequest request, @RequestParam Long detalleId) {
        carritoService.eliminarDetalleCarrito(request, detalleId);
        return "redirect:/carrito";
    }

}
