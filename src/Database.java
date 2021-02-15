import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class is the main entry class
 */
public class Database {
    public static void main(String[] args) throws IOException {

        // Delete output.txt if exists
        // Create a new output.txt file

        long start = System.currentTimeMillis();

        File filename = new File("output.txt");

        if (filename.exists()) {
            filename.delete();
        } else {
            filename.createNewFile();
        }

        WriteToFile.writeToFile("type,key1,key2,value \n");
        // type,key1,key2,value
        Scanner scanner = new Scanner(new FileReader("input.txt"));
        PutKeyValue putKeyValue = new PutKeyValue();

        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String[] columns = scanner.nextLine().split(",");

/**
 * Passing the FileName to the corresponding service with key and values
 */
            int indexFileHashValue = getValueHash(Integer.parseInt(columns[1].substring(columns[1].length() - 1)));

            String service = columns[0];
            String FileName = getFileName(indexFileHashValue);

            String outPutToBeWrittenToOutPutFile = null;
            if (service.equals("put")) {
                outPutToBeWrittenToOutPutFile = putKeyValue.put(FileName, service, columns[1], columns[3]);
            } else if (service.equals("get")) {
                outPutToBeWrittenToOutPutFile = GetKeyValue.get(FileName, service, columns[1]);
            } else if (service.equals("del")) {
                outPutToBeWrittenToOutPutFile = DelKey.delete(FileName, service, columns[1]);
            }

            WriteToFile.writeToFile(outPutToBeWrittenToOutPutFile);
        }
        long end2 = System.currentTimeMillis();
        long res = (end2 - start);
        System.out.println("Time taken to execute with KVDataStoreImplV_3 = " + res+" milliseconds!!!\n");
    }

    /**
     * Get the corresponding file name based on the hash value.
     *
     * @param indexFileHashValue
     * @return
     * @throws IOException
     */
    private static String getFileName(int indexFileHashValue) throws IOException {
        File initialFileName = new File("file" + indexFileHashValue + ".txt");

        if (!initialFileName.exists()) {
            initialFileName.createNewFile();
        }
        return initialFileName.toString();
    }

    /**
     * The hash function that returns the hash value for the file creation
     *
     * @param key
     * @return
     */
    private static int getValueHash(Integer key) {
        Integer hashVal = null;

        if (key != null) {
            hashVal = key % 10;
        }
        return hashVal;
    }

}
