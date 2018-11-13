/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_meia;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Josue Higueros
 */
public class BDD {
    
    //DEBEN LLAMAR A LA INSTANCIA DE BASE DE DATOS DESDE SU FORMULARIO DE LOGIN, EJEMPLO: BDD.getInstancia.conexion();
    private static BDD instancia;
    private static Connection ConexionP; 
    private static Connection Conexion;
    private static Statement stmt;
    private static String mensaje;
    
    public static BDD getInstancia() throws ClassNotFoundException, SQLException{
        if(instancia == null){
            instancia = new BDD();
            Class.forName("org.postgresql.Driver");
            ConexionP = DriverManager.getConnection("jdbc:postgresql://pellefant.db.elephantsql.com:5432/zukrpshu", "zukrpshu", "6JrLoCHHOKEDVAuAqLpm-GYFepB0X7s0");
            Conexion = DriverManager.getConnection("jdbc:postgresql://pellefant.db.elephantsql.com:5432/zukrpshu", "zukrpshu", "6JrLoCHHOKEDVAuAqLpm-GYFepB0X7s0");
            stmt = ConexionP.createStatement();
            DriverManager.setLogWriter(new PrintWriter(System.out, true));            
            Listener listener = new Listener(ConexionP);
            Notificador notifier = new Notificador(Conexion);
            listener.start();
            notifier.start();
        }
        return instancia;
    }
    
    public String getMensaje(){
        return mensaje;
    }
    
    public void setMensaje(String msj){
        mensaje = msj;
    }
    
    public void conexion(){
        
    }
    
    //MODIFICAR
    public void Insert(int grupoemisor,int gruporeceptor, String emisor, String receptor, String asunto, String mensaje) throws SQLException{
        String sql = "INSERT INTO solicitud(grupoemisor, gruporeceptor, emisor, receptor, fecha, asunto, mensaje, aceptado) VALUES(" + grupoemisor + " ," + gruporeceptor + ", '" + emisor + "','" + receptor + "',current_timestamp,'" + asunto +"','"+mensaje +"', false)";    
        stmt.executeUpdate(sql);        
    }
    
    public void Update(String id, boolean bool) throws SQLException{
        if(bool){
            stmt.executeUpdate("UPDATE solicitud SET aceptado = true WHERE idSolicitud = " + id);
        }else{
            stmt.executeUpdate("UPDATE solicitud SET aceptado = false WHERE idSolicitud = " + id);
        }
    }
    
    //NO ES NECESARIO UTILIZAR ESTA PARTE
    public void Delete(String id) throws SQLException{
        stmt.executeUpdate("DELETE FROM solicitud WHERE idSolicitud = " + id);
    }
    
}
