package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.services.AutorService;
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
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    AutorService autorServicio;

    @GetMapping("/form")
    public String fotm(ModelMap modelo) {
        List<Autor> autores = autorServicio.listar();
        List<Autor> autoresAlta = new ArrayList<Autor>();
        for (Autor autore : autores) {
            if (autore.getAlta() == true) {
                autoresAlta.add(autore);
            }
        }
        modelo.put("autores", autoresAlta);
        return "form-autor.html";
    }

    @PostMapping("/form")
    public String crearAutor(RedirectAttributes attr, @RequestParam String nombre, @RequestParam String apellido) {
        try {

            autorServicio.crear(apellido, nombre);
            attr.addFlashAttribute("exito", "El autor " + nombre + " " + apellido + " fue cargado correctamente");

        } catch (Exception e) {
        }

        return "redirect:/autor/form";
    }

    @PostMapping("/editarAutor")
    public String editar(RedirectAttributes attr, @RequestParam String id, @RequestParam String nombre, @RequestParam String apellido) {
        try {

            autorServicio.editar(id, nombre, apellido);
            attr.addFlashAttribute("exito", "La modificacion se realizo correctamente");
        } catch (Exception e) {
        }

        return "redirect:/autor/form";

    }

    @GetMapping("/editarAutor/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) throws Exception {
        Autor autor = autorServicio.buscarPorId(id);
        modelo.put("autor", autor);

        return "editarAutor.html";
    }

    @GetMapping("/eliminarAutor/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) throws Exception {
        Autor autor = autorServicio.buscarPorId(id);
        autorServicio.eliminar(autor);
        modelo.put("autor", autor);

        return "redirect:/autor/form";
    }

}
