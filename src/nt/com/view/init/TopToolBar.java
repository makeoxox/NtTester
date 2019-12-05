package nt.com.view.init;

import java.awt.Event;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nt.com.plugin.Plugin;
import nt.com.util.XmlParser;

/**
 * 上方工具栏
 * 
 * @author kege
 *
 */
public class TopToolBar extends ToolBar {

	public TopToolBar() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/nt/com/view/fxml/TopToolBar.fxml"));
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		// 读取插件文件夹
		File file = new File("plugin");
		if (!file.exists()) {
			return;
		} else if (!file.isDirectory()) {
			return;
		} else {
			File[] files = file.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if (pathname.isDirectory()) 
						return true;
					else
						return false;
				}
			});
			// 加载插件文件夹
			Platform.runLater(new Runnable() {
				@SuppressWarnings("resource")
				@Override
				public void run() {
					for (File plugin : files) {
						String name = plugin.getName();
						String headFileName = plugin.getAbsolutePath() + File.separator + name + ".plg";
						File headFile = new File(headFileName);
						if (!headFile.exists() && !headFile.isDirectory()) {
							continue;
						}
						System.out.println("加载插件 " + name);
						try {
							Document doc = XmlParser.getDocByAbsolutePath(headFileName);
							String tooltip = doc.getRootElement().element("tooltip").getText();
							String iconpath = doc.getRootElement().element("icon").getText();
							String classname = doc.getRootElement().element("class").getText();
							Button button = new Button();
							if (tooltip != null) {
								button.setTooltip(new Tooltip(tooltip));
							}
							if (iconpath != null) {
								ImageView iv = new ImageView();
								iv.setFitHeight(15);
								iv.setFitWidth(15);
								try {
									iv.setImage(new Image(new FileInputStream(
											new File(plugin.getAbsolutePath() + File.separator + iconpath))));
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
								button.setGraphic(iv);
							} else {
								ImageView iv = new ImageView();
								iv.setFitHeight(15);
								iv.setFitWidth(15);
								iv.setImage(new Image(getClass().getResourceAsStream("/res/add.png")));
								button.setGraphic(iv);
							}
							if (classname != null) {
								File[] files = new File(plugin.getAbsolutePath()).listFiles(new FileFilter() {
									@Override
									public boolean accept(File pathname) {
										String name = pathname.getName();
										String exName = name.substring(name.lastIndexOf('.') + 1, name.length());
										if (exName.equals("jar"))
											return true;
										else
											return false;
									}
								});
								URL[] urls = new URL[files.length];
								for (int i = 0; i < files.length; i++) {
									try {
										urls[i] = files[i].toURI().toURL();
									} catch (MalformedURLException e) {
										e.printStackTrace();
									}
								}
								URLClassLoader loader = new URLClassLoader(urls);
								try {
									Plugin plg = (Plugin) loader.loadClass(classname).newInstance();
									button.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
										@Override
										public void handle(ActionEvent event) {
											plg.embed();
										}
									});
								} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
									e.printStackTrace();
								}
							}
							((TopToolBar) MainView.parent.lookup("#toptoolbar")).getItems().add(button);
							((TopToolBar) MainView.parent.lookup("#toptoolbar")).getItems().add(new Separator(Orientation.VERTICAL));
						} catch (DocumentException e) {
							e.printStackTrace();
						}
					}
				}
			});

		}
	}

}
