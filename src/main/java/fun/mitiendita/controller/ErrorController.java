package fun.mitiendita.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class ErrorController {
	
	@GetMapping("/error")
    public String mostrarError() {
        return "error";
    }
	
}
