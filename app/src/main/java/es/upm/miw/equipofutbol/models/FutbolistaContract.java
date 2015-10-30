package es.upm.miw.equipofutbol.models;

import android.provider.BaseColumns;

/**
 * Siguiento el patron Contract hacemos una clase abstracta, para representar el almacenamiento de la tabla en BBDD.
 */
public class FutbolistaContract {
    //implementamos la interfaz BaseColumns
    public static class tablaFutbolista implements BaseColumns{

        public static  final String TABLE_NAME = "futbolistas"; //nombre de la tabla.
        //el nombre de la columna en la BBDD para cada campo.
        public static final String COL_NAME_NOMBRE = "nombre";
        public static final String COL_NAME_DORSAL = "dorsal";
        public static final String COL_NAME_LESIONADO = "lesionado";
        public static final String COL_NAME_EQUIPO = "equipo";
        public static final String COL_NAME_URL = "url_imagen";

    }
}
