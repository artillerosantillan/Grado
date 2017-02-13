package inc.elarriero.android.pruebashopping.actividades;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import inc.elarriero.android.pruebashopping.R;
import inc.elarriero.android.pruebashopping.infraestructura.Utils;
import inc.elarriero.android.pruebashopping.servicios.CuentaServicios;

public class LoginActividad extends BaseActividad {

    @BindView(R.id.activity_login_linear_layout)
    LinearLayout linearLayout;

    @BindView(R.id.activity_login_registerButton)
    Button registerButton;

    @BindView(R.id.activity_login_loginButton)
    Button loginButton;

    @BindView(R.id.activity_login_userEmail)
    EditText userEmail;

    @BindView(R.id.activity_login_userPassword)
    EditText userPassword;

    @BindView(R.id.activity_login_facebook_button)
    LoginButton facebookButton;


    private ProgressDialog mProgressDialog;

    private CallbackManager mCallbackManager;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_login);
        ButterKnife.bind(this);
        linearLayout.setBackgroundResource(R.drawable.background_screen_two);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Cargando....");
        mProgressDialog.setMessage("Intentando aceder a su Cuenta");
        mProgressDialog.setCancelable(false);

        sharedPreferences = getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE);


    }

    @OnClick(R.id.activity_login_registerButton)
    public void setRegisterButton() {
        startActivity(new Intent(this, RegistroActividad.class));
        finish();
    }

    @OnClick(R.id.activity_login_loginButton)
    public void setLoginButton() {
        bus.post(new CuentaServicios.LogUserEnSolicitud(userEmail.getText().toString(),
                userPassword.getText().toString(), mProgressDialog, sharedPreferences));
    }

    //************************************************** inicializacion login facebook **********************
    @OnClick(R.id.activity_login_facebook_button)
    public void setFacebookButton() {
        mCallbackManager = CallbackManager.Factory.create();
        facebookButton.setReadPermissions("email", "public_profile");

        facebookButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String email = object.getString("email");
                                    String name = object.getString("name");
                                    bus.post(new CuentaServicios.LogUserInFacebookRequest(loginResult.getAccessToken(), mProgressDialog,
                                            name, email, sharedPreferences));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplication(), "Un error desconocido ocurrió.", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplication(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    //************************************iniciar login*********************
    @Subscribe
    public void LogUserEn(CuentaServicios.LogUserEnRespuesta respuesta) {
        if (!respuesta.didSuceed()) {
            userEmail.setError(respuesta.getPropertyError("email"));
            userPassword.setError(respuesta.getPropertyError("password"));
        }
    }
}