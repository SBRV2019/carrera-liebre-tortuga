import contendiente.Animal;
import contendiente.Liebre;
import contendiente.Tortuga;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by adun on 11/05/2016.
 */
public class Carrera {
    public static void main(String[] args) throws InterruptedException {

        Animal liebre = new Liebre("liebre");
        Animal tortuga = new Tortuga("tortuga");

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
}
