package com.libreria.services;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Libro;
import com.libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorService {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional(rollbackFor = {Exception.class})
    public Autor crear(String apellido, String nombre) {
        Autor autor = new Autor();

        autor.setNombre(nombre);
        autor.setApellido(apellido);
        autor.setAlta(true);

        return autorRepositorio.save(autor);
    }

    @Transactional(readOnly = true)
    public Autor buscarPorId(String id) throws Exception {
        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            return autor;
        } else {
            throw new Exception("No existe ese autor");
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public Autor darBaja(String id) {
        Autor autor = autorRepositorio.getOne(id);
        autor.setAlta(false);
        return autor;
    }

    @Transactional(readOnly = true)
    public List<Autor> listar() {
        return autorRepositorio.findAll();
    }

    @Transactional(rollbackFor = {Exception.class})
    public Autor editar(String id, String nombre, String apellido) throws Exception {
        Autor autor = buscarPorId(id);
        if (nombre != null) {
            autor.setNombre(nombre);
        }
        if (apellido != null) {
            autor.setApellido(apellido);
        }
        autorRepositorio.save(autor);

        return autor;

    }
    
    @Transactional (rollbackFor = {Exception.class})
    public Autor eliminar(Autor autor) throws Exception{
        
        autor.setAlta(false);
        return autor;
    }

}
