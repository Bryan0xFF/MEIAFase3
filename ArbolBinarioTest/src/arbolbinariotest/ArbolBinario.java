/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolbinariotest;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Bryan Mejía
 */
public class ArbolBinario {
    
    Nodo Raiz;
    
    //se empieza la logica de insertar en el arbol
    
    public ArbolBinario() {
        
        try{
            
            Raiz = ObtenerRaiz();
            CrearArchivos();  
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    private void CrearArchivos() throws IOException{
        
        File file = new File("C:\\MEIA\\Datos.ABB");
        file.createNewFile();
        file = new File("C:\\MEIA\\Datos_Master.ABB");        
        file.createNewFile();
    }
    
    private Nodo ObtenerRaiz() {
        
        try{
            
            String path = "C:\\MEIA\\Datos.ABB";
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr); 
        
            String dato = br.readLine();
            
            br.close();
            
            if(dato == null){
            
             return null;
            }
            
            return Serialize.deserialize(dato);
        
        }catch(Exception e){
            
             e.printStackTrace();
             return null;
        }
    }
    

    
    public void InsertarMaster(String datoSerializado) throws IOException{
        
        FileWriter fw = new FileWriter("C:\\MEIA\\Datos_Master.ABB", true);
        BufferedWriter bw = new BufferedWriter(fw);
        
        //se escribe solo la RAW data sin apuntadores en el master
        bw.append(Serialize.GetDataOnly(datoSerializado));
        bw.flush();
        bw.close();
        
    }
    /**
     * al iniciar la insercion, se debe poner root y regPadre en 1
     * @param datoSerializado
     * @param root
     * @param regPadre
     * @return
     * @throws IOException
     * @throws Exception 
     */
    public boolean Insertar(String datoSerializado, int root, int regPadre, boolean pos) throws IOException, Exception{
        
        Nodo insertar = new Nodo(datoSerializado);
        Nodo temp = Nodo.ObtenerNodo(root);
        
        // no hay dato
        if (temp == null && Raiz == null) {
            
            Raiz = insertar;
            Escribir(Raiz.dato, "C:\\MEIA\\Datos.ABB");
            return true;
   
        }
        
        if (temp == null && Raiz != null) {
            
            
            //Se busca el numero de registro en Master
            FileReader fr = new FileReader("C:\\MEIA\\Datos_Master.ABB");
            BufferedReader br = new BufferedReader(fr);
            
            List<String> datos = br.lines().collect(Collectors.toList());
            br.close();
            
            int datoNuevo = datos.size() - 1;
            
            //se busca el nodo padre para modificar apuntadores
            Nodo modificar = Nodo.ObtenerNodo(regPadre);
            
            if (pos == false) {
                
                //se debe modificar el izquierdo
                modificar.izquierdo = Integer.toString(datoNuevo);
                
            }
            
            if (pos == true) {
                
                //se debe modificar el derecho
                modificar.derecho = Integer.toString(datoNuevo);
            }
            
            //TODO: Sobreescribir
            this.Sobreescribir(Integer.toString(datos.size()), Integer.toString(regPadre), pos);
            this.Escribir(datoSerializado, "C:\\MEIA\\Datos.ABB");
            return true;
        }
        
        //el dato es mas grande que el dato en el nodo actual
        if(Compare.Comparar(datoSerializado, temp.dato) > 0){
            String puntero = temp.derecho.replace("&", "");
            Insertar(datoSerializado, Integer.parseInt(puntero), root, true);
            return true;
        }
        
        //el dato es mas pequenio que el dato en el nodo actual
        if(Compare.Comparar(datoSerializado, temp.dato) < 0){
            String puntero = temp.izquierdo.replace("&", "");
            Insertar(datoSerializado, Integer.parseInt(puntero), root,false);
            return true;
        }
        
     
        return false;
    }
    
    //Recibe el dato ya serializado con los apuntadores cambiados (de ser necesario)
    //AQUI NO VA LA LOGICA DE CAMBIAR APUNTADORES, ESTE METODO SOLO INSERTA HOJAS
    private void Escribir(String Serialize, String path){
        
       try{
           //se escribe en Datos.ABB
           FileWriter fw = new FileWriter(path, true);
           BufferedWriter bw = new BufferedWriter(fw);
           
           bw.append(Serialize);
           bw.flush();
           bw.close();
           
       }catch(Exception e){
           e.printStackTrace();
       } 
        
    }
    
    
    /**
     * Modifica apuntadores y registro en el archivo de forma logica
     * apuntadorRegistro es el # de registro del nuevo dato
     * opcion es: false = izq, true = derecho
     * apuntadorActual es el nodo en el cual se deben modificar los apuntadores
     * obtener el # cada vez que se cambie de nodo
     * @param datoSerializado
     * @param apuntadorRegistro
     * @param apuntadorActual
     * @param opcion 
     */
    private void Sobreescribir(String apuntadorRegistro, String apuntadorActual, boolean opcion){
        
        try{
            
            RandomAccessFile RAF = null;
            int offset = 0;
            
            FileReader fr = new FileReader("C:\\MEIA\\Datos.ABB");
            BufferedReader br = new BufferedReader(fr);
            
            List<String> datos = br.lines().collect(Collectors.toList());
            
            String datoModificar = datos.get(Integer.parseInt(apuntadorActual) -1);
            
            //es el derecho a modificar
            if (opcion == true) {
                
                String[] split = datoModificar.replace("&", "").trim().split("\\|");
                split[1] = apuntadorRegistro;
                
                //se actualiza el dato a modificar
                datoModificar = Serialize.serializar(split[0], split[1],  split[2],  split[3],  split[4],
                         split[5],  split[6],  split[7]);
                
            }
            
            if (opcion == false) {
                
                String[] split = datoModificar.replace("&", "").trim().split("\\|");
                split[0] = apuntadorRegistro;
                
                //se actualiza el dato a modificar
                datoModificar = Serialize.serializar(split[0], split[1],  split[2],  split[3],  split[4],
                         split[5],  split[6],  split[7]);
                
            }
            br.close();
            
            offset = Integer.parseInt(apuntadorActual) -1;
            
            
            RAF = new RandomAccessFile("C:\\MEIA\\Datos.ABB", "rw");
            RAF.seek(offset * Serialize.TamanioFijo());
            long pointer = RAF.getFilePointer();
            RAF.write(datoModificar.getBytes());
            RAF.close();
            
            //TODO: modificar registro en Datos.ABB
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
}

class Serialize {
    
    public static String serializar(String izq, String der,String usuario_emisor, String usuario_receptor, String Fec_Trans, String asunto, String mensaje,
            String adjunto){
        
        StringBuilder result = new StringBuilder();
        
        if (izq.equals("-1") || izq.equals("")) {
            
            result.append("-1");
            
        }else{
            result.append(ToFixedSizeString(izq, 2));
        }
        
        result.append("|");
        
         if (der.equals("-1") || der.equals("")) {
            
            result.append("-1");
            
        }else{
            result.append(ToFixedSizeString(der, 2));
        }
       
        result.append("|");
        result.append(ToFixedSizeString(usuario_emisor, 20));
        result.append("|");
        result.append(ToFixedSizeString(usuario_receptor, 20));
        result.append("|");
        result.append(ToFixedSizeString(Fec_Trans, 34));
        result.append("|");
        result.append(ToFixedSizeString(asunto, 30));
        result.append("|");
        result.append(ToFixedSizeString(mensaje, 150));
        result.append("|");
        result.append("1");
        result.append("\r\n");
        return result.toString();
        
    }
    
    public static String SerializarNodo(Nodo nodo){
        
        StringBuilder result = new StringBuilder();
        
        if (nodo.izquierdo.equals("-1") || nodo.izquierdo.equals("")) {
            
            result.append("-1");
            
        }else{
            result.append(nodo.izquierdo);
        }
        
        result.append("|");
        
         if (nodo.derecho.equals("-1") || nodo.derecho.equals("")) {
            
            result.append("-1");
            
        }else{
            result.append(nodo.derecho);
        }
         
         String[] split = nodo.dato.trim().split("\\|");
         
        result.append("|");
        result.append(ToFixedSizeString(split[0], 20));
        result.append("|");
        result.append(ToFixedSizeString(split[1], 20));
        result.append("|");
        result.append(ToFixedSizeString(split[2], 34));
        result.append("|");
        result.append(ToFixedSizeString(split[3], 30));
        result.append("|");
        result.append(ToFixedSizeString(split[4], 150));
        result.append("|");
        result.append(ToFixedSizeString(split[5], 1));
        result.append("\r\n");
        return result.toString();
        
    }
    
    public static Nodo deserialize(String datosSerializados){
        
        Nodo nodo = null;
        
        String[] split = datosSerializados.trim().split("\\|");
        StringBuilder sb = new StringBuilder();
        sb.append(split[0]);
        sb.append("|");
        sb.append(split[1]);
        sb.append("|");
        sb.append(split[2]);
        sb.append("|");
        sb.append(split[3]);
        sb.append("|");
        sb.append(split[4]);
        sb.append("|");
        sb.append(split[5]);
        sb.append("|");
        sb.append(split[6]);
        sb.append("|");
        sb.append(split[7]);
        
        String izq = split[0];
        String der = split[1];
        
        nodo = new Nodo(izq, der, sb.toString());
        return nodo;
    }
    
    public static int TamanioFijo(){
        return 275;
    }
    
    public static String ToFixedSizeString(String word, int count) {
    String result = ""; 	
    int complement = count - word.length();
      for(int i = 0; i < complement; i++) {
	result += "&";
      }
        return result+word;        
    }
    
    /**
     * obtiene los datos sin los apuntadores a otros nodos
     * @param datosSerializados
     * @return 
     */
    public static String GetDataOnly(String datosSerializados){
        
        String[] split = datosSerializados.trim().split("\\|");
        StringBuilder sb = new StringBuilder();
        sb.append(split[2]);
        sb.append("|");
        sb.append(split[3]);
        sb.append("|");
        sb.append(split[4]);
        sb.append("|");
        sb.append(split[5]);
        sb.append("|");
        sb.append(split[6]);
        sb.append("|");
        sb.append(split[7]);
        sb.append("\r\n");
        return sb.toString();
        
    }
}

class Compare {
    
    public static int Comparar(String datoNodoInsertar, String datoNodoActual) throws Exception{
        
        String[] datosInsertar = datoNodoInsertar.replace("&", "").trim().split("\\|");
        String[] datosActual = datoNodoActual.replace("&","").trim().split("\\|");
        
        if (datosInsertar[2].compareTo(datosActual[2]) == 0) {
             
            if (datosInsertar[3].compareTo(datosActual[3]) == 0) {
                
                return datosInsertar[4].compareTo(datosActual[4]);    
            }
            
            return datosInsertar[3].compareTo(datosActual[3]);
            
        }
        
        return datosInsertar[2].compareTo(datosInsertar[2]);
        
    }
    
}
