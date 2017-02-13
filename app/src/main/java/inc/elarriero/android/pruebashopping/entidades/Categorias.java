package inc.elarriero.android.pruebashopping.entidades;

public class Categorias {
    private String catID;
    private String nombrecat;
    private String imagencat;

    public Categorias() {
    }

    public Categorias(String catID, String nombrecat, String imagencat) {
        this.catID = catID;
        this.nombrecat = nombrecat;
        this.imagencat = imagencat;
    }

    public String getCatID() {
        return catID;
    }

    public String getNombrecat() {
        return nombrecat;
    }

    public String getImagencat() {
        return imagencat;
    }
}
