package contendiente;

/**
 * Created by adun on 11/05/2016.
 */
public class Tortuga extends Animal {

    private static final int PASO_RAPIDO = 3;
    private static final int RESBALON = -6;
    private static final int PASO_LENTO = 1;

    public Tortuga(String name) {
        super(name);
    }

    @Override
    public int getMovimiento(int aleatorio) {
        if (aleatorio >= 1 && aleatorio <= 5) {
            return PASO_RAPIDO;
        } else if (aleatorio >= 6 && aleatorio <= 7) {
            return RESBALON;
        } else if (aleatorio >= 8 && aleatorio <= 10) {
            return PASO_LENTO;
        } else {
            throw new IllegalArgumentException("aleatorio debe ser [1-10]");
        }
    }

    @Override
    public String getMovimientoName(int aleatorio) {
        if (aleatorio >= 1 && aleatorio <= 5) {
            return "PASO_RAPIDO";
        } else if (aleatorio >= 6 && aleatorio <= 7) {
            return "RESBALON";
        } else if (aleatorio >= 8 && aleatorio <= 10) {
            return "PASO_LENTO";
        } else {
            throw new IllegalArgumentException("aleatorio debe ser [1-10]");
        }
    }
}
