package inc.elarriero.android.pruebashopping.live;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.otto.Bus;

import inc.elarriero.android.pruebashopping.infraestructura.ShopingAplicacion;

public class BaseLiveServicio {
    protected Bus bus;
    protected ShopingAplicacion application;
    protected FirebaseAuth auth;


    public BaseLiveServicio(ShopingAplicacion application) {
        this.application = application;
        bus = application.getBus();
        bus.register(this);
        auth = FirebaseAuth.getInstance();
    }
}
