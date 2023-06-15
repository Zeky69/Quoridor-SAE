package graph;

import model.Wall;

import java.util.*;

public class Graph {
    private final List<Noeud> noeuds = new ArrayList<>();

    /**
     *
     * Checks if a wall is at the border of the game board in the specified direction.
     * @param coord the coordinates of the wall
     * @param direction the direction to check
     * @return {@code true} if a wall is at the border in the specified direction, {@code false} otherwise
     */
    private boolean wallIsBorder(int[] coord, Wall.Direction direction) {
        if (direction == Wall.Direction.UP && coord[1] == 0) return false;
        else if (direction == Wall.Direction.DOWN && coord[1] == 8) {
            return false;
        } else if (direction == Wall.Direction.LEFT && coord[0] == 0) {
            return false;
        } else if (direction == Wall.Direction.RIGHT && coord[0] == 8) {
            return false;
        }
        return true;
    }

    /**
     *
     * Constructs a graph based on the given walls.
     * @param walls a 2D array of Wall objects representing the maze walls
     */
    public Graph(Wall[][] walls) {
        for (int i = 0; i < walls.length; i++) {
            for (int j = 0; j < walls[i].length; j++) {
                addNoeud(new int[]{j, i});
                int[] coord = new int[]{j, i};
                if (wallIsBorder(coord, Wall.Direction.UP) && !(walls[i][j].getWall(Wall.Direction.UP) || walls[i - 1][j].getWall(Wall.Direction.DOWN))) {
                    addArete(new int[]{j, i}, new int[]{j, i - 1});
                }
                if (wallIsBorder(coord, Wall.Direction.DOWN) && !(walls[i][j].getWall(Wall.Direction.DOWN) || walls[i + 1][j].getWall(Wall.Direction.UP))) {
                    addArete(new int[]{j, i}, new int[]{j, i + 1});

                }
                if (wallIsBorder(coord, Wall.Direction.LEFT) && !(walls[i][j].getWall(Wall.Direction.LEFT) || walls[i][j - 1].getWall(Wall.Direction.RIGHT))) {
                    addArete(new int[]{j, i}, new int[]{j - 1, i});

                }
                if (wallIsBorder(coord, Wall.Direction.RIGHT) && !(walls[i][j].getWall(Wall.Direction.RIGHT) || walls[i][j + 1].getWall(Wall.Direction.LEFT))) {
                    addArete(new int[]{j, i}, new int[]{j + 1, i});


                }


            }

        }
    }


    /**
     *
     * Removes an edge between two nodes.
     * @param pos1 The position of the first node.
     * @param pos2 The position of the second node.
     */
    public void removeArete(int[] pos1, int[] pos2) {
        Noeud node1 = getNoeud(pos1);
        Noeud node2 = getNoeud(pos2);

        if (node1 == null || node2 == null) {
            System.out.println("Au moins un des nœuds n'existe pas");
            return;
        }

        if (!node1.hasArete(node2) || !node2.hasArete(node1)) {

            System.out.println("Ces deux nœuds ne sont pas connectés");
            return;
        }

        node2.removeArete(node1);
    }


    /**
     * Remove aretes between two nodes
     * @param pos1 the first node
     * @param pos2 the second node
     * @param direction the direction of the wall
     */
    public void removeArete(int[] pos1, int[] pos2, Wall.Direction direction) {
        if (pos1 == null || pos2 == null || direction == null) {
            System.out.println("Une valeur est nulle");
            return;
        }
        int[] dirInt = Wall.directionToInt(direction);
        int[] pos1bis = new int[]{pos1[0] + dirInt[0], pos1[1] + dirInt[1]};
        int[] pos2bis = new int[]{pos2[0] + dirInt[0], pos2[1] + dirInt[1]};
        removeArete(pos1, pos1bis);
        removeArete(pos2, pos2bis);
    }

    /**
     * Add a arrete between two nodes
     * @param pos1 the first node
     * @param pos2 the second node
     * @param direction the direction of the wall
     */
    public void addArete(int[] pos1, int[] pos2, Wall.Direction direction) {
        if (pos1 == null || pos2 == null || direction == null) {
            System.out.println("Une valeur est nulle");
            return;
        }
        int[] dirInt = Wall.directionToInt(direction);
        int[] pos1bis = new int[]{pos1[0] + dirInt[0], pos1[1] + dirInt[1]};
        int[] pos2bis = new int[]{pos2[0] + dirInt[0], pos2[1] + dirInt[1]};
        addArete2(pos1, pos1bis);
        addArete2(pos2, pos2bis);

    }

