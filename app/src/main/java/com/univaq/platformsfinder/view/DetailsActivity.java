package com.univaq.platformsfinder.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.univaq.platformsfinder.R;
import com.univaq.platformsfinder.model.PlatformTable;
import com.univaq.platformsfinder.model.PlatformsDB;
import com.univaq.platformsfinder.tools.DBHandler;

public class DetailsActivity extends AppCompatActivity {

    private int id;
    private PlatformTable table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        id = Integer.parseInt(getIntent().getExtras().getString("ID"));
        DBHandler handler = new DBHandler();
        table = handler.getPlatformByID(id, this);

        //Title TextView
        TextView titleTextView = findViewById(R.id.detailsTitleTextView);
        titleTextView.setText(table.denominazione);

        //Details TextView
        TextView detailsTextView = findViewById(R.id.detailsTextView);
        String currentLine = getLine(getString(R.string.state_string), table.stato);
        detailsTextView.setText(currentLine);
        currentLine = getLine(getString(R.string.year_string), Integer.toString(table.anno));
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.type_string), table.tipo);
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.mineral_string), table.minerale);
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.operator_string), table.operatore);
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.connectedWells_string), Integer.toString(table.numeroPozziAllacciati));
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.nonSupplyingWells_string), Integer.toString(table.pozziProduttiviNonEroganti));
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.productionWells_string), Integer.toString(table.pozziInProduzione));
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.inMonitoringWells_string), Integer.toString(table.pozziInMonitoraggio));
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.mineraryTitle_string), table.titoloMinerario);
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.mainCentral_string), table.collegataAllaCentrale);
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.zone_string), table.zona);
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.paper_string), table.foglio);
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.unmigSection_string), table.sezione);
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.portAuthorities_string), table.capitaneriaDiPorto);
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.coastDistance_string), table.distanzaCosta + " m");
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.height_string), table.altezzaSlm + " m");
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.bottomdepth_string), table.profonditaFondale + " m");
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.dimensions_string), table.dimensioni);
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.longitude_string), Double.toString(table.longitudine));
        detailsTextView.append(currentLine);
        currentLine = getLine(getString(R.string.latitude_string), Double.toString(table.latitudine));
        detailsTextView.append(currentLine);
    }

    private String getLine(String type, String obj)
    {
        String first = type;//"<font color=#d91507>" + type + ": </font>";
        String last = obj;//"<font color=#030000>" + obj +"</font> /n";
        return first + last + "/n";
    }
}
