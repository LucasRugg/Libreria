package com.libreria.controladores;

import com.libreria.entidades.Cliente;
import com.libreria.services.ClienteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/form")
    public String formulario(ModelMap modelo) {
        List<Cliente> clientes = clienteService.listarAltas();
        modelo.put("clientes", clientes);

        return "form-cliente.html";
    }

    @PostMapping("/form")
    public String crear(String nombre, String apellido, Long documento, String telefono) {
        try {
            clienteService.crear(documento, nombre, apellido, telefono);

        } catch (Exception e) {
        }
        return "redirect:/cliente/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) throws Exception {
        Cliente cliente = clienteService.buscarPorId(id);
        modelo.put("cliente", cliente);
        return "editarCliente.html";
    }

    @PostMapping("/editar")
    public String editar(@RequestParam String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam Long documento, @RequestParam String telefono) {
        try {
            clienteService.editar(id, documento, nombre, apellido, telefono);
        } catch (Exception e) {
        }
        return "redirect:/cliente/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) throws Exception {
        Cliente cliente = clienteService.buscarPorId(id);
        clienteService.darBaja(cliente);
        modelo.put("cliente", cliente);
        return "redirect:/cliente/form";

    }

}
