package Controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class CozinhaThread extends Thread {
	static Semaphore pratos = new Semaphore(5);
	static Semaphore entrega = new Semaphore(1);
	int pratoId;
	public CozinhaThread(int pratoId) {
		this.pratoId = pratoId;
	}
	
	public void run() {
		try {
			pratos.acquire();
			this.cozinhar();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pratos.release();
		}
	}
	
	public void cozinhar() throws InterruptedException {
		int tempoCozimento;
		if((this.pratoId % 2) == 0) {
			Random random = new Random();
			tempoCozimento = random.nextInt(600, 1200);
			System.out.println("Cozinhando lasanha a bolonhesa");
			
			double porcentagem = 0;
			double tempoCozimentoWait = tempoCozimento / 100;
			while(true) {
				porcentagem += tempoCozimentoWait;
				System.out.println(String.format("O prato %d esta %f por cento completo", this.pratoId, porcentagem));
				
				Thread.sleep((long) tempoCozimentoWait);
				if(this.isInterrupted() || porcentagem > 90) {
					System.out.println(String.format("A lasanha a bolonhesa %d esta pronto para entrega", this.pratoId));
					CozinhaThread.entrega.acquire();
					this.entregar();
					CozinhaThread.entrega.release();
					break;
				}
			}
		} else {
			Random random = new Random();
			tempoCozimento = random.nextInt(500, 800);
			System.out.println("Cozinhando sopa de cebola");
			
			double porcentagem = 0;
			double tempoCozimentoWait = tempoCozimento;
			while(true) {
				tempoCozimentoWait = tempoCozimento / 100;
				porcentagem += tempoCozimento / 100;
				System.out.println(String.format("O prato %d esta %f por cento completo", this.pratoId, porcentagem));
				
				Thread.sleep((long) tempoCozimentoWait);
				if(this.isInterrupted() || porcentagem > 90) {
					System.out.println(String.format("A sopa de cebola %d esta pronta para entrega", this.pratoId));
					CozinhaThread.entrega.acquire();
					this.entregar();
					CozinhaThread.entrega.release();
					break;
				}
			}
		}
	}
	
	 public void entregar()throws InterruptedException {
		 Thread.sleep(500);
		 System.out.println(String.format("Prato %d entregue!", this.pratoId));
	 }
}
