import java.io.IOException;
import java.util.*;

public class Main {
	
	static Scanner in = new Scanner(System.in); // System.in és global
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		boolean running = true;
		
		while (running) {
			
			System.out.println("Escull una opcio:\n\n0) PR140Main\n1) PR141Main\n2) PR142Main\n100) Sortir\n");
			
			try {
				
				int opcio = Integer.valueOf(llegirLinia("Opcio: "));
				
				switch (opcio) {
					case 0: PR140Main.main(args);
							break;
					case 1: PR141Main.main(args);
							break;
					case 2: PR142Main.main(args);
							break;
					case 100: running = false;
							break;
					default: System.out.println("\nOpcio fora del rang!");
							break;
				}
				
				System.out.println("\n***************************************************************\n");
			
			} catch (Exception e) {
				System.out.println("\nOpcio no numerica!\n\n***************************************************************\n");
			}
		}
	}
	
	public static String llegirLinia (String text) {
		System.out.print(text);
		return in.nextLine();
    }
}
