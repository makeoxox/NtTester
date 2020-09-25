package nt.com.script.java;

import nt.com.view.init.ConsoleTextArea;

/**
 * 
 * ÷¥––java¥˙¬Î
 * 
 * @author kege
 *
 */
public class JavaRun {
	
	  public  void run(String []args,String code) {
	    	
	    	JavaCompile compiler = new JavaCompile(code);
	        boolean res = compiler.compiler();
	        if (res) {
	            try {
	                compiler.runMainMethod(args);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            ConsoleTextArea.AppendMessageOnCurrentConsole(compiler.getRunResult());
	        } else {
	        	ConsoleTextArea.AppendMessageOnCurrentConsole("±‡“Î ß∞‹£∫"+compiler.getCompilerMessage());
	        }

	    }
	  
}
