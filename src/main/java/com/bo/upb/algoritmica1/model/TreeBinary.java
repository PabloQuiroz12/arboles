package com.bo.upb.algoritmica1.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * TreeBinary
 *
 * @author Marcos Quispe
 * @since 1.0
 */
public class TreeBinary {

    @Getter
    @Setter
    private Node root;

    @Getter
    private int size; // default 0

    public TreeBinary() {
        root = null;
        size = 0;
    }

    public TreeBinary(Node root) {
        this.root = root;
        size = 1;
    }

    /**
     * Método hace que el root apunte a un nuevo nodo con valor value
     *
     * @param value: valor del nodo root
     */
    public void putRoot(int value) {
        root = new Node(value);
        size = 1;
    }

    /**
     * Método que inserta un nuevo nodo con el valor value, a la izquierda
     * del nodo con valor valueParent
     *
     * @param valueParent: valor de algun nodo que existe en el arbol
     * @param value:       valor del nuevo nodo que se creará
     */
    public void putLeft(int valueParent, int value) {
        Node nodeParent = getNode(valueParent, root);
        if (nodeParent == null || nodeParent.getLeft() != null)
            return;

        nodeParent.setLeft(new Node(value));
        size++;
    }

    /**
     * Método que inserta un nuevo nodo con el valor value, a la derecha
     * del nodo con valor valueParent
     *
     * @param valueParent: valor de algun nodo que existe en el arbol
     * @param value:       valor del nuevo nodo que se creará
     */
    public void putRight(int valueParent, int value) {
        Node nodeParent = getNode(valueParent, root);
        if (nodeParent == null || nodeParent.getRight() != null)
            return;

        nodeParent.setRight(new Node(value));
        size++;
    }

    /**
     * Función recursiva para obtener el nodo de un valor a buscar
     *
     * @param valueToSearch: valor a buscar de algun nodo
     * @param node:          Nodo actual que se analiza y que posteriormente se hara las llamadas recursivas enviando a sus hijos left y right
     * @return Retorna el nodo que contiene el valor valueToSearch
     */
    public Node getNode(int valueToSearch, Node node) {
        if (node == null)
            return null;

        if (node.getValue() == valueToSearch)
            return node;

        Node izq = getNode(valueToSearch, node.getLeft());
        if (izq != null) {
            return izq;
        } else {
            Node der = getNode(valueToSearch, node.getRight());
            return der;
        }
    }

    public boolean sonHermanos(int valueNodo1, int valueNodo2) {
        return sonHermanos(root, valueNodo1, valueNodo2);
    }

    private boolean sonHermanos(Node node, int valueNodo1, int valueNodo2) { // Función Máscara | Function Mask
        if (node == null || node.isLeaf())
            return false;

        if (node.hasTwoSon() && node.areValuesChildren(valueNodo1, valueNodo2))
            return true;

        return sonHermanos(node.getLeft(), valueNodo1, valueNodo2)
                || sonHermanos(node.getRight(), valueNodo1, valueNodo2);
    }

    /**
     * obtiene la altura de un arbol
     *
     * @return
     */
    public int depth() {
        return depth(root);
    }

    private int depth(Node node) { // Función Máscara | Function Mask
        if (node == null) {
            return 0;
        } else if (node.isLeaf()) {
            return 1;
        }
        int right = depth(node.getRight());
        int left = depth(node.getLeft());
        //return right > left ? right + 1 : left + 1;
        if (right > left) {
            return 1 + right;
        } else {
            return 1 + left;
        }
    }

    public boolean esArbolCompleto() {
        int depth = depth();
        int cantNodosEsperados = (int) Math.pow(2, depth) - 1;
        return size == cantNodosEsperados;
    }

    public void insertarHeap(String values) {
        // implement
    }

