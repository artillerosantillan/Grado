package inc.elarriero.android.pruebashopping.actividades;


import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.ButterKnife;
import inc.elarriero.android.pruebashopping.R;
import inc.elarriero.android.pruebashopping.dialog.CambioListaNombreDialogFragmento;
import inc.elarriero.android.pruebashopping.infraestructura.Utils;

public class ListaDetalleActividad extends BaseActividad {


    public static final String COMPRA_LISTA_DETALLE = "COMPRA_LISTA_DETALLE";

    private String mComprasId;
    private String mComprasName;
    private String mComprasPropietario;

    public static Intent newInstance(Context context, ArrayList<String> shoppingListInfo) {
        Intent intent = new Intent(context, ListaDetalleActividad.class);
        intent.putStringArrayListExtra(COMPRA_LISTA_DETALLE, shoppingListInfo);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_lista_detalle);
        ButterKnife.bind(this);
        mComprasId = getIntent().getStringArrayListExtra(COMPRA_LISTA_DETALLE).get(0);
        mComprasName = getIntent().getStringArrayListExtra(COMPRA_LISTA_DETALLE).get(1);
        mComprasPropietario = getIntent().getStringArrayListExtra(COMPRA_LISTA_DETALLE).get(2);
        getSupportActionBar().setTitle(mComprasName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (Utils.encodeEmail(mComprasPropietario).equals(userEmail)) {
            getMenuInflater().inflate(R.menu.menu_lista_detalle, menu);
            return true;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_list_name:
                ArrayList<String> shoppingListInfo = new ArrayList<>();
                shoppingListInfo.add(mComprasId);
                shoppingListInfo.add(mComprasName);
                DialogFragment dialogFragment = CambioListaNombreDialogFragmento.newInstance(shoppingListInfo);
                dialogFragment.show(getFragmentManager(), CambioListaNombreDialogFragmento.class.getSimpleName());
                return true;


        }
        return true;
    }
}
