package com.egg.biblioteca.controladores;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.excepciones.MiException;


@Controller
@RequestMapping("/autor") // localhost:8080/autor/
public class AutorControlador {

      @Autowired
  private AutorServicio autorServicio;


    @GetMapping("/registrar") // localhost:8080/autor/registrar
    public String registrar() {
        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,ModelMap modelo){
        try {
            autorServicio.crearAutor(nombre);    // llamo a mi servicio para persistir    
            modelo.put("exito", "Se a cargado el autor con exito");
        } catch (MiException ex) {          
            modelo.put("error",ex.getMessage());
            return "autor_form.html";
        }        
        return "index.html";
     
}
  @GetMapping("/lista")
    public String listar(ModelMap modelo) {


        List<Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);
        return "autor_list.html";
    }


}
