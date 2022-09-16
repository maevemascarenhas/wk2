package w2;
import java.lang.AssertionError;


public class MyLinkedList <T> {
	
	class Node {
		//member variables
		public Node pointer = null;
		private T data;
		
		Node(T data) { // Node inherits T from MyLinkedList
			this.data = data;
		}
	}

	//member variables
	private int size;
	public Node head;
	public Node tail;
	
	/**
	 * Construct an MyLinkedList.
	 */
	public MyLinkedList() {
		//create empty list (no nodes)
		this.size = 0;
		this.head = null;
		this.tail = null;
	}

	/**
	 * Return the number of elements in the MyLinkedList.
	 *
	 * @return The number of elements in the MyLinkedList.
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Add an element to the end of the MyLinkedList.
	 *
	 * @param element The element to add.
	 */
	public void add(T element) {
		Node newNode = new Node(element);
		size++;	
		
		//if first element added, set as head (case #1)
		if (this.head == null) {
			this.head = newNode;
		}
		//if other elements exist, set as tail's pointer (case #2)
		else {
			this.tail.pointer = newNode;
		}
		
		//set as new tail
		this.tail = newNode;	
	}

	/**
	 * Get the element at the specified index.
	 *
	 * This function assumes that the index argument is within range of the MyLinkedList.
	 *
	 * @param index The index to get.
	 * @return The element at the specified index.
	 */
	public T get(int index) {
		//doesn't work without separate tail case... not sure why??
		if (index == this.size-1) {
			return this.tail.data;
		}
		
		Node indexNode = this.head;
		for (int i = 0; i < index; i++) {
			indexNode = indexNode.pointer;
		}
		return indexNode.data;
	}

	/**
	 * Remove the element at the specified index.
	 *
	 * This function assumes that the index argument is within range of the MyLinkedList.
	 *
	 * @param index The index to remove.
	 */
	public void remove(int index) {
		this.size--;
		// index = only node / head AND tail (case #1)
		if (this.size == 0) {
			this.head = null;
			this.tail = null;
			return;
		}
		// index = head (case #2)
		if (this.get(index).equals(this.head)) {
			this.head = this.head.pointer;
			return;
		}
		
		// index = tail (case #3) OR index = neither head NOR tail (case #4)
		//find Node before index
		Node prevNode = this.head;
		for (int i = 0; i < index-1; i++) {
			prevNode = prevNode.pointer;
		}
	
		// index = tail (case #3)
		if (this.get(index).equals(this.tail)) {	
			this.tail = prevNode;
			prevNode.pointer = null;
			return;
		}
	
		// index = neither head NOR tail (case #4)
		prevNode.pointer = prevNode.pointer.pointer;
		return;
	}

	/**
	 * Create a String representation of the MyLinkedList.
	 *
	 * @return A String representation of the MyLinkedList.
	 */
	public String toString() {
		String result = "{";
		if (this.size() > 0) {
			result += this.get(0);
		}
		for (int i = 1; i < this.size; i++) {
			result += ", " + this.get(i);
		}
		result += "}";
		return result;
	}

	/**
	 * Check that an MyLinkedList contains the same elements as an int array.
	 *
	 * If the list and the array are not the same, throw an AssertionError.
	 *
	 * @param list The MyLinkedList to check.
	 * @param answer The expected answer, in the form of an int array.
	 */
	public static void assertArraysEqual(MyLinkedList <?> list, int[] answer) {
		if (list.size() != answer.length) {
			throw new AssertionError("Expected list of length " + answer.length + " but got " + list.size());
		}
		for (int i = 0; i < answer.length; i++) {
			if ((Integer)list.get(i) != answer[i]) {
				throw new AssertionError("Expected " + answer[i] + " but got " + list.get(i) + " at index " + i);
			}
		}
	}

	/*
	 * Test that the empty arraylist has size 0.
	 */
	public static void test1() {
		MyLinkedList<Integer> list = new MyLinkedList <> ();
		int[] answer = new int[0];
		assertArraysEqual(list, answer);
	}

	/*
	 * Test insertion into an arraylist (without resizing).
	 */
	public static void test2() {
		MyLinkedList <Integer> list = new MyLinkedList <> ();
		for (int i = 0; i < 3; i++) {
			list.add(i * i);
		}
		int[] answer = {0, 1, 4};
		assertArraysEqual(list, answer);
	}

	/*
	 * Test deletion from an arraylist without emptying it.
	 */
	public static void test3() {
		MyLinkedList <Integer> list = new MyLinkedList <> ();
		for (int i = 0; i < 5; i++) {
			list.add(i * i);
		}
		list.remove(1);
		list.remove(2);
		int[] answer = {0, 4, 16};
		MyLinkedList.assertArraysEqual(list, answer);
	}

	/*
	 * Test deletion from an arraylist and emptying it.
	 */
	public static void test4() {
		MyLinkedList <Integer> list = new MyLinkedList <> ();
		for (int i = 0; i < 5; i++) {
			list.add(i * i);
		}

		list.remove(1);
		list.remove(2);

		// delete the final remaining numbers
		list.remove(0);
		list.remove(0);
		list.remove(0);
		int[] answer1 = {};
		MyLinkedList.assertArraysEqual(list, answer1);

		// check that there are no last-element issues
		for (int i = 0; i < 5; i++) {
			list.add(i * i);
		}
		list.remove(4);
		list.add(-1);
		int[] answer2 = {0, 1, 4, 9, -1};
		MyLinkedList.assertArraysEqual(list, answer2);
	}

	/*
	 * Test insertion into an arraylist (with resizing).
	 */
	public static void test5() {
		MyLinkedList <Integer> list = new MyLinkedList <> ();
		for (int i = 0; i < 12; i++) {
			list.add(i * i);
		}
		int[] answer = {0, 1, 4, 9, 16, 25, 36, 49, 64, 81, 100, 121};
		MyLinkedList.assertArraysEqual(list, answer);
	}

	/**
	 * Put the MyLinkedList through some simple tests.
	 *
	 * @param args Ignored command line arguments.
	 */
	public static void main(String[] args) {
		test1();
		test2();
		test3();
		test4();
		test5();

		System.out.println("pass");
	}

}
