public class TwoFourTree {
	private class TwoFourTreeItem {
		int values = 1; // Values is the number of values within the node Either 1, 2, or 3.
		int value1 = 0; // always exists.
		int value2 = 0; // exists iff the node is a 3-node or 4-node.
		int value3 = 0; // exists iff the node is a 4-node.
		boolean isLeaf = true;

		TwoFourTreeItem parent = null; // parent exists iff the node is not root.
		TwoFourTreeItem leftChild = null; // left and right child exist iff the note is a non-leaf.
		TwoFourTreeItem rightChild = null;
		TwoFourTreeItem centerChild = null; // center child exists iff the node is a non-leaf 3-node.
		TwoFourTreeItem centerLeftChild = null; // center-left and center-right children exist iff the node is a
												// non-leaf 4-node.
		TwoFourTreeItem centerRightChild = null;

		// # of values + 1 = Name of the number node

		public boolean isTwoNode() { // Two node: 1 value within the node
			if (values == 1)
				return true;
			return false;
		}

		public boolean isThreeNode() { // Three node: 2 values within the node
			if (values == 2)
				return true;
			return false;
		}

		public boolean isFourNode() { // Four node: 3 values within the node (Full)
			if (values == 3)
				return true;
			return false;
		}

		public boolean isRoot() { // Checks if the node is the root node
			if (parent == null)
				return true;
			return false;
		}

		public TwoFourTreeItem(int value1) { // Constructor for a two node. When 1 value is created on the spot.
			this.value1 = value1;
		}

		public TwoFourTreeItem(int value1, int value2) { // Constructor for a three node. When 2 value is created on the
															// spot.
			this.value1 = value1;
			this.value2 = value2;
			values = 2;
		}

		public TwoFourTreeItem(int value1, int value2, int value3) { // Constructor for a four node. When 1 value is
																		// created on the spot.
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
			values = 3;
		}

		private void printIndents(int indent) {
			for (int i = 0; i < indent; i++)
				System.out.printf("  ");
		}

		public void printInOrder(int indent) {
			if (!isLeaf)
				leftChild.printInOrder(indent + 1);
			printIndents(indent);
			System.out.printf("%d\n", value1);
			if (isThreeNode()) {
				if (!isLeaf)
					centerChild.printInOrder(indent + 1);
				printIndents(indent);
				System.out.printf("%d\n", value2);
			} else if (isFourNode()) {
				if (!isLeaf)
					centerLeftChild.printInOrder(indent + 1);
				printIndents(indent);
				System.out.printf("%d\n", value2);
				if (!isLeaf)
					centerRightChild.printInOrder(indent + 1);
				printIndents(indent);
				System.out.printf("%d\n", value3);
			}
			if (!isLeaf)
				rightChild.printInOrder(indent + 1);
		}

		public int valueIndex(int index) {
			if (index == 0)
				return value1;
			else if (index == 1)
				return value2;
			return value3;
		}

		public void setValueAt(int index, int val) {
			if (index == 0)
				value1 = val;
			else if (index == 1)
				value2 = val;
			else
				value3 = val;
		}

		public int removeRightmostValue() {
			if (values == 3) {
				int temp = value3;
				value3 = 0;
				values--;
				return temp;
			} else {
				int temp = value2;
				value2 = 0;
				values--;
				return temp;
			}
		}

		public int removeLeftmostValue() {
			int temp = value1;
			value1 = value2;
			value2 = value3;
			value3 = 0;
			values--;
			return temp;
		}

		public void removeValueAt(int index) {

			if (index == 0) {
				value1 = value2;
				value2 = value3;
				value3 = 0;
			} else if (index == 1) {
				value2 = value3;
				value3 = 0;
			} else {
				value3 = 0;
			}
			values--;

		}

		public int IndexLocation(int value) {
			if (value1 == value) {
				return 0;
			} else if (value2 == value) {
				return 1;
			} else
				return 2;
		}

		public int findMaxValue() {
			int temp = this.value1;
			if (this.value1 < this.value2) {
				temp = this.value2;
			}
			if (this.value2 < this.value3) {
				temp = this.value3;
			}
			return temp;
		}

		public int valueAt(int index) {
			if (index == 0)
				return value1;
			if (index == 1)
				return value2;
			return value3;
		}

		public int nextHigher() { // Do not call this on a non-two node
			int temp = this.parent.value1;

			if (this.value1 > this.parent.value1 && this.value1 < this.parent.value2) {
				temp = this.parent.value2;
			} else if (this.value1 > this.parent.value2 && this.value1 < this.parent.value3) {
				temp = this.parent.value3;
			}
			return temp;
		}

		public int nextLower() { // Do not call this on a non-two node
			int temp = this.parent.value1;

			if (this.parent.isThreeNode()) {
				if (this.value1 > this.parent.value2) {
					temp = this.parent.value2;
				} else if (this.value1 > this.parent.value1 && this.value1 < this.parent.value2) {
					temp = this.parent.value1;
				}
			} else if (this.parent.isFourNode()) {
				if (this.value1 > this.parent.value1 && this.value1 < this.parent.value2) {
					temp = this.parent.value1;
				} else if (this.value1 > this.parent.value2 && this.value1 < this.parent.value3) {
					temp = this.parent.value2;
				} else if (this.value1 > this.parent.value3) {
					temp = this.parent.value3;
				}
			} else if (this.parent.isTwoNode()) {
				return temp = this.parent.value1;
			}

			// if (this.value1 > this.parent.value1 && this.value1 < this.parent.value2) {
			// temp = this.parent.value1;
			// }else if (this.value1 > this.parent.value2 && this.value1 <
			// this.parent.value3) {
			// temp = this.parent.value2;
			// }else if(this.value1 < this.parent.value3) {
			// temp = this.parent.value3;
			// }

			return temp;
		}

		public int ParentType() {
			// Parent should either be a 3 or 4 node. If it is a 2 node we failed already.
			if (this.parent == null) {
				return 0;
			} else if (this.parent.isFourNode()) {
				return 4;
			} else if (this.parent.isThreeNode()) {
				return 3;
			} else
				return 2;
		}

