package gal.uvigo.esei.aed1.cubirds.iu;


import java.util.Scanner;

import es.uvigo.esei.aed1.tads.list.*;
import gal.uvigo.esei.aed1.cubirds.core.Player;

public class IU {

    private final Scanner keyboard;

    public IU() {
        keyboard = new Scanner(System.in);
    }

    /**
     * Lee un num. de teclado
     *
     * @param msg El mensaje a visualizar.
     * @return El num., como entero
     */
    public int readNumber(String msg) {
        boolean repeat;
        int toret = 0;

        do {
            repeat = false;
            System.out.print(msg);
            try {
                toret = Integer.parseInt(keyboard.nextLine());
            } catch (NumberFormatException exc) {
                repeat = true;
            }
        } while (repeat);

        return toret;
    }

    /**
     * Lee un string de teclado
     *
     * @param msg mensaje a mostrar antes de la lectura
     * @return el string leido
     */
    public String readString(String msg) {
        String toret;
        System.out.print(msg);
        toret = keyboard.nextLine();
        return toret;
    }

    /**
     * muestra un mensaje por pantalla
     *
     * @param msg el mensaje a mostrar
     */
    public void displayMessage(String msg) {
        System.out.println(msg);
    }

    /**
     * showCardsPlayers: Muestra las cartas de cada jugador 
     */
    public void showCardsPlayers(List<Player> players){
        displayMessage("Jugadores:");
        for(Player player: players){                //Recorre la lista de todos los jugadores 
            displayMessage(player.toString());   //Las cartas de cada jugador se muestran como está definido en el toString de player 
        }
    }

    public int readRow(String msg){
        int toret = 0;
        do {
           toret = readNumber(msg);
        } while ( toret < 0 && toret > 3);

        return toret;
    }
    public int readListPlayer(String msg, int tam){
        int toret = 0;
        do {
           toret = readNumber(msg);
        } while ( toret < 0 && toret > tam);

        return toret;
    }


}
