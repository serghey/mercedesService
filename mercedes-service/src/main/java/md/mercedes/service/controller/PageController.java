package md.mercedes.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	@RequestMapping("/main")
    public String indexPage(){
        return "/index.html";
    }
	
	@RequestMapping("/registration")
    public String registrationPage(){
        return "/register.jsp";
    }
	
	@RequestMapping("/factura")
    public String facturaPage(){
        return "/factura.html";
    }
	
	@RequestMapping("/history")
    public String historyPage(){
        return "/history.html";
    }

}
