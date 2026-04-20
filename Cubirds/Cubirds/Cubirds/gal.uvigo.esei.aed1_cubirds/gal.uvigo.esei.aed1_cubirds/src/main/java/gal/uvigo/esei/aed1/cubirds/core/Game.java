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
        iu.showCardsPlayers(players);
    }

      /**
     * dealCardsToPlayer:Inicializar los jugadores y definirlos 
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
                    player.addCard(table, card);
                }
            }
        }
    }

    

    
}
