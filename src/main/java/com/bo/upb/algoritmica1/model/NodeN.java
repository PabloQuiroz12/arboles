package com.bo.upb.algoritmica1.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class NodeN <T> {

    private T value;
    @Getter @Setter
    private int posX;
    @Getter @Setter
    private int posY;

    private List<NodeN<T>> children = new ArrayList<NodeN<T>>(0);

    private boolean marcado; // para TRIE

    public NodeN(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<NodeN<T>> getChildren() {
        return children;
    }

    public void setChildren(List<NodeN<T>> children) {
        this.children = children;
    }

    public boolean isMarcado() {
        return marcado;
    }
    public int getChildCount() {
        return children.size();
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    /**
     * Función para indicar si el nodo es hoja
     * @return
     */
    public boolean isLeaf() {
        return children.isEmpty();
    }

    public boolean hasOnlyOneChild() {
        return children.size() == 1;
    }

    public boolean hasMoreOneChild() {
        return children.size() > 1;
    }

    public void addChild(NodeN child) {
        children.add(child);
    }

}

