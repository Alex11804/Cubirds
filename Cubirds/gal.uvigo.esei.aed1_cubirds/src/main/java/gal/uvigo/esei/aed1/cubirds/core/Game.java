package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.*;
import gal.uvigo.esei.aed1.cubirds.iu.IU;


public class Game {
private DeckOfCards deckOfCards;
private List<Player> players;
private Table table;
private DiscardedCards discardedCards; 
private IU iu;

    public Game(IU iu) {
        this.deckOfCards = new DeckOfCards();
        this.players = new LinkedList<>();
        this.table = new Table();
        this.discardedCards= new DiscardedCards(); 
        this.iu = iu;
    }

    /**
     * Metodo principal para jugar
     */
    public void play() {
        createPlayers();
        deckOfCards.shuffle();
        dealCardsToPlayer();
        table.fillTable(deckOfCards, discardedCards);
        iu.displayMessage(table.toString());
        do{
            playCards();
        }while(dealCardsToPlayer());
        
        int maxCont=0;
        int posPlayer=0;
        for(int i = 0; i<players.size(); i++){
            if(players.get(i).getCount()>maxCont){
                maxCont=players.get(i).getCount();
                posPlayer = i;
            }
        }
        iu.displayMessage("El ganador es: " + players.get(posPlayer).getName() + " con " + maxCont + " pajaros bajados");
        
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
    public boolean dealCardsToPlayer(){
        for (int i = 0; i < 8; i++) {      //Bucle para cuando i=7 (igual a 8 cartas)
            for (Player player:players) {  //Recorre todos los jugadores
                Card card = deckOfCards.takeCard();  //Saca una carta de la baraja y la guarda en la variable cards
                if(card != null){                    //Si quedan cartas en el mazo -> Añade esa carta que se ha eliminado de la baraja al jugador
                    player.addCard(card);
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * playCards: Jugar cartas, hasta que uno see queda sin cartas
     */

    public void playCards(){ // HACER UN DO WHILE 
        
        boolean finRonda = false;                  //Indica si la ronda ha terminado
        int i = 0;                                                  
        do{                     //Mientras que no se acaba la ronda              
            if(i == players.size()){             //Si llegas al final de los jugadores
                i = 0;                          //Vuelves al principio
            } 
            turnoJugador(players.get(i));      //el jugador ejecuta su turno
            if(players.get(i).handIsEmpty()){ //Si el jugador se quedó sin cartas
                finRonda = true;             //Se acabo la ronda
            }
            i++;                            //Si no se quedo sin cartas-> siguiente jugador
        }while(!finRonda);
        for(Player player : players){
            int j = 0;
            while(!player.handIsEmpty()){
                discardedCards.addCardToEnd(player.removeCards(j));
            }
            deckOfCards.addListCards(discardedCards.removeCards());
        }

    }

    /**
     * turnoJugador: el jugador juega su turno 
     */
    public void turnoJugador(Player player){
        iu.displayMessage(table.toString());  //Se muestra la mesa 
        iu.displayMessage("Turno de: " + player.getName()); 

        iu.displayMessage(player.toString()); //Se muestran las cartas que tiene ese jugador

        int birdList = iu.readListPlayer("Escoge el tipo de pájaro que quieres bajar 0-" + (player.howManyList() - 1) + ": ", player.howManyList());
        List <Card> cardsToTable = player.removeCards(birdList);  //El jugador saca una fila entera de cartas de su mano para jugarlas (se guardan en una variable temporal)
        int rowTable = iu.readRow("Escoge la fila donde quieres bajar las cartas: ");   //escoje la fila donde quiere jugar
        //despues escoje el lado de la fila
        int lado= iu.readLado("Elige lado donde quieres bajar las cartas (O-Izquiera y 1-Derecha): "); 
        player.addCapturedCards(table.bajarCartas(cardsToTable, rowTable, lado)); //Coloca las cartas en la mesa (devuelve las cartas capturadas si hay) y añade esas cartas al jugafor 
        iu.displayMessage(player.toString());
       
        do{ 
            if (deckOfCards.isEmpty()){
                while (!discardedCards.isEmpty()){
                    deckOfCards.addListCards(discardedCards.removeCards());
                }
            }
            table.setCard(rowTable, lado, deckOfCards.takeCard());

        }while (table.validRow(rowTable)==false);  

        int decision = iu.readLado("Deseas añadir una especie a tu zona de juego (0- No, 1- Si)");
        if (decision == 1){
            int selectedBird = iu.readListPlayer("Escoge el pájaro que quieres añadir al contador 0-" + (player.howManyList() - 1) + ": ", player.howManyList());
            if(player.increaseCount(selectedBird)){
                discardedCards.addCardToEnd(player.removeCards(selectedBird));
            }else{
                iu.displayMessage("No es posible bajar esa especie a la zona de juego");
            }
        }
        if(player.winner()){
            iu.displayMessage(player.getName() + "ha conseguido 7 especies de pajaros distintas");
        }else{

        }
        
      } 
    
    
}


