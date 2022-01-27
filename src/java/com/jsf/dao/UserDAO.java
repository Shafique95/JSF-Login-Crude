package com.jsf.dao;

import com.jsf.model.User;
import com.jsf.util.Database;
import java.sql.*;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name = "userDAO")
public class UserDAO {
    Connection con;
    Statement stmt;
    ResultSet rs;
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
   
    public void save(User user){
        try {
        con = Database.getConnection();
        stmt = con.createStatement();
          PreparedStatement stmt = con.prepareStatement("insert into users(username,password) values(?,?)");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            int result = stmt.executeUpdate();
            if(result>0){
                user.setMsg("Success");
            }else{
                user.setMsg("Failed");
            }
            con.close();
        } catch (Exception e) {
        }
        
    }
    public String login(User user){
        try {
        con = Database.getConnection();
        stmt = con.createStatement();
          PreparedStatement stmt = con.prepareStatement("select * from users where username = ?");
            stmt.setString(1, user.getUsername());
            rs = stmt.executeQuery();
            if(rs.next()){
                if(rs.getString("password").equals(user.getPassword())){
                    System.out.println("hello");
                    sessionMap.put("user", user);
                    return "welcome.xhtml?faces-redirect=true";
                }else{
                    System.out.println("hi");
                    return "login.xhtml?faces-redirect=true";
                }
            }
            
        } catch (Exception e) {
        }
        return null;
    }
}
