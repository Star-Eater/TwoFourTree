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
        	if (index == 0) return value1;
        	else if (index == 1) return value2;
        	return value3;
        }
        
        public void setValueAt(int index, int val) {
            if (index == 0) value1 = val;
            else if (index == 1) value2 = val;
            else value3 = val;
        }
        
        public int removeRightmostValue() {
            if (values == 3) {
                int temp = value3;
                value3 = 0;
                values--;
                return temp;
            } 
            else {
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
		    } 
			else if (index == 1) {
		        value2 = value3;
		        value3 = 0;
		    } 
			else {
		        value3 = 0;
		    }
		    values--;
			
		}
		
		public int IndexLocation(int value) {
			if (value1 == value) {
				return 1;
			}
			else if (value2 == value) {
				return 2;
			}
			else return 3;
		}
		public int findMaxValue() {
			int temp = this.value1;
			if (this.value1 < this.value2) {
				 temp = this.value2;
			}
			else if (this.value2 < this.value3) {
				temp = this.value3;
			}
			return temp;
		}
		
		public int valueAt(int index) {
		    if (index == 0) return value1;
		    if (index == 1) return value2;
		    return value3;
		}
    }

    TwoFourTreeItem root = null; // Root of the tree. If null, the tree is empty.
    
    // Adding a value to the tree
    public boolean addValue(int value) {

        // If the tree is empty. Instantiate it with a new value
        if (root == null) {
            root = new TwoFourTreeItem(value);
            return true;
        }
        
        /*
         * Search for where the value needs to be added to. We do that with a walker
         * pointer that starts from the root of the tree and goes
         * accordingly depending on the value we are trying to insert. While doing so we
         * check to find any four nodes and split them.
         */

        TwoFourTreeItem walker = root;
        
        /* SCENARIOS:
    		 * The node in the tree is the first node created: 
    		 * 					1. (v1,(blank),(blank)) The first value goes in the v1. following we try to sort it out with this node having
    		 * 										 no children
    		 * 					2. (v1,v2,(blank))      When the second value goes in, we try to sort them out from least to greatest.
    		 * 					3. (v1,v2,v3)			The 3rd value goes and sorted as so.
    		 * 					
    		 * 					4. Searching phase (13)	(12,15,17) ->     (15, , ) a node a 
    		 * 					Four nodes are broken down on the way    /	    \	  created as a parent for v1 and v3 separated
    		 * 															/		 \	  from each other.
    		 * 														(12, , ) (17, , )	
    		 * 					
    		 * 					4a.						(15, (blank), (blank)) Walker (13)
    		 * 												/            \
    		 * 											   /			  \
    		 * 										 (12, , )			(17, , )
    		 * 					
    		 * 					4b.						(15, (blank) , (blank) )
    		 * 												/            \
    		 * 											   /			  \
    		 * 						 Walker (13)	 (12, , )			(17, , )
    		 * 	
    		 * 					4c. 					(15, (blank), (blank))  
    		 * 												/            \
    		 * 											   /			  \
    		 * 										 (12, 13, )	      (17, , )
    		 * 																		
    		 */
        	

        // =========Special Case 1:Walker starts at the first and (Only) node ever created for the tree.======================================
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
            } 
            else if (walker.isThreeNode()) {
                /*
                 * 4 10
                 * S1. 1 4 10
                 * S2. 4 7 10
                 * S3. 4 10 13
                 */
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
                } 
                else {
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
//            return true;
        }
        
        // With the goal of inserting. Traversing always should go to the leaf 
        while(walker.isLeaf == false) {
        	
        	// Two node Insert scenarios
        	if (walker.isTwoNode()) {
        		// Either you are greater or your less.
        		if (value < walker.value1) {
        			walker = walker.leftChild;
        		}
        		else {
        			walker = walker.rightChild;
        		}
        	}
        	else if (walker.isThreeNode()) {
        		//Value is either your lesser, in between, or greater 
        		if (value < walker.value1) {
        			walker = walker.leftChild;
        		}
        		else if (value > walker.value1 && value < walker.value2) {
        			walker = walker.centerChild;
        		}
        		else {
        			walker = walker.rightChild;
        		}
        	}
        	else if (walker.isFourNode() && !walker.isRoot()) {
        		//Since its at the leaf it has a parent we would promote the middle value to the parent and go on from there.
        		
        		// Parent is a two node scenario.
        		if (walker.parent.isTwoNode()) {
        			// Either the value is smaller or bigger than the parents two node value
        			if (walker.value2 < walker.parent.value1) {
        				walker.parent.value2 = walker.parent.value1;
        				walker.parent.value1 = walker.value2;
        				walker.parent.values++;
        			}
        			else {
        				walker.parent.value2 = walker.value2;
        				walker.parent.values++;
        			}
        			/* 
        			 * The parent is now a three node. It now has a center child place.
        			 * Break apart the old node values and assign them accordingly.
        			*/
        			TwoFourTreeItem temp;
        			TwoFourTreeItem temp2;
        			
        			// If walkers value1 is greater than their parents value1. It means you're on the right hand side of the parent.
        			if (walker.value1 > walker.parent.value1 ) {
        					temp = new TwoFourTreeItem(walker.value1);
        					temp2 = new TwoFourTreeItem(walker.value3);
        					
        					walker.parent.centerChild = temp;
        					walker.parent.rightChild = temp2;
        					
        					temp.isLeaf = false;
        					temp2.isLeaf = false;
        					
        					//Assign the children to temp and assign walkers old children on each side to new parents.
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
        					
        					
        					//6/5/2023 1 of the Errors is here somewhere
        					
        					// Send walker back to the parent node that got a new value
        					walker = walker.parent;
        				
        			}
        			// If walkers value1 is lesser than their parents value1. It means you're on the left side of the parent.
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
        			        if (walker.leftChild != null) walker.leftChild.parent = temp2;
        			        if (walker.centerLeftChild != null) walker.centerLeftChild.parent = temp2;

        			        // Assign children to temp
        			        temp.leftChild = walker.centerRightChild;
        			        temp.rightChild = walker.rightChild;
        			        if (walker.centerRightChild != null) walker.centerRightChild.parent = temp;
        			        if (walker.rightChild != null) walker.rightChild.parent = temp;

        			        // Send walker back to the parent node
        			        walker = walker.parent;
        			    
        			}
        		}
        		// Parent is a three node scenario.
        		else if (walker.parent.isThreeNode()) {
        			
        			TwoFourTreeItem temp = new TwoFourTreeItem(walker.value1);
        			TwoFourTreeItem temp2 = new TwoFourTreeItem(walker.value3);
        			
        			// If value 2 of walker is less than walker parents value 1. Promotion came from the left
        			if (walker.value2 < walker.parent.value1) {
        				walker.parent.value3 = walker.parent.value2;
        				walker.parent.value2 = walker.parent.value1;
        				walker.parent.value1 = walker.value2;
        				walker.parent.values++;
        				
        				//Parent has become a four node. Split apart the nodes appropriately.
        				temp.parent = walker.parent;
        				temp.leftChild = walker.leftChild;
        				temp.rightChild = walker.centerLeftChild;
        				
        				temp2.parent = walker.parent;
        				temp2.leftChild = walker.centerRightChild;
        				temp2.rightChild = walker.rightChild;
        				
        				walker.parent.leftChild = temp;
        				walker.parent.centerLeftChild = temp2;
        				
        				//Reassign walkers children to new parents
        				walker.leftChild.parent = temp;
        				walker.centerLeftChild.parent = temp;
        				walker.centerRightChild.parent = temp2;
        				walker.rightChild.parent = temp2;
        				
        				temp.isLeaf = false;
        				temp2.isLeaf = false;
        				
        				// If the original center existed it goes left to center left.
        				walker.parent.centerRightChild = walker.parent.centerChild;
        				walker.parent.centerChild = null;
        				
        				//Send the node back to the walkers parent. And send it wherever it needs to be from there.
        				walker = walker.parent;
        				
        				if (value < walker.value1) {
        					walker = walker.leftChild;
        				}
        				else if (value > walker.value1 && value < walker.value2) {
        					walker = walker.centerLeftChild;
        				}
        				else if (value > walker.value2 && value < walker.value3) {
        					walker = walker.centerRightChild;
        				}
        				else {
        					walker = walker.rightChild;
        				}
        				
        				
        				
        			}
        			// If value 2 of walker is bigger than parents value 1 but smaller than 3. Promotion came from the middle.
        			else if (walker.value2 > walker.parent.value1 && walker.value2 < walker.parent.value3) {
        				walker.parent.value3 = walker.parent.value2;
        				walker.parent.value2 = walker.value2;
        				walker.parent.values++;
        				
        				//Parent has become a four node. Split apart the nodes appropriately. and Assign them correctly
        				temp.parent = walker.parent;
        				temp.leftChild = walker.leftChild;
        				temp.rightChild = walker.centerLeftChild;
        				
        				temp2.parent = walker.parent;
        				temp2.leftChild = walker.centerRightChild;
        				temp2.rightChild = walker.rightChild;
        				
        				//Reassign walkers children to new parents
        				walker.leftChild.parent = temp;
        				walker.centerLeftChild.parent = temp;
        				walker.centerRightChild.parent = temp2;
        				walker.rightChild.parent = temp2;
        				
        				//The center spliced in half to each of its side.
        				walker.parent.centerLeftChild = temp;
        				walker.parent.centerRightChild = temp2;
        				
        				temp.isLeaf = false;
        				temp2.isLeaf = false;
        				
        				//Send the node back to the walkers parent. And send it wherever it needs to be from there.
        				walker = walker.parent;
        				walker.centerChild = null;
        				
        				if (value < walker.value1) {
        					walker = walker.leftChild;
        				}
        				else if (value > walker.value1 && value < walker.value2) {
        					walker = walker.centerLeftChild;
        				}
        				else if (value > walker.value2 && value < walker.value3) {
        					walker = walker.centerRightChild;
        				}
        				else {
        					walker = walker.rightChild;
        				}
        				
        				
        				
        				
        			}
        			// If value 2 of walker is bigger than parents value 2 then Promotion came from the right.
        			else {
        				walker.parent.value3 = walker.value2;
        				walker.parent.values++;

        				//Parent has become a four node. Split apart the nodes appropriately. and Assign them correctly
        				temp.parent = walker.parent;
        				temp.leftChild = walker.leftChild;
        				temp.rightChild = walker.centerLeftChild;
        				temp2.parent = walker.parent;
        				temp2.leftChild = walker.centerRightChild;
        				temp2.rightChild = walker.rightChild;
        				
        				walker.parent.centerRightChild = temp;
        				walker.parent.rightChild = temp2;
        				
        				//Reassign walkers children to new parents.
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
        				
        				
        				//Send the node back to the walkers parent. And send it wherever it needs to be from there.
        				walker = walker.parent;
        				
        				if (value < walker.value1) {
        					walker = walker.leftChild;
        				}
        				else if (value > walker.value1 && value < walker.value2) {
        					walker = walker.centerLeftChild;
        				}
        				else if (value > walker.value2 && value < walker.value3) {
        					walker = walker.centerRightChild;
        				}
        				else {
        					walker = walker.rightChild;
        				}
        				
        			}
        			
        			
        			
        		}
        		
        	}
        	
        	//Walker is a Four node but our last logic didn't account for if it had a parent in the loop. Ugly solution.
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
                

                // Root is no longer a leaf but also because of the condition set the newly children leaves to false.
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
                }
        		else if (walker.isThreeNode()) {
        			if (value < walker.value1) {
        				walker.value3 = walker.value2;
        				walker.value2 = walker.value1;
        				walker.value1 = value;
        				walker.values++;
        			}
        			else if (value > walker.value1 && value < walker.value2) {
        				walker.value3 = walker.value2;
        				walker.value2 = value;
        				walker.values++;
        			}
        			else {
        				walker.value3 = value;
        				walker.values++;
        			}
        		}
        		else if (walker.isFourNode()) {
        			TwoFourTreeItem temp;
        			TwoFourTreeItem temp2;
        			// Parent Two Node Scenarios
        			if (walker.parent.isTwoNode()) {
        				// Determine if walker value1 is less than or more than there parent.
        				if (walker.value1 > walker.parent.value1) {
        					//The lesser of the node goes as the center node
        					//While the greater stays as the right.
        					walker.parent.value2 = walker.value2;
        					walker.parent.values++;
        					temp = new TwoFourTreeItem(walker.value1);
        					temp2 = new TwoFourTreeItem(walker.value3);
        					walker.parent.centerChild = temp;
        					walker.parent.rightChild = temp2;
        					temp.parent = walker.parent;
        					temp2.parent = walker.parent;
        					walker = walker.parent;
        					
        				}
        				else {
        					//The greater of the node goes as the center node
        					//While the lesser stays as the left
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
        				
        			
        			//End of two node scenario
        			}
        			//Start of three node scenarios
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
            				// Promotion came from the right. The lesser in the node goes to the center right
            				// Meanwhile the greater goes stays on the right hand side in a brand new node.
            				walker.parent.centerRightChild = temp;
            				walker.parent.rightChild = temp2;
            				walker.parent.centerLeftChild = walker.parent.centerChild;
            				
            				walker = walker.parent;
            				
            				//The center no longer exists
            				walker.centerChild = null;
            				
            				//Send the node where ever it needs to be.
            				
            				//Send the node to the left
            				if (value < walker.value1) {
            					walker = walker.leftChild;
            				}
            				//Send the node to the center left
            				else if (value > walker.value1 && value < walker.value2) {
            					walker = walker.centerLeftChild;
            				}
            				//Send the node to the center right
            				else if (value > walker.value2 && value < walker.value3) {
            					walker = walker.centerRightChild;
            				}
            				//Send the node right
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
        				else if(walker.value2 < walker.parent.value2 && walker.value2 > walker.parent.value1) {
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
            				// Promotion came from the middle. The lesser in the node goes to center left vice versa
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
            				
            				//Send the node to the left
            				if (value < walker.value1) {
            					walker = walker.leftChild;
            				}
            				//Send the node to the center left
            				else if (value > walker.value1 && value < walker.value2) {
            					walker = walker.centerLeftChild;
            				}
            				//Send the node to the center right
            				else if (value > walker.value2 && value < walker.value3) {
            					walker = walker.centerRightChild;
            				}
            				//Send the node right
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
            	                }
            					else if (walker.isThreeNode()) {
            						if (value < walker.value1) {
            							walker.value3 = walker.value2;
            							walker.value2 = walker.value1;
            							walker.value1 = value;
            						}
            						else if (value > walker.value1 && value < walker.value2) {
            							walker.value3 = walker.value2;
            							walker.value2 = value;
            						}
            						else {
            							walker.value3 = value;
            						}
            					return true;
            					}
            				}
        				}
        				
        				
        			}
        		}
        	}
