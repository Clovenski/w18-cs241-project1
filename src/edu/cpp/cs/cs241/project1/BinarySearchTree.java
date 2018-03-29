package edu.cpp.cs.cs241.project1;

/*
 * This class is the implementation of the binary search tree data structure.
 * It implements basic properties of a binary search tree such as adding a new
 * value to the tree, removing a value, checking if the tree contains a certain
 * value, checking if the tree is empty or not, as well as providing the tree's
 * pre-order, in-order and post-order sequences.
 */
public class BinarySearchTree {
	// Field containing the root node of this tree.
	private TreeNode rootNode;
	
	// Default constructor, creating an empty binary search tree.
	public BinarySearchTree() {
		rootNode = null;
	}
	
	// Constructor that creates a new tree with a root node containing the given data.
	public BinarySearchTree(int rootData) {
		rootNode = new TreeNode(rootData);
	}
	
	/*
	 * Checks whether or not the tree contains a node containing the given searchValue.
	 * Returns true if the given search value does exist within this tree, false otherwise.
	 */
	public boolean contains(int searchValue) {
		boolean result = false;
		TreeNode target = rootNode;
		int comparisonValue;
		
		while(target != null) {
			comparisonValue = searchValue - target.getData();
			
			// If some data within the tree matches the search value.
			if(comparisonValue == 0) {
				result = true;
				break;
			}
			
			// Otherwise keep traversing the tree.
			if(comparisonValue > 0)
				target = target.getRightChild();
			else
				target = target.getLeftChild();
		}
		
		return result;
	}
	
	// Returns true if the tree is empty, false otherwise.
	public boolean isEmpty() {
		return rootNode == null;
	}
	
	/*
	 * Adds a new node to this tree with the given data. The given data can be
	 * any nonzero integer. A given value of zero will not affect the tree in any
	 * way.
	 */
	public void addNewNode(int nodeData) {
		// A value of zero cannot be added to the tree, as getNodePredecessor/Successor
		// would possibly malfunction.
		if(nodeData == 0)
			return;
		
		TreeNode newNode = new TreeNode(nodeData);
		
		rootNode = addNewNode(rootNode, newNode);
	}
	
	/*
	 * Recursive method for adding a new node to this tree.
	 * If a node containing the new node's data already exists within
	 * this tree, then the tree is left unaffected. This method returns
	 * a root node for the affected tree containing the new node, assuming
	 * the new value did not already exist in the tree.
	 * 
	 * The base cases for the recursion are:
	 * 		1. A node containing the same value as newNode is found.
	 * 		2. An empty spot in the tree for the newNode to occupy is found.
	 */
	private TreeNode addNewNode(TreeNode root, TreeNode newNode) {
		int comparisonValue;
		TreeNode rightChild;
		TreeNode leftChild;
		
		if(root != null) {
			comparisonValue = newNode.getData() - root.getData();
			
			// Data already exists in the tree, no duplicates allowed.
			if(comparisonValue == 0)
				return root;
			
			if(comparisonValue > 0) {
				rightChild = addNewNode(root.getRightChild(), newNode);
				root.setRightChild(rightChild);
			} else {
				leftChild = addNewNode(root.getLeftChild(), newNode);
				root.setLeftChild(leftChild);
			}
			
			return root;
		}
		
		// root == null, spot in the tree for newNode to occupy is found.
		return newNode;
	}
	
	/*
	 * Removes a node with the given nodeData from the tree, if it exists.
	 * An empty tree or a tree that does not contain the given nodeData is
	 * left unaffected.
	 */
	public void removeNode(int nodeData) {
		if(!isEmpty())
			rootNode = removeNode(rootNode, nodeData);
	}
	