    /**
     * Metodo que va insertando los elementos de forma continua hasta llenar el nivel, para luego continuar con el siguiente nivel
     * Reubica el elemento insertado para que el arbol sea un árbol max-heap
     *
     * @param value: valor a insertar
     */
    public void insertarHeap(int value) {
        if (root == null) {
            System.out.println("nivelAInsertar: " + 1 + ", posInit: " + 1 + ", value: " + value);
            putRoot(value);
            return;
        }

        int nivelFinal, posInit;
        nivelFinal = depth();
        if (esArbolCompleto())
            nivelFinal++;
        posInit = (int) Math.pow(2, nivelFinal - 1);
        System.out.println("nivelAInsertar: " + nivelFinal + ", posInit: " + posInit + ", value: " + value);

        insertarHeap(root, value, 1, nivelFinal, null, new boolean[]{false});
    }

    private Node insertarHeap(Node node, int value, int nivelActual, int nivelFinal, Node nodeParent, boolean[] array) { // Metodo Máscara
        if (node == null) {
            if (nivelActual == nivelFinal) {
                if (!array[0]) {
                    Node nodeNew = new Node(value);
                    if (nodeParent.getLeft() == null) {
                        nodeParent.setLeft(nodeNew);
                    } else {
                        nodeParent.setRight(nodeNew);
                    }
                    size++;
                    array[0] = true;
                    return nodeNew;
                } else {
                    //System.out.println("null posArray: " + array[0]);
                }
            }
            return null;
        }

        if (node.isLeaf()) {
            if (nivelActual == nivelFinal) {
                //System.out.println(node.getValue() + " posArray: " + array[0]);
                return null;
            }
        }

        nivelActual++;
        Node childLeft = insertarHeap(node.getLeft(), value, nivelActual, nivelFinal, node, array);
        if (childLeft != null) {
            if (childLeft.getValue() > node.getValue()) {
                swap(node, childLeft);
                return node;
            }
        }
        Node childRight = insertarHeap(node.getRight(), value, nivelActual, nivelFinal, node, array);
        if (childRight != null) {
            if (childRight.getValue() > node.getValue()) {
                swap(node, childRight);
                return node;
            }
        }

        return null;
    }

    public void swap(Node node1, Node node2) {
        int aux = node1.getValue();
        node1.setValue(node2.getValue());
        node2.setValue(aux);
    }

    public void reordenarHeap() {
        reorderHeap(root);
    }

    private void reorderHeap(Node node) { //mascara
        if (node == null || node.isLeaf()) {
            return;
        }
        int nodeValue = node.getValue();
        int leftValue = node.getLeft() == null ? Integer.MIN_VALUE : node.getLeft().getValue();
        int rightValue = node.getRight() == null ? Integer.MIN_VALUE : node.getRight().getValue();
        if (nodeValue < leftValue || nodeValue < rightValue) {
            if (leftValue > rightValue) {
                swap(node, node.getLeft());
                reorderHeap(node.getLeft());
            } else {
                swap(node, node.getRight());
                reorderHeap(node.getRight());
            }
        }
    }

    // TODO Tarea1: completar el metodo deleteHeap para reordenar el elemento raiz y que siga siendo un max-heap
    public void deleteHeap() {
        int nivelFinal = depth();
        deleteLastHeap(root, 1, nivelFinal, null, new boolean[]{false});
        // falta reordenar elemento raiz para que siga siendo un max-heap
        reordenarHeap();
    }

