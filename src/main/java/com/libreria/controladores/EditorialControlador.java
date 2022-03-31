package com.libreria.controladores;

import com.libreria.entidades.Editorial;
import com.libreria.services.EditorialService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    EditorialService editorialServicio;

    @GetMapping("/form")
    public String form(ModelMap modelo) {
        List<Editorial> editoriales = editorialServicio.listar();
        List<Editorial> editorialesAlta = new ArrayList<Editorial>();
        for (Editorial editorial : editoriales) {
            if (editorial.getAlta()) {
                editorialesAlta.add(editorial);
            }
        }
        modelo.put("editoriales", editorialesAlta);
        return "form-editorial.html";

    }
     @PostMapping("/form")
    public String crearAutor(RedirectAttributes attr, @RequestParam String nombre) {
        try {            
            editorialServicio.crear(nombre);
            attr.addFlashAttribute("exito", "La editorial " + nombre + " fue cargado correctamente");

        } catch (Exception e) {
        }
        return "redirect:/editorial/form";
    }


}
