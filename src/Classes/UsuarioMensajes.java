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
public class UsuarioMensajes implements Comparable<UsuarioMensajes> {

    private int numRegistro;    
    private String emisor;
    private String receptor; 
    private String fecha_transaccion; 
    private String asunto; 
    private String mensaje;
    private String adjunto; 
    private int status;
    
    public UsuarioMensajes(int numRegistro, String emisor, String receptor, String fecha_transaccion, String asunto, String mensaje, int status) {
        this.numRegistro = numRegistro;
        this.emisor = emisor;
        this.receptor = receptor;
        this.fecha_transaccion = fecha_transaccion;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.status = status;
    }
    
    public int getNumRegistro() {
        return numRegistro;
    }

    public void setNumRegistro(int numRegistro) {
        this.numRegistro = numRegistro;
    }
    
    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(String adjunto) {
        this.adjunto = adjunto;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }     
    
     public UsuarioMensajes() {
     } 
     
    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getFecha_transaccion() {
        return fecha_transaccion;
    }

    public void setFecha_transaccion(String fecha_transaccion) {
        this.fecha_transaccion = fecha_transaccion;
    }   

    @Override
    public int compareTo(UsuarioMensajes other) {
        
        if(this.getNumRegistro() < other.numRegistro)
            return -1;
        else
        if(this.getNumRegistro() > other.numRegistro)
            return 1; 
        else
        if(this.getNumRegistro() == other.numRegistro)
            return 0;
        else
            return 2;      
       
    }
    
    
}