    private void deleteLastHeap(Node node, int nivelActual, int nivelFinal, Node nodeParent, boolean[] array) {
        if (node == null)
            return;
//        if (node==root && nodeParent==null){
//            node=null;
//        }

        if (node.isLeaf()) {
            if (nivelActual == nivelFinal) {
                System.out.println(node.getValue() + " posArray: " + array[0]);
                if (!array[0]) {
                    swap(node, root);
                    if (nodeParent.getRight() != null) {
                        nodeParent.setRight(null);
                    } else {
                        nodeParent.setLeft(null);
                    }
                    size--;
                    array[0] = true;
                }
                return;
            }
        }

        nivelActual++;
        deleteLastHeap(node.getRight(), nivelActual, nivelFinal, node, array);
        deleteLastHeap(node.getLeft(), nivelActual, nivelFinal, node, array);
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
    private void print(Node node) { // Método Máscara | Void Mask
        if (node == null) // caso base
            return;

        System.out.println("node: " + node.getValue());

        print(node.getLeft());
        print(node.getRight());
    }

    public void recorridoPorNiveles2() {
        if (root == null) {
            System.out.println("Arbol vacio");
            return;
        }

        List<Node> nodes = new ArrayList<>();
        nodes.add(root);

        String nodesStr;
        int levelFinal = depth(); // nivel final
        int level = 1;
        List<Node> children;
        while (level <= levelFinal) {
            nodesStr = "";
            children = new ArrayList<>();
            for (Node node : nodes) {
                nodesStr = nodesStr + node.getValue() + " "; // concatenamos los nodos de un nivel
                children.addAll(node.getChildren()); // obtenemos los hijos de cada nodo de un nivel analizado
            }
            System.out.println("level " + level + ": " + nodesStr);
            level++;
            nodes = children; // los siguientes nodos a revisar seran los hijos que se han obtenido
        }
    }

    public void asignarPosNodos(int width){
        if (root==null){
            return;
        }
        asignarPosNodos(width, root);
    }
    private void asignarPosNodos(int width, Node node) {
        if (root == null)
            return;
        int mitad = width / 2;
        int height = 100;

        if (node == root) {
            node.setPosX(mitad);
            node.setPosY(0);
        }

        if (node.getLeft() != null) {
            if (node.getPosX() > root.getPosX())
                node.getLeft().setPosX(2*mitad + mitad / 2);
            else
                node.getLeft().setPosX(mitad / 2);
            node.getLeft().setPosY(node.getPosY() + height);
            asignarPosNodos(mitad, node.getLeft());
        }
        if (node.getRight() != null) {
            if (node.getPosX() > root.getPosX())
                node.getRight().setPosX(3 * mitad + mitad / 2);
            else
                node.getRight().setPosX(mitad + mitad / 2);
            node.getRight().setPosY(node.getPosY() + height);
            asignarPosNodos(mitad, node.getRight());
        }
    }

    public void asignarPosiciones(int widthNode) {
        if (root != null) {
            asignarPosiciones(root, 1, depth(), 0, 0, widthNode);
        }
    }

    private void asignarPosiciones(Node node, int level, int depth, int xParent, int yParent, int widthNode) {
        if (node == null)
            return;

        int xPos = xParent + (int) Math.pow(2, depth - level) * widthNode / 2;
        int yPos = yParent + level * widthNode;

        node.setPosX(xPos);
        node.setPosY(yPos);

        asignarPosiciones(node.getLeft(), level + 1, depth, xParent, yParent, widthNode);
        asignarPosiciones(node.getRight(), level + 1, depth, xPos, yParent, widthNode);
    }
    public void marcar(int value){
        getNode(value, root).setMarcado(true);
    }
    public void desMarcar(int value){
        getNode(value, root).setMarcado(false);
    }

    public void eliminarRightOfRoot() {
        if (root != null && root.getRight() != null)
            root.setRight(null);
    }

    public static void main(String[] args) {
        // Arboles Heap ----------------
        TreeBinary tb = new TreeBinary();

        for (int i = 1; i < 8; i++) {
            tb.insertarHeap(i * 10);
        }

        tb.insertarHeap("10,20,30,40,50,60,70,80,90");
//        tb.insertarHeap(20);
//        tb.insertarHeap(10);
//        tb.insertarHeap(19);
//        tb.insertarHeap(9);
//        tb.insertarHeap(4);
//        tb.insertarHeap(16);
//        tb.insertarHeap(13);
//        tb.insertarHeap(8);
//        tb.insertarHeap(7);
//        tb.insertarHeap(3);

        System.out.println();
        tb.recorridoPorNiveles2();
        System.out.println("");

    }

}
