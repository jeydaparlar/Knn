package utils;

import model.DataPoint;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe FileManager qui permet de gérer les imports du CSV
 */
public class FileManagerTest {

    /**
     * Permet de lire dans le fichier et d'intégrer et interpréter les données dans une liste d'Iris
     * @param path chemin vers le fichier csv
     * @return une List d'Iris
     */
    public static  List<DataPoint> readFile(String path) {
        List<DataPoint> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            String[] attributes = br.readLine().split(",");

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                DataPoint dataPoint = new DataPoint(); // Cast to T
                for (int i = 0; i < attributes.length; i++) {
                    dataPoint.addAttribute(attributes[i], values[i]);
                }
                result.add(dataPoint);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + path);
            e.printStackTrace();
        }

        return result;
    }



    /**
     * Permet de compter le nombre de lignes présentent dans le fichier CSV
     * @param csvFilePath chemin vers le fichier CSV
     * @return le nombre de lignes du fichier CSV
     * @throws IOException si le fichier n'est pas trouvé
     */
    public static int countLines(String csvFilePath) throws IOException {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            while (reader.readLine() != null) {
                lines++;
            }
        }
        return lines;
    }

    /**
     * Permet de savoir si le fichier passé en paramètre existe
     * @param path chemin vers le fichier csv
     * @return un boolean qui indique si le fichier existe
     */
    public static boolean ifExist(String path) {
        File file = new File(path);
        return file.exists();
    }


}
