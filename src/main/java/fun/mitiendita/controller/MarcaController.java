package fun.mitiendita.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fun.mitiendita.model.Marca;
import fun.mitiendita.service.MarcaService;

@Controller
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping("/marcas")
    public String marcas(Model model) {
        Marca[] marcas = marcaService.getMarcas();
        model.addAttribute("marcas", marcas);
        return "marcas";
    }
    
    @GetMapping("/marca")
    public String detalleMarca(@RequestParam("id") Long id, Model model) {
        Marca marca = marcaService.getMarcaById(id);
        model.addAttribute("marca", marca);
        return "detalleMarca";
    }
}
