package inc.elarriero.android.pruebashopping.actividades;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import inc.elarriero.android.pruebashopping.R;
import inc.elarriero.android.pruebashopping.dialog.AgregarListaDialogFragmento;
import inc.elarriero.android.pruebashopping.dialog.EliminarListaDialogFragmento;
import inc.elarriero.android.pruebashopping.entidades.ListaCompra;
import inc.elarriero.android.pruebashopping.infraestructura.Utils;
import inc.elarriero.android.pruebashopping.vistas.ComprasListaViewHolder;

public class MainActivity extends BaseActividad {

    @BindView(R.id.activity_main_FAB)
    FloatingActionButton floatingActionButton;

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView = (RecyclerView) findViewById(R.id.activity_main_listRecyclerView);

        // **************** titulo en la barra toolbar *******************

        String toolBarName;

        if (userName.contains(" ")) {
            toolBarName = userName.substring(0, userName.indexOf(" ")) + "' Lista de Compra";
        } else {
            toolBarName = userName + "' Lista de compra";
        }

        getSupportActionBar().setTitle(toolBarName);
    }
//**************************adaptador conexion (ComprasListaViewHolder) a  firebase para mostras en el recycler  *****************************************

    @Override
    protected void onResume() {
        super.onResume();
        final Firebase compraListaReference = new Firebase(Utils.FIRE_BASE_COMPRA_LISTA_REFERENCE + userEmail);

        //**********************ordenar por
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
        String sortOrder = sharedPreferences.getString(Utils.LISTA_ORDEN_PREFERENCE, Utils.ORDENARR_POR_CLAVE);
        Query sortQuery;

        if (sortOrder.equals(Utils.ORDENARR_POR_CLAVE)) {
            sortQuery = compraListaReference.orderByKey();
        } else {
            sortQuery = compraListaReference.orderByChild(sortOrder);
        }
        //******************************** ordenar por
        adapter = new FirebaseRecyclerAdapter<ListaCompra, ComprasListaViewHolder>(ListaCompra.class,
                R.layout.lista_compra_lista_agregadas,
                ComprasListaViewHolder.class,
                sortQuery) {


            @Override
            protected void populateViewHolder(ComprasListaViewHolder comprasListaViewHolder, final ListaCompra listaCompra, int i) {
                comprasListaViewHolder.populate(listaCompra);
                comprasListaViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<String> comprasListaInfo = new ArrayList<>();
                        comprasListaInfo.add(listaCompra.getId());
                        comprasListaInfo.add(listaCompra.getListaNombre());
                        comprasListaInfo.add(listaCompra.getEmailPropietario());
                        startActivity(ListaDetalleActividad.newInstance(getApplicationContext(), comprasListaInfo));

                    }
                });
//***********************************eliminar lista de compras **********************************
                comprasListaViewHolder.layout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (userEmail.equals(Utils.encodeEmail(listaCompra.getEmailPropietario()))) {
                            DialogFragment dialogFragment = EliminarListaDialogFragmento.newInstance(listaCompra.getId(), true);
                            dialogFragment.show(getFragmentManager(), EliminarListaDialogFragmento.class.getSimpleName());
                            return true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Solo el propietario puede eliminar una lista", Toast.LENGTH_LONG).show();
                            return true;
                        }
                    }

                });
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.cleanup();
    }

    //*********************************************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                SharedPreferences sharedPreferences2 = getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences2.edit();
                editor.putString(Utils.EMAIL, null).apply();
                editor.putString(Utils.USERNAME, null).apply();
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActividad.class));
                finish();
                return true;
            // llama a la Configuracion herramientas desde menu barra
            case R.id.action_sort:
                startActivity(new Intent(getApplicationContext(), ConfiguracionActividad.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    //***************** Agregar lista de compras  boton flotante**********************
    @OnClick(R.id.activity_main_FAB)
    public void setFloatingActionButton() {
        DialogFragment dialogFragment = AgregarListaDialogFragmento.newInstance();
        dialogFragment.show(getFragmentManager(), AgregarListaDialogFragmento.class.getSimpleName());
    }
}
