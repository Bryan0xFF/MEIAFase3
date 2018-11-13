/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.IOException;
import java.util.Date;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;

/**
 *
 * @author Bryan Mejía
 */
public class Lista {
    
   public String nombre_lista;
   public String usuario;
   public String descripcion;
   public String numero_usuarios;
   public String fecha_creacion;
   public String status;
    
    //llave primaria compuesta por Nombre_lista y usuario
    
    
    public Lista() {
        
    }    
    
    public Lista(String nombre_lista, Usuario usuario, String descripcion) throws Exception{
        
        this.nombre_lista = nombre_lista;
        this.usuario = usuario.getUsuario();
        this.descripcion = descripcion;
        //campos default
        this.numero_usuarios = "0";
        this.status = "1";
        
       //formateamos la fecha:
      Date date = Calendar.getInstance().getTime();
        
      DateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy, hh:mm:ss.SSS a");
      String today = formatter.format(date);
      
      this.fecha_creacion = today;
      
      
        Escribir("lista", setFixedSize(),usuario.getUsuario());
    }
    
    private String ToFixedSizeString(String word, int count) {
    String result = ""; 	
    int complement = count - word.length();
      for(int i = 0; i < complement; i++) {
	result += "&";
      }
        return result+word;        
    }
    
    public String setFixedSize(){
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(ToFixedSizeString(this.nombre_lista, 30));
        sb.append("|");
        sb.append(ToFixedSizeString(this.usuario, 20));
        sb.append("|");
        sb.append(ToFixedSizeString(this.descripcion, 40));
        sb.append("|");
        sb.append(ToFixedSizeString(this.numero_usuarios,4));
        sb.append("|");
        sb.append(ToFixedSizeString(this.fecha_creacion,38));
        sb.append("|");
        sb.append(ToFixedSizeString(this.status,1));
        sb.append("\r\n");
        return sb.toString();
    }
    
