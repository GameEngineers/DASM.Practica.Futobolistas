package es.upm.miw.equipofutbol;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import es.upm.miw.equipofutbol.models.Futbolista;
import es.upm.miw.equipofutbol.models.RepositorioFutbolistas;

public class ActividadPrincipal extends AppCompatActivity {

    ArrayList<Futbolista> futbolistas;
    ListView lvListadoFutbolistas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actividad_principal);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        RepositorioFutbolistas repositorio = new RepositorioFutbolistas(getApplicationContext());

        repositorio.add(new Futbolista(1, "Jugador 1", 1, true, "Primera", null));

        this.futbolistas = repositorio.getAll();
        ArrayAdapter<Futbolista> adaptador = new FutbolistaAdapter(this, futbolistas);
        lvListadoFutbolistas = (ListView) findViewById(R.id.lvListadoFutbolistas);
        lvListadoFutbolistas.setAdapter(adaptador);
        return;
    }

}
