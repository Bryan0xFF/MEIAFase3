/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import static java.awt.image.ImageObserver.WIDTH;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JOptionPane;


/**
 *
 * @author rodri
 */
public class IndexSecuencial {
    
    public IndexSecuencial() throws IOException{
        CrearDescIndice("lista_usuario");
        CrearMaster();
    }
    
    private int countActives(List<String> list) {
        int cont = 0, number = 0; 
        
        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i); 
            String[] split = line.split("\\|"); 
            number = Integer.valueOf(split[6]);
            if(number == 1) {
                cont++;
            }            
        }
        return cont; 
    }
    
    private int countDeActives(List<String> list) {
        int cont = 0, number = 0; 
        
        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i); 
            String[] split = line.split("\\|"); 
            number = Integer.valueOf(split[6]);
            if(number == 0) {
                cont++;
            }            
        }
        return cont; 
    }
    
    public void BorrarLineas(String linea1,String linea2,String lineaAgregar1,String lineaAgregar2)
    {
        try  
        {
            
            List<String> miLista = new ArrayList<>();
            File Archivo = new File("C:\\MEIA\\indice_lista_usuario.txt");
            BufferedReader br2 = new BufferedReader(new FileReader(Archivo));
            String last = br2.readLine(); 
            while (last != null) 
            { 
                miLista.add(last);
                last = br2.readLine(); 
            } 
            br2.close();
            vaciarArchivo();
            miLista.remove(Integer.valueOf(linea1)-1);           
            miLista.add(lineaAgregar1);
            miLista.add(lineaAgregar2);
            Collections.sort(miLista, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

        int extractInt(String s) {
            String num = s.replaceAll("\\D", "");
            return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
         });

            for ( String lineas : miLista ) 
            {
                LlenarArchivo("C:\\MEIA\\indice_lista_usuario.txt",lineas,"ERROR");
                
            }
            
        }
        catch(Exception ex)
        {
            
        }
    }
    
    private boolean CrearDescIndice(String nombreMaster) throws IOException{
        
        File file = new File("C:\\MEIA\\desc_Indice_" + nombreMaster +".txt");
        boolean flag = file.createNewFile();
        
       if(flag) {
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write("Num_reg: " + "0");
        bw.write("\r\n");
        bw.write("Reg_inicio: " + "1");
        bw.write("\r\n");
        bw.write("reg_activos: "+"0");
        bw.write("\r\n");
        bw.write("Reg_inact: " + "0");
        
        bw.flush();
        bw.close();
        
       } else {           
            
       }
        
        return flag;
    }
    
    private static boolean CrearMaster() throws IOException{
        
        File file = null;
        String data = "";
       
        data = "C:\\MEIA\\indice_lista_usuario.txt";
        
        file = new File(data);
        
        boolean flag = file.createNewFile();
        
        return flag;
    }
    
  
    public void BorrarLineas2(String linea1,String lineaAgregar1,String lineaAgregar2)
    {
        try  
        {
            int numero = 0;
            List<String> miLista = new ArrayList<>();
            File Archivo = new File("C:\\MEIA\\indice_lista_usuario.txt");
            BufferedReader br2 = new BufferedReader(new FileReader(Archivo));
            String last = br2.readLine(); 
            while (last != null) 
            { 
                miLista.add(last);
                last = br2.readLine();
                numero++;
            } 
            br2.close();
            vaciarArchivo();
            miLista.remove(Integer.valueOf(linea1)-1);
            miLista.add(lineaAgregar1);
            miLista.add(lineaAgregar2);
            Collections.sort(miLista, new Comparator<String>() {
        public int compare(String o1, String o2) {
            return extractInt(o1) - extractInt(o2);
        }

        int extractInt(String s) {
            String num = s.replaceAll("\\D", "");
            return num.isEmpty() ? 0 : Integer.parseInt(num);
        }
         });
            for ( String lineas : miLista ) 
            {
                int numero2 = 0;
                if (numero2<=numero)
                {
                    LlenarArchivo("C:\\MEIA\\indice_lista_usuario.txt",lineas,"ERROR");
                    numero2++;
                }
                
            }
            
        }
        catch(Exception ex)
        {
            
        }
    }
        
    //usuario lo obtengo obteniendo el reg_inicio, usuario asociado obtener mediante form
    //posicion [2]
    public boolean escribir(String[] usuario, String[] usuarioAsociado,int primero,int posicion) throws IOException
    {
        File file = new File("C:\\MEIA\\indice_lista_usuario.txt");
        
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        List<String> datos = br.lines().collect(Collectors.toList());
        br.close();
        TablaIndices tabla = new TablaIndices(); 
                
        if(datos.isEmpty()) {
          FileWriter Escribir = new FileWriter(file,false);
          BufferedWriter bw = new BufferedWriter(Escribir);
          
          bw.append("1");
          bw.append("|");
          bw.append("1.1");
          bw.append("|");
          bw.append(usuarioAsociado[2]);//nombre de la lista
          bw.append("|");
          bw.append(usuarioAsociado[3]); //usuario
          bw.append("|");
          bw.append(usuarioAsociado[4]);//usuario asociado
          bw.append("|");
          bw.append("-1"); //apuntador
          bw.append("|");
          bw.append("1"); //status
          bw.append("|");
          bw.close();
          ModificarDesc();    
          return true; 
        }
        else {
            List<IndiceListaUsuario> listado = new ArrayList<>();
            IndiceListaUsuario newElement;
            int elementPosition = 0; 
            for (int i = 0; i < datos.size(); i++) {
                listado.add(new IndiceListaUsuario(datos.get(i).split("\\|")));
            }
            
            
            for (int i = 0; i < listado.size(); i++) {
                
                
                /////////////////////////////////////////////////////////
                
                switch (comparar(usuarioAsociado[posicion],usuario[posicion])) {
                    case 0: //En caso sean iguales y busca un diferente parametro de comparacion
                        posicion++; 
                        return escribir(usuario,usuarioAsociado,primero,posicion);
                    
                    case 1: //En caso sea menor el elemento que se insertará
                        elementPosition = getElement(usuarioAsociado, datos);                        
                        tabla = getTabla(elementPosition, listado);
                        
                        //Solo hay un elemento en la tabla, se inserta involucrando la raiz
                        if(tabla.previo == null && tabla.siguiente == null) {
                           newElement = new IndiceListaUsuario(usuarioAsociado);
                           newElement.siguiente = -1;
                           newElement.registro = listado.size() + 1;
                           listado.get(0).siguiente = 1;
                        }
                        
                        //Se inserta al final del archivo
                        if(tabla.previo != null && tabla.siguiente == null) {
                           newElement = new IndiceListaUsuario(usuarioAsociado);
                           newElement.siguiente = -1;
                           listado.get(listado.size() - 1).siguiente = listado.size() + 1;
                           newElement.registro = listado.size() + 1;
                           listado.add(newElement);                             
                        }
                        
                        //Se inserta en medio del archivo, involucrando dos elementos de la lista
                        if(tabla.previo != null && tabla.siguiente != null) {
                           newElement = new IndiceListaUsuario(usuarioAsociado);
                           newElement.siguiente = -1;
                           newElement.registro = listado.size() + 1; 
                           listado.get(tabla.previo.registro-1).siguiente = newElement.registro;
                           
                        }
                        
                        
                    
                    case 2: //En caso sea mayor el elemento que se insertará     
                }                
            }
       }
        return true;
    }
    
    public TablaIndices getTabla (int elementPosition, List<IndiceListaUsuario> listado) {
        TablaIndices tabla = new TablaIndices(); 
        
        if(elementPosition == 0) {
           if(listado.size() == 1) {
              tabla.actual = listado.get(0);
              tabla.previo = null;
              tabla.siguiente = null;
           }
            else {
              tabla.actual = listado.get(0); 
              tabla.siguiente = listado.get(1);
              tabla.previo = null;
            }
        }
         else if(elementPosition > 0) {
                tabla.previo = listado.get(elementPosition -1);
                tabla.actual = listado.get(elementPosition); 
                tabla.siguiente = listado.get(elementPosition+1);
                    
          }
          else if(elementPosition + 1 == listado.size()) {
                tabla.previo = listado.get(elementPosition -1);
                tabla.actual = listado.get(elementPosition); 
                tabla.siguiente = null;
          }
        return tabla;                 
    }
    
    public int getElement(String[] addingItem, List<String> lista) {
        boolean flag = true;
        int cont = 0;
        IndiceListaUsuario comp = new IndiceListaUsuario(addingItem); 
        String comparator = comp.ConvertirATextoTamañoFijo();
        int result = 0;
        
        while(flag) {
            if(!lista.get(cont).equals(comparator)) {
                cont++;
            }
            else if(lista.get(cont).equals(comparator)) {
                result = comparator.charAt(0);
            }   
        }
        return result;
    }
    
    public void agregar(String[] usuarioAgregar) throws IOException
    {
        //Este metodo sirve para obtener la linea donde empieza el archivo
        //En vez del 1 se tiene que mandar a leer primero el descriptor para obtener el registro de inicio\
        //luego de leerlo, se anda el parametro al metodo
        escribir(buscar2("C:\\MEIA\\indice_lista_usuario.txt","1"),usuarioAgregar,1,2);
    }
     
    public void LlenarArchivo(String strPath,String strContenido,String strError)
    {
        
        File Archivo5 = new File(strPath);
        
        try
        {
                FileWriter Escribir = new FileWriter(Archivo5,true);
                BufferedWriter bw = new BufferedWriter(Escribir);
                
                bw.write(strContenido + System.lineSeparator());
                bw.close();
                Escribir.close();
                
        }
        catch(IOException ex)
        {
            strError= ex.getMessage();
        } 
        
    }
    
    public int comparar(String primero, String segundo)
    {
        if (primero.compareTo(segundo)==0) {
            return 0;
        }
        else if(primero.compareTo(segundo)<0)
        {
            return 1;
        }
        else if(primero.compareTo(segundo)>0)
        {
            return 2;
        }
        else
            return 3;
    }    
    
    public String[] apuntadorSiguiente(int apuntador) throws IOException
    {
        try (Stream<String> lines = Files.lines(Paths.get("C:\\MEIA\\indice_lista_usuario.txt"))) 
        {
            String line = lines.skip(apuntador-1).findFirst().get();
            String[] especificLine = line.split("\\|");
            return especificLine;
        }
        catch(Exception ex)
        {
            return null;
        }
    }  
    
    public String quitarCaracteres(String textoEntero)
    {
        StringBuilder texto = new StringBuilder();
        char separador = '&';
        for(int i = 0;i<textoEntero.length(); i++)
        {
            char separador2 = textoEntero.charAt(i);
            if (!String.valueOf(separador).equals(String.valueOf(separador2))) {
                texto.append(separador2);
            }
        }
        
        return texto.toString();
    }
    
    public String[] buscar(String path, String siguiente) throws FileNotFoundException, IOException
    {
        String[] objeto2 = null;
        File Archivo = new File(path);
        BufferedReader br2 = new BufferedReader(new FileReader(Archivo));
        List<String> datos = br2.lines().collect(Collectors.toList());
        String last = br2.readLine(); 
        
        if(datos.size() > 1){
            
          while (last != null) 
           { 
            String[] objeto= last.split("\\|");
            String nuevo = quitarCaracteres(objeto[5]);
            if (nuevo.equals(siguiente)) {
                objeto2 = objeto;
            }
            
            last = br2.readLine(); 
           }
        }
        else {
            String line = datos.get(0);
            objeto2 = line.split("\\|");
        }
        
        br2.close();
        return retorno(objeto2);
    }
    
    public String[] buscar2(String path, String encontrar) throws FileNotFoundException, IOException
    {
        String[] objeto2 = null;
        File Archivo = new File(path);
        BufferedReader br2 = new BufferedReader(new FileReader(Archivo));
        String last = br2.readLine(); 
        while (last != null) 
        { 
            String[] objeto= last.split("\\|");
            String nuevo = quitarCaracteres(objeto[0]);
            if (nuevo.equals(encontrar)) {
                objeto2 = objeto;
            }
            
            last = br2.readLine(); 
        } 
        br2.close();
        return retorno(objeto2);
    }
    
    public String[] retorno(String[] array)
    {
        if (array!=null) {
        array[0] =quitarCaracteres(array[0]);
        array[1] =quitarCaracteres(array[1]);
        array[2] =quitarCaracteres(array[2]);
        array[3] =quitarCaracteres(array[3]);
        array[4] =quitarCaracteres(array[4]);
        array[5] =quitarCaracteres(array[5]);
        array[6] =quitarCaracteres(array[6]);
        return array;    
        }
        else
        {
            return array;
        }
        
    }
    
     public void vaciarArchivo() throws FileNotFoundException, IOException
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\MEIA\\indice_lista_usuario.txt"));
        bw.write("");
        bw.close();
    }
     
    public static String ObtenerInicio() throws IOException {        
        //se abre un buffer hacia el desc_indice para saber donde comenzar a leer
        String pathdescIndice = "C:\\MEIA\\desc_indice_lista_usuario.txt";
        FileReader fr = new FileReader(pathdescIndice);
        BufferedReader br = new BufferedReader(fr);
        
        List<String> datosDescIndice = br.lines().collect(Collectors.toList());
        String[] RegInicio = datosDescIndice.get(1).trim().split(": ");
        br.close();
        
        return RegInicio[1];
    }
    
    
    public static void Sobreescribir
        (String fixedSize, String id, int tamanio) throws IOException{
        
         RandomAccessFile RAF = null;
         
         //se empieza por buscar en la bitacora
         FileReader fr = new FileReader("C:\\MEIA\\indice_lista_usuario.txt");
         BufferedReader br = new BufferedReader(fr);
         
         int count = 0; //sirve para offset
         
         int FixedSize = tamanio + 2;
        
        
        List<String> datosMaster = br.lines().collect(Collectors.toList());
        
        for (int i = 0; i < datosMaster.size(); i++) {
            
             String[] old = datosMaster.get(i).replace("&", "").split("\\|");
             int contReg1 = Integer.valueOf(old[0]);
            
             
             String[] splited = fixedSize.replace("&", "").split("\\|"); 
             int contReg2 = Integer.valueOf(splited[0]);             
             
             if(contReg1 < contReg2) {
               
              contReg2--;   
              StringBuilder sb = new StringBuilder();
        
              sb.append(Integer.toString(contReg1));
              sb.append("|");
              sb.append("1.").append(Integer.toString(contReg2));
              sb.append("|");
              sb.append(old[2]);
              sb.append("|");
              sb.append(old[3]);
              sb.append("|");
              sb.append(old[4]);
              sb.append("|");
              sb.append("-1");
              sb.append("|");
              sb.append("1");
        
              //se cierra para abrir RAF
              br.close();
              RAF = new RandomAccessFile("C:\\MEIA\\indice_lista_usuario.txt","rw");
              RAF.seek((FixedSize * count));
              long pointer = RAF.getFilePointer();
              RAF.write(fixedSize.getBytes());
              RAF.close();
                 
             }
        }
    }
        
     /**
      * numReg: nuevo inicio
      * listaSize: cant. de registros
      * regAct: registros activos
      * regInact: registros Inactivos
      * @param numReg
      * @param listaSize
      * @param regAct
      * @param regInact
      * @return
      * @throws IOException 
      */ 
    private boolean ModificarDescIndice(String numReg) throws IOException{
        
        String path = "C:\\MEIA\\desc_indice_lista_usuario.txt";
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        
        List<String> datos = br.lines().collect(Collectors.toList());
        br.close();
        
        String[] override = datos.get(1).trim().split(": ");
        numReg = override[1];
        
         List<String> miLista = new ArrayList<>();
            File Archivo = new File("C:\\MEIA\\indice_lista_usuario.txt");
            BufferedReader br2 = new BufferedReader(new FileReader(Archivo));
            String last = br2.readLine(); 
            while (last != null) 
            { 
                miLista.add(last);
                last = br2.readLine(); 
            } 
            br2.close();
        
        int activos = countActives(miLista); 
        int inActivos = countDeActives(miLista); 
        int total = activos + inActivos; 
        
        File file = new File(path);
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write("Num_reg: " + total);
        bw.write("\r\n");
        bw.write("Reg_inicio: " + numReg);
        bw.write("\r\n");
        bw.write("reg_activos: "+ activos);
        bw.write("\r\n");
        bw.write("Reg_inact: " + inActivos);
            
        bw.flush();
        bw.close();
        
        return true;
        
    }
    
    private boolean ModificarDesc() throws IOException{
        
        String path = "C:\\MEIA\\desc_indice_lista_usuario.txt";
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        String numReg;
        List<String> datos = br.lines().collect(Collectors.toList());
        br.close();
        
        String[] override = datos.get(1).trim().split(": ");
        numReg = override[1];
        
         List<String> miLista = new ArrayList<>();
         File Archivo = new File("C:\\MEIA\\indice_lista_usuario.txt");
         BufferedReader br2 = new BufferedReader(new FileReader(Archivo));
         String last = br2.readLine(); 
         while (last != null) 
           { 
              miLista.add(last);
             last = br2.readLine(); 
           } 
          br2.close();
        
        int activos = countActives(miLista); 
        int inActivos = countDeActives(miLista); 
        int total = activos + inActivos; 
        
        File file = new File(path);
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write("Num_reg: " + total);
        bw.write("\r\n");
        bw.write("Reg_inicio: " + numReg);
        bw.write("\r\n");
        bw.write("reg_activos: "+ activos);
        bw.write("\r\n");
        bw.write("Reg_inact: " + inActivos);
            
        bw.flush();
        bw.close();
        
        return true;
        
    }
    
    public String UltimoRegistro() throws IOException{
        
        String path = "C:\\MEIA\\indice_lista_usuario.txt";
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        
        List<String> datos = br.lines().collect(Collectors.toList());
        br.close();
        
        if(datos.isEmpty()) {
            return "1|1.1| | | |1|1";
        }
        else {
            return datos.get(datos.size()-1);
        }
        
    }
    
    public void Reordenar(){
        
    }
    
}    

