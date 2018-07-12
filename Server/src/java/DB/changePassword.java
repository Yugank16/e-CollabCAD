/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import DB.DBConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author yugank
 */
public class changePassword extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String url = "jdbc:postgresql://localhost:5432/collab_cad";
    private final String user = "postgres";
    private final String dbpassword = "yugank123";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String user_id = request.getParameter("userid");
        String password = request.getParameter("new_password");
        JSONObject result = validate(user_id, password);
        try {
            boolean status = result.getBoolean("status");
            if(status){
                // data was valid
                try{
                    Connection conn = connect();
                    try{
                        change(conn, user_id, password);
                    } catch(Exception e){
                        result.put("status", false);
                        result.put("reason", e.getMessage());
                        out.print(result);
                    }
                } catch(Exception e){
                    result.put("status", false);
                    result.put("reason", "Could not contact server");
                    System.out.println("" + e.getMessage().toString());
                    out.print(result);
                }
            } else {
                out.print(result);
            }
        } catch (JSONException ex) {
            Logger.getLogger(changePassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        out.print(result);
    }   

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    private JSONObject validate(String user_id, String password) {
        JSONObject result = new JSONObject(true);
        ArrayList<String> reason = new ArrayList<String>();
        boolean flag = true;
//        if(!Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$").matcher(email_id).matches()) {
//            reason.add("Invalid e-mail id");
//            flag = false;
//        }
        if(user_id.trim().length() == 0){
            reason.add("Invalid user id");
            flag = false;
        }
        /*if(!Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$").matcher(email_id).matches()) {
            reason.add("Invalid Password");
            flag = false;
        }*/
        try {
            result.put("status", flag);
            result.put("reason", reason);
        } catch (JSONException ex) {
            Logger.getLogger(changePassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private void change(Connection conn,String user_id, String password) throws SQLException {
        System.out.println("heere");
        String sql = "UPDATE Users SET password='"+password+"' WHERE userid = '" + user_id + "';";

        System.out.println(sql);
        Statement stmt = null;
        conn.setAutoCommit(false);
        stmt = conn.createStatement();
        int s = stmt.executeUpdate(sql);
        System.out.println("" + s);
        stmt.close();
        conn.commit();
        conn.close();
    }
    
    public Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection conn = null;
        conn = DriverManager.getConnection(url, user, dbpassword);
        System.out.println("Connected to the PostgreSQL server successfully.");
        return conn;
    }
}




        