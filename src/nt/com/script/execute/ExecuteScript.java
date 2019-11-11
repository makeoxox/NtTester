package nt.com.script.execute;


public interface ExecuteScript {
	
	public String afterReceive(String message,String recv);
	
	public String beforeSend(String message);
	
}
