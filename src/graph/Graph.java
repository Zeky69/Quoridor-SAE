package graph;
import model.Wall;
import java.util.*;
public class Graph {
    private List<Noeud> noeuds = new ArrayList<>();

    public Graph(){

    }

    private boolean isBorder(int[] coord, Wall.Direction direction){
        if (direction == Wall.Direction.UP && coord[1]==0) return true;
        else if (direction == Wall.Direction.DOWN && coord[1]==8){
            return true;
        }else if (direction == Wall.Direction.LEFT && coord[0]==0){
            return true;
        }else if (direction == Wall.Direction.RIGHT && coord[0]==8){
            return true;
        }
        return false;
    }

    public Graph(Wall[][] walls){
        for (int i = 0; i < walls.length; i++) {
            for (int j = 0; j < walls[i].length; j++) {
                addNoeud(new int[]{j,i});
                if( !isBorder(new int[]{j,i}, Wall.Direction.UP) && !(walls[i][j].getWall(Wall.Direction.UP) || walls[i-1][j].getWall(Wall.Direction.DOWN))){
                    addArete(new int[]{j,i} ,new int[]{j,i-1});
                }
                if(!isBorder(new int[]{j,i}, Wall.Direction.DOWN) && !( walls[i][j].getWall(Wall.Direction.DOWN) || walls[i+1][j].getWall(Wall.Direction.UP))){
                    addArete(new int[]{j,i} ,new int[]{j,i+1});

                }
                if(!isBorder(new int[]{j,i}, Wall.Direction.LEFT) && !(walls[i][j].getWall(Wall.Direction.LEFT ) || walls[i][j-1].getWall(Wall.Direction.RIGHT))){
                    addArete(new int[]{j,i} ,new int[]{j-1,i});

                }
                if(!isBorder(new int[]{j,i}, Wall.Direction.RIGHT) && !(walls[i][j].getWall(Wall.Direction.RIGHT) || walls[i][j+1].getWall(Wall.Direction.LEFT))){
                    addArete(new int[]{j,i} ,new int[]{j+1,i});


                }


            }

        }
    }



    public boolean hasArete(int[] position1, int[] position2) {
        Noeud noeud1 = getNoeud(position1);
        Noeud noeud2 = getNoeud(position2);

        if (noeud1 == null || noeud2 == null) {
            return false; // l'un des noeuds n'existe pas
        }

        for (Noeud voisin : noeud1.getArete()) {
            if (voisin.equals(noeud2)) {
                return true; // on a trouvé une arête vers le noeud spécifié
            }
        }

        return false; // on n'a pas trouvé d'arête vers le noeud spécifié
    }



    public void removeArete(int[] pos1, int[] pos2) {
        Noeud node1 = getNoeud(pos1);
        Noeud node2 = getNoeud(pos2);

        if (node1 == null || node2 == null) {
            System.out.println("Au moins un des nœuds n'existe pas");
            return;
        }

        if (!node1.hasArete(node2) || !node2.hasArete(node1)) {
            System.out.println(node1 + " " + node2);

            System.out.println("Ces deux nœuds ne sont pas connectés");
            return;
        }

        node2.removeArete(node1);

    }

    public void removeArete(int[] pos1, int[] pos2, Wall.Direction direction) {
        if (pos1 == null || pos2 == null || direction == null) {
            System.out.println("Une valeur est nulle");
           return;
        }
        int[] dirInt = Wall.directionToInt(direction);
        int[] pos1bis = new int[]{pos1[0]+dirInt[0], pos1[1]+dirInt[1]};
        int[] pos2bis = new int[]{pos2[0]+dirInt[0], pos2[1]+dirInt[1]};
        removeArete(pos1, pos1bis);
        removeArete(pos2, pos2bis);
    }

    public void addArete(int[] pos1, int[] pos2, Wall.Direction direction) {
        if (pos1 == null || pos2 == null || direction == null) {
            System.out.println("Une valeur est nulle");
            return;
        }
        int[] dirInt = Wall.directionToInt(direction);
        int[] pos1bis = new int[]{pos1[0]+dirInt[0], pos1[1]+dirInt[1]};
        int[] pos2bis = new int[]{pos2[0]+dirInt[0], pos2[1]+dirInt[1]};
        addArete2(pos1, pos1bis);
        addArete2(pos2, pos2bis);

    }





    public Noeud getNoeud(int[] position){
        int index = noeuds.indexOf(new Noeud(position));
        if(index != -1)
            return noeuds.get(index);
        return null;
    }



