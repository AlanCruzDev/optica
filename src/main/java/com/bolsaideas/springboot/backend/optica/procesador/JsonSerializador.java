package com.bolsaideas.springboot.backend.optica.procesador;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

import com.bolsaideas.springboot.backend.optica.Anotaciones.JsonAtributo;

public class JsonSerializador {
	public static String ConvertJson(Object object) {
		if(Objects.isNull(object)) {
			throw new RuntimeException("El Object a sealizar no puede ser null");
		}
		Field[] atributos= object.getClass().getDeclaredFields();
		return Arrays.stream(atributos)
				.filter(f -> f.isAnnotationPresent(JsonAtributo.class))
				.map(f ->{
					f.setAccessible(true);
					String nombre = f.getAnnotation(JsonAtributo.class).nombre().equals("") 
							? f.getName() 
							: f.getAnnotation(JsonAtributo.class).nombre();
					try {
						return "\""+nombre+ "\":\""+ f.get(object)+"\"";
						
					}catch(IllegalAccessException e) {
						throw new RuntimeException("Error al serializar el json: "+e.getMessage());
					}						
				}).reduce("[{", (a,b)->{
					if("[{".equals(a)) {
						return a+b;
					}
					return a + ", "+b;
				}).concat("}]");
	}

}
