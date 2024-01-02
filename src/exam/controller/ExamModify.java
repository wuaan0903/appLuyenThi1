/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exam.controller;

import admin.model.Config;
import exam.model.Question;
import exam.model.exam;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class ExamModify {
    public static List<exam> getExamList(String s) {
        List<exam> dataList = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement statement = null;
        
        try {
            conn = DriverManager.getConnection(Config.DB_URL, Config.USERNAME, Config.PASSWORD);
            
            String sql = "select * from list_exam";
            if(s != null && !s.isEmpty()) {
                sql += " where NameExam like ?";
            }
            statement = conn.prepareStatement(sql);
            if(s != null && !s.isEmpty()) {
                statement.setString(1, s);
            }
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()) {
                exam exam = new exam(
                        resultSet.getInt("id"),
                        resultSet.getString("NameExam"),
                        resultSet.getInt("soCauHoi"),
                        resultSet.getInt("thoigian")
                );
                dataList.add(exam);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExamModify.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ExamModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ExamModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return dataList;
    }
    
    public static List<Question> getQuestion(String s) {
        List<Question> dataList = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement statement = null;
        
        try {
            conn = DriverManager.getConnection(Config.DB_URL, Config.USERNAME, Config.PASSWORD);
            
            String sql = "select * from cauhoi";
            if(s != null && !s.isEmpty()) {
                sql += " where NameExam like ?";
            }
            statement = conn.prepareStatement(sql);
            if(s != null && !s.isEmpty()) {
                statement.setString(1, s);
            }
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()) {
                Question q = new Question(
                        resultSet.getInt("id"),
                        resultSet.getString("NameExam"),
                        resultSet.getString("question"),
                        resultSet.getString("answerA"),
                        resultSet.getString("answerB"),
                        resultSet.getString("answerC"),
                        resultSet.getString("answerD"),
                        resultSet.getInt("answer_correct"),
                        resultSet.getInt("status")
                );
                dataList.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExamModify.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ExamModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ExamModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return dataList;
    }
    
    public static void insert(exam exam) {
        Connection conn = null;
        PreparedStatement statement = null;
        
        try {
            conn = DriverManager.getConnection(Config.DB_URL, Config.USERNAME, Config.PASSWORD);
            
            String sql = "insert into list_exam(NameExam, soCauHoi, thoigian) "
                    + "values (?, ?, ?)";
            statement = conn.prepareStatement(sql);
            statement.setString(1, exam.getNameExam());
            statement.setInt(2, exam.getSoCauHoi());
            statement.setInt(3, exam.getThoigian());
            
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ExamModify.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ExamModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ExamModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static void update(exam exam) {
        Connection conn = null;
        PreparedStatement statement = null;
        
        try {
            conn = DriverManager.getConnection(Config.DB_URL, Config.USERNAME, Config.PASSWORD);
            
            String sql = "update customer set fullname = ?, email = ?, phone_number = ?, birthday = ?, address = ? where id = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, exam.getNameExam());
            statement.setInt(2, exam.getSoCauHoi());
            statement.setInt(3, exam.getThoigian());
            statement.setInt(4, exam.getId());
            
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ExamModify.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ExamModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ExamModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static void delete(int id) {
        Connection conn = null;
        PreparedStatement statement = null;
        
        try {
            conn = DriverManager.getConnection(Config.DB_URL, Config.USERNAME, Config.PASSWORD);
            
            String sql = "delete from customer where id = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ExamModify.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ExamModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ExamModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }


}
