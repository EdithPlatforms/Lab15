package fun.mitiendita.controller;

import fun.mitiendita.model.Producto;
import fun.mitiendita.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    private final ProductoService productoService;

    public IndexController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/")
    public String index(Model model) {
        Producto[] productos = productoService.getProductos();
        model.addAttribute("productos", productos);
        return "index";
    }
    
    @GetMapping("/producto/{id}")
    public String detalleProducto(@PathVariable("id") Long id, Model model) {
        Producto producto = productoService.getProductoById(id);
        model.addAttribute("producto", producto);
        return "detalleProducto";
    }
}