		public int ChildPosition() {

			if (this.parent == null) {
				return 0;
			}
			// Parent Two node scenario
			if (this.parent.isTwoNode()) {
				if (this.parent.leftChild == this) {
					return 1;
				} else
					return 2;
			}
			// Parent Three node scenario
			else if (this.parent.isThreeNode()) {
				if (this.parent.leftChild == this) {
					return 1;
				} else if (this.parent.centerChild == this) {
					return 2;
				} else
					return 3;
			}
			// Parent Four node Scenario
			else {
				if (this.parent.leftChild == this) {
					return 1;
				} else if (this.parent.centerLeftChild == this) {
					return 2;
				} else if (this.parent.centerRightChild == this) {
					return 3;
				} else
					return 4;
			}
		}

		

	}

	TwoFourTreeItem root = null; // Root of the tree. If null, the tree is empty.

	

	

	public boolean addValue(int value) {

		// If the tree is empty. Instantiate it with a new value
		if (root == null) {
			root = new TwoFourTreeItem(value);
			return true;
		}

		/*
		 * Search for where the value needs to be added to. We do that with a walker
		 * pointer that starts from the root of the tree and goes accordingly depending
		 * on the value we are trying to insert. While doing so we check to find any
		 * four nodes and split them.
		 */

		TwoFourTreeItem walker = root;

		// =========Special Case 1:Walker starts at the first and (Only) node ever
		// created for the tree.======================================
		if (walker.isRoot() && walker.isLeaf) {
			// Scenario for a TwoNode
			if (walker.isTwoNode()) {
				// If value is less than nodes value 1. Switch old v1s place to v2 and give to
				// v1.
				if (value < walker.value1) {
					walker.value2 = walker.value1;
					walker.value1 = value;
					++walker.values;
				}
				// Place value in the second spot otherwise.
				else {
					walker.value2 = value;
					++walker.values;
				}
			} else if (walker.isThreeNode()) {

				if (value < walker.value1) {
					walker.value3 = walker.value2;
					walker.value2 = walker.value1;
					walker.value1 = value;
					++walker.values;
				}

				else if (value < walker.value2) {
					walker.value3 = walker.value2;
					walker.value2 = value;
					++walker.values;
				} else {
					walker.value3 = value;
					++walker.values;
				}
			}
			/*
			 * Split the values from each other into each of their own node(?) v2 is a
			 * parent of v1 and v3
			 */
			else if (walker.isFourNode()) {
				// There has to be a better way for doing this.
				TwoFourTreeItem RootParent = new TwoFourTreeItem(walker.value2);
				TwoFourTreeItem Left = new TwoFourTreeItem(walker.value1);
				TwoFourTreeItem Right = new TwoFourTreeItem(walker.value3);

				// Attach the new parent to its child
				RootParent.leftChild = Left;
				RootParent.rightChild = Right;
				// Vice versa
				Left.parent = RootParent;
				Right.parent = RootParent;

				// Root is no longer a leaf
				root = RootParent;
				walker = root;
				walker.isLeaf = false;

			}
			// return true;
		}

		// With the goal of inserting. Traversing always should go to the leaf
		while (walker.isLeaf == false) {

			// Two node Insert scenarios
			if (walker.isTwoNode()) {
				// Either you are greater or your less.
				if (value < walker.value1) {
					walker = walker.leftChild;
				} else {
					walker = walker.rightChild;
				}
			} else if (walker.isThreeNode()) {
				// Value is either your lesser, in between, or greater
				if (value < walker.value1) {
					walker = walker.leftChild;
				} else if (value > walker.value1 && value < walker.value2) {
					walker = walker.centerChild;
				} else {
					walker = walker.rightChild;
				}
			} else if (walker.isFourNode() && !walker.isRoot()) {
				// Since its at the leaf it has a parent we would promote the middle value to
				// the parent and go on from there.

				// Parent is a two node scenario.
				if (walker.parent.isTwoNode()) {
					// Either the value is smaller or bigger than the parents two node value
					if (walker.value2 < walker.parent.value1) {
						walker.parent.value2 = walker.parent.value1;
						walker.parent.value1 = walker.value2;
						walker.parent.values++;
					} else {
						walker.parent.value2 = walker.value2;
						walker.parent.values++;
					}
					/*
					 * The parent is now a three node. It now has a center child place. Break apart
					 * the old node values and assign them accordingly.
					 */
					TwoFourTreeItem temp;
					TwoFourTreeItem temp2;

					// If walkers value1 is greater than their parents value1. It means you're on
					// the right hand side of the parent.
					if (walker.value1 > walker.parent.value1) {
						temp = new TwoFourTreeItem(walker.value1);
						temp2 = new TwoFourTreeItem(walker.value3);

						walker.parent.centerChild = temp;
						walker.parent.rightChild = temp2;

						temp.isLeaf = false;
						temp2.isLeaf = false;

						// Assign the children to temp and assign walkers old children on each side to
						// new parents.
						temp.leftChild = walker.leftChild;
						temp.rightChild = walker.centerLeftChild;
						temp.parent = walker.parent;
						temp2.parent = walker.parent;

						walker.leftChild.parent = temp;
						walker.centerLeftChild.parent = temp;

						temp2.leftChild = walker.centerRightChild;
						temp2.rightChild = walker.rightChild;

						walker.rightChild.parent = temp2;
						walker.centerRightChild.parent = temp2;

						// Send walker back to the parent node that got a new value
						walker = walker.parent;

					}
					// If walkers value1 is lesser than their parents value1. It means you're on the
					// left side of the parent.
					else {

						temp = new TwoFourTreeItem(walker.value3);
						temp2 = new TwoFourTreeItem(walker.value1);

						walker.parent.centerChild = temp;
						walker.parent.leftChild = temp2;

						temp.isLeaf = false;
						temp2.isLeaf = false;

						temp.parent = walker.parent;
						temp2.parent = walker.parent;

						// Assign children to temp2
						temp2.leftChild = walker.leftChild;
						temp2.rightChild = walker.centerLeftChild;
						if (walker.leftChild != null)
							walker.leftChild.parent = temp2;
						if (walker.centerLeftChild != null)
							walker.centerLeftChild.parent = temp2;

						// Assign children to temp
						temp.leftChild = walker.centerRightChild;
						temp.rightChild = walker.rightChild;
						if (walker.centerRightChild != null)
							walker.centerRightChild.parent = temp;
						if (walker.rightChild != null)
							walker.rightChild.parent = temp;

						// Send walker back to the parent node
						walker = walker.parent;

					}
				}
				// Parent is a three node scenario.
				else if (walker.parent.isThreeNode()) {

					TwoFourTreeItem temp = new TwoFourTreeItem(walker.value1);
					TwoFourTreeItem temp2 = new TwoFourTreeItem(walker.value3);

					// If value 2 of walker is less than walker parents value 1. Promotion came from
					// the left
					if (walker.value2 < walker.parent.value1) {
						walker.parent.value3 = walker.parent.value2;
						walker.parent.value2 = walker.parent.value1;
						walker.parent.value1 = walker.value2;
						walker.parent.values++;

						// Parent has become a four node. Split apart the nodes appropriately.
						temp.parent = walker.parent;
						temp.leftChild = walker.leftChild;
						temp.rightChild = walker.centerLeftChild;

						temp2.parent = walker.parent;
						temp2.leftChild = walker.centerRightChild;
						temp2.rightChild = walker.rightChild;

						walker.parent.leftChild = temp;
						walker.parent.centerLeftChild = temp2;

						// Reassign walkers children to new parents
						walker.leftChild.parent = temp;
						walker.centerLeftChild.parent = temp;
						walker.centerRightChild.parent = temp2;
						walker.rightChild.parent = temp2;

						temp.isLeaf = false;
						temp2.isLeaf = false;

						// If the original center existed it goes left to center left.
						walker.parent.centerRightChild = walker.parent.centerChild;
						walker.parent.centerChild = null;

						// Send the node back to the walkers parent. And send it wherever it needs to be
						// from there.
						walker = walker.parent;

						if (value < walker.value1) {
							walker = walker.leftChild;
						} else if (value > walker.value1 && value < walker.value2) {
							walker = walker.centerLeftChild;
						} else if (value > walker.value2 && value < walker.value3) {
							walker = walker.centerRightChild;
						} else {
							walker = walker.rightChild;
						}

					}
					// If value 2 of walker is bigger than parents value 1 but smaller than 3.
					// Promotion came from the middle.
					// BUGGED no longer
					else if (walker.value2 > walker.parent.value1 && walker.value2 < walker.parent.value2) {
						walker.parent.value3 = walker.parent.value2;
						walker.parent.value2 = walker.value2;
						walker.parent.values++;

						// Parent has become a four node. Split apart the nodes appropriately. and
						// Assign them correctly
						temp.parent = walker.parent;
						temp.leftChild = walker.leftChild;
						temp.rightChild = walker.centerLeftChild;

						temp2.parent = walker.parent;
						temp2.leftChild = walker.centerRightChild;
						temp2.rightChild = walker.rightChild;

						// Reassign walkers children to new parents
						walker.leftChild.parent = temp;
						walker.centerLeftChild.parent = temp;
						walker.centerRightChild.parent = temp2;
						walker.rightChild.parent = temp2;

						// The center spliced in half to each of its side.
						walker.parent.centerLeftChild = temp;
						walker.parent.centerRightChild = temp2;

						temp.isLeaf = false;
						temp2.isLeaf = false;

						// Send the node back to the walkers parent. And send it wherever it needs to be
						// from there.
						walker = walker.parent;
						walker.centerChild = null;

						if (value < walker.value1) {
							walker = walker.leftChild;
						} else if (value > walker.value1 && value < walker.value2) {
							walker = walker.centerLeftChild;
						} else if (value > walker.value2 && value < walker.value3) {
							walker = walker.centerRightChild;
						} else {
							walker = walker.rightChild;
						}

					}
					// If value 2 of walker is bigger than parents value 2 then Promotion came from
					// the right.
					else {
						walker.parent.value3 = walker.value2;
						walker.parent.values++;

						// Parent has become a four node. Split apart the nodes appropriately. and
						// Assign them correctly
						temp.parent = walker.parent;
						temp.leftChild = walker.leftChild;
						temp.rightChild = walker.centerLeftChild;
						temp2.parent = walker.parent;
						temp2.leftChild = walker.centerRightChild;
						temp2.rightChild = walker.rightChild;

						walker.parent.centerRightChild = temp;
						walker.parent.rightChild = temp2;

						// Reassign walkers children to new parents.
						walker.leftChild.parent = temp;
						walker.centerLeftChild.parent = temp;
						walker.centerRightChild.parent = temp2;
						walker.rightChild.parent = temp2;

						temp.isLeaf = false;
						temp2.isLeaf = false;

						// MIGHT HAVE A LOGIC ERROR FOR WHEN WE DO OTHER NUMBERS IN DESCENDING ORDER
						// If the original center existed it goes left to center left.
						walker.parent.centerLeftChild = walker.parent.centerChild;
						walker.parent.centerChild = null;

						// Send the node back to the walkers parent. And send it wherever it needs to be
						// from there.
						walker = walker.parent;

						if (value < walker.value1) {
							walker = walker.leftChild;
						} else if (value > walker.value1 && value < walker.value2) {
							walker = walker.centerLeftChild;
						} else if (value > walker.value2 && value < walker.value3) {
							walker = walker.centerRightChild;
						} else {
							walker = walker.rightChild;
						}

					}

				}

			}

			// Walker is a Four node but our last logic didn't account for if it had a
			// parent in the loop. Ugly solution.
			else if (walker.isFourNode() && walker.isRoot()) {
				// There has to be a better way for doing this.
				TwoFourTreeItem RootParent = new TwoFourTreeItem(walker.value2);
				TwoFourTreeItem Left = new TwoFourTreeItem(walker.value1);
				TwoFourTreeItem Right = new TwoFourTreeItem(walker.value3);

				// Attach the new parent to its child
				RootParent.leftChild = Left;
				RootParent.rightChild = Right;
				// Vice versa
				Left.parent = RootParent;
				Right.parent = RootParent;
				Left.leftChild = walker.leftChild;
				Left.rightChild = walker.centerLeftChild;
				Right.rightChild = walker.rightChild;
				Right.leftChild = walker.centerRightChild;
				walker.leftChild.parent = Left;
				walker.centerLeftChild.parent = Left;
				walker.centerRightChild.parent = Right;
				walker.rightChild.parent = Right;

				// Root is no longer a leaf but also because of the condition set the newly
				// children leaves to false.
				root = RootParent;
				walker = root;
				walker.isLeaf = false;
				Left.isLeaf = false;
				Right.isLeaf = false;

				// Our root node in this logic had children, assign them correctly.

			}

			if (walker.isLeaf) {
				if (walker.isTwoNode()) {
					// If value is less than nodes value 1. Switch old v1s place to v2 and give to
					// v1.
					if (value < walker.value1) {
						walker.value2 = walker.value1;
						walker.value1 = value;
						++walker.values;
					}
					// Place value in the second spot otherwise.
					else {
						walker.value2 = value;
						++walker.values;
					}
				} else if (walker.isThreeNode()) {
					if (value < walker.value1) {
						walker.value3 = walker.value2;
						walker.value2 = walker.value1;
						walker.value1 = value;
						walker.values++;
					} else if (value > walker.value1 && value < walker.value2) {
						walker.value3 = walker.value2;
						walker.value2 = value;
						walker.values++;
					} else {
						walker.value3 = value;
						walker.values++;
					}
				} else if (walker.isFourNode()) {
					TwoFourTreeItem temp;
					TwoFourTreeItem temp2;
					// Parent Two Node Scenarios
					if (walker.parent.isTwoNode()) {
						// Determine if walker value1 is less than or more than there parent.
						if (walker.value1 > walker.parent.value1) {
							// The lesser of the node goes as the center node
							// While the greater stays as the right.
							walker.parent.value2 = walker.value2;
							walker.parent.values++;
							temp = new TwoFourTreeItem(walker.value1);
							temp2 = new TwoFourTreeItem(walker.value3);
							walker.parent.centerChild = temp;
							walker.parent.rightChild = temp2;
							temp.parent = walker.parent;
							temp2.parent = walker.parent;
							walker = walker.parent;

						} else {
							// The greater of the node goes as the center node
							// While the lesser stays as the left
							walker.parent.value2 = walker.parent.value1;
							walker.parent.value1 = walker.value2;
							walker.parent.values++;
							temp = new TwoFourTreeItem(walker.value1);
							temp2 = new TwoFourTreeItem(walker.value3);
							walker.parent.centerChild = temp2;
							walker.parent.leftChild = temp;
							temp.parent = walker.parent;
							temp2.parent = walker.parent;
							walker = walker.parent;
						}

						// End of two node scenario
					}
					// Start of three node scenarios
					else if (walker.parent.isThreeNode()) {
						// Promote walkers value 2.
						// If the promotion came from the right child.
						if (walker.value2 > walker.parent.value2) {
							// Update the status of the nodes
							walker.parent.value3 = walker.value2;
							walker.parent.values++;
							// Break the old node apart
							temp = new TwoFourTreeItem(walker.value1);
							temp2 = new TwoFourTreeItem(walker.value3);
							// Its parent is now a four node. Assign Correctly
							temp.parent = walker.parent;
							temp2.parent = walker.parent;
							// Promotion came from the right. The lesser in the node goes to the center
							// right
							// Meanwhile the greater goes stays on the right hand side in a brand new node.
							walker.parent.centerRightChild = temp;
							walker.parent.rightChild = temp2;
							walker.parent.centerLeftChild = walker.parent.centerChild;

							walker = walker.parent;

							// The center no longer exists
							walker.centerChild = null;

							// Send the node where ever it needs to be.

							// Send the node to the left
							if (value < walker.value1) {
								walker = walker.leftChild;
							}
							// Send the node to the center left
							else if (value > walker.value1 && value < walker.value2) {
								walker = walker.centerLeftChild;
							}
							// Send the node to the center right
							else if (value > walker.value2 && value < walker.value3) {
								walker = walker.centerRightChild;
							}
							// Send the node right
							else {
								walker = walker.rightChild;
							}
							if (walker.isLeaf) {
								if (walker.isTwoNode()) {
									// If value is less than nodes value 1. Switch old v1s place to v2 and give to
									// v1.
									if (value < walker.value1) {
										walker.value2 = walker.value1;
										walker.value1 = value;
										++walker.values;
									}
									// Place value in the second spot otherwise.
									else {
										walker.value2 = value;
										++walker.values;
									}
								}
								return true;
							}

						}
						// If the promotion came from the middle child.
						else if (walker.value2 < walker.parent.value2 && walker.value2 > walker.parent.value1) {
							walker.parent.value3 = walker.parent.value2;
							walker.parent.value2 = walker.value2;
							walker.parent.values++;
							//
							// Break the old node apart
							temp = new TwoFourTreeItem(walker.value1);
							temp2 = new TwoFourTreeItem(walker.value3);
							// Its parent is now a four node. Assign Correctly
							temp.parent = walker.parent;
							temp2.parent = walker.parent;
							// Promotion came from the middle. The lesser in the node goes to center left
							// vice versa
							// for the greater in the node.
							walker.parent.centerLeftChild = temp;
							walker.parent.centerRightChild = temp2;
							// Update the status of the nodes
							walker = walker.parent;
							// The center no longer exists
							walker.centerChild = null;

						}
						// If the promotion came from the left child.
						else {
							walker.parent.value3 = walker.parent.value2;
							walker.parent.value2 = walker.parent.value1;
							walker.parent.value1 = walker.value2;
							walker.parent.values++;
							// Break the old node apart
							temp = new TwoFourTreeItem(walker.value1);
							temp2 = new TwoFourTreeItem(walker.value3);
							// Its parent is now a four node. Assign Correctly
							temp.parent = walker.parent;
							temp2.parent = walker.parent;

							walker.parent.leftChild = temp;
							walker.parent.centerLeftChild = temp2;
							walker.parent.centerRightChild = walker.parent.centerChild;

							// Update the status of the nodes
							walker = walker.parent;
							walker.centerChild = null;

							// Send the node to the left
							if (value < walker.value1) {
								walker = walker.leftChild;
							}
							// Send the node to the center left
							else if (value > walker.value1 && value < walker.value2) {
								walker = walker.centerLeftChild;
							}
							// Send the node to the center right
							else if (value > walker.value2 && value < walker.value3) {
								walker = walker.centerRightChild;
							}
							// Send the node right
							else {
								walker = walker.rightChild;
							}
							if (walker.isLeaf) {
								if (walker.isTwoNode()) {
									// If value is less than nodes value 1. Switch old v1s place to v2 and give to
									// v1.
									if (value < walker.value1) {
										walker.value2 = walker.value1;
										walker.value1 = value;
										++walker.values;
									}
									// Place value in the second spot otherwise.
									else {
										walker.value2 = value;
										++walker.values;
									}
									return true;
								} else if (walker.isThreeNode()) {
									if (value < walker.value1) {
										walker.value3 = walker.value2;
										walker.value2 = walker.value1;
										walker.value1 = value;
									} else if (value > walker.value1 && value < walker.value2) {
										walker.value3 = walker.value2;
										walker.value2 = value;
									} else {
										walker.value3 = value;
									}
									return true;
								}
							}
						}

					}
				}
			}

		}

		return false;
	}

