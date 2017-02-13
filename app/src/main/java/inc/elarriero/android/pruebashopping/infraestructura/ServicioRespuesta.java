package inc.elarriero.android.pruebashopping.infraestructura;


import java.util.HashMap;

public class ServicioRespuesta {
    private HashMap<String, String> propertyErrors;

    public ServicioRespuesta() {
        propertyErrors = new HashMap<>();
    }

    public void setPropertyErrors(String property, String error) {
        propertyErrors.put(property, error);
    }

    public String getPropertyError(String property) {
        return propertyErrors.get(property);
    }

    public boolean didSuceed() {
        return (propertyErrors.size() == 0);
    }

}