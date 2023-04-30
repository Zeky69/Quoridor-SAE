package graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Noeud {

    private int[] position;
    private List<Noeud> aretes =  new ArrayList<>();

    public Noeud(int[] position ){
        this.position = position;
    }

    public void addArete(Noeud noeud){
        this.aretes.add(noeud);
    }

    public void removeArrete(Noeud noeud){
        this.aretes.remove(noeud);
    }

    public List<Noeud> getArete(){
        return this.aretes;
    }


    public void setPosition(int[] position){
        this.position = position;
    }

    public boolean hasArete(int[] position) {
        Noeud voisin = new Noeud(position);
        for (Noeud arete : this.aretes) {
            if (arete.equals(voisin)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasArete(Noeud position) {
        for (Noeud arete : this.aretes) {
            if (arete.equals(position)) {
                return true;
            }
        }
        return false;
    }


    public void removeAretes(int[] pos1) {

        if (pos1 == null) {
            System.out.println("Au moins un des nœuds n'existe pas");
            return;
        }
        Noeud node1 = new Noeud(pos1);
        if (!node1.hasArete(this) || !this.hasArete(node1)) {
            System.out.println("Ces deux nœuds ne sont pas connectés");
            return;
        }
        this.aretes.remove(node1);
        node1.aretes.remove(this);

    }

    public void removeArete(Noeud node1){
        if (node1 == null) {
            System.out.println("Au moins un des nœuds n'existe pas");
            return;
        }
        if (!node1.hasArete(this) || !this.hasArete(node1)) {
            System.out.println("Ces deux nœuds ne sont pas connectés" + this + " " + node1);
            return;
        }

        this.aretes.remove(node1);
        node1.aretes.remove(this);

    }




    public int[] getPosition() {
        return this.position;
    }
    @Override
    public boolean equals(Object noeud) {
        if (noeud == null) {
            return false;
        }
        if (noeud == this) {
            return true;
        }
        if (!(noeud instanceof Noeud)) {
            return false;
        }



        return Arrays.equals(this.position, ((Noeud) noeud).getPosition());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.position);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Noeud : ");
        str.append(Arrays.toString(this.position));
        str.append(" ");
        str.append("Arete : ");
        for(Noeud noeud : this.aretes){
            str.append(Arrays.toString(noeud.getPosition()));
            str.append(" ");
        }
        return str.toString();

    }
}
