import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PR141Main {
	
	public static void main(String[] args) {
		
		try {
			
			// Crear la carpeta 'data' si no existeix
			
	        File dir = new File("data");
	        
	        if (!dir.exists()) {
	            if (!dir.mkdirs()) {
	                System.out.println("Error en la creacio de la carpeta 'data'");
	            }
	        }
			
			// Crea un nou document XML
			
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			
			// Crea l'element root del document XML
			
			Element elmRoot = doc.createElement("biblioteca");
			
			// Afegeix l'element root al document XML
			
			doc.appendChild(elmRoot);
			
			// Crea l'element "llibre", li afegeix l'atribut "id" i afegeix aquest element a l'element root
			
			Element elmLlibre = doc.createElement("llibre");
			elmLlibre.setAttribute("id", "001");
			elmRoot.appendChild(elmLlibre);
			
			// Crea l'element "titol" i l'afegeix a l'element "llibre"
			
			Element elmTitol = doc.createElement("titol");
			elmTitol.appendChild(doc.createTextNode("El viatge dels venturons"));
			elmLlibre.appendChild(elmTitol);
			
			// Crea l'element "autor" i l'afegeix a l'element "llibre"
			
			Element elmAutor = doc.createElement("autor");
			elmAutor.appendChild(doc.createTextNode("Joan Pla"));
			elmLlibre.appendChild(elmAutor);
			
			// Crea l'element "anyPublicacio" i l'afegeix a l'element "llibre"
			
			Element elmAnyPublicacio = doc.createElement("anyPublicacio");
			elmAnyPublicacio.appendChild(doc.createTextNode("1998"));
			elmLlibre.appendChild(elmAnyPublicacio);
			
			// Crea l'element "editorial" i l'afegeix a l'element "llibre"
			
			Element elmEditorial = doc.createElement("editorial");
			elmEditorial.appendChild(doc.createTextNode("Edicions Mar"));
			elmLlibre.appendChild(elmEditorial);
			
			// Crea l'element "genere" i l'afegeix a l'element "llibre"
			
			Element elmGenere = doc.createElement("genere");
			elmGenere.appendChild(doc.createTextNode("Aventura"));
			elmLlibre.appendChild(elmGenere);
			
			// Crea l'element "pagines" i l'afegeix a l'element "llibre"
			
			Element elmPagines = doc.createElement("pagines");
			elmPagines.appendChild(doc.createTextNode("320"));
			elmLlibre.appendChild(elmPagines);
			
			// Crea l'element "disponible" i l'afegeix a l'element "llibre"
			
			Element elmDisponible = doc.createElement("disponible");
			elmDisponible.appendChild(doc.createTextNode("true"));
			elmLlibre.appendChild(elmDisponible);
			
			// Crea un transformador XSLT
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			
			// Estableix la propietat OMIT_XML_DECLARATION a "no" per no ometre la declaració XML del document XML resultant
			
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			
			// Estableix la propietat INDENT a "yes" per indentar el document XML resultant
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			// Crea una instància de DOMSource a partir del document XML
			
			DOMSource source = new DOMSource(doc);
			
			// Crea una instància de StreamResult a partir del camí del fitxer XML
			
			StreamResult result = new StreamResult(new FileOutputStream(new File(System.getProperty("user.dir") + "/data/biblioteca.xml")));
			
			// Transforma el document XML especificat per source i escriu el document XML resultant a l'objecte especificat per result
			
			transformer.transform(source, result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
