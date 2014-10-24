package com.turno.bean;

public class bean {
	
	private static String nombreDependencia;
	private static String nombreEmpresa;
	
	public static String getNombreDependencia() {
		return nombreDependencia;
	}

	public static void setNombreDependencia(String nombreDependencia) {
		bean.nombreDependencia = nombreDependencia;
	}

	public static String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public static void setNombreEmpresa(String nombreEmpresa) {
		bean.nombreEmpresa = nombreEmpresa;
	}

}
