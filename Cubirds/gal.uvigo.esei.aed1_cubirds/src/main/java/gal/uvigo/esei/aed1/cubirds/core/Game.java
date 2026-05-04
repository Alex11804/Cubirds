package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.*;
import gal.uvigo.esei.aed1.cubirds.iu.IU;


public class Game {
private DeckOfCards deckOfCards;
private List<Player> players;
private Table table;
private IU iu;

    public Game(IU iu) {
        this.deckOfCards = new DeckOfCards();
        this.players = new LinkedList<>();
        this.table = new Table();
        this.iu = iu;
    }

    /**
     * Metodo principal para jugar
     */
    public void play() {
        createPlayers();
        deckOfCards.shuffle();
        dealCardsToPlayer();
        table.fillTable(deckOfCards);
        iu.displayMessage(table.toString());
        iu.showCardsPlayers(players);//quitarlo

        
        playCards();
    }

      /**
     * createPlayers:Inicializar los jugadores y definirlos 
     */

    public void createPlayers(){
        int numPlayers;
        do{
            numPlayers = iu.readNumber("Introduce el numero de jugadores: ");
        }while(numPlayers<2||numPlayers>5);

        for (int i = 0; i < numPlayers; i++) {
            String name = iu.readString("Introduce el nombre del jugador " + (i+1) + " : ");
            players.add(i, new Player(name));
        }
    }
    
    /**
     * dealCardsToPlayer: Repartir 8 cartas a los jugadores 
     */
    public void dealCardsToPlayer(){
        for (int i = 0; i < 8; i++) {      //Bucle para cuando i=7 (igual a 8 cartas)
            for (Player player:players) {  //Recorre todos los jugadores
                Card card = deckOfCards.takeCard();  //Saca una carta de la baraja y la guarda en la variable cards
                if(card != null){                    //Si quedan cartas en el mazo -> Añade esa carta que se ha eliminado de la baraja al jugador
                    player.addCard(card);
                }
            }
        }
    }

    /**
     * playCards: Jugar cartas, hasta que uno see queda sin cartas
     */

    public void playCards(){ // HACER UN DO WHILE 
        boolean finRonda = false;                  //Indica si la ronda ha terminado
        int i = 0;                                                  
            while(!finRonda){                     //Mientras que no se acaba la ronda              
            if(i == players.size()){             //Si llegas al final de los jugadores
                i = 0;                          //Vuelves al principio
            } 
            turnoJugador(players.get(i));      //el jugador ejecuta su turno
            if(players.get(i).handIsEmpty()){ //Si el jugador se quedó sin cartas
                finRonda = true;             //Se acabo la ronda
            }
            i++;                            //Si no se quedo sin cartas-> siguiente jugador
        }
        iu.displayMessage(players.get(i - 1).getName() + " se ha quedado sin cartas");
    }

    /**
     * turnoJugador: el jugador juega su turno 
     */
    public void turnoJugador(Player player){
        iu.displayMessage(table.toString());  //Se muestra la mesa 
        iu.displayMessage("Turno de: " + player.getName()); 

        iu.displayMessage(player.toString()); //Se muestran las cartas que tiene ese jugador

        int numListCard = iu.readListPlayer("Escoge el tipo de pájaro que quieres bajar 0-" + (player.howManyList() - 1) + ": ", player.howManyList());
        List <Card> cartasMesa = player.removeCards(numListCard);  //El jugador saca una fila entera de cartas de su mano para jugarlas (se guardan en una variable temporal)
        int rowTable = iu.readRow("Escoge la fila donde quieres bajar las cartas: ");   //escoje la fila donde quiere jugar
        //despues escoje el lado de la fila
        int lado= iu.readLado("Elige lado donde quieres bajar las cartas (O-Izquiera y 1-Derecha): "); 
        player.addCapturedCards(table.bajarCartas(cartasMesa, rowTable, lado)); //Coloca las cartas en la mesa (devuelve las cartas capturadas si hay) y añade esas cartas al jugafor 


        iu.displayMessage(player.toString());
        
    }
    
}
