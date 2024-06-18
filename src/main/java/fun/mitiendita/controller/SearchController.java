package fun.mitiendita.controller;

import fun.mitiendita.model.Producto;
import fun.mitiendita.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    private final ProductoService productoService;

    public SearchController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/search")
    public String searchProductos(@RequestParam("query") String query, Model model) {
        Producto[] productos = productoService.searchProductos(query);
        model.addAttribute("productos", productos);
        model.addAttribute("query", query);
        return "searchResults";
    }
}
