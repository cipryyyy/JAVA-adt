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
    void enqueue(Object obj);   //add object to queue
    Object getFront();          //read the first object that will be read
    Object dequeue();           //use the first object (base on FIFO protocol)
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
        String ret = "Stack virtual size: " + vSize + "\nStack capacity: " + BUFFER + "\nType: Cycle\nIndex: " + stopIndex + " " + addIndex;
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
    public void enqueue(Object obj) {           //add element to the queue
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

    public Object dequeue() {               //'pop' method of the queue
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
    public void enqueue(Object obj) {
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

    public Object dequeue() {
        if (vSize == 0) {
            throw new EmptyQueueException();
        }
        readIndex %= v.length;
        Object obj = getFront();
        vSize--;
        readIndex++;

        return (obj);
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
        BUFFER = 128;
        v = new Object[BUFFER];
        makeEmpty();
    }
    //no user defined constructor, buffer is reduced
    public String toString() {              //Datas about the queue
        String ret = "Queue virtual size: " + vSize + "\nQueue capacity: " + BUFFER + "\nType: Growing";
        return(ret);
    }
    private Object[] resize(Object[] arr, int writeIndex, int readIndex, int size) {      //RESIZE the queue
        Object[] newArr = new Object[size];                         
        System.arraycopy(arr, 0, newArr, 0, writeIndex);           //copies the element before the index at the start
        System.arraycopy(arr, readIndex, newArr, size-arr.length, arr.length-readIndex);  //the element after at the end of the array
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
    public void enqueue(Object obj) {           //add element to the queue
        if (vSize == BUFFER) {                      //If queue is full
            if (writeIndex<readIndex){
                readIndex += BUFFER;
            }
            BUFFER *= 2;
            v = resize(v, writeIndex, readIndex, BUFFER);
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

    public Object dequeue() {           //ERROR while reading values
        if (vSize == 0) {
            throw new EmptyQueueException();
        }
        readIndex %= v.length;
        Object obj = getFront();
        vSize--;
        readIndex++;

        return (obj);
    }
}

class FixedDeque implements Stack, Queue{       //double ended Queue, method will copy stack and queue but they will differ
    //CLASS VARIABLES
    final private int BUFFER;
    private int vSize;
    private int queueIndex;
    private int stackIndex;
    private Object[] v;

    //CLASS METHODS
    public FixedDeque() {               //constructor
        BUFFER = 1024;
        v = new Object[BUFFER];
        makeEmpty();
    }
    public FixedDeque(int buffer) {               //constructor
        BUFFER = buffer;
        v = new Object[BUFFER];
        makeEmpty();
    }
    public String toString() {              //Datas about the queue
        String ret = "Deque virtual size: " + vSize + "\nDeque capacity: " + BUFFER + "\nType: Fixed"+"\nIndex: " + queueIndex + " " +stackIndex;
        return(ret);
    }

    //CONTAINER METHODS
    public void makeEmpty() {
        vSize = 0;
        queueIndex = 0;
        stackIndex = 0;
    }
    public boolean isEmpty() {
        return (vSize == 0)
;    }
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

    //RIGHTSIDE
    public void push(Object obj) {              //rightside will first add the element and the move the index
        if (vSize == BUFFER) {
            throw new FullDequeException();
        }
        v[stackIndex] = obj;
        vSize++;
        if (stackIndex == BUFFER-1) {
            stackIndex = 0;
        } else {
            stackIndex++;
        }
    }
    public Object top() {                       //get the last element added
        if (vSize == 0){
            throw new EmptyDequeException();
        }
        Object obj = v[stackIndex-1];
        return  obj;
    }
    public Object pop() {
        if (vSize == 0){
            throw new EmptyDequeException();
        }
        vSize--;
        if (stackIndex == 0) {
            stackIndex = BUFFER-1;
        } else {
            stackIndex--;
        }
        Object obj = v[stackIndex];
        return  obj;
    }

    //LEFTSIDE
    public void enqueue(Object obj){                //leftside will first move back, and the add the element
        if (vSize == BUFFER){
            throw new FullDequeException();
        }
        if (queueIndex == 0) {
            queueIndex = BUFFER-1;
        } else {
            queueIndex--;
        }
        v[queueIndex] = obj;
        vSize++;

    }
    public Object getFront() {
        if (vSize == 0){
            throw new EmptyDequeException();
        }
        Object obj = v[queueIndex];
        return  obj;
    }
    public Object dequeue() {
        if (vSize == 0){
            throw new EmptyDequeException();
        }
        Object obj = v[queueIndex];
        if (queueIndex == BUFFER-1) {
            queueIndex = 0;
        } else {
            queueIndex++;
        }
        vSize--;
        return  obj;
    }
}

class CycleDeque implements Stack, Queue{       //double ended Queue, method will copy stack and queue but they will differ
    //CLASS VARIABLES
    final private int BUFFER;
    private int vSize;
    private int queueIndex;
    private int stackIndex;
    private Object[] v;

    //CLASS METHODS
    public CycleDeque() {               //constructor
        BUFFER = 1024;
        v = new Object[BUFFER];
        makeEmpty();
    }
    public CycleDeque(int buffer) {               //constructor
        BUFFER = buffer;
        v = new Object[BUFFER];
        makeEmpty();
    }
    public String toString() {              //Datas about the queue
        String ret = "Deque virtual size: " + vSize + "\nDeque capacity: " + BUFFER + "\nType: Cycle"+"\nIndex: " + queueIndex + " " +stackIndex;
        return(ret);
    }

    //CONTAINER METHODS
    public void makeEmpty() {
        vSize = 0;
        queueIndex = 0;
        stackIndex = 0;
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

    //RIGHTSIDE
    public void push(Object obj) {              //rightside will first add the element and the move the index
        if (vSize == BUFFER) {
            if (queueIndex == BUFFER-1) {
                queueIndex = 0;
            } else {
                queueIndex++;
            }
        }
        v[stackIndex] = obj;
        if (vSize != BUFFER) {
            vSize++;
        }
        if (stackIndex == BUFFER-1) {
            stackIndex = 0;
        } else {
            stackIndex++;
        }
    }
    public Object top() {                       //get the last element added
        if (vSize == 0){
            throw new EmptyDequeException();
        }
        Object obj = v[stackIndex-1];
        return  obj;
    }
    public Object pop() {
        if (vSize == 0){
            throw new EmptyDequeException();
        }
        vSize--;
        if (stackIndex == 0) {
            stackIndex = BUFFER-1;
        } else {
            stackIndex--;
        }
        Object obj = v[stackIndex];
        return  obj;
    }

    //LEFTSIDE
    public void enqueue(Object obj){                //leftside will first move back, and the add the element
        if (vSize == BUFFER){
            if (stackIndex == 0) {
                stackIndex = BUFFER-1;
            } else {
                stackIndex--;
            }
        }
        if (queueIndex == 0) {
            queueIndex = BUFFER-1;
        } else {
            queueIndex--;
        }
        if (vSize != BUFFER) {
            vSize++;
        }
        v[queueIndex] = obj;
    }

    public Object getFront() {
        if (vSize == 0){
            throw new EmptyDequeException();
        }
        Object obj = v[queueIndex];
        return  obj;
    }

    public Object dequeue() {
        if (vSize == 0){
            throw new EmptyDequeException();
        }
        Object obj = v[queueIndex];
        if (queueIndex == BUFFER-1) {
            queueIndex = 0;
        } else {
            queueIndex++;
        }
        vSize--;
        return  obj;
    }
}

class GrowingDeque implements Stack, Queue{
    //CLASS VARIABLES
    private int BUFFER;
    private int vSize;
    private int queueIndex;
    private int stackIndex;
    private Object[] v;

    //CLASS METHODS
    public GrowingDeque() {                 //constructor
        BUFFER = 8;                               //TEMP    switch 128    
        v = new Object[BUFFER];
        makeEmpty();
    }
    private Object[] resize(Object[] arr, int queueIndex, int stackIndex, int size) {
        Object[] newArr = new Object[size];
        if (queueIndex > stackIndex) {
            System.arraycopy(arr, 0, newArr, 0, stackIndex);
            System.arraycopy(arr, queueIndex, newArr, size-queueIndex, arr.length-queueIndex);
        } else {
            System.arraycopy(arr, 0, newArr, 0, queueIndex);
            System.arraycopy(arr, stackIndex, newArr, size-stackIndex, arr.length-stackIndex);
        }
        return newArr;
    }
    //CONTAINER METHODS
    public void makeEmpty(){
        vSize = 0;
        queueIndex = 0;
        stackIndex = 0;
    }
    public boolean isEmpty(){
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

    //RIGHTSIDE METHODS
    public void push(Object obj) {              //Logic equal to FixedDeque, but without the FullDequeException
        if (vSize == BUFFER) {
            if (queueIndex>stackIndex) {
                queueIndex += BUFFER;
            }
            BUFFER *= 2;
            v = resize(v, queueIndex, stackIndex, vSize);
        }
        v[stackIndex] = obj;
        if (vSize != BUFFER) {
            vSize++;
        }
        if (stackIndex == BUFFER-1) {
            stackIndex = 0;
        } else {
            stackIndex++;
        }
    }
    public Object top() {                       //get the last element added
        if (vSize == 0){
            throw new EmptyDequeException();
        }
        Object obj = v[stackIndex-1];
        return  obj;
    }
    public Object pop() {
        if (vSize == 0){
            throw new EmptyDequeException();
        }
        vSize--;
        if (stackIndex == 0) {
            stackIndex = BUFFER-1;
        } else {
            stackIndex--;
        }
        Object obj = v[stackIndex];
        return  obj;
    }

    //LEFTSIDE METHODS
    public void enqueue(Object obj){
        if (vSize == BUFFER){
                                                    //TODO
        }
        if (queueIndex == 0) {
            queueIndex = BUFFER-1;
        } else {
            queueIndex--;
        }
        if (vSize != BUFFER) {
            vSize++;
        }
        v[queueIndex] = obj;
    }

    public Object getFront() {
        if (vSize == 0){
            throw new EmptyDequeException();
        }
        Object obj = v[queueIndex];
        return  obj;
    }
    public Object dequeue() {
        if (vSize == 0){
            throw new EmptyDequeException();
        }
        Object obj = v[queueIndex];
        if (queueIndex == BUFFER-1) {
            queueIndex = 0;
        } else {
            queueIndex++;
        }
        vSize--;
        return  obj;
    }
}

//EXCEPTIONS
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
class FullDequeException extends RuntimeException {         //Exception thrown if the deque is full
    public String toString() {
        return("Deque is full");
    }
}
class EmptyDequeException extends RuntimeException {         //Exception thrown if the deque is empty
    public String toString() {
        return("Deque is empty");
    }
}
