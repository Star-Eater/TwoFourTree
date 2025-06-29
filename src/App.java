import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;

public class App {
    /*
     * CONFIGURATION OPTIONS
     * Use these options to configure the test harness as you work with it.
     * 
     * Shuffle the created lists. If your code works without this but not with it,
     * something is wrong while "moving left" in a case.
     */
    static boolean ShouldShuffle = true;

    /*
     * Actually randomize the test case. We will use this in testing. When this is
     * false, the random seed will be 1.
     * 
     * With both ShouldShuffle and ShouldBeRandom false, the test cases are
     * deterministic on a given machine, which may aid you in finding specific bugs.
     */
    static boolean ShouldBeRandom = true;

    /*
     * Whether to test deletion. You can turn off this option if you know delete
     * isn't working yet. We'll also use it to determine partial credit.
     */
    static boolean RunDeleteCases = true;

    /*
     * Whether to run large cases, with and without deletion. You can use these
     * options for faster test runs, and we'll also use them to determine partial
     * credit.
     */
    static boolean RunLargeCases = true;
    static boolean RunLargeDeleteCases = true;

    /*
     * Whether to complain when a find returns false. Leaving this on when running
     * delete cases is very noisy.
     */
    static boolean NoisyFinds = false;

    /*
     * Whether to print the tree after the static tests. Useful in early debugging,
     * but just noisy later.
     */
    static boolean PrintStaticTree = false;

    /*
     * Print the tree before and after every delete in dynamic cases. This option
     * is *VERY* noisy, but running it for the small dynamic cases may help you find
     * deletion problems.
     */
    static boolean PrintDeleteTrees = false
    		;

    /*
     * END OF CONFIGURATION OPTIONS
     */

    
    
    
    private static ArrayList<Integer> deDuplicateAndScramble(ArrayList<Integer> list) {
        TreeSet<Integer> deDuped = new TreeSet<Integer>(list);
        ArrayList<Integer> outList = new ArrayList<Integer>(deDuped);
        if (ShouldShuffle) {
            Collections.shuffle(outList, RandomGenerator);
        }

        return outList;
    }

    private static ArrayList<Integer> generateIntArrayList(int howMany) {
        ArrayList<Integer> list = new ArrayList<Integer>(howMany);

        for (int i = 0; i < howMany; i++) {
            list.add(Integer.valueOf(RandomGenerator.nextInt(1000000000)));
        }
        list = deDuplicateAndScramble(list);

        return list;
    }

    private static ArrayList<Integer> generateStrikeList(List<Integer> fromList, int howMany) {
        ArrayList<Integer> strikeList = new ArrayList<Integer>(howMany);
        int fromLast = fromList.size() - 1;

        for (int i = 0; i < howMany; i++) {
            strikeList.add(fromList.get(RandomGenerator.nextInt(fromLast)));
        }
        strikeList = deDuplicateAndScramble(strikeList);

        return strikeList;
    }

    private static ArrayList<Integer> generateRemoveList(List<Integer> fromList) {
        ArrayList<Integer> removeList = new ArrayList<Integer>(fromList.size() / 2);

        for (int i = 0; i < fromList.size() / 2; i++) {
            removeList.add(fromList.get(i));
        }
        removeList = deDuplicateAndScramble(removeList);

        return removeList;
    }

    private static <T> int executeFinds(TwoFourTree coll, List<Integer> strikes) {
        boolean sentinel;
        int failures = 0;

        for (Integer e : strikes) {
            sentinel = coll.hasValue(e);
            if (sentinel == false) {
                if (NoisyFinds) {
                    System.out.printf("\nFailed to find %d", e);
                }
                failures++;
            }
        }

        if (failures > 0) {
            System.out.printf("(%,9d missing) ", failures);
        }

        return 0;
    }

    private static <T> int executeComparisonFinds(TreeSet<Integer> coll, List<Integer> strikes) {
        boolean sentinel;
        int failures = 0;

        for (Integer e : strikes) {
            sentinel = coll.contains(e);
            if (sentinel == false) {
                if (NoisyFinds) {
                    System.out.printf("\nFailed to find %d", e);
                }
                failures++;
            }
        }

        if (failures > 0) {
            System.out.printf("(%,9d missing) ", failures);
        }

        return 0;
    }

