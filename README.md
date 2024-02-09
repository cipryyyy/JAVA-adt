# JAVA ADT

## Stacks
LIFO method
**Stack types**:

 - `FixedStack`, the BUFFER size is constant, if you add too many items it will throw a `FullStackException`.
 It's useful if you want to create a list of action without stressing the memory.
 - `GrowingStack`, the BUFFER size is variable, if you add too many items it will double the size.
It's useful if you don't care about the memory usage.
  - `CycleStack`, the BUFFER size is constant, if you add too many items it will start erasing the older items.
It is wrote with a `O(1)` complexity, so it is very efficient.
Works better if you have to create an history.

**Commands**:

 - `push()`, add an elements `obj` inside of the stack.
*parameters*: `Object obj`
*return*: `void`
 - `top()`, returns the last element inserted.
*parameters*: `None`
*return*: `Object obj`
 - `pop()`, return and remove the last element of the stack.
*parameters*: `None`
*return*: `Object obj`

## Queue

FIFO method
**Queue types**:

 - `FixedQueue`, the BUFFER size is constant, if you add too many items it will throw a `FullQueueException`.
It is designed with an `O(1)`, so the code is a bit complex.
 - `CycleQueue`, the BUFFER size is constant, if you add too many items it will erase the older one and replace it with the newer one.
 - `GrowingQueue`, the BUFFER size can increase if the queue is full.
If `FullQueueException` is thrown, the BUFFER size doubles.

**Commands**:

 - `enqueue()`, add an elements `obj` inside of the queue.
*parameters*: `Object obj`
*return*: `void`
 - `getFront()`, returns the first element inserted.
*parameters*: `None`
*return*: `Object obj`
 - `dequeue()`, return and remove the first element of the queue.
*parameters*: `None`
*return*: `Object obj`

## Deque
Work with LIFO and FIFO methods, it's a _Double Ended Queue_.

**Queue types**:

 - `FixedDeque`, the BUFFER size is constant, if you add too many items it will throw a `FullDequeException`.
 - `CycleDeque` TODO
 - `GrowingDeque`, TODO

**Commands**:

 - `enqueue()`, add an elements `obj` at the left side of the deque.
*parameters*: `Object obj`
*return*: `void`
 - `getFront()`, returns the element at the left end of the deque.
*parameters*: `None`
*return*: `Object obj`
 - `dequeue()`, return and remove the element at the left end of the deque.
*parameters*: `None`
*return*: `Object obj`
 - `push()`, add an elements `obj` at the right end of the deque.
*parameters*: `Object obj`
*return*: `void`
 - `top()`, returns the element at the right end of the deque.
*parameters*: `None`
*return*: `Object obj`
 - `pop()`, return and remove the last element at the right end of the deque.
*parameters*: `None`
*return*: `Object obj`

## About
All ADT types share these methods:
 - `isEmpty()`, checks if the ADT is empty.
*parameters*: `None`
*return*: `boolean`
 - `MakeEmpty()`, makes the ADT is empty (virtually).
*parameters*: `None`
*return*: `None`
 - `print()`, prints all the elements of the array.
*parameters*: `None`
*return*: `String`

You can change the **BUFFER** when you define the object, by default is set at `1024`.
```
FixedStack myStack = new FixedStack(); //BUFFER = 1024
FixedStack myStack = new FixedStack(8); //BUFFER = 8
```
