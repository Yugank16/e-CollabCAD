/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author dravit
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    private DBConnect dBConnect = DBConnect.getInstance();
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String user_id = request.getParameter("userid");
        String password = request.getParameter("password");
        JSONObject result = validate(user_id, password);
        try {
            boolean status = result.getBoolean("status");
            if(status){
                Connection conn = dBConnect.connect();
                boolean flag = loginUser(conn, user_id, password);
                    result.put("status", flag);
                out.print(result);
            } else {
                out.print(result);
            }
        } catch (JSONException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private JSONObject validate(String user_id, String password){
        JSONObject result = new JSONObject(true);
        ArrayList<String> reason = new ArrayList<String>();
        boolean flag = true;
        /*if(!Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$").matcher(email).matches()) {
            reason.add("Invalid e-mail id");
            flag = false;
        }
        if(password.length() < 6){
            flag = false;
            reason.add("Invalid password");
        }*/
        try {
            result.put("status", flag);
            result.put("reason", reason);
        } catch (JSONException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    private boolean loginUser(Connection conn, String user_id, String password) throws SQLException{
        String login_stmt = "SELECT * FROM users WHERE userid = '" + user_id + "' AND password = '" + password + "';";
        Statement stmt = null;
        boolean flag = false;
        conn.setAutoCommit(false);
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(login_stmt);
        System.out.println("flag = " + flag);
        // System.out.println("Query : " + login_stmt);
        while(rs.next()){
            /*
            System.out.println("Here : " + login_stmt);
            System.out.println("Password : " + rs.getString("password"));
            System.out.println("user_id : " + rs.getString("user_id"));
            System.out.println("user_id : " + user_id.equals(rs.getString("user_id")));
            System.out.println("Password : " + password.equals(rs.getString("password")));
            if(password.equals(rs.getString("password")) && user_id.equals(rs.getString("user_id"))){
                flag = true;
                System.out.println("Flag : " + flag);
            }
            */
            flag = true;
        }
        stmt.close();
        conn.commit();
        conn.close();
        return flag;
    }
}
