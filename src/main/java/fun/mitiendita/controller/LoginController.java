package fun.mitiendita.controller;

import fun.mitiendita.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Enumeration;
import java.util.Map;

@Controller
public class LoginController {

	@Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        Model model) {
        try {
            Map<String, Object> res = loginService.login(username, password);

            session.setAttribute("user", username);
            session.setAttribute("accessToken", res.get("access"));
            session.setAttribute("refreshToken", res.get("refresh"));
            session.setAttribute("id", res.get("id"));
            session.setAttribute("nombre", res.get("nombre"));
            session.setAttribute("apellido", res.get("apellido"));
            session.setAttribute("email", res.get("email"));
            session.setAttribute("imagen", res.get("imagen"));
            session.setAttribute("telefono", res.get("telefono"));
            session.setAttribute("direccion", res.get("direccion"));
            
            // Create cookies
            createCookie(response, "accessToken", (String) res.get("access"), 3600);
            createCookie(response, "refreshToken", (String) res.get("refresh"), 3600);
            createCookie(response, "username", username, 3600);
            createCookie(response, "id", String.valueOf(res.get("id")), 3600); 
            createCookie(response, "nombre", (String) res.get("nombre"), 3600);
            createCookie(response, "apellido", (String) res.get("apellido"), 3600);
            createCookie(response, "email", (String) res.get("email"), 3600);
            createCookie(response, "imagen", (String) res.get("imagen"), 3600);
            createCookie(response, "telefono", (String) res.get("telefono"), 3600);
            createCookie(response, "direccion", (String) res.get("direccion"), 3600);

            // Print session attributes for debugging
            printSessionAttributes(session);

            model.addAttribute("message", res.get("message"));
            model.addAttribute("refresh", res.get("refresh"));
            model.addAttribute("access", res.get("access"));
              
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    // Method to create a cookie
    private void createCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    // Method to print session attributes
    private void printSessionAttributes(HttpSession session) {
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attributeValue = session.getAttribute(attributeName);
            System.out.println("Session Attribute: " + attributeName + " = " + attributeValue);
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        session.invalidate();
        
        // Eliminar cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue(null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        
        return "redirect:/";
    }
    
}