//        	// EMERGENCY INSERTION
//        	if (walker.isLeaf && insertion == 1) {
//        		
//        	}
        }
        
        

        return false;
    }

    // Searching for a value in the tree
    public boolean hasValue(int value) {
    	
    	TwoFourTreeItem walker = root;
    	
    	while (!walker.isLeaf) {
    		//Two Node Scenario
    		if (walker.isTwoNode()) {
    			if (value < walker.value1) {
        			walker = walker.leftChild;
        			if (NodeSearcher(walker, value)) {
        				return true;
        			}
        		}
    			else {
        			walker = walker.rightChild;
        			if (NodeSearcher(walker, value)) {
        				return true;
        			}
        		}
    		}
    		
    		//Three node scenario
    		else if (walker.isThreeNode()) {
    			if (value < walker.value1) {
        			walker = walker.leftChild;
        			if (NodeSearcher(walker, value)) {
        				return true;
        			}
        		}
    			else if (value > walker.value1 && value < walker.value2) {
        			walker = walker.centerChild;
        			if (NodeSearcher(walker, value)) {
        				return true;
        			}
        		}
    			else {
        			walker = walker.rightChild;
        			if (NodeSearcher(walker, value)) {
        				return true;
        			}
        		}
    		}
    		else if (walker.isFourNode()) {
    			if (value < walker.value1) {
        			walker = walker.leftChild;
        			if (NodeSearcher(walker, value)) {
        				return true;
        			}
        		}
        		else if (value > walker.value1 && value < walker.value2) {
        			walker = walker.centerLeftChild;
        			if (NodeSearcher(walker, value)) {
        				return true;
        			}
        		}
        		else if (value > walker.value2 && value < walker.value3) {
        			walker = walker.centerRightChild;
        			if (NodeSearcher(walker, value)) {
        				return true;
        			}
        		}
        		else {
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
    public boolean deleteValue(int value) {
    	TwoFourTreeItem walker = root;
    	
    	
    	
    	/*
    	   If the node is not a leaf node then:
		   Find the successor of that node. A successor of a node is the smallest element among the ones which are greater than it or the largest element among the ones that are smaller than it.
		   Swap the successor with the current node and delete that node in the leaf.
		   
		   But it may cause an issue of underflow if the leaf node is a 2 node. 
		   To avoid this we perform the following adjustments on 2 nodes encountered along 
		   the path to reach the node to be removed while moving from top to bottom.


    	*/

    	/*
    	   Case - 1: If either of the siblings of the current node is a 3 or 4 node.

		   Perform a rotation with that sibling.
		   The key having the closest value to this node moves up to the parent that overlooks the current 
		   node and the parent is added to the current node to make it a 3 node.
		   The node that was the originally rotated sibling's child is now the child of the current node.
    	 */
    	
    	
    	
    	
    	
    	
    	
    
    	// If the node is a leaf node then remove the required value from that node and decrease the data elements by 1.
    	
    	
    	
    	return false;
    }
    
    

    // Printing the tree in order
    public void printInOrder() {
        if (root != null)
            root.printInOrder(0);
    }
   
    public boolean NodeSearcher(TwoFourTreeItem node, int value) {
    	if (value == node.value1) {
    		return true;
    	}
    	else if (value == node.value2) {
    		return true;
    	}
    	else if (value == node.value3) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public TwoFourTreeItem NodeTraverser(TwoFourTreeItem node, int value) {
    	//Find the value
    	
    	TwoFourTreeItem walker = node;
    	
    	while (walker.isRoot() || !walker.isLeaf) {
    		
    		//Find the value
    		if (walker.isTwoNode()) {
    			
    			if (value < walker.value1) {
    				walker = walker.leftChild;
    			}
    			else {
    				walker = walker.rightChild;
    			}
    			if (NodeSearcher(walker, value)) {
    				return walker;
    			}
    			
    		}
    		if (walker.isThreeNode()) {
    			if (value < walker.value1) {
    				walker = walker.leftChild;
    			}
    			else if (value < walker.value2) {
    				walker = walker.centerChild;
    			}
    			else {
    				walker = walker.rightChild;
    			}
    			if (NodeSearcher(walker, value)) {
    				return walker;
    			}
    		}
    		
    		if (walker.isFourNode()) {
    			if (value < walker.value1) {
    				walker = walker.leftChild;
    			}
    			else if (value < walker.value2) {
    				walker = walker.centerLeftChild;
    			}
    			else if(value < walker.value3) {
    				walker = walker.centerRightChild;
    			}
    			else {
    				walker = walker.rightChild;
    			}
    			if(NodeSearcher(walker, value)) {
    				return walker;
    			}
    		}
    	}
    	// Search Failed
    	return null;
    }
    
   
    public int ChildPosition(TwoFourTreeItem node) {
    	
    	if (node.parent == null) {
    		return 0;
    	}
    	// Parent Two node scenario
    	if (node.parent.isTwoNode()) {
    		if (node.parent.leftChild == node) {
    			return 1;
    		}
    		else return 2;
    	}
    	//Parent Three node scenario
    	else if (node.parent.isThreeNode()) {
    		if(node.parent.leftChild == node) {
    			return 1;
    		}
    		else if(node.parent.centerChild == node) {
    			return 2;
    		}
    		else return 3;
    	}
    	// Parent Four node Scenario
    	else {
    		if (node.parent.leftChild == node) {
    			return 1;
    		}
    		else if(node.parent.centerLeftChild == node) {
    			return 2;
    		}
    		else if(node.parent.centerRightChild == node) {
    			return 3;
    		}
    		else return 4;
    	}
    }
    
    public void NonLoopTraversal(TwoFourTreeItem walker, int value) {
    	//Send a one time traverse for the delete function to move on.
    	// Two Node Scenario
    	if (walker.isTwoNode()) {
    		if (value < walker.value1) {
    			walker = walker.leftChild;
    		}
    		else walker = walker.rightChild;
    	}
    	// Three Node Scenario
    	else if (walker.isThreeNode()) {
    		if (value < walker.value1) {
    			walker = walker.leftChild;
    		}
    		else if (value > walker.value1 && value < walker.value2) {
    			walker = walker.centerChild;
    		}
    		else walker = walker.rightChild;
    	}
    	// Four Node Scenario
    	else {
    		if (value < walker.value1) {
    			walker = walker.leftChild;
    		}
    		else if (value > walker.value1 && value < walker.value2) {
    			walker = walker.centerLeftChild;
    		}
    		else if (value > walker.value2 && value < walker.value3) {
    			walker = walker.centerRightChild;
    		}
    		else walker = walker.rightChild;
    	}
    }
    
    public void LeftRotation(TwoFourTreeItem walker, int position) {
    	if (walker.isTwoNode()) {
    		if (walker.parent.isFourNode()) {
	    			if (position == 3) {
		    			// Left Rotate the values
		        		walker.value2 = walker.parent.value3;
		        		walker.parent.value3 = walker.parent.rightChild.value1;
		        		walker.values++;
		        		
		        		//Adjust the nodes accordingly
		        		walker.parent.rightChild.removeLeftmostValue();
//		        		walker.parent.rightChild.value1 = walker.parent.rightChild.value2;
//		        		walker.parent.rightChild.value2 = walker.parent.rightChild.value3;
//		        		walker.parent.rightChild.value3 = 0;
//		        		walker.parent.rightChild.values--;
		        		
		        		//Adjust the children
		        		walker.parent.rightChild.leftChild.parent = walker;
		        		walker.parent.rightChild.centerChild = walker.parent.leftChild.leftChild;
	        	}
    		}
    	}
    }
    
    private void borrowFromRightSibling(TwoFourTreeItem underflowNode, TwoFourTreeItem parent, TwoFourTreeItem sibling, int parentIndex) {
        if (sibling.values >= 2) {
            underflowNode.value1 = parent.valueAt(parentIndex);
            underflowNode.values++;

            parent.setValueAt(parentIndex, sibling.removeLeftmostValue());
            sibling.values--;
        }
    }
    
    

    public void NodeMergerRotationTraverser(TwoFourTreeItem walker, int value) {
    	
    	while (walker != null && !NodeSearcher(walker, value)) {
    		if (walker.isTwoNode()) {
    			if (walker.parent.isFourNode()) {
    				int position = ChildPosition(walker);
    				
    				// Left Position
    				if (position == 1) {
    					//Either a Merge Happens
    					if (walker.centerLeftChild.isTwoNode()) {
    						TwoFourTreeItem Merge = new TwoFourTreeItem(walker.value1, walker.parent.value1, walker.parent.centerLeftChild.value1);
    						
    						// Lower the count for parent and readjusts.
    						walker.parent.value1 = walker.parent.value2;
    						walker.parent.value2 = walker.parent.value3;
    						walker.parent.value3 = 0;
    						walker.parent.values--;
    						
    						// Re-attach everything Merged node became a four node
    						Merge.parent = walker.parent;
    						Merge.leftChild = walker.leftChild;
    						Merge.centerLeftChild = walker.rightChild;
    						Merge.centerRightChild = walker.parent.centerLeftChild.leftChild;
    						Merge.rightChild = walker.parent.centerLeftChild.rightChild;
    						
    						walker.leftChild.parent = Merge;
    						walker.rightChild.parent = Merge;
    						walker.parent.centerLeftChild.leftChild.parent = Merge;
    						walker.parent.centerLeftChild.rightChild.parent = Merge;
    						
    						// Send walker down to next node
    						walker = Merge;
    						NonLoopTraversal(walker, value);
    					}
    					// Or Rotation happens
    					else {
    						
    					}
    				}
    				
    				// Center Left Position
    				else if (position == 2) {
    					// Merging the sibling if there both 2 nodes
    					if (walker.centerRightChild.isTwoNode()) {
    						TwoFourTreeItem Merge = new TwoFourTreeItem(walker.value1, walker.parent.value2, walker.parent.centerRightChild.value1);
    						
    						// Lower the count for parent and readjusts.
    						walker.parent.value2 = walker.parent.value3;
    						walker.parent.value3 = 0;
    						walker.parent.values--;
    						
    						// Re-attach everything Merged node became a four node
    						Merge.parent = walker.parent;
    						Merge.leftChild = walker.leftChild;
    						Merge.centerLeftChild = walker.rightChild;
    						Merge.centerRightChild = walker.parent.centerRightChild.leftChild;
    						Merge.rightChild = walker.parent.centerRightChild.rightChild;
    						
    						walker.leftChild.parent = Merge;
    						walker.rightChild.parent = Merge;
    						walker.parent.centerRightChild.leftChild.parent = Merge;
    						walker.parent.centerRightChild.rightChild.parent = Merge;
    						
    						// Send walker down to next node
    						walker = Merge;
    						NonLoopTraversal(walker, value);
    					}
    				}
    			
    				else if (position == 3) {
    					if (walker.rightChild.isTwoNode()) {
    						TwoFourTreeItem Merge = new TwoFourTreeItem(walker.value1, walker.parent.value3, walker.parent.centerRightChild.value1);
    						
    						// Lower the count for parent and readjusts.
    						walker.parent.value3 = 0;
    						walker.parent.values--;
    						
    						// Re-attach everything Merged node became a four node
    						Merge.parent = walker.parent;
    						Merge.leftChild = walker.leftChild;
    						Merge.centerLeftChild = walker.rightChild;
    						Merge.centerRightChild = walker.parent.rightChild.leftChild;
    						Merge.rightChild = walker.parent.rightChild.rightChild;
    						
    						walker.leftChild.parent = Merge;
    						walker.rightChild.parent = Merge;
    						walker.parent.rightChild.leftChild.parent = Merge;
    						walker.parent.rightChild.rightChild.parent = Merge;
    						
    						// Send walker down to next node
    						walker = Merge;
    						NonLoopTraversal(walker, value);
    					}
    					else if (walker.rightChild.values >= 2) {
    						
    					}
    				}
    				else if (position == 4){
    					if (walker.centerRightChild.isTwoNode()) {
    						TwoFourTreeItem Merge = new TwoFourTreeItem(walker.value1, walker.parent.value3, walker.parent.centerLeftChild.value1);
    						
    						// Lower the count for parent and readjusts.
    						walker.parent.value3 = 0;
    						walker.parent.values--;
    						
    						// Re-attach everything Merged node became a four node
    						Merge.parent = walker.parent;
    						Merge.centerRightChild = walker.leftChild;
    						Merge.rightChild = walker.rightChild;
    						Merge.centerLeftChild = walker.parent.centerRightChild.rightChild;
    						Merge.rightChild = walker.parent.centerRightChild.leftChild;
    						
    						walker.leftChild.parent = Merge;
    						walker.rightChild.parent = Merge;
    						walker.parent.centerRightChild.leftChild.parent = Merge;
    						walker.parent.centerRightChild.rightChild.parent = Merge;
    						
    						// Send walker down to next node
    						walker = Merge;
    						NonLoopTraversal(walker, value);
    					}
    				}
    			}
    		}
    	}
    }
    
	public TwoFourTree() {
    }
}
