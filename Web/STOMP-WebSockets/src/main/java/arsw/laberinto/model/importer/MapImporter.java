package arsw.laberinto.model.importer;

import arsw.laberinto.model.game.Mapa;
import java.io.*;
import java.util.*;
import java.util.logging.*;

public class MapImporter {

    public static ArrayList<String> lectura = new ArrayList<>();

    public static ArrayList<String> leer(int j) {
        if (lectura.isEmpty()) {
            try {
                BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/maps/1" + j + "11.txt"));
                String bfRead = "";
                while ((bfRead = bf.readLine()) != null) {
                    lectura.add(bfRead);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lectura;
    }
}
