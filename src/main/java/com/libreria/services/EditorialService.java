package com.libreria.services;

import com.libreria.entidades.Editorial;
import com.libreria.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialService {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional(rollbackFor = {Exception.class})
    public Editorial crear(String nombre) {
        Editorial editorial = new Editorial();

        editorial.setNombre(nombre);
        editorial.setAlta(true);

        return editorialRepositorio.save(editorial);
    }

    public Editorial buscarPorId(String id) throws Exception {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            return editorial;
        } else {
            throw new Exception("No existe esa editorial");

        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public Editorial darBaja(String id) {
        Editorial autor = editorialRepositorio.getOne(id);
        autor.setAlta(false);
        return autor;
    }

    @Transactional(readOnly = true)
    public List<Editorial> listar() {
        return editorialRepositorio.findAll();
    }

    @Transactional(rollbackFor = {Exception.class})
    public Editorial editar(String id, String nombre) throws Exception {
        Editorial editorial = buscarPorId(id);
        if (nombre != null) {
            editorial.setNombre(nombre);
        }

        editorialRepositorio.save(editorial);

        return editorial;

    }
    @Transactional (rollbackFor = {Exception.class})
    public Editorial eliminar(Editorial editorial) throws Exception{
        
        editorial.setAlta(false);
        return editorial;
    }
}
