package inc.elarriero.android.pruebashopping.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.firebase.client.Firebase;

import inc.elarriero.android.pruebashopping.R;
import inc.elarriero.android.pruebashopping.servicios.CompraListaServicio;

public class EliminarListaDialogFragmento extends BaseDialog implements View.OnClickListener {


    public static final String EXTRA_COMPRAS_LISTA_ID = "EXTRA_SHOPPING_LIST_ID";
    public static final String EXTRA_BOOLEAN = "EXTRA_BOOLEAN";

    private String mComprasListaId;
    private boolean hascesClicEnMi;

    private Firebase mCompartirConReference;


    public static EliminarListaDialogFragmento newInstance(String shoppingListId, boolean isLongClicked) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_COMPRAS_LISTA_ID, shoppingListId);
        arguments.putBoolean(EXTRA_BOOLEAN, isLongClicked);

        EliminarListaDialogFragmento dialogFragment = new EliminarListaDialogFragmento();
        dialogFragment.setArguments(arguments);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComprasListaId = getArguments().getString(EXTRA_COMPRAS_LISTA_ID);
        hascesClicEnMi = getArguments().getBoolean(EXTRA_BOOLEAN);


    }

    //**************************fragmento elimainar lista de compras
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_eliminar_lista, null))
                .setPositiveButton("Confirmar", null)
                .setNegativeButton("Cancelar", null)
                .setTitle("Eliminar lista de compras?")
                .show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(this);
        return dialog;
    }


    @Override
    public void onClick(View view) {
        if (hascesClicEnMi) {
            dismiss();
            bus.post(new CompraListaServicio.EliminarComprasListaSolicitud(userEmail, mComprasListaId));

        }
    }
}
