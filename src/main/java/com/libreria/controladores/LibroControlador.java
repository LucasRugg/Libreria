package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.services.AutorService;
import com.libreria.services.EditorialService;
import com.libreria.services.LibroService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    LibroService libroService;
    @Autowired
    AutorService autorService;
    @Autowired
    EditorialService editorialService;

    @GetMapping("/form")
    public String form(ModelMap modelo) {
        List<Autor> autores = autorService.listar();
        List<Autor> autoresAlta = new ArrayList<Autor>();

        for (Autor autor : autores) {
            if (autor.getAlta()) {
                autoresAlta.add(autor);
            }
        }
        
        List<Editorial> editoriales = editorialService.listar();
        List<Editorial> editorialesAlta =  new ArrayList<Editorial>();
        
        for (Editorial editorial : editoriales) {
            if (editorial.getAlta()) {
                editorialesAlta.add(editorial);
            }
        }
        
        
        List<Libro> libros = libroService.listarTodos();
        List<Libro> librosAlta = new ArrayList<Libro>();

        for (Libro libro : libros) {
            if (libro.getAlta()) {
                librosAlta.add(libro);
            }
        }
        
        modelo.put("librosAlta", librosAlta);
        modelo.put("autoresAlta", autoresAlta);
        modelo.put("editorialesAlta", editorialesAlta);
        return "form-libro.html";

    }

    @PostMapping("/form")
    public String crearLibro(RedirectAttributes attr, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String idAutor, String idEditorial) {
        try {
            libroService.crear(titulo, isbn, anio, ejemplares, ejemplaresPrestados, idAutor, idEditorial);
            attr.addFlashAttribute("exito", "El libro " + titulo + " fue cargado correctamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return "redirect:/libro/form";
    }

}
