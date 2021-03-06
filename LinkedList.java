
/**
 * @author : Sharwari Salunkhe ss3398@rit.edu
 * @author : Omkar Sarde       os4802@rit.edu
 * @version : 1.1
 */

class LinkedList<T extends Comparable<T>> {

    Node<T> head;

    /**
     * Constructor
     */
    LinkedList() {
        head = null;
    }

    /**
     * Adds people in increasing order
     * @param obj
     */
    void addIncreasing(T obj){
        if(head == null)
            head = new Node<>(obj,head);
        else {
            // find where to insert this node and then make the appropriate links
            Node<T> newNode = new Node<>(obj,null);
            // if this list contains and such node
            Node<T> current = head;
            Node<T> previous = head;
            while(current != null) {

                if( current.data.compareTo(newNode.data) >= 0 ) {	// found element less than the element to be inserted
                    if( current != previous ) {
                        newNode.next = current;			// so insert this new node after
                        previous.next = newNode;
                        break;
                    } else { 		// so you need to insert at the first position
                        newNode.next = current;			// so insert this new node after
                        head = newNode;
                        break;
                    }


                }

                previous = current;
                current = current.next;
            }

            if(current == null) {
                previous.next = newNode;
            }

        }
    }

    /**
     * Adds people in decreasing order
     * @param obj
     */
    void addDecreasing(T obj){
        if(head == null)
            head = new Node<T>(obj,head);
        else {
            // find where to insert this node and then make the appropriate links
            Node<T> newNode = new Node<>(obj,null);
            // if this list contains and such node
            Node<T> current = head;
            Node<T> previous = head;
            while(current != null) {

                if( current.data.compareTo(newNode.data) <= 0 ) {	// found element less than the element to be inserted
                    if( current != previous ) {
                        newNode.next = current;			// so insert this new node after
                        previous.next = newNode;
                        break;
                    } else { 		// so you need to insert at the first position
                        newNode.next = current;			// so insert this new node after
                        head = newNode;
                        break;
                    }


                }

                previous = current;
                current = current.next;
            }

            if(current == null) {
                previous.next = newNode;
            }

        }
    }

    /**
     * Removes from the linked list
     * @param obj
     */
    void remove(T obj) {
        Node<T> current = head;
        Node<T> previous = head;
        while(current != null) {

            //if( current.data.compareTo(obj) == 0 ) {	// found element less than the element to be inserted
            if( current.data == obj) {					// found element
                if( current != previous ) {
                    previous.next = current.next;			// so remove the current node
                    break;
                } else { 							// this is the first node
                    head = head.next;							// so increment the head
                    break;
                }


            }

            previous = current;
            current = current.next;
        }



    }

    /**
     * toString method
     * @return
     */
    public String toString() {
        Node<T> current = head;
        String temp = "\n[";

        while(current != null) {
            temp = temp + current.data + " ";
            current = current.next;
        }

        temp = temp + "] \n";

        return temp;
    }

}

/**
 * Node class
 * @param <E> type parameter
 */
class Node<E extends Comparable<E>> {
    E data;
    Node<E> next;

    Node( E data, Node<E> next ) {
        this.data = data;
        this.next = next;
    }
}