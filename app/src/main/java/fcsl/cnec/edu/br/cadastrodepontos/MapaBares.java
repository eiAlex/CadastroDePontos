package fcsl.cnec.edu.br.cadastrodepontos;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapaBares extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener{


    private GoogleMap mMap;
    private static final String TAG = "ProviderFragmentV1";

    private LocationManager locationManager;

    private double lon;
    private double lat;
    private Button buttonMapaNormal;
    private Button buttonMapahibrido;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);


        buttonMapaNormal = (Button) getActivity().findViewById(R.id.buttonMapaNormal);
        buttonMapahibrido = (Button) getActivity().findViewById(R.id.buttonMapasHibrido);

        buttonMapaNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });



        buttonMapahibrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap){
        try{

            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            Criteria criteria = new Criteria();

            String provider = locationManager.getBestProvider(criteria, true);

            Toast.makeText(getActivity(),"Provider: "+ provider, Toast.LENGTH_LONG);



            mMap = googleMap;

            mMap.getUiSettings().setZoomControlsEnabled(true);

            mMap.setMyLocationEnabled(true);
           mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);// descomentado




        }catch (SecurityException ex){

            Log.e(TAG, "Error",ex);
        }


        mMap.setOnMapClickListener(this);

    //Cria uma instacia de BarDAO para carregar os pontos
        BarDAO barDAO = new BarDAO(getContext());

        //Carrega os bares

        List<Bar> lista = barDAO.listar();
        for (Bar b :lista){
            double lat1 = Double.parseDouble(b.getLatitude());
            double lon1 = Double.parseDouble(b.getLongitude());
            String local = b.getNomeLocal();

            LatLng loc= new LatLng(lat1,lon1);
            MarkerOptions marker = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher3))
                    .anchor(0.0f,1.0f);
            marker.position(loc);
            marker.title(local);
            mMap.addMarker(marker);

          //  mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 12));
        }



        // coordenadas sidney
       // LatLng sydney = new LatLng(-33.87365, 151.20689);
      /*  LatLng sydney = new LatLng(lat, lon);

        MarkerOptions marker = new MarkerOptions();
        marker.position(sydney);
        marker.title("Sydney");
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }


    @Override
    public void onMapClick(LatLng latLng) {

        Toast.makeText(getContext(),"Coordenadas: " + latLng.toString(),Toast.LENGTH_SHORT).show();




    }
}
