package peliculasDB.auxiliar;

public class MiExcepcion extends Exception {

    private String codError;

    public MiExcepcion() {
        super("Error por defecto");
        codError = "Err0001";
    }

    public MiExcepcion(String msg, String codError) {
        super(msg);
        this.codError = codError;
    }

    public String getCodError() {
        return codError;
    }

    public void setCodError(String codError) {
        this.codError = codError;
    }
}
