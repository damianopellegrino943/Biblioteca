package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.excepciones.MiException;

@Service
public class AutorServicio {

   @Autowired   
    private AutorRepositorio autorRepositorio;


@Transactional
public void crearAutor(String nombre) throws MiException {    
    validar(nombre);
    Autor autor = new Autor();// Instancio un objeto del tipo Autor
    autor.setNombre(nombre);// Seteo el atributo, con el valor recibido como parámetro
    autorRepositorio.save(autor); // Persisto el dato en mi BBDD

    }
    @Transactional
  public void modificarAutor(String nombre, UUID id) throws MiException {
    Optional<Autor> respuesta = autorRepositorio.findById(id);
    if (respuesta.isPresent()) {
      Autor autor = respuesta.get();
      autor.setNombre(nombre);
      autorRepositorio.save(autor);
    } else {
      throw new MiException("Error al modificar autor");
    }
  }
    

    private void validar(String nombre) throws MiException {
        if (nombre.isEmpty() || nombre == null) {throw new MiException("el nombre no puede ser nulo o estar vacío");
      }
   }

   
    public List<Autor> listarAutores() {

    List<Autor> autores = new ArrayList<>();

    autores = autorRepositorio.findAll();
    return autores;
    }
    
}
