package es.upm.miw.equipofutbol;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import es.upm.miw.equipofutbol.models.Futbolista;

/**
 * Created by az0112 on 30/10/2015.
 */
public class FutbolistaAdapter extends ArrayAdapter<Futbolista> {

    Context _contexto;
    private ArrayList<Futbolista> _futbolistas;

    public FutbolistaAdapter(Context context, ArrayList<Futbolista> futbolistas) {
        super(context, R.layout.layout_listado_futbolista, futbolistas);
        this._futbolistas = futbolistas;
        this._contexto = context;
    }

    @Override
    public boolean isEnabled(int position) {
        return true; //se puede pusalr sobre todos los elementos.
    }

    /**
     * recupera la vista en funcion de la posicion pulsada y pasada por parametro.
     * @param position posicion pulsada.
     * @param convertView la vista reciclada.
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){ //no entra vista reciclada
            LayoutInflater inflater =
                    (LayoutInflater) _contexto.getSystemService(_contexto.LAYOUT_INFLATER_SERVICE); //obtenemos el inflater
            convertView = inflater.inflate(R.layout.layout_listado_futbolista, null); //creamos la vista
        }
        Futbolista futbolista = this._futbolistas.get(position);//obtenemos el futbolista de la posicion pulsada.

        Log.i("ADAPTER", futbolista.toString());

        if (futbolista != null){
            //encontramos los elementos en la lista para rellenarlos.
            TextView tvId =(TextView) convertView.findViewById(R.id.tvFutbolistaId);
            tvId.setText(String.format("%d", futbolista.get_id()));
            TextView tvNombre =(TextView) convertView.findViewById(R.id.tvFutbolistaNombre);
            tvNombre.setText(futbolista.get_nombre());
            TextView tvEquipo =(TextView) convertView.findViewById(R.id.tvFutbolistaEquipo);
            tvEquipo.setText(futbolista.get_equipo());

        }

        return convertView; //super.getView(position, convertView, parent);
    }
}
