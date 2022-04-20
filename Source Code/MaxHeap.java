/**
   A class that implements the ADT maxheap by using an array.
 
   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 5.0
*/
public final class MaxHeap<T extends Comparable<? super T>> implements MaxHeapInterface<T>{
    
    private final T[] heap; // Array of heap entries; ignore heap[0]
    private int lastIndex; // Index of last entry and number of entries
    private boolean initialized = false;
    private static final int DEFAULT_CAPACITY = 25;
    private static final int MAX_CAPACITY = 10000;

    private int swapCounter = 0;

    public MaxHeap()
    {
        this(DEFAULT_CAPACITY); // Call next constructor
    } // end default constructor

    public MaxHeap(int initialCapacity)
    {
         // Is initialCapacity too small?
        if (initialCapacity < DEFAULT_CAPACITY)
            initialCapacity = DEFAULT_CAPACITY;
        else // Is initialCapacity too big?
            checkCapacity(initialCapacity);

         // The cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        T[] tempHeap = (T[]) new Comparable[initialCapacity + 1];
        heap = tempHeap;
        lastIndex = 0;
        initialized = true;
    } // end constructor

     // Section 27.16 (max heap constructor)
    public MaxHeap(T[] entries)
    {
        this(entries.length); // Call other constructor
        lastIndex = entries.length;
        assert initialized = true;  // Assertion: integrityOK = true
 
        // Copy given array to data field
        for(int index = 0; index < entries.length; index++)
            heap[index + 1] = entries[index];
        
        // Create heap
        for(int rootIndex = lastIndex / 2; rootIndex > 0; rootIndex --)
            reheap(rootIndex);
    } // end constructor

    // Section 27.8 (add method) (See Segment 27.8 - original)
    public void add(T newEntry)
    {
        checkInitialization();
        int newIndex = lastIndex + 1;
        int parentIndex = newIndex / 2;
        
        while ( (parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) > 0)
        {
            heap[newIndex] = heap[parentIndex];
            newIndex = parentIndex;
            parentIndex = newIndex / 2;

            swapCounter++;
        }// end while

        heap[newIndex] = newEntry;
        lastIndex++;
        ensureCapacity();
    }  // end add

     // Section 27.12 (removeMax method/ remove method of a max-heap) (See Segment 27.12 - original)
    public T removeMax()
    {
        checkInitialization(); // Ensure initialization of data fields
        T root = null;

        if (!isEmpty())
        {
            root = heap[1];            // Return value
            heap[1] = heap[lastIndex]; // Form a semiheap
            lastIndex--;               // Decrease size
            reheap(1);        // Transform to a heap
        } // end if 

        return root;
    } // end removeMax

    public T getMax()
    {

        checkInitialization();
        T root = null;
        if (!isEmpty())
            root = heap[1];

        return root;
    } //end getMax

    public boolean isEmpty()
    {
        return lastIndex < 1;
    } // end isEmpty

    public int getSize()
    {
        return lastIndex;
    } // end getSize

    public void clear()
    {

        checkInitialization();
        while (lastIndex > -1)
        {
            heap[lastIndex] = null;
            lastIndex--;
        } // end while

        lastIndex = 0;
    } // end clear

    public void checkCapacity(int topIndex)
    {
        if (topIndex > MAX_CAPACITY)
            throw new SecurityException("Array MaxHeap has exceeded maximum capacity.");
    } // end checkCapacity

    public void checkInitialization()
    {
        if (!this.initialized){
            throw new SecurityException("Array MaxHeap is corrupt.");
        }
    } // end checkInitialization

    public void ensureCapacity()
    {
        if ((lastIndex * 2) > heap.length)
            throw new SecurityException("Array MaxHeap not currently large enough.");
    } // end ensureCapacity

    public int getSwap()
    {
        return swapCounter;
    } // end getswap

    public void toString(int x)
    {
        for(int i = 1; i <= x; i++)
            System.out.print(heap[i] + ",");
    } // end toString
    
   // Private methods
   // Section 27.11 (reheap with precondition)
   // Precondition: checkIntegrity has been called.
    private void reheap(int rootIndex) 
    {
        boolean done = false;
        T orphan = heap[rootIndex];
        int leftChildIndex = 2 * rootIndex;

        while (!done && (leftChildIndex <= lastIndex)) 
        {
            int largerChildIndex = leftChildIndex;
            int rightChildIndex = leftChildIndex + 1;
            if ((rightChildIndex <= lastIndex) && heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0) 
            {
                largerChildIndex = rightChildIndex;
            }
            if (orphan.compareTo(heap[largerChildIndex]) < 0) 
            {
                heap[rootIndex] = heap[largerChildIndex];
                rootIndex = largerChildIndex;
                leftChildIndex = 2 * rootIndex;
                swapCounter++;
            } else
                done = true;
        }
        heap[rootIndex] = orphan;
    }

}
