import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DelKey {

    /**
     * We read the whole file to a list, edit the list and finally write the list back to file
     */
    public static String delete(String fileName, String service, String key) throws IOException {
        List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8));
        String outCome = "";

        for (int i = 0; i < fileContent.size(); i++) {
            String eachLine = fileContent.get(i);
            String[] kvPair = null;
            if (!eachLine.isEmpty()) {
                kvPair = eachLine.split(",");
            }

            /**
             * if key already exits we will get the value and update outcome
             */
            if (kvPair != null && kvPair[0].equals(key)) {
                outCome = "1";
                fileContent.remove(i);
                break;
            }
        }

        Files.write(Paths.get(fileName), fileContent, StandardCharsets.UTF_8);

        return service + "," + key + "," + outCome + "," + '\n';
    }
}
