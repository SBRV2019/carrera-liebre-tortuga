package v2;

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
public class Carrera2 {
  public static void main(String[] args) throws InterruptedException {

    Semaphore semaforoAvanzar = new Semaphore(0);
    Semaphore semaforoMovimiento = new Semaphore(0);

    Animal2 liebre = new Liebre2("liebre");
    Animal2 tortuga = new Tortuga2("tortuga");

    liebre.setSemaforoContendiente(tortuga.getSemaforoAnimal());
    liebre.setSemaforoAvanzar(semaforoAvanzar);
    liebre.setSemaforoMovimiento(semaforoMovimiento);

    tortuga.setSemaforoContendiente(liebre.getSemaforoAnimal());
    tortuga.setSemaforoAvanzar(semaforoAvanzar);
    tortuga.setSemaforoMovimiento(semaforoMovimiento);

    System.out.println("Bang");
    System.out.println("Comienza la carrera");

    int aleatorio;
    //    aleatorio = ThreadLocalRandom.current().nextInt(1, 10 + 1);
    //    liebre.setAleatorio(aleatorio);
    //    tortuga.setAleatorio(aleatorio);

    tortuga.start();
    liebre.start();

    //    while (!liebre.isMetaAlcanzada() && !tortuga.isMetaAlcanzada()) {
    while (true) {
      Thread.sleep(100); //tic-tac

      // aleatorio para avanzar
      aleatorio = ThreadLocalRandom.current().nextInt(1, 10 + 1);
      liebre.setAleatorio(aleatorio);
      tortuga.setAleatorio(aleatorio);

      // verificamos distancia
      if (liebre.getDistancia() == tortuga.getDistancia()) {
        if (liebre.getDistancia() >= 70 && tortuga.getDistancia() >= 70) {
          // aleatorio para resolver empate
          aleatorio = ThreadLocalRandom.current().nextInt(0, 1 + 1);
          if (aleatorio == 0) {
            System.out.println("Es un empate!");
          } else {
            System.out.println("LA TORTUGA GANÓ!!! EH!!! (por más débil)");
          }
        }
        if (!liebre.isDuerme() && liebre.getDistancia() != 1) {
          System.out.println("OUCH!!!");
        }
      }
      if (liebre.getDistancia() >= 70) {
        System.out.println("La Liebre ganó! Ugh!");

      } else if (tortuga.getDistancia() >= 70) {
        System.out.println("LA TORTUGA GANÓ!!! EH!!!");
        //si alcanzó y está durmiendo?
      }

      if (liebre.getDistancia() >= 70 || tortuga.getDistancia() >= 70) {
        liebre.setCarreraFinalizada(true);
        tortuga.setCarreraFinalizada(true);
        break;
      }

      semaforoAvanzar.release(2);

      //esperamos

      semaforoMovimiento.acquire(2);

    }

    semaforoAvanzar.release(2);
    todo puede que uno esté durmiendo;
  }


/*
    public static void main(String[] args) throws InterruptedException {

        Semaphore semaforoAvanzar = new Semaphore(0);
        Semaphore semaforoMovimiento = new Semaphore(0);

        Animal liebre = new Liebre2("liebre");
        Animal tortuga = new Tortuga2("tortuga");
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
            System.out.println("La Liebre2 ganó! Ugh!");

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
