package app;

import java.util.Vector;
import javax.swing.*;
/**
 * Program <code>app.MyWindow</code>
 * The class <code>app.ListModelTest</code> describes
 * describes creating a List with values.
 * @author Anastasiya Prakapovich
 * @version 1.0	1/05/2020
 */
class ListModelTest extends AbstractListModel {

    private static final long serialVersionUID = 1L;
    Vector<Object> v = new Vector<Object>();

    //public app.ListModelTest() { }

    /**
     * Constructor that adds elements to the List.
     * @param objects array of elements to add.
     */
    public ListModelTest(Object[] objects) {
        for(int i = 0; i < objects.length; i++) {
            v.addElement(objects[i]);
        }
    }

    /**
     * Method returns element at given index.
     * @param index index of element.
     * @return element.
     */
    public Object getElementAt(int index) { return v.elementAt(index); }

    /**
     * Method returns size of List.
     * @return size of List.
     */
    public int getSize() { return v.size(); }
   /* public void add(int index, Object object) {
        v.insertElementAt(object, index);
        fireIntervalAdded(this,index,index);
    }
  public void add(Object object) {
        add(getSize(),object);
    }
    public void remove(int index) {
        v.removeElementAt(index);
        fireIntervalRemoved(this, index, index);
    }*/
}

