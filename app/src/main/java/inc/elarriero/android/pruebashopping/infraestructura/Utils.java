package inc.elarriero.android.pruebashopping.infraestructura;


public class Utils {
    public static final String FIRE_BARS_BASE_URL = "https://databaset-2e4a0.firebaseio.com/";
    public static final String FIRE_BASE_USER_REFERENCE = FIRE_BARS_BASE_URL + "users/";
    public static final String FIRE_BASE_COMPRA_LISTA_REFERENCE = FIRE_BARS_BASE_URL + "ListaCompraUser/";


    public static final String MY_PREFERENCE = "NY_PREFERENCE";
    public static final String EMAIL = "EMAIL";
    public static final String USERNAME = "USERNAME";

    public static final String LISTA_ORDEN_PREFERENCE = "LIST_ORDEN_PREFERENCE";
    public static final String ORDENARR_POR_CLAVE = "ordenarPorClave";

    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    public static String decodeEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }
}
