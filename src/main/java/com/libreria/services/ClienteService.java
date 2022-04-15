package com.libreria.services;

import com.libreria.entidades.Cliente;
import com.libreria.repositorios.ClienteRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public void validar(Long documento, String nombre, String apellido, String telefono) throws Exception {
        if (documento < 0) {
            throw new Exception("El documento no es valido");
        }
        if (nombre == null) {
            throw new Exception("El nombre no puede estar vacio");
        }
        if (apellido == null) {
            throw new Exception("El apellido no puede estar vacio");
        }
        if (telefono == null) {
            throw new Exception("Indique numero de telefono");
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public Cliente crear(Long documento, String nombre, String apellido, String telefono) {
        Cliente cliente = new Cliente();
        cliente.setAlta(true);
        cliente.setApellido(apellido);
        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);

        return clienteRepositorio.save(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> listar() {
        return clienteRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(String id) throws Exception {
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            return cliente;
        } else {
            throw new Exception("No existe ese cliente");

        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public Cliente editar(String id, Long documento, String nombre, String apellido, String telefono) throws Exception {
        Cliente cliente = clienteRepositorio.getById(id);
        validar(documento, nombre, apellido, telefono);

        cliente.setApellido(apellido);
        cliente.setNombre(nombre);
        cliente.setDocumento(documento);
        cliente.setTelefono(telefono);
        return cliente;

    }

    @Transactional(rollbackFor = {Exception.class})
    public Cliente darBaja(Cliente cliente) {

        cliente.setAlta(false);
        return cliente;
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarAltas() {
        List<Cliente> clientes = clienteRepositorio.findAll();
        List<Cliente> alta = new ArrayList();
        for (Cliente cliente : clientes) {
            if (cliente.getAlta()) {
                alta.add(cliente);

            }

        }
        return alta;
    }

}
