package nt.com.script.java;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *java�����ַ���������
 *
 * @author kege
 */

public class JavaCompile {
    //��ȫ��
    private String fullClassName;
    private String sourceCode;
    private Map<String, ByteJavaFileObject> javaFileObjectMap = new ConcurrentHashMap<>();
    private JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    //��ű���������������Ϣ
    private DiagnosticCollector<JavaFileObject> diagnosticsCollector = new DiagnosticCollector<>();
    private String runResult;

    public JavaCompile(String sourceCode) {
        this.sourceCode = sourceCode;
        this.fullClassName = getFullClassName(sourceCode);
    }
    
    //���ȫ������
    public static String getFullClassName(String sourceCode) {
        String className = "";
        Pattern pattern = Pattern.compile("package\\s+\\S+\\s*;");
        Matcher matcher = pattern.matcher(sourceCode);
        if (matcher.find()) {
            className = matcher.group().replaceFirst("package", "").replace(";", "").trim() + ".";
        }

        pattern = Pattern.compile("class\\s+\\S+\\s+\\{");
        matcher = pattern.matcher(sourceCode);
        if (matcher.find()) {
            className += matcher.group().replaceFirst("class", "").replace("{", "").trim();
        }
        return className;
    }

    //����
    public boolean compiler() {
        //��׼�����ݹ�����,�������Լ���ʵ�֣����ǲ��ַ���
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(diagnosticsCollector, null, null);
        JavaFileManager javaFileManager = new StringJavaFileManage(standardFileManager,javaFileObjectMap);
        JavaFileObject javaFileObject = new StringJavaFileObject(fullClassName, sourceCode);
        //��ȡһ����������
        CompilationTask task = compiler.getTask(null, javaFileManager, diagnosticsCollector, null, null, Arrays.asList(javaFileObject));
        return task.call();
    }
    
    //����
    public void runMainMethod(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, UnsupportedEncodingException {
        PrintStream out = System.out;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            System.setOut(printStream);
            StringClassLoader loader = new StringClassLoader(javaFileObjectMap);
            Class<?> aClass = loader.findClass(fullClassName);
            Method main = aClass.getMethod("main", String[].class);
            Object[] pars = new Object[]{args};
            main.invoke(null, pars); 
            runResult = new String(outputStream.toByteArray(), "gbk");
        } finally {
            //��ԭĬ�ϴ�ӡ�Ķ���
            System.setOut(out);
        }

    }

   //������Ϣ
    public String getCompilerMessage() {
        StringBuilder sb = new StringBuilder();
        List<Diagnostic<? extends JavaFileObject>> diagnostics = diagnosticsCollector.getDiagnostics();
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
            sb.append(diagnostic.toString()).append("\r\n");
        }
        return sb.toString();
    }

    //����ʱ��Ϣ
    public String getRunResult() {
        return runResult;
    }

  
}