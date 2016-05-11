package contendiente;

import java.util.concurrent.Semaphore;

/**
 * Created by adun on 11/05/2016.
 * semTortuga = Semaphore (1)
 * Este semáforo le permite a la tortuga correr (realizar 1 movimiento). La liebre envía esta señal.
 * semLiebre = Semaphore (1)
 * Este semáforo le permite a la liebre correr (realizar 1 movimiento). La tortuga envía esta señal.
 * semAvanzar = Semaphore (0)
 * Este semáforo le permite tanto a la tortuga como a la liebre seguir corriendo
 * (realizar 1 movimiento a cada uno)
 * después de realizarse la verificación acerca de la posición de ambos contendientes.
 * Esta verificación se realiza
 * después que ambos realizaron sus movimientos con el objetivo de determinar si la
 * tortuga muerde a la liebre o de
 * dar por finalizada la carrera. La clase principal envía esta señal.
 * semMovimiento = Semaphore (0)
 * Este semáforo le permite a la clase principal verificar la posición de ambos contendientes para determinar si la
 * tortuga muerde a la liebre. La liebre y la tortuga envían esta señal.
 */
public abstract class Animal extends Thread {

    private Semaphore semaforoAnimal = new Semaphore(1);
    private Semaphore semaforoContendiente = null; //explícito!
    private Semaphore semaforoAvanzar = null;
    private boolean metaAlcanzada = false;
    private int distancia = 1;
    private int aleatorio = 0;

    public Animal(String name) {
        super(name);
    }

    public Semaphore getSemaforoAnimal() {
        return semaforoAnimal;
    }

    public void setSemaforoContendiente(Semaphore semaforoContendiente) {
        this.semaforoContendiente = semaforoContendiente;
    }

    public void setSemaforoAvanzar(Semaphore semaforoAvanzar) {
        this.semaforoAvanzar = semaforoAvanzar;
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

    //  Semaphore semaphore = new Semaphore(0);
    //    public void ticTac() {
    //        semaphore.release();
    //    }

    private void carrera() throws InterruptedException {

        System.out.println("La " + getName() + " se encuentra en el Kilómetro " + distancia);

        while (!metaAlcanzada) {

            semaforoContendiente.acquire(); //espera a contendiente

            int movimiento = getMovimiento(aleatorio);
            if (movimiento == 0) {
                //dormir 2 segundos (turnos)
                semaforoAnimal.release();
                //esperamos señal para avanzar turno
                semaforoAvanzar.acquire();
                //zzz
                System.out.println("La " + getName() + " se encuentra en el Kilómetro " + distancia + " - Realizó el movimiento: " + getMovimientoName(aleatorio));
                semaforoContendiente.acquire();
            }

            semaforoAnimal.release(); //ya ha hecho movimiento libero

            //esperamos señal para avanzar turno
            semaforoAvanzar.acquire();
            {
                distancia += movimiento;
                if (distancia <= 1) {
                    distancia = 1;
                } else if (distancia >= 90) {
                    metaAlcanzada = true;
                }
                System.out.println("La " + getName() + " se encuentra en el Kilómetro " + distancia + " - Realizó el movimiento: " + getMovimientoName(aleatorio));
            }

        }
    }

    @Override
    public void run() {
        try {
            carrera();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

/*
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
                //dormir 1 vez, en el sgte se despierta
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
*/

    public abstract int getMovimiento(int random);

    public abstract String getMovimientoName(int random);
}
