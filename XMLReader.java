package nflx;



import java.io.*;
import java.util.regex.Matcher;
import java.util.*;
import java.lang.*;


import org.w3c.dom.*;

import javax.xml.parsers.*;

import org.xml.sax.*; 

public class XMLReader{
	private String fn_ = null;
	
	public boolean showAll = true;
	
	public void setDebug(boolean z) {
		showAll = z;
	}
	
	public void out(String s) {
		if (showAll) {System.out.println(s);}
	}
    public XMLReader (String fn) {
    	out("Filename to read is "+fn);
    	File fyle = new File (fn);
    	try {
    	BufferedReader ifr = new BufferedReader (new FileReader(fyle));
    	int maxlines=6;
    	int lines = 0;
    	out ("reading a bit of the input file");
    	while (lines < maxlines) {
    		String lyne = ifr.readLine();
    		int linez = lines+1;
    		out("Input line " + linez+ ":" + lyne);
    		lines +=1 ;
    	   }
    	} catch (IOException ioe) {
    		out("OOOPS io error: " + ioe.getMessage());
    	}
    	
    	fn_ = fn;
    }
    public void outun(String z) {
    	System.out.println(z);
    }
    //-------------------Fixit--------------to reformat the filename
    String fixit(String toBeFixed) {
    	String result = null;
    	StringBuffer sb = new StringBuffer("file:///");
    	String    checkme = toBeFixed.substring(0,8);
    	//out("checkme:"+checkme);
    	if (checkme.compareTo(sb.toString())==0) {
    		out("incoming "+toBeFixed+" starts with file:///");
    		return toBeFixed;
    	}
    	String slasher = new String ("\\");
    	out("Slasher is "+slasher);
    	
    	char x[] = toBeFixed.toCharArray();
    	int len = x.length;
    	int idx=0;
    	while (idx < len) {
    		if (x[idx] == slasher.toCharArray()[0]) {
    			out("found a backslash at "+idx);
    			x[idx] = "/".toCharArray()[0];
    		}
    		idx += 1;
    	}
    	String slash = new String(x);
    	sb.append(slash);
    	result = sb.toString();
    	out("resulting new file string is \""+result+"\"");
    	return result;
    }
    
    
    
