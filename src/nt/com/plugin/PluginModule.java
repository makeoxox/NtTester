package nt.com.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nt.com.view.init.MainView;
import nt.com.view.init.TopToolBar;

/**
 * 
 * @author 插件模块
 *
 */
public class PluginModule {
	
	public static void plug() {
		
		PluginManager manager = new PluginManager();
		manager.loadPlugin();
		Map<String, PluginModel> map = PluginManager.getPluginModels();
		for (Map.Entry<String, PluginModel> entry : map.entrySet()) {
			PluginModel model = entry.getValue();
			if(!model.isEnable()) return;
			Button button = new Button();
			button.setId(model.getName());
			String tooltip = model.getTooltip();
			if (tooltip != null && tooltip != "") {
				button.setTooltip(new Tooltip(tooltip));
			}
			String iconpath = model.getIcon();
			String plgRootPath = model.getPath();
			if (iconpath != null && tooltip != "") {
				ImageView iv = new ImageView();
				iv.setFitHeight(15);
				iv.setFitWidth(15);
				try {
					iv.setImage(
							new Image(new FileInputStream(new File(plgRootPath + File.separator + iconpath))));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				button.setGraphic(iv);
			} else {
				ImageView iv = new ImageView();
				iv.setFitHeight(15);
				iv.setFitWidth(15);
				iv.setImage(new Image(PluginModule.class.getResourceAsStream("/res/add.png")));
				button.setGraphic(iv);
			}
			Plugin plugin = model.getPlugin();
			button.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					plugin.embed();
				}
			});
			TopToolBar ttb =  (TopToolBar) MainView.parent.lookup("#toptoolbar");
			ttb.getItems().add(button);
		}
		
		//插件启用事件 
		manager.addPluginAddEventListener(new PluginAddEventListener() {
			
			@Override
			public void handleEvent(PluginChangeEvent event) {
				String name = event.getPluginName();
				PluginModel model = map.get(name);
				Button button = new Button();
				button.setId(model.getName());
				String tooltip = model.getTooltip();
				if (tooltip != null && tooltip != "") {
					button.setTooltip(new Tooltip(tooltip));
				}
				String iconpath = model.getIcon();
				String plgRootPath = model.getPath();
				if (iconpath != null && tooltip != "") {
					ImageView iv = new ImageView();
					iv.setFitHeight(15);
					iv.setFitWidth(15);
					try {
						iv.setImage(new Image(new FileInputStream(new File(plgRootPath + File.separator + iconpath))));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					button.setGraphic(iv);
				} else {
					ImageView iv = new ImageView();
					iv.setFitHeight(15);
					iv.setFitWidth(15);
					iv.setImage(new Image(PluginModule.class.getResourceAsStream("/res/add.png")));
					button.setGraphic(iv);
				}
				Plugin plugin = model.getPlugin();
				button.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						plugin.embed();
					}
				});
				TopToolBar ttb =  (TopToolBar) MainView.parent.lookup("#toptoolbar");
				ttb.getItems().add(button);
				
			}
		});
		
		//插件禁用事件 
		manager.addPluginRemoveEventListener(new PluginRemoveEventListener() {
			
			@Override
			public void handleEvent(PluginChangeEvent event) {
				String name = event.getPluginName();
				TopToolBar ttb =  (TopToolBar) MainView.parent.lookup("#toptoolbar");
				Button btn = (Button) ttb.lookup("#"+name);
				ttb.getItems().remove(btn);
				
			}
		});
		
		
	}
}
