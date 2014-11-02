package com.turno.bean;

public class bean {
	
	private static String nombreDependencia;
	private static String idEmpresa;
	private static String nombreEmpresa;
	private static Integer maxEspera;
	
	public static String getNombreDependencia() {
		return nombreDependencia;
	}

	public static void setNombreDependencia(String nombreDependencia) {
		bean.nombreDependencia = nombreDependencia;
	}

	public static String getIdEmpresa() {
		return idEmpresa;
	}

	public static void setIdEmpresa(String nombreEmpresa) {
		bean.idEmpresa = nombreEmpresa;
	}

	public static Integer getMaxEspera() {
		return maxEspera;
	}

	public static void setMaxEspera(Integer maxEspera) {
		bean.maxEspera = maxEspera;
	}

	public static String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public static void setNombreEmpresa(String nombreEmpresa) {
		bean.nombreEmpresa = nombreEmpresa;
	}

}
