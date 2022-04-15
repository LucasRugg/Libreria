package com.libreria.controladores;

import com.libreria.entidades.Editorial;
import com.libreria.services.EditorialService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String crearEditorial(RedirectAttributes attr, @RequestParam String nombre) {
        try {
            editorialServicio.crear(nombre);
            attr.addFlashAttribute("exito", "La editorial " + nombre + " fue cargado correctamente");

        } catch (Exception e) {
        }
        return "redirect:/editorial/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) throws Exception {
        List<Editorial> editoriales = editorialServicio.listar();
        Editorial editorial = editorialServicio.buscarPorId(id);
        modelo.put("editorial", editorial);
        modelo.put("editorialesAlta", editoriales);

        return "editarEditorial.html";

    }

    @PostMapping("/editar")
    public String editar(@RequestParam String id, @RequestParam String nombre) throws Exception {
        try {
            editorialServicio.editar(id, nombre);
        } catch (Exception e) {
        }

        return "redirect:/editorial/form";

    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) throws Exception {
        Editorial editorial = editorialServicio.buscarPorId(id);
        editorialServicio.eliminar(editorial);
        modelo.put("editorial", editorial);

        return "redirect:/editorial/form";
    }

}