    public int shortestPath(int[] positionInit, int[] positionFinal) {
        Map<Noeud, Integer> distances = new HashMap<>();
        for (Noeud noeud : this.noeuds) {
            distances.put(noeud, Integer.MAX_VALUE);
        }
        List<Noeud> nonVisites = new ArrayList<>(this.noeuds);
        distances.put(getNoeud(positionInit), 0);

        while (!nonVisites.isEmpty()) {
            Noeud current = null;
            int minDistance = Integer.MAX_VALUE;
            for (Noeud noeud : nonVisites) {
                if (distances.get(noeud) < minDistance) {
                    current = noeud;
                    minDistance = distances.get(noeud);
                }
            }
            if (current == null) {
                break;
            }
            for (Noeud voisin : current.getArete()) {
                int distance = distances.get(current) + 1;
                if (distance < distances.get(voisin)) {
                    distances.put(voisin, distance);
                }
            }
            nonVisites.remove(current);
        }
        return distances.get(this.getNoeud(positionFinal));
    }
    public int shortestPath(int[] positionInit, int y) {
        Map<Noeud, Integer> distances = new HashMap<>();
        for (Noeud noeud : this.noeuds) {
            distances.put(noeud, Integer.MAX_VALUE);
        }
        List<Noeud> nonVisites = new ArrayList<>(this.noeuds);
        distances.put(getNoeud(positionInit), 0);

        while (!nonVisites.isEmpty()) {
            Noeud current = null;
            int minDistance = Integer.MAX_VALUE;
            for (Noeud noeud : nonVisites) {
                if (distances.get(noeud) < minDistance) {
                    current = noeud;
                    minDistance = distances.get(noeud);
                }
            }
            if (current == null) {
                break;
            }
            for (Noeud voisin : current.getArete()) {
                int distance = distances.get(current) + 1;
                if (distance < distances.get(voisin)) {
                    distances.put(voisin, distance);
                }
            }
            nonVisites.remove(current);
        }
        int minDistance = Integer.MAX_VALUE;
        for ( Noeud noeud : distances.keySet()) {
            if (noeud.getPosition()[1] == y) {
                if (distances.get(noeud) < minDistance) {
                    minDistance = distances.get(noeud);
                }
            }
        }
        return minDistance;
    }







    public boolean isPathPossible(int[] positionInit, int[] positionFinal) {
        Noeud noeudInit = getNoeud(positionInit);
        Noeud noeudFinal = getNoeud(positionFinal);

        if (noeudInit == null || noeudFinal == null) {
            return false; // si l'un des noeuds n'existe pas, il n'y a pas de chemin possible
        }

        Queue<Noeud> queue = new LinkedList<>();
        Set<Noeud> visited = new HashSet<>();
        queue.offer(noeudInit);
        visited.add(noeudInit);

        while (!queue.isEmpty()) {
            Noeud current = queue.poll();
            if (current.equals(noeudFinal)) {
                return true; // si on a atteint le noeud final, on a trouvé un chemin possible
            }
            for (Noeud neighbor : current.getArete()) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return false; // on a parcouru tout le graphe sans atteindre le noeud final, donc pas de chemin possible
    }

    public boolean isPathPossibleY(int[] positionInit, int y) {
        Noeud noeudInit = getNoeud(positionInit);

        if (noeudInit == null) {
            return false; // si l'un des noeuds n'existe pas, il n'y a pas de chemin possible
        }

        Queue<Noeud> queue = new LinkedList<>();
        Set<Noeud> visited = new HashSet<>();
        queue.offer(noeudInit);
        visited.add(noeudInit);

        while (!queue.isEmpty()) {
            Noeud current = queue.poll();
            if (current.getPosition()[1]==y) {
                return true; // si on a atteint le noeud final, on a trouvé un chemin possible
            }
            for (Noeud neighbor : current.getArete()) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return false; // on a parcouru tout le graphe sans atteindre le noeud final, donc pas de chemin possible
    }





    public void addNoeud(int[] position){
        if(!noeuds.contains(new Noeud(position))){
            noeuds.add(new Noeud(position ));

        }

    }


    public void addArete2(int[] positionNoeud , int[] positionArete){
        int posNoeud = noeuds.indexOf(new Noeud(positionNoeud ));

        Noeud noeud = null;
        Noeud arete = null;
        if(posNoeud == -1){
            return;
        }
        int posArete = noeuds.indexOf(new Noeud(positionArete ));
        if(posArete == -1){
            return;
        }
        noeud = noeuds.get(posNoeud);
        arete = noeuds.get(posArete);
        noeud.addArete(arete);
        arete.addArete(noeud);

    }


    public void addArete(int[] positionNoeud , int[] positionArete){
        int posNoeud = noeuds.indexOf(new Noeud(positionNoeud ));
        Noeud noeud = null;
        Noeud arete = null;

        if(posNoeud == -1){
            noeud = new Noeud(positionNoeud );
            noeuds.add(noeud);
        }else{
            noeud = noeuds.get(posNoeud);
        }

        int posArete = noeuds.indexOf(new Noeud(positionArete ));
        if(posArete == -1){
            arete = new Noeud(positionArete);
            noeuds.add(arete);
        }else{
            arete = noeuds.get(posArete);
        }
        noeud.addArete(arete);







    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for(Noeud noeud : noeuds){
            str.append(noeud.toString()+"\n");
        }
        return str.toString();
    }



   }