    public static void executeIntCase(int listSize, int strikeSize, boolean includeRemoves) {
        System.out.printf("CASE: %,8d integers, %,8d finds, %,8d removals.  Generating...\n", listSize, strikeSize,
                strikeSize / 2);

        ArrayList<Integer> intlist = generateIntArrayList(listSize);
        ArrayList<Integer> strikes = generateStrikeList(intlist, strikeSize);
        ArrayList<Integer> removeList = generateRemoveList(strikes);

        long start;
        long end;
        long ms;

        TwoFourTree theTree = new TwoFourTree();

        System.out.printf("  TwoFourTree ");

        start = System.currentTimeMillis();
        for (Integer e : intlist) {
            theTree.addValue(e);
        }
        end = System.currentTimeMillis();
        ms = end - start;
        System.out.printf("add: %,7dms  ", ms);

        start = System.currentTimeMillis();
        executeFinds(theTree, strikes);
        end = System.currentTimeMillis();
        ms = end - start;
        System.out.printf("find: %,7dms  ", ms);

        if (includeRemoves) {
            start = System.currentTimeMillis();
            for (Integer e : removeList) {
                if (PrintDeleteTrees) {
                    System.out.printf("----- delete %d from tree\n", e);
                    theTree.printInOrder();
                }
                theTree.deleteValue(e);
                if (theTree.hasValue(e)) {
                    System.out.printf("Failed to delete %d\n", e);
                } else {
                    // System.out.printf("Successfully deleted %d.\n", e);
                }
                if (PrintDeleteTrees) {
                    System.out.printf("----- After deleting %d from tree\n", e);
                    theTree.printInOrder();
                }
            }
            end = System.currentTimeMillis();
            ms = end - start;
            System.out.printf("del: %,7dms  ", ms);

            start = System.currentTimeMillis();
            executeFinds(theTree, strikes);
            end = System.currentTimeMillis();
            ms = end - start;
            System.out.printf("find: %,6dms  ", ms);
            System.out.printf("(Should be %,9d missing)  ", removeList.size());
        }

        System.out.printf("\n");
        // theTree.printInOrder();

        TreeSet<Integer> theComparison = new TreeSet<Integer>();

        System.out.printf("  TreeSet     ");

        start = System.currentTimeMillis();
        for (Integer e : intlist) {
            theComparison.add(e);
        }
        end = System.currentTimeMillis();
        ms = end - start;
        System.out.printf("add: %,7dms  ", ms);

        start = System.currentTimeMillis();
        executeComparisonFinds(theComparison, strikes);
        end = System.currentTimeMillis();
        ms = end - start;
        System.out.printf("find: %,7dms  ", ms);

        if (includeRemoves) {
            start = System.currentTimeMillis();
            for (Integer e : removeList) {
                // System.out.printf("----- delete %d from tree\n", e);
                /// theTree.printInOrder();
                theComparison.remove(e);
            }
            end = System.currentTimeMillis();
            ms = end - start;
            System.out.printf("del: %,7dms  ", ms);

            start = System.currentTimeMillis();
            executeComparisonFinds(theComparison, strikes);
            end = System.currentTimeMillis();
            ms = end - start;
            System.out.printf("find: %,6dms  ", ms);
            System.out.printf("(Should be %,9d missing)  ", removeList.size());
        }

        System.out.printf("\n");
        System.gc();
    }

    public static void executeStaticCase(List<Integer> values) {
        TwoFourTree tft = new TwoFourTree();

        if (ShouldShuffle)
            Collections.shuffle(values, RandomGenerator);

        for (int i : values) {
        	//System.out.printf("%d is added\n", i);
            tft.addValue(i);
            if (!tft.hasValue(i)) {
                System.out.printf("Failed to add %d in static test\n", i);
            }
            
        }

        for (int i : values) {
            if (!tft.hasValue(i)) {
                System.out.printf("Failed to add %d in static test\n", i);
            }
        }

        if (PrintStaticTree) {
            System.out.println("***** Static test:");
            tft.printInOrder();
        }

        if (RunDeleteCases) {
            List<Integer> deletes = generateStrikeList(values, values.size() / 5);
//            int k = 0;
            System.out.printf("***** After deleting nodes: ");
            System.out.println(deletes.toString());
            for (int i : deletes) {
//            	System.out.printf("%d iteration\n", ++k);
//            	System.out.printf("Deleting %d from Tree\n", i);
                tft.deleteValue(i);
                if (tft.hasValue(i)) {
                	System.out.printf("Failed to delete %d in static test\n", i);
                    System.exit(1);
                }
            }
            if (PrintStaticTree) {
                System.out.printf("***** After deleting nodes: ");
                System.out.println(deletes.toString());
                //tft.printInOrder();
            }
        }

    }
    


    // Only gets used if !ShouldBeRandom.
    static Random RandomGenerator = new Random(1);
    
    public static void main(String[] args) throws Exception {
    	if (ShouldBeRandom) {
    	    RandomGenerator = new Random();
    	    
    	}

    	List<Integer> primeList = Arrays.asList(
    		new Integer[] { 
    					    2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 
    					    53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 
    					    127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 
    					    211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 
    					    307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 
    					    401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 
    					    503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 
    					    601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 
    					    701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 
    					    809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 
    					    907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997
    					});


    	executeStaticCase(primeList);
    	executeIntCase(100, 20, RunDeleteCases);
    	executeIntCase(1000, 200, RunDeleteCases);
    	executeIntCase(10000, 2000, RunDeleteCases);
    	executeIntCase(100000, 20000, RunDeleteCases);
    	if (RunLargeCases) {
    	    executeIntCase(10000000, 2000000, RunDeleteCases && RunLargeDeleteCases);
    	    executeIntCase(100000000, 20000000, RunDeleteCases && RunLargeDeleteCases);
    	}




    }
}

