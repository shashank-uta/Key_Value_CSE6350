import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PutKeyValue {

    public String put(String FileName, String service, String key, String value) throws IOException {
/**
 * We read the whole file to a list, edit the list and finally write the list back to file
 *
 */

        List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(FileName), StandardCharsets.UTF_8));
        String outCome = "0";
        int index = 0;
        boolean firstEntry = false;

        if (fileContent.isEmpty()) {
            firstEntry = true;
        } else {
            for (int i = 0; i < fileContent.size(); i++) {
                String eachLine = fileContent.get(i);
                String[] kvPair = null;
                if (!eachLine.isEmpty()) {
                    kvPair = eachLine.split(",");
                }

                /**
                 * if key already exits we will update same the key with new value
                 */
                if (kvPair != null && kvPair[0].equals(key)) {
                    outCome = "1";
                    kvPair[1] = value;
                    StringBuilder sb = new StringBuilder(kvPair[0] + "," + kvPair[1]);
                    fileContent.set(i, sb.toString());
                    index = i;
                    break;
                }
            }
        }

        /**
         * if not found add the new line
         */
        if (firstEntry || outCome.equals("0")) {
            fileContent.add(new String(key + "," + value));
        }/**
         * update the line at that index
         */
        else {
            fileContent.set(index, new String(key + "," + value));
        }


        Files.write(Paths.get(FileName), fileContent, StandardCharsets.UTF_8);

        return service + "," + key + "," + outCome + '\n';

    }
}

