import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Deque<Item> implements Iterable<Item> {
	private Node first;
	private Node last;
	private Node sentinel;
	private int size = 0;
	private class Node
	{
		Item item;
		Node next;
		Node pre;
	}
 public Deque()                           // construct an empty deque
 {
 	sentinel = null;
 	first = null;
 	last = null;
 }
 public boolean isEmpty() {return size == 0;}                // is the deque empty?

 public int size() {return size;}                   // return the number of items on the deque

 public void addFirst(Item item)          // add the item to the front
 {
 	if (null == item) { throw new IllegalArgumentException(); }
 	else if (size == 0) {
 		size++;
 		first = new Node(); 
 		first.item = item;
 		first.next = sentinel;
 		first.pre = sentinel;
 		last = first;
 		sentinel = new Node();
 		sentinel.next = first;
 		sentinel.pre = last;
 	}
 	else {
 		size++;
 		Node oldfirst = first;
 		first = new Node();
 		first.item = item;
 		first.next = oldfirst;
 		first.pre = sentinel;
 		oldfirst.pre = first;
 		sentinel.next = first;
 	}  
 }

 public void addLast(Item item)           // add the item to the end
 {
 	if (null == item) { throw new IllegalArgumentException(); }
 	else if (size == 0) {
 		size++;
 		last = new Node(); 
 		last.item = item;
 		last.next = sentinel;
 		first = last;
 		sentinel = new Node();
 		sentinel.next = first;
 		sentinel.pre = last;
 	}
 	else {
 		size++;
 		Node oldlast = last;
 		last = new Node();
 		last.item = item;
 		last.pre = oldlast;
 		oldlast.next = last;
 		last.next = sentinel;
 	}  
 }
 public Item removeFirst()                // remove and return the item from the front
 {
 	if (isEmpty()) { throw new NoSuchElementException(); }
 	else if (size == 1) {
 		size--;
 		Item item = first.item;
 		first = null;
 		last = first;
 		sentinel.next = sentinel;
 		sentinel.pre = sentinel;
 		return item;
 	}
 	else {
 		size--;
 		Item item = first.item;
 		first = first.next;
 		sentinel.next = first;
 		first.pre = sentinel;
 		return item;
 	}   
 }
 public Item removeLast()                 // remove and return the item from the end
 {
 	if (isEmpty()) { throw new NoSuchElementException(); }
 	else if (size == 1) {
 		size--;
 		Item item = last.item;
 		last = null;
 		first = last; 
 		sentinel.next = sentinel;
 		sentinel.pre = sentinel;
 		return item;
 	}
 	else {
 		size--;
 		Item item = last.item;
 		last = last.pre;
 		sentinel.pre = last;
 		last.next = sentinel;
 		return item;
 	}   
 }
 public Iterator<Item> iterator() {return new ListIterator();}        // return an iterator over items in order from front to end
 
 private class ListIterator implements Iterator<Item>
 {
 	private Node current = first;

 	public boolean hasNext() {return current != null && current != sentinel;}
 	public void remove() {throw new UnsupportedOperationException();}
 	public Item next()
 	{
 		Item item = current.item;
 		current =  current.next;
 		if (!hasNext()) {
 			throw new NoSuchElementException();
 		} 
 		else{ return item; }
 	}
 }

 public static void main(String[] args)   // unit testing (optional)
 {
 	Deque<String> stack = new Deque<String>();
 	while (!StdIn.isEmpty()) {
 		String item = StdIn.readString();
 		if (!item.equals("-"))
 			stack.addFirst(item);
 		else if (!stack.isEmpty())
 			StdOut.print(stack.removeFirst() + "-" + stack.size() + " ");
  	}
 	StdOut.println("(" + stack.size() + " left on stack)");
 }
}



