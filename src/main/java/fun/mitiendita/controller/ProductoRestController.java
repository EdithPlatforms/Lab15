package fun.mitiendita.controller;

import fun.mitiendita.model.Producto;
import fun.mitiendita.service.ProductoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductoRestController {

    private final ProductoService productoService;

    public ProductoRestController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/mostrar/productos")
    public Producto[] mostrarProductos() {
        return productoService.getProductos();
    }
}
