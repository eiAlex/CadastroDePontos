package fcsl.cnec.edu.br.cadastrodepontos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, "barDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Bar " +
                "(id integer primary key autoincrement, " +
                "nomeLocal text not null, " +
                "latitude text not null, " +
                "longitude text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // TODO implementar num futuro distante.
    }
}