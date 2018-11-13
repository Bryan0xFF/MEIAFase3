/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author rodri
 */
public class Usuario{
    
    private String Usuario; 
    private String Nombre;
    private String Apellido; 
    private String Password; 
    private int Rol; 
    private String Fecha_Nacimiento; 
    private String Correo_Alterno; 
    private int Telefono; 
    private String Path_Fotografia; 
    private int Estatus; 
    public Puntuaciones puntuacion; 
    
    public Usuario() {    
    }

     public Usuario(String Usuario, String Nombre, String Apellido, String Password, int Rol, String Fecha_Nacimiento, String Correo_Alterno, int Telefono, String Path_Fotografia, int Estatus) {
        this.Usuario = Usuario;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Password = Password;    
        this.Fecha_Nacimiento = Fecha_Nacimiento;
        this.Rol = Rol;
        this.Correo_Alterno = Correo_Alterno;
        this.Telefono = Telefono;
        this.Path_Fotografia = Path_Fotografia;    
        this.Estatus = Estatus;
    }
     
     public Usuario(String Usuario, String Nombre, String Apellido, String Password, String Fecha_Nacimiento, String Correo_Alterno, int Telefono, String Path_Fotografia) {
        this.Usuario = Usuario;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Password = Password;    
        this.Fecha_Nacimiento = Fecha_Nacimiento;      
        this.Correo_Alterno = Correo_Alterno;
        this.Telefono = Telefono;
        this.Path_Fotografia = Path_Fotografia;    
        this.Estatus = 1;
    }     
   
    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
    
    public int checkPassword(String password) {
        
        puntuacion = new Puntuaciones(password); 
        return puntuacion.Verificar();
    }

    public int getRol() {
        return Rol;
    }

    public void setRol(int Rol) {
        this.Rol = Rol;
    }

    public String getFecha_Nacimiento() {
        return Fecha_Nacimiento;
    }

    public void setFecha_Nacimiento(String Fecha_Nacimiento) {
        this.Fecha_Nacimiento = Fecha_Nacimiento;
    }

    public String getCorreo_Alterno() {
        return Correo_Alterno;
    }

    public void setCorreo_Alterno(String Correo_Alterno) {
        this.Correo_Alterno = Correo_Alterno;
    }

    public String getTelefono() {
        return String.valueOf(Telefono);
    }

    public void setTelefono(int Telefono) {
        this.Telefono = Telefono;
    }

    public String getPath_Fotografia() {
        return Path_Fotografia;
    }

    public void setPath_Fotografia(String Path_Fotografia) {
        this.Path_Fotografia = Path_Fotografia;
    }

    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(int Estatus) {
        this.Estatus = Estatus;
    }
    
    public static String Encriptar(String cadena) {
        
        try {
            
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(cadena.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

             while (hashtext.length() < 32) {
     
                 hashtext = "0" + hashtext;
             }
 
            return hashtext;            
        }
        catch (NoSuchAlgorithmException e) {
     
           throw new RuntimeException(e);
        }         
    }
    
    public String ToFixedSizeString(String word, int count) {
    String result = ""; 	
    int complement = count - word.length();
      for(int i = 0; i < complement; i++) {
	result += "&";
      }
        return result+word;        
    }
    
    
    public String setFixedSizeString() {
        
    StringBuilder line = new StringBuilder(); 

    line.append(ToFixedSizeString(Usuario, 20));
    line.append("|");

    line.append(ToFixedSizeString(Nombre, 30));
    line.append("|");

    line.append(ToFixedSizeString(Apellido, 30));
    line.append("|");

    line.append(ToFixedSizeString(Password,40));
    line.append("|");

    line.append(Rol);
    line.append("|");

    line.append(ToFixedSizeString(Fecha_Nacimiento,10));
    line.append("|");

    line.append(ToFixedSizeString(Correo_Alterno, 40));
    line.append("|");

    line.append(ToFixedSizeString(String.valueOf(Telefono), 8));
    line.append("|");

    line.append(ToFixedSizeString(Path_Fotografia, 200));
    line.append("|");

    line.append(Estatus);
    line.append("\r\n");

    
    return line.toString();
    }
    
    public static String fromFixedSizeString (String word) {
        StringBuilder newWord = new StringBuilder(); 
        int cont = 0; 
        char amber = '&';
        for (int i = 0; i < word.length(); i++) {
            char pos = word.charAt(i); 
            if(pos != amber) {
                newWord.append(pos);                 
            }           
        }
        return newWord.toString();
    }
    
    public Usuario getFixedSizeString(String cadena) {
        String[] splited = cadena.split("\\|");             
        this.setUsuario(fromFixedSizeString(splited[0]));
        this.setNombre(fromFixedSizeString(splited[1]));
        this.setApellido(fromFixedSizeString(splited[2]));
        this.setPassword(fromFixedSizeString(splited[3]));
        this.setRol(Integer.valueOf(fromFixedSizeString(splited[4])));
        this.setFecha_Nacimiento(fromFixedSizeString(splited[5]));
        this.setCorreo_Alterno(fromFixedSizeString(splited[6]));
        this.setTelefono(Integer.valueOf(fromFixedSizeString(splited[7])));
        this.setPath_Fotografia(fromFixedSizeString(splited[8]));
        this.setEstatus(Integer.valueOf(fromFixedSizeString(splited[9])));
        
        return this;
    }
    
    
   public static class Puntuaciones {
    
   String password; 
   int puntuacionFinal = 0, length  = 0, lowerCase = 0, upperCase = 0, digit = 0, symbol = 0;  
   
   private Puntuaciones (String pwd) {
       password = pwd; 
       length = password.length();
       CountCharacters(); 
    }
   
    private void CountCharacters() {
     
       for (int i = 0; i < length; i++) {
         char caracter = password.charAt(i); 
         
         if(Character.isDigit(caracter)) {
            digit++;  
         }
         else if (Character.isUpperCase(caracter)) {
           upperCase++;   
         }
         else if (Character.isLowerCase(caracter)) {
           lowerCase++;  
         }
         else {
             symbol++;
         }
                 
       }
    }   
 
   
   public int Verificar() {
    
       puntuacionFinal = 3* length; 
       
       puntuacionFinal = puntuacionFinal + (2 * upperCase); 
       
       puntuacionFinal = puntuacionFinal + ( (upperCase + lowerCase) + 1 );
       
       puntuacionFinal = puntuacionFinal + (digit + 2); 
       
       puntuacionFinal = puntuacionFinal + (symbol) * (length + 4);   
       
        if(digit <= 0) {
            
         puntuacionFinal = puntuacionFinal - 6; 
        }
        
        if( upperCase + lowerCase <= 0) {
           
            puntuacionFinal = puntuacionFinal - 3; 
        }
               
       return puntuacionFinal; 
    }
   
  }
   
   public static int TamanioFijo(){
       
       return 389;
       
   }
}
