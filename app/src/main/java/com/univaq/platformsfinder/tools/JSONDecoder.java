package com.univaq.platformsfinder.tools;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.univaq.platformsfinder.model.PlatformTable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONDecoder
{
    private static final String TAG = "DECODER";

    public LatLng addressJSONDecoder(JSONObject obj) throws org.json.JSONException
    {
        JSONArray array = obj.getJSONArray("results");
        JSONObject arrayObj = array.getJSONObject(0);
        JSONObject geometryObj = arrayObj.getJSONObject("geometry");
        JSONObject locationObj = geometryObj.getJSONObject("location");
        double lat = locationObj.getDouble("lat");
        double lng = locationObj.getDouble("lng");
        return new LatLng(lat, lng);
    }

    public ArrayList<PlatformTable> platformsJSONDecoder(JSONArray array) throws org.json.JSONException
    {
        Log.d(TAG, "inside decoder");
        ArrayList<PlatformTable> toReturn = new ArrayList<>();
        for (int i = 0; i < array.length(); i++)
        {
            JSONObject obj = array.getJSONObject(i);
            PlatformTable table = new PlatformTable();
            table.denominazione = obj.getString("cdenominazione__");
            table.id = Integer.parseInt(obj.getString("ccodice"));
            table.stato = obj.getString("cstato");
            table.anno = Integer.parseInt(obj.getString("canno_costruzione").split("\\|")[0]);
            table.tipo = obj.getString("ctipo").split("\\|")[0];
            table.minerale = obj.getString("cminerale");
            table.operatore = obj.getString("coperatore").split("\\|")[0];
            table.numeroPozziAllacciati = possibleEmptytoIntConverter(obj.getString("cnumero_pozzi_allacciati__"));
            table.pozziProduttiviNonEroganti = possibleEmptytoIntConverter(obj.getString("cpozzi_produttivi_non_eroganti"));
            table.pozziInProduzione = possibleEmptytoIntConverter(obj.getString("cpozzi_in_produzione"));
            table.pozziInMonitoraggio = possibleEmptytoIntConverter(obj.getString("cpozzi_in_monitoraggio"));
            table.titoloMinerario = obj.getString("ctitolo_minerario").split("\\|")[0];
            table.collegataAllaCentrale = possibleEmptyStringConverter(obj.getString("ccollegata_alla_centrale"));
            table.zona = possibleEmptyStringConverter(obj.getString("czona"));
            table.foglio = (obj.getString("cfoglio").split("\\|")[0]);
            table.sezione = possibleEmptyStringConverter(obj.getString("csezione_unmig"));
            table.capitaneriaDiPorto = possibleEmptyStringConverter(obj.getString("ccapitaneria_di_porto"));
            table.distanzaCosta = possibleEmptytoIntConverter(obj.getString("cdistanza_costa___km_"));
            table.altezzaSlm = possibleEmptytoIntConverter(obj.getString("caltezza_slm__m_"));
            table.profonditaFondale = possibleEmptytoIntConverter(obj.getString("cprofondit__fondale__m_"));
            table.dimensioni = possibleEmptyStringConverter(obj.getString("cdimensioni"));
            table.longitudine = Double.parseDouble(obj.getString("clongitudine__wgs_84__"));
            table.latitudine = Double.parseDouble(obj.getString("clatitudine__wgs84__"));
            toReturn.add(table);
        }
        Log.d(TAG, "number = " + toReturn.size());
        return toReturn;
    }

    private int possibleEmptytoIntConverter(String toConvert)
    {
        if(toConvert.equals(""))
            return 0;
        else
            return Integer.parseInt(toConvert);
    }

    private String possibleEmptyStringConverter(String toConvert)
    {
        if(toConvert.equals(""))
            return "";
        else
            return toConvert.split("|")[0];
    }
}
