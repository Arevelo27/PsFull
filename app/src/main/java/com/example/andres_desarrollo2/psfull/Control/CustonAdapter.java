package com.example.andres_desarrollo2.psfull.Control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andres_desarrollo2.psfull.R;

import java.util.List;
/**
 * Created by TSI on 24/05/2016.
 */
public class CustonAdapter<T> extends ArrayAdapter<T> {

    public CustonAdapter(Context context, List<T> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Obteniendo una instancia del inflater
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Salvando la referencia del View de la fila
        View listItemView = convertView;

        //Comprobando si el View no existe
        if (null == convertView) {
            //Si no existe, entonces inflarlo con two_line_list_item.xml
            listItemView = inflater.inflate(
                    R.layout.activity_list_radicados_row,
                    parent,
                    false);
        }

        //Obteniendo instancias de los text views
        TextView titulo = (TextView) listItemView.findViewById(R.id.text1);
        TextView subtitulo = (TextView) listItemView.findViewById(R.id.text2);
        ImageView categoria = (ImageView) listItemView.findViewById(R.id.category);


        //Obteniendo instancia de la Tarea en la posición actual
        T item = (T) getItem(position);

        //Dividir la cadena en Nombre y Hora
        String cadenaBruta;
        String subCadenas[];
        String delimitador = ",";

        cadenaBruta = item.toString();
        subCadenas = cadenaBruta.split(delimitador, 3);

        titulo.setText(subCadenas[0]);
        subtitulo.setText(subCadenas[1]);
        //categoria.setImageResource(Integer.parseInt(subCadenas[2]));
        String tipo = subCadenas[2];
        if(tipo.equals("01")){
            categoria.setImageResource(R.drawable.t_01_electrico);
        }else if(tipo.equals("02")){
            categoria.setImageResource(R.drawable.ic_menu_gallery);
        }else if(tipo.equals("03")){
            categoria.setImageResource(R.drawable.ic_menu_gallery);
        }else if(tipo.equals("04")){
            categoria.setImageResource(R.drawable.t_04_locativo);
        }else if(tipo.equals("05")){
            categoria.setImageResource(R.drawable.t_05_civil);
        }else if(tipo.equals("06")){
            categoria.setImageResource(R.drawable.t_06_equipos_de_oficina);
        }else if(tipo.equals("07")){
            categoria.setImageResource(R.drawable.t_07_muebles_de_oficina);
        }else if(tipo.equals("08")){
            categoria.setImageResource(R.drawable.t_08_tecnologia);
        }

        //Devolver al ListView la fila creada
        return listItemView;

    }
}
