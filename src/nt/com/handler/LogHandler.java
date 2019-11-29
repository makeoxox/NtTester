package nt.com.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * ��־������
 * 
 * @author Administrator 
 * 
 *
 */
public class LogHandler implements Handler{
	
	protected static final Log log = LogFactory.getLog(LogHandler.class);

	@Override
	public void before(HandlerContext context) {
		String message =(String) context.getObject();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr =sdf.format(date);
		log.info("[LogHandler - before ]"+"["+dateStr+" ���ͱ���]"+message);
	}

	@Override
	public void after(HandlerContext context) {
		// TODO Auto-generated method stub
		String message =(String) context.getObject();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr =sdf.format(date);
		log.info("[LogHandler - after ]"+"["+dateStr+" �յ�����]"+message);
	}

	

}
