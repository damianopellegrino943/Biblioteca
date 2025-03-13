package com.egg.biblioteca.controladores;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.excepciones.MiException;

import jakarta.websocket.server.PathParam;


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

    @GetMapping("modificar/{id}")
    public String modificar(@PathVariable UUID id, ModelMap modelo){
        modelo.put("autor",autorServicio.getOne(id));
           return "autor_modificar.html";
    }

    @PostMapping("modificar/{id}")
    public String modificar(@PathVariable UUID id,String nombre, ModelMap modelo){
            try {
                autorServicio.modificarAutor(nombre, id);
                return "redirect:../lista";
            } catch (MiException e) {
                // TODO Auto-generated catch block
                modelo.put("error", e.getMessage());
                return "autor_modificar.html";
            }
           // modelo.put("autor",autorServicio.getOne(id));
           // return "autor_modificar.html";
        
    }



}
