package inc.elarriero.android.pruebashopping.live;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.squareup.otto.Subscribe;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

import inc.elarriero.android.pruebashopping.actividades.LoginActividad;
import inc.elarriero.android.pruebashopping.actividades.MainActivity;
import inc.elarriero.android.pruebashopping.entidades.User;
import inc.elarriero.android.pruebashopping.infraestructura.ShopingAplicacion;
import inc.elarriero.android.pruebashopping.infraestructura.Utils;
import inc.elarriero.android.pruebashopping.servicios.CuentaServicios;

public class LiveCuentaServicios extends BaseLiveServicio {
    public LiveCuentaServicios(ShopingAplicacion application) {
        super(application);
    }

    // ***********************registro de usuario a firebase y envio de nueva contraseña a email***************************************
    @Subscribe
    public void RegistroUser(final CuentaServicios.RegistroUserSolicitud solicitud) {
        CuentaServicios.RegistroUserRespuesta respuesta = new CuentaServicios.RegistroUserRespuesta();

        if (solicitud.userEmail.isEmpty()) {
            respuesta.setPropertyErrors("email", "Por favor, introdusca su correo electronico.");
        }

        if (solicitud.userName.isEmpty()) {
            respuesta.setPropertyErrors("userName", "Por favor, introdusca su Nombre.");
        }

        if (respuesta.didSuceed()) {
            solicitud.progressDialog.show();
            SecureRandom random = new SecureRandom();
            final String radomPassword = new BigInteger(32, random).toString();

            auth.createUserWithEmailAndPassword(solicitud.userEmail, radomPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                solicitud.progressDialog.dismiss();
                                Toast.makeText(application.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                auth.sendPasswordResetEmail(solicitud.userEmail)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (!task.isSuccessful()) {
                                                    solicitud.progressDialog.dismiss();
                                                    Toast.makeText(application.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                } else {
                                                    Firebase reference = new Firebase(Utils.FIRE_BASE_USER_REFERENCE + Utils.encodeEmail(solicitud.userEmail));

                                                    HashMap<String, Object> timeJoined = new HashMap<>();
                                                    timeJoined.put("dateJoined", ServerValue.TIMESTAMP);

                                                    reference.child("email").setValue(solicitud.userEmail);
                                                    reference.child("name").setValue(solicitud.userName);
                                                    reference.child("hasLoggedInWithPassword").setValue(false);
                                                    reference.child("timeJoined").setValue(timeJoined);

                                                    Toast.makeText(application.getApplicationContext(), "Por favor, compruebe su correo electrónico", Toast.LENGTH_LONG)
                                                            .show();

                                                    solicitud.progressDialog.dismiss();

                                                    Intent intent = new Intent(application.getApplicationContext(), LoginActividad.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    application.startActivity(intent);

                                                }
                                            }
                                        });
                            }
                        }
                    });

        }

        bus.post(respuesta);
    }

    // ************************************************ Inciando solicitud de secion ****************************************************************
    @Subscribe
    public void LogEnUser(final CuentaServicios.LogUserEnSolicitud solicitud) {
        CuentaServicios.LogUserEnRespuesta respuesta = new CuentaServicios.LogUserEnRespuesta();

        if (solicitud.userEmail.isEmpty()) {
            respuesta.setPropertyErrors("email", "Por Favor, introdusca su correo electronico");
        }

        if (solicitud.userPassword.isEmpty()) {
            respuesta.setPropertyErrors("password", "Por Favor, introdusca su password");
        }

        if (respuesta.didSuceed()) {
            solicitud.progressDialog.show();
            auth.signInWithEmailAndPassword(solicitud.userEmail, solicitud.userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                solicitud.progressDialog.dismiss();
                                Toast.makeText(application.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                final Firebase userLocation = new Firebase(Utils.FIRE_BASE_USER_REFERENCE + Utils.encodeEmail(solicitud.userEmail));
                                userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        User user = dataSnapshot.getValue(User.class);
                                        if (user != null) {
                                            userLocation.child("hasLoggedInWithPassword").setValue(true);
                                            SharedPreferences sharedPreferences = solicitud.sharedPreferences;
                                            sharedPreferences.edit().putString(Utils.EMAIL, Utils.encodeEmail(user.getEmail())).apply();
                                            sharedPreferences.edit().putString(Utils.USERNAME, user.getName()).apply();

                                            solicitud.progressDialog.dismiss();
                                            Intent intent = new Intent(application.getApplicationContext(), MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            application.startActivity(intent);


                                        } else {
                                            solicitud.progressDialog.dismiss();
                                            Toast.makeText(application.getApplicationContext(), "Failed to connect to server: Please try again", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {
                                        solicitud.progressDialog.dismiss();
                                        Toast.makeText(application.getApplicationContext(), firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });

        }
        bus.post(respuesta);

    }

    //****************************************** iniciar con Facebook login*******************************************
    @Subscribe
    public void FacebookLogin(final CuentaServicios.LogUserInFacebookRequest request) {
        request.progressDialog.show();

        AuthCredential authCredential = FacebookAuthProvider.getCredential(request.accessToken.getToken());


        auth.signInWithCredential(authCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            request.progressDialog.dismiss();
                            Toast.makeText(application.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            final Firebase reference = new Firebase(Utils.FIRE_BASE_USER_REFERENCE + Utils.encodeEmail(request.userEmail));
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() == null) {
                                        HashMap<String, Object> timeJoined = new HashMap<>();
                                        timeJoined.put("dateJoined", ServerValue.TIMESTAMP);

                                        reference.child("email").setValue(request.userEmail);
                                        reference.child("name").setValue(request.userName);
                                        reference.child("hasLoggedInWithPassword").setValue(true);
                                        reference.child("timeJoined").setValue(timeJoined);

                                    }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {
                                    request.progressDialog.dismiss();
                                    Toast.makeText(application.getApplicationContext(), firebaseError.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                            SharedPreferences sharedPreferences = request.sharedPreferences;
                            sharedPreferences.edit().putString(Utils.EMAIL, Utils.encodeEmail(request.userEmail)).apply();
                            sharedPreferences.edit().putString(Utils.USERNAME, request.userName).apply();


                            request.progressDialog.dismiss();
                            Intent intent = new Intent(application.getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            application.startActivity(intent);
                        }
                    }
                });
    }


}