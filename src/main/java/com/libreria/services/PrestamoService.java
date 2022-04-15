package com.libreria.services;

import com.libreria.entidades.Cliente;
import com.libreria.entidades.Libro;
import com.libreria.entidades.Prestamo;
import com.libreria.repositorios.ClienteRepositorio;
import com.libreria.repositorios.PrestamoRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepositorio prestamoRepositorio;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private LibroService libroService;

    @Transactional(rollbackFor = {Exception.class})
    public Prestamo crear(String idLibro, String idCliente) throws Exception {
        Prestamo prestamo = new Prestamo();
        
        Cliente cliente = clienteService.buscarPorId(idCliente);
        prestamo.setCliente(cliente);

        Libro libro = libroService.buscarPorId(idLibro);
        prestamo.setLibro(libro);
       
        prestamo.setFechaPrestamo(new Date());

        return prestamoRepositorio.save(prestamo);

    }

    @Transactional(readOnly = true)
    public Prestamo buscarPorId(String id) throws Exception {
        Optional<Prestamo> respuesta = prestamoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Prestamo prestamo = respuesta.get();
            return prestamo;

        } else {
            throw new Exception("No existe ese prestamo");
        }

    }

    @Transactional(readOnly = true)
    public List<Prestamo> listar() {
        return prestamoRepositorio.findAll();

    }

}
