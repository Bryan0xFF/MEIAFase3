/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rodri
 */
public class ABB<T extends Comparable<T>> implements Comparator<T> {

    private Nodo<T> head; 
     
    public ABB() {
        head = new Nodo();
    }
    
    private boolean isLeaf(Nodo<T> node) {
      if (node.Left == null && node.Right == null) {
          return true;
         }
          return false;
    }
    
    public Nodo<T> getHead() {
        return head;
    }
    
    public Nodo<T> addLeft(T value, Nodo<T> node) throws Exception {
        Nodo<T> child = new Nodo(null, null, node, value);
        node.setLeft(child);
        return child;
    }
    
    public Nodo<T> addRight(T value, Nodo<T> node) throws Exception {
        Nodo<T> child = new Nodo(null, null, node, value);
        node.setRight(child);
        return child;
    }
    
    public void insert(T value, Nodo<T> node) {
        if(node == null) {
            node = new Nodo();
            
        }
        else {
            if(compare(value, node.Value) < 0) {
               // node.Left = new Nodo();
              //  node.Left.Parent = node;
                insert(value, node.Left);
            }
            else 
            if(compare(value,node.Value) > 0) {
               // node.Right = new Nodo();
              //  node.Right.Parent = node;
                insert(value, node.Right);
            }
        }
        
    }     

    @Override
    public int compare(T a, T b) {
        return a.compareTo(b);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
