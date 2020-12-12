package sample;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class MySettings
{

    private String mNFLDataFile;
    private String mNFLScoreURL;
    private String mNFLDataFileTemp;
    private String mAPIKey;
    private String mAPISecretKey;
    private String mBearerToken;
    private String mAccessToken;
    private String mAccessSecretToken;
    private String mTwitterUpdateTeam;

    public String getmNFLDataFile() {
        return mNFLDataFile;
    }

    public String getmNFLScoreURL() {
        return mNFLScoreURL;
    }

    public String getmNFLDataFileTemp() {
        return mNFLDataFileTemp;
    }

    public String getmAPIKey() {
        return mAPIKey;
    }

    public String getmAPISecretKey() {
        return mAPISecretKey;
    }

    public String getmBearerToken() {
        return mBearerToken;
    }

    public String getmAccessToken() {
        return mAccessToken;
    }

    public String getmAccessSecretToken() {
        return mAccessSecretToken;
    }

    public String getmTwitterUpdateTeam() {
        return mTwitterUpdateTeam;
    }

    public MySettings()
    {

        Utilities util = new Utilities();
        Document doc = util.FileToDocument("D:\\Dropbox\\COP2800\\JavaFinal\\NFLGames-Version1.6\\src\\sample\\MyAppConfig.xml");

        //Creating a NodeList object
        NodeList nodeList = doc.getElementsByTagName("Setting");
        // NodeList is not iterable, so we are using for loop

        for (int itr = 0; itr < nodeList.getLength(); itr++)
        {
            Node node = nodeList.item(itr);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) node;
                mNFLDataFile = eElement.getElementsByTagName("NFLDataFile").item(0).getTextContent();
                mNFLScoreURL = eElement.getElementsByTagName("NFLScoreURL").item(0).getTextContent();
                mNFLDataFileTemp = eElement.getElementsByTagName("NFLDataFileTemp").item(0).getTextContent();
                mAPIKey = eElement.getElementsByTagName("APIKey").item(0).getTextContent();
                mAPISecretKey = eElement.getElementsByTagName("APISecretKey").item(0).getTextContent();
                mBearerToken = eElement.getElementsByTagName("BearerToken").item(0).getTextContent();
                mAccessToken = eElement.getElementsByTagName("AccessToken").item(0).getTextContent();
                mAccessSecretToken = eElement.getElementsByTagName("AccessSecretToken").item(0).getTextContent();
                mTwitterUpdateTeam = eElement.getElementsByTagName("TwitterUpdateTeam").item(0).getTextContent();
            }
        }

    }

}
