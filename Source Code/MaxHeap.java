import java.util.Arrays;
/**
   A class that implements the ADT maxheap by using an array.
 
   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 5.0
*/
public final class MaxHeap<T extends Comparable<? super T>>
             implements MaxHeapInterface<T>
{
   private T[] heap;      // Array of heap entries; ignore heap[0]
   private int lastIndex; // Index of last entry and number of entries
   private boolean integrityOK = false;
	private static final int DEFAULT_CAPACITY = 25;
	private static final int MAX_CAPACITY = 10000;
   
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
      T[] tempHeap = (T[])new Comparable[initialCapacity + 1];
      heap = tempHeap;
      lastIndex = 0;
      integrityOK = true;
   } // end constructor

   // Section 27.16 (max heap constructor)
   public MaxHeap(T[] entries)
   {
      this(entries.length); // Call other constructor
      lastIndex = entries.length;
      // Assertion: integrityOK = true

      // Copy given array to data field
      for (int index = 0; index < entries.length; index++)
         heap[index + 1] = entries[index];

      // Create heap
      for (int rootIndex = lastIndex / 2; rootIndex > 0; rootIndex--)
         reheap(rootIndex);
   } // end constructor

   // Section 27.20 (heap sort method)
   public static <T extends Comparable<? super T>>
       void heapSort(T[] array, int n)
   {
      // Create first heap
      for (int rootIndex = n / 2 - 1; rootIndex >= 0; rootIndex--)
         reheap(array, rootIndex, n - 1);

      swap(array, 0, n - 1);

      for (int lastIndex = n - 2; lastIndex > 0; lastIndex--)
      {
         reheap(array, 0, lastIndex);
         swap(array, 0, lastIndex);
      } // end for
   } // end heapSort

   private static final <T> void swap(T[]array, int i, int j) {
      T temp = array[i];
      array[i] = array[j];
      array[j] = temp; 
   } // end swap

   // Section 27.8 (add method) (See Segment 27.8 - original)
   public void add(T newEntry)
   {
      checkIntegrity();        // Ensure initialization of data fields
      int newIndex = lastIndex + 1;
      int parentIndex = newIndex / 2;
      while ( (parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) > 0)
      {
         heap[newIndex] = heap[parentIndex];
         newIndex = parentIndex;
         parentIndex = newIndex / 2;
      } // end while

      heap[newIndex] = newEntry;
      lastIndex++;
      ensureCapacity();
   } // end add

   // Section 27.12 (removeMax method/ remove method of a max-heap) (See Segment 27.12 - original)
   public T removeMax()
   {
      checkIntegrity();             // Ensure initialization of data fields
      T root = null;

      if (!isEmpty())
      {
         root = heap[1];            // Return value
         heap[1] = heap[lastIndex]; // Form a semiheap
         lastIndex--;               // Decrease size
         reheap(1);                 // Transform to a heap
      } // end if

      return root;
   } // end removeMax

   public T getMax()
   {
		checkIntegrity();
      T root = null;
      if (!isEmpty())
         root = heap[1];
      return root;
   } // end getMax

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
		checkIntegrity();
      while (lastIndex > -1)
      {
         heap[lastIndex] = null;
         lastIndex--;
      } // end while
      lastIndex = 0;
   } // end clear
   
   // Private methods

   // Section 27.11 (reheap with precondition)
   // Precondition: checkIntegrity has been called.
   private void reheap(int rootIndex)
   {
      boolean done = false;
      T orphan = heap[rootIndex];
      int leftChildIndex = 2 * rootIndex;

      while (!done && (leftChildIndex <= lastIndex) )
      {
         int largerChildIndex = leftChildIndex; // Assume larger
         int rightChildIndex = leftChildIndex + 1;

         if ( (rightChildIndex <= lastIndex) &&
               heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0)
         {
            largerChildIndex = rightChildIndex;
         } // end if

         if (orphan.compareTo(heap[largerChildIndex]) < 0)
         {
            heap[rootIndex] = heap[largerChildIndex];
            rootIndex = largerChildIndex;
            leftChildIndex = 2 * rootIndex;
         }
         else
            done = true;
      } // end while

      heap[rootIndex] = orphan;
   } // end reheap

   // Section 27.19 (reheap method with 3 input fields)
   private static <T extends Comparable<? super T>>
        void reheap(T[] heap, int rootIndex, int lastIndex)
   {
      boolean done = false;
      T orphan = heap[rootIndex];
      int leftChildIndex = 2 * rootIndex + 1;

      while (!done && (leftChildIndex <= lastIndex))
      {
         int largerChildIndex = leftChildIndex;
         int rightChildIndex = leftChildIndex + 1;

         if ( (rightChildIndex <= lastIndex) &&
               heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0)
         {
            largerChildIndex = rightChildIndex;
         } // end if

         if (orphan.compareTo(heap[largerChildIndex]) < 0)
         {
            heap[rootIndex] = heap[largerChildIndex];
            rootIndex = largerChildIndex;
            leftChildIndex = 2 * rootIndex + 1;
         }
         else
            done = true;
      } // end while

      heap[rootIndex] = orphan;
   } // end reheap

   private void ensureCapacity()
   {
      if(lastIndex >= heap.length - 1)
      {
         int newLength = 2 * heap.length;
         checkCapacity(lastIndex);
         heap = Arrays.copyOf(heap, newLength);
      }
   }

   private void checkIntegrity()
   {
      if(!integrityOK)
      {
         throw new SecurityException("ArrayList Object is corrupt.");
      }
   } // end checkIntegrity

   private void checkCapacity(int initialCapacity)
   {
      if(initialCapacity > MAX_CAPACITY)
      {
         throw new ArrayIndexOutOfBoundsException("Array capacity has exceeded maximum capacity.");
      }
   } // end checkCapacity

// . . .
} // end MaxHeap
