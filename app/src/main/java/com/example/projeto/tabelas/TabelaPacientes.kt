package com.example.projeto.tabelas

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPacientes(db: SQLiteDatabase) {


    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL, $DATA_NASCIMENTO DATETIME NOT NULL, $CAMPO_CONTACTO TEXT NOT NULL, $MORADA TEXT NOT NULL, $NIF TEXT NOT NULL, $ESTADO TEXT NOT NULL, FOREIGN KEY ( $CAMPO_ID_MARCACOES) REFERENCES ${TabelaMarcacoes.NOME_TABELA})")
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
        selection: String,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        return db.query(
            NOME_TABELA, columns,
            selection.toString(), selectionArgs, groupBy, having, orderBy
        )
    }

    companion object {
        const val NOME_TABELA = "pacientes"
        const val CAMPO_NOME = "nome"
        const val DATA_NASCIMENTO = "nascimento"
        const val CAMPO_CONTACTO = "contacto"
        const val MORADA = "morada"
        const val NIF = "nif"
        const val ESTADO = "estado"
        const val CAMPO_ID_MARCACOES = "id_marcacoes"
        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, CAMPO_NOME, DATA_NASCIMENTO, CAMPO_CONTACTO, MORADA, NIF, ESTADO, CAMPO_ID_MARCACOES)


    }
    /* var colunas = ""
        for (i in 0..ultimaColuna){
            if (i > 0) colunas += ","
            colunas += if (i == posColNomePaciente){
                "${TabelaMarcacoes.NOME_TABELA}.${columns[i]}"
            }else{

        }
    val tabelas = "$NOME_TABELA INNER JOIN ${TabelaMarcacoes.NOME_TABELA} ON ${TabelaMarcacoes.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_MARCACOES "
    }*/


}





