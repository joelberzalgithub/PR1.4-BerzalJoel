import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PR140Main {
	
	public static void main(String[] args) {
		
		try {
			
			// Crear la carpeta 'data' si no existeix
			
	        File dir = new File("data");
	        
	        if (!dir.exists()) {
	            if (!dir.mkdirs()) {
	                System.out.println("Error en la creacio de la carpeta 'data'");
	            }
	        }
			
			// Analitza el fitxer XML
			
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(System.getProperty("user.dir") + "/data/persones.xml"));
			
			// Obté una llista de tots els elements persona "persona" del document
			
			NodeList listPersones = doc.getElementsByTagName("persona");
			
			// Crea la part superior de la taula
			
			System.out.println("");
			System.out.println(String.format("%-20s %-20s %-10s %-20s\n", "Nom", "Cognom", "Edat", "Ciutat"));
			System.out.println("---------------------------------------------------------------\n");
			
			// Bucle for per recòrrer la llista de persones
			
			for (int i = 0; i < listPersones.getLength(); i++) {
				// Obté la persona actual
				Node nodePersona = listPersones.item(i);
				// Comprova si la persona actual és un element
				if (nodePersona.getNodeType() == Node.ELEMENT_NODE) {
					// Converteix l'element actual en una persona
					Element elm = (Element) nodePersona;
					// **Obté el nom de la persona**
					String nom = elm.getElementsByTagName("nom").item(0).getTextContent();
					// **Obté el cognom de la persona**
					String cognom = elm.getElementsByTagName("cognom").item(0).getTextContent();
					// **Obté l'edat de la persona**
					String edat = elm.getElementsByTagName("edat").item(0).getTextContent();
					// **Obté el nom de la ciutat on viu la persona**
					String ciutat = elm.getElementsByTagName("ciutat").item(0).getTextContent();
					// Dona format a les dades obtingudes i les imprimeix per pantalla
					System.out.println(String.format("%-20s %-20s %-10s %-20s\n", nom, cognom, edat, ciutat));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