    // --------------------------main "readit" loop that parses the XML-----------------
    public void readit() {
    	
    out("Starting \"readit\"");
    int totTyme = 0;
    try {

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder               docBuilder = docBuilderFactory.newDocumentBuilder();
            out ("building doc object");
            Document                             doc = docBuilder.parse (new File(fn_));
            out ("doc object built: "+doc.toString());

            // normalize text representation  - ROOT NODE
            doc.getDocumentElement ().normalize ();
            out ("Root element of the doc is " + 
                 doc.getDocumentElement().getNodeName());
            
            String vsn = doc.getDocumentElement().getAttribute("version");
            out("Version is "+vsn);
            
            String xnsv = doc.getDocumentElement().getAttribute("xmlns");
            out("Name Space is "+xnsv);
            
            String xns = doc.getDocumentElement().getAttribute("xmlns:vlc");
            out("VLC Name Space is "+xns);
            
            /*NodeList rootList = doc.getDocumentElement().getChildNodes();
            for (int n=0;n<rootList.getLength();n++) {
        	    System.out.println("item("+n+" : " + ((Node)rootList.item(n)).getNodeValue().trim());
            }
            */
            
            //-------
            NodeList firstNameLista = doc.getElementsByTagName("title");
            Element firstNameElementa = (Element)firstNameLista.item(0);

            NodeList textFNLista = firstNameElementa.getChildNodes();
            out("title1 : " + 
                   ((Node)textFNLista.item(0)).getNodeValue().trim());
          //-------
            NodeList firstNameListb = doc.getElementsByTagName("location");
            Element firstNameElementb = (Element)firstNameListb.item(0);

            NodeList textFNListb = firstNameElementb.getChildNodes();
            String fyle = ((Node)textFNListb.item(0)).getNodeValue().trim();
            File f = new File(fyle);
            if (f.exists())  {
            	out("locat1 : " + fyle);	
            } else {
            	outun("WARNING: file "+fyle+" not found!!!!");
            }
            //----HERE MUST EXPECT A LIST------
            NodeList listOfTracks = doc.getElementsByTagName("trackList");
            int totalTrackLists = listOfTracks.getLength();
            out("Total no of tracks : " + totalTrackLists);
            Vector <HistoryItemLine> tracks = new Vector<HistoryItemLine>();

            for(int s=0; s<listOfTracks.getLength() ; s++){
            	HistoryItemLine pll = new HistoryItemLine();

                Node firstTrackNode = listOfTracks.item(s);
                if(firstTrackNode.getNodeType() == Node.ELEMENT_NODE){

                    Element firstTrackData = (Element)firstTrackNode;

                    //-------
                    NodeList firstNameList = firstTrackData.getElementsByTagName("title");
                    Element firstNameElement = (Element)firstNameList.item(0);

                    NodeList textFNList = firstNameElement.getChildNodes();
                    String title =((Node)textFNList.item(0)).getNodeValue().trim(); 
                    out("title : " + title);
                    pll.setTitle(title);
                    tracks.add(pll);

                    //-------
                    NodeList lastNameList = firstTrackData.getElementsByTagName("location");
                    Element lastNameElement = (Element)lastNameList.item(0);

                    NodeList textLNList = lastNameElement.getChildNodes();
                    String theFileToFix =  ((Node)textLNList.item(0)).getNodeValue().trim();
                    out("Loc : " + theFileToFix);
                    String newFileLoc = this.fixit(theFileToFix);                    
                   }//end of if clause


            }//end of for loop with s var


            //----HERE MUST EXPECT A LIST------
            NodeList extensions = doc.getElementsByTagName("extension");
            int totalExtensions = extensions.getLength();
            out("Total no of extnsn : " + totalExtensions);
          

            for(int s=0; s<extensions.getLength() ; s++){


                Node firstExtensionNode = extensions.item(s);
                if(firstExtensionNode.getNodeType() == Node.ELEMENT_NODE){

                    out("element Node found");
                    Element firstExtensionData = (Element)firstExtensionNode;
                    
                   //-------
                    NodeList firstNameList = firstExtensionData.getElementsByTagName("vlc:id");
                    Element firstNameElement = (Element)firstNameList.item(0);

                    NodeList textFNList = firstNameElement.getChildNodes();
                    out("id : " + 
                           ((Node)textFNList.item(0)).getNodeValue().trim());

                    //-------
                    
                    NodeList optionList = firstExtensionData.getElementsByTagName("vlc:option");
                    out("option list is length "+optionList.getLength());
                    Integer start = new Integer(0);
                    Integer stop = new Integer(0);
                    for (int t = 0; t < optionList.getLength(); t++) {
                    	Element optElement = (Element)optionList.item(t);

                    	NodeList textLNList = optElement.getChildNodes();
                    	String opt = ((Node)textLNList.item(0)).getNodeValue().trim();
                    	out("Opt: "+opt);                    
                    
                    	if ( opt.substring(0, 4).compareTo("star")==0) {
                    		int idx = opt.indexOf("=")+1;                    		
                    		start=new Integer(opt.substring(idx));
                    		out("Start time"+start);
                    	}
                    	if ( opt.substring(0, 4).compareTo("stop")==0) {
                        		
                        		int idx = opt.indexOf("=")+1;
                        		
                        		stop=new Integer(opt.substring(idx));
                        		int tyme = stop.intValue() - start.intValue();
                        		out("Stop time "+stop);
                        		totTyme = totTyme + tyme;
                    	}
                    }
                }//end of if clause


            }//end of for loop with s var

 
            
        }catch (SAXParseException err) {
        System.err.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.err.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }
        int min = totTyme/60;
        int sec = totTyme - (totTyme/60)*60;
        outun("------TOTAL TIME----------"+totTyme+ " which is "+min + " minutes and "+sec+" seconds");
        //System.exit (0);

    }//end of main

    public static void main (String argv []){
    	System.out.println("start XMLReader. argv[0] is "+argv[0]);
    	XMLReader xr  = new XMLReader(argv[0]);
    	if (argv.length > 1) {
    		if (argv[1].compareTo("-debug")==0) {
    			xr.setDebug(true);
    		}
    	}
    	System.out.println("constructed XMLReader object");
    	xr.readit();
    }
}

