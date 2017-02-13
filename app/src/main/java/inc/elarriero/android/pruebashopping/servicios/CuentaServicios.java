package inc.elarriero.android.pruebashopping.servicios;


import android.app.ProgressDialog;
import android.content.SharedPreferences;

import com.facebook.AccessToken;

import inc.elarriero.android.pruebashopping.infraestructura.ServicioRespuesta;

public class CuentaServicios {
    private CuentaServicios() {
    }

    public static class RegistroUserSolicitud {
        public String userName;
        public String userEmail;
        public ProgressDialog progressDialog;

        public RegistroUserSolicitud(String userName, String userEmail, ProgressDialog progressDialog) {
            this.userName = userName;
            this.userEmail = userEmail;
            this.progressDialog = progressDialog;
        }
    }

    public static class RegistroUserRespuesta extends ServicioRespuesta {
    }


    public static class LogUserEnSolicitud {
        public String userEmail;
        public String userPassword;
        public ProgressDialog progressDialog;
        public SharedPreferences sharedPreferences;


        public LogUserEnSolicitud(String userEmail, String userPassword, ProgressDialog progressDialog, SharedPreferences sharedPreferences) {
            this.userEmail = userEmail;
            this.userPassword = userPassword;
            this.progressDialog = progressDialog;
            this.sharedPreferences = sharedPreferences;
        }
    }

    public static class LogUserEnRespuesta extends ServicioRespuesta {

    }

    public static class LogUserInFacebookRequest {
        public AccessToken accessToken;
        public ProgressDialog progressDialog;
        public String userName;
        public String userEmail;
        public SharedPreferences sharedPreferences;


        public LogUserInFacebookRequest(AccessToken accessToken, ProgressDialog progressDialog, String userName, String userEmail, SharedPreferences sharedPreferences) {
            this.accessToken = accessToken;
            this.progressDialog = progressDialog;
            this.userName = userName;
            this.userEmail = userEmail;
            this.sharedPreferences = sharedPreferences;
        }
    }
}






