package gestionFichiers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ListePyFiles {
	
   // takes a directory path and returns a list of files .py inside it 
	private ArrayList<File> fileList = new ArrayList<>();
	private int nbPyFiles=0;
	
	public ListePyFiles(String directoryPath) {
		this.ListPyfiles(directoryPath);
	}
	 
	
	public void ListPyfiles(String directoryPath){
	    File directory= new File(directoryPath);
	    File[] ls=directory.listFiles();
	    for(File file : ls) {
	    	if(file.isFile()&&file.getName().endsWith(".py")) {
	    		 this.fileList.add(file);
	    		 this.nbPyFiles++;
	    	}
	    }
	    for(File file : ls) {
	    	if(file.isDirectory()) {
	    		this.ListPyfiles(file.getPath());
	    	}
	    }
	    
	}
	
	public void afficheListEtAnalyse() throws IOException {
		
		for(Iterator<File> it = this.getFileList().iterator(); it.hasNext();) {
			File filePath=it.next();
			System.out.println(filePath.getName()+" :");
			FileAnalyser pc=new FileAnalyser();
			if(pc.twoFirstCom(filePath.getAbsolutePath())==0) {
				System.out.println("il ne contient pas les premiere commentaires");
			}else {
				System.out.println("il contient les premiere commentaires");
			}
			System.out.println("le nombre de fonctions sans annotations de type est :"+ pc.annotation(filePath.getAbsolutePath()));
			System.out.println("le nombre de fonctions sans les commentaire de pydoc est :"+ pc.pydocCom(filePath.getAbsolutePath()));
		}
	}
	
	public ArrayList<File> getFileList(){
		return this.fileList;
	}
	
	public int getNbPyFiles() {
		return this.nbPyFiles;
	}
}
