/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author HP
 */
public class StudentDatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String derbyClientDriver = "org.apache.derby.jdbc.ClientDriver";
        Class.forName(derbyClientDriver);
        
        String url = "jdbc:derby://localhost:1527/Student";
        String user = "app";
        String passwd = "app";

        Connection con = DriverManager.getConnection(url, user, passwd);

        Statement stmt = con.createStatement();
        Student s1 = new Student(1, "A", 3.5);
        Student s2 = new Student(2, "B", 3.22);
        insertStudent(stmt, s1);
        insertStudent(stmt, s2);
        Student s = getStudentById(stmt, 1);
        s.setGPA(3.0);
        updateStudentGPA(stmt, s);
        s.setName("C");
        updateStudentName(stmt, s);
        deleteStudent(stmt, s);
        
        stmt.close();
        con.close();
    }
    public static void printAllStudent(ArrayList<Student> StudentList) {
        for(Student s : StudentList) {
           System.out.print(s.getId() + " ");
           System.out.print(s.getName() + " ");
           System.out.println(s.getGPA() + " ");
       }
    }
    
    public static ArrayList<Student> getAllStudent (Connection con) throws SQLException {
        String sql = "select * from Student order by id";
        PreparedStatement ps = con.prepareStatement(sql);
        ArrayList<Student> StudentList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
           Student student = new Student();
           student.setId(rs.getInt("id"));
           student.setName(rs.getString("name"));
           student.setGPA(rs.getDouble("GPA"));
           StudentList.add(student);
       }
       rs.close();
       return StudentList;
       
    }
    
   public static Student getStudentById(Statement stmt, int id) throws SQLException {
       Student s = null;
       String sql = "select * from Student where id = " + id;
       ResultSet rs = stmt.executeQuery(sql);
       if (rs.next()) {
           s = new Student();
           s.setId(rs.getInt("id"));
           s.setName(rs.getString("name"));
           s.setGPA(rs.getDouble("GPA"));
       }
       return s;
   } 
   public static void insertStudent(Statement stmt, Student s) throws SQLException {
       /*String sql = "insert into employee (id, name, salary)" +
                     " values (5, 'Mark', 12345)";*/
        String sql = "insert into Student (id, name, GPA)" +
                     " values (" + s.getId() + "," + "'" + s.getName() + "'" + "," + s.getGPA() + ")";
        int result = stmt.executeUpdate(sql);
        System.out.println("Insert " + result + " row");
   } 
   public static void deleteStudent(Statement stmt, Student s) throws SQLException {
       String sql = "delete from Student where id = " + s.getId();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("delete " + result + " row");
   }
   public static void updateStudentGPA(Statement stmt, Student s) throws SQLException {
       String sql = "update Student set GPA  = " + s.getGPA() + 
               " where id = " + s.getId();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("update " + result + " row");
   }
   public static void updateStudentName(Statement stmt, Student s) throws SQLException {
       String sql = "update Student set name  = '" + s.getName() + "'" + 
               " where id = " + s.getId();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("update " + result + " row");
   }
   
   public static void insertStudentPreparedStatement(Connection con, Student s) throws SQLException {
       String sql = "insert into Student (id, name, GPA)" + 
               " values (?,?,?)";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, s.getId());
       ps.setString(2, s.getName());
       ps.setDouble(3, s.getGPA());
       int result = ps.executeUpdate();
        //display result
        System.out.println("Insert " + result + " row");
   }
   public static void deleteStudentPreparedStatement(Connection con, Student s) throws SQLException {
       String sql ="delete from Student where id = ?";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, s.getId());
       int result = ps.executeUpdate();
        //display result
        System.out.println("Delete " + result + " row");
   }
   public static void updateStudentGPAPreparedStatement(Connection con, Student s) throws SQLException {
       String sql = "update employee set salary  = ? where id = ? ";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setDouble(1, s.getGPA());
       ps.setInt(2, s.getId());
       int result = ps.executeUpdate();
        //display result
        System.out.println("update " + result + " row");
   }
   public static void updateStudentNamePreparedStatement(Connection con, Student s) throws SQLException {
       String sql = "update employee set name  = ? where id = ? ";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setString(1, s.getName());
       ps.setInt(2, s.getId());
       int result = ps.executeUpdate();
        //display result
        System.out.println("update " + result + " row");
   }
   public static Student getEmployeeByIdPreparedStatement(Connection con, int id) throws SQLException {
       Student s = null;
       String sql = "select * from Student where id = ?";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, id);
       ResultSet rs = ps.executeQuery();
       if (rs.next()) {
           s = new Student();
           s.setId(rs.getInt("id"));
           s.setName(rs.getString("name"));
           s.setGPA(rs.getDouble("GPA"));
       }
       return s;
    }
}
