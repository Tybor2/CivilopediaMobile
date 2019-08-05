package android.example.textreader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class TextReader {

    public static void main(String[] args){
        String filePath = "TextReader\\src\\main\\java\\Files\\Units_Text.xml";

        File xmlfile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlfile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Row");
            Pattern r = Pattern.compile("LOC_UNIT_[A-Za-z1-9_]*_DESCRIPTION");

            for(int i = 0; i < nodeList.getLength(); i++){
                /**if(r.matcher(nodeList.item(i).getNodeName()).find()){
                    System.out.println(nodeList.item(i));
                }else {
                    System.out.println("Nada");
                }**/
                if(nodeList.item(i).getNodeValue() != null){
                    try {
                        System.out.println("Tried it: " + nodeList.item(i).getTextContent());
                    } catch (NullPointerException e){

                    }
                }

                if(r.matcher(nodeList.item(i).getAttributes().item(0).getTextContent()).find()) {
                    String title = nodeList.item(i).getAttributes().item(0).getTextContent();
                    Pattern namePattern = Pattern.compile("LOC_UNIT_[A-Za-z1-9_]*_DESCRIPTION");
                    Matcher match = namePattern.matcher(title);
                    /**if(match.find()){
                        System.out.println("Name " + i + " Clear: " + title.replaceAll("LOC_UNIT_|_DESCRIPTION", "").replaceAll("_", " "));
                    }else {
                        System.out.println("Name " + i + ": " + nodeList.item(i).getAttributes().item(0).getTextContent());
                    }**/
                    System.out.println(nodeList.item(i).getLocalName());
                    System.out.println("Name " + i + " Clear: " + title.replaceAll("LOC_UNIT_|_DESCRIPTION", "").replaceAll("_", " "));
                    System.out.println("Description " + i + ": " + nodeList.item(i).getTextContent().replaceAll("\\[(.*?)\\] ", "").replaceAll("\t|\n", ""));
                }
            }
        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
    }
}
