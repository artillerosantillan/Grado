package inc.elarriero.android.pruebashopping.servicios;


import inc.elarriero.android.pruebashopping.infraestructura.ServicioRespuesta;

public class CompraListaServicio {

    public CompraListaServicio() {
    }

    public static class AgregarCompraListaSolicitud {
        public String compraListaNombre;
        public String nombrePropietario;
        public String emailPropietario;

        public AgregarCompraListaSolicitud(String compraListaNombre, String nombrePropietario, String emailPropietario) {
            this.compraListaNombre = compraListaNombre;
            this.nombrePropietario = nombrePropietario;
            this.emailPropietario = emailPropietario;
        }
    }

    public static class AgregarCompraListaRespuesta extends ServicioRespuesta {

    }

    //***********************Elimnar lista compras ******************
    public static class EliminarComprasListaSolicitud {
        public String emailPropietario;
        public String comprasListaId;

        public EliminarComprasListaSolicitud(String emailPropietario, String comprasListaId) {
            this.emailPropietario = emailPropietario;
            this.comprasListaId = comprasListaId;
        }
    }
}
