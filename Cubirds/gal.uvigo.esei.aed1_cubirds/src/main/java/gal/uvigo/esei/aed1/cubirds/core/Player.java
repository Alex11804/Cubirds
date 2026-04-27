package gal.uvigo.esei.aed1.cubirds.core;

import es.uvigo.esei.aed1.tads.list.*;


public class Player {
private String name;
private List<List <Card>> hand;


    public Player(String name) {
        this.name = name;
        this.hand = new LinkedList<>();
    }

    /**
     * getName: Devuelve el nombre del jugador 
     */

    public String getName() {
        return name;
    }



    /**
     * addCard: Inserta una carta en la mano (en la lista correspondiente) manteniendo un orden específico 
     */

    public void addCard(Card card){
        if(hand.isEmpty()){                                            //Si aun no hay cartas
            hand.addFirst(new LinkedList<Card>());                    //Se crea una nueva lista 
            hand.get(0).addFirst(card);                              //Se añade la 1º carta en esa lista 
        }else{
            int i = 0;
             
        //Se recorre hand , se compara el tipo de ave de la nueva carta con el tipo de ave de la primera carta de cada sublista 
            while(i < hand.size() && !hand.get(i).getFirst().getTypeBird().equals(card.getTypeBird())){
                    i++;   
            }

            if(i < hand.size()){                      //Si ENCUENTRA un grupo del mismo tipo
                hand.get(i).addFirst(card);          //Añade la carta de ese tipo al principio  (La carta se agrupa con las de su mismo tipo)
            }else{                                  //Si NO ENCUENTRA ninguna sublista de ese tipo
                hand.addLast(new LinkedList<Card>());  //Se crea una nueva sublista al final 
                hand.get(i).addFirst(card);           //Se añade en esa sublista el nuevo tipo
            }
        }
    } 

    public boolean handIsEmpty(){
        return hand.isEmpty();
    }

    public int howManyList(){
        return hand.size() ;            //!! quite el menos dos para poder elegir todas las listas
    }

    public List<Card> removeCards(int row){
        return hand.remove(row);
    }

    public void addCapturedCards(List <Card> capturedCards ){
        for (Card carta: capturedCards){
            addCard(carta);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(":\n");  //Se muestra el jugador 
        for (int i = 0; i < hand.size() ; i++) {     //recorremos TODA la mano (incluida nuevas filas) (hand.size())
            sb.append(i + ". // ");
            for(Card c:hand.get(i)){   //Recorre todas las cartas que tiene en la mano ese jugador 
                sb.append(c).append(" // ");;   //Las muestra 
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}