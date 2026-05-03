package gal.uvigo.esei.aed1.cubirds.core;



import es.uvigo.esei.aed1.tads.list.*;;

public class Table {
    private List<Card>[] cardsTable;  //Se crea un array de cartas 

    
    /**
     * Constructor: Se crea un array vacío formado por 4 listas (array de listas)
     */ 
    
    public Table() {
        this.cardsTable = new List[4];
        for (int i = 0; i < 4; i++) {
            this.cardsTable[i] = new LinkedList<>();
        }
    }

    /**
     * setCard:Se añade una carta, pasada por parámetro al array de cartas.
     */

    public void setCard(int row, int lado, Card card){
    
        if(lado == 0){                           //Si se añade por la izquierda
            cardsTable[row].addFirst(card);
        }else {                                 //Si se añade por la derecha
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
                    cardsTable[row].addLast(card);;    //Se coloca la carta en la mesa 
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

    public List<Card> bajarCartas(List<Card> listaCartas,int fila,int lado){

        //1º COLOCAR LAS CARTAS EN LA MESA 
        while(!listaCartas.isEmpty()){                          //Mientras que haya cartas en listaCartas
           setCard(fila, lado, listaCartas.removeFirst());     //Saca una carta (removeFirst) y la coloca en la mesa (setCard)
        }
        List<Card> cartasRodeadas = new LinkedList<>();       //Creamos una lista para guardar las cartas capturadas 
        int i=0;
        int j; 
       
        if (lado==0){  //JUGAMOS POR EL LADO IZQUIERDO , lado=0
            
           TypeBird tipoInicial = cardsTable[fila].get(0).getTypeBird(); //Tipo de la primera carta en la fila (del array de cartas)
           
           //Mientras no llegemos al final de la fila y la carta sea del mismo tipo que la inicial
           while (i<cardsTable[fila].size()&& cardsTable[fila].get(i).getTypeBird().equals(tipoInicial)){
                i++;   //Avanzamos 
            }//El tipo de carta ya no es el mismo(Encontramos el final del 1º bloque )

            //Buscamos otro bloque del mismo tipo pero mas a la drecha 
            j=i;
            //Mientras j no llegue al final de la fila y el tipo inicial sea distinto al tipo de cartas 
            while (j != cardsTable[fila].size() && !tipoInicial.equals(cardsTable[fila].get(j).getTypeBird())){
                j++;   //Avanzamos 
            }

            //SI NO SE VOLVIO A ENCONTRAR UNA CARTA DEL MISMO TIPO 
            if(j == cardsTable[fila].size()){  //No hay captura 
                return cartasRodeadas;
            }else{                            //SI SI SE ENCONTRO OTRA CARTA DEL MISMO TIPO
                while(i <= j - 1){
                    cartasRodeadas.addLast(cardsTable[fila].remove(i)); //Se quitan las cartas que estan en el medio de las cartas del mismo tipo 
                    j--;
                }
            }
        }else {  //JUGAMOS POR EL LADO DERECHO, lado=1
            i = cardsTable[fila].size() - 1; //Empezamos desde el final 
            TypeBird tipoInicial = cardsTable[fila].get(i).getTypeBird(); //Tipo de carta del que partimos 

            //Mientras no llegues al final de la fila y el tipo incial de la carta sea el mismo en el que estamos
            while (i>=0 && tipoInicial.equals(cardsTable[fila].get(i).getTypeBird())){
                i--;   //Avanzamos 
            }//El tipo de carta ya no es el mismo (Encontramos el final del 1º bloque)

            //Buscamos otro bloque del mismo tipo pero mas a la izquierda
            j = i;

            //Mientras no llegemos al final de la lista (teniendo en cuenta el ultimo elemento) y el tipo de la carta actual sea distinto al de la inicial
            while (j != -1 && !tipoInicial.equals(cardsTable[fila].get(j).getTypeBird())){
                j--;   //Avanzamos 
            }

            //SI NO SE VOLVIO A ENCONTRAR UNA CARTA DEL MISMO TIPO 
            if(j == -1){
                return cartasRodeadas;   //no hay captura 
            }else{                      //SI SI SE ENCONTRO OTRA CARTA DEL MISMO TIPO 
                while(j + 1 <= i){
                    cartasRodeadas.addLast(cardsTable[fila].remove(j+1)); //Se quitan las cartas que estan en el medio de las cartas del mismo tipo 
                    i--;
                }
            }
        }

        return cartasRodeadas;
    }
    
    @Override
    public String toString() {
        System.out.println("Mesa: ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {                       //Bucle que controla las filas (tienen que ser 4)
            sb.append("Fila ").append(i ).append(": "); //Cada vez que empieza una fila, añade el texto "Fila 1: ", "Fila 2: ", etc.
            for (int j = 0; j < cardsTable[i].size(); j++) {                 //Bucle que controla las columnas 
                sb.append(cardsTable[i].get(j).toString());  //Muestra cada carta, de la manera que se indica en el toString de Cards (ya que cardsTable[][] , lo inicializamos en el constructor como tipo carta )
            }
            sb.append("\n");
        }
        return sb.toString();                         //Devuelve la variable temporal donde hemos guardado la representación de nuestra carta  
    }
}
