package com.example.image_enhancer.image;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

@SuppressWarnings("serial")
public class ImageUploader implements Receiver, SucceededListener {
	
	/**
	 * The container that we will be uploading images into
	 */
	private JPAContainer<Image> images;
	
	/**
	 * The output stream that the uploader will write data into as it is received
	 */
    private ByteArrayOutputStream outStream;
    
    /**
     * The filename of the image being uploaded
     */
    private String filename;
    
    /**
     * Create a new ImageUploader that will add data to a JPAContainer
     * @param images A JPAContainer<Image> that will have uploaded images added to it
     */
    public ImageUploader(JPAContainer<Image> images) {
    	this.images = images;
    }
    
    /**
     * Start the upload process
     */
    public OutputStream receiveUpload(String filename,
                                      String mimeType) {
    	
    	// Create upload stream
    	outStream = new ByteArrayOutputStream();
    	// Save the filename
    	this.filename = filename;
        return outStream;
    }

    /**
     * Called if the upload was successful
     */
    public void uploadSucceeded(SucceededEvent event) {
        // Add the uploaded image to the database
    	Image newImage = new Image();
    	newImage.setData(outStream.toByteArray());
    	newImage.setName(filename);
    	images.addEntity(newImage);
    }
};