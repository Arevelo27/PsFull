package com.example.andres_desarrollo2.psfull.Control;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.andres_desarrollo2.psfull.MainActivity;
import com.example.andres_desarrollo2.psfull.contentActivity;


/**
 * Created by Andres-Desarrollo2 on 05/04/2016.
 */
public class Dialogs extends DialogFragment {
    String titulo,mensaje, boton, btnPositivo, btnNegatrivo;
    int numBotones;

    public Dialogs() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Recibe paquete
        Bundle bundle = this.getArguments();
        //Desempaqueta mensaje
        titulo = bundle.getString("Titulo");
        //Desempaqueta mensaje
        mensaje = bundle.getString("Mensaje");
        //Desempaqueta botón que solicita Diálogo
        boton = bundle.getString("Boton");
        //Desempaqueta Número de botones del Diálogo
        numBotones = bundle.getInt("NumBotones");
        //Desempaqueta el nombre del PositiveButton que solicita el Diálogo
        btnPositivo = bundle.getString("NomBtnPositivo");
        //Desempaqueta el nombre del NegativeButton que solicita el Diálogo
        btnNegatrivo = bundle.getString("NomBtnNegativo");

        //Use the Builder class for convenient dialog construc0on
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle(titulo).setMessage(mensaje).setPositiveButton(btnPositivo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Mostrar segunda Ac0vidad cuando se pulse OK
                if (boton.equals("Ppto")) {

                }else if (boton.equals("Login")) {
                    Intent in = new Intent(getActivity(), MainActivity.class);
                    in.putExtra("id", id);// mandar id
                    getActivity().startActivity(in);
                    getActivity().finish();
                } else if (boton.equals("Exit")) {
                    getActivity().finish();
                }
            }
        });

        if (numBotones == 2) {
            builder.setNegativeButton(btnNegatrivo, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //User cancelled the dialog
                }
            });
        }
        //Create the AlertDialog object and return it
        return builder.create();
    }
}

