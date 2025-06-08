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
    

    public TwoFourTree() {
    }
}
