package inc.elarriero.android.pruebashopping.live;

import inc.elarriero.android.pruebashopping.infraestructura.ShopingAplicacion;

public class Modulo {
    public static void Register(ShopingAplicacion application) {
        new LiveCuentaServicios(application);
        new LiveCompraListaServicio(application);
    }
}