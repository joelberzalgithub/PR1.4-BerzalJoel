import java.io.File;
import java.io.IOException;

import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PR142Main {
	
	static Scanner in = new Scanner(System.in); // System.in és global
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		try {
			
			// Analitza el fitxer XML i crea un document XML
			
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(System.getProperty("user.dir") + "/data/cursos.xml"));
			
			// Crea un objecte XPath
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			// Crea el menú principal
			
			boolean running = true;
			
			while (running) {
				
				System.out.println("\n***************************************************************\n\nEscull una opcio:\n\n0) Llistar ids de cursos" +
								   "\n1) Llistar tutors\n2) Llistar total d'alumnes\n3) Mostrar ids i titols dels moduls\n4) Llistar alumnes d'un curs" +
								   "\n5) Afegir un alumne a un curs\n6) Eliminar un alumne d'un curs\n100) Tornar enrere\n");
				
				try {
					
					int opcio = Integer.valueOf(llegirLinia("Opcio: "));
					
					switch (opcio) {
						case 0: llistarIdCursos(doc, xPath);
								break;
						case 1: llistarTutors(doc, xPath);
								break;
						case 2: llistarTotalAlumnes(doc, xPath);
								break;
						case 3: mostrarModuls(doc, xPath);
								break;
						case 4: llistarAlumnes(doc, xPath);
								break;
						case 5: afegirAlumne(doc, xPath);
								break;
						case 6: eliminarAlumne(doc, xPath);
								break;
						case 100: running = false;
								break;
						default: System.out.println("\nOpcio fora del rang!");
								break;
					}
					
				} catch (Exception e) {
					System.out.println("\nOpcio no numerica!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String llegirLinia (String text) {
		System.out.print(text);
		return in.nextLine();
    }
	
	public static void llistarIdCursos(Document doc, XPath xPath) {
		
		try {
			
			// Avaluem una expressió XPath que selecciona l'id de tots els cursos i obtenim una llista de nodes
			
			NodeList listCursos = (NodeList) xPath.compile("/cursos/curs/@id").evaluate(doc, XPathConstants.NODESET);
			
			// Imprimeix l'id de tots els cursos
			
			System.out.println("");
			
			for (int i = 0; i < listCursos.getLength(); i++) {
				System.out.println("Curs " + (i+1) + ": " + listCursos.item(i).getNodeValue());
			}
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	public static void llistarTutors(Document doc, XPath xPath) {
		
		try {
			
			// Avaluem una expressió XPath que selecciona els tutors i obtenim una llista de nodes
			
			NodeList listTutors = (NodeList) xPath.compile("/cursos/curs/tutor").evaluate(doc, XPathConstants.NODESET);
			
			// Imprimeix els tutors
			
			System.out.println("");
			
			for (int i = 0; i < listTutors.getLength(); i++) {
				System.out.println("Tutor " + (i+1) + ": " + listTutors.item(i).getTextContent());
			}
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	public static void llistarTotalAlumnes(Document doc, XPath xPath) {
		
		try {
			
			// Avaluem una expressió XPath que selecciona els alumnes i obtenim una llista de nodes
			
			NodeList listAlumnes = (NodeList) xPath.compile("/cursos/curs/alumnes/alumne").evaluate(doc, XPathConstants.NODESET);
			
			// Imprimeix el total d'alumnes
			
			System.out.println("\nAlumnes totals: " + listAlumnes.getLength());
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	public static void mostrarModuls(Document doc, XPath xPath) {
		
		try {
			
			Scanner sc = new Scanner(System.in);
			boolean idValid = false;
			String idCurs = "";
			
			while (!idValid) {
			
				// Demanem l'id d'un curs
				
				System.out.print("\nEscriu l'id d'un curs: ");
				idCurs = sc.next();
				
				// Avaluem una expressió XPath que selecciona l'id de tots els cursos i obtenim una llista de nodes
				
				NodeList listCursos = (NodeList) xPath.compile("/cursos/curs/@id").evaluate(doc, XPathConstants.NODESET);
				
				// Comprovem si l'id introduït existeix dins del fitxer XML
				
				for (int i = 0; i < listCursos.getLength(); i++) {
					if (idCurs.equals(listCursos.item(i).getNodeValue())) {
						idValid = true;
					}
				}
				
				if (!idValid) {
					System.out.println("El curs amb id " + idCurs + " no existeix!");
				}
			}
			
			// Avaluem una expressió XPath que selecciona l'id dels mòduls i obtenim una llista de nodes
			
			NodeList listIdModuls = (NodeList) xPath.compile("/cursos/curs[@id='" + idCurs + "']/moduls/modul/@id").evaluate(doc, XPathConstants.NODESET);
			
			// Avaluem una expressió XPath que selecciona el títol dels mòduls i obtenim una llista de nodes
			
			NodeList listTitolModuls = (NodeList) xPath.compile("/cursos/curs[@id='" + idCurs + "']/moduls/modul/titol").evaluate(doc, XPathConstants.NODESET);
			
			// Imprimeix tant l'id com el títol dels mòduls
			
			System.out.println("");
			
			for (int i = 0; i < listIdModuls.getLength(); i++) {
				System.out.println("Modul " + (i+1) + ": [Id = " + listIdModuls.item(i).getNodeValue() + ", Titol = " + listTitolModuls.item(i).getTextContent() + "]");
			}
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	public static void llistarAlumnes(Document doc, XPath xPath) {
		
		try {
			
			Scanner sc = new Scanner(System.in);
			boolean idValid = false;
			String idCurs = "";
			
			while (!idValid) {
			
				// Demanem l'id d'un curs
				
				System.out.print("\nEscriu l'id d'un curs: ");
				idCurs = sc.next();
				
				// Avaluem una expressió XPath que selecciona l'id de tots els cursos i obtenim una llista de nodes
				
				NodeList listCursos = (NodeList) xPath.compile("/cursos/curs/@id").evaluate(doc, XPathConstants.NODESET);
				
				// Comprovem si l'id introduït existeix dins del fitxer XML
				
				for (int i = 0; i < listCursos.getLength(); i++) {
					if (idCurs.equals(listCursos.item(i).getNodeValue())) {
						idValid = true;
					}
				}
				
				if (!idValid) {
					System.out.println("El curs amb id " + idCurs + " no existeix!");
				}
			}
			
			// Avaluem una expressió XPath que selecciona els alumnes i obtenim una llista de nodes
			
			NodeList listAlumnes = (NodeList) xPath.compile("/cursos/curs[@id='" + idCurs + "']/alumnes/alumne").evaluate(doc, XPathConstants.NODESET);
			
			// Imprimeix els alumnes
			
			System.out.println("");
			
			for (int i = 0; i < listAlumnes.getLength(); i++) {
				System.out.println("Alumne " + (i+1) + ": " + listAlumnes.item(i).getTextContent());
			}
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	public static void afegirAlumne(Document doc, XPath xPath) {
		
		try {
			
			Scanner sc = new Scanner(System.in);
			boolean idValid = false;
			String idCurs = "";
			
			while (!idValid) {
			
				// Demanem l'id d'un curs
				
				System.out.print("\nEscriu l'id d'un curs: ");
				idCurs = sc.next();
				
				// Avaluem una expressió XPath que selecciona l'id de tots els cursos i obtenim una llista de nodes
				
				NodeList listCursos = (NodeList) xPath.compile("/cursos/curs/@id").evaluate(doc, XPathConstants.NODESET);
				
				// Comprovem si l'id introduït existeix dins del fitxer XML
				
				for (int i = 0; i < listCursos.getLength(); i++) {
					if (idCurs.equals(listCursos.item(i).getNodeValue())) {
						idValid = true;
					}
				}
				
				if (!idValid) {
					System.out.println("El curs amb id " + idCurs + " no existeix!");
				}
			}
			
			// Demanem el nom d'un nou alumne
			
			System.out.print("\nEscriu el nom del nou alumne: ");
			String nomAlumne = sc.next();
			
			// Avaluem una expressió XPath que selecciona els alumnes d'un curs
			
			XPathExpression expression = xPath.compile("/cursos/curs[@id='" + idCurs + "']/alumnes");
			
			// Obtenim un element de la expressió
			
			Element elmAlumne = (Element) expression.evaluate(doc,  XPathConstants.NODE);
			
			// Obtenim un element d'un alumne
			
			Element elmNouAlumne = doc.createElement("alumne");
			
			// Afegim a l'alumne el nom que s'ha demanat anteriorment
			
			elmNouAlumne.setTextContent(nomAlumne);
			
			// Inserim l'alumne nou dins dels alumnes
			
			elmAlumne.appendChild(elmNouAlumne);
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	public static void eliminarAlumne(Document doc, XPath xPath) {
		
		try {
			
			Scanner sc = new Scanner(System.in);
			boolean idValid = false;
			String idCurs = "";
			
			while (!idValid) {
			
				// Demanem l'id d'un curs
				
				System.out.print("\nEscriu l'id d'un curs: ");
				idCurs = sc.next();
				
				// Avaluem una expressió XPath que selecciona l'id de tots els cursos i obtenim una llista de nodes
				
				NodeList listCursos = (NodeList) xPath.compile("/cursos/curs/@id").evaluate(doc, XPathConstants.NODESET);
				
				// Comprovem si l'id introduït existeix dins del fitxer XML
				
				for (int i = 0; i < listCursos.getLength(); i++) {
					if (idCurs.equals(listCursos.item(i).getNodeValue())) {
						idValid = true;
					}
				}
				
				if (!idValid) {
					System.out.println("El curs amb id " + idCurs + " no existeix!");
				}
			}

			// Demanem el nom d'un nou alumne
			
			System.out.print("\nEscriu el nom del nou alumne: ");
			String nomAlumne = sc.next();
			
			// Avaluem una expressió XPath que selecciona els alumnes d'un curs
			
			XPathExpression expression = xPath.compile("/cursos/curs[@id='" + idCurs + "']/alumnes/alumne[text()='" + nomAlumne + "']");
			
			// Obtenim un node de la expressió
			
			Node nodeAlumne = (Node) expression.evaluate(doc, XPathConstants.NODE);
			
			// Eliminem l'alumne sel·leccionat
			
			if (nodeAlumne != null) {
				nodeAlumne.getParentNode().removeChild(nodeAlumne);
			}
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}			
	}
}
