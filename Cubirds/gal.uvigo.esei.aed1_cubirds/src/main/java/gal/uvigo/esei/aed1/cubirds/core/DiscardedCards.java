package gal.uvigo.esei.aed1.cubirds.core;
import es.uvigo.esei.aed1.tads.list.*; 

public class DiscardedCards {
    private List<Card> discardedCards;

    public DiscardedCards(){
        this.discardedCards = new LinkedList<>();
    }

    public void addCardToEnd(List<Card> cards){   //ES mejor añadir listas
        for(Card card : cards){
            discardedCards.addLast(card);
        }
    }

    public boolean isEmpty (){
        return discardedCards.size()==0; 
    }

    public List<Card> removeCards (){  //Solo se elimina toda la lista
        return discardedCards; 
    }

    

}
