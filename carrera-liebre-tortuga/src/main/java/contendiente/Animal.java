package contendiente;

import java.util.concurrent.Semaphore;

/**
 * Created by adun on 11/05/2016.
 */
public abstract class Animal extends Thread {

    private Semaphore semaphore = new Semaphore(0);
    private boolean metaAlcanzada = false;
    private int distancia = 1;
    private int aleatorio = 0;

    public Animal(String name) {
        super(name);
    }

    public boolean isMetaAlcanzada() {
        return metaAlcanzada;
    }

    public void setMetaAlcanzada(boolean metaAlcanzada) {
        this.metaAlcanzada = metaAlcanzada;
    }

    public void setAleatorio(int aleatorio) {
        this.aleatorio = aleatorio;
    }

    public int getDistancia() {
        return distancia;
    }

    public void ticTac() {
        semaphore.release();
    }

    @Override
    public void run() {

        System.out.println("La " + getName() + " se encuentra en el Kilómetro " + distancia);

        while (!metaAlcanzada) {

            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int movimiento = getMovimiento(aleatorio);
            if (movimiento != 0) {
                distancia += movimiento;
                if (distancia <= 1) {
                    distancia = 1;
                } else if (distancia >= 90) {
                    metaAlcanzada = true;
                }
                System.out.println("La " + getName() + " se encuentra en el Kilómetro " + distancia + " - Realizó el movimiento: " + getMovimientoName(aleatorio));
            } else {
                //dormir 1 vez, en el sgte se despiera
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public abstract int getMovimiento(int random);

    public abstract String getMovimientoName(int random);
}
