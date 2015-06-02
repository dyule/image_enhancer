package com.example.image_enhancer.image;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Represents an image stored in the database, along with relevant meta-data
 */
@Entity
public class Image {
	
	/**
	 * The ID of the image
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id; 
	
	/**
	 * The filename of the image
	 */
	private String name;
	
	/**
	 * The image data itself
	 */
	@Lob
	private byte[]  data;
	
	/**
	 * Gets the id of the image
	 * @return A Long representing the image's ID
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the data for the image
	 * @param data A byte array representing the image data
	 */
	public void setData(byte [] data) {
		this.data = data;
	}
	
	/**
	 * Gets the image data
	 * @return A byte array representing the image data
	 */
	public byte [] getData() {
		return data;
	}
	
	/**
	 * Sets the image's filename
	 * @param name A String representing the name of the file
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the name of the image
	 * @return A String representing the name of the file
	 */
	public String getName() {
		return name;
	}

}
