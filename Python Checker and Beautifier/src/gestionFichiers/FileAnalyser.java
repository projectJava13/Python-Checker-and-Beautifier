package gestionFichiers;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class FileAnalyser {
	
	// opens up a files and uses buffered reader then checks for the two first comments
	
	public int twoFirstCom(String fileName) throws IOException {
	
			BufferedReader br= new BufferedReader(new FileReader(fileName));
			String line1 = "#!/usr/bin/python";
			String line2 = "# -*- coding: utf-8 -*-";
			
			String line=br.readLine();
			while(line!=null&&line.isEmpty()){
				line=br.readLine();
			}
			
			if(line!=null&&line.contains(line1)){
				line=br.readLine();
				if(line!=null&&line.contains(line2)) {
					br.close();
					return 1;
				}
			}
			br.close();
			return 0;
	}
	
	
//checks for annotations in a file 
public int annotation(String fileName) throws IOException {
		BufferedReader br= new BufferedReader(new FileReader(fileName));
		
		String def="def ";
		String arow="->";
		String line=br.readLine();
		int nbFnWithoutAnno=0;
	
		while(line!=null) {
			
			if(line.contains(def)&&!line.contains("#")&&!line.contains(arow)) {
					nbFnWithoutAnno++;
			
			}
			line=br.readLine();
		}
			
			br.close();
			return nbFnWithoutAnno;
	}
	
	// checks for pydoc comments 
	public int pydocCom(String fileName) throws IOException {
		BufferedReader br= new BufferedReader(new FileReader(fileName));
		String def="def ";
		String com= "\"\"\"";
		String line= br.readLine();
		int nbFnWithoutPydoc=0;
		while(line!=null) {
			if(line.contains(def)&&!line.contains("#")) {
				line=br.readLine();
				while(line.isEmpty()) {
					line=br.readLine();	
				}
				if(!line.contains(com)) {
					nbFnWithoutPydoc++;
			     }
			}else {
				line=br.readLine();
			}
			
		}
		br.close();
		return nbFnWithoutPydoc;
	}

	
	// Ajouter une ligne dans un fichier text en une position sans ecraser le contenue de fichier 
	public void ajouterUneLigne(String fileName, String newText,long positionToInsert) throws IOException {
		 
      
       BufferedReader br= new BufferedReader(new FileReader(fileName)); 
       File tempFile = File.createTempFile("temp1247933326157648", ".txt");
       tempFile.deleteOnExit();
       String tmpFileName = tempFile.getName();
       BufferedWriter bw= new BufferedWriter(new FileWriter(tmpFileName));
       String line= br.readLine();
       int pos=1;
      while(line!=null) {
    	  if(pos==positionToInsert) {
    		  bw.append(newText);
    		  bw.newLine();
    	  }else {
    		  bw.append(line);
    		  bw.newLine();
    		  line=br.readLine();
    	  }
    	  pos++;
      }
      br.close();
      bw.close();
      
      BufferedReader br2= new BufferedReader(new FileReader(tmpFileName));
      BufferedWriter bw2= new BufferedWriter(new FileWriter(fileName));
      String line2=br2.readLine();
      while(line2!=null) {
    	  bw2.append(line2);
    		  bw2.newLine();
    		  line2=br2.readLine();
     }
    	 
    br2.close();
    bw2.close();
    
      
       
	}

	// ajouter les deux premiere com
		// on suppose que le version de python est 3
	public void ajouterDeuxCom(String fileName) throws IOException, IllegalArgumentException {
		if(this.twoFirstCom(fileName)==1) {
			throw new IllegalArgumentException("Le fichier contient deja sbutf8 et l'encodage");
		}
		String line1 = "#!/usr/bin/python3";
		String line2 = "# -*- coding: utf-8 -*-";
		this.ajouterUneLigne(fileName, line1, 1);
		this.ajouterUneLigne(fileName, line2, 2);
	}
	
	// ajouter le squelette 
	public void ajouterSquelette(String fileName) throws IOException, IllegalArgumentException {
		if(this.pydocCom(fileName)==0) {
			throw new IllegalArgumentException("Le fichier contient deja les commentaires pydoc dans toutes les fonction");
		}else {
		  Map<Integer, String> posList= this.PosSansPydoc(fileName);
		  Scanner scanner= new Scanner(System.in);
		  int i=0;
		  for(Entry<Integer, String> entry : posList.entrySet()) {
			  
			  System.out.println("Ecrire Pydoc commentaire pour la fonction : >> " + entry.getValue()+" \nEcrire 'fin' dans une nouvelle ligne pour finir");  
			  int nbline= entry.getKey()+i;
			  this.ajouterUneLigne(fileName, "\t\"\"\"", nbline); 
			  i++;
			  nbline++;
			  String text =scanner.nextLine();
			 
			 while(!text.equals("fin")) {
				 this.ajouterUneLigne(fileName, "\t"+text, nbline); 
				 text=scanner.nextLine();
				 i++;
				 nbline++;
			 } 
			 this.ajouterUneLigne(fileName, "\t\"\"\"", nbline); 
			  i++;
			 
		  }
		  scanner.close();
		}
	}
//renvoyer les num des lignes sans commentaires pydoc 
	public Map<Integer, String> PosSansPydoc(String fileName) throws IOException{
		
		Map<Integer, String> list = new HashMap<Integer, String>();
		BufferedReader br= new BufferedReader(new FileReader(fileName));
		String def="def ";
		String com= "\"\"\"";
		String line= br.readLine();
		int nbline=1;
		while(line!=null) {
			if(line.contains(def)&&!line.contains("#")) {
				String fonctionLine=line;
				line=br.readLine();
				nbline++;
				while(line!=null&&line.isEmpty()) {
					line=br.readLine();
					nbline++;
				}
				if(!line.contains(com)) {
					list.put(nbline, fonctionLine);
			     }
			}else {
				line=br.readLine();
				nbline++;
			}
			
		}
		br.close();
		return list;
	}
	
// return the number of functions in a file
	public int numberOfFonc(String fileName) throws IOException {
		
		BufferedReader br= new BufferedReader(new FileReader(fileName));
		
		String def="def ";
		int nbFonc=0;
		String line=br.readLine();
		while(line!=null) {
			if(line.contains(def)&&!line.contains("#")){
				nbFonc++;
			}
			line=br.readLine();
		}
		br.close();
		return nbFonc;
	}
	
	
}
