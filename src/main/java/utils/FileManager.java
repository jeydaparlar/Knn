package utils;

import model.DataPoint;
import model.Iris;
import model.VARIETY;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Classe FileManager qui permet de gérer les imports du CSV
 */
public class FileManager {

    /**
     * Permet de lire dans le fichier et d'intégrer et interpréter les données dans une liste d'Iris
     * @param path chemin vers le fichier csv
     * @return une List d'Iris
     */
    public static List<Iris> readFile(String path) {
        List<Iris> res = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while (line != null) {
                String[] tmp = line.split(",");
                if (tmp[3].startsWith(".")) {
                    // Ajoute un zéro devant le point, même pas sûr que ce soit nécessaire
                    tmp[3] = "0" + tmp[3];
                }
                //  Enlève les guillemets seulement s'ils entourent toute la valeur
                if (tmp[4].startsWith("\"") && tmp[4].endsWith("\"")) {
                    tmp[4] = tmp[4].substring(1, tmp[4].length() - 1); // Enlève les guillemets
                }
                // Enlève les guillemets s'ils sont en double
                tmp[4] = tmp[4].replace("\"\"", "\""); // Remplace "" par "


                VARIETY variety = switch (tmp[4]) {
                    case "Setosa" -> VARIETY.SETOSA;
                    case "Versicolor" -> VARIETY.VERSICOLOR;
                    case "Virginica" -> VARIETY.VIRGINICA;
                    default -> null;

                };

                res.add(new Iris(Double.parseDouble(tmp[0]), Double.parseDouble(tmp[1]), Double.parseDouble(tmp[2]), Double.parseDouble(tmp[3]), variety, false));
                System.out.println(Double.parseDouble(tmp[0]) + " " + Double.parseDouble(tmp[1]) + " "
                        + Double.parseDouble(tmp[2]) + " " + Double.parseDouble(tmp[3]));
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        } catch (IOException e) {
            System.out.println("Reading error: " + e.getMessage());
            e.fillInStackTrace();
        }
        return res;

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
