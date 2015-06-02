package com.example.image_enhancer;

import javax.servlet.annotation.WebServlet;

import org.vaadin.addon.itemlayout.grid.ItemGrid;
import org.vaadin.addon.itemlayout.layout.AbstractItemLayout;
import org.vaadin.addon.itemlayout.layout.model.ItemGenerator;

import com.example.image_enhancer.image.Image;
import com.example.image_enhancer.image.ImageUploader;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

/**
 * The main UI.  Sets up the UI and connects the data and business logic 
 *
 */
@SuppressWarnings("serial")
@Theme("image_enhancer")
public class ImageEnhancerUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = ImageEnhancerUI.class, widgetset = "com.example.image_enhancer.widgetset.Image_enhancerWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	/**
	 * The images that are being stored in the database.
	 */
	private JPAContainer<Image> images = JPAContainerFactory.make(Image.class, "image");
	

	/**
	 * Set up the main UI of the program
	 */
	@Override
	protected void init(VaadinRequest request) {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		
		initUpload(layout);
		initGrid(layout);
	}
	
	/**
	 * Set up the upload area, and connect it to its business logic
	 * @param layout The layout to add the upload panel to
	 */
	private void initUpload(Layout layout) {
		ImageUploader receiver = new ImageUploader(images); 

		// Create the upload with a caption and set receiver later
		Upload upload = new Upload("Upload Image Here", receiver);
		upload.setButtonCaption("Start Upload");
		upload.addSucceededListener(receiver);
		// Put the components in a panel
		Panel panel = new Panel("Cool Image Storage");
		panel.setContent(upload);
		layout.addComponent(panel);
	}
	
	/**
	 * Set up the image display grid.
	 * @param layout The layout to add the image display to
	 */
	private void initGrid(Layout layout) {
	
		// Create a new ImageGrid
		// This is a 3rd party add-on
		ItemGrid imageTable = new ItemGrid(3);
		imageTable.setContainerDataSource(images);
		
		// The item generator determines how the items (in this case images)
		// will be displayed
		imageTable.setItemGenerator(new ItemGenerator() {
			// Based on code found here: https://vaadin.com/forum/#!/thread/261790/580577
			// For details, see DatabaseImageResource
			@Override
			public Component generateItem(AbstractItemLayout pSource, Object pItemId) {
				Embedded eImage = new Embedded("", new DatabaseImageResource(images.getItem(pItemId).getEntity()));
				eImage.setWidth(200, Unit.PIXELS );
				return eImage;
			}
			
			//We can generate any item we like
			@Override
			public boolean canBeGenerated(AbstractItemLayout pSource, Object pItemId,
					Object pPropertyChanged) {
				return true;
			}
		});
		
		// Add the grid to the layout, and make sure it expands
		layout.addComponent(imageTable);
		imageTable.setSizeFull();
	}

}