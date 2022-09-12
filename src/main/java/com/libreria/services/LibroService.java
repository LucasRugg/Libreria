package com.libreria.services;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.repositorios.AutorRepositorio;
import com.libreria.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class LibroService {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorService autorService;
    @Autowired
    private EditorialService editorialService;

    @Transactional(rollbackFor = {Exception.class})
    public Libro crear(String titulo, Long isbn, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String idAutor, String idEditorial) throws Exception {

        Autor autor = autorService.buscarPorId(idAutor);
        Editorial editorial = editorialService.buscarPorId(idEditorial);
        Libro libro = new Libro();
        libro.setAlta(true);
        libro.setAnio(anio);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setId(titulo);
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        return libroRepositorio.save(libro);

    }

    @Transactional(readOnly = true)
    public Libro buscarPorId(String id) throws Exception {

        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new Exception("No existe ese libro");

        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public Libro darBaja(Libro libro) {

        libro.setAlta(false);

        return libro;
    }

    @Transactional(readOnly = true)
    public List<Libro> listarAltas() {
        List<Libro> libros = libroRepositorio.findAll();
        List<Libro> librosAlta = new ArrayList();
        for (Libro libro : libros) {
            if (libro.getAlta()) {
                librosAlta.add(libro);
            }

        }
        return librosAlta;

    }

    @Transactional(rollbackFor = {Exception.class})
    public Libro editar(String id, String titulo, Long isbn, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String idAutor, String idEditorial) throws Exception {
       
        Libro libro = buscarPorId(id);
        Autor autor = autorService.buscarPorId(idAutor);
        Editorial editorial = editorialService.buscarPorId(idEditorial);

        libro.setTitulo(titulo);

        libro.setIsbn(isbn);

        libro.setEjemplares(ejemplares);

        libro.setEjemplaresPrestados(ejemplaresPrestados);

        libro.setAnio(anio);

        libro.setAutor(autor);

        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
        return libro;

    }

    public void validar(String titulo, Long isbn, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Boolean alta, Autor autor, Editorial editorial) throws Exception {

        if (titulo == null || titulo.isEmpty()) {
            throw new Exception();
        }

        if (isbn < 0) {
            throw new Exception();
        }

        if (anio < 0) {
            throw new Exception();
        }
        if (ejemplares < 0) {
            throw new Exception();
        }
        if (ejemplaresPrestados > ejemplares) {
            throw new Exception();
        }
        if (autor == null) {
            throw new Exception();
        }
        if (editorial == null) {
            throw new Exception();
        }
    }
}
