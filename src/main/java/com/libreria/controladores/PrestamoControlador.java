package com.libreria.controladores;

import com.libreria.entidades.Cliente;
import com.libreria.entidades.Libro;
import com.libreria.entidades.Prestamo;
import com.libreria.services.ClienteService;
import com.libreria.services.LibroService;
import com.libreria.services.PrestamoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prestamo")
public class PrestamoControlador {

    @Autowired
    private PrestamoService prestamoService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private LibroService libroService;
    
    
    
    
    @GetMapping("/form")
    public String form(ModelMap modelo) {
        List<Prestamo> prestamos = prestamoService.listar();
        modelo.put("prestamos", prestamos);
        
        List<Cliente>clientes = clienteService.listar();
        modelo.put("clientes", clientes);
       
        List<Libro>libros = libroService.listarTodos();
        modelo.put("libros",libros);
        return "form-prestamo.html";
    }
    
    @PostMapping("/form")
    public String form(String idLibro, String idCliente){
        try {
            prestamoService.crear(idLibro, idCliente);
        } catch (Exception e) {
        }
        return "redirect:/prestamo/form";
    }

}
