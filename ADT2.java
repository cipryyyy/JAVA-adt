package javaFile;           //package definition

interface Container {       //Container interface
    void makeEmpty();       //Erase virtual size
    boolean isEmpty();      //Check if the container is virtually empty
    String print();
}
interface Stack extends Container {
    void push(Object obj);      //add object to stack
    Object top();               //read the first object that will be read
    Object pop();               //use the first object (based on LIFO protocol)
}
interface Queue extends Container {
    void enqueue(Object obj);   //add object to queue
    Object getFront();          //read the first object that will be read
    Object dequeue();           //use the first object (base on FIFO protocol)
}

class FixedStack implements Stack{      //JAVA stack like

    //CLASS VARIABLES
    private final int BUFFER = 1024;       //FIXED buffer
    private int vSize;                    //virtual size
    private Object[] v;                   //array

    //CLASS METHODS
    public FixedStack() {                   //constructor
        v = new Object[BUFFER];
        makeEmpty();                        //set the size to 0
    }
    public String toString() {              //datas about the stack
        String ret = "Stack virtual size: " + vSize + "\nStack capacity: " + BUFFER + "\nType: Fixed";
        return(ret);
    }

    //CONTAINER METHODS
    public boolean isEmpty() {          //Check if stack is empty
        return (vSize == 0);
    }
    public void makeEmpty() {           //Make the stack empty 
        vSize = 0;
    }
    public String print() {
        String ret = "[";
        for (int i = 0; i < BUFFER; i++) {
            ret += v[i];
            if (i != BUFFER-1) {
                ret += ", ";
            }
        }
        ret+="]";
        return ret;
    }

    //STACK METHODS
    public void push(Object obj) {      //add element
        if (vSize == BUFFER) {                  //if it's full it throws the runtime type error
            throw new FullStackException();
        }
        v[vSize] = obj;                 //add the element
        vSize++;                        //increment the virtual size
    }
    public Object pop() {               //reamove
        if (vSize == 0) {                   //If it's empty it throws runtime type error
            throw new EmptyStackException();
        }
        Object obj = v[vSize-1];            //element to be returned
        vSize--;                            //decrement logic size
        return(obj);
    }
    public Object top() {               //last element
        if (vSize == 0) {
            throw new EmptyStackException();
        }
        return (v[vSize-1]);
    }
}

class CycleStack implements Stack {         //Memory action like
    //CLASS VARIABLES
    private final int BUFFER = 1024;           
    private int vSize;          //virtual size
    private int stopPoint;      //stop point (where the stack stored the first element)
    private int addPoint;       //add point (where the stack stored the last element)
    private Object[] v;

    //CLASS METHODS
    public CycleStack() {       //constructor
        v = new Object[BUFFER];
        makeEmpty();
    }
    public String toString() {      //Datas about the stack
        String ret = "Stack virtual size: " + vSize + "\nStack capacity: " + BUFFER + "\nType: Cycle\nPins location: " + stopPoint + " - - " + addPoint;
        return(ret);
    }

    //CONTAINER METHODS
    public String print() {
        String ret = "[";
        for (int i = 0; i < BUFFER; i++) {
            ret += v[i];
            if (i != BUFFER-1) {
                ret += ", ";
            }
        }
        ret+="]";
        return ret;
    }
    public boolean isEmpty() {          //Check line 30 for info
        return (vSize == 0);
    }
    public void makeEmpty() {           //Check line 33 for info
        stopPoint = 0;
        addPoint = 0;
        vSize = 0;
    }

    //STACK METHODS
    public void push(Object obj) {      
        if (vSize == BUFFER) {              //If the stack is full modify add and end points
            if (stopPoint == BUFFER-1) {        // if the stopPoint is at the end, it returns to the start
                stopPoint = 0;
            } else {                            // otherwise it will just increment
                stopPoint++;
            }
            v[addPoint] = obj;                 //replace the old element
            if (addPoint == BUFFER-1) {         //cycles the addPoint like stopPoint
                addPoint = 0;
            } else {
                addPoint++;
            }                                   //NOTE: vSize doesn't change, it is already equal to BUFFER
        } else {                        //actLike FixedArray, but addPoint cycles
            v[addPoint] = obj;              
            vSize++;                        
            if (addPoint == BUFFER-1) {         //cycle part
                addPoint = 0;
            } else {
                addPoint++;
            }
        }
    }
    public Object top(){
        if (vSize == 0) {                   //check if stack is empty
            throw new EmptyStackException();
        }
        if (addPoint == 0) {        //returns the last element, if the addPoint points to zero, return the last element of array
            return v[BUFFER-1];    //it would ask Array[index: -1], throwing ArrayOutOfBoundException.
        } else {
            return v[addPoint-1];
        }
    }
    public Object pop(){
        if (vSize == 0) {
            throw new EmptyStackException();
        }
        if (addPoint == 0) {
            addPoint = BUFFER - 1;
        } else {
            addPoint--;
        }
        Object obj = v[addPoint];
        vSize--;
        return obj;
    }
}

class GrowingStack implements Stack{
    //CLASS VARIABLES
    private int BUFFER = 1024;
    private int vSize;
    private Object[] v;

    //CLASS METHODS
    public GrowingStack() {
        v = new Object[BUFFER];
        makeEmpty();
    }
    public String toString() {              //Datas about the stack
        String ret = "Stack virtual size: " + vSize + "\nStack capacity: " + BUFFER + "\nType: Growing\n\n";
        return(ret);
    }
    private Object[] resize(Object[] arr, int size) {           //makes the array bigger, in case of FullStackExcpetion
        Object[] newArr = new Object[size];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        return newArr;
    }

    //CONTAINER METHODS
    public String print() {
        String ret = "[";
        for (int i = 0; i < BUFFER; i++) {
            ret += v[i];
            if (i != BUFFER-1) {
                ret += ", ";
            }
        }
        ret+="]";
        return ret;
    }
    public boolean isEmpty() {                  //Check line 30 for info
        return (vSize == 0);
    }
    public void makeEmpty() {                   //Check line 33 for info
        vSize = 0;
    }

    //STACK METHODS
    public void push(Object obj) {
        if (vSize == BUFFER) {              //If the stack is full, doubles the size
            BUFFER *=2;
            v = resize(v, BUFFER);
        }
        v[vSize] = obj;
        vSize++;
    }
    public Object pop() {               //pop method, identical to FixedStack one
        if (vSize == 0) {
            throw new EmptyStackException();
        }
        Object obj = v[vSize-1];
        vSize--;
        return(obj);
    }
    public Object top() {               //top method, identical to FixedStack one
        if (vSize == 0) {
            throw new EmptyStackException();
        }
        return (v[vSize-1]);
    }
}

/*
 * class FixedQueue {
 *  
 * }
 * class CycleQueue {
 * 
 * }
 * class GrowingQueue {
 * 
 * }
 * class FixedDeque {
 * 
 * }
 * class CycleDeque {
 * 
 * }
 * class GrowingDeque {
 * 
 * }
 */

//EXCEPTIONS
class FullQueueException extends RuntimeException {         //Exception thrown if the queue is full
    public String toString() {
        return("Queue is full");
    }
}
class EmptyQueueException extends RuntimeException {         //Exception thrown if the queue is empty
    public String toString() {
        return("Queue is empty");
    }
}
class FullStackException extends RuntimeException {         //Exception thrown if the stack is full
    public String toString() {
        return("Stack is full");
    }
}
class EmptyStackException extends RuntimeException {         //Exception thrown if the stack is empty  
    public String toString() {
        return("Stack is empty");
    }
}