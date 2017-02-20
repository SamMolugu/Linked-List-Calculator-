//double linked list
public class Node 
{
	private int value; // 0-9 only
	private Node prev; // goes to next node
	private Node next; // goes to prevuous node

	public Node() // initial value for an empty node
	{
		this.value = 0;
		this.prev = null;
		this.next = null;
	}
	public Node getPrev() // get node before
	{
		return this.prev;
	}
	public Node getNext() // get node after
	{
		return this.next;
	}
	public int getValue() // get value in pointed node
	{
		return this.value;
	}
	public void setPrev(Node x) // set the previous node
	{
		this.prev = x;
	}
	public void setNext(Node x) // set the next node
	{
		this.next = x;
	}
	public void setValue(int x) // set the value in pointer node
	{
		this.value = x;
	}
	public String toString() //for debugging
	{
		return "" + getValue(); // print the value of pointed node
	}
}
