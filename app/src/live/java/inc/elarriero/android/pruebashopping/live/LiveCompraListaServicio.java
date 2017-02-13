package inc.elarriero.android.pruebashopping.live;


import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.squareup.otto.Subscribe;

import java.util.HashMap;

import inc.elarriero.android.pruebashopping.entidades.ListaCompra;
import inc.elarriero.android.pruebashopping.infraestructura.ShopingAplicacion;
import inc.elarriero.android.pruebashopping.infraestructura.Utils;
import inc.elarriero.android.pruebashopping.servicios.CompraListaServicio;

public class LiveCompraListaServicio extends BaseLiveServicio {
    public LiveCompraListaServicio(ShopingAplicacion application) {
        super(application);
    }

    //******************* Agrega Compras a  las lista *****************************
    @Subscribe
    public void AgregarComprasLista(CompraListaServicio.AgregarCompraListaSolicitud solicitud) {
        CompraListaServicio.AgregarCompraListaRespuesta respuesta = new CompraListaServicio.AgregarCompraListaRespuesta();

        if (solicitud.compraListaNombre.isEmpty()) {
            respuesta.setPropertyErrors("listName", "La lista de compras debe tener un nombre");
        }
//**********crea en db firebase la lista agregada(agregarListaDialogFragento)" + los respectivos campos del Usuario que hace la lista**********************
        if (respuesta.didSuceed()) {
            Firebase reference = new Firebase(Utils.FIRE_BASE_COMPRA_LISTA_REFERENCE + solicitud.emailPropietario).push();
            HashMap<String, Object> sellodDeTiempoCreado = new HashMap<>();
            sellodDeTiempoCreado.put("selloDeTiempo", ServerValue.TIMESTAMP);
            ListaCompra listaCompra = new ListaCompra(reference.getKey(), solicitud.compraListaNombre,
                    solicitud.emailPropietario, solicitud.nombrePropietario, sellodDeTiempoCreado);
            reference.child("id").setValue(listaCompra.getId());
            reference.child("listaNombre").setValue(listaCompra.getListaNombre());
            reference.child("emailPropietario").setValue(listaCompra.getEmailPropietario());
            reference.child("nombrePropietario").setValue(listaCompra.getNombrePropietario());
            reference.child("fechaCreacion").setValue(listaCompra.getFechaCreacion());
            reference.child("fechaUltimaModif").setValue(listaCompra.getFechaUltimaModif());
            Toast.makeText(application.getApplicationContext(), "La lista ha sido creada!", Toast.LENGTH_LONG).show();
        }

        bus.post(respuesta);
    }

    //*********************************

    @Subscribe
    public void EliminarComprasLista(CompraListaServicio.EliminarComprasListaSolicitud solicitud) {
        Firebase reference = new Firebase(Utils.FIRE_BASE_COMPRA_LISTA_REFERENCE + solicitud.emailPropietario + "/" + solicitud.comprasListaId);
        /* Firebase itemReference = new Firebase(Utils.FIRE_BASE_LIST_ITEMS_REFERENCE + request.shoppingListId);
        Firebase sharedWithReference = new Firebase(Utils.FIRE_BASE_SHARED_WITH_REFERENCE + request.shoppingListId);
       itemReference.removeValue();
        sharedWithReference.removeValue();
       */
        reference.removeValue();

    }
}