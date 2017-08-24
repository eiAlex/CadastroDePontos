package fcsl.cnec.edu.br.cadastrodepontos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fcsl.cnec.edu.br.cadastrodepontos.*;


/**
 * Created by Álex-PC on 05/12/2016.
 */
public class BarDAO {

    DBHelper dbHelper;
    SQLiteDatabase db;

    public BarDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    public String salvar(Bar bar) {
        // Cria um objeto para armazenar os valores por colunas.
        ContentValues valores = new ContentValues();
        valores.put("nomeLocal", bar.getNomeLocal());
        valores.put("latitude", bar.getLatitude());
        valores.put("longitude", bar.getLongitude());


        // Abre a conexão com o banco, com possibilidade de escrita.
        db = dbHelper.getWritableDatabase();

        // Insere no banco de dados.
        long resultado = db.insert("Bar", null, valores);

        // Fecha a conexão.
        db.close();

        // Retorna conforme o resultado da inserção.
        return resultado == -1 ? "Erro" : "Sucesso";
    }

    public List<Bar> listar() {
        // Cria e instancia uma lista de contatos.
        List<Bar> lista = new ArrayList<>();

        // Cria uma SQL (consulta ao banco)
        String sql = "SELECT * FROM Bar";

        // Abre o banco no formato somente leitura (getReadableDatabase).
        db = dbHelper.getReadableDatabase();

        // executa a consulta e retorna o resultado em formato cursor.
        Cursor cursor = db.rawQuery(sql, null);

        // Verifica se o cursor está preenchido
        // e move para o primeiro registro.
        if (cursor.moveToFirst()) {
            Bar bar;
            // lê os dados retornados.
            do {
                bar = new Bar();
                bar.setId(cursor
                        .getInt(cursor.getColumnIndex("id")));
                bar.setNomeLocal(cursor
                        .getString(cursor.getColumnIndex("nomeLocal")));
                bar.setLatitude(cursor
                        .getString(cursor.getColumnIndex("latitude")));
                bar.setLongitude(cursor
                        .getString(cursor.getColumnIndex("longitude")));

                lista.add(bar);
            } while (cursor.moveToNext());
        }

        // Fecha a conexão com o banco de dados.
        db.close();

        // retorna a lista.
        return lista;
    }


}
