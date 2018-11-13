/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Bryan Mejía
 */
public class Utilidades {
   
    /**
     * Ordena los datos y los introduce en Master
     * @param bitacora
     * @param Master
     * @return
     * @throws IOException 
     */
    public static boolean VolcarMaster(String Master) 
            throws IOException{
        
        File tmpDir = new File("C:\\MEIA\\tempVolcado.txt");
        
        //se abre un buffer hacia la bitacora
        String pathBitacora = "C:\\MEIA\\bitacora_" + Master + ".txt";
        FileReader fr = new FileReader(pathBitacora);
        BufferedReader br = new BufferedReader(fr);
        
        List<String> datosBitacora = br.lines().collect(Collectors.toList());
        
        //se leen los datos de master
        String pathMaster = "C:\\MEIA\\" + Master + ".txt";
        fr = new FileReader(pathMaster);
        br = new BufferedReader(fr);
        
        List<String> datosMaster = br.lines().collect(Collectors.toList());
        
        br.close();
        
        if (datosMaster != null) {
            
            //ordena los datos
            datosMaster.addAll(datosBitacora);
            
            Collections.sort(datosMaster);
            
            FileWriter fw = new FileWriter(tmpDir);
            BufferedWriter bw = new BufferedWriter(fw);
                              
            for (int i = 0; i < datosMaster.size(); i++) {
                //validar si status es 1, de lo contrario no agregar
                
                String[] datos = datosMaster.get(i).replace("&", "")
                        .split("\\|");
                
                if (!datos[8].equals("0")) {
                    bw.append(datosMaster.get(i) + "\r\n");
                }
                
                
            }
            
            bw.close();
            
            File master = new File(pathMaster);          
            File bitacora = new File(pathBitacora);
            FileWriter Escribir = new FileWriter(bitacora,false);
            Escribir.close();
            master.delete();
            renameFile(tmpDir, Master);
            
        }
        
        
       return true;
              
    }
    
    public static boolean VolcarMasterListas(String Master) 
            throws IOException{
        
        File tmpDir = new File("C:\\MEIA\\tempVolcado.txt");
        
        //se abre un buffer hacia la bitacora
        String pathBitacora = "C:\\MEIA\\bitacora_" + Master + ".txt";
        FileReader fr = new FileReader(pathBitacora);
        BufferedReader br = new BufferedReader(fr);
        
        List<String> datosBitacora = br.lines().collect(Collectors.toList());
        
        //se leen los datos de master
        String pathMaster = "C:\\MEIA\\" + Master + ".txt";
        fr = new FileReader(pathMaster);
        br = new BufferedReader(fr);
        
        List<String> datosMaster = br.lines().collect(Collectors.toList());
        
        br.close();
        
        if (datosMaster != null) {
            
            //ordena los datos
            datosMaster.addAll(datosBitacora);
            
            Collections.sort(datosMaster);
            
            FileWriter fw = new FileWriter(tmpDir);
            BufferedWriter bw = new BufferedWriter(fw);
                              
            for (int i = 0; i < datosMaster.size(); i++) {
                //validar si status es 1, de lo contrario no agregar
                
                String[] datos = datosMaster.get(i).replace("&", "")
                        .split("\\|");
                
                if (!datos[5].equals("0")) {
                    bw.append(datosMaster.get(i) + "\r\n");
                }
                
                
            }
            
            bw.close();
            
            File master = new File(pathMaster);          
            File bitacora = new File(pathBitacora);
            FileWriter Escribir = new FileWriter(bitacora,false);
            Escribir.close();
            master.delete();
            renameFile(tmpDir, Master);
            
        }
        
        
       return true;
              
    }
    
    public static void renameFile(File toBeRenamed, String new_name)
    throws IOException {
    //need to be in the same path
    File fileWithNewName = new File(toBeRenamed.getParent(), new_name + ".txt");
    if (fileWithNewName.exists()) {
        throw new IOException("file exists");
    }
    // Rename file (or directory)
    boolean success = toBeRenamed.renameTo(fileWithNewName);
    if (!success) {
        // File was not successfully renamed
    }
}
}
  
