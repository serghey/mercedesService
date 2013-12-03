package md.mercedes.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	@RequestMapping("/main")
    public String indexPage(){
        return "/index.html";
    }

}
