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
        if(hand.isEmpty()){
            hand.addFirst(new LinkedList<Card>());
            hand.get(0).addFirst(card);
        }else{
            int i = 0;
            boolean encontrado = false;
            while(i < hand.size() && !encontrado){
                if(hand.get(i).getFirst().getTypeBird().equals(card.getTypeBird())){
                    encontrado = true;
                }else{
                    i++;
                }
            }
            if(encontrado){
                hand.get(i).addFirst(card);
            }else{
                hand.addLast(new LinkedList<Card>());
                hand.get(i).addFirst(card);
            }
        }
    } 

    public boolean handIsEmpty(){
        return hand.isEmpty();
    }

    public int howManyList(){
        return hand.size();
    }

    public List<Card> removeCards(int row){
        return hand.remove(row);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(":\n");  //Se muestra el jugador 
        for (int i = 0; i < hand.size() - 1 ; i++) {
            sb.append(i + ". // ");
            for(Card c:hand.get(i)){   //Recorre todas las cartas que tiene en la mano ese jugador 
                sb.append(c).append(" // ");;   //Las muestra 
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}