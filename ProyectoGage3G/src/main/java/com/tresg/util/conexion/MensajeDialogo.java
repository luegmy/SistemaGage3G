package com.tresg.util.conexion;

import java.util.HashMap;
import java.util.Map;

public class MensajeDialogo {
	
	  public Map<String, Object> getDialogOptions() {
	      Map<String, Object> options = new HashMap<>();
	      options.put("resizable", false);
	      options.put("draggable", true);
	      options.put("modal", true);
	      options.put("height", 400);
	      options.put("contentHeight", "100%");
	      return options;
	  }

}
