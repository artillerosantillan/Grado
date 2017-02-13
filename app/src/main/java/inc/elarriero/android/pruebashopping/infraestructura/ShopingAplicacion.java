package inc.elarriero.android.pruebashopping.infraestructura;


import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.Firebase;
import com.squareup.otto.Bus;

import inc.elarriero.android.pruebashopping.live.Modulo;

public class ShopingAplicacion extends Application {

    private Bus bus;

    public ShopingAplicacion() {
        bus = new Bus();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Modulo.Register(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);

    }

    public Bus getBus() {
        return bus;
    }
}
