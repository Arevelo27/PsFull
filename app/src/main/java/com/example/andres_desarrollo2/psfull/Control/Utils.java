package com.example.andres_desarrollo2.psfull.Control;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by TSI on 06/04/2016.
 */
public class Utils {
    private static Pattern pattern;
    private static Matcher matcher;

    public static Bundle retonarBundleDialog(String titulo, String mensaje, String nombreBoton, int numBotones, String nameBotonPos, String nameBotonNeg) {
        Bundle bundle = new Bundle();
        //Empaqueta titulo
        bundle.putString("Titulo", titulo);
        //Empaqueta mensaje
        bundle.putString("Mensaje", mensaje);
        //Empaqueta botón que solicita el Diálogo
        bundle.putString("Boton", nombreBoton);
        //Empaqueta el nombre del PositiveButton que solicita el Diálogo
        bundle.putString("NomBtnPositivo", nameBotonPos);
        if (numBotones == 2) {
            //Empaqueta el nombre del NegativeButton que solicita el Diálogo
            bundle.putString("NomBtnNegativo", nameBotonNeg);
        } else {
            //Empaqueta el nombre del NegativeButton que solicita el Diálogo
            bundle.putString("NomBtnNegativo", "CANCEL");
        }

        //Empaqueta	el número de botones del diálogo
        bundle.putInt("NumBotones", numBotones);
        return bundle;
    }

    public static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNotNull(String cadena) {
        return cadena != null && cadena.trim().length() >= 0 ? true : false && cadena.equals(" ");
    }

    public static boolean validaEmail(String email){
        pattern = Pattern.compile(Constantes.EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @NonNull
    public static String[] selectCadena(String selectedItem, int cantCampos) {
        String subCadenas[];
        String delimitador = ",";
        subCadenas = selectedItem.split(delimitador, cantCampos);
        return subCadenas;
    }
}
