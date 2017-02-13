package inc.elarriero.android.pruebashopping.vistas;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import inc.elarriero.android.pruebashopping.R;
import inc.elarriero.android.pruebashopping.entidades.ListaCompra;

public class ComprasListaViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.list_shopping_list_layout)
    public View layout;
    @BindView(R.id.list_shopping_list_listOwnerName)
    TextView nombrePropietario;
    @BindView(R.id.list_shopping_list_listName)
    TextView nombreLista;
    @BindView(R.id.list_shopping_list_dateCreated)
    TextView fechaCreacion;

    public ComprasListaViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    //******************** direccionando con MainActivity adapter *****************
    public void populate(ListaCompra listaCompra) {
        nombrePropietario.setText(listaCompra.getNombrePropietario());
        nombreLista.setText(listaCompra.getListaNombre());

        if (listaCompra.getFechaCreacion().get("selloDeTiempo") != null) {
            fechaCreacion.setText(convertirTiempo((long) listaCompra.getFechaCreacion().get("selloDeTiempo")));
        }
    }

    private String convertirTiempo(Long unixTime) {
        Date dateObject = new Date(unixTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yy kk:mm");
        return simpleDateFormat.format(dateObject);
    }
}
