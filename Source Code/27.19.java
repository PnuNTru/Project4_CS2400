// @author Frank M. Carrano, Timothy M. Henry
// @version 5.0
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

