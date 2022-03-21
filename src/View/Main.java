package View;

import Controller.CozinhaThread;

public class Main {
	public static void main(String[] args) {
		for(int i = 0; i < 20; i++) {
			new CozinhaThread(i).start();
		}
	}
}
