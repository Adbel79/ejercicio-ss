package mx.uv.t4is.agendabd;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import https.t4is_uv_mx.agendabd.BuscarContactosResponse;
import https.t4is_uv_mx.agendabd.CrearContactoRequest;
import https.t4is_uv_mx.agendabd.CrearContactoResponse;
import https.t4is_uv_mx.agendabd.EliminarContactoRequest;
import https.t4is_uv_mx.agendabd.EliminarContactoResponse;


@Endpoint
public class agendabdEndPoint {

    @Autowired
    Iagendadores iagendadores;
     //se crea el metodo donde generaremos el contacto de la agenda 
    @PayloadRoot(localPart = "CrearContactoRequest" ,namespace = "https://t4is.uv.mx/agendabd")
    @ResponsePayload
    public CrearContactoResponse CrearContacto(@RequestPayload CrearContactoRequest peticion) {
        //se crea una respuesta  
        CrearContactoResponse respuesta = new CrearContactoResponse();
        respuesta.setRespuesta("contacto guardado exitosamente! ");
        //se hace un objeto del tipo agendadores 
        Agendadores agenda = new Agendadores();
        //se obtienen el nombre, numero y corrreo
        agenda.setNombre(peticion.getNombre());
        agenda.setNumero(peticion.getNumero());
        agenda.setCorreo(peticion.getCorreo());
        //se guarda lo que hay 
        iagendadores.save(agenda);
        return respuesta;  
    }

    @PayloadRoot(localPart = "BuscarContactosRequest" ,namespace = "https://t4is.uv.mx/agendabd")
    @ResponsePayload
    public BuscarContactosResponse buscarContactos(){
        BuscarContactosResponse respuesta = new BuscarContactosResponse();
        //se genera una lista 
        Iterable<Agendadores> lista = iagendadores.findAll();
        //se recorre la lista de los contactos
        for (Agendadores agenda : lista) {
            BuscarContactosResponse.Contactos e = new BuscarContactosResponse.Contactos();
            //se obtienen los datos
            e.setNombre(agenda.getNombre());
            e.setId(agenda.getId());
            e.setNumero(agenda.getNumero());
            e.setCorreo(agenda.getCorreo());
            //los guarda encontactos
            respuesta.getContactos().add(e);
        }
        //regresa la respuesta 
        return respuesta;
    }

    @PayloadRoot(localPart = "EliminarContactoRequest", namespace = "https://t4is.uv.mx/agendabd")
    @ResponsePayload
    public EliminarContactoResponse eliminarContacto(@RequestPayload EliminarContactoRequest peticion){
        //se hace la respuesta de tipo eliminar contactos
        EliminarContactoResponse respuesta = new EliminarContactoResponse();
        //se obtiene el id del contacto a borrar y se elimina 
        iagendadores.deleteById(peticion.getId());
        respuesta.setRespuesta("contacto eliminado");
        //regresa la respuesta
        return respuesta;
    }

}

