/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author ervi
 */
public class IndiceListaUsuario {
    
     public int registro;
     public String posicion;
     public String nombreLista;
     public String usuario;
     public String usuarioAsociado;
     public int siguiente;
     public String status;
     
     
    public static String padLeft(String str, int length, String padChar) 
    {
        String pad = "";
        for (int i = 0; i < length; i++) 
        {
            pad += padChar;
        }
    return pad.substring(str.length()) + str;
    }
    
    public IndiceListaUsuario()
    {
        this.registro =0;
        this.posicion ="";
        this.nombreLista ="";
        this.usuario ="";
        this.usuarioAsociado = "";
        this.siguiente = 0;
        this.status = "";
        
    }

    public IndiceListaUsuario(int registro, String posicion, String lista, String user, String userAsociate, int next, String status)
    {
        this.registro = registro;
        this.posicion = posicion;
        this.nombreLista = lista;
        this.usuario = user;
        this.usuarioAsociado = userAsociate;
        this.siguiente = next;
        this.status = status;
    }  
    
    public IndiceListaUsuario(String[] linea)
    {        
        this.registro = Integer.valueOf(linea[0]);
        this.posicion = linea[1];
        this.nombreLista = linea[2];
        this.usuario = linea[3];
        this.usuarioAsociado = linea[4];
        this.siguiente = Integer.valueOf(linea[5]);
        this.status = linea[6];
    }  
        
        
    public String ConvertirATextoTamañoFijo()
    {
            StringBuilder sb = new StringBuilder();

            sb.append(registro);
            sb.append("|");
            sb.append(posicion);
            sb.append("|");
            sb.append(nombreLista);
            sb.append("|");
            sb.append(usuario);
            sb.append("|");
            sb.append(usuarioAsociado);
            sb.append("|");
            sb.append(siguiente);
            sb.append("|");
            sb.append(status);
            return sb.toString();
    }
    
    public String ConvertirATextoTamañoFijo(String[] linea)
    {
            StringBuilder sb = new StringBuilder();

            sb.append(linea[0]);
            sb.append("|");
            sb.append(linea[1]);
            sb.append("|");
            sb.append(linea[2]);
            sb.append("|");
            sb.append(linea[3]);
            sb.append("|");
            sb.append(linea[4]);
            sb.append("|");
            sb.append(linea[5]);
            sb.append("|");
            sb.append(linea[6]);
            return sb.toString();
    }
    
    

    public String ToString()
    {
            return ConvertirATextoTamañoFijo();
    }
        
    public String[] obtenerUsuarioSplit() throws IOException{
        
        String inicio = IndexSecuencial.ObtenerInicio();
        FileReader fr = new FileReader("C:\\MEIA\\indice_lista_usuario.txt");
        BufferedReader br = new BufferedReader(fr);
        
        List<String> datos = br.lines().collect(Collectors.toList());
        
        String usuario = datos.get(Integer.parseInt(inicio) - 1);
        String[] usuarioEnviar = usuario.split("\\|");
        
        return usuarioEnviar;
    }
}
