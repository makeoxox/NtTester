package nt.com.script.execute;


public interface ExecuteScript {
	
	public String afterReceive(String message);
	
	public String beforeSend(String message);
	
}
