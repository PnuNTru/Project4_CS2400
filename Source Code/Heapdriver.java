import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Heapdriver
{
    public static void main(String[] args) throws FileNotFoundException
    {
        System.out.println("=====================================================================");
       
        String fileName = "data_sorted.txt";
        generateHeap(fileName);

        fileName = "data_random.txt";
        generateHeap(fileName);
    }

    static void generateHeap(String fileName) throws FileNotFoundException
    {
        System.out.println("\nFile name: " + fileName);
        sequentialInsertion(fileName);
        optimalMethod(fileName);
    }
    
    static void sequentialInsertion(String fileName) throws FileNotFoundException
    {
        Scanner scan = new Scanner(new File(fileName));
        
        MaxHeap<Integer> heap = new MaxHeap<>(200);
        
        while(scan.hasNextInt())
            heap.add(scan.nextInt());
        scan.close();

        int iterations = 10;

        System.out.print("\nHeap built using sequential insertions: ");
        heap.toString(iterations);
        System.out.print("...\n");

        System.out.println("Number of swaps in the heap creation: " + heap.getSwap());

        for(int i = 0; i < iterations; i++)
        {
            heap.removeMax();
        }

        System.out.print("Heap after " + iterations + " removals: ");
        heap.toString(iterations);
        System.out.print("...");
        System.out.println("\n");
    }

    static void optimalMethod(String fileName) throws FileNotFoundException
    {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        Integer[] arr = new Integer[100];
        int counter = 0;
        int iterations = 10;

        while(scan.hasNextInt())
            arr[counter++] = scan.nextInt();
        scan.close();

        MaxHeap<Integer> heap = new MaxHeap<>(arr);

        System.out.print("\nHeap built using optimal method: ");
        heap.toString(iterations);
        System.out.print("...\n");

        System.out.println("Number of swaps in the heap creation: " + heap.getSwap());

        for(int i = 0; i < iterations; i++)
        {
            heap.removeMax();
        }

        System.out.print("Heap after " + iterations + " removals: ");
        heap.toString(iterations);
        System.out.print("...");
        System.out.println("\n=====================================================================");
    }
}
