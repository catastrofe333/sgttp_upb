package modelo.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GestorArchivos {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    //METODO PARA LEER EL JSON
    public static <T> T leerJSON(String rutaArchivo, Class<T> tipoClase){
        try(FileReader reader = new FileReader("./src/main/resources/archivos/" + rutaArchivo + ".json")){
            return gson.fromJson(reader, tipoClase);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //METODO PARA ESCRIBIR EN EL JSON
    public static <T> void escribirJSON(String rutaArchivo, T objeto){
        try(FileWriter writer = new FileWriter("./src/main/resources/archivos/" + rutaArchivo + ".json")){
            gson.toJson(objeto, writer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //METODO PARA LEER TXT
    public static String leerTxt(String ruta){
        try (BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/archivos/" + ruta + ".txt"))) {
            StringBuilder log = new StringBuilder();
            String linea = "";
            while ((linea = reader.readLine()) != null) {
                log.append(linea).append("\n");
            }
            reader.close();
            return log.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //METODO PARA ESCRIBIR EN TXT
    public static void escribirTxt(String ruta, String log) {
        try (FileWriter writer = new FileWriter("./src/main/resources/archivos/" + ruta + ".txt", true)) {
            writer.write(log + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
