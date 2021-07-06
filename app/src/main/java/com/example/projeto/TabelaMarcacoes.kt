package com.example.projeto

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaMarcacoes(db: SQLiteDatabase)  {

    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL, $DOSES INT NOT NULL, $DATA_VACINA DATETIME NOT NULL, $CAMPO_ID_PACIENTE INTEGER NOT NULL, FOREIGN KEY($CAMPO_ID_PACIENTE) REFERENCES ${TabelaPacientes.NOME_TABELA}, $CAMPO_ID_VACINA INTEGER NOT NULL, FOREIGN KEY($CAMPO_ID_VACINA) REFERENCES ${TabelaVacinas.NOME_TABELA})")
    }

    fun insert(values: ContentValues): Long {
        return db.insert(NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(NOME_TABELA, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object {
        const val CAMPO_NOME = "nome"
        const val NOME_TABELA = "marcacoes"
        const val DOSES = "doses"
        const val DATA_VACINA = "data_marcacao"
        const val CAMPO_ID_PACIENTE = "id_paciente"
        const val CAMPO_ID_VACINA = "id_vacina"
        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, DOSES, DATA_VACINA, CAMPO_ID_PACIENTE, CAMPO_ID_VACINA)



    }
}
