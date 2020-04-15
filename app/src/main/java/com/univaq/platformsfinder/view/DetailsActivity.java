package com.univaq.platformsfinder.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.univaq.platformsfinder.R;
import com.univaq.platformsfinder.model.PlatformTable;
import com.univaq.platformsfinder.tools.DBHandler;
import com.univaq.platformsfinder.tools.StringMaker;

/**
 * Details activity.
 */
public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DETAILSACTIVITY";
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
        titleTextView.setTextColor(this.getColor(R.color.colorAccent));
        titleTextView.setTextSize(24);

        //Details TextView
        TextView detailsTextView = findViewById(R.id.detailsTextView);
        StringMaker maker = new StringMaker();
        SpannableString currentLine = maker.detailsLine(getString(R.string.state_string), table.stato, this);
        detailsTextView.setText(currentLine);
        currentLine = maker.detailsLine(getString(R.string.year_string), Integer.toString(table.anno), this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.type_string), table.tipo, this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.mineral_string), table.minerale, this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.operator_string), table.operatore, this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.connectedWells_string), Integer.toString(table.numeroPozziAllacciati), this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.nonSupplyingWells_string), Integer.toString(table.pozziProduttiviNonEroganti), this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.productionWells_string), Integer.toString(table.pozziInProduzione), this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.inMonitoringWells_string), Integer.toString(table.pozziInMonitoraggio), this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.mineraryTitle_string), table.titoloMinerario, this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.mainCentral_string), table.collegataAllaCentrale, this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.zone_string), table.zona, this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.paper_string), table.foglio, this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.unmigSection_string), table.sezione, this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.portAuthorities_string), table.capitaneriaDiPorto, this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.coastDistance_string), table.distanzaCosta + " m", this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.height_string), table.altezzaSlm + " m", this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.bottomdepth_string), table.profonditaFondale + " m", this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.dimensions_string), table.dimensioni, this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.longitude_string), Double.toString(table.longitudine), this);
        detailsTextView.append(currentLine);
        currentLine = maker.detailsLine(getString(R.string.latitude_string), Double.toString(table.latitudine), this);
        detailsTextView.append(currentLine);

        detailsTextView.setMovementMethod(new ScrollingMovementMethod());
    }

}
