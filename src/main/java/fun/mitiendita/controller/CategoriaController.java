package fun.mitiendita.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fun.mitiendita.model.Categoria;
import fun.mitiendita.service.CategoriaService;

@Controller
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/categorias")
    public String categorias(Model model) {
        Categoria[] categorias = categoriaService.getCategorias();
        model.addAttribute("categorias", categorias);
        return "categorias";
    }
    
    @GetMapping("/categoria")
    public String detalleCategoria(@RequestParam("id") Long id, Model model) {
        Categoria categoria = categoriaService.getCategoriaById(id);
        model.addAttribute("categoria", categoria);
        return "detalleCategoria";
    }
}
