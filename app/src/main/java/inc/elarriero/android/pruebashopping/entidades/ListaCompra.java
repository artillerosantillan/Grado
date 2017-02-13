package inc.elarriero.android.pruebashopping.entidades;


import com.firebase.client.ServerValue;

import java.util.HashMap;

public class ListaCompra {
    private String id;
    private String listaNombre;
    private String emailPropietario;
    private String nombrePropietario;
    private HashMap<String, Object> fechaCreacion;
    private HashMap<String, Object> fechaUltimaModif;


    public ListaCompra() {
    }

    public ListaCompra(String id, String listaNombre, String emailPropietario, String nompbrePropietario, HashMap<String, Object> fechaCreacion) {
        this.id = id;
        this.listaNombre = listaNombre;
        this.emailPropietario = emailPropietario;
        this.nombrePropietario = nompbrePropietario;
        this.fechaCreacion = fechaCreacion;
//***************fecha de ultima creaacion************
        HashMap<String, Object> fechaUltimaModifObject = new HashMap<>();
        fechaUltimaModifObject.put("fecha", ServerValue.TIMESTAMP);
        this.fechaUltimaModif = fechaUltimaModifObject;
    }

    public String getId() {
        return id;
    }

    public String getListaNombre() {
        return listaNombre;
    }

    public String getEmailPropietario() {
        return emailPropietario;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    //***********accediendo a la nueva ultima fecha de cracion ************
    public HashMap<String, Object> getFechaCreacion() {
        if (fechaUltimaModif != null) {
            return fechaCreacion;
        }
        HashMap<String, Object> fechaCreacionObject = new HashMap<>();
        fechaCreacionObject.put("fecha", ServerValue.TIMESTAMP);
        return fechaCreacionObject;
    }

    public HashMap<String, Object> getFechaUltimaModif() {
        return fechaUltimaModif;
    }
}


