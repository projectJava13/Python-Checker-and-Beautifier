package gestionFichiers;
import org.apache.commons.cli.*;
import java.nio.file.Files;
import java.nio.file.Paths;
public class ArgumentsAndOtions{
	
	private CommandLine cmd;
	
	
	public void optionHandeler(String[] args) throws ParseException, MissingOptionExecption, IllegalArgumentException {
			CommandLineParser parser= new DefaultParser();
			Options options = new Options();
			
			// adding options 
			options.addOption("h" ,"help", false, "this shows you the options you have");
			options.addOption("f" ,"file", true, "for files");
			options.addOption("d" ,"directory", true, "for directories");
			options.addOption(Option.builder()
                .longOpt("type")
                .desc("check the type annotations")
                .build());
			
			options.addOption(Option.builder()
	                .longOpt("head")
	                .desc("check the two first comments")
	                .build());
			
			
			
			options.addOption(Option.builder()
	                .longOpt("pydoc")
	                .desc("check the pydoc comments in fonctions")
	                .build());
			
			options.addOption(Option.builder()
	                .longOpt("sbutf8")
	                .desc("add the two first comments")
	                .build());
			
			options.addOption(Option.builder()
	                .longOpt("comment")
	                .desc("add pydoc commets")
	                .build());
			
			options.addOption(Option.builder()
	                .longOpt("stat")
	                .desc("show statistiques")
	                .build());
			 
			//parsing the options to the args
			this.cmd=parser.parse(options, args);
			
			// print help
			if(cmd.hasOption("help")) {
				HelpFormatter formater= new HelpFormatter();
				
				formater.printHelp("Commande line options", options);
			}
			
			boolean f= cmd.hasOption("f");
			boolean d=cmd.hasOption("d");
			boolean h=cmd.hasOption("help");
			
			
			
			if(!d&&!f&&!h){
				 throw new MissingOptionExecption();
			}
			// user has to type exactly one of the two options f or d 
			if(f&&d) {
				throw new IllegalArgumentException("Veuillez utiliser l'une des options suivantes dans la ligne de commande, mais pas les deux en même temps :\r\n"
						+ "  -d : Pour les repertoires \r\n"
						+ "  -f : Pour les fichiers \r\n"
						+"tapez « -h » ou « --help » pour obtenir de l’aide");
			}
			// if he gives a directory or not .py file with -f option or it doesn't exist
			
			if(f){
				String path = this.cmd.getOptionValue("f");
				boolean isPyFile = Files.isRegularFile(Paths.get(path)) && path.endsWith(".py");
				if(!isPyFile) {
					throw new IllegalArgumentException( path +" is not a .py file or it doesn't exist");
				}
			}
			// if he gives a file or a directory that doesn't exist 
			if(d) {
				String path = this.cmd.getOptionValue("d");
				boolean isDir = Files.isDirectory(Paths.get(path));
				if(!isDir) {
					throw new IllegalArgumentException(path+" is not a directory or it doesn't exist");
				}
			
			}	
	}
	
	// get file or directory
	public String getFile(){
		return this.cmd.getOptionValue("f");
	}
	public String getDirectory(){
		return this.cmd.getOptionValue("d");
	}
	
	// check if an option is passed
	
	public boolean hasOption(String option) {
		return this.cmd.hasOption(option);
	}
	
	
	
}
