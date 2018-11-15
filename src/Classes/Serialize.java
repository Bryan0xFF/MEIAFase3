/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author rodri
 */
public class Serialize {
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
        result.append(ToFixedSizeString(Fec_Trans, 50));
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
        result.append(ToFixedSizeString(split[2], 50));
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
        return 284;
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
