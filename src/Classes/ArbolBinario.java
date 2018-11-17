/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

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
            DefaultDescriptor();
            
        }catch(Exception e){
            
            e.printStackTrace();
            
        }
        
    }
    
    private void CrearArchivos() throws IOException{
        
        File file = new File("C:\\MEIA\\Datos.ABB");
        file.createNewFile();
        file = new File("C:\\MEIA\\Datos_Master.ABB");        
        file.createNewFile();
        file = new File("C:\\MEIA\\Desc_Arbol.ABB");
        file.createNewFile();
    }
    
    private void DefaultDescriptor () {
        
        try{
            
            
        FileWriter fw = new FileWriter("C:\\MEIA\\Desc_Arbol.ABB");
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
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
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
    /**
     * se busca por el campo de receptor, bajo la premisa que el emisor es siempre quien envia el mensaje
     * @param campo1
     * @param root
     * @return 
     */
    public List<String> Buscar(String campo1, int root) {
        
        List<String> lista = new ArrayList<>();
        
         if (root == -1) {
            
            return lista;
            
        }
        
        Nodo temp = Nodo.ObtenerNodo(root);
 
        String[] split = temp.dato.replace("&","").split("\\|");
        //split[2] usuario_emisor, split[3] usuario_receptor
        String datoComparar = split[3];
        
        //print
        if (datoComparar.equals(campo1)) {
            
            lista.add(temp.dato);
            
 
        }
        
       
        //izquierdo
       lista.addAll(Buscar(campo1, Integer.parseInt(temp.izquierdo)));
       //derecho
       lista.addAll(Buscar(campo1, Integer.parseInt(temp.derecho)));
       
       return lista;
    }
    
    private void ActualizarDescriptor(int nuevaRaiz, int regAct, int regInac) throws IOException{
        
        FileWriter fw = new FileWriter("C:\\MEIA\\Desc_Arbol.ABB");
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write("Num_reg: " + Integer.toString((regAct + regInac)));
        bw.write("\r\n");
        bw.write("Reg_inicio: " + Integer.toString(nuevaRaiz));
        bw.write("\r\n");
        bw.write("reg_activos: "+ Integer.toString(regAct));
        bw.write("\r\n");
        bw.write("Reg_inact: " + Integer.toString(regInac));
        
        bw.flush();
        bw.close();
        
    }
    
    /**
     * aqui se envia el dato completo (con todo y apuntadores) a eliminar
     * @param datoSerializadoEliminar
     * @param root
     * @return 
     */
    public boolean Eliminar(String mensaje, int root){
        
        Nodo actual = Nodo.ObtenerNodo(root);
        String dato = actual.dato;
        
        String[] splitDatoActual = dato.trim().replace("&", "").split("\\|");
        String[] splitMensaje = mensaje.trim().replace("&", "").split("\\|");
        
        if (splitDatoActual[2].equals(splitMensaje[2])) {
            
            if (splitDatoActual[3].equals(splitMensaje[3])) {
                
                if (splitDatoActual[4].equals(splitMensaje[4])) {
                    //es el dato
                    
                    //tiene 2 hijos
                    if (actual.derecho.equals("-1") && actual.izquierdo.equals("-1")) {
                        
                        
                        
                    }
                    
                }
                
            }
            
        }
        
        return false;
    }
    
    private Nodo masIzquierdo(int root, int regPadre){
        
        Nodo actual = Nodo.ObtenerNodo(root);
        
        if (actual.derecho.equals("-1") && actual.izquierdo.equals("-1")) {
            
            return actual;
            
        }
        
        if (!actual.derecho.equals("-1")) {
            
            masIzquierdo(Integer.parseInt(actual.derecho), root);
            
        }
        
        if (actual.derecho.equals("-1")) {
            
            boolean buscarIzq = BuscarIzq(regPadre);
            
            //tiene izquierdo
            if (buscarIzq == true) {
                
                masIzquierdo(Integer.parseInt(actual.izquierdo), root);
                
            }
        }
        
        return null;
    }
    
    private boolean BuscarIzq(int reg){
        
        Nodo actual = Nodo.ObtenerNodo(reg);
        
        if (actual.izquierdo.equals("-1")) {
            return false;
        }
        else{
            return true;
        }
        
    }
    
    public static List getDataFromMaster() throws FileNotFoundException, IOException {
        
        FileReader fr = new FileReader("C:\\MEIA\\Datos.ABB");
        BufferedReader br = new BufferedReader(fr);
        List<String> datos = br.lines().collect(Collectors.toList());
        br.close();
               
        
        return datos;        
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
