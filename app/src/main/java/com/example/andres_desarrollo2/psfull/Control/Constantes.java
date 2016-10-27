package com.example.andres_desarrollo2.psfull.Control;

/**
 * Created by TSI on 31/03/2016.
 */
public class Constantes {


    public static final String EMAIL_PATTERN =
            //NOMBRE.OPCIONAL@
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    //DOMINIO.COM/NET.OPCIONAL(CO)
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final String REFERENCIA_USER = "PersonalReference";
    public static final String KEY_TOKEN = "keyToken";
    public static final String KEY_PASSW = "keyPassw";

    public static final String URL = "http://192.168.1.60:2729/";
    public static final String URL_CENTROPE = URL + "centrope";
    public static final String URL_CENTROPE_DES = URL + "centropeDes";
    public static final String URL_TIPOLOGIA = URL + "tipologia";
    public static final String URL_TIPOLOGIA_DES = URL + "tipologiaDes";
    public static final String URL_LOGIN = URL + "login";
    public static final String URL_REGISTER = URL + "register";
    public static final String URL_LIST_RADICADO = URL + "listRadicado";
    public static final String URL_REGISTER_RADICADO  = URL + "registerRadi";
    public static final String URL_CONSECUTIVO_RADICADO  = URL + "consecutivo_radi";

    public static final String URL_ESTADO = URL + "estado";
    public static final String URL_ESTADO_DES = URL + "estadoDes";

    public static final String JSONArray_CENTROPE = "listCentrope";
    public static final String JSONArray_TIPOLOGIA = "listTipologia";
    public static final String JSONArray_RADICADO = "listRadicado";
    public static final String JSONArray_ESTADO = "listEstado";


}
