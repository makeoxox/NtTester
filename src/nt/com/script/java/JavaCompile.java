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
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by andy on 2018-12-06 21:25
 */
public class JavaCompile {
    //��ȫ��
    private String fullClassName;
    private String sourceCode;
    //��ű���֮����ֽ���(key:��ȫ��,value:����֮��������ֽ���)
    private Map<String, ByteJavaFileObject> javaFileObjectMap = new ConcurrentHashMap<>();
    //��ȡjava�ı�����
    private JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    //��ű���������������Ϣ
    private DiagnosticCollector<JavaFileObject> diagnosticsCollector = new DiagnosticCollector<>();
    //ִ�н��������̨��������ݣ�
    private String runResult;
    //�����ʱ(��λms)
    private long compilerTakeTime;
    //���к�ʱ(��λms)
    private long runTakeTime;


    public JavaCompile(String sourceCode) {
        this.sourceCode = sourceCode;
        this.fullClassName = getFullClassName(sourceCode);
    }

    /**
     * �����ַ���Դ����,����ʧ���� diagnosticsCollector �л�ȡ��ʾ��Ϣ
     *
     * @return true:����ɹ� false:����ʧ��
     */
    public boolean compiler() {
        long startTime = System.currentTimeMillis();
        //��׼�����ݹ�����,�������Լ���ʵ�֣����ǲ��ַ���
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(diagnosticsCollector, null, null);
        JavaFileManager javaFileManager = new StringJavaFileManage(standardFileManager);
        //����Դ�������
        JavaFileObject javaFileObject = new StringJavaFileObject(fullClassName, sourceCode);
        //��ȡһ����������
        CompilationTask task = compiler.getTask(null, javaFileManager, diagnosticsCollector, null, null, Arrays.asList(javaFileObject));
        //���ñ����ʱ
        compilerTakeTime = System.currentTimeMillis() - startTime;
        return task.call();
    }

    /**
     * ִ��main�������ض���System.out.print
     */
    public void runMainMethod(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, UnsupportedEncodingException {
        PrintStream out = System.out;
        try {
            long startTime = System.currentTimeMillis();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            System.setOut(printStream);

            StringClassLoader scl = new StringClassLoader();
            Class<?> aClass = scl.findClass(fullClassName);
            Method main = aClass.getMethod("main", String[].class);
            Object[] pars = new Object[]{1};
            pars[0] = args;
            main.invoke(null, pars); //����main����
            //�������к�ʱ
            runTakeTime = System.currentTimeMillis() - startTime;
            //���ô�ӡ���������
            runResult = new String(outputStream.toByteArray(), "gbk");
        } finally {
            //��ԭĬ�ϴ�ӡ�Ķ���
            System.setOut(out);
        }

    }

    /**
     * @return ������Ϣ(���� ����)
     */
    public String getCompilerMessage() {
        StringBuilder sb = new StringBuilder();
        List<Diagnostic<? extends JavaFileObject>> diagnostics = diagnosticsCollector.getDiagnostics();
        for (Diagnostic diagnostic : diagnostics) {
            sb.append(diagnostic.toString()).append("\r\n");
        }
        return sb.toString();
    }

    /**
     * @return ����̨��ӡ����Ϣ
     */
    public String getRunResult() {
        return runResult;
    }


    public long getCompilerTakeTime() {
        return compilerTakeTime;
    }

    public long getRunTakeTime() {
        return runTakeTime;
    }

    /**
     * ��ȡ���ȫ����
     *
     * @param sourceCode Դ��
     * @return ���ȫ����
     */
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

    /**
     * �Զ���һ���ַ�����Դ�����
     */
    private class StringJavaFileObject extends SimpleJavaFileObject {
        //�ȴ������Դ���ֶ�
        private String contents;

        //javaԴ���� => StringJavaFileObject���� ��ʱ��ʹ��
        public StringJavaFileObject(String className, String contents) {
            super(URI.create("string:///" + className.replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
            this.contents = contents;
        }

        //�ַ���Դ�����ø÷���
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return contents;
        }

    }

    /**
     * �Զ���һ������֮����ֽ������
     */
    private class ByteJavaFileObject extends SimpleJavaFileObject {
        //��ű������ֽ���
        private ByteArrayOutputStream outPutStream;

        public ByteJavaFileObject(String className, Kind kind) {
            super(URI.create("string:///" + className.replaceAll("\\.", "/") + Kind.SOURCE.extension), kind);
        }

        //StringJavaFileManage ����֮����ֽ����������ø÷��������ֽ��������outputStream��
        @Override
        public OutputStream openOutputStream() {
            outPutStream = new ByteArrayOutputStream();
            return outPutStream;
        }

        //������������ص�ʱ����Ҫ�õ�
        public byte[] getCompiledBytes() {
            return outPutStream.toByteArray();
        }
    }

    /**
     * �Զ���һ��JavaFileManage�����Ʊ���֮���ֽ�������λ��
     */
    private class StringJavaFileManage extends ForwardingJavaFileManager {
        StringJavaFileManage(JavaFileManager fileManager) {
            super(fileManager);
        }

        //��ȡ������ļ���������ʾ����λ�ô�ָ�����͵�ָ���ࡣ
        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
            ByteJavaFileObject javaFileObject = new ByteJavaFileObject(className, kind);
            javaFileObjectMap.put(className, javaFileObject);
            return javaFileObject;
        }
    }

    /**
     * �Զ����������, �������ض�̬���ֽ���
     */
    private class StringClassLoader extends ClassLoader {
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            ByteJavaFileObject fileObject = javaFileObjectMap.get(name);
            if (fileObject != null) {
                byte[] bytes = fileObject.getCompiledBytes();
                return defineClass(name, bytes, 0, bytes.length);
            }
            try {
                return ClassLoader.getSystemClassLoader().loadClass(name);
            } catch (Exception e) {
                return super.findClass(name);
            }
        }
    }
    
  
}