    /**
     * Retrieves a node based on its position.
     * @param position The position of the node to retrieve.
     * @return The node at the specified position, or null if not found.
     */
    public Noeud getNoeud(int[] position) {
        int index = noeuds.indexOf(new Noeud(position));
        if (index != -1)
            return noeuds.get(index);
        return null;
    }

    /**
     * Calculates the shortest path from a given initial position to a target position.
     * @param positionInit an array representing the initial position coordinates
     * @param y the y-coordinate of the target position
     * @return the shortest path length as an integer
     */
    public int shortestPath(int[] positionInit, int y) {
        Map<Noeud, Integer> distances = new HashMap<>();
        Set<Noeud> nonVisites = new HashSet<>(this.noeuds);

        for (Noeud noeud : this.noeuds) {
            distances.put(noeud, Integer.MAX_VALUE);
        }

        Noeud start = getNoeud(positionInit);
        distances.put(start, 0);

        PriorityQueue<Noeud> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        queue.add(start);

        while (!queue.isEmpty()) {
            Noeud current = queue.poll();

            if (!nonVisites.contains(current)) {
                continue;
            }

            for (Noeud voisin : current.getArete()) {
                int distance = distances.get(current) + 1;
                if (distance < distances.get(voisin)) {
                    distances.put(voisin, distance);
                    queue.remove(voisin);
                    queue.add(voisin);
                }
            }

            nonVisites.remove(current);
        }

        int minDistance = Integer.MAX_VALUE;
        for (Noeud noeud : distances.keySet()) {
            if (noeud.getPosition()[1] == y && distances.get(noeud) < minDistance) {
                minDistance = distances.get(noeud);
            }
        }

        return minDistance;
    }
    /**
     * Checks if a path is possible to reach a specific y-coordinate.
     * @param positionInit the initial position
     * @param y the target y-coordinate
     * @return {@code true} if a path is possible, {@code false} otherwise
     */
    public boolean isPathPossibleY(int[] positionInit, int y) {
        Noeud noeudInit = getNoeud(positionInit);

        if (noeudInit == null) {
            return false;
        }

        Queue<Noeud> queue = new LinkedList<>();
        Set<Noeud> visited = new HashSet<>();
        queue.offer(noeudInit);
        visited.add(noeudInit);

        while (!queue.isEmpty()) {
            Noeud current = queue.poll();
            if (current.getPosition()[1] == y) {
                return true;
            }
            for (Noeud neighbor : current.getArete()) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return false;
    }

    /**
     * Adds a node to the collection of nodes.
     * @param position The position of the node as an array of integers.
     *
     */
    public void addNoeud(int[] position) {
        if (!noeuds.contains(new Noeud(position))) {
            noeuds.add(new Noeud(position));

        }

    }

    /**
     *
     * Adds an edge between two nodes.
     * @param positionNoeud The position of the node to connect.
     * @param positionArete The position of the edge to connect.
     */
    public void addArete2(int[] positionNoeud, int[] positionArete) {
        int posNoeud = noeuds.indexOf(new Noeud(positionNoeud));

        Noeud noeud = null;
        Noeud arete = null;
        if (posNoeud == -1) {
            return;
        }
        int posArete = noeuds.indexOf(new Noeud(positionArete));
        if (posArete == -1) {
            return;
        }
        noeud = noeuds.get(posNoeud);
        arete = noeuds.get(posArete);
        noeud.addArete(arete);
        arete.addArete(noeud);

    }

    /**
     *
     *  Adds an edge between two nodes identified by their positions.
     * @param positionNoeud The position of the source node.
     * @param positionArete The position of the target node.
     */
    public void addArete(int[] positionNoeud, int[] positionArete) {
        int posNoeud = findNoeudIndex(positionNoeud);
        Noeud noeud = null;
        Noeud arete = null;

        if (posNoeud == -1) {
            noeud = new Noeud(positionNoeud);
            noeuds.add(noeud);
        } else {
            noeud = noeuds.get(posNoeud);
        }

        int posArete = findNoeudIndex(positionArete);
        if (posArete == -1) {
            arete = new Noeud(positionArete);
            noeuds.add(arete);
        } else {
            arete = noeuds.get(posArete);
        }

        noeud.addArete(arete);
    }


    /**
     * Finds the index of a node with a given position in the list of nodes.
     * @param position The position array to compare with the nodes' positions.
     * @return The index of the found node, or -1 if no node with the given position is found.
     */
    private int findNoeudIndex(int[] position) {
        for (int i = 0; i < noeuds.size(); i++) {
            Noeud noeud = noeuds.get(i);
            if (Arrays.equals(noeud.getPosition(), position)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Noeud noeud : noeuds) {
            str.append(noeud.toString() + "\n");
        }
        return str.toString();
    }


}
