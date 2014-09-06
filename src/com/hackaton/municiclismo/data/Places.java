package com.hackaton.municiclismo.data;

public class Places {
	
	private String id;	
	private String name;
	private String title;
	private String comment;
	private String latitud;
	private String longitud;
	private String type;
	private String address;
	private String phone;
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}
	
	
	//Otros Metodos
	public String getLatitud() {
		return this.latitud;
	}
	
	public void setLatitud(String latitud){
		this.latitud = latitud;
	}
	
	public String getLongitud() {
		return this.longitud;
	}
	
	public void setLongitud(String longitud){
		this.longitud = longitud;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}


}
