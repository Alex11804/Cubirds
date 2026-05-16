package gal.uvigo.esei.aed1.cubirds.core;

import java.util.Random;

import es.uvigo.esei.aed1.tads.list.*; 

public class DeckOfCards {
    private List<Card> cards;

    /**
     * Constructor: crea una baraja de cartas ordenada a partir del enumerado
     * Card.values(): Java hace una lista temporal con todas las cartas que hay definidos en el enum. 
     */
    public DeckOfCards() {
        this.cards = new LinkedList<>();  //Lista de cartas, inicialmente vacío
        for (Card c : Card.values()){    //Reparte las cartas y las añade a la Lista de cartas 
            cards.addFirst(c);
        }
    }

    public boolean isEmpty (){
        return cards.size()==0; 
    }

    /**
     * shuffle: Barajar las cartas 
     */

    public void shuffle(){
        Random random = new Random();            //Creamos un objeto random para generar numeros aleatorios 
        for(int i = 0 ; i <= 109 ; i++){        //Recorremos las 110 cartas que tenemos 
            int numero = random.nextInt(109);  // Generamos el numero alatorio-> que usamos como indice (posicion de 1 carta)
            Card temp = cards.get(i);         //Guardamos la carta actual en una variable temporal
            cards.set(i, cards.get(numero)); //Intercambias la carta en la posicion i con la carta en la posicion aleatoria numero
            cards.set(numero, temp);        //Pones en numero la carta original de i (guardada en temp)
        }
    }

    /**
     * takeCard: Elimina la primera carta de la lista, para que ya no estea en la baraja 
     */

    public Card takeCard(){
        if(cards.isEmpty()){    //Si está vacía -> no hace nada
           return null;           
        }
        Card card = cards.remove(0);           //Si no está vacía entonces la elimina
        return card;                          //Desvuelve la carta que ha sido eliminada
    }

    /**
     * addCardToEnd:Añade una carta al final de la lista 
     */

    public void addCardToEnd(Card card){  
        cards.addLast(card);;
    }

    public void addListCards(List<Card> cards){  
        for(Card card : cards){
            cards.addLast(card);
        }
        
    }
}