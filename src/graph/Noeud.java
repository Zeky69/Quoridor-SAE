package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Noeud {

    private final int[] position;
    private final List<Noeud> aretes = new ArrayList<>();


    /**
     * Constructs a new node with the given position.
     *
     * @param position the position of the node
     */
    public Noeud(int[] position) {
        this.position = position;
    }

    /**
     * Adds an edge to another node.
     *
     * @param noeud the node to add as an edge
     */
    public void addArete(Noeud noeud) {
        this.aretes.add(noeud);
    }

    /**
     * Returns the edges of the node.
     *
     * @return a list of nodes that are connected to this node
     */
    public List<Noeud> getArete() {
        return this.aretes;
    }


    /**
     * Checks if the node has an edge to the specified node.
     *
     * @param position the node to check for an edge
     * @return true if the node has an edge to the specified node, false otherwise
     */
    public boolean hasArete(Noeud position) {
        for (Noeud arete : this.aretes) {
            if (arete.equals(position)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an edge between this node and the specified node.
     *
     * @param node1 The node to remove the edge with.
     */
    public void removeArete(Noeud node1) {
        if (node1 == null) {
            System.out.println("Au moins un des nœuds n'existe pas");
            return;
        }
        if (!node1.hasArete(this) || !this.hasArete(node1)) {
            System.out.println("Ces deux nœuds ne sont pas connectés");
            return;
        }

        this.aretes.remove(node1);
        node1.aretes.remove(this);

    }


    /**
     * Returns the position of this node as an array of integers.
     *
     * @return The position of this node.
     */
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
        for (Noeud noeud : this.aretes) {
            str.append(Arrays.toString(noeud.getPosition()));
            str.append(" ");
        }
        return str.toString();

    }
}
