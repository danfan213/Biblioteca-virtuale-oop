package controller;

import java.awt.EventQueue;

import view.LoginView;
public class Main {
	 
public static void main(String[] args) {
EventQueue.invokeLater(new Runnable() {
public void run() {
start();
}
});
}
private static void start() {
 UserCon log=new UserCon();
 log.InitLog();



}
}

