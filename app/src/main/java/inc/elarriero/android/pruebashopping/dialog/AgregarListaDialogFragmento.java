package inc.elarriero.android.pruebashopping.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import inc.elarriero.android.pruebashopping.R;
import inc.elarriero.android.pruebashopping.servicios.CompraListaServicio;

public class AgregarListaDialogFragmento extends BaseDialog implements View.OnClickListener {
    @BindView(R.id.dialog_add_list_ediText)
    EditText newListName;

    public static AgregarListaDialogFragmento newInstance() {
        return new AgregarListaDialogFragmento();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View rootView = layoutInflater.inflate(R.layout.dialog_agregar_lista, null);
        ButterKnife.bind(this, rootView);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(rootView)
                .setPositiveButton("Crear", null)
                .setNegativeButton("Cancelar", null)
                .setTitle("Crear una Lista")
                .show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(this);
        return alertDialog;

    }

    //*****************************agrega nombre usuario y email **********
    @Override
    public void onClick(View view) {
        bus.post(new CompraListaServicio.AgregarCompraListaSolicitud(newListName.getText().toString(), userName, userEmail));

    }

    @Subscribe
    public void AgregarComprasLista(CompraListaServicio.AgregarCompraListaRespuesta respuesta) {
        if (!respuesta.didSuceed()) {
            newListName.setError(respuesta.getPropertyError("listaNombre"));

        } else {
            dismiss();
        }
    }
}
