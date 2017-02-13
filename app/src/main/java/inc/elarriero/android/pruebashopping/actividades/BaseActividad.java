package inc.elarriero.android.pruebashopping.actividades;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.otto.Bus;

import inc.elarriero.android.pruebashopping.infraestructura.ShopingAplicacion;
import inc.elarriero.android.pruebashopping.infraestructura.Utils;

public class BaseActividad extends AppCompatActivity {
    protected ShopingAplicacion application;
    protected Bus bus;
    protected FirebaseAuth auth;
    protected FirebaseAuth.AuthStateListener authStateListener;
    protected String userEmail, userName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (ShopingAplicacion) getApplication();
        bus = application.getBus();
        bus.register(this);

        final SharedPreferences sharedPreferences = getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE);
        userName = sharedPreferences.getString(Utils.USERNAME, "");
        userEmail = sharedPreferences.getString(Utils.EMAIL, "");


        Log.i("TAGLOG", userName);

        Log.i("TAGLOG", userEmail);


        auth = FirebaseAuth.getInstance();


        if (!((this instanceof LoginActividad) || (this instanceof RegistroActividad) || (this instanceof PantallaInicio))) {
            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Utils.EMAIL, null).apply();
                        editor.putString(Utils.USERNAME, null).apply();
                        startActivity(new Intent(getApplicationContext(), LoginActividad.class));
                        finish();

                    }
                }
            };
            if (userEmail.equals("")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Utils.EMAIL, null).apply();
                editor.putString(Utils.USERNAME, null).apply();
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActividad.class));
                finish();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!((this instanceof LoginActividad) || (this instanceof RegistroActividad) || (this instanceof PantallaInicio))) {
            auth.addAuthStateListener(authStateListener);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
        if (!((this instanceof LoginActividad) || (this instanceof RegistroActividad) || (this instanceof PantallaInicio))) {
            auth.removeAuthStateListener(authStateListener);

        }
    }


}
