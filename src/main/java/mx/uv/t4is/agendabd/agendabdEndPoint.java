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

    @PayloadRoot(localPart = "CrearContactoRequest" ,namespace = "https://t4is.uv.mx/agendabd")
    @ResponsePayload
    public CrearContactoResponse CrearContacto(@RequestPayload CrearContactoRequest peticion) {
        CrearContactoResponse respuesta = new CrearContactoResponse();
        respuesta.setRespuesta("contacto guardado exitosamente! ");
        Agendadores agenda = new Agendadores();
        agenda.setNombre(peticion.getNombre());
        agenda.setNumero(peticion.getNumero());
        agenda.setCorreo(peticion.getCorreo());
        iagendadores.save(agenda);
        return respuesta;  
    }

    @PayloadRoot(localPart = "BuscarContactosRequest" ,namespace = "https://t4is.uv.mx/agendabd")
    @ResponsePayload
    public BuscarContactosResponse buscarContactos(){
        BuscarContactosResponse respuesta = new BuscarContactosResponse();
        Iterable<Agendadores> lista = iagendadores.findAll();
        
        for (Agendadores saludo : lista) {
            BuscarContactosResponse.Contactos e = new BuscarContactosResponse.Contactos();
            e.setNombre(saludo.getNombre());
            e.setId(saludo.getId());
            e.setNumero(saludo.getNumero());
            e.setCorreo(saludo.getCorreo());
            respuesta.getContactos().add(e);
        }
        return respuesta;
    }

    @PayloadRoot(localPart = "EliminarContactoRequest", namespace = "https://t4is.uv.mx/agendabd")
    @ResponsePayload
    public EliminarContactoResponse eliminarContacto(@RequestPayload EliminarContactoRequest peticion){
        EliminarContactoResponse respuesta = new EliminarContactoResponse();
        iagendadores.deleteById(peticion.getId());
        respuesta.setRespuesta("contacto eliminado");
        return respuesta;
    }

}
