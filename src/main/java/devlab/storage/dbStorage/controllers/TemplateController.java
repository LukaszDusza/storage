package devlab.storage.dbStorage.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
@Controller
public class TemplateController {

    @GetMapping("/template/das")
    public String getDasTemplate(Model model, @RequestParam(value = "gender", required = false, defaultValue = "male") String gender) {

        String[] greets = {
                "Szanowny Panie",
                "Pana"
        };

        switch (gender) {
            case "male":
                greets[0] = "Szanowny Panie";
                greets[1] = "Pana";
                break;
            case "female":
                greets[0] = "Szanowna Pani";
                greets[1] = "Pani";
                break;
        }

        model.addAttribute("greet", greets);

        return "template-das-email";
    }
}
