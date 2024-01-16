/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package admin.view;

import admin.model.Config;
import exam.view.editQuestion;
import exam.controller.ExamModify;
import exam.model.Question;
import exam.model.exam;
import exam.view.addOption;
import exam.view.addQuestion;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class ExamForm extends javax.swing.JFrame {

    /**
     * Creates new form ExamForm
     */
    DefaultTableModel tableModel;
    List<exam> dataList;
    String filePath;
    int currentPos = -1;

    public ExamForm() {
        initComponents();

        examTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = examTable.getSelectedRow();

                    if (selectedRow != -1) {
                        String nameExam = (String) examTable.getValueAt(selectedRow, 2);
                        int numberExam = (int) examTable.getValueAt(selectedRow, 3);
                        int soCau = (int) examTable.getValueAt(selectedRow, 4);

                        editQuestion editForm = new editQuestion(nameExam, numberExam, soCau);
                        editForm.setVisible(true);
                        dispose();
                    }
                }
            }
        });
        tableModel = (DefaultTableModel) examTable.getModel();
        dataList = ExamModify.getExamList(null, 0);

        showData();

        examTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentPos = examTable.getSelectedRow();

//                fullnameTxt.setText(dataList.get(currentPos).getFullname());
//                emailTxt.setText(dataList.get(currentPos).getEmail());
//                phoneTxt.setText(dataList.get(currentPos).getPhoneNumber());
//                birthdayTxt.setText(dataList.get(currentPos).getBirthday());
//                addressTxt.setText(dataList.get(currentPos).getAddress());
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private int getSelectedExamId() {
        int selectedRow = examTable.getSelectedRow();
        if (selectedRow != -1) {
            return (int) examTable.getValueAt(selectedRow, 1); // Giả sử cột ID là cột thứ 2
        } else {
            return -1; // Trả về -1 nếu không có hàng nào được chọn
        }
    }

    private void showData() {
        tableModel.setRowCount(0);
        for (exam ex : dataList) {
            tableModel.addRow(new Object[]{
                tableModel.getRowCount() + 1,
                ex.getId(),
                ex.getNameExam(),
                ex.getNumberExam(),
                ex.getSoCauHoi(),
                ex.getThoigian()
            });
        }
    }

    private void importExcel() throws FileNotFoundException, IOException {
        try {
//            String selectedNameExam = (String) nameExam.getSelectedItem();
//            int selectedNumberExam = Integer.parseInt(numberExam.getText());

            Connection connection = DriverManager.getConnection(Config.DB_URL, Config.USERNAME, Config.PASSWORD);
            String sql = "INSERT INTO cauhoi(NameExam, numberExam, question, answerA, answerB, answerC, answerD, answer_correct, status) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) "
                    + "ON DUPLICATE KEY UPDATE "
                    + "NameExam = VALUES(NameExam), "
                    + "numberExam = VALUES(numberExam), "
                    + "question = VALUES(question), "
                    + "answerA = VALUES(answerA), "
                    + "answerB = VALUES(answerB), "
                    + "answerC = VALUES(answerC), "
                    + "answerD = VALUES(answerD), "
                    + "answer_correct = VALUES(answer_correct), "
                    + "status = VALUES(status)";
            PreparedStatement statement = connection.prepareStatement(sql);
            BufferedReader lineReader = new BufferedReader(new FileReader(filePath));
            String lineText = null;
            int count = 0;
            lineReader.readLine();
            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(";");

                String NameExam = (String) nameExam.getSelectedItem(); // Sửa giá trị NameExam
                int NumberExam = Integer.parseInt(numberExam.getText());
                String question = data[0];
                String answerA = data[1];
                String answerB = data[2];
                String answerC = data[3];
                String answerD = data[4];
                String answer_correct = data[5];
                String status = data[6];

                statement.setString(1, NameExam); // Thêm giá trị cho NameExam trong phần ON DUPLICATE KEY UPDATE
                statement.setInt(2, NumberExam);
                statement.setString(3, question);
                statement.setString(4, answerA);
                statement.setString(5, answerB);
                statement.setString(6, answerC);
                statement.setString(7, answerD);
                statement.setInt(8, parseInt(answer_correct));
                statement.setInt(9, parseInt(status));
                statement.addBatch();
                if (count % batchSize() == 0) {
                    statement.executeBatch();
                }
            }
            lineReader.close();
            statement.executeBatch();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        examTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        saveBtn = new javax.swing.JButton();
        deleteexam = new javax.swing.JButton();
        searchBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        numberExam = new javax.swing.JTextField();
        nameExam = new javax.swing.JComboBox<>();
        thoiGian = new javax.swing.JComboBox<>();
        soCau = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setForeground(new java.awt.Color(0, 0, 0));

        examTable.setBackground(new java.awt.Color(204, 255, 204));
        examTable.setForeground(new java.awt.Color(0, 0, 0));
        examTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "ID", "Tên đề", "Đề số", "Số câu", "Thời gian"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        examTable.setRowHeight(40);
        jScrollPane1.setViewportView(examTable);

        jLabel1.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DANH SÁCH ĐỀ THI");

        jLabel2.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("THÔNG TIN ĐỀ THI");

        jLabel3.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Tên đề :");

        jLabel4.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Số câu :");

        jLabel5.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Thời gian :");

        saveBtn.setBackground(new java.awt.Color(204, 255, 255));
        saveBtn.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        saveBtn.setForeground(new java.awt.Color(0, 0, 0));
        saveBtn.setText("Thêm");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        deleteexam.setBackground(new java.awt.Color(204, 255, 255));
        deleteexam.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        deleteexam.setForeground(new java.awt.Color(0, 0, 0));
        deleteexam.setText("Xóa");
        deleteexam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteexamActionPerformed(evt);
            }
        });

        searchBtn.setBackground(new java.awt.Color(204, 255, 255));
        searchBtn.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        searchBtn.setForeground(new java.awt.Color(0, 0, 0));
        searchBtn.setText("Tìm kiếm");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        backBtn.setBackground(new java.awt.Color(204, 255, 204));
        backBtn.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        backBtn.setForeground(new java.awt.Color(0, 0, 0));
        backBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/left-arrow-circle-solid-24.png"))); // NOI18N
        backBtn.setText("Quay lại");
        backBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Đề số :");

        numberExam.setBackground(new java.awt.Color(204, 255, 255));
        numberExam.setForeground(new java.awt.Color(0, 0, 0));
        numberExam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberExamActionPerformed(evt);
            }
        });

        nameExam.setBackground(new java.awt.Color(204, 255, 255));
        nameExam.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        nameExam.setForeground(new java.awt.Color(0, 0, 0));
        nameExam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lập trình hướng đối tượng", "Cơ sở dữ liệu", "Công nghệ Java", "Mạng máy tính", "Toán rời rạc", "Tin học đại cương" }));

        thoiGian.setBackground(new java.awt.Color(204, 255, 255));
        thoiGian.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        thoiGian.setForeground(new java.awt.Color(0, 0, 0));
        thoiGian.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "15", "30", "45", "60", "90" }));

        soCau.setBackground(new java.awt.Color(204, 255, 255));
        soCau.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        soCau.setForeground(new java.awt.Color(0, 0, 0));
        soCau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "10", "15", "20", "40", "50" }));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("phút");

        jLabel8.setForeground(new java.awt.Color(204, 255, 255));
        jLabel8.setText("jLabel8");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(soCau, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(thoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(numberExam)
                            .addComponent(nameExam, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(deleteexam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                .addContainerGap(155, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(backBtn)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(nameExam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(numberExam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(soCau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(thoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(deleteexam)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchBtn)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed

        String selectedName = (String) this.nameExam.getSelectedItem();
        String numberExamText = this.numberExam.getText();

        if (selectedName.isEmpty() || numberExamText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin đề thi.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Dừng lại nếu có lỗi
        }

// Chuyển đổi numberExamText sang số nguyên
        int number = Integer.parseInt(numberExamText);

// Kiểm tra xem đã có bài kiểm tra với số bài kiểm tra giống nhau trong dataList chưa
        for (exam existingExam : dataList) {
            if (existingExam.getNameExam().equals(selectedName) && existingExam.getNumberExam() == number) {
                JOptionPane.showMessageDialog(this, "Đề thi với mã đề đã tồn tại trong " + selectedName + ".", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Dừng lại nếu đã có bài kiểm tra với số bài kiểm tra giống nhau trong cùng một nameExam
            }
        }

        int soCau = Integer.parseInt((String) this.soCau.getSelectedItem());
        int thoiGian = Integer.parseInt((String) this.thoiGian.getSelectedItem());

// Tạo một đối tượng bài kiểm tra mới
        exam ex = new exam(selectedName, number, soCau, thoiGian);

// Chèn bài kiểm tra vào cơ sở dữ liệu
        ExamModify.insertExam(ex);

// Cập nhật dataList
        dataList = ExamModify.getExamList(null, 0);

// Hiển thị dữ liệu đã cập nhật
        showData();

        JOptionPane.showMessageDialog(this, "Đã thêm đề thi thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        addOption aO = new addOption(selectedName, number, soCau);
        aO.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_saveBtnActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        // TODO add your handling code here:
        HomeForm hf = new HomeForm();
        hf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backBtnActionPerformed

    private void numberExamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberExamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numberExamActionPerformed

    private void deleteexamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteexamActionPerformed
        // TODO add your handling code here:
        int examIdToDelete = getSelectedExamId();
        if (examIdToDelete != -1) {
            int confirmation = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa kỳ thi này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                ExamModify.delete(examIdToDelete);
                dataList = ExamModify.getExamList(null, 0);
                showData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chưa chọn kỳ thi để xóa.");
        }
    }//GEN-LAST:event_deleteexamActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        // TODO add your handling code here:
        String searchName = JOptionPane.showInputDialog(this, "Nhập tên đề thi cần tìm kiếm:");
        int numberOfQuestions = -1;

        if (searchName != null && !searchName.isEmpty()) {
            String numberOfQuestionsInput = JOptionPane.showInputDialog(this, "Nhập Đề thi số (nếu có):");
            if (numberOfQuestionsInput != null && !numberOfQuestionsInput.isEmpty()) {
                try {
                    numberOfQuestions = Integer.parseInt(numberOfQuestionsInput);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Đề thi không hợp lệ. Vui lòng nhập lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            dataList = ExamModify.getExamList(searchName, numberOfQuestions);
            showData();

            // In ra console để kiểm tra
            System.out.println("Kết quả tìm kiếm:");
            for (exam ex : dataList) {
                System.out.println("ID: " + ex.getId() + ", Tên đề: " + ex.getNameExam() + ", Số câu: " + ex.getSoCauHoi() + ", Thời gian: " + ex.getThoigian());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đề thi cần tìm kiếm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_searchBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ExamForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExamForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExamForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExamForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExamForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JButton deleteexam;
    private javax.swing.JTable examTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> nameExam;
    private javax.swing.JTextField numberExam;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JComboBox<String> soCau;
    private javax.swing.JComboBox<String> thoiGian;
    // End of variables declaration//GEN-END:variables

    private int batchSize() {
        return 20;
    }
}
