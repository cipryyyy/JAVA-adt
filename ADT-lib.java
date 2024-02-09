package javaFile;           //package definition

interface Container {       //Common methods between ADTs
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
    void enQueue(Object obj);   //add object to queue
    Object getFront();          //read the first object that will be read
    Object deQueue();           //use the first object (base on FIFO protocol)
}

class FixedStack implements Stack{      //JAVA stack like

    //CLASS VARIABLES
    private final int BUFFER;       //FIXED buffer
    private int vSize;                    //virtual size
    private Object[] v;                   //array

    //CLASS METHODS
    public FixedStack() {                   //constructor
        BUFFER = 1024;
        v = new Object[BUFFER];
        makeEmpty();                        //set the size to 0
    }
    public FixedStack(int buffer) {                   //constructor user defined
        BUFFER = buffer;
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
    public Object pop() {               //remove
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
    private final int BUFFER;           
    private int vSize;          //virtual size
    private int stopIndex;      //stop point (where the stack stored the first element)
    private int addIndex;       //add point (where the stack stored the last element)
    private Object[] v;

    //CLASS METHODS
    public CycleStack() {       //constructor
        BUFFER = 1024;
        v = new Object[BUFFER];
        makeEmpty();
    }
    public CycleStack(int buffer) {       //constructor user defined
        BUFFER = buffer;
        v = new Object[BUFFER];
        makeEmpty();
    }
    public String toString() {      //Datas about the stack
        String ret = "Stack virtual size: " + vSize + "\nStack capacity: " + BUFFER + "\nType: Cycle\nPins location: " + stopIndex + " - - " + addIndex;
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
        stopIndex = 0;
        addIndex = 0;
        vSize = 0;
    }

    //STACK METHODS
    public void push(Object obj) {      
        if (vSize == BUFFER) {              //If the stack is full modify add and end points
            if (stopIndex == BUFFER-1) {        // if the stopIndex is at the end, it returns to the start
                stopIndex = 0;
            } else {                            // otherwise it will just increment
                stopIndex++;
            }
            v[addIndex] = obj;                 //replace the old element
            if (addIndex == BUFFER-1) {         //cycles the addIndex like stopIndex
                addIndex = 0;
            } else {
                addIndex++;
            }                                   //NOTE: vSize doesn't change, it is already equal to BUFFER
        } else {                        //actLike FixedArray, but addIndex cycles
            v[addIndex] = obj;              
            vSize++;                        
            if (addIndex == BUFFER-1) {         //cycle part
                addIndex = 0;
            } else {
                addIndex++;
            }
        }
    }
    public Object top(){
        if (vSize == 0) {                   //check if stack is empty
            throw new EmptyStackException();
        }
        if (addIndex == 0) {        //returns the last element, if the addIndex points to zero, return the last element of array
            return v[BUFFER-1];    //it would ask Array[index: -1], throwing ArrayOutOfBoundException.
        } else {
            return v[addIndex-1];
        }
    }
    public Object pop(){
        if (vSize == 0) {
            throw new EmptyStackException();
        }
        if (addIndex == 0) {
            addIndex = BUFFER - 1;
        } else {
            addIndex--;
        }
        Object obj = v[addIndex];
        vSize--;
        return obj;
    }
}

class GrowingStack implements Stack{        //If you hate the FullStackException
    //CLASS VARIABLES
    private int BUFFER;             //buffer isn't constant
    private int vSize;
    private Object[] v;

    //CLASS METHODS
    public GrowingStack() {         //constructor
        BUFFER = 128;
        v = new Object[BUFFER];
        makeEmpty();
    }
    //there is no user defined constrcutor and the buffer is reduced to save memory (if possible)
    public String toString() {              //Datas about the stack
        String ret = "Stack virtual size: " + vSize + "\nStack capacity: " + BUFFER + "\nType: Growing";
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

class FixedQueue implements Queue {         //Works like a waiting queue
    //CLASS VARIABLES
    private Object[] v;
    private int vSize;
    private int writeIndex;             //index were element will be added
    private int readIndex;              //index were element will be read
    private final int BUFFER;

    //CLASS METHODS
    public FixedQueue() {           //constructor
        BUFFER = 1024;
        v = new Object[BUFFER];
        makeEmpty();
    }
    public FixedQueue(int buffer) {     //constructor user defined
        BUFFER = buffer;
        v = new Object[BUFFER];
        makeEmpty();
    }
    public String toString() {              //Datas about the queue
        String ret = "Queue virtual size: " + vSize + "\nQueue capacity: " + BUFFER + "\nType: Fixed";
        return(ret);
    }

    //CONTAINER METHODS
    public void makeEmpty() {
        vSize = 0;
        writeIndex = 0;
        readIndex = 0;
    }
    public boolean isEmpty() {
        return (vSize == 0);
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

    //QUEUE METHODS
    public void enQueue(Object obj) {           //add element to the queue
        if (vSize == BUFFER) {                      //If queue is full
            throw new FullQueueException();
        }
        vSize++;
        writeIndex %= v.length;             //If BUFFER is n size, and i go to index n(OutOfBound), i go to 0.
        v[writeIndex] = obj;
        writeIndex++;
    }

    public Object getFront() {                  //get the first element
        if (vSize == 0) {
            throw new EmptyQueueException();            //check if the queue is empty
        }
        readIndex %= v.length;
        return (v[readIndex]);
    }

    public Object deQueue() {               //'pop' method of the queue
        if (vSize == 0) {
            throw new EmptyQueueException();
        }
        readIndex %= v.length;          //same logic of the enqueue method       
        Object obj = getFront();
        vSize--;
        readIndex++;

        return (obj);
    }
}

class CycleQueue implements Queue {         //Careful with this one
    //CLASS VARIABLES
    private Object[] v;
    private int vSize;
    private int writeIndex;
    private int readIndex;
    private final int BUFFER;

    //CLASS METHODS
    public CycleQueue() {           //constructor
        BUFFER = 1024;
        v = new Object[BUFFER];
        makeEmpty();
    }
    public CycleQueue(int buffer) {     //constructor user defined
        BUFFER = buffer;
        v = new Object[BUFFER];
        makeEmpty();
    }
    public String toString() {              //Datas about the queue
        String ret = "Queue virtual size: " + vSize + "\nQueue capacity: " + BUFFER + "\nType: Cycle";
        return(ret);
    }

    //CONTAINER METHODS
    public void makeEmpty() {
        vSize = 0;
        writeIndex = 0;
        readIndex = 0;
    }
    public boolean isEmpty() {
        return (vSize == 0);
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

    //QUEUE METHODS
    public void enQueue(Object obj) {
        if (vSize == BUFFER) {              //if the buffer is full, i move the readIndex
            readIndex++;                        //in this way I have a space in the queue
            readIndex %= BUFFER;                //obviously the first element will be erased
            writeIndex %= BUFFER;
            v[writeIndex] = obj;
            writeIndex++;
        } else {                    //else, works like the fixed queue
            vSize++;
            writeIndex %= BUFFER;
            v[writeIndex] = obj;
            writeIndex++;
        }
    }

    public Object getFront() {
        if (vSize == 0) {
            throw new EmptyQueueException();
        }
        readIndex %= v.length;
        return (v[readIndex]);
    }

    public Object deQueue() {
        if (vSize == 0) {
            throw new EmptyQueueException();
        }
        readIndex %= v.length;
        Object temp = getFront();
        vSize--;
        readIndex++;

        return (temp);
    }
}

class GrowingQueue {                    //ERROR WHILE DEQUEUEING, DON'T USE
    //CLASS VARIABLES
    private Object[] v;
    private int vSize;
    private int writeIndex;
    private int readIndex;
    private int BUFFER;             //buffer is a variable

    //CLASS METHODS
    public GrowingQueue() {           //constructor
        BUFFER = 4;
        v = new Object[BUFFER];
        makeEmpty();
    }
    //no user defined constructor, buffer is reduced
    public String toString() {              //Datas about the queue
        String ret = "Queue virtual size: " + vSize + "\nQueue capacity: " + BUFFER + "\nType: Cycle";
        return(ret);
    }
    private Object[] resize(Object[] arr, int actualIndex, int size) {      //RESIZE the queue
        Object[] newArr = new Object[size];                         
        System.arraycopy(arr, 0, newArr, 0, actualIndex);           //copies the element before the index at the start
        System.arraycopy(arr, actualIndex, newArr, size-arr.length, arr.length-actualIndex);  //the element after at the end of the array
        return newArr;
    }

    //CONTAINER METHODS
    public void makeEmpty() {
        vSize = 0;
        writeIndex = 0;
        readIndex = 0;
    }
    public boolean isEmpty() {
        return (vSize == 0);
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

    //QUEUE METHODS
    public void enQueue(Object obj) {           //add element to the queue
        if (vSize == BUFFER) {                      //If queue is full
            BUFFER *=2;
            v = resize(v, writeIndex, BUFFER);
        }
        vSize++;
        writeIndex %= v.length;             //If BUFFER is n size, and i go to index n(OutOfBound), i go to 0.
        v[writeIndex] = obj;
        writeIndex++;
    }

    public Object getFront() {
        if (vSize == 0) {
            throw new EmptyQueueException();
        }
        readIndex %= v.length;
        return (v[readIndex]);
    }

    public Object deQueue() {           //ERROR while reading values
        if (vSize == 0) {
            throw new EmptyQueueException();
        }
        readIndex %= v.length;
        Object temp = getFront();
        vSize--;
        readIndex++;

        return (temp);
    }
}

/*TODO:
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
