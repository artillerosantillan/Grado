package inc.elarriero.android.pruebashopping.dialog;


import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.squareup.otto.Bus;

import inc.elarriero.android.pruebashopping.infraestructura.ShopingAplicacion;
import inc.elarriero.android.pruebashopping.infraestructura.Utils;

public class BaseDialog extends DialogFragment {
    protected ShopingAplicacion application;
    protected Bus bus;
    protected String userEmail, userName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (ShopingAplicacion) getActivity().getApplication();
        bus = application.getBus();
        bus.register(this);
        userEmail = getActivity().getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE).getString(Utils.EMAIL, "");
        userName = getActivity().getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE).getString(Utils.USERNAME, "");


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
