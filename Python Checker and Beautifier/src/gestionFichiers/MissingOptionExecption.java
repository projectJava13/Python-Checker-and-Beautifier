package gestionFichiers;


@SuppressWarnings("serial")
public class MissingOptionExecption extends Exception{

	public MissingOptionExecption() {
		super("Il manque des paramètres, tapez « -h » ou « --help » pour obtenir de l’aide"); 
				 
	}

}
