/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.infn.ct;

import java.io.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 *
 * @author ccarrubba
 */
public class ApiVirtuosoForGraph {
    
    public static String FILE_JSON_RESOURCE = SemanticSearch_portlet.appServerPath+"/job/json_resource.txt";
    
    public static HttpMethod callAPI_virtuoso() {
        
       String search= "http://openDocuments/resource/oai:mpra.ub.uni-muenchen.de:27785";
       String word="eye"; 
       
        HttpMethod method = null;


        HttpClient client = new HttpClient();
      
       // method = new GetMethod("http://www.chain-project.eu/virtuoso/api/singleResource?id="+search);
       method = new GetMethod("http://www.chain-project.eu/virtuoso/api/resources?keyword="+word+"&limit=20&offset=0");

       // System.out.println("QUERY API=> http://www.chain-project.eu/virtuoso/api/singleResource?id="+search);
        
        System.out.println("QUERY API=> http://www.chain-project.eu/virtuoso/api/resources?keyword="+word+"&limit=20&offset=0");  



        return method;

    }
    
    
    public static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {

            Writer writer = new StringWriter();



            char[] buffer = new char[1024];

            try {

                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                int n;

                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);

                }

            } finally {

                is.close();

            }

            return writer.toString();

        } else {

            return "";

        }

    }
    
     public static void getRecordVirtuoso() {

        String responseXML = null;
        HttpClient client = new HttpClient();
        HttpMethod method = callAPI_virtuoso();


        try {
            client.executeMethod(method);

            if (method.getStatusCode() == HttpStatus.SC_OK) {



                method.getResponseBody();


                responseXML = convertStreamToString(method.getResponseBodyAsStream());




                

                FileWriter fw = new FileWriter(FILE_JSON_RESOURCE);

                
                System.out.println("sono qua al file =>"+FILE_JSON_RESOURCE);
                fw.append(responseXML);

                fw.close();


            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            method.releaseConnection();

        }


    }
    
}
