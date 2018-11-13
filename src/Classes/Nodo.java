/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author rodri
 */
public class Nodo<T> {
    
    public Nodo<T> Left;
    public Nodo<T> Right;
    public Nodo<T> Parent;
    public T Value;
    
    public Nodo() {   
    }
    
    public Nodo(Nodo<T> Left, Nodo<T> Right, Nodo<T> Parent, T Value) {
        this.Left = Left;
        this.Right = Right;
        this.Parent = Parent;
        this.Value = Value;
    }

    public Nodo<T> getLeft() {
        return Left;
    }

    public void setLeft(Nodo<T> Left) {
        this.Left = Left;
    }

    public Nodo<T> getRight() {
        return Right;
    }

    public void setRight(Nodo<T> Right) {
        this.Right = Right;
    }

    public Nodo<T> getParent() {
        return Parent;
    }

    public void setParent(Nodo<T> Parent) {
        this.Parent = Parent;
    }

    public T getValue() {
        return Value;
    }

    public void setValue(T Value) {
        this.Value = Value;
    }
    
        
    
    
}
