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
        createPlayers();                                         //crear jugadores 
        deckOfCards.shuffle();                                  //barajar 
        dealCardsToPlayer();                                   //Entrega las cartas iniciales al jugador 
        table.fillTable(deckOfCards, discardedCards);         //LLena la mesa con cartas iniciales (del mazo/descartes)
        int i=0;
        Player actualPlayer = null;
        boolean isWinner = false;
        boolean continueGame = true;

        do {

            if (i == players.size()) { // Si llegas al final de los jugadores
                i = 0; // Vuelves al principio
            }

            actualPlayer = players.get(i);
            turnoJugador(actualPlayer); // el jugador ejecuta su turno

            if (actualPlayer.winner()) { // Si el jugador ganó-> se termina la ronda
                isWinner = true;
            } else if (players.get(i).handIsEmpty()) { // Si el jugador se quedó sin cartas
                noHayCartas();
                continueGame = dealCardsToPlayer();
            }
            i++;

        } while (continueGame && !isWinner);

        if (!continueGame) {// Despues de cada ronda, se repartes cartas, hasta que se quede sin ellas
            int maxCont = 0; // Mayor puntuacion
            int posPlayer = 0; // posicion del jugador
            for (int j = 0; j < players.size(); j++) { // Busca quien tiene mas pajaros bajados
                if (players.get(j).getCount() > maxCont) { // Si el jugador tiene mas puntos
                    maxCont = players.get(j).getCount();
                    posPlayer = j; // Actualiza el ganador actual
                }
            }
            iu.displayMessage(
                    "El ganador es: " + players.get(posPlayer).getName() + " con " + maxCont + " pajaros bajados"); // muestra
                                                                                                                    // el
                                                                                                                    // ganador
        } else {
            iu.displayMessage(actualPlayer.getName() +
                    " ha conseguido 7 especies de pájaros distintas");
        }

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
     * dealCardsToPlayer: Repartir 8 cartas a los jugadores, devolviedo false en el caso de que no haya suficientes cartas a repartir 
     */
    public boolean dealCardsToPlayer(){            
        for (int i = 0; i < 8; i++) {      //Bucle para cuando i=7 (igual a 8 cartas)
            for (Player player:players) {  //Recorre todos los jugadores
                Card card = deckOfCards.takeCard();  //Saca una carta de la baraja y la guarda en la variable cards
                if(card != null){                    //Si quedan cartas en el mazo -> Añade esa carta que se ha eliminado de la baraja al jugador
                    player.addCard(card);
                }else{
                    return false;   // boolean para comprobar si hay suficienes cartas para repartir
                }
            }
        }
        return true;
    }

    /**
     * playCards: Gestiona una ronda del juego, haciendo que los jugadores jueguen turnos hasta que alguien se quede sin cartas 
     * o haya un ganador 
     */

    private void noHayCartas(){
        for(Player player : players){                                //Recorre las cartas de todos los jugadores
                int j = 0;
                while(!player.handIsEmpty()){                           //vacia la mano de cada jugador
                    discardedCards.addCardToEnd(player.removeCards(j));//elimina las cartas de su mano y las añade al monton de descartes
                }
            }
            deckOfCards.addListCards(discardedCards.removeCards());  //las cartas descartadas vuelven al mazo original
            deckOfCards.shuffle();
        
    }

    /* public void playCards(){ 
        Player actualPlayer= null; 
        boolean finRonda = false;                  //Indica si la ronda ha terminado
        int i = 0;                                                  
        do{                                         //Mientras que no se acaba la ronda              
            if(i == players.size()){             //Si llegas al final de los jugadores
                i = 0;                          //Vuelves al principio
            } 
            actualPlayer= players.get(i); 
            turnoJugador(actualPlayer);      //el jugador ejecuta su turno

             if(actualPlayer.winner()){     //Si el jugador ganó-> se termina la ronda 
            finRonda = true;
            }

            if(players.get(i).handIsEmpty()){ //Si el jugador se quedó sin cartas
                finRonda = true;             //Se acabo la ronda
            }
            i++;                            //Si no se quedo sin cartas-> siguiente jugador
        }while(!finRonda);   //este bucle continua mientras que no se acaba la ronda (algn se queda sin cartas) o no haya un ganador

        if(!players.get(i).winner()){     //Si no hay un ganador 
            for(Player player : players){                                //Recorre las cartas de todos los jugadores
                int j = 0;
                while(!player.handIsEmpty()){                           //vacia la mano de cada jugador
                    discardedCards.addCardToEnd(player.removeCards(j));//elimina las cartas de su mano y las añade al monton de descartes
                }
            }
            deckOfCards.addListCards(discardedCards.removeCards());  //las cartas descartadas vuelven al mazo original
        }
    }*/

    /**
     * turnoJugador: Controla todo el turno de un jugador dentro del juego
     * */
    public void turnoJugador(Player player){
        iu.displayMessage(table.toString());
        iu.displayMessage("Turno de: " + player.getName() + "\n"); 

        iu.displayMessage(player.toString()); //Se muestran las cartas que tiene ese jugador

        int birdList = iu.readListPlayer("Escoge el tipo de pájaro que quieres bajar 0-" + (player.howManyList() - 1) + ": ", player.howManyList());
        List <Card> cardsToTable = player.removeCards(birdList);  //El jugador saca una fila entera de cartas de su mano para jugarlas (se guardan en una variable temporal)
        int rowTable = iu.readRow("Escoge la fila donde quieres bajar las cartas: ");   //escoje la fila donde quiere jugar
    
        int lado= iu.readLado("Elige lado donde quieres bajar las cartas (O-Izquiera y 1-Derecha): ");     //despues escoje el lado de la filañ
        player.addCapturedCards(table.bajarCartas(cardsToTable, rowTable, lado)); //Coloca las cartas en la mesa (devuelve las cartas capturadas si hay) y añade esas cartas al jugafor 
        iu.displayMessage(player.toString()); //Muestra las cartas que tiene el jugador en la mano despues de bajar cartas
       
        while (table.validRow(rowTable)==false){                         //Mientras la fila no sea valida
            if (deckOfCards.isEmpty()){                                 //Si el mazo está vacio
                
                deckOfCards.addListCards(discardedCards.removeCards()); //recoje descartes
            }
            table.setCard(rowTable, lado, deckOfCards.takeCard());     //Añade cartas desde el mazo a la mesa hasta que la fila sea valida 

        }
        if(!player.handIsEmpty()){
            int decision = iu.readLado("Deseas añadir una especie a tu zona de juego (0- No, 1- Si): ");
            if (decision == 1){
                int selectedBird = iu.readListPlayer("Escoge el pájaro que quieres añadir al contador 0-" + (player.howManyList() - 1) + ": ", player.howManyList());
                if(player.increaseCount(selectedBird)){
                    discardedCards.addCardToEnd(player.removeCards(selectedBird)); //Las cartas que bajas se añaden al monton de descartes 
                    iu.displayMessage("\n Especie bajada correctamente\n ");
                }else{
                    iu.displayMessage("\n No es posible bajar esa especie a la zona de juego \n");
                    }
                }    
            }
        

    }

}
