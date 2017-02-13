package inc.elarriero.android.pruebashopping.actividades;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class PantallaInicio extends BaseActividad {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}