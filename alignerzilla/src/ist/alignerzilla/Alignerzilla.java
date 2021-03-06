package ist.alignerzilla;

import ist.alignerzilla.domain.Aligner;
import ist.alignerzilla.domain.SubtitlePair;
import ist.alignerzilla.io.ConfigLoader;
import ist.alignerzilla.io.SubtitleReader;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Main class for Alignerzilla.<br>
 * Running this class will align all the subtitles present in <b>list.txt</b> file.<br>
 * Will use the configurations specified in <b>alignerzilla.config</b>.<br>
 * Will store them in <b>results/</b> directory.
 * */
public class Alignerzilla {
	public static void main(String[] args){
		ConfigLoader config = new ConfigLoader();
		SubtitleReader reader = new SubtitleReader(config.getSpecialPunctiationCases());
		
		while(reader.hasMorePairs()){
			SubtitlePair pair = reader.readPair();
			System.out.print("[ STARTED: "+ pair.getName() + " ... ");
			if(pair.isValid()){
				Aligner aligner = new Aligner(pair, config);
				aligner.align();	
				
				PrintWriter writer;
				try {
					writer = new PrintWriter("results/"+pair.getName()+".txt", "UTF-8");
					writer.println(aligner.toString());
					writer.close();
					System.out.println("DONE ]");
				} catch (FileNotFoundException | UnsupportedEncodingException e) {
					System.err.println("ERROR: Couldn't save output of "+pair.getName());
				}
			}
		}
	}
}
