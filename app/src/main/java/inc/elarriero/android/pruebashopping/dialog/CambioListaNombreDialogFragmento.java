package inc.elarriero.android.pruebashopping.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import inc.elarriero.android.pruebashopping.R;

public class CambioListaNombreDialogFragmento extends BaseDialog implements View.OnClickListener {

    public static final String COMPRA_LISTA_EXTRA_INFO = "COMPRA_LISTA_EXTRA_INFO";
    @BindView(R.id.dialog_change_list_name_editText)
    EditText newListName;
    private String mComprasListaId;

    public static CambioListaNombreDialogFragmento newInstance(ArrayList<String> comprasListaInfo) {
        Bundle arguments = new Bundle();
        arguments.putStringArrayList(COMPRA_LISTA_EXTRA_INFO, comprasListaInfo);
        CambioListaNombreDialogFragmento dialogFragment = new CambioListaNombreDialogFragmento();
        dialogFragment.setArguments(arguments);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComprasListaId = getArguments().getStringArrayList(COMPRA_LISTA_EXTRA_INFO).get(0);

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_cambiar_lista_nombre, null);
        ButterKnife.bind(this, rootView);
        newListName.setText(getArguments().getStringArrayList(COMPRA_LISTA_EXTRA_INFO).get(1));

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(rootView)
                .setPositiveButton("Cambiar Nombre", null)
                .setNegativeButton("Cancelar", null)
                .setTitle("Cambiar el Nombre de la Lista de Compras?")
                .show();

        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(this);

        return alertDialog;


    }


    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "Ahora el nombre de la lista se cambiará pronto", Toast.LENGTH_LONG).show();

    }
}