    public int getSize(){
        
        return 138;
    }
    /**
     * dato normalizado
     * @param nombreMaster 
     */
    public void Escribir(String nombreMaster, String datoIngresar, String nombreAdmin) throws IOException, Exception{
        //busca si existe un usuario con este nombre
        try{
            boolean flag = true;
            String path = "C:\\MEIA\\bitacora_" + nombreMaster + ".txt"; 
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            
            List<String> datos = br.lines().collect(Collectors.toList());
            String[] ingreso = datoIngresar.trim().split("\\|");
            
            for (int i = 0; i < datos.size(); i++) {
                
                String[] datosSplit = datos.get(i).trim().split("\\|");
                if (datosSplit[0].equals(ingreso[0]) && datosSplit[1].equals(ingreso[1])) {
                    throw new Exception("ya existe la lista");
                }
            }
            
            Secuencial.Escribir(datoIngresar,"lista",nombreAdmin);
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public static DefaultListModel<String> BuscarLista(String idUsuario) throws IOException{
        
        //se busca primero en la bitacora
        String path = "C:\\MEIA\\bitacora_lista.txt";
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        DefaultListModel datosEnviar = new DefaultListModel();
        
        List<String> datos = br.lines().collect(Collectors.toList());
        br.close();
        
         for (int i = 0; i < datos.size(); i++) {
             String trim = Classes.Usuario.fromFixedSizeString(datos.get(i)); 
             String[] split = trim.split("\\|");
             
             if (split[1].equals(idUsuario)) {
                 datosEnviar.addElement(split[0]);
             }
         }
         
         //se busca luego en Master
          path = "C:\\MEIA\\lista.txt";
          fr = new FileReader(path);
          br = new BufferedReader(fr);
          
          datos = br.lines().collect(Collectors.toList());
          br.close();
          
          for (int i = 0; i < datos.size(); i++) {
             String trim = Classes.Usuario.fromFixedSizeString(datos.get(i)); 
             String[] split = trim.split("\\|");
             
             if (split[1].equals(idUsuario)) {
                 datosEnviar.addElement(split[0]);
             }
         }
        return datosEnviar;
    }
    
    public static Lista ObtenerLista(String id, String name) throws IOException {
        //se busca primero en la bitacora
        String path = "C:\\MEIA\\bitacora_lista.txt";
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        Lista datosEnviar = new Lista();
        
        List<String> datos = br.lines().collect(Collectors.toList());
        br.close();
        
         for (int i = 0; i < datos.size(); i++) {
             String trim = Classes.Usuario.fromFixedSizeString(datos.get(i)); 
             String[] split = trim.split("\\|");
             
             if (split[0].equals(name) && split[1].equals(id)) {
                 datosEnviar.nombre_lista = split[0];
                 datosEnviar.usuario = split[1];
                 datosEnviar.descripcion = split[2]; 
                 datosEnviar.numero_usuarios = split[3]; 
                 datosEnviar.fecha_creacion = split[4]; 
                 datosEnviar.status = split[5];                  
             }
         }
         
         //se busca luego en Master
          path = "C:\\MEIA\\lista.txt";
          fr = new FileReader(path);
          br = new BufferedReader(fr);
          
          datos = br.lines().collect(Collectors.toList());
          br.close();
          
          for (int i = 0; i < datos.size(); i++) {
             String trim = Classes.Usuario.fromFixedSizeString(datos.get(i)); 
             String[] split = trim.split("\\|");
             
             if (split[0].equals(name) && split[1].equals(id)) {
                 datosEnviar.nombre_lista = split[0];
                 datosEnviar.usuario = split[1];
                 datosEnviar.descripcion = split[2]; 
                 datosEnviar.numero_usuarios = split[3]; 
                 datosEnviar.fecha_creacion = split[4]; 
                 datosEnviar.status = split[5];                  
             }
         }
        return datosEnviar;
    }
    
    public static boolean SobreescribirLista
        (String fixedSize, String id, String master, int tamanio, String usuario) throws IOException{
        
         RandomAccessFile RAF = null;
         
         //se empieza por buscar en la bitacora
         FileReader fr = new FileReader("C:\\MEIA\\bitacora_" + master + ".txt");
         BufferedReader br = new BufferedReader(fr);
         
         int count = 0; //sirve para offset
         
         int FixedSize = tamanio;
         
         List<String> datosTemp = br.lines().collect(Collectors.toList());
         
         for (int i = 0; i < datosTemp.size(); i++) {
            
             String[] linea = datosTemp.get(i).replace("&", "").split("\\|");
             
             if (linea[0].equals(id)) {
                 if (linea[1].equals(usuario)) {
                     
                 //se cierra para abrir RAF
                 br.close();
                 RAF = new RandomAccessFile("C:\\MEIA\\bitacora_" + master + ".txt","rw");
                 RAF.seek((FixedSize * count));
                 long pointer = RAF.getFilePointer();
                 RAF.write(fixedSize.getBytes());
                 RAF.close();
                
                 return true;
                 }
                
             }else{
                 count = count + 1;
             }
        }
         
        count = 0;
        
        //se abre un stream hacia Master
        fr = new FileReader("C:\\MEIA\\" + master + ".txt");
        br = new BufferedReader(fr);
        
        List<String> datosMaster = br.lines().collect(Collectors.toList());
        
        for (int i = 0; i < datosMaster.size(); i++) {
            
             String[] linea = datosMaster.get(i).replace("&", "").split("\\|");
             
             if (linea[0].equals(id)) {
                 if (linea[1].equals(usuario)) {
                 //se cierra para abrir RAF
                 br.close();
                 RAF = new RandomAccessFile("C:\\MEIA\\" + master + ".txt","rw");
                 RAF.seek((FixedSize * count));
                 long pointer = RAF.getFilePointer();
                 RAF.write(fixedSize.getBytes());
                 RAF.close();
                
                 return true;
                 }  
             }else{
                 count = count + 1;
             }
        }
        
         return false;
    }
    
}
