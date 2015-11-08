import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by a on 8/11/15.
 */
public class pasaporte {
    String path;
    String[] Datos = new String [7];

    public pasaporte(String s){
        path = s;
        try {
            llamarTerminalV2();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Analizar();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public  void Analizar() throws Exception{

        int z;
        String FileName = "original.txt";

        FileReader file= new FileReader(FileName);
        BufferedReader reader = new BufferedReader(file);

        String text="";
        String line = reader.readLine();

        while(line !=null){
            text+=line;
            line=reader.readLine();
        }

        System.out.println(text);

        z=0;

        try {
            while (!(text.charAt(z) == 'P' && (text.charAt(z + 1) == '<'))) {
                z++;
            }
        }catch (StringIndexOutOfBoundsException e){
            System.out.println("***********************************");
            JOptionPane.showMessageDialog(null, "Hubo un error con la calidad de la imagen en " + path);
            return;

        }


        text=text.substring(z);
        //text=text.substring(0, 87);
        getFields(text);

        System.out.println(text);

        /*Scanner stdin = new Scanner (System.in);
        String a = stdin.next();
        getFields(text);
        for(int j = 0; j < fields.length; j++)
            System.out.println(fields[j]);*/

    }

    public  void getFields(String code) {
        int count = 0;
        int i, j;

        for(i = 5; i < 44; i++){
            if(code.charAt(i) == '<' && code.charAt(i + 1) == '<')
                break;
        }
        Datos[count++] = code.substring(5, i).replace('<', ' ');
        for(j = i+2; j < 44; j++){
            if(code.charAt(j) == '<' && code.charAt(j + 1) == '<')
                break;
        }
        Datos[count++] = code.substring(i+2, j).replace('<', ' ');
        Datos[count++] = code.substring(44, 52);
        Datos[count++] = code.substring(54, 57);
        Datos[count++] = code.substring(61, 63) + "/" + code.substring(59, 61) + "/" + code.substring(57, 59);
        if(code.charAt(64) == 'M' || code.charAt(64) == 'H')
            Datos[count++] = "MASCULINO";
        else if (code.charAt(64) == 'F')
            Datos[count++] = "FEMENINO";
        else
            Datos[count++] = "INDEFINIDO";
        Datos[count++] = code.substring(67, 69) + "/" + code.substring(65, 67);

        for(int m = 0; m < 7; m++){
            System.out.println(Datos[m]);
        }

    }

    public void llamarTerminalV2()throws IOException, InterruptedException{
        // Runtime.getRuntime().exec("/bin/bash -c tesseract jurgen.jpg si");
        String command = "tesseract ";
        command += path;
        command += " original";

        Process proc = Runtime.getRuntime().exec(command);

        // Read the output

        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        String line = "";
        /*while((line = reader.readLine()) != null) {
            System.out.print(line + "\n");
        }*/
        proc.waitFor();
    }

}
