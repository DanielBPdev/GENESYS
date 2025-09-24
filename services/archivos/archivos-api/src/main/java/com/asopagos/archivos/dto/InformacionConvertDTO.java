package com.asopagos.archivos.dto;

import java.util.ArrayList;
import java.util.List;

public class InformacionConvertDTO {

	/*
	 * CONSTANTES
	 */
	
	private static final Float MARGIN_X = new Float(56);
	private static final Float MARGIN_Y = new Float(10);
	private static final Float SELLO_X = new Float(520);
	private static final Float SELLO_Y = new Float(300);
	private static final Float ALTURA = new Float(100);
    
	/*
	 * VARIABLES
	 */
	private String htmlHeader;
	private String htmlFooter;
	private String htmlSello;
    private boolean requiereSello;
	private String htmlContenido;	
	private List<Float> margenesx;
	private List<Float> margenesy;
	private List<Float> margenesSelloxy;
	private Float altura;
	
	/*
	 * CONSTRUCTOR
	 */
	public InformacionConvertDTO() {
		altura = ALTURA;
		margenesx=inicializarMargenesX();
		margenesy=inicializarMargenesY();
		margenesSelloxy=inicializarMargenesSello();
        htmlSello="<img style=\"heigth:120%; width:120%\" src=\"data:image/jpeg;base64,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\" />";
	}
    
	/*
	 * FUNCIONES Y GET/SET
	 */
	private List<Float> inicializarMargenesX(){
		List<Float> margenesx = new ArrayList<>();
		margenesx.add(MARGIN_X);
		margenesx.add(MARGIN_X);
		return margenesx;
	}
    
	private List<Float> inicializarMargenesY(){
		List<Float> margenesy = new ArrayList<>();
		margenesy.add(MARGIN_Y);
		margenesy.add(MARGIN_Y);
		return margenesy;
	}
    
	private List<Float> inicializarMargenesSello(){
		List<Float> margenesSello = new ArrayList<>();
		margenesSello.add(SELLO_X);
		margenesSello.add(SELLO_Y);
		return margenesSello;
	}
	
	public String getHtmlHeader() {
		return htmlHeader;
	}
    
	public void setHtmlHeader(String htmlHeader) {
		this.htmlHeader = htmlHeader;
	}
    
	public String getHtmlFooter() {
		return htmlFooter;
	}
    
	public void setHtmlFooter(String htmlFooter) {
		this.htmlFooter = htmlFooter;
	}
    
	public String getHtmlSello() {
		return htmlSello;
	}
    
	public void setHtmlSello(String htmlSello) {
		this.htmlSello = htmlSello;
	}
    
	public String getHtmlContenido() {
		return htmlContenido;
	}
    
	public void setHtmlContenido(String htmlContenido) {
		this.htmlContenido = htmlContenido;
	}
    
	public List<Float> getMargenesx() {
		return margenesx;
	}
    
	public void setMargenesx(List<Float> margenesx) {
		this.margenesx = margenesx;
	}
    
	public List<Float> getMargenesy() {
		return margenesy;
	}
    
	public void setMargenesy(List<Float> margenesy) {
		this.margenesy = margenesy;
	}
    
	public float getAltura() {
		return altura;
	}
    
	public void setAltura(float altura) {
		this.altura = altura;
	}
	public List<Float> getMargenesSelloxy() {
		return margenesSelloxy;
	}
    
	public void setMargenesSelloxy(List<Float> margenesSelloxy) {
		this.margenesSelloxy = margenesSelloxy;
	}

    public boolean isRequiereSello() {
        return requiereSello;
    }

    public void setRequiereSello(boolean requiereSello) {
        this.requiereSello = requiereSello;
    }
	
}
