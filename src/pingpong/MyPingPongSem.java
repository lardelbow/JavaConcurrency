package pingpong;
import java.util.concurrent.Semaphore;

public class MyPingPongSem {
	
	public static void main(String[] args) throws InterruptedException {
		
		final Semaphore pongSema=new Semaphore(1,true);
		final Semaphore pingSema=new Semaphore(1,true);
		pongSema.acquire();
		
		System.out.println("Pong anyone?");
		
		Thread pong=new Thread(new Runnable() {
			public void run() {
				for(int i=1;i<20;i++) {
					try {
						pongSema.acquire();
						System.out.println("Pong ("+i+")");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally {
						pingSema.release();
					}
				}
			}
		});
		
		Thread ping=new Thread(new Runnable() {
			public void run() {
				for(int i=1;i<20;i++) {
					try {
						pingSema.acquire();
						System.out.println("Ping ("+i+")");
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						pongSema.release();
					}
				}
			}
		});
		
		ping.start();
		pong.start();
			
		ping.join();
		pong.join();
		
		System.out.println("That's all folks!!");
	}
}