	/*
	 * Recursive method for removing a value from this tree. If the given value, nodeData,
	 * is found within the tree, then it is properly removed from the tree, otherwise the tree
	 * is left unaffected. If the node to be removed is the root of the entire tree, then removeRootNode()
	 * is invoked.
	 * 
	 * The base cases for the recursion are:
	 * 		1. The target node to be removed is found.
	 * 		2. A null reference is encountered when traversing the tree.
	 */
	private TreeNode removeNode(TreeNode root, int nodeData) {
		int comparisonValue;
		
		if(root != null) {
			comparisonValue = nodeData - root.getData();
			
			// Target node found.
			if(comparisonValue == 0) {
				if(root == rootNode)
					return removeRootNode();
				else if(root.hasLeftChild() && root.hasRightChild()) {
					// Set the node's data to the same as rightmost child in its left subtree.
					root.setData(getRightMostChild(root.getLeftChild()).getData());
					// Set the node's left child to resulting subtree after removing its rightmost child.
					root.setLeftChild(removeRightMostChild(root.getLeftChild()));
					return root;
				} else if(root.hasLeftChild())
					return root.getLeftChild();
				else
					return root.getRightChild();
				
			// True if nodeData is greater than root, false if root is greater.
			} else if(comparisonValue > 0)
				root.setRightChild(removeNode(root.getRightChild(), nodeData));
			else
				root.setLeftChild(removeNode(root.getLeftChild(), nodeData));
		}
		
		// root == null, nodeData does not exist within the tree.
		return root;
	}
	
	/*
	 * This method is invoked when a node to be removed from this tree happens to be
	 * the root node. The root node of this tree is changed appropriately after the removal
	 * and then returned.
	 */
	private TreeNode removeRootNode() {
		if(rootNode.hasLeftChild() && rootNode.hasRightChild()) {
			// Set the root node's data to the same as rightmost child in its left subtree.
			rootNode.setData(getRightMostChild(rootNode.getLeftChild()).getData());
			// Set root node's left child to resulting subtree after removing its rightmost child.
			rootNode.setLeftChild(removeRightMostChild(rootNode.getLeftChild()));
		} else if(rootNode.hasLeftChild())
			rootNode = rootNode.getLeftChild();
		else
			rootNode = rootNode.getRightChild();
		
		return rootNode;
	}
	
	/*
	 * Removes the rightmost child of a tree that is rooted at the given root parameter.
	 * The root of the affected tree is then returned.
	 */
	private TreeNode removeRightMostChild(TreeNode root) {
		if(root.hasRightChild()) {
			root.setRightChild(removeRightMostChild(root.getRightChild()));
			return root;
		} else
			return root.getLeftChild();
	}
	
	/*
	 * Returns a reference to the leftmost child of a tree rooted at the given root
	 * parameter.
	 */
	private TreeNode getLeftMostChild(TreeNode root) {
		TreeNode target = root;
		
		while(target.hasLeftChild())
			target = target.getLeftChild();
		
		return target;
	}
	
	/*
	 * Returns a reference to the rightmost child of a tree rooted at the given
	 * root parameter.
	 */
	private TreeNode getRightMostChild(TreeNode root) {
		TreeNode target = root;
		
		while(target.hasRightChild())
			target = target.getRightChild();
		
		return target;
	}
	
	/*
	 * Returns a string containing the sequence of printed output when traversing this tree
	 * in a pre-order traversal. Each printed value is separated by a space. An empty tree
	 * will return an empty string.
	 */
	public String getPreOrderSequence() {
		if(isEmpty())
			return "";
		else
			return getPreOrderSequence(rootNode);
	}
	
	/*
	 * Builds the pre-order sequence string and then returns it.
	 */
	private String getPreOrderSequence(TreeNode root) {
		String result;
		
		result = Integer.toString(root.getData()) + " ";
		
		if(root.hasLeftChild())
			result = result + getPreOrderSequence(root.getLeftChild());
		
		if(root.hasRightChild())
			result = result + getPreOrderSequence(root.getRightChild());
		
		return result;
	}
	
