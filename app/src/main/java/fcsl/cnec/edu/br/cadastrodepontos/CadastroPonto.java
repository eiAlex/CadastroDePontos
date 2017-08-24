package fcsl.cnec.edu.br.cadastrodepontos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CadastroPonto extends Fragment {


    public CadastroPonto() {
        // Required empty public constructor
    }

    //variaveis
    private EditText editTextCEP;
    private EditText editTextLogradouro;
    private EditText editTextBairro;
    private EditText editTextNumero;
    private EditText editTextUf;
    private EditText editTextCidade;
    private TextView textViewLatitude;
    private TextView textViewLongitude;
    private EditText editTextNomeDoBar;


    //botoes
    private Button buttonPesCep;
    private Button buttonSalvarBar;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_cadastro_ponto, container, false);

        editTextCEP = (EditText) view.findViewById(R.id.editTextCep);
        editTextLogradouro = (EditText) view.findViewById(R.id.editTextLogradouro);
        editTextBairro = (EditText) view.findViewById(R.id.editTextBairro);
        editTextNumero = (EditText) view.findViewById(R.id.editTextNumero);
        editTextUf = (EditText) view.findViewById(R.id.editTextUf);
        editTextCidade = (EditText) view.findViewById(R.id.editTextCidade);
        editTextNomeDoBar =(EditText) view.findViewById(R.id.editTextNomeDoBar);
        textViewLatitude = (TextView)view.findViewById(R.id.textViewLatitude2);
        textViewLongitude = (TextView)view.findViewById(R.id.textViewLongitude2);



        buttonPesCep = (Button)view.findViewById(R.id.buttonCep);
        buttonSalvarBar = (Button)view.findViewById(R.id.buttonSalvarBar);


        //Precher dados
        //evento botão pesquisar

        buttonPesCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeoLocalizacao g = new GeoLocalizacao();
                g.buscarCEP(editTextCEP.getText().toString());
                g.BucaLocalizacao(editTextNumero.getText().toString());

                editTextLogradouro.setText(g.getRua());
                editTextBairro.setText(g.getBairro());
                editTextCidade.setText(g.getCidade());
                editTextUf.setText(g.getEstado());
                textViewLatitude.setText(String.valueOf(g.getLat()));
                textViewLongitude.setText(String.valueOf(g.getLon()));


            }
        });

        buttonSalvarBar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                   Bar bar = new Bar();
                   BarDAO barDAO = new BarDAO(getContext());
                            bar.setNomeLocal(editTextNomeDoBar.getText().toString());
                            bar.setLatitude(textViewLatitude.getText().toString());
                            bar.setLongitude(textViewLongitude.getText().toString());
                            barDAO.salvar(bar);






                        }
                    });



   return  view;


    }










}
