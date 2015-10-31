package es.upm.miw.equipofutbol.models;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

//para imporatar una clase con statics.
import java.util.ArrayList;

import static es.upm.miw.equipofutbol.models.FutbolistaContract.tablaFutbolista;
/**
 * Clase para la gestión de la BBDD. extiende SQLiteHelper para la gestión.
 */
public class RepositorioFutbolistas extends SQLiteOpenHelper {

    //el nombre del archivo de la BBDD.
    private static final String DATABASE_FILENAME = "futbolistas.db";

    //cambiando el numero de version de la base de datos provocamos que se haga una actualizacion (upgrade).
    private static final int DATABASE_VERSION = 1;
    //nuestra base de datos. conveniente implementar un singleton para tener una unica instancia.
    //viene dada por la padre por lo que no hay que declararla usando el helper.
    //SQLiteDatabase db;

    //creamos el constructor heredado de la clase padre. No tenemos qie inicializar la db aqui, se hará en el oncreate.
    public RepositorioFutbolistas(Context context) {
        //construimos el padre con el nombre y la version que tenemos actualmente.
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    //encargado de generar la tabla en la database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //para mirar la sintaxis de SQLite podemos buscarlo en la referencia de internet de SQLite.
        String sentenciaSQL = "CREATE TABLE " + tablaFutbolista.TABLE_NAME + "("
                + tablaFutbolista._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + tablaFutbolista.COL_NAME_NOMBRE + " TEXT, "
                + tablaFutbolista.COL_NAME_DORSAL + " INTEGER, "
                + tablaFutbolista.COL_NAME_LESIONADO + " INTEGER, "
                + tablaFutbolista.COL_NAME_EQUIPO + " TEXT, "
                + tablaFutbolista.COL_NAME_URL + " TEXT)";
        db.execSQL(sentenciaSQL);
    }

    /**
     * cuando cambiemos el numero de version se ejecutara este metodo.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sentenciaSQL = "DROP TABLE ID EXISTS " + tablaFutbolista.TABLE_NAME;
        db.execSQL(sentenciaSQL);
        onCreate(db); //generamos la nueva tabla
    }


    /**
     * metodo para insertar un nuevo futbolista.
     * @return
     */
    public long add(Futbolista futbolista){

        //acceso a db en modo de escritura.
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        //quitamos el id al insertar, es autonumerico evitamos colision de ids.
        //valores.put(tablaFutbolista._ID, futbolista.get_id());
        valores.put(tablaFutbolista.COL_NAME_NOMBRE, futbolista.get_nombre());
        valores.put(tablaFutbolista.COL_NAME_DORSAL, futbolista.get_dorsal());
        valores.put(tablaFutbolista.COL_NAME_LESIONADO, futbolista.is_lesionado());
        valores.put(tablaFutbolista.COL_NAME_EQUIPO, futbolista.get_equipo());
        valores.put(tablaFutbolista.COL_NAME_URL, futbolista.get_url_imagen());

        return db.insert(tablaFutbolista.TABLE_NAME, null, valores);
    }

    public ArrayList<Futbolista> getAll(){
        ArrayList<Futbolista> futbolistas = new ArrayList<>();
        //obtenemos la base de datos para lectura
        SQLiteDatabase db = getReadableDatabase();
        String sentenciaSql = "SELECT * FROM " + tablaFutbolista.TABLE_NAME;

        //devuelve un cursor para lectura de datos del select
        Cursor cursor = db.rawQuery(sentenciaSql, null);

        if (cursor.moveToFirst()){//si hay datos
            while (!cursor.isAfterLast()){
                Futbolista futbolista = new Futbolista(
                        cursor.getInt(cursor.getColumnIndex(tablaFutbolista._ID)),
                        cursor.getString(cursor.getColumnIndex(tablaFutbolista.COL_NAME_NOMBRE)),
                        cursor.getInt(cursor.getColumnIndex(tablaFutbolista.COL_NAME_DORSAL)),
                        cursor.getInt(cursor.getColumnIndex(tablaFutbolista.COL_NAME_LESIONADO)) != 0,
                        cursor.getString(cursor.getColumnIndex(tablaFutbolista.COL_NAME_EQUIPO)),
                        cursor.getString(cursor.getColumnIndex(tablaFutbolista.COL_NAME_URL))
                );
                //TODO rellenar los datos recuperados con el cursor
                futbolistas.add(futbolista);
                cursor.moveToNext();
            }
            cursor.close();
        }


        return futbolistas;
    }

    /**
     * Devuelve el número de futbolistas de la tabla
     * @return Número de futbolistas
     */
    public long count() {
        String consultaSQL = "SELECT COUNT(*) FROM " + tablaFutbolista.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(consultaSQL, null);
        cursor.moveToFirst();
        long numero = cursor.getLong(0);
        cursor.close();

        return numero;
    }

    /**
     * Elimina todos los futbolistas
     * @return long Número de filas eliminadas
     */
    public long deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tablaFutbolista.TABLE_NAME, "1", null);
    }

    /**
     * Recupera un futbolista por ID
     * @param id Identificador del futbolista
     * @return  futbolistas
     */
    public Futbolista getFutbolistaByID(int id) {
        String consultaSQL = "SELECT * FROM " + tablaFutbolista.TABLE_NAME
                + " WHERE " + tablaFutbolista._ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Futbolista futbolista = null;
        Cursor cursor = db.rawQuery(
                consultaSQL,
                new String[]{ String.valueOf(id) }
        );

        if (cursor.moveToFirst()) {
            futbolista = new Futbolista(
                    cursor.getInt(cursor.getColumnIndex(tablaFutbolista._ID)),           // id
                    cursor.getString(cursor.getColumnIndex(tablaFutbolista.COL_NAME_NOMBRE)),    // nombre
                    cursor.getInt(cursor.getColumnIndex(tablaFutbolista.COL_NAME_DORSAL)),       // dorsal
                    cursor.getInt(cursor.getColumnIndex(tablaFutbolista.COL_NAME_LESIONADO)) != 0,  // lesionado
                    cursor.getString(cursor.getColumnIndex(tablaFutbolista.COL_NAME_EQUIPO)),    // equipo
                    cursor.getString(cursor.getColumnIndex(tablaFutbolista.COL_NAME_URL))        // imagen_url
            );
            cursor.close();
        }

        return futbolista;
    }

}
