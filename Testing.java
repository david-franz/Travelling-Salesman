import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

import org.junit.Test;

public class Testing {

    private final static String DIRECTORY_PATH = Testing.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    private static final int NUMBER_OF_TEST_DATA_FILES = 10;

    private static int index;
    private static double[] approximationResults = new double[NUMBER_OF_TEST_DATA_FILES];

    @Test
    public void testApproximationAlgorithm() {
        Testing.testHelper(f -> {
            try {
                return Testing.approximationAlgorithm(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public static void testHelper(Function<File, List<CompleteGraph.Node>> algorithm) {
        Optional<List<CompleteGraph.Node>> path;
        for(index = 0; index < NUMBER_OF_TEST_DATA_FILES; index++) {
            path = Optional.ofNullable(
                    algorithm.apply(new File(DIRECTORY_PATH + "/Data/TSPData" + index + ".txt")));

            if(path.isEmpty()) continue;

            double pathLength = Testing.printResults(path.get());
            approximationResults[index] = pathLength;
        }
    }

    public static List<CompleteGraph.Node> approximationAlgorithm(File file) throws FileNotFoundException {
        CompleteGraph graph = Parser.parseCompleteGraph(file);

        List<CompleteGraph.Node> path = new ArrayList<>();

        CompleteGraph.Node startNode = graph.getNodes().get(0);
        startNode.markVisited();
        path.add(startNode);

        CompleteGraph.Node currentNode = startNode.findNextNode(startNode);
        while(currentNode.getID() != startNode.getID()) {
            currentNode.markVisited();
            path.add(currentNode);
            currentNode = currentNode.findNextNode(startNode);
        }

        return path;
    }

    /**
     * Prints path and path length, returns the path length to caller to be stored
     *
     * @param path
     * @return path length
     */
    private static double printResults(List<CompleteGraph.Node> path) {
        path.stream().forEach(node -> System.out.print(node.getID() + "->"));
        System.out.println(path.get(0).getID()); // path.get(0) is the startNode
        double pathLength = Testing.calculateTotalDistance(path);
        System.out.printf("distance of path: %.2f\n", pathLength);

        return pathLength;
    }

    /**
     * Helper method to printResults(List<CompleteGraph.Node> path).
     * Calculates path length from the given path and returns it to caller.
     *
     * @param path
     * @return path length
     */
    private static double calculateTotalDistance(List<CompleteGraph.Node> path) {
        double totalDistance = 0;
        for(int i = 0; i < path.size(); i++) {
            if(i+1 == path.size()) {
                totalDistance += path.get(i).calculateDistToNode(path.get(0)); // path.get(0) is startNode
                break;
            }
            totalDistance += path.get(i).calculateDistToNode(path.get(i+1));
        }
        return totalDistance;
    }
}
