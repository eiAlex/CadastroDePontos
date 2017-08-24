package fcsl.cnec.edu.br.cadastrodepontos;

import android.location.Address;
import android.location.Geocoder;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Álex-PC on 05/12/2016.
 */





public class GeoLocalizacao extends FragmentActivity {

    //variaveis
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private double lon;
    private double lat;


    //getts e setts
    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }



    //metodo busca cep

    public void buscarCEP(String cepUsuario){
        // Configura essa activity para permitir o acesso à internet pela
        // Thread principal.
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        // Cria uma conexão HTTPs nula.
        HttpsURLConnection conexao = null;
        // Cria um leitor.
        BufferedReader leitor = null;



        try {
            // Pega o cep digitado pelo o usuário.


          // cepUsuario = c.getEditTextCEP().getText().toString();
            String link = "https://viacep.com.br/ws/" + cepUsuario +"/json";

            // Cria um objeto URL a partir de uma String que é uma URL.
            URL url = new URL(link);
            // Abre uma conexão HTTP a partir do objeto URL.
            conexao = (HttpsURLConnection) url.openConnection();

            // Lê o conteúdo retornado pela conexão.
            InputStream is = conexao.getInputStream();
            // Passa o stream para o buffered reader
            leitor = new BufferedReader(new InputStreamReader(is));

            // Realiza a leitura linha a linha.
            String linha = "";
            final StringBuilder resultado = new StringBuilder();
            while ((linha = leitor.readLine()) != null) {
                resultado.append(linha);
            }


            Bar c = new Gson().fromJson(resultado.toString(), Bar.class);

            rua  = c.getLogradouro();
            bairro = c.getBairro();
            estado = c.getUf();
            cidade = c.getLocalidade();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conexao != null)
                conexao.disconnect();

            if (leitor != null)
                try {
                    leitor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }


    }



    public void BucaLocalizacao(String numero)  {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> enderecos = null ;
        try {
           /* enderecos = geocoder.getFromLocationName("Av. Sampaio Vidal, Centro, Marília, SP", 1);*/
            enderecos = geocoder.getFromLocationName(rua +numero + bairro + cidade + estado, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (enderecos.size() != 0) {
            Log.v("tag", "coordenadas " + enderecos.get(0).getLatitude() + ", " + enderecos.get(0).getLongitude());

            lat = (enderecos.get(0).getLatitude());
            lon = (enderecos.get(0).getLongitude());

        }

    }



}
