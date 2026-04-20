package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.*;;

public class Table {
    private List<Card>[] cardsTable;  //Se crea un array de cartas 

    
    /**
     * Constructor: Se crea un array vacío de 4 filas
     */ 
    
    public Table() {
        this.cardsTable = new List[4];
        for (int i = 0; i < cardsTable.length; i++) {
            cardsTable[i] = new LinkedList<>();
        }
    }

    /**
     * setCard:Se añade una carta, pasada por parámetro al array de cartas.
     */

    public void setCard(int row, int lado, Card card){
    
        if(lado == 1){
            cardsTable[row].addFirst(card);
        }else if (lado == 2){
            cardsTable[row].addLast(card);
        }
        
    }


    /**
     * fillTable: Colocar las cartas en la mesa, de manera que no haya dos especies de pajaros iguales seguidos *
     */

    public void fillTable(DeckOfCards deckOfCards){
        for (int row = 0; row < 4; row++) {     //4 filas de cartas 
            int column = 0;
            while(column < 3){                 //n columnas de cartas 
                Card card = deckOfCards.takeCard();           //Por cada columna se guarda en una variable temporal card, la carta que queramos colocar en la mesa (y esta se elimina de la baraja)
                
                    if(!sameTypeOfBird(row, card)){         //Si el tipo de pajaro de esa carta es distinto al anterior (en caso de que haya anterior, si no ,se ejecutaría igualmente)
                        cardsTable[row].addLast(card);;         //Se coloca la carta en la mesa 
                        column++;                         //Se pasa a la siguiente columna 
                    }else{                               //En caso de que el tipo de pajaro es igual al anterior
                        deckOfCards.addCardToEnd(card); // La carta descartada se añade al final de la baraja para no perderla
                    }
                
            }
        }
    }
    
    /**
     * sameTypeOfBird: Comparar si tenemos o no el mismo tipo de pajaro en dos cartas 
     */

    public boolean sameTypeOfBird(int row, Card card){

        if (cardsTable[row]==null){
            return false;
        }else{
            for(int column = 0 ; column < cardsTable[row].size() ; column++){        //Recorre las n columnas que hay por cada fila con el metodo size().
                Card cardOnTable = cardsTable[row].get(column);                     //Coges la carta que está en la mesa y la guardas en la variable Card (pero esta carta no se elimina)
                                                                                    //Devuelve true si la la variable temporal no es nula y su tipo de pájaro (la carta que ya tenemos en la mesa) = el tipo de la carta que pasamos por parámetro (carta que queremos añadir a la mesa) 
                if(cardOnTable != null && cardOnTable.getTypeBird().equals(card.getTypeBird())){ 
                    return true;
                }
            }
        }
        return false;                                      //Si no , devuelve falso 
    }
    
    @Override
    public String toString() {
        System.out.println("Mesa: ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {                       //Bucle que controla las filas (tienen que ser 4)
            sb.append("Fila ").append(i + 1).append(": "); //Cada vez que empieza una fila, añade el texto "Fila 1: ", "Fila 2: ", etc.
            for (int j = 0; j < cardsTable[i].size(); j++) {                 //Bucle que controla las columnas 
                sb.append(cardsTable[i].get(j).toString());  //Muestra cada carta, de la manera que se indica en el toString de Cards (ya que cardsTable[][] , lo inicializamos en el constructor como tipo carta )
            }
            sb.append("\n");
        }
        return sb.toString();                         //Devuelve la variable temporal donde hemos guardado la representación de nuestra carta  
    }
}
