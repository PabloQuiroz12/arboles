package com.bo.upb.algoritmica1.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class TreeNario<T> {

    private NodeN<T> root;
    @Getter
    @Setter
    private int degree;

    private List<TreeNario<T>> child;

    public List<TreeNario<T>> getChild() {
        return child;
    }

    public void setChild(List<TreeNario<T>> child) {
        this.child = child;
    }

    public void setRoot(NodeN<T> root) {
        this.root = root;
    }

    public void setRootValue(T value) {
        if (root == null) {
            root = new NodeN<>(value);
        } else {
            root.setValue(value);
        }
    }

    public NodeN<T> getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }

    private int size; // default 0

    public TreeNario() {
        root = null;
        size = 0;
    }

    public TreeNario(Integer valueRoot) {
        this.root = new NodeN(valueRoot);
        size = 1;
        degree = 0;
    }

    public TreeNario(NodeN root) {
        this.root = root;
        size = 1;
    }

    /**
     * Método hace que el root apunte a un nuevo nodo con valor value
     *
     * @param value: valor del nodo root
     */
    public void putRoot(T value) {
        root = new NodeN<>(value);
        size = 1;
    }

    /**
     * Método que inserta un nuevo nodo con el valor value, a la izquierda
     * del nodo con valor valueParent
     *
     * @param valueParent: valor de algun nodo que existe en el arbol
     * @param value:       valor del nuevo nodo que se creará
     */
    public void put(T valueParent, T value) {
        NodeN nodeParent = getNode(valueParent, root);
        if (nodeParent == null)
            return;

        nodeParent.addChild(new NodeN(value));
        size++;
    }


    /**
     * Función recursiva para obtener el nodo de un valor a buscar
     *
     * @param valueToSearch: valor a buscar de algun nodo
     * @param node:          Nodo actual que se analiza y que posteriormente se hara las llamadas recursivas enviando a sus hijos left y right
     * @return Retorna el nodo que contiene el valor valueToSearch
     */
    public NodeN getNode(T valueToSearch, NodeN<T> node) {
        if (node == null)
            return null;

        if (node.getValue() == valueToSearch)
            return node;

        for (NodeN child : node.getChildren()) {
            NodeN aux = getNode(valueToSearch, child);
            if (aux != null)
                return aux;
        }
        return null;
    }

    /**
     * obtiene la altura de un arbol
     *
     * @return
     */
    public int depth() {
        return depth(root);
    }

    private int depth(NodeN<T> node) { // Función Máscara | Function Mask
        if (node == null) {
            return 0;
        } else if (node.isLeaf()) {
            return 1;
        }

        int max = Integer.MIN_VALUE;
        for (NodeN child : node.getChildren()) {
            int aux = depth(child);
            max = Math.max(max, aux);
        }
        return max + 1;
    }

    public void print() {
        System.out.println("size: " + size);
        print(root);
    }

    /**
     * Imprime los elementos del arbol
     *
     * @param node: Nodo actual que se analiza y que posteriormente se hara las llamadas recursivas enviando a sus hijos left y right
     */
    private void print(NodeN<T> node) { // Método Máscara | Void Mask
        if (node == null) // caso base
            return;

        System.out.println("Node: " + node.getValue());

        for (NodeN child : node.getChildren()) {
            print(child);
        }
    }

    public void recorridoPorNiveles2() { // sin usar cola
        if (root == null) {
            System.out.println("Arbol vacio");
            return;
        }

        List<NodeN> Nodes = new ArrayList<>();
        Nodes.add(root);

        String nodesStr;
        int levelFinal = depth(); // nivel final
        int level = 1;
        List<NodeN> children;
        while (level <= levelFinal) {
            nodesStr = "";
            children = new ArrayList<>();
            for (NodeN node : Nodes) {
                if (node != null) {
                    nodesStr = nodesStr + node.getValue() + " "; // concatenamos los nodos de un nivel
                    children.addAll(node.getChildren()); // obtenemos los hijos de cada nodo de un nivel analizado
                }
            }
            System.out.println("level " + level + ": " + nodesStr);
            level++;
            Nodes = children; // los siguientes nodos a revisar seran los hijos que se han obtenido
        }
    }

    public void clear() {
        root = null;
    }

    public void asignarPosiciones(int widthNode, int horizontalSpacing, int verticalSpacing) {
        if (root != null) {
            asignarPosiciones(root, 0, 0, widthNode, horizontalSpacing, verticalSpacing);
        }
    }

    private void asignarPosiciones(NodeN<T> node, int x, int y, int widthNode, int horizontalSpacing, int verticalSpacing) {
        if (node == null)
            return;
        // Establecer la posición del nodo actual
        node.setPosX(x);
        node.setPosY(y);

        int childX = x - (node.getChildren().size() - 1) * widthNode / 2; // Calcula la posición X del primer hijo

        // Llama al método de manera recursiva para los hijos del nodo
        for (NodeN<T> child : node.getChildren()) {
            asignarPosiciones(child, childX, y + verticalSpacing, widthNode, horizontalSpacing, verticalSpacing);
            childX += widthNode + horizontalSpacing; // Avanza la posición X para el siguiente hijo
        }
    }

    public NodeN<T> search(T value) {
        return search(root, value);
    }

    private NodeN<T> search(NodeN<T> node, T value) {
        if (node == null) {
            return null;
        }

        if (node.getValue().equals(value)) {
            return node;
        }

        for (NodeN<T> child : node.getChildren()) {
            NodeN<T> result = search(child, value);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    public boolean isDescendant(NodeN<T> parent, NodeN<T> child) {
        if (parent == null || child == null) {
            return false;
        }
        return isDescendantRecursive(parent, child);
    }

    private boolean isDescendantRecursive(NodeN<T> parent, NodeN<T> current) {
        if (current == null) {
            return false;
        }

        if (current == parent) {
            return true;
        }

        for (NodeN<T> child : current.getChildren()) {
            if (isDescendantRecursive(parent, child)) {
                return true;
            }
        }

        return false;
    }

    // Método para eliminar un nodo del árbol
    public void removeChild(NodeN child) {
        if (root == child) {
            root = null; // Elimina la raíz
        } else {
            removeChildRecursive(root, child);
        }
    }

    private void removeChildRecursive(NodeN<T> current, NodeN child) {
        if (current == null) {
            return;
        }

        current.getChildren().remove(child); // Elimina el nodo child de los hijos de current

        for (NodeN c : current.getChildren()) {
            removeChildRecursive(c, child);
        }
    }

    public static void main(String[] args) {
        TreeNario<Integer> tb;

        // Forma 2 de poblar el árbol (correcto) ----------------

        tb = new TreeNario();
        tb.putRoot(10);
        tb.put(10, 20);
        tb.put(10, 30);
        tb.put(10, 400);
        tb.put(20, 15);
        tb.put(30, 25);
        //tb.putRight(30, 35);
        //tb.putRight(35, 38);
        //tb.putLeft(20, 12);

        //System.out.println(tb.esArbolCompleto());
        System.out.println(tb.depth());
        System.out.println();
        tb.recorridoPorNiveles2();
        System.out.println();
        tb.print();
    }

}

