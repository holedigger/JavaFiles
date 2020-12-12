package sample;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.net.URL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Utilities
{

    public Document  FileToDocument (String filepath)
    {//Loads an xml file and returns a Document object

            //Creating a constructor of file class
            File file = new File(filepath);

            //Defines a factory API that enables applications to obtain a parser that produces DOM object trees from XML documents.
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = null;
            Document doc =  null;
            try
            {
                //An instance of builder to parse the specified xml file
                db = dbf.newDocumentBuilder();
                //Creating an object of Document.
                //Represents the entire XML document. A Document object is often referred to as a DOM tree.
                doc = db.parse(file);
            }
            catch (Exception ex)
            {
            }

            //Internal cleansing of the document for better,clearer results and reading.
            doc.getDocumentElement().normalize();

            return doc;

    }

    public void DownLoadWebPage (String urlString, String FileTemp) throws IOException
    {
        URL url = new URL(urlString);
        try(
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                BufferedWriter writer = new BufferedWriter(new FileWriter(FileTemp));
        )
        {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
            }
        }
    }


}


