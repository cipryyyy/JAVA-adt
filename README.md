# JAVA ADT

## Stacks
LIFO method
**Stack types**:

 - `FixedArray`, the BUFFER size is constant, if you add too many items it will throw a `FullStackException`.
 It's useful if you want to create a list of action without stressing the memory.
 - `GrowingArray`, the BUFFER size is variable, if you add too many items it will double the size.
It's useful if you don't care about the memory usage.
  - `CycleArray`, the BUFFER size is constant, if you add too many items it will start erasing the older items.
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
*#TODO*
## Deque
*#TODO*


## About
All the ADT shares these methods:
 - `isEmpty()`, checks if the ADT is empty.
*parameters*: `None`
*return*: `boolean`
 - `MakeEmpty()`, makes the ADT is empty (virtually).
*parameters*: `None`
*return*: `None`
 - `print()`, prints all the elements of the array.
*parameters*: `None`
*return*: `String`

You can change the **BUFFER**, by default is set at `1024`.

