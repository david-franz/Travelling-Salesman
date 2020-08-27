import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    public static CompleteGraph parseCompleteGraph(File f) throws FileNotFoundException {
        CompleteGraph g = new CompleteGraph();

        Scanner input = new Scanner(f);

        String next = input.next();

        // second token is the dataset name (test datasets have been modified so this is always true)
        System.out.println("dataset name: " + input.next());

        while(!next.equals("NODE_COORD_SECTION")) next = input.next();

        while(input.hasNext()) {
            if(input.hasNext("EOF")) break;

            int ID = input.nextInt();
            double xCoordinate = input.nextDouble();
            double yCoordinate = input.nextDouble();

            g.addNode(ID, xCoordinate, yCoordinate);
        }

        input.close();

        g.processNodes();
        return g;
    }
}
