package gestionFichiers;


import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Stats {
	
	private ListePyFiles ls;
		public Stats(String directoryPath) {
			this.ls=new ListePyFiles(directoryPath);
		} 
		
		// calculer le pourcentage des fonction avec les commentaires de pydoc 
		public double pourcentagePydoc() throws IOException{
			double nbOfFonc= this.numberOfFonc();
			ArrayList<File> files=this.ls.getFileList();
			double nbFnWithPydoc=0;
			for(Iterator<File> it=files.iterator(); it.hasNext();) {
				String file = it.next().getAbsolutePath();
				FileAnalyser pc=new FileAnalyser();
				
				nbFnWithPydoc=nbFnWithPydoc+(pc.numberOfFonc(file)-pc.pydocCom(file));
			}
			
			if(nbOfFonc==0) {
				return 0;
			}
			
			return (nbFnWithPydoc/nbOfFonc)*100;
		}
		
		
		// calculer le pourcentage de fonction avec les annotation de types 
		
		public double pourcentageAnno() throws IOException{
			
			double nbOfFonc= this.numberOfFonc();
			ArrayList<File> files=this.ls.getFileList();
			double nbFnWithAnno=0;
			
		for(Iterator<File> it=files.iterator(); it.hasNext();) {
			
			String file = it.next().getAbsolutePath();
			FileAnalyser pc=new FileAnalyser();
			//the total number of functions in the file - the number of fonctions without annotation : to find the fn with annotation 
			nbFnWithAnno=nbFnWithAnno+(pc.numberOfFonc(file)-pc.annotation(file));
 
		}
		if(nbOfFonc==0) {
			return 0;
		}
		
		return (nbFnWithAnno/nbOfFonc)*100;
			
		}
		
		//calculer le porcentage des fichers avec les deux premières lignes de commentaire
		public double pourcentageDeuxCom() throws IOException {
			
			double nbOfFonc= this.numberOfFonc();
			ArrayList<File> files=this.ls.getFileList();
			double nbFilesWith2Com=0;
			for(Iterator<File> it=files.iterator(); it.hasNext();) {
				
				String file = it.next().getAbsolutePath();
				FileAnalyser pc=new FileAnalyser();
				nbFilesWith2Com=nbFilesWith2Com+pc.twoFirstCom(file);
			}
			if(nbOfFonc==0) {
				return 0;
			}
			
			return (nbFilesWith2Com/this.ls.getNbPyFiles())*100;
			
				
			
		}
		
		public int numberOfFonc() throws IOException {
			ArrayList<File> files=ls.getFileList();
			int nbOfFonc=0;
			for(Iterator<File> it=files.iterator(); it.hasNext();) {
				FileAnalyser pc=new FileAnalyser();
				nbOfFonc =nbOfFonc+ pc.numberOfFonc(it.next().getAbsolutePath());
				
			}
			return nbOfFonc;
		}
}
