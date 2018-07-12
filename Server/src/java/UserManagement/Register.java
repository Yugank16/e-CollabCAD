/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserManagement;

import DB.DBConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
 * @author dravit
 */
public class Register extends HttpServlet {


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
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String email_id = request.getParameter("email_id");
        String user_id = request.getParameter("user_id");
        String password = request.getParameter("password");
        JSONObject result = validate(email_id, user_id, password);
        try {
            boolean status = result.getBoolean("status");
            if(status){
                // data was valid
                DBConnect dBConnect = DBConnect.getInstance();
                try{
                    Connection conn = dBConnect.connect();
                    try{
                        regsiterUser(conn, email_id, user_id, password);
                    } catch(Exception e){
                        result.put("flag", false);
                        result.put("reason", e.getMessage());
                        out.print(result);
                    }
                } catch(Exception e){
                    result.put("status", false);
                    result.put("reason", "Could not contact server");
                    out.print(result);
                }
            } else {
                out.print(result);
            }
        } catch (JSONException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        out.print(result);
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "end point to register a user";
    }// </editor-fold>

    private JSONObject validate(String email_id, String user_id, String password) {
        JSONObject result = new JSONObject(true);
        ArrayList<String> reason = new ArrayList<String>();
        boolean flag = true;
        if(!Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$").matcher(email_id).matches()) {
            reason.add("Invalid e-mail id");
            flag = false;
        }
        if(user_id.trim().length() == 0){
            reason.add("Invalid user id");
            flag = false;
        }
        if(!Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$").matcher(email_id).matches()) {
            reason.add("Invalid Password");
            flag = false;
        }
        try {
            result.put("status", flag);
            result.put("reason", reason);
        } catch (JSONException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private void regsiterUser(Connection conn, String email_id, String user_id, String password) throws SQLException {
        String sql = "INSERT INTO Users (EMAIL , USER_ID, PASSWORD) "
            + "VALUES ('" + email_id + "', '"+ user_id +"', '" + password + "');";
        Statement stmt = null;
        conn.setAutoCommit(false);
        stmt.executeUpdate(sql);
        stmt.close();
        conn.commit();
        conn.close();
    }
    
}
/*^                 # start-of-string
(?=.*[0-9])       # a digit must occur at least once
(?=.*[a-z])       # a lower case letter must occur at least once
(?=.*[A-Z])       # an upper case letter must occur at least once
(?=.*[@#$%^&+=])  # a special character must occur at least once
(?=\S+$)          # no whitespace allowed in the entire string
.{8,}             # anything, at least eight places though
$                 # end-of-string*/