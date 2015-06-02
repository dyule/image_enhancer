package com.example.image_enhancer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.example.image_enhancer.image.Image;
import com.vaadin.server.StreamResource;
// Based on code found here: https://vaadin.com/forum/#!/thread/261790/580577

/**
 * Represents an image stored in the database
 *
 */
@SuppressWarnings("serial")
public class DatabaseImageResource extends StreamResource {
	
	/**
	 * Represents an image source that is in a database 
	 *
	 */
	static private class ImageStreamSource implements StreamSource {
		
		/**
		 * The image that is being stored
		 */
		private Image image;
		
		/**
		 * Create a new source with this image
		 * @param image The Image in the database
		 */
		public ImageStreamSource(Image image) {
			this.image = image;
		}

		/**
		 * Create the input stream that will transmit the image to the client
		 */
		@Override
		public InputStream getStream() {
			return new ByteArrayInputStream(image.getData());
		}
		
	}

	/**
	 * Create a new resource centered around the given database image
	 * @param image The Image to create a database around
	 */
	public DatabaseImageResource(Image image) {
		super(new ImageStreamSource(image), image.getName());
	}

}
