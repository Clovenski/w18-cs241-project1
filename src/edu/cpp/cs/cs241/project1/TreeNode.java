package edu.cpp.cs.cs241.project1;

/*
 * This class represents a node that is implemented in a binary tree data structure.
 * Each node has a data value represented as an integer, as well as a left and right child
 * of type TreeNode to implement the binary tree structure.
 */
public class TreeNode {
	private int data;
	private TreeNode leftChild;
	private TreeNode rightChild;
	
	/*
	 * Default constructor that creates a new TreeNode object with a data value of zero
	 * and no left or right children.
	 */
	public TreeNode() {
		data = 0;
		leftChild = null;
		rightChild = null;
	}
	
	/*
	 * Constructor that creates a new TreeNode object with a data value according to the
	 * parameter given.
	 */
	public TreeNode(int data) {
		this.data = data;
		leftChild = null;
		rightChild = null;
	}
	
	/*
	 * Constructor that creates a new TreeNode object with a data value and left and right
	 * child according the parameters given.
	 */
	public TreeNode(int data, TreeNode leftChild, TreeNode rightChild) {
		this.data = data;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
	
	/*
	 * Sets the data of this TreeNode according to the parameter given.
	 */
	public void setData(int newData) {
		data = newData;
	}
	
	/*
	 * Sets the left child of this TreeNode according to the parameter given.
	 */
	public void setLeftChild(TreeNode newNode) {
		leftChild = newNode;
	}
	
	/*
	 * Sets the right child of this TreeNode according to the parameter given.
	 */
	public void setRightChild(TreeNode newNode) {
		rightChild = newNode;
	}
	
	/*
	 * Gets the data of this TreeNode in the form of an integer.
	 */
	public int getData() {
		return data;
	}
	
	/*
	 * Gets a reference to this TreeNode's left child.
	 */
	public TreeNode getLeftChild() {
		return leftChild;
	}
	
	/*
	 * Gets a reference to this TreeNode's right child.
	 */
	public TreeNode getRightChild() {
		return rightChild;
	}
	
	/*
	 * Returns true if this TreeNode has a left child, false otherwise.
	 */
	public boolean hasLeftChild() {
		if(leftChild != null)
			return true;
		else
			return false;
	}
	
	/*
	 * Returns true if this TreeNode has a right child, false otherwise.
	 */
	public boolean hasRightChild() {
		if(rightChild != null)
			return true;
		else
			return false;
	}
}
