import contendiente.Animal;
import contendiente.Liebre;
import contendiente.Tortuga;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by adun on 11/05/2016.
 * <p>
 * https://github.com/lmiguelmh/carrera-liebre-tortuga
 * <p>
 * semTortuga = Semaphore (1)
 * Este semáforo le permite a la tortuga correr (realizar 1 movimiento). La liebre envía esta señal.
 * semLiebre = Semaphore (1)
 * Este semáforo le permite a la liebre correr (realizar 1 movimiento). La tortuga envía esta señal.
 * <p>
 * semAvanzar = Semaphore (0)
 * Este semáforo le permite tanto a la tortuga como a la liebre seguir corriendo
 * (realizar 1 movimiento a cada uno) después de realizarse la verificación acerca
 * de la posición de ambos contendientes. Esta verificación se realiza después que
 * ambos realizaron sus movimientos con el objetivo de determinar si la tortuga
 * muerde a la liebre o de dar por finalizada la carrera. La clase principal
 * envía esta señal.
 * <p>
 * semMovimiento = Semaphore (0)
 * Este semáforo le permite a la clase principal verificar la posición de ambos
 * contendientes para determinar si la tortuga muerde a la liebre.
 * La liebre y la tortuga envían esta señal.
 */
public class Carrera {
    public static void main(String[] args) throws InterruptedException {

        Semaphore semaforoAvanzar = new Semaphore(0);
        Semaphore semaforoMovimiento = new Semaphore(0);

        Animal liebre = new Liebre("liebre");
        Animal tortuga = new Tortuga("tortuga");
        liebre.setSemaforoContendiente(tortuga.getSemaforoAnimal());
        liebre.setSemaforoAvanzar(semaforoAvanzar);
        liebre.setSemaforoMovimiento(semaforoMovimiento);
        tortuga.setSemaforoContendiente(liebre.getSemaforoAnimal());
        tortuga.setSemaforoAvanzar(semaforoAvanzar);
        tortuga.setSemaforoMovimiento(semaforoMovimiento);

        System.out.println("Bang");
        System.out.println("Comienza la carrera");

        int aleatorio;
        aleatorio = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        liebre.setAleatorio(aleatorio);
        tortuga.setAleatorio(aleatorio);

        tortuga.start();
        liebre.start();

        while (!liebre.isMetaAlcanzada() && !tortuga.isMetaAlcanzada()) {
            Thread.sleep(100); //tic-tac

            semaforoAvanzar.release();
            semaforoAvanzar.release();

            aleatorio = ThreadLocalRandom.current().nextInt(1, 10 + 1);
            liebre.setAleatorio(aleatorio);
            tortuga.setAleatorio(aleatorio);

            semaforoMovimiento.acquire();
            semaforoMovimiento.acquire();

            if(liebre.getDistancia() == tortuga.getDistancia()) {
                if(!liebre.isDuerme() && liebre.getDistancia() != 1) {
                    System.out.println("OUCH!!!");
                }
            }
        }

        if (liebre.isMetaAlcanzada() && tortuga.isMetaAlcanzada()) {
            aleatorio = ThreadLocalRandom.current().nextInt(0, 1 + 1);
            if (aleatorio == 0) {
                System.out.println("Es un empate!");
            } else {
                System.out.println("LA TORTUGA GANÓ!!! EH!!! (por más débil)");
            }
        } else if (liebre.isMetaAlcanzada()) {
            System.out.println("La Liebre ganó! Ugh!");

        } else if (tortuga.isMetaAlcanzada()) {
            System.out.println("LA TORTUGA GANÓ!!! EH!!!");
            //si alcanzó y está durmiendo?
        }

        // finalizamos carrera
        liebre.setMetaAlcanzada(true);
        tortuga.setMetaAlcanzada(true);
        semaforoAvanzar.release(2+1); //+1 para liberar al dormilón (si lo hay)
    }
/*
    public static void main(String[] args) throws InterruptedException {

        Semaphore semaforoAvanzar = new Semaphore(0);
        Semaphore semaforoMovimiento = new Semaphore(0);

        Animal liebre = new Liebre("liebre");
        Animal tortuga = new Tortuga("tortuga");
        liebre.setSemaforoContendiente(tortuga.getSemaforoAnimal());
        tortuga.setSemaforoContendiente(liebre.getSemaforoAnimal());

        System.out.println("Bang");
        System.out.println("Comienza la carrera");

        tortuga.start();
        liebre.start();

        while (!liebre.isMetaAlcanzada() && !tortuga.isMetaAlcanzada()) {
            int aleatorio = ThreadLocalRandom.current().nextInt(1, 10+1);
            liebre.setAleatorio(aleatorio);
            tortuga.setAleatorio(aleatorio);

            Thread.sleep(100);

            liebre.ticTac();
            tortuga.ticTac();
        }

        if(liebre.isMetaAlcanzada() && tortuga.isMetaAlcanzada()) {
            int aleatorio = ThreadLocalRandom.current().nextInt(0,1+1);
            if(aleatorio == 0) {
                System.out.println("Es un empate!");
            } else {
                System.out.println("LA TORTUGA GANÓ!!! EH!!! (por más débil)");
            }
        } else if(liebre.isMetaAlcanzada()) {
            System.out.println("La Liebre ganó! Ugh!");

        } else if(tortuga.isMetaAlcanzada()) {
            System.out.println("LA TORTUGA GANÓ!!! EH!!!");
        }

        // finalizamos carrera
        liebre.setMetaAlcanzada(true);
        liebre.ticTac();
        tortuga.setMetaAlcanzada(true);
        tortuga.ticTac();
    }
*/
}
