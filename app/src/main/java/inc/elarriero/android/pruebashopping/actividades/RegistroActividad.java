package inc.elarriero.android.pruebashopping.actividades;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import inc.elarriero.android.pruebashopping.R;
import inc.elarriero.android.pruebashopping.servicios.CuentaServicios;

public class RegistroActividad extends BaseActividad {

    @BindView(R.id.activity_register_logginButton)
    Button loginButton;

    @BindView(R.id.activity_register_linear_layout)
    LinearLayout linearLayout;

    @BindView(R.id.activity_register_userEmail)
    EditText userEmail;

    @BindView(R.id.activity_register_userName)
    EditText userName;

    @BindView(R.id.activity_register_registerButton)
    Button registerButton;


    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registro);
        ButterKnife.bind(this);
        linearLayout.setBackgroundResource(R.drawable.background_screen_two);


        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Cargando....");
        mProgressDialog.setMessage("Intentando Registrar una Cuenta");
        mProgressDialog.setCancelable(false);

    }


    @OnClick(R.id.activity_register_logginButton)
    public void setLoginButton() {
        startActivity(new Intent(this, LoginActividad.class));
        finish();
    }

    @OnClick(R.id.activity_register_registerButton)
    public void setRegisterButton() {
        bus.post(new CuentaServicios.RegistroUserSolicitud(userName.getText().toString(), userEmail.getText().toString(), mProgressDialog));
    }

    @Subscribe
    public void RegistroUser(CuentaServicios.RegistroUserRespuesta respuesta) {
        if (!respuesta.didSuceed()) {
            userEmail.setError(respuesta.getPropertyError("email"));
            userName.setError(respuesta.getPropertyError("userName"));
        }
    }

}