package gestionFichiers;

import java.io.IOException;

import org.apache.commons.cli.ParseException;

public class MyMain {

	public static void main(String[] args) throws IOException, IllegalArgumentException, MissingOptionExecption{
		
		ArgumentsAndOtions op= new ArgumentsAndOtions();
		try {
			op.optionHandeler(args);
			if(op.hasOption("f")) {
				FileAnalyser pc= new FileAnalyser();
				if(op.hasOption("stat")) {
					throw new IllegalArgumentException("l'option que vous avez spécifiée n'est pas applicable aux fichiers. Pour obtenir de l'aide, veuillez utiliser l'option -h");
				}
				if(op.hasOption("head")) {
					if(pc.twoFirstCom(op.getFile())==0) {
						System.out.println("Il ne contient pas les premiere commentaires");
					}else {
						System.out.println("Il contient les premiere commentaires");
					}
				}
				if(op.hasOption("type")) {
					System.out.println("Le nombre de fonctions sans annotations de type est :"+ pc.annotation(op.getFile()));
				}
				if(op.hasOption("pydoc")) {
					System.out.println("Le nombre de fonctions sans les commentaire de pydoc est :"+ pc.pydocCom(op.getFile()));
				}
				if(op.hasOption("sbutf8")) {
					
					pc.ajouterDeuxCom(op.getFile());
				}
				if(op.hasOption("comment")) {
					pc.ajouterSquelette(op.getFile());
				}
				if(!op.hasOption("head")&&!op.hasOption("pydoc")&&!op.hasOption("type")&&!op.hasOption("sbutf8")&&!op.hasOption("comment")) {
					throw new MissingOptionExecption();
				}
				
			}
			
			if(op.hasOption("d")) {
				ListePyFiles ls= new ListePyFiles(op.getDirectory());
				Stats stats= new Stats(op.getDirectory());
				if(op.hasOption("head")||op.hasOption("pydoc")||op.hasOption("type")||op.hasOption("sbutf8")||op.hasOption("comment")) {
					throw new IllegalArgumentException("l'option que vous avez spécifiée n'est pas applicable aux répertoires. Pour obtenir de l'aide, veuillez utiliser l'option -h");
				}
				if(op.hasOption("stat")) {
					System.out.println("Statistique de repertoire :"+ op.getDirectory());
					System.out.println("le nombre de fichier analyses :"+ls.getNbPyFiles());
					System.out.println("Le nombre de fonction :"+stats.numberOfFonc());
					System.out.println("le pourcentage des fonctions ayant les annotation de type : "+ stats.pourcentageAnno()+"%");
					System.out.println("le pourcentage des fonctions ayant les commentaires de pydoc : "+ stats.pourcentagePydoc()+"%");
					System.out.println("le pourcentage des fichier ayant les deux premiere commentaire : "+ stats.pourcentageDeuxCom()+"%");
					
				}else {
					ls.afficheListEtAnalyse();
				}
				
				
			}
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		} catch (MissingOptionExecption e) {
		    System.out.println(e.getMessage());	   
		}catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
