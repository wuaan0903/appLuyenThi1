/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package exam.view;

import com.mysql.cj.protocol.x.Notice;
import com.sun.jdi.connect.spi.Connection;

import exam.controller.ExamModify;
import exam.model.Question;
import exam.model.exam;
import java.awt.Color;
import java.awt.image.TileObserver;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.Timer;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import user.view.HistoryList;

/**
 *
 * @author admin
 */
public class viewTest extends javax.swing.JFrame {

    /**
     * Creates new form viewTest
     */
    List<Question> dataList;
    List<exam> ex;
    Question q;
    int pos = 0;
    private int totalTimeInSeconds;
    private int initialTimeInSeconds;// Thời gian tổng cộng cho bài kiểm tra tính bằng giây (300 giây = 5 phút)
    private Timer countdownTimer;

    public class DatabaseConnection {

        private static java.sql.Connection connection;

        public static java.sql.Connection getConnection() {
            if (connection == null) {
                try {
                    // Thay đổi thông tin kết nối theo CSDL của bạn
                    String url = "jdbc:mysql://localhost:3306/tracnghiem";
                    String username = "root";
                    String password = "";

                    connection = DriverManager.getConnection(url, username, password);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return connection;
        }
    }

    public viewTest(exam e, String id, String name) {
        initComponents();
        idU.setText(id);
        nameU.setText(name);
        dataList = ExamModify.getQuestion(e.getNameExam(), e.getNumberExam());
        View();
        ViewList();
        nameExam.setText(e.getNameExam());
        numberExam.setText("" + e.getNumberExam());
        totalTimeInSeconds = e.getThoigian() * 60;
        startCountdown();
    }

    private void startCountdown() {
        initialTimeInSeconds = totalTimeInSeconds;
        countdownTimer = new Timer(1000, e -> {
            if (totalTimeInSeconds > 0) {
                int minutes = totalTimeInSeconds / 60;
                int seconds = totalTimeInSeconds % 60;

                String timeString = String.format("%02d:%02d", minutes, seconds);
                time.setText(timeString);

                totalTimeInSeconds--; // Giảm thời gian tổng cộng
            } else {
                // Hết giờ
                time.setText("Hết giờ!");
                countdownTimer.stop();
                ResultForm rf = new ResultForm(this.getScore(), this.getTime(), dataList);
                rf.setVisible(true);
                this.dispose();
                // Dừng đồng hồ đếm ngược khi hết giờ
                // Tuỳ chọn: thực hiện các hành động cần thiết khi hết giờ
            }
        });
        countdownTimer.start(); // Bắt đầu đồng hồ đếm ngược
    }

    int getTime() {

        int timeSpentInSeconds = initialTimeInSeconds - totalTimeInSeconds;
        ResultForm rf = new ResultForm(this.getScore(), timeSpentInSeconds, dataList);
        rf.setVisible(true);
        this.dispose();
        return initialTimeInSeconds - totalTimeInSeconds;
    }

    public void View() {

        q = dataList.get(pos);
        this.question.setText("Câu số " + (pos + 1) + " : " + q.getQuestion());
        this.answerA.setText("A." + q.getAnswerA());
        this.answerB.setText("B." + q.getAnswerB());
        this.answerC.setText("C." + q.getAnswerC());
        this.answerD.setText("D." + q.getAnswerD());

        switch (q.getStatus()) {
            case 1:
                OnOff(true, false, false, false);
                break;
            case 2:
                OnOff(false, true, false, false);
                break;
            case 3:
                OnOff(false, false, true, false);
                break;
            case 4:
                OnOff(false, false, false, true);
                break;
            default:
                this.buttonGroup1.clearSelection();
                break;
        }
    }

    public void ViewList() {
        DefaultListModel model = new DefaultListModel();
        this.listQ.setModel(model);
        int n = 1;
        for (Question x : dataList) {
            model.addElement("Câu số : " + n++);

        }
    }

    public int Choice() {
        int n = 0;
        if (this.answerA.isSelected()) {
            n = 1;
        }
        if (this.answerB.isSelected()) {
            n = 2;
        }
        if (this.answerC.isSelected()) {
            n = 3;
        }
        if (this.answerD.isSelected()) {
            n = 4;
        }
        return n;
    }

    int getScore() {
        int d = 0;
        for (Question x : dataList) {
            if (x.getAnswer() == x.getStatus()) {
                d++;
            }
        }
        return d;
    }

    public void OnOff(boolean a, boolean b, boolean c, boolean d) {
        this.answerA.setSelected(a);
        this.answerB.setSelected(b);
        this.answerC.setSelected(c);
        this.answerD.setSelected(d);
    }

    public void goToNextQuestion() {
        if (pos < dataList.size() - 1) { // Kiểm tra xem còn câu hỏi tiếp theo không
            q.setStatus(Choice()); // Lấy trạng thái câu hỏi hiện tại
            dataList.set(pos, q); // Cập nhật câu hỏi hiện tại trong danh sách

            pos++; // Tăng chỉ số câu hỏi để chuyển đến câu hỏi tiếp theo
            View(); // Hiển thị câu hỏi tiếp theo
//            ViewResult(); // Cập nhật kết quả
        }
        // Có thể thêm thông báo nếu không còn câu hỏi tiếp theo
    }

    private String convertSecondsToTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        numberExam = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        question = new javax.swing.JLabel();
        BackBtn = new javax.swing.JButton();
        NextBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        answerA = new javax.swing.JRadioButton();
        answerB = new javax.swing.JRadioButton();
        answerD = new javax.swing.JRadioButton();
        answerC = new javax.swing.JRadioButton();
        submitBtn = new javax.swing.JButton();
        ExitBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listQ = new javax.swing.JList<>();
        nameExam = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        idU = new javax.swing.JLabel();
        nameU = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        numberExam.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        numberExam.setForeground(new java.awt.Color(0, 0, 0));
        numberExam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numberExam.setText("ĐỀ THI SỐ 1");

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        question.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        question.setForeground(new java.awt.Color(255, 255, 255));
        question.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        question.setText("Câu số 1 :       1+1 = ?");

        BackBtn.setBackground(new java.awt.Color(0, 153, 153));
        BackBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/left-arrow-solid-60 (1).png"))); // NOI18N
        BackBtn.setBorder(null);
        BackBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBtnActionPerformed(evt);
            }
        });

        NextBtn.setBackground(new java.awt.Color(0, 153, 153));
        NextBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/right-arrow-solid-60 (1).png"))); // NOI18N
        NextBtn.setBorder(null);
        NextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(BackBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(question, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(738, Short.MAX_VALUE)
                    .addComponent(NextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(16, 16, 16)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(question, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BackBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(NextBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));

        buttonGroup1.add(answerA);
        answerA.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        answerA.setForeground(new java.awt.Color(255, 255, 255));
        answerA.setText("A. 1");

        buttonGroup1.add(answerB);
        answerB.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        answerB.setForeground(new java.awt.Color(255, 255, 255));
        answerB.setText("B. 2");

        answerD.setBackground(new java.awt.Color(0, 153, 153));
        buttonGroup1.add(answerD);
        answerD.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        answerD.setForeground(new java.awt.Color(255, 255, 255));
        answerD.setText("D. 4");
        answerD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answerDActionPerformed(evt);
            }
        });

        answerC.setBackground(new java.awt.Color(0, 153, 153));
        buttonGroup1.add(answerC);
        answerC.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        answerC.setForeground(new java.awt.Color(255, 255, 255));
        answerC.setText("C. 3");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(answerA, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                        .addGap(128, 128, 128))
                    .addComponent(answerB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(answerC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(answerD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(answerA)
                .addGap(18, 18, 18)
                .addComponent(answerB)
                .addGap(18, 18, 18)
                .addComponent(answerC)
                .addGap(18, 18, 18)
                .addComponent(answerD)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        submitBtn.setBackground(new java.awt.Color(0, 153, 153));
        submitBtn.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        submitBtn.setForeground(new java.awt.Color(255, 255, 255));
        submitBtn.setText("Nộp bài");
        submitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitBtnActionPerformed(evt);
            }
        });

        ExitBtn.setBackground(new java.awt.Color(0, 153, 153));
        ExitBtn.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        ExitBtn.setForeground(new java.awt.Color(255, 255, 255));
        ExitBtn.setText("Thoát");
        ExitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitBtnActionPerformed(evt);
            }
        });

        listQ.setBackground(new java.awt.Color(204, 255, 255));
        listQ.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        listQ.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        listQ.setForeground(new java.awt.Color(51, 51, 51));
        listQ.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Câu 1", "Câu 2", "Câu 3", "Câu 4", "Câu 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listQ.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listQValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listQ);

        nameExam.setFont(new java.awt.Font("Montserrat", 0, 20)); // NOI18N
        nameExam.setForeground(new java.awt.Color(0, 0, 0));
        nameExam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameExam.setText("Môn học : Mạng máy tính");

        time.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        time.setForeground(new java.awt.Color(0, 0, 0));
        time.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/timer-regular-36.png"))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        idU.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        idU.setForeground(new java.awt.Color(0, 0, 0));
        idU.setText("1");

        nameU.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nameU.setForeground(new java.awt.Color(0, 0, 0));
        nameU.setText("Phan Van Tươi");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Mã sinh viên:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idU, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nameU, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(nameU, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(idU, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(nameExam, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(135, 135, 135)
                        .addComponent(time, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(348, 348, 348))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(submitBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ExitBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(numberExam, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nameExam, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)))
                .addComponent(numberExam, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(submitBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ExitBtn))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 869, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void answerDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answerDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_answerDActionPerformed

    private void ExitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ExitBtnActionPerformed

    private void listQValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listQValueChanged
        // TODO add your handling code here:
        int n = Choice();
        q.setStatus(n);
        dataList.set(pos, q);
        pos = this.listQ.getSelectedIndex();
        View();
    }//GEN-LAST:event_listQValueChanged

    private void submitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitBtnActionPerformed

        int lastQuestionStatus = Choice();
        q.setStatus(lastQuestionStatus);
        dataList.set(pos, q);
        double score = this.getScore();
        int timer = this.getTime();
        String formattedTime = convertSecondsToTime(timer);

        // Lấy thông tin người dùng hoặc các dữ liệu cần thiết từ form
        String selectedName = (String) this.nameExam.getText(); // Thay thế nameExam bằng thành phần tương ứng trên form của bạn
        String NameUser = (String) this.nameU.getText();
        int numberEx = Integer.parseInt(this.numberExam.getText());
        int userID = Integer.parseInt(this.idU.getText()); // Thay thế idd bằng thành phần tương ứng trên form của bạn

        // Kết nối đến cơ sở dữ liệu
        try (java.sql.Connection connection = DatabaseConnection.getConnection()) {
            // Thực hiện truy vấn SQL để chèn dữ liệu vào bảng history_test
            String sql = "INSERT INTO history_test (user_id,name, nameExam, ID_exam, total, time_completed) VALUES (?, ?, ?, ?, ?, ?)";

            try (java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userID);
                preparedStatement.setString(2, NameUser);
                preparedStatement.setString(3, selectedName);
                preparedStatement.setInt(4, numberEx);
                preparedStatement.setDouble(5, score);
                preparedStatement.setString(6, formattedTime);

                // Thực hiện lưu dữ liệu vào bảng
                preparedStatement.executeUpdate();
            }

            // Hiển thị cửa sổ kết quả
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý lỗi, có thể hiển thị thông báo lỗi cho người dùng nếu cần
        }
        ResultForm rf = new ResultForm(this.getScore(), this.getTime(), dataList);
        rf.setVisible(true);


    }//GEN-LAST:event_submitBtnActionPerformed

    private void BackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBtnActionPerformed
        // TODO add your handling code here:
        if (pos > 0) { // Kiểm tra xem còn câu hỏi tiếp theo không
            q.setStatus(Choice()); // Lấy trạng thái câu hỏi hiện tại
            dataList.set(pos, q); // Cập nhật câu hỏi hiện tại trong danh sách

            pos--; // Tăng chỉ số câu hỏi để chuyển đến câu hỏi tiếp theo
            View(); // Hiển thị câu hỏi tiếp theo
//            ViewResult(); // Cập nhật kết quả
        }
        // Có thể thêm thông báo nếu không còn câu hỏi tiếp theo
    }//GEN-LAST:event_BackBtnActionPerformed

    private void NextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextBtnActionPerformed
        // TODO add your handling code here:
        goToNextQuestion();
    }//GEN-LAST:event_NextBtnActionPerformed

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
            java.util.logging.Logger.getLogger(viewTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBtn;
    private javax.swing.JButton ExitBtn;
    private javax.swing.JButton NextBtn;
    private javax.swing.JRadioButton answerA;
    private javax.swing.JRadioButton answerB;
    private javax.swing.JRadioButton answerC;
    private javax.swing.JRadioButton answerD;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel idU;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listQ;
    private javax.swing.JLabel nameExam;
    private javax.swing.JLabel nameU;
    private javax.swing.JLabel numberExam;
    private javax.swing.JLabel question;
    private javax.swing.JButton submitBtn;
    private javax.swing.JLabel time;
    // End of variables declaration//GEN-END:variables
}
