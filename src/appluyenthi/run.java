/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appluyenthi;

import Login.HomeLogin;

/**
 *
 * @author admin
 */
public class run {
    
    public void Run() {
        HomeLogin hl = new HomeLogin();
        hl.setVisible(true);
    }

    public static void main(String[] args) {
        run runner = new run();
        runner.Run();
    }
}

