package nt.com.view.init;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import nt.com.enums.TextType;
import nt.com.global.Config;

/**
 * 富文本编辑域
 * 
 * @author kege
 */
public class RichEditTextArea extends CodeArea {
	
	private TextType type;
	
	public RichEditTextArea() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nt/com/view/fxml/RichEditTextArea.fxml"));
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		String fontFamily = "-fx-font-family:"+Config.getEditFont().getFamily()+";";
		String fontSize = "-fx-font-size:"+Config.getEditFont().getSize()+";";
		this.setStyle(fontFamily+fontSize);

	}
	
	//js关键字
    private static final String[] KEYWORDS = new String[] {
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while","var"
            ,"function","prototype","typeof","null","true","false"
    };
    
    //匹配js/json
    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
            + "|(?<PAREN>" + PAREN_PATTERN + ")"
            + "|(?<BRACE>" + BRACE_PATTERN + ")"
            + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
            + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
            + "|(?<STRING>" + STRING_PATTERN + ")"
            + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );
    
    //匹配xml
    private static final Pattern XML_TAG = Pattern.compile("(?<ELEMENT>(</?\\h*)(\\w+)([^<>]*)(\\h*/?>))"+"|(?<COMMENT><!--[^<>]+-->)");
    private static final Pattern ATTRIBUTES = Pattern.compile("(\\w+\\h*)(=)(\\h*\"[^\"]+\")");
    
    private static final int GROUP_OPEN_BRACKET = 2;
    private static final int GROUP_ELEMENT_NAME = 3;
    private static final int GROUP_ATTRIBUTES_SECTION = 4;
    private static final int GROUP_CLOSE_BRACKET = 5;
    private static final int GROUP_ATTRIBUTE_NAME = 1;
    private static final int GROUP_EQUAL_SYMBOL = 2;
    private static final int GROUP_ATTRIBUTE_VALUE = 3;
    
	public void setText(String text,TextType type) {
		this.setParagraphGraphicFactory(LineNumberFactory.get(this));
        final Pattern whiteSpace = Pattern.compile( "^\\s+" );
        this.type=type;
        switch(type) {
	        case JAVASCRIPT:
	        	  this.multiPlainChanges().successionEnds(Duration.ofMillis(500)).subscribe(ignore -> this.setStyleSpans(0, computeJSHighlighting(this.getText())));
	              this.addEventHandler( KeyEvent.KEY_PRESSED, KE ->
	              {
	                  if ( KE.getCode() == KeyCode.ENTER ) {
	                  	int caretPosition = this.getCaretPosition();
	                  	int currentParagraph = this.getCurrentParagraph();
	                      Matcher m0 = whiteSpace.matcher( this.getParagraph( currentParagraph-1 ).getSegments().get( 0 ) );
	                      if ( m0.find() ) Platform.runLater( () -> this.insertText( caretPosition, m0.group() ) );
	                  }
	              });
	              this.replaceText(0, 0, text);
	      		this.getStylesheets().add(this.getClass().getResource("/nt/com/view/css/RichTextEdit-js.css").toExternalForm());
	      		break;
	        case XML:
	        	   this.textProperty().addListener((obs, oldText, newText) -> {
	                   this.setStyleSpans(0, computeXMLHighlighting(newText));
	               });
	               this.replaceText(0, 0, text);
	               this.getStylesheets().add(this.getClass().getResource("/nt/com/view/css/RichTextEdit-xml.css").toExternalForm());
	        	break;
	        case HTML:
	        	   this.textProperty().addListener((obs, oldText, newText) -> {
	                   this.setStyleSpans(0, computeXMLHighlighting(newText));
	               });
	               this.replaceText(0, 0, text);
	               this.getStylesheets().add(this.getClass().getResource("/nt/com/view/css/RichTextEdit-xml.css").toExternalForm());
	        	break;	
	        case JSON:
	        	  this.multiPlainChanges().successionEnds(Duration.ofMillis(500)).subscribe(ignore -> this.setStyleSpans(0, computeJsonHighlighting(this.getText())));
	              this.addEventHandler( KeyEvent.KEY_PRESSED, KE ->
	              {
	                  if ( KE.getCode() == KeyCode.ENTER ) {
	                  	int caretPosition = this.getCaretPosition();
	                  	int currentParagraph = this.getCurrentParagraph();
	                      Matcher m0 = whiteSpace.matcher( this.getParagraph( currentParagraph-1 ).getSegments().get( 0 ) );
	                      if ( m0.find() ) Platform.runLater( () -> this.insertText( caretPosition, m0.group() ) );
	                  }
	              });
	              this.replaceText(0, 0, text);
	      		this.getStylesheets().add(this.getClass().getResource("/nt/com/view/css/RichTextEdit-json.css").toExternalForm());
	      		break;
	      		
	        default:
				 this.replaceText(0, 0, text);
				break;
        }
	}
	
	public TextType getTextType() {
		return this.type;
	}
	
	
	//计算js高亮
	 private static StyleSpans<Collection<String>> computeJSHighlighting(String text) {
	        Matcher matcher = PATTERN.matcher(text);
	        int lastKwEnd = 0;
	        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
	        while(matcher.find()) {
	            String styleClass =
	                    matcher.group("KEYWORD") != null ? "keyword" :
	                    matcher.group("PAREN") != null ? "paren" :
	                    matcher.group("BRACE") != null ? "brace" :
	                    matcher.group("BRACKET") != null ? "bracket" :
	                    matcher.group("SEMICOLON") != null ? "semicolon" :
	                    matcher.group("STRING") != null ? "string" :
	                    matcher.group("COMMENT") != null ? "comment" :
	                    null; 
	            assert styleClass != null;
	            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
	            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
	            lastKwEnd = matcher.end();
	        }
	        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
	        return spansBuilder.create();
	    }
	 
	 
	 //计算JSON高亮
	 private static StyleSpans<Collection<String>> computeJsonHighlighting(String text) {
	        Matcher matcher = PATTERN.matcher(text);
	        int lastKwEnd = 0;
	        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
	        while(matcher.find()) {
	            String styleClass =
	                    matcher.group("PAREN") != null ? "paren" :
	                    matcher.group("BRACE") != null ? "brace" :
	                    matcher.group("BRACKET") != null ? "bracket" :
	                    matcher.group("SEMICOLON") != null ? "semicolon" :
	                    matcher.group("STRING") != null ? "string" :
	                    matcher.group("COMMENT") != null ? "comment" :
	                    null; 
	            assert styleClass != null;
	            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
	            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
	            lastKwEnd = matcher.end();
	        }
	        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
	        return spansBuilder.create();
	    }
	 
	 //计算XML高亮
	 private static StyleSpans<Collection<String>> computeXMLHighlighting(String text) {
	    	
	        Matcher matcher = XML_TAG.matcher(text);
	        int lastKwEnd = 0;
	        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
	        while(matcher.find()) {
	        	
	        	spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
	        	if(matcher.group("COMMENT") != null) {
	        		spansBuilder.add(Collections.singleton("comment"), matcher.end() - matcher.start());
	        	}
	        	else {
	        		if(matcher.group("ELEMENT") != null) {
	        			String attributesText = matcher.group(GROUP_ATTRIBUTES_SECTION);
	        			
	        			spansBuilder.add(Collections.singleton("tagmark"), matcher.end(GROUP_OPEN_BRACKET) - matcher.start(GROUP_OPEN_BRACKET));
	        			spansBuilder.add(Collections.singleton("anytag"), matcher.end(GROUP_ELEMENT_NAME) - matcher.end(GROUP_OPEN_BRACKET));

	        			if(!attributesText.isEmpty()) {
	        				
	        				lastKwEnd = 0;
	        				
	        				Matcher amatcher = ATTRIBUTES.matcher(attributesText);
	        				while(amatcher.find()) {
	        					spansBuilder.add(Collections.emptyList(), amatcher.start() - lastKwEnd);
	        					spansBuilder.add(Collections.singleton("attribute"), amatcher.end(GROUP_ATTRIBUTE_NAME) - amatcher.start(GROUP_ATTRIBUTE_NAME));
	        					spansBuilder.add(Collections.singleton("tagmark"), amatcher.end(GROUP_EQUAL_SYMBOL) - amatcher.end(GROUP_ATTRIBUTE_NAME));
	        					spansBuilder.add(Collections.singleton("avalue"), amatcher.end(GROUP_ATTRIBUTE_VALUE) - amatcher.end(GROUP_EQUAL_SYMBOL));
	        					lastKwEnd = amatcher.end();
	        				}
	        				if(attributesText.length() > lastKwEnd)
	        					spansBuilder.add(Collections.emptyList(), attributesText.length() - lastKwEnd);
	        			}

	        			lastKwEnd = matcher.end(GROUP_ATTRIBUTES_SECTION);
	        			
	        			spansBuilder.add(Collections.singleton("tagmark"), matcher.end(GROUP_CLOSE_BRACKET) - lastKwEnd);
	        		}
	        	}
	            lastKwEnd = matcher.end();
	        }
	        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
	        return spansBuilder.create();
	    }
	 
}
