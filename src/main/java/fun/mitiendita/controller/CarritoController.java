package fun.mitiendita.controller;

import fun.mitiendita.model.Carrito;
import fun.mitiendita.service.CarritoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        // Llamar al servicio para eliminar carritos existentes y crear uno nuevo si es necesario
        Long carritoId = carritoService.eliminarCarritosYCrearNuevo(request, response);

        if (carritoId != null) {
            // Llamar al servicio para agregar detalle al carrito
            carritoService.agregarDetalleCarrito(request, idProducto, cantidad, precioUnitario, carritoId);

            return "redirect:/"; // Redirigir a la página principal u otra página según sea necesario
        } else {
            // Manejar el caso de error al crear el carrito
            return "error"; // Por ejemplo, redirigir a una página de error
        }
    }

    
    @GetMapping("/carrito")
    public String verCarrito(HttpServletRequest request, Model model) {
        List<Carrito> carritos = carritoService.obtenerCarritos(request);
        if (carritos != null && !carritos.isEmpty()) {
            Carrito carrito = carritos.get(0); // Tomamos el primer carrito, asumiendo uno por cliente
            model.addAttribute("carritoId", carrito.getId());
            model.addAttribute("detalles", carrito.getDetalles());
        } else {
            // Manejar el caso de error al obtener los carritos
            return "redirect:/"; // Por ejemplo, redirigir a una página de error
        }
        return "carrito"; // Nombre de la vista Thymeleaf que mostrará los detalles del carrito
    }

    @PostMapping("/eliminarDetalle")
    public String eliminarDetalle(HttpServletRequest request, @RequestParam Long detalleId) {
        carritoService.eliminarDetalleCarrito(request, detalleId);
        return "redirect:/carrito"; // Redirigir a la página del carrito después de eliminar el detalle
    }
    
}
