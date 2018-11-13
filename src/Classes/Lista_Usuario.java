/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Bryan Mejía
 */
public class Lista_Usuario {
    
    String nombre_lista;
    String usuario;
    String usuario_asociado;
    String descripcion;
    String fecha_creacion;
    String status;
    
    public Lista_Usuario(String nombre, String usuario, String Usuario_asociado, String descripcion) throws Exception{
        nombre_lista = nombre;
        this.usuario = usuario;
        this.usuario_asociado = Usuario_asociado;
        this.descripcion = descripcion;
        
         //formateamos la fecha:
      Date date = Calendar.getInstance().getTime();
        
      DateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy, hh:mm:ss.SSS a");
      String today = formatter.format(date);
      
      this.fecha_creacion = today;
      status = "1";      
      
      Escribir("lista_usuario", setFixedSizeString(),usuario);
    }
    
    private String ToFixedSizeString(String word, int count) {
    String result = ""; 	
    int complement = count - word.length();
      for(int i = 0; i < complement; i++) {
	result += "&";
      }
        return result+word;        
    }
    
    /**
     * este es para master
     * @return 
     */
    public String setFixedSizeString(){
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(ToFixedSizeString(this.nombre_lista, 30));
        sb.append("|");
        sb.append(ToFixedSizeString(this.usuario, 20));
        sb.append("|");
        sb.append(ToFixedSizeString(this.usuario_asociado, 20));
        sb.append("|");
        sb.append(ToFixedSizeString(this.descripcion,40));
        sb.append("|");
        sb.append(ToFixedSizeString(this.fecha_creacion,38));
        sb.append("|");
        sb.append(ToFixedSizeString(this.status,1));
        sb.append("\r\n");
        
        return sb.toString();
    }
    
    public int getFixedSize(){
        return 154;
    }
    
    public static String toFixedSizeString(String[] array){
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < (array.length - 1)) {
               sb.append("|");
            }
        }
        
        return sb.toString();
    }
    
    /**
     * este es para indice
     * @param listaSize
     * @param nombre_lista
     * @param usuario
     * @param usuarioAsociado
     * @return 
     */
    
    public String FillFixedSizeString(String word, int count) {
    String result = ""; 	
    int complement = count - word.length();
      for(int i = 0; i < complement; i++) {
	result += "&";
      }
        return result+word;        
    }
    
    public static String setData(int listaSize, String nombre_lista, String usuario,
            String usuarioAsociado){
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(Integer.toString(listaSize + 1));
        sb.append("|");
        sb.append("1.").append(Integer.toString(listaSize + 1));
        sb.append("|");
        sb.append(nombre_lista);
        sb.append("|");
        sb.append(usuario);
        sb.append("|");
        sb.append(usuarioAsociado);
        sb.append("|");
        sb.append("-1");
        sb.append("|");
        sb.append("1");
        
        
        return sb.toString();  
    }
    
     public void Escribir(String nombreMaster, String datoIngresar, String nombreAdmin) throws IOException, Exception{
        //busca si existe un usuario con este nombre
        try{
            boolean flag = true;
            String path = "C:\\MEIA\\" + nombreMaster + ".txt"; 
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            
            List<String> datos = br.lines().collect(Collectors.toList());
            String[] ingreso = datoIngresar.trim().split("\\|");
            br.close();
            for (int i = 0; i < datos.size(); i++) {
                
                String[] datosSplit = datos.get(i).trim().split("\\|");
                if (datosSplit[0].equals(ingreso[0]) && datosSplit[1].equals(ingreso[1]) && datosSplit[2].equals(ingreso[2])) {
                    throw new Exception("ya existe el usuario en esa lista");
                }
            }
            
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.append(datoIngresar);
            bw.append("\r\n");
            bw.flush();
            bw.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