	/*
	 * Returns a string containing the sequence of printed output when traversing this tree
	 * in an in-order traversal. Each printed value is separated by a space. An empty tree
	 * will return an empty string.
	 */
	public String getInOrderSequence() {
		if(isEmpty())
			return "";
		else
			return getInOrderSequence(rootNode);
	}
	
	/*
	 * Builds the in-order sequence string and then returns it.
	 */
	private String getInOrderSequence(TreeNode root) {
		String result = "";
		
		if(root.hasLeftChild())
			result = result + getInOrderSequence(root.getLeftChild());
		
		result = result + Integer.toString(root.getData()) + " ";
		
		if(root.hasRightChild())
			result = result + getInOrderSequence(root.getRightChild());
		
		return result;
	}
	
	/*
	 * Returns a string containing the sequence of printed output when traversing this tree
	 * in a post-order traversal. Each printed value is separated by a space. An empty tree
	 * will return an empty string.
	 */
	public String getPostOrderSequence() {
		if(isEmpty())
			return "";
		else
			return getPostOrderSequence(rootNode);
	}
	
	/*
	 * Builds the post-order sequence string and then returns it.
	 */
	private String getPostOrderSequence(TreeNode root) {
		String result = "";
		
		if(root.hasLeftChild())
			result = result + getPostOrderSequence(root.getLeftChild());
		
		if(root.hasRightChild())
			result = result + getPostOrderSequence(root.getRightChild());
		
		result = result + Integer.toString(root.getData()) + " ";
		
		return result;
	}
	
	/*
	 * Gets the data of a predecessor of a node within this tree that contains the given nodeData, if it exists.
	 * A predecessor of a node is defined as being the node that prints its data immediately before that node in
	 * an in-order traversal. An integer value of zero is returned if this tree is empty or nodeData does not exist
	 * in this tree, otherwise the predecessor's data is returned.
	 */
	public int getNodePredecessor(int nodeData) {
		TreeNode target;
		TreeNode predecessor = new TreeNode();
		int comparisonValue;
		
		if(isEmpty())
			return 0;
		
		target = rootNode;
		
		do {
			comparisonValue = nodeData - target.getData();
			
			if(comparisonValue == 0) {
				if(target.hasLeftChild())
					// Predecessor will be rightmost child in target's left subtree.
					predecessor = getRightMostChild(target.getLeftChild());
				return predecessor.getData();
			} else if(comparisonValue > 0) {
				// Possible predecessor if the node does exist in the tree and does not have a left child
				// and is the leftmost child in some other node's right subtree.
				predecessor = target;
				target = target.getRightChild();
			} else
				target = target.getLeftChild();
		} while(target != null);
		
		// The desired node does not exist in this tree.
		return 0;
	}
	
	/*
	 * Gets the data of a successor of a node within this tree that contains the given nodeData, if it exists.
	 * A successor of a node is defined as being the node that prints its data immediately after that node in
	 * an in-order traversal. An integer value of zero is returned if this tree is empty or nodeData does not
	 * exist in this tree, otherwise the successor's data is returned.
	 */
	public int getNodeSuccessor(int nodeData) {
		TreeNode target;
		TreeNode successor = new TreeNode();
		int comparisonValue;
		
		if(isEmpty())
			return 0;
		
		target = rootNode;
		
		do {
			comparisonValue = nodeData - target.getData();
			
			if(comparisonValue == 0) {
				if(target.hasRightChild())
					// Successor will be leftmost child in target's right subtree.
					successor = getLeftMostChild(target.getRightChild());
				return successor.getData();
			} else if(comparisonValue > 0)
				target = target.getRightChild();
			else {
				// Possible successor if the node does exist in the tree and does not have a right child
				// and is the rightmost child in some other node's left subtree.
				successor = target;
				target = target.getLeftChild();
			}
		} while(target != null);
		
		// The desired node does not exist in this tree.
		return 0;
	}
}