	// Searching for a value in the tree
	public boolean hasValue(int value) {
		int i = 0;
		TwoFourTreeItem walker = root;

		// Root case
		if (NodeSearcher(walker, value)) {
			return true;
		}
		
		while (!walker.isLeaf) {
			i++;
			// Two Node Scenario
			if (walker.isTwoNode()) {
				if (value < walker.value1) {
					walker = walker.leftChild;
					if (NodeSearcher(walker, value)) {
						return true;
					}
				} else {
					walker = walker.rightChild;
					if (NodeSearcher(walker, value)) {
						return true;
					}
				}
			}

			// Three node scenario
			else if (walker.isThreeNode()) {
				if (value < walker.value1) {
					walker = walker.leftChild;
					if (NodeSearcher(walker, value)) {
						return true;
					}
				} else if (value > walker.value1 && value < walker.value2) {
					walker = walker.centerChild;
					if (NodeSearcher(walker, value)) {
						return true;
					}
				} else {
					walker = walker.rightChild;
					if (NodeSearcher(walker, value)) {
						return true;
					}
				}
			} else if (walker.isFourNode()) {
				if (value < walker.value1) {
					walker = walker.leftChild;
					if (NodeSearcher(walker, value)) {
						return true;
					}
				} else if (value > walker.value1 && value < walker.value2) {
					walker = walker.centerLeftChild;
					if (NodeSearcher(walker, value)) {
						return true;
					}
				} else if (value > walker.value2 && value < walker.value3) {
					walker = walker.centerRightChild;
					if (NodeSearcher(walker, value)) {
						return true;
					}
				} else {
					walker = walker.rightChild;
					if (NodeSearcher(walker, value)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	// Deleting a value from the tree
	// public boolean deleteValue(int value) {
	// TwoFourTreeItem walker = root;
	//
	// //Root case
	// if (walker.isRoot()) {
	// if (walker.isTwoNode()) {
	// if (walker.leftChild.isTwoNode() && walker.rightChild.isTwoNode()) {
	// walker = Merge(walker.leftChild, walker, walker.rightChild);
	// }
	// else {
	// walker = NonLoopTraversal(walker, value);
	// }
	// }
	// }
	//
	//
	//
	// // Traverse to the leaf. Perform Merge or Rotate on every two node. While
	// searching for the value.
	// while (!NodeSearcher(walker, value) && walker != null) {
	//
	// /*
	// * If the value is found leave the loop until then.
	// * Perform or Rotate on every two node.
	// */
	// if (walker.isTwoNode()) {
	// // Check for the position where the child is in and type of parent.
	// int position = walker.ChildPosition();
	// int ParentType = walker.ParentType();
	//
	// // Either Merge or Rotate
	// NodeMergeRotate(position, ParentType, walker);
	// }
	// //Send walker along the way
	// walker = NonLoopTraversal(walker, value);
	//
	// }
	//
	// //Un-elegant solution
	//
	// if (walker.isTwoNode()) {
	// int position = walker.ChildPosition();
	// int ParentType = walker.ParentType();
	// walker = NodeMergeRotate(position, ParentType, walker);
	// }
	//
	// /*
	// * When the node is found
	// * Either we delete from internal or delete from leaf.
	// */
	// if (NodeSearcher(walker, value) && walker != null) {
	// if (!walker.isLeaf) {
	// DeleteFromInternal(walker, value);
	//
	// if (walker.isThreeNode()) {
	// if (walker.centerChild == null) {
	//
	// }
	// }
	// }
	// }
	//
	//
	//
	// return false;
	// }

	public boolean deleteValue(int value) {

		TwoFourTreeItem walker = root;
		TwoFourTreeItem next = walker; // Will tell us what the next node is to pre-emptevely merge or rotate

		while (walker != null && !NodeSearcher(walker, value) && !walker.isLeaf) {
			next = NonLoopTraversal(walker, value);
			if (next == null) {
				next = walker;
			}
			
			if (next.isTwoNode()) {
				int childPosition = next.ChildPosition();
				int ParentType = next.ParentType();
				next = NodeMergeRotate(childPosition, ParentType, next);
			}

			walker = next;
			if (walker.parent.values == 0) {
				root = walker;
			}
			
		}

		if (NodeSearcher(walker, value)) {
			deleteInternal(walker, value);
			//printInOrder();
			//System.out.printf("\n");
			return true;
		}

		return false;

	}

	private void deleteInternal(TwoFourTree.TwoFourTreeItem node, int value) {

		TwoFourTreeItem hold = node;   // Holds the node where the value is. Would need to be updated 
		TwoFourTreeItem walker = hold; // Walks through the tree
		TwoFourTreeItem next = walker; // Scouts the tree for scenarios


		int indexPosition = hold.IndexLocation(value);
		int d = 0;
		while (!walker.isLeaf) {
			d++;
			indexPosition = walker.IndexLocation(value);
			
			// Four node Scenarios
			if (indexPosition == 0 && walker.isFourNode()) {
				if (!walker.leftChild.isTwoNode()) {
					walker = FindMaxNode(walker.leftChild);
					int MaxValue = walker.findMaxValue();
					
					hold.setValueAt(indexPosition, MaxValue);
					walker.removeRightmostValue();
				} else if (!walker.centerLeftChild.isTwoNode()) {
					walker = FindMinNode(walker.centerLeftChild);
					int MinValue = walker.value1;
					
					hold.setValueAt(indexPosition, MinValue);
					walker.removeValueAt(walker.IndexLocation(MinValue));
				} else {
					TwoFourTreeItem Merge = Merge(walker.leftChild, walker, walker.centerLeftChild);
					if(Merge.parent.values == 0) {
						Merge.parent = null;
					}
					walker = Merge;
					hold = walker;
					
				}
			} else if (indexPosition == 1 && walker.isFourNode()) {
				if (!walker.centerLeftChild.isTwoNode()) {
					walker = FindMaxNode(walker.centerLeftChild);
					int MaxValue = walker.findMaxValue();
					
					hold.setValueAt(indexPosition, MaxValue); // Set it at the new old
					walker.removeRightmostValue(); //Remove it from the old node
				} else if (!walker.centerRightChild.isTwoNode()) {
					walker = FindMinNode(walker.centerRightChild);
					//Insert steps here
					int MinValue = walker.value1;
					hold.setValueAt(indexPosition, MinValue);
					walker.removeLeftmostValue();
					
					
				} else {
					TwoFourTreeItem Merge = Merge(walker.centerLeftChild, walker, walker.centerRightChild);
					if(Merge.parent.values == 0) {
						Merge.parent = null;
					}
					walker = Merge;
					hold = walker;
				}

			} else if (indexPosition == 2 && walker.isFourNode()) {
				if (!walker.centerRightChild.isTwoNode()) {
					walker = FindMaxNode(walker.centerRightChild);
					int MaxValue = walker.findMaxValue();
					
					hold.setValueAt(indexPosition, MaxValue); // Set it at the new old
					walker.removeRightmostValue(); //Remove it from the old node
					
					
				} else if (!walker.rightChild.isTwoNode()) {
					walker = FindMinNode(walker.rightChild);
					//Insert steps here
					int MinValue = walker.value1;
					hold.setValueAt(indexPosition, MinValue);
					walker.removeLeftmostValue();
				} else {
					TwoFourTreeItem Merge = Merge(walker.centerRightChild, walker, walker.rightChild);
					if(Merge.parent.values == 0) {
						Merge.parent = null;
					}
					walker = Merge;
					hold = walker;
				}
			// Three Node Scenarios
			} else if (indexPosition == 0 && walker.isThreeNode()) {
				if (!walker.leftChild.isTwoNode()) {
					walker = FindMaxNode(walker.leftChild); //Find the node with the max value on this while performing NodeMerge
					int MaxValue = walker.findMaxValue(); // Grab the max value
					
					hold.setValueAt(indexPosition, MaxValue); // Set it at the new old
					walker.removeValueAt(walker.IndexLocation(MaxValue)); //Remove it from the old node
				} else if (!walker.centerChild.isTwoNode()) {
					walker = FindMinNode(walker.centerChild);
					int MinValue = walker.value1;
					hold.setValueAt(indexPosition, MinValue);
					walker.removeValueAt(walker.IndexLocation(MinValue));
				} else {
					TwoFourTreeItem Merge = Merge(walker.leftChild, walker, walker.centerChild);
					if(Merge.parent.values == 0) {
						Merge.parent = null;
					}
					walker = Merge;
					hold = walker;
				}

			} else if (indexPosition == 1 && walker.isThreeNode()) {
				if (!walker.centerChild.isTwoNode()) {
					walker = FindMaxNode(walker.centerChild);
					int MaxValue = walker.findMaxValue();
					
					hold.setValueAt(indexPosition, MaxValue);
					walker.removeRightmostValue();
					
				} else if (!walker.rightChild.isTwoNode()) {
					walker = FindMinNode(walker.rightChild);
					int MinValue = walker.value1;
					
					hold.setValueAt(indexPosition, MinValue);
					walker.removeLeftmostValue();
				} else {
					TwoFourTreeItem Merge = Merge(walker.centerChild, walker, walker.rightChild);
					if(Merge.parent.values == 0) {
						Merge.parent = null;
					}
					walker = Merge;
					hold = walker;
				}
			// Two Node Scenarios
			} else {
				if (!walker.leftChild.isTwoNode()) {
					walker = FindMaxNode(walker.leftChild);
					int MaxValue = walker.findMaxValue();
					hold.setValueAt(indexPosition, MaxValue);
					walker.removeRightmostValue();

				} else if (!walker.rightChild.isTwoNode()) {
					walker = FindMinNode(walker.rightChild);
					int MinValue = walker.valueAt(0);
					hold.setValueAt(indexPosition, MinValue);
					walker.removeLeftmostValue();
				} else {
					TwoFourTreeItem Merge = Merge(walker.leftChild, walker, walker.rightChild);
					if(Merge.parent.values == 0) {
						Merge.parent = null;
					}
					root = Merge;
					walker = Merge;
					hold = walker;
				}
			}
			d++;
		}
		if (walker.isLeaf) {
			if (NodeSearcher(walker, value)) {
				walker.removeValueAt(walker.IndexLocation(value));
			}
		}

	}

	private TwoFourTreeItem FindMinNode(TwoFourTreeItem node) {
		TwoFourTreeItem walker = node;
		TwoFourTreeItem nextNode = walker;

		while (nextNode.leftChild != null) {

			nextNode = nextNode.leftChild;

			if (nextNode.isTwoNode()) {
				int childPosition = nextNode.ChildPosition();
				int ParentType = nextNode.ParentType();
				nextNode = NodeMergeRotate(childPosition, ParentType, nextNode);
			}

			walker = nextNode;
		}

		return walker;
	}

	private TwoFourTreeItem FindMaxNode(TwoFourTreeItem node) {

		TwoFourTreeItem walker = node;
		TwoFourTreeItem nextNode = walker;

		while (nextNode.rightChild != null) {

			nextNode = nextNode.rightChild;

			if (nextNode.isTwoNode()) {
				int childPosition = nextNode.ChildPosition();
				int ParentType = nextNode.ParentType();
				nextNode = NodeMergeRotate(childPosition, ParentType, nextNode);
			}

			walker = nextNode;
		}

		return walker;
	}

	private TwoFourTree.TwoFourTreeItem NodeMergeRotate(int position, int parentType,
			TwoFourTree.TwoFourTreeItem node) {
		// Parent is a three node scenario
		if (parentType == 3) {
			if (position == 1) {

				if (node.parent.centerChild.isTwoNode()) {
					return Merge(node, node.parent, node.parent.centerChild);
				} else {
					RotateLeft(node, node.parent.centerChild);
				}

			}

			else if (position == 2) {

				if (node.parent.rightChild.isTwoNode()) {
					return Merge(node, node.parent, node.parent.rightChild);
				} else {
					RotateLeft(node, node.parent.rightChild);
				}

			}

			else if (position == 3) {

				if (node.parent.centerChild.isTwoNode()) {
					return Merge(node.parent.centerChild, node.parent, node);
				} else {
					RotateRight(node, node.parent.centerChild);
				}

			}

		}
		// Parent is a four node scenario
		else if (parentType == 4) {

			// Walker is in the 1st position of the child for parent.
			if (position == 1) {

				if (node.parent.centerLeftChild.isTwoNode()) {
					return Merge(node, node.parent, node.parent.centerLeftChild);
				} else {
					RotateLeft(node, node.parent.centerLeftChild);
				}

			} else if (position == 2) {

				if (node.parent.centerRightChild.isTwoNode()) {
					return Merge(node, node.parent, node.parent.centerRightChild);
				} else {
					RotateLeft(node, node.parent.centerRightChild);
				}
			} else if (position == 3) {

				if (node.parent.rightChild.isTwoNode()) {
					return Merge(node, node.parent, node.parent.rightChild);
				} else {
					RotateLeft(node, node.parent.rightChild);
				}
			} else if (position == 4) {
				if (node.parent.centerRightChild.isTwoNode()) {
					return Merge(node.parent.centerRightChild, node.parent, node); // This logic will be twisted to fit
																					// the need
				} else {
					RotateRight(node, node.parent.centerRightChild);
				}
			}
		} else {
			if (position == 1) {
				if (node.parent.leftChild.isTwoNode()) {
					return Merge(node, node.parent, node.parent.rightChild);
				} else {
					RotateLeft(node, node.parent.rightChild);
				}
			} else {
				if (node.parent.leftChild.isTwoNode()) {
					return Merge(node.parent.leftChild, node.parent, node);
				} else {
					RotateRight(node, node.parent.leftChild);
				}
			}
		}
		return node;
	}

	private TwoFourTree.TwoFourTreeItem Merge(TwoFourTreeItem left, TwoFourTreeItem center, TwoFourTreeItem right) {

		int position = left.ChildPosition();

		
		if (position == 1) {
			// Merge the nodes together
			int centerPiece = left.nextHigher();
			TwoFourTreeItem Merge = new TwoFourTreeItem(left.value1, centerPiece, right.value1);

			// Grab the index of the center value from parent
			int valueIndex = center.IndexLocation(left.nextHigher());
			center.removeValueAt(valueIndex);

			// Re-attach children's statuses
			Merge.parent = center;

			if (!left.isLeaf && !right.isLeaf) {
				Merge.leftChild = left.leftChild;
				Merge.centerLeftChild = left.rightChild;
				Merge.centerRightChild = right.leftChild;
				Merge.rightChild = right.rightChild;

				left.leftChild.parent = Merge;
				left.rightChild.parent = Merge;
				right.leftChild.parent = Merge;
				right.rightChild.parent = Merge;

				Merge.isLeaf = false;
			}
			// Parent went from a 4 node to a 3 node
			if (Merge.parent.isThreeNode()) {
				if (position == 3) {
					center.rightChild = Merge;
					center.centerChild = center.centerLeftChild;
					center.centerLeftChild = null;
					center.centerRightChild = null;
				} else if (position == 2) {
					center.centerChild = Merge;
					center.centerLeftChild = null;
					center.centerRightChild = null;
				} else {
					center.leftChild = Merge;
					center.centerChild = center.centerRightChild;
					center.centerRightChild = null;
					center.centerLeftChild = null;
				}
			}
			// Parent went from a 3 node to a 2 node
			else if (Merge.parent.isTwoNode()) {
				if (position == 2) {
					center.centerChild = null;
					center.rightChild = Merge;
				}
				if (position == 1) {
					center.centerChild = null;
					center.leftChild = Merge;
				}
			}
			

			return Merge;
		} else if (position == 2) {
			// Merge the nodes together
			int centerPiece = left.nextHigher();
			TwoFourTreeItem Merge = new TwoFourTreeItem(left.value1, centerPiece, right.value1);

			// Grab the index of the center value from parent
			int valueIndex = center.IndexLocation(centerPiece);
			center.removeValueAt(valueIndex);

			// Re-attach children's statuses
			Merge.parent = center;

			if (!left.isLeaf && !right.isLeaf) {
				Merge.leftChild = left.leftChild;
				Merge.centerLeftChild = left.rightChild;
				Merge.centerRightChild = right.leftChild;
				Merge.rightChild = right.rightChild;

				left.leftChild.parent = Merge;
				left.rightChild.parent = Merge;
				right.leftChild.parent = Merge;
				right.rightChild.parent = Merge;

				Merge.isLeaf = false;
			}
			// Parent went from a 4 node to a 3 node
			if (Merge.parent.isThreeNode()) {
				if (position == 3) {
					center.rightChild = Merge;
					center.centerChild = center.centerLeftChild;
					center.centerLeftChild = null;
					center.centerRightChild = null;
				} else if (position == 2) {
					center.centerChild = Merge;
					center.centerLeftChild = null;
					center.centerRightChild = null;
				} else {
					center.leftChild = Merge;
					center.centerChild = center.centerRightChild;
					center.centerRightChild = null;
					center.centerLeftChild = null;
				}
			}
			// Parent went from a 3 node to a 2 node
			else if (Merge.parent.isTwoNode()) {
				if (position == 2) {
					center.centerChild = null;
					center.rightChild = Merge;
				}
				if (position == 1) {
					center.centerChild = null;
					center.leftChild = Merge;
				}
			}

			return Merge;
		} else if (position == 3) {
			// Merge the nodes together
			int centerPiece = left.nextHigher();
			TwoFourTreeItem Merge = new TwoFourTreeItem(left.value1, centerPiece, right.value1);

			// Grab the index of the center value from parent
			int valueIndex = center.IndexLocation(left.nextHigher());
			center.removeValueAt(valueIndex);

			// Re-attach children's statuses
			Merge.parent = center;

			if (!left.isLeaf && !right.isLeaf) {
				Merge.leftChild = left.leftChild;
				Merge.centerLeftChild = left.rightChild;
				Merge.centerRightChild = right.leftChild;
				Merge.rightChild = right.rightChild;

				left.leftChild.parent = Merge;
				left.rightChild.parent = Merge;
				right.leftChild.parent = Merge;
				right.rightChild.parent = Merge;

				Merge.isLeaf = false;
			}
			// Parent went from a 4 node to a 3 node
			if (Merge.parent.isThreeNode()) {
				if (position == 3) {
					center.rightChild = Merge;
					center.centerChild = center.centerLeftChild;
					center.centerLeftChild = null;
					center.centerRightChild = null;
				} else if (position == 2) {
					center.centerChild = Merge;
					center.centerLeftChild = null;
					center.centerRightChild = null;
				} else {
					center.leftChild = Merge;
					center.centerChild = center.centerRightChild;
					center.centerRightChild = null;
					center.centerLeftChild = null;
				}
			}
			// Parent went from a 3 node to a 2 node
			else if (Merge.parent.isTwoNode()) {
				if (position == 2) {
					center.centerChild = null;
					center.rightChild = Merge;
				}
				if (position == 1) {
					center.centerChild = null;
					center.leftChild = Merge;
				}
			}

			return Merge;
		} else {
			// Merge the nodes together
			int centerPiece = right.nextLower();
			TwoFourTreeItem Merge = new TwoFourTreeItem(left.value1, centerPiece, right.value1);

			// Grab the index of the center value from parent
			int valueIndex = center.IndexLocation(centerPiece);
			center.removeValueAt(valueIndex);

			// Re-attach children's statuses
			if (!left.isLeaf && !right.isLeaf) {
				Merge.leftChild = left.leftChild;
				Merge.centerLeftChild = left.rightChild;
				Merge.centerRightChild = right.leftChild;
				Merge.rightChild = right.rightChild;

				left.leftChild.parent = Merge;
				left.rightChild.parent = Merge;
				right.leftChild.parent = Merge;
				right.rightChild.parent = Merge;

				Merge.isLeaf = false;
			}

			center.rightChild = Merge;

			return Merge;
		}

	}

	private void RotateLeft(TwoFourTreeItem node, TwoFourTreeItem sibling) {
		// Grab the next higher value from parent
		int nextHigher = node.nextHigher();
		node.value2 = nextHigher;
		// Grab the index of parents value location
		int parentValueLocation = node.parent.IndexLocation(nextHigher);
		// Perform the last steps of Left rotation
		node.parent.setValueAt(parentValueLocation, sibling.value1);
		sibling.removeLeftmostValue();
		node.values++;
		// Re-attach children to new statuses
		if (!node.isLeaf) {
			if (node.isThreeNode()) {
				node.centerChild = node.rightChild;
			}

			node.rightChild = sibling.leftChild;

			sibling.leftChild.parent = node;

			// Sibling went from a 3 node to a 2 node
			if (sibling.isTwoNode()) {
				sibling.leftChild = sibling.centerChild;
				sibling.centerChild = null;
			}
			// Sibling went from a 4 node to a 3 node
			else {
				sibling.leftChild = sibling.centerLeftChild;
				sibling.centerChild = sibling.centerRightChild;
				sibling.centerRightChild = null;
			}
		}
	}

	private void RotateRight(TwoFourTreeItem node, TwoFourTreeItem sibling) {
		// Grab the next higher value from parent
		node.value2 = node.value1;
		int oldValue = node.nextLower();
		node.value1 = oldValue;
		node.values++;

		// Grab the index of parents value location
		int parentValueLocation = node.parent.IndexLocation(oldValue);
		// Perform the last steps of Left rotation
		node.parent.setValueAt(parentValueLocation, sibling.findMaxValue());
		sibling.removeRightmostValue();

		// Re-attach children to new statuses
		if (!node.isLeaf) {
			node.centerChild = node.leftChild;
			node.leftChild = sibling.rightChild;

			sibling.rightChild.parent = node;

			// Sibling went from a 4 node to a 3 node
			if (sibling.isThreeNode()) {
				sibling.rightChild = sibling.centerRightChild;
				sibling.centerChild = sibling.centerLeftChild;
				sibling.centerLeftChild = null;
			}
			// Sibling went from a 3 node to a 2 node
			else {
				sibling.rightChild = sibling.centerChild;
				sibling.centerChild = null;
			}

		}
	}

	// Printing the tree in order
	public void printInOrder() {
		if (root != null)
			root.printInOrder(0);
	}

	public boolean NodeSearcher(TwoFourTreeItem node, int value) {
		if (value == node.value1) {
			return true;
		} else if (value == node.value2) {
			return true;
		} else if (value == node.value3) {
			return true;
		} else {
			return false;
		}
	}

	public TwoFourTreeItem NodeTraverser(TwoFourTreeItem node, int value) {
		// Find the value

		TwoFourTreeItem walker = node;

		while (walker.isRoot() || !walker.isLeaf) {

			// Find the value
			if (walker.isTwoNode()) {

				if (value < walker.value1) {
					walker = walker.leftChild;
				} else {
					walker = walker.rightChild;
				}
				if (NodeSearcher(walker, value)) {
					return walker;
				}

			}
			if (walker.isThreeNode()) {
				if (value < walker.value1) {
					walker = walker.leftChild;
				} else if (value < walker.value2) {
					walker = walker.centerChild;
				} else {
					walker = walker.rightChild;
				}
				if (NodeSearcher(walker, value)) {
					return walker;
				}
			}

			if (walker.isFourNode()) {
				if (value < walker.value1) {
					walker = walker.leftChild;
				} else if (value < walker.value2) {
					walker = walker.centerLeftChild;
				} else if (value < walker.value3) {
					walker = walker.centerRightChild;
				} else {
					walker = walker.rightChild;
				}
				if (NodeSearcher(walker, value)) {
					return walker;
				}
			}
		}
		// Search Failed
		return null;
	}



	public TwoFourTree.TwoFourTreeItem NonLoopTraversal(TwoFourTreeItem walker, int value) {
		// Send a one time traverse for the delete function to move on.
		if (walker.isLeaf) {
			return walker;
		}
		
		// Two Node Scenario
		if (walker.isTwoNode()) {
			if (value < walker.value1) {
				walker = walker.leftChild;
			} else
				walker = walker.rightChild;
		}
		// Three Node Scenario
		else if (walker.isThreeNode()) {
			if (value < walker.value1) {
				walker = walker.leftChild;
			} else if (value > walker.value1 && value < walker.value2) {
				walker = walker.centerChild;
			} else
				walker = walker.rightChild;
		}
		// Four Node Scenario
		else {
			if (value < walker.value1) {
				walker = walker.leftChild;
			} else if (value > walker.value1 && value < walker.value2) {
				walker = walker.centerLeftChild;
			} else if (value > walker.value2 && value < walker.value3) {
				walker = walker.centerRightChild;
			} else
				walker = walker.rightChild;
		}
	
		return walker;
	}

	

	

	

	public TwoFourTree() {
	}
}
