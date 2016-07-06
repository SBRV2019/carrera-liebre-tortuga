package v2;

/**
 * Created by adun on 11/05/2016.
 */
public class Liebre2 extends Animal2 {

    private static final int DORMIR = 0;
    private static final int SALTO_LARGO = 9;
    private static final int RESBALON_GRANDE = -12;
    private static final int SALTO_PEQUEÑO = 1;
    private static final int RESBALON_PEQUEÑO = -2;

    public Liebre2(String name) {
        super(name);
    }

    @Override
    // 1-2    50%
    // 3-4    20%
    // 5      10%
    // 6-8    30%
    // 9-10   20%
    public int getMovimiento(int random) {
        if (random >= 1 && random <= 2) {
            return DORMIR;
        } else if (random >= 3 && random <= 4) {
            return SALTO_LARGO;
        } else if (random >= 5 && random <= 5) {
            return RESBALON_GRANDE;
        } else if (random >= 6 && random <= 8) {
            return SALTO_PEQUEÑO;
        } else if (random >= 9 && random <= 10) {
            return RESBALON_PEQUEÑO;
        } else {
            throw new IllegalArgumentException("random debe ser [1-10]");
        }
    }

    @Override
    public String getMovimientoName(int random) {
        if (random >= 1 && random <= 2) {
            return "DORMIR";
        } else if (random >= 3 && random <= 4) {
            return "SALTO_LARGO";
        } else if (random >= 5 && random <= 5) {
            return "RESBALON_GRANDE";
        } else if (random >= 6 && random <= 8) {
            return "SALTO_PEQUEÑO";
        } else if (random >= 9 && random <= 10) {
            return "RESBALON_PEQUEÑO";
        } else {
            throw new IllegalArgumentException("random debe ser [1-10]");
        }
    }

}
